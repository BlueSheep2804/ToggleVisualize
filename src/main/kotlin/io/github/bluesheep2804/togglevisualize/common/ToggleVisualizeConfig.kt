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
                                        Option.createBuilder<Boolean>()
                                            .name(Component.translatable("togglevisualize.config.option.indicator"))
                                            .binding(
                                                defaultConfig.sneakShow,
                                                { config.sneakShow },
                                                { newVal -> config.sneakShow = newVal })
                                            .controller(TickBoxControllerBuilder::create)
                                            .build()
                                    )
                                    .option(
                                        Option.createBuilder<Int>()
                                            .name(Component.translatable("togglevisualize.config.option.indicatorPositionX"))
                                            .binding(
                                                defaultConfig.sneakPositionX,
                                                { config.sneakPositionX },
                                                { newVal -> config.sneakPositionX = newVal })
                                            .controller { opt -> IntegerFieldControllerBuilder.create(opt).min(0) }
                                            .build())
                                    .option(
                                        Option.createBuilder<Int>()
                                            .name(Component.translatable("togglevisualize.config.option.indicatorPositionY"))
                                            .binding(
                                                defaultConfig.sneakPositionY,
                                                { config.sneakPositionY },
                                                { newVal -> config.sneakPositionY = newVal })
                                            .controller { opt -> IntegerFieldControllerBuilder.create(opt).min(0) }
                                            .build())
                                    .option(
                                        Option.createBuilder<Boolean>()
                                            .name(Component.translatable("togglevisualize.config.option.text"))
                                            .binding(
                                                defaultConfig.sneakShowText,
                                                { config.sneakShowText },
                                                { newVal -> config.sneakShowText = newVal })
                                            .controller(TickBoxControllerBuilder::create)
                                            .build()
                                    )
                                    .option(
                                        Option.createBuilder<Int>()
                                            .name(Component.translatable("togglevisualize.config.option.textPositionX"))
                                            .binding(
                                                defaultConfig.sneakTextPositionX,
                                                { config.sneakTextPositionX },
                                                { newVal -> config.sneakTextPositionX = newVal })
                                            .controller { opt -> IntegerFieldControllerBuilder.create(opt).min(0) }
                                            .build())
                                    .option(
                                        Option.createBuilder<Int>()
                                            .name(Component.translatable("togglevisualize.config.option.textPositionY"))
                                            .binding(
                                                defaultConfig.sneakTextPositionY,
                                                { config.sneakTextPositionY },
                                                { newVal -> config.sneakTextPositionY = newVal })
                                            .controller { opt -> IntegerFieldControllerBuilder.create(opt).min(0) }
                                            .build())
                                    .build())
                            .group(
                                OptionGroup.createBuilder()
                                    .name(Component.translatable("key.sprint"))
                                    .option(
                                        Option.createBuilder<Boolean>()
                                            .name(Component.translatable("togglevisualize.config.option.indicator"))
                                            .binding(
                                                defaultConfig.sprintShow,
                                                { config.sprintShow },
                                                { newVal -> config.sprintShow = newVal })
                                            .controller(TickBoxControllerBuilder::create)
                                            .build()
                                    )
                                    .option(
                                        Option.createBuilder<Int>()
                                            .name(Component.translatable("togglevisualize.config.option.indicatorPositionX"))
                                            .binding(
                                                defaultConfig.sprintPositionX,
                                                { config.sprintPositionX },
                                                { newVal -> config.sprintPositionX = newVal })
                                            .controller { opt -> IntegerFieldControllerBuilder.create(opt).min(0) }
                                            .build())
                                    .option(
                                        Option.createBuilder<Int>()
                                            .name(Component.translatable("togglevisualize.config.option.indicatorPositionY"))
                                            .binding(
                                                defaultConfig.sprintPositionY,
                                                { config.sprintPositionY },
                                                { newVal -> config.sprintPositionY = newVal })
                                            .controller { opt -> IntegerFieldControllerBuilder.create(opt).min(0) }
                                            .build())
                                    .option(
                                        Option.createBuilder<Boolean>()
                                            .name(Component.translatable("togglevisualize.config.option.text"))
                                            .binding(
                                                defaultConfig.sprintShowText,
                                                { config.sprintShowText },
                                                { newVal -> config.sprintShowText = newVal })
                                            .controller(TickBoxControllerBuilder::create)
                                            .build()
                                    )
                                    .option(
                                        Option.createBuilder<Int>()
                                            .name(Component.translatable("togglevisualize.config.option.textPositionX"))
                                            .binding(
                                                defaultConfig.sprintTextPositionX,
                                                { config.sprintTextPositionX },
                                                { newVal -> config.sprintTextPositionX = newVal })
                                            .controller { opt -> IntegerFieldControllerBuilder.create(opt).min(0) }
                                            .build())
                                    .option(
                                        Option.createBuilder<Int>()
                                            .name(Component.translatable("togglevisualize.config.option.textPositionY"))
                                            .binding(
                                                defaultConfig.sprintTextPositionY,
                                                { config.sprintTextPositionY },
                                                { newVal -> config.sprintTextPositionY = newVal })
                                            .controller { opt -> IntegerFieldControllerBuilder.create(opt).min(0) }
                                            .build())
                                    .build())
                            //? if >1.21.8 {
                            .group(
                                OptionGroup.createBuilder()
                                    .name(Component.translatable("key.attack"))
                                    .option(
                                        Option.createBuilder<Boolean>()
                                            .name(Component.translatable("togglevisualize.config.option.indicator"))
                                            .binding(
                                                defaultConfig.attackShow,
                                                { config.attackShow },
                                                { newVal -> config.attackShow = newVal })
                                            .controller(TickBoxControllerBuilder::create)
                                            .build()
                                    )
                                    .option(
                                        Option.createBuilder<Int>()
                                            .name(Component.translatable("togglevisualize.config.option.indicatorPositionX"))
                                            .binding(
                                                defaultConfig.attackPositionX,
                                                { config.attackPositionX },
                                                { newVal -> config.attackPositionX = newVal })
                                            .controller { opt -> IntegerFieldControllerBuilder.create(opt).min(0) }
                                            .build())
                                    .option(
                                        Option.createBuilder<Int>()
                                            .name(Component.translatable("togglevisualize.config.option.indicatorPositionY"))
                                            .binding(
                                                defaultConfig.attackPositionY,
                                                { config.attackPositionY },
                                                { newVal -> config.attackPositionY = newVal })
                                            .controller { opt -> IntegerFieldControllerBuilder.create(opt).min(0) }
                                            .build())
                                    .option(
                                        Option.createBuilder<Boolean>()
                                            .name(Component.translatable("togglevisualize.config.option.text"))
                                            .binding(
                                                defaultConfig.attackShowText,
                                                { config.attackShowText },
                                                { newVal -> config.attackShowText = newVal })
                                            .controller(TickBoxControllerBuilder::create)
                                            .build()
                                    )
                                    .option(
                                        Option.createBuilder<Int>()
                                            .name(Component.translatable("togglevisualize.config.option.textPositionX"))
                                            .binding(
                                                defaultConfig.attackTextPositionX,
                                                { config.attackTextPositionX },
                                                { newVal -> config.attackTextPositionX = newVal })
                                            .controller { opt -> IntegerFieldControllerBuilder.create(opt).min(0) }
                                            .build())
                                    .option(
                                        Option.createBuilder<Int>()
                                            .name(Component.translatable("togglevisualize.config.option.textPositionY"))
                                            .binding(
                                                defaultConfig.attackTextPositionY,
                                                { config.attackTextPositionY },
                                                { newVal -> config.attackTextPositionY = newVal })
                                            .controller { opt -> IntegerFieldControllerBuilder.create(opt).min(0) }
                                            .build())
                                    .build())
                            .group(
                                OptionGroup.createBuilder()
                                    .name(Component.translatable("key.use"))
                                    .option(
                                        Option.createBuilder<Boolean>()
                                            .name(Component.translatable("togglevisualize.config.option.indicator"))
                                            .binding(
                                                defaultConfig.useShow,
                                                { config.useShow },
                                                { newVal -> config.useShow = newVal })
                                            .controller(TickBoxControllerBuilder::create)
                                            .build()
                                    )
                                    .option(
                                        Option.createBuilder<Int>()
                                            .name(Component.translatable("togglevisualize.config.option.indicatorPositionX"))
                                            .binding(
                                                defaultConfig.usePositionX,
                                                { config.usePositionX },
                                                { newVal -> config.usePositionX = newVal })
                                            .controller { opt -> IntegerFieldControllerBuilder.create(opt).min(0) }
                                            .build())
                                    .option(
                                        Option.createBuilder<Int>()
                                            .name(Component.translatable("togglevisualize.config.option.indicatorPositionY"))
                                            .binding(
                                                defaultConfig.usePositionY,
                                                { config.usePositionY },
                                                { newVal -> config.usePositionY = newVal })
                                            .controller { opt -> IntegerFieldControllerBuilder.create(opt).min(0) }
                                            .build())
                                    .option(
                                        Option.createBuilder<Boolean>()
                                            .name(Component.translatable("togglevisualize.config.option.text"))
                                            .binding(
                                                defaultConfig.useShowText,
                                                { config.useShowText },
                                                { newVal -> config.useShowText = newVal })
                                            .controller(TickBoxControllerBuilder::create)
                                            .build()
                                    )
                                    .option(
                                        Option.createBuilder<Int>()
                                            .name(Component.translatable("togglevisualize.config.option.textPositionX"))
                                            .binding(
                                                defaultConfig.useTextPositionX,
                                                { config.useTextPositionX },
                                                { newVal -> config.useTextPositionX = newVal })
                                            .controller { opt -> IntegerFieldControllerBuilder.create(opt).min(0) }
                                            .build())
                                    .option(
                                        Option.createBuilder<Int>()
                                            .name(Component.translatable("togglevisualize.config.option.textPositionY"))
                                            .binding(
                                                defaultConfig.useTextPositionY,
                                                { config.useTextPositionY },
                                                { newVal -> config.useTextPositionY = newVal })
                                            .controller { opt -> IntegerFieldControllerBuilder.create(opt).min(0) }
                                            .build())
                                    .build())
                            //?}
                            .group(
                                OptionGroup.createBuilder()
                                    .name(Component.translatable("item.minecraft.elytra"))
                                    .option(
                                        Option.createBuilder<Boolean>()
                                            .name(Component.translatable("togglevisualize.config.option.indicator"))
                                            .binding(
                                                defaultConfig.flyingShow,
                                                { config.flyingShow },
                                                { newVal -> config.flyingShow = newVal })
                                            .controller(TickBoxControllerBuilder::create)
                                            .build()
                                    )
                                    .option(
                                        Option.createBuilder<Int>()
                                            .name(Component.translatable("togglevisualize.config.option.indicatorPositionX"))
                                            .binding(
                                                defaultConfig.flyingPositionX,
                                                { config.flyingPositionX },
                                                { newVal -> config.flyingPositionX = newVal })
                                            .controller { opt -> IntegerFieldControllerBuilder.create(opt).min(0) }
                                            .build())
                                    .option(
                                        Option.createBuilder<Int>()
                                            .name(Component.translatable("togglevisualize.config.option.indicatorPositionY"))
                                            .binding(
                                                defaultConfig.flyingPositionY,
                                                { config.flyingPositionY },
                                                { newVal -> config.flyingPositionY = newVal })
                                            .controller { opt -> IntegerFieldControllerBuilder.create(opt).min(0) }
                                            .build())
                                    .option(
                                        Option.createBuilder<Boolean>()
                                            .name(Component.translatable("togglevisualize.config.option.text"))
                                            .binding(
                                                defaultConfig.flyingShowText,
                                                { config.flyingShowText },
                                                { newVal -> config.flyingShowText = newVal })
                                            .controller(TickBoxControllerBuilder::create)
                                            .build()
                                    )
                                    .option(
                                        Option.createBuilder<Int>()
                                            .name(Component.translatable("togglevisualize.config.option.textPositionX"))
                                            .binding(
                                                defaultConfig.flyingTextPositionX,
                                                { config.flyingTextPositionX },
                                                { newVal -> config.flyingTextPositionX = newVal })
                                            .controller { opt -> IntegerFieldControllerBuilder.create(opt).min(0) }
                                            .build())
                                    .option(
                                        Option.createBuilder<Int>()
                                            .name(Component.translatable("togglevisualize.config.option.textPositionY"))
                                            .binding(
                                                defaultConfig.flyingTextPositionY,
                                                { config.flyingTextPositionY },
                                                { newVal -> config.flyingTextPositionY = newVal })
                                            .controller { opt -> IntegerFieldControllerBuilder.create(opt).min(0) }
                                            .build())
                                    .build())
                            .build())
            }
        }
    }
}
