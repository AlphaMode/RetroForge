package me.alphamode.retroforge.client.mixin;

import com.mojang.blaze3d.ProjectionType;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.renderpearl.api.buffers.GpuBufferSlice;
import com.mojang.renderpearl.api.commands.RenderPass;
import me.alphamode.retroforge.asm.RecordTransformer;
import me.alphamode.retroforge.client.api.extensions.GuiElementRenderStateExt;
import me.alphamode.retroforge.client.api.extensions.StagedVertexBufferDrawExt;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.TextRenderable;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.GuiRenderer;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.Projection;
import net.minecraft.client.renderer.ProjectionMatrixBuffer;
import net.minecraft.client.renderer.RenderPassLayouts;
import net.minecraft.client.renderer.StagedVertexBuffer;
import net.minecraft.client.renderer.state.gui.GlyphRenderState;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;
import net.minecraft.client.renderer.state.gui.GuiRenderState;
import org.joml.Matrix3x2fc;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;

@Mixin(GuiRenderer.class)
public abstract class GuiRendererMixin {

    @Shadow
    private StagedVertexBuffer.@Nullable Draw previousDraw;

    @Shadow
    @Final
    private GuiRenderState renderState;

    @Shadow
    @Final
    private List<GuiRenderer.Draw> draws;

    @Shadow
    protected abstract void enableScissor(ScreenRectangle rectangle, RenderPass renderPass);

    @Shadow
    @Final
    private StagedVertexBuffer vertexBuffer;

    @Shadow
    @Final
    private Projection guiProjection;

    @Shadow
    @Final
    private ProjectionMatrixBuffer guiProjectionMatrixBuffer;

    @Shadow
    protected abstract void executeDraw(GuiRenderer.Draw draw, RenderPass renderPass);

    @Shadow
    protected abstract void preparePictureInPicture();

    @Shadow
    protected abstract void prepareItemElements();

    @Shadow
    @Final
    private static Comparator<GuiElementRenderState> ELEMENT_SORT_COMPARATOR;

    @Shadow
    protected abstract void addElementsToMeshes(GuiRenderState.TraverseRange range);

    @Shadow
    private int firstDrawIndexAfterBlur;

    @Shadow
    private @Nullable ScreenRectangle previousScissorArea;

    @Shadow
    private @Nullable RenderPipeline previousPipeline;

    @Shadow
    private @Nullable TextureSetup previousTextureSetup;

    @Shadow
    protected abstract boolean scissorChanged(@Nullable ScreenRectangle newScissor, @Nullable ScreenRectangle oldScissor);

    @Inject(method = "addElementToMesh", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
    private void addProjectionData(GuiElementRenderState elementState, CallbackInfo ci) {
        ((StagedVertexBufferDrawExt) this.previousDraw).setProjectionMatrix(((GuiElementRenderStateExt)elementState).projectionMatrix());
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    private void prepareText() {
        this.renderState.forEachText(text -> {
            final Matrix3x2fc pose = text.pose;
            final ScreenRectangle scissor = text.scissor;
            final GuiRenderState state = this.renderState;
            text.ensurePrepared().visit(new Font.GlyphVisitor() {
                @Override
                public void acceptRenderable(final TextRenderable renderable) {
                    var projMat = ((GuiElementRenderStateExt) (Object) text).projectionMatrix();
                    if (projMat != null)
                        RecordTransformer.GLYPH_KEY.queueNext(projMat);
                    state.addGlyphToCurrentLayer(new GlyphRenderState(pose, renderable, scissor));
                }
            });
        });
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    private void executeDrawRange(
            final Supplier<String> label, final RenderTarget mainRenderTarget, final GpuBufferSlice dynamicTransforms, final int startIndex, final int endIndex
    ) {
        List<GuiRenderer.Draw> projectionDraws = new ArrayList<>();
        try (RenderPass renderPass = RenderSystem.getDevice()
                .createCommandEncoder()
                .createRenderPass(label, RenderPassLayouts.COLOR_ONLY, mainRenderTarget.getColorTextureView())) {
            RenderSystem.bindDefaultUniforms(renderPass);
            renderPass.setUniform("DynamicTransforms", dynamicTransforms);

            for (int i = startIndex; i < endIndex; i++) {
                GuiRenderer.Draw draw = this.draws.get(i);
                if (((StagedVertexBufferDrawExt) draw.draw()).getProjectionMatrix() == null)
                    this.executeDraw(draw, renderPass);
                else
                    projectionDraws.add(draw);
            }
        }

        for (GuiRenderer.Draw draw : projectionDraws) {
            RenderSystem.backupProjectionMatrix();
            RenderSystem.setProjectionMatrix(this.guiProjectionMatrixBuffer.getBuffer(((StagedVertexBufferDrawExt) draw.draw()).getProjectionMatrix()), ProjectionType.ORTHOGRAPHIC);
            try (RenderPass renderPass = RenderSystem.getDevice()
                    .createCommandEncoder()
                    .createRenderPass(() -> label.get() + "_projection", RenderPassLayouts.COLOR_ONLY, mainRenderTarget.getColorTextureView())) {
                RenderSystem.bindDefaultUniforms(renderPass);
                renderPass.setUniform("DynamicTransforms", dynamicTransforms);
                this.executeDraw(draw, renderPass);
            }
            RenderSystem.restoreProjectionMatrix();
        }
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Ljava/util/List;clear()V"))
    private void clearProjectionDraws(CallbackInfo ci) {

    }

//    /**
//     * @author
//     * @reason
//     */
//    @Overwrite
//    private void executeDraw(final GuiRenderer.Draw draw, final RenderPass renderPass) {
//        Matrix4f projectionMatrix = ((StagedVertexBufferDrawExt) draw.draw()).getProjectionMatrix();
//        if (projectionMatrix != null) {
//            projectionDraws.add(draw);
//            return;
//        }
//        StagedVertexBuffer.ExecuteInfo executeInfo = this.vertexBuffer.getExecuteInfo(draw.draw());
//        if (executeInfo != null) {
//            RenderPipeline pipeline = draw.pipeline();
//            renderPass.setPipeline(RenderSystem.getCompiledPipeline(pipeline, renderPass.activeSubpass()));
//            renderPass.setVertexBuffer(0, executeInfo.vertexBuffer().slice());
//            ScreenRectangle scissorArea = draw.scissorArea();
//            if (scissorArea != null) {
//                this.enableScissor(scissorArea, renderPass);
//            } else {
//                renderPass.disableScissor();
//            }
//
//            if (draw.textureSetup().texure0() != null) {
//                renderPass.setUniform("Sampler0", draw.textureSetup().texure0(), draw.textureSetup().sampler0());
//            }
//
//            if (draw.textureSetup().texure1() != null) {
//                renderPass.setUniform("Sampler1", draw.textureSetup().texure1(), draw.textureSetup().sampler1());
//            }
//
//            if (draw.textureSetup().texure2() != null) {
//                renderPass.setUniform("Sampler2", draw.textureSetup().texure2(), draw.textureSetup().sampler2());
//            }
//
//            renderPass.setIndexBuffer(executeInfo.indexBuffer(), executeInfo.indexType());
//            renderPass.drawIndexed(executeInfo.indexCount(), 1, executeInfo.firstIndex(), executeInfo.baseVertex(), 0);
//        }
//        if (projectionMatrix != null) {
//            RenderSystem.restoreProjectionMatrix();
//            renderPass.setUniform("Projection", RenderSystem.getProjectionMatrixBuffer());
//        }
//    }
}
