package trinarybrain.magia.naturalis.common.block.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import trinarybrain.magia.naturalis.common.MagiaNaturalis;
import trinarybrain.magia.naturalis.common.tile.TileBannerCustom;
import trinarybrain.magia.naturalis.common.util.Platform;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockBannerItem extends ItemBlock
{
	public BlockBannerItem(Block block)
	{
		super(block);
		this.setMaxDamage(0);
		this.setCreativeTab(MagiaNaturalis.creativeTab);
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		list.add(new StringBuilder().append(EnumChatFormatting.DARK_PURPLE).append(Platform.translate("flavor.magianaturalis:banner")).toString());
	}

	public int getMetadata(int meta)
	{
		return meta;
	}

	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
	{
		boolean ret = super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
	   
		if(ret)
		{
			TileBannerCustom tile = (TileBannerCustom) world.getTileEntity(x, y, z);
			if(tile != null)
			{
				if(side <= 1)
				{
					int i = MathHelper.floor_double((player.rotationYaw + 180.0F) * 16.0F / 360.0F + 0.5D) & 0xF;
					tile.setFacing((byte) i);
				}
				else
				{
					tile.setWall(true);

					int i = 0;
					if(side == 2) i = 8;
					if(side == 4) i = 4;
					if(side == 5) i = 12;

					tile.setFacing((byte) i);
				}

				if(stack.hasTagCompound())
				{
					if(stack.stackTagCompound.hasKey("color"))
					{
						tile.setColor(stack.stackTagCompound.getByte("color"));
					}
				}
				
				tile.markDirty();
				world.markBlockForUpdate(x, y, z);
			}
		}
		return ret;
	}
}
