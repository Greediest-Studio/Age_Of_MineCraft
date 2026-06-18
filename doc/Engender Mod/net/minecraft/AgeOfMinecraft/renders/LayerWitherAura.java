package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityWither;
import net.minecraft.AgeOfMinecraft.models.ModelWither;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerWitherAura implements LayerRenderer<EntityLivingBase> {
  private static final ResourceLocation WITHER_ARMOR = new ResourceLocation("textures/entity/wither/wither_armor.png");
  
  private final RenderWither witherRenderer;
  
  private final ModelWither witherModel = new ModelWither(0.5F);
  
  public LayerWitherAura(RenderWither p_i46105_1_) {
    this.witherRenderer = p_i46105_1_;
  }
  
  public void func_177214_a(EntityWither p_177214_1_, float p_177214_2_, float p_177214_3_, float p_177214_4_, float p_177214_5_, float p_177214_6_, float p_177214_7_, float p_177214_8_) {
    if (p_177214_1_.isArmored() && p_177214_1_.func_70089_S()) {
      GlStateManager.func_179132_a(!p_177214_1_.func_82150_aj());
      this.witherRenderer.func_110776_a(WITHER_ARMOR);
      GlStateManager.func_179128_n(5890);
      GlStateManager.func_179096_D();
      float f7 = p_177214_1_.field_70173_aa + p_177214_4_;
      float f8 = MathHelper.func_76134_b(f7 * 0.02F) * 3.0F;
      float f9 = f7 * 0.01F;
      GlStateManager.func_179109_b(f8, f9, 0.0F);
      GlStateManager.func_179128_n(5888);
      GlStateManager.func_179147_l();
      float f10 = 0.5F;
      GlStateManager.func_179131_c(f10, f10, f10, 1.0F);
      GlStateManager.func_179140_f();
      GlStateManager.func_179112_b(1, 1);
      this.witherModel.func_78086_a((EntityLivingBase)p_177214_1_, p_177214_2_, p_177214_3_, p_177214_4_);
      this.witherModel.func_178686_a(this.witherRenderer.func_177087_b());
      this.witherModel.func_78088_a((Entity)p_177214_1_, p_177214_2_, p_177214_3_, p_177214_5_, p_177214_6_, p_177214_7_, p_177214_8_);
      GlStateManager.func_179128_n(5890);
      GlStateManager.func_179096_D();
      GlStateManager.func_179128_n(5888);
      GlStateManager.func_179145_e();
      GlStateManager.func_179084_k();
    } 
  }
  
  public boolean func_177142_b() {
    return false;
  }
  
  public void func_177141_a(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
    func_177214_a((EntityWither)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
  }
}
