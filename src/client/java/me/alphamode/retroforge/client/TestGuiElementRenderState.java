package me.alphamode.retroforge.client;

import com.mojang.blaze3d.vertex.VertexConsumer;
import me.alphamode.retroforge.client.api.extensions.GuiElementRenderStateExt;
import net.minecraft.client.gui.GuiPipeline;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;
import org.joml.Matrix3x2fc;
import org.joml.Matrix4f;
import org.jspecify.annotations.Nullable;

public record TestGuiElementRenderState(GuiPipeline pipeline, TextureSetup textureSetup, int color,
                                        Matrix3x2fc pose, Matrix4f projMat,
                                        @Nullable ScreenRectangle scissorArea,
                                        @Nullable ScreenRectangle bounds) implements GuiElementRenderState, GuiElementRenderStateExt {
    @Override
    public void buildVertices(VertexConsumer vertexConsumer) {
        vertexConsumer.addVertexWith2DPose(pose, 320 - 256, 240 - 256).setUv(0, 0).setColor(color);
        vertexConsumer.addVertexWith2DPose(pose, 320 - 256, 240 + 256).setUv(0, 1).setColor(color);
        vertexConsumer.addVertexWith2DPose(pose, 320 + 256, 240 + 256).setUv(1, 1).setColor(color);
        vertexConsumer.addVertexWith2DPose(pose, 320 + 256, 240 - 256).setUv(1, 0).setColor(color);
    }

    @Override
    public @Nullable Matrix4f projectionMatrix() {
        return projMat;
    }
}
