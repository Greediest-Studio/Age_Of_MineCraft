package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.model;

import java.util.Arrays;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantEnderman;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelMutantEnderman extends ModelBase {
  private final ModelRenderer pelvis;
  
  private final ModelRenderer abdomen;
  
  private final ModelRenderer chest;
  
  private final ModelRenderer neck;
  
  private final ModelRenderer head;
  
  private final ModelRenderer mouth;
  
  private final Arm rightArm;
  
  private final Arm leftArm;
  
  private final Arm lowerRightArm;
  
  private final Arm lowerLeftArm;
  
  private final ModelRenderer legjoint1;
  
  private final ModelRenderer legjoint2;
  
  private final ModelRenderer leg1;
  
  private final ModelRenderer leg2;
  
  private final ModelRenderer foreleg1;
  
  private final ModelRenderer foreleg2;
  
  private float partialTick;
  
  public ModelMutantEnderman() {
    this.field_78090_t = 128;
    this.field_78089_u = 64;
    this.pelvis = new ModelRenderer(this);
    this.pelvis.func_78793_a(0.0F, -15.5F, 8.0F);
    this.abdomen = new ModelRenderer(this, 32, 0);
    this.abdomen.func_78789_a(-4.0F, -10.0F, -2.0F, 8, 10, 4);
    this.pelvis.func_78792_a(this.abdomen);
    this.chest = new ModelRenderer(this, 50, 8);
    this.chest.func_78789_a(-5.0F, -16.0F, -3.0F, 10, 16, 6);
    this.chest.func_78793_a(0.0F, -8.0F, 0.0F);
    this.abdomen.func_78792_a(this.chest);
    this.neck = new ModelRenderer(this, 32, 14);
    this.neck.func_78789_a(-1.5F, -4.0F, -1.5F, 3, 4, 3);
    this.neck.func_78793_a(0.0F, -15.0F, 0.0F);
    this.chest.func_78792_a(this.neck);
    this.head = new ModelRenderer(this);
    this.head.func_78784_a(0, 0).func_78790_a(-4.0F, -4.0F, -8.0F, 8, 6, 8, 0.5F);
    this.head.func_78784_a(0, 14).func_78790_a(-4.0F, 3.0F, -8.0F, 8, 2, 8, 0.5F);
    this.head.func_78793_a(0.0F, -5.0F, 3.0F);
    this.neck.func_78792_a(this.head);
    this.mouth = new ModelRenderer(this, 0, 24);
    this.mouth.func_78789_a(-4.0F, 3.0F, -8.0F, 8, 2, 8);
    this.head.func_78792_a(this.mouth);
    this.rightArm = new Arm(this, this.chest, true);
    this.leftArm = new Arm(this, this.chest, false);
    this.lowerRightArm = new Arm(this, this.chest, true);
    this.lowerRightArm.arm.field_78797_d += 6.0F;
    this.lowerLeftArm = new Arm(this, this.chest, false);
    this.lowerLeftArm.arm.field_78797_d += 6.0F;
    this.legjoint1 = new ModelRenderer(this);
    this.legjoint1.func_78793_a(-1.5F, 0.0F, 0.75F);
    this.abdomen.func_78792_a(this.legjoint1);
    this.legjoint2 = new ModelRenderer(this);
    this.legjoint2.func_78793_a(1.5F, 0.0F, 0.75F);
    this.abdomen.func_78792_a(this.legjoint2);
    this.leg1 = new ModelRenderer(this, 0, 34);
    this.leg1.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 24, 3, 0.5F);
    this.leg1.func_78793_a(0.0F, -2.0F, 0.0F);
    this.legjoint1.func_78792_a(this.leg1);
    this.leg2 = new ModelRenderer(this, 0, 34);
    this.leg2.field_78809_i = true;
    this.leg2.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 24, 3, 0.5F);
    this.leg2.func_78793_a(0.0F, -2.0F, 0.0F);
    this.legjoint2.func_78792_a(this.leg2);
    this.foreleg1 = new ModelRenderer(this, 12, 34);
    this.foreleg1.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 24, 3, 0.5F);
    this.foreleg1.func_78793_a(0.0F, 23.0F, 0.0F);
    this.leg1.func_78792_a(this.foreleg1);
    this.foreleg2 = new ModelRenderer(this, 12, 34);
    this.foreleg2.field_78809_i = true;
    this.foreleg2.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 24, 3, 0.5F);
    this.foreleg2.func_78793_a(0.0F, 23.0F, 0.0F);
    this.leg2.func_78792_a(this.foreleg2);
  }
  
  public void func_78088_a(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    setAngles();
    EntityMutantEnderman mutantEnderman = (EntityMutantEnderman)entity;
    animate(mutantEnderman, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    this.lowerRightArm.arm.setScale(mutantEnderman.getArmScale(this.partialTick));
    this.lowerLeftArm.arm.setScale(mutantEnderman.getArmScale(this.partialTick));
    this.pelvis.func_78785_a(scale);
  }
  
  private void setAngles() {
    this.pelvis.field_78797_d = -15.5F;
    this.abdomen.field_78795_f = 0.31415927F;
    this.chest.field_78795_f = 0.3926991F;
    this.chest.field_78796_g = 0.0F;
    this.chest.field_78808_h = 0.0F;
    this.neck.field_78795_f = 0.19634955F;
    this.neck.field_78808_h = 0.0F;
    this.head.field_78795_f = -0.7853982F;
    this.head.field_78796_g = 0.0F;
    this.head.field_78808_h = 0.0F;
    this.mouth.field_78795_f = 0.0F;
    this.rightArm.setAngles();
    this.leftArm.setAngles();
    this.lowerRightArm.setAngles();
    this.lowerRightArm.arm.field_78795_f += 0.1F;
    this.lowerRightArm.arm.field_78808_h -= 0.2F;
    this.lowerLeftArm.setAngles();
    this.lowerLeftArm.arm.field_78795_f += 0.1F;
    this.lowerLeftArm.arm.field_78808_h += 0.2F;
    this.legjoint1.field_78795_f = 0.0F;
    this.legjoint2.field_78795_f = 0.0F;
    this.leg1.field_78795_f = -0.8975979F;
    this.leg1.field_78796_g = 0.0F;
    this.leg1.field_78808_h = 0.2617994F;
    this.leg2.field_78795_f = -0.8975979F;
    this.leg2.field_78796_g = 0.0F;
    this.leg2.field_78808_h = -0.2617994F;
    this.foreleg1.field_78795_f = 0.7853982F;
    this.foreleg1.field_78808_h = -0.1308997F;
    this.foreleg2.field_78795_f = 0.7853982F;
    this.foreleg2.field_78808_h = 0.1308997F;
  }
  
  private void animate(EntityMutantEnderman enderman, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float f5) {
    float walkSpeed = 0.3F;
    float walkAnim1 = (MathHelper.func_76126_a((limbSwing - 0.8F) * walkSpeed) + 0.8F) * limbSwingAmount;
    float walkAnim2 = -(MathHelper.func_76126_a((limbSwing + 0.8F) * walkSpeed) - 0.8F) * limbSwingAmount;
    float walkAnim3 = (MathHelper.func_76126_a((limbSwing + 0.8F) * walkSpeed) - 0.8F) * limbSwingAmount;
    float walkAnim4 = -(MathHelper.func_76126_a((limbSwing - 0.8F) * walkSpeed) + 0.8F) * limbSwingAmount;
    float[] walkAnim = new float[5];
    Arrays.fill(walkAnim, MathHelper.func_76126_a(limbSwing * walkSpeed) * limbSwingAmount);
    float breatheAnim = MathHelper.func_76126_a(ageInTicks * 0.15F);
    float faceYaw = netHeadYaw * 3.1415927F / 180.0F;
    float facePitch = headPitch * 3.1415927F / 180.0F;
    int arm;
    for (arm = 1; arm < enderman.heldBlock.length; arm++) {
      if (enderman.heldBlock[arm] != 0) {
        animateHoldBlock(enderman.heldBlockTick[arm], arm, (enderman.hasTarget > 0));
        walkAnim[arm] = walkAnim[arm] * 0.4F;
      } 
    } 
    if (enderman.getAttackID() == 4) {
      arm = enderman.getActiveArm();
      animateMelee(enderman.getAttackTick(), arm);
      walkAnim[arm] = 0.0F;
    } 
    if (enderman.getAttackID() == 5) {
      arm = enderman.getActiveArm();
      animateThrowBlock(enderman.getAttackTick(), arm);
    } 
    if (enderman.getAttackID() == 8) {
      animateScream(enderman.getAttackTick());
      float scale = 1.0F - MathHelper.func_76131_a(enderman.field_70725_aQ / 6.0F, 0.0F, 1.0F);
      faceYaw *= scale;
      facePitch *= scale;
      walkAnim1 *= scale;
      walkAnim2 *= scale;
      walkAnim3 *= scale;
      walkAnim4 *= scale;
      Arrays.fill(walkAnim, 0.0F);
    } 
    if (enderman.getAttackID() == 10)
      animateTeleSmash(enderman.getAttackTick()); 
    if (enderman.getAttackID() == 11) {
      animateDeath(enderman.field_70725_aQ);
      float scale = 1.0F - MathHelper.func_76131_a(enderman.field_70725_aQ / 6.0F, 0.0F, 1.0F);
      faceYaw *= scale;
      facePitch *= scale;
      walkAnim1 *= scale;
      walkAnim2 *= scale;
      walkAnim3 *= scale;
      walkAnim4 *= scale;
      Arrays.fill(walkAnim, 0.0F);
    } 
    this.head.field_78795_f += facePitch * 0.5F;
    this.head.field_78796_g += faceYaw * 0.7F;
    this.head.field_78808_h -= faceYaw * 0.7F;
    this.neck.field_78795_f += facePitch * 0.3F;
    this.chest.field_78795_f += facePitch * 0.2F;
    this.mouth.field_78795_f += breatheAnim * 0.02F + 0.02F;
    this.neck.field_78795_f -= breatheAnim * 0.02F;
    this.rightArm.arm.field_78808_h += breatheAnim * 0.004F;
    this.leftArm.arm.field_78808_h -= breatheAnim * 0.004F;
    ModelRenderer[] arr$ = this.rightArm.finger;
    int len$ = arr$.length;
    int i$;
    for (i$ = 0; i$ < len$; i$++) {
      ModelRenderer finger = arr$[i$];
      finger.field_78808_h += breatheAnim * 0.05F;
    } 
    this.rightArm.thumb.field_78808_h -= breatheAnim * 0.05F;
    arr$ = this.leftArm.finger;
    len$ = arr$.length;
    for (i$ = 0; i$ < len$; i$++) {
      ModelRenderer finger = arr$[i$];
      finger.field_78808_h -= breatheAnim * 0.05F;
    } 
    this.leftArm.thumb.field_78808_h += breatheAnim * 0.05F;
    this.lowerRightArm.arm.field_78808_h += breatheAnim * 0.002F;
    this.lowerLeftArm.arm.field_78808_h -= breatheAnim * 0.002F;
    arr$ = this.lowerRightArm.finger;
    len$ = arr$.length;
    for (i$ = 0; i$ < len$; i$++) {
      ModelRenderer finger = arr$[i$];
      finger.field_78808_h += breatheAnim * 0.02F;
    } 
    this.lowerRightArm.thumb.field_78808_h -= breatheAnim * 0.02F;
    arr$ = this.lowerLeftArm.finger;
    len$ = arr$.length;
    for (i$ = 0; i$ < len$; i$++) {
      ModelRenderer finger = arr$[i$];
      finger.field_78808_h -= breatheAnim * 0.02F;
    } 
    this.lowerLeftArm.thumb.field_78808_h += breatheAnim * 0.02F;
    this.pelvis.field_78797_d -= Math.abs(walkAnim[0]);
    this.chest.field_78796_g -= walkAnim[0] * 0.06F;
    this.rightArm.arm.field_78795_f -= walkAnim[1] * 0.6F;
    this.leftArm.arm.field_78795_f += walkAnim[2] * 0.6F;
    this.rightArm.forearm.field_78795_f -= walkAnim[1] * 0.2F;
    this.leftArm.forearm.field_78795_f += walkAnim[2] * 0.2F;
    this.lowerRightArm.arm.field_78795_f -= walkAnim[3] * 0.3F;
    this.lowerLeftArm.arm.field_78795_f += walkAnim[4] * 0.3F;
    this.lowerRightArm.forearm.field_78795_f -= walkAnim[3] * 0.1F;
    this.lowerLeftArm.forearm.field_78795_f += walkAnim[4] * 0.1F;
    this.legjoint1.field_78795_f += walkAnim1 * 0.6F;
    this.legjoint2.field_78795_f += walkAnim2 * 0.6F;
    this.foreleg1.field_78795_f += walkAnim3 * 0.3F;
    this.foreleg2.field_78795_f += walkAnim4 * 0.3F;
  }
  
  private void animateHoldBlock(int fullTick, int armID, boolean hasTarget) {
    float tick = (fullTick + this.partialTick) / 10.0F;
    if (!hasTarget)
      tick = (fullTick == 0) ? 0.0F : ((fullTick - this.partialTick) / 10.0F); 
    float f = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F);
    if (armID == 1) {
      this.rightArm.arm.field_78808_h += f * 0.8F;
      this.rightArm.forearm.field_78808_h += f * 0.6F;
      this.rightArm.hand.field_78796_g += f * 0.8F;
      (this.rightArm.finger[0]).field_78795_f += -f * 0.2F;
      (this.rightArm.finger[2]).field_78795_f += f * 0.2F;
      for (int i = 0; i < this.rightArm.finger.length; i++)
        (this.rightArm.finger[i]).field_78808_h += f * 0.6F; 
      this.rightArm.thumb.field_78808_h += -f * 0.4F;
    } else if (armID == 2) {
      this.leftArm.arm.field_78808_h += -f * 0.8F;
      this.leftArm.forearm.field_78808_h += -f * 0.6F;
      this.leftArm.hand.field_78796_g += -f * 0.8F;
      (this.leftArm.finger[0]).field_78795_f += -f * 0.2F;
      (this.leftArm.finger[2]).field_78795_f += f * 0.2F;
      for (int i = 0; i < this.leftArm.finger.length; i++)
        (this.leftArm.finger[i]).field_78808_h += -f * 0.6F; 
      this.leftArm.thumb.field_78808_h += f * 0.4F;
    } else if (armID == 3) {
      this.lowerRightArm.arm.field_78808_h += f * 0.5F;
      this.lowerRightArm.forearm.field_78808_h += f * 0.4F;
      this.lowerRightArm.hand.field_78796_g += f * 0.4F;
      (this.lowerRightArm.finger[0]).field_78795_f += -f * 0.2F;
      (this.lowerRightArm.finger[2]).field_78795_f += f * 0.2F;
      for (int i = 0; i < this.lowerRightArm.finger.length; i++)
        (this.lowerRightArm.finger[i]).field_78808_h += f * 0.6F; 
      this.lowerRightArm.thumb.field_78808_h += -f * 0.4F;
    } else if (armID == 4) {
      this.lowerLeftArm.arm.field_78808_h += -f * 0.5F;
      this.lowerLeftArm.forearm.field_78808_h += -f * 0.4F;
      this.lowerLeftArm.hand.field_78796_g += -f * 0.4F;
      (this.lowerLeftArm.finger[0]).field_78795_f += -f * 0.2F;
      (this.lowerLeftArm.finger[2]).field_78795_f += f * 0.2F;
      for (int i = 0; i < this.lowerLeftArm.finger.length; i++)
        (this.lowerLeftArm.finger[i]).field_78808_h += -f * 0.6F; 
      this.lowerLeftArm.thumb.field_78808_h += f * 0.4F;
    } 
  }
  
  private void animateMelee(int fullTick, int armID) {
    int right = (armID == 1) ? 1 : -1;
    Arm arm = getArmFromID(armID);
    if (fullTick < 2) {
      float tick = (fullTick + this.partialTick) / 2.0F;
      float f = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F);
      arm.arm.field_78795_f += f * 0.2F;
      (arm.finger[0]).field_78808_h += f * 0.3F * right;
      (arm.finger[1]).field_78808_h += f * 0.3F * right;
      (arm.finger[2]).field_78808_h += f * 0.3F * right;
      (arm.foreFinger[0]).field_78808_h += -f * 0.5F * right;
      (arm.foreFinger[1]).field_78808_h += -f * 0.5F * right;
      (arm.foreFinger[2]).field_78808_h += -f * 0.5F * right;
    } else if (fullTick < 5) {
      float tick = ((fullTick - 2) + this.partialTick) / 3.0F;
      float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      float f1 = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F);
      this.chest.field_78796_g += -f1 * 0.1F * right;
      arm.arm.field_78795_f += f * 1.1F - 1.1F;
      arm.forearm.field_78795_f += -f * 0.4F;
      (arm.finger[0]).field_78808_h += 0.3F * right;
      (arm.finger[1]).field_78808_h += 0.3F * right;
      (arm.finger[2]).field_78808_h += 0.3F * right;
      (arm.foreFinger[0]).field_78808_h += -0.5F * right;
      (arm.foreFinger[1]).field_78808_h += -0.5F * right;
      (arm.foreFinger[2]).field_78808_h += -0.5F * right;
    } else if (fullTick < 6) {
      this.chest.field_78796_g += -0.1F * right;
      arm.arm.field_78795_f += -1.1F;
      arm.forearm.field_78795_f += -0.4F;
      (arm.finger[0]).field_78808_h += 0.3F * right;
      (arm.finger[1]).field_78808_h += 0.3F * right;
      (arm.finger[2]).field_78808_h += 0.3F * right;
      (arm.foreFinger[0]).field_78808_h += -0.5F * right;
      (arm.foreFinger[1]).field_78808_h += -0.5F * right;
      (arm.foreFinger[2]).field_78808_h += -0.5F * right;
    } else if (fullTick < 10) {
      float tick = ((fullTick - 6) + this.partialTick) / 4.0F;
      float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      this.chest.field_78796_g += -f * 0.1F * right;
      arm.arm.field_78795_f += -f * 1.1F;
      arm.forearm.field_78795_f += -f * 0.4F;
      (arm.finger[0]).field_78808_h += f * 0.3F * right;
      (arm.finger[1]).field_78808_h += f * 0.3F * right;
      (arm.finger[2]).field_78808_h += f * 0.3F * right;
      (arm.foreFinger[0]).field_78808_h += -f * 0.5F * right;
      (arm.foreFinger[1]).field_78808_h += -f * 0.5F * right;
      (arm.foreFinger[2]).field_78808_h += -f * 0.5F * right;
    } 
  }
  
  private void animateThrowBlock(int fullTick, int armID) {
    if (armID == 1) {
      if (fullTick < 4) {
        float tick = (fullTick + this.partialTick) / 4.0F;
        float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
        float f1 = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F);
        this.rightArm.arm.field_78795_f += -f1 * 1.5F;
        this.rightArm.arm.field_78808_h += f * 0.8F;
        this.rightArm.forearm.field_78808_h += f * 0.6F;
        this.rightArm.hand.field_78796_g += f * 0.8F;
        (this.rightArm.finger[0]).field_78795_f += -f * 0.2F;
        (this.rightArm.finger[2]).field_78795_f += f * 0.2F;
        for (int i = 0; i < this.rightArm.finger.length; i++)
          (this.rightArm.finger[i]).field_78808_h += f * 0.6F; 
        this.rightArm.thumb.field_78808_h += -f * 0.4F;
      } else if (fullTick < 7) {
        this.rightArm.arm.field_78795_f += -1.5F;
      } else if (fullTick < 14) {
        float tick = ((fullTick - 7) + this.partialTick) / 7.0F;
        float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
        this.rightArm.arm.field_78795_f += -f * 1.5F;
      } 
    } else if (armID == 2) {
      if (fullTick < 4) {
        float tick = (fullTick + this.partialTick) / 4.0F;
        float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
        float f1 = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F);
        this.leftArm.arm.field_78795_f += -f1 * 1.5F;
        this.leftArm.arm.field_78808_h += -f * 0.8F;
        this.leftArm.forearm.field_78808_h += -f * 0.6F;
        this.leftArm.hand.field_78796_g += -f * 0.8F;
        (this.leftArm.finger[0]).field_78795_f += -f * 0.2F;
        (this.leftArm.finger[2]).field_78795_f += f * 0.2F;
        for (int i = 0; i < this.leftArm.finger.length; i++)
          (this.leftArm.finger[i]).field_78808_h += -f * 0.6F; 
        this.leftArm.thumb.field_78808_h += f * 0.4F;
      } else if (fullTick < 7) {
        this.leftArm.arm.field_78795_f += -1.5F;
      } else if (fullTick < 14) {
        float tick = ((fullTick - 7) + this.partialTick) / 7.0F;
        float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
        this.leftArm.arm.field_78795_f += -f * 1.5F;
      } 
    } else if (armID == 3) {
      if (fullTick < 4) {
        float tick = (fullTick + this.partialTick) / 4.0F;
        float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
        float f1 = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F);
        this.lowerRightArm.arm.field_78795_f += -f1 * 1.5F;
        this.lowerRightArm.arm.field_78808_h += f * 0.5F;
        this.lowerRightArm.forearm.field_78808_h += f * 0.4F;
        this.lowerRightArm.hand.field_78796_g += f * 0.4F;
        (this.lowerRightArm.finger[0]).field_78795_f += -f * 0.2F;
        (this.lowerRightArm.finger[2]).field_78795_f += f * 0.2F;
        for (int i = 0; i < this.lowerRightArm.finger.length; i++)
          (this.lowerRightArm.finger[i]).field_78808_h += f * 0.6F; 
        this.lowerRightArm.thumb.field_78808_h += -f * 0.4F;
      } else if (fullTick < 7) {
        this.lowerRightArm.arm.field_78795_f += -1.5F;
      } else if (fullTick < 14) {
        float tick = ((fullTick - 7) + this.partialTick) / 7.0F;
        float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
        this.lowerRightArm.arm.field_78795_f += -f * 1.5F;
      } 
    } else if (armID == 4) {
      if (fullTick < 4) {
        float tick = (fullTick + this.partialTick) / 4.0F;
        float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
        float f1 = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F);
        this.lowerLeftArm.arm.field_78795_f += -f1 * 1.5F;
        this.lowerLeftArm.arm.field_78808_h += -f * 0.5F;
        this.lowerLeftArm.forearm.field_78808_h += -f * 0.4F;
        this.lowerLeftArm.hand.field_78796_g += -f * 0.4F;
        (this.lowerLeftArm.finger[0]).field_78795_f += -f * 0.2F;
        (this.lowerLeftArm.finger[2]).field_78795_f += f * 0.2F;
        for (int i = 0; i < this.lowerLeftArm.finger.length; i++)
          (this.lowerLeftArm.finger[i]).field_78808_h += -f * 0.6F; 
        this.lowerLeftArm.thumb.field_78808_h += f * 0.4F;
      } else if (fullTick < 7) {
        this.lowerLeftArm.arm.field_78795_f += -1.5F;
      } else if (fullTick < 14) {
        float tick = ((fullTick - 7) + this.partialTick) / 7.0F;
        float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
        this.lowerLeftArm.arm.field_78795_f += -f * 1.5F;
      } 
    } 
  }
  
  private void animateScream(int fullTick) {
    if (fullTick < 35) {
      float tick = (fullTick + this.partialTick) / 35.0F;
      float f = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F);
      this.abdomen.field_78795_f += f * 0.3F;
      this.chest.field_78795_f += f * 0.4F;
      this.neck.field_78795_f += f * 0.2F;
      this.head.field_78795_f += f * 0.3F;
      this.rightArm.arm.field_78795_f += -f * 0.6F;
      this.rightArm.arm.field_78796_g += f * 0.4F;
      this.rightArm.forearm.field_78795_f += -f * 0.8F;
      this.rightArm.hand.field_78808_h += -f * 0.4F;
      int i;
      for (i = 0; i < 3; i++) {
        (this.rightArm.finger[i]).field_78808_h += f * 0.3F;
        (this.rightArm.foreFinger[i]).field_78808_h += -f * 0.5F;
      } 
      this.leftArm.arm.field_78795_f += -f * 0.6F;
      this.leftArm.arm.field_78796_g += -f * 0.4F;
      this.leftArm.forearm.field_78795_f += -f * 0.8F;
      this.leftArm.hand.field_78808_h += f * 0.4F;
      for (i = 0; i < 3; i++) {
        (this.leftArm.finger[i]).field_78808_h += -f * 0.3F;
        (this.leftArm.foreFinger[i]).field_78808_h += f * 0.5F;
      } 
      this.lowerRightArm.arm.field_78795_f += -f * 0.4F;
      this.lowerRightArm.arm.field_78796_g += f * 0.2F;
      this.lowerRightArm.forearm.field_78795_f += -f * 0.8F;
      this.lowerRightArm.hand.field_78808_h += -f * 0.4F;
      for (i = 0; i < 3; i++) {
        (this.lowerRightArm.finger[i]).field_78808_h += f * 0.3F;
        (this.lowerRightArm.foreFinger[i]).field_78808_h += -f * 0.5F;
      } 
      this.lowerLeftArm.arm.field_78795_f += -f * 0.4F;
      this.lowerLeftArm.arm.field_78796_g += -f * 0.2F;
      this.lowerLeftArm.forearm.field_78795_f += -f * 0.8F;
      this.lowerLeftArm.hand.field_78808_h += f * 0.4F;
      for (i = 0; i < 3; i++) {
        (this.lowerLeftArm.finger[i]).field_78808_h += -f * 0.3F;
        (this.lowerLeftArm.foreFinger[i]).field_78808_h += f * 0.5F;
      } 
    } else if (fullTick < 40) {
      this.abdomen.field_78795_f += 0.3F;
      this.chest.field_78795_f += 0.4F;
      this.neck.field_78795_f += 0.2F;
      this.head.field_78795_f += 0.3F;
      this.rightArm.arm.field_78795_f += -0.6F;
      this.rightArm.arm.field_78796_g += 0.4F;
      this.rightArm.forearm.field_78795_f += -0.8F;
      this.rightArm.hand.field_78808_h += -0.4F;
      int i;
      for (i = 0; i < 3; i++) {
        (this.rightArm.finger[i]).field_78808_h += 0.3F;
        (this.rightArm.foreFinger[i]).field_78808_h += -0.5F;
      } 
      this.leftArm.arm.field_78795_f += -0.6F;
      this.leftArm.arm.field_78796_g += -0.4F;
      this.leftArm.forearm.field_78795_f += -0.8F;
      this.leftArm.hand.field_78808_h += 0.4F;
      for (i = 0; i < 3; i++) {
        (this.leftArm.finger[i]).field_78808_h += -0.3F;
        (this.leftArm.foreFinger[i]).field_78808_h += 0.5F;
      } 
      this.lowerRightArm.arm.field_78795_f += -0.4F;
      this.lowerRightArm.arm.field_78796_g += 0.2F;
      this.lowerRightArm.forearm.field_78795_f += -0.8F;
      this.lowerRightArm.hand.field_78808_h += -0.4F;
      for (i = 0; i < 3; i++) {
        (this.lowerRightArm.finger[i]).field_78808_h += 0.3F;
        (this.lowerRightArm.foreFinger[i]).field_78808_h += -0.5F;
      } 
      this.lowerLeftArm.arm.field_78795_f += -0.4F;
      this.lowerLeftArm.arm.field_78796_g += -0.2F;
      this.lowerLeftArm.forearm.field_78795_f += -0.8F;
      this.lowerLeftArm.hand.field_78808_h += 0.4F;
      for (i = 0; i < 3; i++) {
        (this.lowerLeftArm.finger[i]).field_78808_h += -0.3F;
        (this.lowerLeftArm.foreFinger[i]).field_78808_h += 0.5F;
      } 
    } else if (fullTick < 44) {
      float tick = ((fullTick - 40) + this.partialTick) / 4.0F;
      float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      float f1 = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F);
      this.abdomen.field_78795_f += -f * 0.1F + 0.1F;
      this.chest.field_78795_f += f * 0.1F + 0.1F;
      this.chest.field_78808_h += f1 * 0.5F;
      this.neck.field_78795_f += f * 0.2F;
      this.neck.field_78808_h += f1 * 0.2F;
      this.head.field_78795_f += f * 1.2F - 0.8F;
      this.head.field_78808_h += f1 * 0.4F;
      this.mouth.field_78795_f += f1 * 0.6F;
      this.rightArm.arm.field_78795_f += -f * 0.6F;
      this.rightArm.arm.field_78796_g += 0.4F;
      this.rightArm.forearm.field_78795_f += -f * 0.8F;
      this.rightArm.hand.field_78808_h += -f * 0.4F;
      int i;
      for (i = 0; i < 3; i++) {
        (this.rightArm.finger[i]).field_78808_h += f * 0.3F;
        (this.rightArm.foreFinger[i]).field_78808_h += -f * 0.5F;
      } 
      this.leftArm.arm.field_78795_f += -f * 0.6F;
      this.leftArm.arm.field_78796_g += -0.4F;
      this.leftArm.forearm.field_78795_f += -f * 0.8F;
      this.leftArm.hand.field_78808_h += f * 0.4F;
      for (i = 0; i < 3; i++) {
        (this.leftArm.finger[i]).field_78808_h += -f * 0.3F;
        (this.leftArm.foreFinger[i]).field_78808_h += f * 0.5F;
      } 
      this.lowerRightArm.arm.field_78795_f += -f * 0.4F;
      this.lowerRightArm.arm.field_78796_g += -f * 0.1F + 0.3F;
      this.lowerRightArm.forearm.field_78795_f += -f * 0.8F;
      this.lowerRightArm.hand.field_78808_h += -f * 0.4F;
      for (i = 0; i < 3; i++) {
        (this.lowerRightArm.finger[i]).field_78808_h += f * 0.3F;
        (this.lowerRightArm.foreFinger[i]).field_78808_h += -f * 0.5F;
      } 
      this.lowerLeftArm.arm.field_78795_f += -f * 0.4F;
      this.lowerLeftArm.arm.field_78796_g += f * 0.1F - 0.3F;
      this.lowerLeftArm.forearm.field_78795_f += -f * 0.8F;
      this.lowerLeftArm.hand.field_78808_h += f * 0.4F;
      for (i = 0; i < 3; i++) {
        (this.lowerLeftArm.finger[i]).field_78808_h += -f * 0.3F;
        (this.lowerLeftArm.foreFinger[i]).field_78808_h += f * 0.5F;
      } 
      this.leg1.field_78808_h += f1 * 0.1F;
      this.leg2.field_78808_h += -f1 * 0.1F;
    } else if (fullTick < 155) {
      float tick = ((fullTick - 44) + this.partialTick) / 111.0F;
      float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      this.abdomen.field_78795_f += 0.3F;
      this.chest.field_78795_f -= 0.2F;
      this.chest.field_78808_h += f * 1.0F - 0.5F;
      this.neck.field_78808_h += f * 0.4F - 0.2F;
      this.head.field_78795_f += -0.8F;
      this.head.field_78808_h += f * 0.8F - 0.4F;
      this.mouth.field_78795_f += 0.6F;
      this.rightArm.arm.field_78796_g += 0.4F;
      this.leftArm.arm.field_78796_g += -0.4F;
      this.lowerRightArm.arm.field_78796_g += 0.3F;
      this.lowerLeftArm.arm.field_78796_g += -0.3F;
      this.leg1.field_78808_h += 0.1F;
      this.leg2.field_78808_h += -0.1F;
    } else if (fullTick < 160) {
      float tick = ((fullTick - 155) + this.partialTick) / 5.0F;
      float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      this.abdomen.field_78795_f += f * 0.3F;
      this.chest.field_78795_f -= f * 0.2F;
      this.chest.field_78808_h += -f * 0.5F;
      this.neck.field_78808_h += -f * 0.2F;
      this.head.field_78795_f += -f * 0.8F;
      this.head.field_78808_h += -f * 0.4F;
      this.mouth.field_78795_f += f * 0.6F;
      this.rightArm.arm.field_78796_g += f * 0.4F;
      this.leftArm.arm.field_78796_g += -f * 0.4F;
      this.lowerRightArm.arm.field_78796_g += f * 0.3F;
      this.lowerLeftArm.arm.field_78796_g += -f * 0.3F;
      this.leg1.field_78808_h += f * 0.1F;
      this.leg2.field_78808_h += -f * 0.1F;
    } 
  }
  
  private void animateTeleSmash(int fullTick) {
    if (fullTick < 18) {
      float tick = (fullTick + this.partialTick) / 18.0F;
      float f = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F);
      this.chest.field_78795_f += -f * 0.3F;
      this.rightArm.arm.field_78796_g += f * 0.2F;
      this.rightArm.arm.field_78808_h += f * 0.8F;
      this.rightArm.hand.field_78796_g += f * 1.7F;
      this.leftArm.arm.field_78796_g += -f * 0.2F;
      this.leftArm.arm.field_78808_h += -f * 0.8F;
      this.leftArm.hand.field_78796_g += -f * 1.7F;
      this.lowerRightArm.arm.field_78796_g += f * 0.2F;
      this.lowerRightArm.arm.field_78808_h += f * 0.6F;
      this.lowerRightArm.hand.field_78796_g += f * 1.7F;
      this.lowerLeftArm.arm.field_78796_g += -f * 0.2F;
      this.lowerLeftArm.arm.field_78808_h += -f * 0.6F;
      this.lowerLeftArm.hand.field_78796_g += -f * 1.7F;
    } else if (fullTick < 20) {
      float tick = ((fullTick - 18) + this.partialTick) / 2.0F;
      float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      float f1 = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F);
      this.chest.field_78795_f += -f * 0.3F;
      this.rightArm.arm.field_78795_f += -f1 * 0.8F;
      this.rightArm.arm.field_78796_g += 0.2F;
      this.rightArm.arm.field_78808_h += 0.8F;
      this.rightArm.hand.field_78796_g++;
      this.leftArm.arm.field_78795_f += -f1 * 0.8F;
      this.leftArm.arm.field_78796_g += -0.2F;
      this.leftArm.arm.field_78808_h += -0.8F;
      this.leftArm.hand.field_78796_g += -1.7F;
      this.lowerRightArm.arm.field_78795_f += -f1 * 0.9F;
      this.lowerRightArm.arm.field_78796_g += 0.2F;
      this.lowerRightArm.arm.field_78808_h += 0.6F;
      this.lowerRightArm.hand.field_78796_g++;
      this.lowerLeftArm.arm.field_78795_f += -f1 * 0.9F;
      this.lowerLeftArm.arm.field_78796_g += -0.2F;
      this.lowerLeftArm.arm.field_78808_h += -0.6F;
      this.lowerLeftArm.hand.field_78796_g += -1.7F;
    } else if (fullTick < 24) {
      this.rightArm.arm.field_78795_f += -0.8F;
      this.rightArm.arm.field_78796_g += 0.2F;
      this.rightArm.arm.field_78808_h += 0.8F;
      this.rightArm.hand.field_78796_g++;
      this.leftArm.arm.field_78795_f += -0.8F;
      this.leftArm.arm.field_78796_g += -0.2F;
      this.leftArm.arm.field_78808_h += -0.8F;
      this.leftArm.hand.field_78796_g += -1.7F;
      this.lowerRightArm.arm.field_78795_f += -0.9F;
      this.lowerRightArm.arm.field_78796_g += 0.2F;
      this.lowerRightArm.arm.field_78808_h += 0.6F;
      this.lowerRightArm.hand.field_78796_g++;
      this.lowerLeftArm.arm.field_78795_f += -0.9F;
      this.lowerLeftArm.arm.field_78796_g += -0.2F;
      this.lowerLeftArm.arm.field_78808_h += -0.6F;
      this.lowerLeftArm.hand.field_78796_g += -1.7F;
    } else if (fullTick < 30) {
      float tick = ((fullTick - 24) + this.partialTick) / 6.0F;
      float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      this.rightArm.arm.field_78795_f += -f * 0.8F;
      this.rightArm.arm.field_78796_g += f * 0.2F;
      this.rightArm.arm.field_78808_h += f * 0.8F;
      this.rightArm.hand.field_78796_g += f * 1.7F;
      this.leftArm.arm.field_78795_f += -f * 0.8F;
      this.leftArm.arm.field_78796_g += -f * 0.2F;
      this.leftArm.arm.field_78808_h += -f * 0.8F;
      this.leftArm.hand.field_78796_g += -f * 1.7F;
      this.lowerRightArm.arm.field_78795_f += -f * 0.9F;
      this.lowerRightArm.arm.field_78796_g += f * 0.2F;
      this.lowerRightArm.arm.field_78808_h += f * 0.6F;
      this.lowerRightArm.hand.field_78796_g += f * 1.7F;
      this.lowerLeftArm.arm.field_78795_f += -f * 0.9F;
      this.lowerLeftArm.arm.field_78796_g += -f * 0.2F;
      this.lowerLeftArm.arm.field_78808_h += -f * 0.6F;
      this.lowerLeftArm.hand.field_78796_g += -f * 1.7F;
    } 
  }
  
  private void animateDeath(int deathTick) {
    if (deathTick < 80) {
      float tick = (deathTick + this.partialTick) / 80.0F;
      float f = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F);
      this.head.field_78795_f += f * 0.4F;
      this.neck.field_78795_f += f * 0.3F;
      this.pelvis.field_78797_d += -f * 12.0F;
      this.rightArm.arm.field_78795_f += -f * 0.4F;
      this.rightArm.arm.field_78796_g += f * 0.4F;
      this.rightArm.arm.field_78808_h += f * 0.6F;
      this.rightArm.forearm.field_78795_f += -f * 1.2F;
      this.leftArm.arm.field_78795_f += -f * 0.4F;
      this.leftArm.arm.field_78796_g += -f * 0.2F;
      this.leftArm.arm.field_78808_h += -f * 0.6F;
      this.leftArm.forearm.field_78795_f += -f * 1.2F;
      this.lowerRightArm.arm.field_78795_f += -f * 0.4F;
      this.lowerRightArm.arm.field_78796_g += f * 0.4F;
      this.lowerRightArm.arm.field_78808_h += f * 0.6F;
      this.lowerRightArm.forearm.field_78795_f += -f * 1.2F;
      this.lowerLeftArm.arm.field_78795_f += -f * 0.4F;
      this.lowerLeftArm.arm.field_78796_g += -f * 0.2F;
      this.lowerLeftArm.arm.field_78808_h += -f * 0.6F;
      this.lowerLeftArm.forearm.field_78795_f += -f * 1.2F;
      this.leg1.field_78795_f += -f * 0.9F;
      this.leg1.field_78796_g += f * 0.3F;
      this.leg2.field_78795_f += -f * 0.9F;
      this.leg2.field_78796_g += -f * 0.3F;
      this.foreleg1.field_78795_f += f * 1.6F;
      this.foreleg2.field_78795_f += f * 1.6F;
    } else if (deathTick < 84) {
      float tick = ((deathTick - 80) + this.partialTick) / 4.0F;
      float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      float f1 = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F);
      this.head.field_78795_f += f * 0.4F;
      this.neck.field_78795_f += f * 0.4F - 0.1F;
      this.chest.field_78795_f += -f1 * 0.8F;
      this.abdomen.field_78795_f += -f1 * 0.2F;
      this.pelvis.field_78797_d += -12.0F;
      this.rightArm.arm.field_78795_f += -f * 0.4F;
      this.rightArm.arm.field_78796_g += -f * 1.4F + 1.8F;
      this.rightArm.arm.field_78808_h += f * 0.6F;
      this.rightArm.forearm.field_78795_f += -f * 1.2F;
      this.leftArm.arm.field_78795_f += -f * 0.4F;
      this.leftArm.arm.field_78796_g += f * 1.6F - 1.8F;
      this.leftArm.arm.field_78808_h += -f * 0.6F;
      this.leftArm.forearm.field_78795_f += -f * 1.2F;
      this.lowerRightArm.arm.field_78795_f += -f * 0.5F + 0.1F;
      this.lowerRightArm.arm.field_78796_g += -f * 1.1F + 1.5F;
      this.lowerRightArm.arm.field_78808_h += f * 0.6F;
      this.lowerRightArm.forearm.field_78795_f += -f * 1.2F;
      this.lowerLeftArm.arm.field_78795_f += -f * 0.5F + 0.1F;
      this.lowerLeftArm.arm.field_78796_g += f * 1.1F - 1.5F;
      this.lowerLeftArm.arm.field_78808_h += -f * 0.6F;
      this.lowerLeftArm.forearm.field_78795_f += -f * 1.2F;
      this.leg1.field_78795_f += -f * 1.7F + 0.8F;
      this.leg1.field_78796_g += f * 0.3F;
      this.leg1.field_78808_h += f1 * 0.2F;
      this.leg2.field_78795_f += -f * 1.7F + 0.8F;
      this.leg2.field_78796_g += -f * 0.3F;
      this.leg2.field_78808_h += -f1 * 0.2F;
      this.foreleg1.field_78795_f += f * 1.6F;
      this.foreleg2.field_78795_f += f * 1.6F;
    } else {
      this.neck.field_78795_f += -0.1F;
      this.chest.field_78795_f += -0.8F;
      this.abdomen.field_78795_f += -0.2F;
      this.pelvis.field_78797_d += -12.0F;
      this.rightArm.arm.field_78796_g++;
      this.leftArm.arm.field_78796_g += -1.8F;
      this.lowerRightArm.arm.field_78795_f += 0.1F;
      this.lowerRightArm.arm.field_78796_g++;
      this.lowerLeftArm.arm.field_78795_f += 0.1F;
      this.lowerLeftArm.arm.field_78796_g += -1.5F;
      this.leg1.field_78795_f += 0.8F;
      this.leg1.field_78808_h += 0.2F;
      this.leg2.field_78795_f += 0.8F;
      this.leg2.field_78808_h += -0.2F;
    } 
  }
  
  public Arm getArmFromID(int armID) {
    return (armID == 1) ? this.rightArm : ((armID == 2) ? this.leftArm : ((armID == 3) ? this.lowerRightArm : this.lowerLeftArm));
  }
  
  public void postRenderArm(float scale, int armID) {
    this.pelvis.func_78794_c(scale);
    this.abdomen.func_78794_c(scale);
    this.chest.func_78794_c(scale);
    getArmFromID(armID).postRender(scale);
  }
  
  public void func_78086_a(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
    this.partialTick = partialTickTime;
  }
  
  @SideOnly(Side.CLIENT)
  static class Arm {
    private final ScalableModelRenderer arm;
    
    private final ModelRenderer forearm;
    
    private final ModelRenderer hand;
    
    private final ModelRenderer[] finger;
    
    private final ModelRenderer[] foreFinger;
    
    private final ModelRenderer thumb;
    
    private final boolean right;
    
    public Arm(ModelBase model, ModelRenderer connect, boolean right) {
      this.right = right;
      this.finger = new ModelRenderer[3];
      this.foreFinger = new ModelRenderer[3];
      this.arm = new ScalableModelRenderer(model, 92, 0);
      this.arm.field_78809_i = !this.right;
      this.arm.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 22, 3, 0.1F);
      this.arm.func_78793_a(this.right ? -4.0F : 4.0F, -14.0F, 0.0F);
      connect.func_78792_a(this.arm);
      this.forearm = new ModelRenderer(model, 104, 0);
      this.forearm.field_78809_i = !this.right;
      this.forearm.func_78789_a(-1.5F, 0.0F, -1.5F, 3, 18, 3);
      this.forearm.func_78793_a(0.0F, 21.0F, 1.0F);
      this.arm.func_78792_a(this.forearm);
      this.hand = new ModelRenderer(model);
      this.hand.func_78793_a(0.0F, 17.5F, 0.0F);
      this.forearm.func_78792_a(this.hand);
      float fingerScale = 0.6F;
      int i;
      for (i = 0; i < this.finger.length; i++) {
        this.finger[i] = new ModelRenderer(model, 76, 0);
        (this.finger[i]).field_78809_i = !this.right;
        this.finger[i].func_78790_a(-0.5F, 0.0F, -0.5F, 1, (i == 1) ? 6 : 5, 1, fingerScale);
      } 
      this.finger[0].func_78793_a(this.right ? -0.5F : 0.5F, 0.0F, -1.0F);
      this.finger[1].func_78793_a(this.right ? -0.5F : 0.5F, 0.0F, 0.0F);
      this.finger[2].func_78793_a(this.right ? -0.5F : 0.5F, 0.0F, 1.0F);
      for (i = 0; i < this.foreFinger.length; i++) {
        this.foreFinger[i] = new ModelRenderer(model, 76, 0);
        (this.foreFinger[i]).field_78809_i = !this.right;
        this.foreFinger[i].func_78790_a(-0.5F, 0.0F, -0.5F, 1, (i == 1) ? 6 : 5, 1, fingerScale - 0.01F);
        this.foreFinger[i].func_78793_a(0.0F, 0.5F + ((i == 1) ? 6 : 5), 0.0F);
      } 
      for (i = 0; i < this.finger.length; i++) {
        this.hand.func_78792_a(this.finger[i]);
        this.finger[i].func_78792_a(this.foreFinger[i]);
      } 
      this.thumb = new ModelRenderer(model, 76, 0);
      this.thumb.field_78809_i = this.right;
      this.thumb.func_78790_a(-0.5F, 0.0F, -0.5F, 1, 5, 1, fingerScale);
      this.thumb.func_78793_a(this.right ? 0.5F : -0.5F, 0.0F, -0.5F);
      this.hand.func_78792_a(this.thumb);
    }
    
    private void setAngles() {
      ModelMutantEnderman.resetAngles(this.arm);
      ModelMutantEnderman.resetAngles(this.forearm);
      ModelMutantEnderman.resetAngles(this.hand);
      for (int i = 0; i < this.finger.length; i++) {
        ModelMutantEnderman.resetAngles(this.finger[i]);
        ModelMutantEnderman.resetAngles(this.foreFinger[i]);
      } 
      ModelMutantEnderman.resetAngles(this.thumb);
      if (this.right) {
        this.arm.field_78795_f = -0.5235988F;
        this.arm.field_78808_h = 0.5235988F;
        this.forearm.field_78795_f = -0.62831855F;
        this.hand.field_78796_g = -0.3926991F;
        (this.finger[0]).field_78795_f = -0.2617994F;
        (this.finger[1]).field_78808_h = 0.17453294F;
        (this.finger[2]).field_78795_f = 0.2617994F;
        (this.foreFinger[0]).field_78808_h = -0.2617994F;
        (this.foreFinger[1]).field_78808_h = -0.3926991F;
        (this.foreFinger[2]).field_78808_h = -0.2617994F;
        this.thumb.field_78795_f = -0.62831855F;
        this.thumb.field_78808_h = -0.3926991F;
      } else {
        this.arm.field_78795_f = -0.5235988F;
        this.arm.field_78808_h = -0.5235988F;
        this.forearm.field_78795_f = -0.62831855F;
        this.hand.field_78796_g = 0.3926991F;
        (this.finger[0]).field_78795_f = -0.2617994F;
        (this.finger[1]).field_78808_h = -0.17453294F;
        (this.finger[2]).field_78795_f = 0.2617994F;
        (this.foreFinger[0]).field_78808_h = 0.2617994F;
        (this.foreFinger[1]).field_78808_h = 0.3926991F;
        (this.foreFinger[2]).field_78808_h = 0.2617994F;
        this.thumb.field_78795_f = -0.62831855F;
        this.thumb.field_78808_h = 0.3926991F;
      } 
    }
    
    private void postRender(float scale) {
      this.arm.func_78794_c(scale);
      this.forearm.func_78794_c(scale);
      this.hand.func_78794_c(scale);
    }
  }
  
  public static void resetAngles(ModelRenderer model) {
    model.field_78795_f = 0.0F;
    model.field_78796_g = 0.0F;
    model.field_78808_h = 0.0F;
  }
}
