package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityCreeder;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelCreeder extends ModelBase implements ICappedModel {
  public ModelRenderer body;
  
  public ModelRenderer rightleg1;
  
  public ModelRenderer rightleg2;
  
  public ModelRenderer rightleg3;
  
  public ModelRenderer leftleg1;
  
  public ModelRenderer leftleg2;
  
  public ModelRenderer leftleg3;
  
  public ModelRenderer head;
  
  public boolean isSneak;
  
  public ModelRenderer bipedCape;
  
  public ModelCreeder() {
    this(0.0F);
  }
  
  public ModelCreeder(float p_i46366_1_) {
    this.field_78090_t = 64;
    this.field_78089_u = 32;
    this.rightleg2 = new ModelRenderer(this, 18, 0);
    this.rightleg2.func_78793_a(-4.0F, 15.0F, 0.0F);
    this.rightleg2.func_78790_a(-15.0F, -1.0F, -1.0F, 16, 2, 2, p_i46366_1_);
    setRotateAngle(this.rightleg2, 0.0F, 0.0F, -0.58119464F);
    this.rightleg1 = new ModelRenderer(this, 18, 0);
    this.rightleg1.func_78793_a(-4.0F, 15.0F, -1.0F);
    this.rightleg1.func_78790_a(-15.0F, -1.0F, -1.0F, 16, 2, 2, p_i46366_1_);
    setRotateAngle(this.rightleg1, 0.0F, -0.7853982F, -0.7853982F);
    this.rightleg3 = new ModelRenderer(this, 18, 0);
    this.rightleg3.func_78793_a(-4.0F, 15.0F, 1.0F);
    this.rightleg3.func_78790_a(-15.0F, -1.0F, -1.0F, 16, 2, 2, p_i46366_1_);
    setRotateAngle(this.rightleg3, 0.0F, 0.7853982F, -0.7853982F);
    this.leftleg1 = new ModelRenderer(this, 18, 0);
    this.leftleg1.field_78809_i = true;
    this.leftleg1.func_78793_a(4.0F, 15.0F, -1.0F);
    this.leftleg1.func_78790_a(-1.0F, -1.0F, -1.0F, 16, 2, 2, p_i46366_1_);
    setRotateAngle(this.leftleg1, 0.0F, 0.7853982F, 0.7853982F);
    this.leftleg2 = new ModelRenderer(this, 18, 0);
    this.leftleg2.field_78809_i = true;
    this.leftleg2.func_78793_a(4.0F, 15.0F, 0.0F);
    this.leftleg2.func_78790_a(-1.0F, -1.0F, -1.0F, 16, 2, 2, p_i46366_1_);
    setRotateAngle(this.leftleg2, 0.0F, 0.0F, 0.58119464F);
    this.leftleg3 = new ModelRenderer(this, 18, 0);
    this.leftleg3.field_78809_i = true;
    this.leftleg3.func_78793_a(4.0F, 15.0F, 1.0F);
    this.leftleg3.func_78790_a(-1.0F, -1.0F, -1.0F, 16, 2, 2, p_i46366_1_);
    setRotateAngle(this.leftleg3, 0.0F, -0.7853982F, 0.7853982F);
    this.head = new ModelRenderer(this, 32, 4);
    this.head.func_78793_a(0.0F, 4.0F, 0.0F);
    this.head.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, p_i46366_1_);
    this.body = new ModelRenderer(this, 0, 4);
    this.body.func_78793_a(0.0F, 16.0F, 0.0F);
    this.body.func_78790_a(-4.0F, -12.0F, -2.0F, 8, 12, 4, p_i46366_1_);
    this.bipedCape = new ModelRenderer(this, 0, 0);
    this.bipedCape.func_78787_b(64, 32);
    this.bipedCape.func_78790_a(-5.0F, 0.0F, -1.0F, 10, 16, 1, p_i46366_1_);
  }
  
  public void renderCape(float scale, float flo1, float flo2, float flo3) {
    if (this.field_78091_s) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
      GlStateManager.func_179109_b(0.0F, 1.0F, -0.025F);
      GlStateManager.func_179114_b(6.0F + flo2 / 2.0F + flo1, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179114_b(flo3 / 2.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.func_179114_b(-flo3 / 2.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
      this.bipedCape.func_78785_a(scale);
      GlStateManager.func_179121_F();
    } else {
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b(0.0F, this.isSneak ? 0.425F : 0.375F, this.isSneak ? -0.25F : -0.025F);
      GlStateManager.func_179114_b(6.0F + flo2 / 2.0F + flo1, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179114_b(flo3 / 2.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.func_179114_b(-flo3 / 2.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
      this.bipedCape.func_78785_a(scale);
      GlStateManager.func_179121_F();
    } 
  }
  
  public void func_78088_a(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    func_78087_a(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
    GlStateManager.func_179094_E();
    if (this.field_78091_s) {
      GlStateManager.func_179152_a(0.75F, 0.75F, 0.75F);
      GlStateManager.func_179109_b(0.0F, 16.0F * scale, 0.0F);
      this.head.func_78785_a(scale);
      GlStateManager.func_179121_F();
      GlStateManager.func_179094_E();
      GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
      GlStateManager.func_179109_b(0.0F, 24.0F * scale, 0.0F);
      this.body.func_78785_a(scale);
      this.rightleg1.func_78785_a(scale);
      this.rightleg2.func_78785_a(scale);
      this.rightleg3.func_78785_a(scale);
      this.leftleg1.func_78785_a(scale);
      this.leftleg2.func_78785_a(scale);
      this.leftleg3.func_78785_a(scale);
    } else {
      this.head.func_78785_a(scale);
      this.body.func_78785_a(scale);
      this.rightleg1.func_78785_a(scale);
      this.rightleg2.func_78785_a(scale);
      this.rightleg3.func_78785_a(scale);
      this.leftleg1.func_78785_a(scale);
      this.leftleg2.func_78785_a(scale);
      this.leftleg3.func_78785_a(scale);
    } 
    GlStateManager.func_179121_F();
  }
  
  public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
    modelRenderer.field_78795_f = x;
    modelRenderer.field_78796_g = y;
    modelRenderer.field_78808_h = z;
  }
  
  public void func_78087_a(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    float l = 0.7853982F;
    float lc = 0.58119464F;
    this.rightleg1.field_78808_h = -l;
    this.rightleg2.field_78808_h = -lc;
    this.rightleg3.field_78808_h = -l;
    this.leftleg1.field_78808_h = l;
    this.leftleg2.field_78808_h = lc;
    this.leftleg3.field_78808_h = l;
    float f = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F);
    this.head.field_78796_g = netHeadYaw * 0.017453292F;
    this.head.field_78795_f = headPitch * 0.017453292F + f;
    this.head.field_78800_c = 0.0F;
    if (this.isSneak) {
      this.body.field_78795_f = 0.5F;
      this.head.field_78797_d = 6.0F;
      this.head.field_78798_e = -7.0F;
    } else {
      this.body.field_78795_f = 0.0F;
      this.head.field_78797_d = 4.0F;
      this.head.field_78798_e = 0.0F;
    } 
    EntityCreeder entity = (EntityCreeder)entityIn;
    this.field_78091_s = entity.func_70631_g_();
    this.isSneak = entity.func_70093_af();
    if (entity.func_70027_ad() || (!entity.func_70089_S() && !entity.field_70122_E)) {
      this.head.field_78795_f -= 0.5F;
      this.head.field_78796_g += MathHelper.func_76134_b(ageInTicks * 0.6662F) * 0.5F;
    } 
    if (entity.getJukeboxToDanceTo() != null) {
      float fl = MathHelper.func_76126_a(ageInTicks * 0.5F) * 0.7F;
      this.head.field_78800_c += MathHelper.func_76134_b(ageInTicks * 0.25F) * 1.0F;
      this.head.field_78797_d -= fl;
    } 
    this.rightleg1.field_78796_g = -l;
    this.rightleg2.field_78796_g = 0.0F;
    this.rightleg3.field_78796_g = l;
    this.leftleg1.field_78796_g = l;
    this.leftleg2.field_78796_g = 0.0F;
    this.leftleg3.field_78796_g = -l;
    float f3 = -(MathHelper.func_76134_b(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbSwingAmount;
    float f4 = -(MathHelper.func_76134_b(limbSwing * 0.6662F * 2.0F + 3.1415927F) * 0.4F) * limbSwingAmount;
    float f5 = -(MathHelper.func_76134_b(limbSwing * 0.6662F * 2.0F + 1.5707964F) * 0.4F) * limbSwingAmount;
    float f7 = Math.abs(MathHelper.func_76126_a(limbSwing * 0.6662F + 0.0F) * 0.4F) * limbSwingAmount;
    float f8 = Math.abs(MathHelper.func_76126_a(limbSwing * 0.6662F + 3.1415927F) * 0.4F) * limbSwingAmount;
    float f9 = Math.abs(MathHelper.func_76126_a(limbSwing * 0.6662F + 1.5707964F) * 0.4F) * limbSwingAmount;
    this.rightleg1.field_78796_g += f3;
    this.leftleg1.field_78796_g += -f3;
    this.rightleg2.field_78796_g += f4;
    this.leftleg2.field_78796_g += -f4;
    this.rightleg3.field_78796_g += f5;
    this.leftleg3.field_78796_g += -f5;
    this.rightleg1.field_78808_h += f7;
    this.leftleg1.field_78808_h += -f7;
    this.rightleg2.field_78808_h += f8;
    this.leftleg2.field_78808_h += -f8;
    this.rightleg3.field_78808_h += f9;
    this.leftleg3.field_78808_h += -f9;
  }
}
