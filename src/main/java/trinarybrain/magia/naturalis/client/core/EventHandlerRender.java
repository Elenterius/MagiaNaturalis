package trinarybrain.magia.naturalis.client.core;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.api.nodes.INode;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.tiles.TileOwned;
import thaumcraft.common.tiles.TileTubeBuffer;
import trinarybrain.magia.naturalis.api.IRevealInvisible;
import trinarybrain.magia.naturalis.api.ISpectacles;
import trinarybrain.magia.naturalis.common.core.Log;
import trinarybrain.magia.naturalis.common.item.artifact.ItemSpectacles;
import trinarybrain.magia.naturalis.common.tile.TileArcaneChest;
import trinarybrain.magia.naturalis.common.util.Platform;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EventHandlerRender
{
	public Minecraft mc = Minecraft.getMinecraft();
	
	public static void register()
	{
		MinecraftForge.EVENT_BUS.register(new EventHandlerRender());
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent event)
	{
		if(event.type == RenderGameOverlayEvent.ElementType.HELMET)
		{
			if((mc.getMinecraft().renderViewEntity instanceof EntityPlayer))
			{
				EntityPlayer player = mc.thePlayer;
				if((player != null) && (mc.isGuiEnabled()))
				{
					ItemStack stack = player.inventory.armorItemInSlot(3);
					if(stack != null && stack.getItem() instanceof ISpectacles && ((ISpectacles) stack.getItem()).drawSpectacleHUD(stack, player))
					{
						this.renderSpectaclesHUD(mc, player);
					}
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	protected void renderSpectaclesHUD(Minecraft mc, EntityPlayer player)
	{
		Boolean meterEquiped = false;
		if(player.inventory.getCurrentItem() != null)
		{
			if(player.inventory.getCurrentItem().getItem() == ConfigItems.itemThaumometer)
			{
				meterEquiped = true;
			}
		}

		if(mc.gameSettings.thirdPersonView == 0 && mc.currentScreen == null && !meterEquiped)
		{
			TileEntity tile = null;		
			MovingObjectPosition mop = mc.objectMouseOver;
			if(mop != null)
			{
				if(mop.typeOfHit == MovingObjectType.BLOCK)
				{
					tile = player.worldObj.getTileEntity(mop.blockX, mop.blockY, mop.blockZ);
				}
			}
			
			if(tile != null)
			{
				ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
				int w = scaledresolution.getScaledWidth();
				int h = scaledresolution.getScaledHeight();

				if(tile instanceof IEssentiaTransport)
				{
					IEssentiaTransport essentiaTransport = (IEssentiaTransport) tile;
					ForgeDirection face = ForgeDirection.getOrientation(mop.sideHit);
					String t = "";

					if(!(tile instanceof TileTubeBuffer) && essentiaTransport.getEssentiaType(face) != null)
					{
						t = new ChatComponentTranslation("tc.resonator1", new Object[] {"" + essentiaTransport.getEssentiaAmount(face), essentiaTransport.getEssentiaType(face).getName()}).getFormattedText();
					}
					else if(tile instanceof TileTubeBuffer && ((IAspectContainer) tile).getAspects().size() > 0)
					{
						for(Aspect aspect : ((IAspectContainer) tile).getAspects().getAspectsSorted())
						{
							t = new ChatComponentTranslation("tc.resonator1", new Object[] {"" + ((IAspectContainer) tile).getAspects().getAmount(aspect), aspect.getName()}).getFormattedText();
						}
					}

					String s = StatCollector.translateToLocal("tc.resonator3");
					if(essentiaTransport.getSuctionType(face) != null)
						s = essentiaTransport.getSuctionType(face).getName();
					s = new ChatComponentTranslation("tc.resonator2", new Object[] {"" + essentiaTransport.getSuctionAmount(face), s}).getFormattedText();

					FontRenderer fontRenderer = mc.fontRenderer;
					
					GL11.glPushMatrix();
					GL11.glTranslatef(w / 2, h / 2, 0F);

					fontRenderer.drawStringWithShadow(t, -fontRenderer.getStringWidth(t) / 2 + 1, 25, 16777215);
					fontRenderer.drawStringWithShadow(s, -fontRenderer.getStringWidth(s) / 2 + 1, 35, 16777215);

					GL11.glPopMatrix();
				}
				else if(tile instanceof INode)
				{
					INode node = (INode) tile;
					
					String t = Platform.translate("nodetype." + node.getNodeType() + ".name");
					if(node.getNodeModifier() != null)
						t = t + ", " + Platform.translate(new StringBuilder().append("nodemod.").append(node.getNodeModifier()).append(".name").toString());

					String d = Platform.translate("tile.blockAiry.0.name");
					String v = " ";

					if(node.getAspectsBase() != null && node.getAspectsBase().size() > 0)
			        {
						for(Aspect a : node.getAspectsBase().getAspects())
						{
							if(a != null)
								v += a.getName() + "Max:§5 " + Integer.toString(node.getAspectsBase().getAmount(a)) + "§r ";
						}
			        }
					
					String msg = "I'm a happy little Aura Node, happy... happy...";
					FontRenderer fontRenderer = mc.fontRenderer;

					int offset = 0;

					GL11.glPushMatrix();
					GL11.glTranslatef(w / 2, h / 2, 0F);

					if((d.length() & 1) != 0)
					{
						offset = 1;
					}
					fontRenderer.drawStringWithShadow(d, -fontRenderer.getStringWidth(d) / 2 + offset, 10, 100100);
					offset = 1;

					GL11.glPushMatrix();
					GL11.glScaled(0.8D, 0.8D, 0.8D);
					fontRenderer.drawStringWithShadow(t, -fontRenderer.getStringWidth(t) / 2 + offset, 25, 15751100);
					fontRenderer.drawStringWithShadow(v, -fontRenderer.getStringWidth(v) / 2 + offset, 35, 15651100);
					GL11.glPopMatrix();

					GL11.glPushMatrix();
					GL11.glScaled(0.5D, 0.5D, 0.5D);
					mc.ingameGUI.drawString(fontRenderer, msg, -fontRenderer.getStringWidth(msg) / 2 + offset, 75, 16777215);
					GL11.glPopMatrix();

					GL11.glPopMatrix();
				}
				else if(tile instanceof TileOwned)
				{
					TileOwned owned = (TileOwned) tile;
					
					String owner = "Owner: " + owned.owner;
					ArrayList<String> accessList = owned.accessList;
					
					FontRenderer fontRenderer = mc.fontRenderer;
					
					GL11.glPushMatrix();
					GL11.glTranslatef(w / 2, h / 2, 0F);
					
					fontRenderer.drawStringWithShadow(owner, -fontRenderer.getStringWidth(owner) / 2, 10, 100100);
					
					int offset = 0;
					for(String str : accessList)
					{
						fontRenderer.drawStringWithShadow(str, -fontRenderer.getStringWidth(str) / 2, 15 + 10 * offset, 16777215);
						offset++;
					}
					
					GL11.glPopMatrix();
				}
				else if(tile instanceof TileArcaneChest)
				{
					TileArcaneChest chest = (TileArcaneChest) tile;
				}
			}
		}
	}

//	@SideOnly(Side.CLIENT)
//	@SubscribeEvent
//	public void renderParticlesForInvisible(RenderLivingEvent.Post event)
//	{
//		if(event.entity.isInvisible() && mc.getMinecraft().renderViewEntity instanceof EntityPlayer)
//		{
//			EntityPlayer player = mc.thePlayer;
//			if(!event.entity.getUniqueID().equals(player.getUniqueID()))
//			{
//				ItemStack stack = player.inventory.armorItemInSlot(3);
//				if(stack != null && stack.getItem() instanceof IRevealInvisible)
//				{
//					if(event.entity.isInvisibleToPlayer(player) && ((IRevealInvisible) stack.getItem()).showInvisibleEntity(stack, player, event.entity))
//					{
//						
//						Coremodding is easy if you have two things:
//							<Itaros> 1)Compiled code before transformation
//							<Itaros> 2)Compile code with change
//							<Itaros> Then you you cut similar parts off
//							<Itaros> And write a routine to go to requered frame and replace it
//							<Itaros> But:
//							<Itaros> There is a twist. Frame size is tricky to calculate properly, because it envolves deep knowledge of JVM
//							<Itaros> It is much easier to replace method
//							<Itaros> instead of injecting something into it :)
//							<Itaros> You can look at CL coremod. It injects new methods and changes something. I don't actually recall what it does XDDD
//							
//						event.entity.setInvisible(false);
//					}
//				}
//			}
//		}
//	}
}
