package io.github.bluesheep2804.togglevisualize

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.DeltaTracker
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.renderer.RenderType
import net.minecraft.resources.ResourceLocation
import org.slf4j.LoggerFactory

object ToggleVisualize : ModInitializer {
    private val logger = LoggerFactory.getLogger("togglevisualize")
    private val sprintOverlayTexture = ResourceLocation.withDefaultNamespace("textures/mob_effect/speed.png")
	private lateinit var config: ToggleVisualizeConfig

	override fun onInitialize() {
		ToggleVisualizeConfig.HANDLER.load()
		config = ToggleVisualizeConfig.HANDLER.instance()
		HudRenderCallback.EVENT.register { guiGraphics: GuiGraphics, _: DeltaTracker ->
			val options = Minecraft.getInstance().options
			val optionToggleSprint = options.toggleSprint().get()
			val isSprintDown = options.keySprint.isDown
			if (optionToggleSprint && isSprintDown) {
				guiGraphics.blit(RenderType::guiTexturedOverlay, sprintOverlayTexture, 16, 16, 0F, 0F, 16, 16, 16, 16)
			}
		}
	}
}