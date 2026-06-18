package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityCommandBlockWither;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelCommandBlockWither extends ModelBase {
  public ModelRenderer Supporter;
  
  public ModelRenderer MainHead;
  
  public ModelRenderer spine;
  
  public ModelRenderer LeftHead;
  
  public ModelRenderer RightHead;
  
  public ModelRenderer lowerspine;
  
  public ModelRenderer Rib1;
  
  public ModelRenderer Rib2;
  
  public ModelRenderer Rib3;
  
  public ModelRenderer commandblock;
  
  public ModelRenderer Rib11;
  
  public ModelRenderer Rib12;
  
  public ModelRenderer Rib21;
  
  public ModelRenderer Rib22;
  
  public ModelRenderer Rib31;
  
  public ModelRenderer Rib32;
  
  public ModelCommandBlockWither() {
    this.field_78090_t = 64;
    this.field_78089_u = 64;
    this.Supporter = new ModelRenderer(this, 0, 16);
    this.Supporter.func_78793_a(0.0F, 1.0F, 0.0F);
    this.Supporter.func_78790_a(-10.0F, 3.9F, -0.5F, 20, 3, 3, 0.0F);
    this.spine = new ModelRenderer(this, 0, 22);
    this.spine.func_78793_a(0.0F, 6.9F, -0.5F);
    this.spine.func_78790_a(-1.5F, 0.0F, 0.0F, 3, 10, 3, 0.0F);
    this.Rib21 = new ModelRenderer(this, 24, 22);
    this.Rib21.func_78793_a(6.0F, 5.0F, 2.5F);
    this.Rib21.func_78790_a(0.0F, -1.0F, -1.0F, 9, 2, 2, 0.0F);
    setRotateAngle(this.Rib21, 0.0F, 1.5707964F, 0.0F);
    this.Rib22 = new ModelRenderer(this, 24, 22);
    this.Rib22.func_78793_a(-3.0F, 5.0F, 2.5F);
    this.Rib22.func_78790_a(0.0F, -1.0F, -1.0F, 9, 2, 2, 0.0F);
    setRotateAngle(this.Rib22, 0.0F, 1.5707964F, 0.0F);
    this.Rib2 = new ModelRenderer(this, 24, 22);
    this.Rib2.func_78793_a(-1.5F, 0.0F, 0.0F);
    this.Rib2.func_78790_a(-4.0F, 4.0F, 0.5F, 11, 2, 2, 0.0F);
    this.Rib12 = new ModelRenderer(this, 24, 22);
    this.Rib12.func_78793_a(-3.0F, 2.5F, 2.5F);
    this.Rib12.func_78790_a(0.0F, -1.0F, -1.0F, 9, 2, 2, 0.0F);
    setRotateAngle(this.Rib12, 0.0F, 1.5707964F, 0.0F);
    this.LeftHead = new ModelRenderer(this, 32, 0);
    this.LeftHead.field_78809_i = true;
    this.LeftHead.func_78793_a(9.0F, 3.0F, 0.0F);
    this.LeftHead.func_78790_a(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
    this.Rib1 = new ModelRenderer(this, 24, 22);
    this.Rib1.func_78793_a(-1.5F, 0.0F, 0.0F);
    this.Rib1.func_78790_a(-4.0F, 1.5F, 0.5F, 11, 2, 2, 0.0F);
    this.Rib32 = new ModelRenderer(this, 24, 22);
    this.Rib32.func_78793_a(-3.0F, 7.5F, 2.5F);
    this.Rib32.func_78790_a(0.0F, -1.0F, -1.0F, 9, 2, 2, 0.0F);
    setRotateAngle(this.Rib32, 0.0F, 1.5707964F, 0.0F);
    this.lowerspine = new ModelRenderer(this, 12, 22);
    this.lowerspine.func_78793_a(0.0F, 10.0F, 0.0F);
    this.lowerspine.func_78790_a(-1.5F, 0.0F, 0.0F, 3, 6, 3, 0.0F);
    this.Rib31 = new ModelRenderer(this, 24, 22);
    this.Rib31.func_78793_a(6.0F, 7.5F, 2.5F);
    this.Rib31.func_78790_a(0.0F, -1.0F, -1.0F, 9, 2, 2, 0.0F);
    setRotateAngle(this.Rib31, 0.0F, 1.5707964F, 0.0F);
    this.commandblock = new ModelRenderer(this, 32, 48);
    this.commandblock.func_78793_a(0.0F, 5.0F, -2.0F);
    this.commandblock.func_78790_a(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F);
    this.MainHead = new ModelRenderer(this, 0, 0);
    this.MainHead.func_78793_a(0.0F, 5.0F, 0.0F);
    this.MainHead.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
    this.Rib3 = new ModelRenderer(this, 24, 22);
    this.Rib3.func_78793_a(-1.5F, 0.0F, 0.0F);
    this.Rib3.func_78790_a(-4.0F, 6.5F, 0.5F, 11, 2, 2, 0.0F);
    this.Rib11 = new ModelRenderer(this, 24, 22);
    this.Rib11.func_78793_a(6.0F, 2.5F, 2.5F);
    this.Rib11.func_78790_a(0.0F, -1.0F, -1.0F, 9, 2, 2, 0.0F);
    setRotateAngle(this.Rib11, 0.0F, 1.5707964F, 0.0F);
    this.RightHead = new ModelRenderer(this, 32, 0);
    this.RightHead.func_78793_a(-9.0F, 3.0F, 0.0F);
    this.RightHead.func_78790_a(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
    this.Supporter.func_78792_a(this.spine);
    this.Rib2.func_78792_a(this.Rib21);
    this.Rib2.func_78792_a(this.Rib22);
    this.spine.func_78792_a(this.Rib2);
    this.Rib1.func_78792_a(this.Rib12);
    this.Supporter.func_78792_a(this.LeftHead);
    this.spine.func_78792_a(this.Rib1);
    this.Rib3.func_78792_a(this.Rib32);
    this.spine.func_78792_a(this.lowerspine);
    this.Rib3.func_78792_a(this.Rib31);
    this.spine.func_78792_a(this.commandblock);
    this.spine.func_78792_a(this.Rib3);
    this.Rib1.func_78792_a(this.Rib11);
    this.Supporter.func_78792_a(this.RightHead);
  }
  
  public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    this.Supporter.func_78785_a(f5);
    this.MainHead.func_78785_a(f5);
  }
  
  public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
    modelRenderer.field_78795_f = x;
    modelRenderer.field_78796_g = y;
    modelRenderer.field_78808_h = z;
  }
  
  public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
    this.MainHead.field_78796_g = p_78087_4_ / 57.295776F;
    this.MainHead.field_78795_f = p_78087_5_ / 57.295776F;
    EntityCommandBlockWither wither = (EntityCommandBlockWither)p_78087_7_;
    this.MainHead.field_78808_h = 0.0F;
    this.LeftHead.field_78808_h = 0.0F;
    this.RightHead.field_78808_h = 0.0F;
    this.spine.field_78795_f = 0.0F;
    this.lowerspine.field_78795_f = 0.0F;
    this.spine.field_78808_h = 0.0F;
    this.lowerspine.field_78808_h = 0.0F;
    if (wither.getJukeboxToDanceTo() != null) {
      this.MainHead.field_78795_f += MathHelper.func_76134_b(p_78087_3_ * 1.0F) * 0.5F;
      this.MainHead.field_78808_h += MathHelper.func_76134_b(p_78087_3_ * 0.5F) * 0.5F;
      this.LeftHead.field_78808_h += MathHelper.func_76134_b(p_78087_3_ * 0.5F - 2.0F) * 0.5F;
      this.RightHead.field_78808_h -= MathHelper.func_76134_b(p_78087_3_ * 0.5F - 2.0F) * 0.5F;
      this.spine.field_78795_f += MathHelper.func_76134_b(p_78087_3_ * 0.5F) * 0.5F;
      this.lowerspine.field_78795_f += MathHelper.func_76134_b(p_78087_3_ * 0.5F + 1.0F) * 0.5F;
      this.spine.field_78808_h += MathHelper.func_76134_b(p_78087_3_ * 0.25F) * 0.5F;
      this.lowerspine.field_78808_h += MathHelper.func_76134_b(p_78087_3_ * 0.25F + 1.0F) * 0.5F;
    } 
    if (wither.func_70093_af()) {
      float f6 = MathHelper.func_76134_b(p_78087_3_ * 0.25F);
      this.spine.field_78795_f += (0.35F + 0.025F * f6) * 3.1415927F;
      f6 = MathHelper.func_76134_b(p_78087_3_ * 0.25F - 1.5F);
      this.lowerspine.field_78795_f += (0.35F + 0.05F * f6) * 3.1415927F;
      f6 = MathHelper.func_76134_b(p_78087_3_ * 0.275F - 1.0F);
      this.LeftHead.field_78795_f += (-0.01F + 0.01F * f6) * 3.1415927F;
      this.RightHead.field_78795_f += (-0.01F + 0.01F * f6) * 3.1415927F;
    } else {
      float f6 = MathHelper.func_76134_b(p_78087_3_ * 0.1F);
      this.spine.field_78795_f += (0.065F + 0.05F * f6) * 3.1415927F;
      f6 = MathHelper.func_76134_b(p_78087_3_ * 0.1F - 1.5F);
      this.lowerspine.field_78795_f += (0.3F + 0.1F * f6) * 3.1415927F;
      f6 = MathHelper.func_76134_b(p_78087_3_ * 0.125F - 1.0F);
      this.LeftHead.field_78795_f += (-0.01F + 0.01F * f6) * 3.1415927F;
      this.RightHead.field_78795_f += (-0.01F + 0.01F * f6) * 3.1415927F;
    } 
  }
  
  public void func_78086_a(EntityLivingBase p_78086_1_, float p_78086_2_, float p_78086_3_, float p_78086_4_) {
    EntityCommandBlockWither entitywither = (EntityCommandBlockWither)p_78086_1_;
    this.RightHead.field_78796_g = (entitywither.func_82207_a(1) - p_78086_1_.field_70761_aq) / 57.295776F;
    this.RightHead.field_78795_f = entitywither.func_82210_r(1) / 57.295776F;
    this.LeftHead.field_78796_g = (entitywither.func_82207_a(1) - p_78086_1_.field_70761_aq) / 57.295776F;
    this.LeftHead.field_78795_f = entitywither.func_82210_r(1) / 57.295776F;
  }
}
