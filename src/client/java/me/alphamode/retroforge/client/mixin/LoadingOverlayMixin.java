package me.alphamode.retroforge.client.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ProgressManager;
import cpw.mods.fml.common.ProgressManager.ProgressBar;
import me.alphamode.retroforge.client.TestGuiElementRenderState;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.GuiPipeline;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.renderer.Projection;
import net.minecraft.client.renderer.texture.TextureHandle;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import org.apache.commons.io.IOUtils;
import org.joml.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.function.IntSupplier;

@Mixin(LoadingOverlay.class)
public abstract class LoadingOverlayMixin {
    private static final Identifier LOGO_TEXTURE = Identifier.fromNamespaceAndPath("retroforge", "textures/gui/mojang.png");

    @Shadow
    protected abstract float fadeOutAnim();

    @Shadow
    protected abstract float fadeInAnim();

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    private static int replaceAlpha(int color, int alpha) {
        throw new UnsupportedOperationException("Implemented via mixin");
    }

    @Shadow
    @Final
    private static IntSupplier BRAND_BACKGROUND;
    @Shadow
    @Final
    private boolean fadeIn;

    @Shadow
    @Final
    private static Identifier MOJANG_STUDIOS_LOGO_LOCATION;

    @Shadow
    protected abstract void extractProgressBar(GuiGraphicsExtractor graphics, int x0, int y0, int x1, int y1, float fade);

    private static Properties config;

    private static boolean enabled;
    private static boolean rotate;
    private static int logoOffset;
    private static int backgroundColor;
    private static int fontColor;
    private static int barBorderColor;
    private static int barColor;
    private static int barBackgroundColor;

    private final int barWidth = 400;
    private final int barHeight = 20;
    private final int textHeight2 = 20;
    private final int barOffset = 55;

    private static String getString(String name, String def) {
        String value = config.getProperty(name, def);
        config.setProperty(name, value);
        return value;
    }

    private static boolean getBool(String name, boolean def) {
        return Boolean.parseBoolean(getString(name, Boolean.toString(def)));
    }

    private static int getInt(String name, int def) {
        return Integer.decode(getString(name, Integer.toString(def)));
    }

