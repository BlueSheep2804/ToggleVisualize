package io.github.bluesheep2804.togglevisualize

import io.github.bluesheep2804.togglevisualize.ToggleType.*
import io.github.bluesheep2804.togglevisualize.ToggleVisualize.flyingOverlayTexture
import io.github.bluesheep2804.togglevisualize.ToggleVisualize.logger
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
    private var sprintTextPositionX = config.sprintTextPositionX
    private var sprintTextPositionY = config.sprintTextPositionY
    private var sneakPositionX = config.sneakPositionX
    private var sneakPositionY = config.sneakPositionY
    private var sneakTextPositionX = config.sneakTextPositionX
    private var sneakTextPositionY = config.sneakTextPositionY
    private var flyingPositionX = config.flyingPositionX
    private var flyingPositionY = config.flyingPositionY
    private var flyingTextPositionX = config.flyingTextPositionX
    private var flyingTextPositionY = config.flyingTextPositionY
    private var mouseOffsetX = 0
    private var mouseOffsetY = 0
    private var activeToggleElement: ToggleType? = null
    private var isTextElement: Boolean = false
    private val layout = FrameLayout()
    private val descriptionLayout = LinearLayout.vertical()
    private val howtoLayout = FrameLayout()
    private val positionSettingLayout = FrameLayout()
    private lateinit var howtoStringWidget: StringWidget
    private lateinit var selectedHowtoStringWidget: StringWidget
    private lateinit var selectionStringWidget: StringWidget
    private val selectionComponentKey = "togglevisualize.config.positioning_tool.selecting"
    private lateinit var sprintText: StringWidget
    private lateinit var sneakText: StringWidget
    private lateinit var flyingText: StringWidget

    override fun init() {
        howtoStringWidget = StringWidget(Component.translatable("togglevisualize.config.positioning_tool.howto").withStyle(ChatFormatting.GRAY), font)
        selectedHowtoStringWidget = StringWidget(Component.translatable("togglevisualize.config.positioning_tool.howto.selected").withStyle(ChatFormatting.GRAY), font)
        selectionStringWidget = StringWidget(Component.empty(), font)

        sprintText = StringWidget(Component.translatable("key.sprint").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC), font)
        sneakText = StringWidget(Component.translatable("key.sneak").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC), font)
        flyingText = StringWidget(Component.translatable("item.minecraft.elytra").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC), font)

        selectionStringWidget.width = width
        descriptionLayout.defaultCellSetting().align(0.5f, 0.5f)
        descriptionLayout.spacing(4)

        positionSettingLayout.setMinDimensions(width, height)
        positionSettingLayout.addChild(sprintText)
        positionSettingLayout.addChild(sneakText)
        positionSettingLayout.addChild(flyingText)

        selectedHowtoStringWidget.visible = false
        selectionStringWidget.visible = false
        howtoLayout.addChild(howtoStringWidget)
        howtoLayout.addChild(selectedHowtoStringWidget)
        descriptionLayout.addChild(howtoLayout)
        descriptionLayout.addChild(selectionStringWidget)

        layout.addChild(descriptionLayout)
        layout.addChild(positionSettingLayout)
        layout.visitWidgets { guiEventListener: AbstractWidget ->
            val var10000 = addRenderableWidget(guiEventListener) as AbstractWidget
        }
        repositionElements()
    }

    override fun mouseMoved(rawMouseX: Double, rawMouseY: Double) {
        if (!isTextElement) return
        val mouseX = rawMouseX.toInt()
        val mouseY = rawMouseY.toInt()
        when (activeToggleElement) {
            Sprint -> sprintText.setPosition(mouseX - mouseOffsetX, mouseY - mouseOffsetY)
            Sneak -> sneakText.setPosition(mouseX - mouseOffsetX, mouseY - mouseOffsetY)
            Flying -> flyingText.setPosition(mouseX - mouseOffsetX, mouseY - mouseOffsetY)
            else -> TODO()
        }
    }

    override fun repositionElements() {
        descriptionLayout.arrangeElements()

        layout.setMinWidth(width)
        layout.setMinHeight(height)
        layout.arrangeElements()

        sprintText.setPosition(sprintTextPositionX, sprintTextPositionY)
        sneakText.setPosition(sneakTextPositionX, sneakTextPositionY)
        flyingText.setPosition(flyingTextPositionX, flyingTextPositionY)
    }

    // 背景のぼかしを無効化
    override fun renderBlurredBackground() {}

    override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        super.render(guiGraphics, mouseX, mouseY, partialTick)

        blit(
            guiGraphics,
            sprintOverlayTexture,
            if (activeToggleElement == Sprint && !isTextElement) mouseX - mouseOffsetX else sprintPositionX,
            if (activeToggleElement == Sprint && !isTextElement) mouseY - mouseOffsetY else sprintPositionY
        )
        blit(
            guiGraphics,
            sneakOverlayTexture,
            if (activeToggleElement == Sneak && !isTextElement) mouseX - mouseOffsetX else sneakPositionX,
            if (activeToggleElement == Sneak && !isTextElement) mouseY - mouseOffsetY else sneakPositionY
        )
        blit(
            guiGraphics,
            flyingOverlayTexture,
            if (activeToggleElement == Flying && !isTextElement) mouseX - mouseOffsetX else flyingPositionX,
            if (activeToggleElement == Flying && !isTextElement) mouseY - mouseOffsetY else flyingPositionY
        )

        if (activeToggleElement != null) return

        val selected: List<Int> = when {
            isHoveredSprint(mouseX, mouseY) -> listOf(sprintPositionX, sprintPositionY)
            isHoveredSneak(mouseX, mouseY) -> listOf(sneakPositionX, sneakPositionY)
            isHoveredFlying(mouseX, mouseY) -> listOf(flyingPositionX, flyingPositionY)
            else -> emptyList()
        }
        if (selected.isNotEmpty()) {
            blit(
                guiGraphics,
                selectOverlayTexture,
                selected[0],
                selected[1]
            )
        }

        val rectangle = when {
            sprintText.isHovered -> sprintText.rectangle
            sneakText.isHovered -> sneakText.rectangle
            flyingText.isHovered -> flyingText.rectangle
            else -> null
        }

        if (rectangle != null){
            guiGraphics.renderOutline(
                rectangle.left()-1,
                rectangle.top()-1,
                rectangle.width+1,
                rectangle.height+1,
                -1
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
                        if (isTextElement) {
                            sprintTextPositionX = mouseX - mouseOffsetX
                            sprintTextPositionY = mouseY - mouseOffsetY
                            config.sprintTextPositionX = sprintTextPositionX
                            config.sprintTextPositionY = sprintTextPositionY
                        } else {
                            sprintPositionX = mouseX - mouseOffsetX
                            sprintPositionY = mouseY - mouseOffsetY
                            config.sprintPositionX = sprintPositionX
                            config.sprintPositionY = sprintPositionY
                        }
                    }
                    Sneak -> {
                        if (isTextElement) {
                            sneakTextPositionX = mouseX - mouseOffsetX
                            sneakTextPositionY = mouseY - mouseOffsetY
                            config.sneakTextPositionX = sneakTextPositionX
                            config.sneakTextPositionY = sneakTextPositionY
                        } else {
                            sneakPositionX = mouseX - mouseOffsetX
                            sneakPositionY = mouseY - mouseOffsetY
                            config.sneakPositionX = sneakPositionX
                            config.sneakPositionY = sneakPositionY
                        }
                    }
                    Flying -> {
                        if (isTextElement) {
                            flyingTextPositionX = mouseX - mouseOffsetX
                            flyingTextPositionY = mouseY - mouseOffsetY
                            config.flyingTextPositionX = flyingTextPositionX
                            config.flyingTextPositionY = flyingTextPositionY
                        } else {
                            flyingPositionX = mouseX - mouseOffsetX
                            flyingPositionY = mouseY - mouseOffsetY
                            config.flyingPositionX = flyingPositionX
                            config.flyingPositionY = flyingPositionY
                        }
                    }
                    else -> {}
                }
                ToggleVisualizeConfig.save()
            }

            when (activeToggleElement) {
                Sprint -> sprintText.setPosition(sprintTextPositionX, sprintTextPositionY)
                Sneak -> sneakText.setPosition(sneakTextPositionX, sneakTextPositionY)
                Flying -> flyingText.setPosition(flyingTextPositionX, flyingTextPositionY)
                else -> {}
            }

            selectionStringWidget.visible = false
            howtoStringWidget.visible = true
            selectedHowtoStringWidget.visible = false
            activeToggleElement = null
            isTextElement = false
        } else if (isHoveredSprint(mouseX, mouseY)) {
            activeToggleElement = Sprint
            mouseOffsetX = mouseX - sprintPositionX
            mouseOffsetY = mouseY - sprintPositionY
        } else if (sprintText.isHovered) {
            activeToggleElement = Sprint
            isTextElement = true
            mouseOffsetX = mouseX - sprintTextPositionX
            mouseOffsetY = mouseY - sprintTextPositionY
        } else if (isHoveredSneak(mouseX, mouseY)) {
            activeToggleElement = Sneak
            mouseOffsetX = mouseX - sneakPositionX
            mouseOffsetY = mouseY - sneakPositionY
        } else if (sneakText.isHovered) {
            activeToggleElement = Sneak
            isTextElement = true
            mouseOffsetX = mouseX - sneakTextPositionX
            mouseOffsetY = mouseY - sneakTextPositionY
        } else if (isHoveredFlying(mouseX, mouseY)) {
            activeToggleElement = Flying
            mouseOffsetX = mouseX - flyingPositionX
            mouseOffsetY = mouseY - flyingPositionY
        } else if (flyingText.isHovered) {
            activeToggleElement = Flying
            isTextElement = true
            mouseOffsetX = mouseX - flyingTextPositionX
            mouseOffsetY = mouseY - flyingTextPositionY
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

        val widgetTypeComponent = Component.translatable(if (isTextElement) {
            "togglevisualize.config.option.text"
        } else {
            "togglevisualize.config.option.indicator"
        })

        selectionStringWidget.message = Component.translatable(selectionComponentKey, toggleTypeComponent, widgetTypeComponent).withStyle(ChatFormatting.GRAY)
    }

    private fun blit(guiGraphics: GuiGraphics, atlasLocation: ResourceLocation, x: Int, y: Int) {
        guiGraphics.blit(
            RenderType::guiTexturedOverlay,
            atlasLocation,
            x,
            y,
            0F,
            0F,
            16,
            16,
            16,
            16
        )
    }
}