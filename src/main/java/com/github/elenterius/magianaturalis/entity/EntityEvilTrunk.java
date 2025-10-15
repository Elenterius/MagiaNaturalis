package com.github.elenterius.magianaturalis.entity;

import com.github.elenterius.magianaturalis.MagiaNaturalis;
import com.github.elenterius.magianaturalis.entity.ai.AIFollowJumpOwner;
import com.github.elenterius.magianaturalis.entity.ai.AILeapAtTarget;
import com.github.elenterius.magianaturalis.entity.ai.AIOwnerHurtByTarget;
import com.github.elenterius.magianaturalis.entity.ai.AIOwnerHurtTarget;
import com.github.elenterius.magianaturalis.init.MNItems;
import com.github.elenterius.magianaturalis.inventory.InventoryEvilTrunk;
import com.github.elenterius.magianaturalis.util.Platform;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import thaumcraft.common.config.ConfigItems;

public class EntityEvilTrunk extends EntityOwnableCreature {

    public float skullrot;
    private int jumpDelay;
    private int eatDelay;
    public InventoryEvilTrunk inventory = new InventoryEvilTrunk(this, 36);
    public String[] name = new String[]{"corrupted", "sinister", "demonic", "tainted"};

    public EntityEvilTrunk(World world, int type) {
        this(world);
        setTrunkType((byte) type);
    }

    public EntityEvilTrunk(World world) {
        super(world);
        setSize(0.8F, 0.8F);
        skullrot = 0.0F;
        preventEntitySpawning = true;
        isImmuneToFire = true;

        // if(getTrunkType() == 3)
        // ADD FLIGHT

        tasks.addTask(2, aiWait);
        tasks.addTask(3, new AILeapAtTarget(this));
        tasks.addTask(4, new EntityAIAttackOnCollide(this, 0.6D, true));
        tasks.addTask(5, new AIFollowJumpOwner(this));
        tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        tasks.addTask(6, new EntityAILookIdle(this));

        targetTasks.addTask(1, new AIOwnerHurtByTarget(this));
        targetTasks.addTask(2, new AIOwnerHurtTarget(this));
        targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataWatcher.addObject(14, (byte) 0);
        dataWatcher.addObject(15, (byte) 0);
        dataWatcher.addObject(18, (byte) 0);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(75.0D);
        getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
        getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
    }

    public void writeEntityToNBT(NBTTagCompound data) {
        super.writeEntityToNBT(data);
        data.setByte("TrunkType", getTrunkType());
        data.setTag("Inventory", inventory.writeToNBT(new NBTTagList()));
    }

    public void readEntityFromNBT(NBTTagCompound data) {
        super.readEntityFromNBT(data);
        setTrunkType(data.getByte("TrunkType"));

        NBTTagList nbttaglist = data.getTagList("Inventory", 10);
        inventory.readFromNBT(nbttaglist);
    }

    public boolean isAIEnabled() {
        return true;
    }

