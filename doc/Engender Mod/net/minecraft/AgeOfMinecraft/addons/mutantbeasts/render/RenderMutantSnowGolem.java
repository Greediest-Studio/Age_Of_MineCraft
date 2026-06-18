package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.render;

import chumbanotz.mutantbeasts.MutantBeasts;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantSnowGolem;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.model.ModelMutantSnowGolem;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMutantSnowGolem extends RenderLiving<EntityMutantSnowGolem> {
  private static final ResourceLocation TEXTURE = MutantBeasts.getEntityTexture("mutant_snow_golem/mutant_snow_golem");
  
  private static final ResourceLocation PUMPKIN_TEXTURE = MutantBeasts.getEntityTexture("mutant_snow_golem/pumpkin");
  
  private static final ResourceLocation GLOW_TEXTURE = MutantBeasts.getEntityTexture("mutant_snow_golem/glow");
  
  private static final ResourceLocation Anti_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/mutant/anti/anti_mutant_snow_golem.png");
  
  public RenderMutantSnowGolem(RenderManager manager) {
    super(manager, (ModelBase)new ModelMutantSnowGolem(), 0.7F);
    func_177094_a(new GlowLayer());
    func_177094_a(new ThrownBlockLayer());
  }
  
  protected void preRenderCallback(EntityMutantSnowGolem entitylivingbaseIn, float partialTickTime) {
    float fit = entitylivingbaseIn.getFittness();
    GlStateManager.func_179152_a(fit, fit, fit);
    if (entitylivingbaseIn.isHero())
      GlStateManager.func_179152_a(1.05F, 1.05F, 1.05F); 
    if (entitylivingbaseIn.func_70631_g_())
      GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F); 
    if (!entitylivingbaseIn.field_70122_E)
      GlStateManager.func_179114_b(entitylivingbaseIn.prevRotationPitchFalling + (entitylivingbaseIn.rotationPitchFalling - entitylivingbaseIn.prevRotationPitchFalling) * 2.0F - 1.0F, 1.0F, 0.0F, 0.0F); 
    if (entitylivingbaseIn.field_70173_aa <= 21 && entitylivingbaseIn.field_70173_aa > 0) {
      float f5 = (entitylivingbaseIn.field_70173_aa + partialTickTime - 1.0F) / 20.0F * 1.6F;
      f5 = MathHelper.func_76129_c(f5);
      if (f5 > 1.0F)
        f5 = 1.0F; 
      GlStateManager.func_179152_a(f5, f5, f5);
      GlStateManager.func_179114_b(f5 * 90.0F - 90.0F, f5, f5, f5);
    } 
  }
  
  public ModelMutantSnowGolem getMainModel() {
    return (ModelMutantSnowGolem)super.func_177087_b();
  }
  
  protected void renderModel(EntityMutantSnowGolem entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
    super.func_77036_a((EntityLivingBase)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
    if (!entitylivingbaseIn.func_82150_aj() && entitylivingbaseIn.isPumpkinEquipped()) {
      func_110776_a(PUMPKIN_TEXTURE);
      this.field_77045_g.func_78088_a((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
    } 
  }
  
  protected ResourceLocation getEntityTexture(EntityMutantSnowGolem entity) {
    return entity.isAntiMob() ? Anti_TEXTURE : TEXTURE;
  }
  
  @SideOnly(Side.CLIENT)
  class GlowLayer implements LayerRenderer<EntityMutantSnowGolem> {
    public void doRenderLayer(EntityMutantSnowGolem entityIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      if (entityIn.isPumpkinEquipped()) {
        RenderMutantSnowGolem.this.func_110776_a(RenderMutantSnowGolem.GLOW_TEXTURE);
        GlStateManager.func_179140_f();
        OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 61680.0F, 0.0F);
        float f = entityIn.field_70173_aa + partialTicks;
        float f1 = MathHelper.func_76134_b(f * 0.1F);
        float f2 = MathHelper.func_76134_b(f * 0.15F);
        GlStateManager.func_179131_c(1.0F, 0.8F + 0.05F * f2, 0.15F + 0.2F * f1, 1.0F);
        (Minecraft.func_71410_x()).field_71460_t.func_191514_d(true);
        RenderMutantSnowGolem.this.field_77045_g.func_78088_a((Entity)entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        (Minecraft.func_71410_x()).field_71460_t.func_191514_d(false);
        GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
        RenderMutantSnowGolem.this.func_177105_a((EntityLiving)entityIn);
        GlStateManager.func_179145_e();
      } 
    }
    
    public boolean func_177142_b() {
      return false;
    }
  }
  
  @SideOnly(Side.CLIENT)
  class ThrownBlockLayer implements LayerRenderer<EntityMutantSnowGolem> {
    public void doRenderLayer(EntityMutantSnowGolem entityIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      if (entityIn.isThrowing() && entityIn.getThrowingTick() < 7) {
        GlStateManager.func_179091_B();
        GlStateManager.func_179094_E();
        GlStateManager.func_179109_b(0.4F, 0.0F, 0.0F);
        RenderMutantSnowGolem.this.getMainModel().postRenderArm(0.0625F);
        GlStateManager.func_179109_b(0.0F, 0.9F, 0.0F);
        GlStateManager.func_179152_a(-0.8F, -0.8F, 0.8F);
        int i = entityIn.func_70070_b();
        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, j, k);
        GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
        RenderMutantSnowGolem.this.func_110776_a(TextureMap.field_110575_b);
        GlStateManager.func_179109_b(-0.5F, -0.5F, 0.5F);
        Minecraft.func_71410_x().func_175602_ab().func_175016_a((!entityIn.func_184614_ca().func_190926_b() && entityIn.func_184614_ca().func_77973_b() instanceof net.minecraft.item.ItemBlock) ? Block.func_149634_a(entityIn.func_184614_ca().func_77973_b()).func_176223_P() : Blocks.field_150432_aD.func_176223_P(), 1.0F);
        GlStateManager.func_179121_F();
        GlStateManager.func_179101_C();
      } 
    }
    
    public boolean func_177142_b() {
      return false;
    }
  }
}
