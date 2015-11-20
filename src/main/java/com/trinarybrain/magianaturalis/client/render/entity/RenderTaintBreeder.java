package com.trinarybrain.magianaturalis.client.render.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import com.trinarybrain.magianaturalis.client.model.entity.ModelTaintBreeder;
import com.trinarybrain.magianaturalis.common.util.ResourceUtil;

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
