package me.alphamode.retroforge.asm;

import org.joml.Matrix4f;
import recordrecoder.api.record.ComponentKeyRegistry;
import recordrecoder.api.record.RecordComponentKey;

public class RecordTransformer implements Runnable {
    public static final RecordComponentKey<Matrix4f> BLIT_KEY = RecordComponentKey.create("projMat", "net/minecraft/client/renderer/state/gui/BlitRenderState", Matrix4f.class);
    public static final RecordComponentKey<Matrix4f> COLOR_KEY = RecordComponentKey.create("projMat", "net/minecraft/client/renderer/state/gui/ColoredRectangleRenderState", Matrix4f.class);
    public static final RecordComponentKey<Matrix4f> GLYPH_KEY = RecordComponentKey.create("projMat", "net/minecraft/client/renderer/state/gui/GlyphRenderState", Matrix4f.class);
    public static final RecordComponentKey<Matrix4f> TILED_KEY = RecordComponentKey.create("projMat", "net/minecraft/client/renderer/state/gui/TiledBlitRenderState", Matrix4f.class);

    @Override
    public void run() {
        ComponentKeyRegistry.INSTANCE.register(BLIT_KEY);
        ComponentKeyRegistry.INSTANCE.register(COLOR_KEY);
        ComponentKeyRegistry.INSTANCE.register(GLYPH_KEY);
        ComponentKeyRegistry.INSTANCE.register(TILED_KEY);
    }
}
