package com.trinarybrain.magianaturalis.common.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import com.trinarybrain.magianaturalis.common.entity.EntityOwnableCreature;

public class AIWait extends EntityAIBase {

    private EntityOwnableCreature taskOwner;
    private boolean isWaiting;

    public AIWait(EntityOwnableCreature entity) {
        this.taskOwner = entity;
        this.setMutexBits(5);
    }

    @Override
    public boolean shouldExecute() {
        if (this.taskOwner.isInWater()) {
            return false;
        }
        else if (!this.taskOwner.onGround) {
            return false;
        }
        else {
            EntityLivingBase entitylivingbase = this.taskOwner.getOwner();
            return entitylivingbase == null ? true : (this.taskOwner.getDistanceSqToEntity(entitylivingbase) < 144.0D && entitylivingbase.getAITarget() != null ? false : this.isWaiting);
        }
    }

    public void startExecuting() {
        this.taskOwner.getNavigator().clearPathEntity();
        this.taskOwner.setWaiting(true);
    }

    public void resetTask() {
        this.taskOwner.setWaiting(false);
    }

    public void setWaiting(boolean par1) {
        this.isWaiting = par1;
    }
}
