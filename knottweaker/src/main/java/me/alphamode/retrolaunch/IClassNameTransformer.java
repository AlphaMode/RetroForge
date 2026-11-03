package me.alphamode.retrolaunch;

public interface IClassNameTransformer {
    String unmapClassName(String name);

    String remapClassName(String name);
}
