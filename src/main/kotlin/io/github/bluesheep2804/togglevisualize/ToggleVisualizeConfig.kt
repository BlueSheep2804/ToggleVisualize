package io.github.bluesheep2804.togglevisualize

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler
import dev.isxander.yacl3.config.v2.api.SerialEntry
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.resources.ResourceLocation

class ToggleVisualizeConfig {
    @SerialEntry
    var sprintShow = true

    @SerialEntry
    var sprintPositionX = 16

    @SerialEntry
    var sprintPositionY = 16

    @SerialEntry
    var sprintShowText = false

    @SerialEntry
    var sprintTextPositionX = 36

    @SerialEntry
    var sprintTextPositionY = 20

    @SerialEntry
    var crouchShow = true

    @SerialEntry
    var crouchPositionX = 16

    @SerialEntry
    var crouchPositionY = 32

    @SerialEntry
    var crouchShowText = false

    @SerialEntry
    var crouchTextPositionX = 36

    @SerialEntry
    var crouchTextPositionY = 36

    companion object {
        //? if <1.21 {
        /*private val configId = ResourceLocation("togglevisualize", "config")
        *///?} else {
        private val configId = ResourceLocation.fromNamespaceAndPath("togglevisualize", "config")
        //?}
        var HANDLER: ConfigClassHandler<ToggleVisualizeConfig> = ConfigClassHandler.createBuilder<ToggleVisualizeConfig>(ToggleVisualizeConfig::class.java)
                .id(configId)
                .serializer { config: ConfigClassHandler<ToggleVisualizeConfig>? ->
                    GsonConfigSerializerBuilder.create<ToggleVisualizeConfig>(config)
                            .setPath(FabricLoader.getInstance().configDir.resolve("togglevisualize.json5"))
                            .setJson5(true)
                            .build()
                }
                .build()
    }
}