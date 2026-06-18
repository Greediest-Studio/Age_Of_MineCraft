package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelIronGolem extends ModelBase implements ICappedModel {
  public ModelRenderer ironGolemHead;
  
  public ModelRenderer ironGolemBody;
  
  public ModelRenderer ironGolemRightArm;
  
  public ModelRenderer ironGolemLeftArm;
  
  public ModelRenderer ironGolemLeftLeg;
  
  public ModelRenderer ironGolemRightLeg;
  
  public ModelRenderer bipedCape;
  
  public ModelIronGolem() {
    this(0.0F);
  }
  
  public ModelIronGolem(float p_i1161_1_) {
    this(p_i1161_1_, -7.0F);
  }
  
  public ModelIronGolem(float p_i46362_1_, float p_i46362_2_) {
    short short1 = 128;
    short short2 = 128;
    this.ironGolemHead = (new ModelRenderer(this)).func_78787_b(short1, short2);
    this.ironGolemHead.func_78793_a(0.0F, 0.0F + p_i46362_2_, -2.0F);
    this.ironGolemHead.func_78784_a(0, 0).func_78790_a(-4.0F, -12.0F, -5.5F, 8, 10, 8, p_i46362_1_);
    this.ironGolemHead.func_78784_a(24, 0).func_78790_a(-1.0F, -5.0F, -7.5F, 2, 4, 2, p_i46362_1_);
    this.ironGolemBody = (new ModelRenderer(this)).func_78787_b(short1, short2);
    this.ironGolemBody.func_78793_a(0.0F, 0.0F + p_i46362_2_, 0.0F);
    this.ironGolemBody.func_78784_a(0, 40).func_78790_a(-9.0F, -2.0F, -6.0F, 18, 12, 11, p_i46362_1_);
    this.ironGolemBody.func_78784_a(0, 70).func_78790_a(-4.5F, 10.0F, -3.0F, 9, 5, 6, p_i46362_1_ + 0.5F);
    this.ironGolemRightArm = (new ModelRenderer(this)).func_78787_b(short1, short2);
    this.ironGolemRightArm.func_78793_a(0.0F, -7.0F, 0.0F);
    this.ironGolemRightArm.func_78784_a(60, 21).func_78790_a(-13.0F, -2.5F, -3.0F, 4, 30, 6, p_i46362_1_);
    this.ironGolemLeftArm = (new ModelRenderer(this)).func_78787_b(short1, short2);
    this.ironGolemLeftArm.func_78793_a(0.0F, -7.0F, 0.0F);
    this.ironGolemLeftArm.func_78784_a(60, 58).func_78790_a(9.0F, -2.5F, -3.0F, 4, 30, 6, p_i46362_1_);
    this.ironGolemLeftLeg = (new ModelRenderer(this, 0, 22)).func_78787_b(short1, short2);
    this.ironGolemLeftLeg.func_78793_a(-4.0F, 18.0F + p_i46362_2_, 0.0F);
    this.ironGolemLeftLeg.func_78784_a(37, 0).func_78790_a(-3.5F, -3.0F, -3.0F, 6, 16, 5, p_i46362_1_);
    this.ironGolemRightLeg = (new ModelRenderer(this, 0, 22)).func_78787_b(short1, short2);
    this.ironGolemRightLeg.field_78809_i = true;
    this.ironGolemRightLeg.func_78784_a(60, 0).func_78793_a(5.0F, 18.0F + p_i46362_2_, 0.0F);
    this.ironGolemRightLeg.func_78790_a(-3.5F, -3.0F, -3.0F, 6, 16, 5, p_i46362_1_);
    this.bipedCape = new ModelRenderer(this, 0, 0);
    this.bipedCape.func_78787_b(64, 32);
    this.bipedCape.func_78790_a(-5.0F, 0.0F, -1.0F, 10, 16, 1, p_i46362_1_);
  }
  
  public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
    func_78087_a(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
    this.ironGolemHead.func_78785_a(p_78088_7_);
    this.ironGolemBody.func_78785_a(p_78088_7_);
    this.ironGolemLeftLeg.func_78785_a(p_78088_7_);
    this.ironGolemRightLeg.func_78785_a(p_78088_7_);
    this.ironGolemRightArm.func_78785_a(p_78088_7_);
    this.ironGolemLeftArm.func_78785_a(p_78088_7_);
  }
  
  public void renderCape(float scale, float flo1, float flo2, float flo3) {
    GlStateManager.func_179094_E();
    GlStateManager.func_179152_a(2.0F, 2.0F, 2.0F);
    GlStateManager.func_179109_b(0.0F, -0.275F, 0.0625F);
    GlStateManager.func_179114_b(6.0F + flo2 / 2.0F + flo1, 1.0F, 0.0F, 0.0F);
    GlStateManager.func_179114_b(flo3 / 2.0F, 0.0F, 0.0F, 1.0F);
    GlStateManager.func_179114_b(-flo3 / 2.0F, 0.0F, 1.0F, 0.0F);
    GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
    this.bipedCape.func_78785_a(scale);
    GlStateManager.func_179121_F();
  }
  
  public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
    this.ironGolemHead.field_78796_g = p_78087_4_ / 57.295776F;
    this.ironGolemHead.field_78795_f = p_78087_5_ / 57.295776F;
    this.ironGolemLeftLeg.field_78795_f = -1.5F * func_78172_a(p_78087_1_, 13.0F) * p_78087_2_;
    this.ironGolemRightLeg.field_78795_f = 1.5F * func_78172_a(p_78087_1_, 13.0F) * p_78087_2_;
    this.ironGolemLeftLeg.field_78796_g = 0.0F;
    this.ironGolemRightLeg.field_78796_g = 0.0F;
    this.ironGolemHead.field_78808_h = 0.0F;
    this.ironGolemRightArm.field_78808_h = 0.0F;
    this.ironGolemLeftArm.field_78808_h = 0.0F;
    EntityTameBase entity = (EntityTameBase)p_78087_7_;
    if (entity.getJukeboxToDanceTo() != null) {
      this.ironGolemHead.field_78808_h = MathHelper.func_76134_b(p_78087_3_ * 0.5F - 3.1415927F) * 0.25F - MathHelper.func_76134_b(p_78087_3_ * 0.25F) * 0.25F;
      this.ironGolemRightArm.field_78795_f -= MathHelper.func_76134_b(p_78087_3_ * 0.5F) * 0.5F - MathHelper.func_76134_b(p_78087_3_ * 0.25F - 3.1415927F) * 0.5F;
      this.ironGolemLeftArm.field_78795_f += MathHelper.func_76134_b(p_78087_3_ * 0.5F - 3.1415927F) * 0.25F - MathHelper.func_76134_b(p_78087_3_ * 0.25F) * 0.25F;
    } 
  }
  
  public void func_78086_a(EntityLivingBase p_78086_1_, float p_78086_2_, float p_78086_3_, float p_78086_4_) {
    EntityTameBase entityirongolem = (EntityTameBase)p_78086_1_;
    int i = entityirongolem.getAttackTimer();
    if (entityirongolem.func_70093_af()) {
      this.ironGolemBody.field_78795_f = 0.25F;
      this.ironGolemRightLeg.field_78798_e = 4.0F;
      this.ironGolemLeftLeg.field_78798_e = 4.0F;
      this.ironGolemRightArm.field_78798_e = 2.0F;
      this.ironGolemLeftArm.field_78798_e = 2.0F;
      this.ironGolemRightArm.field_78797_d = -6.0F;
      this.ironGolemLeftArm.field_78797_d = -6.0F;
      this.ironGolemHead.field_78797_d = -5.0F;
      this.ironGolemBody.field_78797_d = -6.0F;
    } else {
      this.ironGolemBody.field_78795_f = 0.0F;
      this.ironGolemRightLeg.field_78798_e = 0.0F;
      this.ironGolemLeftLeg.field_78798_e = 0.0F;
      this.ironGolemRightArm.field_78798_e = 0.0F;
      this.ironGolemLeftArm.field_78798_e = 0.0F;
      this.ironGolemRightArm.field_78797_d = -7.0F;
      this.ironGolemLeftArm.field_78797_d = -7.0F;
      this.ironGolemHead.field_78797_d = -7.0F;
      this.ironGolemBody.field_78797_d = -7.0F;
    } 
    if (i > 0) {
      this.ironGolemRightArm.field_78795_f = -2.0F + 1.5F * func_78172_a(i - p_78086_4_, 10.0F);
      this.ironGolemLeftArm.field_78795_f = -2.0F + 1.5F * func_78172_a(i - p_78086_4_, 10.0F);
    } else {
      int j = entityirongolem.getHoldRoseTick();
      if (j > 0) {
        this.ironGolemRightArm.field_78795_f = -0.8F + 0.025F * func_78172_a(j, 70.0F);
        this.ironGolemLeftArm.field_78795_f = (-0.2F - 1.5F * func_78172_a(p_78086_2_, 13.0F)) * p_78086_3_;
      } else {
        this.ironGolemRightArm.field_78795_f = (-0.2F + 1.5F * func_78172_a(p_78086_2_, 13.0F)) * p_78086_3_;
        this.ironGolemLeftArm.field_78795_f = (-0.2F - 1.5F * func_78172_a(p_78086_2_, 13.0F)) * p_78086_3_;
      } 
    } 
    this.ironGolemRightArm.field_78796_g = 0.0F;
    this.ironGolemLeftArm.field_78796_g = 0.0F;
    if (!entityirongolem.getCurrentBook().func_190926_b()) {
      this.ironGolemRightArm.field_78796_g = (entityirongolem.bookSpread - 2.0F) * 0.25F;
      this.ironGolemLeftArm.field_78796_g = (-entityirongolem.bookSpread + 2.0F) * 0.25F;
      this.ironGolemRightArm.field_78808_h = 0.0F;
      this.ironGolemLeftArm.field_78808_h = 0.0F;
      this.ironGolemRightArm.field_78795_f = -1.25F + 0.1F + MathHelper.func_76126_a(entityirongolem.field_70173_aa * 0.1F) * 0.01F;
      this.ironGolemLeftArm.field_78795_f = -1.25F + 0.1F + MathHelper.func_76126_a(entityirongolem.field_70173_aa * 0.1F) * 0.01F;
    } 
  }
  
  private float func_78172_a(float p_78172_1_, float p_78172_2_) {
    return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5F) - p_78172_2_ * 0.25F) / p_78172_2_ * 0.25F;
  }
}
