package trinarybrain.magia.naturalis.common.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import thaumcraft.common.config.ConfigItems;
import trinarybrain.magia.naturalis.common.MagiaNaturalis;
import trinarybrain.magia.naturalis.common.core.Log;
import trinarybrain.magia.naturalis.common.entity.ai.AIFollowJumpOwner;
import trinarybrain.magia.naturalis.common.entity.ai.AILeapAtTarget;
import trinarybrain.magia.naturalis.common.entity.ai.AIOwnerHurtByTarget;
import trinarybrain.magia.naturalis.common.entity.ai.AIOwnerHurtTarget;
import trinarybrain.magia.naturalis.common.inventory.InventoryEvilTrunk;
import trinarybrain.magia.naturalis.common.item.ItemsMN;
import trinarybrain.magia.naturalis.common.util.Platform;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityEvilTrunk extends EntityOwnableCreature
{
	public float skullrot;
	private int jumpDelay;
	private int eatDelay;
	public InventoryEvilTrunk inventory = new InventoryEvilTrunk(this, 36);
	public String name[] = new String[] {"corrupted", "sinister", "demonic", "tainted"};

	public EntityEvilTrunk(World world, int type)
	{
		this(world);
		this.setTrunkType((byte) type);
	}

	public EntityEvilTrunk(World world)
	{
		super(world);
		this.setSize(0.8F, 0.8F);
		this.skullrot = 0.0F;
		this.preventEntitySpawning = true;
		this.isImmuneToFire = true;

		// if(this.getTrunkType() == 3)
		// ADD FLIGHT

		this.tasks.addTask(2, this.aiWait);
		this.tasks.addTask(3, new AILeapAtTarget(this));
		this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 0.6D, true));
		this.tasks.addTask(5, new AIFollowJumpOwner(this));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));

		this.targetTasks.addTask(1, new AIOwnerHurtByTarget(this));
		this.targetTasks.addTask(2, new AIOwnerHurtTarget(this));
		this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(14, new Byte((byte)0));
		this.dataWatcher.addObject(15, new Byte((byte)0));
		this.dataWatcher.addObject(18, new Byte((byte)0));
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(75.0D);
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
	}

	public void writeEntityToNBT(NBTTagCompound data)
	{
		super.writeEntityToNBT(data);
		data.setByte("TrunkType", this.getTrunkType());
		data.setTag("Inventory", this.inventory.writeToNBT(new NBTTagList()));
	}

	public void readEntityFromNBT(NBTTagCompound data)
	{
		super.readEntityFromNBT(data);
		this.setTrunkType(data.getByte("TrunkType"));
		
		NBTTagList nbttaglist = data.getTagList("Inventory", 10);
	    this.inventory.readFromNBT(nbttaglist);
	}

	public boolean isAIEnabled()
	{
		return true;
	}

	protected void updateAITick()
	{
		if (this.eatDelay > 0) this.eatDelay -= 1;
		this.fallDistance = 0.0F;

		if(this.getOwner() != null)
		{
			if(this.getAttackTarget() != null && !getAttackTarget().isDead && this.getAttackTarget() != this.getOwner())
			{
				this.faceEntity(this.getAttackTarget(), 10.0F, 20.0F);
				if ((this.attackTime <= 0) && (this.getDistanceToEntity(this.getAttackTarget()) < 1.5D) && (this.getAttackTarget().boundingBox.maxY > this.boundingBox.minY) && (this.getAttackTarget().boundingBox.minY < this.boundingBox.maxY))
				{
					this.attackTime = (10 + this.worldObj.rand.nextInt(5));
					this.getAttackTarget().attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
					this.worldObj.setEntityState(this, (byte)17);
					this.worldObj.playSoundAtEntity(this, "mob.blaze.hit", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
				}

			}
		}
	}

	public boolean attackEntityFrom(DamageSource damage, float amount)
	{
		if(this.isEntityInvulnerable())
		{
			return false;
		}
		else
		{
			if(damage == DamageSource.lava) return false;
			if(damage == DamageSource.inFire) return false;
			if(damage == DamageSource.onFire) return false;
			if(damage == DamageSource.drown) return false;
			return super.attackEntityFrom(damage, amount);
		}
	}

	@Override
	public boolean canAttack(EntityLivingBase attacker, EntityLivingBase owner)
	{
		if(!(attacker instanceof EntityCreeper) && !(attacker instanceof EntityGhast))
		{
			if(attacker instanceof EntityWolf)
			{
				if(((EntityWolf)attacker).isTamed() && ((EntityWolf)attacker).getOwner() == owner)
					return false;
			}
			if(attacker instanceof EntityOwnableCreature)
			{
				if(((EntityOwnableCreature)attacker).getOwner() == owner)
					return false;
			}

			return attacker instanceof EntityPlayer && owner instanceof EntityPlayer && !((EntityPlayer)owner).canAttackPlayer((EntityPlayer)attacker) ? false : !(attacker instanceof EntityHorse) || !((EntityHorse)attacker).isTame();
		}
		else
		{
			return false;
		}
	}

	public void onUpdate()
	{
		super.onUpdate();

		if (this.worldObj.isRemote)
		{
			if (!this.onGround)
			{
				if ((this.motionY < 0.0D) && (!this.inWater)) this.skullrot += 0.015F;
			}

			if ((this.onGround) || (this.inWater))
			{
				if (!isOpen())
				{
					this.skullrot -= 0.1F;
					if (this.skullrot < 0.0F) this.skullrot = 0.0F;
				}
			}

			if (isOpen()) this.skullrot += 0.035F;
			if (this.skullrot > (isOpen() ? 0.5F : 0.2F)) this.skullrot = (isOpen() ? 0.5F : 0.2F);
		}
		else if ((getHealth() < getMaxHealth()) && (this.ticksExisted % 50 == 0))
		{
			heal(1.0F);
		}
	}

	public boolean interact(EntityPlayer player)
	{
		ItemStack stack = player.inventory.getCurrentItem();
		if(stack != null)
		{
			if(stack.getItem() instanceof ItemFood && this.getHealth() < this.getMaxHealth())
			{
				ItemFood itemfood = (ItemFood)stack.getItem();
				stack.stackSize -= 1;
				heal(itemfood.func_150905_g(stack));
				
				if(this.getHealth() == this.getMaxHealth())
					this.worldObj.playSoundAtEntity(this, "random.burp", 0.5F, this.worldObj.rand.nextFloat() * 0.5F + 0.5F);
				else
					this.worldObj.playSoundAtEntity(this, "random.eat", 0.5F, this.worldObj.rand.nextFloat() * 0.5F + 0.5F);
				
				this.worldObj.setEntityState(this, (byte)18);
				this.skullrot = 0.15F;
				
				if(stack.stackSize <= 0)
					player.inventory.setInventorySlotContents(player.inventory.currentItem, null);

				return true;
			}
			else if(this.isOwner(player) && stack.getItem() == ConfigItems.itemGolemBell && !this.isDead)
			{
				if(Platform.isClient())
				{
					this.spawnExplosionParticle();
					return true;
				}
				else if(Platform.isServer())
				{
					ItemStack drop = new ItemStack(ItemsMN.evilTrunkSpawner, 1, this.getTrunkType());
					
					if(player.isSneaking())
						drop.setTagInfo("inventory", this.inventory.writeToNBT(new NBTTagList()));
					else
						this.inventory.dropAllItems();
					
					this.entityDropItem(drop, 0.5F);
					this.worldObj.playSoundAtEntity(this, "thaumcraft:zap", 0.5F, 1.0F);
					this.setDead();
					return true;
				}
			}
		}

		if(Platform.isServer() && this.isOwner(player) && !player.isSneaking())
		{
			player.openGui(MagiaNaturalis.instance, 3, this.worldObj, this.getEntityId(), 0, 0); 
			return true;
		}

		return false;
	}

	protected String getHurtSound()
	{
		return Blocks.log2.stepSound.getStepResourcePath();
	}

	protected String getDeathSound()
	{
		return "random.break";
	}

	public void setTrunkType(byte id)
	{
		this.dataWatcher.updateObject(14, Byte.valueOf(id));
	}

	public byte getTrunkType()
	{
		return this.dataWatcher.getWatchableObjectByte(14);
	}

	public boolean isOpen()
	{
		return this.dataWatcher.getWatchableObjectByte(15) == 1;
	}

	public void setOpen(boolean par1)
	{
		this.dataWatcher.updateObject(15, Byte.valueOf((byte)(par1 ? 1 : 0)));
	}

	public void setUpgrade(byte i)
	{
		this.dataWatcher.updateObject(18, Byte.valueOf(i));
	}

	public byte getUpgrade()
	{
		return this.dataWatcher.getWatchableObjectByte(18);
	}

	void showHeartsOrSmokeFX(boolean flag)
	{
		String s = "heart";
		int amount = 1;
		if(!flag)
		{
			s = "explode";
			amount = 7;
		}
		for(int i = 0; i < amount; i++)
		{
			double d = this.rand.nextGaussian() * 0.02D;
			double d1 = this.rand.nextGaussian() * 0.02D;
			double d2 = this.rand.nextGaussian() * 0.02D;
			this.worldObj.spawnParticle(s, this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.5D + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, d, d1, d2);
		}
	}

	@SideOnly(Side.CLIENT)
	public void handleHealthUpdate(byte par1)
	{
		if (par1 == 17)
		{
			this.skullrot = 0.15F;
		}
		else if (par1 == 18)
		{
			this.skullrot = 0.15F;
			showHeartsOrSmokeFX(true);
		}
		else
		{
			super.handleHealthUpdate(par1);
		}
	}
}
