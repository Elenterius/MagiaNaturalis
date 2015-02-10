package trinarybrain.magia.naturalis.common.item.baubles;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import trinarybrain.magia.naturalis.common.MagiaNaturalis;

public class BaseBauble extends Item implements IBauble
{	
	public BaseBauble()
	{
		this.setMaxStackSize(1);
		this.canRepair = false;
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setCreativeTab(MagiaNaturalis.creativeTab);
	}

	public void applyAttributesModifiersToEntity(IAttributeInstance iattributeinstance, AttributeModifier attributeM)
	{
		if(iattributeinstance != null && attributeM != null && iattributeinstance.getModifier(attributeM.getID()) == null)
		{
			iattributeinstance.applyModifier(attributeM);
		}
	}

	public void removeAttributesModifiersFromEntity(IAttributeInstance iattributeinstance, AttributeModifier attributeM)
	{
		if(iattributeinstance != null && attributeM != null && iattributeinstance.getModifier(attributeM.getID()) != null)
		{
			iattributeinstance.removeModifier(iattributeinstance.getModifier(attributeM.getID()));
		}
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack)
	{
		return null;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player)
	{
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player)
	{
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player)
	{
	}

	@Override
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player)
	{
		return false;
	}

	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player)
	{
		return false;
	}
}
