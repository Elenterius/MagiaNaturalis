package com.trinarybrain.magianaturalis.client.gui;

import org.lwjgl.opengl.GL11;

import com.trinarybrain.magianaturalis.common.container.ContainerEvilTrunk;
import com.trinarybrain.magianaturalis.common.entity.EntityEvilTrunk;
import com.trinarybrain.magianaturalis.common.util.Platform;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import thaumcraft.client.lib.UtilsFX;

public class GuiEvilTrunk extends GuiContainer
{
	private EntityPlayer entityPlayer;
	private EntityEvilTrunk entityTrunk;
	private int inventoryRows = 4;

	public GuiEvilTrunk(EntityPlayer player, EntityEvilTrunk entity)
	{
		super(new ContainerEvilTrunk(player.inventory, player.worldObj, entity));
		this.entityPlayer = player;
		this.entityTrunk = entity;
		this.ySize = 200;
	}

	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		GL11.glPushMatrix();
		GL11.glScaled(0.5D, 0.5D, 0.5D);
		this.fontRendererObj.drawString(this.entityTrunk.getOwner().getCommandSenderName() + Platform.translate("entity.trunk.guiname"), 8, 4, 0xC0A0F0);
		GL11.glPopMatrix();
	}

	protected void drawGuiContainerBackgroundLayer(float f, int x, int y)
	{
		if(this.entityTrunk.isDead) this.mc.thePlayer.closeScreen();

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		UtilsFX.bindTexture("textures/gui/guitrunkbase.png");
		GL11.glEnable(GL11.GL_BLEND);

		int j = (this.width - this.xSize) / 2;
		int k = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(j, k, 0, 0, this.xSize, this.ySize);

		int healthbar = Math.round(this.entityTrunk.getHealth() / this.entityTrunk.getMaxHealth() * 39.0F);
		this.drawTexturedModalRect(j + 134, k + 2, 176, 16, healthbar, 6);
		this.drawTexturedModalRect(j, k + 80, 0, 206, this.xSize, 27);

		if(this.entityTrunk.isWaiting())
			this.drawTexturedModalRect(j + 112, k, 176, 0, 10, 10);

		GL11.glDisable(GL11.GL_BLEND);
	}

	protected void mouseClicked(int x, int y, int z)
	{
		super.mouseClicked(x, y, z);
		int sx = (this.width - this.xSize) / 2;
		int sy = (this.height - this.ySize) / 2;
		int i = x - (sx + 112);
		int j = y - (sy + 0);

		if(i >= 0 && j >= 0 && i < 10 && j <= 10)
		{
			this.entityTrunk.worldObj.playSound(this.entityTrunk.posX, this.entityTrunk.posY, this.entityTrunk.posZ, "random.click", 0.3F, 0.6F + (this.entityTrunk.isWaiting() ? 0.0F : 0.2F), false);

			if(this.entityTrunk.isWaiting())
				this.entityPlayer.addChatMessage(new ChatComponentTranslation("entity.trunk.move", new Object[0]));
			else
				this.entityPlayer.addChatMessage(new ChatComponentTranslation("entity.trunk.stay", new Object[0]));

			this.mc.playerController.sendEnchantPacket(this.inventorySlots.windowId, 1);
		}
	}

	public void onGuiClosed()
	{
		this.entityTrunk.setOpen(false);
		super.onGuiClosed();
	}
}
