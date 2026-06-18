//? if neoforge {
/*package io.github.bluesheep2804.togglevisualize.neoforge

import io.github.bluesheep2804.togglevisualize.ToggleVisualize
import io.github.bluesheep2804.togglevisualize.ToggleVisualize.rl
import io.github.bluesheep2804.togglevisualize.common.HudOverlay
import io.github.bluesheep2804.togglevisualize.common.ToggleVisualizeConfig
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.common.Mod
import net.neoforged.fml.loading.FMLPaths
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent
import net.neoforged.neoforge.client.gui.IConfigScreenFactory
import thedarkcolour.kotlinforforge.neoforge.forge.LOADING_CONTEXT
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

@Mod(ToggleVisualize.MOD_ID, dist = [Dist.CLIENT])
object ToggleVisualizeNeoforge {
    init {
        ToggleVisualize.init(FMLPaths.CONFIGDIR.get())
        LOADING_CONTEXT.registerExtensionPoint(IConfigScreenFactory::class.java) {
            IConfigScreenFactory { container, screen ->
                ToggleVisualizeConfig.configScreen(screen).generateScreen(screen)
            }
        }
        MOD_BUS.addListener(::registerGuiOverlays)
    }

    fun registerGuiOverlays(event: RegisterGuiLayersEvent) {
        event.registerAboveAll(rl(ToggleVisualize.OVERLAY_ID), HudOverlay::renderOverlay)
    }
}
*///?}
