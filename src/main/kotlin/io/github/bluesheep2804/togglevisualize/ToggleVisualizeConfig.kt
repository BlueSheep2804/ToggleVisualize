package io.github.bluesheep2804.togglevisualize

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler
import dev.isxander.yacl3.config.v2.api.SerialEntry
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.resources.ResourceLocation

class ToggleVisualizeConfig {
    @SerialEntry
    var myCoolBoolean = true

    @SerialEntry
    var myCoolInteger = 5

    @SerialEntry(comment = "This string is amazing")
    var myCoolString = "How amazing!"

    companion object {
        var HANDLER: ConfigClassHandler<ToggleVisualizeConfig> = ConfigClassHandler.createBuilder<ToggleVisualizeConfig>(ToggleVisualizeConfig::class.java)
                .id(ResourceLocation.fromNamespaceAndPath("togglevisualize", "config"))
                .serializer { config: ConfigClassHandler<ToggleVisualizeConfig>? ->
                    GsonConfigSerializerBuilder.create<ToggleVisualizeConfig>(config)
                            .setPath(FabricLoader.getInstance().configDir.resolve("togglevisualize.json5"))
                            .setJson5(true)
                            .build()
                }
                .build()
    }
}