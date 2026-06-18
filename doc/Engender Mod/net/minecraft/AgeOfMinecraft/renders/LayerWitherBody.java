package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityCommandBlockWither;
import net.minecraft.AgeOfMinecraft.models.ModelCommandBlockWitherBody;
import net.minecraft.AgeOfMinecraft.models.ModelWitherStormTentecle;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerWitherBody implements LayerRenderer<EntityLivingBase> {
  private static final ResourceLocation witherTextures = new ResourceLocation("ageofminecraft", "textures/entities/wither_storm_hue_small.png");
  
  private final ModelBase modelmatter = (ModelBase)new ModelCommandBlockWitherBody();
  
  private final ModelBase modelsmalltentacle = (ModelBase)new ModelWitherStormTentecle();
  
  private final RenderCommandBlockWither witherRenderer;
  
  public LayerWitherBody(RenderCommandBlockWither p_i46105_1_) {
    this.witherRenderer = p_i46105_1_;
  }
  
  public void func_177214_a(EntityCommandBlockWither p_177214_1_, float p_177214_2_, float p_177214_3_, float p_177214_4_, float p_177214_5_, float p_177214_6_, float p_177214_7_, float p_177214_8_) {
    if (p_177214_1_.getSize() >= 1000) {
      GlStateManager.func_179094_E();
      this.witherRenderer.func_110776_a(witherTextures);
      this.modelmatter.func_78088_a((Entity)p_177214_1_, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GlStateManager.func_179121_F();
    } 
    if (p_177214_1_.getSize() >= 5000) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179152_a(1.51F, 1.51F, 1.51F);
      GlStateManager.func_179109_b(0.0F, 0.0F, 0.45F);
      this.witherRenderer.func_110776_a(witherTextures);
      this.modelmatter.func_78088_a((Entity)p_177214_1_, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GlStateManager.func_179121_F();
    } 
    if (p_177214_1_.getSize() >= 8000) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179152_a(0.05F, 0.05F, 0.05F);
      GlStateManager.func_179109_b(8.0F, -16.0F, 20.0F);
      GlStateManager.func_179114_b(-90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(-30.0F, 1.0F, 0.0F, 0.0F);
      this.witherRenderer.func_110776_a(new ResourceLocation("ageofminecraft", "textures/entities/wither_storm_hue.png"));
      this.modelsmalltentacle.func_78088_a((Entity)p_177214_1_, p_177214_2_, p_177214_3_, p_177214_5_, p_177214_6_, p_177214_7_, p_177214_8_);
      this.modelsmalltentacle.func_78087_a(p_177214_2_, p_177214_3_, p_177214_5_, p_177214_6_, p_177214_7_, p_177214_8_, (Entity)p_177214_1_);
      this.modelsmalltentacle.func_78086_a((EntityLivingBase)p_177214_1_, p_177214_2_, p_177214_3_, p_177214_4_);
      GlStateManager.func_179121_F();
    } 
    if (p_177214_1_.getSize() >= 12250) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179152_a(4.0F, 4.0F, 4.0F);
      GlStateManager.func_179109_b(0.0F, -0.5F, -0.5F);
      this.witherRenderer.func_110776_a(witherTextures);
      this.modelmatter.func_78088_a((Entity)p_177214_1_, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GlStateManager.func_179121_F();
    } 
    if (p_177214_1_.getSize() >= 5250) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b(0.0F, 1.3F, -0.5F);
      GlStateManager.func_179114_b(30.0F, 1.0F, 0.0F, 0.0F);
      this.witherRenderer.func_110776_a(witherTextures);
      this.modelmatter.func_78088_a((Entity)p_177214_1_, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GlStateManager.func_179121_F();
    } 
  }
  
  public boolean func_177142_b() {
    return true;
  }
  
  public void func_177141_a(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
    func_177214_a((EntityCommandBlockWither)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
  }
}
