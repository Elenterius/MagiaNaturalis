package com.trinarybrain.magianaturalis.common.item;

import com.trinarybrain.magianaturalis.common.MagiaNaturalis;
import com.trinarybrain.magianaturalis.common.item.alchemy.ItemAlchemicalStone;
import com.trinarybrain.magianaturalis.common.item.artifact.*;
import com.trinarybrain.magianaturalis.common.item.baubles.ItemFocusPouchEnder;
import com.trinarybrain.magianaturalis.common.item.focus.ItemFocusBuild;
import com.trinarybrain.magianaturalis.common.item.focus.ItemFocusRevenant;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

import java.util.function.Supplier;

public class ItemsMN {

    public static Item researchLog;
    public static Item biomeReport;
    public static Item alchemicalStone;
    public static Item key;
    public static Item gogglesDark;
    public static Item spectacles;
    public static Item focusBuild;
    public static Item focusRevenant;
    public static Item evilTrunkSpawner;
    public static Item focusPouchEnder;
    public static Item sickleThaumium;
    public static Item sickleElemental;
    public static Item voidSickle;

    //public static Item devTool;
    //public static Item biomeDevTool;

    //public static final String MIXING_FLASK = "mixingFlask";

    public static void initItems() {
        researchLog = registerItem("researchLog", ItemResearchLog::new);
        alchemicalStone = registerItem("alchemicalStone", ItemAlchemicalStone::new);
        key = registerItem("key", ItemKey::new);
        gogglesDark = registerItem("gogglesDarkCrystal", ItemGogglesDark::new);
        spectacles = registerItem("spectacles", ItemSpectacles::new);
        focusBuild = registerItem("focusBuild", ItemFocusBuild::new);
        evilTrunkSpawner = registerItem("evilTrunk", ItemEvilTrunkSpawner::new);
        focusPouchEnder = registerItem("focusPouchEnder", ItemFocusPouchEnder::new);
        sickleThaumium = registerItem("sickleThaumium", ItemThaumiumSickle::new);
        sickleElemental = registerItem("sickleElemental", ItemElementalSickle::new);
        biomeReport = registerItem("biomeSampler", ItemBiomeSampler::new);
        focusRevenant = registerItem("focusRevenant", ItemFocusRevenant::new);
        voidSickle = registerItem("voidSickle", ItemVoidSickle::new);

        //devTool = registerItem("devTool", DevTool::new);
        //biomeDevTool = registerItem("biomeDevTool", BiomeDevTool::new);
    }

    private static <T extends Item> T registerItem(String name, Supplier<T> factory) {
        T item = factory.get();
        item.setUnlocalizedName(MagiaNaturalis.translationKey(name));
        item.setCreativeTab(MagiaNaturalis.CREATIVE_TAB);

        GameRegistry.registerItem(item, name);

        return item;
    }

}
