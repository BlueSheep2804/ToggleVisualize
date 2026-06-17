package io.github.bluesheep2804.togglevisualize.common

import net.minecraft.client.Minecraft
import net.minecraft.client.Options
import net.minecraft.client.gui.screens.Screen

object CompatLayer {
    val minecraft
        get() = Minecraft.getInstance()
    val options: Options
        get() = minecraft.options

    val isHudHidden: Boolean
        get() =
            //? if >= 26.2 {
            minecraft.gui.hud.isHidden
            //?} else {
            /*options.hideGui
            *///?}

    val isDebugOverlayVisible: Boolean
        get() =
            //? if >= 26.1 {
            minecraft.debugEntries.isOverlayVisible
            //?} else if >= 1.21.9 {
            /*minecraft.debugEntries.isF3Visible
            *///?} else if >= 1.20.2 {
            /*minecraft.debugOverlay.showDebugScreen()
            *///?} else {
            /*options.renderDebug
            *///?}

    var screen: Screen?
        get() =
            //? if >= 26.2 {
            minecraft.gui.screen()
            //?} else {
            /*minecraft.screen
            *///?}
        set(value) =
            //? if >= 26.2 {
            minecraft.gui.setScreen(value)
            //?} else {
            /*minecraft.setScreen(value)
            *///?}
}