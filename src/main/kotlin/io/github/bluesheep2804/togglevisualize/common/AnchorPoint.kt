package io.github.bluesheep2804.togglevisualize.common

import dev.isxander.yacl3.api.NameableEnum
import net.minecraft.network.chat.Component

enum class AnchorPoint(val x: Float, val y: Float): NameableEnum {
    LEFT_TOP(0f, 0f),
    RIGHT_TOP(1f, 0f),
    LEFT_BOTTOM(0f, 1f),
    RIGHT_BOTTOM(1f, 1f),
    CENTER(0.5f, 0.5f),
    LEFT_CENTER(0f, 0.5f),
    RIGHT_CENTER(1f, 0.5f),
    CENTER_TOP(0.5f, 0f),
    CENTER_BOTTOM(0.5f, 1f);

    override fun getDisplayName(): Component {
        return Component.translatable("togglevisualize.anchor_point.${this.name.lowercase()}")
    }

    fun calculateX(width: Int, screenWidth: Int, offsetX: Int): Int {
        return (screenWidth * x + offsetX - width * x).toInt()
    }

    fun calculateY(height: Int, screenHeight: Int, offsetY: Int): Int {
        return (screenHeight * y + offsetY - height * y).toInt()
    }
}