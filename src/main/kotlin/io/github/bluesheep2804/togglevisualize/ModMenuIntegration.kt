package io.github.bluesheep2804.togglevisualize

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import dev.isxander.yacl3.api.*
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder
import dev.isxander.yacl3.api.controller.SliderControllerBuilder
import dev.isxander.yacl3.api.controller.StringControllerBuilder
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder
import dev.isxander.yacl3.gui.controllers.slider.IntegerSliderController
import dev.isxander.yacl3.gui.controllers.string.StringController
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
                                    .name(Component.literal("Test Group"))
                                    .option(Option.createBuilder<Boolean>()
                                            .name(Component.literal("Bool Option"))
                                            .description(OptionDescription.of(Component.literal("Bool Description")))
                                            .binding(defaultConfig.myCoolBoolean, { config.myCoolBoolean }, {newVal -> config.myCoolBoolean = newVal})
                                            .controller(TickBoxControllerBuilder::create)
                                            .build())
                                    .option(Option.createBuilder<Int>()
                                            .name(Component.literal("Integer Option"))
                                            .binding(defaultConfig.myCoolInteger, { config.myCoolInteger }, {newVal -> config.myCoolInteger = newVal})
                                            .controller { opt -> IntegerSliderControllerBuilder.create(opt).range(0, 10).step(1) }
                                            .build())
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(Component.literal("Another Group"))
                                    .option(Option.createBuilder<String>()
                                            .name(Component.literal("String Option"))
                                            .binding(defaultConfig.myCoolString, { config.myCoolString }, {newVal -> config.myCoolString = newVal})
                                            .controller(StringControllerBuilder::create)
                                            .build())
                                    .build())
                            .build())
                    .save(ToggleVisualizeConfig.HANDLER::save)
                    .build()
                    .generateScreen(parentScreen)
        }
    }
}