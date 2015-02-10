package trinarybrain.magia.naturalis.common.item.baubles;

import java.util.List;
import java.util.UUID;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBeltHealth extends BaseBauble
{
	public IIcon icon;
	public static final UUID HB_UUID = UUID.fromString("f164e793-1d4a-47ea-aa02-b35dd39d71d8");
	public AttributeModifier healtBoost = new AttributeModifier(this.HB_UUID, "belt.healthBoost", 2, 0);

	public ItemBeltHealth()
	{
		super();
	}

	@Override @SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
		list.add(new ItemStack(item, 1, 2));
		list.add(new ItemStack(item, 1, 3));
		list.add(new ItemStack(item, 1, 4));
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack)
	{
		return super.getUnlocalizedName() + "." + Integer.toString(itemStack.getItemDamage());
	}

	@Override @SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister)
	{
		icon = iconRegister.registerIcon("thaumcraft:bauble_belt");
	}

	@Override @SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int par1)
	{
		return this.icon;
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack)
	{
		return BaubleType.BELT;
	}

	@Override
	public EnumRarity getRarity(ItemStack itemstack)
	{
		switch(itemstack.getItemDamage())
		{
		case 1: return EnumRarity.common;
		case 2: return EnumRarity.uncommon;
		case 3: return EnumRarity.rare;
		case 4: return EnumRarity.epic;
		}
		return EnumRarity.common;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player)
	{
		if (!player.worldObj.isRemote)
		{
			player.worldObj.playSoundAtEntity(player, "random.orb", 0.1F, 1.3f);
		}

		int i = (itemstack.getItemDamage()+1) * 2;
		AttributeModifier attributeM = new AttributeModifier(this.healtBoost.getID(), "belt.healthBoost"+" "+i, i, this.healtBoost.getOperation());
		this.applyAttributesModifiersToEntity(player.getEntityAttribute(SharedMonsterAttributes.maxHealth), attributeM);
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player)
	{
		int i = (itemstack.getItemDamage()+1) * 2;
		AttributeModifier attributeM = new AttributeModifier(this.healtBoost.getID(), "belt.healthBoost"+" "+i, i, this.healtBoost.getOperation());
		this.removeAttributesModifiersFromEntity(player.getEntityAttribute(SharedMonsterAttributes.maxHealth), attributeM);
	}

	@Override
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player)
	{
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player)
	{
		return true;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4)
	{
		list.add("");
		list.add(EnumChatFormatting.BLUE + "+"+ Integer.toString(((itemstack.getItemDamage()+1)*2)) + " Max Health");	
	}
}
