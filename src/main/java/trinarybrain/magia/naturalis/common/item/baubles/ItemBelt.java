package trinarybrain.magia.naturalis.common.item.baubles;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.aspects.Aspect;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class ItemBelt extends BaseBauble implements IVisDiscountGear
{
	public IIcon[] icon = new IIcon[4];
	public String name[] = new String[] {"absorb", "reverse", "anchor", "thorn", "vileThorn"};

	public ItemBelt()
	{
		super();
	}

	@Override @SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister)
	{
		icon[0] = iconRegister.registerIcon("thaumcraft:bauble_belt");
		icon[1] = iconRegister.registerIcon("thaumcraft:runic_girdle");
		icon[2] = iconRegister.registerIcon("thaumcraft:runic_girdle_kinetic");
		icon[3] = iconRegister.registerIcon("thaumcraft:hovergirdle");
	}

	@Override @SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int par1)
	{
		return par1 < 4 ? this.icon[par1] : this.icon[1];
	}

	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		return super.getUnlocalizedName() + "." + name[par1ItemStack.getItemDamage()];
	}

	@Override @SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
		list.add(new ItemStack(item, 1, 2));
		list.add(new ItemStack(item, 1, 3));
		list.add(new ItemStack(item, 1, 4));
	}

	@Override
	public int getVisDiscount(ItemStack stack, EntityPlayer player,	Aspect aspect)
	{
		return 0;
	}

	@Override
	public BaubleType getBaubleType(ItemStack stack)
	{
		return BaubleType.BELT;
	}

	@Override
	public EnumRarity getRarity(ItemStack stack)
	{
		return EnumRarity.rare;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player)
	{
		if(itemstack.getItemDamage() == 0 && !player.isPotionActive(Potion.field_76444_x)) // Absorb Belt
		{
			int d = 80;
			int p = 0;
			if(player.getHealth() <= 5.0)
			{
				d = 45; p = 1;
			}

			if(player.getHealth() < 8.0) player.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, d, p));
		}

		if(itemstack.getItemDamage() == 1) // Reverse Belt
		{
			EntityPlayer player2 = (EntityPlayer) player;
			if(player2 != null && player2.isSneaking())
			{
				player.motionY = 0;
			}
			else if(player.posY <= 256)
			{
				player.motionY += 0.1;
			}
		}

		if(itemstack.getItemDamage() == 2) // Anchor Belt
		{
			EntityPlayer player2 = (EntityPlayer) player;
			if(player2 != null && !player2.isSneaking())
			{
				player.motionY = 0.0;
			}
			player.fallDistance = 0;
		}

		if(itemstack.getItemDamage() == 3) // Thorn Belt
		{
			if(player.getAITarget() != null && player.hurtTime > 0)
			{
				player.getAITarget().attackEntityFrom(DamageSource.causeThornsDamage(player), (float)1 + player.getRNG().nextInt(3));
				player.getAITarget().playSound("damage.thorns", 0.5F, 1.0F);
			}
		}

		if(itemstack.getItemDamage() == 4) // Vile Thorn Belt
		{
			if(player.getAITarget() != null && player.hurtTime > 0)
			{
				List list = player.worldObj.selectEntitiesWithinAABB(EntityLiving.class, player.boundingBox.expand(3.0D, 3.0D, 3.0D), IMob.mobSelector);
				for(Object ob : list)
				{
					if(ob instanceof EntityLiving)
					{
						((EntityLiving)ob).attackEntityFrom(DamageSource.causeThornsDamage(player), (float)1 + player.getRNG().nextInt(3));
						((EntityLiving)ob).playSound("damage.thorns", 0.5F, 1.0F);
					}
				}
			}
		}
	}

	@Override
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player)
	{
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player)
	{
		return true;
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player)
	{				
		if (!player.worldObj.isRemote)
		{
			player.worldObj.playSoundAtEntity(player, "random.orb", 0.1F, 1.3f);
		}
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player)
	{

	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4)
	{
		list.add("");

		switch(itemstack.getItemDamage())
		{
		case 0:
			list.add(EnumChatFormatting.BLUE + "Emergency Absorbtion");
			break;

		case 1:
			list.add(EnumChatFormatting.BLUE + "Gravitational Reverser");
			break;

		case 2:
			list.add(EnumChatFormatting.BLUE + "Gravitational Anchor");
			break;

		case 3:
			list.add(EnumChatFormatting.BLUE + "Thorny");
			break;

		case 4:
			list.add(EnumChatFormatting.BLUE + "Vile Thorns");
		}		
	}
}
