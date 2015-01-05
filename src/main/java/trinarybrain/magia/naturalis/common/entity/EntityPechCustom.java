package trinarybrain.magia.naturalis.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import thaumcraft.api.ItemApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IEssentiaContainerItem;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.monster.EntityPech;
import trinarybrain.magia.naturalis.common.entity.ai.AIFollowCreature;
import trinarybrain.magia.naturalis.common.entity.ai.AIInteractPlayer;
import trinarybrain.magia.naturalis.common.entity.ai.AILookAtPlayer;

public class EntityPechCustom extends EntityMob
{
	public boolean isInteracting = false;
	private double damageBonus = 2.5D;
	public String name[] = new String[] {"gambler", "verdant", "alchemist"};
	
	public EntityPechCustom(World world)
	{
		this(world, 0);
	}
	
	public EntityPechCustom(World world, int type)
	{
		super(world);
		
		this.setPechType(type);
		
		this.setSize(0.6F, 1.8F);
		this.getNavigator().setBreakDoors(true);
	    getNavigator().setAvoidsWater(true);
	    this.tasks.addTask(0, new EntityAISwimming(this));

	    this.tasks.addTask(1, new AIInteractPlayer(this));
	    this.tasks.addTask(1, new AILookAtPlayer(this));
//	    this.tasks.addTask(3, new AIPechItemEntityGoto(this));
        
        this.tasks.addTask(2, new EntityAIMoveIndoors(this));
        this.tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
        this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.5D));
        this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
        
        this.tasks.addTask(7, new AIFollowCreature(this, EntityAgeable.class));
        
        this.tasks.addTask(9, new EntityAIWander(this, 0.6D));
	    this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
	    this.tasks.addTask(10, new EntityAIWatchClosest2(this, EntityPechCustom.class, 5.0F, 0.02F));
	    this.tasks.addTask(11, new EntityAIWatchClosest(this, EntityPech.class, 5.0F, 0.02F));
	    this.tasks.addTask(12, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
	    this.tasks.addTask(13, new EntityAILookIdle(this));

	    this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
	}

	public boolean isAIEnabled()
	{
		return true;
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(13, Integer.valueOf(0));
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D  + this.damageBonus);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
	}

	public String getEntityName()
	{
		if (hasCustomNameTag())
		{
			return getCustomNameTag();
		}
		return StatCollector.translateToLocal("entity.pechCustom." + name[getPechType()] + ".name");
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
	{
		par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);
		this.setPechType(this.rand.nextInt(3));
		return par1EntityLivingData;
	}

	public boolean getCanSpawnHere()
	{
		BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ));
	    boolean magicBiome = false;
	    if (biome != null)
	    {
	      magicBiome = (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.MAGICAL)) && (biome.biomeID != Config.biomeTaintID);
	    }
	    return (magicBiome) && (super.getCanSpawnHere());
	}

	public boolean interact(EntityPlayer player)
	{
		//Add Condition here + link to gui
		return false;
	}

	public void onLivingUpdate()
	{
		super.onLivingUpdate();
	}

	protected boolean canDespawn()
	{
		if(hasCustomNameTag())
		{
			return false;
		}
		return true;
	}
	
	public void setPechType(int id)
    {
        this.dataWatcher.updateObject(13, Integer.valueOf(id));
    }

    public int getPechType()
    {
        return this.dataWatcher.getWatchableObjectInt(13);
    }

	protected void dropFewItems(boolean flag, int i)
	{
		Aspect[] aspects = (Aspect[])Aspect.aspects.values().toArray(new Aspect[0]);
		for (int j = 0; j < 1 + i; j++)
			if (this.rand.nextBoolean())
			{
				ItemStack stack = new ItemStack(ConfigItems.itemManaBean);
				((IEssentiaContainerItem)stack.getItem()).setAspects(stack, new AspectList().add(aspects[this.rand.nextInt(aspects.length)], 1));
				entityDropItem(stack, 1.5F);
			}
	}

	protected void dropRareDrop(int par1)
	{
		// Rare Drop Knowledge Framgment 
		//TODO: Add Loaded Dice for Gambling
		ItemStack stack = ItemApi.getItem("itemResource", 9);
		entityDropItem(stack, 1.5F);
	}
	
	public int getTalkInterval()
	{
		return 120;
	}

	protected float getSoundVolume()
	{
		return 0.4F;
	}

	protected String getLivingSound()
	{
		return "thaumcraft:pech_idle";
	}

	protected String getHurtSound()
	{
		return "thaumcraft:pech_hit";
	}

	protected String getDeathSound()
	{
		return "thaumcraft:pech_death";
	}

	public Entity getInteractionTarget()
	{
		return null;
	}
}
