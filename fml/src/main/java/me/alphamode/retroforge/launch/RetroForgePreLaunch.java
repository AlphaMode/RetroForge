package me.alphamode.retroforge.launch;

import cpw.mods.fml.relauncher.FMLInjectionData;
import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.FMLRelaunchLog;
import me.alphamode.launchhack.LaunchClassLoaderDelegate;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.fabricmc.loader.impl.launch.FabricLauncherBase;

import java.lang.reflect.Method;

public class RetroForgePreLaunch implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        FabricLoader loader = FabricLoader.getInstance();
        LaunchClassLoaderDelegate delegate = new LaunchClassLoaderDelegate(FabricLauncherBase.getLauncher());
        if (loader.getEnvironmentType() == EnvType.CLIENT) {
            FMLLaunchHandler.configureForClientLaunch(delegate, loader);
        } else {
            FMLLaunchHandler.configureForServerLaunch(delegate, loader);
        }
        FMLLaunchHandler.appendCoreMods();

        try
        {
            FMLRelaunchLog.fine("Validating minecraft");
            Class<?> loaderClazz = Class.forName("cpw.mods.fml.common.Loader", true, delegate.getClassLoader());
            Method m = loaderClazz.getMethod("injectData", Object[].class);
            m.invoke(null, (Object) FMLInjectionData.data());
            m = loaderClazz.getMethod("instance");
            m.invoke(null);
            FMLRelaunchLog.fine("Minecraft validated, launching...");
        }
        catch (Exception e)
        {
            // Load in the Loader, make sure he's ready to roll - this will initialize most of the rest of minecraft here
            System.out.println("A CRITICAL PROBLEM OCCURED INITIALIZING MINECRAFT - LIKELY YOU HAVE AN INCORRECT VERSION FOR THIS FML");
            throw new RuntimeException(e);
        }

//        FMLCommonHandler.instance().beginLoading();
//        FMLCommonHandler.instance().beginLoading(this);
//        try
//        {

//        }
//        catch (WrongMinecraftVersionException wrong)
//        {
//            wrongMC = wrong;
//        }
    }
}
