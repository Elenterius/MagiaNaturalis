package trinarybrain.magia.naturalis.common.item.artifact;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import trinarybrain.magia.naturalis.common.entity.EntityEvilTrunk;
import trinarybrain.magia.naturalis.common.item.BaseItem;
import trinarybrain.magia.naturalis.common.util.Platform;
import trinarybrain.magia.naturalis.common.util.ResourceUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemEvilTrunkSpawner extends BaseItem
{
	public String name[] = new String[] {"corrupted", "sinister", "demonic", "tainted"};

	public ItemEvilTrunkSpawner()
	{
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		this.itemIcon = ir.registerIcon(ResourceUtil.PREFIX + ResourceUtil.EMTPY_TEXTURE);
	}

	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
		list.add(new ItemStack(item, 1, 2));
		list.add(new ItemStack(item, 1, 3));
	}

	public String getUnlocalizedName(ItemStack stack)
	{
		return super.getUnlocalizedName() + "." + name[stack.getItemDamage()];
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(Platform.isClient()) return false;
		
		Block block = world.getBlock(x, y, z);
		x += net.minecraft.util.Facing.offsetsXForSide[side];
		y += net.minecraft.util.Facing.offsetsYForSide[side];
		z += net.minecraft.util.Facing.offsetsZForSide[side];

		double d0 = 0.0D;
		if(side == 1 && !block.isAir(world, x, y, z) && block.getRenderType() == 11) d0 = 0.5D;
		EntityEvilTrunk entity = new EntityEvilTrunk(world, stack.getItemDamage());
		((EntityEvilTrunk) entity).setOwner(player.getCommandSenderName());

		if(entity != null)
		{
			entity.setLocationAndAngles(x, y + d0, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
			entity.rotationYawHead = entity.rotationYaw;
			entity.renderYawOffset = entity.rotationYaw;
			if(stack.hasDisplayName()) entity.setCustomNameTag(stack.getDisplayName());
			world.spawnEntityInWorld(entity);
			entity.playLivingSound();
			if(!player.capabilities.isCreativeMode) stack.stackSize -= 1;
		}
		
		return true;
	}
}
