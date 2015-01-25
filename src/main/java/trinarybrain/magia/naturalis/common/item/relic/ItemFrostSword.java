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
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
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

		return super.hitEntity(stack, target, attacker);
	}

	public void onUpdate(ItemStack stack, World world, Entity entity, int tick, boolean flag)
	{
		if(entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;
			PotionEffect haste = player.getActivePotionEffect(Potion.digSpeed);
			float check = haste == null ? 0.16666667F : haste.getAmplifier() == 1 ? 0.5F : 0.4F;

			if(player.getCurrentEquippedItem() == stack && !player.isSneaking() && player.swingProgress == check /* && Platform.isServer() && world.rand.nextInt(2) == 0*/)
			{
//				this.doIceBurst(player, world);
				stack.damageItem(2, player);
//				world.playSoundAtEntity(player, Blocks.ice.stepSound.getBreakSound(), 0.3F, 1.2F / (world.rand.nextFloat() * 0.2F + 0.9F));
			}
		}
	}

	public void doIceBurst(EntityLivingBase entity, World world)
	{
		int frosty = 2;
		int type = 2;
		int potency = 0;
		EntityFrostShard shard = null;

		if(type == 2) //scatter-shot
		{
			for (int a = 0; a < 5 + potency * 2; a++)
			{
				shard = new EntityFrostShard(world, entity, 8.0F);
				shard.setDamage(1.0F);
				shard.fragile = true;
				shard.setFrosty(frosty);
				world.spawnEntityInWorld(shard);
			}
		}
		else if (type == 1) //ice-boulder
		{
			shard = new EntityFrostShard(world, entity, 1.0F);
			shard.setDamage(4 + potency * 2);
			shard.bounce = 0.8D;
			shard.bounceLimit = 6;
			shard.setFrosty(frosty);
			world.spawnEntityInWorld(shard);
		}
		else
		{
			shard = new EntityFrostShard(world, entity, 1.0F);
			shard.setDamage((float)(3.0D + potency * 1.5D));
			shard.setFrosty(frosty);
			world.spawnEntityInWorld(shard);
		}
	}
}
