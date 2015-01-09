package trinarybrain.magia.naturalis.common.block;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.common.lib.utils.InventoryUtils;
import trinarybrain.magia.naturalis.client.util.RenderUtil;
import trinarybrain.magia.naturalis.common.MagiaNaturalis;
import trinarybrain.magia.naturalis.common.tile.TileArcaneChest;
import trinarybrain.magia.naturalis.common.util.NBTUtil;
import trinarybrain.magia.naturalis.common.util.Platform;
import trinarybrain.magia.naturalis.common.util.access.UserAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockArcaneChest extends BlockContainer
{

	public BlockArcaneChest()
	{
		super(Material.wood);
		this.setResistance(999.0F);
		this.setHardness(8.0F);
		this.setStepSound(this.soundTypeWood);
		this.setCreativeTab(MagiaNaturalis.creativeTab);
	}

	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		ItemStack stack = new ItemStack(item, 1, 1);
		this.setChestType(stack, (byte) 1);
		list.add(stack);
		stack = new ItemStack(item, 1, 2);
		this.setChestType(stack, (byte) 2);
		list.add(stack);
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		this.blockIcon = ir.registerIcon("thaumcraft:woodplain");
	}

	public boolean isOpaqueCube() {return false;}
	public boolean renderAsNormalBlock() {return false;}

	public int getRenderType()
	{
		return RenderUtil.RenderID;
	}

	@SideOnly(Side.CLIENT)
	public boolean addHitEffects(World world, MovingObjectPosition target, EffectRenderer effectRenderer)
	{
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		TileArcaneChest chest = (TileArcaneChest) world.getTileEntity(target.blockX, target.blockY, target.blockZ);

		if(chest != null && player != null)
		{
			if(chest.owner.equals(player.getGameProfile().getId()))
			{
				return false;
			}
			else
			{
				if(chest.accessList != null && chest.accessList.size() > 0)
					for(UserAccess user : chest.accessList)
						if(user.getUUID().equals(player.getGameProfile().getId()))
							return user.getAccessLevel() > 1;
			}
		}

		float f = (float)target.hitVec.xCoord - target.blockX;
		float f1 = (float)target.hitVec.yCoord - target.blockY;
		float f2 = (float)target.hitVec.zCoord - target.blockZ;
		MagiaNaturalis.proxyTC4.blockWard(world, target.blockX, target.blockY, target.blockZ, ForgeDirection.getOrientation(target.sideHit), f, f1, f2);
		return true;
	}

	public byte getChestType(ItemStack stack)
	{
		if(stack != null)
			return NBTUtil.openNbtData(stack).getByte("chestType");

		return 0;
	}

	public static boolean setChestType(ItemStack stack, byte type)
	{
		if(stack != null && type > 0)
		{
			if(type > 2) return false;
			NBTUtil.openNbtData(stack).setByte("chestType", type);
			return true;
		}
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileArcaneChest();
	}

	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		int meta = BlockPistonBase.determineOrientation(world, x, y, z, (EntityPlayer)entity);
		world.setBlockMetadataWithNotify(x, y, z, meta, 3);
		TileArcaneChest chest = (TileArcaneChest) world.getTileEntity(x, y, z);

		if(chest != null)
		{
			EntityPlayer player = (EntityPlayer)entity;
			chest.owner = player.getUniqueID();
			chest.setChestType((byte) this.getChestType(stack));

			ItemStack[] items = NBTUtil.loadInventoryFromNBT(stack, chest.getSizeInventory());
			if(items != null && items.length == chest.getSizeInventory()) chest.setInvetory(items);

			ArrayList<UserAccess> users = NBTUtil.loadUserAccesFromNBT(stack);
			if(!users.isEmpty()) chest.accessList = users;
		}
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f0, float f1, float f3)
	{
		if(Platform.isClient()) return false;

		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile == null) return false;
		if(tile instanceof TileArcaneChest)
		{
			TileArcaneChest chest = (TileArcaneChest) tile;

			boolean hasAccess = false;
			if(player.capabilities.isCreativeMode || player.getGameProfile().getId().equals(chest.owner))
			{
				hasAccess = true;
			}
			else
			{
				if(chest.accessList != null && chest.accessList.size() > 0)
					for(UserAccess user : chest.accessList)
						if(user.getUUID().equals(player.getGameProfile().getId()))
						{
							hasAccess = user.hasAccess();
							break;
						}
			}

			if(hasAccess)
			{
				player.openGui(MagiaNaturalis.instance, 2, world, x, y, z);
			}
			else
			{
				world.playSoundEffect(x, y, z, "thaumcraft:doorfail", 0.66F, 1.0F);
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE + Platform.translate("chat.magianaturalis:chest.access.denied")));
			}
			return true;
		}
		return false;
	}

	public boolean canHarvestBlock(EntityPlayer player, int meta) {return true;}

	public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, int x, int y, int z)
	{
		TileArcaneChest chest = (TileArcaneChest) world.getTileEntity(x, y, z);
		if(chest != null && player != null && player instanceof EntityPlayer)
		{
			if(chest.owner.equals(player.getGameProfile().getId()))
			{
				return ForgeHooks.blockStrength(this, player, world, x, y, z);
			}
			else
			{
				if(chest.accessList != null && chest.accessList.size() > 0)
					for(UserAccess user : chest.accessList)
						if(user.getUUID().equals(player.getGameProfile().getId()))
						{
							if(user.getAccessLevel() > 1) return ForgeHooks.blockStrength(this, player, world, x, y, z);
							break;
						}
			}
		}
		return 0.0F;		
	}

	public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity)
	{
		TileArcaneChest chest = (TileArcaneChest) world.getTileEntity(x, y, z);
		if(chest != null && entity != null && entity instanceof EntityPlayer)
		{
			if(chest.owner.equals(entity.getUniqueID())) 
			{
				return true;
			}
			else
			{
				if(chest.accessList != null && chest.accessList.size() > 0)
					for(UserAccess user : chest.accessList)
						if(user.getUUID().equals(entity.getUniqueID()))
							return user.getAccessLevel() > 1;
			}
		}
		return false;
	}

	public void onBlockExploded(World world, int x, int y, int z, Explosion explosion) {}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		TileArcaneChest chest = (TileArcaneChest) world.getTileEntity(x, y, z);
		if(chest != null)
		{
			this.CacheChestType = (byte) chest.getChestType();
		}

		InventoryUtils.dropItems(world, x, y, z);
		super.breakBlock(world, x, y, z, block, meta);
	}

	private byte CacheChestType;

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();

		if(CacheChestType != -1)
		{
			ItemStack stack = new ItemStack(this, 1, CacheChestType);
			BlockArcaneChest.setChestType(stack, (byte) CacheChestType);
			CacheChestType = -1;
			drops.add(stack);
		}

		return drops;
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		float f = 0.0625F;
		return AxisAlignedBB.getBoundingBox(x + f, y, z + f, x + 1 - f, y + 1 - f, z + 1 - f);
	}

	public void setBlockBoundsBasedOnState(IBlockAccess iBAccess, int x, int y, int z)
	{
		this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
	}

	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		TileArcaneChest chest = (TileArcaneChest) world.getTileEntity(x, y, z);
		if(chest == null) return null;

		ItemStack stack = new ItemStack(this, 1, chest.getChestType());
		BlockArcaneChest.setChestType(stack, (byte) chest.getChestType());

		return stack;
	}
}
