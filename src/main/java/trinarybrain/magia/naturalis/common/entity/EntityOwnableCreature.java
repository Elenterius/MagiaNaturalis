package trinarybrain.magia.naturalis.common.entity;

import trinarybrain.magia.naturalis.common.entity.ai.AIWait;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.Team;
import net.minecraft.world.World;

public class EntityOwnableCreature extends EntityCreature implements IEntityOwnable
{
	protected AIWait aiWait = new AIWait(this);

	public EntityOwnableCreature(World world)
	{
		super(world);
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(16, Byte.valueOf((byte) 0));
		this.dataWatcher.addObject(17, "");
	}

	public void writeEntityToNBT(NBTTagCompound data)
	{
		super.writeEntityToNBT(data);

		if(this.getOwnerName() == null)
			data.setString("Owner", "");
		else
			data.setString("Owner", this.getOwnerName());

		data.setBoolean("Waiting", this.isWaiting());
	}

	public void readEntityFromNBT(NBTTagCompound data)
	{
		super.readEntityFromNBT(data);
		String s = data.getString("Owner");

		if(s.length() > 0) this.setOwner(s);

		this.aiWait.setWaiting(data.getBoolean("Waiting"));
		this.setWaiting(data.getBoolean("Waiting"));
	}

	public boolean isWaiting()
	{
		return this.dataWatcher.getWatchableObjectByte(16) != 0;
	}

	public void setWaiting(boolean bool)
	{
		this.dataWatcher.updateObject(16, Byte.valueOf((byte) (bool ? 1 : 0)));
	}

	@Override
	// getOwnerName() from IEntityOwnable
	public String func_152113_b()
	{
		return this.dataWatcher.getWatchableObjectString(17);
	}

	/*
	*Wrapper for func_152113_b()
	*/
	public String getOwnerName()
	{
		return this.func_152113_b();
	}

	public void setOwner(String owner)
	{
		this.dataWatcher.updateObject(17, owner);
	}

	@Override
	public EntityLivingBase getOwner()
	{
		return this.worldObj.getPlayerEntityByName(this.getOwnerName());
	}

	public AIWait getAIWaitObj()
	{
		return this.aiWait;
	}

	public Team getTeam()
	{
		EntityLivingBase entitylivingbase = this.getOwner();
		if(entitylivingbase != null) return entitylivingbase.getTeam();
		return super.getTeam();
	}

	public boolean isOnSameTeam(EntityLivingBase par1EntityLivingBase)
	{
		EntityLivingBase entitylivingbase1 = this.getOwner();
		if(par1EntityLivingBase == entitylivingbase1) return true;
		if(entitylivingbase1 != null) return entitylivingbase1.isOnSameTeam(par1EntityLivingBase);
		return super.isOnSameTeam(par1EntityLivingBase);
	}

	public boolean canAttack(EntityLivingBase attacker, EntityLivingBase owner)
	{
		return true;
	}

}
