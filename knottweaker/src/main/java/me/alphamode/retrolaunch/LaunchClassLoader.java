package me.alphamode.retrolaunch;

import net.fabricmc.loader.impl.launch.FabricLauncher;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LaunchClassLoader {
    private final FabricLauncher launcher;
    private List<IClassTransformer> transformers = new ArrayList<>(2);
    private Set<String> classLoaderExceptions = new HashSet<>();
    private Set<String> transformerExceptions = new HashSet<>();

    private IClassNameTransformer renameTransformer;

    public LaunchClassLoader(FabricLauncher launcher) {
        this.launcher = launcher;
    }

    public void registerTransformer(String transformerClassName) {
        try {
            IClassTransformer transformer = (IClassTransformer) launcher.loadIntoTarget(transformerClassName).newInstance();
            transformers.add(transformer);
            if (transformer instanceof IClassNameTransformer && renameTransformer == null) {
                renameTransformer = (IClassNameTransformer) transformer;
            }
        } catch (Exception e) {
//            LogWrapper.log(Level.ERROR, e, "A critical problem occurred registering the ASM transformer class %s", transformerClassName);
        }
    }

    public void addClassLoaderExclusion(String toExclude) {
        classLoaderExceptions.add(toExclude);
    }

    public void addTransformerExclusion(String toExclude) {
        transformerExceptions.add(toExclude);
    }

    public InputStream getResourceAsStream(String name) {
        return launcher.getResourceAsStream(name);
    }

    public ClassLoader getClassLoader() {
        return launcher.getTargetClassLoader();
    }

    public void addToClassPath(Path path) {
        launcher.addToClassPath(path);
    }

    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return launcher.loadIntoTarget(name);
    }

    public byte[] getClassBytes(String name) throws IOException {
        return launcher.getClassByteArray(name, false);
    }
}
