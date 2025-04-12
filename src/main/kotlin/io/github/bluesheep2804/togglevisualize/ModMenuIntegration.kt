package io.github.bluesheep2804.togglevisualize

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi

class ModMenuIntegration: ModMenuApi {
    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> {
        return ConfigScreenFactory { parentScreen -> ToggleVisualizeConfig.configScreen(parentScreen).generateScreen(parentScreen) }
    }
}