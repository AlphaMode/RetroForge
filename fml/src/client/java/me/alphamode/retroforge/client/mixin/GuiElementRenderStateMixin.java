package me.alphamode.retroforge.client.mixin;

import me.alphamode.retroforge.client.api.extensions.GuiElementRenderStateExt;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GuiElementRenderState.class)
public interface GuiElementRenderStateMixin extends GuiElementRenderStateExt {
}
