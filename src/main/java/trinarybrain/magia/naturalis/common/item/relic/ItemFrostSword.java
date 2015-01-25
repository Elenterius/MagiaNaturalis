package trinarybrain.magia.naturalis.common.item.relic;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumcraft.common.entities.projectile.EntityFrostShard;
import trinarybrain.magia.naturalis.common.util.Platform;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemFrostSword extends ItemVisSword
{

	public ItemFrostSword()
	{
		super();
		this.setMaxDamage(400);
	}

	@SideOnly(Side.CLIENT)
	public boolean isFull3D()
	{
		return true;
	}

	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		if(Platform.isServer())
		{
			if(this.itemRand.nextInt(6) == 0) target.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 200, 0));
			stack.damageItem(2, attacker);
		}
		return true;
	}

	public boolean onEntitySwing(EntityLivingBase entity, ItemStack stack)
	{
		if(Platform.isServer() && entity instanceof EntityPlayer && entity.worldObj != null && this.itemRand.nextInt(4) == 0)
		{
			EntityPlayer player = (EntityPlayer) entity;
			World world = player.worldObj;
			
			int frosty = 2;
			EntityFrostShard shard = null;
			int type = 2;
			int potency = 0;

			if(type == 2) //scatter-shot
			{
				for (int a = 0; a < 5 + potency * 2; a++)
				{
					shard = new EntityFrostShard(world, player, 8.0F);
					shard.setDamage(1.0F);
					shard.fragile = true;
					shard.setFrosty(frosty);
					world.spawnEntityInWorld(shard);
				}
				stack.damageItem((int) (5 + potency * 2), entity);
			}
			else if (type == 1) //ice-boulder
			{
				shard = new EntityFrostShard(world, player, 1.0F);
				shard.setDamage(4 + potency * 2);
				shard.bounce = 0.8D;
				shard.bounceLimit = 6;
				shard.setFrosty(frosty);
				world.spawnEntityInWorld(shard);
				stack.damageItem((int) (4 + potency * 2), entity);
			}
			else
			{
				shard = new EntityFrostShard(world, player, 1.0F);
				shard.setDamage((float)(3.0D + potency * 1.5D));
				shard.setFrosty(frosty);
				world.spawnEntityInWorld(shard);
				stack.damageItem((int) (3 + potency * 1.5D), entity);
			}

			world.playSoundAtEntity(shard, "thaumcraft:ice", 0.25F, 1.0F + world.rand.nextFloat() * 0.1F);
		}
		return false;
	}
}
