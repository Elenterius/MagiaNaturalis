package com.trinarybrain.magianaturalis.common.block;

import java.util.List;

import com.trinarybrain.magianaturalis.common.MagiaNaturalis;
import com.trinarybrain.magianaturalis.common.util.ResourceUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockArcaneWood extends Block
{
	//TODO: FANCY GREATWOOD & SILVERWOOD SIGN?
	private IIcon[] icon = new IIcon[6];

	public BlockArcaneWood()
	{
		super(Material.wood);
		setHardness(2.0F);
		setResistance(5.0F);
		setStepSound(soundTypeWood);
		setCreativeTab(MagiaNaturalis.creativeTab);
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		icon[0] = ir.registerIcon(ResourceUtil.PREFIX + "greatwood_planks");
		icon[1] = ir.registerIcon(ResourceUtil.PREFIX + "greatwood_orn");
		icon[2] = ir.registerIcon(ResourceUtil.PREFIX + "silverwood_planks");
		icon[3] = ir.registerIcon(ResourceUtil.PREFIX + "greatwood_gold_orn");
		icon[4] = ir.registerIcon(ResourceUtil.PREFIX + "greatwood_gold_orn2");
		icon[5] = ir.registerIcon(ResourceUtil.PREFIX + "greatwood_gold_trim");
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if(meta < 0 || meta > 5)
			meta = 0;

		if(meta == 5)
			return side == 1 || side == 0 ? icon[0] : icon[meta];

			return icon[meta];
	}


	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
		list.add(new ItemStack(item, 1, 2));
		list.add(new ItemStack(item, 1, 3));
		list.add(new ItemStack(item, 1, 4));
		list.add(new ItemStack(item, 1, 5));
	}

	public int damageDropped(int meta)
	{
		return meta;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		int meta = stack.getItemDamage();
		if(meta < 0 || meta > 5)
		{
			meta = 0;
		}
		world.setBlockMetadataWithNotify(x, y, z, meta, 2);
	}
}
