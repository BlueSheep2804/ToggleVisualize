//? if forge {
/*package io.github.bluesheep2804.togglevisualize.forge

import io.github.bluesheep2804.togglevisualize.ToggleVisualize
import io.github.bluesheep2804.togglevisualize.common.HudOverlay
import io.github.bluesheep2804.togglevisualize.common.ToggleVisualizeConfig
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.ConfigScreenHandler.ConfigScreenFactory
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent
import net.minecraftforge.client.gui.overlay.ForgeGui
import net.minecraftforge.client.gui.overlay.IGuiOverlay
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.loading.FMLPaths
import thedarkcolour.kotlinforforge.forge.LOADING_CONTEXT
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.forge.runWhenOn

@Mod(ToggleVisualize.MOD_ID)
object ToggleVisualizeForge {
    init {
        runWhenOn(Dist.CLIENT) {
            ToggleVisualize.init(FMLPaths.CONFIGDIR.get())
            LOADING_CONTEXT.registerExtensionPoint(ConfigScreenFactory::class.java) {
                ConfigScreenFactory { parentScreen ->
                    ToggleVisualizeConfig.configScreen(parentScreen).generateScreen(parentScreen)
                }
            }
            MOD_BUS.addListener(::registerGuiOverlays)
        }
    }

    fun registerGuiOverlays(event: RegisterGuiOverlaysEvent) {
        event.registerAboveAll(ToggleVisualize.OVERLAY_ID, HudOverlayForge())
    }

    private class HudOverlayForge : IGuiOverlay {
        override fun render(forgeGui: ForgeGui, guiGraphics: GuiGraphicsExtractor, partialTick: Float, screenWidth: Int, screenHeight: Int) {
            HudOverlay.renderOverlay(guiGraphics, partialTick)
        }
    }
}
*///?}
