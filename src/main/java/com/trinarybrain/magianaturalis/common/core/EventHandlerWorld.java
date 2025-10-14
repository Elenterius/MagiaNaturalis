package com.trinarybrain.magianaturalis.common.core;

import com.trinarybrain.magianaturalis.common.entity.EntityEvilTrunk;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public final class EventHandlerWorld {

    private EventHandlerWorld() {}

    public static void register() {
        MinecraftForge.EVENT_BUS.register(new EventHandlerWorld());
    }

    @SubscribeEvent
    public void onEntitySpawn(final EntityJoinWorldEvent event) {
        if (event.entity instanceof EntityCreeper) {
            ((EntityCreeper) event.entity).tasks.addTask(3, new EntityAIAvoidEntity(((EntityCreeper) event.entity), EntityEvilTrunk.class, 6.0F, 1.0D, 1.2D));
        }
    }

}
