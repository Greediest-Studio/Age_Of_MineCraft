package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.model;

import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantSnowGolem;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelMutantSnowGolem extends ModelBase {
  private final ModelRenderer pelvis;
  
  private final ModelRenderer abdomen;
  
  private final ModelRenderer chest;
  
  private final JointModelRenderer head;
  
  private final ModelRenderer headCore;
  
  private final JointModelRenderer arm1;
  
  private final JointModelRenderer arm2;
  
  private final JointModelRenderer forearm1;
  
  private final JointModelRenderer forearm2;
  
  private final JointModelRenderer leg1;
  
  private final JointModelRenderer leg2;
  
  private final JointModelRenderer foreleg1;
  
  private final JointModelRenderer foreleg2;
  
  private float partialTick;
  
  public ModelMutantSnowGolem() {
    this.field_78090_t = 128;
    this.field_78089_u = 64;
    this.pelvis = new ModelRenderer(this);
    this.pelvis.func_78793_a(0.0F, 13.5F, 5.0F);
    this.abdomen = new ModelRenderer(this, 0, 32);
    this.abdomen.func_78789_a(-5.0F, -8.0F, -4.0F, 10, 8, 8);
    this.pelvis.func_78792_a(this.abdomen);
    this.chest = new ModelRenderer(this, 24, 36);
    this.chest.func_78789_a(-8.0F, -12.0F, -6.0F, 16, 12, 12);
    this.chest.func_78793_a(0.0F, -6.0F, 0.0F);
    this.head = new JointModelRenderer(this, 0, 0);
    this.head.func_78787_b(64, 32);
    this.head.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.5F);
    this.head.func_78793_a(0.0F, -12.0F, -2.0F);
    this.chest.func_78792_a(this.head);
    this.headCore = new ModelRenderer(this, 64, 0);
    this.headCore.func_78789_a(-4.0F, -8.0F, -4.0F, 8, 8, 8);
    this.headCore.func_78784_a(80, 46).func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, -0.5F);
    this.headCore.func_78793_a(0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.headCore);
    this.abdomen.func_78792_a(this.chest);
    this.arm1 = new JointModelRenderer(this, 68, 16);
    this.arm1.func_78789_a(-2.5F, 0.0F, -2.5F, 5, 10, 5);
    this.arm1.func_78793_a(-9.0F, -11.0F, 0.0F);
    this.chest.func_78792_a(this.arm1);
    this.forearm1 = new JointModelRenderer(this, 96, 0);
    this.forearm1.func_78789_a(-3.0F, 0.0F, -3.0F, 6, 12, 6);
    this.forearm1.func_78793_a(0.0F, 10.0F, 0.0F);
    this.arm1.func_78792_a(this.forearm1);
    this.arm2 = new JointModelRenderer(this, 68, 16);
    this.arm2.field_78809_i = true;
    this.arm2.func_78789_a(-2.5F, 0.0F, -2.5F, 5, 10, 5);
    this.arm2.func_78793_a(9.0F, -11.0F, 0.0F);
    this.chest.func_78792_a(this.arm2);
    this.forearm2 = new JointModelRenderer(this, 96, 0);
    this.forearm2.field_78809_i = true;
    this.forearm2.func_78789_a(-3.0F, 0.0F, -3.0F, 6, 12, 6);
    this.forearm2.func_78793_a(0.0F, 10.0F, 0.0F);
    this.arm2.func_78792_a(this.forearm2);
    this.leg1 = new JointModelRenderer(this, 88, 18);
    this.leg1.func_78789_a(-3.0F, 0.0F, -3.0F, 6, 8, 6);
    this.leg1.func_78793_a(-4.0F, -1.0F, -3.0F);
    this.pelvis.func_78792_a(this.leg1);
    this.foreleg1 = new JointModelRenderer(this, 88, 32);
    this.foreleg1.func_78789_a(-3.0F, 0.0F, -3.0F, 6, 8, 6);
    this.foreleg1.func_78793_a(-1.0F, 6.0F, -0.0F);
    this.leg1.func_78792_a(this.foreleg1);
    this.leg2 = new JointModelRenderer(this, 88, 18);
    this.leg2.field_78809_i = true;
    this.leg2.func_78789_a(-3.0F, 0.0F, -3.0F, 6, 8, 6);
    this.leg2.func_78793_a(4.0F, -1.0F, -3.0F);
    this.pelvis.func_78792_a(this.leg2);
    this.foreleg2 = new JointModelRenderer(this, 88, 32);
    this.foreleg2.field_78809_i = true;
    this.foreleg2.func_78789_a(-3.0F, 0.0F, -3.0F, 6, 8, 6);
    this.foreleg2.func_78793_a(1.0F, 6.0F, -0.0F);
    this.leg2.func_78792_a(this.foreleg2);
  }
  
  public void func_78088_a(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    setAngles();
    animate((EntityMutantSnowGolem)entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    this.pelvis.func_78785_a(scale);
  }
  
  private void setAngles() {
    this.pelvis.field_78797_d = 13.5F;
    this.abdomen.field_78795_f = 0.1308997F;
    this.chest.field_78795_f = 0.1308997F;
    this.chest.field_78796_g = 0.0F;
    this.head.field_78795_f = -0.2617994F;
    (this.head.getModel()).field_78795_f = 0.0F;
    (this.head.getModel()).field_78796_g = 0.0F;
    this.arm1.field_78795_f = -0.31415927F;
    this.arm1.field_78808_h = 0.0F;
    (this.arm1.getModel()).field_78795_f = 0.0F;
    (this.arm1.getModel()).field_78796_g = 0.5235988F;
    (this.arm1.getModel()).field_78808_h = 0.5235988F;
    this.forearm1.field_78796_g = -0.5235988F;
    this.forearm1.field_78808_h = -0.2617994F;
    (this.forearm1.getModel()).field_78795_f = -0.5235988F;
    this.arm2.field_78795_f = -0.31415927F;
    this.arm2.field_78808_h = 0.0F;
    (this.arm2.getModel()).field_78795_f = 0.0F;
    (this.arm2.getModel()).field_78796_g = -0.5235988F;
    (this.arm2.getModel()).field_78808_h = -0.5235988F;
    this.forearm2.field_78796_g = 0.5235988F;
    this.forearm2.field_78808_h = 0.2617994F;
    (this.forearm2.getModel()).field_78795_f = -0.5235988F;
    this.leg1.field_78795_f = -0.62831855F;
    (this.leg1.getModel()).field_78808_h = 0.5235988F;
    this.foreleg1.field_78808_h = -0.5235988F;
    (this.foreleg1.getModel()).field_78795_f = 0.69813174F;
    this.leg2.field_78795_f = -0.62831855F;
    (this.leg2.getModel()).field_78808_h = -0.5235988F;
    this.foreleg2.field_78808_h = 0.5235988F;
    (this.foreleg2.getModel()).field_78795_f = 0.69813174F;
  }
  
  private void animate(EntityMutantSnowGolem golem, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    float temp = 0.5F;
    float walkAnim = MathHelper.func_76126_a(limbSwing * 0.45F) * limbSwingAmount;
    float walkAnim1 = (MathHelper.func_76134_b((limbSwing - temp) * 0.45F) + temp) * limbSwingAmount;
    float walkAnim2 = (MathHelper.func_76134_b((limbSwing - temp + 6.2831855F) * 0.45F) + temp) * limbSwingAmount;
    float breatheAnim = MathHelper.func_76126_a(ageInTicks * 0.11F);
    float faceYaw = netHeadYaw * 3.1415927F / 180.0F;
    float facePitch = headPitch * 3.1415927F / 180.0F;
    if (golem.isThrowing()) {
      animateThrow(golem.getThrowingTick());
      float scale1 = 1.0F - MathHelper.func_76131_a(golem.getThrowingTick() / 4.0F, 0.0F, 1.0F);
      walkAnim *= scale1;
    } 
    (this.head.getModel()).field_78795_f -= breatheAnim * 0.01F;
    this.chest.field_78795_f -= breatheAnim * 0.01F;
    this.arm1.field_78808_h += breatheAnim * 0.03F;
    this.arm2.field_78808_h -= breatheAnim * 0.03F;
    (this.head.getModel()).field_78795_f += facePitch;
    (this.head.getModel()).field_78796_g += faceYaw;
    this.pelvis.field_78797_d += Math.abs(walkAnim) * 1.5F;
    this.abdomen.field_78795_f += limbSwingAmount * 0.2F;
    this.chest.field_78796_g -= walkAnim * 0.1F;
    this.head.field_78795_f -= limbSwingAmount * 0.2F;
    this.arm1.field_78795_f -= walkAnim * 0.6F;
    this.arm2.field_78795_f += walkAnim * 0.6F;
    (this.forearm1.getModel()).field_78795_f -= walkAnim * 0.2F;
    (this.forearm2.getModel()).field_78795_f += walkAnim * 0.2F;
    this.leg1.field_78795_f += walkAnim1 * 1.1F;
    this.leg2.field_78795_f += walkAnim2 * 1.1F;
    (this.foreleg1.getModel()).field_78795_f += walkAnim * 0.2F;
    (this.foreleg2.getModel()).field_78795_f -= walkAnim * 0.2F;
  }
  
  private void animateThrow(int fullTick) {
    if (fullTick < 7) {
      float tick = (fullTick + this.partialTick) / 7.0F;
      float f = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F);
      this.abdomen.field_78795_f += -f * 0.2F;
      this.chest.field_78795_f += -f * 0.4F;
      this.arm1.field_78795_f += -f * 1.6F;
      this.arm1.field_78808_h += f * 0.8F;
      this.arm2.field_78795_f += -f * 1.6F;
      this.arm2.field_78808_h += -f * 0.8F;
    } else if (fullTick < 10) {
      float tick = ((fullTick - 7) + this.partialTick) / 3.0F;
      float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      this.abdomen.field_78795_f += -f * 0.4F + 0.2F;
      this.chest.field_78795_f += -f * 0.6F + 0.2F;
      this.arm1.field_78795_f += -f * 0.8F - 0.8F;
      this.arm1.field_78808_h += 0.8F;
      this.arm2.field_78795_f += -f * 0.8F - 0.8F;
      this.arm2.field_78808_h += -0.8F;
    } else if (fullTick < 14) {
      this.abdomen.field_78795_f += 0.2F;
      this.chest.field_78795_f += 0.2F;
      this.arm1.field_78795_f += -0.8F;
      this.arm1.field_78808_h += 0.8F;
      this.arm2.field_78795_f += -0.8F;
      this.arm2.field_78808_h += -0.8F;
    } else if (fullTick < 20) {
      float tick = ((fullTick - 14) + this.partialTick) / 6.0F;
      float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      this.abdomen.field_78795_f += f * 0.2F;
      this.chest.field_78795_f += f * 0.2F;
      this.arm1.field_78795_f += -f * 0.8F;
      this.arm1.field_78808_h += f * 0.8F;
      this.arm2.field_78795_f += -f * 0.8F;
      this.arm2.field_78808_h += -f * 0.8F;
    } 
  }
  
  public void postRenderArm(float scale) {
    this.pelvis.func_78794_c(scale);
    this.abdomen.func_78794_c(scale);
    this.chest.func_78794_c(scale);
    this.arm1.func_78794_c(scale);
    this.arm1.getModel().func_78794_c(scale);
    this.forearm1.func_78794_c(scale);
    this.forearm1.getModel().func_78794_c(scale);
  }
  
  public void func_78086_a(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
    this.partialTick = partialTickTime;
  }
}
