package me.alphamode.retroforge.client.api.extensions;

import net.fabricmc.fabric.api.client.rendering.v1.FabricRenderState;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;
import org.joml.Matrix4f;

import java.util.Map;

public interface GuiGraphicsExtractorExtension {
    RenderStateDataKey<Matrix4f> MATRIX_KEY = RenderStateDataKey.create(() -> "RetroForge|Matrix");
    RenderStateDataKey<Map<GuiElementRenderState, Matrix4f>> MATRIX_MAPPING_KEY = RenderStateDataKey.create(() -> "RetroForge|Matrix");

    default void pushProjection(Matrix4f matrix) {
    }

    default void popProjection() {
    }

    default Matrix4f peekProjection() {
        return null;
    }
}
