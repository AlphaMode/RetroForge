package me.alphamode.retroforge.client.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.renderpearl.api.textures.GpuSampler;
import com.mojang.renderpearl.api.textures.GpuTextureView;
import me.alphamode.retroforge.asm.RecordTransformer;
import me.alphamode.retroforge.client.api.extensions.GuiElementRenderStateExt;
import me.alphamode.retroforge.client.api.extensions.GuiGraphicsExtractorExtension;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.GuiPipeline;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.state.gui.GuiRenderState;
import net.minecraft.client.renderer.state.gui.GuiTextRenderState;
import net.minecraft.util.FormattedCharSequence;
import org.joml.Matrix3x2fc;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphicsExtractor.class)
public abstract class GuiGraphicsExtractorMixin implements GuiGraphicsExtractorExtension {
    @Shadow
    public abstract void nextStratum();

    @Shadow
    @Final
    public GuiRenderState guiRenderState;
    private final Matrix4f[] mats = new Matrix4f[6];

    private int curr;

    @Override
    public void pushProjection(Matrix4f matrix) {

        this.guiRenderState.setData(GuiGraphicsExtractorExtension.MATRIX_KEY, matrix);
        if (curr == mats.length) {
            throw new IllegalStateException("max stack size of " + (curr + 1) + " reached"); //$NON-NLS-1$ //$NON-NLS-2$
        }
        nextStratum();
        if (curr == 0 && mats[curr] != null) {
//            curr++;
        }
        mats[curr] = matrix;
    }

    @Override
    public void popProjection() {
        nextStratum();
//        if (curr == 0) {
//            throw new IllegalStateException("already at the bottom of the stack"); //$NON-NLS-1$
//        }
        mats[curr] = null;
//        --curr;
    }

    @Override
    public Matrix4f peekProjection() {
        return mats[curr];
    }

    @Inject(method = "innerFill", at = @At("HEAD"))
    private void addProj(GuiPipeline guiPipeline, TextureSetup textureSetup, int x0, int y0, int x1, int y1, int color1, Integer color2, CallbackInfo ci) {
        Matrix4f projMat = peekProjection();
        if (projMat != null)
            RecordTransformer.COLOR_KEY.queueNext(peekProjection());
    }

    @Inject(method = "innerBlit(Lnet/minecraft/client/gui/GuiPipeline;Lcom/mojang/renderpearl/api/textures/GpuTextureView;Lcom/mojang/renderpearl/api/textures/GpuSampler;IIIIFFFFI)V", at = @At("HEAD"))
    private void addProj2(GuiPipeline guiPipeline, GpuTextureView textureView, GpuSampler sampler, int x0, int y0, int x1, int y1, float u0, float u1, float v0, float v1, int color, CallbackInfo ci) {
        Matrix4f projMat = peekProjection();
        if (projMat != null)
            RecordTransformer.BLIT_KEY.queueNext(peekProjection());
    }

    @Inject(method = "innerTiledBlit", at = @At("HEAD"))
    private void addProj3(GuiPipeline guiPipeline, GpuTextureView textureView, GpuSampler sampler, int tileWidth, int tileHeight, int x0, int y0, int x1, int y1, float u0, float u1, float v0, float v1, int color, CallbackInfo ci) {
        Matrix4f projMat = peekProjection();
        if (projMat != null)
            RecordTransformer.TILED_KEY.queueNext(peekProjection());
    }

    @WrapOperation(method = "text(Lnet/minecraft/client/gui/Font;Lnet/minecraft/util/FormattedCharSequence;IIIZ)V", at = @At(value = "NEW", target = "(Lnet/minecraft/client/gui/Font;Lnet/minecraft/util/FormattedCharSequence;Lorg/joml/Matrix3x2fc;IIIIZZLnet/minecraft/client/gui/navigation/ScreenRectangle;)Lnet/minecraft/client/renderer/state/gui/GuiTextRenderState;"))
    private GuiTextRenderState addProj4(Font font, FormattedCharSequence text, Matrix3x2fc pose, int x, int y, int color, int backgroundColor, boolean dropShadow, boolean includeEmpty, ScreenRectangle scissor, Operation<GuiTextRenderState> original) {
        GuiTextRenderState state = original.call(font, text, pose, x, y, color, backgroundColor, dropShadow, includeEmpty, scissor);
        ((GuiElementRenderStateExt) (Object) state).projectionMatrix(peekProjection());
        return state;
    }
}
