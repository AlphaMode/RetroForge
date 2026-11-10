package me.alphamode.retroforge.client.mixin;

import me.alphamode.retroforge.client.api.extensions.GuiElementRenderStateExt;
import net.minecraft.client.renderer.state.gui.GuiTextRenderState;
import org.joml.Matrix4f;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GuiTextRenderState.class)
public class GuiTextRenderStateMixin implements GuiElementRenderStateExt {
    private Matrix4f projectionMatrix;

    @Override
    public @Nullable Matrix4f projectionMatrix() {
        return this.projectionMatrix;
    }

    @Override
    public void projectionMatrix(Matrix4f projMat) {
        this.projectionMatrix = projMat;
    }
}
