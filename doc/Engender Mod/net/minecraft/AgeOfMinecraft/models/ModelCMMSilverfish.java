package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntitySilverfish;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelCMMSilverfish extends ModelBase implements ICappedModel {
  public ModelRenderer LLeg;
  
  public ModelRenderer Abdoman;
  
  public ModelRenderer RArm;
  
  public ModelRenderer HTop;
  
  public ModelRenderer LArm;
  
  public ModelRenderer RLeg;
  
  public ModelRenderer Body;
  
  public ModelRenderer Skirt1;
  
  public ModelRenderer Neck;
  
  public ModelRenderer Head;
  
  public ModelRenderer Hair;
  
  public ModelRenderer Hat;
  
  public ModelRenderer Skirt2;
  
  public ModelRenderer Skirt3;
  
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
  
  public ModelCMMSilverfish() {
    this.bipedCape = new ModelRenderer(this, 0, 0);
    this.bipedCape.func_78787_b(64, 32);
    this.bipedCape.func_78790_a(-5.0F, 0.0F, -1.0F, 10, 16, 1, 0.0F);
    this.field_78090_t = 64;
    this.field_78089_u = 64;
    this.Abdoman = new ModelRenderer(this, 0, 44);
    this.Abdoman.func_78793_a(0.0F, 13.0F, 0.0F);
    this.Abdoman.func_78790_a(-3.0F, -5.0F, -1.5F, 6, 5, 3, 0.0F);
    this.RLeg = new ModelRenderer(this, 52, 0);
    this.RLeg.func_78793_a(-2.0F, 14.0F, 0.0F);
    this.RLeg.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 10, 3, 0.0F);
    this.HTop = new ModelRenderer(this, 0, 28);
    this.HTop.func_78793_a(0.0F, 2.0F, 0.0F);
    this.HTop.func_78790_a(-3.5F, -8.0F, -3.0F, 7, 1, 6, 0.0F);
    this.Neck = new ModelRenderer(this, 20, 35);
    this.Neck.func_78793_a(0.0F, -5.0F, 0.0F);
    this.Neck.func_78790_a(-1.5F, -2.0F, -1.5F, 3, 2, 3, 0.0F);
    this.RArm = new ModelRenderer(this, 36, 0);
    this.RArm.func_78793_a(-3.5F, 4.0F, 0.0F);
    this.RArm.func_78790_a(-2.0F, -1.0F, -1.0F, 2, 9, 2, 0.0F);
    this.Head = new ModelRenderer(this, 0, 0);
    this.Head.func_78793_a(0.0F, -1.0F, 0.0F);
    this.Head.func_78790_a(-3.5F, -7.0F, -3.0F, 7, 7, 6, 0.0F);
    this.Skirt2 = new ModelRenderer(this, 34, 43);
    this.Skirt2.func_78793_a(0.0F, 3.0F, 0.0F);
    this.Skirt2.func_78790_a(-4.5F, 0.0F, -3.0F, 9, 3, 6, 0.0F);
    this.LLeg = new ModelRenderer(this, 52, 0);
    this.LLeg.field_78809_i = true;
    this.LLeg.func_78793_a(2.0F, 14.0F, 0.0F);
    this.LLeg.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 10, 3, 0.0F);
    this.LArm = new ModelRenderer(this, 36, 0);
    this.LArm.field_78809_i = true;
    this.LArm.func_78793_a(3.5F, 4.0F, 0.0F);
    this.LArm.func_78790_a(0.0F, -1.0F, -1.0F, 2, 9, 2, 0.0F);
    this.Skirt3 = new ModelRenderer(this, 26, 52);
    this.Skirt3.func_78793_a(0.0F, 3.0F, 0.0F);
    this.Skirt3.func_78790_a(-5.5F, 0.0F, -4.0F, 11, 4, 8, 0.0F);
    this.Body = new ModelRenderer(this, 0, 36);
    this.Body.func_78793_a(0.0F, -5.0F, 0.0F);
    this.Body.func_78790_a(-3.5F, -5.0F, -1.5F, 7, 5, 3, 0.0F);
    this.Hat = new ModelRenderer(this, 0, 52);
    this.Hat.func_78793_a(0.0F, 0.0F, 0.0F);
    this.Hat.func_78790_a(3.0F, -8.5F, -2.0F, 3, 5, 4, 0.0F);
    this.Skirt1 = new ModelRenderer(this, 42, 36);
    this.Skirt1.func_78793_a(0.0F, -2.0F, 0.0F);
    this.Skirt1.func_78790_a(-3.5F, 0.0F, -2.0F, 7, 3, 4, 0.0F);
    this.Hair = new ModelRenderer(this, 0, 13);
    this.Hair.func_78793_a(0.0F, 0.0F, 0.0F);
    this.Hair.func_78790_a(-4.0F, -7.0F, -3.5F, 8, 8, 7, 0.0F);
    this.Body.func_78792_a(this.Neck);
    this.Neck.func_78792_a(this.Head);
    this.Skirt1.func_78792_a(this.Skirt2);
    this.Skirt2.func_78792_a(this.Skirt3);
    this.Abdoman.func_78792_a(this.Body);
    this.Head.func_78792_a(this.Hat);
    this.Abdoman.func_78792_a(this.Skirt1);
    this.Head.func_78792_a(this.Hair);
    this.Head.func_78792_a(this.HTop);
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
    limbSwing *= 3.0F;
    this.Neck.field_78796_g = netHeadYaw * 0.017453292F / 2.0F;
    this.Neck.field_78795_f = headPitch * 0.017453292F / 2.0F;
    this.Head.field_78796_g = netHeadYaw * 0.017453292F / 2.0F;
    this.Head.field_78795_f = headPitch * 0.017453292F / 2.0F;
    EntitySilverfish entity = (EntitySilverfish)entityIn;
    if (entity.func_70093_af()) {
      this.isSneak = true;
    } else {
      this.isSneak = false;
    } 
    this.LLeg.func_78793_a(2.0F, 14.0F, 0.0F);
    this.RLeg.func_78793_a(-2.0F, 14.0F, 0.0F);
    this.Abdoman.func_78793_a(0.0F, 12.0F, 0.0F);
    this.RArm.func_78793_a(-3.5F, 4.0F, 0.0F);
    this.LArm.func_78793_a(3.5F, 4.0F, 0.0F);
    this.Body.field_78795_f = 0.0F;
    this.Abdoman.field_78795_f = 0.0F;
    this.Body.field_78808_h = 0.0F;
    this.Abdoman.field_78808_h = 0.0F;
    this.Neck.field_78808_h = 0.0F;
    this.Head.field_78808_h = 0.0F;
    this.Skirt1.field_78795_f = 0.0F;
    this.Skirt2.field_78795_f = 0.0F;
    this.Skirt3.field_78795_f = 0.0F;
    this.Skirt1.field_78808_h = 0.0F;
    this.Skirt2.field_78808_h = 0.0F;
    this.Skirt3.field_78808_h = 0.0F;
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
    this.Neck.field_78808_h += MathHelper.func_76134_b(limbSwing * 0.6662F) * 0.3F * limbSwingAmount * 0.5F;
    this.Body.field_78808_h -= MathHelper.func_76134_b(limbSwing * 0.6662F) * 0.4F * limbSwingAmount * 0.5F;
    this.Abdoman.field_78808_h += MathHelper.func_76134_b(limbSwing * 0.6662F) * 0.2F * limbSwingAmount * 0.5F;
    this.Abdoman.field_78798_e = MathHelper.func_76134_b(limbSwing * 0.6662F) * 0.5F * limbSwingAmount * 0.5F;
    this.Abdoman.field_78797_d = 12.0F + MathHelper.func_76134_b(limbSwing * 0.6662F) * 0.1F * limbSwingAmount * 0.5F;
    this.RArm.field_78800_c += MathHelper.func_76134_b(limbSwing * 0.6662F) * 0.5F * limbSwingAmount * 0.5F;
    this.LArm.field_78800_c += MathHelper.func_76134_b(limbSwing * 0.6662F) * 0.5F * limbSwingAmount * 0.5F;
    if (!entity.field_70122_E)
      if (!entity.func_184207_aI() && !this.field_78093_q) {
        this.RArm.field_78808_h -= 0.6F;
        this.LArm.field_78808_h += 0.6F;
        this.RLeg.field_78808_h = 0.5F;
        this.LLeg.field_78808_h = -0.5F;
        this.RArm.field_78795_f -= 2.0F;
        this.LArm.field_78795_f -= 2.0F;
        this.RLeg.field_78795_f = (float)(this.RLeg.field_78795_f + (MathHelper.func_76134_b(ageInTicks * 0.5F) * 0.25F) - ((entity.field_70181_x > 2.0D) ? 1.0D : ((entity.field_70181_x < -2.0D) ? -1.0D : (entity.field_70181_x / 2.0D))));
        this.LLeg.field_78795_f = (float)(this.LLeg.field_78795_f + (MathHelper.func_76134_b(ageInTicks * 0.5F + 3.1415927F) * 0.25F) - ((entity.field_70181_x > 2.0D) ? 1.0D : ((entity.field_70181_x < -2.0D) ? -1.0D : (entity.field_70181_x / 2.0D))));
      }  
    if (this.isSneak && !entity.isSitResting() && entity.field_70122_E) {
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
      this.RArm.field_78795_f += -0.15707964F;
      this.LArm.field_78795_f += -0.15707964F;
      this.RLeg.field_78795_f = -1.4137167F;
      this.RLeg.field_78796_g = 0.05F - MathHelper.func_76134_b(ageInTicks * 0.45F) * 0.1F;
      this.RLeg.field_78808_h = 0.07853982F;
      this.LLeg.field_78795_f = -1.4137167F;
      this.LLeg.field_78796_g = -0.05F + MathHelper.func_76134_b(ageInTicks * 0.45F) * 0.1F;
      this.LLeg.field_78808_h = -0.07853982F;
      this.Skirt1.field_78795_f = -0.47123888F;
      this.Skirt2.field_78795_f = -0.47123888F;
      this.Skirt3.field_78795_f = -0.47123888F;
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
      this.Neck.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.5F - 3.1415927F) * 0.5F;
      this.Head.field_78795_f += MathHelper.func_76134_b(ageInTicks * 1.0F) * 0.5F;
      this.Body.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.5F - 3.1415927F) * 0.5F;
      this.Abdoman.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.5F) * 0.5F;
      this.Abdoman.field_78800_c = MathHelper.func_76134_b(ageInTicks * 0.5F - 3.1415927F) * 1.0F;
      this.Abdoman.field_78797_d = 12.0F + MathHelper.func_76134_b(ageInTicks * 1.0F) * 1.0F;
      this.RArm.field_78800_c = -3.5F + MathHelper.func_76134_b(ageInTicks * 0.5F) * 3.0F;
      this.LArm.field_78800_c = 3.5F + MathHelper.func_76134_b(ageInTicks * 0.5F) * 3.0F;
      this.RArm.field_78797_d = 4.0F + MathHelper.func_76134_b(ageInTicks * 1.0F) * 1.0F;
      this.LArm.field_78797_d = 4.0F + MathHelper.func_76134_b(ageInTicks * 1.0F) * 1.0F;
      this.RArm.field_78795_f = -1.0F + MathHelper.func_76134_b(ageInTicks * 0.5F - 2.0F) * 1.0F;
      this.LArm.field_78795_f = -1.0F + MathHelper.func_76134_b(ageInTicks * 0.5F - 2.0F) * 1.0F;
      this.RArm.field_78808_h = 2.0F;
      this.LArm.field_78808_h = -2.0F;
      this.RArm.field_78796_g = 0.0F;
      this.LArm.field_78796_g = 0.0F;
      this.LLeg.field_78808_h = -(MathHelper.func_76134_b(ageInTicks * 0.5F) * 0.2F);
      this.LLeg.field_78795_f = 0.0F;
      this.RLeg.field_78795_f = -(MathHelper.func_76134_b(ageInTicks * 0.5F) * 0.5F);
      this.RLeg.field_78800_c = -2.0F - MathHelper.func_76134_b(ageInTicks * 0.5F) * 2.0F;
      this.LLeg.field_78800_c = 2.0F - MathHelper.func_76134_b(ageInTicks * 0.5F) * 2.0F;
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
