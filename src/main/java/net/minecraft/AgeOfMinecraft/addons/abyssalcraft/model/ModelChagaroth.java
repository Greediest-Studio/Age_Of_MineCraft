package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.model;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityChagaroth;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelChagaroth extends ModelBase {
  private int rot = 0;
  
  public ModelRenderer frontspike1;
  
  public ModelRenderer frontspike2;
  
  public ModelRenderer frontspike3;
  
  public ModelRenderer frontspike4;
  
  public ModelRenderer frontspike5;
  
  public ModelRenderer leftneck;
  
  public ModelRenderer middleneck;
  
  public ModelRenderer rightneck;
  
  public ModelRenderer body;
  
  public ModelRenderer tentacle2base;
  
  public ModelRenderer tentacle1;
  
  public ModelRenderer tentacle2;
  
  public ModelRenderer tentacle3;
  
  public ModelRenderer tentacle4;
  
  public ModelRenderer tentacle5;
  
  public ModelRenderer tentacle6;
  
  public ModelRenderer tentacle7;
  
  public ModelRenderer tentacle8;
  
  public ModelRenderer tentacle9;
  
  public ModelRenderer tentacle10;
  
  public ModelRenderer tentacle11;
  
  public ModelRenderer tentacle12;
  
  public ModelRenderer tentacle13;
  
  public ModelRenderer tentacle14;
  
  public ModelRenderer tentacle15;
  
  public ModelRenderer tentacle16;
  
  public ModelRenderer tentacle17;
  
  public ModelRenderer tentacle18;
  
  public ModelRenderer tentacle19;
  
  public ModelRenderer tentacle20;
  
  public ModelRenderer tlib12;
  
  public ModelRenderer tlib22;
  
  public ModelRenderer tspike12;
  
  public ModelRenderer tspike22;
  
  public ModelRenderer tspike32;
  
  public ModelRenderer tspike42;
  
  public ModelRenderer tlib1;
  
  public ModelRenderer tlib2;
  
  public ModelRenderer tspike1;
  
  public ModelRenderer tspike2;
  
  public ModelRenderer tspike3;
  
  public ModelRenderer tspike4;
  
  public ModelRenderer lefthead;
  
  public ModelRenderer leftjaw;
  
  public ModelRenderer leftupperjaw;
  
  public ModelRenderer lstooth1;
  
  public ModelRenderer lstooth2;
  
  public ModelRenderer lstooth3;
  
  public ModelRenderer lstooth4;
  
  public ModelRenderer lbtooth1;
  
  public ModelRenderer lbtooth2;
  
  public ModelRenderer lbtooth3;
  
  public ModelRenderer lbtooth4;
  
  public ModelRenderer lbtooth5;
  
  public ModelRenderer lbackspike1;
  
  public ModelRenderer lbackspike2;
  
  public ModelRenderer lbackspike3;
  
  public ModelRenderer lackspike4;
  
  public ModelRenderer lefteye1;
  
  public ModelRenderer lefteye2;
  
  public ModelRenderer middlehead;
  
  public ModelRenderer middlejaw;
  
  public ModelRenderer middleupperjaw;
  
  public ModelRenderer mstooth1;
  
  public ModelRenderer mstooth2;
  
  public ModelRenderer mstooth3;
  
  public ModelRenderer mstooth4;
  
  public ModelRenderer mbtooth1;
  
  public ModelRenderer mbtooth2;
  
  public ModelRenderer mbtooth3;
  
  public ModelRenderer mbtooth4;
  
  public ModelRenderer mbtooth5;
  
  public ModelRenderer mbackspike1;
  
  public ModelRenderer mbackspike2;
  
  public ModelRenderer mbackspike3;
  
  public ModelRenderer mbackspike4;
  
  public ModelRenderer middleeye1;
  
  public ModelRenderer middleeye2;
  
  public ModelRenderer righthead;
  
  public ModelRenderer rightjaw;
  
  public ModelRenderer rightupperjaw;
  
  public ModelRenderer rstooth1;
  
  public ModelRenderer rstooth2;
  
  public ModelRenderer rstooth3;
  
  public ModelRenderer rstooth4;
  
  public ModelRenderer rbtooth1;
  
  public ModelRenderer rbtooth2;
  
  public ModelRenderer rbtooth3;
  
  public ModelRenderer rbtooth4;
  
  public ModelRenderer rbtooth5;
  
  public ModelRenderer rbackspike1;
  
  public ModelRenderer rbackspike2;
  
  public ModelRenderer rbackspike3;
  
  public ModelRenderer rbackspike4;
  
  public ModelRenderer righteye1;
  
  public ModelRenderer righteye2;
  
  public ModelRenderer middlewall;
  
  public ModelRenderer rightwall;
  
  public ModelRenderer leftwall;
  
  public ModelRenderer walltentacle1;
  
  public ModelRenderer walltentacle2;
  
  public ModelRenderer walltentacle3;
  
  public ModelRenderer walltentacle4;
  
  public ModelRenderer walltentacle5;
  
  public ModelRenderer walltentacle6;
  
  public ModelRenderer walltentacle7;
  
  public ModelRenderer walltentacle8;
  
  public ModelChagaroth() {
    this.textureWidth = 128;
    this.textureHeight = 64;
    this.frontspike1 = new ModelRenderer(this, 30, 50);
    this.frontspike1.addBox(0.0F, -4.0F, 0.0F, 1, 5, 1);
    this.frontspike1.setRotationPoint(-10.0F, 10.0F, -9.0F);
    this.frontspike1.setTextureSize(128, 64);
    this.frontspike1.mirror = true;
    setRotation(this.frontspike1, 0.6320364F, 0.0F, 0.0F);
    this.frontspike2 = new ModelRenderer(this, 30, 50);
    this.frontspike2.addBox(0.0F, -4.0F, 0.0F, 1, 5, 1);
    this.frontspike2.setRotationPoint(-4.0F, 10.0F, -9.0F);
    this.frontspike2.setTextureSize(128, 64);
    this.frontspike2.mirror = true;
    setRotation(this.frontspike2, 0.6320364F, 0.0F, 0.0F);
    this.frontspike3 = new ModelRenderer(this, 30, 50);
    this.frontspike3.addBox(0.0F, -4.0F, 0.0F, 1, 5, 1);
    this.frontspike3.setRotationPoint(2.0F, 10.0F, -9.0F);
    this.frontspike3.setTextureSize(128, 64);
    this.frontspike3.mirror = true;
    setRotation(this.frontspike3, 0.6320364F, 0.0F, 0.0F);
    this.frontspike4 = new ModelRenderer(this, 30, 50);
    this.frontspike4.addBox(0.0F, -4.0F, 0.0F, 1, 5, 1);
    this.frontspike4.setRotationPoint(8.0F, 10.0F, -9.0F);
    this.frontspike4.setTextureSize(128, 64);
    this.frontspike4.mirror = true;
    setRotation(this.frontspike4, 0.6320364F, 0.0F, 0.0F);
    this.frontspike5 = new ModelRenderer(this, 30, 50);
    this.frontspike5.addBox(0.0F, -4.0F, 0.0F, 1, 5, 1);
    this.frontspike5.setRotationPoint(14.0F, 10.0F, -9.0F);
    this.frontspike5.setTextureSize(128, 64);
    this.frontspike5.mirror = true;
    setRotation(this.frontspike5, 0.6320364F, 0.0F, 0.0F);
    this.leftneck = new ModelRenderer(this, 34, 0);
    this.leftneck.addBox(-1.0F, -16.0F, -2.0F, 3, 16, 3);
    this.leftneck.setRotationPoint(-8.0F, 10.0F, -1.0F);
    this.leftneck.setTextureSize(128, 64);
    this.leftneck.mirror = true;
    setRotation(this.leftneck, 0.4833219F, 0.2230717F, 0.0F);
    this.middleneck = new ModelRenderer(this, 34, 0);
    this.middleneck.addBox(-1.0F, -16.0F, -2.0F, 3, 16, 3);
    this.middleneck.setRotationPoint(2.0F, 10.0F, -1.0F);
    this.middleneck.setTextureSize(128, 64);
    this.middleneck.mirror = true;
    setRotation(this.middleneck, 0.4833219F, 0.0F, 0.0F);
    this.rightneck = new ModelRenderer(this, 34, 0);
    this.rightneck.addBox(-1.0F, -16.0F, -2.0F, 3, 16, 3);
    this.rightneck.setRotationPoint(12.0F, 10.0F, -1.0F);
    this.rightneck.setTextureSize(128, 64);
    this.rightneck.mirror = true;
    setRotation(this.rightneck, 0.4833219F, -0.2230705F, 0.0F);
    this.body = new ModelRenderer(this, 38, 30);
    this.body.addBox(0.0F, 0.0F, 0.0F, 25, 14, 20);
    this.body.setRotationPoint(-10.0F, 10.0F, -9.0F);
    this.body.setTextureSize(128, 64);
    this.body.mirror = true;
    setRotation(this.body, 0.0F, 0.0F, 0.0F);
    this.tentacle2base = new ModelRenderer(this, 0, 0);
    this.tentacle2base.addBox(0.0F, 0.0F, 0.0F, 9, 3, 3);
    this.tentacle2base.setRotationPoint(15.0F, 12.0F, -8.0F);
    this.tentacle2base.setTextureSize(128, 64);
    this.tentacle2base.mirror = true;
    setRotation(this.tentacle2base, 0.0F, 0.0F, 0.0F);
    this.tentacle1 = new ModelRenderer(this, 0, 0);
    this.tentacle1.addBox(-1.5F, -10.0F, -1.5F, 3, 10, 3);
    this.tentacle1.setRotationPoint(-2.0F, 10.0F, 9.0F);
    this.tentacle1.setTextureSize(128, 64);
    this.tentacle1.mirror = true;
    setRotation(this.tentacle1, 0.0F, 0.0F, 0.0F);
    this.tentacle2 = new ModelRenderer(this, 0, 0);
    this.tentacle2.addBox(-1.5F, -9.0F, -1.5F, 3, 9, 3);
    this.tentacle2.setRotationPoint(-2.0F, 1.0F, 9.0F);
    this.tentacle2.setTextureSize(128, 64);
    this.tentacle2.mirror = true;
    setRotation(this.tentacle2, 0.4833219F, 0.0F, 0.0F);
    this.tentacle3 = new ModelRenderer(this, 0, 0);
    this.tentacle3.addBox(-1.5F, -8.0F, -1.5F, 3, 8, 3);
    this.tentacle3.setRotationPoint(-2.0F, -6.0F, 5.0F);
    this.tentacle3.setTextureSize(128, 64);
    this.tentacle3.mirror = true;
    setRotation(this.tentacle3, -0.4461433F, 0.0F, 0.0F);
    this.tentacle4 = new ModelRenderer(this, 0, 0);
    this.tentacle4.addBox(0.0F, -1.5F, -1.5F, 9, 3, 3);
    this.tentacle4.setRotationPoint(-1.0F, 1.0F, 9.0F);
    this.tentacle4.setTextureSize(128, 64);
    this.tentacle4.mirror = true;
    setRotation(this.tentacle4, 0.0F, 0.0F, -0.5205006F);
    this.tentacle5 = new ModelRenderer(this, 0, 0);
    this.tentacle5.addBox(-1.5F, -1.5F, 0.0F, 3, 3, 10);
    this.tentacle5.setRotationPoint(-2.0F, -12.0F, 7.0F);
    this.tentacle5.setTextureSize(128, 64);
    this.tentacle5.mirror = true;
    setRotation(this.tentacle5, 0.0F, 0.0F, 0.0F);
    this.tentacle6 = new ModelRenderer(this, 0, 0);
    this.tentacle6.addBox(-1.5F, -8.5F, -1.5F, 3, 9, 3);
    this.tentacle6.setRotationPoint(-17.5F, 16.0F, -4.0F);
    this.tentacle6.setTextureSize(128, 64);
    this.tentacle6.mirror = true;
    setRotation(this.tentacle6, 0.0F, 0.0F, -0.3346075F);
    this.tentacle7 = new ModelRenderer(this, 0, 0);
    this.tentacle7.addBox(0.0F, -1.5F, -1.5F, 10, 3, 3);
    this.tentacle7.setRotationPoint(27.0F, 5.0F, 3.0F);
    this.tentacle7.setTextureSize(128, 64);
    this.tentacle7.mirror = true;
    setRotation(this.tentacle7, 0.0F, -0.5576792F, 0.0F);
    this.tentacle8 = new ModelRenderer(this, 0, 0);
    this.tentacle8.addBox(0.0F, -2.5F, -2.5F, 13, 5, 5);
    this.tentacle8.setRotationPoint(17.0F, 6.0F, 4.0F);
    this.tentacle8.setTextureSize(128, 64);
    this.tentacle8.mirror = true;
    setRotation(this.tentacle8, 0.0F, 0.2602503F, -0.1487144F);
    this.tentacle9 = new ModelRenderer(this, 0, 0);
    this.tentacle9.addBox(0.0F, 0.0F, 0.0F, 3, 12, 3);
    this.tentacle9.setRotationPoint(8.0F, 23.0F, -4.0F);
    this.tentacle9.setTextureSize(128, 64);
    this.tentacle9.mirror = true;
    setRotation(this.tentacle9, -0.4833219F, 0.0F, -0.5948578F);
    this.tentacle10 = new ModelRenderer(this, 0, 0);
    this.tentacle10.addBox(-9.0F, -1.5F, -1.5F, 9, 3, 3);
    this.tentacle10.setRotationPoint(-10.0F, 17.0F, -4.0F);
    this.tentacle10.setTextureSize(128, 64);
    this.tentacle10.mirror = true;
    setRotation(this.tentacle10, 0.0F, 0.0F, 0.0F);
    this.tentacle11 = new ModelRenderer(this, 0, 0);
    this.tentacle11.addBox(-1.5F, -1.5F, -11.0F, 3, 3, 11);
    this.tentacle11.setRotationPoint(-6.0F, 19.0F, -7.0F);
    this.tentacle11.setTextureSize(128, 64);
    this.tentacle11.mirror = true;
    setRotation(this.tentacle11, 0.1487144F, -0.4089647F, 0.0F);
    this.tentacle12 = new ModelRenderer(this, 0, 0);
    this.tentacle12.addBox(-2.5F, -10.0F, -2.5F, 5, 10, 5);
    this.tentacle12.setRotationPoint(16.0F, 8.0F, 4.0F);
    this.tentacle12.setTextureSize(128, 64);
    this.tentacle12.mirror = true;
    setRotation(this.tentacle12, 0.0F, 0.0F, 0.0F);
    this.tentacle13 = new ModelRenderer(this, 0, 0);
    this.tentacle13.addBox(-2.5F, -13.0F, -2.5F, 5, 8, 5);
    this.tentacle13.setRotationPoint(10.0F, 18.0F, 5.0F);
    this.tentacle13.setTextureSize(128, 64);
    this.tentacle13.mirror = true;
    setRotation(this.tentacle13, 0.0371786F, 0.0F, 0.5205006F);
    this.tentacle14 = new ModelRenderer(this, 0, 0);
    this.tentacle14.addBox(-1.5F, -11.0F, -1.5F, 3, 11, 3);
    this.tentacle14.setRotationPoint(17.0F, 0.0F, 5.0F);
    this.tentacle14.setTextureSize(128, 64);
    this.tentacle14.mirror = true;
    setRotation(this.tentacle14, -0.2602503F, 0.0F, 1.07818F);
    this.tentacle15 = new ModelRenderer(this, 0, 0);
    this.tentacle15.addBox(-1.5F, -1.5F, -8.0F, 3, 3, 8);
    this.tentacle15.setRotationPoint(25.0F, -5.0F, 7.0F);
    this.tentacle15.setTextureSize(128, 64);
    this.tentacle15.mirror = true;
    setRotation(this.tentacle15, 0.0F, -0.2974289F, 0.0F);
    this.tentacle16 = new ModelRenderer(this, 0, 0);
    this.tentacle16.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3);
    this.tentacle16.setRotationPoint(0.0F, 22.0F, -6.0F);
    this.tentacle16.setTextureSize(128, 64);
    this.tentacle16.mirror = true;
    setRotation(this.tentacle16, -0.1115358F, 0.0F, 0.5205006F);
    this.tentacle17 = new ModelRenderer(this, 0, 0);
    this.tentacle17.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3);
    this.tentacle17.setRotationPoint(5.0F, 24.0F, 4.0F);
    this.tentacle17.setTextureSize(128, 64);
    this.tentacle17.mirror = true;
    setRotation(this.tentacle17, 0.4461433F, 0.0F, 0.0F);
    this.tentacle18 = new ModelRenderer(this, 0, 0);
    this.tentacle18.addBox(0.0F, 0.0F, 0.0F, 5, 15, 5);
    this.tentacle18.setRotationPoint(-8.0F, 21.0F, 1.0F);
    this.tentacle18.setTextureSize(128, 64);
    this.tentacle18.mirror = true;
    setRotation(this.tentacle18, -0.37179F, 0.0F, 0.37179F);
    this.tentacle19 = new ModelRenderer(this, 0, 0);
    this.tentacle19.addBox(-12.0F, -1.5F, -1.5F, 12, 3, 3);
    this.tentacle19.setRotationPoint(-20.0F, 17.0F, 3.0F);
    this.tentacle19.setTextureSize(128, 64);
    this.tentacle19.mirror = true;
    setRotation(this.tentacle19, 0.0F, 0.8179294F, 0.3717861F);
    this.tentacle20 = new ModelRenderer(this, 0, 0);
    this.tentacle20.addBox(-8.0F, 0.0F, 0.0F, 13, 5, 5);
    this.tentacle20.setRotationPoint(-15.0F, 15.0F, 0.0F);
    this.tentacle20.setTextureSize(128, 64);
    this.tentacle20.mirror = true;
    setRotation(this.tentacle20, 0.0F, 0.0F, 0.0F);
    this.tlib12 = new ModelRenderer(this, 0, 0);
    this.tlib12.addBox(-1.0F, -1.0F, -8.0F, 3, 3, 8);
    this.tlib12.setRotationPoint(22.0F, 13.0F, -8.0F);
    this.tlib12.setTextureSize(128, 64);
    this.tlib12.mirror = true;
    setRotation(this.tlib12, 0.0F, 0.0F, 0.0F);
    this.tlib22 = new ModelRenderer(this, 30, 20);
    this.tlib22.addBox(-1.0F, -5.0F, -1.0F, 3, 5, 3);
    this.tlib22.setRotationPoint(22.0F, 14.0F, -15.5F);
    this.tlib22.setTextureSize(128, 64);
    this.tlib22.mirror = true;
    setRotation(this.tlib22, 0.8922867F, 0.0F, 0.0F);
    this.tspike12 = new ModelRenderer(this, 25, 25);
    this.tspike12.addBox(0.0F, -2.0F, 0.0F, 1, 2, 1);
    this.tspike12.setRotationPoint(23.0F, 10.0F, -19.0F);
    this.tspike12.setTextureSize(128, 64);
    this.tspike12.mirror = true;
    setRotation(this.tspike12, 0.0371786F, 0.0F, 0.5948578F);
    this.tspike22 = new ModelRenderer(this, 25, 25);
    this.tspike22.addBox(-1.0F, -2.0F, 0.0F, 1, 2, 1);
    this.tspike22.setRotationPoint(22.0F, 10.0F, -19.0F);
    this.tspike22.setTextureSize(128, 64);
    this.tspike22.mirror = true;
    setRotation(this.tspike22, 0.0371786F, 0.0F, -0.5948606F);
    this.tspike32 = new ModelRenderer(this, 25, 25);
    this.tspike32.addBox(0.0F, -2.0F, 0.0F, 1, 2, 1);
    this.tspike32.setRotationPoint(23.0F, 11.5F, -19.0F);
    this.tspike32.setTextureSize(128, 64);
    this.tspike32.mirror = true;
    setRotation(this.tspike32, 1.115355F, 0.0F, 0.5948578F);
    this.tspike42 = new ModelRenderer(this, 25, 25);
    this.tspike42.addBox(-1.0F, -2.0F, 0.0F, 1, 2, 1);
    this.tspike42.setRotationPoint(22.0F, 11.5F, -19.0F);
    this.tspike42.setTextureSize(128, 64);
    this.tspike42.mirror = true;
    setRotation(this.tspike42, 1.115353F, 0.0F, -0.5948606F);
    this.tlib1 = new ModelRenderer(this, 0, 0);
    this.tlib1.addBox(-1.0F, -1.0F, -8.0F, 3, 3, 8);
    this.tlib1.setRotationPoint(-2.0F, 20.0F, -16.0F);
    this.tlib1.setTextureSize(128, 64);
    this.tlib1.mirror = true;
    setRotation(this.tlib1, 0.0F, 0.0F, 0.0F);
    this.tlib2 = new ModelRenderer(this, 30, 20);
    this.tlib2.addBox(-1.0F, -5.0F, -1.0F, 3, 5, 3);
    this.tlib2.setRotationPoint(-2.0F, 21.0F, -23.5F);
    this.tlib2.setTextureSize(128, 64);
    this.tlib2.mirror = true;
    setRotation(this.tlib2, 0.8922867F, 0.0F, 0.0F);
    this.tspike1 = new ModelRenderer(this, 25, 25);
    this.tspike1.addBox(0.0F, -2.0F, 0.0F, 1, 2, 1);
    this.tspike1.setRotationPoint(-1.0F, 17.0F, -27.0F);
    this.tspike1.setTextureSize(128, 64);
    this.tspike1.mirror = true;
    setRotation(this.tspike1, 0.0371786F, 0.0F, 0.5948578F);
    this.tspike2 = new ModelRenderer(this, 25, 25);
    this.tspike2.addBox(-1.0F, -2.0F, 0.0F, 1, 2, 1);
    this.tspike2.setRotationPoint(-2.0F, 17.0F, -27.0F);
    this.tspike2.setTextureSize(128, 64);
    this.tspike2.mirror = true;
    setRotation(this.tspike2, 0.0371786F, 0.0F, -0.5948606F);
    this.tspike3 = new ModelRenderer(this, 25, 25);
    this.tspike3.addBox(0.0F, -2.0F, 0.0F, 1, 2, 1);
    this.tspike3.setRotationPoint(-1.0F, 18.5F, -27.0F);
    this.tspike3.setTextureSize(128, 64);
    this.tspike3.mirror = true;
    setRotation(this.tspike3, 1.115355F, 0.0F, 0.5948578F);
    this.tspike4 = new ModelRenderer(this, 25, 25);
    this.tspike4.addBox(-1.0F, -2.0F, 0.0F, 1, 2, 1);
    this.tspike4.setRotationPoint(-2.0F, 18.5F, -27.5F);
    this.tspike4.setTextureSize(128, 64);
    this.tspike4.mirror = true;
    setRotation(this.tspike4, 1.115353F, 0.0F, -0.5948606F);
    this.lefthead = new ModelRenderer(this, 0, 30);
    this.lefthead.addBox(-4.5F, -9.0F, -4.5F, 9, 9, 8);
    this.lefthead.setRotationPoint(-9.0F, -3.0F, -7.0F);
    this.lefthead.setTextureSize(128, 64);
    this.lefthead.mirror = true;
    setRotation(this.lefthead, 0.0F, 0.0F, 0.0F);
    this.leftjaw = new ModelRenderer(this, 0, 50);
    this.leftjaw.addBox(-4.5F, 1.0F, -6.0F, 9, 1, 9);
    this.leftjaw.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.leftjaw.setTextureSize(128, 64);
    this.leftjaw.mirror = true;
    setRotation(this.leftjaw, 0.3346075F, 0.0F, 0.0F);
    this.lefthead.addChild(this.leftjaw);
    this.leftupperjaw = new ModelRenderer(this, 0, 28);
    this.leftupperjaw.addBox(0.0F, 0.0F, 0.0F, 9, 1, 1);
    this.leftupperjaw.setRotationPoint(-4.5F, -1.0F, -5.5F);
    this.leftupperjaw.setTextureSize(128, 64);
    this.leftupperjaw.mirror = true;
    setRotation(this.leftupperjaw, 0.0F, 0.0F, 0.0F);
    this.lefthead.addChild(this.leftupperjaw);
    this.lstooth1 = new ModelRenderer(this, 0, 50);
    this.lstooth1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.lstooth1.setRotationPoint(-3.5F, 0.0F, -6.0F);
    this.lstooth1.setTextureSize(128, 64);
    this.lstooth1.mirror = true;
    setRotation(this.lstooth1, 0.0F, 0.0F, 0.0F);
    this.leftjaw.addChild(this.lstooth1);
    this.lstooth2 = new ModelRenderer(this, 0, 50);
    this.lstooth2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.lstooth2.setRotationPoint(-1.5F, 0.0F, -6.0F);
    this.lstooth2.setTextureSize(128, 64);
    this.lstooth2.mirror = true;
    setRotation(this.lstooth2, 0.0F, 0.0F, 0.0F);
    this.leftjaw.addChild(this.lstooth2);
    this.lstooth3 = new ModelRenderer(this, 0, 50);
    this.lstooth3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.lstooth3.setRotationPoint(0.5F, 0.0F, -6.0F);
    this.lstooth3.setTextureSize(128, 64);
    this.lstooth3.mirror = true;
    setRotation(this.lstooth3, 0.0F, 0.0F, 0.0F);
    this.leftjaw.addChild(this.lstooth3);
    this.lstooth4 = new ModelRenderer(this, 0, 50);
    this.lstooth4.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.lstooth4.setRotationPoint(2.5F, 0.0F, -6.0F);
    this.lstooth4.setTextureSize(128, 64);
    this.lstooth4.mirror = true;
    setRotation(this.lstooth4, 0.0F, 0.0F, 0.0F);
    this.leftjaw.addChild(this.lstooth4);
    this.lbtooth1 = new ModelRenderer(this, 0, 50);
    this.lbtooth1.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.lbtooth1.setRotationPoint(0.0F, 1.0F, 0.0F);
    this.lbtooth1.setTextureSize(128, 64);
    this.lbtooth1.mirror = true;
    setRotation(this.lbtooth1, 0.0F, 0.0F, 0.0F);
    this.leftupperjaw.addChild(this.lbtooth1);
    this.lbtooth2 = new ModelRenderer(this, 0, 50);
    this.lbtooth2.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.lbtooth2.setRotationPoint(2.0F, 1.0F, 0.0F);
    this.lbtooth2.setTextureSize(128, 64);
    this.lbtooth2.mirror = true;
    setRotation(this.lbtooth2, 0.0F, 0.0F, 0.0F);
    this.leftupperjaw.addChild(this.lbtooth2);
    this.lbtooth3 = new ModelRenderer(this, 0, 50);
    this.lbtooth3.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.lbtooth3.setRotationPoint(4.0F, 1.0F, 0.0F);
    this.lbtooth3.setTextureSize(128, 64);
    this.lbtooth3.mirror = true;
    setRotation(this.lbtooth3, 0.0F, 0.0F, 0.0F);
    this.leftupperjaw.addChild(this.lbtooth3);
    this.lbtooth4 = new ModelRenderer(this, 0, 50);
    this.lbtooth4.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.lbtooth4.setRotationPoint(6.0F, 1.0F, 0.0F);
    this.lbtooth4.setTextureSize(128, 64);
    this.lbtooth4.mirror = true;
    setRotation(this.lbtooth4, 0.0F, 0.0F, 0.0F);
    this.leftupperjaw.addChild(this.lbtooth4);
    this.lbtooth5 = new ModelRenderer(this, 0, 50);
    this.lbtooth5.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.lbtooth5.setRotationPoint(8.0F, 1.0F, 0.0F);
    this.lbtooth5.setTextureSize(128, 64);
    this.lbtooth5.mirror = true;
    setRotation(this.lbtooth5, 0.0F, 0.0F, 0.0F);
    this.leftupperjaw.addChild(this.lbtooth5);
    this.lbackspike1 = new ModelRenderer(this, 10, 24);
    this.lbackspike1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3);
    this.lbackspike1.setRotationPoint(-0.5F, -2.0F, 3.5F);
    this.lbackspike1.setTextureSize(128, 64);
    this.lbackspike1.mirror = true;
    setRotation(this.lbackspike1, 0.0F, 0.0F, 0.0F);
    this.lefthead.addChild(this.lbackspike1);
    this.lbackspike2 = new ModelRenderer(this, 10, 24);
    this.lbackspike2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3);
    this.lbackspike2.setRotationPoint(-0.5F, -4.0F, 3.5F);
    this.lbackspike2.setTextureSize(128, 64);
    this.lbackspike2.mirror = true;
    setRotation(this.lbackspike2, 0.0F, 0.0F, 0.0F);
    this.lefthead.addChild(this.lbackspike2);
    this.lbackspike3 = new ModelRenderer(this, 10, 24);
    this.lbackspike3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3);
    this.lbackspike3.setRotationPoint(-0.5F, -6.0F, 3.5F);
    this.lbackspike3.setTextureSize(128, 64);
    this.lbackspike3.mirror = true;
    setRotation(this.lbackspike3, 0.0F, 0.0F, 0.0F);
    this.lefthead.addChild(this.lbackspike3);
    this.lackspike4 = new ModelRenderer(this, 10, 24);
    this.lackspike4.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3);
    this.lackspike4.setRotationPoint(-0.5F, -8.0F, 3.5F);
    this.lackspike4.setTextureSize(128, 64);
    this.lackspike4.mirror = true;
    setRotation(this.lackspike4, 0.0F, 0.0F, 0.0F);
    this.lefthead.addChild(this.lackspike4);
    this.lefteye1 = new ModelRenderer(this, 0, 24);
    this.lefteye1.addBox(0.0F, 0.0F, 0.0F, 3, 3, 1);
    this.lefteye1.setRotationPoint(0.5F, -6.0F, -5.5F);
    this.lefteye1.setTextureSize(128, 64);
    this.lefteye1.mirror = true;
    setRotation(this.lefteye1, 0.0F, 0.0F, 0.0F);
    this.lefthead.addChild(this.lefteye1);
    this.lefteye2 = new ModelRenderer(this, 0, 24);
    this.lefteye2.addBox(0.0F, 0.0F, 0.0F, 3, 3, 1);
    this.lefteye2.setRotationPoint(-3.5F, -6.0F, -5.5F);
    this.lefteye2.setTextureSize(128, 64);
    this.lefteye2.mirror = true;
    setRotation(this.lefteye2, 0.0F, 0.0F, 0.0F);
    this.lefthead.addChild(this.lefteye2);
    this.middlehead = new ModelRenderer(this, 0, 30);
    this.middlehead.addBox(-4.5F, -9.0F, -4.5F, 9, 9, 8);
    this.middlehead.setRotationPoint(3.0F, -3.0F, -7.0F);
    this.middlehead.setTextureSize(128, 64);
    this.middlehead.mirror = true;
    setRotation(this.middlehead, 0.0F, 0.0F, 0.0F);
    this.middlejaw = new ModelRenderer(this, 0, 50);
    this.middlejaw.addBox(-4.5F, 1.0F, -6.0F, 9, 1, 9);
    this.middlejaw.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.middlejaw.setTextureSize(128, 64);
    this.middlejaw.mirror = true;
    setRotation(this.middlejaw, 0.3346075F, 0.0F, 0.0F);
    this.middlehead.addChild(this.middlejaw);
    this.middleupperjaw = new ModelRenderer(this, 0, 28);
    this.middleupperjaw.addBox(0.0F, 0.0F, 0.0F, 9, 1, 1);
    this.middleupperjaw.setRotationPoint(-4.5F, -1.0F, -5.5F);
    this.middleupperjaw.setTextureSize(128, 64);
    this.middleupperjaw.mirror = true;
    setRotation(this.middleupperjaw, 0.0F, 0.0F, 0.0F);
    this.middlehead.addChild(this.middleupperjaw);
    this.mstooth1 = new ModelRenderer(this, 0, 50);
    this.mstooth1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.mstooth1.setRotationPoint(-3.5F, 0.0F, -6.0F);
    this.mstooth1.setTextureSize(128, 64);
    this.mstooth1.mirror = true;
    setRotation(this.mstooth1, 0.0F, 0.0F, 0.0F);
    this.middlejaw.addChild(this.mstooth1);
    this.mstooth2 = new ModelRenderer(this, 0, 50);
    this.mstooth2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.mstooth2.setRotationPoint(-1.5F, 0.0F, -6.0F);
    this.mstooth2.setTextureSize(128, 64);
    this.mstooth2.mirror = true;
    setRotation(this.mstooth2, 0.0F, 0.0F, 0.0F);
    this.middlejaw.addChild(this.mstooth2);
    this.mstooth3 = new ModelRenderer(this, 0, 50);
    this.mstooth3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.mstooth3.setRotationPoint(0.5F, 0.0F, -6.0F);
    this.mstooth3.setTextureSize(128, 64);
    this.mstooth3.mirror = true;
    setRotation(this.mstooth3, 0.0F, 0.0F, 0.0F);
    this.middlejaw.addChild(this.mstooth3);
    this.mstooth4 = new ModelRenderer(this, 0, 50);
    this.mstooth4.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.mstooth4.setRotationPoint(2.5F, 0.0F, -6.0F);
    this.mstooth4.setTextureSize(128, 64);
    this.mstooth4.mirror = true;
    setRotation(this.mstooth4, 0.0F, 0.0F, 0.0F);
    this.middlejaw.addChild(this.mstooth4);
    this.mbtooth1 = new ModelRenderer(this, 0, 50);
    this.mbtooth1.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.mbtooth1.setRotationPoint(0.0F, 1.0F, 0.0F);
    this.mbtooth1.setTextureSize(128, 64);
    this.mbtooth1.mirror = true;
    setRotation(this.mbtooth1, 0.0F, 0.0F, 0.0F);
    this.middleupperjaw.addChild(this.mbtooth1);
    this.mbtooth2 = new ModelRenderer(this, 0, 50);
    this.mbtooth2.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.mbtooth2.setRotationPoint(2.0F, 1.0F, 0.0F);
    this.mbtooth2.setTextureSize(128, 64);
    this.mbtooth2.mirror = true;
    setRotation(this.mbtooth2, 0.0F, 0.0F, 0.0F);
    this.middleupperjaw.addChild(this.mbtooth2);
    this.mbtooth3 = new ModelRenderer(this, 0, 50);
    this.mbtooth3.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.mbtooth3.setRotationPoint(4.0F, 1.0F, 0.0F);
    this.mbtooth3.setTextureSize(128, 64);
    this.mbtooth3.mirror = true;
    setRotation(this.mbtooth3, 0.0F, 0.0F, 0.0F);
    this.middleupperjaw.addChild(this.mbtooth3);
    this.mbtooth4 = new ModelRenderer(this, 0, 50);
    this.mbtooth4.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.mbtooth4.setRotationPoint(6.0F, 1.0F, 0.0F);
    this.mbtooth4.setTextureSize(128, 64);
    this.mbtooth4.mirror = true;
    setRotation(this.mbtooth4, 0.0F, 0.0F, 0.0F);
    this.middleupperjaw.addChild(this.mbtooth4);
    this.mbtooth5 = new ModelRenderer(this, 0, 50);
    this.mbtooth5.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.mbtooth5.setRotationPoint(8.0F, 1.0F, 0.0F);
    this.mbtooth5.setTextureSize(128, 64);
    this.mbtooth5.mirror = true;
    setRotation(this.mbtooth5, 0.0F, 0.0F, 0.0F);
    this.middleupperjaw.addChild(this.mbtooth5);
    this.mbackspike1 = new ModelRenderer(this, 10, 24);
    this.mbackspike1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3);
    this.mbackspike1.setRotationPoint(-0.5F, -2.0F, 3.5F);
    this.mbackspike1.setTextureSize(128, 64);
    this.mbackspike1.mirror = true;
    setRotation(this.mbackspike1, 0.0F, 0.0F, 0.0F);
    this.middlehead.addChild(this.mbackspike1);
    this.mbackspike2 = new ModelRenderer(this, 10, 24);
    this.mbackspike2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3);
    this.mbackspike2.setRotationPoint(-0.5F, -4.0F, 3.5F);
    this.mbackspike2.setTextureSize(128, 64);
    this.mbackspike2.mirror = true;
    setRotation(this.mbackspike2, 0.0F, 0.0F, 0.0F);
    this.middlehead.addChild(this.mbackspike2);
    this.mbackspike3 = new ModelRenderer(this, 10, 24);
    this.mbackspike3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3);
    this.mbackspike3.setRotationPoint(-0.5F, -6.0F, 3.5F);
    this.mbackspike3.setTextureSize(128, 64);
    this.mbackspike3.mirror = true;
    setRotation(this.mbackspike3, 0.0F, 0.0F, 0.0F);
    this.middlehead.addChild(this.mbackspike3);
    this.mbackspike4 = new ModelRenderer(this, 10, 24);
    this.mbackspike4.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3);
    this.mbackspike4.setRotationPoint(-0.5F, -8.0F, 3.5F);
    this.mbackspike4.setTextureSize(128, 64);
    this.mbackspike4.mirror = true;
    setRotation(this.mbackspike4, 0.0F, 0.0F, 0.0F);
    this.middlehead.addChild(this.mbackspike4);
    this.middleeye1 = new ModelRenderer(this, 0, 24);
    this.middleeye1.addBox(0.0F, 0.0F, 0.0F, 3, 3, 1);
    this.middleeye1.setRotationPoint(0.5F, -6.0F, -5.5F);
    this.middleeye1.setTextureSize(128, 64);
    this.middleeye1.mirror = true;
    setRotation(this.middleeye1, 0.0F, 0.0F, 0.0F);
    this.middlehead.addChild(this.middleeye1);
    this.middleeye2 = new ModelRenderer(this, 0, 24);
    this.middleeye2.addBox(0.0F, 0.0F, 0.0F, 3, 3, 1);
    this.middleeye2.setRotationPoint(-3.5F, -6.0F, -5.5F);
    this.middleeye2.setTextureSize(128, 64);
    this.middleeye2.mirror = true;
    setRotation(this.middleeye2, 0.0F, 0.0F, 0.0F);
    this.middlehead.addChild(this.middleeye2);
    this.righthead = new ModelRenderer(this, 0, 30);
    this.righthead.addBox(-4.5F, -9.0F, -4.5F, 9, 9, 8);
    this.righthead.setRotationPoint(15.0F, -3.0F, -7.0F);
    this.righthead.setTextureSize(128, 64);
    this.righthead.mirror = true;
    setRotation(this.righthead, 0.0F, 0.0F, 0.0F);
    this.rightjaw = new ModelRenderer(this, 0, 50);
    this.rightjaw.addBox(-4.5F, 1.0F, -6.0F, 9, 1, 9);
    this.rightjaw.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.rightjaw.setTextureSize(128, 64);
    this.rightjaw.mirror = true;
    setRotation(this.rightjaw, 0.3346075F, 0.0F, 0.0F);
    this.righthead.addChild(this.rightjaw);
    this.rightupperjaw = new ModelRenderer(this, 0, 28);
    this.rightupperjaw.addBox(0.0F, 0.0F, 0.0F, 9, 1, 1);
    this.rightupperjaw.setRotationPoint(-4.5F, -1.0F, -5.5F);
    this.rightupperjaw.setTextureSize(128, 64);
    this.rightupperjaw.mirror = true;
    setRotation(this.rightupperjaw, 0.0F, 0.0F, 0.0F);
    this.righthead.addChild(this.rightupperjaw);
    this.rstooth1 = new ModelRenderer(this, 0, 50);
    this.rstooth1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.rstooth1.setRotationPoint(-3.5F, 0.0F, -6.0F);
    this.rstooth1.setTextureSize(128, 64);
    this.rstooth1.mirror = true;
    setRotation(this.rstooth1, 0.0F, 0.0F, 0.0F);
    this.rightjaw.addChild(this.rstooth1);
    this.rstooth2 = new ModelRenderer(this, 0, 50);
    this.rstooth2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.rstooth2.setRotationPoint(-1.5F, 0.0F, -6.0F);
    this.rstooth2.setTextureSize(128, 64);
    this.rstooth2.mirror = true;
    setRotation(this.rstooth2, 0.0F, 0.0F, 0.0F);
    this.rightjaw.addChild(this.rstooth2);
    this.rstooth3 = new ModelRenderer(this, 0, 50);
    this.rstooth3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.rstooth3.setRotationPoint(0.5F, 0.0F, -6.0F);
    this.rstooth3.setTextureSize(128, 64);
    this.rstooth3.mirror = true;
    setRotation(this.rstooth3, 0.0F, 0.0F, 0.0F);
    this.rightjaw.addChild(this.rstooth3);
    this.rstooth4 = new ModelRenderer(this, 0, 50);
    this.rstooth4.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.rstooth4.setRotationPoint(2.5F, 0.0F, -6.0F);
    this.rstooth4.setTextureSize(128, 64);
    this.rstooth4.mirror = true;
    setRotation(this.rstooth4, 0.0F, 0.0F, 0.0F);
    this.rightjaw.addChild(this.rstooth4);
    this.rbtooth1 = new ModelRenderer(this, 0, 50);
    this.rbtooth1.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.rbtooth1.setRotationPoint(0.0F, 1.0F, 0.0F);
    this.rbtooth1.setTextureSize(128, 64);
    this.rbtooth1.mirror = true;
    setRotation(this.rbtooth1, 0.0F, 0.0F, 0.0F);
    this.rightupperjaw.addChild(this.rbtooth1);
    this.rbtooth2 = new ModelRenderer(this, 0, 50);
    this.rbtooth2.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.rbtooth2.setRotationPoint(2.0F, 1.0F, 0.0F);
    this.rbtooth2.setTextureSize(128, 64);
    this.rbtooth2.mirror = true;
    setRotation(this.rbtooth2, 0.0F, 0.0F, 0.0F);
    this.rightupperjaw.addChild(this.rbtooth2);
    this.rbtooth3 = new ModelRenderer(this, 0, 50);
    this.rbtooth3.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.rbtooth3.setRotationPoint(4.0F, 1.0F, 0.0F);
    this.rbtooth3.setTextureSize(128, 64);
    this.rbtooth3.mirror = true;
    setRotation(this.rbtooth3, 0.0F, 0.0F, 0.0F);
    this.rightupperjaw.addChild(this.rbtooth3);
    this.rbtooth4 = new ModelRenderer(this, 0, 50);
    this.rbtooth4.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.rbtooth4.setRotationPoint(6.0F, 1.0F, 0.0F);
    this.rbtooth4.setTextureSize(128, 64);
    this.rbtooth4.mirror = true;
    setRotation(this.rbtooth4, 0.0F, 0.0F, 0.0F);
    this.rightupperjaw.addChild(this.rbtooth4);
    this.rbtooth5 = new ModelRenderer(this, 0, 50);
    this.rbtooth5.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.rbtooth5.setRotationPoint(8.0F, 1.0F, 0.0F);
    this.rbtooth5.setTextureSize(128, 64);
    this.rbtooth5.mirror = true;
    setRotation(this.rbtooth5, 0.0F, 0.0F, 0.0F);
    this.rightupperjaw.addChild(this.rbtooth5);
    this.rbackspike1 = new ModelRenderer(this, 10, 24);
    this.rbackspike1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3);
    this.rbackspike1.setRotationPoint(-0.5F, -2.0F, 3.5F);
    this.rbackspike1.setTextureSize(128, 64);
    this.rbackspike1.mirror = true;
    setRotation(this.rbackspike1, 0.0F, 0.0F, 0.0F);
    this.righthead.addChild(this.rbackspike1);
    this.rbackspike2 = new ModelRenderer(this, 10, 24);
    this.rbackspike2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3);
    this.rbackspike2.setRotationPoint(-0.5F, -4.0F, 3.5F);
    this.rbackspike2.setTextureSize(128, 64);
    this.rbackspike2.mirror = true;
    setRotation(this.rbackspike2, 0.0F, 0.0F, 0.0F);
    this.righthead.addChild(this.rbackspike2);
    this.rbackspike3 = new ModelRenderer(this, 10, 24);
    this.rbackspike3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3);
    this.rbackspike3.setRotationPoint(-0.5F, -6.0F, 3.5F);
    this.rbackspike3.setTextureSize(128, 64);
    this.rbackspike3.mirror = true;
    setRotation(this.rbackspike3, 0.0F, 0.0F, 0.0F);
    this.righthead.addChild(this.rbackspike3);
    this.rbackspike4 = new ModelRenderer(this, 10, 24);
    this.rbackspike4.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3);
    this.rbackspike4.setRotationPoint(-0.5F, -8.0F, 3.5F);
    this.rbackspike4.setTextureSize(128, 64);
    this.rbackspike4.mirror = true;
    setRotation(this.rbackspike4, 0.0F, 0.0F, 0.0F);
    this.righthead.addChild(this.rbackspike4);
    this.righteye1 = new ModelRenderer(this, 0, 24);
    this.righteye1.addBox(0.0F, 0.0F, 0.0F, 3, 3, 1);
    this.righteye1.setRotationPoint(0.5F, -6.0F, -5.5F);
    this.righteye1.setTextureSize(128, 64);
    this.righteye1.mirror = true;
    setRotation(this.righteye1, 0.0F, 0.0F, 0.0F);
    this.righthead.addChild(this.righteye1);
    this.righteye2 = new ModelRenderer(this, 0, 24);
    this.righteye2.addBox(0.0F, 0.0F, 0.0F, 3, 3, 1);
    this.righteye2.setRotationPoint(-3.5F, -6.0F, -5.5F);
    this.righteye2.setTextureSize(128, 64);
    this.righteye2.mirror = true;
    setRotation(this.righteye2, 0.0F, 0.0F, 0.0F);
    this.righthead.addChild(this.righteye2);
    this.middlewall = new ModelRenderer(this, 46, 0);
    this.middlewall.addBox(0.0F, 0.0F, 0.0F, 40, 40, 1);
    this.middlewall.setRotationPoint(-18.0F, -15.0F, 17.0F);
    this.middlewall.setTextureSize(128, 64);
    this.middlewall.mirror = true;
    setRotation(this.middlewall, 0.0F, 0.0F, 0.0F);
    this.rightwall = new ModelRenderer(this, 46, 0);
    this.rightwall.addBox(0.0F, 0.0F, 0.0F, 40, 40, 1);
    this.rightwall.setRotationPoint(22.0F, -15.0F, 17.0F);
    this.rightwall.setTextureSize(128, 64);
    this.rightwall.mirror = true;
    setRotation(this.rightwall, 0.0F, 0.669215F, 0.0F);
    this.leftwall = new ModelRenderer(this, 46, 0);
    this.leftwall.addBox(-40.0F, 0.0F, 0.0F, 40, 40, 1);
    this.leftwall.setRotationPoint(-18.0F, -15.0F, 17.0F);
    this.leftwall.setTextureSize(128, 64);
    this.leftwall.mirror = true;
    setRotation(this.leftwall, 0.0F, -0.6692116F, 0.0F);
    this.walltentacle1 = new ModelRenderer(this, 0, 0);
    this.walltentacle1.addBox(-1.5F, -1.5F, -12.0F, 3, 3, 12);
    this.walltentacle1.setRotationPoint(-30.0F, -3.0F, 9.0F);
    this.walltentacle1.setTextureSize(128, 64);
    this.walltentacle1.mirror = true;
    setRotation(this.walltentacle1, 0.0F, 0.0F, 0.0F);
    this.walltentacle2 = new ModelRenderer(this, 0, 0);
    this.walltentacle2.addBox(-1.5F, -1.5F, -12.0F, 3, 3, 12);
    this.walltentacle2.setRotationPoint(-33.0F, 9.0F, 7.0F);
    this.walltentacle2.setTextureSize(128, 64);
    this.walltentacle2.mirror = true;
    setRotation(this.walltentacle2, 0.0F, 0.0F, 0.0F);
    this.walltentacle3 = new ModelRenderer(this, 0, 0);
    this.walltentacle3.addBox(-1.5F, -1.5F, -12.0F, 3, 3, 12);
    this.walltentacle3.setRotationPoint(-42.0F, 0.0F, 0.0F);
    this.walltentacle3.setTextureSize(128, 64);
    this.walltentacle3.mirror = true;
    setRotation(this.walltentacle3, 0.0F, 0.0F, 0.0F);
    this.walltentacle4 = new ModelRenderer(this, 0, 0);
    this.walltentacle4.addBox(-1.5F, -1.5F, -12.0F, 3, 3, 12);
    this.walltentacle4.setRotationPoint(-23.0F, 6.0F, 15.0F);
    this.walltentacle4.setTextureSize(128, 64);
    this.walltentacle4.mirror = true;
    setRotation(this.walltentacle4, 0.0F, 0.0F, 0.0F);
    this.walltentacle5 = new ModelRenderer(this, 0, 0);
    this.walltentacle5.addBox(-1.5F, -1.5F, -12.0F, 3, 3, 12);
    this.walltentacle5.setRotationPoint(42.0F, 0.0F, 3.0F);
    this.walltentacle5.setTextureSize(128, 64);
    this.walltentacle5.mirror = true;
    setRotation(this.walltentacle5, 0.0F, 0.0F, 0.0F);
    this.walltentacle6 = new ModelRenderer(this, 0, 0);
    this.walltentacle6.addBox(-1.5F, -1.5F, -12.0F, 3, 3, 12);
    this.walltentacle6.setRotationPoint(33.0F, -6.0F, 9.0F);
    this.walltentacle6.setTextureSize(128, 64);
    this.walltentacle6.mirror = true;
    setRotation(this.walltentacle6, 0.0F, 0.0F, 0.0F);
    this.walltentacle7 = new ModelRenderer(this, 0, 0);
    this.walltentacle7.addBox(-1.5F, -1.5F, -12.0F, 3, 3, 12);
    this.walltentacle7.setRotationPoint(36.0F, 11.0F, 7.0F);
    this.walltentacle7.setTextureSize(128, 64);
    this.walltentacle7.mirror = true;
    setRotation(this.walltentacle7, 0.0F, 0.0F, 0.0F);
    this.walltentacle8 = new ModelRenderer(this, 0, 0);
    this.walltentacle8.addBox(-1.5F, -1.5F, -12.0F, 3, 3, 12);
    this.walltentacle8.setRotationPoint(46.13334F, 17.0F, 0.0F);
    this.walltentacle8.setTextureSize(128, 64);
    this.walltentacle8.mirror = true;
    setRotation(this.walltentacle8, 0.0F, 0.0F, 0.0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    GlStateManager.pushMatrix();
    super.render(entity, f, f1, f2, f3, f4, f5);
    GlStateManager.scale(2.0F, 2.0F, 2.0F);
    GlStateManager.translate(-0.175F, -0.75F, 0.5F);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.frontspike1.render(f5);
    this.frontspike2.render(f5);
    this.frontspike3.render(f5);
    this.frontspike4.render(f5);
    this.frontspike5.render(f5);
    this.leftneck.render(f5);
    this.middleneck.render(f5);
    this.rightneck.render(f5);
    this.body.render(f5);
    this.tentacle2base.render(f5);
    this.tentacle1.render(f5);
    this.tentacle2.render(f5);
    this.tentacle3.render(f5);
    this.tentacle4.render(f5);
    this.tentacle5.render(f5);
    this.tentacle6.render(f5);
    this.tentacle7.render(f5);
    this.tentacle8.render(f5);
    this.tentacle9.render(f5);
    this.tentacle10.render(f5);
    this.tentacle11.render(f5);
    this.tentacle12.render(f5);
    this.tentacle13.render(f5);
    this.tentacle14.render(f5);
    this.tentacle15.render(f5);
    this.tentacle16.render(f5);
    this.tentacle17.render(f5);
    this.tentacle18.render(f5);
    this.tentacle19.render(f5);
    this.tentacle20.render(f5);
    this.tlib12.render(f5);
    this.tlib22.render(f5);
    this.tspike12.render(f5);
    this.tspike22.render(f5);
    this.tspike32.render(f5);
    this.tspike42.render(f5);
    this.tlib1.render(f5);
    this.tlib2.render(f5);
    this.tspike1.render(f5);
    this.tspike2.render(f5);
    this.tspike3.render(f5);
    this.tspike4.render(f5);
    this.lefthead.render(f5);
    this.middlehead.render(f5);
    this.righthead.render(f5);
    this.middlewall.render(f5);
    this.rightwall.render(f5);
    this.leftwall.render(f5);
    this.walltentacle1.render(f5);
    this.walltentacle2.render(f5);
    this.walltentacle3.render(f5);
    this.walltentacle4.render(f5);
    this.walltentacle5.render(f5);
    this.walltentacle6.render(f5);
    this.walltentacle7.render(f5);
    this.walltentacle8.render(f5);
    GlStateManager.popMatrix();
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z) {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setLivingAnimations(EntityLivingBase p_78086_1_, float p_78086_2_, float p_78086_3_, float p_78086_4_) {
    EntityChagaroth entitywither = (EntityChagaroth)p_78086_1_;
    this.lefthead.rotateAngleY = (entitywither.getHeadYRotation(0) - p_78086_1_.renderYawOffset) * 0.017453292F + (!entitywither.getCurrentBook().isEmpty() ? -(MathHelper.sin(entitywither.ticksExisted * 0.1F) * 0.5F + 0.5F) : 0.0F);
    this.lefthead.rotateAngleX = entitywither.getHeadXRotation(0) * 0.017453292F;
    this.righthead.rotateAngleY = (entitywither.getHeadYRotation(1) - p_78086_1_.renderYawOffset) * 0.017453292F + (!entitywither.getCurrentBook().isEmpty() ? -(MathHelper.sin(entitywither.ticksExisted * 0.1F) * 0.5F - 0.5F) : 0.0F);
    this.righthead.rotateAngleX = entitywither.getHeadXRotation(1) * 0.017453292F;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
    this.middlehead.rotateAngleY = f3 / 57.295776F;
    this.middlehead.rotateAngleX = f4 / 57.295776F;
    this.walltentacle1.rotateAngleX = MathHelper.cos(f4 * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.walltentacle2.rotateAngleX = MathHelper.cos(f4 * 0.6662F) * 2.0F * f1 * 0.5F;
    this.walltentacle3.rotateAngleX = MathHelper.cos(f4 * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.walltentacle4.rotateAngleX = MathHelper.cos(f4 * 0.6662F) * 2.0F * f1 * 0.5F;
    this.walltentacle5.rotateAngleX = MathHelper.cos(f4 * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.walltentacle6.rotateAngleX = MathHelper.cos(f4 * 0.6662F) * 2.0F * f1 * 0.5F;
    this.walltentacle7.rotateAngleX = MathHelper.cos(f4 * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.walltentacle8.rotateAngleX = MathHelper.cos(f4 * 0.6662F) * 2.0F * f1 * 0.5F;
    this.walltentacle1.rotateAngleY = MathHelper.cos(f3 * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.walltentacle2.rotateAngleY = MathHelper.cos(f3 * 0.6662F) * 2.0F * f1 * 0.5F;
    this.walltentacle3.rotateAngleY = MathHelper.cos(f3 * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.walltentacle4.rotateAngleY = MathHelper.cos(f3 * 0.6662F) * 2.0F * f1 * 0.5F;
    this.walltentacle5.rotateAngleY = MathHelper.cos(f3 * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.walltentacle6.rotateAngleY = MathHelper.cos(f3 * 0.6662F) * 2.0F * f1 * 0.5F;
    this.walltentacle7.rotateAngleY = MathHelper.cos(f3 * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.walltentacle8.rotateAngleY = MathHelper.cos(f3 * 0.6662F) * 2.0F * f1 * 0.5F;
    this.walltentacle1.rotateAngleZ = MathHelper.cos(f5 * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.walltentacle2.rotateAngleZ = MathHelper.cos(f5 * 0.6662F) * 2.0F * f1 * 0.5F;
    this.walltentacle3.rotateAngleZ = MathHelper.cos(f5 * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.walltentacle4.rotateAngleZ = MathHelper.cos(f5 * 0.6662F) * 2.0F * f1 * 0.5F;
    this.walltentacle5.rotateAngleZ = MathHelper.cos(f5 * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.walltentacle6.rotateAngleZ = MathHelper.cos(f5 * 0.6662F) * 2.0F * f1 * 0.5F;
    this.walltentacle7.rotateAngleZ = MathHelper.cos(f5 * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.walltentacle8.rotateAngleZ = MathHelper.cos(f5 * 0.6662F) * 2.0F * f1 * 0.5F;
    if (((EntityChagaroth)entity).deathTicks <= 200 && ((EntityChagaroth)entity).deathTicks > 0) {
      this.rot++;
      this.lefthead.rotateAngleY = (180.0F - this.rot) / 3.1415927F;
      this.lefthead.rotateAngleX = (180.0F + this.rot) / 3.1415927F;
      this.lefthead.rotateAngleZ = (180.0F + this.rot) / 3.1415927F;
      this.middlehead.rotateAngleY = (180.0F + this.rot) / 3.1415927F;
      this.middlehead.rotateAngleX = (180.0F - this.rot) / 3.1415927F;
      this.middlehead.rotateAngleZ = (180.0F - this.rot) / 3.1415927F;
      this.righthead.rotateAngleY = (180.0F + this.rot) / 3.1415927F;
      this.righthead.rotateAngleX = (180.0F + this.rot) / 3.1415927F;
      this.righthead.rotateAngleZ = (180.0F + this.rot) / 3.1415927F;
      this.walltentacle1.isHidden = true;
      this.walltentacle2.isHidden = true;
      this.walltentacle3.isHidden = true;
      this.walltentacle4.isHidden = true;
      this.walltentacle5.isHidden = true;
      this.walltentacle6.isHidden = true;
      this.walltentacle7.isHidden = true;
      this.walltentacle8.isHidden = true;
      this.leftwall.isHidden = true;
      this.rightwall.isHidden = true;
    } 
    if (((EntityChagaroth)entity).deathTicks == 0) {
      this.lefthead.rotateAngleZ = 0.0F;
      this.middlehead.rotateAngleY = f3 / 57.295776F;
      this.middlehead.rotateAngleX = f4 / 57.295776F;
      this.middlehead.rotateAngleZ = 0.0F;
      this.middlejaw.rotateAngleX = 0.3346075F;
      this.righthead.rotateAngleZ = 0.0F;
      this.walltentacle1.isHidden = false;
      this.walltentacle2.isHidden = false;
      this.walltentacle3.isHidden = false;
      this.walltentacle4.isHidden = false;
      this.walltentacle5.isHidden = false;
      this.walltentacle6.isHidden = false;
      this.walltentacle7.isHidden = false;
      this.walltentacle8.isHidden = false;
      this.leftwall.isHidden = false;
      this.rightwall.isHidden = false;
      if (((EntityChagaroth)entity).hasOpenMouth()) {
        this.middlehead.rotateAngleX -= 0.75F + MathHelper.cos(f2 * 2.0F) * 0.125F;
        this.middlejaw.rotateAngleX += 0.75F;
      } 
    } 
  }
}
