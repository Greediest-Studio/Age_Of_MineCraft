package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.model;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelRemnant extends ModelBase {
  public ModelRenderer mask1;
  
  public ModelRenderer mask2;
  
  public ModelRenderer head;
  
  public ModelRenderer body;
  
  public ModelRenderer leg1;
  
  public ModelRenderer leg11;
  
  public ModelRenderer leg12;
  
  public ModelRenderer leg13;
  
  public ModelRenderer leg14;
  
  public ModelRenderer leg2;
  
  public ModelRenderer leg21;
  
  public ModelRenderer leg22;
  
  public ModelRenderer leg23;
  
  public ModelRenderer leg24;
  
  public ModelRenderer leg3;
  
  public ModelRenderer leg31;
  
  public ModelRenderer leg32;
  
  public ModelRenderer leg33;
  
  public ModelRenderer leg34;
  
  public ModelRenderer leg4;
  
  public ModelRenderer leg41;
  
  public ModelRenderer leg42;
  
  public ModelRenderer leg43;
  
  public ModelRenderer leg44;
  
  public ModelRenderer leftarm;
  
  public ModelRenderer tentacle1;
  
  public ModelRenderer tentacle2;
  
  public ModelRenderer tentacle3;
  
  public ModelRenderer tentacle4;
  
  public ModelRenderer rightarm;
  
  public ModelRemnant() {
    this.field_78090_t = 128;
    this.field_78089_u = 64;
    this.mask1 = new ModelRenderer(this, 32, 0);
    this.mask1.func_78789_a(-3.4F, -8.0F, -6.0F, 6, 8, 1);
    this.mask1.func_78793_a(0.0F, 0.0F, 0.0F);
    this.mask1.func_78787_b(128, 64);
    this.mask1.field_78809_i = true;
    setRotation(this.mask1, 0.0F, -0.5235988F, 0.0F);
    this.mask2 = new ModelRenderer(this, 32, 0);
    this.mask2.func_78789_a(-2.6F, -8.0F, -6.0F, 6, 8, 1);
    this.mask2.func_78793_a(0.0F, 0.0F, 0.0F);
    this.mask2.func_78787_b(128, 64);
    this.mask2.field_78809_i = true;
    setRotation(this.mask2, 0.0F, 0.5235988F, 0.0F);
    this.head = new ModelRenderer(this, 0, 0);
    this.head.func_78789_a(-4.0F, -8.0F, -4.0F, 8, 8, 8);
    this.head.func_78793_a(0.0F, 0.0F, 0.0F);
    this.head.func_78787_b(128, 64);
    this.head.field_78809_i = true;
    setRotation(this.head, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.mask1);
    this.head.func_78792_a(this.mask2);
    this.body = new ModelRenderer(this, 16, 16);
    this.body.func_78789_a(-4.0F, 0.0F, -2.0F, 8, 22, 4);
    this.body.func_78793_a(0.0F, 0.0F, 0.0F);
    this.body.func_78787_b(128, 64);
    this.body.field_78809_i = true;
    setRotation(this.body, 0.0F, 0.0F, 0.0F);
    this.leg1 = new ModelRenderer(this, 0, 42);
    this.leg1.func_78789_a(0.0F, -1.0F, -1.0F, 2, 2, 2);
    this.leg1.func_78793_a(1.0F, 23.0F, 0.0F);
    this.leg1.func_78787_b(128, 64);
    this.leg1.field_78809_i = true;
    setRotation(this.leg1, 0.0F, 0.0F, 0.0F);
    this.leg11 = new ModelRenderer(this, 0, 42);
    this.leg11.func_78789_a(0.0F, -1.0F, -1.0F, 2, 2, 2);
    this.leg11.func_78793_a(2.0F, 0.0F, 0.0F);
    this.leg11.func_78787_b(128, 64);
    this.leg11.field_78809_i = true;
    setRotation(this.leg11, 0.0F, 0.0F, 0.0F);
    this.leg1.func_78792_a(this.leg11);
    this.leg12 = new ModelRenderer(this, 0, 42);
    this.leg12.func_78789_a(0.0F, -1.0F, -1.0F, 2, 2, 2);
    this.leg12.func_78793_a(2.0F, 0.0F, 0.0F);
    this.leg12.func_78787_b(128, 64);
    this.leg12.field_78809_i = true;
    setRotation(this.leg12, 0.0F, 0.0F, 0.0F);
    this.leg11.func_78792_a(this.leg12);
    this.leg13 = new ModelRenderer(this, 0, 42);
    this.leg13.func_78789_a(0.0F, -1.0F, -1.0F, 2, 2, 2);
    this.leg13.func_78793_a(2.0F, 0.0F, 0.0F);
    this.leg13.func_78787_b(128, 64);
    this.leg13.field_78809_i = true;
    setRotation(this.leg13, 0.0F, 0.0F, 0.0F);
    this.leg12.func_78792_a(this.leg13);
    this.leg14 = new ModelRenderer(this, 0, 42);
    this.leg14.func_78789_a(0.0F, -1.0F, -1.0F, 2, 2, 2);
    this.leg14.func_78793_a(2.0F, 0.0F, 0.0F);
    this.leg14.func_78787_b(128, 64);
    this.leg14.field_78809_i = true;
    setRotation(this.leg14, 0.0F, 0.0F, 0.0F);
    this.leg13.func_78792_a(this.leg14);
    this.leg2 = new ModelRenderer(this, 0, 42);
    this.leg2.func_78789_a(-2.0F, -1.0F, -1.0F, 2, 2, 2);
    this.leg2.func_78793_a(-1.0F, 23.0F, 0.0F);
    this.leg2.func_78787_b(128, 64);
    this.leg2.field_78809_i = true;
    setRotation(this.leg2, 0.0F, 0.0F, 0.0F);
    this.leg21 = new ModelRenderer(this, 0, 42);
    this.leg21.func_78789_a(-2.0F, -1.0F, -1.0F, 2, 2, 2);
    this.leg21.func_78793_a(-2.0F, 0.0F, 0.0F);
    this.leg21.func_78787_b(128, 64);
    this.leg21.field_78809_i = true;
    setRotation(this.leg21, 0.0F, 0.0F, 0.0F);
    this.leg2.func_78792_a(this.leg21);
    this.leg22 = new ModelRenderer(this, 0, 42);
    this.leg22.func_78789_a(-2.0F, -1.0F, -1.0F, 2, 2, 2);
    this.leg22.func_78793_a(-2.0F, 0.0F, 0.0F);
    this.leg22.func_78787_b(128, 64);
    this.leg22.field_78809_i = true;
    setRotation(this.leg22, 0.0F, 0.0F, 0.0F);
    this.leg21.func_78792_a(this.leg22);
    this.leg23 = new ModelRenderer(this, 0, 42);
    this.leg23.func_78789_a(-2.0F, -1.0F, -1.0F, 2, 2, 2);
    this.leg23.func_78793_a(-2.0F, 0.0F, 0.0F);
    this.leg23.func_78787_b(128, 64);
    this.leg23.field_78809_i = true;
    setRotation(this.leg23, 0.0F, 0.0F, 0.0F);
    this.leg22.func_78792_a(this.leg23);
    this.leg24 = new ModelRenderer(this, 0, 42);
    this.leg24.func_78789_a(-2.0F, -1.0F, -1.0F, 2, 2, 2);
    this.leg24.func_78793_a(-2.0F, 0.0F, 0.0F);
    this.leg24.func_78787_b(128, 64);
    this.leg24.field_78809_i = true;
    setRotation(this.leg24, 0.0F, 0.0F, 0.0F);
    this.leg23.func_78792_a(this.leg24);
    this.leg3 = new ModelRenderer(this, 2, 42);
    this.leg3.func_78789_a(-1.0F, -1.0F, 0.0F, 2, 2, 2);
    this.leg3.func_78793_a(0.0F, 23.0F, 1.0F);
    this.leg3.func_78787_b(128, 64);
    this.leg3.field_78809_i = true;
    setRotation(this.leg3, 0.0F, 0.0F, 0.0F);
    this.leg31 = new ModelRenderer(this, 2, 42);
    this.leg31.func_78789_a(-1.0F, -1.0F, 0.0F, 2, 2, 2);
    this.leg31.func_78793_a(0.0F, 0.0F, 2.0F);
    this.leg31.func_78787_b(128, 64);
    this.leg31.field_78809_i = true;
    setRotation(this.leg31, 0.0F, 0.0F, 0.0F);
    this.leg3.func_78792_a(this.leg31);
    this.leg32 = new ModelRenderer(this, 2, 42);
    this.leg32.func_78789_a(-1.0F, -1.0F, 0.0F, 2, 2, 2);
    this.leg32.func_78793_a(0.0F, 0.0F, 2.0F);
    this.leg32.func_78787_b(128, 64);
    this.leg32.field_78809_i = true;
    setRotation(this.leg32, 0.0F, 0.0F, 0.0F);
    this.leg31.func_78792_a(this.leg32);
    this.leg33 = new ModelRenderer(this, 2, 42);
    this.leg33.func_78789_a(-1.0F, -1.0F, 0.0F, 2, 2, 2);
    this.leg33.func_78793_a(0.0F, 0.0F, 2.0F);
    this.leg33.func_78787_b(128, 64);
    this.leg33.field_78809_i = true;
    setRotation(this.leg33, 0.0F, 0.0F, 0.0F);
    this.leg32.func_78792_a(this.leg33);
    this.leg34 = new ModelRenderer(this, 2, 42);
    this.leg34.func_78789_a(-1.0F, -1.0F, 0.0F, 2, 2, 2);
    this.leg34.func_78793_a(0.0F, 0.0F, 2.0F);
    this.leg34.func_78787_b(128, 64);
    this.leg34.field_78809_i = true;
    setRotation(this.leg34, 0.0F, 0.0F, 0.0F);
    this.leg33.func_78792_a(this.leg34);
    this.leg4 = new ModelRenderer(this, 2, 42);
    this.leg4.func_78789_a(-1.0F, -1.0F, -2.0F, 2, 2, 2);
    this.leg4.func_78793_a(0.0F, 23.0F, -1.0F);
    this.leg4.func_78787_b(128, 64);
    this.leg4.field_78809_i = true;
    setRotation(this.leg4, 0.0F, 0.0F, 0.0F);
    this.leg41 = new ModelRenderer(this, 2, 42);
    this.leg41.func_78789_a(-1.0F, -1.0F, -2.0F, 2, 2, 2);
    this.leg41.func_78793_a(0.0F, 0.0F, -2.0F);
    this.leg41.func_78787_b(128, 64);
    this.leg41.field_78809_i = true;
    setRotation(this.leg41, 0.0F, 0.0F, 0.0F);
    this.leg4.func_78792_a(this.leg41);
    this.leg42 = new ModelRenderer(this, 2, 42);
    this.leg42.func_78789_a(-1.0F, -1.0F, -2.0F, 2, 2, 2);
    this.leg42.func_78793_a(0.0F, 0.0F, -2.0F);
    this.leg42.func_78787_b(128, 64);
    this.leg42.field_78809_i = true;
    setRotation(this.leg42, 0.0F, 0.0F, 0.0F);
    this.leg41.func_78792_a(this.leg42);
    this.leg43 = new ModelRenderer(this, 2, 42);
    this.leg43.func_78789_a(-1.0F, -1.0F, -2.0F, 2, 2, 2);
    this.leg43.func_78793_a(0.0F, 0.0F, -2.0F);
    this.leg43.func_78787_b(128, 64);
    this.leg43.field_78809_i = true;
    setRotation(this.leg43, 0.0F, 0.0F, 0.0F);
    this.leg42.func_78792_a(this.leg43);
    this.leg44 = new ModelRenderer(this, 2, 42);
    this.leg44.func_78789_a(-1.0F, -1.0F, -2.0F, 2, 2, 2);
    this.leg44.func_78793_a(0.0F, 0.0F, -2.0F);
    this.leg44.func_78787_b(128, 64);
    this.leg44.field_78809_i = true;
    setRotation(this.leg44, 0.0F, 0.0F, 0.0F);
    this.leg43.func_78792_a(this.leg44);
    this.leftarm = new ModelRenderer(this, 0, 20);
    this.leftarm.func_78789_a(0.0F, -2.0F, -2.0F, 4, 10, 4);
    this.leftarm.func_78793_a(4.0F, 2.0F, 0.0F);
    this.leftarm.func_78787_b(128, 64);
    this.leftarm.field_78809_i = true;
    setRotation(this.leftarm, 0.0F, 0.0F, 0.0F);
    this.tentacle1 = new ModelRenderer(this, 0, 46);
    this.tentacle1.func_78789_a(-1.0F, 0.0F, -1.0F, 2, 6, 2);
    this.tentacle1.func_78793_a(1.0F, 8.0F, 1.0F);
    this.tentacle1.func_78787_b(128, 64);
    this.tentacle1.field_78809_i = true;
    setRotation(this.tentacle1, 0.0F, 0.0F, 0.0F);
    this.tentacle2 = new ModelRenderer(this, 0, 46);
    this.tentacle2.func_78789_a(-1.0F, 0.0F, -1.0F, 2, 6, 2);
    this.tentacle2.func_78793_a(3.0F, 8.0F, 1.0F);
    this.tentacle2.func_78787_b(128, 64);
    this.tentacle2.field_78809_i = true;
    setRotation(this.tentacle2, 0.0F, 0.0F, 0.0F);
    this.tentacle3 = new ModelRenderer(this, 0, 46);
    this.tentacle3.func_78789_a(-1.0F, 0.0F, -1.0F, 2, 6, 2);
    this.tentacle3.func_78793_a(1.0F, 8.0F, -1.0F);
    this.tentacle3.func_78787_b(128, 64);
    this.tentacle3.field_78809_i = true;
    setRotation(this.tentacle3, 0.0F, 0.0F, 0.0F);
    this.tentacle4 = new ModelRenderer(this, 0, 46);
    this.tentacle4.func_78789_a(-1.0F, 0.0F, -1.0F, 2, 6, 2);
    this.tentacle4.func_78793_a(3.0F, 8.0F, -1.0F);
    this.tentacle4.func_78787_b(128, 64);
    this.tentacle4.field_78809_i = true;
    setRotation(this.tentacle4, 0.0F, 0.0F, 0.0F);
    this.leftarm.func_78792_a(this.tentacle1);
    this.leftarm.func_78792_a(this.tentacle2);
    this.leftarm.func_78792_a(this.tentacle3);
    this.leftarm.func_78792_a(this.tentacle4);
    this.rightarm = new ModelRenderer(this, 0, 20);
    this.rightarm.func_78789_a(-4.0F, -2.0F, -2.0F, 4, 16, 4);
    this.rightarm.func_78793_a(-4.0F, 2.0F, 0.0F);
    this.rightarm.func_78787_b(128, 64);
    this.rightarm.field_78809_i = true;
    setRotation(this.rightarm, 0.0F, 0.0F, 0.0F);
  }
  
  public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    super.func_78088_a(entity, f, f1, f2, f3, f4, f5);
    func_78087_a(f, f1, f2, f3, f4, f5, entity);
    this.head.func_78785_a(f5);
    this.body.func_78785_a(f5);
    this.leg1.func_78785_a(f5);
    this.leg2.func_78785_a(f5);
    this.leg3.func_78785_a(f5);
    this.leg4.func_78785_a(f5);
    this.leftarm.func_78785_a(f5);
    this.rightarm.func_78785_a(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z) {
    model.field_78795_f = x;
    model.field_78796_g = y;
    model.field_78808_h = z;
  }
  
  public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity par7Entity) {
    EntityTameBase entity = (EntityTameBase)par7Entity;
    this.head.field_78796_g = f3 / 57.295776F;
    this.head.field_78795_f = f4 / 57.295776F;
    this.rightarm.field_78795_f = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.leftarm.field_78795_f = MathHelper.func_76134_b(f * 0.6662F) * 2.0F * f1 * 0.5F;
    this.rightarm.field_78808_h = 0.0F;
    this.leftarm.field_78808_h = 0.0F;
    this.tentacle1.field_82906_o = this.tentacle1.field_82908_p = this.tentacle1.field_82907_q = 0.0F;
    float f16 = 0.03F * (entity.func_145782_y() % 10);
    this.tentacle1.field_78795_f = MathHelper.func_76134_b(entity.field_70173_aa * f16) * 10.5F * 3.1415927F / 180.0F;
    this.tentacle1.field_78796_g = 0.0F;
    this.tentacle1.field_78808_h = MathHelper.func_76126_a(entity.field_70173_aa * f16) * 6.5F * 3.1415927F / 180.0F;
    float f17 = 0.03F * (entity.func_145782_y() % 10);
    this.tentacle2.field_82906_o = this.tentacle2.field_82908_p = this.tentacle2.field_82907_q = 0.0F;
    this.tentacle2.field_78795_f = MathHelper.func_76126_a(entity.field_70173_aa * f17) * 10.5F * 3.1415927F / 180.0F;
    this.tentacle2.field_78796_g = 0.0F;
    this.tentacle2.field_78808_h = MathHelper.func_76134_b(entity.field_70173_aa * f17) * 6.5F * 3.1415927F / 180.0F;
    float f18 = 0.03F * (entity.func_145782_y() % 10);
    this.tentacle3.field_82906_o = this.tentacle3.field_82908_p = this.tentacle3.field_82907_q = 0.0F;
    this.tentacle3.field_78795_f = MathHelper.func_76126_a(entity.field_70173_aa * f18) * 10.5F * 3.1415927F / 180.0F;
    this.tentacle3.field_78796_g = 0.0F;
    this.tentacle3.field_78808_h = MathHelper.func_76134_b(entity.field_70173_aa * f18) * 6.5F * 3.1415927F / 180.0F;
    float f19 = 0.03F * (entity.func_145782_y() % 10);
    this.tentacle4.field_82906_o = this.tentacle4.field_82908_p = this.tentacle4.field_82907_q = 0.0F;
    this.tentacle4.field_78795_f = MathHelper.func_76134_b(entity.field_70173_aa * f19) * 10.5F * 3.1415927F / 180.0F;
    this.tentacle4.field_78796_g = 0.0F;
    this.tentacle4.field_78808_h = MathHelper.func_76126_a(entity.field_70173_aa * f19) * 6.5F * 3.1415927F / 180.0F;
    this.leg1.field_78796_g = 0.6662F + MathHelper.func_76134_b(f * 0.6662F) * 2.0F * f1 * 0.5F;
    this.leg11.field_78796_g = MathHelper.func_76134_b(f * 0.6662F) * 1.9F * f1 * 0.5F;
    this.leg12.field_78796_g = MathHelper.func_76134_b(f * 0.6662F) * 1.8F * f1 * 0.5F;
    this.leg13.field_78796_g = MathHelper.func_76134_b(f * 0.6662F) * 1.7F * f1 * 0.5F;
    this.leg14.field_78796_g = MathHelper.func_76134_b(f * 0.6662F) * 1.6F * f1 * 0.5F;
    this.leg2.field_78796_g = 0.6662F + MathHelper.func_76134_b(f * 0.6662F) * 2.0F * f1 * 0.5F;
    this.leg21.field_78796_g = MathHelper.func_76134_b(f * 0.6662F) * 1.9F * f1 * 0.5F;
    this.leg22.field_78796_g = MathHelper.func_76134_b(f * 0.6662F) * 1.8F * f1 * 0.5F;
    this.leg23.field_78796_g = MathHelper.func_76134_b(f * 0.6662F) * 1.7F * f1 * 0.5F;
    this.leg24.field_78796_g = MathHelper.func_76134_b(f * 0.6662F) * 1.6F * f1 * 0.5F;
    this.leg3.field_78796_g = 0.6662F + MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.leg31.field_78796_g = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 1.9F * f1 * 0.5F;
    this.leg32.field_78796_g = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 1.8F * f1 * 0.5F;
    this.leg33.field_78796_g = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 1.7F * f1 * 0.5F;
    this.leg34.field_78796_g = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 1.6F * f1 * 0.5F;
    this.leg4.field_78796_g = 0.6662F + MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.leg41.field_78796_g = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 1.9F * f1 * 0.5F;
    this.leg42.field_78796_g = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 1.8F * f1 * 0.5F;
    this.leg43.field_78796_g = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 1.7F * f1 * 0.5F;
    this.leg44.field_78796_g = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 1.6F * f1 * 0.5F;
    if (this.field_78093_q) {
      this.rightarm.field_78795_f += -0.62831855F;
      this.leftarm.field_78795_f += -0.62831855F;
      this.leg1.field_78795_f = -1.2566371F;
      this.leg2.field_78795_f = -1.2566371F;
      this.leg3.field_78795_f = -1.2566371F;
      this.leg4.field_78795_f = -1.2566371F;
      this.leg1.field_78796_g = 0.31415927F;
      this.leg2.field_78796_g = -0.31415927F;
      this.leg3.field_78796_g = 0.31415927F;
      this.leg4.field_78796_g = -0.31415927F;
    } 
    this.rightarm.field_78796_g = 0.0F;
    this.leftarm.field_78796_g = 0.0F;
    if (this.field_78095_p > -9990.0F) {
      float f6 = this.field_78095_p;
      this.body.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightarm.field_78796_g += this.body.field_78796_g;
      this.leftarm.field_78796_g += this.body.field_78796_g;
      f6 = 1.0F - this.field_78095_p;
      f6 *= f6;
      f6 *= f6;
      f6 = 1.0F - f6;
      float f7 = MathHelper.func_76126_a(f6 * 3.1415927F);
      float f8 = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F) * -(this.head.field_78795_f - 0.7F) * 0.75F;
      this.rightarm.field_78795_f = (float)(this.rightarm.field_78795_f - f7 * 1.2D + f8);
      this.rightarm.field_78796_g += this.body.field_78796_g * 2.0F;
      this.rightarm.field_78808_h = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F) * -0.4F;
      this.leftarm.field_78795_f = (float)(this.leftarm.field_78795_f - f7 * 1.2D + f8);
      this.leftarm.field_78796_g += this.body.field_78796_g * -2.0F;
      this.leftarm.field_78808_h = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F) * 0.4F;
    } 
    if (!entity.getCurrentBook().func_190926_b()) {
      this.tentacle1.field_78795_f = 0.0F;
      this.tentacle1.field_78796_g = 0.0F;
      this.tentacle1.field_78808_h = entity.bookSpread - 0.75F;
      this.tentacle2.field_78795_f = 0.0F;
      this.tentacle2.field_78796_g = 0.0F;
      this.tentacle2.field_78808_h = entity.bookSpread - 0.75F;
      this.tentacle3.field_78795_f = 0.0F;
      this.tentacle3.field_78796_g = 0.0F;
      this.tentacle3.field_78808_h = entity.bookSpread - 0.75F;
      this.tentacle4.field_78795_f = 0.0F;
      this.tentacle4.field_78796_g = 0.0F;
      this.tentacle4.field_78808_h = entity.bookSpread - 0.75F;
      this.rightarm.field_78796_g = entity.bookSpread - 1.0F;
      this.leftarm.field_78796_g = -entity.bookSpread + 1.0F;
      this.rightarm.field_78808_h = 0.0F;
      this.leftarm.field_78808_h = 0.0F;
      this.rightarm.field_78795_f = -1.5F + 0.1F + MathHelper.func_76126_a(entity.field_70173_aa * 0.1F) * 0.01F;
      this.leftarm.field_78795_f = -1.5F + 0.1F + MathHelper.func_76126_a(entity.field_70173_aa * 0.1F) * 0.01F;
    } 
  }
}
