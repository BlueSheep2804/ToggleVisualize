package io.github.bluesheep2804.togglevisualize

import io.github.bluesheep2804.togglevisualize.common.ToggleVisualizeConfig
import net.minecraft.resources.ResourceLocation
import org.slf4j.LoggerFactory
import java.nio.file.Path

object ToggleVisualize {
    const val MOD_ID = "togglevisualize"
    const val OVERLAY_ID = "toggle_overlay"
    val logger = LoggerFactory.getLogger(MOD_ID)
    lateinit var config: ToggleVisualizeConfig

    fun init(configDir: Path) {
        ToggleVisualizeConfig.init(configDir.resolve("togglevisualize.json5"))
        config = ToggleVisualizeConfig.instance
    }

    @Suppress("DEPRECATION", "removal")
    fun rl(path: String): ResourceLocation {
        //? if <1.21 {
        /*return ResourceLocation("togglevisualize", path)
        *///?} else {
        return ResourceLocation.fromNamespaceAndPath("togglevisualize", path)
        //?}
    }

    @Suppress("DEPRECATION", "removal")
    fun rlMinecraft(path: String): ResourceLocation {
        //? if <1.21 {
        /*return ResourceLocation("minecraft", path)
        *///?} else {
        return ResourceLocation.withDefaultNamespace(path)
        //?}
    }
}
