package trinarybrain.magia.naturalis.common.item.alchemy;

import java.util.Collection;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import trinarybrain.magia.naturalis.common.MagiaNaturalis;
import trinarybrain.magia.naturalis.common.item.BaseItem;
import trinarybrain.magia.naturalis.common.util.Platform;
import trinarybrain.magia.naturalis.common.util.ResourceUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemQuicksilverStone extends BaseItem
{

	public ItemQuicksilverStone()
	{
		super();
		this.maxStackSize = 1;
	}

	@Override @SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister icon)
	{
		this.itemIcon = icon.registerIcon(ResourceUtil.PREFIX + "quicksilver_stone");
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		super.addInformation(stack, player, list, par4);
		list.add(EnumChatFormatting.DARK_PURPLE + "Of Twisting and Molding Status Effects");
	}

	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{	
		if(Platform.isServer() && stack != null && !player.isSneaking())
		{
			if(MagiaNaturalis.proxyTC4.playerKnowledge.hasDiscoveredAspect(player.getCommandSenderName(), Aspect.EXCHANGE))
			{
				Collection<PotionEffect> collection = player.getActivePotionEffects();
				for(PotionEffect effect : collection)
				{
					if(player.inventory.consumeInventoryItem(Items.glowstone_dust))
					{
						if(effect.getAmplifier() + 1 <= 2)
							player.addPotionEffect(new PotionEffect(effect.getPotionID(), (int) (effect.getDuration() / 3), effect.getAmplifier() + 1));
						else
						{
							player.addPotionEffect(new PotionEffect(Potion.confusion.getId(), 288, 2));
							player.addPotionEffect(new PotionEffect(Potion.poison.getId(), 144, 0));
						}
					}
					else
					{
						player.addPotionEffect(new PotionEffect(Potion.confusion.getId(), 144, 1));
						break;
					}
				}
				player.inventoryContainer.detectAndSendChanges();
			}
			else
			{
				player.addPotionEffect(new PotionEffect(Potion.confusion.getId(), 144, 1));
			}
		}
		return stack;
	}
}
