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

@Mod(ToggleVisualize.MOD_ID)
@EventBusSubscriber(modid = ToggleVisualize.MOD_ID, value = [Dist.CLIENT])
class ToggleVisualizeNeoforge {
    init {
        ToggleVisualize.init(FMLPaths.CONFIGDIR.get())
        LOADING_CONTEXT.registerExtensionPoint(IConfigScreenFactory::class.java) {
            IConfigScreenFactory { container, screen ->
                ToggleVisualizeConfig.Companion.configScreen(screen).generateScreen(screen)
            }
        }
    }

    companion object {
        @SubscribeEvent
        @JvmStatic
        fun registerGuiOverlays(event: RegisterGuiLayersEvent) {
            event.registerAboveAll(rl("toggle_overlay"), HudOverlay::renderOverlay)
        }
    }
}
*///?}
