package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.model;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelGatekeeperMinion extends ModelBase {
  public ModelRenderer mask1;
  
  public ModelRenderer maskpart1;
  
  public ModelRenderer maskpart2;
  
  public ModelRenderer mask2;
  
  public ModelRenderer maskpart3;
  
  public ModelRenderer maskpart4;
  
  public ModelRenderer head;
  
  public ModelRenderer body;
  
  public ModelRenderer rightshoulder;
  
  public ModelRenderer leftshoulder;
  
  public ModelRenderer rightarm1;
  
  public ModelRenderer rightarm2;
  
  public ModelRenderer leftarm1;
  
  public ModelRenderer leftarm2;
  
  public ModelRenderer tentacle1;
  
  public ModelRenderer tentacle2;
  
  public ModelRenderer tentacle3;
  
  public ModelRenderer tentacle4;
  
  public ModelRenderer rltentacle1;
  
  public ModelRenderer rltentacle2;
  
  public ModelRenderer rltentacle3;
  
  public ModelRenderer rltentacle4;
  
  public ModelRenderer lltentacle1;
  
  public ModelRenderer lltentacle2;
  
  public ModelRenderer lltentacle3;
  
  public ModelRenderer lltentacle4;
  
  public ModelRenderer lowerbody;
  
  public ModelGatekeeperMinion() {
    this.field_78090_t = 128;
    this.field_78089_u = 64;
    this.mask1 = new ModelRenderer(this, 32, 0);
    this.mask1.func_78789_a(-3.4F, -8.0F, -6.0F, 6, 8, 1);
    this.mask1.func_78793_a(0.0F, 0.0F, 0.0F);
    this.mask1.func_78787_b(128, 64);
    this.mask1.field_78809_i = true;
    setRotation(this.mask1, 0.0F, -0.5235988F, 0.0F);
    this.maskpart1 = new ModelRenderer(this, 26, 0);
    this.maskpart1.func_78789_a(1.6F, -8.0F, -7.0F, 1, 1, 1);
    this.maskpart1.func_78793_a(0.0F, 0.0F, 0.0F);
    this.maskpart1.func_78787_b(128, 64);
    this.maskpart1.field_78809_i = true;
    setRotation(this.maskpart1, 0.0F, -0.5235988F, 0.0F);
    this.maskpart2 = new ModelRenderer(this, 26, 0);
    this.maskpart2.func_78789_a(1.6F, -1.0F, -7.0F, 1, 1, 1);
    this.maskpart2.func_78793_a(0.0F, 0.0F, 0.0F);
    this.maskpart2.func_78787_b(128, 64);
    this.maskpart2.field_78809_i = true;
    setRotation(this.maskpart2, 0.0F, -0.5235988F, 0.0F);
    this.mask2 = new ModelRenderer(this, 32, 0);
    this.mask2.func_78789_a(-2.6F, -8.0F, -6.0F, 6, 8, 1);
    this.mask2.func_78793_a(0.0F, 0.0F, 0.0F);
    this.mask2.func_78787_b(128, 64);
    this.mask2.field_78809_i = true;
    setRotation(this.mask2, 0.0F, 0.5235988F, 0.0F);
    this.maskpart3 = new ModelRenderer(this, 26, 0);
    this.maskpart3.func_78789_a(-2.6F, -8.0F, -7.0F, 1, 1, 1);
    this.maskpart3.func_78793_a(0.0F, 0.0F, 0.0F);
    this.maskpart3.func_78787_b(128, 64);
    this.maskpart3.field_78809_i = true;
    setRotation(this.maskpart3, 0.0F, 0.5235988F, 0.0F);
    this.maskpart4 = new ModelRenderer(this, 26, 0);
    this.maskpart4.func_78789_a(-2.6F, -1.0F, -7.0F, 1, 1, 1);
    this.maskpart4.func_78793_a(0.0F, 0.0F, 0.0F);
    this.maskpart4.func_78787_b(128, 64);
    this.maskpart4.field_78809_i = true;
    setRotation(this.maskpart4, 0.0F, 0.5235988F, 0.0F);
    this.head = new ModelRenderer(this, 0, 0);
    this.head.func_78789_a(-4.0F, -8.0F, -4.0F, 8, 8, 8);
    this.head.func_78793_a(0.0F, -12.0F, 0.0F);
    this.head.func_78787_b(128, 64);
    this.head.field_78809_i = true;
    setRotation(this.head, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.mask1);
    this.head.func_78792_a(this.mask2);
    this.head.func_78792_a(this.maskpart1);
    this.head.func_78792_a(this.maskpart2);
    this.head.func_78792_a(this.maskpart3);
    this.head.func_78792_a(this.maskpart4);
    this.body = new ModelRenderer(this, 16, 16);
    this.body.func_78789_a(0.0F, 0.0F, 0.0F, 8, 22, 6);
    this.body.func_78793_a(-4.0F, -12.0F, -3.0F);
    this.body.func_78787_b(128, 64);
    this.body.field_78809_i = true;
    setRotation(this.body, 0.0F, 0.0F, 0.0F);
    this.rightshoulder = new ModelRenderer(this, 44, 9);
    this.rightshoulder.func_78789_a(-7.0F, -2.0F, -2.0F, 7, 4, 6);
    this.rightshoulder.func_78793_a(0.5F, 2.0F, 2.0F);
    this.rightshoulder.func_78787_b(128, 64);
    this.rightshoulder.field_78809_i = true;
    setRotation(this.rightshoulder, 0.0F, 0.0F, -0.1745329F);
    this.body.func_78792_a(this.rightshoulder);
    this.leftshoulder = new ModelRenderer(this, 44, 9);
    this.leftshoulder.func_78789_a(0.0F, -2.0F, -2.0F, 7, 4, 6);
    this.leftshoulder.func_78793_a(7.5F, 2.0F, 2.0F);
    this.leftshoulder.func_78787_b(128, 64);
    this.leftshoulder.field_78809_i = true;
    setRotation(this.leftshoulder, 0.0F, 0.0F, 0.1745329F);
    this.body.func_78792_a(this.leftshoulder);
    this.rightarm1 = new ModelRenderer(this, 0, 20);
    this.rightarm1.func_78789_a(-4.0F, -2.0F, -2.0F, 4, 8, 4);
    this.rightarm1.func_78793_a(-5.0F, -8.0F, 0.0F);
    this.rightarm1.func_78787_b(128, 64);
    this.rightarm1.field_78809_i = true;
    setRotation(this.rightarm1, -0.3839724F, 0.0F, 0.0F);
    this.rightarm2 = new ModelRenderer(this, 0, 26);
    this.rightarm2.func_78789_a(-4.0F, 5.5F, -1.0F, 4, 10, 4);
    this.rightarm2.func_78793_a(0.0F, 0.0F, 0.0F);
    this.rightarm2.func_78787_b(128, 64);
    this.rightarm2.field_78809_i = true;
    setRotation(this.rightarm2, -0.5585054F - this.rightarm1.field_78795_f, 0.0F, 0.0F);
    this.rightarm1.func_78792_a(this.rightarm2);
    this.leftarm1 = new ModelRenderer(this, 0, 20);
    this.leftarm1.func_78789_a(0.0F, -2.0F, -2.0F, 4, 8, 4);
    this.leftarm1.func_78793_a(5.0F, -8.0F, 0.0F);
    this.leftarm1.func_78787_b(128, 64);
    this.leftarm1.field_78809_i = true;
    setRotation(this.leftarm1, -0.3839724F, 0.0F, 0.0F);
    this.leftarm2 = new ModelRenderer(this, 0, 26);
    this.leftarm2.func_78789_a(0.0F, 5.5F, -1.0F, 4, 4, 4);
    this.leftarm2.func_78793_a(0.0F, 0.0F, 0.0F);
    this.leftarm2.func_78787_b(128, 64);
    this.leftarm2.field_78809_i = true;
    setRotation(this.leftarm2, -0.5585054F - this.leftarm1.field_78795_f, 0.0F, 0.0F);
    this.leftarm1.func_78792_a(this.leftarm2);
    this.tentacle1 = new ModelRenderer(this, 0, 46);
    this.tentacle1.func_78789_a(-1.0F, 0.0F, -1.0F, 2, 6, 2);
    this.tentacle1.func_78793_a(1.0F, 9.0F, 2.0F);
    this.tentacle1.func_78787_b(128, 64);
    this.tentacle1.field_78809_i = true;
    setRotation(this.tentacle1, 0.0F, 0.0F, 0.0F);
    this.tentacle2 = new ModelRenderer(this, 0, 46);
    this.tentacle2.func_78789_a(-1.0F, 0.0F, -1.0F, 2, 6, 2);
    this.tentacle2.func_78793_a(3.0F, 9.0F, 2.0F);
    this.tentacle2.func_78787_b(128, 64);
    this.tentacle2.field_78809_i = true;
    setRotation(this.tentacle2, 0.0F, 0.0F, 0.0F);
    this.tentacle3 = new ModelRenderer(this, 0, 46);
    this.tentacle3.func_78789_a(-1.0F, 0.0F, -1.0F, 2, 6, 2);
    this.tentacle3.func_78793_a(1.0F, 9.0F, 0.0F);
    this.tentacle3.func_78787_b(128, 64);
    this.tentacle3.field_78809_i = true;
    setRotation(this.tentacle3, 0.0F, 0.0F, 0.0F);
    this.tentacle4 = new ModelRenderer(this, 0, 46);
    this.tentacle4.func_78789_a(-1.0F, 0.0F, -1.0F, 2, 6, 2);
    this.tentacle4.func_78793_a(3.0F, 9.0F, 0.0F);
    this.tentacle4.func_78787_b(128, 64);
    this.tentacle4.field_78809_i = true;
    setRotation(this.tentacle4, 0.0F, 0.0F, 0.0F);
    this.rltentacle1 = new ModelRenderer(this, 0, 46);
    this.rltentacle1.func_78789_a(-1.0F, 0.0F, -1.0F, 2, 6, 2);
    this.rltentacle1.func_78793_a(-3.0F, 18.0F, 1.0F);
    this.rltentacle1.func_78787_b(128, 64);
    this.rltentacle1.field_78809_i = true;
    this.leftarm2.func_78792_a(this.tentacle1);
    this.leftarm2.func_78792_a(this.tentacle2);
    this.leftarm2.func_78792_a(this.tentacle3);
    this.leftarm2.func_78792_a(this.tentacle4);
    setRotation(this.rltentacle1, 0.0F, 0.0F, 0.0F);
    this.rltentacle2 = new ModelRenderer(this, 0, 46);
    this.rltentacle2.func_78789_a(-1.0F, 0.0F, -1.0F, 2, 6, 2);
    this.rltentacle2.func_78793_a(-3.0F, 18.0F, -1.0F);
    this.rltentacle2.func_78787_b(128, 64);
    this.rltentacle2.field_78809_i = true;
    setRotation(this.rltentacle2, 0.0F, 0.0F, 0.0F);
    this.rltentacle3 = new ModelRenderer(this, 0, 46);
    this.rltentacle3.func_78789_a(-1.0F, 0.0F, -1.0F, 2, 6, 2);
    this.rltentacle3.func_78793_a(-1.0F, 18.0F, 1.0F);
    this.rltentacle3.func_78787_b(128, 64);
    this.rltentacle3.field_78809_i = true;
    setRotation(this.rltentacle3, 0.0F, 0.0F, 0.0F);
    this.rltentacle4 = new ModelRenderer(this, 0, 46);
    this.rltentacle4.func_78789_a(-1.0F, 0.0F, -1.0F, 2, 6, 2);
    this.rltentacle4.func_78793_a(-1.0F, 18.0F, -1.0F);
    this.rltentacle4.func_78787_b(128, 64);
    this.rltentacle4.field_78809_i = true;
    setRotation(this.rltentacle4, 0.0F, 0.0F, 0.0F);
    this.lltentacle1 = new ModelRenderer(this, 0, 46);
    this.lltentacle1.func_78789_a(-1.0F, 0.0F, -1.0F, 2, 6, 2);
    this.lltentacle1.func_78793_a(1.0F, 18.0F, 1.0F);
    this.lltentacle1.func_78787_b(128, 64);
    this.lltentacle1.field_78809_i = true;
    setRotation(this.lltentacle1, 0.0F, 0.0F, 0.0F);
    this.lltentacle2 = new ModelRenderer(this, 0, 46);
    this.lltentacle2.func_78789_a(-1.0F, 0.0F, -1.0F, 2, 6, 2);
    this.lltentacle2.func_78793_a(1.0F, 18.0F, -1.0F);
    this.lltentacle2.func_78787_b(128, 64);
    this.lltentacle2.field_78809_i = true;
    setRotation(this.lltentacle2, 0.0F, 0.0F, 0.0F);
    this.lltentacle3 = new ModelRenderer(this, 0, 46);
    this.lltentacle3.func_78789_a(-1.0F, 0.0F, -1.0F, 2, 6, 2);
    this.lltentacle3.func_78793_a(3.0F, 18.0F, 1.0F);
    this.lltentacle3.func_78787_b(128, 64);
    this.lltentacle3.field_78809_i = true;
    setRotation(this.lltentacle3, 0.0F, 0.0F, 0.0F);
    this.lltentacle4 = new ModelRenderer(this, 0, 46);
    this.lltentacle4.func_78789_a(-1.0F, 0.0F, -1.0F, 2, 6, 2);
    this.lltentacle4.func_78793_a(3.0F, 18.0F, -1.0F);
    this.lltentacle4.func_78787_b(128, 64);
    this.lltentacle4.field_78809_i = true;
    setRotation(this.lltentacle4, 0.0F, 0.0F, 0.0F);
    this.lowerbody = new ModelRenderer(this, 8, 44);
    this.lowerbody.func_78789_a(0.0F, 0.0F, 0.0F, 8, 8, 4);
    this.lowerbody.func_78793_a(0.0F, 22.0F, 1.0F);
    this.lowerbody.func_78787_b(128, 64);
    this.lowerbody.field_78809_i = true;
    setRotation(this.lowerbody, 0.0F, 0.0F, 0.0F);
    this.body.func_78792_a(this.lowerbody);
  }
  
  public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    super.func_78088_a(entity, f, f1, f2, f3, f4, f5);
    func_78087_a(f, f1, f2, f3, f4, f5, entity);
    this.head.func_78785_a(f5);
    this.body.func_78785_a(f5);
    this.rightarm1.func_78785_a(f5);
    this.leftarm1.func_78785_a(f5);
    this.rltentacle1.func_78785_a(f5);
    this.rltentacle2.func_78785_a(f5);
    this.rltentacle3.func_78785_a(f5);
    this.rltentacle4.func_78785_a(f5);
    this.lltentacle1.func_78785_a(f5);
    this.lltentacle2.func_78785_a(f5);
    this.lltentacle3.func_78785_a(f5);
    this.lltentacle4.func_78785_a(f5);
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
    this.rightarm1.field_78795_f = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.leftarm1.field_78795_f = MathHelper.func_76134_b(f * 0.6662F) * 2.0F * f1 * 0.5F;
    this.rightarm1.field_78808_h = 0.0F;
    this.leftarm1.field_78808_h = 0.0F;
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
    this.rltentacle1.field_78795_f = MathHelper.func_76134_b(f * 0.6662F) * 1.4F * f1;
    this.rltentacle1.field_78808_h = MathHelper.func_76134_b(f * 0.6662F) * 1.4F * f1;
    this.rltentacle1.field_78796_g = 0.0F;
    this.rltentacle2.field_78795_f = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 1.4F * f1;
    this.rltentacle2.field_78808_h = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 1.4F * f1;
    this.rltentacle2.field_78796_g = 0.0F;
    this.rltentacle3.field_78795_f = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 1.4F * f1;
    this.rltentacle3.field_78808_h = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 1.4F * f1;
    this.rltentacle3.field_78796_g = 0.0F;
    this.rltentacle4.field_78795_f = MathHelper.func_76134_b(f * 0.6662F) * 1.4F * f1;
    this.rltentacle4.field_78808_h = MathHelper.func_76134_b(f * 0.6662F) * 1.4F * f1;
    this.rltentacle4.field_78796_g = 0.0F;
    this.lltentacle1.field_78795_f = MathHelper.func_76134_b(f * 0.6662F) * 1.4F * f1;
    this.lltentacle1.field_78808_h = -MathHelper.func_76134_b(f * 0.6662F) * 1.4F * f1;
    this.lltentacle1.field_78796_g = 0.0F;
    this.lltentacle2.field_78795_f = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 1.4F * f1;
    this.lltentacle2.field_78808_h = -MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 1.4F * f1;
    this.lltentacle2.field_78796_g = 0.0F;
    this.lltentacle3.field_78795_f = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 1.4F * f1;
    this.lltentacle3.field_78808_h = -MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 1.4F * f1;
    this.lltentacle3.field_78796_g = 0.0F;
    this.lltentacle4.field_78795_f = MathHelper.func_76134_b(f * 0.6662F) * 1.4F * f1;
    this.lltentacle4.field_78808_h = -MathHelper.func_76134_b(f * 0.6662F) * 1.4F * f1;
    this.lltentacle4.field_78796_g = 0.0F;
    this.rightarm1.field_78796_g = 0.0F;
    this.leftarm1.field_78796_g = 0.0F;
    if (this.field_78095_p > -9990.0F) {
      float f6 = this.field_78095_p;
      this.body.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightarm1.field_78796_g += this.body.field_78796_g;
      this.leftarm1.field_78796_g += this.body.field_78796_g;
      f6 = 1.0F - this.field_78095_p;
      f6 *= f6;
      f6 *= f6;
      f6 = 1.0F - f6;
      float f7 = MathHelper.func_76126_a(f6 * 3.1415927F);
      float f8 = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F) * -(this.head.field_78795_f - 0.7F) * 0.75F;
      this.rightarm1.field_78795_f = (float)(this.rightarm1.field_78795_f - f7 * 1.2D + f8);
      this.rightarm1.field_78796_g += this.body.field_78796_g * 2.0F;
      this.rightarm1.field_78808_h = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F) * -0.4F;
      this.leftarm1.field_78795_f = (float)(this.leftarm1.field_78795_f - f7 * 1.2D + f8);
      this.leftarm1.field_78796_g += this.body.field_78796_g * -2.0F;
      this.leftarm1.field_78808_h = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F) * 0.4F;
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
      this.rightarm1.field_78796_g = entity.bookSpread - 1.0F;
      this.leftarm1.field_78796_g = -entity.bookSpread + 1.0F;
      this.rightarm1.field_78808_h = 0.0F;
      this.leftarm1.field_78808_h = 0.0F;
      this.rightarm1.field_78795_f = -1.25F + 0.1F + MathHelper.func_76126_a(entity.field_70173_aa * 0.1F) * 0.01F;
      this.leftarm1.field_78795_f = -1.25F + 0.1F + MathHelper.func_76126_a(entity.field_70173_aa * 0.1F) * 0.01F;
    } 
  }
}
