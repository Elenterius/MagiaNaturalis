package trinarybrain.api;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

/**
 * Equipped head slot items that extend this class will draw info on HUD.
 * 
 * @author TrinaryBrain
 */
public interface ISpectacles
{
	/*
	 * If this method returns true the info will be drawn on the HUD
	 */
	public boolean drawSpectacleHUD(ItemStack itemstack, EntityLivingBase player);
}
