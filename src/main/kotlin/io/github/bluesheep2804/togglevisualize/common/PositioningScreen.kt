package io.github.bluesheep2804.togglevisualize.common

import net.minecraft.ChatFormatting
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.components.AbstractWidget
import net.minecraft.client.gui.components.ImageWidget
import net.minecraft.client.gui.components.StringWidget
import net.minecraft.client.gui.layouts.FrameLayout
import net.minecraft.client.gui.layouts.LinearLayout
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import kotlin.reflect.KMutableProperty1

//? if >1.21.8 {
import net.minecraft.client.input.MouseButtonEvent
import com.mojang.blaze3d.platform.cursor.CursorTypes
//?}

class PositioningScreen(private val yaclParent: Screen): Screen(Component.translatable("togglevisualize.config.positioning_tool.title")) {
    private val config = ToggleVisualizeConfig.Companion.instance
    private var mouseOffsetX = 0
    private var mouseOffsetY = 0
    private var activeToggleType: ToggleType? = null
    private var isTextElement: Boolean = false
    private val layout = FrameLayout()
    private val descriptionLayout = FrameLayout()
    private lateinit var howtoLayout: LinearLayout
    private lateinit var selectedHowtoLayout: LinearLayout
    private val positionSettingLayout = FrameLayout()
    private lateinit var selectionStringWidget: StringWidget
    private val selectionComponentKey = "togglevisualize.config.positioning_tool.selecting"
    private val indicatorWidgets: MutableMap<ToggleType, ImageWidget> = mutableMapOf()
    private val textWidgets: MutableMap<ToggleType, StringWidget> = mutableMapOf()
    private val indicatorAnchorPoints: MutableMap<ToggleType, AnchorPoint> = mutableMapOf()
    private val textAnchorPoints: MutableMap<ToggleType, AnchorPoint> = mutableMapOf()

    override fun init() {
        val howtoStringWidgets = listOf(
            StringWidget(
                Component.translatable("togglevisualize.config.positioning_tool.howto.left_click")
                    .withStyle(ChatFormatting.GRAY),
                font
            ),
            StringWidget(
                Component.translatable("togglevisualize.config.positioning_tool.howto.scroll")
                    .withStyle(ChatFormatting.GRAY),
                font
            )
        )
        val selectedHowtoStringWidgets = listOf(
            StringWidget(
                Component.translatable("togglevisualize.config.positioning_tool.howto.selected.left_click")
                    .withStyle(ChatFormatting.GRAY),
                font
            ),
            StringWidget(
                Component.translatable("togglevisualize.config.positioning_tool.howto.selected.right_click")
                    .withStyle(ChatFormatting.GRAY),
                font
            )
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

            indicatorAnchorPoints[it] = it.indicatorAnchorPoint.get(config)
            textAnchorPoints[it] = it.textAnchorPoint.get(config)
        }

        selectionStringWidget.width = width
        //? if >= 1.20.2 {
        howtoLayout = LinearLayout.vertical()
        howtoLayout.defaultCellSetting().align(0.5f, 0.5f)
        howtoLayout.spacing(4)

        selectedHowtoLayout = LinearLayout.vertical()
        selectedHowtoLayout.defaultCellSetting().align(0.5f, 0.5f)
        selectedHowtoLayout.spacing(4)
        //?} else {
        /*howtoLayout = LinearLayout(
            0,
            howtoStringWidgets.sumOf { it.height } + 12,
            LinearLayout.Orientation.VERTICAL
        )
        howtoLayout.defaultChildLayoutSetting().align(0.5f, 0.5f)
        howtoLayout.defaultChildLayoutSetting().padding(4)

        selectedHowtoLayout = LinearLayout(
            0,
            selectedHowtoStringWidgets.sumOf { it.height } + selectionStringWidget.height + 16,
            LinearLayout.Orientation.VERTICAL
        )
        selectedHowtoLayout.defaultChildLayoutSetting().align(0.5f, 0.5f)
        selectedHowtoLayout.defaultChildLayoutSetting().padding(4)
        *///?}

        positionSettingLayout.setMinDimensions(width, height)
        indicatorWidgets.forEach { positionSettingLayout.addChild(it.value) }
        textWidgets.forEach{ positionSettingLayout.addChild(it.value) }

        selectedHowtoStringWidgets.forEach { it.visible = false }
        selectionStringWidget.visible = false

        howtoStringWidgets.forEach(howtoLayout::addChild)
        selectedHowtoStringWidgets.forEach(selectedHowtoLayout::addChild)
        selectedHowtoLayout.addChild(selectionStringWidget)

        descriptionLayout.addChild(howtoLayout)
        descriptionLayout.addChild(selectedHowtoLayout)

        layout.addChild(descriptionLayout)
        layout.addChild(positionSettingLayout)
        layout.visitWidgets { guiEventListener: AbstractWidget ->
            val var10000 = addRenderableWidget(guiEventListener) as AbstractWidget
        }
        repositionElements()
    }

