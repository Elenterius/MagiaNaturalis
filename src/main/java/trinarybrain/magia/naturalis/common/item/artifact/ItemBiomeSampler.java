package trinarybrain.magia.naturalis.common.item.artifact;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import trinarybrain.magia.naturalis.common.item.BaseItem;
import trinarybrain.magia.naturalis.common.tile.TileGeoMorpher;
import trinarybrain.magia.naturalis.common.util.NBTUtil;
import trinarybrain.magia.naturalis.common.util.Platform;
import trinarybrain.magia.naturalis.common.util.ResourceUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBiomeSampler extends BaseItem
{
	private byte modes;

	public ItemBiomeSampler()
	{
		super();
		this.maxStackSize = 1;
		this.setMaxDamage(0);
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		super.addInformation(stack, player, list, par4);
		String name = NBTUtil.openNbtData(stack).getString("biomeName");
		name = name.equals("") ? "None" : name;
		list.add(EnumChatFormatting.DARK_GRAY + "Sampled Biome: " + name);
	}

	@Override @SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister icon)
	{
		this.itemIcon = icon.registerIcon(ResourceUtil.PREFIX + "crystal_0");
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
	{
		if(Platform.isClient()) return false;
		
		if(player.isSneaking())
		{
			BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
			if(biome != null)
			{
				NBTTagCompound data = NBTUtil.openNbtData(stack);
				data.setString("biomeName", biome.biomeName);
				data.setInteger("biomeID", biome.biomeID);
				data.setInteger("color", biome.color);
				return true;
			}
		}
		else
		{
			TileEntity tile = world.getTileEntity(x, y, z);
			if(tile instanceof TileGeoMorpher)
			{
				TileGeoMorpher geo = (TileGeoMorpher) tile;
				geo.cachedBiome = BiomeGenBase.getBiome(NBTUtil.openNbtData(stack).getInteger("biomeID"));
				world.markBlockForUpdate(x, y, z);
				return true;
			}
		}
		
		return false;
	}

	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int i)
	{
		int color = NBTUtil.openNbtData(stack).getInteger("color");
		color = color == 0 ? 0xFFFFFF : color;
		return color;
	}
}