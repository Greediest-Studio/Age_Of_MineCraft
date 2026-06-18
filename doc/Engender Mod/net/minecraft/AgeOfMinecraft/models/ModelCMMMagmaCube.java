package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityMagmaCube;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelCMMMagmaCube extends ModelBase implements ICappedModel {
  public ModelRenderer LLeg;
  
  public ModelRenderer Abdoman;
  
  public ModelRenderer RArm;
  
  public ModelRenderer HTop;
  
  public ModelRenderer LArm;
  
  public ModelRenderer RLeg;
  
  public ModelRenderer Body;
  
  public ModelRenderer Skirt;
  
  public ModelRenderer Neck;
  
  public ModelRenderer BustFront;
  
  public ModelRenderer Head;
  
  public ModelRenderer Hair;
  
  public ModelRenderer Hat;
  
  public ModelRenderer BHair1;
  
  public ModelRenderer BHair2;
  
  public ModelRenderer BHair3;
  
  public ModelRenderer BHair4;
  
  public ModelRenderer BustTop;
  
  public ModelRenderer BustBottom;
  
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
  
  public ModelCMMMagmaCube() {
    this.bipedCape = new ModelRenderer(this, 0, 0);
    this.bipedCape.func_78787_b(64, 32);
    this.bipedCape.func_78790_a(-5.0F, 0.0F, -1.0F, 10, 16, 1, 0.0F);
    this.field_78090_t = 64;
    this.field_78089_u = 64;
    this.LLeg = new ModelRenderer(this, 52, 0);
    this.LLeg.field_78809_i = true;
    this.LLeg.func_78793_a(2.0F, 12.0F, 0.0F);
    this.LLeg.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 12, 3, 0.0F);
    this.LArm = new ModelRenderer(this, 36, 0);
    this.LArm.field_78809_i = true;
    this.LArm.func_78793_a(3.5F, 2.0F, 0.0F);
    this.LArm.func_78790_a(0.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
    this.RLeg = new ModelRenderer(this, 52, 0);
    this.RLeg.func_78793_a(-2.0F, 12.0F, 0.0F);
    this.RLeg.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 12, 3, 0.0F);
    this.Hair = new ModelRenderer(this, 0, 12);
    this.Hair.func_78793_a(0.0F, 0.0F, 0.0F);
    this.Hair.func_78790_a(-4.0F, -6.0F, -3.5F, 8, 7, 7, 0.0F);
    this.BHair1 = new ModelRenderer(this, 30, 14);
    this.BHair1.func_78793_a(0.0F, 0.0F, 3.0F);
    this.BHair1.func_78790_a(-4.0F, 0.0F, -0.5F, 8, 3, 1, 0.0F);
    this.RArm = new ModelRenderer(this, 36, 0);
    this.RArm.func_78793_a(-3.5F, 2.0F, 0.0F);
    this.RArm.func_78790_a(-2.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
    this.Neck = new ModelRenderer(this, 52, 47);
    this.Neck.func_78793_a(0.0F, -6.0F, 0.0F);
    this.Neck.func_78790_a(-1.5F, -2.0F, -1.5F, 3, 2, 3, 0.0F);
    this.Abdoman = new ModelRenderer(this, 0, 56);
    this.Abdoman.func_78793_a(0.0F, 12.0F, 0.0F);
    this.Abdoman.func_78790_a(-3.0F, -5.0F, -1.5F, 6, 5, 3, 0.0F);
    this.Body = new ModelRenderer(this, 0, 47);
    this.Body.func_78793_a(0.0F, -5.0F, 0.0F);
    this.Body.func_78790_a(-3.5F, -6.0F, -1.5F, 7, 6, 3, 0.0F);
    this.Skirt = new ModelRenderer(this, 40, 30);
    this.Skirt.func_78793_a(0.0F, -1.0F, 0.0F);
    this.Skirt.func_78790_a(-4.0F, 0.0F, -2.0F, 8, 3, 4, 0.0F);
    this.HTop = new ModelRenderer(this, 0, 37);
    this.HTop.func_78793_a(0.0F, 0.0F, 0.0F);
    this.HTop.func_78790_a(-3.5F, -7.0F, -3.0F, 7, 1, 6, 0.0F);
    this.BHair2 = new ModelRenderer(this, 30, 16);
    this.BHair2.func_78793_a(0.0F, 3.0F, 0.0F);
    this.BHair2.func_78790_a(-4.0F, 0.0F, -0.5F, 8, 3, 1, 0.0F);
    this.BHair3 = new ModelRenderer(this, 30, 20);
    this.BHair3.func_78793_a(0.0F, 3.0F, 0.0F);
    this.BHair3.func_78790_a(-4.0F, 0.0F, -0.5F, 8, 3, 1, 0.0F);
    this.Head = new ModelRenderer(this, 0, 0);
    this.Head.func_78793_a(0.0F, -1.0F, 0.0F);
    this.Head.func_78790_a(-3.5F, -6.0F, -3.0F, 7, 6, 6, 0.0F);
    this.BHair4 = new ModelRenderer(this, 30, 22);
    this.BHair4.func_78793_a(0.0F, 3.0F, 0.0F);
    this.BHair4.func_78790_a(-4.0F, 0.0F, -0.5F, 8, 3, 1, 0.0F);
    this.Hat = new ModelRenderer(this, 0, 26);
    this.Hat.func_78793_a(0.0F, 0.0F, 0.0F);
    this.Hat.func_78790_a(-4.0F, -10.0F, -3.5F, 8, 4, 7, 0.0F);
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
    this.Head.func_78792_a(this.Hair);
    this.Head.func_78792_a(this.BHair1);
    this.BustFront.func_78792_a(this.BustTop);
    this.Body.func_78792_a(this.Neck);
    this.Abdoman.func_78792_a(this.Body);
    this.Abdoman.func_78792_a(this.Skirt);
    this.BHair1.func_78792_a(this.BHair2);
    this.BHair2.func_78792_a(this.BHair3);
    this.Body.func_78792_a(this.BustFront);
    this.BustFront.func_78792_a(this.BustBottom);
    this.Neck.func_78792_a(this.Head);
    this.BHair3.func_78792_a(this.BHair4);
    this.Head.func_78792_a(this.Hat);
    this.Head.func_78792_a(this.HTop);
  }
  
  public void func_78088_a(Entity entityIn, float p_78088_2_, float limbSwing, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    func_78087_a(p_78088_2_, limbSwing, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
    this.LLeg.func_78785_a(scale);
    this.LArm.func_78785_a(scale);
    this.RLeg.func_78785_a(scale);
    this.RArm.func_78785_a(scale);
    this.Abdoman.func_78785_a(scale);
  }
  
  public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
    modelRenderer.field_78795_f = x;
    modelRenderer.field_78796_g = y;
    modelRenderer.field_78808_h = z;
  }
  
  public void func_78087_a(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    this.Neck.field_78796_g = netHeadYaw * 0.017453292F / 2.0F;
    this.Neck.field_78795_f = headPitch * 0.017453292F / 2.0F;
    this.Head.field_78796_g = netHeadYaw * 0.017453292F / 2.0F;
    this.Head.field_78795_f = headPitch * 0.017453292F / 2.0F;
    EntityMagmaCube entity = (EntityMagmaCube)entityIn;
    if (entity.getSlimeSize() <= 1)
      limbSwing *= 3.0F; 
    if (entity.getSlimeSize() == 2)
      limbSwing *= 2.0F; 
    if (entity.func_70093_af()) {
      this.isSneak = true;
    } else {
      this.isSneak = false;
    } 
    this.Hat.field_78797_d = 0.0F + entity.squishAmount;
    this.BustFront.func_78793_a(0.0F, -1.75F, -1.0F);
    this.RArm.func_78793_a(-3.5F, 3.0F, 0.0F);
    this.LArm.func_78793_a(3.5F, 3.0F, 0.0F);
    this.Body.field_78795_f = 0.0F;
    this.Abdoman.field_78795_f = 0.0F;
    this.Abdoman.field_78796_g = 0.0F;
    this.Body.field_78808_h = 0.0F;
    this.Abdoman.field_78808_h = 0.0F;
    this.Neck.field_78808_h = 0.0F;
    this.Head.field_78808_h = 0.0F;
    this.Head.field_78796_g = 0.0F;
    this.BHair1.field_78795_f = entity.field_70721_aZ * 0.5F - headPitch * 0.0045F;
    this.BHair2.field_78795_f = entity.field_70721_aZ * 0.25F - headPitch * 0.0045F;
    this.BHair3.field_78795_f = entity.field_70721_aZ * 0.25F - headPitch * 0.0045F;
    this.BHair4.field_78795_f = entity.field_70721_aZ * 0.25F - headPitch * 0.0045F;
    this.BHair1.field_78808_h = 0.0F;
    this.BHair2.field_78808_h = 0.0F;
    this.BHair3.field_78808_h = 0.0F;
    this.BHair4.field_78808_h = 0.0F;
    this.Skirt.field_78795_f = 0.0F;
    this.RArm.field_78808_h = 0.0F;
    this.LArm.field_78808_h = 0.0F;
    this.RArm.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F + 3.1415927F) * 2.0F * limbSwingAmount * 0.5F;
    this.LArm.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
    this.RArm.field_78808_h = 0.0F;
    this.LArm.field_78808_h = 0.0F;
    this.RArm.field_78796_g = 0.0F;
    this.LArm.field_78796_g = 0.0F;
    this.RLeg.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
    this.LLeg.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount * 0.5F;
    this.RLeg.field_78796_g = 0.0F;
    this.LLeg.field_78796_g = 0.0F;
    this.RLeg.field_78808_h = 0.0F;
    this.LLeg.field_78808_h = 0.0F;
    this.BustFront.field_78795_f = MathHelper.func_76134_b(ageInTicks * 0.2F) * 0.025F + entity.squishAmount + (float)entity.field_70181_x / 5.0F + MathHelper.func_76134_b(limbSwing * 0.6662F + 1.5707964F) * 0.5F * limbSwingAmount * 0.5F;
    this.BustFront.field_78796_g = MathHelper.func_76134_b(ageInTicks * 0.1F) * 0.025F + MathHelper.func_76134_b(limbSwing * 0.6662F + 3.1415927F) * 0.5F * limbSwingAmount * 0.5F;
    this.BustFront.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.15F) * 0.025F + MathHelper.func_76134_b(limbSwing * 0.6662F) * 0.5F * limbSwingAmount * 0.5F;
    this.Neck.field_78808_h += MathHelper.func_76134_b(limbSwing * 0.6662F) * 0.6F * limbSwingAmount * 0.5F;
    this.Body.field_78808_h -= MathHelper.func_76134_b(limbSwing * 0.6662F) * 0.8F * limbSwingAmount * 0.5F;
    this.Abdoman.field_78808_h += MathHelper.func_76134_b(limbSwing * 0.6662F) * 0.4F * limbSwingAmount * 0.5F;
    this.Abdoman.field_78798_e = MathHelper.func_76134_b(limbSwing * 0.6662F) * 0.5F * limbSwingAmount * 0.5F;
    this.Abdoman.field_78797_d = 12.0F + MathHelper.func_76134_b(limbSwing * 0.6662F) * 0.1F * limbSwingAmount * 0.5F;
    this.RArm.field_78800_c += MathHelper.func_76134_b(limbSwing * 0.6662F) * 1.5F * limbSwingAmount * 0.5F;
    this.LArm.field_78800_c += MathHelper.func_76134_b(limbSwing * 0.6662F) * 1.5F * limbSwingAmount * 0.5F;
    this.BHair1.field_78795_f += MathHelper.func_76134_b(limbSwing * 0.9993F) * 0.5F * limbSwingAmount * 0.5F + entity.squishAmount;
    this.BHair1.field_78808_h += MathHelper.func_76134_b(limbSwing * 0.6662F - 1.0F) * 0.5F * limbSwingAmount * 0.5F;
    this.BHair2.field_78808_h += MathHelper.func_76134_b(limbSwing * 0.6662F - 2.0F) * 0.5F * limbSwingAmount * 0.5F;
    this.BHair3.field_78808_h += MathHelper.func_76134_b(limbSwing * 0.6662F - 3.0F) * 0.5F * limbSwingAmount * 0.5F;
    this.BHair4.field_78808_h += MathHelper.func_76134_b(limbSwing * 0.6662F - 4.0F) * 0.5F * limbSwingAmount * 0.5F;
    if (this.isSneak && !entity.isSitResting() && entity.field_70122_E && entity.getJukeboxToDanceTo() == null) {
      this.BHair1.field_78795_f += 0.75F;
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
    if (!entity.field_70122_E) {
      this.BHair1.field_78795_f = (float)(this.BHair1.field_78795_f + (MathHelper.func_76134_b(ageInTicks * 0.3F - 0.5F) * 0.1F) - ((entity.field_70181_x > 4.0D) ? 1.0D : ((entity.field_70181_x < -4.0D) ? 0.0D : (entity.field_70181_x / 4.0D))));
      this.BHair2.field_78795_f = (float)(this.BHair2.field_78795_f + (MathHelper.func_76134_b(ageInTicks * 0.3F - 1.0F) * 0.1F) - ((entity.field_70181_x > 4.0D) ? 1.0D : ((entity.field_70181_x < -4.0D) ? 0.0D : (entity.field_70181_x / 4.0D))));
      this.BHair3.field_78795_f = (float)(this.BHair3.field_78795_f + (MathHelper.func_76134_b(ageInTicks * 0.3F - 1.5F) * 0.1F) - ((entity.field_70181_x > 4.0D) ? 1.0D : ((entity.field_70181_x < -4.0D) ? 0.0D : (entity.field_70181_x / 4.0D))));
      this.BHair4.field_78795_f = (float)(this.BHair4.field_78795_f + (MathHelper.func_76134_b(ageInTicks * 0.3F - 2.0F) * 0.1F) - ((entity.field_70181_x > 4.0D) ? 1.0D : ((entity.field_70181_x < -4.0D) ? 0.0D : (entity.field_70181_x / 4.0D))));
      if (!this.field_78093_q) {
        this.RArm.field_78808_h = -0.6F;
        this.LArm.field_78808_h = 0.6F;
        this.RLeg.field_78808_h = 0.6F;
        this.LLeg.field_78808_h = -0.6F;
        this.RArm.field_78795_f = -2.0F;
        this.LArm.field_78795_f = -2.0F;
        this.RLeg.field_78795_f = 1.0F;
        this.LLeg.field_78795_f = 1.0F;
        this.Skirt.field_78795_f = 0.5F;
      } 
    } 
    if (this.field_78093_q)
      if (this.field_78091_s || entity.getSlimeSize() <= 1) {
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
      this.RArm.field_78795_f = -0.8975979F;
      this.LArm.field_78795_f = -0.8975979F;
      this.RArm.field_78796_g = -0.5F;
      this.LArm.field_78796_g = 0.5F;
      this.RLeg.field_78795_f = -1.4F;
      this.RLeg.field_78796_g = -0.3F;
      this.RLeg.field_78808_h = 0.025F;
      this.LLeg.field_78796_g = 0.25F;
      this.LLeg.field_78808_h = -0.025F;
      if (entity.getSlimeSize() <= 1) {
        this.Head.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.4F) * 0.05F;
        this.Neck.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.4F) * 0.05F;
        this.BHair1.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.4F - 0.5F) * 0.1F;
        this.BHair2.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.4F - 1.0F) * 0.1F;
        this.BHair3.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.4F - 1.5F) * 0.1F;
        this.BHair4.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.4F - 2.0F) * 0.1F;
        this.LLeg.field_78795_f = -1.6F + MathHelper.func_76134_b(ageInTicks * 1.0F) * 0.05F;
      } else if (entity.getSlimeSize() == 2) {
        this.Head.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.3F) * 0.05F;
        this.Neck.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.3F) * 0.05F;
        this.BHair1.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.3F - 0.5F) * 0.1F;
        this.BHair2.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.3F - 1.0F) * 0.1F;
        this.BHair3.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.3F - 1.5F) * 0.1F;
        this.BHair4.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.3F - 2.0F) * 0.1F;
        this.LLeg.field_78795_f = -1.6F + MathHelper.func_76134_b(ageInTicks * 0.75F) * 0.05F;
      } else {
        this.Head.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.2F) * 0.05F;
        this.Neck.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.2F) * 0.05F;
        this.BHair1.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.2F - 0.5F) * 0.1F;
        this.BHair2.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.2F - 1.0F) * 0.1F;
        this.BHair3.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.2F - 1.5F) * 0.1F;
        this.BHair4.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.2F - 2.0F) * 0.1F;
        this.LLeg.field_78795_f = -1.6F + MathHelper.func_76134_b(ageInTicks * 0.5F) * 0.05F;
      } 
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
    this.RArm.field_78808_h += MathHelper.func_76134_b(ageInTicks * 0.09F) * 0.05F + 0.1F;
    this.LArm.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.09F) * 0.05F + 0.1F;
    this.RArm.field_78795_f += MathHelper.func_76126_a(ageInTicks * 0.067F) * 0.05F;
    this.LArm.field_78795_f -= MathHelper.func_76126_a(ageInTicks * 0.067F) * 0.05F;
    if (this.field_78095_p > 0.0F) {
      float f1 = this.field_78095_p;
      this.Abdoman.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f1) * 6.2831855F) * 0.2F;
      this.RArm.field_78798_e = MathHelper.func_76126_a(this.Abdoman.field_78796_g) * 5.0F;
      this.RArm.field_78800_c = -MathHelper.func_76134_b(this.Abdoman.field_78796_g) * 3.0F;
      this.LArm.field_78798_e = -MathHelper.func_76126_a(this.Abdoman.field_78796_g) * 5.0F;
      this.LArm.field_78800_c = MathHelper.func_76134_b(this.Abdoman.field_78796_g) * 3.0F;
      this.RArm.field_78796_g += this.Abdoman.field_78796_g;
      this.LArm.field_78796_g += this.Abdoman.field_78796_g;
      this.LArm.field_78795_f += this.Abdoman.field_78796_g;
      f1 = 1.0F - this.field_78095_p;
      f1 *= f1;
      f1 *= f1;
      f1 = 1.0F - f1;
      float f2 = MathHelper.func_76126_a(f1 * 3.1415927F);
      float f3 = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F) * -(this.Neck.field_78795_f - 0.7F) * 0.75F;
      if (entity.func_184638_cS()) {
        this.Abdoman.field_78796_g *= -1.0F;
        this.LArm.field_78795_f = (float)(this.LArm.field_78795_f - f2 * 1.2D + f3);
        this.LArm.field_78796_g += this.Abdoman.field_78796_g * 2.0F;
        this.LArm.field_78808_h += MathHelper.func_76126_a(this.field_78095_p * 3.1415927F) * -0.4F;
      } else {
        this.RArm.field_78795_f = (float)(this.RArm.field_78795_f - f2 * 1.2D + f3);
        this.RArm.field_78796_g += this.Abdoman.field_78796_g * 2.0F;
        this.RArm.field_78808_h += MathHelper.func_76126_a(this.field_78095_p * 3.1415927F) * -0.4F;
      } 
    } 
    if (entity.getJukeboxToDanceTo() != null) {
      this.BHair1.field_78808_h += MathHelper.func_76134_b(ageInTicks * 0.5F - 3.5F) * 0.5F;
      this.BHair2.field_78808_h += MathHelper.func_76134_b(ageInTicks * 0.5F - 4.0F) * 0.25F;
      this.BHair3.field_78808_h += MathHelper.func_76134_b(ageInTicks * 0.5F - 4.5F) * 0.25F;
      this.BHair4.field_78808_h += MathHelper.func_76134_b(ageInTicks * 0.5F - 5.0F) * 0.25F;
      this.Neck.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.5F - 3.1415927F) * 0.5F;
      this.Head.field_78795_f += MathHelper.func_76134_b(ageInTicks * 1.0F) * 0.5F;
      this.BHair1.field_78795_f += 1.0F + MathHelper.func_76134_b(ageInTicks * 1.0F - 2.0F) * 0.25F;
      this.BHair2.field_78795_f += MathHelper.func_76134_b(ageInTicks * 1.0F - 2.5F) * 0.25F;
      this.BHair3.field_78795_f += MathHelper.func_76134_b(ageInTicks * 1.0F - 3.0F) * 0.25F;
      this.BHair4.field_78795_f += MathHelper.func_76134_b(ageInTicks * 1.0F - 2.5F) * 0.25F;
      this.Body.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.5F - 3.1415927F) * 0.5F;
      this.Abdoman.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.5F) * 0.5F;
      this.Abdoman.field_78800_c = MathHelper.func_76134_b(ageInTicks * 0.5F - 3.1415927F) * 0.5F;
      this.Abdoman.field_78797_d = 12.0F + MathHelper.func_76134_b(ageInTicks * 1.0F) * 1.0F;
      this.RArm.field_78800_c = -3.5F + MathHelper.func_76134_b(ageInTicks * 0.5F) * 3.0F;
      this.LArm.field_78800_c = 3.5F + MathHelper.func_76134_b(ageInTicks * 0.5F) * 3.0F;
      this.RArm.field_78797_d = 3.0F + MathHelper.func_76134_b(ageInTicks * 1.0F) * 1.0F;
      this.LArm.field_78797_d = 3.0F + MathHelper.func_76134_b(ageInTicks * 1.0F) * 1.0F;
      this.RArm.field_78795_f = MathHelper.func_76134_b(ageInTicks * 0.5F - 3.1415927F) * 1.0F;
      this.LArm.field_78795_f = -(MathHelper.func_76134_b(ageInTicks * 0.5F - 3.1415927F) * 1.0F);
      this.RArm.field_78808_h = 1.5F + MathHelper.func_76134_b(ageInTicks * 0.5F - 3.1415927F) * 0.25F - MathHelper.func_76134_b(ageInTicks * 0.25F) * 1.0F;
      this.LArm.field_78808_h = -1.5F - MathHelper.func_76134_b(ageInTicks * 0.5F - 3.1415927F) * 0.25F + MathHelper.func_76134_b(ageInTicks * 0.25F) * 1.0F;
      this.RArm.field_78796_g = 0.0F;
      this.LArm.field_78796_g = 0.0F;
      this.RLeg.field_78795_f = 0.0F;
      this.LLeg.field_78795_f = 0.0F;
    } 
    if (!entity.getCurrentBook().func_190926_b()) {
      this.RArm.field_78796_g = entity.bookSpread - 1.0F;
      this.LArm.field_78796_g = -entity.bookSpread + 1.0F;
      this.RArm.field_78808_h = 0.0F;
      this.LArm.field_78808_h = 0.0F;
      this.RArm.field_78795_f = -1.5F + 0.1F + MathHelper.func_76126_a(entity.field_70173_aa * 0.1F) * 0.01F;
      this.LArm.field_78795_f = -1.5F + 0.1F + MathHelper.func_76126_a(entity.field_70173_aa * 0.1F) * 0.01F;
    } 
  }
}
