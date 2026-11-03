package me.alphamode.retroforge.launch;

import me.alphamode.retrolaunch.Launch;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class RetroForgePreLaunch implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        // Setup RetroLaunch, which will set up FML for us
        Launch.launch("--version", "1.7.10", "--tweakClass", "cpw.mods.fml.common.launcher.FMLTweaker");
    }
}
