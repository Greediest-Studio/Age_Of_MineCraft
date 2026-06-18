package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.models.ICappedModel;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerMobCape implements LayerRenderer<EntityTameBase> {
  private static final String[] NAMES = new String[] { "Umbrella_Ghast", "JadeRabbitTsuki", "Mrbt0907", "Entanos", "GB_Doge_9000", "BeckBroJack", "Milo1133", "4ChanMeta", "TheMCOverlordYT" };
  
  private static final String[] CAPES = new String[] { "textures/cape_ug.png", "textures/cape_jd.png", "textures/cape_bt.png", "textures/cape_en.png", "textures/cape_sh.png", "textures/cape_bbj.png", "textures/cape_m.png", "textures/cape_4c.png", "textures/cape_mc.png" };
  
  private final RenderLivingBase<?> playerRenderer;
  
  public LayerMobCape(RenderLivingBase<?> playerRendererIn) {
    this.playerRenderer = playerRendererIn;
  }
  
  public void doRenderLayer(EntityTameBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    if (!entitylivingbaseIn.isInvisible() && !entitylivingbaseIn.isChild() && this.playerRenderer.getMainModel() instanceof ICappedModel && !entitylivingbaseIn.isWild() && ((AbstractClientPlayer)entitylivingbaseIn.getOwner()).hasPlayerInfo() && ((AbstractClientPlayer)entitylivingbaseIn.getOwner()).isWearing(EnumPlayerModelParts.CAPE) && ((AbstractClientPlayer)entitylivingbaseIn.getOwner()).getLocationCape() != null) {
      ItemStack itemstack = entitylivingbaseIn.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
      String cape = null;
      for (int i = 0; i <= NAMES.length; i++) {
        if (entitylivingbaseIn.getOwner().getName() == NAMES[i])
          cape = CAPES[i]; 
      } 
      if (cape != null && itemstack.getItem() != Items.ELYTRA) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.playerRenderer.bindTexture(new ResourceLocation("ageofminecraft", cape));
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, 0.0F, 0.125F);
        double d0 = entitylivingbaseIn.prevChasingPosX + (entitylivingbaseIn.chasingPosX - entitylivingbaseIn.prevChasingPosX) * partialTicks - entitylivingbaseIn.prevPosX + (entitylivingbaseIn.posX - entitylivingbaseIn.prevPosX) * partialTicks;
        double d1 = entitylivingbaseIn.prevChasingPosY + (entitylivingbaseIn.chasingPosY - entitylivingbaseIn.prevChasingPosY) * partialTicks - entitylivingbaseIn.prevPosY + (entitylivingbaseIn.posY - entitylivingbaseIn.prevPosY) * partialTicks;
        double d2 = entitylivingbaseIn.prevChasingPosZ + (entitylivingbaseIn.chasingPosZ - entitylivingbaseIn.prevChasingPosZ) * partialTicks - entitylivingbaseIn.prevPosZ + (entitylivingbaseIn.posZ - entitylivingbaseIn.prevPosZ) * partialTicks;
        float f = entitylivingbaseIn.prevRenderYawOffset + (entitylivingbaseIn.renderYawOffset - entitylivingbaseIn.prevRenderYawOffset) * partialTicks;
        double d3 = MathHelper.sin(f * 0.017453292F);
        double d4 = -MathHelper.cos(f * 0.017453292F);
        float f1 = (float)d1 * 10.0F;
        f1 = MathHelper.clamp(f1, -6.0F, 32.0F);
        float f2 = (float)(d0 * d3 + d2 * d4) * 100.0F;
        float f3 = (float)(d0 * d4 - d2 * d3) * 100.0F;
        if (f2 < 0.0F)
          f2 = 0.0F; 
        float f4 = entitylivingbaseIn.prevRotationYaw + (entitylivingbaseIn.rotationYaw - entitylivingbaseIn.rotationYaw) * partialTicks;
        f1 += MathHelper.sin((entitylivingbaseIn.prevDistanceWalkedModified + (entitylivingbaseIn.distanceWalkedModified - entitylivingbaseIn.prevDistanceWalkedModified) * partialTicks) * 6.0F) * 32.0F * f4;
        if (entitylivingbaseIn.isSneaking())
          f1 += 25.0F; 
        if (entitylivingbaseIn.isAirBorne)
          f1 -= MathHelper.clamp((float)(entitylivingbaseIn.motionY * Math.PI * 45.0D), -75.0F, 3.0F); 
        if (entitylivingbaseIn.isInvisible())
          f1 = 0.0F; 
        ((ICappedModel)this.playerRenderer.getMainModel()).renderCape(0.0625F, f1, f2, f3);
        GlStateManager.popMatrix();
      } 
    } 
  }
  
  public boolean shouldCombineTextures() {
    return false;
  }
}
