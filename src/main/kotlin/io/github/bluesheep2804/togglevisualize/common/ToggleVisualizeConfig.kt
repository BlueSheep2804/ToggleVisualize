package io.github.bluesheep2804.togglevisualize.common

import dev.isxander.yacl3.api.ButtonOption
import dev.isxander.yacl3.api.ConfigCategory
import dev.isxander.yacl3.api.Option
import dev.isxander.yacl3.api.OptionGroup
import dev.isxander.yacl3.api.YetAnotherConfigLib
import dev.isxander.yacl3.api.controller.EnumControllerBuilder
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler
import dev.isxander.yacl3.config.v2.api.SerialEntry
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder
import io.github.bluesheep2804.togglevisualize.ToggleVisualize.rl
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import java.nio.file.Path
import kotlin.reflect.KMutableProperty1

class ToggleVisualizeConfig {
    @SerialEntry
    var sneakShow = true

    @SerialEntry
    var sneakPositionX = 16

    @SerialEntry
    var sneakPositionY = 32

    @SerialEntry
    var sneakAnchorPoint = AnchorPoint.LEFT_TOP

    @SerialEntry
    var sneakShowText = false

    @SerialEntry
    var sneakTextPositionX = 36

    @SerialEntry
    var sneakTextPositionY = 36

    @SerialEntry
    var sneakTextAnchorPoint = AnchorPoint.LEFT_TOP

    @SerialEntry
    var sprintShow = true

    @SerialEntry
    var sprintPositionX = 16

    @SerialEntry
    var sprintPositionY = 16

    @SerialEntry
    var sprintAnchorPoint = AnchorPoint.LEFT_TOP

    @SerialEntry
    var sprintShowText = false

    @SerialEntry
    var sprintTextPositionX = 36

    @SerialEntry
    var sprintTextPositionY = 20

    @SerialEntry
    var sprintTextAnchorPoint = AnchorPoint.LEFT_TOP

    //? if >1.21.8 {
    @SerialEntry
    var attackShow = true

    @SerialEntry
    var attackPositionX = 16

    @SerialEntry
    var attackPositionY = 48

    @SerialEntry
    var attackAnchorPoint = AnchorPoint.LEFT_TOP

    @SerialEntry
    var attackShowText = false

    @SerialEntry
    var attackTextPositionX = 36

    @SerialEntry
    var attackTextPositionY = 52

    @SerialEntry
    var attackTextAnchorPoint = AnchorPoint.LEFT_TOP

    @SerialEntry
    var useShow = true

    @SerialEntry
    var usePositionX = 16

    @SerialEntry
    var usePositionY = 64

    @SerialEntry
    var useAnchorPoint = AnchorPoint.LEFT_TOP

    @SerialEntry
    var useShowText = false

    @SerialEntry
    var useTextPositionX = 36

    @SerialEntry
    var useTextPositionY = 68

    @SerialEntry
    var useTextAnchorPoint = AnchorPoint.LEFT_TOP
    //?}

    @SerialEntry
    var flyingShow = true

    @SerialEntry
    var flyingPositionX = -16

    @SerialEntry
    var flyingPositionY = 16

    @SerialEntry
    var flyingAnchorPoint = AnchorPoint.RIGHT_TOP

    @SerialEntry
    var flyingShowText = false

    @SerialEntry
    var flyingTextPositionX = -36

    @SerialEntry
    var flyingTextPositionY = 20

    @SerialEntry
    var flyingTextAnchorPoint = AnchorPoint.RIGHT_TOP

    @SerialEntry
    var glidingShow = true

    @SerialEntry
    var glidingPositionX = -16

    @SerialEntry
    var glidingPositionY = 32

    @SerialEntry
    var glidingAnchorPoint = AnchorPoint.RIGHT_TOP

    @SerialEntry
    var glidingShowText = false

    @SerialEntry
    var glidingTextPositionX = -36

    @SerialEntry
    var glidingTextPositionY = 36

    @SerialEntry
    var glidingTextAnchorPoint = AnchorPoint.RIGHT_TOP

    @SerialEntry
    var swimmingShow = true

    @SerialEntry
    var swimmingPositionX = -16

    @SerialEntry
    var swimmingPositionY = 48

    @SerialEntry
    var swimmingAnchorPoint = AnchorPoint.RIGHT_TOP

    @SerialEntry
    var swimmingShowText = false

    @SerialEntry
    var swimmingTextPositionX = -36

    @SerialEntry
    var swimmingTextPositionY = 52

    @SerialEntry
    var swimmingTextAnchorPoint = AnchorPoint.RIGHT_TOP

    @SerialEntry
    var crawlingShow = true

    @SerialEntry
    var crawlingPositionX = -16

    @SerialEntry
    var crawlingPositionY = 64