    protected void updateAITick() {
        if (eatDelay > 0) eatDelay -= 1;
        fallDistance = 0.0F;

        if (getOwner() != null) {
            if (getAttackTarget() != null && !getAttackTarget().isDead && getAttackTarget() != getOwner()) {
                faceEntity(getAttackTarget(), 10.0F, 20.0F);
                if ((attackTime <= 0) && (getDistanceToEntity(getAttackTarget()) < 1.5D) && (getAttackTarget().boundingBox.maxY > boundingBox.minY) && (getAttackTarget().boundingBox.minY < boundingBox.maxY)) {
                    attackTime = (10 + worldObj.rand.nextInt(5));
                    getAttackTarget().attackEntityFrom(DamageSource.causeMobDamage(this), (float) getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
                    worldObj.setEntityState(this, (byte) 17);
                    worldObj.playSoundAtEntity(this, "mob.blaze.hit", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
                }

            }
        }
    }

    public boolean attackEntityFrom(DamageSource damage, float amount) {
        if (isEntityInvulnerable()) {
            return false;
        }
        else {
            if (damage == DamageSource.lava) return false;
            if (damage == DamageSource.inFire) return false;
            if (damage == DamageSource.onFire) return false;
            if (damage == DamageSource.drown) return false;
            return super.attackEntityFrom(damage, amount);
        }
    }

    @Override
    public boolean canAttack(EntityLivingBase attacker, EntityLivingBase owner) {
        if (!(attacker instanceof EntityCreeper) && !(attacker instanceof EntityGhast)) {
            if (attacker instanceof EntityWolf) {
                if (((EntityWolf) attacker).isTamed() && ((EntityWolf) attacker).getOwner() == owner)
                    return false;
            }
            if (attacker instanceof EntityOwnableCreature) {
                if (((EntityOwnableCreature) attacker).getOwner() == owner)
                    return false;
            }

            return attacker instanceof EntityPlayer && owner instanceof EntityPlayer && !((EntityPlayer) owner).canAttackPlayer((EntityPlayer) attacker) ? false : !(attacker instanceof EntityHorse) || !((EntityHorse) attacker).isTame();
        }
        else {
            return false;
        }
    }

    public void onUpdate() {
        super.onUpdate();

        if (worldObj.isRemote) {
            if (!onGround) {
                if ((motionY < 0.0D) && (!inWater)) skullrot += 0.015F;
            }

            if ((onGround) || (inWater)) {
                if (!isOpen()) {
                    skullrot -= 0.1F;
                    if (skullrot < 0.0F) skullrot = 0.0F;
                }
            }

            if (isOpen()) skullrot += 0.035F;
            if (skullrot > (isOpen() ? 0.5F : 0.2F)) skullrot = (isOpen() ? 0.5F : 0.2F);
        }
        else if ((getHealth() < getMaxHealth()) && (ticksExisted % 50 == 0)) {
            heal(1.0F);
        }
    }

    public boolean interact(EntityPlayer player) {
        ItemStack stack = player.inventory.getCurrentItem();
        if (stack != null) {
            if (stack.getItem() instanceof ItemFood && getHealth() < getMaxHealth()) {
                ItemFood itemfood = (ItemFood) stack.getItem();
                stack.stackSize -= 1;
                heal(itemfood.func_150905_g(stack));

                if (getHealth() == getMaxHealth())
                    worldObj.playSoundAtEntity(this, "random.burp", 0.5F, worldObj.rand.nextFloat() * 0.5F + 0.5F);
                else
                    worldObj.playSoundAtEntity(this, "random.eat", 0.5F, worldObj.rand.nextFloat() * 0.5F + 0.5F);

                worldObj.setEntityState(this, (byte) 18);
                skullrot = 0.15F;

                if (stack.stackSize <= 0)
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, null);

                return true;
            }
            else if (isOwner(player) && stack.getItem() == ConfigItems.itemGolemBell && !isDead) {
                if (Platform.isClient()) {
                    spawnExplosionParticle();
                    return true;
                }
                else if (Platform.isServer()) {
                    ItemStack drop = new ItemStack(MNItems.evilTrunkSpawner, 1, getTrunkType());

                    if (player.isSneaking())
                        drop.setTagInfo("inventory", inventory.writeToNBT(new NBTTagList()));
                    else
                        inventory.dropAllItems();

                    entityDropItem(drop, 0.5F);
                    worldObj.playSoundAtEntity(this, "thaumcraft:zap", 0.5F, 1.0F);
                    setDead();
                    return true;
                }
            }
        }

        if (Platform.isServer() && isOwner(player) && !player.isSneaking()) {
            player.openGui(MagiaNaturalis.instance, 3, worldObj, getEntityId(), 0, 0);
            return true;
        }

        return false;
    }

    protected String getHurtSound() {
        return Blocks.log2.stepSound.getStepResourcePath();
    }

    protected String getDeathSound() {
        return "random.break";
    }

    public void setTrunkType(byte id) {
        dataWatcher.updateObject(14, id);
    }

    public byte getTrunkType() {
        return dataWatcher.getWatchableObjectByte(14);
    }

    public boolean isOpen() {
        return dataWatcher.getWatchableObjectByte(15) == 1;
    }

    public void setOpen(boolean par1) {
        dataWatcher.updateObject(15, (byte) (par1 ? 1 : 0));
    }

    public void setUpgrade(byte i) {
        dataWatcher.updateObject(18, i);
    }

    public byte getUpgrade() {
        return dataWatcher.getWatchableObjectByte(18);
    }

    void showHeartsOrSmokeFX(boolean flag) {
        String s = "heart";
        int amount = 1;
        if (!flag) {
            s = "explode";
            amount = 7;
        }
        for (int i = 0; i < amount; i++) {
            double d = rand.nextGaussian() * 0.02D;
            double d1 = rand.nextGaussian() * 0.02D;
            double d2 = rand.nextGaussian() * 0.02D;
            worldObj.spawnParticle(s, posX + rand.nextFloat() * width * 2.0F - width, posY + 0.5D + rand.nextFloat() * height, posZ + rand.nextFloat() * width * 2.0F - width, d, d1, d2);
        }
    }

    @SideOnly(Side.CLIENT)
    public void handleHealthUpdate(byte par1) {
        if (par1 == 17) {
            skullrot = 0.15F;
        }
        else if (par1 == 18) {
            skullrot = 0.15F;
            showHeartsOrSmokeFX(true);
        }
        else {
            super.handleHealthUpdate(par1);
        }
    }
}
