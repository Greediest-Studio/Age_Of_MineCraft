package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class ModelCMMSpider extends ModelBiped implements ICappedModel {
  public ModelRenderer Abdoman;
  
  public ModelRenderer RArm;
  
  public ModelRenderer LArm;
  
  public ModelRenderer RLeg;
  
  public ModelRenderer LLeg;
  
  public ModelRenderer Body;
  
  public ModelRenderer Skirt1;
  
  public ModelRenderer Neck;
  
  public ModelRenderer Head;
  
  public ModelRenderer HTop;
  
  public ModelRenderer Hair;
  
  public ModelRenderer LEye;
  
  public ModelRenderer REye;
  
  public ModelRenderer Ponytail1;
  
  public ModelRenderer Ponytail2;
  
  public ModelRenderer Ponytail3;
  
  public ModelRenderer Ponytail4;
  
  public ModelRenderer Ponytail5;
  
  public ModelRenderer Ponytail6;
  
  public ModelRenderer Ponytailtip;
  
  public ModelRenderer Skirt2;
  
  public ModelRenderer RArm2;
  
  public ModelRenderer LArm2;
  
  public ModelRenderer bipedCape;
  
  public void renderCape(float scale, float flo1, float flo2, float flo3) {
    GlStateManager.func_179094_E();
    GlStateManager.func_179109_b(0.0F, this.field_78117_n ? 0.25F : 0.075F, this.field_78117_n ? -0.625F : -0.0325F);
    GlStateManager.func_179114_b(6.0F + flo2 / 2.0F + flo1 + (this.field_78117_n ? 20.0F : 1.0F), 1.0F, 0.0F, 0.0F);
    GlStateManager.func_179114_b(flo3 / 2.0F, 0.0F, 0.0F, 1.0F);
    GlStateManager.func_179114_b(-flo3 / 2.0F, 0.0F, 1.0F, 0.0F);
    GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
    this.bipedCape.func_78785_a(scale);
    GlStateManager.func_179121_F();
  }
  
  public ModelCMMSpider() {
    this.bipedCape = new ModelRenderer((ModelBase)this, 0, 0);
    this.bipedCape.func_78787_b(64, 32);
    this.bipedCape.func_78790_a(-5.0F, 0.0F, -1.0F, 10, 16, 1, 0.0F);
    this.field_78090_t = 64;
    this.field_78089_u = 64;
    this.Body = new ModelRenderer((ModelBase)this, 0, 47);
    this.Body.func_78793_a(0.0F, -6.0F, 0.0F);
    this.Body.func_78790_a(-3.5F, -5.0F, -1.5F, 7, 5, 3, 0.0F);
    this.Ponytail4 = new ModelRenderer((ModelBase)this, 20, 51);
    this.Ponytail4.func_78793_a(0.0F, 4.0F, 0.0F);
    this.Ponytail4.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
    this.HTop = new ModelRenderer((ModelBase)this, 0, 28);
    this.HTop.func_78793_a(0.0F, 0.0F, 0.0F);
    this.HTop.func_78790_a(-3.5F, -8.0F, -3.0F, 7, 1, 6, 0.0F);
    this.Ponytail2 = new ModelRenderer((ModelBase)this, 20, 43);
    this.Ponytail2.func_78793_a(0.0F, 3.0F, 1.5F);
    this.Ponytail2.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
    this.LEye = new ModelRenderer((ModelBase)this, 26, 2);
    this.LEye.func_78793_a(0.0F, 0.0F, 0.0F);
    this.LEye.func_78790_a(1.0F, -9.0F, -2.0F, 2, 1, 1, 0.0F);
    this.RArm2 = new ModelRenderer((ModelBase)this, 40, 22);
    this.RArm2.func_78793_a(0.0F, 0.0F, 0.0F);
    this.RArm2.func_78790_a(-2.5F, 5.0F, -1.5F, 3, 5, 3, 0.0F);
    this.LArm2 = new ModelRenderer((ModelBase)this, 40, 14);
    this.LArm2.func_78793_a(0.0F, 0.0F, 0.0F);
    this.LArm2.func_78790_a(-0.5F, 5.0F, -1.5F, 3, 5, 3, 0.0F);
    this.Skirt2 = new ModelRenderer((ModelBase)this, 38, 36);
    this.Skirt2.func_78793_a(0.0F, 2.0F, 0.0F);
    this.Skirt2.func_78790_a(-4.0F, 0.0F, -2.5F, 8, 2, 5, 0.0F);
    this.RArm = new ModelRenderer((ModelBase)this, 36, 0);
    this.RArm.func_78793_a(-3.5F, 2.0F, 0.0F);
    this.RArm.func_78790_a(-2.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
    this.Head = new ModelRenderer((ModelBase)this, 0, 0);
    this.Head.func_78793_a(0.0F, -1.0F, 0.0F);
    this.Head.func_78790_a(-3.5F, -7.0F, -3.0F, 7, 7, 6, 0.0F);
    this.Neck = new ModelRenderer((ModelBase)this, 0, 35);
    this.Neck.func_78793_a(0.0F, -5.0F, 0.0F);
    this.Neck.func_78790_a(-1.5F, -2.0F, -1.5F, 3, 2, 3, 0.0F);
    this.Hair = new ModelRenderer((ModelBase)this, 0, 13);
    this.Hair.func_78793_a(0.0F, 0.0F, 0.0F);
    this.Hair.func_78790_a(-4.0F, -7.0F, -3.5F, 8, 8, 7, 0.0F);
    this.Ponytail3 = new ModelRenderer((ModelBase)this, 20, 47);
    this.Ponytail3.func_78793_a(0.0F, 4.0F, 0.0F);
    this.Ponytail3.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
    this.Ponytail6 = new ModelRenderer((ModelBase)this, 20, 59);
    this.Ponytail6.func_78793_a(0.0F, 4.0F, 0.0F);
    this.Ponytail6.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 2, 3, 0.0F);
    this.Skirt1 = new ModelRenderer((ModelBase)this, 42, 30);
    this.Skirt1.func_78793_a(0.0F, -2.0F, 0.0F);
    this.Skirt1.func_78790_a(-3.5F, 0.0F, -2.0F, 7, 2, 4, 0.0F);
    this.Abdoman = new ModelRenderer((ModelBase)this, 0, 55);
    this.Abdoman.func_78793_a(0.0F, 12.0F, 0.0F);
    this.Abdoman.func_78790_a(-3.0F, -6.0F, -1.5F, 6, 6, 3, 0.0F);
    this.RLeg = new ModelRenderer((ModelBase)this, 52, 0);
    this.RLeg.func_78793_a(-2.0F, 12.0F, 0.0F);
    this.RLeg.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 12, 3, 0.0F);
    this.Ponytail1 = new ModelRenderer((ModelBase)this, 20, 39);
    this.Ponytail1.func_78793_a(0.0F, -8.0F, 3.0F);
    this.Ponytail1.func_78790_a(-1.5F, -1.0F, 0.0F, 3, 4, 3, 0.0F);
    this.Ponytail5 = new ModelRenderer((ModelBase)this, 20, 55);
    this.Ponytail5.func_78793_a(0.0F, 4.0F, 0.0F);
    this.Ponytail5.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
    this.Ponytailtip = new ModelRenderer((ModelBase)this, 20, 39);
    this.Ponytailtip.func_78793_a(0.0F, 2.1F, 0.0F);
    this.Ponytailtip.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 1, 3, 0.0F);
    setRotateAngle(this.Ponytailtip, 0.0F, 0.0F, 3.1415927F);
    this.LArm = new ModelRenderer((ModelBase)this, 44, 0);
    this.LArm.func_78793_a(3.5F, 2.0F, 0.0F);
    this.LArm.func_78790_a(0.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
    this.LLeg = new ModelRenderer((ModelBase)this, 52, 15);
    this.LLeg.func_78793_a(2.0F, 12.0F, 0.0F);
    this.LLeg.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 12, 3, 0.0F);
    this.REye = new ModelRenderer((ModelBase)this, 26, 0);
    this.REye.func_78793_a(0.0F, 0.0F, 0.0F);
    this.REye.func_78790_a(-3.0F, -9.0F, -2.0F, 2, 1, 1, 0.0F);
    this.Abdoman.func_78792_a(this.Body);
    this.Ponytail3.func_78792_a(this.Ponytail4);
    this.Head.func_78792_a(this.HTop);
    this.Ponytail1.func_78792_a(this.Ponytail2);
    this.Head.func_78792_a(this.LEye);
    this.RArm.func_78792_a(this.RArm2);
    this.LArm.func_78792_a(this.LArm2);
    this.Skirt1.func_78792_a(this.Skirt2);
    this.Neck.func_78792_a(this.Head);
    this.Body.func_78792_a(this.Neck);
    this.Head.func_78792_a(this.Hair);
    this.Ponytail2.func_78792_a(this.Ponytail3);
    this.Ponytail5.func_78792_a(this.Ponytail6);
    this.Abdoman.func_78792_a(this.Skirt1);
    this.Head.func_78792_a(this.Ponytail1);
    this.Ponytail4.func_78792_a(this.Ponytail5);
    this.Ponytail6.func_78792_a(this.Ponytailtip);
    this.Head.func_78792_a(this.REye);
    this.field_78116_c.field_78807_k = true;
    this.field_78115_e.field_78807_k = true;
    this.field_178723_h.field_78807_k = true;
    this.field_178724_i.field_78807_k = true;
    this.field_178721_j.field_78807_k = true;
    this.field_178722_k.field_78807_k = true;
    this.field_178720_f.field_78807_k = true;
  }
  
  public ModelCMMSpider(float modelSize) {
    super(modelSize);
    this.field_178723_h = new ModelRenderer((ModelBase)this, 40, 16);
    this.field_178723_h.func_78790_a(-3.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
    this.field_178723_h.func_78793_a(-4.0F, 2.0F, 0.0F);
    this.field_178724_i = new ModelRenderer((ModelBase)this, 40, 16);
    this.field_178724_i.field_78809_i = true;
    this.field_178724_i.func_78790_a(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
    this.field_178724_i.func_78793_a(4.0F, 2.0F, 0.0F);
    this.Body = new ModelRenderer((ModelBase)this, 0, 47);
    this.Body.func_78793_a(0.0F, -6.0F, 0.0F);
    this.Body.func_78790_a(-3.5F, -5.0F, -1.5F, 7, 5, 3, 0.0F);
    this.Ponytail4 = new ModelRenderer((ModelBase)this, 20, 51);
    this.Ponytail4.func_78793_a(0.0F, 4.0F, 0.0F);
    this.Ponytail4.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
    this.HTop = new ModelRenderer((ModelBase)this, 0, 28);
    this.HTop.func_78793_a(0.0F, 0.0F, 0.0F);
    this.HTop.func_78790_a(-3.5F, -8.0F, -3.0F, 7, 1, 6, 0.0F);
    this.Ponytail2 = new ModelRenderer((ModelBase)this, 20, 43);
    this.Ponytail2.func_78793_a(0.0F, 3.0F, 1.5F);
    this.Ponytail2.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
    this.LEye = new ModelRenderer((ModelBase)this, 26, 2);
    this.LEye.func_78793_a(0.0F, 0.0F, 0.0F);
    this.LEye.func_78790_a(1.0F, -9.0F, -2.0F, 2, 1, 1, 0.0F);
    this.RArm2 = new ModelRenderer((ModelBase)this, 40, 22);
    this.RArm2.func_78793_a(0.0F, 0.0F, 0.0F);
    this.RArm2.func_78790_a(-2.5F, 5.0F, -1.5F, 3, 5, 3, 0.0F);
    this.LArm2 = new ModelRenderer((ModelBase)this, 40, 14);
    this.LArm2.func_78793_a(0.0F, 0.0F, 0.0F);
    this.LArm2.func_78790_a(-0.5F, 5.0F, -1.5F, 3, 5, 3, 0.0F);
    this.Skirt2 = new ModelRenderer((ModelBase)this, 38, 36);
    this.Skirt2.func_78793_a(0.0F, 2.0F, 0.0F);
    this.Skirt2.func_78790_a(-4.0F, 0.0F, -2.5F, 8, 2, 5, 0.0F);
    this.RArm = new ModelRenderer((ModelBase)this, 36, 0);
    this.RArm.func_78793_a(-3.5F, 2.0F, 0.0F);
    this.RArm.func_78790_a(-2.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
    this.Head = new ModelRenderer((ModelBase)this, 0, 0);
    this.Head.func_78793_a(0.0F, -1.0F, 0.0F);
    this.Head.func_78790_a(-3.5F, -7.0F, -3.0F, 7, 7, 6, 0.0F);
    this.Neck = new ModelRenderer((ModelBase)this, 0, 35);
    this.Neck.func_78793_a(0.0F, -5.0F, 0.0F);
    this.Neck.func_78790_a(-1.5F, -2.0F, -1.5F, 3, 2, 3, 0.0F);
    this.Hair = new ModelRenderer((ModelBase)this, 0, 13);
    this.Hair.func_78793_a(0.0F, 0.0F, 0.0F);
    this.Hair.func_78790_a(-4.0F, -7.0F, -3.5F, 8, 8, 7, 0.0F);
    this.Ponytail3 = new ModelRenderer((ModelBase)this, 20, 47);
    this.Ponytail3.func_78793_a(0.0F, 4.0F, 0.0F);
    this.Ponytail3.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
    this.Ponytail6 = new ModelRenderer((ModelBase)this, 20, 59);
    this.Ponytail6.func_78793_a(0.0F, 4.0F, 0.0F);
    this.Ponytail6.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 2, 3, 0.0F);
    this.Skirt1 = new ModelRenderer((ModelBase)this, 42, 30);
    this.Skirt1.func_78793_a(0.0F, -2.0F, 0.0F);
    this.Skirt1.func_78790_a(-3.5F, 0.0F, -2.0F, 7, 2, 4, 0.0F);
    this.Abdoman = new ModelRenderer((ModelBase)this, 0, 55);
    this.Abdoman.func_78793_a(0.0F, 12.0F, 0.0F);
    this.Abdoman.func_78790_a(-3.0F, -6.0F, -1.5F, 6, 6, 3, 0.0F);
    this.RLeg = new ModelRenderer((ModelBase)this, 52, 0);
    this.RLeg.func_78793_a(-2.0F, 12.0F, 0.0F);
    this.RLeg.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 12, 3, 0.0F);
    this.Ponytail1 = new ModelRenderer((ModelBase)this, 20, 39);
    this.Ponytail1.func_78793_a(0.0F, -8.0F, 3.0F);
    this.Ponytail1.func_78790_a(-1.5F, -1.0F, 0.0F, 3, 4, 3, 0.0F);
    this.Ponytail5 = new ModelRenderer((ModelBase)this, 20, 55);
    this.Ponytail5.func_78793_a(0.0F, 4.0F, 0.0F);
    this.Ponytail5.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
    this.Ponytailtip = new ModelRenderer((ModelBase)this, 20, 39);
    this.Ponytailtip.func_78793_a(0.0F, 2.1F, 0.0F);
    this.Ponytailtip.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 1, 3, 0.0F);
    setRotateAngle(this.Ponytailtip, 0.0F, 0.0F, 3.1415927F);
    this.LArm = new ModelRenderer((ModelBase)this, 44, 0);
    this.LArm.func_78793_a(3.5F, 2.0F, 0.0F);
    this.LArm.func_78790_a(0.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
    this.LLeg = new ModelRenderer((ModelBase)this, 52, 15);
    this.LLeg.func_78793_a(2.0F, 12.0F, 0.0F);
    this.LLeg.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 12, 3, 0.0F);
    this.REye = new ModelRenderer((ModelBase)this, 26, 0);
    this.REye.func_78793_a(0.0F, 0.0F, 0.0F);
    this.REye.func_78790_a(-3.0F, -9.0F, -2.0F, 2, 1, 1, 0.0F);
    this.Abdoman.func_78792_a(this.Body);
    this.Ponytail3.func_78792_a(this.Ponytail4);
    this.Head.func_78792_a(this.HTop);
    this.Ponytail1.func_78792_a(this.Ponytail2);
    this.Head.func_78792_a(this.LEye);
    this.RArm.func_78792_a(this.RArm2);
    this.LArm.func_78792_a(this.LArm2);
    this.Skirt1.func_78792_a(this.Skirt2);
    this.Neck.func_78792_a(this.Head);
    this.Body.func_78792_a(this.Neck);
    this.Head.func_78792_a(this.Hair);
    this.Ponytail2.func_78792_a(this.Ponytail3);
    this.Ponytail5.func_78792_a(this.Ponytail6);
    this.Abdoman.func_78792_a(this.Skirt1);
    this.Head.func_78792_a(this.Ponytail1);
    this.Ponytail4.func_78792_a(this.Ponytail5);
    this.Ponytail6.func_78792_a(this.Ponytailtip);
    this.Head.func_78792_a(this.REye);
    this.RArm.field_78807_k = true;
    this.LArm.field_78807_k = true;
    this.RLeg.field_78807_k = true;
    this.LLeg.field_78807_k = true;
    this.Abdoman.field_78807_k = true;
  }
  
  public void func_78088_a(Entity entityIn, float p_78088_2_, float limbSwing, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    func_78087_a(p_78088_2_, limbSwing, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
    GlStateManager.func_179094_E();
    if (this.field_78091_s) {
      GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
      GlStateManager.func_179109_b(0.0F, 24.0F * scale, 0.0F);
      this.RArm.func_78785_a(scale);
      this.LArm.func_78785_a(scale);
      this.RLeg.func_78785_a(scale);
      this.LLeg.func_78785_a(scale);
      this.Abdoman.func_78785_a(scale);
      this.field_78116_c.func_78785_a(scale);
      this.field_78115_e.func_78785_a(scale);
      this.field_178723_h.func_78785_a(scale);
      this.field_178724_i.func_78785_a(scale);
      this.field_178721_j.func_78785_a(scale);
      this.field_178722_k.func_78785_a(scale);
      this.field_178720_f.func_78785_a(scale);
    } else {
      this.RArm.func_78785_a(scale);
      this.LArm.func_78785_a(scale);
      this.RLeg.func_78785_a(scale);
      this.LLeg.func_78785_a(scale);
      this.Abdoman.func_78785_a(scale);
      this.field_78116_c.func_78785_a(scale);
      this.field_78115_e.func_78785_a(scale);
      this.field_178723_h.func_78785_a(scale);
      this.field_178724_i.func_78785_a(scale);
      this.field_178721_j.func_78785_a(scale);
      this.field_178722_k.func_78785_a(scale);
      this.field_178720_f.func_78785_a(scale);
    } 
    GlStateManager.func_179121_F();
  }
  
  public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
    modelRenderer.field_78795_f = x;
    modelRenderer.field_78796_g = y;
    modelRenderer.field_78808_h = z;
  }
  
  protected ModelRenderer func_187074_a(EnumHandSide side) {
    return (side == EnumHandSide.LEFT) ? this.LArm : this.RArm;
  }
  
  public void func_78087_a(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    EntityTameBase entity = (EntityTameBase)entityIn;
    if (entity.func_175446_cd())
      ageInTicks = 0.0F; 
    limbSwing *= 1.5F;
    this.Head.field_78796_g = netHeadYaw * 0.017453292F;
    this.Head.field_78795_f = headPitch * 0.017453292F;
    this.field_78091_s = entity.func_70631_g_();
    this.field_78117_n = (entity.func_70093_af() && entity.field_70122_E);
    if (!this.field_78091_s) {
      this.Ponytail1.field_78807_k = false;
    } else {
      this.Ponytail1.field_78807_k = true;
    } 
    this.LLeg.func_78793_a(2.0F, 12.0F, 0.0F);
    this.RLeg.func_78793_a(-2.0F, 12.0F, 0.0F);
    this.Abdoman.func_78793_a(0.0F, 12.0F, 0.0F);
    this.RArm.func_78793_a(-3.5F, 3.0F, 0.0F);
    this.LArm.func_78793_a(3.5F, 3.0F, 0.0F);
    this.Ponytail1.field_78795_f = entity.field_70721_aZ * 0.2F - headPitch * 0.0045F;
    this.Ponytail2.field_78795_f = entity.field_70721_aZ * 0.16F - headPitch * 0.0045F;
    this.Ponytail3.field_78795_f = entity.field_70721_aZ * 0.16F - headPitch * 0.0045F;
    this.Ponytail4.field_78795_f = entity.field_70721_aZ * 0.16F - headPitch * 0.0045F;
    this.Ponytail5.field_78795_f = entity.field_70721_aZ * 0.16F - headPitch * 0.0045F;
    this.Ponytail6.field_78795_f = entity.field_70721_aZ * 0.16F - headPitch * 0.0045F;
    this.Ponytail1.field_78808_h = 0.0F;
    this.Ponytail2.field_78808_h = 0.0F;
    this.Ponytail3.field_78808_h = 0.0F;
    this.Ponytail4.field_78808_h = 0.0F;
    this.Ponytail5.field_78808_h = 0.0F;
    this.Ponytail6.field_78808_h = 0.0F;
    this.Body.field_78795_f = 0.0F;
    this.Abdoman.field_78795_f = 0.0F;
    this.Body.field_78796_g = 0.0F;
    this.Abdoman.field_78796_g = 0.0F;
    this.Body.field_78808_h = 0.0F;
    this.Abdoman.field_78808_h = 0.0F;
    this.Neck.field_78808_h = 0.0F;
    this.Neck.field_78796_g = 0.0F;
    this.Neck.field_78795_f = 0.0F;
    this.Head.field_78808_h = 0.0F;
    this.Skirt1.field_78795_f = 0.0F;
    this.Skirt2.field_78795_f = 0.0F;
    this.RArm.field_78808_h = 0.0F;
    this.LArm.field_78808_h = 0.0F;
    this.RArm.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F + 3.1415927F) * 2.0F * limbSwingAmount * 0.5F;
    this.LArm.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
    this.RArm.field_78808_h = 0.0F;
    this.LArm.field_78808_h = 0.0F;
    this.RArm.field_78796_g = 0.0F;
    this.LArm.field_78796_g = 0.0F;
    this.RLeg.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
    this.LLeg.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount * 0.5F;
    this.RLeg.field_78796_g = 0.0F;
    this.LLeg.field_78796_g = 0.0F;
    this.RLeg.field_78808_h = 0.0F;
    this.LLeg.field_78808_h = 0.0F;
    this.Neck.field_78808_h += MathHelper.func_76134_b(limbSwing * 0.6662F) * 0.6F * limbSwingAmount * 0.5F;
    this.Body.field_78808_h -= MathHelper.func_76134_b(limbSwing * 0.6662F) * 0.8F * limbSwingAmount * 0.5F;
    this.Abdoman.field_78808_h += MathHelper.func_76134_b(limbSwing * 0.6662F) * 0.4F * limbSwingAmount * 0.5F;
    this.Abdoman.field_78798_e = MathHelper.func_76134_b(limbSwing * 0.6662F) * 0.5F * limbSwingAmount * 0.5F;
    this.Abdoman.field_78797_d = 12.0F + MathHelper.func_76134_b(limbSwing * 0.6662F) * 0.1F * limbSwingAmount * 0.5F;
    this.RArm.field_78800_c += MathHelper.func_76134_b(limbSwing * 0.6662F) * 1.5F * limbSwingAmount * 0.5F;
    this.LArm.field_78800_c += MathHelper.func_76134_b(limbSwing * 0.6662F) * 1.5F * limbSwingAmount * 0.5F;
    this.Ponytail1.field_78808_h += MathHelper.func_76134_b(ageInTicks * 0.1F - 0.5F) * 0.01F;
    this.Ponytail2.field_78808_h += MathHelper.func_76134_b(ageInTicks * 0.1F - 1.0F) * 0.01F;
    this.Ponytail3.field_78808_h += MathHelper.func_76134_b(ageInTicks * 0.1F - 1.5F) * 0.01F;
    this.Ponytail4.field_78808_h += MathHelper.func_76134_b(ageInTicks * 0.1F - 2.0F) * 0.01F;
    this.Ponytail5.field_78808_h += MathHelper.func_76134_b(ageInTicks * 0.1F - 2.5F) * 0.01F;
    this.Ponytail6.field_78808_h += MathHelper.func_76134_b(ageInTicks * 0.1F - 3.0F) * 0.01F;
    this.Ponytail1.field_78808_h += MathHelper.func_76134_b(limbSwing * 0.6662F - 0.5F) * 0.5F * limbSwingAmount * 0.5F;
    this.Ponytail2.field_78808_h += MathHelper.func_76134_b(limbSwing * 0.6662F - 1.0F) * 0.5F * limbSwingAmount * 0.5F;
    this.Ponytail3.field_78808_h += MathHelper.func_76134_b(limbSwing * 0.6662F - 1.5F) * 0.5F * limbSwingAmount * 0.5F;
    this.Ponytail4.field_78808_h += MathHelper.func_76134_b(limbSwing * 0.6662F - 2.0F) * 0.5F * limbSwingAmount * 0.5F;
    this.Ponytail5.field_78808_h += MathHelper.func_76134_b(limbSwing * 0.6662F - 2.5F) * 0.5F * limbSwingAmount * 0.5F;
    this.Ponytail6.field_78808_h += MathHelper.func_76134_b(limbSwing * 0.6662F - 3.0F) * 0.5F * limbSwingAmount * 0.5F;
    if (entity.func_70617_f_() && !entity.func_184207_aI()) {
      this.RArm.field_78795_f -= 2.0F;
      this.LArm.field_78795_f -= 2.0F;
      this.RArm.field_78808_h -= 0.6F;
      this.LArm.field_78808_h += 0.6F;
    } 
    if (!entity.field_70122_E && !this.field_78093_q) {
      this.Ponytail1.field_78795_f = (float)(this.Ponytail1.field_78795_f + (1.0F + MathHelper.func_76134_b(ageInTicks * 0.3F - 0.5F) * 0.1F) - ((entity.field_70181_x > 3.0D) ? 1.0D : ((entity.field_70181_x < -3.0D) ? -1.0D : (entity.field_70181_x / 3.0D))));
      this.Ponytail2.field_78795_f += MathHelper.func_76134_b(ageInTicks * 0.3F - 1.0F) * 0.1F;
      this.Ponytail3.field_78795_f += MathHelper.func_76134_b(ageInTicks * 0.3F - 1.5F) * 0.1F;
      this.Ponytail4.field_78795_f += MathHelper.func_76134_b(ageInTicks * 0.3F - 2.0F) * 0.1F;
      this.Ponytail5.field_78795_f += MathHelper.func_76134_b(ageInTicks * 0.3F - 2.5F) * 0.1F;
      this.Ponytail6.field_78795_f += MathHelper.func_76134_b(ageInTicks * 0.3F - 3.0F) * 0.1F;
      if (entity.func_70617_f_()) {
        this.RArm.field_78795_f -= 2.0F;
        this.LArm.field_78795_f -= 2.0F;
      } else {
        this.RArm.field_78795_f = -2.0F;
        this.LArm.field_78795_f = -2.0F;
      } 
      this.Neck.field_78795_f--;
      if (!entity.func_70617_f_()) {
        this.RLeg.field_78795_f = 0.5F;
        this.LLeg.field_78795_f = 0.5F;
      } 
      this.RLeg.field_78808_h = 0.5F;
      this.LLeg.field_78808_h = -0.5F;
    } 
    if (this.field_78093_q && this.field_78091_s) {
      this.Head.field_78795_f = -0.5F;
      this.Neck.field_78795_f = -0.5F;
      this.RArm.field_78795_f = -1.75F;
      this.LArm.field_78795_f = -1.75F;
      this.RArm.field_78796_g = 0.5F;
      this.LArm.field_78796_g = -0.5F;
      this.RLeg.field_78795_f = 0.25F + MathHelper.func_76134_b(ageInTicks * 0.3F + 3.1415927F) * 0.1F + MathHelper.func_76134_b(limbSwing * 0.6662F + 3.1415927F) * 0.25F * limbSwingAmount * 0.5F;
      this.LLeg.field_78795_f = 0.25F + MathHelper.func_76134_b(ageInTicks * 0.3F) * 0.1F + MathHelper.func_76134_b(limbSwing * 0.6662F) * 0.25F * limbSwingAmount * 0.5F;
      this.RLeg.field_78808_h = 0.25F + MathHelper.func_76134_b(ageInTicks * 0.3F - 2.0F) * 0.1F;
      this.LLeg.field_78808_h = -0.25F + MathHelper.func_76134_b(ageInTicks * 0.3F - 2.0F) * 0.1F;
    } 
    if (entity.func_184207_aI()) {
      if (entity.func_70617_f_()) {
        this.RArm.field_78795_f -= 2.0F;
        this.LArm.field_78795_f -= 2.0F;
      } else {
        this.RArm.field_78795_f = -2.0F;
        this.LArm.field_78795_f = -2.0F;
      } 
      this.RArm.field_78808_h -= 0.6F;
      this.LArm.field_78808_h += 0.6F;
    } 
    this.field_78115_e.field_78800_c = 0.0F;
    this.field_78115_e.field_78797_d = 0.0F;
    this.field_78115_e.field_78798_e = 0.0F;
    this.field_78116_c.field_78800_c = 0.0F;
    this.field_78116_c.field_78797_d = 0.0F;
    this.field_78116_c.field_78798_e = 0.0F;
    if (this.field_78117_n && !entity.isSitResting() && entity.field_70122_E && entity.getJukeboxToDanceTo() == null) {
      this.Ponytail1.field_78795_f += 0.5F;
      this.Abdoman.field_78795_f++;
      this.Body.field_78795_f -= 0.5F;
      this.Head.field_78795_f -= 0.5F;
      this.RArm.field_78795_f += 0.75F;
      this.LArm.field_78795_f += 0.75F;
      this.Abdoman.field_78798_e--;
      this.RArm.field_78797_d += 4.0F;
      this.LArm.field_78797_d += 4.0F;
      this.RArm.field_78798_e -= 8.0F;
      this.LArm.field_78798_e -= 8.0F;
    } 
    switch (this.field_187075_l) {
      default:
        this.LArm.field_78796_g = 0.0F;
        break;
      case BLOCK:
        this.LArm.field_78795_f = this.LArm.field_78795_f * 0.5F - 1.0F;
        this.LArm.field_78796_g = 0.75F;
        break;
      case ITEM:
        this.LArm.field_78795_f = this.LArm.field_78795_f * 0.5F - 0.31415927F;
        this.LArm.field_78796_g = 0.0F;
        break;
      case BOW_AND_ARROW:
        this.RArm.field_78796_g = -0.1F + this.Neck.field_78796_g + this.Head.field_78796_g - 0.4F;
        this.LArm.field_78796_g = 0.1F + this.Neck.field_78796_g + this.Head.field_78796_g;
        this.RArm.field_78795_f = -1.5707964F + this.Neck.field_78795_f + this.Head.field_78795_f;
        this.LArm.field_78795_f = -1.5707964F + this.Neck.field_78795_f + this.Head.field_78795_f;
        break;
    } 
    switch (this.field_187076_m) {
      default:
        this.RArm.field_78796_g = 0.0F;
        break;
      case BLOCK:
        this.RArm.field_78795_f = this.RArm.field_78795_f * 0.5F - 1.0F;
        this.RArm.field_78796_g = -0.75F;
        break;
      case ITEM:
        this.RArm.field_78795_f = this.RArm.field_78795_f * 0.5F - 0.31415927F;
        this.RArm.field_78796_g = 0.0F;
        break;
      case BOW_AND_ARROW:
        this.RArm.field_78796_g = -0.1F + this.Neck.field_78796_g + this.Head.field_78796_g;
        this.LArm.field_78796_g = 0.1F + this.Neck.field_78796_g + this.Head.field_78796_g + 0.4F;
        this.RArm.field_78795_f = -1.5707964F + this.Neck.field_78795_f + this.Head.field_78795_f;
        this.LArm.field_78795_f = -1.5707964F + this.Neck.field_78795_f + this.Head.field_78795_f;
        break;
    } 
    if (entity.isSitResting()) {
      this.Head.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.2F) * 0.05F;
      this.Neck.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.2F) * 0.05F;
      this.Ponytail1.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.2F - 0.5F) * 0.1F;
      this.Ponytail2.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.2F - 1.0F) * 0.1F;
      this.Ponytail3.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.2F - 1.5F) * 0.1F;
      this.Ponytail4.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.2F - 2.0F) * 0.1F;
      this.Ponytail3.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.2F - 2.5F) * 0.1F;
      this.Ponytail4.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.2F - 3.0F) * 0.1F;
      this.RArm.field_78795_f += -0.15707964F;
      this.LArm.field_78795_f += -0.15707964F;
      this.RLeg.field_78795_f = -1.4137167F;
      this.RLeg.field_78796_g = 0.05F - MathHelper.func_76134_b(ageInTicks * 0.3F) * 0.1F;
      this.RLeg.field_78808_h = 0.07853982F;
      this.LLeg.field_78795_f = -1.4137167F;
      this.LLeg.field_78796_g = -0.05F + MathHelper.func_76134_b(ageInTicks * 0.3F) * 0.1F;
      this.LLeg.field_78808_h = -0.07853982F;
      this.Skirt2.field_78795_f = -0.70685834F;
    } 
    if (entity.func_70027_ad() || (!entity.func_70089_S() && !entity.field_70122_E)) {
      this.Head.field_78795_f -= 0.5F;
      this.Head.field_78796_g += MathHelper.func_76134_b(ageInTicks * 0.6662F) * 0.5F;
      this.RArm.field_78795_f = -MathHelper.func_76134_b(ageInTicks * 0.6662F) * 0.5F;
      this.LArm.field_78795_f = MathHelper.func_76134_b(ageInTicks * 0.6662F) * 0.5F;
      this.RArm.field_78808_h = 2.3561945F;
      this.LArm.field_78808_h = -2.3561945F;
      this.RArm.field_78796_g = 0.0F;
      this.LArm.field_78796_g = 0.0F;
    } 
    this.RArm.field_78808_h += MathHelper.func_76134_b(ageInTicks * 0.09F) * 0.05F + 0.1F;
    this.LArm.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.09F) * 0.05F + 0.1F;
    this.RArm.field_78795_f += MathHelper.func_76126_a(ageInTicks * 0.067F) * 0.05F;
    this.LArm.field_78795_f -= MathHelper.func_76126_a(ageInTicks * 0.067F) * 0.05F;
    if (entity.func_70617_f_()) {
      limbSwing *= 0.25F;
      limbSwingAmount *= 0.25F;
    } 
    if (this.field_78095_p > 0.0F) {
      float f1 = this.field_78095_p;
      this.Abdoman.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f1) * 6.2831855F) * 0.2F;
      this.RArm.field_78798_e = MathHelper.func_76126_a(this.Abdoman.field_78796_g) * 5.0F;
      this.RArm.field_78800_c = -MathHelper.func_76134_b(this.Abdoman.field_78796_g) * 3.0F;
      this.LArm.field_78798_e = -MathHelper.func_76126_a(this.Abdoman.field_78796_g) * 5.0F;
      this.LArm.field_78800_c = MathHelper.func_76134_b(this.Abdoman.field_78796_g) * 3.0F;
      this.RArm.field_78796_g += this.Abdoman.field_78796_g;
      this.LArm.field_78796_g += this.Abdoman.field_78796_g;
      this.LArm.field_78795_f += this.Abdoman.field_78796_g;
      f1 = 1.0F - this.field_78095_p;
      f1 *= f1;
      f1 *= f1;
      f1 = 1.0F - f1;
      float f2 = MathHelper.func_76126_a(f1 * 3.1415927F);
      float f3 = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F) * -(this.Neck.field_78795_f - 0.7F) * 0.75F;
      if (entity.func_184638_cS()) {
        this.Abdoman.field_78796_g *= -1.0F;
        this.LArm.field_78795_f = (float)(this.LArm.field_78795_f - f2 * 1.2D + f3);
        this.LArm.field_78796_g += this.Abdoman.field_78796_g * 2.0F;
        this.LArm.field_78808_h += MathHelper.func_76126_a(this.field_78095_p * 3.1415927F) * -0.4F;
      } else {
        this.RArm.field_78795_f = (float)(this.RArm.field_78795_f - f2 * 1.2D + f3);
        this.RArm.field_78796_g += this.Abdoman.field_78796_g * 2.0F;
        this.RArm.field_78808_h += MathHelper.func_76126_a(this.field_78095_p * 3.1415927F) * -0.4F;
      } 
    } 
    if (entity.getJukeboxToDanceTo() != null) {
      this.Ponytail1.field_78808_h += MathHelper.func_76134_b(ageInTicks * 0.5F - 3.1415927F) * 0.25F;
      this.Ponytail2.field_78808_h += MathHelper.func_76134_b(ageInTicks * 0.5F - 3.6415927F) * 0.25F;
      this.Ponytail3.field_78808_h += MathHelper.func_76134_b(ageInTicks * 0.5F - 4.1415925F) * 0.25F;
      this.Ponytail4.field_78808_h += MathHelper.func_76134_b(ageInTicks * 0.5F - 4.6415925F) * 0.25F;
      this.Ponytail5.field_78808_h += MathHelper.func_76134_b(ageInTicks * 0.5F - 5.1415925F) * 0.25F;
      this.Ponytail6.field_78808_h += MathHelper.func_76134_b(ageInTicks * 0.5F - 5.6415925F) * 0.25F;
      this.Neck.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.5F - 3.1415927F) * 0.25F;
      this.Head.field_78795_f += MathHelper.func_76134_b(ageInTicks * 1.0F) * 0.25F;
      this.Body.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.5F - 3.1415927F) * 0.5F;
      this.Abdoman.field_78808_h = MathHelper.func_76134_b(ageInTicks * 0.5F) * 0.5F;
      this.Abdoman.field_78800_c = MathHelper.func_76134_b(ageInTicks * 0.5F - 3.1415927F) * 1.0F;
      this.Abdoman.field_78797_d = 12.0F + MathHelper.func_76134_b(ageInTicks * 1.0F) * 1.0F;
      this.RArm.field_78800_c = -3.5F + MathHelper.func_76134_b(ageInTicks * 0.5F) * 3.0F;
      this.LArm.field_78800_c = 3.5F + MathHelper.func_76134_b(ageInTicks * 0.5F) * 3.0F;
      this.RArm.field_78797_d = 4.0F + MathHelper.func_76134_b(ageInTicks * 1.0F) * 1.0F;
      this.LArm.field_78797_d = 4.0F + MathHelper.func_76134_b(ageInTicks * 1.0F) * 1.0F;
      this.RArm.field_78795_f = -0.5F + MathHelper.func_76134_b(ageInTicks * 0.5F - 3.0F) * 1.0F;
      this.LArm.field_78795_f = 0.0F + MathHelper.func_76134_b(ageInTicks * 1.0F) * 0.5F;
      this.RArm.field_78808_h = 2.0F;
      this.LArm.field_78808_h = -2.0F + MathHelper.func_76126_a(ageInTicks * 1.0F) * 0.5F;
      this.RArm.field_78796_g = 0.0F;
      this.LArm.field_78796_g = 0.0F;
      this.LLeg.field_78808_h = -(MathHelper.func_76134_b(ageInTicks * 0.5F) * 0.2F);
      this.LLeg.field_78795_f = 0.0F;
      this.RLeg.field_78795_f = MathHelper.func_76134_b(ageInTicks * 0.5F) * 0.5F;
      this.RLeg.field_78800_c = -2.0F - MathHelper.func_76134_b(ageInTicks * 0.5F) * 2.0F;
      this.LLeg.field_78800_c = 2.0F - MathHelper.func_76134_b(ageInTicks * 0.5F) * 2.0F;
    } 
    this.Neck.field_78795_f += this.Head.field_78795_f;
    this.Neck.field_78796_g += this.Head.field_78796_g;
    this.Neck.field_78808_h += this.Head.field_78808_h;
    func_178685_a(this.RLeg, this.field_178721_j);
    func_178685_a(this.LLeg, this.field_178722_k);
    func_178685_a(this.RArm, this.field_178723_h);
    func_178685_a(this.LArm, this.field_178724_i);
    this.field_178721_j.field_78797_d -= 1.5F;
    this.field_178722_k.field_78797_d -= 1.5F;
    this.field_178723_h.field_78800_c--;
    this.field_178724_i.field_78800_c++;
    this.field_78116_c.field_78797_d -= 1.1F;
    if (!entity.getCurrentBook().func_190926_b()) {
      this.RArm.field_78796_g = entity.bookSpread - 0.825F;
      this.LArm.field_78796_g = -entity.bookSpread + 0.825F;
      this.RArm.field_78808_h = 0.0F;
      this.LArm.field_78808_h = 0.0F;
      this.RArm.field_78795_f = -1.5F + 0.1F + MathHelper.func_76126_a(entity.field_70173_aa * 0.1F) * 0.01F;
      this.LArm.field_78795_f = -1.5F + 0.1F + MathHelper.func_76126_a(entity.field_70173_aa * 0.1F) * 0.01F;
    } 
    if (entity.field_70725_aQ > 0) {
      float k = (entity.field_70725_aQ - 1.0F) / 20.0F;
      k = MathHelper.func_76129_c(k);
      if (k > 1.0F)
        k = 1.0F; 
      this.RArm.field_78808_h = k * 2.25F;
      this.LArm.field_78808_h = -k * 2.25F;
      this.RLeg.field_78808_h = k * 0.25F;
      this.LLeg.field_78808_h = -k * 0.25F;
      this.Neck.field_78796_g = k * 0.25F;
      this.Head.field_78796_g = k * 0.75F;
    } 
  }
}
