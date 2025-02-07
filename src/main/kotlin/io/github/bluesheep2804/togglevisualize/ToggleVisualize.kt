package io.github.bluesheep2804.togglevisualize

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.ChatFormatting
//? if >=1.21
import net.minecraft.client.DeltaTracker
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
//? if >=1.21.2
import net.minecraft.client.renderer.RenderType
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import org.slf4j.LoggerFactory

object ToggleVisualize : ClientModInitializer {
    private val logger = LoggerFactory.getLogger("togglevisualize")
	//? if <1.21 {
	/*private val sprintOverlayTexture = ResourceLocation("minecraft:textures/mob_effect/speed.png")
	private val crouchOverlayTexture = ResourceLocation("minecraft:textures/item/hopper.png")
	*///?} else {
	private val sprintOverlayTexture = ResourceLocation.withDefaultNamespace("textures/mob_effect/speed.png")
	private val crouchOverlayTexture = ResourceLocation.withDefaultNamespace("textures/item/hopper.png")
	//?}
	private lateinit var config: ToggleVisualizeConfig

	override fun onInitializeClient() {
		ToggleVisualizeConfig.HANDLER.load()
		config = ToggleVisualizeConfig.HANDLER.instance()
		HudRenderCallback.EVENT.register { guiGraphics: GuiGraphics, _: /*? if <1.21 {*//*Float*//*?} else {*/ DeltaTracker /*?}*/ ->
			val minecraftInstance = Minecraft.getInstance()
			val options = minecraftInstance.options
			val debugScreen = minecraftInstance.debugOverlay.showDebugScreen()
			val hideGui = options.hideGui
			if (debugScreen || hideGui) return@register

			val optionToggleSprint = options.toggleSprint().get()
			val isSprintDown = options.keySprint.isDown
			if (optionToggleSprint && isSprintDown) {
				if (config.sprintShow) {
					//? if <1.21.2 {
					/*guiGraphics.blit(sprintOverlayTexture, config.sprintPositionX, config.sprintPositionY, 0F, 0F, 16, 16, 16, 16)
					*///?} else {
					guiGraphics.blit(RenderType::guiTexturedOverlay, sprintOverlayTexture, config.sprintPositionX, config.sprintPositionY, 0F, 0F, 16, 16, 16, 16)
					//?}
				}
				if (config.sprintShowText) {
					guiGraphics.drawString(minecraftInstance.font, Component.literal("Sprint").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY), config.sprintTextPositionX, config.sprintTextPositionY, 16777215)
				}
			}

			val optionToggleCrouch = options.toggleCrouch().get()
			val isCrouchDown = options.keyShift.isDown
			if (optionToggleCrouch && isCrouchDown) {
				if (config.crouchShow) {
					//? if <1.21.2 {
					/*guiGraphics.blit(crouchOverlayTexture, config.crouchPositionX, config.crouchPositionY, 0F, 0F, 16, 16, 16, 16)
					*///?} else {
					guiGraphics.blit(RenderType::guiTexturedOverlay, crouchOverlayTexture, config.crouchPositionX, config.crouchPositionY, 0F, 0F, 16, 16, 16, 16)
					//?}
				}
				if (config.crouchShowText) {
					guiGraphics.drawString(minecraftInstance.font, Component.literal("Crouch").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY), config.crouchTextPositionX, config.crouchTextPositionY, 16777215)
				}
			}
		}
	}
}