    private static int getHex(String name, int def) {
        return ARGB.color(255, Integer.decode(getString(name, "0x" + Integer.toString(def, 16).toUpperCase())));
    }

//    @Overwrite
//    public void tick() {
//        if (this.fadeOutStart == -1L && this.reload.isDone() && this.isReadyToFadeOut()) {
//            try {
//                this.reload.checkExceptions();
//                this.onFinish.accept(Optional.empty());
//            } catch (Throwable t) {
//                this.onFinish.accept(Optional.of(t));
//            }
//
//            this.fadeOutStart = Util.getMillis();
//            if (this.minecraft.gui.screen() != null) {
//                Window window = this.minecraft.getWindow();
//                this.minecraft.gui.screen().init(window.getGuiScaledWidth(), window.getGuiScaledHeight());
//            }
//        }
//
//    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void initForgeData(CallbackInfo ci) {
        File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), "config/splash.properties");
        FileReader r = null;
        config = new Properties();
        try {
            r = new FileReader(configFile);
            config.load(r);
        } catch (IOException e) {
            FMLLog.info("Could not load splash.properties, will create a default one");
        } finally {
            IOUtils.closeQuietly(r);
        }

        // Enable if we have the flag, and there's either no optifine, or optifine has added a key to the blackboard ("optifine.ForgeSplashCompatible")
        // Optifine authors - add this key to the blackboard if you feel your modifications are now compatible with this code.
        enabled = getBool("enabled", true);// && ((!FMLClientHandler.instance().hasOptifine()) || Launch.blackboard.containsKey("optifine.ForgeSplashCompatible"));
        rotate = getBool("rotate", false);
        logoOffset = getInt("logoOffset", 0);
        backgroundColor = getHex("background", 0xFFFFFF);
        fontColor = getHex("font", 0x000000);
        barBorderColor = getHex("barBorder", 0xC0C0C0);
        barColor = getHex("bar", 0xCB3D35);
        barBackgroundColor = getHex("barBackground", 0xFFFFFF);
    }

    private final Projection vanillaProjection = new Projection();
    private final Matrix4f projection = new Matrix4f();

    /**
     * @author
     * @reason
     */
    @Overwrite
    public void extractRenderState(final GuiGraphicsExtractor graphics) {
        graphics.pose().pushMatrix();
        ProgressManager.ProgressBar first = null, penult = null, last = null;
        Iterator<ProgressManager.ProgressBar> i = ProgressManager.barIterator();
        while (i.hasNext()) {
            if (first == null) first = i.next();
            else {
                penult = last;
                last = i.next();
            }
        }

        int w = minecraft.getWindow().getWidth();//graphics.guiWidth();
        int h = minecraft.getWindow().getHeight();//graphics.guiHeight();

        graphics.nextStratum();

        graphics.fill(0, 0, graphics.guiWidth(), graphics.guiHeight(), backgroundColor);

        projection.identity();
        projection.setOrtho(320 - w/2, 320 + w/2, 240 + h/2, 240 - h/2, 1000.0F, 11000.0F, RenderSystem.getDevice().getDeviceInfo().isZZeroToOne());
        graphics.pushProjection(projection);

        TextureHandle texture = this.minecraft.getTextureManager().getTexture(LOGO_TEXTURE);
        graphics.guiRenderState.addGuiElement(new TestGuiElementRenderState(GuiPipeline.TEXTURED, TextureSetup.singleTexture(texture.textureView(), texture.sampler()), -1, new Matrix3x2f(graphics.pose()), projection, graphics.scissorStack.peek(), new ScreenRectangle(320 - 256, 240 - 256, 320 + 256, 240 + 256)));

//        graphics.blit(GuiPipeline.TEXTURED, LOGO_TEXTURE, 320 - 256, 240 - 256, 0, 0, 320 + 256, 240 + 256, 320 + 256, 240 + 256);
        if (first != null && enabled) {
            graphics.pose().pushMatrix();
            graphics.pose().translate(320 - (float)barWidth / 2, 310);
            drawBar(graphics, first);
            if (penult != null) {
                graphics.pose().translate(0, barOffset);
                drawBar(graphics, penult);
            }
            if (last != null) {
                graphics.pose().translate(0, barOffset);
                drawBar(graphics, last);
            }
            graphics.pose().popMatrix();
        }

        graphics.popProjection();
        graphics.pose().popMatrix();
    }

    private void drawBar(GuiGraphicsExtractor graphics, ProgressBar b) {
        // title - message
        graphics.pose().pushMatrix();
        graphics.pose().scale(2, 2);
        graphics.text(minecraft.font, b.getTitle() + " - " + b.getMessage(), 0, 0, fontColor, false);
        graphics.pose().popMatrix();

        // border
        graphics.pose().pushMatrix();
        graphics.pose().translate(0, textHeight2);
        drawBox(graphics, barWidth, barHeight, barBorderColor);

        // interior
        graphics.pose().translate(1, 1);
        drawBox(graphics, barWidth - 2, barHeight - 2, barBackgroundColor);

        // slidy part
        drawBox(graphics, (barWidth - 2) * (b.getStep() + 1) / (b.getSteps() + 1), barHeight - 2, barColor);

        // progress text
        String progress = "" + b.getStep() + "/" + b.getSteps();
        graphics.pose().translate(((float)barWidth - 2) / 2 - minecraft.font.width(progress), 2);
        graphics.pose().scale(2, 2);
        graphics.text(minecraft.font, progress, 0, 0, fontColor, false);
        graphics.pose().popMatrix();
    }

    private void drawBox(GuiGraphicsExtractor graphics, int w, int h, int color) {
//        graphics.nextStratum();
//        var pos0 = transform(0, 0);
//        var pos1 = transform(w, h);
//        graphics.fill(pos0.x, pos0.y, pos1.x, pos1.y, color);
        graphics.fill(0, 0, w, h, color);
    }
}
//