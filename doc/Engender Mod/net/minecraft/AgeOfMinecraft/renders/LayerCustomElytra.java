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
    ItemStack itemstack = entitylivingbaseIn.func_184582_a(EntityEquipmentSlot.CHEST);
    if (itemstack.func_77973_b() == Items.field_185160_cR) {
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179147_l();
      GlStateManager.func_187401_a(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
      if (!entitylivingbaseIn.isWild()) {
        AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer)entitylivingbaseIn.getOwner();
        if (abstractclientplayer.func_184833_s() && abstractclientplayer.func_184834_t() != null) {
          this.renderPlayer.func_110776_a(abstractclientplayer.func_184834_t());
        } else if (abstractclientplayer.func_152122_n() && abstractclientplayer.func_110303_q() != null && abstractclientplayer.func_175148_a(EnumPlayerModelParts.CAPE)) {
          this.renderPlayer.func_110776_a(abstractclientplayer.func_110303_q());
        } else {
          this.renderPlayer.func_110776_a(TEXTURE_ELYTRA);
        } 
      } else {
        this.renderPlayer.func_110776_a(TEXTURE_ELYTRA);
      } 
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b(0.0F, 0.0F, 0.125F);
      this.modelElytra.func_78087_a(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, (Entity)entitylivingbaseIn);
      this.modelElytra.func_78088_a((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      if (itemstack.func_77948_v())
        LayerArmorBase.func_188364_a(this.renderPlayer, (EntityLivingBase)entitylivingbaseIn, (ModelBase)this.modelElytra, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale); 
      GlStateManager.func_179084_k();
      GlStateManager.func_179121_F();
    } 
  }
  
  public boolean func_177142_b() {
    return false;
  }
}
