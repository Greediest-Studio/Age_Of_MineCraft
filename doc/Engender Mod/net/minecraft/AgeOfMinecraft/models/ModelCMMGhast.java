package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGhast;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelCMMGhast extends ModelBase implements ICappedModel {
  public ModelRenderer Chest;
  
  public ModelRenderer Abdoman;
  
  public ModelRenderer Neck;
  
  public ModelRenderer BustFront;
  
  public ModelRenderer LArm;
  
  public ModelRenderer RArm;
  
  public ModelRenderer LLeg;
  
  public ModelRenderer RLeg;
  
  public ModelRenderer Skirt1;
  
  public ModelRenderer Skirt2;
  
  public ModelRenderer Skirt3;
  
  public ModelRenderer Skirt4;
  
  public ModelRenderer Head;
  
  public ModelRenderer Hat;
  
  public ModelRenderer HTop;
  
  public ModelRenderer BHair1;
  
  public ModelRenderer Hair;
  
  public ModelRenderer RFHair;
  
  public ModelRenderer LFHair;
  
  public ModelRenderer BHair2;
  
  public ModelRenderer BHair3;
  
  public ModelRenderer BHair4;
  
  public ModelRenderer BustTop;
  
  public ModelRenderer BustBottom;
  
  public boolean isSneak;
  
  public ModelRenderer bipedCape;
  
  public void renderCape(float scale, float flo1, float flo2, float flo3) {
    GlStateManager.func_179094_E();
    GlStateManager.func_179109_b(0.0F, this.isSneak ? 0.375F : 0.05F, this.isSneak ? -0.025F : -0.025F);
    GlStateManager.func_179114_b(6.0F + flo2 / 2.0F + flo1 + (this.isSneak ? 20.0F : 1.0F), 1.0F, 0.0F, 0.0F);
    GlStateManager.func_179114_b(flo3 / 2.0F, 0.0F, 0.0F, 1.0F);
    GlStateManager.func_179114_b(-flo3 / 2.0F, 0.0F, 1.0F, 0.0F);
    GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
    this.bipedCape.func_78785_a(scale);
    GlStateManager.func_179121_F();
  }
  
  public ModelCMMGhast() {
    this.bipedCape = new ModelRenderer(this, 0, 0);
    this.bipedCape.func_78787_b(64, 32);
    this.bipedCape.func_78790_a(-5.0F, 0.0F, -1.0F, 10, 16, 1, 0.0F);
    this.field_78090_t = 64;
    this.field_78089_u = 64;
    this.Hat = new ModelRenderer(this, 0, 34);
    this.Hat.func_78793_a(0.0F, 0.0F, 0.0F);
    this.Hat.func_78790_a(-4.5F, -9.0F, -4.0F, 9, 4, 8, 0.0F);
    this.Abdoman = new ModelRenderer(this, 0, 54);
    this.Abdoman.func_78793_a(0.0F, 5.0F, 0.0F);
    this.Abdoman.func_78790_a(-3.0F, 0.0F, -1.5F, 6, 4, 3, 0.0F);
    this.Skirt4 = new ModelRenderer(this, 30, 26);
    this.Skirt4.func_78793_a(0.0F, 3.0F, 0.0F);
    this.Skirt4.func_78790_a(-5.0F, 0.0F, -3.5F, 10, 4, 7, 0.0F);
    this.Hair = new ModelRenderer(this, 0, 11);
    this.Hair.func_78793_a(0.0F, 0.0F, 0.0F);
    this.Hair.func_78790_a(-4.0F, -5.0F, -3.5F, 8, 6, 7, 0.0F);
    this.RFHair = new ModelRenderer(this, 6, 23);
    this.RFHair.func_78793_a(-3.5F, 1.0F, -3.0F);
    this.RFHair.func_78790_a(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
    this.RLeg = new ModelRenderer(this, 52, 0);
    this.RLeg.func_78793_a(-1.9F, 4.0F, 0.0F);
    this.RLeg.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 14, 3, 0.0F);
    this.BHair2 = new ModelRenderer(this, 20, 49);
    this.BHair2.func_78793_a(0.0F, 3.0F, 0.0F);
    this.BHair2.func_78790_a(-4.0F, 0.0F, -0.5F, 8, 3, 1, 0.0F);
    this.Neck = new ModelRenderer(this, 18, 59);
    this.Neck.func_78793_a(0.0F, 0.0F, 0.0F);
    this.Neck.func_78790_a(-1.5F, -2.0F, -1.5F, 3, 2, 3, 0.0F);
    this.Skirt2 = new ModelRenderer(this, 38, 45);
    this.Skirt2.func_78793_a(0.0F, 3.0F, 0.0F);
    this.Skirt2.func_78790_a(-4.0F, 0.0F, -2.5F, 8, 4, 5, 0.0F);
    this.RArm = new ModelRenderer(this, 36, 0);
    this.RArm.func_78793_a(-3.5F, 1.0F, 0.0F);
    this.RArm.func_78790_a(-2.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
    this.BustFront = new ModelRenderer(this, 36, 18);
    this.BustFront.func_78793_a(0.0F, 3.2F, -1.0F);
    this.BustFront.func_78790_a(-3.0F, -1.0F, -2.5F, 6, 2, 2, 0.0F);
    this.BHair4 = new ModelRenderer(this, 20, 55);
    this.BHair4.func_78793_a(0.0F, 3.0F, 0.0F);
    this.BHair4.func_78790_a(-4.0F, 0.0F, -0.5F, 8, 3, 1, 0.0F);
    this.BHair1 = new ModelRenderer(this, 20, 46);
    this.BHair1.func_78793_a(0.0F, 0.0F, 3.0F);
    this.BHair1.func_78790_a(-4.0F, 0.0F, -0.5F, 8, 3, 1, 0.0F);
    this.HTop = new ModelRenderer(this, 0, 27);
    this.HTop.func_78793_a(0.0F, 0.0F, 0.0F);
    this.HTop.func_78790_a(-3.5F, -10.0F, -3.0F, 7, 1, 6, 0.0F);
    this.Skirt1 = new ModelRenderer(this, 42, 37);
    this.Skirt1.func_78793_a(0.0F, 3.0F, 0.0F);
    this.Skirt1.func_78790_a(-3.5F, 0.0F, -2.0F, 7, 4, 4, 0.0F);
    this.BHair3 = new ModelRenderer(this, 20, 52);
    this.BHair3.func_78793_a(0.0F, 3.0F, 0.0F);
    this.BHair3.func_78790_a(-4.0F, 0.0F, -0.5F, 8, 3, 1, 0.0F);
    this.Skirt3 = new ModelRenderer(this, 34, 54);
    this.Skirt3.func_78793_a(0.0F, 3.0F, 0.0F);
    this.Skirt3.func_78790_a(-4.5F, 0.0F, -3.0F, 9, 4, 6, 0.0F);
    this.BustTop = new ModelRenderer(this, 36, 14);
    this.BustTop.func_78793_a(0.0F, -2.0F, -0.65F);
    this.BustTop.func_78790_a(-3.0F, 0.0F, 0.0F, 6, 2, 2, 0.0F);
    setRotateAngle(this.BustTop, -1.0471976F, 0.0F, 0.0F);
    this.BustBottom = new ModelRenderer(this, 36, 22);
    this.BustBottom.func_78793_a(0.0F, -0.8F, -1.7F);
    this.BustBottom.func_78790_a(-3.0F, 0.0F, 0.0F, 6, 2, 2, 0.0F);
    setRotateAngle(this.BustBottom, -0.4098033F, 0.0F, 0.0F);
    this.Chest = new ModelRenderer(this, 0, 46);
    this.Chest.func_78793_a(0.0F, 1.0F, 0.0F);
    this.Chest.func_78790_a(-3.5F, 0.0F, -1.5F, 7, 5, 3, 0.0F);
    this.LLeg = new ModelRenderer(this, 52, 0);
    this.LLeg.field_78809_i = true;
    this.LLeg.func_78793_a(1.9F, 4.0F, 0.0F);
    this.LLeg.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 14, 3, 0.0F);
    this.Head = new ModelRenderer(this, 0, 0);
    this.Head.func_78793_a(0.0F, -1.0F, 0.0F);
    this.Head.func_78790_a(-3.5F, -5.0F, -3.0F, 7, 5, 6, 0.0F);
    this.LArm = new ModelRenderer(this, 36, 0);
    this.LArm.field_78809_i = true;
    this.LArm.func_78793_a(3.5F, 1.0F, 0.0F);
    this.LArm.func_78790_a(0.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
    this.LFHair = new ModelRenderer(this, 13, 23);
    this.LFHair.func_78793_a(3.5F, 1.0F, -3.0F);
    this.LFHair.func_78790_a(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
    this.Head.func_78792_a(this.Hat);
    this.Chest.func_78792_a(this.Abdoman);
    this.Skirt3.func_78792_a(this.Skirt4);
    this.Head.func_78792_a(this.Hair);
    this.Head.func_78792_a(this.RFHair);
    this.Abdoman.func_78792_a(this.RLeg);
    this.BHair1.func_78792_a(this.BHair2);
    this.Chest.func_78792_a(this.Neck);
    this.Skirt1.func_78792_a(this.Skirt2);
    this.Chest.func_78792_a(this.RArm);
    this.Chest.func_78792_a(this.BustFront);
    this.BHair3.func_78792_a(this.BHair4);
    this.Head.func_78792_a(this.BHair1);
    this.Head.func_78792_a(this.HTop);
    this.Abdoman.func_78792_a(this.Skirt1);
    this.BHair2.func_78792_a(this.BHair3);
    this.Skirt2.func_78792_a(this.Skirt3);
    this.BustFront.func_78792_a(this.BustTop);
    this.BustFront.func_78792_a(this.BustBottom);
    this.Abdoman.func_78792_a(this.LLeg);
    this.Neck.func_78792_a(this.Head);
    this.Chest.func_78792_a(this.LArm);
    this.Head.func_78792_a(this.LFHair);
  }
  
  public void func_78088_a(Entity entityIn, float p_78088_2_, float limbSwing, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    func_78087_a(p_78088_2_, limbSwing, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
    GlStateManager.func_179094_E();
    if (this.field_78091_s) {
      GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
      GlStateManager.func_179109_b(0.0F, 24.0F * scale, 0.0F);
      this.Chest.func_78785_a(scale);
    } else {
      this.Chest.func_78785_a(scale);
    } 
    GlStateManager.func_179121_F();
  }
  
  public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
    modelRenderer.field_78795_f = x;
    modelRenderer.field_78796_g = y;
    modelRenderer.field_78808_h = z;
  }
  
  public void func_78087_a(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    if (this.field_78091_s) {
      this.BustFront.field_78807_k = true;
      this.BHair1.field_78807_k = true;
      this.LFHair.field_78807_k = true;
      this.RFHair.field_78807_k = true;
    } else {
      this.BustFront.field_78807_k = false;
      this.BHair1.field_78807_k = false;
      this.LFHair.field_78807_k = false;
      this.RFHair.field_78807_k = false;
    } 
    this.Head.field_78796_g = netHeadYaw * 0.017453292F;
    this.Head.field_78795_f = headPitch * 0.017453292F;
    EntityGhast entity = (EntityGhast)entityIn;
    this.field_78091_s = entity.func_70631_g_();
    this.isSneak = (entity.func_70093_af() || entity.getJukeboxToDanceTo() != null || !entity.field_70122_E);
    this.BustFront.func_78793_a(0.0F, 3.0F, -1.0F);
    this.RArm.func_78793_a(-3.5F, 2.0F, 0.0F);
    this.LArm.func_78793_a(3.5F, 2.0F, 0.0F);
    this.Chest.func_78793_a(0.0F, 1.0F, 0.0F);
    this.LFHair.field_78795_f = entity.field_70721_aZ * 0.75F - headPitch * 0.0045F;
    this.RFHair.field_78795_f = entity.field_70721_aZ * 0.75F - headPitch * 0.0045F;
    this.LFHair.field_78808_h = -(entity.field_70721_aZ * 0.25F - headPitch * 0.0045F);
    this.RFHair.field_78808_h = entity.field_70721_aZ * 0.25F - headPitch * 0.0045F;
    this.BHair1.field_78795_f = entity.field_70721_aZ * 0.25F - headPitch * 0.0045F;
    this.BHair2.field_78795_f = entity.field_70721_aZ * 0.25F - headPitch * 0.0045F;
    this.BHair3.field_78795_f = entity.field_70721_aZ * 0.25F - headPitch * 0.0045F;
    this.BHair4.field_78795_f = entity.field_70721_aZ * 0.25F - headPitch * 0.0045F;
    this.BHair1.field_78808_h = 0.0F;
    this.BHair2.field_78808_h = 0.0F;
    this.BHair3.field_78808_h = 0.0F;
    this.BHair4.field_78808_h = 0.0F;
    this.Chest.field_78795_f = 0.0F;
    this.Abdoman.field_78795_f = 0.0F;
    this.Skirt1.field_78808_h = 0.0F;
    this.Skirt1.field_78796_g = 0.0F;
    this.Skirt1.field_78795_f = 0.0F;
    this.Skirt2.field_78795_f = 0.0F;
    this.Skirt3.field_78795_f = 0.0F;
    this.Skirt4.field_78795_f = 0.0F;
    this.Skirt1.field_78808_h = 0.0F;
    this.Skirt2.field_78808_h = 0.0F;
    this.Skirt3.field_78808_h = 0.0F;
    this.Skirt4.field_78808_h = 0.0F;
    this.Chest.field_78808_h = 0.0F;
    this.Abdoman.field_78808_h = 0.0F;
    this.BustFront.field_78795_f = 0.0F;
    this.Neck.field_78808_h = 0.0F;
    this.Neck.field_78796_g = 0.0F;
    this.Neck.field_78795_f = 0.0F;
    this.Head.field_78808_h = 0.0F;
    this.Skirt1.field_78808_h = 0.0F;
    this.RArm.field_78795_f = 0.0F;
    this.LArm.field_78795_f = 0.0F;
    this.RArm.field_78808_h = 0.0F;
    this.LArm.field_78808_h = 0.0F;
    this.RArm.field_78796_g = 0.0F;
    this.LArm.field_78796_g = 0.0F;
    if (entity.field_70122_E && !entity.func_184207_aI()) {
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
      this.Neck.field_78808_h -= MathHelper.func_76134_b(limbSwing * 0.6662F) * 0.5F * limbSwingAmount * 0.5F;
      this.Chest.field_78808_h += MathHelper.func_76134_b(limbSwing * 0.6662F) * 0.5F * limbSwingAmount * 0.5F;
      this.BustFront.field_78795_f += MathHelper.func_76134_b(limbSwing * 0.6662F - 1.5707964F) * 0.75F * limbSwingAmount * 0.5F;
      this.Abdoman.field_78808_h += MathHelper.func_76134_b(limbSwing * 0.6662F - 0.5F) * 0.5F * limbSwingAmount * 0.5F;
      this.Skirt1.field_78808_h -= MathHelper.func_76134_b(limbSwing * 0.6662F - 0.5F) * 0.5F * limbSwingAmount * 0.5F;
      this.Skirt1.field_78796_g += MathHelper.func_76134_b(limbSwing * 0.6662F - 0.5F) * 1.0F * limbSwingAmount * 0.5F;
      this.RLeg.field_78808_h -= MathHelper.func_76134_b(limbSwing * 0.6662F - 0.5F) * 0.75F * limbSwingAmount * 0.5F;
      this.LLeg.field_78808_h -= MathHelper.func_76134_b(limbSwing * 0.6662F - 0.5F) * 0.75F * limbSwingAmount * 0.5F;
      this.BHair1.field_78808_h += MathHelper.func_76134_b(limbSwing * 0.6662F - 1.0F) * 0.75F * limbSwingAmount * 0.5F;
      this.BHair2.field_78808_h += MathHelper.func_76134_b(limbSwing * 0.6662F - 2.0F) * 0.75F * limbSwingAmount * 0.5F;
      this.BHair3.field_78808_h += MathHelper.func_76134_b(limbSwing * 0.6662F - 3.0F) * 0.75F * limbSwingAmount * 0.5F;
      this.BHair4.field_78808_h += MathHelper.func_76134_b(limbSwing * 0.6662F - 4.0F) * 0.75F * limbSwingAmount * 0.5F;
      if (entity.isSitResting()) {
        this.Head.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.2F) * 0.05F;
        this.Neck.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.2F) * 0.05F;
        this.LFHair.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.2F - 1.0F) * 0.1F;
        this.RFHair.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.2F - 1.0F) * 0.1F;
        this.BHair1.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.2F - 0.5F) * 0.1F;
        this.BHair2.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.2F - 1.0F) * 0.1F;
        this.BHair3.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.2F - 1.5F) * 0.1F;
        this.BHair4.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.2F - 2.0F) * 0.1F;
        this.RArm.field_78795_f = -0.8975979F;
        this.LArm.field_78795_f = -0.8975979F;
        this.RArm.field_78796_g = -0.5F;
        this.LArm.field_78796_g = 0.5F;
        this.RLeg.field_78795_f = -1.25F;
        this.RLeg.field_78796_g = -0.3F;
        this.RLeg.field_78808_h = 0.025F;
        this.LLeg.field_78795_f = -1.5F + MathHelper.func_76134_b(ageInTicks * 0.5F) * 0.05F;
        this.LLeg.field_78796_g = 0.25F;
        this.LLeg.field_78808_h = -0.025F;
        this.Skirt1.field_78795_f = -0.75F;
        this.Skirt2.field_78795_f = -0.75F;
      } 
    } else {
      float f6 = MathHelper.func_76134_b(ageInTicks * 0.1F);
      this.BHair1.field_78795_f += 0.5F + (0.075F + 0.05F * MathHelper.func_76134_b(ageInTicks * 0.1F - 1.0F)) * 3.1415927F;
      this.BHair2.field_78795_f += (0.075F + 0.05F * MathHelper.func_76134_b(ageInTicks * 0.1F - 2.0F)) * 3.1415927F;
      this.BHair3.field_78795_f += (0.075F + 0.05F * MathHelper.func_76134_b(ageInTicks * 0.1F - 3.0F)) * 3.1415927F;
      this.BHair4.field_78795_f += (0.075F + 0.05F * MathHelper.func_76134_b(ageInTicks * 0.1F - 4.0F)) * 3.1415927F;
      if (!this.field_78093_q) {
        this.Neck.field_78795_f -= (0.065F + 0.02F * f6) * 3.1415927F;
        this.Chest.field_78795_f = (0.065F + 0.02F * f6) * 3.1415927F;
        this.Abdoman.field_78795_f = (0.065F + 0.02F * f6) * 3.1415927F;
        this.Skirt1.field_78795_f = (0.0375F + 0.0125F * f6) * 3.1415927F;
        this.Skirt2.field_78795_f = (0.0375F + 0.0125F * f6) * 3.1415927F;
        this.Skirt3.field_78795_f = (0.0375F + 0.0125F * f6) * 3.1415927F;
        this.Skirt4.field_78795_f = (0.0375F + 0.0125F * f6) * 3.1415927F;
        this.LLeg.field_78795_f = (0.1F + 0.05F * f6) * 3.1415927F;
        this.RLeg.field_78795_f = (0.1F + 0.05F * f6) * 3.1415927F;
        if (entity.func_184207_aI()) {
          this.RArm.field_78795_f = -2.25F;
          this.LArm.field_78795_f = -2.25F;
          this.RArm.field_78808_h -= 0.3F;
          this.LArm.field_78808_h += 0.3F;
        } else {
          this.RArm.field_78795_f = (0.1F + 0.2F * f6) * 3.1415927F;
          this.LArm.field_78795_f = (0.1F + 0.2F * f6) * 3.1415927F;
          this.RArm.field_78796_g = (0.1F + -0.15F * f6) * 3.1415927F;
          this.LArm.field_78796_g = (-0.1F + 0.15F * f6) * 3.1415927F;
          this.LArm.field_78808_h = (-0.075F + 0.05F * f6) * 3.1415927F;
          this.RArm.field_78808_h = (0.075F + -0.05F * f6) * 3.1415927F;
        } 
      } 
    } 
    if (this.field_78093_q)
      if (this.field_78091_s) {
        this.Head.field_78795_f = -0.5F;
        this.Neck.field_78795_f = -0.5F;
        this.RArm.field_78795_f = -1.75F;
        this.LArm.field_78795_f = -1.75F;
        this.RArm.field_78796_g = 0.5F;
        this.LArm.field_78796_g = -0.5F;
        this.RLeg.field_78795_f = 0.1F + MathHelper.func_76134_b(ageInTicks * 0.3F + 3.1415927F) * 0.1F + MathHelper.func_76134_b(limbSwing * 0.6662F + 3.1415927F) * 0.25F * limbSwingAmount * 0.5F;
        this.LLeg.field_78795_f = 0.1F + MathHelper.func_76134_b(ageInTicks * 0.3F) * 0.1F + MathHelper.func_76134_b(limbSwing * 0.6662F) * 0.25F * limbSwingAmount * 0.5F;
        this.RLeg.field_78808_h = 0.1F + MathHelper.func_76134_b(ageInTicks * 0.3F - 2.0F) * 0.1F;
        this.LLeg.field_78808_h = -0.1F + MathHelper.func_76134_b(ageInTicks * 0.3F - 2.0F) * 0.1F;
        this.Skirt1.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.3F - 2.0F) * 0.05F;
        this.Skirt2.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.3F - 2.0F) * 0.05F;
        this.Skirt3.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.3F - 2.0F) * 0.05F;
        this.Skirt4.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.3F - 2.0F) * 0.05F;
      } else {
        this.RArm.field_78795_f += -0.62831855F;
        this.LArm.field_78795_f += -0.62831855F;
        this.RLeg.field_78795_f = -1.4137167F;
        this.RLeg.field_78796_g = 0.31415927F;
        this.RLeg.field_78808_h = 0.07853982F;
        this.LLeg.field_78795_f = -1.4137167F;
        this.LLeg.field_78796_g = -0.31415927F;
        this.LLeg.field_78808_h = -0.07853982F;
        this.Skirt1.field_78795_f = -0.75F;
        this.Skirt2.field_78795_f = -0.75F;
      }  
    if (this.isSneak && !entity.isSitResting() && entity.field_70122_E) {
      this.Chest.field_78797_d += 5.0F;
      this.BHair1.field_78795_f += 0.5F + (0.075F + 0.05F * MathHelper.func_76134_b(ageInTicks * 0.1F - 1.0F)) * 3.1415927F;
      this.BHair2.field_78795_f += (0.075F + 0.05F * MathHelper.func_76134_b(ageInTicks * 0.1F - 2.0F)) * 3.1415927F;
      this.BHair3.field_78795_f += (0.075F + 0.05F * MathHelper.func_76134_b(ageInTicks * 0.1F - 3.0F)) * 3.1415927F;
      this.BHair4.field_78795_f += (0.075F + 0.05F * MathHelper.func_76134_b(ageInTicks * 0.1F - 4.0F)) * 3.1415927F;
      float f6 = MathHelper.func_76134_b(ageInTicks * 0.1F);
      this.Neck.field_78795_f -= (0.065F + 0.02F * f6) * 3.1415927F;
      this.Chest.field_78795_f = (0.065F + 0.02F * f6) * 3.1415927F;
      this.Abdoman.field_78795_f = 0.25F + (0.065F + 0.02F * f6) * 3.1415927F;
      this.Skirt1.field_78795_f = (0.0375F + 0.0125F * f6) * 3.1415927F;
      this.Skirt2.field_78795_f = (0.0375F + 0.0125F * f6) * 3.1415927F;
      this.Skirt3.field_78795_f = (0.0375F + 0.0125F * f6) * 3.1415927F;
      this.Skirt4.field_78795_f = (0.0375F + 0.0125F * f6) * 3.1415927F;
      this.LLeg.field_78795_f = (0.1F + 0.05F * f6) * 3.1415927F;
      this.RLeg.field_78795_f = (0.1F + 0.05F * f6) * 3.1415927F;
      this.RArm.field_78795_f = (0.1F + 0.2F * f6) * 3.1415927F;
      this.LArm.field_78795_f = (0.1F + 0.2F * f6) * 3.1415927F;
      this.RArm.field_78796_g = (0.1F + -0.15F * f6) * 3.1415927F;
      this.LArm.field_78796_g = (-0.1F + 0.15F * f6) * 3.1415927F;
      this.LArm.field_78808_h = (-0.075F + 0.05F * f6) * 3.1415927F;
      this.RArm.field_78808_h = (0.075F + -0.05F * f6) * 3.1415927F;
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
    this.RArm.field_78808_h += MathHelper.func_76134_b(ageInTicks * 0.09F) * 0.05F + 0.05F;
    this.LArm.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.09F) * 0.05F + 0.05F;
    this.RArm.field_78795_f += MathHelper.func_76126_a(ageInTicks * 0.067F) * 0.05F;
    this.LArm.field_78795_f -= MathHelper.func_76126_a(ageInTicks * 0.067F) * 0.05F;
    if (this.field_78095_p > 0.0F) {
      float f1 = this.field_78095_p;
      this.Abdoman.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f1) * 6.2831855F) * 0.2F;
      this.RArm.field_78796_g += this.Abdoman.field_78796_g;
      this.LArm.field_78796_g += this.Abdoman.field_78796_g;
      this.LArm.field_78795_f += this.Abdoman.field_78796_g;
      this.BustFront.field_78795_f += this.Abdoman.field_78796_g;
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
      this.Chest.func_78793_a(0.0F + MathHelper.func_76126_a(ageInTicks * 0.125F) * 1.0F, 0.0F - MathHelper.func_76134_b(ageInTicks * 0.125F) * 1.0F, 0.0F + MathHelper.func_76134_b(ageInTicks * 0.125F) * 1.0F);
      this.BHair1.field_78808_h += MathHelper.func_76134_b(ageInTicks * 0.25F - 2.6415927F) * 0.2F;
      this.BHair2.field_78808_h += MathHelper.func_76134_b(ageInTicks * 0.25F - 2.1415927F) * 0.2F;
      this.BHair3.field_78808_h += MathHelper.func_76134_b(ageInTicks * 0.25F - 3.1415927F) * 0.2F;
      this.BHair4.field_78808_h += MathHelper.func_76134_b(ageInTicks * 0.25F - 3.6415927F) * 0.2F;
      this.Head.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.25F - 2.1415927F) * 0.25F;
      this.Neck.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.25F - 3.1415927F) * 0.25F;
      this.Chest.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.25F) * 0.5F;
      this.Abdoman.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.25F - 1.0F) * 0.5F;
      this.RArm.field_78795_f = MathHelper.func_76134_b(ageInTicks * 0.25F - 1.0F) * 0.5F;
      this.LArm.field_78795_f = MathHelper.func_76134_b(ageInTicks * 0.25F - 1.0F) * 0.5F;
      this.RArm.field_78808_h = 1.0F + MathHelper.func_76126_a(ageInTicks * 0.125F) * 0.5F;
      this.LArm.field_78808_h = -1.0F + MathHelper.func_76126_a(ageInTicks * 0.125F) * 0.5F;
      this.RArm.field_78796_g = 0.0F;
      this.LArm.field_78796_g = 0.0F;
      this.Skirt1.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.25F - 2.0F) * 0.15F;
      this.Skirt2.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.25F - 2.0F) * 0.125F;
      this.Skirt3.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.25F - 2.0F) * 0.125F;
      this.Skirt4.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.25F - 2.0F) * 0.125F;
      this.RLeg.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.25F - 2.0F) * 0.5F;
      this.LLeg.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.25F - 2.0F) * 0.5F;
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
}
