package trinarybrain.magia.naturalis.common.entity.ai;

import net.minecraft.entity.ai.EntityAIBase;
import trinarybrain.magia.naturalis.common.entity.EntityPechCustom;

public class AIInteractPlayer extends EntityAIBase
{
    private EntityPechCustom pech;

    public AIInteractPlayer(EntityPechCustom entityPech)
    {
        this.pech = entityPech;
        this.setMutexBits(5);
    }

    public boolean shouldExecute()
    {
        if (!this.pech.isEntityAlive())
        {
            return false;
        }
        else if (this.pech.isInWater())
        {
            return false;
        }
        else if (!this.pech.onGround)
        {
            return false;
        }
        else if (this.pech.velocityChanged)
        {
            return false;
        }
        
        return this.pech.isInteracting;
    }

    public void startExecuting()
    {
        this.pech.getNavigator().clearPathEntity();
    }

    public void resetTask()
    {
    	this.pech.isInteracting = false;
    }
}
