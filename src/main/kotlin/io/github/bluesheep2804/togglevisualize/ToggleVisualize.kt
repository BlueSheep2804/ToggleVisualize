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
	private val sneakOverlayTexture = ResourceLocation("minecraft:textures/item/hopper.png")
	private val flyingOverlayTexture = ResourceLocation("minecraft:textures/item/elytra.png")
	*///?} else {
	private val sprintOverlayTexture = ResourceLocation.withDefaultNamespace("textures/mob_effect/speed.png")
	private val sneakOverlayTexture = ResourceLocation.withDefaultNamespace("textures/item/hopper.png")
	private val flyingOverlayTexture = ResourceLocation.withDefaultNamespace("textures/item/elytra.png")
	//?}
	private lateinit var config: ToggleVisualizeConfig

	override fun onInitializeClient() {
		ToggleVisualizeConfig.load()
		config = ToggleVisualizeConfig.instance
		HudRenderCallback.EVENT.register { guiGraphics: GuiGraphics, _: /*? if <1.21 {*//*Float*//*?} else {*/ DeltaTracker /*?}*/ ->
			val minecraftInstance = Minecraft.getInstance()
			val options = minecraftInstance.options
			//? if <1.20.2 {
			/*val debugScreen = options.renderDebug
			*///?} else {
			val debugScreen = minecraftInstance.debugOverlay.showDebugScreen()
			//?}
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
					guiGraphics.drawString(minecraftInstance.font, Component.translatable("key.sprint").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY), config.sprintTextPositionX, config.sprintTextPositionY, 16777215)
				}
			}

			val optionToggleSneak = options.toggleCrouch().get()
			val isSneakDown = options.keyShift.isDown
			if (optionToggleSneak && isSneakDown) {
				if (config.sneakShow) {
					//? if <1.21.2 {
					/*guiGraphics.blit(sneakOverlayTexture, config.sneakPositionX, config.sneakPositionY, 0F, 0F, 16, 16, 16, 16)
					*///?} else {
					guiGraphics.blit(RenderType::guiTexturedOverlay, sneakOverlayTexture, config.sneakPositionX, config.sneakPositionY, 0F, 0F, 16, 16, 16, 16)
					//?}
				}
				if (config.sneakShowText) {
					guiGraphics.drawString(minecraftInstance.font, Component.translatable("key.sneak").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY), config.sneakTextPositionX, config.sneakTextPositionY, 16777215)
				}
			}

			val player = minecraftInstance.player
			val isFlying = player?.isFallFlying
			if (isFlying == true) {
				if (config.flyingShow) {
					//? if <1.21.2 {
					/*guiGraphics.blit(
						flyingOverlayTexture,
						config.flyingPositionX,
						config.flyingPositionY,
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
						flyingOverlayTexture,
						config.flyingPositionX,
						config.flyingPositionY,
						0F,
						0F,
						16,
						16,
						16,
						16
					)
					//?}
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
	}
}