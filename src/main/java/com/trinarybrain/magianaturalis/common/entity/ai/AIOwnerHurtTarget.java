package com.trinarybrain.magianaturalis.common.entity.ai;

import com.trinarybrain.magianaturalis.common.entity.EntityOwnableCreature;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;

public class AIOwnerHurtTarget extends EntityAITarget {

    EntityOwnableCreature taskOwner;
    EntityLivingBase ownerTarget;
    private int lastAttackTimer;

    public AIOwnerHurtTarget(EntityOwnableCreature entity) {
        super(entity, false);
        taskOwner = entity;
        setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        EntityLivingBase entityOwner = taskOwner.getOwner();

        if (entityOwner == null) {
            return false;
        }
        else {
            ownerTarget = entityOwner.getLastAttacker();
            int i = entityOwner.getLastAttackerTime();
            return i != lastAttackTimer && isSuitableTarget(ownerTarget, false) && taskOwner.canAttack(ownerTarget, entityOwner);
        }
    }

    @Override
    public void startExecuting() {
        taskOwner.setAttackTarget(ownerTarget);
        EntityLivingBase owner = taskOwner.getOwner();

        if (owner != null) {
            lastAttackTimer = owner.getLastAttackerTime();
        }

        super.startExecuting();
    }

}
