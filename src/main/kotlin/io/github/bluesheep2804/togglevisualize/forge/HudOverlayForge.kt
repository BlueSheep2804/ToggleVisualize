//? if forge {
/*package io.github.bluesheep2804.togglevisualize.forge

import io.github.bluesheep2804.togglevisualize.common.HudOverlay
import net.minecraft.client.gui.GuiGraphics
import net.minecraftforge.client.gui.overlay.ForgeGui
import net.minecraftforge.client.gui.overlay.IGuiOverlay

class HudOverlayForge: IGuiOverlay {
    override fun render(forgeGui: ForgeGui, guiGraphics: GuiGraphics, partialTick: Float, screenWidth: Int, screenHeight: Int) {
        HudOverlay.renderOverlay(guiGraphics, partialTick)
    }
}
*///?}
