package com.trinarybrain.magianaturalis.common.block;

import com.trinarybrain.magianaturalis.client.util.RenderUtil;
import com.trinarybrain.magianaturalis.common.MagiaNaturalis;
import com.trinarybrain.magianaturalis.common.tile.TileArcaneChest;
import com.trinarybrain.magianaturalis.common.util.NBTUtil;
import com.trinarybrain.magianaturalis.common.util.Platform;
import com.trinarybrain.magianaturalis.common.util.access.UserAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.lib.utils.InventoryUtils;

import java.util.ArrayList;
import java.util.List;

public class BlockArcaneChest extends BlockContainer {

    private byte CacheChestType;

    public BlockArcaneChest() {
        super(Material.wood);
        setResistance(999.0F);
        setHardness(8.0F);
        setStepSound(soundTypeWood);
    }

    public static boolean setChestType(ItemStack stack, byte type) {
        if (stack != null && type > 0) {
            if (type > 2) return false;
            NBTUtil.openNbtData(stack).setByte("chestType", type);
            return true;
        }
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        ItemStack stack = new ItemStack(item, 1, 1);
        setChestType(stack, (byte) 1);
        list.add(stack);
        stack = new ItemStack(item, 1, 2);
        setChestType(stack, (byte) 2);
        list.add(stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ir) {
        blockIcon = ir.registerIcon("thaumcraft:woodplain");
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return RenderUtil.RenderID;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean addHitEffects(World world, MovingObjectPosition target, EffectRenderer effectRenderer) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        TileArcaneChest chest = (TileArcaneChest) world.getTileEntity(target.blockX, target.blockY, target.blockZ);

        if (chest != null && player != null) {
            if (chest.owner.equals(player.getGameProfile().getId())) {
                return false;
            }
            else {
                if (chest.accessList != null && chest.accessList.size() > 0) for (UserAccess user : chest.accessList)
                    if (user.getUUID().equals(player.getGameProfile().getId())) return user.getAccessLevel() > 1;
            }
        }

        float f = (float) target.hitVec.xCoord - target.blockX;
        float f1 = (float) target.hitVec.yCoord - target.blockY;
        float f2 = (float) target.hitVec.zCoord - target.blockZ;
        MagiaNaturalis.proxyTC4.blockWard(world, target.blockX, target.blockY, target.blockZ, ForgeDirection.getOrientation(target.sideHit), f, f1, f2);
        return true;
    }

    public byte getChestType(ItemStack stack) {
        if (stack != null) return NBTUtil.openNbtData(stack).getByte("chestType");

        return 0;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileArcaneChest();
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        int meta = BlockPistonBase.determineOrientation(world, x, y, z, entity);
        world.setBlockMetadataWithNotify(x, y, z, meta, 3);
        TileArcaneChest chest = (TileArcaneChest) world.getTileEntity(x, y, z);

        if (chest != null) {
            EntityPlayer player = (EntityPlayer) entity;
            chest.owner = player.getUniqueID();
            chest.setChestType(getChestType(stack));

            ItemStack[] items = NBTUtil.loadInventoryFromNBT(stack, chest.getSizeInventory());
            if (items != null && items.length == chest.getSizeInventory()) chest.setInvetory(items);
            else if (chest.getChestType() == 2) chest.setInvetory(new ItemStack[77]);

            ArrayList<UserAccess> users = NBTUtil.loadUserAccessFromNBT(stack);
            if (!users.isEmpty()) chest.accessList = users;
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f0, float f1, float f3) {
        if (Platform.isClient()) return false;

        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile == null) return false;
        if (tile instanceof TileArcaneChest) {
            TileArcaneChest chest = (TileArcaneChest) tile;
            boolean hasAccess = false;
            if (player.capabilities.isCreativeMode || player.getGameProfile().getId().equals(chest.owner)) {
                hasAccess = true;
            }
            else {
                if (chest.accessList != null && chest.accessList.size() > 0) for (UserAccess user : chest.accessList)
                    if (user.getUUID().equals(player.getGameProfile().getId())) {
                        hasAccess = user.hasAccess();
                        break;
                    }
            }

            if (hasAccess) {
                player.openGui(MagiaNaturalis.instance, 2, world, x, y, z);
            }
            else {
                world.playSoundEffect(x, y, z, "thaumcraft:doorfail", 0.66F, 1.0F);
                player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE + Platform.translate("chat.magianaturalis:chest.access.denied")));
            }
            return true;
        }
        return false;
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
        if (Platform.isServer()) {
            TileEntity tile = world.getTileEntity(x, y, z);
            if (tile != null && tile instanceof TileArcaneChest) {
                ItemStack curStack = player.getCurrentEquippedItem();
                if (curStack != null && curStack.getItem() == ConfigItems.itemKey) {
                    TileArcaneChest chest = (TileArcaneChest) tile;
                    int isKeyAdmin = 0;

                    if (player.capabilities.isCreativeMode || player.getGameProfile().getId().equals(chest.owner)) {
                        isKeyAdmin = 2;
                    }
                    else {
                        if (chest.accessList != null && chest.accessList.size() > 0) for (UserAccess user : chest.accessList)
                            if (user.getUUID().equals(player.getGameProfile().getId())) {
                                if (user.getAccessLevel() > 0) isKeyAdmin = 1;
                                break;
                            }
                    }

                    if (curStack.hasTagCompound() && curStack.stackTagCompound.hasKey("location")) {
                        String loc = x + "," + y + "," + z;
                        if (!loc.equals(curStack.stackTagCompound.getString("location"))) {
                            player.addChatMessage(new ChatComponentText("§5§o" + Platform.translate("tc.key7")));
                        }
                        else if (loc.equals(curStack.stackTagCompound.getString("location"))) {
                            if (isKeyAdmin > 0) {
                                player.addChatMessage(new ChatComponentText("§5§o" + Platform.translate("tc.key8")));
                            }
                            else if (isKeyAdmin == 0) {
                                chest.accessList.add(new UserAccess(player.getGameProfile().getId(), (byte) curStack.getItemDamage()));
                                world.markBlockForUpdate(x, y, z);

                                if (!player.capabilities.isCreativeMode) if (--curStack.stackSize <= 0) player.inventory.mainInventory[player.inventory.currentItem] = null;

                                player.addChatMessage(new ChatComponentText("§5§o" + Platform.translate("chat.magianaturalis:key.access.chest")));
                                world.playSoundEffect(x, y, z, "thaumcraft:key", 1.0F, 0.9F);
                            }
                        }
                    }
                    else if (!curStack.hasTagCompound()) {
                        if (isKeyAdmin > 0) {
                            String loc = x + "," + y + "," + z;
                            ItemStack stack = new ItemStack(ConfigItems.itemKey, 1, curStack.getItemDamage());
                            stack.setTagInfo("location", new NBTTagString(loc));
                            stack.setTagInfo("type", new NBTTagByte((byte) -1));

                            if (!player.capabilities.isCreativeMode) if (--curStack.stackSize <= 0) player.inventory.mainInventory[player.inventory.currentItem] = null;

                            if (!player.inventory.addItemStackToInventory(stack)) world.spawnEntityInWorld(new EntityItem(world, player.posX, player.posY, player.posZ, stack));
                            //player.inventoryContainer.detectAndSendChanges();
                            world.playSoundEffect(x, y, z, "thaumcraft:key", 1.0F, 0.9F);
                        }
                        else {
                            player.addChatMessage(new ChatComponentText("§5§o" + Platform.translate("chat.magianaturalis:chest.access.denied")));
                            world.playSoundEffect(x, y, z, "thaumcraft:doorfail", 0.66F, 1.0F);
                        }
                    }

                }
            }
        }
    }

    @Override
    public boolean canHarvestBlock(EntityPlayer player, int meta) {
        return true;
    }

    @Override
    public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, int x, int y, int z) {
        TileArcaneChest chest = (TileArcaneChest) world.getTileEntity(x, y, z);
        if (chest != null && player != null && player instanceof EntityPlayer) {
            if (chest.owner.equals(player.getGameProfile().getId())) {
                return ForgeHooks.blockStrength(this, player, world, x, y, z);
            }
            else {
                if (chest.accessList != null && chest.accessList.size() > 0) for (UserAccess user : chest.accessList)
                    if (user.getUUID().equals(player.getGameProfile().getId())) {
                        if (user.getAccessLevel() > 1) return ForgeHooks.blockStrength(this, player, world, x, y, z);
                        break;
                    }
            }
        }
        return 0.0F;
    }

    @Override
    public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity) {
        TileArcaneChest chest = (TileArcaneChest) world.getTileEntity(x, y, z);
        if (chest != null && entity != null && entity instanceof EntityPlayer) {
            if (chest.owner.equals(entity.getUniqueID())) {
                return true;
            }
            else {
                if (chest.accessList != null && chest.accessList.size() > 0) for (UserAccess user : chest.accessList)
                    if (user.getUUID().equals(entity.getUniqueID())) return user.getAccessLevel() > 1;
            }
        }
        return false;
    }

    @Override
    public void onBlockExploded(World world, int x, int y, int z, Explosion explosion) {
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileArcaneChest chest = (TileArcaneChest) world.getTileEntity(x, y, z);
        if (chest != null) {
            CacheChestType = (byte) chest.getChestType();
        }

        InventoryUtils.dropItems(world, x, y, z);
        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> drops = new ArrayList<ItemStack>();

        if (CacheChestType != -1) {
            ItemStack stack = new ItemStack(this, 1, CacheChestType);
            BlockArcaneChest.setChestType(stack, CacheChestType);
            CacheChestType = -1;
            drops.add(stack);
        }

        return drops;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        float f = 0.0625F;
        return AxisAlignedBB.getBoundingBox(x + f, y, z + f, x + 1 - f, y + 1 - f, z + 1 - f);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess iBAccess, int x, int y, int z) {
        setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        TileArcaneChest chest = (TileArcaneChest) world.getTileEntity(x, y, z);
        if (chest == null) return null;

        ItemStack stack = new ItemStack(this, 1, chest.getChestType());
        BlockArcaneChest.setChestType(stack, (byte) chest.getChestType());

        return stack;
    }

}
