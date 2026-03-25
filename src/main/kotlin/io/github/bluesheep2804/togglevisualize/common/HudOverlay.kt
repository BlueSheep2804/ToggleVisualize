package io.github.bluesheep2804.togglevisualize.common

import io.github.bluesheep2804.togglevisualize.ToggleVisualize.config
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.resources.Identifier
import net.minecraft.util.CommonColors
//? if >= 1.21
import net.minecraft.client.DeltaTracker
//? if >= 1.21.6 {
import net.minecraft.client.renderer.RenderPipelines
//?} else if >= 1.21.2 {
/*import net.minecraft.client.renderer.RenderType
*///?}

object HudOverlay {
    fun renderOverlay(guiGraphics: GuiGraphicsExtractor, deltaTracker: /*? if <1.21 {*//*Float*//*?} else {*/ DeltaTracker /*?}*/) {
        val minecraftInstance = Minecraft.getInstance()
        val options = minecraftInstance.options

        val debugScreen =
            //? if >= 26.1 {
            minecraftInstance.debugEntries.isOverlayVisible
            //?} else if >= 1.21.9 {
            /*minecraftInstance.debugEntries.isF3Visible
            *///?} else if >= 1.20.2 {
            /*minecraftInstance.debugOverlay.showDebugScreen()
            *///?} else {
            /*options.renderDebug
            *///?}

        val hideGui = options.hideGui
        if (debugScreen || hideGui || minecraftInstance.screen is PositioningScreen) return

        ToggleType.entries.forEach {
            if (it.condition.invoke()) {
                if (it.showIndicator.get(config)) {
                    blit(
                        guiGraphics,
                        it.indicatorLocation,
                        it.indicatorPosX.get(config),
                        it.indicatorPosY.get(config),
                        it.indicatorAnchorPoint.get(config)
                    )
                }
                if (it.showText.get(config)) {
                    val anchorPoint = it.textAnchorPoint.get(config)
                    val posX = anchorPoint.calculateX(
                        minecraftInstance.font.width(it.textComponent),
                        guiGraphics.guiWidth(),
                        it.textPosX.get(config)
                    )
                    val posY = anchorPoint.calculateY(
                        minecraftInstance.font.lineHeight,
                        guiGraphics.guiHeight(),
                        it.textPosY.get(config)
                    )

                    //~ if >= 26.1 'drawString' -> 'text'
                    guiGraphics.text(
                        minecraftInstance.font,
                        it.textComponent.copy().withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY),
                        posX,
                        posY,
                        CommonColors.WHITE
                    )
                }
            }
        }
    }

    fun blit(guiGraphics: GuiGraphicsExtractor, texture: Identifier, x: Int, y: Int, anchorPoint: AnchorPoint) {
        val posX = anchorPoint.calculateX(16, guiGraphics.guiWidth(), x)
        val posY = anchorPoint.calculateY(16, guiGraphics.guiHeight(), y)

        guiGraphics.blit(
            //? if >= 1.21.6 {
            RenderPipelines.GUI_TEXTURED,
            //?} else if >= 1.21.2{
            /*RenderType::guiTexturedOverlay,
            *///?}
            texture,
            posX,
            posY,
            0F,
            0F,
            16,
            16,
            16,
            16
        )
    }
}
