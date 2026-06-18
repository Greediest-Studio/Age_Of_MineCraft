package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityOcelot;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelOcelot extends ModelBase {
  ModelRenderer ocelotBackLeftLeg;
  
  ModelRenderer ocelotBackRightLeg;
  
  ModelRenderer ocelotFrontLeftLeg;
  
  ModelRenderer ocelotFrontRightLeg;
  
  ModelRenderer ocelotTail;
  
  ModelRenderer ocelotTail2;
  
  ModelRenderer ocelotHead;
  
  ModelRenderer ocelotBody;
  
  int field_78163_i = 1;
  
  public ModelOcelot() {
    func_78085_a("head.main", 0, 0);
    func_78085_a("head.nose", 0, 24);
    func_78085_a("head.ear1", 0, 10);
    func_78085_a("head.ear2", 6, 10);
    this.ocelotHead = new ModelRenderer(this, "head");
    this.ocelotHead.func_78786_a("main", -2.5F, -2.0F, -3.0F, 5, 4, 5);
    this.ocelotHead.func_78786_a("nose", -1.5F, 0.0F, -4.0F, 3, 2, 2);
    this.ocelotHead.func_78786_a("ear1", -2.0F, -3.0F, 0.0F, 1, 1, 2);
    this.ocelotHead.func_78786_a("ear2", 1.0F, -3.0F, 0.0F, 1, 1, 2);
    this.ocelotHead.func_78793_a(0.0F, 15.0F, -9.0F);
    this.ocelotBody = new ModelRenderer(this, 20, 0);
    this.ocelotBody.func_78790_a(-2.0F, 3.0F, -8.0F, 4, 16, 6, 0.0F);
    this.ocelotBody.func_78793_a(0.0F, 12.0F, -10.0F);
    this.ocelotTail = new ModelRenderer(this, 0, 15);
    this.ocelotTail.func_78789_a(-0.5F, 0.0F, 0.0F, 1, 8, 1);
    this.ocelotTail.field_78795_f = 0.9F;
    this.ocelotTail.func_78793_a(0.0F, 15.0F, 8.0F);
    this.ocelotTail2 = new ModelRenderer(this, 4, 15);
    this.ocelotTail2.func_78789_a(-0.5F, 0.0F, 0.0F, 1, 8, 1);
    this.ocelotTail2.func_78793_a(0.0F, 20.0F, 14.0F);
    this.ocelotBackLeftLeg = new ModelRenderer(this, 8, 13);
    this.ocelotBackLeftLeg.func_78789_a(-1.0F, 0.0F, 1.0F, 2, 6, 2);
    this.ocelotBackLeftLeg.func_78793_a(1.1F, 18.0F, 5.0F);
    this.ocelotBackRightLeg = new ModelRenderer(this, 8, 13);
    this.ocelotBackRightLeg.func_78789_a(-1.0F, 0.0F, 1.0F, 2, 6, 2);
    this.ocelotBackRightLeg.func_78793_a(-1.1F, 18.0F, 5.0F);
    this.ocelotFrontLeftLeg = new ModelRenderer(this, 40, 0);
    this.ocelotFrontLeftLeg.func_78789_a(-1.0F, 0.0F, 0.0F, 2, 10, 2);
    this.ocelotFrontLeftLeg.func_78793_a(1.2F, 13.8F, -5.0F);
    this.ocelotFrontRightLeg = new ModelRenderer(this, 40, 0);
    this.ocelotFrontRightLeg.func_78789_a(-1.0F, 0.0F, 0.0F, 2, 10, 2);
    this.ocelotFrontRightLeg.func_78793_a(-1.2F, 13.8F, -5.0F);
  }
  
  public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
    func_78087_a(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
    if (this.field_78091_s) {
      float f6 = 2.0F;
      GlStateManager.func_179094_E();
      GlStateManager.func_179152_a(1.5F / f6, 1.5F / f6, 1.5F / f6);
      GlStateManager.func_179109_b(0.0F, 10.0F * p_78088_7_, 4.0F * p_78088_7_);
      this.ocelotHead.func_78785_a(p_78088_7_);
      GlStateManager.func_179121_F();
      GlStateManager.func_179094_E();
      GlStateManager.func_179152_a(1.0F / f6, 1.0F / f6, 1.0F / f6);
      GlStateManager.func_179109_b(0.0F, 24.0F * p_78088_7_, 0.0F);
      this.ocelotBody.func_78785_a(p_78088_7_);
      this.ocelotBackLeftLeg.func_78785_a(p_78088_7_);
      this.ocelotBackRightLeg.func_78785_a(p_78088_7_);
      this.ocelotFrontLeftLeg.func_78785_a(p_78088_7_);
      this.ocelotFrontRightLeg.func_78785_a(p_78088_7_);
      this.ocelotTail.func_78785_a(p_78088_7_);
      this.ocelotTail2.func_78785_a(p_78088_7_);
      GlStateManager.func_179121_F();
    } else {
      this.ocelotHead.func_78785_a(p_78088_7_);
      this.ocelotBody.func_78785_a(p_78088_7_);
      this.ocelotTail.func_78785_a(p_78088_7_);
      this.ocelotTail2.func_78785_a(p_78088_7_);
      this.ocelotBackLeftLeg.func_78785_a(p_78088_7_);
      this.ocelotBackRightLeg.func_78785_a(p_78088_7_);
      this.ocelotFrontLeftLeg.func_78785_a(p_78088_7_);
      this.ocelotFrontRightLeg.func_78785_a(p_78088_7_);
    } 
  }
  
  public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
    this.ocelotHead.field_78795_f = p_78087_5_ / 57.295776F;
    this.ocelotHead.field_78796_g = p_78087_4_ / 57.295776F;
    if (this.field_78163_i != 3) {
      this.ocelotBody.field_78795_f = 1.5707964F;
      if (this.field_78163_i == 2) {
        this.ocelotBackLeftLeg.field_78795_f = MathHelper.func_76134_b(p_78087_1_ * 0.6662F) * 1.0F * p_78087_2_;
        this.ocelotBackRightLeg.field_78795_f = MathHelper.func_76134_b(p_78087_1_ * 0.6662F + 0.3F) * 1.0F * p_78087_2_;
        this.ocelotFrontLeftLeg.field_78795_f = MathHelper.func_76134_b(p_78087_1_ * 0.6662F + 3.1415927F + 0.3F) * 1.0F * p_78087_2_;
        this.ocelotFrontRightLeg.field_78795_f = MathHelper.func_76134_b(p_78087_1_ * 0.6662F + 3.1415927F) * 1.0F * p_78087_2_;
        this.ocelotTail2.field_78795_f = 1.7278761F + 0.31415927F * MathHelper.func_76134_b(p_78087_1_) * p_78087_2_;
      } else {
        this.ocelotBackLeftLeg.field_78795_f = MathHelper.func_76134_b(p_78087_1_ * 0.6662F) * 1.0F * p_78087_2_;
        this.ocelotBackRightLeg.field_78795_f = MathHelper.func_76134_b(p_78087_1_ * 0.6662F + 3.1415927F) * 1.0F * p_78087_2_;
        this.ocelotFrontLeftLeg.field_78795_f = MathHelper.func_76134_b(p_78087_1_ * 0.6662F + 3.1415927F) * 1.0F * p_78087_2_;
        this.ocelotFrontRightLeg.field_78795_f = MathHelper.func_76134_b(p_78087_1_ * 0.6662F) * 1.0F * p_78087_2_;
        if (this.field_78163_i == 1) {
          this.ocelotTail2.field_78795_f = 1.7278761F + 0.7853982F * MathHelper.func_76134_b(p_78087_1_) * p_78087_2_;
        } else {
          this.ocelotTail2.field_78795_f = 1.7278761F + 0.47123894F * MathHelper.func_76134_b(p_78087_1_) * p_78087_2_;
        } 
      } 
    } 
  }
  
  public void func_78086_a(EntityLivingBase p_78086_1_, float p_78086_2_, float p_78086_3_, float p_78086_4_) {
    EntityOcelot entityocelot = (EntityOcelot)p_78086_1_;
    this.ocelotBody.field_78797_d = 12.0F;
    this.ocelotBody.field_78798_e = -10.0F;
    this.ocelotHead.field_78797_d = 15.0F;
    this.ocelotHead.field_78798_e = -9.0F;
    this.ocelotTail.field_78797_d = 15.0F;
    this.ocelotTail.field_78798_e = 8.0F;
    this.ocelotTail2.field_78797_d = 20.0F;
    this.ocelotTail2.field_78798_e = 14.0F;
    this.ocelotFrontRightLeg.field_78797_d = 13.8F;
    this.ocelotFrontRightLeg.field_78798_e = -5.0F;
    this.ocelotBackRightLeg.field_78797_d = 18.0F;
    this.ocelotBackRightLeg.field_78798_e = 5.0F;
    this.ocelotTail.field_78795_f = 0.9F;
    if (entityocelot.func_70093_af()) {
      this.ocelotBody.field_78797_d++;
      this.ocelotHead.field_78797_d += 2.0F;
      this.ocelotTail.field_78797_d++;
      this.ocelotTail2.field_78797_d += -4.0F;
      this.ocelotTail2.field_78798_e += 2.0F;
      this.ocelotTail.field_78795_f = 1.5707964F;
      this.ocelotTail2.field_78795_f = 1.5707964F;
      this.field_78163_i = 0;
    } else if (entityocelot.func_70051_ag()) {
      this.ocelotTail2.field_78797_d = this.ocelotTail.field_78797_d;
      this.ocelotTail2.field_78798_e += 2.0F;
      this.ocelotTail.field_78795_f = 1.5707964F;
      this.ocelotTail2.field_78795_f = 1.5707964F;
      this.field_78163_i = 2;
    } else {
      this.field_78163_i = 1;
    } 
  }
}
