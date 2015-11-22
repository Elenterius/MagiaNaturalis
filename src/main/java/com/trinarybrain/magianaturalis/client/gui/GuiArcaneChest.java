package com.trinarybrain.magianaturalis.client.gui;

import org.lwjgl.opengl.GL11;

import com.trinarybrain.magianaturalis.client.util.RenderUtil;
import com.trinarybrain.magianaturalis.common.Reference;
import com.trinarybrain.magianaturalis.common.container.ContainerArcaneChest;
import com.trinarybrain.magianaturalis.common.tile.TileArcaneChest;
import com.trinarybrain.magianaturalis.common.util.ResourceUtil;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiArcaneChest extends GuiContainer
{
	private static final ResourceLocation rl_gw = new ResourceLocation(Reference.ID, "textures/gui/container/chest_greatwood.png");
	private static final ResourceLocation rl_sw = new ResourceLocation(Reference.ID, "textures/gui/container/chest_silverwood.png");
	private TileArcaneChest chest;
	private int type;

	public GuiArcaneChest(InventoryPlayer invPlayer, TileArcaneChest tile)
	{
		super(new ContainerArcaneChest(invPlayer, tile));
		chest = tile;
		type = tile.getChestType();
		ySize = type == 1 ? 222 : 240;
		if(type == 2) xSize = 212;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y)
	{
		fontRendererObj.drawString(chest.getInventoryName(), 8, 6, 4210752);
		fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8 + ((type-1)*18), ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f1, int x, int y)
	{
		RenderUtil.bindTexture(type == 1 ? rl_gw : rl_sw);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
	}
}
