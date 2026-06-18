package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.model;

import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantZombie;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelMutantZombie extends ModelBase {
  private final ModelRenderer pelvis;
  
  private final ModelRenderer waist;
  
  private final ModelRenderer chest;
  
  private final ModelRenderer head;
  
  private final ModelRenderer arm1;
  
  private final ModelRenderer arm2;
  
  private final ModelRenderer forearm1;
  
  private final ModelRenderer forearm2;
  
  private final ModelRenderer leg1;
  
  private final ModelRenderer leg2;
  
  private final ModelRenderer foreleg1;
  
  private final ModelRenderer foreleg2;
  
  private float partialTick;
  
  public ModelMutantZombie() {
    this.field_78090_t = 128;
    this.field_78089_u = 128;
    this.pelvis = new ModelRenderer(this);
    this.pelvis.func_78793_a(0.0F, 10.0F, 6.0F);
    this.waist = new ModelRenderer(this, 0, 44);
    this.waist.func_78789_a(-7.0F, -16.0F, -6.0F, 14, 16, 12);
    this.pelvis.func_78792_a(this.waist);
    this.chest = new ModelRenderer(this, 0, 16);
    this.chest.func_78789_a(-12.0F, -12.0F, -8.0F, 24, 12, 16);
    this.chest.func_78793_a(0.0F, -12.0F, 0.0F);
    this.waist.func_78792_a(this.chest);
    this.head = new ModelRenderer(this, 0, 0);
    this.head.func_78789_a(-4.0F, -8.0F, -4.0F, 8, 8, 8);
    this.head.func_78793_a(0.0F, -11.0F, -4.0F);
    this.chest.func_78792_a(this.head);
    this.arm1 = new ModelRenderer(this, 104, 0);
    this.arm1.func_78789_a(-3.0F, 0.0F, -3.0F, 6, 16, 6);
    this.arm1.func_78793_a(-11.0F, -8.0F, 2.0F);
    this.chest.func_78792_a(this.arm1);
    this.arm2 = new ModelRenderer(this, 104, 0);
    this.arm2.field_78809_i = true;
    this.arm2.func_78789_a(-3.0F, 0.0F, -3.0F, 6, 16, 6);
    this.arm2.func_78793_a(11.0F, -8.0F, 2.0F);
    this.chest.func_78792_a(this.arm2);
    this.forearm1 = new ModelRenderer(this, 104, 22);
    this.forearm1.func_78790_a(-3.0F, 0.0F, -3.0F, 6, 16, 6, 0.1F);
    this.forearm1.func_78793_a(0.0F, 14.0F, 0.0F);
    this.arm1.func_78792_a(this.forearm1);
    this.forearm2 = new ModelRenderer(this, 104, 22);
    this.forearm2.field_78809_i = true;
    this.forearm2.func_78790_a(-3.0F, 0.0F, -3.0F, 6, 16, 6, 0.1F);
    this.forearm2.func_78793_a(0.0F, 14.0F, 0.0F);
    this.arm2.func_78792_a(this.forearm2);
    this.leg1 = new ModelRenderer(this, 80, 0);
    this.leg1.func_78789_a(-3.0F, 0.0F, -3.0F, 6, 11, 6);
    this.leg1.func_78793_a(-5.0F, -2.0F, 0.0F);
    this.pelvis.func_78792_a(this.leg1);
    this.leg2 = new ModelRenderer(this, 80, 0);
    this.leg2.field_78809_i = true;
    this.leg2.func_78789_a(-3.0F, 0.0F, -3.0F, 6, 11, 6);
    this.leg2.func_78793_a(5.0F, -2.0F, 0.0F);
    this.pelvis.func_78792_a(this.leg2);
    this.foreleg1 = new ModelRenderer(this, 80, 17);
    this.foreleg1.func_78790_a(-3.0F, 0.0F, -3.0F, 6, 8, 6, 0.1F);
    this.foreleg1.func_78793_a(0.0F, 9.5F, 0.0F);
    this.leg1.func_78792_a(this.foreleg1);
    this.foreleg2 = new ModelRenderer(this, 80, 17);
    this.foreleg2.field_78809_i = true;
    this.foreleg2.func_78790_a(-3.0F, 0.0F, -3.0F, 6, 8, 6, 0.1F);
    this.foreleg2.func_78793_a(0.0F, 9.5F, 0.0F);
    this.leg2.func_78792_a(this.foreleg2);
  }
  
  public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    setAngles();
    animate((EntityMutantZombie)entity, f, f1, f2, f3, f4, f5);
    this.pelvis.func_78785_a(f5);
  }
  
  public void setAngles() {
    this.pelvis.field_78797_d = 10.0F;
    this.waist.field_78795_f = 0.19634955F;
    this.chest.field_78795_f = 0.5235988F;
    this.chest.field_78796_g = 0.0F;
    this.head.field_78795_f = -0.71994835F;
    this.head.field_78796_g = 0.0F;
    this.head.field_78808_h = 0.0F;
    this.arm1.field_78795_f = -0.32724923F;
    this.arm1.field_78796_g = 0.0F;
    this.arm1.field_78808_h = 0.3926991F;
    this.arm2.field_78795_f = -0.32724923F;
    this.arm2.field_78796_g = 0.0F;
    this.arm2.field_78808_h = -0.3926991F;
    this.forearm1.field_78795_f = -1.0471976F;
    this.forearm2.field_78795_f = -1.0471976F;
    this.leg1.field_78795_f = -0.7853982F;
    this.leg1.field_78796_g = 0.0F;
    this.leg1.field_78808_h = 0.0F;
    this.leg2.field_78795_f = -0.7853982F;
    this.leg2.field_78796_g = 0.0F;
    this.leg2.field_78808_h = 0.0F;
    this.foreleg1.field_78795_f = 0.7853982F;
    this.foreleg2.field_78795_f = 0.7853982F;
  }
  
  public void animate(EntityMutantZombie zombie, float f, float f1, float f2, float f3, float f4, float f5) {
    float walkAnim1 = (MathHelper.func_76126_a((f - 0.7F) * 0.4F) + 0.7F) * f1;
    float walkAnim2 = -(MathHelper.func_76126_a((f + 0.7F) * 0.4F) - 0.7F) * f1;
    float walkAnim = MathHelper.func_76126_a(f * 0.4F) * f1;
    float breatheAnim = MathHelper.func_76126_a(f2 * 0.1F);
    float faceYaw = f3 * 3.1415927F / 180.0F;
    float facePitch = f4 * 3.1415927F / 180.0F;
    if (zombie.field_70725_aQ <= 0) {
      if (zombie.getAttackID() == 4)
        animateMelee(zombie.getAttackTick()); 
      if (zombie.getAttackID() == 5) {
        animateThrow(zombie);
        float scale = 1.0F - MathHelper.func_76131_a(zombie.getAttackTick() / 3.0F, 0.0F, 1.0F);
        walkAnim1 *= scale;
        walkAnim2 *= scale;
        walkAnim *= scale;
        facePitch *= scale;
      } 
      if (zombie.getAttackID() == 6) {
        animateRoar(zombie.getAttackTick());
        float scale = 1.0F - MathHelper.func_76131_a(zombie.getAttackTick() / 6.0F, 0.0F, 1.0F);
        walkAnim1 *= scale;
        walkAnim2 *= scale;
        walkAnim *= scale;
        facePitch *= scale;
      } 
      if (zombie.getAttackID() == 7) {
        animateToss(zombie);
        float scale = 1.0F - MathHelper.func_76131_a(zombie.getAttackTick() / 3.0F, 0.0F, 1.0F);
        walkAnim1 *= scale;
        walkAnim2 *= scale;
        walkAnim *= scale;
        facePitch *= scale;
      } 
    } else {
      animateDeath(zombie);
      float scale = 1.0F - MathHelper.func_76131_a(zombie.field_70725_aQ / 6.0F, 0.0F, 1.0F);
      walkAnim1 *= scale;
      walkAnim2 *= scale;
      walkAnim *= scale;
      breatheAnim *= scale;
      faceYaw *= scale;
      facePitch *= scale;
    } 
    this.chest.field_78795_f += breatheAnim * 0.02F;
    this.arm1.field_78808_h -= breatheAnim * 0.05F;
    this.arm2.field_78808_h += breatheAnim * 0.05F;
    this.head.field_78795_f += facePitch * 0.6F;
    this.head.field_78796_g += faceYaw * 0.8F;
    this.head.field_78808_h -= faceYaw * 0.2F;
    this.chest.field_78795_f += facePitch * 0.4F;
    this.chest.field_78796_g += faceYaw * 0.2F;
    this.pelvis.field_78797_d += MathHelper.func_76126_a(f * 0.8F) * f1 * 0.5F;
    this.chest.field_78796_g -= walkAnim * 0.1F;
    this.arm1.field_78795_f -= walkAnim * 0.6F;
    this.arm2.field_78795_f += walkAnim * 0.6F;
    this.leg1.field_78795_f += walkAnim1 * 0.9F;
    this.leg2.field_78795_f += walkAnim2 * 0.9F;
  }
  
  protected void animateMelee(int fullTick) {
    this.arm1.field_78808_h = 0.0F;
    this.arm2.field_78808_h = 0.0F;
    if (fullTick < 8) {
      float tick = (fullTick + this.partialTick) / 8.0F;
      float f = -MathHelper.func_76126_a(tick * 3.1415927F / 2.0F);
      float f1 = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      this.waist.field_78795_f += f * 0.2F;
      this.chest.field_78795_f += f * 0.2F;
      this.arm1.field_78795_f += f * 2.3F;
      this.arm1.field_78808_h += f1 * 3.1415927F / 8.0F;
      this.arm2.field_78795_f += f * 2.3F;
      this.arm2.field_78808_h -= f1 * 3.1415927F / 8.0F;
      this.forearm1.field_78795_f += f * 0.8F;
      this.forearm2.field_78795_f += f * 0.8F;
    } else if (fullTick < 12) {
      float tick = ((fullTick - 8) + this.partialTick) / 4.0F;
      float f = -MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      float f1 = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F);
      this.waist.field_78795_f += f * 0.9F + 0.7F;
      this.chest.field_78795_f += f * 0.9F + 0.7F;
      this.arm1.field_78795_f += f * 0.2F - 2.1F;
      this.arm1.field_78808_h += f1 * 0.3F;
      this.arm2.field_78795_f += f * 0.2F - 2.1F;
      this.arm2.field_78808_h -= f1 * 0.3F;
      this.forearm1.field_78795_f += f * 1.0F + 0.2F;
      this.forearm2.field_78795_f += f * 1.0F + 0.2F;
    } else if (fullTick < 16) {
      this.waist.field_78795_f += 0.7F;
      this.chest.field_78795_f += 0.7F;
      this.arm1.field_78795_f -= 2.1F;
      this.arm1.field_78808_h += 0.3F;
      this.arm2.field_78795_f -= 2.1F;
      this.arm2.field_78808_h -= 0.3F;
      this.forearm1.field_78795_f += 0.2F;
      this.forearm2.field_78795_f += 0.2F;
    } else if (fullTick < 24) {
      float tick = ((fullTick - 16) + this.partialTick) / 8.0F;
      float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      this.waist.field_78795_f += f * 0.7F;
      this.chest.field_78795_f += f * 0.7F;
      this.arm1.field_78795_f -= f * 2.1F;
      this.arm1.field_78808_h += f * -0.09269908F + 0.3926991F;
      this.arm2.field_78795_f -= f * 2.1F;
      this.arm2.field_78808_h -= f * -0.09269908F + 0.3926991F;
      this.forearm1.field_78795_f += f * 0.2F;
      this.forearm2.field_78795_f += f * 0.2F;
    } else {
      this.arm1.field_78808_h += 0.3926991F;
      this.arm2.field_78808_h += -0.3926991F;
    } 
  }
  
  protected void animateRoar(int fullTick) {
    if (fullTick < 10) {
      float tick = (fullTick + this.partialTick) / 10.0F;
      float f = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F);
      float f1 = MathHelper.func_76126_a(tick * 3.1415927F * 3.1415927F / 8.0F);
      this.waist.field_78795_f += f * 0.2F;
      this.chest.field_78795_f += f * 0.4F;
      this.chest.field_78796_g += f1 * 0.06F;
      this.head.field_78795_f += f * 0.8F;
      this.arm1.field_78795_f -= f * 1.2F;
      this.arm1.field_78808_h += f * 0.6F;
      this.arm2.field_78795_f -= f * 1.2F;
      this.arm2.field_78808_h -= f * 0.6F;
      this.forearm1.field_78795_f -= f * 0.8F;
      this.forearm2.field_78795_f -= f * 0.8F;
    } else if (fullTick < 15) {
      float tick = ((fullTick - 10) + this.partialTick) / 5.0F;
      float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      float f1 = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F);
      this.waist.field_78795_f += f * 0.39634955F - 0.19634955F;
      this.chest.field_78795_f += f * 0.6F - 0.2F;
      this.head.field_78795_f += f * 1.0F - 0.2F;
      this.arm1.field_78795_f -= f * 2.2F - 1.0F;
      this.arm1.field_78796_g += f1 * 0.4F;
      this.arm1.field_78808_h += 0.6F;
      this.arm2.field_78795_f -= f * 2.2F - 1.0F;
      this.arm2.field_78796_g -= f1 * 0.4F;
      this.arm2.field_78808_h -= 0.6F;
      this.forearm1.field_78795_f -= f * 1.0F - 0.2F;
      this.forearm2.field_78795_f -= f * 1.0F - 0.2F;
      this.leg1.field_78796_g += f1 * 0.3F;
      this.leg2.field_78796_g -= f1 * 0.3F;
    } else if (fullTick < 75) {
      this.waist.field_78795_f -= 0.19634955F;
      this.chest.field_78795_f -= 0.2F;
      this.head.field_78795_f -= 0.2F;
      addRotation(this.arm1, 1.0F, 0.4F, 0.6F);
      addRotation(this.arm2, 1.0F, -0.4F, -0.6F);
      this.forearm1.field_78795_f += 0.2F;
      this.forearm2.field_78795_f += 0.2F;
      this.leg1.field_78796_g += 0.3F;
      this.leg2.field_78796_g -= 0.3F;
    } else if (fullTick < 90) {
      float tick = ((fullTick - 75) + this.partialTick) / 15.0F;
      float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      this.waist.field_78795_f -= f * 0.69634956F - 0.5F;
      this.chest.field_78795_f -= f * 0.7F - 0.5F;
      this.head.field_78795_f -= f * 0.6F - 0.4F;
      addRotation(this.arm1, f * 2.6F - 1.6F, f * 0.4F, f * 0.99269915F - 0.3926991F);
      addRotation(this.arm2, f * 2.6F - 1.6F, -f * 0.4F, -f * 0.99269915F + 0.3926991F);
      this.forearm1.field_78795_f += f * -0.6F + 0.8F;
      this.forearm2.field_78795_f += f * -0.6F + 0.8F;
      this.leg1.field_78796_g += f * 0.3F;
      this.leg2.field_78796_g -= f * 0.3F;
    } else if (fullTick < 110) {
      this.waist.field_78795_f += 0.5F;
      this.chest.field_78795_f += 0.5F;
      this.head.field_78795_f += 0.4F;
      addRotation(this.arm1, -1.6F, 0.0F, -0.3926991F);
      addRotation(this.arm2, -1.6F, 0.0F, 0.3926991F);
      this.forearm1.field_78795_f += 0.8F;
      this.forearm2.field_78795_f += 0.8F;
    } else {
      float tick = ((fullTick - 110) + this.partialTick) / 10.0F;
      float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      this.waist.field_78795_f += f * 0.5F;
      this.chest.field_78795_f += f * 0.5F;
      this.head.field_78795_f += f * 0.4F;
      addRotation(this.arm1, f * -1.6F, 0.0F, f * -3.1415927F / 8.0F);
      addRotation(this.arm2, f * -1.6F, 0.0F, f * 3.1415927F / 8.0F);
      this.forearm1.field_78795_f += f * 0.8F;
      this.forearm2.field_78795_f += f * 0.8F;
    } 
    if (fullTick >= 10 && fullTick < 75) {
      float tick = ((fullTick - 10) + this.partialTick) / 65.0F;
      float f = MathHelper.func_76126_a(tick * 3.1415927F * 8.0F);
      float f1 = MathHelper.func_76126_a(tick * 3.1415927F * 8.0F + 0.7853982F);
      this.head.field_78796_g += f * 0.5F - f1 * 0.2F;
      this.head.field_78808_h -= f * 0.5F;
      this.chest.field_78796_g += f1 * 0.06F;
    } 
  }
  
  protected void animateThrow(EntityMutantZombie zombie) {
    if (zombie.getAttackTick() < 3) {
      float tick = (zombie.getAttackTick() + this.partialTick) / 3.0F;
      float f = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F);
      this.chest.field_78795_f -= f * 0.4F;
      this.arm1.field_78795_f -= f * 1.8F;
      this.arm1.field_78808_h -= f * 3.1415927F / 8.0F;
      this.arm2.field_78795_f -= f * 1.8F;
      this.arm2.field_78808_h += f * 3.1415927F / 8.0F;
    } else if (zombie.getAttackTick() < 5) {
      this.chest.field_78795_f -= 0.4F;
      this.arm1.field_78795_f--;
      this.arm1.field_78808_h = 0.0F;
      this.arm2.field_78795_f--;
      this.arm2.field_78808_h = 0.0F;
    } else if (zombie.getAttackTick() < 8) {
      float tick = ((zombie.getAttackTick() - 5) + this.partialTick) / 3.0F;
      float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      float f1 = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F);
      this.waist.field_78795_f += f1 * 0.2F;
      this.chest.field_78795_f -= f * 0.6F - 0.2F;
      this.arm1.field_78795_f -= f * 2.2F - 0.4F;
      this.arm1.field_78808_h -= f * 3.1415927F / 8.0F;
      this.arm2.field_78795_f -= f * 2.2F - 0.4F;
      this.arm2.field_78808_h += f * 3.1415927F / 8.0F;
      this.forearm1.field_78795_f -= f1 * 0.4F;
      this.forearm2.field_78795_f -= f1 * 0.4F;
    } else if (zombie.getAttackTick() < 10) {
      this.waist.field_78795_f += 0.2F;
      this.chest.field_78795_f += 0.2F;
      this.arm1.field_78795_f += 0.4F;
      this.arm2.field_78795_f += 0.4F;
      this.forearm1.field_78795_f -= 0.4F;
      this.forearm2.field_78795_f -= 0.4F;
    } else if (zombie.getAttackTick() < 15) {
      float tick = ((zombie.getAttackTick() - 10) + this.partialTick) / 5.0F;
      float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      float f1 = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F);
      this.waist.field_78795_f += f * 0.39634955F - 0.19634955F;
      this.chest.field_78795_f += f * 0.8F - 0.6F;
      this.arm1.field_78795_f += f * 3.0F - 2.6F;
      this.arm2.field_78795_f += f * 3.0F - 2.6F;
      this.forearm1.field_78795_f -= f * 0.4F;
      this.forearm2.field_78795_f -= f * 0.4F;
      this.leg1.field_78795_f += f1 * 0.6F;
      this.leg2.field_78795_f += f1 * 0.6F;
    } else if (zombie.throwHitTick == -1) {
      this.waist.field_78795_f -= 0.19634955F;
      this.chest.field_78795_f -= 0.6F;
      this.arm1.field_78795_f -= 2.6F;
      this.arm2.field_78795_f -= 2.6F;
      this.leg1.field_78795_f += 0.6F;
      this.leg2.field_78795_f += 0.6F;
    } else if (zombie.throwHitTick < 5) {
      float tick = (zombie.throwHitTick + this.partialTick) / 3.0F;
      float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      float f1 = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F);
      this.waist.field_78795_f -= f * 0.39634955F - 0.2F;
      this.chest.field_78795_f -= f * 0.8F - 0.2F;
      addRotation(this.arm1, -(f * 2.2F + 0.4F), -f1 * 3.1415927F / 8.0F, f1 * 0.4F);
      addRotation(this.arm2, -(f * 2.2F + 0.4F), f1 * 3.1415927F / 8.0F, -f1 * 0.4F);
      this.forearm1.field_78795_f += f1 * 0.2F;
      this.forearm2.field_78795_f += f1 * 0.2F;
      this.leg1.field_78795_f += f * 0.8F - 0.2F;
      this.leg2.field_78795_f += f * 0.8F - 0.2F;
    } else if (zombie.throwFinishTick == -1) {
      this.waist.field_78795_f += 0.2F;
      this.chest.field_78795_f += 0.2F;
      addRotation(this.arm1, -0.4F, -0.3926991F, 0.4F);
      addRotation(this.arm2, -0.4F, 0.3926991F, -0.4F);
      this.forearm1.field_78795_f += 0.2F;
      this.forearm2.field_78795_f += 0.2F;
      this.leg1.field_78795_f -= 0.2F;
      this.leg2.field_78795_f -= 0.2F;
    } else if (zombie.throwFinishTick < 10) {
      float tick = (zombie.throwFinishTick + this.partialTick) / 10.0F;
      float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      this.waist.field_78795_f += f * 0.2F;
      this.chest.field_78795_f += f * 0.2F;
      addRotation(this.arm1, -f * 0.4F, -f * 3.1415927F / 8.0F, f * 0.4F);
      addRotation(this.arm1, -f * 0.4F, f * 3.1415927F / 8.0F, -f * 0.4F);
      this.forearm1.field_78795_f += f * 0.2F;
      this.forearm2.field_78795_f += f * 0.2F;
      this.leg1.field_78795_f -= f * 0.2F;
      this.leg2.field_78795_f -= f * 0.2F;
    } 
  }
  
  protected void animateToss(EntityMutantZombie zombie) {
    if (zombie.getAttackTick() < 3) {
      float tick = (zombie.getAttackTick() + this.partialTick) / 3.0F;
      float f = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F);
      this.chest.field_78795_f -= f * 0.4F;
      this.arm1.field_78795_f -= f * 1.8F;
      this.arm1.field_78808_h -= f * 3.1415927F / 8.0F;
      this.arm2.field_78795_f -= f * 1.8F;
      this.arm2.field_78808_h += f * 3.1415927F / 8.0F;
    } else if (zombie.getAttackTick() < 5) {
      this.chest.field_78795_f -= 0.4F;
      this.arm1.field_78795_f--;
      this.arm1.field_78808_h = 0.0F;
      this.arm2.field_78795_f--;
      this.arm2.field_78808_h = 0.0F;
    } else if (zombie.getAttackTick() < 8) {
      float tick = ((zombie.getAttackTick() - 5) + this.partialTick) / 3.0F;
      float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      float f1 = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F);
      this.waist.field_78795_f += f1 * 0.2F;
      this.chest.field_78795_f -= f * 0.6F - 0.2F;
      this.arm1.field_78795_f -= f * 2.2F - 0.4F;
      this.arm1.field_78808_h -= f * 3.1415927F / 8.0F;
      this.arm2.field_78795_f -= f * 2.2F - 0.4F;
      this.arm2.field_78808_h += f * 3.1415927F / 8.0F;
      this.forearm1.field_78795_f -= f1 * 0.4F;
      this.forearm2.field_78795_f -= f1 * 0.4F;
    } else if (zombie.getAttackTick() < 10) {
      this.waist.field_78795_f += 0.2F;
      this.chest.field_78795_f += 0.2F;
      this.arm1.field_78795_f += 0.4F;
      this.arm2.field_78795_f += 0.4F;
      this.forearm1.field_78795_f -= 0.4F;
      this.forearm2.field_78795_f -= 0.4F;
    } 
  }
  
  protected void animateDeath(EntityMutantZombie zombie) {
    if (zombie.field_70725_aQ <= 20) {
      float tick = (zombie.field_70725_aQ + this.partialTick - 1.0F) / 20.0F;
      float f = MathHelper.func_76126_a(tick * 3.1415927F / 2.0F);
      this.pelvis.field_78797_d += f * 28.0F;
      this.head.field_78795_f -= f * 3.1415927F / 10.0F;
      this.head.field_78796_g += f * 3.1415927F / 5.0F;
      this.chest.field_78795_f -= f * 3.1415927F / 12.0F;
      this.waist.field_78795_f -= f * 3.1415927F / 10.0F;
      this.arm1.field_78795_f -= f * 3.1415927F / 2.0F;
      this.arm1.field_78796_g += f * 3.1415927F / 2.8F;
      this.arm2.field_78795_f -= f * 3.1415927F / 2.0F;
      this.arm2.field_78796_g -= f * 3.1415927F / 2.8F;
      this.leg1.field_78795_f += f * 3.1415927F / 6.0F;
      this.leg1.field_78808_h += f * 3.1415927F / 12.0F;
      this.leg2.field_78795_f += f * 3.1415927F / 6.0F;
      this.leg2.field_78808_h -= f * 3.1415927F / 12.0F;
    } else if (zombie.field_70725_aQ <= 100 || zombie.getLives() <= 0) {
      this.pelvis.field_78797_d += 28.0F;
      this.head.field_78795_f -= 0.31415927F;
      this.head.field_78796_g += 0.62831855F;
      this.chest.field_78795_f -= 0.2617994F;
      this.waist.field_78795_f -= 0.31415927F;
      this.arm1.field_78795_f = (float)(this.arm1.field_78795_f - 1.57079635D);
      this.arm1.field_78796_g = (float)(this.arm1.field_78796_g + 1.12199739D);
      this.arm2.field_78795_f = (float)(this.arm2.field_78795_f - 1.57079635D);
      this.arm2.field_78796_g = (float)(this.arm2.field_78796_g - 1.12199739D);
      this.leg1.field_78795_f += 0.5235988F;
      this.leg1.field_78808_h += 0.2617994F;
      this.leg2.field_78795_f += 0.5235988F;
      this.leg2.field_78808_h -= 0.2617994F;
    } else if (zombie.field_70725_aQ > 100 && zombie.getLives() > 0) {
      float tick = (zombie.field_70725_aQ - this.partialTick) / 80.0F;
      float f = MathHelper.func_76134_b(tick * 3.1415927F / 2.0F);
      this.pelvis.field_78797_d -= f * 28.0F;
      this.head.field_78795_f -= f * 3.1415927F / 10.0F;
      this.head.field_78796_g += f * 3.1415927F / 5.0F;
      this.chest.field_78795_f -= f * 3.1415927F / 12.0F;
      this.waist.field_78795_f -= f * 3.1415927F / 10.0F;
      this.arm1.field_78795_f -= f * 3.1415927F / 2.0F;
      this.arm1.field_78796_g += f * 3.1415927F / 2.8F;
      this.arm2.field_78795_f -= f * 3.1415927F / 2.0F;
      this.arm2.field_78796_g -= f * 3.1415927F / 2.8F;
      this.leg1.field_78795_f += f * 3.1415927F / 6.0F;
      this.leg1.field_78808_h += f * 3.1415927F / 12.0F;
      this.leg2.field_78795_f += f * 3.1415927F / 6.0F;
      this.leg2.field_78808_h -= f * 3.1415927F / 12.0F;
    } 
  }
  
  public void addRotation(ModelRenderer model, float x, float y, float z) {
    model.field_78795_f += x;
    model.field_78796_g += y;
    model.field_78808_h += z;
  }
  
  public float getPartialTick() {
    return this.partialTick;
  }
  
  public void func_78086_a(EntityLivingBase entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
    this.partialTick = partialTick;
  }
}
