package com.trinarybrain.magianaturalis.common.item.artifact;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.IShearable;
import com.trinarybrain.magianaturalis.common.item.BaseItem;
import com.trinarybrain.magianaturalis.common.util.BlockUtil;
import com.trinarybrain.magianaturalis.common.util.Platform;
import com.trinarybrain.magianaturalis.common.util.WorldCoord;
import com.trinarybrain.magianaturalis.common.util.WorldUtil;

import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSickle extends BaseItem
{
	protected ToolMaterial theToolMaterial;
	protected float efficiencyOnProperMaterial = 8.0F;
	private float damageVsEntity = 3.0F;
	protected int abundanceLevel = 0;
	protected int areaSize = 0;
	protected int colorLoot = 3;
	protected boolean collectLoot = false;
	private static final Block[] isEffective = {Blocks.web, Blocks.tallgrass, Blocks.vine, Blocks.tripwire, Blocks.redstone_wire};

	public ItemSickle(ToolMaterial material)
	{
		super();
		theToolMaterial = material;
		efficiencyOnProperMaterial += material.getEfficiencyOnProperMaterial();
		maxStackSize = 1;
		setMaxDamage(material.getMaxUses());
		damageVsEntity += material.getDamageVsEntity();
	}

	// Can Harvest Block in Adventure Mode?
	public boolean func_150897_b(Block block)
	{
		return block == Blocks.web || block == Blocks.redstone_wire || block == Blocks.tripwire;
	}

	// Get Dig Speed
	public float func_150893_a(ItemStack stack, Block block)
	{
		return block != Blocks.web && block.getMaterial() != Material.leaves && !(block instanceof IPlantable) ? (block == Blocks.wool ? efficiencyOnProperMaterial - 10.0F : 1.0F) : efficiencyOnProperMaterial;
	}

	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity)
	{
		if(Platform.isClient()) return false;

		if(entity instanceof IShearable)
		{
			IShearable target = (IShearable)entity;
			if(target.isShearable(stack, entity.worldObj, (int)entity.posX, (int)entity.posY, (int)entity.posZ))
			{
				ArrayList<ItemStack> drops = target.onSheared(stack, entity.worldObj, (int)entity.posX, (int)entity.posY, (int)entity.posZ, EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, stack));

				for(int i = 0; i < abundanceLevel; i++)
					drops.addAll(target.onSheared(stack, entity.worldObj, (int)entity.posX, (int)entity.posY, (int)entity.posZ, EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, stack)));

				Random rand = new Random();
				for(ItemStack drop : drops)
				{
					EntityItem ent = entity.entityDropItem(drop, 1.0F);
					ent.motionY += rand.nextFloat() * 0.05F;
					ent.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
					ent.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
				}
				stack.damageItem(1, entity);
			}
			return true;
		}
		return false;
	}

	private boolean isEffectiveVsBlock(Block block)
	{
		if(block.getMaterial() == Material.leaves || block instanceof IShearable || block instanceof IPlantable || block instanceof IGrowable && !(block instanceof BlockGrass)) return true;

		for(int index = 0; index < isEffective.length; index++)
		{
			if(isEffective[index] == block)
			{
				return true;
			}
		}

		return false;
	}

	public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase entity)
	{
		if(!isEffectiveVsBlock(block))
		{
			stack.damageItem(1, entity);
			return false;
		}
		else if(entity.isSneaking())
		{
			boolean success = false;
			if(world.canMineBlock((EntityPlayer)entity, x, y, z))
			{
				success = BlockUtil.harvestBlock(world, (EntityPlayer)entity, x, y, z, collectLoot, abundanceLevel, colorLoot);
				if(success) stack.damageItem(1, entity);
			}
			return success;
		}
		else
		{
			if(Platform.isServer() && entity instanceof EntityPlayer)
			{
				List<WorldCoord> blocks = WorldUtil.plotVeinArea((EntityPlayer) entity, world, x, y, z, areaSize);
				boolean success = false;
				for(WorldCoord coord : blocks)
				{
					if(stack.getItemDamage() <= (stack.getMaxDamage() - abundanceLevel))
					{
						if(world.canMineBlock((EntityPlayer)entity, coord.x, coord.y, coord.z))
						{
							Block tempBlock = world.getBlock(coord.x, coord.y, coord.z);
							if(tempBlock.getBlockHardness(world, coord.x, coord.y, coord.z) >= 0.0F && isEffectiveVsBlock(tempBlock))
							{
								success = BlockUtil.harvestBlock(world, (EntityPlayer)entity, coord.x, coord.y, coord.z, collectLoot, abundanceLevel, colorLoot);
								if(success)
								{
									stack.damageItem(1 + abundanceLevel, entity);
								}
							}
						}
					}
					else
					{
						break;
					}

				}
				return success;
			}
		}
		return false;
	}

	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		stack.damageItem(2, attacker);
		return true;
	}

	@SideOnly(Side.CLIENT)
	public boolean isFull3D()
	{
		return true;
	}

	public String getToolMaterialName()
	{
		return theToolMaterial.toString();
	}

	public Multimap getItemAttributeModifiers()
	{
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", damageVsEntity, 0));
		return multimap;
	}
}
