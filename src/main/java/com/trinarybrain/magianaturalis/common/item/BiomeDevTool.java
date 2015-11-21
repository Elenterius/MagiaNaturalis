package com.trinarybrain.magianaturalis.common.item;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager.BiomeType;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.lib.world.biomes.BiomeHandler;
import com.trinarybrain.magianaturalis.common.core.Log;
import com.trinarybrain.magianaturalis.common.util.Platform;
import com.trinarybrain.magianaturalis.common.util.ResourceUtil;
import cpw.mods.fml.relauncher.FMLInjectionData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BiomeDevTool extends BaseItem
{
	public BiomeDevTool()
	{
		super();
		maxStackSize = 1;
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		super.addInformation(stack, player, list, par4);
		list.add(EnumChatFormatting.DARK_PURPLE + "Last Biome: " + stack.getItemDamage());
	}

	@Override @SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister icon)
	{
		itemIcon = icon.registerIcon(ResourceUtil.PREFIX + "book_magia_natura");
	}

	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if(Platform.isClient()) return stack;
		return stack;
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
	{
		if(Platform.isClient()) return false;
		boolean ignore = false;

		if(ignore)
		{
			Log.logger.info(world.getBlock(x, y, z));
			Log.logger.info(world.getBlockMetadata(x, y, z));
		}
		else
		{
			String divider = "----------------------------------------------------";

			File mcDir = (File) FMLInjectionData.data()[6];
			File modsDir = new File(mcDir, "magia_naturalis");
			if (!modsDir.exists()) modsDir.mkdirs();

			File mnDir = new File(mcDir, "magia_naturalis/" + "Biome_Types-Aspects.txt");

			try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mnDir))))
			{
				String aspectName = "";
				String types = "";
				int aura = 0;

				Aspect aspect;
				for(BiomeDictionary.Type type : BiomeDictionary.Type.values())
				{
					types = "[" + type.toString() + "]";
					aspect = (Aspect)((List)BiomeHandler.biomeInfo.get(type)).get(1);
					aura = (int)((List)BiomeHandler.biomeInfo.get(type)).get(0);
					aura = Math.round(aura * 2F / 100F);

					if(aspect != null)
					{
						aspectName = "[" + aspect.getName() + "]";
					}
					else
					{
						aspectName = type == type.MAGICAL ? "[" + Aspect.MAGIC.getName() + "]" : "[NULL]";
					}

					String info = String.format("%-16s :\t%dx\t%s%n", types, aura, aspectName);
					writer.write(info);
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}

			mnDir = new File(mcDir, "magia_naturalis/" + "Biome_Composition.txt");

			try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mnDir))))
			{
				BiomeGenBase[] biomes = BiomeGenBase.getBiomeGenArray();
				for(BiomeGenBase biome : biomes)
				{
					if(biome != null)
					{
						String aspectName = "";
						String types = "";
						int aura = 0;
						String aspectCost = "";

						Aspect aspect;
						for(BiomeDictionary.Type type : BiomeDictionary.getTypesForBiome(biome))
						{
							types = "[" + type.toString() + "]";
							aspect = (Aspect)((List)BiomeHandler.biomeInfo.get(type)).get(1);
							aura = (int)((List)BiomeHandler.biomeInfo.get(type)).get(0);
							aura = Math.round(aura * 2F / 100F);

							if(aspect != null)
							{
								aspectName = "[" + aspect.getName() + "]";
							}
							else
							{
								aspectName = type == type.MAGICAL ? "[" + Aspect.MAGIC.getName() + "]" : "[NULL]";
							}

							aspectCost += String.format("%-16s :\t%dx\t%s%n", types, aura, aspectName);
						}

						String info = String.format(divider + "%nBiome: %s%n%s", biome.biomeName, aspectCost);
						writer.write(info);
					}
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		return false;
	}
}