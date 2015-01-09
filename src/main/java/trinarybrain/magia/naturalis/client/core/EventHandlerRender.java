package trinarybrain.magia.naturalis.client.core;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.api.nodes.INode;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.tiles.TileOwned;
import thaumcraft.common.tiles.TileTubeBuffer;
import trinarybrain.magia.naturalis.api.ISpectacles;
import trinarybrain.magia.naturalis.client.util.RenderUtil;
import trinarybrain.magia.naturalis.common.core.Log;
import trinarybrain.magia.naturalis.common.item.artifact.ItemGogglesDark;
import trinarybrain.magia.naturalis.common.item.focus.ItemFocusBuild;
import trinarybrain.magia.naturalis.common.tile.TileArcaneChest;
import trinarybrain.magia.naturalis.common.util.FocusBuildHelper;
import trinarybrain.magia.naturalis.common.util.FocusBuildHelper.Meta;
import trinarybrain.magia.naturalis.common.util.Platform;
import trinarybrain.magia.naturalis.common.util.ResourceUtil;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EventHandlerRender
{
	public Minecraft mc = Minecraft.getMinecraft();
	FontRenderer fontRenderer = mc.fontRenderer;
	RenderItem itemRender = new RenderItem();
	ItemStack lastItem = null;
	int lastCount = 0;
	private static final ResourceLocation rlSilk = new ResourceLocation("thaumcraft", "textures/foci/silktouch.png");
	private static final ResourceLocation rlHudFrame = new ResourceLocation(ResourceUtil.DOMAIN, ResourceUtil.PATH_MISC + "frame-9-2-gold.png");

	public static void register()
	{
		MinecraftForge.EVENT_BUS.register(new EventHandlerRender());
	}

	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent event)
	{
		if(event.type == RenderGameOverlayEvent.ElementType.HELMET)
		{
			if(mc.isGuiEnabled() && !mc.isGamePaused() && mc.currentScreen == null && !mc.gameSettings.showDebugInfo)
			{
				if(mc.renderViewEntity != null && mc.renderViewEntity instanceof EntityPlayer)
				{
					EntityPlayer player = (EntityPlayer) mc.renderViewEntity;

					ItemStack stack = player.inventory.armorItemInSlot(3);
					if(stack != null && stack.getItem() instanceof ISpectacles && ((ISpectacles) stack.getItem()).drawSpectacleHUD(stack, player))
					{
						this.renderSpectaclesHUD(mc, player);
					}

					stack = player.inventory.getCurrentItem();
					if(stack != null && stack.getItem() instanceof ItemWandCasting)
					{
						ItemWandCasting wand = (ItemWandCasting) stack.getItem();
						ItemFocusBasic focus = wand.getFocus(stack);
						if(focus != null && focus instanceof ItemFocusBuild)
						{
							ItemStack focusStack = wand.getFocusItem(stack);
							this.renderBuildFocusHUD(focusStack, player);
						}
					}
				}
			}
		}
	}

	private static final ResourceLocation rlGlowingEyes = new ResourceLocation(ResourceUtil.DOMAIN, ResourceUtil.PATH_MODEL + "glowingEyes.png");
	private static ModelBiped modelOverlay = new ModelBiped();
	
	@SubscribeEvent
	public void renderPlayerSpecial(RenderPlayerEvent.Specials.Pre event)
	{
		ModelBiped model = event.renderer.modelBipedMain;
		ItemStack itemstack = event.entityPlayer.inventory.armorItemInSlot(3);
		if(event.renderHelmet && itemstack != null && itemstack.getItem() instanceof ItemGogglesDark)
		{
			GL11.glPushMatrix();
			float f6 = 0.0625F;
						
			model.bipedHead.postRender(f6);
			mc.renderEngine.bindTexture(this.rlGlowingEyes);
			
			GL11.glTranslatef(0.0F, f6, -0.01F);
			GL11.glScalef(1.25F, 1.25F, 1.25F);
			GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
            
            if(event.entityPlayer.isInvisible())
                GL11.glDepthMask(false);
            else
                GL11.glDepthMask(true);
            
            char c0 = 61680;
            int j = c0 % 65536;
            int k = c0 / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j / 1.0F, (float)k / 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			modelOverlay.bipedHead.render(f6);
			GL11.glDisable(GL11.GL_BLEND);
			
			GL11.glPopMatrix();
		}
	}

	//	@SubscribeEvent
	//	public void renderBlockHighlight(DrawBlockHighlightEvent event)
	//	{
	//		int ticks = event.player.ticksExisted;
	//		MovingObjectPosition target = event.target;
	//
	//		if(Thaumcraft.instance.renderEventHandler.wandHandler == null) Thaumcraft.instance.renderEventHandler.wandHandler = new REHWandHandler();
	//
	//		if(target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
	//		{
	//			if(event.player.getHeldItem() != null && event.player.getHeldItem().getItem() instanceof ItemWandCasting)
	//			{
	//				ItemWandCasting wand = (ItemWandCasting) event.player.getHeldItem().getItem();
	//				ItemStack focus = wand.getFocusItem( event.player.getHeldItem());
	//				if(focus.getItem() instanceof ItemFocusBuild)
	//				{
	//					Log.logger.info("DO IT NOW");
	//					if(Thaumcraft.instance.renderEventHandler.wandHandler.handleArchitectOverlay(event.player.getHeldItem(), event, ticks, target))
	//						event.setCanceled(true);
	//				}
	//			}
	//		}
	//	}

	protected void renderBuildFocusHUD(ItemStack focusStack, EntityPlayer player)
	{
		GL11.glClear(GL11.GL_ACCUM);

		Meta meta = FocusBuildHelper.getMeta(focusStack);
		Block pblock = null;
		int pbdata = 0;
		Item item = null;
		ItemStack pickedBlock = null;

		if(meta == Meta.UNIFORM)
		{	
			if(mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectType.BLOCK)
			{
				pblock = player.worldObj.getBlock(mc.objectMouseOver.blockX, mc.objectMouseOver.blockY, mc.objectMouseOver.blockZ);
				pbdata = player.worldObj.getBlockMetadata(mc.objectMouseOver.blockX, mc.objectMouseOver.blockY, mc.objectMouseOver.blockZ);
				item = Item.getItemFromBlock(pblock);
			}

			if(item != null)
			{
				if(pblock == Blocks.double_plant)
					pbdata = pblock.getDamageValue(player.worldObj, mc.objectMouseOver.blockX, mc.objectMouseOver.blockY, mc.objectMouseOver.blockZ);

				pickedBlock = new ItemStack(item, 1, pbdata);
			}

			if(pickedBlock == null && pblock != null)
			{
				if(pblock == Blocks.lit_redstone_ore)
					pickedBlock = new ItemStack(Blocks.redstone_ore);
				else
					pickedBlock = pblock.getPickBlock(mc.objectMouseOver, player.worldObj, mc.objectMouseOver.blockX, mc.objectMouseOver.blockY, mc.objectMouseOver.blockZ);
			}
		}
		else
		{
			int[] i = FocusBuildHelper.getPickedBlock(focusStack);
			pblock = Block.getBlockById(i[0]);
			pbdata = i[1];

			if(pblock != Blocks.air && pblock != null)
			{
				item = Item.getItemFromBlock(pblock);
				pickedBlock = new ItemStack(item, 1, pbdata);
			}
		}

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glTranslatef(45F, 40F, 0F);
		RenderUtil.drawTextureQuad(rlHudFrame, 32, 32);
		if(pickedBlock == null && meta == Meta.UNIFORM)
		{
			GL11.glTranslatef(1.5F, 1F, 0F);
			GL11.glColor4f(1F, 1F, 1F, 0.3F);
			RenderUtil.drawTextureQuad(rlSilk, 30, 30);
			GL11.glColor4f(1F, 1F, 1F, 1F);
		}
		GL11.glPopMatrix();

		if(pickedBlock != null)
		{
			int amount = this.lastCount;
			if(player.inventory.inventoryChanged || !pickedBlock.isItemEqual(this.lastItem))
			{
				amount = 0;
				for(ItemStack is : player.inventory.mainInventory)
				{
					if(is != null && is.isItemEqual(pickedBlock))
					{
						amount += is.stackSize;
					}
				}
				this.lastItem = pickedBlock;
				player.inventory.inventoryChanged = false;
			}
			this.lastCount = amount;

			GL11.glPushMatrix();
			GL11.glTranslatef(49F, 44F, 0F);
			GL11.glScalef(1.5F, 1.5F, 1.5F);

			if(itemRender != null && fontRenderer != null)
				RenderUtil.drawItemStack(itemRender, fontRenderer, pickedBlock, 0, 0);
			GL11.glEnable(GL11.GL_BLEND); //TODO: REMOVE this Hack when TC4 fixes its shaders issues 

			GL11.glPushMatrix();
			String am = "" + amount;
			int sw = fontRenderer.getStringWidth(am);
			GL11.glTranslatef(0.0F, -fontRenderer.FONT_HEIGHT, 500.0F);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			for(int a = -1; a <= 1; a++)
				for(int b = -1; b <= 1; b++)
					if((a == 0 || b == 0) && (a != 0 || b != 0))
						mc.fontRenderer.drawString(am, a + 16 - sw, b + 24, 0);
			mc.fontRenderer.drawString(am, 16 - sw, 24, 16777215);

			if(meta == Meta.UNIFORM)
			{
				GL11.glPushMatrix();
				GL11.glScalef(1.5F, 1.5F, 1.5F);
				GL11.glTranslatef(15F, 15F, 0F);
				RenderUtil.drawTextureQuad(rlSilk, 16, 16);
				GL11.glPopMatrix();
			}

			GL11.glPopMatrix();
			GL11.glPopMatrix();
		}
	}

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
}
