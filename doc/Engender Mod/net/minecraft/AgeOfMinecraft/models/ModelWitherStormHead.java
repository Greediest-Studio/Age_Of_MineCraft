package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStormHead;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelWitherStormHead extends ModelBase {
  public ModelRenderer BackMouth;
  
  public ModelRenderer Jaw;
  
  public ModelRenderer Head;
  
  public ModelRenderer JawRight;
  
  public ModelRenderer JawLeft;
  
  public ModelRenderer JawFront;
  
  public ModelRenderer BottomJawTooth1;
  
  public ModelRenderer BottomJawTooth5;
  
  public ModelRenderer BottomJawTooth9;
  
  public ModelRenderer JawRight_1;
  
  public ModelRenderer JawLeft_1;
  
  public ModelRenderer BottomJawTooth2;
  
  public ModelRenderer BottomJawTooth3;
  
  public ModelRenderer BottomJawTooth4;
  
  public ModelRenderer BottomJawTooth6;
  
  public ModelRenderer BottomJawTooth7;
  
  public ModelRenderer BottomJawTooth8;
  
  public ModelRenderer BottomJawTooth10;
  
  public ModelRenderer BottomJawTooth11;
  
  public ModelRenderer BottomJawTooth12;
  
  public ModelRenderer Face;
  
  public ModelRenderer HeadRightSide;
  
  public ModelRenderer HeadLeftSide;
  
  public ModelRenderer HeadTop;
  
  public ModelRenderer UpperJawTooth1;
  
  public ModelRenderer UpperJawTooth5;
  
  public ModelRenderer UpperJawTooth9;
  
  public ModelRenderer UpperJawTooth2;
  
  public ModelRenderer UpperJawTooth3;
  
  public ModelRenderer UpperJawTooth4;
  
  public ModelRenderer UpperJawTooth6;
  
  public ModelRenderer UpperJawTooth7;
  
  public ModelRenderer UpperJawTooth8;
  
  public ModelRenderer UpperJawTooth10;
  
  public ModelRenderer UpperJawTooth11;
  
  public ModelRenderer UpperJawTooth12;
  
  public ModelWitherStormHead() {
    this.field_78090_t = 256;
    this.field_78089_u = 256;
    this.UpperJawTooth4 = new ModelRenderer(this, 0, 88);
    this.UpperJawTooth4.func_78793_a(0.0F, 0.0F, -12.0F);
    this.UpperJawTooth4.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 4, 4, 0.0F);
    this.JawFront = new ModelRenderer(this, 0, 0);
    this.JawFront.func_78793_a(0.0F, 0.0F, 0.0F);
    this.JawFront.func_78790_a(-20.0F, 0.0F, -56.0F, 40, 12, 8, 0.0F);
    this.HeadLeftSide = new ModelRenderer(this, 0, 0);
    this.HeadLeftSide.func_78793_a(0.0F, 0.0F, 0.0F);
    this.HeadLeftSide.func_78790_a(20.0F, -28.0F, -40.0F, 8, 32, 48, 0.0F);
    this.UpperJawTooth5 = new ModelRenderer(this, 0, 88);
    this.UpperJawTooth5.func_78793_a(24.0F, 4.0F, 0.0F);
    this.UpperJawTooth5.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 4, 4, 0.0F);
    this.JawLeft = new ModelRenderer(this, 0, 0);
    this.JawLeft.func_78793_a(0.0F, 0.0F, 0.0F);
    this.JawLeft.func_78790_a(20.0F, 0.0F, -48.0F, 8, 12, 48, 0.0F);
    this.Face = new ModelRenderer(this, 0, 96);
    this.Face.func_78793_a(0.0F, 0.0F, 0.0F);
    this.Face.func_78790_a(-20.0F, -28.0F, -48.0F, 40, 32, 8, 0.0F);
    this.UpperJawTooth10 = new ModelRenderer(this, 0, 88);
    this.UpperJawTooth10.func_78793_a(-10.0F, 0.0F, 0.0F);
    this.UpperJawTooth10.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 4, 4, 0.0F);
    this.BottomJawTooth9 = new ModelRenderer(this, 0, 88);
    this.BottomJawTooth9.func_78793_a(16.0F, 0.0F, -52.0F);
    this.BottomJawTooth9.func_78790_a(-2.0F, -4.0F, -2.0F, 4, 4, 4, 0.0F);
    this.JawLeft_1 = new ModelRenderer(this, 0, 0);
    this.JawLeft_1.func_78793_a(0.0F, 0.0F, 0.0F);
    this.JawLeft_1.func_78790_a(20.0F, 0.0F, -48.0F, 8, 12, 48, 0.0F);
    this.Head = new ModelRenderer(this, 0, 0);
    this.Head.func_78793_a(0.0F, 0.0F, -12.0F);
    this.Head.func_78790_a(-20.0F, -28.0F, -40.0F, 40, 32, 48, 0.0F);
    this.UpperJawTooth1 = new ModelRenderer(this, 0, 88);
    this.UpperJawTooth1.func_78793_a(-24.0F, 4.0F, 0.0F);
    this.UpperJawTooth1.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 4, 4, 0.0F);
    this.BottomJawTooth6 = new ModelRenderer(this, 0, 88);
    this.BottomJawTooth6.func_78793_a(0.0F, 0.0F, -12.0F);
    this.BottomJawTooth6.func_78790_a(-2.0F, -4.0F, -2.0F, 4, 4, 4, 0.0F);
    this.UpperJawTooth12 = new ModelRenderer(this, 0, 88);
    this.UpperJawTooth12.func_78793_a(-10.0F, 0.0F, 0.0F);
    this.UpperJawTooth12.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 4, 4, 0.0F);
    this.BottomJawTooth2 = new ModelRenderer(this, 0, 88);
    this.BottomJawTooth2.func_78793_a(0.0F, 0.0F, -12.0F);
    this.BottomJawTooth2.func_78790_a(-2.0F, -4.0F, -2.0F, 4, 4, 4, 0.0F);
    this.UpperJawTooth2 = new ModelRenderer(this, 0, 88);
    this.UpperJawTooth2.func_78793_a(0.0F, 0.0F, -12.0F);
    this.UpperJawTooth2.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 4, 4, 0.0F);
    this.HeadRightSide = new ModelRenderer(this, 0, 0);
    this.HeadRightSide.func_78793_a(0.0F, 0.0F, 0.0F);
    this.HeadRightSide.func_78790_a(-28.0F, -28.0F, -40.0F, 8, 32, 48, 0.0F);
    this.BottomJawTooth10 = new ModelRenderer(this, 0, 88);
    this.BottomJawTooth10.func_78793_a(-10.0F, 0.0F, 0.0F);
    this.BottomJawTooth10.func_78790_a(-2.0F, -4.0F, -2.0F, 4, 4, 4, 0.0F);
    this.BottomJawTooth1 = new ModelRenderer(this, 0, 88);
    this.BottomJawTooth1.func_78793_a(-24.0F, 0.0F, -8.0F);
    this.BottomJawTooth1.func_78790_a(-2.0F, -4.0F, -2.0F, 4, 4, 4, 0.0F);
    this.BottomJawTooth7 = new ModelRenderer(this, 0, 88);
    this.BottomJawTooth7.func_78793_a(0.0F, 0.0F, -12.0F);
    this.BottomJawTooth7.func_78790_a(-2.0F, -4.0F, -2.0F, 4, 4, 4, 0.0F);
    this.JawRight = new ModelRenderer(this, 0, 0);
    this.JawRight.func_78793_a(0.0F, 0.0F, 0.0F);
    this.JawRight.func_78790_a(-28.0F, 0.0F, -48.0F, 8, 12, 48, 0.0F);
    this.BottomJawTooth8 = new ModelRenderer(this, 0, 88);
    this.BottomJawTooth8.func_78793_a(0.0F, 0.0F, -12.0F);
    this.BottomJawTooth8.func_78790_a(-2.0F, -4.0F, -2.0F, 4, 4, 4, 0.0F);
    this.BottomJawTooth3 = new ModelRenderer(this, 0, 88);
    this.BottomJawTooth3.func_78793_a(0.0F, 0.0F, -12.0F);
    this.BottomJawTooth3.func_78790_a(-2.0F, -4.0F, -2.0F, 4, 4, 4, 0.0F);
    this.UpperJawTooth11 = new ModelRenderer(this, 0, 88);
    this.UpperJawTooth11.func_78793_a(-10.0F, 0.0F, 0.0F);
    this.UpperJawTooth11.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 4, 4, 0.0F);
    this.BottomJawTooth4 = new ModelRenderer(this, 0, 88);
    this.BottomJawTooth4.func_78793_a(0.0F, 0.0F, -12.0F);
    this.BottomJawTooth4.func_78790_a(-2.0F, -4.0F, -2.0F, 4, 4, 4, 0.0F);
    this.JawRight_1 = new ModelRenderer(this, 0, 0);
    this.JawRight_1.func_78793_a(0.0F, 0.0F, 0.0F);
    this.JawRight_1.func_78790_a(-28.0F, 0.0F, -48.0F, 8, 12, 48, 0.0F);
    this.UpperJawTooth3 = new ModelRenderer(this, 0, 88);
    this.UpperJawTooth3.func_78793_a(0.0F, 0.0F, -12.0F);
    this.UpperJawTooth3.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 4, 4, 0.0F);
    this.BackMouth = new ModelRenderer(this, 0, 0);
    this.BackMouth.func_78793_a(0.0F, 0.0F, -18.0F);
    this.BackMouth.func_78790_a(-28.0F, -28.0F, -8.0F, 56, 54, 8, 0.0F);
    this.Jaw = new ModelRenderer(this, 0, 0);
    this.Jaw.func_78793_a(0.0F, 14.0F, -4.0F);
    this.Jaw.func_78790_a(-20.0F, 0.0F, -48.0F, 40, 12, 48, 0.0F);
    this.UpperJawTooth6 = new ModelRenderer(this, 0, 88);
    this.UpperJawTooth6.func_78793_a(0.0F, 0.0F, -12.0F);
    this.UpperJawTooth6.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 4, 4, 0.0F);
    this.UpperJawTooth7 = new ModelRenderer(this, 0, 88);
    this.UpperJawTooth7.func_78793_a(0.0F, 0.0F, -12.0F);
    this.UpperJawTooth7.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 4, 4, 0.0F);
    this.UpperJawTooth8 = new ModelRenderer(this, 0, 88);
    this.UpperJawTooth8.func_78793_a(0.0F, 0.0F, -12.0F);
    this.UpperJawTooth8.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 4, 4, 0.0F);
    this.BottomJawTooth5 = new ModelRenderer(this, 0, 88);
    this.BottomJawTooth5.func_78793_a(24.0F, 0.0F, -8.0F);
    this.BottomJawTooth5.func_78790_a(-2.0F, -4.0F, -2.0F, 4, 4, 4, 0.0F);
    this.HeadTop = new ModelRenderer(this, 0, 0);
    this.HeadTop.func_78793_a(0.0F, 0.0F, 0.0F);
    this.HeadTop.func_78790_a(-20.0F, -36.0F, -40.0F, 40, 8, 48, 0.0F);
    this.BottomJawTooth11 = new ModelRenderer(this, 0, 88);
    this.BottomJawTooth11.func_78793_a(-10.0F, 0.0F, 0.0F);
    this.BottomJawTooth11.func_78790_a(-2.0F, -4.0F, -2.0F, 4, 4, 4, 0.0F);
    this.UpperJawTooth9 = new ModelRenderer(this, 0, 88);
    this.UpperJawTooth9.func_78793_a(16.0F, 4.0F, -44.0F);
    this.UpperJawTooth9.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 4, 4, 0.0F);
    this.BottomJawTooth12 = new ModelRenderer(this, 0, 88);
    this.BottomJawTooth12.func_78793_a(-10.0F, 0.0F, 0.0F);
    this.BottomJawTooth12.func_78790_a(-2.0F, -4.0F, -2.0F, 4, 4, 4, 0.0F);
    this.UpperJawTooth3.func_78792_a(this.UpperJawTooth4);
    this.Jaw.func_78792_a(this.JawFront);
    this.Head.func_78792_a(this.HeadLeftSide);
    this.Head.func_78792_a(this.UpperJawTooth5);
    this.Jaw.func_78792_a(this.JawLeft);
    this.Head.func_78792_a(this.Face);
    this.UpperJawTooth9.func_78792_a(this.UpperJawTooth10);
    this.Jaw.func_78792_a(this.BottomJawTooth9);
    this.JawFront.func_78792_a(this.JawLeft_1);
    this.BackMouth.func_78792_a(this.Head);
    this.Head.func_78792_a(this.UpperJawTooth1);
    this.BottomJawTooth5.func_78792_a(this.BottomJawTooth6);
    this.UpperJawTooth11.func_78792_a(this.UpperJawTooth12);
    this.BottomJawTooth1.func_78792_a(this.BottomJawTooth2);
    this.UpperJawTooth1.func_78792_a(this.UpperJawTooth2);
    this.Head.func_78792_a(this.HeadRightSide);
    this.BottomJawTooth9.func_78792_a(this.BottomJawTooth10);
    this.Jaw.func_78792_a(this.BottomJawTooth1);
    this.BottomJawTooth6.func_78792_a(this.BottomJawTooth7);
    this.Jaw.func_78792_a(this.JawRight);
    this.BottomJawTooth7.func_78792_a(this.BottomJawTooth8);
    this.BottomJawTooth2.func_78792_a(this.BottomJawTooth3);
    this.UpperJawTooth10.func_78792_a(this.UpperJawTooth11);
    this.BottomJawTooth3.func_78792_a(this.BottomJawTooth4);
    this.JawFront.func_78792_a(this.JawRight_1);
    this.UpperJawTooth2.func_78792_a(this.UpperJawTooth3);
    this.BackMouth.func_78792_a(this.Jaw);
    this.UpperJawTooth5.func_78792_a(this.UpperJawTooth6);
    this.UpperJawTooth6.func_78792_a(this.UpperJawTooth7);
    this.UpperJawTooth7.func_78792_a(this.UpperJawTooth8);
    this.Jaw.func_78792_a(this.BottomJawTooth5);
    this.Head.func_78792_a(this.HeadTop);
    this.BottomJawTooth10.func_78792_a(this.BottomJawTooth11);
    this.Head.func_78792_a(this.UpperJawTooth9);
    this.BottomJawTooth11.func_78792_a(this.BottomJawTooth12);
  }
  
  public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    this.BackMouth.func_78785_a(f5);
  }
  
  public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
    modelRenderer.field_78795_f = x;
    modelRenderer.field_78796_g = y;
    modelRenderer.field_78808_h = z;
  }
  
  public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
    this.BackMouth.field_78796_g = p_78087_4_ / 57.295776F;
    this.BackMouth.field_78795_f = p_78087_5_ / 57.295776F;
    this.BackMouth.field_78808_h = 0.0F;
    EntityWitherStormHead wither = (EntityWitherStormHead)p_78087_7_;
    if (wither.getJukeboxToDanceTo() != null) {
      this.BackMouth.field_78795_f += MathHelper.func_76134_b(p_78087_3_ * 0.5F) * 0.5F;
      this.BackMouth.field_78808_h += MathHelper.func_76134_b(p_78087_3_ * 0.25F) * 0.5F;
    } 
  }
  
  public void func_78086_a(EntityLivingBase p_78086_1_, float p_78086_2_, float p_78086_3_, float p_78086_4_) {
    EntityWitherStormHead entity = (EntityWitherStormHead)p_78086_1_;
    if (entity.openMouthCounter > 0) {
      this.Jaw.field_78795_f = 0.5F;
    } else {
      this.Jaw.field_78795_f = 0.0F;
    } 
  }
}
