package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityIronGolem;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelCMMIronGolem extends ModelBase implements ICappedModel {
  public ModelRenderer RArm;
  
  public ModelRenderer LArm;
  
  public ModelRenderer LLeg;
  
  public ModelRenderer RLeg;
  
  public ModelRenderer Abdoman;
  
  public ModelRenderer Body;
  
  public ModelRenderer Skirt1;
  
  public ModelRenderer Neck;
  
  public ModelRenderer BustFront;
  
  public ModelRenderer Head;
  
  public ModelRenderer Hair;
  
  public ModelRenderer HTop;
  
  public ModelRenderer HLeft;
  
  public ModelRenderer BustTop;
  
  public ModelRenderer BustBottom;
  
  public ModelRenderer Skirt;
  
  public boolean isSneak;
  
  public ModelRenderer bipedCape;
  
  public void renderCape(float scale, float flo1, float flo2, float flo3) {
    GlStateManager.func_179094_E();
    GlStateManager.func_179109_b(0.0F, this.isSneak ? 0.25F : 0.075F, this.isSneak ? -0.625F : -0.0325F);
    GlStateManager.func_179114_b(6.0F + flo2 / 2.0F + flo1 + (this.isSneak ? 20.0F : 1.0F), 1.0F, 0.0F, 0.0F);
    GlStateManager.func_179114_b(flo3 / 2.0F, 0.0F, 0.0F, 1.0F);
    GlStateManager.func_179114_b(-flo3 / 2.0F, 0.0F, 1.0F, 0.0F);
    GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
    this.bipedCape.func_78785_a(scale);
    GlStateManager.func_179121_F();
  }
  
  public ModelCMMIronGolem() {
    this.bipedCape = new ModelRenderer(this, 0, 0);
    this.bipedCape.func_78787_b(64, 32);
    this.bipedCape.func_78790_a(-5.0F, 0.0F, -1.0F, 10, 16, 1, 0.0F);
    this.field_78090_t = 64;
    this.field_78089_u = 64;
    this.RLeg = new ModelRenderer(this, 52, 0);
    this.RLeg.func_78793_a(-2.0F, 11.0F, 0.0F);
    this.RLeg.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 13, 3, 0.0F);
    this.LLeg = new ModelRenderer(this, 52, 0);
    this.LLeg.field_78809_i = true;
    this.LLeg.func_78793_a(2.0F, 11.0F, 0.0F);
    this.LLeg.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 13, 3, 0.0F);
    this.LArm = new ModelRenderer(this, 36, 0);
    this.LArm.field_78809_i = true;
    this.LArm.func_78793_a(3.5F, 1.0F, 0.0F);
    this.LArm.func_78790_a(0.0F, -1.0F, -1.0F, 2, 13, 2, 0.0F);
    this.Body = new ModelRenderer(this, 0, 47);
    this.Body.func_78793_a(0.0F, -6.0F, 0.0F);
    this.Body.func_78790_a(-3.5F, -5.0F, -1.5F, 7, 5, 3, 0.0F);
    this.Hair = new ModelRenderer(this, 0, 12);
    this.Hair.func_78793_a(0.0F, 0.0F, 0.0F);
    this.Hair.func_78790_a(-4.0F, -6.0F, -3.5F, 8, 7, 7, 0.0F);
    this.RArm = new ModelRenderer(this, 36, 0);
    this.RArm.func_78793_a(-3.5F, 1.0F, 0.0F);
    this.RArm.func_78790_a(-2.0F, -1.0F, -1.0F, 2, 13, 2, 0.0F);
    this.Neck = new ModelRenderer(this, 0, 42);
    this.Neck.func_78793_a(0.0F, -5.0F, 0.0F);
    this.Neck.func_78790_a(-1.5F, -2.0F, -1.5F, 3, 2, 3, 0.0F);
    this.HLeft = new ModelRenderer(this, 32, 0);
    this.HLeft.func_78793_a(0.0F, 0.0F, 0.0F);
    this.HLeft.func_78790_a(3.5F, -8.0F, -0.5F, 1, 4, 1, 0.0F);
    this.HTop = new ModelRenderer(this, 0, 26);
    this.HTop.func_78793_a(0.0F, 0.0F, 0.0F);
    this.HTop.func_78790_a(-4.0F, -9.0F, -3.5F, 8, 3, 7, 0.0F);
    this.Abdoman = new ModelRenderer(this, 0, 55);
    this.Abdoman.func_78793_a(0.0F, 11.0F, 0.0F);
    this.Abdoman.func_78790_a(-3.0F, -6.0F, -1.5F, 6, 6, 3, 0.0F);
    this.Head = new ModelRenderer(this, 0, 0);
    this.Head.func_78793_a(0.0F, -1.0F, 0.0F);
    this.Head.func_78790_a(-3.5F, -6.0F, -3.0F, 7, 6, 6, 0.0F);
    this.Skirt = new ModelRenderer(this, 38, 39);
    this.Skirt.func_78793_a(0.0F, 3.0F, 0.0F);
    this.Skirt.func_78790_a(-4.0F, 0.0F, -2.5F, 8, 3, 5, 0.0F);
    this.Skirt1 = new ModelRenderer(this, 42, 32);
    this.Skirt1.func_78793_a(0.0F, -2.0F, 0.0F);
    this.Skirt1.func_78790_a(-3.5F, 0.0F, -2.0F, 7, 3, 4, 0.0F);
    this.BustFront = new ModelRenderer(this, 48, 56);
    this.BustFront.func_78793_a(0.0F, -0.8F, -1.0F);
    this.BustFront.func_78790_a(-3.0F, -1.0F, -2.0F, 6, 2, 2, 0.0F);
    this.BustTop = new ModelRenderer(this, 48, 52);
    this.BustTop.func_78793_a(0.0F, -2.3F, -0.6F);
    this.BustTop.func_78790_a(-3.0F, 0.0F, 0.0F, 6, 2, 2, 0.0F);
    setRotateAngle(this.BustTop, -0.7853982F, 0.0F, 0.0F);
    this.BustBottom = new ModelRenderer(this, 48, 60);
    this.BustBottom.func_78793_a(0.0F, -0.7F, -1.0F);
    this.BustBottom.func_78790_a(-3.0F, 0.0F, 0.0F, 6, 2, 2, 0.0F);
    setRotateAngle(this.BustBottom, -0.43633232F, 0.0F, 0.0F);
    this.Abdoman.func_78792_a(this.Body);
    this.Body.func_78792_a(this.BustFront);
    this.Head.func_78792_a(this.Hair);
    this.BustFront.func_78792_a(this.BustTop);
    this.Body.func_78792_a(this.Neck);
    this.Head.func_78792_a(this.HLeft);
    this.Head.func_78792_a(this.HTop);
    this.Neck.func_78792_a(this.Head);
    this.Skirt1.func_78792_a(this.Skirt);
    this.Abdoman.func_78792_a(this.Skirt1);
    this.BustFront.func_78792_a(this.BustBottom);
  }
  
  public void func_78088_a(Entity entityIn, float p_78088_2_, float limbSwing, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    func_78087_a(p_78088_2_, limbSwing, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
    GlStateManager.func_179094_E();
    if (this.field_78091_s) {
      GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
      GlStateManager.func_179109_b(0.0F, 24.0F * scale, 0.0F);
      this.RArm.func_78785_a(scale);
      this.LArm.func_78785_a(scale);
      this.RLeg.func_78785_a(scale);
      this.LLeg.func_78785_a(scale);
      this.Abdoman.func_78785_a(scale);
    } else {
      this.RArm.func_78785_a(scale);
      this.LArm.func_78785_a(scale);
      this.RLeg.func_78785_a(scale);
      this.LLeg.func_78785_a(scale);
      this.Abdoman.func_78785_a(scale);
    } 
    GlStateManager.func_179121_F();
  }
  
  public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
    modelRenderer.field_78795_f = x;
    modelRenderer.field_78796_g = y;
    modelRenderer.field_78808_h = z;
  }
  
  public void func_78087_a(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    EntityIronGolem entity = (EntityIronGolem)entityIn;
    this.LLeg.field_78795_f = -1.5F * func_78172_a(limbSwing, 13.0F) * limbSwingAmount;
    this.RLeg.field_78795_f = 1.5F * func_78172_a(limbSwing, 13.0F) * limbSwingAmount;
    float swing = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F);
    int i = entity.getAttackTimer();
    if (i > 0) {
      this.RArm.field_78795_f = -2.0F + 1.5F * func_78172_a(swing * 5.0F, 10.0F);
      this.LArm.field_78795_f = -2.0F + 1.5F * func_78172_a(swing * 5.0F, 10.0F);
    } else {
      int j = entity.getHoldRoseTick();
      if (j > 0) {
        this.RArm.field_78795_f = -0.8F + 0.025F * func_78172_a(j, 70.0F);
        this.LArm.field_78795_f = (-0.2F - 1.5F * func_78172_a(limbSwing, 13.0F)) * limbSwingAmount;
      } else {
        this.RArm.field_78795_f = (-0.2F + 1.5F * func_78172_a(limbSwing, 13.0F)) * limbSwingAmount;
        this.LArm.field_78795_f = (-0.2F - 1.5F * func_78172_a(limbSwing, 13.0F)) * limbSwingAmount;
      } 
    } 
    if (this.field_78091_s) {
      this.BustFront.field_78807_k = true;
    } else {
      this.BustFront.field_78807_k = false;
    } 
    this.Head.field_78796_g = netHeadYaw * 0.017453292F;
    this.Head.field_78795_f = headPitch * 0.017453292F;
    if (entity.func_70093_af()) {
      this.isSneak = true;
    } else {
      this.isSneak = false;
    } 
    this.BustFront.func_78793_a(0.0F, -0.75F, -1.0F);
    this.RArm.func_78793_a(-3.5F, 3.0F, 0.0F);
    this.LArm.func_78793_a(3.5F, 3.0F, 0.0F);
    this.Body.field_78795_f = 0.0F;
    this.Abdoman.field_78795_f = 0.0F;
    this.Body.field_78808_h = 0.0F;
    this.Abdoman.field_78808_h = 0.0F;
    this.Neck.field_78808_h = 0.0F;
    this.Neck.field_78796_g = 0.0F;
    this.Neck.field_78795_f = 0.0F;
    this.Abdoman.field_78798_e = 0.0F;
    this.Abdoman.field_78797_d = 12.0F;
    this.RArm.field_78808_h = 0.0F;
    this.LArm.field_78808_h = 0.0F;
    this.RArm.field_78808_h = 0.0F;
    this.LArm.field_78808_h = 0.0F;
    this.RArm.field_78796_g = 0.0F;
    this.LArm.field_78796_g = 0.0F;
    this.RLeg.field_78796_g = 0.0F;
    this.LLeg.field_78796_g = 0.0F;
    this.RLeg.field_78808_h = 0.0F;
    this.LLeg.field_78808_h = 0.0F;
    this.BustFront.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F + 2.0F) * 0.5F * limbSwingAmount * 0.5F;
    this.Abdoman.field_78798_e = MathHelper.func_76134_b(limbSwing * 0.6662F) * 0.5F * limbSwingAmount * 0.5F;
    this.Abdoman.field_78797_d = 12.0F + MathHelper.func_76134_b(limbSwing * 0.6662F) * 0.1F * limbSwingAmount * 0.5F;
    if (this.isSneak && !entity.isSitResting() && entity.field_70122_E && entity.getJukeboxToDanceTo() == null) {
      this.Abdoman.field_78795_f++;
      this.Body.field_78795_f -= 0.5F;
      this.Head.field_78795_f -= 0.5F;
      this.RArm.field_78795_f += 0.75F;
      this.LArm.field_78795_f += 0.75F;
      this.Abdoman.field_78798_e--;
      this.RArm.field_78797_d += 4.0F;
      this.LArm.field_78797_d += 4.0F;
      this.RArm.field_78798_e -= 8.0F;
      this.LArm.field_78798_e -= 8.0F;
    } 
    if (this.field_78093_q)
      if (this.field_78091_s) {
        this.Head.field_78795_f = -0.5F;
        this.Neck.field_78795_f = -0.5F;
        this.RArm.field_78795_f = -1.75F;
        this.LArm.field_78795_f = -1.75F;
        this.RArm.field_78796_g = 0.5F;
        this.LArm.field_78796_g = -0.5F;
        this.RLeg.field_78795_f = 0.25F + MathHelper.func_76134_b(ageInTicks * 0.3F + 3.1415927F) * 0.1F + MathHelper.func_76134_b(limbSwing * 0.6662F + 3.1415927F) * 0.25F * limbSwingAmount * 0.5F;
        this.LLeg.field_78795_f = 0.25F + MathHelper.func_76134_b(ageInTicks * 0.3F) * 0.1F + MathHelper.func_76134_b(limbSwing * 0.6662F) * 0.25F * limbSwingAmount * 0.5F;
        this.RLeg.field_78808_h = 0.25F + MathHelper.func_76134_b(ageInTicks * 0.3F - 2.0F) * 0.1F;
        this.LLeg.field_78808_h = -0.25F + MathHelper.func_76134_b(ageInTicks * 0.3F - 2.0F) * 0.1F;
      } else {
        this.RArm.field_78795_f += -0.62831855F;
        this.LArm.field_78795_f += -0.62831855F;
        this.RLeg.field_78795_f = -1.4137167F;
        this.RLeg.field_78796_g = 0.31415927F;
        this.RLeg.field_78808_h = 0.07853982F;
        this.LLeg.field_78795_f = -1.4137167F;
        this.LLeg.field_78796_g = -0.31415927F;
        this.LLeg.field_78808_h = -0.07853982F;
      }  
    if (entity.isSitResting()) {
      this.RArm.field_78795_f += -0.62831855F;
      this.LArm.field_78795_f += -0.62831855F;
      this.RLeg.field_78795_f = -1.4137167F;
      this.RLeg.field_78796_g = 0.31415927F;
      this.RLeg.field_78808_h = 0.07853982F;
      this.LLeg.field_78795_f = -1.4137167F;
      this.LLeg.field_78796_g = -0.31415927F;
      this.LLeg.field_78808_h = -0.07853982F;
    } 
    if (entity.func_70027_ad() || (!entity.func_70089_S() && !entity.field_70122_E)) {
      this.Head.field_78795_f -= 0.5F;
      this.Head.field_78796_g += MathHelper.func_76134_b(ageInTicks * 0.6662F) * 0.5F;
      this.RArm.field_78795_f = -MathHelper.func_76134_b(ageInTicks * 0.6662F) * 0.5F;
      this.LArm.field_78795_f = MathHelper.func_76134_b(ageInTicks * 0.6662F) * 0.5F;
      this.RArm.field_78808_h = 2.3561945F;
      this.LArm.field_78808_h = -2.3561945F;
      this.RArm.field_78796_g = 0.0F;
      this.LArm.field_78796_g = 0.0F;
    } 
    if (entity.getJukeboxToDanceTo() != null) {
      this.Neck.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.5F - 3.1415927F) * 0.25F - MathHelper.func_76134_b(ageInTicks * 0.25F) * 0.25F;
      this.RArm.field_78808_h = 1.0F - MathHelper.func_76134_b(ageInTicks * 0.5F - 3.1415927F) * 0.25F - MathHelper.func_76134_b(ageInTicks * 0.25F) * 1.0F;
      this.LArm.field_78808_h = -1.0F - MathHelper.func_76134_b(ageInTicks * 0.5F - 3.1415927F) * 0.25F - MathHelper.func_76134_b(ageInTicks * 0.25F) * 1.0F;
      this.RArm.field_78796_g = 0.0F;
      this.LArm.field_78796_g = 0.0F;
      this.RLeg.field_78795_f = 0.0F;
      this.LLeg.field_78795_f = 0.0F;
    } 
    if (!entity.getCurrentBook().func_190926_b()) {
      this.RArm.field_78796_g = entity.bookSpread - 0.825F;
      this.LArm.field_78796_g = -entity.bookSpread + 0.825F;
      this.RArm.field_78808_h = 0.0F;
      this.LArm.field_78808_h = 0.0F;
      this.RArm.field_78795_f = -1.5F + 0.1F + MathHelper.func_76126_a(entity.field_70173_aa * 0.1F) * 0.01F;
      this.LArm.field_78795_f = -1.5F + 0.1F + MathHelper.func_76126_a(entity.field_70173_aa * 0.1F) * 0.01F;
    } 
  }
  
  private float func_78172_a(float p_78172_1_, float p_78172_2_) {
    return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5F) - p_78172_2_ * 0.25F) / p_78172_2_ * 0.25F;
  }
}
