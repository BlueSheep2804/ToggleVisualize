package io.github.bluesheep2804.togglevisualize

import io.github.bluesheep2804.togglevisualize.common.ToggleVisualizeConfig
import net.minecraft.resources.Identifier
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
    fun rl(path: String): Identifier {
        //? if <1.21 {
        /*return Identifier("togglevisualize", path)
        *///?} else {
        return Identifier.fromNamespaceAndPath("togglevisualize", path)
        //?}
    }

    @Suppress("DEPRECATION", "removal")
    fun rlMinecraft(path: String): Identifier {
        //? if <1.21 {
        /*return Identifier("minecraft", path)
        *///?} else {
        return Identifier.withDefaultNamespace(path)
        //?}
    }
}
