package com.trinarybrain.magianaturalis.common.item.focus;

import com.trinarybrain.magianaturalis.common.MagiaNaturalis;
import com.trinarybrain.magianaturalis.common.entity.EntityZombieExtended;
import com.trinarybrain.magianaturalis.common.util.Platform;
import com.trinarybrain.magianaturalis.common.util.ResourceUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.utils.EntityUtils;

public class ItemFocusRevenant extends ItemFocusBasic
{
	public ItemFocusRevenant()
	{
		super();
		setCreativeTab(MagiaNaturalis.creativeTab);
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		icon = ir.registerIcon(ResourceUtil.PREFIX +  "focus_revenant");
	}

	@Override
	public int getFocusColor(ItemStack focusstack)
	{
		return 0x385226;
	}

	@Override
	public AspectList getVisCost(ItemStack focusstack)
	{
		return new AspectList().add(Aspect.EARTH, 450).add(Aspect.ENTROPY, 350).add(Aspect.WATER, 200);
	}

	@Override
	public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack focusstack, int rank)
	{
		return new FocusUpgradeType[] {FocusUpgradeType.potency, FocusUpgradeType.frugal};
	}

	public ItemStack onFocusRightClick(ItemStack wandstack, World world, EntityPlayer player, MovingObjectPosition movingobjectposition)
	{
		player.swingItem();
		if(Platform.isClient()) return wandstack;

		Entity pointedEntity = EntityUtils.getPointedEntity(player.worldObj, player, 32.0D, EntityZombieExtended.class);
		if(pointedEntity != null && pointedEntity instanceof EntityLivingBase)
		{
			double px = player.posX;
			double py = player.boundingBox.minY;
			double pz = player.posZ;
			px -= MathHelper.cos(player.rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
			pz -= MathHelper.sin(player.rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
			Vec3 vec3d = player.getLook(1.0F);
			px += vec3d.xCoord * 0.5D;
			pz += vec3d.zCoord * 0.5D;

			if (!world.isRemote)
			{
				if(pointedEntity instanceof EntityPlayer && !MinecraftServer.getServer().isPVPEnabled()) return wandstack;
				EntityZombieExtended zombie = new EntityZombieExtended(world);
				zombie.setOwner(player.getCommandSenderName());
				zombie.setChild(true);

				if(world.rand.nextBoolean())
					zombie.setVillager(true);

				zombie.setLocationAndAngles(px, py + 0.1, pz, player.rotationYaw, 0.0F);
				zombie.setExperienceValue(0);

				zombie.setTarget(pointedEntity);
				zombie.setAttackTarget((EntityLivingBase) pointedEntity);
//				player.setRevengeTarget((EntityLivingBase) pointedEntity); TODO: Use this for Bone Flute

				ItemWandCasting wand = (ItemWandCasting) wandstack.getItem();
				int potency =  EnchantmentHelper.getEnchantmentLevel(ThaumcraftApi.enchantPotency, wand.getFocusItem(wandstack));
				zombie.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D + 0.5 * potency);

				if(ThaumcraftApiHelper.consumeVisFromWand(wandstack, player, getVisCost(wand.getFocusItem(wandstack)), true, false) && world.spawnEntityInWorld(zombie))
				{
					world.playAuxSFXAtEntity(null, 1016, (int)px, (int)py, (int)pz, 0);
					world.playSoundAtEntity(zombie, "thaumcraft:ice", 0.2F, 0.95F + world.rand.nextFloat() * 0.1F);
				}else
				{
					world.playSoundAtEntity(player, "thaumcraft:wandfail", 0.2F, 0.8F + world.rand.nextFloat() * 0.1F);
				}
			}

			player.swingItem();
		}
		return wandstack;
	}
}