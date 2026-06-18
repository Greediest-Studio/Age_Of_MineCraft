package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelElytra;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerCustomElytra implements LayerRenderer<EntityTameBase> {
  private static final ResourceLocation TEXTURE_ELYTRA = new ResourceLocation("textures/entity/elytra.png");
  
  protected final RenderLivingBase<?> renderPlayer;
  
  private final ModelElytra modelElytra = new ModelElytra();
  
  public LayerCustomElytra(RenderLivingBase<?> p_i47185_1_) {
    this.renderPlayer = p_i47185_1_;
  }
  
  public void doRenderLayer(EntityTameBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    ItemStack itemstack = entitylivingbaseIn.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
    if (itemstack.getItem() == Items.ELYTRA) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
      if (!entitylivingbaseIn.isWild()) {
        AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer)entitylivingbaseIn.getOwner();
        if (abstractclientplayer.isPlayerInfoSet() && abstractclientplayer.getLocationElytra() != null) {
          this.renderPlayer.bindTexture(abstractclientplayer.getLocationElytra());
        } else if (abstractclientplayer.hasPlayerInfo() && abstractclientplayer.getLocationCape() != null && abstractclientplayer.isWearing(EnumPlayerModelParts.CAPE)) {
          this.renderPlayer.bindTexture(abstractclientplayer.getLocationCape());
        } else {
          this.renderPlayer.bindTexture(TEXTURE_ELYTRA);
        } 
      } else {
        this.renderPlayer.bindTexture(TEXTURE_ELYTRA);
      } 
      GlStateManager.pushMatrix();
      GlStateManager.translate(0.0F, 0.0F, 0.125F);
      this.modelElytra.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, (Entity)entitylivingbaseIn);
      this.modelElytra.render((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      if (itemstack.isItemEnchanted())
        LayerArmorBase.renderEnchantedGlint(this.renderPlayer, (EntityLivingBase)entitylivingbaseIn, (ModelBase)this.modelElytra, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale); 
      GlStateManager.disableBlend();
      GlStateManager.popMatrix();
    } 
  }
  
  public boolean shouldCombineTextures() {
    return false;
  }
}
