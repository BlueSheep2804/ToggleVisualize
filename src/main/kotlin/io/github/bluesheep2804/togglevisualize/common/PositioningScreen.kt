package io.github.bluesheep2804.togglevisualize.common

import net.minecraft.ChatFormatting
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.AbstractWidget
import net.minecraft.client.gui.components.ImageWidget
import net.minecraft.client.gui.components.StringWidget
import net.minecraft.client.gui.layouts.FrameLayout
import net.minecraft.client.gui.layouts.LinearLayout
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation

//? if >1.21.8 {
import net.minecraft.client.input.MouseButtonEvent
import com.mojang.blaze3d.platform.cursor.CursorTypes
//?}

class PositioningScreen(private val yaclParent: Screen): Screen(Component.translatable("togglevisualize.config.positioning_tool.title")) {
    private val config = ToggleVisualizeConfig.Companion.instance
    private var mouseOffsetX = 0
    private var mouseOffsetY = 0
    private var activeToggleElement: ToggleType? = null
    private var isTextElement: Boolean = false
    private val layout = FrameLayout()
    private lateinit var descriptionLayout: LinearLayout
    private val howtoLayout = FrameLayout()
    private val positionSettingLayout = FrameLayout()
    private lateinit var howtoStringWidget: StringWidget
    private lateinit var selectedHowtoStringWidget: StringWidget
    private lateinit var selectionStringWidget: StringWidget
    private val selectionComponentKey = "togglevisualize.config.positioning_tool.selecting"
    private val indicatorWidgets: MutableMap<ToggleType, ImageWidget> = mutableMapOf()
    private val textWidgets: MutableMap<ToggleType, StringWidget> = mutableMapOf()

    override fun init() {
        howtoStringWidget = StringWidget(
            Component.translatable("togglevisualize.config.positioning_tool.howto").withStyle(ChatFormatting.GRAY), font
        )
        selectedHowtoStringWidget = StringWidget(
            Component.translatable("togglevisualize.config.positioning_tool.howto.selected")
                .withStyle(ChatFormatting.GRAY), font
        )
        selectionStringWidget = StringWidget(Component.empty(), font)

        ToggleType.entries.forEach {
            if (it.showIndicator.get(config)) {
                indicatorWidgets[it] = imageWidget(it.indicatorLocation)
            }
            if (it.showText.get(config)) {
                textWidgets[it] = StringWidget(
                    it.textComponent.copy().withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC),
                    font
                )
            }
        }

        selectionStringWidget.width = width
        //? if >1.20.1 {
        descriptionLayout = LinearLayout.vertical()
        descriptionLayout.defaultCellSetting().align(0.5f, 0.5f)
        descriptionLayout.spacing(4)
        //?} else {
        /*descriptionLayout = LinearLayout(0, 0, LinearLayout.Orientation.VERTICAL)
        descriptionLayout.defaultChildLayoutSetting().align(0.5f, 0.5f)
        descriptionLayout.defaultChildLayoutSetting().padding(4)
        *///?}

        positionSettingLayout.setMinDimensions(width, height)
        indicatorWidgets.forEach { positionSettingLayout.addChild(it.value) }
        textWidgets.forEach{ positionSettingLayout.addChild(it.value) }

        selectedHowtoStringWidget.visible = false
        selectionStringWidget.visible = false
        howtoLayout.addChild(howtoStringWidget)
        howtoLayout.addChild(selectedHowtoStringWidget)
        //? if >1.20.1 {
        descriptionLayout.addChild(howtoLayout)
        descriptionLayout.addChild(selectionStringWidget)
        //?} else {
        /*descriptionLayout.addChild(selectionStringWidget)
        descriptionLayout.addChild(howtoLayout)
        *///?}

