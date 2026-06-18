package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityBat;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelBat extends ModelBase {
  private ModelRenderer batHead;
  
  private ModelRenderer batBody;
  
  private ModelRenderer batRightWing;
  
  private ModelRenderer batLeftWing;
  
  private ModelRenderer batOuterRightWing;
  
  private ModelRenderer batOuterLeftWing;
  
  public ModelBat() {
    this.field_78090_t = 64;
    this.field_78089_u = 64;
    this.batHead = new ModelRenderer(this, 0, 0);
    this.batHead.func_78789_a(-3.0F, -3.0F, -3.0F, 6, 6, 6);
    ModelRenderer modelrenderer = new ModelRenderer(this, 24, 0);
    modelrenderer.func_78789_a(-4.0F, -6.0F, -2.0F, 3, 4, 1);
    this.batHead.func_78792_a(modelrenderer);
    ModelRenderer modelrenderer1 = new ModelRenderer(this, 24, 0);
    modelrenderer1.field_78809_i = true;
    modelrenderer1.func_78789_a(1.0F, -6.0F, -2.0F, 3, 4, 1);
    this.batHead.func_78792_a(modelrenderer1);
    this.batBody = new ModelRenderer(this, 0, 16);
    this.batBody.func_78789_a(-3.0F, 4.0F, -3.0F, 6, 12, 6);
    this.batBody.func_78784_a(0, 34).func_78789_a(-5.0F, 16.0F, 0.0F, 10, 6, 1);
    this.batRightWing = new ModelRenderer(this, 42, 0);
    this.batRightWing.func_78789_a(-12.0F, 1.0F, 1.5F, 10, 16, 1);
    this.batOuterRightWing = new ModelRenderer(this, 24, 16);
    this.batOuterRightWing.func_78793_a(-12.0F, 1.0F, 1.5F);
    this.batOuterRightWing.func_78789_a(-8.0F, 1.0F, 0.0F, 8, 12, 1);
    this.batLeftWing = new ModelRenderer(this, 42, 0);
    this.batLeftWing.field_78809_i = true;
    this.batLeftWing.func_78789_a(2.0F, 1.0F, 1.5F, 10, 16, 1);
    this.batOuterLeftWing = new ModelRenderer(this, 24, 16);
    this.batOuterLeftWing.field_78809_i = true;
    this.batOuterLeftWing.func_78793_a(12.0F, 1.0F, 1.5F);
    this.batOuterLeftWing.func_78789_a(0.0F, 1.0F, 0.0F, 8, 12, 1);
    this.batBody.func_78792_a(this.batRightWing);
    this.batBody.func_78792_a(this.batLeftWing);
    this.batRightWing.func_78792_a(this.batOuterRightWing);
    this.batLeftWing.func_78792_a(this.batOuterLeftWing);
  }
  
  public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
    func_78087_a(p_78088_2_, p_78088_3_, ((EntityTameBase)p_78088_1_).func_175446_cd() ? 0.0F : p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
    this.batHead.func_78785_a(p_78088_7_);
    this.batBody.func_78785_a(p_78088_7_);
  }
  
  public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
    EntityBat entity = (EntityBat)p_78087_7_;
    if (entity.func_175446_cd())
      p_78087_3_ = 1.0F; 
    if (entity.getIsBatHanging()) {
      this.batHead.field_78795_f = p_78087_5_ / 57.295776F;
      this.batHead.field_78796_g = 3.1415927F - p_78087_4_ / 57.295776F;
      this.batHead.field_78808_h = 3.1415927F;
      this.batHead.func_78793_a(0.0F, -2.0F, 0.0F);
      this.batRightWing.func_78793_a(-3.0F, 0.0F, 3.0F);
      this.batLeftWing.func_78793_a(3.0F, 0.0F, 3.0F);
      this.batBody.field_78795_f = 3.1415927F;
      this.batRightWing.field_78795_f = -0.15707964F;
      this.batRightWing.field_78796_g = -1.2566371F;
      this.batOuterRightWing.field_78796_g = -1.7278761F;
      this.batLeftWing.field_78795_f = this.batRightWing.field_78795_f;
      this.batLeftWing.field_78796_g = -this.batRightWing.field_78796_g;
      this.batOuterLeftWing.field_78796_g = -this.batOuterRightWing.field_78796_g;
      if (!entity.getCurrentBook().func_190926_b()) {
        this.batHead.field_78796_g += 3.1415927F;
        this.batHead.field_78795_f = -(p_78087_5_ / 57.295776F);
      } 
    } else {
      this.batHead.field_78795_f = p_78087_5_ / 57.295776F;
      this.batHead.field_78796_g = p_78087_4_ / 57.295776F;
      this.batHead.field_78808_h = 0.0F;
      this.batHead.func_78793_a(0.0F, 0.0F, 0.0F);
      this.batRightWing.func_78793_a(0.0F, 0.0F, 0.0F);
      this.batLeftWing.func_78793_a(0.0F, 0.0F, 0.0F);
      this.batBody.field_78795_f = 0.7853982F + MathHelper.func_76134_b(p_78087_3_ * 0.1F) * 0.15F;
      this.batBody.field_78796_g = 0.0F;
      this.batRightWing.field_78796_g = MathHelper.func_76134_b(p_78087_3_ * 1.3F) * 3.1415927F * 0.25F;
      this.batLeftWing.field_78796_g = -this.batRightWing.field_78796_g;
      this.batRightWing.field_78796_g *= 0.5F;
      this.batOuterLeftWing.field_78796_g = -this.batRightWing.field_78796_g * 0.5F;
    } 
  }
}
