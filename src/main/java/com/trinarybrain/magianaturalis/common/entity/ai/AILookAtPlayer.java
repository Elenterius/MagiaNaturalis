package com.trinarybrain.magianaturalis.common.entity.ai;

import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import com.trinarybrain.magianaturalis.common.entity.EntityPechCustom;

public class AILookAtPlayer extends EntityAIWatchClosest
{
	private final EntityPechCustom pech;

	public AILookAtPlayer(EntityPechCustom entityPech)
	{
		super(entityPech, EntityPlayer.class, 8.0F);
		this.pech = entityPech;
	}

	public boolean shouldExecute()
	{
		if (this.pech.isInteracting)
		{
			this.closestEntity = this.pech.getInteractionTarget();
			return true;
		}
		else
		{
			return false;
		}
	}
}
