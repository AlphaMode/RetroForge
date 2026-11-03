package me.alphamode.retroforge.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.renderpearl.api.device.GpuDevice;

public class MatrixUtil {
    private static final GpuDevice DEVICE = RenderSystem.getDevice(); // GpuBuffer.USAGE_UNIFORM | GpuBuffer.USAGE_COPY_DST
}
