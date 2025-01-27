package io.github.bluesheep2804.togglevisualize

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import dev.isxander.yacl3.api.*
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder
import net.minecraft.network.chat.Component

class ModMenuIntegration: ModMenuApi {
    private val config = ToggleVisualizeConfig.HANDLER.instance()
    private val defaultConfig = ToggleVisualizeConfig.HANDLER.defaults()

    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> {
        return ConfigScreenFactory { parentScreen ->
            YetAnotherConfigLib.createBuilder()
                    .title(Component.literal("Toggle Visualize"))
                    .category(ConfigCategory.createBuilder()
                            .name(Component.literal(""))
                            .group(OptionGroup.createBuilder()
                                    .name(Component.literal("Sprint"))
                                    .option(Option.createBuilder<Int>()
                                            .name(Component.literal("Indicator position X"))
                                            .binding(defaultConfig.sprintPositionX, { config.sprintPositionX }, {newVal -> config.sprintPositionX = newVal})
                                            .controller { opt -> IntegerFieldControllerBuilder.create(opt).min(0) }
                                            .build())
                                    .option(Option.createBuilder<Int>()
                                            .name(Component.literal("Indicator position Y"))
                                            .binding(defaultConfig.sprintPositionY, { config.sprintPositionY }, {newVal -> config.sprintPositionY = newVal})
                                            .controller { opt -> IntegerFieldControllerBuilder.create(opt).min(0) }
                                            .build())
                                    .option(Option.createBuilder<Boolean>()
                                            .name(Component.literal("Indicator text"))
                                            .binding(defaultConfig.sprintShowText, { config.sprintShowText }, {newVal -> config.sprintShowText = newVal})
                                            .controller(TickBoxControllerBuilder::create)
                                            .build())
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(Component.literal("Crouch"))
                                    .option(Option.createBuilder<Int>()
                                            .name(Component.literal("Indicator position X"))
                                            .binding(defaultConfig.crouchPositionX, { config.crouchPositionX }, {newVal -> config.crouchPositionX = newVal})
                                            .controller { opt -> IntegerFieldControllerBuilder.create(opt).min(0) }
                                            .build())
                                    .option(Option.createBuilder<Int>()
                                            .name(Component.literal("Indicator position Y"))
                                            .binding(defaultConfig.crouchPositionY, { config.crouchPositionY }, {newVal -> config.crouchPositionY = newVal})
                                            .controller { opt -> IntegerFieldControllerBuilder.create(opt).min(0) }
                                            .build())
                                    .option(Option.createBuilder<Boolean>()
                                            .name(Component.literal("Indicator text"))
                                            .binding(defaultConfig.crouchShowText, { config.crouchShowText }, {newVal -> config.crouchShowText = newVal})
                                            .controller(TickBoxControllerBuilder::create)
                                            .build())
                                    .build())
                            .build())
                    .save(ToggleVisualizeConfig.HANDLER::save)
                    .build()
                    .generateScreen(parentScreen)
        }
    }
}