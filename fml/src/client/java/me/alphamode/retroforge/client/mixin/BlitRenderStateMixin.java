package me.alphamode.retroforge.client.mixin;

import me.alphamode.retroforge.asm.RecordTransformer;
import me.alphamode.retroforge.client.api.extensions.GuiElementRenderStateExt;
import net.minecraft.client.renderer.state.gui.BlitRenderState;
import org.joml.Matrix4f;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlitRenderState.class)
public class BlitRenderStateMixin implements GuiElementRenderStateExt {
    @Override
    public @Nullable Matrix4f projectionMatrix() {
        return RecordTransformer.BLIT_KEY.getOrNull((Record) (Object) this);
    }
}
