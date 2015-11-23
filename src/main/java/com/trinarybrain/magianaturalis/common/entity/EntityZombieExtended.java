package com.trinarybrain.magianaturalis.common.entity;

import com.trinarybrain.magianaturalis.common.entity.ai.EntityAIOwnerTarget;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityZombieExtended extends EntityZombie implements IEntityOwnable
{
	public double damBonus = 0;

	public EntityZombieExtended(World par1World)
	{
		super(par1World);
		getNavigator().setBreakDoors(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIBreakDoor(this));
		tasks.addTask(2, new EntityAILeapAtTarget(this, 0.4F));
		//tasks.addTask(2, new EntityAIAttackExclude(this, getOwnerName(), 1.0D, true));
		tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityVillager.class, 1.0D, true));
		tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0D));
		tasks.addTask(5, new EntityAIMoveThroughVillage(this, 1.0D, false));
		tasks.addTask(6, new EntityAIWander(this, 1.0D));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(7, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAIOwnerTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, 0, false));
	}

	protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(17, "");
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D + damBonus);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(6.0D);
	}

	protected boolean isAIEnabled()
	{
		return true;
	}

	public void onUpdate()
	{
		super.onUpdate();
		if(!worldObj.isRemote && (getAttackTarget() == null || (getAttackTarget() == null && ticksExisted > 100)))
		{
			if(worldObj.isRemote)
				spawnExplosionParticle();
			setDead();
		}
	}

	@Override
	public void onKillEntity(EntityLivingBase par1EntityLivingBase)
	{
		super.onKillEntity(par1EntityLivingBase);

		if (par1EntityLivingBase instanceof EntityVillager)
		{
			if (rand.nextBoolean())
			{
				return;
			}

			EntityZombieExtended entityZombie = new EntityZombieExtended(worldObj);
			entityZombie.copyLocationAndAnglesFrom(par1EntityLivingBase);
			worldObj.removeEntity(par1EntityLivingBase);
			entityZombie.setVillager(true);
			worldObj.spawnEntityInWorld(entityZombie);
			worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1016, (int)posX, (int)posY, (int)posZ, 0);
		}
	}

	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeEntityToNBT(nbttagcompound);
		if(func_152113_b() == null)
		{
			nbttagcompound.setString("Owner", "");
		}
		else
		{
			nbttagcompound.setString("Owner", func_152113_b());
		}
	}

	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readEntityFromNBT(nbttagcompound);
		String name = nbttagcompound.getString("Owner");

		if (name.length() > 0)
		{
			setOwner(name);
		}
	}

	public void setOwner(String par1Str)
	{
		dataWatcher.updateObject(17, par1Str);
	}

	public EntityLivingBase getOwnerEntity()
	{
		return worldObj.getPlayerEntityByName(func_152113_b());
	}

	@Override
	public Entity getOwner()
	{
		return getOwnerEntity();
	}

	public void setExperienceValue(int i)
	{
		experienceValue = i;
	}

	@Override
	public String func_152113_b()
	{
		return dataWatcher.getWatchableObjectString(17);
	}
}
