package com.trinarybrain.magianaturalis.coremod;

import com.trinarybrain.api.IRevealInvisible;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

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
