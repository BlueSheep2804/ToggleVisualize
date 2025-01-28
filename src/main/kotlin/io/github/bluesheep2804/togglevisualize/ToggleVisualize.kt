package io.github.bluesheep2804.togglevisualize

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.ChatFormatting
import net.minecraft.client.DeltaTracker
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.renderer.RenderType
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import org.slf4j.LoggerFactory

object ToggleVisualize : ModInitializer {
    private val logger = LoggerFactory.getLogger("togglevisualize")
    private val sprintOverlayTexture = ResourceLocation.withDefaultNamespace("textures/mob_effect/speed.png")
    private val crouchOverlayTexture = ResourceLocation.withDefaultNamespace("textures/item/hopper.png")
	private lateinit var config: ToggleVisualizeConfig

	override fun onInitialize() {
		ToggleVisualizeConfig.HANDLER.load()
		config = ToggleVisualizeConfig.HANDLER.instance()
		HudRenderCallback.EVENT.register { guiGraphics: GuiGraphics, _: DeltaTracker ->
			val minecraftInstance = Minecraft.getInstance()
			val options = minecraftInstance.options
			val debugScreen = minecraftInstance.debugOverlay.showDebugScreen()
			val hideGui = options.hideGui
			if (debugScreen || hideGui) return@register

			val optionToggleSprint = options.toggleSprint().get()
			val optionToggleCrouch = options.toggleCrouch().get()
			val isSprintDown = options.keySprint.isDown
			val isCrouchDown = options.keyShift.isDown
			if (optionToggleSprint && isSprintDown) {
				guiGraphics.blit(RenderType::guiTexturedOverlay, sprintOverlayTexture, config.sprintPositionX, config.sprintPositionY, 0F, 0F, 16, 16, 16, 16)
				if (config.sprintShowText) {
					guiGraphics.drawString(minecraftInstance.font, Component.literal("Sprint").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY), config.sprintTextPositionX, config.sprintTextPositionY, 16777215)
				}
			}
			if (optionToggleCrouch && isCrouchDown) {
				guiGraphics.blit(RenderType::guiTexturedOverlay, crouchOverlayTexture, config.crouchPositionX, config.crouchPositionY, 0F, 0F, 16, 16, 16, 16)
				if (config.crouchShowText) {
					guiGraphics.drawString(minecraftInstance.font, Component.literal("Crouch").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY), config.crouchTextPositionX, config.crouchTextPositionY, 16777215)
				}
			}
		}
	}
}