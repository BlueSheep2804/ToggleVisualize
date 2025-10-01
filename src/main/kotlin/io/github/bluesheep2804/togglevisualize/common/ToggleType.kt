package io.github.bluesheep2804.togglevisualize.common

import io.github.bluesheep2804.togglevisualize.ToggleVisualize.rlMinecraft
import net.minecraft.client.Minecraft
import net.minecraft.client.Options
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import kotlin.reflect.KMutableProperty1

enum class ToggleType(
    val indicatorLocation: ResourceLocation,
    val textComponent: MutableComponent,
    val condition: () -> Boolean,
    val showIndicator: KMutableProperty1<ToggleVisualizeConfig, Boolean>,
    val indicatorPosX: KMutableProperty1<ToggleVisualizeConfig, Int>,
    val indicatorPosY: KMutableProperty1<ToggleVisualizeConfig, Int>,
    val showText: KMutableProperty1<ToggleVisualizeConfig, Boolean>,
    val textPosX: KMutableProperty1<ToggleVisualizeConfig, Int>,
    val textPosY: KMutableProperty1<ToggleVisualizeConfig, Int>,
) {
    Sprint(
        rlMinecraft("textures/item/iron_boots.png"),
        Component.translatable("key.sprint"),
        {
            options.toggleSprint().get() && options.keySprint.isDown
        },
        ToggleVisualizeConfig::sprintShow,
        ToggleVisualizeConfig::sprintPositionX,
        ToggleVisualizeConfig::sprintPositionY,
        ToggleVisualizeConfig::sprintShowText,
        ToggleVisualizeConfig::sprintTextPositionX,
        ToggleVisualizeConfig::sprintTextPositionY
    ),
    Sneak(
        rlMinecraft("textures/item/hopper.png"),
        Component.translatable("key.sneak"),
        {
            options.toggleCrouch().get() && options.keyShift.isDown
        },
        ToggleVisualizeConfig::sneakShow,
        ToggleVisualizeConfig::sneakPositionX,
        ToggleVisualizeConfig::sneakPositionY,
        ToggleVisualizeConfig::sneakShowText,
        ToggleVisualizeConfig::sneakTextPositionX,
        ToggleVisualizeConfig::sneakTextPositionY
    ),
    Flying(
        rlMinecraft("textures/item/elytra.png"),
        Component.translatable("item.minecraft.elytra"),
        {
            Minecraft.getInstance().player?.isFallFlying == true
        },
        ToggleVisualizeConfig::flyingShow,
        ToggleVisualizeConfig::flyingPositionX,
        ToggleVisualizeConfig::flyingPositionY,
        ToggleVisualizeConfig::flyingShowText,
        ToggleVisualizeConfig::flyingTextPositionX,
        ToggleVisualizeConfig::flyingTextPositionY
    );

    companion object {
        private val options: Options
            get() = Minecraft.getInstance().options
    }
}
