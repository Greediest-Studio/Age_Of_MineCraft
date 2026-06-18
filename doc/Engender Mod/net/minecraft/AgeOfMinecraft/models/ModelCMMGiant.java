package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityGiant;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class ModelCMMGiant extends ModelGiant implements ICappedModel {
  public ModelRenderer Abdoman;
  
  public ModelRenderer RArm;
  
  public ModelRenderer LArm;
  
  public ModelRenderer RLeg;
  
  public ModelRenderer LLeg;
  
  public ModelRenderer Body;
  
  public ModelRenderer Body2Z;
  
  public ModelRenderer Neck;
  
  public ModelRenderer BustFront;
  
  public ModelRenderer Head;
  
  public ModelRenderer HSide;
  
  public ModelRenderer Hair;
  
  public ModelRenderer HTop;
  
  public ModelRenderer RArm2;
  
  public ModelRenderer LArm2;
  
  public ModelRenderer bipedCape;
  
  public void renderCape(float scale, float flo1, float flo2, float flo3) {
    GlStateManager.func_179094_E();
    GlStateManager.func_179109_b(0.0F, this.field_78117_n ? 0.25F : 0.075F, this.field_78117_n ? -0.625F : -0.0325F);
    GlStateManager.func_179114_b(6.0F + flo2 / 2.0F + flo1 + (this.field_78117_n ? 20.0F : 1.0F), 1.0F, 0.0F, 0.0F);
    GlStateManager.func_179114_b(flo3 / 2.0F, 0.0F, 0.0F, 1.0F);
    GlStateManager.func_179114_b(-flo3 / 2.0F, 0.0F, 1.0F, 0.0F);
    GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
    this.bipedCape.func_78785_a(scale);
    GlStateManager.func_179121_F();
  }
  
  public ModelCMMGiant() {
    this.bipedCape = new ModelRenderer((ModelBase)this, 0, 0);
    this.bipedCape.func_78787_b(64, 32);
    this.bipedCape.func_78790_a(-5.0F, 0.0F, -1.0F, 10, 16, 1, 0.0F);
    this.field_78090_t = 64;
    this.field_78089_u = 128;
    this.HSide = new ModelRenderer((ModelBase)this, 0, 71);
    this.HSide.func_78793_a(0.0F, 0.0F, 0.0F);
    this.HSide.func_78790_a(-5.0F, -6.0F, -3.5F, 10, 7, 6, 0.0F);
    this.RArm2 = new ModelRenderer((ModelBase)this, 30, 14);
    this.RArm2.func_78793_a(0.0F, 0.0F, 0.0F);
    this.RArm2.func_78790_a(-2.5F, 4.0F, -1.5F, 3, 6, 3, 0.0F);
    this.Head = new ModelRenderer((ModelBase)this, 0, 0);
    this.Head.func_78793_a(0.0F, -1.0F, 0.0F);
    this.Head.func_78790_a(-3.5F, -7.0F, -3.0F, 7, 7, 6, 0.0F);
    this.HTop = new ModelRenderer((ModelBase)this, 0, 30);
    this.HTop.func_78793_a(0.0F, 0.0F, 0.0F);
    this.HTop.func_78790_a(-3.0F, -8.0F, -2.5F, 6, 1, 5, 0.0F);
    this.LArm = new ModelRenderer((ModelBase)this, 36, 0);
    this.LArm.field_78809_i = true;
    this.LArm.func_78793_a(3.5F, 2.0F, 0.0F);
    this.LArm.func_78790_a(0.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
    this.RLeg = new ModelRenderer((ModelBase)this, 52, 0);
    this.RLeg.func_78793_a(-2.0F, 12.0F, 0.0F);
    this.RLeg.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 12, 3, 0.0F);
    this.RArm = new ModelRenderer((ModelBase)this, 36, 0);
    this.RArm.func_78793_a(-3.5F, 2.0F, 0.0F);
    this.RArm.func_78790_a(-2.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
    this.Body = new ModelRenderer((ModelBase)this, 0, 47);
    this.Body.func_78793_a(0.0F, -6.0F, 0.0F);
    this.Body.func_78790_a(-3.5F, -5.0F, -1.5F, 7, 5, 3, 0.0F);
    this.Body2Z = new ModelRenderer((ModelBase)this, 0, 64);
    this.Body2Z.func_78793_a(0.0F, -3.0F, 0.0F);
    this.Body2Z.func_78790_a(-3.5F, 0.0F, -1.5F, 7, 4, 3, 0.0F);
    this.LLeg = new ModelRenderer((ModelBase)this, 52, 0);
    this.LLeg.field_78809_i = true;
    this.LLeg.func_78793_a(2.0F, 12.0F, 0.0F);
    this.LLeg.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 12, 3, 0.0F);
    this.Hair = new ModelRenderer((ModelBase)this, 0, 13);
    this.Hair.func_78793_a(0.0F, 0.0F, 0.0F);
    this.Hair.func_78790_a(-4.0F, -7.0F, -3.5F, 8, 10, 7, 0.0F);
    this.Neck = new ModelRenderer((ModelBase)this, 0, 42);
    this.Neck.func_78793_a(0.0F, -5.0F, 0.0F);
    this.Neck.func_78790_a(-1.5F, -2.0F, -1.5F, 3, 2, 3, 0.0F);
    this.LArm2 = new ModelRenderer((ModelBase)this, 30, 15);
    this.LArm2.field_78809_i = true;
    this.LArm2.func_78793_a(0.0F, 0.0F, 0.0F);
    this.LArm2.func_78790_a(-0.5F, 4.0F, -1.5F, 3, 6, 3, 0.0F);
    this.Abdoman = new ModelRenderer((ModelBase)this, 0, 55);
    this.Abdoman.func_78793_a(0.0F, 12.0F, 0.0F);
    this.Abdoman.func_78790_a(-3.0F, -6.0F, -1.5F, 6, 6, 3, 0.0F);
    this.BustFront = new ModelRenderer((ModelBase)this, 20, 49);
    this.BustFront.func_78793_a(0.0F, -1.7F, -1.0F);
    this.BustFront.func_78790_a(-3.0F, -1.0F, -2.0F, 6, 2, 2, 0.0F);
    this.Head.func_78792_a(this.HSide);
    this.RArm.func_78792_a(this.RArm2);
    this.Neck.func_78792_a(this.Head);
    this.Head.func_78792_a(this.HTop);
    this.Abdoman.func_78792_a(this.Body);
    this.Abdoman.func_78792_a(this.Body2Z);
    this.Head.func_78792_a(this.Hair);
    this.Body.func_78792_a(this.Neck);
    this.LArm.func_78792_a(this.LArm2);
    this.Body.func_78792_a(this.BustFront);
    this.field_78116_c.field_78807_k = true;
    this.field_78115_e.field_78807_k = true;
    this.field_178723_h.field_78807_k = true;
    this.field_178724_i.field_78807_k = true;
    this.field_178721_j.field_78807_k = true;
    this.field_178722_k.field_78807_k = true;
    this.field_178720_f.field_78807_k = true;
  }
  
  public ModelCMMGiant(float modelSize, boolean p_i46303_2_) {
    super(modelSize, p_i46303_2_);
    this.field_78090_t = 64;
    this.field_78089_u = 32;
    this.HSide = new ModelRenderer((ModelBase)this, 0, 71);
    this.HSide.func_78793_a(0.0F, 0.0F, 0.0F);
    this.HSide.func_78790_a(-5.0F, -6.0F, -3.5F, 10, 7, 6, 0.0F);
    this.RArm2 = new ModelRenderer((ModelBase)this, 30, 14);
    this.RArm2.func_78793_a(0.0F, 0.0F, 0.0F);
    this.RArm2.func_78790_a(-2.5F, 4.0F, -1.5F, 3, 6, 3, 0.0F);
    this.Head = new ModelRenderer((ModelBase)this, 0, 0);
    this.Head.func_78793_a(0.0F, -1.0F, 0.0F);
    this.Head.func_78790_a(-3.5F, -7.0F, -3.0F, 7, 7, 6, 0.0F);
    this.HTop = new ModelRenderer((ModelBase)this, 0, 30);
    this.HTop.func_78793_a(0.0F, 0.0F, 0.0F);
    this.HTop.func_78790_a(-3.0F, -8.0F, -2.5F, 6, 1, 5, 0.0F);
    this.LArm = new ModelRenderer((ModelBase)this, 36, 0);
    this.LArm.field_78809_i = true;
    this.LArm.func_78793_a(3.5F, 2.0F, 0.0F);
    this.LArm.func_78790_a(0.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
    this.RLeg = new ModelRenderer((ModelBase)this, 52, 0);
    this.RLeg.func_78793_a(-2.0F, 12.0F, 0.0F);
    this.RLeg.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 12, 3, 0.0F);
    this.RArm = new ModelRenderer((ModelBase)this, 36, 0);
    this.RArm.func_78793_a(-3.5F, 2.0F, 0.0F);
    this.RArm.func_78790_a(-2.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
    this.Body = new ModelRenderer((ModelBase)this, 0, 47);
    this.Body.func_78793_a(0.0F, -6.0F, 0.0F);
    this.Body.func_78790_a(-3.5F, -5.0F, -1.5F, 7, 5, 3, 0.0F);
    this.Body2Z = new ModelRenderer((ModelBase)this, 0, 64);
    this.Body2Z.func_78793_a(0.0F, -3.0F, 0.0F);
    this.Body2Z.func_78790_a(-3.5F, 0.0F, -1.5F, 7, 4, 3, 0.0F);
    this.LLeg = new ModelRenderer((ModelBase)this, 52, 0);
    this.LLeg.field_78809_i = true;
    this.LLeg.func_78793_a(2.0F, 12.0F, 0.0F);
    this.LLeg.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 12, 3, 0.0F);
    this.Hair = new ModelRenderer((ModelBase)this, 0, 13);
    this.Hair.func_78793_a(0.0F, 0.0F, 0.0F);
    this.Hair.func_78790_a(-4.0F, -7.0F, -3.5F, 8, 10, 7, 0.0F);
    this.Neck = new ModelRenderer((ModelBase)this, 0, 42);
    this.Neck.func_78793_a(0.0F, -5.0F, 0.0F);
    this.Neck.func_78790_a(-1.5F, -2.0F, -1.5F, 3, 2, 3, 0.0F);
    this.LArm2 = new ModelRenderer((ModelBase)this, 30, 15);
    this.LArm2.field_78809_i = true;
    this.LArm2.func_78793_a(0.0F, 0.0F, 0.0F);
    this.LArm2.func_78790_a(-0.5F, 4.0F, -1.5F, 3, 6, 3, 0.0F);
    this.Abdoman = new ModelRenderer((ModelBase)this, 0, 55);
    this.Abdoman.func_78793_a(0.0F, 12.0F, 0.0F);
    this.Abdoman.func_78790_a(-3.0F, -6.0F, -1.5F, 6, 6, 3, 0.0F);
    this.BustFront = new ModelRenderer((ModelBase)this, 20, 49);
    this.BustFront.func_78793_a(0.0F, -1.7F, -1.0F);
    this.BustFront.func_78790_a(-3.0F, -1.0F, -2.0F, 6, 2, 2, 0.0F);
    this.Head.func_78792_a(this.HSide);
    this.RArm.func_78792_a(this.RArm2);
    this.Neck.func_78792_a(this.Head);
    this.Head.func_78792_a(this.HTop);
    this.Abdoman.func_78792_a(this.Body);
    this.Abdoman.func_78792_a(this.Body2Z);
    this.Head.func_78792_a(this.Hair);
    this.Body.func_78792_a(this.Neck);
    this.LArm.func_78792_a(this.LArm2);
    this.Body.func_78792_a(this.BustFront);
    this.RArm.field_78807_k = true;
    this.LArm.field_78807_k = true;
    this.RLeg.field_78807_k = true;
    this.LLeg.field_78807_k = true;
    this.Abdoman.field_78807_k = true;
  }
  
  public void func_78088_a(Entity entityIn, float p_78088_2_, float limbSwing, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    func_78087_a(p_78088_2_, limbSwing, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
    GlStateManager.func_179094_E();
    if (this.field_78091_s) {
      GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
      GlStateManager.func_179109_b(0.0F, 24.0F * scale, 0.0F);
      this.field_78116_c.func_78785_a(scale);
      this.field_78115_e.func_78785_a(scale);
      this.field_178723_h.func_78785_a(scale);
      this.field_178724_i.func_78785_a(scale);
      this.field_178721_j.func_78785_a(scale);
      this.field_178722_k.func_78785_a(scale);
      this.field_178720_f.func_78785_a(scale);
      this.RArm.func_78785_a(scale);
      this.LArm.func_78785_a(scale);
      this.RLeg.func_78785_a(scale);
      this.LLeg.func_78785_a(scale);
      this.Abdoman.func_78785_a(scale);
    } else {
      this.field_78116_c.func_78785_a(scale);
      this.field_78115_e.func_78785_a(scale);
      this.field_178723_h.func_78785_a(scale);
      this.field_178724_i.func_78785_a(scale);
      this.field_178721_j.func_78785_a(scale);
      this.field_178722_k.func_78785_a(scale);
      this.field_178720_f.func_78785_a(scale);
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
  
  protected ModelRenderer func_187074_a(EnumHandSide side) {
    return (side == EnumHandSide.LEFT) ? this.LArm : this.RArm;
  }
  
  public void func_78087_a(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    super.func_78087_a(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    if (this.field_78091_s) {
      this.BustFront.field_78807_k = true;
    } else {
      this.BustFront.field_78807_k = false;
    } 
    this.Head.field_78796_g = netHeadYaw * 0.017453292F;
    this.Head.field_78795_f = headPitch * 0.017453292F;
    EntityGiant entity = (EntityGiant)entityIn;
    if (entity.func_70093_af()) {
      this.field_78117_n = true;
    } else {
      this.field_78117_n = false;
    } 
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
    if (!entity.field_70122_E && !entity.func_184207_aI() && !this.field_78093_q) {
      this.RArm.field_78808_h -= 0.6F;
      this.LArm.field_78808_h += 0.6F;
      this.RLeg.field_78808_h = 0.5F;
      this.LLeg.field_78808_h = -0.5F;
      this.RArm.field_78795_f--;
      this.LArm.field_78795_f--;
      this.RLeg.field_78795_f += MathHelper.func_76134_b(ageInTicks * 0.1F) * 0.25F;
      this.LLeg.field_78795_f += MathHelper.func_76134_b(ageInTicks * 0.1F + 3.1415927F) * 0.25F;
    } 
    this.field_78115_e.field_78800_c = 0.0F;
    this.field_78115_e.field_78797_d = 0.0F;
    this.field_78115_e.field_78798_e = 0.0F;
    this.field_78116_c.field_78800_c = 0.0F;
    this.field_78116_c.field_78797_d = 0.0F;
    this.field_78116_c.field_78798_e = 0.0F;
    if (this.field_78117_n && !entity.isSitResting() && entity.field_70122_E && entity.getJukeboxToDanceTo() == null) {
      this.Abdoman.field_78795_f++;
      this.Body.field_78795_f -= 0.5F;
      this.Head.field_78795_f -= 0.5F;
      this.RArm.field_78795_f += 0.75F;
      this.LArm.field_78795_f += 0.75F;
      this.Abdoman.field_78798_e--;
      this.field_78115_e.field_78798_e -= 8.0F;
      this.field_78115_e.field_78797_d += 6.0F;
      this.field_78116_c.field_78798_e -= 8.0F;
      this.field_78116_c.field_78797_d += 3.0F;
      this.field_78115_e.field_78795_f += 0.5F;
      this.RArm.field_78797_d += 4.0F;
      this.LArm.field_78797_d += 4.0F;
      this.RArm.field_78798_e -= 8.0F;
      this.LArm.field_78798_e -= 8.0F;
    } 
    if (entity.isSitResting()) {
      this.RArm.field_78795_f += -0.62831855F;
      this.LArm.field_78795_f += -0.62831855F;
      this.RLeg.field_78795_f = -1.5F;
      this.RLeg.field_78796_g = 0.15707964F;
      this.RLeg.field_78808_h = 0.08F;
      this.LLeg.field_78795_f = -1.5F;
      this.LLeg.field_78796_g = -0.15707964F;
      this.LLeg.field_78808_h = -0.08F;
      if (entity.isSitResting() && entity.getCurrentBook().func_190926_b()) {
        this.RArm.field_78795_f = 2.0F - MathHelper.func_76134_b(ageInTicks * 0.2F) * 0.5F;
        this.LArm.field_78795_f = 2.0F - MathHelper.func_76134_b(ageInTicks * 0.2F) * 0.5F;
        this.Abdoman.field_78795_f = -1.0F - MathHelper.func_76134_b(ageInTicks * 0.2F) * 0.5F;
        this.RArm.field_78798_e = 7.0F + MathHelper.func_76134_b(ageInTicks * 0.2F) * 3.0F;
        this.LArm.field_78798_e = 7.0F + MathHelper.func_76134_b(ageInTicks * 0.2F) * 3.0F;
        this.RArm.field_78797_d = 7.0F + MathHelper.func_76134_b(ageInTicks * 0.2F) * 3.0F;
        this.LArm.field_78797_d = 7.0F + MathHelper.func_76134_b(ageInTicks * 0.2F) * 3.0F;
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
    if (this.field_78095_p > 0.0F) {
      float f4 = this.field_78095_p;
      this.Abdoman.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f4) * 6.2831855F) * 0.2F;
      this.RArm.field_78798_e = MathHelper.func_76126_a(this.Abdoman.field_78796_g) * 5.0F;
      this.RArm.field_78800_c = -MathHelper.func_76134_b(this.Abdoman.field_78796_g) * 3.0F;
      this.LArm.field_78798_e = -MathHelper.func_76126_a(this.Abdoman.field_78796_g) * 5.0F;
      this.LArm.field_78800_c = MathHelper.func_76134_b(this.Abdoman.field_78796_g) * 3.0F;
      this.RArm.field_78796_g += this.Abdoman.field_78796_g;
      this.LArm.field_78796_g += this.Abdoman.field_78796_g;
      this.LArm.field_78795_f += this.Abdoman.field_78796_g;
      f4 = 1.0F - this.field_78095_p;
      f4 *= f4;
      f4 *= f4;
      f4 = 1.0F - f4;
      float f5 = MathHelper.func_76126_a(f4 * 3.1415927F);
      float f3 = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F) * -(this.Neck.field_78795_f - 0.7F) * 0.75F;
      if (entity.func_184638_cS()) {
        this.Abdoman.field_78796_g *= -1.0F;
        this.LArm.field_78795_f = (float)(this.LArm.field_78795_f - f5 * 1.2D + f3);
        this.LArm.field_78796_g += this.Abdoman.field_78796_g * 2.0F;
        this.LArm.field_78808_h += MathHelper.func_76126_a(this.field_78095_p * 3.1415927F) * -0.4F;
      } else {
        this.RArm.field_78795_f = (float)(this.RArm.field_78795_f - f5 * 1.2D + f3);
        this.RArm.field_78796_g += this.Abdoman.field_78796_g * 2.0F;
        this.RArm.field_78808_h += MathHelper.func_76126_a(this.field_78095_p * 3.1415927F) * -0.4F;
      } 
    } 
    switch (this.field_187075_l) {
      default:
        this.LArm.field_78796_g = 0.0F;
        break;
      case BLOCK:
        this.LArm.field_78795_f = this.LArm.field_78795_f * 0.5F - 1.0F;
        this.LArm.field_78796_g = 0.75F;
        break;
      case ITEM:
        this.LArm.field_78795_f = this.LArm.field_78795_f * 0.5F - 0.31415927F;
        this.LArm.field_78796_g = 0.0F;
        break;
      case BOW_AND_ARROW:
        this.RArm.field_78796_g = -0.1F + this.Neck.field_78796_g + this.Head.field_78796_g - 0.4F;
        this.LArm.field_78796_g = 0.1F + this.Neck.field_78796_g + this.Head.field_78796_g;
        this.RArm.field_78795_f = -1.5707964F + this.Neck.field_78795_f + this.Head.field_78795_f;
        this.LArm.field_78795_f = -1.5707964F + this.Neck.field_78795_f + this.Head.field_78795_f;
        break;
    } 
    switch (this.field_187076_m) {
      default:
        this.RArm.field_78796_g = 0.0F;
        break;
      case BLOCK:
        this.RArm.field_78795_f = this.RArm.field_78795_f * 0.5F - 1.0F;
        this.RArm.field_78796_g = -0.75F;
        break;
      case ITEM:
        this.RArm.field_78795_f = this.RArm.field_78795_f * 0.5F - 0.31415927F;
        this.RArm.field_78796_g = 0.0F;
        break;
      case BOW_AND_ARROW:
        this.RArm.field_78796_g = -0.1F + this.Neck.field_78796_g + this.Head.field_78796_g;
        this.LArm.field_78796_g = 0.1F + this.Neck.field_78796_g + this.Head.field_78796_g + 0.4F;
        this.RArm.field_78795_f = -1.5707964F + this.Neck.field_78795_f + this.Head.field_78795_f;
        this.LArm.field_78795_f = -1.5707964F + this.Neck.field_78795_f + this.Head.field_78795_f;
        break;
    } 
    float f = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F);
    float f1 = MathHelper.func_76126_a((1.0F - (1.0F - this.field_78095_p) * (1.0F - this.field_78095_p)) * 3.1415927F);
    this.RArm.field_78808_h = 0.0F;
    this.LArm.field_78808_h = 0.0F;
    this.RArm.field_78796_g = -(f * 0.6F);
    this.LArm.field_78796_g = f * 0.6F;
    float f2 = -1.0471976F;
    this.RArm.field_78795_f = this.field_78116_c.field_78795_f + f2 - 0.5F;
    this.LArm.field_78795_f = this.field_78116_c.field_78795_f + f2 - 0.5F;
    this.RArm.field_78795_f += f * 1.2F - f1 * 0.4F;
    this.LArm.field_78795_f += f * 1.2F - f1 * 0.4F;
    if (this.field_78093_q)
      if (this.field_78091_s) {
        this.Head.field_78795_f = 0.1F;
        this.Neck.field_78795_f = 0.1F;
        this.RArm.field_78795_f = -1.0F;
        this.LArm.field_78795_f = -1.0F;
        this.RArm.field_78796_g = -0.5F;
        this.LArm.field_78796_g = 0.5F;
        this.RLeg.field_78795_f = 1.5F;
        this.LLeg.field_78795_f = 1.5F;
        this.RLeg.field_78796_g = -0.25F;
        this.LLeg.field_78796_g = 0.25F;
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
    this.RArm.field_78808_h += MathHelper.func_76134_b(ageInTicks * 0.09F) * 0.05F + 0.1F;
    this.LArm.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.09F) * 0.05F + 0.1F;
    this.RArm.field_78795_f += MathHelper.func_76126_a(ageInTicks * 0.067F) * 0.05F;
    this.LArm.field_78795_f -= MathHelper.func_76126_a(ageInTicks * 0.067F) * 0.05F;
    if (entity.getJukeboxToDanceTo() != null) {
      this.Neck.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.125F - 3.1415927F) * 0.125F;
      this.Body.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.125F - 3.1415927F) * 0.25F;
      this.Abdoman.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.125F) * 0.25F;
      this.Abdoman.field_78800_c = MathHelper.func_76134_b(ageInTicks * 0.125F - 3.1415927F) * 0.25F;
      this.Abdoman.field_78797_d = 12.0F + MathHelper.func_76134_b(ageInTicks * 0.25F) * 1.0F;
      this.RArm.field_78800_c = -3.5F + MathHelper.func_76134_b(ageInTicks * 0.125F) * 1.5F;
      this.LArm.field_78800_c = 3.5F + MathHelper.func_76134_b(ageInTicks * 0.125F) * 1.5F;
      this.Abdoman.field_78798_e = 0.0F + MathHelper.func_76134_b(ageInTicks * 0.125F) * 6.0F;
      this.RArm.field_78798_e = 0.0F + MathHelper.func_76134_b(ageInTicks * 0.125F) * 6.0F;
      this.LArm.field_78798_e = 0.0F + MathHelper.func_76134_b(ageInTicks * 0.125F) * 6.0F;
      this.RLeg.field_78798_e = 0.0F + MathHelper.func_76134_b(ageInTicks * 0.125F) * 6.0F;
      this.LLeg.field_78798_e = 0.0F + MathHelper.func_76134_b(ageInTicks * 0.125F) * 6.0F;
      this.RArm.field_78797_d = 3.0F + MathHelper.func_76134_b(ageInTicks * 0.25F) * 1.0F;
      this.LArm.field_78797_d = 3.0F + MathHelper.func_76134_b(ageInTicks * 0.25F) * 1.0F;
      this.RArm.field_78795_f = -0.5F;
      this.LArm.field_78795_f = -0.5F;
      this.RArm.field_78808_h = 2.0F + MathHelper.func_76134_b(ageInTicks * 0.25F - 3.1415927F) * 0.25F - MathHelper.func_76134_b(ageInTicks * 0.125F) * 1.0F;
      this.LArm.field_78808_h = -2.0F - MathHelper.func_76134_b(ageInTicks * 0.25F - 3.1415927F) * 0.25F + MathHelper.func_76134_b(ageInTicks * 0.125F) * 1.0F;
      this.RArm.field_78796_g = 0.0F;
      this.LArm.field_78796_g = 0.0F;
      this.RLeg.field_78795_f = MathHelper.func_76134_b(ageInTicks * 0.125F) * 0.5F - MathHelper.func_76134_b(ageInTicks * 0.125F) * 1.0F;
      this.LLeg.field_78795_f = MathHelper.func_76134_b(ageInTicks * 0.125F - 3.1415927F) * 0.5F - MathHelper.func_76134_b(ageInTicks * 0.125F - 3.1415927F) * 1.0F;
    } 
    this.Neck.field_78795_f += this.Head.field_78795_f;
    this.Neck.field_78796_g += this.Head.field_78796_g;
    this.Neck.field_78808_h += this.Head.field_78808_h;
    func_178685_a(this.RLeg, this.field_178721_j);
    func_178685_a(this.LLeg, this.field_178722_k);
    func_178685_a(this.RArm, this.field_178723_h);
    func_178685_a(this.LArm, this.field_178724_i);
    this.field_178721_j.field_78797_d -= 1.5F;
    this.field_178722_k.field_78797_d -= 1.5F;
    this.field_178723_h.field_78800_c--;
    this.field_178724_i.field_78800_c++;
    this.field_78116_c.field_78797_d -= 1.1F;
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
