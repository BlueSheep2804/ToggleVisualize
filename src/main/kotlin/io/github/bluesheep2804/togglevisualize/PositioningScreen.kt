package io.github.bluesheep2804.togglevisualize

import io.github.bluesheep2804.togglevisualize.ToggleType.*
import io.github.bluesheep2804.togglevisualize.ToggleVisualize.flyingOverlayTexture
import io.github.bluesheep2804.togglevisualize.ToggleVisualize.sneakOverlayTexture
import io.github.bluesheep2804.togglevisualize.ToggleVisualize.sprintOverlayTexture
import net.minecraft.ChatFormatting
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.AbstractWidget
import net.minecraft.client.gui.components.StringWidget
import net.minecraft.client.gui.layouts.FrameLayout
import net.minecraft.client.gui.layouts.LinearLayout
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.renderer.RenderType
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation

class PositioningScreen(private val yaclParent: Screen): Screen(Component.translatable("togglevisualize.config.positioning_tool.title")) {
    private val selectOverlayTexture = ResourceLocation.withDefaultNamespace("textures/block/sculk_shrieker_top.png")
    private val config = ToggleVisualizeConfig.instance
    private var sprintPositionX = config.sprintPositionX
    private var sprintPositionY = config.sprintPositionY
    private var sneakPositionX = config.sneakPositionX
    private var sneakPositionY = config.sneakPositionY
    private var flyingPositionX = config.flyingPositionX
    private var flyingPositionY = config.flyingPositionY
    private var mouseOffsetX = 0
    private var mouseOffsetY = 0
    private var activeToggleElement: ToggleType? = null
    private val layout = FrameLayout()
    private val descriptionLayout = LinearLayout.vertical()
    private val howtoLayout = FrameLayout()
    private lateinit var howtoStringWidget: StringWidget
    private lateinit var selectedHowtoStringWidget: StringWidget
    private lateinit var selectionStringWidget: StringWidget
    private val selectionComponentKey = "togglevisualize.config.positioning_tool.selecting"

    override fun init() {
        howtoStringWidget = StringWidget(Component.translatable("togglevisualize.config.positioning_tool.howto").withStyle(ChatFormatting.GRAY), font)
        selectedHowtoStringWidget = StringWidget(Component.translatable("togglevisualize.config.positioning_tool.howto.selected").withStyle(ChatFormatting.GRAY), font)
        selectionStringWidget = StringWidget(Component.empty(), font)

        selectionStringWidget.width = width
        descriptionLayout.defaultCellSetting().align(0.5f, 0.5f)
        descriptionLayout.spacing(4)

        selectedHowtoStringWidget.visible = false
        selectionStringWidget.visible = false
        howtoLayout.addChild(howtoStringWidget)
        howtoLayout.addChild(selectedHowtoStringWidget)
        descriptionLayout.addChild(howtoLayout)
        descriptionLayout.addChild(selectionStringWidget)

        layout.addChild(descriptionLayout)
        layout.visitWidgets { guiEventListener: AbstractWidget ->
            val var10000 = addRenderableWidget(guiEventListener) as AbstractWidget
        }
        repositionElements()
    }

    override fun repositionElements() {
        descriptionLayout.arrangeElements()

        layout.setMinWidth(width)
        layout.setMinHeight(height)
        layout.arrangeElements()
    }

    // 背景のぼかしを無効化
    override fun renderBlurredBackground() {}

    override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        super.render(guiGraphics, mouseX, mouseY, partialTick)

        guiGraphics.blit(
            RenderType::guiTexturedOverlay,
            sprintOverlayTexture,
            if (activeToggleElement == Sprint) mouseX - mouseOffsetX else sprintPositionX,
            if (activeToggleElement == Sprint) mouseY - mouseOffsetY else sprintPositionY,
            0F,
            0F,
            16,
            16,
            16,
            16
        )
        guiGraphics.blit(
            RenderType::guiTexturedOverlay,
            sneakOverlayTexture,
            if (activeToggleElement == Sneak) mouseX - mouseOffsetX else sneakPositionX,
            if (activeToggleElement == Sneak) mouseY - mouseOffsetY else sneakPositionY,
            0F,
            0F,
            16,
            16,
            16,
            16
        )
        guiGraphics.blit(
            RenderType::guiTexturedOverlay,
            flyingOverlayTexture,
            if (activeToggleElement == Flying) mouseX - mouseOffsetX else flyingPositionX,
            if (activeToggleElement == Flying) mouseY - mouseOffsetY else flyingPositionY,
            0F,
            0F,
            16,
            16,
            16,
            16
        )

