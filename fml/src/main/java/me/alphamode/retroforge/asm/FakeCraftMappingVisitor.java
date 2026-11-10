package me.alphamode.retroforge.asm;

import net.fabricmc.mappingio.MappedElementKind;
import net.fabricmc.mappingio.MappingVisitor;
import net.fabricmc.mappingio.adapter.ForwardingMappingVisitor;
import net.fabricmc.mappingio.tree.MappingTree;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class FakeCraftMappingVisitor extends ForwardingMappingVisitor {
    protected FakeCraftMappingVisitor(MappingVisitor output) {
        super(output);
    }

    private String remap(String source) {
        if (source == null) return null;
        return source.replace("net/minecraft", "net/fakecraft");
    }

    @Override
    public boolean visitField(String srcName, @Nullable String srcDesc) throws IOException {
        return super.visitField(srcName, remap(srcDesc));
    }

    @Override
    public boolean visitClass(String srcName) throws IOException {
        return super.visitClass(remap(srcName));
    }

    @Override
    public boolean visitMethod(String srcName, @Nullable String srcDesc) throws IOException {
        return super.visitMethod(srcName, remap(srcDesc));
    }

    @Override
    public boolean visitMethodArg(int argPosition, int lvIndex, @Nullable String srcName) throws IOException {
        return super.visitMethodArg(argPosition, lvIndex, srcName);
    }

    @Override
    public void visitDstDesc(MappedElementKind targetKind, int namespace, String desc) throws IOException {
        super.visitDstDesc(targetKind, namespace, desc);
    }

    @Override
    public void visitDstName(MappedElementKind targetKind, int namespace, String name) throws IOException {
        super.visitDstName(targetKind, namespace, remap(name));
    }


}
