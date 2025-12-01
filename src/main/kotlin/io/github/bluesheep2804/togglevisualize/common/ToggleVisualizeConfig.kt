package io.github.bluesheep2804.togglevisualize.common

import dev.isxander.yacl3.api.ButtonOption
import dev.isxander.yacl3.api.ConfigCategory
import dev.isxander.yacl3.api.Option
import dev.isxander.yacl3.api.OptionGroup
import dev.isxander.yacl3.api.YetAnotherConfigLib
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler
import dev.isxander.yacl3.config.v2.api.SerialEntry
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder
import io.github.bluesheep2804.togglevisualize.ToggleVisualize.rl
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import java.nio.file.Path

class ToggleVisualizeConfig {
    @SerialEntry
    var sneakShow = true

    @SerialEntry
    var sneakPositionX = 16

    @SerialEntry
    var sneakPositionY = 32

    @SerialEntry
    var sneakShowText = false

    @SerialEntry
    var sneakTextPositionX = 36

    @SerialEntry
    var sneakTextPositionY = 36

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

    //? if >1.21.8 {
    @SerialEntry
    var attackShow = true

    @SerialEntry
    var attackPositionX = 16

    @SerialEntry
    var attackPositionY = 48

    @SerialEntry
    var attackShowText = false

    @SerialEntry
    var attackTextPositionX = 36

    @SerialEntry
    var attackTextPositionY = 52

    @SerialEntry
    var useShow = true

    @SerialEntry
    var usePositionX = 16

    @SerialEntry
    var usePositionY = 64

    @SerialEntry
    var useShowText = false

    @SerialEntry
    var useTextPositionX = 36

    @SerialEntry
    var useTextPositionY = 68
    //?}

    @SerialEntry
    var flyingShow = true

    @SerialEntry
    var flyingPositionX = 16

    @SerialEntry
    var flyingPositionY = /*? if >1.21.8 {*/ 80 /*?} else {*/ /*48 *//*?}*/

    @SerialEntry
    var flyingShowText = false

    @SerialEntry
    var flyingTextPositionX = 36

    @SerialEntry
    var flyingTextPositionY = /*? if >1.21.8 {*/ 84 /*?} else {*/ /*52 *//*?}*/