    override fun mouseMoved(rawMouseX: Double, rawMouseY: Double) {
        if (activeToggleType == null) return
        if (!isTextElement && indicatorWidgets[activeToggleType] == null) return
        if (isTextElement && textWidgets[activeToggleType] == null) return
        val mouseX = rawMouseX.toInt()
        val mouseY = rawMouseY.toInt()
        if (isTextElement) {
            textWidgets[activeToggleType]!!.setPosition(
                textAnchorPoints[activeToggleType]!!.calculateX(
                    font.width(activeToggleType!!.textComponent),
                    width,
                    mouseX - mouseOffsetX
                ),
                textAnchorPoints[activeToggleType]!!.calculateY(font.lineHeight, height, mouseY - mouseOffsetY)
            )
        } else {
            indicatorWidgets[activeToggleType]!!.setPosition(
                indicatorAnchorPoints[activeToggleType]!!.calculateX(16, width, mouseX - mouseOffsetX),
                indicatorAnchorPoints[activeToggleType]!!.calculateY(16, height, mouseY - mouseOffsetY)
            )
        }
    }

    override fun mouseScrolled(mouseX: Double, mouseY: Double/*? if >1.20.1 {*/, scrollX: Double/*?}*/, scrollY: Double): Boolean {
        if (activeToggleType != null) return false
        val hoveredWidget = getHoveredWidget() ?: return false
        val hoveredToggleType = getHoveredToggleType(hoveredWidget)

        if (textWidgets.containsValue(hoveredWidget)) {
            val anchorPoint = textAnchorPoints[hoveredToggleType]!!
            val newAnchorPoint = if (scrollY > 0) anchorPoint.previous() else anchorPoint.next()
            hoveredToggleType.textAnchorPoint.set(config, newAnchorPoint)
            textAnchorPoints[hoveredToggleType] = newAnchorPoint

            adjustPosition(
                config,
                anchorPoint,
                newAnchorPoint,
                hoveredToggleType.textPosX,
                hoveredToggleType.textPosY,
                font.width(hoveredToggleType.textComponent),
                font.lineHeight
            )
        } else {
            val anchorPoint = indicatorAnchorPoints[hoveredToggleType]!!
            val newAnchorPoint = if (scrollY > 0) anchorPoint.previous() else anchorPoint.next()
            hoveredToggleType.indicatorAnchorPoint.set(config, newAnchorPoint)
            indicatorAnchorPoints[hoveredToggleType] = newAnchorPoint

            adjustPosition(
                config,
                anchorPoint,
                newAnchorPoint,
                hoveredToggleType.indicatorPosX,
                hoveredToggleType.indicatorPosY,
                16,
                16
            )
        }

        return super.mouseScrolled(mouseX, mouseY/*? if >1.20.1 {*/, scrollX/*?}*/, scrollY)
    }

