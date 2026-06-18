package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelGiantMagmaGolem extends ModelBase {
  public ModelRenderer LeftLeg1;
  
  public ModelRenderer RightLeg1;
  
  public ModelRenderer Torso;
  
  public ModelRenderer LeftLeg2;
  
  public ModelRenderer RightLeg2;
  
  public ModelRenderer Body;
  
  public ModelRenderer Head;
  
  public ModelRenderer LeftArm1;
  
  public ModelRenderer RightArm1;
  
  public ModelRenderer Nose;
  
  public ModelRenderer LeftArm2;
  
  public ModelRenderer LeftArm3;
  
  public ModelRenderer LeftArm4;
  
  public ModelRenderer RightArm2;
  
  public ModelRenderer RightArm3;
  
  public ModelRenderer RightArm4;
  
  public ModelGiantMagmaGolem() {
    this.field_78090_t = 128;
    this.field_78089_u = 64;
    this.LeftArm4 = new ModelRenderer(this, 58, 29);
    this.LeftArm4.field_78809_i = true;
    this.LeftArm4.func_78793_a(0.0F, 7.0F, 0.0F);
    this.LeftArm4.func_78790_a(-2.0F, 0.0F, -3.0F, 4, 10, 6, 0.0F);
    this.Torso = new ModelRenderer(this, 0, 41);
    this.Torso.func_78793_a(0.0F, 8.0F, 0.0F);
    this.Torso.func_78790_a(-4.5F, -5.0F, -3.0F, 9, 5, 6, 0.5F);
    this.RightLeg2 = new ModelRenderer(this, 98, 0);
    this.RightLeg2.func_78793_a(0.0F, 8.0F, 0.0F);
    this.RightLeg2.func_78790_a(-3.0F, 0.0F, -2.5F, 6, 8, 5, 0.0F);
    this.RightArm1 = new ModelRenderer(this, 58, 13);
    this.RightArm1.func_78793_a(-9.0F, -9.0F, 0.0F);
    this.RightArm1.func_78790_a(-4.0F, -3.0F, -3.0F, 4, 6, 6, 0.0F);
    this.RightArm2 = new ModelRenderer(this, 78, 13);
    this.RightArm2.func_78793_a(-2.0F, 2.0F, 0.0F);
    this.RightArm2.func_78790_a(-2.0F, 0.0F, -3.0F, 4, 10, 6, 0.0F);
    this.RightArm3 = new ModelRenderer(this, 98, 13);
    this.RightArm3.func_78793_a(0.0F, 8.0F, 0.0F);
    this.RightArm3.func_78790_a(-2.0F, 0.0F, -3.0F, 4, 10, 6, 0.0F);
    this.LeftArm3 = new ModelRenderer(this, 98, 13);
    this.LeftArm3.field_78809_i = true;
    this.LeftArm3.func_78793_a(0.0F, 8.0F, 0.0F);
    this.LeftArm3.func_78790_a(-2.0F, 0.0F, -3.0F, 4, 10, 6, 0.0F);
    this.Head = new ModelRenderer(this, 0, 0);
    this.Head.func_78793_a(0.0F, -12.0F, -3.0F);
    this.Head.func_78790_a(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F);
    this.Body = new ModelRenderer(this, 0, 18);
    this.Body.func_78793_a(0.0F, -5.0F, 0.0F);
    this.Body.func_78790_a(-9.0F, -12.0F, -6.0F, 18, 12, 11, 0.0F);
    this.RightLeg1 = new ModelRenderer(this, 76, 0);
    this.RightLeg1.func_78793_a(-4.0F, 8.0F, 0.0F);
    this.RightLeg1.func_78790_a(-3.0F, 0.0F, -2.5F, 6, 8, 5, 0.0F);
    this.LeftArm2 = new ModelRenderer(this, 78, 13);
    this.LeftArm2.field_78809_i = true;
    this.LeftArm2.func_78793_a(2.0F, 2.0F, 0.0F);
    this.LeftArm2.func_78790_a(-2.0F, 0.0F, -3.0F, 4, 10, 6, 0.0F);
    this.LeftArm1 = new ModelRenderer(this, 58, 13);
    this.LeftArm1.field_78809_i = true;
    this.LeftArm1.func_78793_a(9.0F, -9.0F, 0.0F);
    this.LeftArm1.func_78790_a(0.0F, -3.0F, -3.0F, 4, 6, 6, 0.0F);
    this.Nose = new ModelRenderer(this, 0, 0);
    this.Nose.func_78793_a(0.0F, 0.0F, -5.0F);
    this.Nose.func_78790_a(-1.0F, -3.0F, -1.0F, 2, 4, 2, 0.0F);
    this.RightArm4 = new ModelRenderer(this, 58, 29);
    this.RightArm4.func_78793_a(0.0F, 7.0F, 0.0F);
    this.RightArm4.func_78790_a(-2.0F, 0.0F, -3.0F, 4, 10, 6, 0.0F);
    this.LeftLeg1 = new ModelRenderer(this, 32, 0);
    this.LeftLeg1.field_78809_i = true;
    this.LeftLeg1.func_78793_a(4.0F, 8.0F, 0.0F);
    this.LeftLeg1.func_78790_a(-3.0F, 0.0F, -2.5F, 6, 8, 5, 0.0F);
    this.LeftLeg2 = new ModelRenderer(this, 54, 0);
    this.LeftLeg2.field_78809_i = true;
    this.LeftLeg2.func_78793_a(0.0F, 8.0F, 0.0F);
    this.LeftLeg2.func_78790_a(-3.0F, 0.0F, -2.5F, 6, 8, 5, 0.0F);
    this.LeftArm3.func_78792_a(this.LeftArm4);
    this.RightLeg1.func_78792_a(this.RightLeg2);
    this.Body.func_78792_a(this.RightArm1);
    this.RightArm1.func_78792_a(this.RightArm2);
    this.RightArm2.func_78792_a(this.RightArm3);
    this.LeftArm2.func_78792_a(this.LeftArm3);
    this.Body.func_78792_a(this.Head);
    this.Torso.func_78792_a(this.Body);
    this.LeftArm1.func_78792_a(this.LeftArm2);
    this.Body.func_78792_a(this.LeftArm1);
    this.Head.func_78792_a(this.Nose);
    this.RightArm3.func_78792_a(this.RightArm4);
    this.LeftLeg1.func_78792_a(this.LeftLeg2);
  }
  
  public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    this.Torso.func_78785_a(f5);
    this.RightLeg1.func_78785_a(f5);
    this.LeftLeg1.func_78785_a(f5);
  }
  
  public void setAngles() {
    setRotateAngle(this.Body, 0.0F, 0.0F, 0.0F);
    setRotateAngle(this.Torso, 0.0F, 0.0F, 0.0F);
    setRotateAngle(this.LeftLeg1, 0.0F, 0.0F, 0.0F);
    setRotateAngle(this.RightLeg1, 0.0F, 0.0F, 0.0F);
    setRotateAngle(this.LeftLeg2, 0.0F, 0.0F, 0.0F);
    setRotateAngle(this.RightLeg2, 0.0F, 0.0F, 0.0F);
    setRotateAngle(this.LeftArm1, 0.0F, 0.0F, 0.0F);
    setRotateAngle(this.RightArm1, 0.0F, 0.0F, 0.0F);
    setRotateAngle(this.LeftArm2, 0.0F, 0.0F, 0.0F);
    setRotateAngle(this.RightArm2, 0.0F, 0.0F, 0.0F);
    setRotateAngle(this.LeftArm3, 0.0F, 0.0F, 0.0F);
    setRotateAngle(this.RightArm3, 0.0F, 0.0F, 0.0F);
    setRotateAngle(this.LeftArm4, 0.0F, 0.0F, 0.0F);
    setRotateAngle(this.RightArm4, 0.0F, 0.0F, 0.0F);
    this.RightArm1.func_78793_a(-9.0F, -9.0F, 0.0F);
    this.RightArm2.func_78793_a(-2.0F, 1.0F, 0.0F);
    this.RightArm3.func_78793_a(0.0F, 8.0F, 0.0F);
    this.RightArm4.func_78793_a(0.0F, 8.0F, 0.0F);
    this.LeftArm1.func_78793_a(9.0F, -9.0F, 0.0F);
    this.LeftArm2.func_78793_a(2.0F, 1.0F, 0.0F);
    this.LeftArm3.func_78793_a(0.0F, 8.0F, 0.0F);
    this.LeftArm4.func_78793_a(0.0F, 8.0F, 0.0F);
    this.Nose.func_78793_a(0.0F, 0.0F, -5.0F);
    this.Head.func_78793_a(0.0F, -12.0F, -3.0F);
    this.Body.func_78793_a(0.0F, -5.0F, 0.0F);
    this.Torso.func_78793_a(0.0F, 8.0F, 0.0F);
    this.RightLeg1.func_78793_a(-4.0F, 8.0F, 0.0F);
    this.RightLeg2.func_78793_a(0.0F, 8.0F, 0.0F);
    this.LeftLeg1.func_78793_a(4.0F, 8.0F, 0.0F);
    this.LeftLeg2.func_78793_a(0.0F, 8.0F, 0.0F);
  }
  
  public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
    modelRenderer.field_78795_f = x;
    modelRenderer.field_78796_g = y;
    modelRenderer.field_78808_h = z;
  }
  
  public void func_78087_a(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    setAngles();
    float fo = 0.22206666F;
    this.Head.field_78796_g = netHeadYaw / 57.295776F;
    this.Head.field_78795_f = headPitch / 57.295776F;
    this.Torso.field_78808_h = MathHelper.func_76134_b(limbSwing * fo) * 0.2F * limbSwingAmount;
    this.Body.field_78808_h = MathHelper.func_76134_b(limbSwing * fo - 1.0F) * 0.2F * limbSwingAmount;
    this.RightLeg1.field_78795_f = MathHelper.func_76134_b(limbSwing * fo - 0.5F) * 0.75F * limbSwingAmount;
    this.LeftLeg1.field_78795_f = MathHelper.func_76134_b(limbSwing * fo + 2.6415927F) * 0.75F * limbSwingAmount;
    this.RightLeg2.field_78795_f = MathHelper.func_76134_b(limbSwing * fo + 3.1415927F) * 0.75F * limbSwingAmount;
    this.LeftLeg2.field_78795_f = MathHelper.func_76134_b(limbSwing * fo) * 0.75F * limbSwingAmount;
    this.RightArm1.field_78795_f = MathHelper.func_76134_b(limbSwing * fo + 3.1415927F) * 0.5F * limbSwingAmount;
    this.RightArm2.field_78795_f = MathHelper.func_76134_b(limbSwing * fo + 2.1415927F) * 0.5F * limbSwingAmount;
    this.RightArm3.field_78795_f = MathHelper.func_76134_b(limbSwing * fo + 1.6415927F) * 0.5F * limbSwingAmount;
    this.RightArm4.field_78795_f = MathHelper.func_76134_b(limbSwing * fo + 1.1415927F) * 0.5F * limbSwingAmount;
    this.LeftArm1.field_78795_f = MathHelper.func_76134_b(limbSwing * fo) * 0.5F * limbSwingAmount;
    this.LeftArm2.field_78795_f = MathHelper.func_76134_b(limbSwing * fo - 1.0F) * 0.5F * limbSwingAmount;
    this.LeftArm3.field_78795_f = MathHelper.func_76134_b(limbSwing * fo - 1.5F) * 0.5F * limbSwingAmount;
    this.LeftArm4.field_78795_f = MathHelper.func_76134_b(limbSwing * fo - 2.0F) * 0.5F * limbSwingAmount;
    if (this.RightLeg2.field_78795_f < 0.0F)
      this.RightLeg2.field_78795_f = 0.0F; 
    if (this.LeftLeg2.field_78795_f < 0.0F)
      this.LeftLeg2.field_78795_f = 0.0F; 
    if (this.RightArm2.field_78795_f > 0.0F)
      this.RightArm2.field_78795_f = 0.0F; 
    if (this.LeftArm2.field_78795_f > 0.0F)
      this.LeftArm2.field_78795_f = 0.0F; 
    if (this.RightArm3.field_78795_f > 0.0F)
      this.RightArm3.field_78795_f = 0.0F; 
    if (this.LeftArm3.field_78795_f > 0.0F)
      this.LeftArm3.field_78795_f = 0.0F; 
    if (this.RightArm4.field_78795_f > 0.0F)
      this.RightArm4.field_78795_f = 0.0F; 
    if (this.LeftArm4.field_78795_f > 0.0F)
      this.LeftArm4.field_78795_f = 0.0F; 
    this.RightLeg1.func_78793_a(-4.0F, this.Torso.field_78797_d, 0.0F);
    this.LeftLeg1.func_78793_a(4.0F, this.Torso.field_78797_d, 0.0F);
  }
}
