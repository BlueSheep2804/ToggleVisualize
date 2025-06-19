package io.github.bluesheep2804.togglevisualize

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.resources.ResourceLocation
import org.slf4j.LoggerFactory

object ToggleVisualize : ClientModInitializer {
    val logger = LoggerFactory.getLogger("togglevisualize")
    val sprintOverlayTexture: ResourceLocation = rl("textures/mob_effect/speed.png")
    val sneakOverlayTexture: ResourceLocation = rl("textures/item/hopper.png")
    val flyingOverlayTexture: ResourceLocation = rl("textures/item/elytra.png")
    lateinit var config: ToggleVisualizeConfig

    override fun onInitializeClient() {
        ToggleVisualizeConfig.load()
        config = ToggleVisualizeConfig.instance
        HudRenderCallback.EVENT.register(HudOverlay::renderOverlay)
    }

    fun rl(path: String): ResourceLocation {
        //? if <1.21 {
        /*return ResourceLocation("minecraft", path)
        *///?} else {
        return ResourceLocation.withDefaultNamespace(path)
        //?}
    }
}