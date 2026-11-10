package cpw.mods.fml.common.launcher;

import me.alphamode.retrolaunch.ITweaker;
import me.alphamode.retrolaunch.LaunchClassLoader;

import java.io.File;
import java.util.List;

public final class TerminalTweaker implements ITweaker {
    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader)
    {
        classLoader.registerTransformer("cpw.mods.fml.common.asm.transformers.TerminalTransformer");
    }

    @Override
    public String getLaunchTarget()
    {
        return null;
    }

    @Override
    public String[] getLaunchArguments()
    {
        return new String[0];
    }

    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile)
    {

    }
}