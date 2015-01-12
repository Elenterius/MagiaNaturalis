package trinarybrain.magia.naturalis.common.item.artifact;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.IShearable;
import thaumcraft.common.blocks.BlockMagicalLeaves;
import thaumcraft.common.lib.utils.BlockUtils;
import trinarybrain.magia.naturalis.common.item.BaseItem;
import trinarybrain.magia.naturalis.common.util.Platform;
import trinarybrain.magia.naturalis.common.util.WorldUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSickle extends BaseItem
{
	protected ToolMaterial theToolMaterial;
	protected float efficiencyOnProperMaterial = 8.0F;
	private float damageVsEntity = 3.0F;
	protected int abundanceLevel = 0;
	protected int areaSize = 18;
	private static final Block[] isEffective = {Blocks.web, Blocks.tallgrass, Blocks.vine, Blocks.tripwire, Blocks.redstone_wire};

	public ItemSickle(ToolMaterial material)
	{
		super();
		this.theToolMaterial = material;
		this.efficiencyOnProperMaterial += material.getEfficiencyOnProperMaterial();
		this.maxStackSize = 1;
		this.setMaxDamage(material.getMaxUses());
		this.damageVsEntity += material.getDamageVsEntity();
	}

	// Can Harvest Block in Adventure Mode?
	public boolean func_150897_b(Block block)
	{
		return block == Blocks.web || block == Blocks.redstone_wire || block == Blocks.tripwire;
	}

	// Get Dig Speed
	public float func_150893_a(ItemStack stack, Block block)
	{
		return block != Blocks.web && block.getMaterial() != Material.leaves && !(block instanceof IPlantable) ? (block == Blocks.wool ? this.efficiencyOnProperMaterial - 10.0F : 1.0F) : this.efficiencyOnProperMaterial;
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

				for(int i = 0; i < this.abundanceLevel; i++)
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

	int side = 0;

	public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player)
	{
		if(Platform.isClient()) return false;

		Block block = player.worldObj.getBlock(x, y, z);
		if(block != null && block instanceof BlockMagicalLeaves)
		{
			for(int i = 0; i < this.abundanceLevel; i++)
				block.dropBlockAsItemWithChance(player.worldObj, x, y, z, player.worldObj.getBlockMetadata(x, y, z), 1.0F,  3);
		}

		MovingObjectPosition movingobjectposition = BlockUtils.getTargetBlock(player.worldObj, player, true);
		if(movingobjectposition != null && movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
		{
			this.side = movingobjectposition.sideHit;
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
		if(entity.isSneaking() || !this.isEffectiveVsBlock(block))
		{
			return super.onBlockDestroyed(stack, world, block, x, y, z, entity);
		}
		else
		{
			if(Platform.isServer() && entity instanceof EntityPlayer)
			{
				List list = WorldUtils.plotVeinArea((EntityPlayer) entity, world, x, y, z, this.areaSize);
				
				for(int dimZ = -this.areaSize; dimZ <= this.areaSize; dimZ++)
				{
					int xOffset = 0;
					int yOffset = 0;
					int zOffset = 0;

					if(world.canMineBlock((EntityPlayer)entity, x + xOffset, y + yOffset, z + zOffset))
					{
						Block tempBlock = world.getBlock(x + xOffset, y + yOffset, z + zOffset);

						if(tempBlock.getBlockHardness(world, x + xOffset, y + yOffset, z + zOffset) >= 0.0F && this.isEffectiveVsBlock(tempBlock) && meta == world.getBlockMetadata(x + xOffset, y + yOffset, z + zOffset))
						{
							stack.damageItem(1, entity);
							BlockUtils.harvestBlock(world, (EntityPlayer)entity, x + xOffset, y + yOffset, z + zOffset, true, 3);
						}
					}
				}
			}
		}

		return true;
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
		return this.theToolMaterial.toString();
	}

	public Multimap getItemAttributeModifiers()
	{
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", this.damageVsEntity, 0));
		return multimap;
	}
}
