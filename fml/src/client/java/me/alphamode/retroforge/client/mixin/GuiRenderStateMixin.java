package me.alphamode.retroforge.client.mixin;

import me.alphamode.retroforge.client.api.extensions.GuiGraphicsExtractorExtension;
import me.alphamode.retroforge.client.api.extensions.GuiRenderStateNodeExt;
import net.fabricmc.fabric.api.client.rendering.v1.FabricRenderState;
import net.minecraft.client.renderer.state.gui.GuiRenderState;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.Consumer;

@Mixin(GuiRenderState.class)
public abstract class GuiRenderStateMixin implements FabricRenderState {


    @Shadow
    private GuiRenderState.Node current;

    @Shadow
    @Final
    private List<GuiRenderState.Node> strata;

    @Shadow
    private int firstStratumAfterBlur;

    @Shadow
    protected abstract void traverse(GuiRenderState.Node node, Consumer<GuiRenderState.Node> consumer);

    @Inject(method = "nextStratum", at = @At("TAIL"))
    private void addProjectionMappings(CallbackInfo ci) {
        var projMat = getData(GuiGraphicsExtractorExtension.MATRIX_KEY);
        if (projMat != null)
            ((GuiRenderStateNodeExt)this.current).setProjectionMatrix(projMat);
    }
//
//    /**
//     * @author
//     * @reason
//     */
//    @Overwrite
//    private void traverse(final Consumer<GuiRenderState.Node> consumer, final GuiRenderState.TraverseRange range) {
//        int startIndex = 0;
//        int endIndex = this.strata.size();
//        if (range == GuiRenderState.TraverseRange.BEFORE_BLUR) {
//            endIndex = Math.min(this.firstStratumAfterBlur, this.strata.size());
//        } else if (range == GuiRenderState.TraverseRange.AFTER_BLUR) {
//            startIndex = this.firstStratumAfterBlur;
//        }
//
//        if (range == GuiRenderState.TraverseRange.PROJECTION) {
//            for (int i = startIndex; i < endIndex; i++) {
//                GuiRenderState.Node stratum = this.strata.get(i);
//                if (((GuiRenderStateNodeExt) stratum).getProjectionMatrix() != null) {
//                    setData(GuiGraphicsExtractorExtension.MATRIX_KEY, ((GuiRenderStateNodeExt) stratum).getProjectionMatrix());
//                    this.traverse(stratum, consumer);
//                    setData(GuiGraphicsExtractorExtension.MATRIX_KEY, null);
//                }
//            }
//        }
//
//        for (int i = startIndex; i < endIndex; i++) {
//            GuiRenderState.Node stratum = this.strata.get(i);
//            if (((GuiRenderStateNodeExt)stratum).getProjectionMatrix() == null)
//                this.traverse(stratum, consumer);
//        }
//    }

    @Mixin(GuiRenderState.Node.class)
    public static class NodeMixin implements GuiRenderStateNodeExt {
        private Matrix4f projMat;

        @Override
        public void setProjectionMatrix(Matrix4f projMat) {
            this.projMat = projMat;
        }

        @Override
        public Matrix4f getProjectionMatrix() {
            return this.projMat;
        }
    }
}
