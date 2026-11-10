package me.alphamode.retroforge.client.mixin;

import me.alphamode.retroforge.client.api.extensions.StagedVertexBufferDrawExt;
import net.minecraft.client.renderer.StagedVertexBuffer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(StagedVertexBuffer.Draw.class)
public class StagedVertexBufferDrawMixin implements StagedVertexBufferDrawExt {
    private Matrix4f projectionMatrix;

    @Override
    public void setProjectionMatrix(Matrix4f matrix) {
        this.projectionMatrix = matrix;
    }

    @Override
    public Matrix4f getProjectionMatrix() {
        return this.projectionMatrix;
    }
}
