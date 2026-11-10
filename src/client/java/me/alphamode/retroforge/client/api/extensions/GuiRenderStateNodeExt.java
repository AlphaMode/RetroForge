package me.alphamode.retroforge.client.api.extensions;

import org.joml.Matrix4f;

public interface GuiRenderStateNodeExt {
    void setProjectionMatrix(Matrix4f projMat);

    Matrix4f getProjectionMatrix();
}
