package io.github.bluesheep2804.togglevisualize

import net.fabricmc.api.ClientModInitializer
//? if <1.21.6 {
/*import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
*///?} else {
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry
//?}
import net.minecraft.resources.ResourceLocation
import org.slf4j.LoggerFactory

object ToggleVisualize : ClientModInitializer {
    val logger = LoggerFactory.getLogger("togglevisualize")
    val sprintOverlayTexture: ResourceLocation = rlMinecraft("textures/mob_effect/speed.png")
    val sneakOverlayTexture: ResourceLocation = rlMinecraft("textures/item/hopper.png")
    val flyingOverlayTexture: ResourceLocation = rlMinecraft("textures/item/elytra.png")
    lateinit var config: ToggleVisualizeConfig

    override fun onInitializeClient() {
        ToggleVisualizeConfig.load()
        config = ToggleVisualizeConfig.instance
        //? if <1.21.6 {
        /*HudRenderCallback.EVENT.register(HudOverlay::renderOverlay)
        *///?} else {
        HudElementRegistry.addLast(rl("overlay"), HudOverlay::renderOverlay)
        //?}
    }

    fun rl(path: String): ResourceLocation {
        //? if <1.21 {
        /*return ResourceLocation("togglevisualize", path)
        *///?} else {
        return ResourceLocation.fromNamespaceAndPath("togglevisualize", path)
        //?}
    }

    fun rlMinecraft(path: String): ResourceLocation {
        //? if <1.21 {
        /*return ResourceLocation("minecraft", path)
        *///?} else {
        return ResourceLocation.withDefaultNamespace(path)
        //?}
    }
}