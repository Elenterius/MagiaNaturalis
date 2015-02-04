package trinarybrain.magia.naturalis.common.tile;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.visnet.VisNetHandler;
import thaumcraft.common.lib.events.EssentiaHandler;
import thaumcraft.common.lib.world.biomes.BiomeHandler;
import trinarybrain.magia.naturalis.common.core.Log;
import trinarybrain.magia.naturalis.common.util.Platform;
import trinarybrain.magia.naturalis.common.util.WorldUtil;

public class TileGeoMorpher extends TileThaumcraft implements IAspectContainer
{
	public int ticks = 0;
	int morphX = 0;
	int morphZ = 0;
	BiomeGenBase lastBiome = null;
	AspectList morphCost = null;
	AspectList realCost = new AspectList();	
	boolean idle = false;

	@Override
	public void updateEntity()
	{
		boolean update = false;
		if(Platform.isServer())
		{
			if(++this.ticks % 5 == 0)
			{
				 if(!idle) update = this.handleBiomeMorphing(8, BiomeGenBase.jungleHills);
				 else Log.logger.info("IDLE");				 
			}
			
			if(update)
			{
				this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
				this.markDirty();
			}
		}
	}

	public boolean handleBiomeMorphing(int radius, BiomeGenBase newBiome)
	{
		boolean update = false;
		boolean isComplete = false;
		if(this.realCost.visSize() > 0)
		{
			for(Aspect aspect : this.realCost.getAspectsSortedAmount())
			{
				if(this.realCost.getAmount(aspect) > 0)
				{
					int drain = VisNetHandler.drainVis(this.worldObj, this.xCoord, this.yCoord, this.zCoord, aspect, this.realCost.getAmount(aspect));

					if(drain > 0)
					{
						this.realCost.reduce(aspect, drain);
						update = true;
					}
					else if(aspect != Aspect.AIR && aspect != Aspect.FIRE && aspect != Aspect.WATER && aspect != Aspect.EARTH && aspect != Aspect.ORDER && aspect != Aspect.ENTROPY)
					{
						if(EssentiaHandler.drainEssentia(this, aspect, ForgeDirection.UNKNOWN, 12))
						{
							this.realCost.reduce(aspect, 25);
							update = true;
						}
					}
				}
			}

			if((this.realCost.visSize() <= 0))
			{
				isComplete = true;
				this.realCost = new AspectList();
				int posX = this.xCoord - radius + this.morphX;
				int posZ = this.zCoord - radius + this.morphZ;
				WorldUtil.setBiomeAt(this.worldObj, posX, posZ, newBiome);
				this.worldObj.getChunkFromBlockCoords(this.xCoord, this.zCoord).setChunkModified();
				Minecraft.getMinecraft().theWorld.markBlockForUpdate(posX, this.worldObj.getTopSolidOrLiquidBlock(posX, posZ), posZ); //Will only work in SSP
			}
		}
		else
		{
			isComplete = true;
		}

		if(isComplete && this.ticks % 20 == 0)
		{
			if(this.morphZ < radius * 2)
			{
				if(this.morphX < radius * 2)
				{
					this.morphX++;
				}
				else
				{
					Log.logger.info("Resetting Location for MorphX, Incrementing MorphZ");
					this.morphX = 0;
					this.morphZ++;
				}

				int posX = this.xCoord - radius + this.morphX;
				int posZ = this.zCoord - radius + this.morphZ;
				BiomeGenBase oldBiome = this.worldObj.getBiomeGenForCoords(posX, posZ);
				
				if(newBiome != oldBiome && Math.sqrt((morphX-radius)*(morphX-radius) + (morphZ-radius)*(morphZ-radius)) <= radius)
				{
					if(this.lastBiome != oldBiome) //prevent recalculation of Cost every Cycle
					{
						this.lastBiome = oldBiome;
						this.morphCost = this.calculateMorphCost(oldBiome, newBiome);
					}
					this.realCost = this.morphCost.copy();
					return true;
				}
			}
			else
			{
				Log.logger.info("Resetting Location for MorphZ, Now Idle! Biome Conversion done?");
				this.morphZ = 0;
				this.idle = true;
			}
		}

		return update;
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
		this.realCost.readFromNBT(data);
		this.morphX = data.getByte("morphX");
		this.morphZ = data.getByte("morphZ");
		this.idle = data.getBoolean("idle");
	}

	@Override
	public void writeCustomNBT(NBTTagCompound data)
	{
		this.realCost.writeToNBT(data);
		data.setByte("morphX", (byte) this.morphX);
		data.setByte("morphZ", (byte) this.morphZ);
		data.setBoolean("idle", this.idle);
	}

	@Override
	public AspectList getAspects()
	{
		return this.realCost;
	}

	@Override
	public void setAspects(AspectList aspects) {}

	@Override
	public boolean doesContainerAccept(Aspect tag)
	{
		return false;
	}

	@Override
	public int addToContainer(Aspect tag, int amount)
	{
		return 0;
	}

	@Override
	public boolean takeFromContainer(Aspect tag, int amount)
	{
		return false;
	}

	@Override
	public boolean takeFromContainer(AspectList ot)
	{
		return false;
	}

	@Override
	public boolean doesContainerContainAmount(Aspect tag, int amount)
	{
		return false;
	}

	@Override
	public boolean doesContainerContain(AspectList ot)
	{
		return false;
	}

	@Override
	public int containerContains(Aspect tag)
	{
		return 0;
	}
}
