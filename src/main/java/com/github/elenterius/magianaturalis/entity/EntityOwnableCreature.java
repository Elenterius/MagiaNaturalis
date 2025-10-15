package com.github.elenterius.magianaturalis.entity;

import com.github.elenterius.magianaturalis.entity.ai.AIWait;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.world.World;

import java.util.UUID;

public class EntityOwnableCreature extends EntityCreature implements IEntityOwnable {

    protected AIWait aiWait = new AIWait(this);

    public EntityOwnableCreature(World world) {
        super(world);
    }

    protected void entityInit() {
        super.entityInit();
        dataWatcher.addObject(16, (byte) 0);
        dataWatcher.addObject(17, "");
    }

    public void writeEntityToNBT(NBTTagCompound data) {
        super.writeEntityToNBT(data);

        if (getOwnerUUID() == null)
            data.setString("OwnerUUID", "");
        else
            data.setString("OwnerUUID", getOwnerUUID());

        data.setBoolean("Waiting", isWaiting());
    }

    public void readEntityFromNBT(NBTTagCompound data) {
        super.readEntityFromNBT(data);

        String s = "";
        if (data.hasKey("OwnerUUID", 8)) {
            s = data.getString("OwnerUUID");
        }
        else {
            String s1 = data.getString("Owner");
            s = PreYggdrasilConverter.func_152719_a(s1);
        }

        if (!s.isEmpty()) {
            setOwnerUUID(s);
        }

        aiWait.setWaiting(data.getBoolean("Waiting"));
        setWaiting(data.getBoolean("Waiting"));
    }

    public boolean isWaiting() {
        return dataWatcher.getWatchableObjectByte(16) != 0;
    }

    public void setWaiting(boolean bool) {
        dataWatcher.updateObject(16, (byte) (bool ? 1 : 0));
    }

    @Override
    // getOwnerName() from IEntityOwnable
    public String func_152113_b() {
        return dataWatcher.getWatchableObjectString(17);
    }

    /*
     *Wrapper for func_152113_b()
     */
    public String getOwnerUUID() {
        return func_152113_b();
    }

    public void setOwnerUUID(String uuid) {
        dataWatcher.updateObject(17, uuid);
    }

    @Override
    public EntityLivingBase getOwner() {
        try {
            UUID uuid = UUID.fromString(getOwnerUUID());
            return worldObj.func_152378_a(uuid);
        }
        catch (IllegalArgumentException illegalargumentexception) {
            return null;
        }
    }

    public boolean isOwner(EntityLivingBase entity) {
        return entity == getOwner();
    }

    public AIWait getAIWaitObj() {
        return aiWait;
    }

    public Team getTeam() {
        EntityLivingBase owner = getOwner();
        if (owner != null) return owner.getTeam();
        return super.getTeam();
    }

    public boolean isOnSameTeam(EntityLivingBase entity) {
        EntityLivingBase owner = getOwner();
        if (entity == owner) return true;
        if (owner != null) return owner.isOnSameTeam(entity);
        return super.isOnSameTeam(entity);
    }

    public boolean canAttack(EntityLivingBase attacker, EntityLivingBase owner) {
        return true;
    }

}
