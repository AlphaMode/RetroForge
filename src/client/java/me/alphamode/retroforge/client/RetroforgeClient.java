package me.alphamode.retroforge.client;

import cpw.mods.fml.client.FMLClientHandler;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;

import java.util.List;

public class RetroforgeClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        FMLClientHandler.instance().beginMinecraftLoading(Minecraft.getInstance(), List.of(), Minecraft.getInstance().getResourceManager());
//        Loader.instance().loadMods();
    }
}
