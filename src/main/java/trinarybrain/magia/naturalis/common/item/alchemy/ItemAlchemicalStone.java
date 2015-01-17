package trinarybrain.magia.naturalis.common.item.alchemy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import thaumcraft.api.BlockCoordinates;
import thaumcraft.api.IArchitect;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.config.ConfigBlocks;
import trinarybrain.magia.naturalis.common.MagiaNaturalis;
import trinarybrain.magia.naturalis.common.item.BaseItem;
import trinarybrain.magia.naturalis.common.util.Platform;
import trinarybrain.magia.naturalis.common.util.ResourceUtil;
import trinarybrain.magia.naturalis.common.util.alchemy.BlockMorpher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAlchemicalStone extends BaseItem implements IArchitect
{
	IIcon[] icons = new IIcon[2];

	public ItemAlchemicalStone()
	{
		super();
		this.maxStackSize = 1;
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	@Override @SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister icon)
	{
		this.icons[0] = icon.registerIcon(ResourceUtil.PREFIX + "mutation_stone");
		this.icons[1] = icon.registerIcon(ResourceUtil.PREFIX + "quicksilver_stone");
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int damage)
	{
		return this.icons[damage];
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		super.addInformation(stack, player, list, par4);

		if(stack.getItemDamage() == 0)
			list.add(EnumChatFormatting.DARK_PURPLE + "Mold the Visual");
		else
			list.add(EnumChatFormatting.DARK_PURPLE + "Of Twisting and Molding Status Effects");
	}

	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		list.add(new ItemStack(this, 1, 0)); //Mutation Stone
		list.add(new ItemStack(this, 1, 1)); //Quicksilver Stone
	}
	
	public String getUnlocalizedName(ItemStack stack)
	{
		return new StringBuilder().append(super.getUnlocalizedName()).append(".").append(stack.getItemDamage()).toString();
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
	{
		if(Platform.isClient()) return false;

		if(stack.getItemDamage() == 0)
			return BlockMorpher.setMorphOrRotation(world, x, y, z, world.getBlock(x, y, z), world.getBlockMetadata(x, y, z), player.isSneaking());

		return false;			
	}

	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{	
		if(Platform.isServer() && stack.getItemDamage() == 1 && !player.isSneaking())
		{
			if(MagiaNaturalis.proxyTC4.playerKnowledge.hasDiscoveredAspect(player.getCommandSenderName(), Aspect.EXCHANGE))
			{
				Collection<PotionEffect> collection = player.getActivePotionEffects();
				for(PotionEffect effect : collection)
				{
					if(player.inventory.consumeInventoryItem(Items.glowstone_dust))
					{
						if(effect.getAmplifier() + 1 <= 2)
							player.addPotionEffect(new PotionEffect(effect.getPotionID(), (int) (effect.getDuration() * 0.3F), effect.getAmplifier() + 1));
						else
						{
							player.addPotionEffect(new PotionEffect(Potion.confusion.getId(), 288, 2));
							player.addPotionEffect(new PotionEffect(Potion.poison.getId(), 144, 0));
						}
					}
					else
					{
						player.addPotionEffect(new PotionEffect(Potion.confusion.getId(), 144, 1));
						break;
					}
				}
				player.inventoryContainer.detectAndSendChanges();
			}
			else
			{
				player.addPotionEffect(new PotionEffect(Potion.confusion.getId(), 144, 1));
			}
		}
		return stack;
	}

	@Override
	public ArrayList<BlockCoordinates> getArchitectBlocks(ItemStack stack, World world, int x, int y, int z, int side, EntityPlayer player)
	{
		if(stack.getItemDamage() == 0)
		{
			Block block = world.getBlock(x, y, z);
			if(block != null && block != Blocks.air)
			{
				if(this.canMorphBlock(block))
				{
					ArrayList blocks = new ArrayList();
					blocks.add(new BlockCoordinates(x, y, z));
					return blocks;
				}
			}
		}
		return null;
	}
	
	private static final Block[] canMorph = {Blocks.wool, Blocks.log, Blocks.log2, ConfigBlocks.blockMagicalLog,Blocks.stonebrick, Blocks.stained_hardened_clay, Blocks.carpet, Blocks.sandstone, Blocks.quartz_block, Blocks.cobblestone, Blocks.cobblestone_wall, Blocks.mossy_cobblestone, Blocks.dirt, Blocks.grass, Blocks.stained_glass, Blocks.stained_glass_pane};
	private boolean canMorphBlock(Block block)
	{
		if(block instanceof BlockStairs || block instanceof BlockSlab || block instanceof BlockRotatedPillar) return true;
		for(int index = 0; index < canMorph.length; index++)
		{
			if(canMorph[index] == block)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean showAxis(ItemStack stack, World world, EntityPlayer player, int side, EnumAxis axis)
	{
		return false;
	}
}
