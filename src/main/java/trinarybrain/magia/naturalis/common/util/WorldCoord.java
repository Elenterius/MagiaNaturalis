package trinarybrain.magia.naturalis.common.util;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.BlockCoordinates;

public class WorldCoord extends BlockCoordinates
{
	public WorldCoord(TileEntity tile)
	{
		super(tile);
	}

	public WorldCoord(BlockCoordinates blockCoordinates)
	{
		super(blockCoordinates);
	}

	public WorldCoord(int x, int y, int z)
	{
		super(x, y, z);
	}

	public void add(ForgeDirection opposite, int i)
	{
		//TODO: ADD METHOD BODY
	}

	public void add(int minmax, int i, int minmax2)
	{
		//TODO: ADD METHOD BODY
	}

	public void subtract(int i, int j, int k)
	{
		//TODO: ADD METHOD BODY
	}

}
