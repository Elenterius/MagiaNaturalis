package com.trinarybrain.magianaturalis.client.gui;

import org.lwjgl.opengl.GL11;

import com.trinarybrain.magianaturalis.client.util.RenderUtil;
import com.trinarybrain.magianaturalis.common.container.ContainerArcaneChest;
import com.trinarybrain.magianaturalis.common.tile.TileArcaneChest;
import com.trinarybrain.magianaturalis.common.util.ResourceUtil;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiArcaneChest extends GuiContainer
{
	private static final ResourceLocation rl = new ResourceLocation(ResourceUtil.DOMAIN, ResourceUtil.PATH_CONTAINER + "chest_double.png");
	private TileArcaneChest chest;

	public GuiArcaneChest(InventoryPlayer invPlayer, TileArcaneChest tile)
	{
		super(new ContainerArcaneChest(invPlayer, tile));
		this.chest = tile;
		this.ySize = 222;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y)
	{
		this.fontRendererObj.drawString(this.chest.getInventoryName(), 8, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f1, int x, int y)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderUtil.bindTexture(rl);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}
}
