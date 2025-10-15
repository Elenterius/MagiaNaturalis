package com.github.elenterius.magianaturalis.block.jar;

import com.github.elenterius.magianaturalis.util.NBTUtil;
import com.github.elenterius.magianaturalis.util.Platform;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;

public class PrisonJarBlockItem extends ItemBlock {

    public PrisonJarBlockItem(Block block) {
        super(block);
        setMaxDamage(0);
    }

    public static boolean storeEntityLiving(ItemStack stack, EntityLivingBase entity) {
        if (entity == null || entity instanceof IBossDisplayData || entity instanceof EntityPlayer) return false;
        if (!(entity instanceof EntityCreature)) return false;
        if (stack == null) return false;

        NBTTagCompound data = new NBTTagCompound();
        if (!entity.writeMountToNBT(data)) return false;
        entity.setDead();
        stack.setTagInfo("entity", data);

        return true;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List lines, boolean bool) {
        super.addInformation(stack, player, lines, bool);
        String id = getEntityLivingID(stack);
        if (id != null) {
            lines.add(Platform.translate("entity." + id + ".name"));
        }
    }

    public String getEntityLivingID(ItemStack stack) {
        NBTTagCompound data = NBTUtil.openNbtData(stack);
        if (data.hasKey("entity")) {
            return data.getCompoundTag("entity").getString("id");
        }
        return null;
    }

    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity) {
        if (Platform.isClient()) return false;
        if (NBTUtil.openNbtData(stack).hasKey("entity")) return false;

        ItemStack stack2 = stack.copy();
        stack2.stackSize = 1;
        if (storeEntityLiving(stack2, entity)) {
            player.inventory.decrStackSize(player.inventory.currentItem, 1);
            player.inventory.addItemStackToInventory(stack2);
            player.inventoryContainer.detectAndSendChanges();
            return true;
        }
        return false;
    }

    public boolean releaseEntityLiving(ItemStack stack, World world, double x, double y, double z) {
        NBTTagCompound data = NBTUtil.openNbtData(stack);
        if (data.hasKey("entity")) {
            return NBTUtil.spawnEntityFromNBT(data.getCompoundTag("entity"), world, x, y, z);
        }
        return false;
    }

}
