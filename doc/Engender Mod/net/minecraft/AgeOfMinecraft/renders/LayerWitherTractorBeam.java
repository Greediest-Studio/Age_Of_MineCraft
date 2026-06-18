package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityCommandBlockWither;
import net.minecraft.AgeOfMinecraft.models.ModelCommandBlockWither;
import net.minecraft.AgeOfMinecraft.models.ModelTractorBeam;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerWitherTractorBeam implements LayerRenderer<EntityLivingBase> {
  private static final ResourceLocation witherTextures = new ResourceLocation("ageofminecraft", "textures/entities/tractor_beam.png");
  
  private final ModelBase modelmatter = (ModelBase)new ModelTractorBeam();
  
  private final RenderCommandBlockWither witherRenderer;
  
  public LayerWitherTractorBeam(RenderCommandBlockWither p_i46105_1_) {
    this.witherRenderer = p_i46105_1_;
  }
  
  public void func_177214_a(EntityCommandBlockWither p_177214_1_, float p_177214_2_, float p_177214_3_, float p_177214_4_, float p_177214_5_, float p_177214_6_, float p_177214_7_, float p_177214_8_) {
    if (p_177214_1_.getSize() >= 6000) {
      boolean flag = p_177214_1_.func_82150_aj();
      GlStateManager.func_179132_a(!flag);
      ((ModelCommandBlockWither)this.witherRenderer.func_177087_b()).MainHead.func_78794_c(0.0625F);
      GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179152_a(0.5F, 4.0F, 0.5F);
      GlStateManager.func_179109_b(0.0F, 0.0F, 0.5F);
      this.witherRenderer.func_110776_a(witherTextures);
      GlStateManager.func_179128_n(5890);
      GlStateManager.func_179096_D();
      float f = p_177214_1_.field_70173_aa + p_177214_4_;
      GlStateManager.func_179109_b(0.0F, f * -0.02F, 0.0F);
      GlStateManager.func_179128_n(5888);
      GlStateManager.func_179147_l();
      float f1 = 0.5F;
      GlStateManager.func_179131_c(f1, f1, f1, 1.0F);
      GlStateManager.func_179140_f();
      GlStateManager.func_187401_a(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
      this.modelmatter.func_78088_a((Entity)p_177214_1_, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GlStateManager.func_179128_n(5890);
      GlStateManager.func_179096_D();
      GlStateManager.func_179128_n(5888);
      GlStateManager.func_179145_e();
      GlStateManager.func_179084_k();
      GlStateManager.func_179132_a(flag);
    } 
  }
  
  public boolean func_177142_b() {
    return false;
  }
  
  public void func_177141_a(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
    func_177214_a((EntityCommandBlockWither)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
  }
}