    companion object {
        private val configId = rl("config")

        private lateinit var configPath: Path

        private lateinit var HANDLER: ConfigClassHandler<ToggleVisualizeConfig>

        val instance: ToggleVisualizeConfig
            get() = HANDLER.instance()

        fun init(path: Path) {
            configPath = path
            HANDLER = ConfigClassHandler.createBuilder(ToggleVisualizeConfig::class.java)
                .id(configId)
                .serializer { config: ConfigClassHandler<ToggleVisualizeConfig>? ->
                    GsonConfigSerializerBuilder.create(config)
                        .setPath(configPath)
                        .setJson5(true)
                        .build()
                }
                .build()
            load()
        }

        fun load() {
            HANDLER.load()
        }

        fun save() {
            HANDLER.save()
        }

        fun configScreen(parent: Screen): YetAnotherConfigLib {
            return YetAnotherConfigLib.create(HANDLER) { defaultConfig, config, builder -> builder
                    .title(Component.translatable("togglevisualize.config.title"))
                    .save(HANDLER::save)
                    .category(
                        ConfigCategory.createBuilder()
                            .name(Component.translatable("togglevisualize.config.category.main"))
                            .option(
                                ButtonOption.createBuilder()
                                    .name(Component.translatable("togglevisualize.config.option.open_positioning_tool"))
                                    .action{_, _ ->
                                        Minecraft.getInstance().setScreen(PositioningScreen(parent))
                                    }
                                    .build()
                            )
                            .group(
                                OptionGroup.createBuilder()
                                    .name(Component.translatable("key.sneak"))
                                    .option(
                                        booleanOption(
                                            "indicator",
                                            defaultConfig.sneakShow,
                                            { config.sneakShow },
                                            { newVal -> config.sneakShow = newVal }
                                        )
                                    )
                                    .option(intOption(
                                            "indicatorPositionX",
                                            defaultConfig.sneakPositionX,
                                            { config.sneakPositionX },
                                            { newVal -> config.sneakPositionX = newVal }
                                        ))
                                    .option(intOption(
                                            "indicatorPositionY",
                                            defaultConfig.sneakPositionY,
                                            { config.sneakPositionY },
                                            { newVal -> config.sneakPositionY = newVal }
                                        ))
                                    .option(
                                        booleanOption(
                                            "text",
                                            defaultConfig.sneakShowText,
                                            { config.sneakShowText },
                                            { newVal -> config.sneakShowText = newVal }
                                        )
                                    )
                                    .option(intOption(
                                            "textPositionX",
                                            defaultConfig.sneakTextPositionX,
                                            { config.sneakTextPositionX },
                                            { newVal -> config.sneakTextPositionX = newVal }
                                        ))
                                    .option(intOption(
                                            "textPositionY",
                                            defaultConfig.sneakTextPositionY,
                                            { config.sneakTextPositionY },
                                            { newVal -> config.sneakTextPositionY = newVal }
                                        ))
                                    .build())
                            .group(
                                OptionGroup.createBuilder()
                                    .name(Component.translatable("key.sprint"))
                                    .option(
                                        booleanOption(
                                            "indicator",
                                            defaultConfig.sprintShow,
                                            { config.sprintShow },
                                            { newVal -> config.sprintShow = newVal }
                                        )
                                    )
                                    .option(intOption(
                                            "indicatorPositionX",
                                            defaultConfig.sprintPositionX,
                                            { config.sprintPositionX },
                                            { newVal -> config.sprintPositionX = newVal }
                                        ))
                                    .option(intOption(
                                            "indicatorPositionY",
                                            defaultConfig.sprintPositionY,
                                            { config.sprintPositionY },
                                            { newVal -> config.sprintPositionY = newVal }
                                        ))
                                    .option(
                                        booleanOption(
                                            "text",
                                            defaultConfig.sprintShowText,
                                            { config.sprintShowText },
                                            { newVal -> config.sprintShowText = newVal }
                                        )
                                    )
                                    .option(intOption(
                                            "textPositionX",
                                            defaultConfig.sprintTextPositionX,
                                            { config.sprintTextPositionX },
                                            { newVal -> config.sprintTextPositionX = newVal }
                                        ))
                                    .option(intOption(
                                            "textPositionY",
                                            defaultConfig.sprintTextPositionY,
                                            { config.sprintTextPositionY },
                                            { newVal -> config.sprintTextPositionY = newVal }
                                        ))
                                    .build())
                            //? if >1.21.8 {
                            .group(
                                OptionGroup.createBuilder()
                                    .name(Component.translatable("key.attack"))
                                    .option(
                                        booleanOption(
                                            "indicator",
                                            defaultConfig.attackShow,
                                            { config.attackShow },
                                            { newVal -> config.attackShow = newVal }
                                        )
                                    )
                                    .option(intOption(
                                            "indicatorPositionX",
                                            defaultConfig.attackPositionX,
                                            { config.attackPositionX },
                                            { newVal -> config.attackPositionX = newVal }
                                        ))
                                    .option(intOption(
                                            "indicatorPositionY",
                                            defaultConfig.attackPositionY,
                                            { config.attackPositionY },
                                            { newVal -> config.attackPositionY = newVal }
                                        ))
                                    .option(
                                        booleanOption(
                                            "text",
                                            defaultConfig.attackShowText,
                                            { config.attackShowText },
                                            { newVal -> config.attackShowText = newVal }
                                        )
                                    )
                                    .option(intOption(
                                            "textPositionX",
                                            defaultConfig.attackTextPositionX,
                                            { config.attackTextPositionX },
                                            { newVal -> config.attackTextPositionX = newVal }
                                        ))
                                    .option(intOption(
                                            "textPositionY",
                                            defaultConfig.attackTextPositionY,
                                            { config.attackTextPositionY },
                                            { newVal -> config.attackTextPositionY = newVal }
                                        ))
                                    .build())
                            .group(
                                OptionGroup.createBuilder()
                                    .name(Component.translatable("key.use"))
                                    .option(
                                        booleanOption(
                                            "indicator",
                                            defaultConfig.useShow,
                                            { config.useShow },
                                            { newVal -> config.useShow = newVal }
                                        )
                                    )
                                    .option(intOption(
                                            "indicatorPositionX",
                                            defaultConfig.usePositionX,
                                            { config.usePositionX },
                                            { newVal -> config.usePositionX = newVal }
                                        ))
                                    .option(intOption(
                                            "indicatorPositionY",
                                            defaultConfig.usePositionY,
                                            { config.usePositionY },
                                            { newVal -> config.usePositionY = newVal }
                                        ))
                                    .option(
                                        booleanOption(
                                            "text",
                                            defaultConfig.useShowText,
                                            { config.useShowText },
                                            { newVal -> config.useShowText = newVal }
                                        )
                                    )
                                    .option(intOption(
                                            "textPositionX",
                                            defaultConfig.useTextPositionX,
                                            { config.useTextPositionX },
                                            { newVal -> config.useTextPositionX = newVal }
                                        ))
                                    .option(intOption(
                                            "textPositionY",
                                            defaultConfig.useTextPositionY,
                                            { config.useTextPositionY },
                                            { newVal -> config.useTextPositionY = newVal }
                                        ))
                                    .build())
                            //?}
                            .group(
                                OptionGroup.createBuilder()
                                    .name(Component.translatable("item.minecraft.elytra"))
                                    .option(
                                        booleanOption(
                                            "indicator",
                                            defaultConfig.flyingShow,
                                            { config.flyingShow },
                                            { newVal -> config.flyingShow = newVal }
                                        )
                                    )
                                    .option(intOption(
                                            "indicatorPositionX",
                                            defaultConfig.flyingPositionX,
                                            { config.flyingPositionX },
                                            { newVal -> config.flyingPositionX = newVal }
                                        ))
                                    .option(intOption(
                                            "indicatorPositionY",
                                            defaultConfig.flyingPositionY,
                                            { config.flyingPositionY },
                                            { newVal -> config.flyingPositionY = newVal }
                                        ))
                                    .option(
                                        booleanOption(
                                            "text",
                                            defaultConfig.flyingShowText,
                                            { config.flyingShowText },
                                            { newVal -> config.flyingShowText = newVal }
                                        )
                                    )
                                    .option(intOption(
                                            "textPositionX",
                                            defaultConfig.flyingTextPositionX,
                                            { config.flyingTextPositionX },
                                            { newVal -> config.flyingTextPositionX = newVal }
                                        ))
                                    .option(intOption(
                                            "textPositionY",
                                            defaultConfig.flyingTextPositionY,
                                            { config.flyingTextPositionY },
                                            { newVal -> config.flyingTextPositionY = newVal }
                                        ))
                                    .build())
                            .build())
            }
        }

        private fun booleanOption(id: String, defaultValue: Boolean, getter: () -> Boolean, setter: (Boolean) -> Unit): Option<Boolean> {
            return Option.createBuilder<Boolean>()
                .name(Component.translatable("togglevisualize.config.option.$id"))
                .binding(
                    defaultValue,
                    getter,
                    setter
                )
                .controller(TickBoxControllerBuilder::create)
                .build()
        }

        private fun intOption(id: String, defaultValue: Int, getter: () -> Int, setter: (Int) -> Unit): Option<Int> {
            return Option.createBuilder<Int>()
                .name(Component.translatable("togglevisualize.config.option.$id"))
                .binding(
                    defaultValue,
                    getter,
                    setter
                )
                .controller { opt -> IntegerFieldControllerBuilder.create(opt).min(0) }
                .build()
        }
    }
}
