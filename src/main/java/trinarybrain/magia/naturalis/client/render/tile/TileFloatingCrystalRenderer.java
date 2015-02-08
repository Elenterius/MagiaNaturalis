package trinarybrain.magia.naturalis.client.render.tile;

import java.awt.Color;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

public class TileFloatingCrystalRenderer extends TileEntitySpecialRenderer
{
	private Color color;
	private IModelCustom imodel;
	private static final ResourceLocation relay = new ResourceLocation("thaumcraft", "textures/models/vis_relay.obj");
	private ResourceLocation rl = new ResourceLocation("thaumcraft", "textures/models/vis_relay.png");

	public TileFloatingCrystalRenderer()
	{
		this.color = new Color(0.0F, 0.5F, 0.5F);
		this.imodel = AdvancedModelLoader.loadModel(relay);
	}

	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float tick)
	{
		this.renderCrystalPylon((float)x, (float)y, (float)z, tick, color.getRGB(), new Random(entity.xCoord * entity.yCoord + entity.zCoord));
	}

	public void renderCrystalPylon(double x, double y, double z, float partialTicks, int color, Random rand)
	{
		int ticks = Minecraft.getMinecraft().renderViewEntity.ticksExisted;
		float shade = MathHelper.sin((ticks + rand.nextInt(10)) / (10.0F + rand.nextFloat())) * 0.05F + 0.95F;
		float bob = MathHelper.sin(ticks / 14.0F) * 0.025F;

		GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5D, y + 1.0D + bob, z + 0.5D);
			GL11.glRotatef(270.0F, 1.0F, 0.0F, 0.0F);
			GL11.glScalef(2.0F, 2.0F, 2.0F);
			
			GL11.glPushMatrix();
				this.bindTexture(this.rl);
		
				GL11.glPushMatrix();
				GL11.glScalef(2.25F, 2.25F, 1.25F);
				GL11.glTranslated(0.0D, 0.0D, 0.05D + bob);
				GL11.glRotatef(-ticks * 0.5F, 0.0F, 0.0F, 1.0F);
				this.imodel.renderPart("RingFloat");
				GL11.glPopMatrix();
		
				GL11.glRotatef(ticks * 0.5F, 0.0F, 0.0F, 1.0F);
				
				GL11.glPushMatrix();
				GL11.glScaled(1.0D, 1.0D, 1.0D);
				GL11.glTranslated(0.0D, 0.0D, 0.2D);
				this.imodel.renderPart("RingBase");
				GL11.glPopMatrix();
				
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
				Color c = new Color(color);
				GL11.glColor3f(c.getRed() / 255F, c.getGreen() / 255F, c.getBlue() / 255F);
					
				int shadeOffset = (int) (210F * shade);
				int lightX = shadeOffset % 65536;
				int lightY = shadeOffset / 65536;
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightX / 1.0F, lightY / 1.0F);
				
				this.imodel.renderPart("Crystal");
				
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
}
