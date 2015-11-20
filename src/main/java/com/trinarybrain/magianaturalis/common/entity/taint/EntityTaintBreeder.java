package com.trinarybrain.magianaturalis.common.entity.taint;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import thaumcraft.api.entities.ITaintedMob;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.monster.EntityTaintSpider;
import com.trinarybrain.magianaturalis.common.util.Platform;

public class EntityTaintBreeder extends EntitySpider implements ITaintedMob
{

	private byte breedTime = 8;
	private byte broodTimer;

	public EntityTaintBreeder(World world)
	{
		super(world);
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(42.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.600000011920929D);
	}

	public boolean getCanSpawnHere()
	{
		BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ));
		if(biome != null) return biome.biomeID == Config.biomeTaintID && super.getCanSpawnHere();
		return false;
	}

	protected Entity findPlayerToAttack()
	{
		return this.worldObj.getClosestVulnerablePlayerToEntity(this, 16.0D);
	}

	public boolean isPotionApplicable(PotionEffect potionEffect)
	{
		return potionEffect.getPotionID() == Config.potionTaintPoisonID ? false : super.isPotionApplicable(potionEffect);
	}

	public void onLivingUpdate()
	{
		if(Platform.isServer())
			if(this.ticksExisted % 20 == 0)
				if(this.getHealth() < this.getMaxHealth() * 0.8F)
					if(broodTimer++ % breedTime == 0 && this.findPlayerToAttack() != null)
						for(int i = this.worldObj.rand.nextInt(2); i >= 0; i--)
						{
							EntityTaintSpider breedling = new EntityTaintSpider(this.worldObj);
							breedling.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
							int level = this.worldObj.difficultySetting == EnumDifficulty.HARD ? 2 : 3;
							breedling.addPotionEffect(new PotionEffect(Potion.jump.id, 144, level));
							breedling.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 144, level - 2));
							this.worldObj.spawnEntityInWorld(breedling);
						}

		super.onLivingUpdate();
	}

	protected Item getDropItem()
	{
		return ConfigItems.itemResource;
	}

	protected void dropFewItems(boolean bool, int chance)
	{
		if(this.worldObj.rand.nextInt(6) == 0)
			if(this.worldObj.rand.nextBoolean())
				entityDropItem(new ItemStack(ConfigItems.itemResource, 1, 11), this.height / 2.0F);
			else
				entityDropItem(new ItemStack(ConfigItems.itemResource, 1, 12), this.height / 2.0F);
	}
}
