package trinarybrain.magia.naturalis.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import trinarybrain.magia.naturalis.client.util.RenderUtil;
import trinarybrain.magia.naturalis.common.container.ContainerTranscribingTable;
import trinarybrain.magia.naturalis.common.tile.TileTranscribingTable;
import trinarybrain.magia.naturalis.common.util.ResourceUtil;

public class GuiTranscribingTable extends GuiContainer
{
	private static final ResourceLocation rl = new ResourceLocation(ResourceUtil.DOMAIN, ResourceUtil.PATH_CONTAINER + "transcribing_table.png");
	private TileTranscribingTable tileTable;

	public GuiTranscribingTable(InventoryPlayer invPlayer, TileTranscribingTable tileTable)
	{
		super(new ContainerTranscribingTable(invPlayer, tileTable));
		this.tileTable = tileTable;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f1, int x, int y)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderUtil.bindTexture(rl);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;

		GL11.glEnable(GL11.GL_BLEND);
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		if(this.tileTable.timer > 0)
		{	    	
			int i1 = this.tileTable.timer * 46 / 40;
			this.drawTexturedModalRect(k + 93, l + 15 + 46 - i1, 176, 46 - i1, 9, i1);
		}
		GL11.glDisable(GL11.GL_BLEND);
	}
}
