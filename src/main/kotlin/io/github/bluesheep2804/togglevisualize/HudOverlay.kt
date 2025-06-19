package io.github.bluesheep2804.togglevisualize

import io.github.bluesheep2804.togglevisualize.ToggleVisualize.config
import io.github.bluesheep2804.togglevisualize.ToggleVisualize.flyingOverlayTexture
import io.github.bluesheep2804.togglevisualize.ToggleVisualize.sneakOverlayTexture
import io.github.bluesheep2804.togglevisualize.ToggleVisualize.sprintOverlayTexture
import net.minecraft.ChatFormatting
//? if >=1.21
import net.minecraft.client.DeltaTracker
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
//? if >=1.21.2
import net.minecraft.client.renderer.RenderType
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation

class HudOverlay {
    companion object {
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
                    blit(guiGraphics, sprintOverlayTexture, config.sprintPositionX, config.sprintPositionY)
                }
                if (config.sprintShowText) {
                    guiGraphics.drawString(minecraftInstance.font, Component.translatable("key.sprint").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY), config.sprintTextPositionX, config.sprintTextPositionY, 16777215)
                }
            }

            val optionToggleSneak = options.toggleCrouch().get()
            val isSneakDown = options.keyShift.isDown
            if (optionToggleSneak && isSneakDown) {
                if (config.sneakShow) {
                    blit(guiGraphics, sneakOverlayTexture, config.sneakPositionX, config.sneakPositionY)
                }
                if (config.sneakShowText) {
                    guiGraphics.drawString(minecraftInstance.font, Component.translatable("key.sneak").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY), config.sneakTextPositionX, config.sneakTextPositionY, 16777215)
                }
            }

            val player = minecraftInstance.player
            val isFlying = player?.isFallFlying
            if (isFlying == true) {
                if (config.flyingShow) {
                    blit(guiGraphics, flyingOverlayTexture, config.flyingPositionX, config.flyingPositionY)
                }
                if (config.flyingShowText) {
                    guiGraphics.drawString(
                        minecraftInstance.font,
                        Component.translatable("item.minecraft.elytra").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY),
                        config.flyingTextPositionX,
                        config.flyingTextPositionY,
                        16777215
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
                RenderType::guiTexturedOverlay,
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
}