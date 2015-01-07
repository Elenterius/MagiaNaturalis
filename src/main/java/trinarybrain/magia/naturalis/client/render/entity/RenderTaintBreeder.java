package trinarybrain.magia.naturalis.client.render.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import trinarybrain.magia.naturalis.client.model.entity.ModelTaintBreeder;
import trinarybrain.magia.naturalis.common.util.ResourceUtil;

public class RenderTaintBreeder extends RenderLiving
{
	private static final ResourceLocation rl = new ResourceLocation(ResourceUtil.DOMAIN, ResourceUtil.PATH_MODEL + "taint_breeder.png");
	public RenderTaintBreeder()
	{
		super(new ModelTaintBreeder(), 1.0F);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return rl;
	}
}
