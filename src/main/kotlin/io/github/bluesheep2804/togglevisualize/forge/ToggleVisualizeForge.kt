//? if forge {
/*package io.github.bluesheep2804.togglevisualize.forge

import io.github.bluesheep2804.togglevisualize.ToggleVisualize
import io.github.bluesheep2804.togglevisualize.common.ToggleVisualizeConfig
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.ConfigScreenHandler.ConfigScreenFactory
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.loading.FMLPaths
import thedarkcolour.kotlinforforge.forge.LOADING_CONTEXT

@Mod(ToggleVisualize.MOD_ID)
@Mod.EventBusSubscriber(modid = ToggleVisualize.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = [Dist.CLIENT])
object ToggleVisualizeForge {
    init {
        ToggleVisualize.init(FMLPaths.CONFIGDIR.get())
        LOADING_CONTEXT.registerExtensionPoint(ConfigScreenFactory::class.java) {
            ConfigScreenFactory { parentScreen ->
                ToggleVisualizeConfig.Companion.configScreen(parentScreen).generateScreen(parentScreen)
            }
        }
    }

    @SubscribeEvent
    fun registerGuiOverlays(event: RegisterGuiOverlaysEvent) {
        event.registerAboveAll(ToggleVisualize.MOD_ID, HudOverlayForge())
    }
}
*///?}
