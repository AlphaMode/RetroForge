package me.alphamode.retroforge.asm;

import net.fabricmc.mappingio.MappingReader;
import net.fabricmc.mappingio.MappingWriter;
import net.fabricmc.mappingio.adapter.MappingNsRenamer;
import net.fabricmc.mappingio.format.MappingFormat;
import net.fabricmc.mappingio.tree.MemoryMappingTree;
import net.fabricmc.tinyremapper.NonClassCopyMode;
import net.fabricmc.tinyremapper.OutputConsumerPath;
import net.fabricmc.tinyremapper.TinyRemapper;
import net.fabricmc.tinyremapper.TinyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class FakeCraftRemapper {
    private final Path mappingFile;
    private final MemoryMappingTree mappings;
    private final Path outputDir;

    public FakeCraftRemapper(Path outputPath) {
        this.mappingFile = outputPath.resolve("mappings.tiny");
        try {
            this.mappings = loadMappings();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load mappings", e);
        }
        this.outputDir = outputPath;
    }

    protected MemoryMappingTree loadMappings() throws IOException {
        if (Files.exists(this.mappingFile)) {
            MemoryMappingTree mappings = new MemoryMappingTree();
            MappingReader.read(this.mappingFile, MappingFormat.TINY_2_FILE, mappings);
            return mappings;
        }

        MemoryMappingTree srgMappings = new MemoryMappingTree();
        MemoryMappingTree nostalgiaMappings = new MemoryMappingTree();

        FakeCraftMappingVisitor fakeCraftVisitor = new FakeCraftMappingVisitor(nostalgiaMappings);
        MappingReader.read(new InputStreamReader(FakeCraftRemapper.class.getResourceAsStream("/joined.srg")), MappingFormat.SRG_FILE, new MappingNsRenamer(srgMappings, Map.of("source", "official", "target", "srg")));
        MappingReader.read(new InputStreamReader(FakeCraftRemapper.class.getResourceAsStream("/mappings.tiny")), MappingFormat.TINY_2_FILE, fakeCraftVisitor);

        MemoryMappingTree output = new MemoryMappingTree();
        srgMappings.accept(output);
        nostalgiaMappings.accept(output);

        MappingWriter writer = MappingWriter.create(this.mappingFile, MappingFormat.TINY_2_FILE);
        output.accept(writer);

        return output;
    }

    public Path remap(Path jar) {
        Path outputJar = this.outputDir.resolve("remapped-" + jar.getFileName());

        TinyRemapper remapper = TinyRemapper.newRemapper()
                .withMappings(TinyUtils.createMappingProvider(mappings, "srg", "named"))
                .renameInvalidLocals(false)
                .build();

        try (OutputConsumerPath outputConsumer = new OutputConsumerPath.Builder(outputJar).build()) {
            outputConsumer.addNonClassFiles(jar, NonClassCopyMode.FIX_META_INF, remapper);
            remapper.readClassPath(jar);
            remapper.readInputs(jar);
            remapper.apply(outputConsumer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            remapper.finish();
        }

        return outputJar;
    }
}
