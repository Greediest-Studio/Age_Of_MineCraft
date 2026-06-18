package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySpider;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelSpider extends ModelBase {
  public ModelRenderer spiderHead;
  
  public ModelRenderer spiderNeck;
  
  public ModelRenderer spiderBody;
  
  public ModelRenderer spiderLeg1;
  
  public ModelRenderer spiderLeg2;
  
  public ModelRenderer spiderLeg3;
  
  public ModelRenderer spiderLeg4;
  
  public ModelRenderer spiderLeg5;
  
  public ModelRenderer spiderLeg6;
  
  public ModelRenderer spiderLeg7;
  
  public ModelRenderer spiderLeg8;
  
  public ModelSpider() {
    float f = 0.0F;
    int i = 15;
    this.spiderHead = new ModelRenderer(this, 32, 4);
    this.spiderHead.func_78790_a(-4.0F, -4.0F, -8.0F, 8, 8, 8, f);
    this.spiderHead.func_78793_a(0.0F, i, -3.0F);
    this.spiderNeck = new ModelRenderer(this, 0, 0);
    this.spiderNeck.func_78790_a(-3.0F, -3.0F, -3.0F, 6, 6, 6, f);
    this.spiderNeck.func_78793_a(0.0F, i, 0.0F);
    this.spiderBody = new ModelRenderer(this, 0, 12);
    this.spiderBody.func_78790_a(-5.0F, -4.0F, -6.0F, 10, 8, 12, f);
    this.spiderBody.func_78793_a(0.0F, i, 9.0F);
    this.spiderLeg1 = new ModelRenderer(this, 18, 0);
    this.spiderLeg1.func_78790_a(-15.0F, -1.0F, -1.0F, 16, 2, 2, f);
    this.spiderLeg1.func_78793_a(-4.0F, i, 2.0F);
    this.spiderLeg2 = new ModelRenderer(this, 18, 0);
    this.spiderLeg2.func_78790_a(-1.0F, -1.0F, -1.0F, 16, 2, 2, f);
    this.spiderLeg2.func_78793_a(4.0F, i, 2.0F);
    this.spiderLeg3 = new ModelRenderer(this, 18, 0);
    this.spiderLeg3.func_78790_a(-15.0F, -1.0F, -1.0F, 16, 2, 2, f);
    this.spiderLeg3.func_78793_a(-4.0F, i, 1.0F);
    this.spiderLeg4 = new ModelRenderer(this, 18, 0);
    this.spiderLeg4.func_78790_a(-1.0F, -1.0F, -1.0F, 16, 2, 2, f);
    this.spiderLeg4.func_78793_a(4.0F, i, 1.0F);
    this.spiderLeg5 = new ModelRenderer(this, 18, 0);
    this.spiderLeg5.func_78790_a(-15.0F, -1.0F, -1.0F, 16, 2, 2, f);
    this.spiderLeg5.func_78793_a(-4.0F, i, 0.0F);
    this.spiderLeg6 = new ModelRenderer(this, 18, 0);
    this.spiderLeg6.func_78790_a(-1.0F, -1.0F, -1.0F, 16, 2, 2, f);
    this.spiderLeg6.func_78793_a(4.0F, i, 0.0F);
    this.spiderLeg7 = new ModelRenderer(this, 18, 0);
    this.spiderLeg7.func_78790_a(-15.0F, -1.0F, -1.0F, 16, 2, 2, f);
    this.spiderLeg7.func_78793_a(-4.0F, i, -1.0F);
    this.spiderLeg8 = new ModelRenderer(this, 18, 0);
    this.spiderLeg8.func_78790_a(-1.0F, -1.0F, -1.0F, 16, 2, 2, f);
    this.spiderLeg8.func_78793_a(4.0F, i, -1.0F);
  }
  
  public void func_78088_a(Entity entityIn, float p_78088_2_, float limbSwing, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    func_78087_a(p_78088_2_, limbSwing, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
    this.spiderHead.func_78785_a(scale);
    this.spiderNeck.func_78785_a(scale);
    this.spiderBody.func_78785_a(scale);
    this.spiderLeg1.func_78785_a(scale);
    this.spiderLeg2.func_78785_a(scale);
    this.spiderLeg3.func_78785_a(scale);
    this.spiderLeg4.func_78785_a(scale);
    this.spiderLeg5.func_78785_a(scale);
    this.spiderLeg6.func_78785_a(scale);
    this.spiderLeg7.func_78785_a(scale);
    this.spiderLeg8.func_78785_a(scale);
  }
  
  public void func_78087_a(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    float swing = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F);
    this.spiderHead.field_78796_g = netHeadYaw * 0.017453292F;
    this.spiderHead.field_78795_f = headPitch * 0.017453292F + swing;
    float f = 0.7853982F;
    this.spiderLeg1.field_78808_h = -f;
    this.spiderLeg2.field_78808_h = f;
    this.spiderLeg3.field_78808_h = -f * 0.74F;
    this.spiderLeg4.field_78808_h = f * 0.74F;
    this.spiderLeg5.field_78808_h = -f * 0.74F;
    this.spiderLeg6.field_78808_h = f * 0.74F;
    this.spiderLeg7.field_78808_h = -f - swing;
    this.spiderLeg8.field_78808_h = f + swing;
    float f1 = -0.0F;
    float f2 = 0.3926991F;
    this.spiderLeg1.field_78796_g = f2 * 2.0F + f1;
    this.spiderLeg2.field_78796_g = -f2 * 2.0F - f1;
    this.spiderLeg3.field_78796_g = f2 * 1.0F + f1;
    this.spiderLeg4.field_78796_g = -f2 * 1.0F - f1;
    this.spiderLeg5.field_78796_g = -f2 * 1.0F + f1;
    this.spiderLeg6.field_78796_g = f2 * 1.0F - f1;
    this.spiderLeg7.field_78796_g = -f2 * 2.0F + f1 - swing;
    this.spiderLeg8.field_78796_g = f2 * 2.0F - f1 + swing;
    EntitySpider entity = (EntitySpider)entityIn;
    float f3 = -(MathHelper.func_76134_b(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbSwingAmount;
    float f4 = -(MathHelper.func_76134_b(limbSwing * 0.6662F * 2.0F + 3.1415927F) * 0.4F) * limbSwingAmount;
    float f5 = -(MathHelper.func_76134_b(limbSwing * 0.6662F * 2.0F + 1.5707964F) * 0.4F) * limbSwingAmount;
    float f6 = -(MathHelper.func_76134_b(limbSwing * 0.6662F * 2.0F + 4.712389F) * 0.4F) * limbSwingAmount;
    float f7 = Math.abs(MathHelper.func_76126_a(limbSwing * 0.6662F + 0.0F) * 0.4F) * limbSwingAmount;
    float f8 = Math.abs(MathHelper.func_76126_a(limbSwing * 0.6662F + 3.1415927F) * 0.4F) * limbSwingAmount;
    float f9 = Math.abs(MathHelper.func_76126_a(limbSwing * 0.6662F + 1.5707964F) * 0.4F) * limbSwingAmount;
    float f10 = Math.abs(MathHelper.func_76126_a(limbSwing * 0.6662F + 4.712389F) * 0.4F) * limbSwingAmount;
    this.spiderLeg1.field_78796_g += f3;
    this.spiderLeg2.field_78796_g += -f3;
    this.spiderLeg3.field_78796_g += f4;
    this.spiderLeg4.field_78796_g += -f4;
    this.spiderLeg5.field_78796_g += f5;
    this.spiderLeg6.field_78796_g += -f5;
    this.spiderLeg7.field_78796_g += f6;
    this.spiderLeg8.field_78796_g += -f6;
    this.spiderLeg1.field_78808_h += f7;
    this.spiderLeg2.field_78808_h += -f7;
    this.spiderLeg3.field_78808_h += f8;
    this.spiderLeg4.field_78808_h += -f8;
    this.spiderLeg5.field_78808_h += f9;
    this.spiderLeg6.field_78808_h += -f9;
    this.spiderLeg7.field_78808_h += f10;
    this.spiderLeg8.field_78808_h += -f10;
    if (entity.func_70093_af()) {
      this.spiderLeg1.field_78796_g += 0.2F;
      this.spiderLeg2.field_78796_g += -0.2F;
      this.spiderLeg3.field_78796_g += 0.2F;
      this.spiderLeg4.field_78796_g += -0.2F;
      this.spiderLeg5.field_78796_g += 0.2F;
      this.spiderLeg6.field_78796_g += -0.2F;
      this.spiderLeg7.field_78796_g += 0.2F;
      this.spiderLeg8.field_78796_g += -0.2F;
      this.spiderLeg1.field_78808_h -= f7 + -0.35F;
      this.spiderLeg2.field_78808_h -= -f7 + 0.35F;
      this.spiderLeg3.field_78808_h -= f8 + -0.3F;
      this.spiderLeg4.field_78808_h -= -f8 + 0.3F;
      this.spiderLeg5.field_78808_h -= f9 + -0.3F;
      this.spiderLeg6.field_78808_h -= -f9 + 0.3F;
      this.spiderLeg7.field_78808_h -= f10 + -0.35F;
      this.spiderLeg8.field_78808_h -= -f10 + 0.35F;
    } 
    this.spiderHead.field_78795_f += MathHelper.func_76134_b(ageInTicks * 0.1F) * 0.0125F * 3.1415927F;
    if (entity.func_70027_ad() || (!entity.func_70089_S() && !entity.field_70122_E)) {
      this.spiderHead.field_78795_f -= 0.5F;
      this.spiderHead.field_78796_g += MathHelper.func_76134_b(ageInTicks * 0.6662F) * 0.5F;
    } 
    if (entity.getJukeboxToDanceTo() != null) {
      float fl = MathHelper.func_76126_a(ageInTicks * 0.5F) * 0.7F;
      this.spiderLeg7.field_78808_h += -fl - MathHelper.func_76134_b(ageInTicks * 0.25F) * 0.5F;
      this.spiderLeg8.field_78808_h += fl + MathHelper.func_76134_b(ageInTicks * 0.25F) * 0.5F;
      this.spiderHead.func_78793_a(0.0F + MathHelper.func_76134_b(ageInTicks * 0.25F) * 1.0F, 15.0F - fl - MathHelper.func_76134_b(ageInTicks * 0.5F) * 1.0F, -3.0F);
    } 
  }
}
