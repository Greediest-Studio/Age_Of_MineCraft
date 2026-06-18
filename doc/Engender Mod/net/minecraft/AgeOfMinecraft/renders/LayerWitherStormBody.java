package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStorm;
import net.minecraft.AgeOfMinecraft.models.ModelCommandBlockWitherBody;
import net.minecraft.AgeOfMinecraft.models.ModelWitherStormBody;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerWitherStormBody implements LayerRenderer<EntityLivingBase> {
  private static final ResourceLocation withersmallTextures = new ResourceLocation("ageofminecraft", "textures/entities/wither_storm_hue_small.png");
  
  private static final ResourceLocation witherTextures = new ResourceLocation("ageofminecraft", "textures/entities/wither_storm_hue.png");
  
  private final ModelBase modelmatter = (ModelBase)new ModelWitherStormBody();
  
  private final ModelBase modelmaterial = (ModelBase)new ModelCommandBlockWitherBody();
  
  private final RenderWitherStorm witherRenderer;
  
  public LayerWitherStormBody(RenderWitherStorm p_i46105_1_) {
    this.witherRenderer = p_i46105_1_;
  }
  
  public void func_177214_a(EntityWitherStorm p_177214_1_, float p_177214_2_, float p_177214_3_, float p_177214_4_, float p_177214_5_, float p_177214_6_, float p_177214_7_, float p_177214_8_) {
    if (p_177214_1_.doesntContainACommandBlock()) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179152_a(3.0F, 3.0F, 3.0F);
      GlStateManager.func_179109_b(0.0F, 0.5F, 0.5F);
      GlStateManager.func_179114_b(180.0F, 1.0F, 0.0F, 0.0F);
      this.witherRenderer.func_110776_a(withersmallTextures);
      this.modelmaterial.func_78088_a((Entity)p_177214_1_, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GlStateManager.func_179121_F();
    } else {
      float size = p_177214_1_.func_110138_aP() * 2.5E-5F;
      GlStateManager.func_179152_a(0.01F + size, 0.01F + size, 0.01F + size);
      GlStateManager.func_179109_b(0.0F, -12.0F - p_177214_1_.func_110138_aP() * 5.0E-7F, 0.0F + p_177214_1_.func_110138_aP() * 6.25E-7F);
      if (p_177214_1_.getSize() > 50000)
        GlStateManager.func_179109_b(0.0F, 4.0F + p_177214_1_.func_110138_aP() * 5.0E-7F, 0.5F + p_177214_1_.func_110138_aP() * 1.0E-5F); 
      if (p_177214_1_.getSize() > 250000)
        GlStateManager.func_179109_b(0.0F, 2.0F - p_177214_1_.func_110138_aP() * 1.0E-7F, 1.0F - p_177214_1_.func_110138_aP() * 1.0E-5F); 
      GlStateManager.func_179114_b(p_177214_1_.func_70093_af() ? -60.0F : -20.0F, 1.0F, 0.0F, 0.0F);
      if (p_177214_1_.func_110138_aP() >= 300000.0F)
        GlStateManager.func_179152_a(1.0F + MathHelper.func_76134_b(p_177214_1_.field_70173_aa + p_177214_4_) * 0.01F, 1.0F + MathHelper.func_76134_b(p_177214_1_.field_70173_aa + p_177214_4_) * 0.01F, 1.0F + MathHelper.func_76126_a(p_177214_1_.field_70173_aa + p_177214_4_) * 0.01F); 
      this.witherRenderer.func_110776_a(witherTextures);
      this.modelmatter.func_78088_a((Entity)p_177214_1_, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
    } 
  }
  
  public boolean func_177142_b() {
    return true;
  }
  
  public void func_177141_a(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
    func_177214_a((EntityWitherStorm)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
  }
}
