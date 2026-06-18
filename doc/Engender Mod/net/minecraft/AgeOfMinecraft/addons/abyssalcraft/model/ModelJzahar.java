package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.model;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityJzahar;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelJzahar extends ModelBase {
  public ModelRenderer head;
  
  public ModelRenderer mask1;
  
  public ModelRenderer mask2;
  
  public ModelRenderer facetentacle1;
  
  public ModelRenderer facetentacle2;
  
  public ModelRenderer facetentacle3;
  
  public ModelRenderer body1;
  
  public ModelRenderer body2;
  
  public ModelRenderer body3;
  
  public ModelRenderer body4;
  
  public ModelRenderer body5;
  
  public ModelRenderer eye1;
  
  public ModelRenderer eye2;
  
  public ModelRenderer tentacle1;
  
  public ModelRenderer tentacle12;
  
  public ModelRenderer tentacle13;
  
  public ModelRenderer foot1;
  
  public ModelRenderer tentacle2;
  
  public ModelRenderer tentacle22;
  
  public ModelRenderer tentacle23;
  
  public ModelRenderer foot2;
  
  public ModelRenderer tentacle3;
  
  public ModelRenderer tentacle32;
  
  public ModelRenderer tentacle33;
  
  public ModelRenderer foot3;
  
  public ModelRenderer tentacle4;
  
  public ModelRenderer tentacle42;
  
  public ModelRenderer tentacle43;
  
  public ModelRenderer foot4;
  
  public ModelRenderer tentacle5;
  
  public ModelRenderer tentacle52;
  
  public ModelRenderer tentacle53;
  
  public ModelRenderer tentacle54;
  
  public ModelRenderer tentacle55;
  
  public ModelRenderer arm;
  
  public ModelRenderer Staff1;
  
  public ModelRenderer Staff2;
  
  public ModelRenderer Staff3;
  
  public ModelRenderer Staff4;
  
  public ModelRenderer Staff5;
  
  public ModelRenderer Staff6;
  
  public ModelRenderer Cube;
  
  public ModelRenderer tentacle6;
  
  public ModelRenderer tentacle62;
  
  public ModelRenderer tentacle63;
  
  public ModelRenderer foot5;
  
  public ModelRenderer tentacle7;
  
  public ModelRenderer tentacle72;
  
  public ModelRenderer tentacle73;
  
  public ModelRenderer tentacle74;
  
  public ModelRenderer tentacle75;
  
  public ModelRenderer tentacle76;
  
  public ModelRenderer tentacle77;
  
  public ModelRenderer abyssalnomicon;
  
  public ModelJzahar(boolean renderStaff) {
    this.field_78090_t = 128;
    this.field_78089_u = 64;
    this.head = new ModelRenderer(this, 72, 0);
    this.head.func_78789_a(-5.0F, -10.0F, -5.0F, 10, 10, 10);
    this.head.func_78793_a(2.0F, -28.0F, 1.0F);
    this.head.func_78787_b(64, 32);
    this.head.field_78809_i = true;
    setRotation(this.head, 0.0F, 0.0F, 0.0F);
    this.mask1 = new ModelRenderer(this, 102, 0);
    this.mask1.func_78789_a(-3.5F, -10.0F, -7.0F, 6, 8, 1);
    this.mask1.func_78793_a(0.0F, 0.0F, 0.0F);
    this.mask1.func_78787_b(64, 32);
    this.mask1.field_78809_i = true;
    setRotation(this.mask1, 0.0F, 0.3490659F, 0.0F);
    this.head.func_78792_a(this.mask1);
    this.mask2 = new ModelRenderer(this, 102, 0);
    this.mask2.func_78789_a(-2.5F, -10.0F, -7.0F, 6, 8, 1);
    this.mask2.func_78793_a(0.0F, 0.0F, 0.0F);
    this.mask2.func_78787_b(64, 32);
    this.mask2.field_78809_i = true;
    setRotation(this.mask2, 0.0F, -0.3490659F, 0.0F);
    this.head.func_78792_a(this.mask2);
    this.facetentacle1 = new ModelRenderer(this, 116, 0);
    this.facetentacle1.func_78789_a(0.0F, 0.0F, 0.0F, 2, 6, 2);
    this.facetentacle1.func_78793_a(-4.0F, -2.0F, -7.0F);
    this.facetentacle1.func_78787_b(64, 32);
    this.facetentacle1.field_78809_i = true;
    setRotation(this.facetentacle1, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.facetentacle1);
    this.facetentacle2 = new ModelRenderer(this, 116, 0);
    this.facetentacle2.func_78789_a(0.0F, 0.0F, 0.0F, 2, 6, 2);
    this.facetentacle2.func_78793_a(-1.0F, -2.0F, -7.0F);
    this.facetentacle2.func_78787_b(64, 32);
    this.facetentacle2.field_78809_i = true;
    setRotation(this.facetentacle2, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.facetentacle2);
    this.facetentacle3 = new ModelRenderer(this, 116, 0);
    this.facetentacle3.func_78789_a(0.0F, 0.0F, 0.0F, 2, 6, 2);
    this.facetentacle3.func_78793_a(2.0F, -2.0F, -7.0F);
    this.facetentacle3.func_78787_b(64, 32);
    this.facetentacle3.field_78809_i = true;
    setRotation(this.facetentacle3, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.facetentacle3);
    this.body1 = new ModelRenderer(this, 0, 0);
    this.body1.func_78789_a(0.0F, 0.0F, 0.0F, 18, 22, 5);
    this.body1.func_78793_a(-7.0F, -28.0F, 0.0F);
    this.body1.func_78787_b(64, 32);
    this.body1.field_78809_i = true;
    setRotation(this.body1, 0.0F, 0.0F, 0.0F);
    this.body2 = new ModelRenderer(this, 46, 0);
    this.body2.func_78789_a(0.0F, 0.0F, 0.0F, 5, 26, 1);
    this.body2.func_78793_a(-7.0F, -28.0F, -1.0F);
    this.body2.func_78787_b(64, 32);
    this.body2.field_78809_i = true;
    setRotation(this.body2, 0.0F, 0.0F, 0.0F);
    this.body3 = new ModelRenderer(this, 58, 0);
    this.body3.func_78789_a(0.0F, 0.0F, 0.0F, 5, 26, 1);
    this.body3.func_78793_a(6.0F, -28.0F, -1.0F);
    this.body3.func_78787_b(64, 32);
    this.body3.field_78809_i = true;
    setRotation(this.body3, 0.0F, 0.0F, 0.0F);
    this.body4 = new ModelRenderer(this, 46, 0);
    this.body4.func_78789_a(0.0F, 0.0F, 0.0F, 4, 26, 1);
    this.body4.func_78793_a(-7.0F, -28.0F, -2.0F);
    this.body4.func_78787_b(64, 32);
    this.body4.field_78809_i = true;
    setRotation(this.body4, 0.0F, 0.0F, 0.0F);
    this.body5 = new ModelRenderer(this, 59, 0);
    this.body5.func_78789_a(0.0F, 0.0F, 0.0F, 4, 26, 1);
    this.body5.func_78793_a(7.0F, -28.0F, -2.0F);
    this.body5.func_78787_b(64, 32);
    this.body5.field_78809_i = true;
    setRotation(this.body5, 0.0F, 0.0F, 0.0F);
    this.eye1 = new ModelRenderer(this, 70, 0);
    this.eye1.func_78789_a(0.0F, 0.0F, 0.0F, 5, 5, 1);
    this.eye1.func_78793_a(-0.5F, -21.0F, -1.0F);
    this.eye1.func_78787_b(64, 32);
    this.eye1.field_78809_i = true;
    setRotation(this.eye1, 0.0F, 0.0F, 0.0F);
    this.eye2 = new ModelRenderer(this, 70, 6);
    this.eye2.func_78789_a(-1.0F, -1.0F, 0.0F, 2, 2, 1);
    this.eye2.func_78793_a(2.0F, -18.5F, -1.5F);
    this.eye2.func_78787_b(64, 32);
    this.eye2.field_78809_i = true;
    setRotation(this.eye2, 0.0F, 0.0F, 0.0F);
    this.tentacle1 = new ModelRenderer(this, 0, 27);
    this.tentacle1.func_78789_a(-1.5F, 0.0F, -1.5F, 3, 9, 3);
    this.tentacle1.func_78793_a(-2.5F, -6.5F, 2.0F);
    this.tentacle1.func_78787_b(64, 32);
    this.tentacle1.field_78809_i = true;
    setRotation(this.tentacle1, -0.0872665F, 0.0F, 0.0872665F);
    this.tentacle12 = new ModelRenderer(this, 0, 27);
    this.tentacle12.func_78789_a(-1.5F, 0.0F, -1.5F, 3, 9, 3);
    this.tentacle12.func_78793_a(-3.3F, 2.3F, 1.2F);
    this.tentacle12.func_78787_b(64, 32);
    this.tentacle12.field_78809_i = true;
    setRotation(this.tentacle12, -0.0872665F, 0.0F, 0.1745329F);
    this.tentacle13 = new ModelRenderer(this, 0, 27);
    this.tentacle13.func_78789_a(-1.5F, 0.0F, -1.5F, 3, 9, 3);
    this.tentacle13.func_78793_a(-4.8F, 10.8F, 0.4F);
    this.tentacle13.func_78787_b(64, 32);
    this.tentacle13.field_78809_i = true;
    setRotation(this.tentacle13, -0.0872665F, 0.0F, 0.0F);
    this.foot1 = new ModelRenderer(this, 0, 39);
    this.foot1.func_78789_a(-1.5F, 0.0F, -5.0F, 3, 3, 6);
    this.foot1.func_78793_a(-4.8F, 19.1F, -0.2F);
    this.foot1.func_78787_b(64, 32);
    this.foot1.field_78809_i = true;
    setRotation(this.foot1, 0.2617994F, 0.0F, 0.0F);
    this.tentacle2 = new ModelRenderer(this, 0, 27);
    this.tentacle2.func_78789_a(-1.5F, 0.0F, -1.5F, 3, 9, 3);
    this.tentacle2.func_78793_a(0.5F, -6.5F, 2.0F);
    this.tentacle2.func_78787_b(64, 32);
    this.tentacle2.field_78809_i = true;
    setRotation(this.tentacle2, -0.0872665F, 0.0F, 0.0349066F);
    this.tentacle22 = new ModelRenderer(this, 0, 27);
    this.tentacle22.func_78789_a(-1.5F, 0.0F, -1.5F, 3, 9, 3);
    this.tentacle22.func_78793_a(0.2F, 2.3F, 1.2F);
    this.tentacle22.func_78787_b(64, 32);
    this.tentacle22.field_78809_i = true;
    setRotation(this.tentacle22, -0.1745329F, 0.0F, 0.0523599F);
    this.tentacle23 = new ModelRenderer(this, 0, 27);
    this.tentacle23.func_78789_a(-1.5F, 0.0F, -1.5F, 3, 9, 3);
    this.tentacle23.func_78793_a(-0.2F, 10.7F, -0.4F);
    this.tentacle23.func_78787_b(64, 32);
    this.tentacle23.field_78809_i = true;
    setRotation(this.tentacle23, 0.0872665F, 0.0F, 0.0872665F);
    this.foot2 = new ModelRenderer(this, 0, 39);
    this.foot2.func_78789_a(-1.5F, 0.0F, -5.0F, 3, 3, 6);
    this.foot2.func_78793_a(-1.0F, 19.0F, 0.7F);
    this.foot2.func_78787_b(64, 32);
    this.foot2.field_78809_i = true;
    setRotation(this.foot2, 0.2617994F, 0.0F, 0.0F);
    this.tentacle3 = new ModelRenderer(this, 0, 27);
    this.tentacle3.func_78789_a(-1.5F, 0.0F, -1.5F, 3, 9, 3);
    this.tentacle3.func_78793_a(3.5F, -6.5F, 2.0F);
    this.tentacle3.func_78787_b(64, 32);
    this.tentacle3.field_78809_i = true;
    setRotation(this.tentacle3, -0.0872665F, 0.0F, -0.0349066F);
    this.tentacle32 = new ModelRenderer(this, 0, 27);
    this.tentacle32.func_78789_a(-1.5F, 0.0F, -1.5F, 3, 9, 3);
    this.tentacle32.func_78793_a(3.8F, 2.3F, 1.2F);
    this.tentacle32.func_78787_b(64, 32);
    this.tentacle32.field_78809_i = true;
    setRotation(this.tentacle32, -0.0698132F, 0.0F, -0.0698132F);
    this.tentacle33 = new ModelRenderer(this, 0, 27);
    this.tentacle33.func_78789_a(-1.5F, 0.0F, -1.5F, 3, 9, 3);
    this.tentacle33.func_78793_a(4.4F, 11.0F, 0.6F);
    this.tentacle33.func_78787_b(64, 32);
    this.tentacle33.field_78809_i = true;
    setRotation(this.tentacle33, -0.1745329F, 0.0F, 0.0F);
    this.foot3 = new ModelRenderer(this, 0, 39);
    this.foot3.func_78789_a(-1.5F, 0.0F, -5.0F, 3, 3, 6);
    this.foot3.func_78793_a(4.4F, 19.1F, -0.7F);
    this.foot3.func_78787_b(64, 32);
    this.foot3.field_78809_i = true;
    setRotation(this.foot3, 0.2617994F, 0.0F, 0.0F);
    this.tentacle4 = new ModelRenderer(this, 0, 27);
    this.tentacle4.func_78789_a(-1.5F, 0.0F, -1.5F, 3, 9, 3);
    this.tentacle4.func_78793_a(6.5F, -6.5F, 2.0F);
    this.tentacle4.func_78787_b(64, 32);
    this.tentacle4.field_78809_i = true;
    setRotation(this.tentacle4, -0.0872665F, 0.0F, -0.0872665F);
    this.tentacle42 = new ModelRenderer(this, 0, 27);
    this.tentacle42.func_78789_a(-1.5F, 0.0F, -1.5F, 3, 9, 3);
    this.tentacle42.func_78793_a(7.2F, 2.0F, 1.2F);
    this.tentacle42.func_78787_b(64, 32);
    this.tentacle42.field_78809_i = true;
    setRotation(this.tentacle42, 0.0872665F, 0.0F, -0.1745329F);
    this.tentacle43 = new ModelRenderer(this, 0, 27);
    this.tentacle43.func_78789_a(-1.5F, 0.0F, -1.5F, 3, 9, 3);
    this.tentacle43.func_78793_a(8.7F, 10.6F, 2.0F);
    this.tentacle43.func_78787_b(64, 32);
    this.tentacle43.field_78809_i = true;
    setRotation(this.tentacle43, 0.1745329F, 0.0F, -0.0872665F);
    this.foot4 = new ModelRenderer(this, 0, 39);
    this.foot4.func_78789_a(-1.5F, 0.0F, -5.0F, 3, 3, 6);
    this.foot4.func_78793_a(9.4F, 19.0F, 4.0F);
    this.foot4.func_78787_b(64, 32);
    this.foot4.field_78809_i = true;
    setRotation(this.foot4, 0.2617994F, 0.0F, 0.0F);
    this.tentacle5 = new ModelRenderer(this, 0, 27);
    this.tentacle5.func_78789_a(-1.5F, 0.0F, -1.5F, 3, 9, 3);
    this.tentacle5.func_78793_a(-1.0F, -6.5F, 3.0F);
    this.tentacle5.func_78787_b(64, 32);
    this.tentacle5.field_78809_i = true;
    setRotation(this.tentacle5, 0.0872665F, 0.0F, 0.0872665F);
    this.tentacle52 = new ModelRenderer(this, 0, 27);
    this.tentacle52.func_78789_a(-1.5F, 0.0F, -1.5F, 3, 3, 3);
    this.tentacle52.func_78793_a(-1.7F, 2.0F, 3.8F);
    this.tentacle52.func_78787_b(64, 32);
    this.tentacle52.field_78809_i = true;
    setRotation(this.tentacle52, 0.0F, 0.0F, 0.2617994F);
    this.tentacle53 = new ModelRenderer(this, 0, 27);
    this.tentacle53.func_78789_a(-1.5F, 0.0F, -1.5F, 3, 3, 3);
    this.tentacle53.func_78793_a(-2.3F, 4.7F, 3.8F);
    this.tentacle53.func_78787_b(64, 32);
    this.tentacle53.field_78809_i = true;
    setRotation(this.tentacle53, 0.0F, 0.0F, 0.4363323F);
    this.tentacle54 = new ModelRenderer(this, 12, 27);
    this.tentacle54.func_78789_a(-5.0F, -1.5F, -1.5F, 5, 3, 3);
    this.tentacle54.func_78793_a(-2.2F, 6.6F, 3.8F);
    this.tentacle54.func_78787_b(64, 32);
    this.tentacle54.field_78809_i = true;
    setRotation(this.tentacle54, 0.0F, 0.0F, 0.0F);
    this.tentacle55 = new ModelRenderer(this, 0, 30);
    this.tentacle55.func_78789_a(-3.0F, -1.5F, -1.5F, 3, 3, 3);
    this.tentacle55.func_78793_a(-6.4F, 6.7F, 3.9F);
    this.tentacle55.func_78787_b(64, 32);
    this.tentacle55.field_78809_i = true;
    setRotation(this.tentacle55, 0.0F, -0.2617994F, 0.2617994F);
    this.arm = new ModelRenderer(this, 12, 33);
    this.arm.func_78789_a(-1.5F, -9.0F, -1.5F, 3, 9, 3);
    this.arm.func_78793_a(-8.2F, 6.7F, 3.4F);
    this.arm.func_78787_b(64, 32);
    this.arm.field_78809_i = true;
    setRotation(this.arm, 0.0F, -0.2617994F, -0.5235988F);
    this.Staff1 = new ModelRenderer(this, 54, 27);
    this.Staff1.func_78789_a(-4.8F, -20.7F, -1.4F, 1, 18, 1);
    this.Staff1.func_78793_a(0.0F, 0.0F, 0.0F);
    this.Staff1.func_78787_b(64, 32);
    this.Staff1.field_78809_i = true;
    setRotation(this.Staff1, 0.0F, -this.arm.field_78796_g, -this.arm.field_78808_h);
    if (renderStaff)
      this.arm.func_78792_a(this.Staff1); 
    this.Staff2 = new ModelRenderer(this, 62, 27);
    this.Staff2.func_78789_a(6.0F, -23.8F, -1.4F, 1, 4, 1);
    this.Staff2.func_78793_a(0.0F, 0.0F, 0.0F);
    this.Staff2.func_78787_b(64, 32);
    this.Staff2.field_78809_i = true;
    setRotation(this.Staff2, 0.0F, 0.0F, -0.5235988F);
    this.Staff1.func_78792_a(this.Staff2);
    this.Staff3 = new ModelRenderer(this, 66, 27);
    this.Staff3.func_78789_a(-6.7F, -25.6F, -1.4F, 1, 2, 1);
    this.Staff3.func_78793_a(0.0F, 0.0F, 0.0F);
    this.Staff3.func_78787_b(64, 32);
    this.Staff3.field_78809_i = true;
    setRotation(this.Staff3, 0.0F, 0.0F, 0.0F);
    this.Staff1.func_78792_a(this.Staff3);
    this.Staff4 = new ModelRenderer(this, 66, 30);
    this.Staff4.func_78789_a(-21.6F, -19.3F, -1.4F, 1, 4, 1);
    this.Staff4.func_78793_a(0.0F, 0.0F, 0.0F);
    this.Staff4.func_78787_b(64, 32);
    this.Staff4.field_78809_i = true;
    setRotation(this.Staff4, 0.0F, 0.0F, 0.6981317F);
    this.Staff1.func_78792_a(this.Staff4);
    this.Staff5 = new ModelRenderer(this, 62, 32);
    this.Staff5.func_78789_a(18.3F, -21.5F, -1.4F, 1, 4, 1);
    this.Staff5.func_78793_a(0.0F, 0.0F, 0.0F);
    this.Staff5.func_78787_b(64, 32);
    this.Staff5.field_78809_i = true;
    setRotation(this.Staff5, 0.0F, 0.0F, -0.8726646F);
    this.Staff1.func_78792_a(this.Staff5);
    this.Staff6 = new ModelRenderer(this, 66, 35);
    this.Staff6.func_78789_a(-3.9F, 1.0F, -4.1F, 1, 1, 1);
    this.Staff6.func_78793_a(0.0F, 0.0F, 0.0F);
    this.Staff6.func_78787_b(64, 32);
    this.Staff6.field_78809_i = true;
    setRotation(this.Staff6, -0.8922867F, 0.5948578F, 0.2230717F);
    this.Staff1.func_78792_a(this.Staff6);
    this.Cube = new ModelRenderer(this, 62, 42);
    this.Cube.func_78789_a(-14.0F, -20.0F, -8.5F, 2, 2, 2);
    this.Cube.func_78793_a(0.0F, 0.0F, 0.0F);
    this.Cube.func_78787_b(64, 32);
    this.Cube.field_78809_i = true;
    setRotation(this.Cube, 0.0F, 0.4833219F, 0.5205006F);
    this.Staff1.func_78792_a(this.Cube);
    this.tentacle6 = new ModelRenderer(this, 0, 27);
    this.tentacle6.func_78789_a(-1.5F, 0.0F, -1.5F, 3, 9, 3);
    this.tentacle6.func_78793_a(2.0F, -6.5F, 3.0F);
    this.tentacle6.func_78787_b(64, 32);
    this.tentacle6.field_78809_i = true;
    setRotation(this.tentacle6, 0.0872665F, 0.0F, 0.0F);
    this.tentacle62 = new ModelRenderer(this, 0, 27);
    this.tentacle62.func_78789_a(-1.5F, 0.0F, -1.5F, 3, 9, 3);
    this.tentacle62.func_78793_a(2.0F, 2.3F, 3.8F);
    this.tentacle62.func_78787_b(64, 32);
    this.tentacle62.field_78809_i = true;
    setRotation(this.tentacle62, 0.1745329F, 0.0F, 0.0872665F);
    this.tentacle63 = new ModelRenderer(this, 0, 27);
    this.tentacle63.func_78789_a(-1.5F, 0.0F, -1.5F, 3, 9, 3);
    this.tentacle63.func_78793_a(1.2F, 10.5F, 5.3F);
    this.tentacle63.func_78787_b(64, 32);
    this.tentacle63.field_78809_i = true;
    setRotation(this.tentacle63, -0.0872665F, 0.0F, -0.0872665F);
    this.foot5 = new ModelRenderer(this, 0, 39);
    this.foot5.func_78789_a(-1.5F, 0.0F, -1.0F, 3, 3, 6);
    this.foot5.func_78793_a(1.9F, 19.2F, 4.1F);
    this.foot5.func_78787_b(64, 32);
    this.foot5.field_78809_i = true;
    setRotation(this.foot5, -0.2617994F, 0.0F, 0.0F);
    this.tentacle7 = new ModelRenderer(this, 0, 27);
    this.tentacle7.func_78789_a(-1.5F, 0.0F, -1.5F, 3, 9, 3);
    this.tentacle7.func_78793_a(5.0F, -6.5F, 3.0F);
    this.tentacle7.func_78787_b(64, 32);
    this.tentacle7.field_78809_i = true;
    setRotation(this.tentacle7, 0.0872665F, -0.0872665F, -0.0872665F);
    this.tentacle72 = new ModelRenderer(this, 0, 27);
    this.tentacle72.func_78789_a(-1.5F, 0.0F, -1.5F, 3, 4, 3);
    this.tentacle72.func_78793_a(5.7F, 1.9F, 3.8F);
    this.tentacle72.func_78787_b(64, 32);
    this.tentacle72.field_78809_i = true;
    setRotation(this.tentacle72, 0.0F, -0.1745329F, -0.3490659F);
    this.tentacle73 = new ModelRenderer(this, 0, 27);
    this.tentacle73.func_78789_a(-1.5F, 0.0F, -1.5F, 3, 5, 3);
    this.tentacle73.func_78793_a(6.7F, 5.1F, 3.8F);
    this.tentacle73.func_78787_b(64, 32);
    this.tentacle73.field_78809_i = true;
    setRotation(this.tentacle73, 0.0F, -0.1745329F, -0.7853982F);
    this.tentacle74 = new ModelRenderer(this, 0, 30);
    this.tentacle74.func_78789_a(0.0F, -1.5F, -1.5F, 3, 3, 3);
    this.tentacle74.func_78793_a(9.2F, 8.2F, 3.9F);
    this.tentacle74.func_78787_b(64, 32);
    this.tentacle74.field_78809_i = true;
    setRotation(this.tentacle74, 0.0F, -0.1745329F, 0.0F);
    this.tentacle75 = new ModelRenderer(this, 12, 33);
    this.tentacle75.func_78789_a(-1.5F, -6.0F, -1.5F, 3, 6, 3);
    this.tentacle75.func_78793_a(10.9F, 9.0F, 4.5F);
    this.tentacle75.func_78787_b(64, 32);
    this.tentacle75.field_78809_i = true;
    setRotation(this.tentacle75, 0.0F, -0.1745329F, 0.5235988F);
    this.tentacle76 = new ModelRenderer(this, 12, 33);
    this.tentacle76.func_78789_a(-1.5F, -3.0F, -1.5F, 3, 3, 3);
    this.tentacle76.func_78793_a(13.7F, 4.3F, 4.5F);
    this.tentacle76.func_78787_b(64, 32);
    this.tentacle76.field_78809_i = true;
    setRotation(this.tentacle76, 0.0F, -0.1745329F, 0.2617994F);
    this.tentacle77 = new ModelRenderer(this, 12, 33);
    this.tentacle77.func_78789_a(-1.5F, -6.0F, -1.5F, 3, 6, 3);
    this.tentacle77.func_78793_a(14.3F, 1.9F, 4.7F);
    this.tentacle77.func_78787_b(64, 32);
    this.tentacle77.field_78809_i = true;
    setRotation(this.tentacle77, 0.3490659F, -0.1745329F, 0.2617994F);
    this.abyssalnomicon = new ModelRenderer(this, 28, 27);
    this.abyssalnomicon.func_78789_a(-5.0F, -12.0F, -1.5F, 10, 12, 3);
    this.abyssalnomicon.func_78793_a(17.0F, -5.0F, 3.0F);
    this.abyssalnomicon.func_78787_b(64, 32);
    this.abyssalnomicon.field_78809_i = true;
    setRotation(this.abyssalnomicon, 0.0F, 0.0F, 0.0F);
  }
  
  public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    super.func_78088_a(entity, f, f1, f2, f3, f4, f5);
    func_78087_a(f, f1, f2, f3, f4, f5, entity);
    this.head.func_78785_a(f5);
    this.body1.func_78785_a(f5);
    this.body2.func_78785_a(f5);
    this.body3.func_78785_a(f5);
    this.body4.func_78785_a(f5);
    this.body5.func_78785_a(f5);
    this.eye1.func_78785_a(f5);
    this.eye2.func_78785_a(f5);
    this.tentacle1.func_78785_a(f5);
    this.tentacle12.func_78785_a(f5);
    this.tentacle13.func_78785_a(f5);
    this.foot1.func_78785_a(f5);
    this.tentacle2.func_78785_a(f5);
    this.tentacle22.func_78785_a(f5);
    this.tentacle23.func_78785_a(f5);
    this.foot2.func_78785_a(f5);
    this.tentacle3.func_78785_a(f5);
    this.tentacle32.func_78785_a(f5);
    this.tentacle33.func_78785_a(f5);
    this.foot3.func_78785_a(f5);
    this.tentacle4.func_78785_a(f5);
    this.tentacle42.func_78785_a(f5);
    this.tentacle43.func_78785_a(f5);
    this.foot4.func_78785_a(f5);
    this.tentacle5.func_78785_a(f5);
    this.tentacle52.func_78785_a(f5);
    this.tentacle53.func_78785_a(f5);
    this.tentacle54.func_78785_a(f5);
    this.tentacle55.func_78785_a(f5);
    this.arm.func_78785_a(f5);
    this.tentacle6.func_78785_a(f5);
    this.tentacle62.func_78785_a(f5);
    this.tentacle63.func_78785_a(f5);
    this.foot5.func_78785_a(f5);
    this.tentacle7.func_78785_a(f5);
    this.tentacle72.func_78785_a(f5);
    this.tentacle73.func_78785_a(f5);
    this.tentacle74.func_78785_a(f5);
    this.tentacle75.func_78785_a(f5);
    this.tentacle76.func_78785_a(f5);
    this.tentacle77.func_78785_a(f5);
    this.abyssalnomicon.func_78785_a(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z) {
    model.field_78795_f = x;
    model.field_78796_g = y;
    model.field_78808_h = z;
  }
  
  public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
    EntityJzahar jzahar = (EntityJzahar)entity;
    this.head.func_78793_a(2.0F, -28.0F, 1.0F);
    this.mask1.func_78793_a(0.0F, 0.0F, 0.0F);
    setRotation(this.mask1, 0.0F, 0.3490659F, 0.0F);
    this.mask2.func_78793_a(0.0F, 0.0F, 0.0F);
    setRotation(this.mask2, 0.0F, -0.3490659F, 0.0F);
    this.facetentacle1.func_78793_a(-4.0F, -2.0F, -7.0F);
    setRotation(this.facetentacle1, 0.0F, 0.0F, 0.0F);
    this.facetentacle2.func_78793_a(-1.0F, -2.0F, -7.0F);
    setRotation(this.facetentacle2, 0.0F, 0.0F, 0.0F);
    this.facetentacle3.func_78793_a(2.0F, -2.0F, -7.0F);
    setRotation(this.facetentacle3, 0.0F, 0.0F, 0.0F);
    this.body1.func_78793_a(-7.0F, -28.0F, 0.0F);
    setRotation(this.body1, 0.0F, 0.0F, 0.0F);
    this.body2.func_78793_a(-7.0F, -28.0F, -1.0F);
    setRotation(this.body2, 0.0F, 0.0F, 0.0F);
    this.body3.func_78793_a(6.0F, -28.0F, -1.0F);
    setRotation(this.body3, 0.0F, 0.0F, 0.0F);
    this.body4.func_78793_a(-7.0F, -28.0F, -2.0F);
    setRotation(this.body4, 0.0F, 0.0F, 0.0F);
    this.body5.func_78793_a(7.0F, -28.0F, -2.0F);
    setRotation(this.body5, 0.0F, 0.0F, 0.0F);
    this.eye1.func_78793_a(-0.5F, -21.0F, -1.0F);
    setRotation(this.eye1, 0.0F, 0.0F, 0.0F);
    this.eye2.func_78793_a(2.0F, -18.5F, -1.5F);
    setRotation(this.eye2, 0.0F, 0.0F, 0.0F);
    this.tentacle1.func_78793_a(-2.5F, -6.5F, 2.0F);
    setRotation(this.tentacle1, -0.0872665F, 0.0F, 0.0872665F);
    this.tentacle12.func_78793_a(-3.3F, 2.3F, 1.2F);
    setRotation(this.tentacle12, -0.0872665F, 0.0F, 0.1745329F);
    this.tentacle13.func_78793_a(-4.8F, 10.8F, 0.4F);
    setRotation(this.tentacle13, -0.0872665F, 0.0F, 0.0F);
    this.foot1.func_78793_a(-4.8F, 19.1F, -0.2F);
    setRotation(this.foot1, 0.2617994F, 0.0F, 0.0F);
    this.tentacle2.func_78793_a(0.5F, -6.5F, 2.0F);
    setRotation(this.tentacle2, -0.0872665F, 0.0F, 0.0349066F);
    this.tentacle22.func_78793_a(0.2F, 2.3F, 1.2F);
    setRotation(this.tentacle22, -0.1745329F, 0.0F, 0.0523599F);
    this.tentacle23.func_78793_a(-0.2F, 10.7F, -0.4F);
    setRotation(this.tentacle23, 0.0872665F, 0.0F, 0.0872665F);
    this.foot2.func_78793_a(-1.0F, 19.0F, 0.7F);
    setRotation(this.foot2, 0.2617994F, 0.0F, 0.0F);
    this.tentacle3.func_78793_a(3.5F, -6.5F, 2.0F);
    setRotation(this.tentacle3, -0.0872665F, 0.0F, -0.0349066F);
    this.tentacle32.func_78793_a(3.8F, 2.3F, 1.2F);
    setRotation(this.tentacle32, -0.0698132F, 0.0F, -0.0698132F);
    this.tentacle33.func_78793_a(4.4F, 11.0F, 0.6F);
    setRotation(this.tentacle33, -0.1745329F, 0.0F, 0.0F);
    this.foot3.func_78793_a(4.4F, 19.1F, -0.7F);
    setRotation(this.foot3, 0.2617994F, 0.0F, 0.0F);
    this.tentacle4.func_78793_a(6.5F, -6.5F, 2.0F);
    setRotation(this.tentacle4, -0.0872665F, 0.0F, -0.0872665F);
    this.tentacle42.func_78793_a(7.2F, 2.0F, 1.2F);
    setRotation(this.tentacle42, 0.0872665F, 0.0F, -0.1745329F);
    this.tentacle43.func_78793_a(8.7F, 10.6F, 2.0F);
    setRotation(this.tentacle43, 0.1745329F, 0.0F, -0.0872665F);
    this.foot4.func_78793_a(9.4F, 19.0F, 4.0F);
    setRotation(this.foot4, 0.2617994F, 0.0F, 0.0F);
    this.tentacle5.func_78793_a(-1.0F, -6.5F, 3.0F);
    setRotation(this.tentacle5, 0.0872665F, 0.0F, 0.0872665F);
    this.tentacle52.func_78793_a(-1.7F, 2.0F, 3.8F);
    setRotation(this.tentacle52, 0.0F, 0.0F, 0.2617994F);
    this.tentacle53.func_78793_a(-2.3F, 4.7F, 3.8F);
    setRotation(this.tentacle53, 0.0F, 0.0F, 0.4363323F);
    this.tentacle54.func_78793_a(-2.2F, 6.6F, 3.8F);
    setRotation(this.tentacle54, 0.0F, 0.0F, 0.0F);
    this.tentacle55.func_78793_a(-6.4F, 6.7F, 3.9F);
    setRotation(this.tentacle55, 0.0F, -0.2617994F, 0.2617994F);
    this.arm.func_78793_a(-8.2F, 6.7F, 3.4F);
    setRotation(this.arm, 0.0F, -0.2617994F, -0.5235988F);
    this.Staff1.func_78793_a(0.0F, 0.0F, 0.0F);
    setRotation(this.Staff1, 0.0F, -this.arm.field_78796_g, -this.arm.field_78808_h);
    this.Staff2.func_78793_a(0.0F, 0.0F, 0.0F);
    setRotation(this.Staff2, 0.0F, 0.0F, -0.5235988F);
    this.Staff3.func_78793_a(0.0F, 0.0F, 0.0F);
    setRotation(this.Staff3, 0.0F, 0.0F, 0.0F);
    this.Staff4.func_78793_a(0.0F, 0.0F, 0.0F);
    setRotation(this.Staff4, 0.0F, 0.0F, 0.6981317F);
    this.Staff5.func_78793_a(0.0F, 0.0F, 0.0F);
    setRotation(this.Staff5, 0.0F, 0.0F, -0.8726646F);
    this.Staff6.func_78793_a(0.0F, 0.0F, 0.0F);
    setRotation(this.Staff6, -0.8922867F, 0.5948578F, 0.2230717F);
    this.Cube.func_78793_a(0.0F, 0.0F, 0.0F);
    setRotation(this.Cube, 0.0F, 0.4833219F, 0.5205006F);
    this.tentacle6.func_78793_a(2.0F, -6.5F, 3.0F);
    setRotation(this.tentacle6, 0.0872665F, 0.0F, 0.0F);
    this.tentacle62.func_78793_a(2.0F, 2.3F, 3.8F);
    setRotation(this.tentacle62, 0.1745329F, 0.0F, 0.0872665F);
    this.tentacle63.func_78793_a(1.2F, 10.5F, 5.3F);
    setRotation(this.tentacle63, -0.0872665F, 0.0F, -0.0872665F);
    this.foot5.func_78793_a(1.9F, 19.2F, 4.1F);
    setRotation(this.foot5, -0.2617994F, 0.0F, 0.0F);
    this.tentacle7.func_78793_a(5.0F, -6.5F, 3.0F);
    setRotation(this.tentacle7, 0.0872665F, -0.0872665F, -0.0872665F);
    this.tentacle72.func_78793_a(5.7F, 1.9F, 3.8F);
    setRotation(this.tentacle72, 0.0F, -0.1745329F, -0.3490659F);
    this.tentacle73.func_78793_a(6.7F, 5.1F, 3.8F);
    setRotation(this.tentacle73, 0.0F, -0.1745329F, -0.7853982F);
    this.tentacle74.func_78793_a(9.2F, 8.2F, 3.9F);
    setRotation(this.tentacle74, 0.0F, -0.1745329F, 0.0F);
    this.tentacle75.func_78793_a(10.9F, 9.0F, 4.5F);
    setRotation(this.tentacle75, 0.0F, -0.1745329F, 0.5235988F);
    this.tentacle76.func_78793_a(13.7F, 4.3F, 4.5F);
    setRotation(this.tentacle76, 0.0F, -0.1745329F, 0.2617994F);
    this.tentacle77.func_78793_a(14.3F, 1.9F, 4.7F);
    setRotation(this.tentacle77, 0.3490659F, -0.1745329F, 0.2617994F);
    this.abyssalnomicon.func_78793_a(17.0F, -5.0F, 3.0F);
    setRotation(this.abyssalnomicon, 0.0F, 0.0F, 0.0F);
    this.head.field_78796_g = f3 / 57.295776F;
    this.head.field_78795_f = f4 / 57.295776F;
    for (int i = 0; i < 4; i++)
      this.abyssalnomicon.field_78797_d = -5.5F + MathHelper.func_76134_b(((i * 2) + f2) * 0.25F); 
    if (jzahar.getTimer(0) > 960 || jzahar.getTimer(2) > 1160 || jzahar.getTimer(1) > 1560) {
      setRotation(this.abyssalnomicon, 0.0F, MathHelper.func_76126_a(f2 * 20.0F), 0.0F);
    } else {
      setRotation(this.abyssalnomicon, 0.0F, 0.0F, 0.0F);
    } 
    this.foot1.field_78796_g = MathHelper.func_76134_b(f * 0.6662F) * 2.0F * f1 * 0.5F;
    this.foot2.field_78796_g = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 1.4F * f1;
    this.foot3.field_78796_g = MathHelper.func_76134_b(f * 0.6662F) * 1.4F * f1;
    this.foot4.field_78796_g = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.foot5.field_78796_g = MathHelper.func_76134_b(f * 0.6662F) * 2.0F * f1 * 0.5F;
    setRotation(this.arm, 0.0F, -0.2617994F, -0.5235988F);
    if (jzahar.getJukeboxToDanceTo() != null) {
      setRotation(this.abyssalnomicon, 0.0F, MathHelper.func_76126_a(jzahar.field_70173_aa), 0.0F);
      this.arm.field_78795_f = 0.5F + MathHelper.func_76126_a(f2 * 0.5F - 1.0F) * 0.5F;
      this.arm.field_78796_g = MathHelper.func_76134_b(f2 * 0.25F) * 0.5F;
      this.head.field_78795_f += MathHelper.func_76134_b(f2 * 0.5F) * 0.25F;
      this.head.field_78796_g += MathHelper.func_76126_a(f2 * 0.25F) * 0.5F;
    } 
    if (this.field_78095_p > -9990.0F) {
      float f6 = this.field_78095_p;
      this.arm.field_78796_g += MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      f6 = 1.0F - this.field_78095_p;
      f6 *= f6;
      f6 *= f6;
      f6 = 1.0F - f6;
      float f7 = MathHelper.func_76126_a(f6 * 3.1415927F);
      float f8 = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F) * 0.75F;
      this.arm.field_78795_f = (float)(this.arm.field_78795_f + f7 * 1.2D + f8);
      this.arm.field_78796_g += MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F * 2.0F;
    } 
    if (jzahar.deathTicks > 0) {
      this.head.field_78795_f = 20.0F;
      this.eye1.field_78807_k = true;
      this.eye2.field_78807_k = true;
      this.abyssalnomicon.field_78807_k = true;
      this.facetentacle1.field_78807_k = true;
      this.facetentacle2.field_78807_k = true;
      this.facetentacle3.field_78807_k = true;
      this.tentacle1.field_78807_k = true;
      this.tentacle12.field_78807_k = true;
      this.tentacle13.field_78807_k = true;
      this.tentacle2.field_78807_k = true;
      this.tentacle22.field_78807_k = true;
      this.tentacle23.field_78807_k = true;
      this.tentacle3.field_78807_k = true;
      this.tentacle32.field_78807_k = true;
      this.tentacle33.field_78807_k = true;
      this.tentacle4.field_78807_k = true;
      this.tentacle42.field_78807_k = true;
      this.tentacle43.field_78807_k = true;
      this.tentacle5.field_78807_k = true;
      this.tentacle52.field_78807_k = true;
      this.tentacle53.field_78807_k = true;
      this.tentacle54.field_78807_k = true;
      this.tentacle55.field_78807_k = true;
      this.arm.field_78807_k = true;
      this.tentacle6.field_78807_k = true;
      this.tentacle62.field_78807_k = true;
      this.tentacle63.field_78807_k = true;
      this.tentacle7.field_78807_k = true;
      this.tentacle72.field_78807_k = true;
      this.tentacle73.field_78807_k = true;
      this.tentacle74.field_78807_k = true;
      this.tentacle75.field_78807_k = true;
      this.tentacle76.field_78807_k = true;
      this.tentacle77.field_78807_k = true;
      this.foot1.field_78807_k = true;
      this.foot2.field_78807_k = true;
      this.foot3.field_78807_k = true;
      this.foot4.field_78807_k = true;
      this.foot5.field_78807_k = true;
      for (int j = 0; j < 4; j++) {
        this.head.field_78797_d = -9.5F + MathHelper.func_76134_b(((j * 2) + f2) * 0.25F);
        this.body1.field_78797_d = -9.5F + MathHelper.func_76134_b(((j * 2) + f2) * 0.25F);
        this.body2.field_78797_d = -9.5F + MathHelper.func_76134_b(((j * 2) + f2) * 0.25F);
        this.body3.field_78797_d = -9.5F + MathHelper.func_76134_b(((j * 2) + f2) * 0.25F);
        this.body4.field_78797_d = -9.5F + MathHelper.func_76134_b(((j * 2) + f2) * 0.25F);
        this.body5.field_78797_d = -9.5F + MathHelper.func_76134_b(((j * 2) + f2) * 0.25F);
      } 
    } 
    if (jzahar.deathTicks == 0)
      if (jzahar.func_70093_af() || jzahar.getJukeboxToDanceTo() != null || !jzahar.field_70122_E || jzahar.field_70163_u <= 0.0D) {
        this.tentacle12.field_78795_f = -1.2566371F;
        this.tentacle22.field_78795_f = -1.2566371F;
        this.tentacle32.field_78795_f = -1.2566371F;
        this.tentacle42.field_78795_f = -1.2566371F;
        this.tentacle62.field_78795_f = -1.2566371F;
        this.tentacle13.field_78795_f = -1.2566371F;
        this.tentacle23.field_78795_f = -1.2566371F;
        this.tentacle33.field_78795_f = -1.2566371F;
        this.tentacle43.field_78795_f = -1.2566371F;
        this.tentacle63.field_78795_f = -1.2566371F;
        this.tentacle12.field_78796_g = 0.31415927F;
        this.tentacle22.field_78796_g = 0.31415927F;
        this.tentacle32.field_78796_g = -0.31415927F;
        this.tentacle42.field_78796_g = -0.31415927F;
        this.tentacle13.field_78796_g = 0.31415927F;
        this.tentacle23.field_78796_g = 0.31415927F;
        this.tentacle33.field_78796_g = -0.31415927F;
        this.tentacle43.field_78796_g = -0.31415927F;
        this.foot1.field_78795_f = -1.2566371F;
        this.foot2.field_78795_f = -1.2566371F;
        this.foot3.field_78795_f = -1.2566371F;
        this.foot4.field_78795_f = -1.2566371F;
        this.foot5.field_78795_f = -1.2566371F;
        this.foot1.field_78796_g = 0.31415927F;
        this.foot2.field_78796_g = 0.31415927F;
        this.foot3.field_78796_g = -0.31415927F;
        this.foot4.field_78796_g = -0.31415927F;
        this.foot5.field_78796_g = 0.31415927F;
        this.tentacle12.field_78798_e = 2.0F;
        this.tentacle22.field_78798_e = 2.0F;
        this.tentacle32.field_78798_e = 2.0F;
        this.tentacle42.field_78798_e = 2.0F;
        this.tentacle62.field_78798_e = 4.5F;
        this.tentacle12.field_78800_c = -3.5F;
        this.tentacle22.field_78800_c = 0.5F;
        this.tentacle32.field_78800_c = 3.3F;
        this.tentacle42.field_78800_c = 6.6F;
        this.tentacle13.field_78797_d = 4.6F;
        this.tentacle23.field_78797_d = 4.8F;
        this.tentacle33.field_78797_d = 5.0F;
        this.tentacle43.field_78797_d = 4.3F;
        this.tentacle63.field_78797_d = 5.3F;
        this.tentacle13.field_78798_e = -5.5F;
        this.tentacle23.field_78798_e = -5.5F;
        this.tentacle33.field_78798_e = -6.0F;
        this.tentacle43.field_78798_e = -6.0F;
        this.tentacle63.field_78798_e = -4.0F;
        this.tentacle13.field_78800_c = -6.2F;
        this.tentacle23.field_78800_c = -2.2F;
        this.tentacle33.field_78800_c = 6.0F;
        this.tentacle43.field_78800_c = 9.6F;
        this.tentacle63.field_78800_c = 2.0F;
        this.foot1.field_78800_c = -8.2F;
        this.foot2.field_78800_c = -4.5F;
        this.foot3.field_78800_c = 7.7F;
        this.foot4.field_78800_c = 11.6F;
        this.foot1.field_78797_d = 7.0F;
        this.foot2.field_78797_d = 7.0F;
        this.foot3.field_78797_d = 7.5F;
        this.foot4.field_78797_d = 6.5F;
        this.foot5.field_78797_d = 3.7F;
        this.foot1.field_78798_e = -12.0F;
        this.foot2.field_78798_e = -12.0F;
        this.foot3.field_78798_e = -12.0F;
        this.foot4.field_78798_e = -12.0F;
        this.foot5.field_78798_e = -12.0F;
      } else {
        this.head.field_78795_f = f4 / 57.295776F;
        this.eye1.field_78807_k = false;
        this.eye2.field_78807_k = false;
        this.abyssalnomicon.field_78807_k = false;
        this.facetentacle1.field_78807_k = false;
        this.facetentacle2.field_78807_k = false;
        this.facetentacle3.field_78807_k = false;
        this.tentacle1.field_78807_k = false;
        this.tentacle12.field_78807_k = false;
        this.tentacle13.field_78807_k = false;
        this.tentacle2.field_78807_k = false;
        this.tentacle22.field_78807_k = false;
        this.tentacle23.field_78807_k = false;
        this.tentacle3.field_78807_k = false;
        this.tentacle32.field_78807_k = false;
        this.tentacle33.field_78807_k = false;
        this.tentacle4.field_78807_k = false;
        this.tentacle42.field_78807_k = false;
        this.tentacle43.field_78807_k = false;
        this.tentacle5.field_78807_k = false;
        this.tentacle52.field_78807_k = false;
        this.tentacle53.field_78807_k = false;
        this.tentacle54.field_78807_k = false;
        this.tentacle55.field_78807_k = false;
        this.arm.field_78807_k = false;
        this.tentacle6.field_78807_k = false;
        this.tentacle62.field_78807_k = false;
        this.tentacle63.field_78807_k = false;
        this.tentacle7.field_78807_k = false;
        this.tentacle72.field_78807_k = false;
        this.tentacle73.field_78807_k = false;
        this.tentacle74.field_78807_k = false;
        this.tentacle75.field_78807_k = false;
        this.tentacle76.field_78807_k = false;
        this.tentacle77.field_78807_k = false;
        this.foot1.field_78807_k = false;
        this.foot2.field_78807_k = false;
        this.foot3.field_78807_k = false;
        this.foot4.field_78807_k = false;
        this.foot5.field_78807_k = false;
        this.head.field_78797_d = -28.0F;
        this.body1.field_78797_d = -28.0F;
        this.body2.field_78797_d = -28.0F;
        this.body3.field_78797_d = -28.0F;
        this.body4.field_78797_d = -28.0F;
        this.body5.field_78797_d = -28.0F;
      }  
  }
}
