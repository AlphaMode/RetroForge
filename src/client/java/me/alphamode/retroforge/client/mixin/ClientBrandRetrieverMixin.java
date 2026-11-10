package me.alphamode.retroforge.client.mixin;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.ClientBrandRetriever;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ClientBrandRetriever.class)
public class ClientBrandRetrieverMixin {
    /**
     * @author
     * @reason
     */
    @Overwrite
    public static String getClientModName() {
        return FMLCommonHandler.instance().getModName();
    }
}
