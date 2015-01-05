package trinarybrain.magia.naturalis.client.render.entity;

import org.lwjgl.opengl.GL11;

import trinarybrain.magia.naturalis.client.model.entity.ModelTrunkCorrupted;
import trinarybrain.magia.naturalis.client.model.entity.ModelTrunkDemonic;
import trinarybrain.magia.naturalis.client.model.entity.ModelTrunkSinister;
import trinarybrain.magia.naturalis.client.model.entity.ModelTrunkTainted;
import trinarybrain.magia.naturalis.common.entity.EntityEvilTrunk;
import trinarybrain.magia.naturalis.common.util.ResourceUtil;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.ResourceLocation;

public class RenderEvilTrunk extends RenderLiving
{
	public ModelTrunkCorrupted modelTC = new ModelTrunkCorrupted();
	public ModelTrunkSinister modelTS = new ModelTrunkSinister();
	public ModelTrunkDemonic modelTD = new ModelTrunkDemonic();
	public ModelTrunkTainted modelTT = new ModelTrunkTainted();

	public static final ResourceLocation rlTC = new ResourceLocation(ResourceUtil.DOMAIN, ResourceUtil.PATH_MODEL + "trunk_corrupted.png");
	public static final ResourceLocation rlTD = new ResourceLocation(ResourceUtil.DOMAIN, ResourceUtil.PATH_MODEL + "trunk_demonic_wings.png");
	public static final ResourceLocation rlTS = new ResourceLocation(ResourceUtil.DOMAIN, ResourceUtil.PATH_MODEL + "trunk_sinister.png");
	public static final ResourceLocation rlTT = new ResourceLocation(ResourceUtil.DOMAIN, ResourceUtil.PATH_MODEL + "trunk_tainted.png");

	public RenderEvilTrunk()
	{
		super(new ModelTrunkCorrupted(), 0.5F);
	}

	//"corrupted", "sinister", "demonic", "tainted"
	protected void renderModel(EntityLivingBase entity, float x, float y, float z, float f1, float f2, float f3)
	{
		if(entity != null)
		{
			switch(((EntityEvilTrunk)entity).getTrunkType())
			{
			case 0:
				this.mainModel = modelTC;
				break;
			case 1:
				this.mainModel = modelTS;
				break;
			case 2: 
				this.mainModel = modelTD;
				break;
			case 3:
				this.mainModel = modelTT;
			}
		}

		super.renderModel(entity, x, y, z, f1, f2, f3);
	}
		
	protected ResourceLocation getEntityTexture(EntityEvilTrunk entity)
	{
		switch(entity.getTrunkType())
		{
		case 0:
			return rlTC;
		case 1:
			return rlTS;
		case 2: 
			return rlTD;
		case 3:
			return rlTT;
		}
		return rlTC;
	}

	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.getEntityTexture((EntityEvilTrunk) entity);
	}
}
