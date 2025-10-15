package com.github.elenterius.magianaturalis.item.artifact;

import com.github.elenterius.magianaturalis.MagiaNaturalis;
import com.github.elenterius.magianaturalis.entity.EntityEvilTrunk;
import com.github.elenterius.magianaturalis.util.Platform;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class EvilTrunkSpawnerItem extends Item {

    public String[] name = new String[]{"corrupted", "sinister", "demonic", "tainted"};

    public EvilTrunkSpawnerItem() {
        setMaxStackSize(1);
        setHasSubtypes(true);
    }

    @Override
    public Item setTextureName(String iconString) {
        this.iconString = MagiaNaturalis.rlString("empty_texture");
        return this;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 1));
        list.add(new ItemStack(item, 1, 2));
        list.add(new ItemStack(item, 1, 3));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        if (stack.hasTagCompound() && stack.stackTagCompound.hasKey("inventory")) {
            list.add(Platform.translate("item.TrunkSpawner.text.1"));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + name[stack.getItemDamage()];
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (Platform.isClient()) return false;

        Block block = world.getBlock(x, y, z);
        x += net.minecraft.util.Facing.offsetsXForSide[side];
        y += net.minecraft.util.Facing.offsetsYForSide[side];
        z += net.minecraft.util.Facing.offsetsZForSide[side];

        double d0 = 0.0D;
        if (side == 1 && !block.isAir(world, x, y, z) && block.getRenderType() == 11) d0 = 0.5D;
        EntityEvilTrunk entity = new EntityEvilTrunk(world, stack.getItemDamage());

        if (stack.hasTagCompound() && stack.stackTagCompound.hasKey("inventory")) {
            NBTTagList dataList = stack.stackTagCompound.getTagList("inventory", 10);
            entity.inventory.readFromNBT(dataList);
        }

        entity.setOwnerUUID(player.getGameProfile().getId().toString());
        entity.setLocationAndAngles(x, y + d0, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
        entity.rotationYawHead = entity.rotationYaw;
        entity.renderYawOffset = entity.rotationYaw;
        if (stack.hasDisplayName()) entity.setCustomNameTag(stack.getDisplayName());

        if (world.spawnEntityInWorld(entity)) {
            entity.playLivingSound();

            if (!player.capabilities.isCreativeMode) stack.stackSize -= 1;
        }

        return true;
    }

}