        layout.addChild(descriptionLayout)
        layout.addChild(positionSettingLayout)
        layout.visitWidgets { guiEventListener: AbstractWidget ->
            val var10000 = addRenderableWidget(guiEventListener) as AbstractWidget
        }
        repositionElements()
    }

    override fun mouseMoved(rawMouseX: Double, rawMouseY: Double) {
        if (activeToggleElement == null) return
        if (!isTextElement && indicatorWidgets[activeToggleElement] == null) return
        if (isTextElement && textWidgets[activeToggleElement] == null) return
        val mouseX = rawMouseX.toInt()
        val mouseY = rawMouseY.toInt()
        if (isTextElement) {
            textWidgets[activeToggleElement]!!.setPosition(
                mouseX - mouseOffsetX,
                mouseY - mouseOffsetY
            )
        } else {
            indicatorWidgets[activeToggleElement]!!.setPosition(
                mouseX - mouseOffsetX,
                mouseY - mouseOffsetY
            )
        }
    }

    override fun repositionElements() {
        descriptionLayout.arrangeElements()

        layout.setMinWidth(width)
        layout.setMinHeight(height)
        layout.arrangeElements()

        indicatorWidgets.forEach {
            it.value.setPosition(it.key.indicatorPosX.get(config), it.key.indicatorPosY.get(config))
        }
        textWidgets.forEach {
            it.value.setPosition(it.key.textPosX.get(config), it.key.textPosY.get(config))
        }
    }

    // 背景のぼかしを無効化
    //? if >1.20.4 {
    override fun renderBlurredBackground(/*? if >1.20.4 <1.21.3 {*//*patialTick: Float*//*?} else if >=1.21.6 {*/guiGraphics: GuiGraphics/*?}*/) {}
    //?}

    override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        //? if <1.20.2 {
        /*if (minecraft?.level == null) {
            renderBackground(guiGraphics)
        }
        *///?}
        super.render(guiGraphics, mouseX, mouseY, partialTick)

        if (activeToggleElement != null) return

        val rectangle = arrayOf(*indicatorWidgets.values.toTypedArray(), *textWidgets.values.toTypedArray()).firstOrNull {
            it.isHovered
        }?.rectangle

        if (rectangle != null){
            //? if >1.21.8 {
            guiGraphics.requestCursor(CursorTypes.RESIZE_ALL)
            guiGraphics.submitOutline(
            //?} else {
            /*guiGraphics.renderOutline(
            *///?}
                rectangle.left()-1,
                rectangle.top()-1,
                rectangle.width+2,
                rectangle.height+2,
                -1
            )
        }
    }

    override fun mouseClicked(
        //? if >1.21.8 {
        event: MouseButtonEvent,
        bl: Boolean
        //?} else {
        /*rawMouseX: Double,
        rawMouseY: Double,
        button: Int
        *///?}
    ): Boolean {
        //? if >1.21.8 {
        val button = event.button()
        val rawMouseX = event.x()
        val rawMouseY = event.y()
        //?}

        if (button > 1) {
            //? if >1.21.8 {
            return super.mouseClicked(event, bl)
            //?} else {
            /*return super.mouseClicked(rawMouseX, rawMouseY, button)
            *///?}
        }

        val mouseX = rawMouseX.toInt()
        val mouseY = rawMouseY.toInt()
        if (activeToggleElement != null) {
            if (isTextElement && textWidgets[activeToggleElement] != null) {
                if (button == 0) {
                    activeToggleElement!!.textPosX.set(config, mouseX - mouseOffsetX)
                    activeToggleElement!!.textPosY.set(config, mouseY - mouseOffsetY)
                }
                textWidgets[activeToggleElement]!!
                    .setPosition(activeToggleElement!!.textPosX.get(config), activeToggleElement!!.textPosY.get(config))
            } else if (!isTextElement && indicatorWidgets[activeToggleElement] != null) {
                if (button == 0) {
                    activeToggleElement!!.indicatorPosX.set(config, mouseX - mouseOffsetX)
                    activeToggleElement!!.indicatorPosY.set(config, mouseY - mouseOffsetY)
                }
                indicatorWidgets[activeToggleElement]!!
                    .setPosition(activeToggleElement!!.indicatorPosX.get(config), activeToggleElement!!.indicatorPosY.get(config))
            }

            selectionStringWidget.visible = false
            howtoStringWidget.visible = true
            selectedHowtoStringWidget.visible = false
            activeToggleElement = null
            isTextElement = false
        } else {
            val element = arrayOf(*indicatorWidgets.values.toTypedArray(), *textWidgets.values.toTypedArray()).firstOrNull {
                it.isHovered
            }
            if (element != null) {
                isTextElement = textWidgets.containsValue(element)
                activeToggleElement = indicatorWidgets.entries.firstOrNull { it.value == element }?.key
                    ?: textWidgets.entries.firstOrNull { it.value == element }?.key
                mouseOffsetX = mouseX - activeToggleElement.let { if (isTextElement) it!!.textPosX else it!!.indicatorPosX }
                    .get(config)
                mouseOffsetY = mouseY - activeToggleElement.let { if (isTextElement) it!!.textPosY else it!!.indicatorPosY }
                    .get(config)
            }
        }

        if (activeToggleElement != null) {
            changeSelectionStringWidget(activeToggleElement!!)
            selectionStringWidget.visible = true
            howtoStringWidget.visible = false
            selectedHowtoStringWidget.visible = true
        }
        //? if >1.21.8 {
        return super.mouseClicked(event, bl)
        //?} else {
        /*return super.mouseClicked(rawMouseX, rawMouseY, button)
        *///?}
    }

    override fun onClose() {
        ToggleVisualizeConfig.Companion.save()
        minecraft?.setScreen(ToggleVisualizeConfig.Companion.configScreen(yaclParent).generateScreen(yaclParent))
    }

    private fun changeSelectionStringWidget(toggle: ToggleType) {
        val widgetTypeComponent = Component.translatable(if (isTextElement) {
            "togglevisualize.config.option.text"
        } else {
            "togglevisualize.config.option.indicator"
        })

        selectionStringWidget.message = Component.translatable(
            selectionComponentKey,
            toggle.textComponent,
            widgetTypeComponent
        ).withStyle(ChatFormatting.GRAY)
    }

    private fun imageWidget(imageLocation: ResourceLocation): ImageWidget {
        //? if >1.20.1 {
        return ImageWidget.texture(
            16,
            16,
            imageLocation,
            16,
            16
        )
        //?} else {
        /*return ImageWidget(
            16,
            16,
            imageLocation
        )
        *///?}
    }
}
