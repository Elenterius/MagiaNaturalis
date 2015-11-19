package trinarybrain.magia.naturalis.coremod;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import trinarybrain.api.IRevealInvisible;

public class MethodStub
{

	public static boolean showInvisibleEntityToPlayer(EntityLivingBase entity)
	{
		boolean visibleToPlayer = !entity.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer);
		if(visibleToPlayer)
		{
			return true;
		}
		else if(!visibleToPlayer)
		{
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			if(player == null) return false;

			ItemStack stack = player.inventory.armorInventory[3];

			if(stack != null && stack.getItem() instanceof IRevealInvisible)
			{
				return ((IRevealInvisible)stack.getItem()).showInvisibleEntity(stack, player, entity);
			}
		}

		return false;
	}
}
