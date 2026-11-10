package me.alphamode.retroforge.client.api.extensions;

import org.joml.Matrix4f;

public interface StagedVertexBufferDrawExt {
    void setProjectionMatrix(Matrix4f matrix);

    Matrix4f getProjectionMatrix();
}
