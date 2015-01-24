package trinarybrain.magia.naturalis.common.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import trinarybrain.magia.naturalis.common.MagiaNaturalis;
import trinarybrain.magia.naturalis.common.util.ResourceUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockArcaneWood extends Block
{
	// FANCY GREATWOOD & SILVERWOOD SIGN?
	private IIcon[] icon = new IIcon[7];

	public BlockArcaneWood()
	{
		super(Material.wood);
		this.setHardness(2.0F);
		this.setResistance(5.0F);
		this.setStepSound(soundTypeWood);
		this.setCreativeTab(MagiaNaturalis.creativeTab);
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		this.icon[0] = ir.registerIcon(ResourceUtil.PREFIX + "greatwood_planks");
		this.icon[1] = ir.registerIcon(ResourceUtil.PREFIX + "greatwood_orn");
		this.icon[2] = ir.registerIcon(ResourceUtil.PREFIX + "silverwood_planks2");
		this.icon[3] = ir.registerIcon(ResourceUtil.PREFIX + "silverwood_planks");
		this.icon[4] = ir.registerIcon(ResourceUtil.PREFIX + "greatwood_gold_orn");
		this.icon[5] = ir.registerIcon(ResourceUtil.PREFIX + "greatwood_gold_orn2");
		this.icon[6] = ir.registerIcon(ResourceUtil.PREFIX + "greatwood_gold_trim");
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if(meta < 0 || meta > 6)
			meta = 0;
		
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
		list.add(new ItemStack(item, 1, 6));
	}

	public int damageDropped(int meta)
	{
		return meta;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		int meta = stack.getItemDamage();
		if(meta < 0 || meta > 6)
		{
			meta = 0;
		}
		world.setBlockMetadataWithNotify(x, y, z, meta, 2);
	}
}
