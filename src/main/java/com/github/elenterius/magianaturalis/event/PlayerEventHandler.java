package com.github.elenterius.magianaturalis.event;

import com.github.elenterius.magianaturalis.item.artifact.ArcaneKeyItem;
import com.github.elenterius.magianaturalis.util.NBTUtil;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;

import java.util.UUID;

public final class PlayerEventHandler {

    @SubscribeEvent
    public void onAnvilRepair(AnvilRepairEvent event) {
        EntityPlayer player = event.entityPlayer;
        ItemStack stack = event.output;

        if (player == null || stack == null) return;

        if (stack.getItem() instanceof ArcaneKeyItem) {
            NBTTagCompound data = NBTUtil.openNbtData(stack);
            boolean hasAccess = false;
            if (data.hasKey("forger") && UUID.fromString(data.getString("forger")).equals(player.getGameProfile().getId())) {
                hasAccess = true;
            }
            else if (data.hasKey("owner") && UUID.fromString(data.getString("owner")).equals(player.getGameProfile().getId()) && data.hasKey("accessLevel") && data.getByte("accessLevel") > 0) {
                hasAccess = true;
            }

            if (hasAccess) {
                System.out.print("\nHAS ACCESS");
                if (data.hasKey("display", NBT.TAG_COMPOUND)) {
                    NBTTagCompound tempData = data.getCompoundTag("display");
                    if (tempData.hasKey("Name", NBT.TAG_STRING)) {
                        String str = tempData.getString("Name");
                        System.out.print("\n" + str);
                        str = str.substring(2);
                        System.out.print("\n" + str);
                    }
                }
            }
        }
    }

//    @SubscribeEvent
//    public void onAnvilUpdate(AnvilUpdateEvent event) {
//        if (event.left.getItem() != null && event.right == null) {
//            if (event.left.getItem() instanceof ItemKey) {
                //				if(ScrollHelper.getKeyFromStack(event.right).equals(ScrollLib.LOST_KEY))
                //				{
                //					ItemStack blade = new ItemStack(ModItems.itemNamelessBlade);
                //					RelicHelper.setUpgrade(blade, ScrollLib.LOST_KEY);
                //					event.cost = 10;
                //					event.output = blade;
                //				}
                //				if(ScrollHelper.getKeyFromStack(event.right).equals(ScrollLib.ORIGIN2_KEY))
                //				{
                //					ItemStack blade = new ItemStack(ModItems.itemNamelessBlade);
                //					RelicHelper.setUpgrade(blade, ScrollLib.ORIGIN2_KEY);
                //					event.cost = 10;
                //					event.output = blade;
                //				}
//            }
//        }
//    }

}
