package trinarybrain.magia.naturalis.common.item.artifact;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
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
	IIcon icon_overlay;

	public ItemBiomeSampler()
	{
		super();
		this.maxStackSize = 1;
		this.setMaxDamage(0);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		String name = NBTUtil.openNbtData(stack).getString("biomeName");
		return (name + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name")).trim();
	}

	@Override @SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister icon)
	{
		this.itemIcon = icon.registerIcon(ResourceUtil.PREFIX + "report_base");
		this.icon_overlay = icon.registerIcon(ResourceUtil.PREFIX + "report_overlay");
	}

	@Override @SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int damage, int renderPass)
	{
		return renderPass == 0 ?  this.itemIcon : this.icon_overlay;
	}

	@Override @SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int i)
	{
		int color = NBTUtil.openNbtData(stack).getInteger("color");
		color = i == 0 ? 0xFFFFFF : color;
		return color;
	}

	@Override @SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
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
				data.setString("biomeName", biome.biomeName + " ");
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
}