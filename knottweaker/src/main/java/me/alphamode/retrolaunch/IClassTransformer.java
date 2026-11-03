package me.alphamode.retrolaunch;

public interface IClassTransformer {
    byte[] transform(String name, String transformedName, byte[] basicClass);
}
