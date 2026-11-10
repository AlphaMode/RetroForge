package me.alphamode.retroforge.client.mixin;

import com.mojang.blaze3d.platform.SdlDebug;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.SharedConstants;
import net.minecraft.util.TimeSource;
import org.lwjgl.sdl.SDLError;
import org.lwjgl.sdl.SDLHints;
import org.lwjgl.sdl.SDLInit;
import org.lwjgl.sdl.SDLTimer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(RenderSystem.class)
public class RenderSystemMixin {
    /**
     * @author
     * @reason
     */
    @Overwrite
    public static TimeSource.NanoTimeSource initBackendSystem() {
        SdlDebug.init();
        SDLHints.SDL_SetHint(SDLHints.SDL_HINT_VIDEO_DRIVER, "x11");
        SDLInit.SDL_SetAppMetadataProperty("SDL.app.metadata.name", "Minecraft");
        SDLInit.SDL_SetAppMetadataProperty("SDL.app.metadata.version", SharedConstants.getCurrentVersion().name());
        SDLInit.SDL_SetAppMetadataProperty("SDL.app.metadata.identifier", "com.mojang.minecraft");
        SDLInit.SDL_SetAppMetadataProperty("SDL.app.metadata.creator", "Mojang Studios");
        SDLInit.SDL_SetAppMetadataProperty("SDL.app.metadata.copyright", "Copyright Mojang AB.");
        SDLInit.SDL_SetAppMetadataProperty("SDL.app.metadata.url", "https://www.minecraft.net");
        SDLInit.SDL_SetAppMetadataProperty("SDL.app.metadata.type", "game");
        SDLHints.SDL_SetHint("SDL_NO_SIGNAL_HANDLERS", "1");
        SDLHints.SDL_SetHint("SDL_QUIT_ON_LAST_WINDOW_CLOSE", "0");
        SDLHints.SDL_SetHint("SDL_MOUSE_FOCUS_CLICKTHROUGH", "1");
        SDLHints.SDL_SetHint("SDL_ENABLE_SCREEN_KEYBOARD", "0");
        SDLHints.SDL_SetHint("SDL_IME_IMPLEMENTED_UI", "composition");
        if (!SDLInit.SDL_Init(32)) {
            throw new IllegalStateException("Unable to initialize SDL: " + SDLError.SDL_GetError());
        } else {
            return SDLTimer::SDL_GetTicksNS;
        }
    }
}
