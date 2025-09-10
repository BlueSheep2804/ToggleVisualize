package io.github.bluesheep2804.togglevisualize.common

import io.github.bluesheep2804.togglevisualize.ToggleVisualize
import io.github.bluesheep2804.togglevisualize.ToggleVisualize.config
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.chat.Component
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

        val optionToggleSprint = options.toggleSprint().get()
        val isSprintDown = options.keySprint.isDown
        if (optionToggleSprint && isSprintDown) {
            if (config.sprintShow) {
                blit(guiGraphics,
                    ToggleVisualize.sprintOverlayTexture, config.sprintPositionX, config.sprintPositionY)
            }
            if (config.sprintShowText) {
                guiGraphics.drawString(
                    minecraftInstance.font,
                    Component.translatable("key.sprint").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY),
                    config.sprintTextPositionX,
                    config.sprintTextPositionY,
                    CommonColors.WHITE
                )
            }
        }

        val optionToggleSneak = options.toggleCrouch().get()
        val isSneakDown = options.keyShift.isDown
        if (optionToggleSneak && isSneakDown) {
            if (config.sneakShow) {
                blit(guiGraphics,
                    ToggleVisualize.sneakOverlayTexture, config.sneakPositionX, config.sneakPositionY)
            }
            if (config.sneakShowText) {
                guiGraphics.drawString(
                    minecraftInstance.font,
                    Component.translatable("key.sneak").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY),
                    config.sneakTextPositionX,
                    config.sneakTextPositionY,
                    CommonColors.WHITE
                )
            }
        }

        val player = minecraftInstance.player
        val isFlying = player?.isFallFlying
        if (isFlying == true) {
            if (config.flyingShow) {
                blit(guiGraphics,
                    ToggleVisualize.flyingOverlayTexture, config.flyingPositionX, config.flyingPositionY)
            }
            if (config.flyingShowText) {
                guiGraphics.drawString(
                    minecraftInstance.font,
                    Component.translatable("item.minecraft.elytra").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY),
                    config.flyingTextPositionX,
                    config.flyingTextPositionY,
                    CommonColors.WHITE
                )
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