        if (activeToggleElement != null) return

        val selected: List<Int> = when {
            isHoveredSprint(mouseX, mouseY) -> listOf(sprintPositionX, sprintPositionY)
            isHoveredSneak(mouseX, mouseY) -> listOf(sneakPositionX, sneakPositionY)
            isHoveredFlying(mouseX, mouseY) -> listOf(flyingPositionX, flyingPositionY)
            else -> emptyList()
        }
        if (selected.isNotEmpty()) {
            guiGraphics.blit(
                RenderType::guiTexturedOverlay,
                selectOverlayTexture,
                selected[0],
                selected[1],
                0F,
                0F,
                16,
                16,
                16,
                16
            )
        }
    }

    override fun mouseClicked(rawMouseX: Double, rawMouseY: Double, button: Int): Boolean {
        if (button > 1) return super.mouseClicked(rawMouseX, rawMouseY, button)

        val mouseX = rawMouseX.toInt()
        val mouseY = rawMouseY.toInt()
        if (activeToggleElement != null) {
            if (button == 0) {
                when (activeToggleElement) {
                    Sprint -> {
                        sprintPositionX = mouseX - mouseOffsetX
                        sprintPositionY = mouseY - mouseOffsetY
                        config.sprintPositionX = sprintPositionX
                        config.sprintPositionY = sprintPositionY
                    }
                    Sneak -> {
                        sneakPositionX = mouseX - mouseOffsetX
                        sneakPositionY = mouseY - mouseOffsetY
                        config.sneakPositionX = sneakPositionX
                        config.sneakPositionY = sneakPositionY
                    }
                    Flying -> {
                        flyingPositionX = mouseX - mouseOffsetX
                        flyingPositionY = mouseY - mouseOffsetY
                        config.flyingPositionX = flyingPositionX
                        config.flyingPositionY = flyingPositionY
                    }
                    else -> {}
                }
                ToggleVisualizeConfig.save()
            }
            selectionStringWidget.visible = false
            howtoStringWidget.visible = true
            selectedHowtoStringWidget.visible = false
            activeToggleElement = null
        } else if (isHoveredSprint(mouseX, mouseY)) {
            activeToggleElement = Sprint
            mouseOffsetX = mouseX - sprintPositionX
            mouseOffsetY = mouseY - sprintPositionY
        } else if (isHoveredSneak(mouseX, mouseY)) {
            activeToggleElement = Sneak
            mouseOffsetX = mouseX - sneakPositionX
            mouseOffsetY = mouseY - sneakPositionY
        } else if (isHoveredFlying(mouseX, mouseY)) {
            activeToggleElement = Flying
            mouseOffsetX = mouseX - flyingPositionX
            mouseOffsetY = mouseY - flyingPositionY
        }

        if (activeToggleElement != null) {
            changeSelectionStringWidget(activeToggleElement!!)
            selectionStringWidget.visible = true
            howtoStringWidget.visible = false
            selectedHowtoStringWidget.visible = true
        }
        return super.mouseClicked(rawMouseX, rawMouseY, button)
    }

    override fun onClose() {
        minecraft?.setScreen(ToggleVisualizeConfig.configScreen(yaclParent).generateScreen(yaclParent))
    }

    private fun isHovered(mouseX: Int, mouseY: Int, x: Int, y: Int): Boolean {
        return (x <= mouseX && x+16 >= mouseX && y <= mouseY && y+16 >= mouseY)
    }

    private fun isHoveredSprint(mouseX: Int, mouseY: Int): Boolean {
        return isHovered(mouseX, mouseY, sprintPositionX, sprintPositionY)
    }

    private fun isHoveredSneak(mouseX: Int, mouseY: Int): Boolean {
        return isHovered(mouseX, mouseY, sneakPositionX, sneakPositionY)
    }

    private fun isHoveredFlying(mouseX: Int, mouseY: Int): Boolean {
        return isHovered(mouseX, mouseY, flyingPositionX, flyingPositionY)
    }

    private fun changeSelectionStringWidget(toggle: ToggleType) {
        val toggleTypeComponent = Component.translatable(when (toggle) {
            Sprint -> "key.sprint"
            Sneak -> "key.sneak"
            Flying -> "item.minecraft.elytra"
        })
        selectionStringWidget.message = Component.translatable(selectionComponentKey, toggleTypeComponent).withStyle(ChatFormatting.GRAY)
    }
}