    override fun repositionElements() {
        descriptionLayout.arrangeElements()

        layout.setMinWidth(width)
        layout.setMinHeight(height)
        layout.arrangeElements()

        indicatorWidgets.forEach {
            val posX = it.key.indicatorPosX.get(config)
            val posY = it.key.indicatorPosY.get(config)
            val anchorPoint = it.key.indicatorAnchorPoint.get(config)
            it.value.setPosition(
                anchorPoint.calculateX(16, width, posX),
                anchorPoint.calculateY(16, height, posY)
            )
        }
        textWidgets.forEach {
            val posX = it.key.textPosX.get(config)
            val posY = it.key.textPosY.get(config)
            val anchorPoint = it.key.textAnchorPoint.get(config)
            it.value.setPosition(
                anchorPoint.calculateX(font.width(it.key.textComponent), width, posX),
                anchorPoint.calculateY(font.lineHeight, height, posY)
            )
        }
    }

    // 背景のぼかしを無効化
    //? if >= 26.1 {
    override fun extractBlurredBackground(guiGraphics: GuiGraphicsExtractor) {}
    //?} else if >= 1.20.5 {
    /*override fun renderBlurredBackground(
        //? if >= 1.21.6 {
        guiGraphics: GuiGraphicsExtractor
        //?} else if <1.21.3 {
        /*patialTick: Float
        *///?}
    ) {}
    *///?}

    //~ if >= 26.1 'render' -> 'extractRenderState'
    override fun extractRenderState(guiGraphics: GuiGraphicsExtractor, mouseX: Int, mouseY: Int, partialTick: Float) {
        // 1.20.1で描画がリセットされない不具合の対策
        //? if <= 1.20.1 {
        /*if (minecraft?.level == null) {
            renderBackground(guiGraphics)
        }
        *///?}
        //~ if >=26.1 'render' -> 'extractRenderState'
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick)

        if (activeToggleType != null) return

        val hoveredWidget = getHoveredWidget()
        if (hoveredWidget != null) {
            isTextElement = textWidgets.containsValue(hoveredWidget)
            val hoveredToggleType = getHoveredToggleType(hoveredWidget)

            val anchorPoint = (if (isTextElement) textAnchorPoints else indicatorAnchorPoints)[hoveredToggleType]!!
            //? if >= 1.21.6 {
            guiGraphics.setTooltipForNextFrame(
            //?} else {
            /*guiGraphics.renderTooltip(
                font,
            *///?}
                listOf(
                    anchorPoint.previous().previous().displayName.copy().withStyle(ChatFormatting.DARK_GRAY),
                    anchorPoint.previous().displayName.copy().withStyle(ChatFormatting.GRAY),
                    anchorPoint.displayName.copy().withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.WHITE),
                    anchorPoint.next().displayName.copy().withStyle(ChatFormatting.GRAY),
                    anchorPoint.next().next().displayName.copy().withStyle(ChatFormatting.DARK_GRAY)
                ).map { it.visualOrderText },
                mouseX + 4,
                mouseY + 16
            )

            val rectangle = hoveredWidget.rectangle

            //? if >=1.21.9
            guiGraphics.requestCursor(CursorTypes.RESIZE_ALL)

            guiGraphics
                //? if >= 26.1 {
                .outline(
                //?} else if >=1.21.9 {
                /*.submitOutline(
                *///?} else {
                /*.renderOutline(
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
        //? if >= 1.21.9 {
        event: MouseButtonEvent,
        bl: Boolean
        //?} else {
        /*rawMouseX: Double,
        rawMouseY: Double,
        button: Int
        *///?}
    ): Boolean {
        //? if >= 1.21.9 {
        val button = event.button()
        val rawMouseX = event.x()
        val rawMouseY = event.y()
        //?}

        if (button > 1) {
            //? if >= 1.21.9 {
            return super.mouseClicked(event, bl)
            //?} else {
            /*return super.mouseClicked(rawMouseX, rawMouseY, button)
            *///?}
        }

        val mouseX = rawMouseX.toInt()
        val mouseY = rawMouseY.toInt()
        if (activeToggleType != null) {
            if (isTextElement && textWidgets[activeToggleType] != null) {
                val anchorPoint = textAnchorPoints[activeToggleType]!!
                if (button == 0) {
                    activeToggleType!!.textPosX.set(config, mouseX - mouseOffsetX)
                    activeToggleType!!.textPosY.set(config, mouseY - mouseOffsetY)
                }
                textWidgets[activeToggleType]!!
                    .setPosition(
                        anchorPoint.calculateX(
                            font.width(activeToggleType!!.textComponent),
                            width,
                            activeToggleType!!.textPosX.get(config)
                        ),
                        anchorPoint.calculateY(font.lineHeight, height, activeToggleType!!.textPosY.get(config))
                    )
            } else if (!isTextElement && indicatorWidgets[activeToggleType] != null) {
                val anchorPoint = indicatorAnchorPoints[activeToggleType]!!
                if (button == 0) {
                    activeToggleType!!.indicatorPosX.set(config, mouseX - mouseOffsetX)
                    activeToggleType!!.indicatorPosY.set(config, mouseY - mouseOffsetY)
                }
                indicatorWidgets[activeToggleType]!!
                    .setPosition(
                        anchorPoint.calculateX(16, width, activeToggleType!!.indicatorPosX.get(config)),
                        anchorPoint.calculateY(16, height, activeToggleType!!.indicatorPosY.get(config))
                    )
            }

            howtoLayout.visitWidgets { it.visible = true }
            selectedHowtoLayout.visitWidgets { it.visible = false }
            activeToggleType = null
            isTextElement = false
        } else {
            if (button == 0) {
                val hoveredWidget = getHoveredWidget()
                if (hoveredWidget != null) {
                    isTextElement = textWidgets.containsValue(hoveredWidget)
                    val hoveredToggleType = getHoveredToggleType(hoveredWidget)

                    activeToggleType = hoveredToggleType
                    mouseOffsetX = mouseX - activeToggleType.let { if (isTextElement) it!!.textPosX else it!!.indicatorPosX }
                        .get(config)
                    mouseOffsetY = mouseY - activeToggleType.let { if (isTextElement) it!!.textPosY else it!!.indicatorPosY }
                        .get(config)
                }
            }
        }

        if (activeToggleType != null) {
            changeSelectionStringWidget(activeToggleType!!)
            howtoLayout.visitWidgets { it.visible = false }
            selectedHowtoLayout.visitWidgets { it.visible = true }
        }
        //? if >= 1.21.9 {
        return super.mouseClicked(event, bl)
        //?} else {
        /*return super.mouseClicked(rawMouseX, rawMouseY, button)
        *///?}
    }

    override fun onClose() {
        ToggleVisualizeConfig.Companion.save()
        minecraft?.setScreen(ToggleVisualizeConfig.Companion.configScreen(yaclParent).generateScreen(yaclParent))
    }

    private fun adjustPosition(
        config: ToggleVisualizeConfig,
        oldAnchorPoint: AnchorPoint,
        newAnchorPoint: AnchorPoint,
        posX: KMutableProperty1<ToggleVisualizeConfig, Int>,
        posY: KMutableProperty1<ToggleVisualizeConfig, Int>,
        elementWidth: Int,
        elementHeight: Int
    ) {
        val offsetX = oldAnchorPoint.calculateDeltaX(newAnchorPoint, elementWidth, width)
        val offsetY = oldAnchorPoint.calculateDeltaY(newAnchorPoint, elementHeight, height)
        posX.set(
            config,
            posX.get(config) + offsetX
        )
        posY.set(
            config,
            posY.get(config) + offsetY
        )
    }

    private fun getHoveredWidget(): AbstractWidget? {
        return arrayOf(*indicatorWidgets.values.toTypedArray(), *textWidgets.values.toTypedArray()).firstOrNull {
            it.isHovered
        }
    }

    private fun getHoveredToggleType(hoveredWidget: AbstractWidget): ToggleType {
        return if (textWidgets.containsValue(hoveredWidget)) {
            textWidgets.entries.first { it.value == hoveredWidget }.key
        } else {
            indicatorWidgets.entries.first { it.value == hoveredWidget }.key
        }
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
        repositionElements()
    }

    private fun imageWidget(imageLocation: Identifier): ImageWidget {
        //? if >= 1.20.2 {
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
