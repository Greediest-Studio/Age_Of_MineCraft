package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGuardian;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class ModelCMMGuardian extends ModelBase implements ICappedModel {
  public ModelRenderer Chest;
  
  public ModelRenderer Abdoman;
  
  public ModelRenderer Neck;
  
  public ModelRenderer LArm;
  
  public ModelRenderer RArm;
  
  public ModelRenderer BustFront;
  
  public ModelRenderer Body2Child;
  
  public ModelRenderer Skirt1;
  
  public ModelRenderer RLeg;
  
  public ModelRenderer LLeg;
  
  public ModelRenderer Body2ChildChild;
  
  public ModelRenderer Body2ChildChildChild0;
  
  public ModelRenderer Body2ChildChildChild1;
  
  public ModelRenderer Skirt2;
  
  public ModelRenderer Head;
  
  public ModelRenderer Hair;
  
  public ModelRenderer HTop;
  
  public ModelRenderer REar;
  
  public ModelRenderer LEar;
  
  public ModelRenderer LHorn;
  
  public ModelRenderer RHorn;
  
  public ModelRenderer Eye;
  
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
  
  public ModelCMMGuardian() {
    this.bipedCape = new ModelRenderer(this, 0, 0);
    this.bipedCape.func_78787_b(64, 32);
    this.bipedCape.func_78790_a(-5.0F, 0.0F, -1.0F, 10, 16, 1, 0.0F);
    this.field_78090_t = 64;
    this.field_78089_u = 64;
    this.Body2ChildChild = new ModelRenderer(this, 18, 36);
    this.Body2ChildChild.func_78793_a(0.0F, 0.0F, 7.0F);
    this.Body2ChildChild.func_78790_a(-1.5F, -1.5F, 0.0F, 3, 3, 7, 0.0F);
    this.Body2ChildChildChild0 = new ModelRenderer(this, 0, 39);
    this.Body2ChildChildChild0.func_78793_a(0.0F, 0.0F, 6.0F);
    this.Body2ChildChildChild0.func_78790_a(-1.0F, -1.0F, 0.0F, 2, 2, 6, 0.0F);
    this.HTop = new ModelRenderer(this, 0, 28);
    this.HTop.func_78793_a(0.0F, 0.0F, 0.0F);
    this.HTop.func_78790_a(-3.5F, -8.0F, -3.0F, 7, 1, 6, 0.0F);
    this.Neck = new ModelRenderer(this, 52, 47);
    this.Neck.func_78793_a(0.0F, 0.0F, 0.0F);
    this.Neck.func_78790_a(-1.5F, -2.0F, -1.5F, 3, 2, 3, 0.0F);
    this.LHorn = new ModelRenderer(this, 0, 35);
    this.LHorn.func_78793_a(0.0F, 0.0F, 0.0F);
    this.LHorn.func_78790_a(3.0F, -7.5F, -1.0F, 2, 1, 1, 0.0F);
    this.Head = new ModelRenderer(this, 0, 0);
    this.Head.func_78793_a(0.0F, -1.0F, 0.0F);
    this.Head.func_78790_a(-3.5F, -7.0F, -3.0F, 7, 7, 6, 0.0F);
    this.RLeg = new ModelRenderer(this, 52, 0);
    this.RLeg.func_78793_a(-2.0F, 6.0F, 0.0F);
    this.RLeg.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 12, 3, 0.0F);
    this.RArm = new ModelRenderer(this, 56, 15);
    this.RArm.func_78793_a(-3.5F, 1.0F, 0.0F);
    this.RArm.func_78790_a(-2.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
    this.Body2ChildChildChild1 = new ModelRenderer(this, 28, 46);
    this.Body2ChildChildChild1.func_78793_a(0.0F, 0.0F, 6.0F);
    this.Body2ChildChildChild1.func_78790_a(0.0F, -4.5F, 0.0F, 1, 9, 9, 0.0F);
    this.Skirt2 = new ModelRenderer(this, 38, 37);
    this.Skirt2.func_78793_a(0.0F, 3.0F, 0.0F);
    this.Skirt2.func_78790_a(-4.0F, 0.0F, -2.5F, 8, 4, 5, 0.0F);
    this.RHorn = new ModelRenderer(this, 0, 37);
    this.RHorn.func_78793_a(0.0F, 0.0F, 0.0F);
    this.RHorn.func_78790_a(-5.0F, -7.5F, -1.0F, 2, 1, 1, 0.0F);
    this.Body2Child = new ModelRenderer(this, 32, 15);
    this.Body2Child.func_78793_a(0.0F, 4.0F, 1.0F);
    this.Body2Child.func_78790_a(-2.0F, -2.0F, 0.0F, 4, 4, 8, 0.0F);
    this.LLeg = new ModelRenderer(this, 40, 0);
    this.LLeg.func_78793_a(2.0F, 6.0F, 0.0F);
    this.LLeg.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 12, 3, 0.0F);
    this.Abdoman = new ModelRenderer(this, 0, 55);
    this.Abdoman.func_78793_a(0.0F, 5.0F, 0.0F);
    this.Abdoman.func_78790_a(-3.0F, 0.0F, -1.5F, 6, 6, 3, 0.0F);
    this.LArm = new ModelRenderer(this, 32, 0);
    this.LArm.func_78793_a(3.5F, 1.0F, 0.0F);
    this.LArm.func_78790_a(0.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
    this.LEar = new ModelRenderer(this, 18, 60);
    this.LEar.func_78793_a(0.0F, 0.0F, 0.0F);
    this.LEar.func_78790_a(3.5F, -4.0F, -1.5F, 2, 3, 1, 0.0F);
    this.REar = new ModelRenderer(this, 18, 56);
    this.REar.func_78793_a(0.0F, 0.0F, 0.0F);
    this.REar.func_78790_a(-5.5F, -4.0F, -1.5F, 2, 3, 1, 0.0F);
    this.Skirt1 = new ModelRenderer(this, 42, 29);
    this.Skirt1.func_78793_a(0.0F, 3.0F, 0.0F);
    this.Skirt1.func_78790_a(-3.5F, 0.0F, -2.0F, 7, 4, 4, 0.0F);
    this.Hair = new ModelRenderer(this, 0, 13);
    this.Hair.func_78793_a(0.0F, 0.0F, 0.0F);
    this.Hair.func_78790_a(-4.0F, -7.0F, -3.5F, 8, 8, 7, 0.0F);
    this.Eye = new ModelRenderer(this, 0, 0);
    this.Eye.func_78793_a(0.0F, -2.5F, -2.6F);
    this.Eye.func_78790_a(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
    this.Chest = new ModelRenderer(this, 0, 47);
    this.Chest.func_78793_a(0.0F, 1.0F, 0.0F);
    this.Chest.func_78790_a(-3.5F, 0.0F, -1.5F, 7, 5, 3, 0.0F);
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
    this.Body2Child.func_78792_a(this.Body2ChildChild);
    this.Body2ChildChild.func_78792_a(this.Body2ChildChildChild0);
    this.Head.func_78792_a(this.HTop);
    this.Chest.func_78792_a(this.Neck);
    this.Chest.func_78792_a(this.BustFront);
    this.Head.func_78792_a(this.LHorn);
    this.Neck.func_78792_a(this.Head);
    this.Abdoman.func_78792_a(this.RLeg);
    this.Chest.func_78792_a(this.RArm);
    this.Body2ChildChildChild0.func_78792_a(this.Body2ChildChildChild1);
    this.Skirt1.func_78792_a(this.Skirt2);
    this.Head.func_78792_a(this.RHorn);
    this.BustFront.func_78792_a(this.BustTop);
    this.Abdoman.func_78792_a(this.Body2Child);
    this.Abdoman.func_78792_a(this.LLeg);
    this.Chest.func_78792_a(this.Abdoman);
    this.Chest.func_78792_a(this.LArm);
    this.Head.func_78792_a(this.LEar);
    this.Head.func_78792_a(this.REar);
    this.Abdoman.func_78792_a(this.Skirt1);
    this.Head.func_78792_a(this.Hair);
    this.Head.func_78792_a(this.Eye);
    this.BustFront.func_78792_a(this.BustBottom);
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
    EntityGuardian entity = (EntityGuardian)entityIn;
    float f6 = ageInTicks - entity.field_70173_aa;
    this.Head.field_78796_g = netHeadYaw * 0.017453292F;
    this.Head.field_78795_f = headPitch * 0.017453292F;
    if (!entity.isElder())
      limbSwing *= 2.0F; 
    this.Eye.field_78798_e = -2.6F;
    Object object = Minecraft.func_71410_x().func_175606_aa();
    if (entity.isElder() && !this.field_78091_s) {
      this.BustFront.field_78807_k = false;
    } else {
      this.BustFront.field_78807_k = true;
    } 
    if (entity.hasTargetedEntity())
      object = entity.getTargetedEntity(); 
    if (object != null) {
      Vec3d vec3 = ((Entity)object).func_174824_e(0.0F);
      Vec3d vec31 = entityIn.func_174824_e(0.0F);
      double d0 = vec3.field_72448_b - vec31.field_72448_b;
      if (d0 <= 0.0D || !entity.getCurrentBook().func_190926_b()) {
        this.Eye.field_78797_d = -2.5F;
      } else {
        this.Eye.field_78797_d = -3.5F;
      } 
      Vec3d vec32 = entityIn.func_70676_i(0.0F);
      vec32 = new Vec3d(vec32.field_72450_a, 0.0D, vec32.field_72449_c);
      Vec3d vec33 = (new Vec3d(vec31.field_72450_a - vec3.field_72450_a, 0.0D, vec31.field_72449_c - vec3.field_72449_c)).func_72432_b().func_178785_b(1.5707964F);
      double d1 = vec32.func_72430_b(vec33);
      this.Eye.field_78800_c = !entity.getCurrentBook().func_190926_b() ? (MathHelper.func_76126_a(ageInTicks * 0.1F + MathHelper.func_76126_a(ageInTicks * 0.05F)) * 0.5F) : (0.05F + MathHelper.func_76129_c((float)Math.abs(d1)) * 0.5F * (float)Math.signum(d1));
    } 
    this.Eye.field_78806_j = true;
    float f8 = entity.func_175471_a(f6);
    this.Body2Child.field_78796_g = MathHelper.func_76126_a(f8) * 3.1415927F * 0.05F;
    this.Body2ChildChild.field_78796_g = MathHelper.func_76126_a(f8) * 3.1415927F * 0.1F;
    this.Body2ChildChildChild0.field_78796_g = MathHelper.func_76126_a(f8) * 3.1415927F * 0.15F;
    this.BustFront.func_78793_a(0.0F, 2.75F, -1.0F);
    this.RArm.func_78793_a(-3.5F, 2.0F, 0.0F);
    this.LArm.func_78793_a(3.5F, 2.0F, 0.0F);
    this.Chest.func_78793_a(0.0F, 1.0F, 0.0F);
    this.Chest.field_78795_f = 0.0F;
    this.Abdoman.field_78795_f = 0.0F;
    this.Chest.field_78808_h = 0.0F;
    this.Abdoman.field_78808_h = 0.0F;
    this.BustFront.field_78795_f = 0.0F;
    this.Skirt1.field_78795_f = 0.0F;
    this.Skirt2.field_78795_f = 0.0F;
    this.Neck.field_78808_h = 0.0F;
    this.Neck.field_78796_g = 0.0F;
    this.Neck.field_78795_f = 0.0F;
    this.Body2Child.field_78795_f = 0.0F;
    this.Body2ChildChild.field_78795_f = 0.0F;
    this.Body2ChildChildChild0.field_78795_f = 0.0F;
    if (!entity.func_70090_H()) {
      this.RArm.field_78808_h = 0.0F;
      this.LArm.field_78808_h = 0.0F;
      this.RArm.field_78808_h = 0.0F;
      this.LArm.field_78808_h = 0.0F;
      this.RArm.field_78796_g = 0.0F;
      this.LArm.field_78796_g = 0.0F;
      this.BustFront.field_78795_f = (float)(entity.field_70181_x / 2.0D);
      if (entity.isSitResting() || (entity.isElder() && !this.field_78091_s && entity.func_70089_S())) {
        this.RArm.field_78795_f += -0.31415927F;
        this.LArm.field_78795_f += -0.31415927F;
        this.RLeg.field_78795_f = -1.2F + MathHelper.func_76134_b(ageInTicks * 0.2F + 1.0F) * 0.1F;
        this.RLeg.field_78796_g = -0.3F;
        this.RLeg.field_78808_h = 0.025F;
        this.LLeg.field_78795_f = -1.6F + MathHelper.func_76134_b(ageInTicks * 0.2F + 1.0F) * 0.1F;
        this.LLeg.field_78796_g = 0.25F;
        this.LLeg.field_78808_h = -0.025F;
        this.RArm.field_78795_f = -0.25F;
        this.LArm.field_78795_f = -0.25F;
        this.Skirt1.field_78795_f = -0.5F + MathHelper.func_76134_b(ageInTicks * 0.2F + 1.0F) * 0.05F;
        this.Skirt2.field_78795_f = -0.7F + MathHelper.func_76134_b(ageInTicks * 0.2F + 1.0F) * 0.05F;
        this.BustFront.field_78795_f += MathHelper.func_76134_b(ageInTicks * 0.2F + 1.0F) * 0.05F;
      } else {
        this.RLeg.field_78796_g = 0.0F;
        this.LLeg.field_78796_g = 0.0F;
        this.RLeg.field_78808_h = 0.0F;
        this.LLeg.field_78808_h = 0.0F;
        this.RArm.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F + 3.1415927F) * 2.0F * limbSwingAmount * 0.5F;
        this.LArm.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
        this.RLeg.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
        this.LLeg.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount * 0.5F;
      } 
    } else {
      float f61 = MathHelper.func_76134_b(ageInTicks * 0.1F);
      this.Neck.field_78795_f -= (0.065F + 0.02F * f61) * 3.1415927F;
      this.Chest.field_78795_f = (0.065F + 0.02F * f61) * 3.1415927F;
      this.Abdoman.field_78795_f = (0.065F + 0.01F * f61) * 3.1415927F + limbSwingAmount;
      this.Body2Child.field_78795_f = -((0.065F + 0.02F * f61) * 3.1415927F) - limbSwingAmount;
      this.LLeg.field_78795_f = (0.05F + 0.01F * f61) * 3.1415927F;
      this.RLeg.field_78795_f = (0.05F + 0.01F * f61) * 3.1415927F;
      this.RLeg.field_78796_g = 0.0F;
      this.LLeg.field_78796_g = 0.0F;
      this.RLeg.field_78808_h = 0.0F;
      this.LLeg.field_78808_h = 0.0F;
      this.RArm.field_78795_f = (0.1F + 0.2F * f61) * 3.1415927F;
      this.LArm.field_78795_f = (0.1F + 0.2F * f61) * 3.1415927F;
      this.RArm.field_78796_g = (0.1F + -0.15F * f61) * 3.1415927F;
      this.LArm.field_78796_g = (-0.1F + 0.15F * f61) * 3.1415927F;
      this.LArm.field_78808_h = (-0.075F + 0.05F * f61) * 3.1415927F;
      this.RArm.field_78808_h = (0.075F + -0.05F * f61) * 3.1415927F;
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
    if (this.isSneak && !entity.isSitResting() && !entity.func_70090_H()) {
      this.Chest.field_78797_d += 5.0F;
      this.RArm.field_78797_d += 5.0F;
      this.LArm.field_78797_d += 5.0F;
      float f61 = MathHelper.func_76134_b(ageInTicks * 0.1F);
      this.Neck.field_78795_f -= (0.065F + 0.02F * f61) * 3.1415927F;
      this.Chest.field_78795_f = (0.065F + 0.02F * f61) * 3.1415927F;
      this.Abdoman.field_78795_f = 0.25F + (0.065F + 0.02F * f61) * 3.1415927F;
      this.LLeg.field_78795_f = (0.15F + 0.05F * f61) * 3.1415927F;
      this.RLeg.field_78795_f = (0.15F + 0.05F * f61) * 3.1415927F;
      this.LLeg.field_78808_h = (-0.05F + 0.05F * f61) * 3.1415927F;
      this.RLeg.field_78808_h = (0.05F + -0.05F * f61) * 3.1415927F;
      this.LLeg.field_78796_g = (-0.05F + 0.05F * f61) * 3.1415927F;
      this.RLeg.field_78796_g = (0.05F + -0.05F * f61) * 3.1415927F;
      this.RArm.field_78795_f = (0.1F + 0.2F * f61) * 3.1415927F;
      this.LArm.field_78795_f = (0.1F + 0.2F * f61) * 3.1415927F;
      this.RArm.field_78796_g = (0.1F + -0.15F * f61) * 3.1415927F;
      this.LArm.field_78796_g = (-0.1F + 0.15F * f61) * 3.1415927F;
      this.LArm.field_78808_h = (-0.075F + 0.05F * f61) * 3.1415927F;
      this.RArm.field_78808_h = (0.075F + -0.05F * f61) * 3.1415927F;
    } 
    if (entity.getTargetedEntity() != null)
      if (entity.func_184638_cS()) {
        if (!entity.isElder())
          this.RArm.field_78795_f = this.Neck.field_78795_f + this.Head.field_78795_f - 1.6F; 
        this.LArm.field_78795_f = this.Neck.field_78795_f + this.Head.field_78795_f - 1.6F;
      } else {
        if (!entity.isElder())
          this.LArm.field_78795_f = this.Neck.field_78795_f + this.Head.field_78795_f - 1.6F; 
        this.RArm.field_78795_f = this.Neck.field_78795_f + this.Head.field_78795_f - 1.6F;
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
      this.RArm.field_78798_e = MathHelper.func_76126_a(this.Abdoman.field_78796_g) * 5.0F;
      this.RArm.field_78800_c = -MathHelper.func_76134_b(this.Abdoman.field_78796_g) * 3.0F;
      this.LArm.field_78798_e = -MathHelper.func_76126_a(this.Abdoman.field_78796_g) * 5.0F;
      this.LArm.field_78800_c = MathHelper.func_76134_b(this.Abdoman.field_78796_g) * 3.0F;
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
      if (entity.isElder()) {
        this.Chest.func_78793_a(0.0F + MathHelper.func_76126_a(ageInTicks * 0.125F) * 1.0F, 0.0F - MathHelper.func_76134_b(ageInTicks * 0.125F) * 1.0F, 0.0F + MathHelper.func_76134_b(ageInTicks * 0.125F) * 1.0F);
        this.Neck.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.5F - 3.1415927F) * 0.5F;
        this.Head.field_78795_f += MathHelper.func_76134_b(ageInTicks * 1.0F - 3.0F) * 0.5F;
        this.Chest.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.25F) * 0.25F;
        this.Abdoman.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.25F - 1.0F) * 0.5F;
        this.RLeg.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.25F - 2.0F) * 0.5F;
        this.LLeg.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.25F - 2.0F) * 0.5F;
      } else {
        this.Neck.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.5F - 3.1415927F) * 0.5F;
        this.Head.field_78795_f += MathHelper.func_76134_b(ageInTicks * 1.0F - 3.0F) * 0.5F;
        this.Chest.field_78808_h = -(MathHelper.func_76134_b(ageInTicks * 0.5F) * 0.5F);
        this.Abdoman.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.5F) * 0.5F;
        this.RLeg.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.5F) * 0.5F;
        this.LLeg.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.5F) * 0.5F;
      } 
      this.RArm.field_78795_f = -1.0F + MathHelper.func_76134_b(ageInTicks * 0.5F - 2.0F) * 1.0F;
      this.LArm.field_78795_f = -1.0F + MathHelper.func_76134_b(ageInTicks * 0.5F - 2.0F) * 1.0F;
      this.RArm.field_78808_h = 2.0F;
      this.LArm.field_78808_h = -2.0F;
      this.RArm.field_78796_g = 0.0F;
      this.LArm.field_78796_g = 0.0F;
      this.Body2Child.field_78796_g = MathHelper.func_76126_a(ageInTicks * 0.5F - 2.0F) * 0.5F;
      this.Body2ChildChild.field_78796_g = MathHelper.func_76126_a(ageInTicks * 0.5F - 2.0F) * 0.5F;
      this.Body2ChildChildChild0.field_78796_g = MathHelper.func_76126_a(ageInTicks * 0.5F - 2.0F) * 0.5F;
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
