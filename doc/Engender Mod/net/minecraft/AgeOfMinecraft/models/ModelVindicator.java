package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityVindicator;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelIllager;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelVindicator extends ModelIllager implements ICappedModel {
  public ModelRenderer bipedCape;
  
  public ModelVindicator(float scaleFactor) {
    this(scaleFactor, 0.0F, 64, 64);
    this.bipedCape = new ModelRenderer((ModelBase)this, 0, 0);
    this.bipedCape.func_78787_b(64, 32);
    this.bipedCape.func_78790_a(-5.0F, 0.0F, -1.0F, 10, 16, 1, scaleFactor);
  }
  
  public ModelVindicator(float scaleFactor, float p_i47223_2_, int textureWidthIn, int textureHeightIn) {
    super(scaleFactor, p_i47223_2_, textureWidthIn, textureHeightIn);
  }
  
  public void renderCape(float scale, float flo1, float flo2, float flo3) {
    GlStateManager.func_179094_E();
    GlStateManager.func_179109_b(0.0F, 0.0F, 0.0625F);
    GlStateManager.func_179114_b(6.0F + flo2 / 2.0F + flo1, 1.0F, 0.0F, 0.0F);
    GlStateManager.func_179114_b(flo3 / 2.0F, 0.0F, 0.0F, 1.0F);
    GlStateManager.func_179114_b(-flo3 / 2.0F, 0.0F, 1.0F, 0.0F);
    GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
    this.bipedCape.func_78785_a(scale);
    GlStateManager.func_179121_F();
  }
  
  public void func_78088_a(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    super.func_78088_a(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    EntityVindicator entityvindicator = (EntityVindicator)entityIn;
    if (entityvindicator.isAggressive() || !entityvindicator.getCurrentBook().func_190926_b() || entityvindicator.func_70027_ad() || (!entityvindicator.func_70089_S() && !entityvindicator.field_70122_E)) {
      this.field_191223_g.func_78785_a(scale);
      this.field_191224_h.func_78785_a(scale);
    } else {
      this.field_191219_c.func_78785_a(scale);
    } 
  }
  
  public void func_78087_a(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    super.func_78087_a(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    float f = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F);
    float f1 = MathHelper.func_76126_a((1.0F - (1.0F - this.field_78095_p) * (1.0F - this.field_78095_p)) * 3.1415927F);
    this.field_191223_g.field_78808_h = 0.0F;
    this.field_191224_h.field_78808_h = 0.0F;
    this.field_191223_g.field_78796_g = 0.15707964F;
    this.field_191224_h.field_78796_g = -0.15707964F;
    if (((EntityLivingBase)entityIn).func_184591_cq() == EnumHandSide.RIGHT) {
      this.field_191223_g.field_78795_f = -1.8849558F + MathHelper.func_76134_b(ageInTicks * 0.09F) * 0.15F;
      this.field_191224_h.field_78795_f = -0.0F + MathHelper.func_76134_b(ageInTicks * 0.19F) * 0.5F;
      this.field_191223_g.field_78795_f += f * 2.2F - f1 * 0.4F;
      this.field_191224_h.field_78795_f += f * 1.2F - f1 * 0.4F;
    } else {
      this.field_191223_g.field_78795_f = -0.0F + MathHelper.func_76134_b(ageInTicks * 0.19F) * 0.5F;
      this.field_191224_h.field_78795_f = -1.8849558F + MathHelper.func_76134_b(ageInTicks * 0.09F) * 0.15F;
      this.field_191223_g.field_78795_f += f * 1.2F - f1 * 0.4F;
      this.field_191224_h.field_78795_f += f * 2.2F - f1 * 0.4F;
    } 
    this.field_191223_g.field_78808_h += MathHelper.func_76134_b(ageInTicks * 0.09F) * 0.05F + 0.05F;
    this.field_191224_h.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.09F) * 0.05F + 0.05F;
    this.field_191223_g.field_78795_f += MathHelper.func_76126_a(ageInTicks * 0.067F) * 0.05F;
    this.field_191224_h.field_78795_f -= MathHelper.func_76126_a(ageInTicks * 0.067F) * 0.05F;
    this.field_191219_c.field_78795_f = -0.75F;
    if (((EntityVindicator)entityIn).func_70027_ad() || (!((EntityVindicator)entityIn).func_70089_S() && !((EntityVindicator)entityIn).field_70122_E)) {
      this.field_191217_a.field_78795_f -= 0.5F;
      this.field_191217_a.field_78796_g += MathHelper.func_76134_b(ageInTicks * 0.6662F) * 0.5F;
      this.field_191223_g.field_78798_e = 0.0F;
      this.field_191223_g.field_78800_c = -5.0F;
      this.field_191224_h.field_78798_e = 0.0F;
      this.field_191224_h.field_78800_c = 5.0F;
      this.field_191223_g.field_78795_f = -MathHelper.func_76134_b(ageInTicks * 0.6662F) * 0.5F;
      this.field_191224_h.field_78795_f = MathHelper.func_76134_b(ageInTicks * 0.6662F) * 0.5F;
      this.field_191223_g.field_78808_h = 2.3561945F;
      this.field_191224_h.field_78808_h = -2.3561945F;
      this.field_191223_g.field_78796_g = 0.0F;
      this.field_191224_h.field_78796_g = 0.0F;
    } 
    if (((EntityVindicator)entityIn).getJukeboxToDanceTo() != null) {
      float fl = MathHelper.func_76126_a(ageInTicks * 0.5F) * 0.7F;
      this.field_191217_a.func_78793_a(0.0F + MathHelper.func_76134_b(ageInTicks * 0.25F) * 1.0F, 0.5F - fl, 0.0F);
      this.field_191219_c.field_78795_f += MathHelper.func_76134_b(ageInTicks * 0.25F) * 0.25F;
    } 
    if (!((EntityVindicator)entityIn).getCurrentBook().func_190926_b()) {
      this.field_191223_g.field_78796_g = ((EntityVindicator)entityIn).bookSpread - 1.0F;
      this.field_191224_h.field_78796_g = -((EntityVindicator)entityIn).bookSpread + 1.0F;
      this.field_191223_g.field_78808_h = 0.0F;
      this.field_191224_h.field_78808_h = 0.0F;
      this.field_191223_g.field_78795_f = -1.5F + 0.1F + MathHelper.func_76126_a(entityIn.field_70173_aa * 0.1F) * 0.01F;
      this.field_191224_h.field_78795_f = -1.5F + 0.1F + MathHelper.func_76126_a(entityIn.field_70173_aa * 0.1F) * 0.01F;
    } 
  }
}
