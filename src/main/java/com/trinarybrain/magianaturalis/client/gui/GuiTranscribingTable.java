package com.trinarybrain.magianaturalis.client.gui;

import org.lwjgl.opengl.GL11;

import com.trinarybrain.magianaturalis.client.util.RenderUtil;
import com.trinarybrain.magianaturalis.common.container.ContainerTranscribingTable;
import com.trinarybrain.magianaturalis.common.tile.TileTranscribingTable;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiTranscribingTable extends GuiContainer {

    private static final ResourceLocation TEXTURE = new ResourceLocation("thaumcraft", "textures/gui/gui_decontable.png");

    private final TileTranscribingTable tileTable;

    public GuiTranscribingTable(InventoryPlayer invPlayer, TileTranscribingTable tileTable) {
        super(new ContainerTranscribingTable(invPlayer, tileTable));
        this.tileTable = tileTable;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f1, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderUtil.bindTexture(TEXTURE);
        int k = (width - xSize) / 2;
        int l = (height - ySize) / 2;

        GL11.glEnable(GL11.GL_BLEND);
        drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
        if (tileTable.timer > 0) {
            int i1 = tileTable.timer * 46 / 40;
            drawTexturedModalRect(k + 93, l + 15 + 46 - i1, 176, 46 - i1, 9, i1);
        }
        GL11.glDisable(GL11.GL_BLEND);
    }

}
