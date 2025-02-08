package io.github.bluesheep2804.togglevisualize

import dev.isxander.yacl3.api.ConfigCategory
import dev.isxander.yacl3.api.Option
import dev.isxander.yacl3.api.OptionGroup
import dev.isxander.yacl3.api.YetAnotherConfigLib
import dev.isxander.yacl3.api.YetAnotherConfigLib.ConfigBackedBuilder
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler
import dev.isxander.yacl3.config.v2.api.SerialEntry
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.network.chat.Component
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

    companion object {
        //? if <1.21 {
        /*private val configId = ResourceLocation("togglevisualize", "config")
        *///?} else {
        private val configId = ResourceLocation.fromNamespaceAndPath("togglevisualize", "config")
        //?}
        private val HANDLER: ConfigClassHandler<ToggleVisualizeConfig> = ConfigClassHandler.createBuilder(ToggleVisualizeConfig::class.java)
                .id(configId)
                .serializer { config: ConfigClassHandler<ToggleVisualizeConfig>? ->
                    GsonConfigSerializerBuilder.create(config)
                            .setPath(FabricLoader.getInstance().configDir.resolve("togglevisualize.json5"))
                            .setJson5(true)
                            .build()
                }
                .build()

        val instance: ToggleVisualizeConfig
            get() = HANDLER.instance()

        fun load() {
            HANDLER.load()
        }

        val configScreen: YetAnotherConfigLib
            get() = YetAnotherConfigLib.create(HANDLER) { defaultConfig, config, builder -> builder
                    .title(Component.translatable("togglevisualize.config.title"))
                    .save(HANDLER::save)
                    .category(
                        ConfigCategory.createBuilder()
                            .name(Component.translatable("togglevisualize.config.category.main"))
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
                            .build())
            }
    }
}