package trinarybrain.api;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

/** 
 * Equipped head slot items that implement this class will reveal any Invisible LivingEntity to the player.
 *
 *@author TrinaryBrain
 */
public interface IRevealInvisible
{
	/*
	 * If this method returns true Invisible Entities will be visible.
	 */
	public boolean showInvisibleEntity(ItemStack itemstack, EntityLivingBase player, EntityLivingBase entityInvisible);
}