    @SerialEntry
    var crawlingAnchorPoint = AnchorPoint.RIGHT_TOP

    @SerialEntry
    var crawlingShowText = false

    @SerialEntry
    var crawlingTextPositionX = -36

    @SerialEntry
    var crawlingTextPositionY = 68

    @SerialEntry
    var crawlingTextAnchorPoint = AnchorPoint.RIGHT_TOP

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
            return YetAnotherConfigLib.create(HANDLER) { defaultConfig, config, builder ->
                val mainCategory = ConfigCategory.createBuilder()
                    .name(Component.translatable("togglevisualize.config.category.main"))
                    .option(
                        ButtonOption.createBuilder()
                            .name(Component.translatable("togglevisualize.config.option.open_positioning_tool"))
                            .action{_, _ ->
                                CompatLayer.screen = PositioningScreen(parent)
                            }
                            .build()
                    )

                val controlCategory = ConfigCategory.createBuilder()
                    .name(Component.translatable("togglevisualize.config.category.control"))
                ToggleType.byControl.forEach { toggleType ->
                    controlCategory.group(optionGroup(toggleType, defaultConfig, config))
                }

                val playerCategory = ConfigCategory.createBuilder()
                    .name(Component.translatable("togglevisualize.config.category.player"))
                ToggleType.byPlayer.forEach { toggleType ->
                    playerCategory.group(optionGroup(toggleType, defaultConfig, config))
                }

                return@create builder
                    .title(Component.translatable("togglevisualize.config.title"))
                    .save(HANDLER::save)
                    .categories(listOf(
                        mainCategory.build(),
                        controlCategory.build(),
                        playerCategory.build()
                    ))
            }
        }

        private fun optionGroup(type: ToggleType, defaultConfig: ToggleVisualizeConfig, config: ToggleVisualizeConfig): OptionGroup {
            return OptionGroup.createBuilder()
                .name(type.textComponent)
                .option(booleanOption(
                    "indicator",
                    type.showIndicator,
                    defaultConfig,
                    config
                ))
                .option(intOption(
                    "indicatorPositionX",
                    type.indicatorPosX,
                    defaultConfig,
                    config
                ))
                .option(intOption(
                    "indicatorPositionY",
                    type.indicatorPosY,
                    defaultConfig,
                    config
                ))
                .option(anchorPointOption(
                    "indicatorAnchorPoint",
                    type.indicatorAnchorPoint,
                    defaultConfig,
                    config
                ))
                .option(booleanOption(
                    "text",
                    type.showText,
                    defaultConfig,
                    config
                ))
                .option(intOption(
                    "textPositionX",
                    type.textPosX,
                    defaultConfig,
                    config
                ))
                .option(intOption(
                    "textPositionY",
                    type.textPosY,
                    defaultConfig,
                    config
                ))
                .option(anchorPointOption(
                    "textAnchorPoint",
                    type.textAnchorPoint,
                    defaultConfig,
                    config
                ))
                .build()
        }

        private fun booleanOption(id: String, entry: KMutableProperty1<ToggleVisualizeConfig, Boolean>, defaultConfig: ToggleVisualizeConfig, config: ToggleVisualizeConfig): Option<Boolean> {
            return Option.createBuilder<Boolean>()
                .name(Component.translatable("togglevisualize.config.option.$id"))
                .binding(
                    entry.get(defaultConfig),
                    { entry.get(config) },
                    { newVal -> entry.set(config, newVal) }
                )
                .controller(TickBoxControllerBuilder::create)
                .build()
        }

        private fun intOption(id: String, entry: KMutableProperty1<ToggleVisualizeConfig, Int>, defaultConfig: ToggleVisualizeConfig, config: ToggleVisualizeConfig): Option<Int> {
            return Option.createBuilder<Int>()
                .name(Component.translatable("togglevisualize.config.option.$id"))
                .binding(
                    entry.get(defaultConfig),
                    { entry.get(config) },
                    { newVal -> entry.set(config, newVal) }
                )
                .controller(IntegerFieldControllerBuilder::create)
                .build()
        }

        private fun anchorPointOption(id: String, entry: KMutableProperty1<ToggleVisualizeConfig, AnchorPoint>, defaultConfig: ToggleVisualizeConfig, config: ToggleVisualizeConfig): Option<AnchorPoint> {
            return Option.createBuilder<AnchorPoint>()
                .name(Component.translatable("togglevisualize.config.option.$id"))
                .binding(
                    entry.get(defaultConfig),
                    { entry.get(config) },
                    { newVal -> entry.set(config, newVal) }
                )
                .controller { opt -> EnumControllerBuilder.create(opt).enumClass(AnchorPoint::class.java) }
                .build()
        }
    }
}
