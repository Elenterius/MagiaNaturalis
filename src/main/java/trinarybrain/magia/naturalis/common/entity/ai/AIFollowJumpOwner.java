package trinarybrain.magia.naturalis.common.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import trinarybrain.magia.naturalis.common.core.Log;
import trinarybrain.magia.naturalis.common.entity.EntityOwnableCreature;

public class AIFollowJumpOwner extends EntityAIBase
{
	World world;
	EntityOwnableCreature taskOwner;
	EntityLivingBase entityOwner;
	private int jumpDelay;
	private boolean move;

	public AIFollowJumpOwner(EntityOwnableCreature TaskOwner)
	{
		this.taskOwner = TaskOwner;
		this.world = TaskOwner.worldObj;
		if(world != null) this.jumpDelay = this.world.rand.nextInt(20) + 10;
		this.setMutexBits(3);
	}

	public boolean shouldExecute()
	{
		if(!this.taskOwner.isWaiting())
		{
			this.entityOwner =  this.taskOwner.getOwner();
			
			if(this.entityOwner == null)
				return false;
			else if(this.taskOwner.getDistanceToEntity(this.entityOwner) > 5.0F)
				return true;
		}
		return false;
	}
	
	public boolean continueExecuting()
    {
		if(!this.taskOwner.isWaiting() && this.entityOwner != null && this.taskOwner.getDistanceToEntity(this.entityOwner) > 5.0F)
		{
			return true;
		}
		return false;	
    }
	
	public void updateTask()
	{
		this.entityOwner = this.taskOwner.getOwner();
		if(this.entityOwner != null)
		{
			float distance = this.taskOwner.getDistanceToEntity(this.entityOwner);
			if(distance > 20.0F || (!this.taskOwner.canEntityBeSeen(this.entityOwner) && distance > 8F && this.entityMotionXZbelow(taskOwner, 0.1D)))
			{
				int i = MathHelper.floor_double(this.entityOwner.posX) - 2;
				int j = MathHelper.floor_double(this.entityOwner.posZ) - 2;
				int k = MathHelper.floor_double(this.entityOwner.boundingBox.minY);
				
				for (int l = 0; l <= 4; l++)
					for (int i1 = 0; i1 <= 4; i1++)
						if (((l < 1) || (i1 < 1) || (l > 3) || (i1 > 3)) && (this.world.isBlockNormalCubeDefault(i + l, k - 1, j + i1, false)) && (!this.world.isBlockNormalCubeDefault(i + l, k, j + i1, false)) && (!this.world.isBlockNormalCubeDefault(i + l, k + 1, j + i1, false)))
						{
							this.world.playSoundEffect(i + l + 0.5F, k, j + i1 + 0.5F, "mob.endermen.portal", 0.1F, 1.0F);
							this.taskOwner.setLocationAndAngles(i + l + 0.5F, k, j + i1 + 0.5F, this.taskOwner.rotationYaw, this.taskOwner.rotationPitch);
							this.taskOwner.setAttackTarget(null);
						}
			}
			
			if(distance > 4.8F)
			{
				this.taskOwner.faceEntity(this.entityOwner, 15.0F, 20.0F);
				this.move = true;
			}
			else
			{
				this.move = false;
			}
			
			boolean high = true;

			if((this.taskOwner.onGround || this.taskOwner.isInWater()) && this.jumpDelay-- <= 0 && this.move)
			{
				this.taskOwner.faceEntity(this.entityOwner, 10.0F, 20.0F);
				this.jumpDelay = this.getJumpDelay()/3;
				
				double d0 = this.entityOwner.posX - this.taskOwner.posX;
		        double d1 = this.entityOwner.posZ - this.taskOwner.posZ;
		        float f = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
		        double d2 = 0.3000000298023224D;
		        this.taskOwner.motionX += d0 / (double)f * 0.5D * 1.25D + this.taskOwner.motionX * d2;
		        this.taskOwner.motionZ += d1 / (double)f * 0.5D * 1.25D + this.taskOwner.motionZ * d2;
		        this.taskOwner.motionY = (high ? 0.45D : 0.35D);
				
				this.world.playSoundAtEntity(this.taskOwner, "random.chestclosed", 0.15F, this.world.rand.nextFloat() * 0.1F + 0.9F);
			}
		}
	}
	
	protected boolean entityMotionXZbelow(EntityLiving entity, double d0)
	{
		double d1 = entity.motionX;
		double d2 = entity.motionZ;
		float f = MathHelper.sqrt_double(d1 * d1 + d2 * d2);
		if(f < d0)
			return true;
		return false;
	}
	
	protected int getJumpDelay()
	{
		return this.world.rand.nextInt(20) + 10;
	}
}
