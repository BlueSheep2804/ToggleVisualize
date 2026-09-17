package io.github.bluesheep2804.togglevisualize.common.compatibility

//? if >= 1.21.9
import net.minecraft.client.input.MouseButtonEvent

class MouseButtonEventWrapper(val x: Double, val y: Double, val button: Int) {
    //? if >= 1.21.9 {
    constructor(event: MouseButtonEvent) : this(
        event.x,
        event.y,
        event.button()
    )
    //?}

    val isPrimaryButton: Boolean
        get() {
            //? if < 26.3 {
            /*return button == 0
            *///?} else {
            return button == 1
            //?}
        }

    val isSecondaryButton: Boolean
        get() {
            //? if <26.3 {
            /*return button == 1
            *///?} else {
            return button == 3
            //?}
        }
}