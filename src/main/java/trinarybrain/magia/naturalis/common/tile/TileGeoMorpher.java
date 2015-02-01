package trinarybrain.magia.naturalis.common.tile;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.lib.world.biomes.BiomeHandler;
import trinarybrain.magia.naturalis.common.core.Log;
import trinarybrain.magia.naturalis.common.util.Platform;
import trinarybrain.magia.naturalis.common.util.WorldUtil;

public class TileGeoMorpher extends TileThaumcraft
{
	public int ticks = 0;
	private int morphX = 0;
	private int morphZ = 0;
	private BiomeGenBase lastBiome = null;
	private AspectList morphCost = null;

	@Override
	public void updateEntity()
	{
		boolean update = false;
		if(Platform.isServer())
		{
			if(++this.ticks >= 30) {update = this.handleBiomeMorphing(32);}
			if(update) this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
	}

	public boolean handleBiomeMorphing(int radius)
	{
		ticks = 0;
		BiomeGenBase newBiome = BiomeGenBase.jungle;

		if(this.morphZ < radius * 2)
		{
			if(this.morphX < radius * 2)
			{
				this.morphX++;
			}
			else
			{
				this.morphX = 0;
				this.morphZ++;
			}

			int posX = this.xCoord - radius + this.morphX;
			int posZ = this.zCoord - radius + this.morphZ;
			BiomeGenBase oldBiome = this.worldObj.getBiomeGenForCoords(posX, posZ);
			
			if(newBiome != oldBiome && (Math.abs(morphX-radius) + Math.abs(morphZ-radius)) <= radius)
			{
				if(this.lastBiome != oldBiome) //prevent recalculation of Cost every Cycle
				{
					this.lastBiome = oldBiome;
					this.morphCost = this.calculateMorphCost(oldBiome, newBiome);
					Log.logger.info("Recalculated Morph Cost!");
				}
		
				Log.logger.info(this.morphCost);
				
				WorldUtil.setBiomeAt(this.worldObj, posX, posZ, newBiome);
				this.worldObj.getChunkFromBlockCoords(this.xCoord, this.zCoord).setChunkModified();
				Minecraft.getMinecraft().theWorld.markBlockForUpdate(posX, this.worldObj.getTopSolidOrLiquidBlock(posX, posZ), posZ); //Will only work in SSP
				return true;
			}
			ticks = 25;
		}
		else
		{
			this.morphZ = 0;
			ticks = 20;
		}
		return false;
	}
	
	public AspectList calculateMorphCost(BiomeGenBase oldBiome, BiomeGenBase newBiome)
	{  	    
		BiomeDictionary.Type[] oldTypes = BiomeDictionary.getTypesForBiome(oldBiome);
		BiomeDictionary.Type[] newTypes = BiomeDictionary.getTypesForBiome(newBiome);
		
		AspectList aspectCost = new AspectList();
		
		// Remove Equal Biome Types
	    for(int i = 0; i < oldTypes.length; i++)
	    {
	    	for(int j = 0; j < newTypes.length; j++)
	    	{
	    		if(oldTypes[i] == newTypes[j])
	    		{
	    			oldTypes[i] = newTypes[j] = null;
	    			break;
	    		}
	    	}
	    }
	    
	    // Add AspectCost for Removing old Biome Types
	    for(int i = 0; i < oldTypes.length; i++)
	    {
	    	if(oldTypes[i] != null)
	    	{
	    		if(oldTypes[i] != Type.MAGICAL) // Because Thaumcraft adds null aspect for magical biome type
	    		{
	    			Aspect aspect = (Aspect)((List)BiomeHandler.biomeInfo.get(oldTypes[i])).get(1);
	    			if(aspect != null)
	    			{
	    				int aura = (int)((List)BiomeHandler.biomeInfo.get(oldTypes[i])).get(0);
						aspectCost.add(aspect, aura);
	    			}
	    		}
	    		else
	    		{
	    			aspectCost.add(Aspect.MAGIC, 100);
	    		}
	    	}
	    }
	    
	    // Add MorphCost for adding Biome Types & reduce by difference of aspects that will remain in biome
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
	    				
	    				int currentCost = aspectCost.getAmount(aspect);
	    				if(currentCost > 0)
	    				{
	    					aspectCost.reduce(aspect, Math.abs(currentCost - aura));
	    				}
	    				else
	    				{
	    					aspectCost.add(aspect, aura);
	    				}
	    			}
	    		}
	    		else
	    		{
	    			int currentCost = aspectCost.getAmount(Aspect.MAGIC);
    				if(currentCost > 0)
    				{
    					aspectCost.reduce(Aspect.MAGIC, Math.abs(currentCost - 100));
    				}
    				else
    				{
    					aspectCost.add(Aspect.MAGIC, 100);
    				}
	    		}
	    	}
	    }
	    
	    return aspectCost;
	}

	@Override
	public void readCustomNBT(NBTTagCompound data)
	{
		this.morphX = data.getByte("morphX");
		this.morphZ = data.getByte("morphZ");
	}

	@Override
	public void writeCustomNBT(NBTTagCompound data)
	{
		data.setByte("morphX", (byte) this.morphX);
		data.setByte("morphZ", (byte) this.morphZ);
	}
}
