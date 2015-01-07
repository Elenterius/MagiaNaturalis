package trinarybrain.magia.naturalis.client.render.tile;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import thaumcraft.client.renderers.models.ModelJar;
import thaumcraft.common.entities.monster.EntityTaintacle;
import trinarybrain.magia.naturalis.common.tile.TileArcaneChest;
import trinarybrain.magia.naturalis.common.tile.TileJarPrison;

public class TileJarPrisonRenderer extends TileEntitySpecialRenderer
{
	private final ModelJar jarModel = new ModelJar();

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f)
	{
		this.renderTileEntityAt((TileJarPrison)tile, x, y, z, f);
	}

	public void renderTileEntityAt(TileJarPrison tile, double x, double y, double z, float f)
	{
		EntityLiving entity = (EntityLiving) tile.getCachedEntity();
		if(entity != null)
		{
			GL11.glPushMatrix();
			GL11.glTranslated(x, y, z);
			GL11.glTranslatef(0.5F, 0.1F, 0.5F);

//			GL11.glRotatef(-RenderManager.instance.playerViewY - 180, 0.0F, 1.0F, 0.0F);
			
//			EntityLivingBase viewer = Minecraft.getMinecraft().renderViewEntity;
//			double d0 = viewer.posX - tile.xCoord;
//	        double d2 = viewer.posZ - tile.yCoord;
//	        
//			double d3 = (double)MathHelper.sqrt_double(d0 * d0 + d2 * d2);
//	        float f2 = (float)(Math.atan2(viewer.posX, d3) * 180.0D / Math.PI) - 90.0F;
//	        
//	        GL11.glRotatef(f2, 0.0F, 1.0F, 0.0F);

			float f1 = 0.21875F;
			GL11.glScalef(f1, f1, f1);

			RenderManager.instance.renderEntityWithPosYaw(entity, 0D, 0D, 0D, 0F, 0F);
			GL11.glPopMatrix();
		}
	}
}
