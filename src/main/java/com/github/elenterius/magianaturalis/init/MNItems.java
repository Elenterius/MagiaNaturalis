package com.github.elenterius.magianaturalis.init;

import com.github.elenterius.magianaturalis.MagiaNaturalis;
import com.github.elenterius.magianaturalis.item.alchemy.ItemAlchemicalStone;
import com.github.elenterius.magianaturalis.item.artifact.*;
import com.github.elenterius.magianaturalis.item.baubles.ItemFocusPouchEnder;
import com.github.elenterius.magianaturalis.item.focus.ItemFocusBuild;
import com.github.elenterius.magianaturalis.item.focus.ItemFocusRevenant;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

import java.util.function.Supplier;

public class MNItems {

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
        researchLog = registerItem("research_log", ItemResearchLog::new);
        biomeReport = registerItem("biome_sampler", ItemBiomeSampler::new);
        alchemicalStone = registerItem("alchemical_stone", ItemAlchemicalStone::new);
        key = registerItem("thaumium_key", ItemKey::new);
        gogglesDark = registerItem("dark_crystal_goggles", ItemGogglesDark::new);
        spectacles = registerItem("spectacles", ItemSpectacles::new);
        focusBuild = registerItem("builder_focus", ItemFocusBuild::new);
        focusRevenant = registerItem("revenant_focus", ItemFocusRevenant::new);
        evilTrunkSpawner = registerItem("evil_trunk", ItemEvilTrunkSpawner::new);
        focusPouchEnder = registerItem("focus_ender_pouch", ItemFocusPouchEnder::new);
        sickleThaumium = registerItem("thaumium_sickle", ItemThaumiumSickle::new);
        sickleElemental = registerItem("elemental_sickle", ItemElementalSickle::new);
        voidSickle = registerItem("void_sickle", ItemVoidSickle::new);

        //devTool = registerItem("devTool", DevTool::new);
        //biomeDevTool = registerItem("biomeDevTool", BiomeDevTool::new);
    }

    private static <T extends Item> T registerItem(String name, Supplier<T> factory) {
        T item = factory.get();
        item.setUnlocalizedName(MagiaNaturalis.translationKey(name));
        item.setCreativeTab(MNCreativeTabs.MAIN);

        GameRegistry.registerItem(item, name);

        return item;
    }

}
