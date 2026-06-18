package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelPortal extends ModelBase {
  public ModelRenderer Base;
  
  public ModelRenderer Edge1;
  
  public ModelRenderer Edge2;
  
  public ModelRenderer Edge3;
  
  public ModelRenderer Edge4;
  
  public ModelRenderer Spike1;
  
  public ModelRenderer Spike2;
  
  public ModelRenderer Spike3;
  
  public ModelRenderer Spike4;
  
  public boolean onlybase;
  
  public ModelPortal() {
    this(false);
  }
  
  public ModelPortal(boolean onlybase) {
    this.onlybase = onlybase;
    this.field_78090_t = 64;
    this.field_78089_u = 64;
    this.Edge2 = new ModelRenderer(this, 0, 20);
    this.Edge2.func_78793_a(0.0F, 24.0F, 0.0F);
    this.Edge2.func_78790_a(-12.0F, -6.0F, -8.0F, 4, 4, 16, 0.0F);
    this.Edge3 = new ModelRenderer(this, 0, 20);
    this.Edge3.func_78793_a(0.0F, 24.0F, 0.0F);
    this.Edge3.func_78790_a(-12.0F, -6.0F, -8.0F, 4, 4, 16, 0.0F);
    setRotateAngle(this.Edge3, 0.0F, -3.1415927F, 0.0F);
    this.Edge1 = new ModelRenderer(this, 0, 20);
    this.Edge1.func_78793_a(0.0F, 24.0F, 0.0F);
    this.Edge1.func_78790_a(-12.0F, -6.0F, -8.0F, 4, 4, 16, 0.0F);
    setRotateAngle(this.Edge1, 0.0F, -1.5707964F, 0.0F);
    this.Spike4 = new ModelRenderer(this, 0, 44);
    this.Spike4.func_78793_a(9.0F, 22.0F, -9.0F);
    this.Spike4.func_78790_a(-2.0F, -16.0F, -2.0F, 4, 16, 4, 0.0F);
    setRotateAngle(this.Spike4, -0.43633232F, 2.3561945F, 0.0F);
    this.Spike3 = new ModelRenderer(this, 0, 44);
    this.Spike3.func_78793_a(-9.0F, 22.0F, -9.0F);
    this.Spike3.func_78790_a(-2.0F, -16.0F, -2.0F, 4, 16, 4, 0.0F);
    setRotateAngle(this.Spike3, -0.43633232F, -2.3561945F, 0.0F);
    this.Base = new ModelRenderer(this, 0, 0);
    this.Base.func_78793_a(0.0F, 24.0F, 0.0F);
    this.Base.func_78790_a(-8.0F, -4.0F, -8.0F, 16, 4, 16, 0.0F);
    this.Spike1 = new ModelRenderer(this, 0, 44);
    this.Spike1.func_78793_a(9.0F, 22.0F, 9.0F);
    this.Spike1.func_78790_a(-2.0F, -16.0F, -2.0F, 4, 16, 4, 0.0F);
    setRotateAngle(this.Spike1, -0.43633232F, 0.7853982F, 0.0F);
    this.Spike2 = new ModelRenderer(this, 0, 44);
    this.Spike2.func_78793_a(-9.0F, 22.0F, 9.0F);
    this.Spike2.func_78790_a(-2.0F, -16.0F, -2.0F, 4, 16, 4, 0.0F);
    setRotateAngle(this.Spike2, -0.43633232F, -0.7853982F, 0.0F);
    this.Edge4 = new ModelRenderer(this, 0, 20);
    this.Edge4.func_78793_a(0.0F, 24.0F, 0.0F);
    this.Edge4.func_78790_a(-12.0F, -6.0F, -8.0F, 4, 4, 16, 0.0F);
    setRotateAngle(this.Edge4, 0.0F, 1.5707964F, 0.0F);
  }
  
  public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    if (this.onlybase) {
      this.Base.func_78785_a(f5);
    } else {
      this.Base.func_78785_a(f5);
      this.Edge1.func_78785_a(f5);
      this.Edge2.func_78785_a(f5);
      this.Edge3.func_78785_a(f5);
      this.Edge4.func_78785_a(f5);
      this.Spike1.func_78785_a(f5);
      this.Spike2.func_78785_a(f5);
      this.Spike3.func_78785_a(f5);
      this.Spike4.func_78785_a(f5);
    } 
  }
  
  public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
    modelRenderer.field_78795_f = x;
    modelRenderer.field_78796_g = y;
    modelRenderer.field_78808_h = z;
  }
  
  public void func_78087_a(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    EntityTameBase entity = (EntityTameBase)entityIn;
    if (entity.field_70725_aQ > 0) {
      this.Spike1.field_78795_f = -0.4375F - entity.field_70725_aQ * 0.02F;
      this.Spike2.field_78795_f = -0.4375F - entity.field_70725_aQ * 0.02F;
      this.Spike3.field_78795_f = -0.4375F - entity.field_70725_aQ * 0.02F;
      this.Spike4.field_78795_f = -0.4375F - entity.field_70725_aQ * 0.02F;
    } else if (ageInTicks < 60.0F) {
      this.Spike1.field_78795_f = 1.6F - ageInTicks / 30.0F;
      this.Spike2.field_78795_f = 1.6F - ageInTicks / 30.0F;
      this.Spike3.field_78795_f = 1.6F - ageInTicks / 30.0F;
      this.Spike4.field_78795_f = 1.6F - ageInTicks / 30.0F;
    } else {
      float f6 = -MathHelper.func_76134_b(ageInTicks * 0.05F);
      this.Spike1.field_78795_f = -0.4375F + 0.021875F * f6 * 3.1415927F;
      this.Spike2.field_78795_f = -0.4375F + 0.021875F * f6 * 3.1415927F;
      this.Spike3.field_78795_f = -0.4375F + 0.021875F * f6 * 3.1415927F;
      this.Spike4.field_78795_f = -0.4375F + 0.021875F * f6 * 3.1415927F;
      if (entity.getJukeboxToDanceTo() != null) {
        f6 = -MathHelper.func_76134_b(ageInTicks * 0.25F);
        this.Spike1.field_78795_f = -0.4375F + MathHelper.func_76134_b(ageInTicks * 0.25F);
        this.Spike2.field_78795_f = -0.4375F + MathHelper.func_76134_b(ageInTicks * 0.25F);
        this.Spike3.field_78795_f = -0.4375F + MathHelper.func_76134_b(ageInTicks * 0.25F);
        this.Spike4.field_78795_f = -0.4375F + MathHelper.func_76134_b(ageInTicks * 0.25F);
      } 
    } 
  }
}
