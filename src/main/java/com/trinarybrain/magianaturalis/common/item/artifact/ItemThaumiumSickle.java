package com.trinarybrain.magianaturalis.common.item.artifact;

import com.trinarybrain.magianaturalis.common.MagiaNaturalis;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import thaumcraft.api.IRepairable;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.common.config.ConfigItems;

public class ItemThaumiumSickle extends ItemSickle implements IRepairable {

    public ItemThaumiumSickle() {
        super(ThaumcraftApi.toolMatThaumium);
        areaSize = 2;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister icon) {
        itemIcon = icon.registerIcon(MagiaNaturalis.rlString("sickle_thaumium"));
    }

    public int getItemEnchantability() {
        return 5;
    }

    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.uncommon;
    }

    public boolean getIsRepairable(ItemStack stack, ItemStack stackMaterial) {
        return stackMaterial.isItemEqual(new ItemStack(ConfigItems.itemResource, 1, 2)) || super.getIsRepairable(stack, stackMaterial);
    }

}
