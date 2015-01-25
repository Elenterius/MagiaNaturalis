package trinarybrain.magia.naturalis.common.item.relic;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.projectile.EntityFrostShard;
import trinarybrain.magia.naturalis.common.util.Platform;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemFrostSword extends ItemVisSword
{

	public ItemFrostSword()
	{
		super();
	}

	public EnumRarity getRarity(ItemStack itemstack)
	{
		return EnumRarity.rare;
	}

	public boolean getIsRepairable(ItemStack stack, ItemStack resource)
	{
		return resource.isItemEqual(new ItemStack(Blocks.packed_ice)) ? true : super.getIsRepairable(stack, resource);
	}

	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		if(Platform.isServer())
			if(this.itemRand.nextInt(4) == 0) target.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 200, 0));

		this.damageItem(stack, 1, attacker);
		return true;
	}

	public void onUpdate(ItemStack stack, World world, Entity entity, int tick, boolean flag)
	{
		if(entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;
			PotionEffect haste = player.getActivePotionEffect(Potion.digSpeed);
			float check = haste == null ? 0.16666667F : haste.getAmplifier() == 1 ? 0.5F : 0.4F;

			if(player.getCurrentEquippedItem() == stack && !player.isSneaking() && player.swingProgress == check)
			{
				if(Platform.isServer())
				{
					this.doIceBurst(player, world);
					this.damageItem(stack, 2, player);
					world.playSoundAtEntity(player, Blocks.ice.stepSound.getBreakSound(), 0.3F, 1.2F / (world.rand.nextFloat() * 0.2F + 0.9F));
				}
				if(Platform.isClient() && stack.getItemDamage() >= this.getMaxDamage()) player.renderBrokenItemStack(stack);
			}
		}
	}

	public void damageItem(ItemStack stack, int amount, EntityLivingBase entity)
	{
		if(!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).capabilities.isCreativeMode)
		{
			if(stack.isItemStackDamageable())
			{
				if(stack.attemptDamageItem(amount, entity.getRNG()))
				{
					entity.renderBrokenItemStack(stack);
					--stack.stackSize;

					if(entity instanceof EntityPlayer)
					{
						EntityPlayer player = (EntityPlayer) entity;
						player.addStat(StatList.objectBreakStats[Item.getIdFromItem(stack.getItem())], 1);

						if(stack.stackSize == 0)
						{
							player.destroyCurrentEquippedItem(); //Use ItemDestroyEvent!!
						}
					}

					if(stack.stackSize < 0) stack.stackSize = 0;
					this.setDamage(stack, 0);
				}
			}
		}
    }

	public void doIceBurst(EntityLivingBase entity, World world)
	{
		for (int a = 0; a < 3; a++)
		{
			EntityFrostShard shard = new EntityFrostShard(world, entity, 8.0F);
			shard.setDamage(1.0F);
			shard.bounceLimit = 1;
			shard.fragile = true;
			shard.setFrosty(2);
			world.spawnEntityInWorld(shard);
		}
	}
}
