package me.alphamode.launchhack;

public interface IClassTransformer {
    byte[] transform(String name, String transformedName, byte[] basicClass);
}
