package trinarybrain.magia.naturalis.common.item.artifact;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.text.WordUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.util.Constants.NBT;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.lib.world.biomes.BiomeHandler;
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
		setMaxStackSize(1);
		setMaxDamage(0);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		String name = NBTUtil.openNbtData(stack).getString("biomeName");
		return (name + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name")).trim();
	}

	@Override @SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		super.addInformation(stack, player, list, par4);
		if(Minecraft.getMinecraft().currentScreen.isCtrlKeyDown())
		{
			String[] aspects = getAspects(stack);
			if(aspects == null) return;

			for(int i = 0; i < aspects.length; i++)
				if(aspects[i] != null)
					list.add(aspects[i]);
		}
		else
		{
			list.add(EnumChatFormatting.DARK_GRAY + Platform.translate("hint.magianaturalis:ctrl"));
		}
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

				BiomeDictionary.Type[] newTypes = BiomeDictionary.getTypesForBiome(biome);
				NBTTagList nbttaglist = new NBTTagList();
				NBTTagCompound tempData = new NBTTagCompound();

				for(int i = 0; i < newTypes.length; i++)
				{
					if(newTypes[i] != null)
					{
						if(newTypes[i] != Type.MAGICAL) // Because Thaumcraft adds null aspect for magical biome type
						{
							Aspect aspect = (Aspect)((List)BiomeHandler.biomeInfo.get(newTypes[i])).get(1);
							if(aspect != null)
							{
								int aura = (int)((List)BiomeHandler.biomeInfo.get(newTypes[i])).get(0);
								tempData.setShort(aspect.getTag(), (short) Math.round(aura * 2F / 100F));
								nbttaglist.appendTag(tempData);
							}
						}
						else
						{
							tempData.setShort(Aspect.MAGIC.getTag(), (short) 2);
							nbttaglist.appendTag(tempData);
						}
					}
				}

				data.setTag("aspects", nbttaglist);
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

	public String[] getAspects(ItemStack stack)
	{
		if(stack != null)
		{
			NBTTagCompound data = NBTUtil.openNbtData(stack);
			if(!data.hasKey("aspects")) return null;
			NBTTagList nbttaglist = data.getTagList("aspects", NBT.TAG_COMPOUND);

			String[] aspects = new String[nbttaglist.tagCount()];
			for(int i = 0; i < nbttaglist.tagCount(); ++i)
			{
				NBTTagCompound tempData = nbttaglist.getCompoundTagAt(i);
				Set keys = tempData.func_150296_c();
				int j = 0;
				for(Object ob : keys)
				{
					if(ob != null && ob instanceof String)
					{
						if(j >= nbttaglist.tagCount()) break;
						String aspectTag = (String) ob;

						String color = Aspect.getAspect(aspectTag).getChatcolor();
						if(color == null) color = "5";
						aspects[j++] = String.format("%dx %s%s", tempData.getShort(aspectTag), String.valueOf('\u00a7') + color, WordUtils.capitalizeFully(aspectTag));
					}
				}
			}
			return aspects;
		}
		return null;
	}
}