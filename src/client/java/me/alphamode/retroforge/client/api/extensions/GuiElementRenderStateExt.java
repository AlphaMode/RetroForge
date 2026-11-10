package me.alphamode.retroforge.client.api.extensions;

import org.joml.Matrix4f;
import org.jspecify.annotations.Nullable;

public interface GuiElementRenderStateExt {
    default @Nullable Matrix4f projectionMatrix() {
        return null;
    }

    // Only supported on non-record classes
    default void projectionMatrix(Matrix4f projMat) {
        throw new UnsupportedOperationException();
    }
}
