package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.model;

import chumbanotz.mutantbeasts.client.animationapi.Animator;
import chumbanotz.mutantbeasts.client.animationapi.IAnimatedEntity;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantSkeleton;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelMutantSkeleton extends ModelBase {
  private final ModelRenderer skeleBase;
  
  private final ModelRenderer pelvis;
  
  private final ModelRenderer waist;
  
  private final Spine[] spine;
  
  private final ModelRenderer neck;
  
  private final JointModelRenderer head;
  
  private final ModelRenderer jaw;
  
  private final ModelRenderer shoulder1;
  
  private final ModelRenderer shoulder2;
  
  private final JointModelRenderer arm1;
  
  private final JointModelRenderer arm2;
  
  private final JointModelRenderer forearm1;
  
  private final JointModelRenderer forearm2;
  
  private final JointModelRenderer leg1;
  
  private final JointModelRenderer leg2;
  
  private final JointModelRenderer foreleg1;
  
  private final JointModelRenderer foreleg2;
  
  private final MutantCrossbowModel bow;
  
  private final Animator animator;
  
  private float partialTick;
  
  public ModelMutantSkeleton() {
    this.field_78090_t = 128;
    this.field_78089_u = 128;
    this.skeleBase = new ModelRenderer(this);
    this.skeleBase.func_78793_a(0.0F, 3.0F, 0.0F);
    this.pelvis = new ModelRenderer(this, 0, 16);
    this.pelvis.func_78789_a(-4.0F, -6.0F, -3.0F, 8, 6, 6);
    this.skeleBase.func_78792_a(this.pelvis);
    this.waist = new ModelRenderer(this, 32, 0);
    this.waist.func_78789_a(-2.5F, -8.0F, -2.0F, 5, 8, 4);
    this.waist.func_78793_a(0.0F, -5.0F, 0.0F);
    this.pelvis.func_78792_a(this.waist);
    this.spine = new Spine[3];
    this.spine[0] = new Spine(this);
    (this.spine[0]).middle.func_78793_a(0.0F, -7.0F, 0.0F);
    this.waist.func_78792_a((this.spine[0]).middle);
    for (int i = 1; i < this.spine.length; i++) {
      this.spine[i] = new Spine(this);
      (this.spine[i]).middle.func_78793_a(0.0F, -5.0F, 0.0F);
      (this.spine[i - 1]).middle.func_78792_a((this.spine[i]).middle);
    } 
    this.neck = new ModelRenderer(this, 64, 0);
    this.neck.func_78789_a(-1.5F, -4.0F, -1.5F, 3, 4, 3);
    this.neck.func_78793_a(0.0F, -4.0F, 0.0F);
    (this.spine[2]).middle.func_78792_a(this.neck);
    this.head = new JointModelRenderer(this, 0, 0);
    this.head.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.4F);
    this.head.func_78793_a(0.0F, -4.0F, -1.0F);
    this.neck.func_78792_a(this.head);
    this.jaw = new ModelRenderer(this, 72, 0);
    this.jaw.func_78790_a(-4.0F, -3.0F, -8.0F, 8, 3, 8, 0.7F);
    this.jaw.func_78793_a(0.0F, -0.2F, 3.5F);
    this.head.func_78792_a(this.jaw);
    this.shoulder1 = new ModelRenderer(this, 28, 16);
    this.shoulder1.func_78789_a(-4.0F, -3.0F, -3.0F, 8, 3, 6);
    this.shoulder1.func_78793_a(-7.0F, -3.0F, -1.0F);
    (this.spine[2]).middle.func_78792_a(this.shoulder1);
    this.shoulder2 = new ModelRenderer(this, 28, 16);
    this.shoulder2.field_78809_i = true;
    this.shoulder2.func_78789_a(-4.0F, -3.0F, -3.0F, 8, 3, 6);
    this.shoulder2.func_78793_a(7.0F, -3.0F, -1.0F);
    (this.spine[2]).middle.func_78792_a(this.shoulder2);
    this.arm1 = new JointModelRenderer(this, 0, 28);
    this.arm1.func_78789_a(-2.0F, 0.0F, -2.0F, 4, 12, 4);
    this.arm1.func_78793_a(-1.0F, -1.0F, 0.0F);
    this.shoulder1.func_78792_a(this.arm1);
    this.arm2 = new JointModelRenderer(this, 0, 28);
    this.arm2.field_78809_i = true;
    this.arm2.func_78789_a(-2.0F, 0.0F, -2.0F, 4, 12, 4);
    this.arm2.func_78793_a(1.0F, -1.0F, 0.0F);
    this.shoulder2.func_78792_a(this.arm2);
    this.forearm1 = new JointModelRenderer(this, 16, 28);
    this.forearm1.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 14, 4, -0.01F);
    this.forearm1.func_78793_a(0.0F, 11.0F, 0.0F);
    this.arm1.func_78792_a(this.forearm1);
    this.forearm2 = new JointModelRenderer(this, 16, 28);
    this.forearm2.field_78809_i = true;
    this.forearm2.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 14, 4, -0.01F);
    this.forearm2.func_78793_a(0.0F, 11.0F, 0.0F);
    this.arm2.func_78792_a(this.forearm2);
    this.leg1 = new JointModelRenderer(this, 0, 28);
    this.leg1.func_78789_a(-2.0F, 0.0F, -2.0F, 4, 12, 4);
    this.leg1.func_78793_a(-2.5F, -2.5F, 0.0F);
    this.pelvis.func_78792_a(this.leg1);
    this.leg2 = new JointModelRenderer(this, 0, 28);
    this.leg2.field_78809_i = true;
    this.leg2.func_78789_a(-2.0F, 0.0F, -2.0F, 4, 12, 4);
    this.leg2.func_78793_a(2.5F, -2.5F, 0.0F);
    this.pelvis.func_78792_a(this.leg2);
    this.foreleg1 = new JointModelRenderer(this, 32, 28);
    this.foreleg1.func_78789_a(-2.0F, 0.0F, -2.0F, 4, 12, 4);
    this.foreleg1.func_78793_a(0.0F, 12.0F, 0.0F);
    this.leg1.func_78792_a(this.foreleg1);
    this.foreleg2 = new JointModelRenderer(this, 32, 28);
    this.foreleg2.field_78809_i = true;
    this.foreleg2.func_78789_a(-2.0F, 0.0F, -2.0F, 4, 12, 4);
    this.foreleg2.func_78793_a(0.0F, 12.0F, 0.0F);
    this.leg2.func_78792_a(this.foreleg2);
    this.bow = new MutantCrossbowModel(this);
    this.bow.armwear.func_78793_a(0.0F, 8.0F, 0.0F);
    this.forearm1.func_78792_a(this.bow.armwear);
    this.animator = new Animator(this);
  }
  
  public void func_78088_a(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    this.animator.update((IAnimatedEntity)entityIn, this.partialTick);
    setAngles();
    animate((EntityMutantSkeleton)entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    this.skeleBase.func_78785_a(scale);
  }
  
  public void setAngles() {
    this.skeleBase.field_78797_d = 3.0F;
    this.pelvis.field_78795_f = -0.31415927F;
    this.waist.field_78795_f = 0.22439948F;
    for (int i = 0; i < this.spine.length; i++)
      this.spine[i].setAngles(3.1415927F, (i == 1)); 
    this.neck.field_78795_f = -0.1308997F;
    this.head.field_78795_f = -0.1308997F;
    this.jaw.field_78795_f = 0.09817477F;
    this.shoulder1.field_78795_f = -0.7853982F;
    this.shoulder2.field_78795_f = -0.7853982F;
    (this.arm1.getModel()).field_78795_f = 0.5235988F;
    (this.arm1.getModel()).field_78808_h = 0.31415927F;
    (this.arm2.getModel()).field_78795_f = 0.5235988F;
    (this.arm2.getModel()).field_78808_h = -0.31415927F;
    (this.forearm1.getModel()).field_78795_f = -0.5235988F;
    (this.forearm2.getModel()).field_78795_f = -0.5235988F;
    this.leg1.field_78795_f = -0.2617994F - this.pelvis.field_78795_f;
    this.leg1.field_78808_h = 0.19634955F;
    this.leg2.field_78795_f = -0.2617994F - this.pelvis.field_78795_f;
    this.leg2.field_78808_h = -0.19634955F;
    this.foreleg1.field_78808_h = -0.1308997F;
    (this.foreleg1.getModel()).field_78795_f = 0.31415927F;
    this.foreleg2.field_78808_h = 0.1308997F;
    (this.foreleg2.getModel()).field_78795_f = 0.31415927F;
    this.bow.setAngles(3.1415927F);
  }
  
  public void animate(EntityMutantSkeleton skele, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float f5) {
    float walkAnim1 = MathHelper.func_76126_a(limbSwing * 0.5F);
    float walkAnim2 = MathHelper.func_76126_a(limbSwing * 0.5F - 1.1F);
    float breatheAnim = MathHelper.func_76126_a(ageInTicks * 0.1F);
    float faceYaw = netHeadYaw * 3.1415927F / 180.0F;
    float facePitch = headPitch * 3.1415927F / 180.0F;
    if (skele.getAnimationID() == 4) {
      animateMelee(skele.getAnimationTick());
      this.bow.rotateRope();
      float scale = 1.0F - MathHelper.func_76131_a(skele.getAnimationTick() / 4.0F, 0.0F, 1.0F);
      walkAnim1 *= scale;
      walkAnim2 *= scale;
    } else if (skele.getAnimationID() == 5) {
      animateShoot(skele.getAnimationTick(), facePitch, faceYaw);
      float scale = 1.0F - MathHelper.func_76131_a(skele.getAnimationTick() / 4.0F, 0.0F, 1.0F);
      walkAnim1 *= scale;
      walkAnim2 *= scale;
      facePitch *= scale;
      faceYaw *= scale;
    } else if (skele.getAnimationID() == 6) {
      animateMultiShoot(skele.getAnimationTick(), facePitch, faceYaw);
      float scale = 1.0F - MathHelper.func_76131_a(skele.getAnimationTick() / 4.0F, 0.0F, 1.0F);
      walkAnim1 *= scale;
      walkAnim2 *= scale;
      facePitch *= scale;
      faceYaw *= scale;
    } else if (this.animator.setAnimation(7)) {
      animateConstrict();
      this.bow.rotateRope();
      float scale = 1.0F - MathHelper.func_76131_a(skele.getAnimationTick() / 6.0F, 0.0F, 1.0F);
      facePitch *= scale;
      faceYaw *= scale;
    } else if (skele.getAnimationID() == 8) {
      animateSnipe(skele.getAnimationTick(), facePitch, faceYaw);
      float scale = 1.0F - MathHelper.func_76131_a(skele.getAnimationTick() / 4.0F, 0.0F, 1.0F);
      walkAnim1 *= scale;
      walkAnim2 *= scale;
      facePitch *= scale;
      faceYaw *= scale;
    } else {
      this.bow.rotateRope();
    } 
    this.skeleBase.field_78797_d -= (-0.5F + Math.abs(walkAnim1)) * limbSwingAmount;
    (this.spine[0]).middle.field_78796_g -= walkAnim1 * 0.06F * limbSwingAmount;
    this.arm1.field_78795_f -= walkAnim1 * 0.9F * limbSwingAmount;
    this.arm2.field_78795_f += walkAnim1 * 0.9F * limbSwingAmount;
    this.leg1.field_78795_f += (0.2F + walkAnim1) * 1.0F * limbSwingAmount;
    this.leg2.field_78795_f -= (-0.2F + walkAnim1) * 1.0F * limbSwingAmount;
    (this.foreleg1.getModel()).field_78795_f += (0.6F + walkAnim2) * 0.6F * limbSwingAmount;
    (this.foreleg2.getModel()).field_78795_f -= (-0.6F + walkAnim2) * 0.6F * limbSwingAmount;
    for (int i = 0; i < this.spine.length; i++)
      this.spine[i].animate(breatheAnim); 
    this.head.field_78795_f -= breatheAnim * 0.02F;
    this.jaw.field_78795_f += breatheAnim * 0.04F + 0.04F;
    this.arm1.field_78808_h += breatheAnim * 0.025F;
    this.arm2.field_78808_h -= breatheAnim * 0.025F;
    (this.head.getModel()).field_78795_f += facePitch;
    (this.head.getModel()).field_78796_g += faceYaw;
  }
  
  protected void animateMelee(int fullTick) {
    if (fullTick < 3) {
      float tick = (fullTick + this.partialTick) / 3.0F;
      float f = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F);
      for (int i = 0; i < this.spine.length; i++)
        (this.spine[i]).middle.field_78796_g += f * 3.1415927F / 16.0F; 
      this.arm1.field_78796_g += f * 3.1415927F / 10.0F;
      this.arm1.field_78808_h += f * 3.1415927F / 4.0F;
      this.arm2.field_78808_h += f * -3.1415927F / 16.0F;
    } else if (fullTick < 5) {
      float tick = ((fullTick - 3) + this.partialTick) / 2.0F;
      float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      for (int i = 0; i < this.spine.length; i++)
        (this.spine[i]).middle.field_78796_g += f * 0.5890486F - 0.3926991F; 
      this.arm1.field_78796_g += f * 2.7307692F - 2.41661F;
      this.arm1.field_78808_h += f * 1.1780972F - 0.3926991F;
      this.arm2.field_78808_h += -0.19634955F;
    } else if (fullTick < 8) {
      for (int i = 0; i < this.spine.length; i++)
        (this.spine[i]).middle.field_78796_g += -0.3926991F; 
      this.arm1.field_78796_g += -2.41661F;
      this.arm1.field_78808_h += -0.3926991F;
      this.arm2.field_78808_h += -0.19634955F;
    } else if (fullTick < 14) {
      float tick = ((fullTick - 8) + this.partialTick) / 6.0F;
      float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      for (int i = 0; i < this.spine.length; i++)
        (this.spine[i]).middle.field_78796_g += f * -3.1415927F / 8.0F; 
      this.arm1.field_78796_g += f * -3.1415927F / 1.3F;
      this.arm1.field_78808_h += f * -3.1415927F / 8.0F;
      this.arm2.field_78808_h += f * -3.1415927F / 16.0F;
    } 
  }
  
  protected void animateShoot(int fullTick, float facePitch, float faceYaw) {
    if (fullTick < 5) {
      float tick = (fullTick + this.partialTick) / 5.0F;
      float f = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F);
      (this.arm1.getModel()).field_78795_f += -f * 3.1415927F / 4.0F;
      this.arm1.field_78796_g += -f * 3.1415927F / 2.0F;
      this.arm1.field_78808_h += f * 3.1415927F / 16.0F;
      this.forearm1.field_78795_f += f * 3.1415927F / 7.0F;
      (this.arm2.getModel()).field_78795_f += -f * 3.1415927F / 4.0F;
      this.arm2.field_78796_g += f * 3.1415927F / 2.0F;
      this.arm2.field_78808_h += -f * 3.1415927F / 16.0F;
      (this.arm2.getModel()).field_78808_h += -f * 3.1415927F / 8.0F;
      this.forearm2.field_78795_f += -f * 3.1415927F / 6.0F;
      this.bow.rotateRope();
    } else if (fullTick < 12) {
      float tick = ((fullTick - 5) + this.partialTick) / 7.0F;
      float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      float f1 = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F);
      float f1s = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F * 0.4F);
      (this.head.getModel()).field_78796_g += f1 * 3.1415927F / 4.0F;
      for (int i = 0; i < this.spine.length; i++) {
        (this.spine[i]).middle.field_78796_g += -f1 * 3.1415927F / 12.0F;
        (this.spine[i]).middle.field_78795_f += f1 * facePitch / 3.0F;
        (this.spine[i]).middle.field_78796_g += f1 * faceYaw / 3.0F;
      } 
      (this.arm1.getModel()).field_78795_f += f * 0.2617994F - 1.0471976F;
      this.arm1.field_78796_g += f * -0.9424778F - 0.62831855F;
      this.arm1.field_78808_h += f * -0.850848F + 1.0471976F;
      this.forearm1.field_78795_f += 0.44879895F;
      (this.arm2.getModel()).field_78795_f += f * 1.8325956F - 2.6179938F;
      this.arm2.field_78796_g += f * 0.9424778F + 0.62831855F;
      this.arm2.field_78808_h += f * 0.850848F - 1.0471976F;
      (this.arm2.getModel()).field_78808_h += -f * 3.1415927F / 8.0F;
      this.forearm2.field_78795_f += f * 0.10471976F - 0.62831855F;
      this.bow.middle1.field_78795_f += -f1s * 3.1415927F / 16.0F;
      this.bow.side1.field_78795_f += -f1s * 3.1415927F / 24.0F;
      this.bow.middle2.field_78795_f += f1s * 3.1415927F / 16.0F;
      this.bow.side2.field_78795_f += f1s * 3.1415927F / 24.0F;
      this.bow.rotateRope();
      this.bow.rope1.field_78795_f += f1s * 3.1415927F / 6.0F;
      this.bow.rope2.field_78795_f += -f1s * 3.1415927F / 6.0F;
    } else if (fullTick < 26) {
      (this.head.getModel()).field_78796_g += 0.7853982F;
      for (int i = 0; i < this.spine.length; i++) {
        (this.spine[i]).middle.field_78796_g += -0.2617994F;
        (this.spine[i]).middle.field_78795_f += facePitch / 3.0F;
        (this.spine[i]).middle.field_78796_g += faceYaw / 3.0F;
      } 
      (this.arm1.getModel()).field_78795_f += -1.0471976F;
      this.arm1.field_78796_g += -0.62831855F;
      this.arm1.field_78808_h++;
      this.forearm1.field_78795_f += 0.44879895F;
      (this.arm2.getModel()).field_78795_f += -2.6179938F;
      this.arm2.field_78796_g += 0.62831855F;
      this.arm2.field_78808_h += -1.0471976F;
      this.forearm2.field_78795_f += -0.62831855F;
      float tick = MathHelper.func_76131_a((fullTick - 25) + this.partialTick, 0.0F, 1.0F);
      float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      this.bow.middle1.field_78795_f += -f * 3.1415927F / 16.0F;
      this.bow.side1.field_78795_f += -f * 3.1415927F / 24.0F;
      this.bow.middle2.field_78795_f += f * 3.1415927F / 16.0F;
      this.bow.side2.field_78795_f += f * 3.1415927F / 24.0F;
      this.bow.rotateRope();
      this.bow.rope1.field_78795_f += f * 3.1415927F / 6.0F;
      this.bow.rope2.field_78795_f += -f * 3.1415927F / 6.0F;
    } else if (fullTick < 30) {
      float tick = ((fullTick - 26) + this.partialTick) / 4.0F;
      float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      (this.head.getModel()).field_78796_g += f * 3.1415927F / 4.0F;
      for (int i = 0; i < this.spine.length; i++) {
        (this.spine[i]).middle.field_78796_g += -f * 3.1415927F / 12.0F;
        (this.spine[i]).middle.field_78795_f += f * facePitch / 3.0F;
        (this.spine[i]).middle.field_78796_g += f * faceYaw / 3.0F;
      } 
      (this.arm1.getModel()).field_78795_f += -f * 3.1415927F / 3.0F;
      this.arm1.field_78796_g += -f * 3.1415927F / 5.0F;
      this.arm1.field_78808_h += f * 3.1415927F / 3.0F;
      this.forearm1.field_78795_f += f * 3.1415927F / 7.0F;
      (this.arm2.getModel()).field_78795_f += -f * 3.1415927F / 1.2F;
      this.arm2.field_78796_g += f * 3.1415927F / 5.0F;
      this.arm2.field_78808_h += -f * 3.1415927F / 3.0F;
      this.forearm2.field_78795_f += -f * 3.1415927F / 5.0F;
      this.bow.rotateRope();
    } 
  }
  
  protected void animateSnipe(int fullTick, float facePitch, float faceYaw) {
    if (fullTick < 20) {
      float tick = (fullTick + this.partialTick) / 20.0F;
      float f = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F);
      (this.arm1.getModel()).field_78795_f += -f * 3.1415927F / 4.0F;
      this.arm1.field_78796_g += -f * 3.1415927F / 2.0F;
      this.arm1.field_78808_h += f * 3.1415927F / 16.0F;
      this.forearm1.field_78795_f += f * 3.1415927F / 7.0F;
      (this.arm2.getModel()).field_78795_f += -f * 3.1415927F / 4.0F;
      this.arm2.field_78796_g += f * 3.1415927F / 2.0F;
      this.arm2.field_78808_h += -f * 3.1415927F / 16.0F;
      (this.arm2.getModel()).field_78808_h += -f * 3.1415927F / 8.0F;
      this.forearm2.field_78795_f += -f * 3.1415927F / 6.0F;
      this.bow.rotateRope();
    } else if (fullTick < 48) {
      float tick = ((fullTick - 20) + this.partialTick) / 28.0F;
      float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      float f1 = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F);
      float f1s = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F * 0.4F);
      (this.head.getModel()).field_78796_g += f1 * 3.1415927F / 4.0F;
      for (int i = 0; i < this.spine.length; i++) {
        (this.spine[i]).middle.field_78796_g += -f1 * 3.1415927F / 12.0F;
        (this.spine[i]).middle.field_78795_f += f1 * facePitch / 3.0F;
        (this.spine[i]).middle.field_78796_g += f1 * faceYaw / 3.0F;
      } 
      (this.arm1.getModel()).field_78795_f += f * 0.2617994F - 1.0471976F;
      this.arm1.field_78796_g += f * -0.9424778F - 0.62831855F;
      this.arm1.field_78808_h += f * -0.850848F + 1.0471976F;
      this.forearm1.field_78795_f += 0.44879895F;
      (this.arm2.getModel()).field_78795_f += f * 1.8325956F - 2.6179938F;
      this.arm2.field_78796_g += f * 0.9424778F + 0.62831855F;
      this.arm2.field_78808_h += f * 0.850848F - 1.0471976F;
      (this.arm2.getModel()).field_78808_h += -f * 3.1415927F / 8.0F;
      this.forearm2.field_78795_f += f * 0.10471976F - 0.62831855F;
      this.bow.middle1.field_78795_f += -f1s * 3.1415927F / 16.0F;
      this.bow.side1.field_78795_f += -f1s * 3.1415927F / 24.0F;
      this.bow.middle2.field_78795_f += f1s * 3.1415927F / 16.0F;
      this.bow.side2.field_78795_f += f1s * 3.1415927F / 24.0F;
      this.bow.rotateRope();
      this.bow.rope1.field_78795_f += f1s * 3.1415927F / 6.0F;
      this.bow.rope2.field_78795_f += -f1s * 3.1415927F / 6.0F;
    } else if (fullTick < 104) {
      (this.head.getModel()).field_78796_g += 0.7853982F;
      for (int i = 0; i < this.spine.length; i++) {
        (this.spine[i]).middle.field_78796_g += -0.2617994F;
        (this.spine[i]).middle.field_78795_f += facePitch / 3.0F;
        (this.spine[i]).middle.field_78796_g += faceYaw / 3.0F;
      } 
      (this.arm1.getModel()).field_78795_f += -1.0471976F;
      this.arm1.field_78796_g += -0.62831855F;
      this.arm1.field_78808_h++;
      this.forearm1.field_78795_f += 0.44879895F;
      (this.arm2.getModel()).field_78795_f += -2.6179938F;
      this.arm2.field_78796_g += 0.62831855F;
      this.arm2.field_78808_h += -1.0471976F;
      this.forearm2.field_78795_f += -0.62831855F;
      float tick = MathHelper.func_76131_a((fullTick - 100) + this.partialTick, 0.0F, 1.0F);
      float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      this.bow.middle1.field_78795_f += -f * 3.1415927F / 16.0F;
      this.bow.side1.field_78795_f += -f * 3.1415927F / 24.0F;
      this.bow.middle2.field_78795_f += f * 3.1415927F / 16.0F;
      this.bow.side2.field_78795_f += f * 3.1415927F / 24.0F;
      this.bow.rotateRope();
      this.bow.rope1.field_78795_f += f * 3.1415927F / 6.0F;
      this.bow.rope2.field_78795_f += -f * 3.1415927F / 6.0F;
    } else if (fullTick < 120) {
      float tick = ((fullTick - 104) + this.partialTick) / 16.0F;
      float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      (this.head.getModel()).field_78796_g += f * 3.1415927F / 4.0F;
      for (int i = 0; i < this.spine.length; i++) {
        (this.spine[i]).middle.field_78796_g += -f * 3.1415927F / 12.0F;
        (this.spine[i]).middle.field_78795_f += f * facePitch / 3.0F;
        (this.spine[i]).middle.field_78796_g += f * faceYaw / 3.0F;
      } 
      (this.arm1.getModel()).field_78795_f += -f * 3.1415927F / 3.0F;
      this.arm1.field_78796_g += -f * 3.1415927F / 5.0F;
      this.arm1.field_78808_h += f * 3.1415927F / 3.0F;
      this.forearm1.field_78795_f += f * 3.1415927F / 7.0F;
      (this.arm2.getModel()).field_78795_f += -f * 3.1415927F / 1.2F;
      this.arm2.field_78796_g += f * 3.1415927F / 5.0F;
      this.arm2.field_78808_h += -f * 3.1415927F / 3.0F;
      this.forearm2.field_78795_f += -f * 3.1415927F / 5.0F;
      this.bow.rotateRope();
    } 
  }
  
  protected void animateMultiShoot(int fullTick, float facePitch, float faceYaw) {
    if (fullTick < 10) {
      float tick = (fullTick + this.partialTick) / 10.0F;
      float f = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F);
      this.skeleBase.field_78797_d += f * 3.5F;
      (this.spine[0]).middle.field_78795_f += f * 3.1415927F / 6.0F;
      this.head.field_78795_f += -f * 3.1415927F / 4.0F;
      this.arm1.field_78795_f += f * 3.1415927F / 6.0F;
      this.arm1.field_78808_h += f * 3.1415927F / 16.0F;
      this.arm2.field_78795_f += f * 3.1415927F / 6.0F;
      this.arm2.field_78808_h += -f * 3.1415927F / 16.0F;
      this.leg1.field_78795_f += -f * 3.1415927F / 8.0F;
      this.leg2.field_78795_f += -f * 3.1415927F / 8.0F;
      (this.foreleg1.getModel()).field_78795_f += f * 3.1415927F / 4.0F;
      (this.foreleg2.getModel()).field_78795_f += f * 3.1415927F / 4.0F;
      this.bow.rotateRope();
    } else if (fullTick < 12) {
      float tick = ((fullTick - 10) + this.partialTick) / 2.0F;
      float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      float f1 = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F);
      this.skeleBase.field_78797_d += f * 3.5F;
      (this.spine[0]).middle.field_78795_f += f * 3.1415927F / 6.0F;
      this.head.field_78795_f += -f * 3.1415927F / 4.0F;
      this.arm1.field_78795_f += f * 3.1415927F / 6.0F;
      this.arm1.field_78808_h += f * 3.1415927F / 16.0F;
      this.arm2.field_78795_f += f * 3.1415927F / 6.0F;
      this.arm2.field_78808_h += -f * 3.1415927F / 16.0F;
      this.leg1.field_78795_f += -f * 3.1415927F / 8.0F;
      this.leg2.field_78795_f += -f * 3.1415927F / 8.0F;
      (this.foreleg1.getModel()).field_78795_f += f * 3.1415927F / 4.0F;
      (this.foreleg2.getModel()).field_78795_f += f * 3.1415927F / 4.0F;
      this.arm1.field_78808_h += -f1 * 3.1415927F / 14.0F;
      this.arm2.field_78808_h += f1 * 3.1415927F / 14.0F;
      this.leg1.field_78808_h += -f1 * 3.1415927F / 24.0F;
      this.leg2.field_78808_h += f1 * 3.1415927F / 24.0F;
      this.foreleg1.field_78808_h += f1 * 3.1415927F / 64.0F;
      this.foreleg2.field_78808_h += -f1 * 3.1415927F / 64.0F;
      this.bow.rotateRope();
    } else if (fullTick < 14) {
      this.arm1.field_78808_h += -0.22439948F;
      this.arm2.field_78808_h += 0.22439948F;
      this.leg1.field_78808_h += -0.1308997F;
      this.leg2.field_78808_h += 0.1308997F;
      this.foreleg1.field_78808_h += 0.049087387F;
      this.foreleg2.field_78808_h += -0.049087387F;
      this.bow.rotateRope();
    } else if (fullTick < 17) {
      float tick = ((fullTick - 14) + this.partialTick) / 3.0F;
      float f = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F);
      float f1 = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      this.arm1.field_78808_h += -f1 * 3.1415927F / 14.0F;
      this.arm2.field_78808_h += f1 * 3.1415927F / 14.0F;
      this.leg1.field_78808_h += -f1 * 3.1415927F / 24.0F;
      this.leg2.field_78808_h += f1 * 3.1415927F / 24.0F;
      this.foreleg1.field_78808_h += f1 * 3.1415927F / 64.0F;
      this.foreleg2.field_78808_h += -f1 * 3.1415927F / 64.0F;
      (this.arm1.getModel()).field_78795_f += -f * 3.1415927F / 4.0F;
      this.arm1.field_78796_g += -f * 3.1415927F / 2.0F;
      this.arm1.field_78808_h += f * 3.1415927F / 16.0F;
      this.forearm1.field_78795_f += f * 3.1415927F / 7.0F;
      (this.arm2.getModel()).field_78795_f += -f * 3.1415927F / 4.0F;
      this.arm2.field_78796_g += f * 3.1415927F / 2.0F;
      this.arm2.field_78808_h += -f * 3.1415927F / 16.0F;
      (this.arm2.getModel()).field_78808_h += -f * 3.1415927F / 8.0F;
      this.forearm2.field_78795_f += -f * 3.1415927F / 6.0F;
      this.bow.rotateRope();
    } else if (fullTick < 20) {
      float tick = ((fullTick - 17) + this.partialTick) / 3.0F;
      float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      float f1 = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F);
      float f1s = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F * 0.4F);
      (this.head.getModel()).field_78796_g += f1 * 3.1415927F / 4.0F;
      for (int i = 0; i < this.spine.length; i++) {
        (this.spine[i]).middle.field_78796_g += -f1 * 3.1415927F / 12.0F;
        (this.spine[i]).middle.field_78795_f += f1 * facePitch / 3.0F;
        (this.spine[i]).middle.field_78796_g += f1 * faceYaw / 3.0F;
      } 
      (this.arm1.getModel()).field_78795_f += f * 0.2617994F - 1.0471976F;
      this.arm1.field_78796_g += f * -0.9424778F - 0.62831855F;
      this.arm1.field_78808_h += f * -0.850848F + 1.0471976F;
      this.forearm1.field_78795_f += 0.44879895F;
      (this.arm2.getModel()).field_78795_f += f * 1.8325956F - 2.6179938F;
      this.arm2.field_78796_g += f * 0.9424778F + 0.62831855F;
      this.arm2.field_78808_h += f * 0.850848F - 1.0471976F;
      (this.arm2.getModel()).field_78808_h += -f * 3.1415927F / 8.0F;
      this.forearm2.field_78795_f += f * 0.10471976F - 0.62831855F;
      this.bow.middle1.field_78795_f += -f1s * 3.1415927F / 16.0F;
      this.bow.side1.field_78795_f += -f1s * 3.1415927F / 24.0F;
      this.bow.middle2.field_78795_f += f1s * 3.1415927F / 16.0F;
      this.bow.side2.field_78795_f += f1s * 3.1415927F / 24.0F;
      this.bow.rotateRope();
      this.bow.rope1.field_78795_f += f1s * 3.1415927F / 6.0F;
      this.bow.rope2.field_78795_f += -f1s * 3.1415927F / 6.0F;
    } else if (fullTick < 24) {
      (this.head.getModel()).field_78796_g += 0.7853982F;
      for (int i = 0; i < this.spine.length; i++) {
        (this.spine[i]).middle.field_78796_g += -0.2617994F;
        (this.spine[i]).middle.field_78795_f += facePitch / 3.0F;
        (this.spine[i]).middle.field_78796_g += faceYaw / 3.0F;
      } 
      (this.arm1.getModel()).field_78795_f += -1.0471976F;
      this.arm1.field_78796_g += -0.62831855F;
      this.arm1.field_78808_h++;
      this.forearm1.field_78795_f += 0.44879895F;
      (this.arm2.getModel()).field_78795_f += -2.6179938F;
      this.arm2.field_78796_g += 0.62831855F;
      this.arm2.field_78808_h += -1.0471976F;
      this.forearm2.field_78795_f += -0.62831855F;
      float tick = MathHelper.func_76131_a((fullTick - 25) + this.partialTick, 0.0F, 1.0F);
      float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      this.bow.middle1.field_78795_f += -f * 3.1415927F / 16.0F;
      this.bow.side1.field_78795_f += -f * 3.1415927F / 24.0F;
      this.bow.middle2.field_78795_f += f * 3.1415927F / 16.0F;
      this.bow.side2.field_78795_f += f * 3.1415927F / 24.0F;
      this.bow.rotateRope();
      this.bow.rope1.field_78795_f += f * 3.1415927F / 6.0F;
      this.bow.rope2.field_78795_f += -f * 3.1415927F / 6.0F;
    } else if (fullTick < 28) {
      float tick = ((fullTick - 24) + this.partialTick) / 4.0F;
      float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      (this.head.getModel()).field_78796_g += f * 3.1415927F / 4.0F;
      for (int i = 0; i < this.spine.length; i++) {
        (this.spine[i]).middle.field_78796_g += -f * 3.1415927F / 12.0F;
        (this.spine[i]).middle.field_78795_f += f * facePitch / 3.0F;
        (this.spine[i]).middle.field_78796_g += f * faceYaw / 3.0F;
      } 
      (this.arm1.getModel()).field_78795_f += -f * 3.1415927F / 3.0F;
      this.arm1.field_78796_g += -f * 3.1415927F / 5.0F;
      this.arm1.field_78808_h += f * 3.1415927F / 3.0F;
      this.forearm1.field_78795_f += f * 3.1415927F / 7.0F;
      (this.arm2.getModel()).field_78795_f += -f * 3.1415927F / 1.2F;
      this.arm2.field_78796_g += f * 3.1415927F / 5.0F;
      this.arm2.field_78808_h += -f * 3.1415927F / 3.0F;
      this.forearm2.field_78795_f += -f * 3.1415927F / 5.0F;
      this.bow.rotateRope();
    } 
  }
  
  protected void animateConstrict() {
    this.animator.startPhase(5);
    this.animator.rotate(this.waist, 0.1308997F, 0.0F, 0.0F);
    int animTick;
    for (animTick = 0; animTick < this.spine.length; animTick++) {
      float tick = (animTick == 0) ? 0.3926991F : ((animTick == 2) ? -0.3926991F : 0.0F);
      float f = (animTick == 1) ? 0.3926991F : 0.31415927F;
      this.animator.rotate((this.spine[animTick]).side1[0], tick, f, 0.0F);
      this.animator.rotate((this.spine[animTick]).side1[1], 0.0F, 0.15707964F, 0.0F);
      this.animator.rotate((this.spine[animTick]).side1[2], 0.0F, 0.2617994F, 0.0F);
      this.animator.rotate((this.spine[animTick]).side2[0], tick, -f, 0.0F);
      this.animator.rotate((this.spine[animTick]).side2[1], 0.0F, -0.15707964F, 0.0F);
      this.animator.rotate((this.spine[animTick]).side2[2], 0.0F, -0.2617994F, 0.0F);
    } 
    this.animator.rotate(this.arm1, 0.0F, 0.0F, 0.8975979F);
    this.animator.rotate(this.arm2, 0.0F, 0.0F, -0.8975979F);
    this.animator.move(this.skeleBase, 0.0F, 1.0F, 0.0F);
    this.animator.rotate(this.leg1, -0.44879895F, 0.0F, 0.0F);
    this.animator.rotate(this.leg2, -0.44879895F, 0.0F, 0.0F);
    this.animator.rotate(this.foreleg1.getModel(), 0.5235988F, 0.0F, 0.0F);
    this.animator.rotate(this.foreleg2.getModel(), 0.5235988F, 0.0F, 0.0F);
    this.animator.endPhase();
    this.animator.setStationaryPhase(2);
    this.animator.startPhase(1);
    this.animator.rotate(this.neck, 0.19634955F, 0.0F, 0.0F);
    this.animator.rotate(this.head, 0.15707964F, 0.0F, 0.0F);
    this.animator.rotate(this.waist, 0.31415927F, 0.0F, 0.0F);
    this.animator.rotate((this.spine[0]).middle, 0.2617994F, 0.0F, 0.0F);
    for (animTick = 0; animTick < this.spine.length; animTick++) {
      float tick = (animTick == 0) ? 0.1308997F : ((animTick == 2) ? -0.1308997F : 0.0F);
      float f = (animTick == 1) ? -0.17453294F : -0.22439948F;
      this.animator.rotate((this.spine[animTick]).side1[0], tick - 0.08F, f, 0.0F);
      this.animator.rotate((this.spine[animTick]).side1[1], 0.0F, 0.15707964F, 0.0F);
      this.animator.rotate((this.spine[animTick]).side1[2], 0.0F, 0.2617994F, 0.0F);
      this.animator.rotate((this.spine[animTick]).side2[0], tick + 0.08F, -f, 0.0F);
      this.animator.rotate((this.spine[animTick]).side2[1], 0.0F, -0.15707964F, 0.0F);
      this.animator.rotate((this.spine[animTick]).side2[2], 0.0F, -0.2617994F, 0.0F);
    } 
    this.animator.move(this.skeleBase, 0.0F, 1.0F, 0.0F);
    this.animator.rotate(this.leg1, -0.44879895F, 0.0F, 0.0F);
    this.animator.rotate(this.leg2, -0.44879895F, 0.0F, 0.0F);
    this.animator.rotate(this.foreleg1.getModel(), 0.5235988F, 0.0F, 0.0F);
    this.animator.rotate(this.foreleg2.getModel(), 0.5235988F, 0.0F, 0.0F);
    this.animator.endPhase();
    this.animator.setStationaryPhase(4);
    this.animator.resetPhase(8);
    animTick = this.animator.getEntity().getAnimationTick();
    if (animTick < 5) {
      float tick = (animTick + this.partialTick) / 5.0F;
      float f = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F);
      for (int i = 0; i < this.spine.length; i++) {
        (this.spine[i]).side1[0].setScale(1.0F + f * 0.6F);
        (this.spine[i]).side2[0].setScale(1.0F + f * 0.6F);
      } 
    } else if (animTick < 12) {
      for (int i = 0; i < this.spine.length; i++) {
        (this.spine[i]).side1[0].setScale(1.6F);
        (this.spine[i]).side2[0].setScale(1.6F);
      } 
    } else if (animTick < 20) {
      float tick = ((animTick - 12) + this.partialTick) / 8.0F;
      float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      for (int i = 0; i < this.spine.length; i++) {
        (this.spine[i]).side1[0].setScale(1.0F + f * 0.6F);
        (this.spine[i]).side2[0].setScale(1.0F + f * 0.6F);
      } 
    } 
  }
  
  public static ModelRenderer createSkull(ModelBase base) {
    ModelRenderer head = new ModelRenderer(base, 0, 0);
    head.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.4F);
    ModelRenderer jaw = new ModelRenderer(base, 32, 0);
    jaw.func_78790_a(-4.0F, -3.0F, -8.0F, 8, 3, 8, 0.7F);
    jaw.func_78793_a(0.0F, -0.2F, 3.5F);
    jaw.field_78795_f = 0.09817477F;
    head.func_78792_a(jaw);
    return head;
  }
  
  public void func_78086_a(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
    this.partialTick = partialTickTime;
  }
  
  @SideOnly(Side.CLIENT)
  static class Spine {
    public final ModelRenderer middle;
    
    public final ScalableModelRenderer[] side1;
    
    public final ScalableModelRenderer[] side2;
    
    public Spine(ModelBase model) {
      this(model, false);
    }
    
    public Spine(ModelBase model, boolean skeletonPart) {
      this.middle = new ModelRenderer(model, 50, 0);
      this.middle.func_78790_a(-2.5F, -4.0F, -2.0F, 5, 4, 4, 0.5F);
      this.side1 = new ScalableModelRenderer[3];
      this.side2 = new ScalableModelRenderer[3];
      this.side1[0] = new ScalableModelRenderer(model, 32, 12);
      this.side1[0].func_78790_a(skeletonPart ? 0.0F : -6.0F, -2.0F, -2.0F, 6, 2, 2, 0.25F);
      if (!skeletonPart)
        this.side1[0].func_78793_a(-3.0F, -1.0F, 1.75F); 
      this.middle.func_78792_a(this.side1[0]);
      this.side2[0] = new ScalableModelRenderer(model, 32, 12);
      (this.side2[0]).field_78809_i = true;
      this.side2[0].func_78790_a(skeletonPart ? -6.0F : 0.0F, -2.0F, -2.0F, 6, 2, 2, 0.25F);
      if (!skeletonPart)
        this.side2[0].func_78793_a(3.0F, -1.0F, 1.75F); 
      this.middle.func_78792_a(this.side2[0]);
      this.side1[1] = new ScalableModelRenderer(model, 32, 12);
      (this.side1[1]).field_78809_i = true;
      this.side1[1].func_78790_a(-6.0F, -2.0F, -2.0F, 6, 2, 2, 0.2F);
      this.side1[1].func_78793_a(skeletonPart ? -0.5F : -6.5F, 0.0F, 0.0F);
      this.side1[0].func_78792_a(this.side1[1]);
      this.side2[1] = new ScalableModelRenderer(model, 32, 12);
      this.side2[1].func_78790_a(0.0F, -2.0F, -2.0F, 6, 2, 2, 0.2F);
      this.side2[1].func_78793_a(skeletonPart ? 0.5F : 6.5F, 0.0F, 0.0F);
      this.side2[0].func_78792_a(this.side2[1]);
      this.side1[2] = new ScalableModelRenderer(model, 32, 12);
      this.side1[2].func_78790_a(-6.0F, -2.0F, -2.0F, 6, 2, 2, 0.15F);
      this.side1[2].func_78793_a(-6.4F, 0.0F, 0.0F);
      this.side1[1].func_78792_a(this.side1[2]);
      this.side2[2] = new ScalableModelRenderer(model, 32, 12);
      (this.side2[2]).field_78809_i = true;
      this.side2[2].func_78790_a(0.0F, -2.0F, -2.0F, 6, 2, 2, 0.15F);
      this.side2[2].func_78793_a(6.4F, 0.0F, 0.0F);
      this.side2[1].func_78792_a(this.side2[2]);
    }
    
    private void resetAngles(ModelRenderer... boxes) {
      for (ModelRenderer box : boxes) {
        box.field_78795_f = 0.0F;
        box.field_78796_g = 0.0F;
        box.field_78808_h = 0.0F;
      } 
    }
    
    public void setAngles(float PI, boolean middleSpine) {
      resetAngles(new ModelRenderer[] { this.middle });
      resetAngles((ModelRenderer[])this.side1);
      resetAngles((ModelRenderer[])this.side2);
      this.middle.field_78795_f = PI / 18.0F;
      (this.side1[0]).field_78796_g = -PI / 4.5F;
      (this.side2[0]).field_78796_g = PI / 4.5F;
      (this.side1[1]).field_78796_g = -PI / 3.0F;
      (this.side2[1]).field_78796_g = PI / 3.0F;
      (this.side1[2]).field_78796_g = -PI / 3.5F;
      (this.side2[2]).field_78796_g = PI / 3.5F;
      if (middleSpine)
        for (int i = 0; i < this.side1.length; i++) {
          (this.side1[i]).field_78796_g *= 0.98F;
          (this.side2[i]).field_78796_g *= 0.98F;
        }  
      this.side1[0].setScale(1.0F);
      this.side2[0].setScale(1.0F);
    }
    
    public void animate(float breatheAnim) {
      (this.side1[1]).field_78796_g += breatheAnim * 0.02F;
      (this.side2[1]).field_78796_g -= breatheAnim * 0.02F;
    }
  }
  
  public class MutantCrossbowModel {
    public final ModelRenderer armwear;
    
    public final ModelRenderer middle;
    
    public final ModelRenderer middle1;
    
    public final ModelRenderer middle2;
    
    public final ModelRenderer side1;
    
    public final ModelRenderer side2;
    
    public final ModelRenderer side3;
    
    public final ModelRenderer side4;
    
    public final ModelRenderer rope1;
    
    public final ModelRenderer rope2;
    
    public MutantCrossbowModel(ModelBase model) {
      this.armwear = new ModelRenderer(model, 0, 64);
      this.armwear.func_78790_a(-2.0F, -3.0F, -2.0F, 4, 6, 4, 0.3F);
      this.middle = new ModelRenderer(model, 16, 64);
      this.middle.func_78789_a(-2.0F, -2.0F, -3.0F, 4, 4, 6);
      this.middle.func_78793_a(-3.5F, 0.0F, 0.0F);
      this.armwear.func_78792_a(this.middle);
      this.middle1 = new ModelRenderer(model, 36, 64);
      this.middle1.func_78789_a(-1.5F, -1.5F, -3.0F, 3, 3, 6);
      this.middle1.func_78793_a(0.0F, 0.6F, -4.0F);
      this.middle.func_78792_a(this.middle1);
      this.middle2 = new ModelRenderer(model, 36, 64);
      this.middle2.func_78789_a(-1.5F, -1.5F, -3.0F, 3, 3, 6);
      this.middle2.func_78793_a(0.0F, 0.6F, 4.0F);
      this.middle.func_78792_a(this.middle2);
      this.side1 = new ModelRenderer(model, 0, 74);
      this.side1.func_78789_a(-1.0F, -1.0F, -8.0F, 2, 2, 8);
      this.side1.func_78793_a(0.0F, 0.0F, -2.0F);
      this.middle1.func_78792_a(this.side1);
      this.side2 = new ModelRenderer(model, 0, 74);
      this.side2.func_78789_a(-1.0F, -1.0F, 0.0F, 2, 2, 8);
      this.side2.func_78793_a(0.0F, 0.0F, 2.0F);
      this.middle2.func_78792_a(this.side2);
      this.side3 = new ModelRenderer(model, 20, 74);
      this.side3.func_78789_a(-0.5F, -0.5F, -8.0F, 1, 1, 8);
      this.side3.func_78793_a(0.0F, 0.0F, -5.0F);
      this.side1.func_78792_a(this.side3);
      this.side4 = new ModelRenderer(model, 20, 74);
      this.side4.func_78789_a(-0.5F, -0.5F, 0.0F, 1, 1, 8);
      this.side4.func_78793_a(0.0F, 0.0F, 5.0F);
      this.side2.func_78792_a(this.side4);
      this.rope1 = new ModelRenderer(model, 0, 84);
      this.rope1.func_78790_a(-0.5F, -0.5F, 0.0F, 1, 1, 15, -0.4F);
      this.rope1.func_78793_a(0.0F, 0.0F, -6.0F);
      this.side3.func_78792_a(this.rope1);
      this.rope2 = new ModelRenderer(model, 0, 84);
      this.rope2.func_78790_a(-0.5F, -0.5F, -15.0F, 1, 1, 15, -0.4F);
      this.rope2.func_78793_a(0.0F, 0.0F, 6.0F);
      this.side4.func_78792_a(this.rope2);
    }
    
    public void setAngles(float PI) {
      this.middle1.field_78795_f = PI / 8.0F;
      this.middle2.field_78795_f = -PI / 8.0F;
      this.side1.field_78795_f = -PI / 5.0F;
      this.side2.field_78795_f = PI / 5.0F;
      this.side3.field_78795_f = -PI / 4.0F;
      this.side4.field_78795_f = PI / 4.0F;
    }
    
    public void rotateRope() {
      this.rope1.field_78795_f = -(this.middle1.field_78795_f + this.side1.field_78795_f + this.side3.field_78795_f);
      this.rope2.field_78795_f = -(this.middle2.field_78795_f + this.side2.field_78795_f + this.side4.field_78795_f);
    }
  }
}
