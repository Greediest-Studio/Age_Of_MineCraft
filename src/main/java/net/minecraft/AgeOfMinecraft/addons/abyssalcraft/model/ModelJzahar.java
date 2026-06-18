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
    this.textureWidth = 128;
    this.textureHeight = 64;
    this.head = new ModelRenderer(this, 72, 0);
    this.head.addBox(-5.0F, -10.0F, -5.0F, 10, 10, 10);
    this.head.setRotationPoint(2.0F, -28.0F, 1.0F);
    this.head.setTextureSize(64, 32);
    this.head.mirror = true;
    setRotation(this.head, 0.0F, 0.0F, 0.0F);
    this.mask1 = new ModelRenderer(this, 102, 0);
    this.mask1.addBox(-3.5F, -10.0F, -7.0F, 6, 8, 1);
    this.mask1.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.mask1.setTextureSize(64, 32);
    this.mask1.mirror = true;
    setRotation(this.mask1, 0.0F, 0.3490659F, 0.0F);
    this.head.addChild(this.mask1);
    this.mask2 = new ModelRenderer(this, 102, 0);
    this.mask2.addBox(-2.5F, -10.0F, -7.0F, 6, 8, 1);
    this.mask2.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.mask2.setTextureSize(64, 32);
    this.mask2.mirror = true;
    setRotation(this.mask2, 0.0F, -0.3490659F, 0.0F);
    this.head.addChild(this.mask2);
    this.facetentacle1 = new ModelRenderer(this, 116, 0);
    this.facetentacle1.addBox(0.0F, 0.0F, 0.0F, 2, 6, 2);
    this.facetentacle1.setRotationPoint(-4.0F, -2.0F, -7.0F);
    this.facetentacle1.setTextureSize(64, 32);
    this.facetentacle1.mirror = true;
    setRotation(this.facetentacle1, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.facetentacle1);
    this.facetentacle2 = new ModelRenderer(this, 116, 0);
    this.facetentacle2.addBox(0.0F, 0.0F, 0.0F, 2, 6, 2);
    this.facetentacle2.setRotationPoint(-1.0F, -2.0F, -7.0F);
    this.facetentacle2.setTextureSize(64, 32);
    this.facetentacle2.mirror = true;
    setRotation(this.facetentacle2, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.facetentacle2);
    this.facetentacle3 = new ModelRenderer(this, 116, 0);
    this.facetentacle3.addBox(0.0F, 0.0F, 0.0F, 2, 6, 2);
    this.facetentacle3.setRotationPoint(2.0F, -2.0F, -7.0F);
    this.facetentacle3.setTextureSize(64, 32);
    this.facetentacle3.mirror = true;
    setRotation(this.facetentacle3, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.facetentacle3);
    this.body1 = new ModelRenderer(this, 0, 0);
    this.body1.addBox(0.0F, 0.0F, 0.0F, 18, 22, 5);
    this.body1.setRotationPoint(-7.0F, -28.0F, 0.0F);
    this.body1.setTextureSize(64, 32);
    this.body1.mirror = true;
    setRotation(this.body1, 0.0F, 0.0F, 0.0F);
    this.body2 = new ModelRenderer(this, 46, 0);
    this.body2.addBox(0.0F, 0.0F, 0.0F, 5, 26, 1);
    this.body2.setRotationPoint(-7.0F, -28.0F, -1.0F);
    this.body2.setTextureSize(64, 32);
    this.body2.mirror = true;
    setRotation(this.body2, 0.0F, 0.0F, 0.0F);
    this.body3 = new ModelRenderer(this, 58, 0);
    this.body3.addBox(0.0F, 0.0F, 0.0F, 5, 26, 1);
    this.body3.setRotationPoint(6.0F, -28.0F, -1.0F);
    this.body3.setTextureSize(64, 32);
    this.body3.mirror = true;
    setRotation(this.body3, 0.0F, 0.0F, 0.0F);
    this.body4 = new ModelRenderer(this, 46, 0);
    this.body4.addBox(0.0F, 0.0F, 0.0F, 4, 26, 1);
    this.body4.setRotationPoint(-7.0F, -28.0F, -2.0F);
    this.body4.setTextureSize(64, 32);
    this.body4.mirror = true;
    setRotation(this.body4, 0.0F, 0.0F, 0.0F);
    this.body5 = new ModelRenderer(this, 59, 0);
    this.body5.addBox(0.0F, 0.0F, 0.0F, 4, 26, 1);
    this.body5.setRotationPoint(7.0F, -28.0F, -2.0F);
    this.body5.setTextureSize(64, 32);
    this.body5.mirror = true;
    setRotation(this.body5, 0.0F, 0.0F, 0.0F);
    this.eye1 = new ModelRenderer(this, 70, 0);
    this.eye1.addBox(0.0F, 0.0F, 0.0F, 5, 5, 1);
    this.eye1.setRotationPoint(-0.5F, -21.0F, -1.0F);
    this.eye1.setTextureSize(64, 32);
    this.eye1.mirror = true;
    setRotation(this.eye1, 0.0F, 0.0F, 0.0F);
    this.eye2 = new ModelRenderer(this, 70, 6);
    this.eye2.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 1);
    this.eye2.setRotationPoint(2.0F, -18.5F, -1.5F);
    this.eye2.setTextureSize(64, 32);
    this.eye2.mirror = true;
    setRotation(this.eye2, 0.0F, 0.0F, 0.0F);
    this.tentacle1 = new ModelRenderer(this, 0, 27);
    this.tentacle1.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3);
    this.tentacle1.setRotationPoint(-2.5F, -6.5F, 2.0F);
    this.tentacle1.setTextureSize(64, 32);
    this.tentacle1.mirror = true;
    setRotation(this.tentacle1, -0.0872665F, 0.0F, 0.0872665F);
    this.tentacle12 = new ModelRenderer(this, 0, 27);
    this.tentacle12.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3);
    this.tentacle12.setRotationPoint(-3.3F, 2.3F, 1.2F);
    this.tentacle12.setTextureSize(64, 32);
    this.tentacle12.mirror = true;
    setRotation(this.tentacle12, -0.0872665F, 0.0F, 0.1745329F);
    this.tentacle13 = new ModelRenderer(this, 0, 27);
    this.tentacle13.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3);
    this.tentacle13.setRotationPoint(-4.8F, 10.8F, 0.4F);
    this.tentacle13.setTextureSize(64, 32);
    this.tentacle13.mirror = true;
    setRotation(this.tentacle13, -0.0872665F, 0.0F, 0.0F);
    this.foot1 = new ModelRenderer(this, 0, 39);
    this.foot1.addBox(-1.5F, 0.0F, -5.0F, 3, 3, 6);
    this.foot1.setRotationPoint(-4.8F, 19.1F, -0.2F);
    this.foot1.setTextureSize(64, 32);
    this.foot1.mirror = true;
    setRotation(this.foot1, 0.2617994F, 0.0F, 0.0F);
    this.tentacle2 = new ModelRenderer(this, 0, 27);
    this.tentacle2.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3);
    this.tentacle2.setRotationPoint(0.5F, -6.5F, 2.0F);
    this.tentacle2.setTextureSize(64, 32);
    this.tentacle2.mirror = true;
    setRotation(this.tentacle2, -0.0872665F, 0.0F, 0.0349066F);
    this.tentacle22 = new ModelRenderer(this, 0, 27);
    this.tentacle22.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3);
    this.tentacle22.setRotationPoint(0.2F, 2.3F, 1.2F);
    this.tentacle22.setTextureSize(64, 32);
    this.tentacle22.mirror = true;
    setRotation(this.tentacle22, -0.1745329F, 0.0F, 0.0523599F);
    this.tentacle23 = new ModelRenderer(this, 0, 27);
    this.tentacle23.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3);
    this.tentacle23.setRotationPoint(-0.2F, 10.7F, -0.4F);
    this.tentacle23.setTextureSize(64, 32);
    this.tentacle23.mirror = true;
    setRotation(this.tentacle23, 0.0872665F, 0.0F, 0.0872665F);
    this.foot2 = new ModelRenderer(this, 0, 39);
    this.foot2.addBox(-1.5F, 0.0F, -5.0F, 3, 3, 6);
    this.foot2.setRotationPoint(-1.0F, 19.0F, 0.7F);
    this.foot2.setTextureSize(64, 32);
    this.foot2.mirror = true;
    setRotation(this.foot2, 0.2617994F, 0.0F, 0.0F);
    this.tentacle3 = new ModelRenderer(this, 0, 27);
    this.tentacle3.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3);
    this.tentacle3.setRotationPoint(3.5F, -6.5F, 2.0F);
    this.tentacle3.setTextureSize(64, 32);
    this.tentacle3.mirror = true;
    setRotation(this.tentacle3, -0.0872665F, 0.0F, -0.0349066F);
    this.tentacle32 = new ModelRenderer(this, 0, 27);
    this.tentacle32.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3);
    this.tentacle32.setRotationPoint(3.8F, 2.3F, 1.2F);
    this.tentacle32.setTextureSize(64, 32);
    this.tentacle32.mirror = true;
    setRotation(this.tentacle32, -0.0698132F, 0.0F, -0.0698132F);
    this.tentacle33 = new ModelRenderer(this, 0, 27);
    this.tentacle33.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3);
    this.tentacle33.setRotationPoint(4.4F, 11.0F, 0.6F);
    this.tentacle33.setTextureSize(64, 32);
    this.tentacle33.mirror = true;
    setRotation(this.tentacle33, -0.1745329F, 0.0F, 0.0F);
    this.foot3 = new ModelRenderer(this, 0, 39);
    this.foot3.addBox(-1.5F, 0.0F, -5.0F, 3, 3, 6);
    this.foot3.setRotationPoint(4.4F, 19.1F, -0.7F);
    this.foot3.setTextureSize(64, 32);
    this.foot3.mirror = true;
    setRotation(this.foot3, 0.2617994F, 0.0F, 0.0F);
    this.tentacle4 = new ModelRenderer(this, 0, 27);
    this.tentacle4.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3);
    this.tentacle4.setRotationPoint(6.5F, -6.5F, 2.0F);
    this.tentacle4.setTextureSize(64, 32);
    this.tentacle4.mirror = true;
    setRotation(this.tentacle4, -0.0872665F, 0.0F, -0.0872665F);
    this.tentacle42 = new ModelRenderer(this, 0, 27);
    this.tentacle42.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3);
    this.tentacle42.setRotationPoint(7.2F, 2.0F, 1.2F);
    this.tentacle42.setTextureSize(64, 32);
    this.tentacle42.mirror = true;
    setRotation(this.tentacle42, 0.0872665F, 0.0F, -0.1745329F);
    this.tentacle43 = new ModelRenderer(this, 0, 27);
    this.tentacle43.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3);
    this.tentacle43.setRotationPoint(8.7F, 10.6F, 2.0F);
    this.tentacle43.setTextureSize(64, 32);
    this.tentacle43.mirror = true;
    setRotation(this.tentacle43, 0.1745329F, 0.0F, -0.0872665F);
    this.foot4 = new ModelRenderer(this, 0, 39);
    this.foot4.addBox(-1.5F, 0.0F, -5.0F, 3, 3, 6);
    this.foot4.setRotationPoint(9.4F, 19.0F, 4.0F);
    this.foot4.setTextureSize(64, 32);
    this.foot4.mirror = true;
    setRotation(this.foot4, 0.2617994F, 0.0F, 0.0F);
    this.tentacle5 = new ModelRenderer(this, 0, 27);
    this.tentacle5.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3);
    this.tentacle5.setRotationPoint(-1.0F, -6.5F, 3.0F);
    this.tentacle5.setTextureSize(64, 32);
    this.tentacle5.mirror = true;
    setRotation(this.tentacle5, 0.0872665F, 0.0F, 0.0872665F);
    this.tentacle52 = new ModelRenderer(this, 0, 27);
    this.tentacle52.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3);
    this.tentacle52.setRotationPoint(-1.7F, 2.0F, 3.8F);
    this.tentacle52.setTextureSize(64, 32);
    this.tentacle52.mirror = true;
    setRotation(this.tentacle52, 0.0F, 0.0F, 0.2617994F);
    this.tentacle53 = new ModelRenderer(this, 0, 27);
    this.tentacle53.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3);
    this.tentacle53.setRotationPoint(-2.3F, 4.7F, 3.8F);
    this.tentacle53.setTextureSize(64, 32);
    this.tentacle53.mirror = true;
    setRotation(this.tentacle53, 0.0F, 0.0F, 0.4363323F);
    this.tentacle54 = new ModelRenderer(this, 12, 27);
    this.tentacle54.addBox(-5.0F, -1.5F, -1.5F, 5, 3, 3);
    this.tentacle54.setRotationPoint(-2.2F, 6.6F, 3.8F);
    this.tentacle54.setTextureSize(64, 32);
    this.tentacle54.mirror = true;
    setRotation(this.tentacle54, 0.0F, 0.0F, 0.0F);
    this.tentacle55 = new ModelRenderer(this, 0, 30);
    this.tentacle55.addBox(-3.0F, -1.5F, -1.5F, 3, 3, 3);
    this.tentacle55.setRotationPoint(-6.4F, 6.7F, 3.9F);
    this.tentacle55.setTextureSize(64, 32);
    this.tentacle55.mirror = true;
    setRotation(this.tentacle55, 0.0F, -0.2617994F, 0.2617994F);
    this.arm = new ModelRenderer(this, 12, 33);
    this.arm.addBox(-1.5F, -9.0F, -1.5F, 3, 9, 3);
    this.arm.setRotationPoint(-8.2F, 6.7F, 3.4F);
    this.arm.setTextureSize(64, 32);
    this.arm.mirror = true;
    setRotation(this.arm, 0.0F, -0.2617994F, -0.5235988F);
    this.Staff1 = new ModelRenderer(this, 54, 27);
    this.Staff1.addBox(-4.8F, -20.7F, -1.4F, 1, 18, 1);
    this.Staff1.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.Staff1.setTextureSize(64, 32);
    this.Staff1.mirror = true;
    setRotation(this.Staff1, 0.0F, -this.arm.rotateAngleY, -this.arm.rotateAngleZ);
    if (renderStaff)
      this.arm.addChild(this.Staff1); 
    this.Staff2 = new ModelRenderer(this, 62, 27);
    this.Staff2.addBox(6.0F, -23.8F, -1.4F, 1, 4, 1);
    this.Staff2.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.Staff2.setTextureSize(64, 32);
    this.Staff2.mirror = true;
    setRotation(this.Staff2, 0.0F, 0.0F, -0.5235988F);
    this.Staff1.addChild(this.Staff2);
    this.Staff3 = new ModelRenderer(this, 66, 27);
    this.Staff3.addBox(-6.7F, -25.6F, -1.4F, 1, 2, 1);
    this.Staff3.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.Staff3.setTextureSize(64, 32);
    this.Staff3.mirror = true;
    setRotation(this.Staff3, 0.0F, 0.0F, 0.0F);
    this.Staff1.addChild(this.Staff3);
    this.Staff4 = new ModelRenderer(this, 66, 30);
    this.Staff4.addBox(-21.6F, -19.3F, -1.4F, 1, 4, 1);
    this.Staff4.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.Staff4.setTextureSize(64, 32);
    this.Staff4.mirror = true;
    setRotation(this.Staff4, 0.0F, 0.0F, 0.6981317F);
    this.Staff1.addChild(this.Staff4);
    this.Staff5 = new ModelRenderer(this, 62, 32);
    this.Staff5.addBox(18.3F, -21.5F, -1.4F, 1, 4, 1);
    this.Staff5.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.Staff5.setTextureSize(64, 32);
    this.Staff5.mirror = true;
    setRotation(this.Staff5, 0.0F, 0.0F, -0.8726646F);
    this.Staff1.addChild(this.Staff5);
    this.Staff6 = new ModelRenderer(this, 66, 35);
    this.Staff6.addBox(-3.9F, 1.0F, -4.1F, 1, 1, 1);
    this.Staff6.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.Staff6.setTextureSize(64, 32);
    this.Staff6.mirror = true;
    setRotation(this.Staff6, -0.8922867F, 0.5948578F, 0.2230717F);
    this.Staff1.addChild(this.Staff6);
    this.Cube = new ModelRenderer(this, 62, 42);
    this.Cube.addBox(-14.0F, -20.0F, -8.5F, 2, 2, 2);
    this.Cube.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.Cube.setTextureSize(64, 32);
    this.Cube.mirror = true;
    setRotation(this.Cube, 0.0F, 0.4833219F, 0.5205006F);
    this.Staff1.addChild(this.Cube);
    this.tentacle6 = new ModelRenderer(this, 0, 27);
    this.tentacle6.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3);
    this.tentacle6.setRotationPoint(2.0F, -6.5F, 3.0F);
    this.tentacle6.setTextureSize(64, 32);
    this.tentacle6.mirror = true;
    setRotation(this.tentacle6, 0.0872665F, 0.0F, 0.0F);
    this.tentacle62 = new ModelRenderer(this, 0, 27);
    this.tentacle62.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3);
    this.tentacle62.setRotationPoint(2.0F, 2.3F, 3.8F);
    this.tentacle62.setTextureSize(64, 32);
    this.tentacle62.mirror = true;
    setRotation(this.tentacle62, 0.1745329F, 0.0F, 0.0872665F);
    this.tentacle63 = new ModelRenderer(this, 0, 27);
    this.tentacle63.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3);
    this.tentacle63.setRotationPoint(1.2F, 10.5F, 5.3F);
    this.tentacle63.setTextureSize(64, 32);
    this.tentacle63.mirror = true;
    setRotation(this.tentacle63, -0.0872665F, 0.0F, -0.0872665F);
    this.foot5 = new ModelRenderer(this, 0, 39);
    this.foot5.addBox(-1.5F, 0.0F, -1.0F, 3, 3, 6);
    this.foot5.setRotationPoint(1.9F, 19.2F, 4.1F);
    this.foot5.setTextureSize(64, 32);
    this.foot5.mirror = true;
    setRotation(this.foot5, -0.2617994F, 0.0F, 0.0F);
    this.tentacle7 = new ModelRenderer(this, 0, 27);
    this.tentacle7.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3);
    this.tentacle7.setRotationPoint(5.0F, -6.5F, 3.0F);
    this.tentacle7.setTextureSize(64, 32);
    this.tentacle7.mirror = true;
    setRotation(this.tentacle7, 0.0872665F, -0.0872665F, -0.0872665F);
    this.tentacle72 = new ModelRenderer(this, 0, 27);
    this.tentacle72.addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3);
    this.tentacle72.setRotationPoint(5.7F, 1.9F, 3.8F);
    this.tentacle72.setTextureSize(64, 32);
    this.tentacle72.mirror = true;
    setRotation(this.tentacle72, 0.0F, -0.1745329F, -0.3490659F);
    this.tentacle73 = new ModelRenderer(this, 0, 27);
    this.tentacle73.addBox(-1.5F, 0.0F, -1.5F, 3, 5, 3);
    this.tentacle73.setRotationPoint(6.7F, 5.1F, 3.8F);
    this.tentacle73.setTextureSize(64, 32);
    this.tentacle73.mirror = true;
    setRotation(this.tentacle73, 0.0F, -0.1745329F, -0.7853982F);
    this.tentacle74 = new ModelRenderer(this, 0, 30);
    this.tentacle74.addBox(0.0F, -1.5F, -1.5F, 3, 3, 3);
    this.tentacle74.setRotationPoint(9.2F, 8.2F, 3.9F);
    this.tentacle74.setTextureSize(64, 32);
    this.tentacle74.mirror = true;
    setRotation(this.tentacle74, 0.0F, -0.1745329F, 0.0F);
    this.tentacle75 = new ModelRenderer(this, 12, 33);
    this.tentacle75.addBox(-1.5F, -6.0F, -1.5F, 3, 6, 3);
    this.tentacle75.setRotationPoint(10.9F, 9.0F, 4.5F);
    this.tentacle75.setTextureSize(64, 32);
    this.tentacle75.mirror = true;
    setRotation(this.tentacle75, 0.0F, -0.1745329F, 0.5235988F);
    this.tentacle76 = new ModelRenderer(this, 12, 33);
    this.tentacle76.addBox(-1.5F, -3.0F, -1.5F, 3, 3, 3);
    this.tentacle76.setRotationPoint(13.7F, 4.3F, 4.5F);
    this.tentacle76.setTextureSize(64, 32);
    this.tentacle76.mirror = true;
    setRotation(this.tentacle76, 0.0F, -0.1745329F, 0.2617994F);
    this.tentacle77 = new ModelRenderer(this, 12, 33);
    this.tentacle77.addBox(-1.5F, -6.0F, -1.5F, 3, 6, 3);
    this.tentacle77.setRotationPoint(14.3F, 1.9F, 4.7F);
    this.tentacle77.setTextureSize(64, 32);
    this.tentacle77.mirror = true;
    setRotation(this.tentacle77, 0.3490659F, -0.1745329F, 0.2617994F);
    this.abyssalnomicon = new ModelRenderer(this, 28, 27);
    this.abyssalnomicon.addBox(-5.0F, -12.0F, -1.5F, 10, 12, 3);
    this.abyssalnomicon.setRotationPoint(17.0F, -5.0F, 3.0F);
    this.abyssalnomicon.setTextureSize(64, 32);
    this.abyssalnomicon.mirror = true;
    setRotation(this.abyssalnomicon, 0.0F, 0.0F, 0.0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.head.render(f5);
    this.body1.render(f5);
    this.body2.render(f5);
    this.body3.render(f5);
    this.body4.render(f5);
    this.body5.render(f5);
    this.eye1.render(f5);
    this.eye2.render(f5);
    this.tentacle1.render(f5);
    this.tentacle12.render(f5);
    this.tentacle13.render(f5);
    this.foot1.render(f5);
    this.tentacle2.render(f5);
    this.tentacle22.render(f5);
    this.tentacle23.render(f5);
    this.foot2.render(f5);
    this.tentacle3.render(f5);
    this.tentacle32.render(f5);
    this.tentacle33.render(f5);
    this.foot3.render(f5);
    this.tentacle4.render(f5);
    this.tentacle42.render(f5);
    this.tentacle43.render(f5);
    this.foot4.render(f5);
    this.tentacle5.render(f5);
    this.tentacle52.render(f5);
    this.tentacle53.render(f5);
    this.tentacle54.render(f5);
    this.tentacle55.render(f5);
    this.arm.render(f5);
    this.tentacle6.render(f5);
    this.tentacle62.render(f5);
    this.tentacle63.render(f5);
    this.foot5.render(f5);
    this.tentacle7.render(f5);
    this.tentacle72.render(f5);
    this.tentacle73.render(f5);
    this.tentacle74.render(f5);
    this.tentacle75.render(f5);
    this.tentacle76.render(f5);
    this.tentacle77.render(f5);
    this.abyssalnomicon.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z) {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
    EntityJzahar jzahar = (EntityJzahar)entity;
    this.head.setRotationPoint(2.0F, -28.0F, 1.0F);
    this.mask1.setRotationPoint(0.0F, 0.0F, 0.0F);
    setRotation(this.mask1, 0.0F, 0.3490659F, 0.0F);
    this.mask2.setRotationPoint(0.0F, 0.0F, 0.0F);
    setRotation(this.mask2, 0.0F, -0.3490659F, 0.0F);
    this.facetentacle1.setRotationPoint(-4.0F, -2.0F, -7.0F);
    setRotation(this.facetentacle1, 0.0F, 0.0F, 0.0F);
    this.facetentacle2.setRotationPoint(-1.0F, -2.0F, -7.0F);
    setRotation(this.facetentacle2, 0.0F, 0.0F, 0.0F);
    this.facetentacle3.setRotationPoint(2.0F, -2.0F, -7.0F);
    setRotation(this.facetentacle3, 0.0F, 0.0F, 0.0F);
    this.body1.setRotationPoint(-7.0F, -28.0F, 0.0F);
    setRotation(this.body1, 0.0F, 0.0F, 0.0F);
    this.body2.setRotationPoint(-7.0F, -28.0F, -1.0F);
    setRotation(this.body2, 0.0F, 0.0F, 0.0F);
    this.body3.setRotationPoint(6.0F, -28.0F, -1.0F);
    setRotation(this.body3, 0.0F, 0.0F, 0.0F);
    this.body4.setRotationPoint(-7.0F, -28.0F, -2.0F);
    setRotation(this.body4, 0.0F, 0.0F, 0.0F);
    this.body5.setRotationPoint(7.0F, -28.0F, -2.0F);
    setRotation(this.body5, 0.0F, 0.0F, 0.0F);
    this.eye1.setRotationPoint(-0.5F, -21.0F, -1.0F);
    setRotation(this.eye1, 0.0F, 0.0F, 0.0F);
    this.eye2.setRotationPoint(2.0F, -18.5F, -1.5F);
    setRotation(this.eye2, 0.0F, 0.0F, 0.0F);
    this.tentacle1.setRotationPoint(-2.5F, -6.5F, 2.0F);
    setRotation(this.tentacle1, -0.0872665F, 0.0F, 0.0872665F);
    this.tentacle12.setRotationPoint(-3.3F, 2.3F, 1.2F);
    setRotation(this.tentacle12, -0.0872665F, 0.0F, 0.1745329F);
    this.tentacle13.setRotationPoint(-4.8F, 10.8F, 0.4F);
    setRotation(this.tentacle13, -0.0872665F, 0.0F, 0.0F);
    this.foot1.setRotationPoint(-4.8F, 19.1F, -0.2F);
    setRotation(this.foot1, 0.2617994F, 0.0F, 0.0F);
    this.tentacle2.setRotationPoint(0.5F, -6.5F, 2.0F);
    setRotation(this.tentacle2, -0.0872665F, 0.0F, 0.0349066F);
    this.tentacle22.setRotationPoint(0.2F, 2.3F, 1.2F);
    setRotation(this.tentacle22, -0.1745329F, 0.0F, 0.0523599F);
    this.tentacle23.setRotationPoint(-0.2F, 10.7F, -0.4F);
    setRotation(this.tentacle23, 0.0872665F, 0.0F, 0.0872665F);
    this.foot2.setRotationPoint(-1.0F, 19.0F, 0.7F);
    setRotation(this.foot2, 0.2617994F, 0.0F, 0.0F);
    this.tentacle3.setRotationPoint(3.5F, -6.5F, 2.0F);
    setRotation(this.tentacle3, -0.0872665F, 0.0F, -0.0349066F);
    this.tentacle32.setRotationPoint(3.8F, 2.3F, 1.2F);
    setRotation(this.tentacle32, -0.0698132F, 0.0F, -0.0698132F);
    this.tentacle33.setRotationPoint(4.4F, 11.0F, 0.6F);
    setRotation(this.tentacle33, -0.1745329F, 0.0F, 0.0F);
    this.foot3.setRotationPoint(4.4F, 19.1F, -0.7F);
    setRotation(this.foot3, 0.2617994F, 0.0F, 0.0F);
    this.tentacle4.setRotationPoint(6.5F, -6.5F, 2.0F);
    setRotation(this.tentacle4, -0.0872665F, 0.0F, -0.0872665F);
    this.tentacle42.setRotationPoint(7.2F, 2.0F, 1.2F);
    setRotation(this.tentacle42, 0.0872665F, 0.0F, -0.1745329F);
    this.tentacle43.setRotationPoint(8.7F, 10.6F, 2.0F);
    setRotation(this.tentacle43, 0.1745329F, 0.0F, -0.0872665F);
    this.foot4.setRotationPoint(9.4F, 19.0F, 4.0F);
    setRotation(this.foot4, 0.2617994F, 0.0F, 0.0F);
    this.tentacle5.setRotationPoint(-1.0F, -6.5F, 3.0F);
    setRotation(this.tentacle5, 0.0872665F, 0.0F, 0.0872665F);
    this.tentacle52.setRotationPoint(-1.7F, 2.0F, 3.8F);
    setRotation(this.tentacle52, 0.0F, 0.0F, 0.2617994F);
    this.tentacle53.setRotationPoint(-2.3F, 4.7F, 3.8F);
    setRotation(this.tentacle53, 0.0F, 0.0F, 0.4363323F);
    this.tentacle54.setRotationPoint(-2.2F, 6.6F, 3.8F);
    setRotation(this.tentacle54, 0.0F, 0.0F, 0.0F);
    this.tentacle55.setRotationPoint(-6.4F, 6.7F, 3.9F);
    setRotation(this.tentacle55, 0.0F, -0.2617994F, 0.2617994F);
    this.arm.setRotationPoint(-8.2F, 6.7F, 3.4F);
    setRotation(this.arm, 0.0F, -0.2617994F, -0.5235988F);
    this.Staff1.setRotationPoint(0.0F, 0.0F, 0.0F);
    setRotation(this.Staff1, 0.0F, -this.arm.rotateAngleY, -this.arm.rotateAngleZ);
    this.Staff2.setRotationPoint(0.0F, 0.0F, 0.0F);
    setRotation(this.Staff2, 0.0F, 0.0F, -0.5235988F);
    this.Staff3.setRotationPoint(0.0F, 0.0F, 0.0F);
    setRotation(this.Staff3, 0.0F, 0.0F, 0.0F);
    this.Staff4.setRotationPoint(0.0F, 0.0F, 0.0F);
    setRotation(this.Staff4, 0.0F, 0.0F, 0.6981317F);
    this.Staff5.setRotationPoint(0.0F, 0.0F, 0.0F);
    setRotation(this.Staff5, 0.0F, 0.0F, -0.8726646F);
    this.Staff6.setRotationPoint(0.0F, 0.0F, 0.0F);
    setRotation(this.Staff6, -0.8922867F, 0.5948578F, 0.2230717F);
    this.Cube.setRotationPoint(0.0F, 0.0F, 0.0F);
    setRotation(this.Cube, 0.0F, 0.4833219F, 0.5205006F);
    this.tentacle6.setRotationPoint(2.0F, -6.5F, 3.0F);
    setRotation(this.tentacle6, 0.0872665F, 0.0F, 0.0F);
    this.tentacle62.setRotationPoint(2.0F, 2.3F, 3.8F);
    setRotation(this.tentacle62, 0.1745329F, 0.0F, 0.0872665F);
    this.tentacle63.setRotationPoint(1.2F, 10.5F, 5.3F);
    setRotation(this.tentacle63, -0.0872665F, 0.0F, -0.0872665F);
    this.foot5.setRotationPoint(1.9F, 19.2F, 4.1F);
    setRotation(this.foot5, -0.2617994F, 0.0F, 0.0F);
    this.tentacle7.setRotationPoint(5.0F, -6.5F, 3.0F);
    setRotation(this.tentacle7, 0.0872665F, -0.0872665F, -0.0872665F);
    this.tentacle72.setRotationPoint(5.7F, 1.9F, 3.8F);
    setRotation(this.tentacle72, 0.0F, -0.1745329F, -0.3490659F);
    this.tentacle73.setRotationPoint(6.7F, 5.1F, 3.8F);
    setRotation(this.tentacle73, 0.0F, -0.1745329F, -0.7853982F);
    this.tentacle74.setRotationPoint(9.2F, 8.2F, 3.9F);
    setRotation(this.tentacle74, 0.0F, -0.1745329F, 0.0F);
    this.tentacle75.setRotationPoint(10.9F, 9.0F, 4.5F);
    setRotation(this.tentacle75, 0.0F, -0.1745329F, 0.5235988F);
    this.tentacle76.setRotationPoint(13.7F, 4.3F, 4.5F);
    setRotation(this.tentacle76, 0.0F, -0.1745329F, 0.2617994F);
    this.tentacle77.setRotationPoint(14.3F, 1.9F, 4.7F);
    setRotation(this.tentacle77, 0.3490659F, -0.1745329F, 0.2617994F);
    this.abyssalnomicon.setRotationPoint(17.0F, -5.0F, 3.0F);
    setRotation(this.abyssalnomicon, 0.0F, 0.0F, 0.0F);
    this.head.rotateAngleY = f3 / 57.295776F;
    this.head.rotateAngleX = f4 / 57.295776F;
    for (int i = 0; i < 4; i++)
      this.abyssalnomicon.rotationPointY = -5.5F + MathHelper.cos(((i * 2) + f2) * 0.25F); 
    if (jzahar.getTimer(0) > 960 || jzahar.getTimer(2) > 1160 || jzahar.getTimer(1) > 1560) {
      setRotation(this.abyssalnomicon, 0.0F, MathHelper.sin(f2 * 20.0F), 0.0F);
    } else {
      setRotation(this.abyssalnomicon, 0.0F, 0.0F, 0.0F);
    } 
    this.foot1.rotateAngleY = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
    this.foot2.rotateAngleY = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1;
    this.foot3.rotateAngleY = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
    this.foot4.rotateAngleY = MathHelper.cos(f * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.foot5.rotateAngleY = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
    setRotation(this.arm, 0.0F, -0.2617994F, -0.5235988F);
    if (jzahar.getJukeboxToDanceTo() != null) {
      setRotation(this.abyssalnomicon, 0.0F, MathHelper.sin(jzahar.ticksExisted), 0.0F);
      this.arm.rotateAngleX = 0.5F + MathHelper.sin(f2 * 0.5F - 1.0F) * 0.5F;
      this.arm.rotateAngleY = MathHelper.cos(f2 * 0.25F) * 0.5F;
      this.head.rotateAngleX += MathHelper.cos(f2 * 0.5F) * 0.25F;
      this.head.rotateAngleY += MathHelper.sin(f2 * 0.25F) * 0.5F;
    } 
    if (this.swingProgress > -9990.0F) {
      float f6 = this.swingProgress;
      this.arm.rotateAngleY += MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      f6 = 1.0F - this.swingProgress;
      f6 *= f6;
      f6 *= f6;
      f6 = 1.0F - f6;
      float f7 = MathHelper.sin(f6 * 3.1415927F);
      float f8 = MathHelper.sin(this.swingProgress * 3.1415927F) * 0.75F;
      this.arm.rotateAngleX = (float)(this.arm.rotateAngleX + f7 * 1.2D + f8);
      this.arm.rotateAngleY += MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F * 2.0F;
    } 
    if (jzahar.deathTicks > 0) {
      this.head.rotateAngleX = 20.0F;
      this.eye1.isHidden = true;
      this.eye2.isHidden = true;
      this.abyssalnomicon.isHidden = true;
      this.facetentacle1.isHidden = true;
      this.facetentacle2.isHidden = true;
      this.facetentacle3.isHidden = true;
      this.tentacle1.isHidden = true;
      this.tentacle12.isHidden = true;
      this.tentacle13.isHidden = true;
      this.tentacle2.isHidden = true;
      this.tentacle22.isHidden = true;
      this.tentacle23.isHidden = true;
      this.tentacle3.isHidden = true;
      this.tentacle32.isHidden = true;
      this.tentacle33.isHidden = true;
      this.tentacle4.isHidden = true;
      this.tentacle42.isHidden = true;
      this.tentacle43.isHidden = true;
      this.tentacle5.isHidden = true;
      this.tentacle52.isHidden = true;
      this.tentacle53.isHidden = true;
      this.tentacle54.isHidden = true;
      this.tentacle55.isHidden = true;
      this.arm.isHidden = true;
      this.tentacle6.isHidden = true;
      this.tentacle62.isHidden = true;
      this.tentacle63.isHidden = true;
      this.tentacle7.isHidden = true;
      this.tentacle72.isHidden = true;
      this.tentacle73.isHidden = true;
      this.tentacle74.isHidden = true;
      this.tentacle75.isHidden = true;
      this.tentacle76.isHidden = true;
      this.tentacle77.isHidden = true;
      this.foot1.isHidden = true;
      this.foot2.isHidden = true;
      this.foot3.isHidden = true;
      this.foot4.isHidden = true;
      this.foot5.isHidden = true;
      for (int j = 0; j < 4; j++) {
        this.head.rotationPointY = -9.5F + MathHelper.cos(((j * 2) + f2) * 0.25F);
        this.body1.rotationPointY = -9.5F + MathHelper.cos(((j * 2) + f2) * 0.25F);
        this.body2.rotationPointY = -9.5F + MathHelper.cos(((j * 2) + f2) * 0.25F);
        this.body3.rotationPointY = -9.5F + MathHelper.cos(((j * 2) + f2) * 0.25F);
        this.body4.rotationPointY = -9.5F + MathHelper.cos(((j * 2) + f2) * 0.25F);
        this.body5.rotationPointY = -9.5F + MathHelper.cos(((j * 2) + f2) * 0.25F);
      } 
    } 
    if (jzahar.deathTicks == 0)
      if (jzahar.isSneaking() || jzahar.getJukeboxToDanceTo() != null || !jzahar.onGround || jzahar.posY <= 0.0D) {
        this.tentacle12.rotateAngleX = -1.2566371F;
        this.tentacle22.rotateAngleX = -1.2566371F;
        this.tentacle32.rotateAngleX = -1.2566371F;
        this.tentacle42.rotateAngleX = -1.2566371F;
        this.tentacle62.rotateAngleX = -1.2566371F;
        this.tentacle13.rotateAngleX = -1.2566371F;
        this.tentacle23.rotateAngleX = -1.2566371F;
        this.tentacle33.rotateAngleX = -1.2566371F;
        this.tentacle43.rotateAngleX = -1.2566371F;
        this.tentacle63.rotateAngleX = -1.2566371F;
        this.tentacle12.rotateAngleY = 0.31415927F;
        this.tentacle22.rotateAngleY = 0.31415927F;
        this.tentacle32.rotateAngleY = -0.31415927F;
        this.tentacle42.rotateAngleY = -0.31415927F;
        this.tentacle13.rotateAngleY = 0.31415927F;
        this.tentacle23.rotateAngleY = 0.31415927F;
        this.tentacle33.rotateAngleY = -0.31415927F;
        this.tentacle43.rotateAngleY = -0.31415927F;
        this.foot1.rotateAngleX = -1.2566371F;
        this.foot2.rotateAngleX = -1.2566371F;
        this.foot3.rotateAngleX = -1.2566371F;
        this.foot4.rotateAngleX = -1.2566371F;
        this.foot5.rotateAngleX = -1.2566371F;
        this.foot1.rotateAngleY = 0.31415927F;
        this.foot2.rotateAngleY = 0.31415927F;
        this.foot3.rotateAngleY = -0.31415927F;
        this.foot4.rotateAngleY = -0.31415927F;
        this.foot5.rotateAngleY = 0.31415927F;
        this.tentacle12.rotationPointZ = 2.0F;
        this.tentacle22.rotationPointZ = 2.0F;
        this.tentacle32.rotationPointZ = 2.0F;
        this.tentacle42.rotationPointZ = 2.0F;
        this.tentacle62.rotationPointZ = 4.5F;
        this.tentacle12.rotationPointX = -3.5F;
        this.tentacle22.rotationPointX = 0.5F;
        this.tentacle32.rotationPointX = 3.3F;
        this.tentacle42.rotationPointX = 6.6F;
        this.tentacle13.rotationPointY = 4.6F;
        this.tentacle23.rotationPointY = 4.8F;
        this.tentacle33.rotationPointY = 5.0F;
        this.tentacle43.rotationPointY = 4.3F;
        this.tentacle63.rotationPointY = 5.3F;
        this.tentacle13.rotationPointZ = -5.5F;
        this.tentacle23.rotationPointZ = -5.5F;
        this.tentacle33.rotationPointZ = -6.0F;
        this.tentacle43.rotationPointZ = -6.0F;
        this.tentacle63.rotationPointZ = -4.0F;
        this.tentacle13.rotationPointX = -6.2F;
        this.tentacle23.rotationPointX = -2.2F;
        this.tentacle33.rotationPointX = 6.0F;
        this.tentacle43.rotationPointX = 9.6F;
        this.tentacle63.rotationPointX = 2.0F;
        this.foot1.rotationPointX = -8.2F;
        this.foot2.rotationPointX = -4.5F;
        this.foot3.rotationPointX = 7.7F;
        this.foot4.rotationPointX = 11.6F;
        this.foot1.rotationPointY = 7.0F;
        this.foot2.rotationPointY = 7.0F;
        this.foot3.rotationPointY = 7.5F;
        this.foot4.rotationPointY = 6.5F;
        this.foot5.rotationPointY = 3.7F;
        this.foot1.rotationPointZ = -12.0F;
        this.foot2.rotationPointZ = -12.0F;
        this.foot3.rotationPointZ = -12.0F;
        this.foot4.rotationPointZ = -12.0F;
        this.foot5.rotationPointZ = -12.0F;
      } else {
        this.head.rotateAngleX = f4 / 57.295776F;
        this.eye1.isHidden = false;
        this.eye2.isHidden = false;
        this.abyssalnomicon.isHidden = false;
        this.facetentacle1.isHidden = false;
        this.facetentacle2.isHidden = false;
        this.facetentacle3.isHidden = false;
        this.tentacle1.isHidden = false;
        this.tentacle12.isHidden = false;
        this.tentacle13.isHidden = false;
        this.tentacle2.isHidden = false;
        this.tentacle22.isHidden = false;
        this.tentacle23.isHidden = false;
        this.tentacle3.isHidden = false;
        this.tentacle32.isHidden = false;
        this.tentacle33.isHidden = false;
        this.tentacle4.isHidden = false;
        this.tentacle42.isHidden = false;
        this.tentacle43.isHidden = false;
        this.tentacle5.isHidden = false;
        this.tentacle52.isHidden = false;
        this.tentacle53.isHidden = false;
        this.tentacle54.isHidden = false;
        this.tentacle55.isHidden = false;
        this.arm.isHidden = false;
        this.tentacle6.isHidden = false;
        this.tentacle62.isHidden = false;
        this.tentacle63.isHidden = false;
        this.tentacle7.isHidden = false;
        this.tentacle72.isHidden = false;
        this.tentacle73.isHidden = false;
        this.tentacle74.isHidden = false;
        this.tentacle75.isHidden = false;
        this.tentacle76.isHidden = false;
        this.tentacle77.isHidden = false;
        this.foot1.isHidden = false;
        this.foot2.isHidden = false;
        this.foot3.isHidden = false;
        this.foot4.isHidden = false;
        this.foot5.isHidden = false;
        this.head.rotationPointY = -28.0F;
        this.body1.rotationPointY = -28.0F;
        this.body2.rotationPointY = -28.0F;
        this.body3.rotationPointY = -28.0F;
        this.body4.rotationPointY = -28.0F;
        this.body5.rotationPointY = -28.0F;
      }  
  }
}
