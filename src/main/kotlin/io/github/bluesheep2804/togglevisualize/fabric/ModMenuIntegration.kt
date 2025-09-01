package io.github.bluesheep2804.togglevisualize.fabric

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import io.github.bluesheep2804.togglevisualize.common.ToggleVisualizeConfig

class ModMenuIntegration: ModMenuApi {
    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> {
        return ConfigScreenFactory { parentScreen ->
            ToggleVisualizeConfig.Companion.configScreen(parentScreen).generateScreen(parentScreen)
        }
    }
}
