package com.github.elenterius.magianaturalis.init;

import com.github.elenterius.magianaturalis.MagiaNaturalis;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public final class MNCreativeTabs {

    public static final CreativeTabs MAIN = new CreativeTabs(CreativeTabs.getNextID(), MagiaNaturalis.MOD_ID) {

        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
            return MNItems.researchLog;
        }

    };

}
