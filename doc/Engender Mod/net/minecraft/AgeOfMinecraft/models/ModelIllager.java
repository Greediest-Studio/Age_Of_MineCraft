package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityAbstractIllagers;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityIllusioner;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelIllager extends ModelBase {
  public ModelRenderer head;
  
  public ModelRenderer hat;
  
  public ModelRenderer body;
  
  public ModelRenderer arms;
  
  public ModelRenderer leg0;
  
  public ModelRenderer leg1;
  
  public ModelRenderer nose;
  
  public ModelRenderer rightArm;
  
  public ModelRenderer leftArm;
  
  public ModelIllager(float scaleFactor, float p_i47227_2_, int textureWidthIn, int textureHeightIn) {
    this.head = (new ModelRenderer(this)).func_78787_b(textureWidthIn, textureHeightIn);
    this.head.func_78793_a(0.0F, 0.0F + p_i47227_2_, 0.0F);
    this.head.func_78784_a(0, 0).func_78790_a(-4.0F, -10.0F, -4.0F, 8, 10, 8, scaleFactor);
    this.hat = (new ModelRenderer(this, 32, 0)).func_78787_b(textureWidthIn, textureHeightIn);
    this.hat.func_78790_a(-4.0F, -10.0F, -4.0F, 8, 12, 8, scaleFactor + 0.45F);
    this.head.func_78792_a(this.hat);
    this.nose = (new ModelRenderer(this)).func_78787_b(textureWidthIn, textureHeightIn);
    this.nose.func_78793_a(0.0F, p_i47227_2_ - 2.0F, 0.0F);
    this.nose.func_78784_a(24, 0).func_78790_a(-1.0F, -1.0F, -6.0F, 2, 4, 2, scaleFactor);
    this.head.func_78792_a(this.nose);
    this.body = (new ModelRenderer(this)).func_78787_b(textureWidthIn, textureHeightIn);
    this.body.func_78793_a(0.0F, 0.0F + p_i47227_2_, 0.0F);
    this.body.func_78784_a(16, 20).func_78790_a(-4.0F, 0.0F, -3.0F, 8, 12, 6, scaleFactor);
    this.body.func_78784_a(0, 38).func_78790_a(-4.0F, 0.0F, -3.0F, 8, 18, 6, scaleFactor + 0.5F);
    this.arms = (new ModelRenderer(this)).func_78787_b(textureWidthIn, textureHeightIn);
    this.arms.func_78793_a(0.0F, 0.0F + p_i47227_2_ + 2.0F, 0.0F);
    this.arms.func_78784_a(44, 22).func_78790_a(-8.0F, -2.0F, -2.0F, 4, 8, 4, scaleFactor);
    ModelRenderer modelrenderer = (new ModelRenderer(this, 44, 22)).func_78787_b(textureWidthIn, textureHeightIn);
    modelrenderer.field_78809_i = true;
    modelrenderer.func_78790_a(4.0F, -2.0F, -2.0F, 4, 8, 4, scaleFactor);
    this.arms.func_78792_a(modelrenderer);
    this.arms.func_78784_a(40, 38).func_78790_a(-4.0F, 2.0F, -2.0F, 8, 4, 4, scaleFactor);
    this.leg0 = (new ModelRenderer(this, 0, 22)).func_78787_b(textureWidthIn, textureHeightIn);
    this.leg0.func_78793_a(-2.0F, 12.0F + p_i47227_2_, 0.0F);
    this.leg0.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, scaleFactor);
    this.leg1 = (new ModelRenderer(this, 0, 22)).func_78787_b(textureWidthIn, textureHeightIn);
    this.leg1.field_78809_i = true;
    this.leg1.func_78793_a(2.0F, 12.0F + p_i47227_2_, 0.0F);
    this.leg1.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, scaleFactor);
    this.rightArm = (new ModelRenderer(this, 40, 46)).func_78787_b(textureWidthIn, textureHeightIn);
    this.rightArm.func_78790_a(-3.0F, -2.0F, -2.0F, 4, 12, 4, scaleFactor);
    this.rightArm.func_78793_a(-5.0F, 2.0F + p_i47227_2_, 0.0F);
    this.leftArm = (new ModelRenderer(this, 40, 46)).func_78787_b(textureWidthIn, textureHeightIn);
    this.leftArm.field_78809_i = true;
    this.leftArm.func_78790_a(-1.0F, -2.0F, -2.0F, 4, 12, 4, scaleFactor);
    this.leftArm.func_78793_a(5.0F, 2.0F + p_i47227_2_, 0.0F);
  }
  
  public void func_78088_a(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    func_78087_a(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
    this.head.func_78785_a(scale);
    this.body.func_78785_a(scale);
    this.leg0.func_78785_a(scale);
    this.leg1.func_78785_a(scale);
    EntityAbstractIllagers abstractillager = (EntityAbstractIllagers)entityIn;
    if (abstractillager.getArmPose() != EntityAbstractIllagers.IllagerArmPose.CROSSED || !abstractillager.getCurrentBook().func_190926_b()) {
      this.rightArm.func_78785_a(scale);
      this.leftArm.func_78785_a(scale);
    } else {
      this.arms.func_78785_a(scale);
    } 
  }
  
  public void func_78087_a(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    this.hat.field_78806_j = ((EntityAbstractIllagers)entityIn instanceof EntityIllusioner && ((EntityIllusioner)entityIn).getDisguiseID() == 0);
    this.head.field_78796_g = netHeadYaw * 0.017453292F;
    this.head.field_78795_f = headPitch * 0.017453292F;
    this.arms.field_78797_d = 3.0F;
    this.arms.field_78798_e = -1.0F;
    this.arms.field_78795_f = -0.75F;
    this.leg0.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
    this.leg1.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount * 0.5F;
    this.leg0.field_78796_g = 0.0F;
    this.leg1.field_78796_g = 0.0F;
    EntityAbstractIllagers.IllagerArmPose abstractillager$illagerarmpose = ((EntityAbstractIllagers)entityIn).getArmPose();
    if (!((EntityAbstractIllagers)entityIn).getCurrentBook().func_190926_b()) {
      this.rightArm.field_78796_g = ((EntityAbstractIllagers)entityIn).bookSpread - 1.0F;
      this.leftArm.field_78796_g = -((EntityAbstractIllagers)entityIn).bookSpread + 1.0F;
      this.rightArm.field_78808_h = 0.0F;
      this.leftArm.field_78808_h = 0.0F;
      this.rightArm.field_78795_f = -1.5F + 0.1F + MathHelper.func_76126_a(((EntityAbstractIllagers)entityIn).field_70173_aa * 0.1F) * 0.01F;
      this.leftArm.field_78795_f = -1.5F + 0.1F + MathHelper.func_76126_a(((EntityAbstractIllagers)entityIn).field_70173_aa * 0.1F) * 0.01F;
    } else if (abstractillager$illagerarmpose == EntityAbstractIllagers.IllagerArmPose.ATTACKING) {
      float f = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F);
      float f1 = MathHelper.func_76126_a((1.0F - (1.0F - this.field_78095_p) * (1.0F - this.field_78095_p)) * 3.1415927F);
      this.rightArm.field_78808_h = 0.0F;
      this.leftArm.field_78808_h = 0.0F;
      this.rightArm.field_78796_g = 0.15707964F;
      this.leftArm.field_78796_g = -0.15707964F;
      if (((EntityAbstractIllagers)entityIn).func_184591_cq() == EnumHandSide.RIGHT) {
        this.rightArm.field_78795_f = -1.8849558F + MathHelper.func_76134_b(ageInTicks * 0.09F) * 0.15F;
        this.leftArm.field_78795_f = -0.0F + MathHelper.func_76134_b(ageInTicks * 0.19F) * 0.5F;
        this.rightArm.field_78795_f += f * 2.2F - f1 * 0.4F;
        this.leftArm.field_78795_f += f * 1.2F - f1 * 0.4F;
      } else {
        this.rightArm.field_78795_f = -0.0F + MathHelper.func_76134_b(ageInTicks * 0.19F) * 0.5F;
        this.leftArm.field_78795_f = -1.8849558F + MathHelper.func_76134_b(ageInTicks * 0.09F) * 0.15F;
        this.rightArm.field_78795_f += f * 1.2F - f1 * 0.4F;
        this.leftArm.field_78795_f += f * 2.2F - f1 * 0.4F;
      } 
      this.rightArm.field_78808_h += MathHelper.func_76134_b(ageInTicks * 0.09F) * 0.05F + 0.05F;
      this.leftArm.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.09F) * 0.05F + 0.05F;
      this.rightArm.field_78795_f += MathHelper.func_76126_a(ageInTicks * 0.067F) * 0.05F;
      this.leftArm.field_78795_f -= MathHelper.func_76126_a(ageInTicks * 0.067F) * 0.05F;
    } else if (abstractillager$illagerarmpose == EntityAbstractIllagers.IllagerArmPose.SPELLCASTING) {
      this.rightArm.field_78798_e = 0.0F;
      this.rightArm.field_78800_c = -5.0F;
      this.leftArm.field_78798_e = 0.0F;
      this.leftArm.field_78800_c = 5.0F;
      float f = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F);
      float f1 = MathHelper.func_76126_a((1.0F - (1.0F - this.field_78095_p) * (1.0F - this.field_78095_p)) * 3.1415927F);
      this.rightArm.field_78795_f = MathHelper.func_76134_b(ageInTicks * 0.6662F) * 0.25F;
      this.leftArm.field_78795_f = MathHelper.func_76134_b(ageInTicks * 0.6662F) * 0.25F;
      this.rightArm.field_78795_f -= f * 2.5F - f1 * 0.5F;
      this.leftArm.field_78795_f -= f * 2.5F - f1 * 0.5F;
      this.rightArm.field_78808_h = 2.3561945F;
      this.leftArm.field_78808_h = -2.3561945F;
      this.rightArm.field_78796_g = 0.0F;
      this.leftArm.field_78796_g = 0.0F;
    } else if (abstractillager$illagerarmpose == EntityAbstractIllagers.IllagerArmPose.BOW_AND_ARROW) {
      this.rightArm.field_78796_g = -0.1F + this.head.field_78796_g;
      this.rightArm.field_78795_f = -1.5707964F + this.head.field_78795_f;
      this.leftArm.field_78795_f = -0.9424779F + this.head.field_78795_f;
      this.head.field_78796_g -= 0.4F;
      this.leftArm.field_78808_h = 1.5707964F;
    } 
  }
  
  public ModelRenderer getArm(EnumHandSide p_191216_1_) {
    return (p_191216_1_ == EnumHandSide.LEFT) ? this.leftArm : this.rightArm;
  }
}
