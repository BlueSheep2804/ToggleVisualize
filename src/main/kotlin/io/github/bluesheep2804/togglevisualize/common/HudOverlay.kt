package io.github.bluesheep2804.togglevisualize.common

import io.github.bluesheep2804.togglevisualize.ToggleVisualize.config
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.CommonColors
//? if >=1.21
import net.minecraft.client.DeltaTracker
//? if >=1.21.6 {
import net.minecraft.client.renderer.RenderPipelines
//?} else if >=1.21.2 {
/*import net.minecraft.client.renderer.RenderType
*///?}

object HudOverlay {
    fun renderOverlay(guiGraphics: GuiGraphics, deltaTracker: /*? if <1.21 {*//*Float*//*?} else {*/ DeltaTracker /*?}*/) {
        val minecraftInstance = Minecraft.getInstance()
        val options = minecraftInstance.options
        //? if <1.20.2 {
        /*val debugScreen = options.renderDebug
        *///?} else {
        val debugScreen = minecraftInstance.debugOverlay.showDebugScreen()
        //?}
        val hideGui = options.hideGui
        if (debugScreen || hideGui || minecraftInstance.screen is PositioningScreen) return

        ToggleType.entries.forEach {
            if (it.condition.invoke()) {
                if (it.showIndicator.get(config)) {
                    blit(guiGraphics, it.indicatorLocation, it.indicatorPosX.get(config), it.indicatorPosY.get(config))
                }
                if (it.showText.get(config)) {
                    guiGraphics.drawString(
                        minecraftInstance.font,
                        it.textComponent.copy().withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY),
                        it.textPosX.get(config),
                        it.textPosY.get(config),
                        CommonColors.WHITE
                    )
                }
            }
        }
    }

    fun blit(guiGraphics: GuiGraphics, texture: ResourceLocation, x: Int, y: Int) {
        //? if <1.21.2 {
        /*guiGraphics.blit(
            texture,
            x,
            y,
            0F,
            0F,
            16,
            16,
            16,
            16
        )
        *///?} else {
        guiGraphics.blit(
            //? if <1.21.6 {
            /*RenderType::guiTexturedOverlay,
            *///?} else {
            RenderPipelines.GUI_TEXTURED,
            //?}
            texture,
            x,
            y,
            0F,
            0F,
            16,
            16,
            16,
            16
        )
        //?}
    }
}
