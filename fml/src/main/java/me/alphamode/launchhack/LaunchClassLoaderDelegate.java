package me.alphamode.launchhack;

import net.fabricmc.loader.impl.launch.FabricLauncher;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;

public class LaunchClassLoaderDelegate {
    private final FabricLauncher launcher;

    public LaunchClassLoaderDelegate(FabricLauncher launcher) {
        this.launcher = launcher;
    }

    public byte[] getClassBytes(String name) throws IOException {
        return launcher.getClassByteArray(name, true);
    }

    public InputStream getResourceAsStream(String name) {
        return launcher.getResourceAsStream(name);
    }

    public ClassLoader getClassLoader() {
        return launcher.getTargetClassLoader();
    }

    public void registerTransformer(String transformerClass) {
//        launcher.addToClassPath();
    }

    public void addToClassPath(Path url) {
        launcher.addToClassPath(url);
    }

    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return this.launcher.loadIntoTarget(name);
    }
}
