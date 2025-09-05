//? if fabric {
package io.github.bluesheep2804.togglevisualize.fabric

import io.github.bluesheep2804.togglevisualize.ToggleVisualize
import io.github.bluesheep2804.togglevisualize.common.HudOverlay
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.loader.api.FabricLoader
//? if <1.21.6 {
/*import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
*///?} else {
import io.github.bluesheep2804.togglevisualize.ToggleVisualize.rl
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry
//?}

object ToggleVisualizeFabric : ClientModInitializer {
    override fun onInitializeClient() {
        ToggleVisualize.init(FabricLoader.getInstance().configDir.resolve("togglevisualize.json5"))
        //? if <1.21.6 {
        /*HudRenderCallback.EVENT.register(HudOverlay::renderOverlay)
        *///?} else {
        HudElementRegistry.addLast(rl("overlay"), HudOverlay::renderOverlay)
        //?}
    }
}
//?}
