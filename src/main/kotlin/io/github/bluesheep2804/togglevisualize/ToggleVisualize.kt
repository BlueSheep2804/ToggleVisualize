package io.github.bluesheep2804.togglevisualize

import io.github.bluesheep2804.togglevisualize.common.ToggleVisualizeConfig
import net.minecraft.resources.ResourceLocation
import org.slf4j.LoggerFactory
import java.nio.file.Path

object ToggleVisualize {
    const val MOD_ID = "togglevisualize"
    val logger = LoggerFactory.getLogger(MOD_ID)
    lateinit var config: ToggleVisualizeConfig
    val sprintOverlayTexture: ResourceLocation = rlMinecraft("textures/mob_effect/speed.png")
    val sneakOverlayTexture: ResourceLocation = rlMinecraft("textures/item/hopper.png")
    val flyingOverlayTexture: ResourceLocation = rlMinecraft("textures/item/elytra.png")

    fun init(configDir: Path) {
        ToggleVisualizeConfig.init(configDir)
        config = ToggleVisualizeConfig.instance
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
