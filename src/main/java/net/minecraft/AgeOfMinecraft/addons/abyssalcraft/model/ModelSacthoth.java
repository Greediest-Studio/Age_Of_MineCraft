package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.model;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelSacthoth extends ModelBase {
  public ModelRenderer head;
  
  public ModelRenderer leftshoulder;
  
  public ModelRenderer leftarm1;
  
  public ModelRenderer leftarm2;
  
  public ModelRenderer rightshoulder;
  
  public ModelRenderer rightarm1;
  
  public ModelRenderer rightarm2;
  
  public ModelRenderer body;
  
  public ModelRenderer core;
  
  public ModelRenderer leftleg;
  
  public ModelRenderer rightleg;
  
  public ModelRenderer spikerow11;
  
  public ModelRenderer spikerow12;
  
  public ModelRenderer spikerow13;
  
  public ModelRenderer spikerow14;
  
  public ModelRenderer spikerow15;
  
  public ModelRenderer spikerow16;
  
  public ModelRenderer spikerow17;
  
  public ModelRenderer spikerow18;
  
  public ModelRenderer spikerow19;
  
  public ModelRenderer spikerow110;
  
  public ModelRenderer spikerow111;
  
  public ModelRenderer spikerow112;
  
  public ModelRenderer spikerow113;
  
  public ModelRenderer spikerow114;
  
  public ModelRenderer spikerow115;
  
  public ModelRenderer spikerow116;
  
  public ModelRenderer spikerow21;
  
  public ModelRenderer spikerow22;
  
  public ModelRenderer spikerow23;
  
  public ModelRenderer spikerow24;
  
  public ModelRenderer spikerow25;
  
  public ModelRenderer spikerow26;
  
  public ModelRenderer spikerow27;
  
  public ModelRenderer spikerow28;
  
  public ModelRenderer spikerow29;
  
  public ModelRenderer spikerow210;
  
  public ModelRenderer spikerow211;
  
  public ModelRenderer spikerow212;
  
  public ModelRenderer spikerow31;
  
  public ModelRenderer spikerow32;
  
  public ModelRenderer spikerow33;
  
  public ModelRenderer spikerow34;
  
  public ModelRenderer spikerow35;
  
  public ModelRenderer spikerow36;
  
  public ModelRenderer spikerow37;
  
  public ModelRenderer spikerow38;
  
  public ModelRenderer spikerow41;
  
  public ModelRenderer spikerow42;
  
  public ModelRenderer spikerow43;
  
  public ModelRenderer spikerow44;
  
  public ModelRenderer tbase1;
  
  public ModelRenderer tbase2;
  
  public ModelRenderer tbase3;
  
  public ModelRenderer tbase4;
  
  public ModelRenderer tbase5;
  
  public ModelRenderer tentacle1;
  
  public ModelRenderer tentacle2;
  
  public ModelRenderer tentacle3;
  
  public ModelRenderer tentacle4;
  
  public ModelRenderer tentacle5;
  
  public ModelSacthoth() {
    this.textureWidth = 128;
    this.textureHeight = 64;
    this.head = new ModelRenderer(this, 0, 0);
    this.head.addBox(-4.5F, -10.0F, -4.5F, 9, 10, 9);
    this.head.setRotationPoint(0.0F, -22.0F, 0.0F);
    this.head.setTextureSize(128, 64);
    this.head.mirror = true;
    setRotation(this.head, 0.0F, 0.0F, 0.0F);
    this.leftshoulder = new ModelRenderer(this, 72, 0);
    this.leftshoulder.addBox(0.0F, 0.0F, 0.0F, 8, 5, 7);
    this.leftshoulder.setRotationPoint(5.5F, -22.0F, -3.5F);
    this.leftshoulder.setTextureSize(128, 64);
    this.leftshoulder.mirror = true;
    setRotation(this.leftshoulder, 0.0F, 0.0F, 0.185895F);
    this.leftarm1 = new ModelRenderer(this, 72, 12);
    this.leftarm1.addBox(-5.0F, -1.0F, -2.5F, 5, 12, 5);
    this.leftarm1.setRotationPoint(-6.5F, -19.0F, 0.0F);
    this.leftarm1.setTextureSize(128, 64);
    this.leftarm1.mirror = true;
    setRotation(this.leftarm1, 0.0F, 0.0F, 0.0F);
    this.leftarm2 = new ModelRenderer(this, 72, 12);
    this.leftarm2.addBox(-5.0F, 8.0F, 3.0F, 5, 12, 5);
    this.leftarm2.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.leftarm2.setTextureSize(128, 64);
    this.leftarm2.mirror = true;
    setRotation(this.leftarm2, -0.5576792F, 0.0F, 0.0F);
    this.leftarm1.addChild(this.leftarm2);
    this.rightshoulder = new ModelRenderer(this, 72, 0);
    this.rightshoulder.addBox(-8.0F, 0.0F, 0.0F, 8, 5, 7);
    this.rightshoulder.setRotationPoint(-5.5F, -22.0F, -3.5F);
    this.rightshoulder.setTextureSize(128, 64);
    this.rightshoulder.mirror = true;
    setRotation(this.rightshoulder, 0.0F, 0.0F, -0.1858931F);
    this.rightarm1 = new ModelRenderer(this, 72, 12);
    this.rightarm1.addBox(-0.5F, -1.0F, -2.5F, 5, 12, 5);
    this.rightarm1.setRotationPoint(7.0F, -19.0F, 0.0F);
    this.rightarm1.setTextureSize(128, 64);
    this.rightarm1.mirror = true;
    setRotation(this.rightarm1, 0.0F, 0.0F, 0.0F);
    this.rightarm2 = new ModelRenderer(this, 72, 12);
    this.rightarm2.addBox(-0.5F, 8.0F, 3.0F, 5, 12, 5);
    this.rightarm2.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.rightarm2.setTextureSize(128, 64);
    this.rightarm2.mirror = true;
    setRotation(this.rightarm2, -0.5576851F, 0.0F, 0.0F);
    this.rightarm1.addChild(this.rightarm2);
    this.body = new ModelRenderer(this, 36, 0);
    this.body.addBox(0.0F, 0.0F, 0.0F, 11, 24, 7);
    this.body.setRotationPoint(-5.5F, -22.0F, -3.5F);
    this.body.setTextureSize(128, 64);
    this.body.mirror = true;
    setRotation(this.body, 0.0F, 0.0F, 0.0F);
    this.core = new ModelRenderer(this, 0, 20);
    this.core.addBox(0.0F, 0.0F, 0.0F, 5, 5, 1);
    this.core.setRotationPoint(-2.5F, -15.0F, -4.5F);
    this.core.setTextureSize(128, 64);
    this.core.mirror = true;
    setRotation(this.core, 0.0F, 0.0F, 0.0F);
    this.leftleg = new ModelRenderer(this, 102, 0);
    this.leftleg.addBox(-2.5F, 0.0F, -3.5F, 5, 22, 7);
    this.leftleg.setRotationPoint(-3.0F, 2.0F, 0.0F);
    this.leftleg.setTextureSize(128, 64);
    this.leftleg.mirror = true;
    setRotation(this.leftleg, 0.0F, 0.0F, 0.0F);
    this.rightleg = new ModelRenderer(this, 102, 0);
    this.rightleg.addBox(-2.5F, 0.0F, -3.5F, 5, 22, 7);
    this.rightleg.setRotationPoint(3.0F, 2.0F, 0.0F);
    this.rightleg.setTextureSize(128, 64);
    this.rightleg.mirror = true;
    setRotation(this.rightleg, 0.0F, 0.0F, 0.0F);
    this.spikerow11 = new ModelRenderer(this, 0, 0);
    this.spikerow11.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.spikerow11.setRotationPoint(-4.5F, -12.0F, -4.5F);
    this.spikerow11.setTextureSize(128, 64);
    this.spikerow11.mirror = true;
    setRotation(this.spikerow11, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow11);
    this.spikerow12 = new ModelRenderer(this, 0, 0);
    this.spikerow12.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.spikerow12.setRotationPoint(-2.5F, -12.0F, -4.5F);
    this.spikerow12.setTextureSize(128, 64);
    this.spikerow12.mirror = true;
    setRotation(this.spikerow12, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow12);
    this.spikerow13 = new ModelRenderer(this, 0, 0);
    this.spikerow13.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.spikerow13.setRotationPoint(-0.5F, -12.0F, -4.5F);
    this.spikerow13.setTextureSize(128, 64);
    this.spikerow13.mirror = true;
    setRotation(this.spikerow13, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow13);
    this.spikerow14 = new ModelRenderer(this, 0, 0);
    this.spikerow14.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.spikerow14.setRotationPoint(1.5F, -12.0F, -4.5F);
    this.spikerow14.setTextureSize(128, 64);
    this.spikerow14.mirror = true;
    setRotation(this.spikerow14, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow14);
    this.spikerow15 = new ModelRenderer(this, 0, 0);
    this.spikerow15.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.spikerow15.setRotationPoint(3.5F, -12.0F, -4.5F);
    this.spikerow15.setTextureSize(128, 64);
    this.spikerow15.mirror = true;
    setRotation(this.spikerow15, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow15);
    this.spikerow16 = new ModelRenderer(this, 0, 0);
    this.spikerow16.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.spikerow16.setRotationPoint(3.5F, -12.0F, -2.5F);
    this.spikerow16.setTextureSize(128, 64);
    this.spikerow16.mirror = true;
    setRotation(this.spikerow16, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow16);
    this.spikerow17 = new ModelRenderer(this, 0, 0);
    this.spikerow17.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.spikerow17.setRotationPoint(3.5F, -12.0F, -0.5F);
    this.spikerow17.setTextureSize(128, 64);
    this.spikerow17.mirror = true;
    setRotation(this.spikerow17, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow17);
    this.spikerow18 = new ModelRenderer(this, 0, 0);
    this.spikerow18.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.spikerow18.setRotationPoint(3.5F, -12.0F, 1.5F);
    this.spikerow18.setTextureSize(128, 64);
    this.spikerow18.mirror = true;
    setRotation(this.spikerow18, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow18);
    this.spikerow19 = new ModelRenderer(this, 0, 0);
    this.spikerow19.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.spikerow19.setRotationPoint(3.5F, -12.0F, 3.5F);
    this.spikerow19.setTextureSize(128, 64);
    this.spikerow19.mirror = true;
    setRotation(this.spikerow19, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow19);
    this.spikerow110 = new ModelRenderer(this, 0, 0);
    this.spikerow110.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.spikerow110.setRotationPoint(1.5F, -12.0F, 3.5F);
    this.spikerow110.setTextureSize(128, 64);
    this.spikerow110.mirror = true;
    setRotation(this.spikerow110, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow110);
    this.spikerow111 = new ModelRenderer(this, 0, 0);
    this.spikerow111.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.spikerow111.setRotationPoint(-0.5F, -12.0F, 3.5F);
    this.spikerow111.setTextureSize(128, 64);
    this.spikerow111.mirror = true;
    setRotation(this.spikerow111, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow111);
    this.spikerow112 = new ModelRenderer(this, 0, 0);
    this.spikerow112.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.spikerow112.setRotationPoint(-2.5F, -12.0F, 3.5F);
    this.spikerow112.setTextureSize(128, 64);
    this.spikerow112.mirror = true;
    setRotation(this.spikerow112, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow112);
    this.spikerow113 = new ModelRenderer(this, 0, 0);
    this.spikerow113.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.spikerow113.setRotationPoint(-4.5F, -12.0F, 3.5F);
    this.spikerow113.setTextureSize(128, 64);
    this.spikerow113.mirror = true;
    setRotation(this.spikerow113, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow113);
    this.spikerow114 = new ModelRenderer(this, 0, 0);
    this.spikerow114.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.spikerow114.setRotationPoint(-4.5F, -12.0F, 1.5F);
    this.spikerow114.setTextureSize(128, 64);
    this.spikerow114.mirror = true;
    setRotation(this.spikerow114, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow114);
    this.spikerow115 = new ModelRenderer(this, 0, 0);
    this.spikerow115.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.spikerow115.setRotationPoint(-4.5F, -12.0F, -0.5F);
    this.spikerow115.setTextureSize(128, 64);
    this.spikerow115.mirror = true;
    setRotation(this.spikerow115, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow115);
    this.spikerow116 = new ModelRenderer(this, 0, 0);
    this.spikerow116.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.spikerow116.setRotationPoint(-4.5F, -12.0F, -2.5F);
    this.spikerow116.setTextureSize(128, 64);
    this.spikerow116.mirror = true;
    setRotation(this.spikerow116, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow116);
    this.spikerow21 = new ModelRenderer(this, 0, 0);
    this.spikerow21.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.spikerow21.setRotationPoint(-3.5F, -13.0F, -3.5F);
    this.spikerow21.setTextureSize(128, 64);
    this.spikerow21.mirror = true;
    setRotation(this.spikerow21, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow21);
    this.spikerow22 = new ModelRenderer(this, 0, 0);
    this.spikerow22.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.spikerow22.setRotationPoint(-1.5F, -13.0F, -3.5F);
    this.spikerow22.setTextureSize(128, 64);
    this.spikerow22.mirror = true;
    setRotation(this.spikerow22, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow22);
    this.spikerow23 = new ModelRenderer(this, 0, 0);
    this.spikerow23.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.spikerow23.setRotationPoint(0.5F, -13.0F, -3.5F);
    this.spikerow23.setTextureSize(128, 64);
    this.spikerow23.mirror = true;
    setRotation(this.spikerow23, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow23);
    this.spikerow24 = new ModelRenderer(this, 0, 0);
    this.spikerow24.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.spikerow24.setRotationPoint(2.5F, -13.0F, -3.5F);
    this.spikerow24.setTextureSize(128, 64);
    this.spikerow24.mirror = true;
    setRotation(this.spikerow24, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow24);
    this.spikerow25 = new ModelRenderer(this, 0, 0);
    this.spikerow25.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.spikerow25.setRotationPoint(2.5F, -13.0F, -1.5F);
    this.spikerow25.setTextureSize(128, 64);
    this.spikerow25.mirror = true;
    setRotation(this.spikerow25, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow25);
    this.spikerow26 = new ModelRenderer(this, 0, 0);
    this.spikerow26.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.spikerow26.setRotationPoint(2.5F, -13.0F, 0.5F);
    this.spikerow26.setTextureSize(128, 64);
    this.spikerow26.mirror = true;
    setRotation(this.spikerow26, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow26);
    this.spikerow27 = new ModelRenderer(this, 0, 0);
    this.spikerow27.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.spikerow27.setRotationPoint(2.5F, -13.0F, 2.5F);
    this.spikerow27.setTextureSize(128, 64);
    this.spikerow27.mirror = true;
    setRotation(this.spikerow27, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow27);
    this.spikerow28 = new ModelRenderer(this, 0, 0);
    this.spikerow28.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.spikerow28.setRotationPoint(0.5F, -13.0F, 2.5F);
    this.spikerow28.setTextureSize(128, 64);
    this.spikerow28.mirror = true;
    setRotation(this.spikerow28, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow28);
    this.spikerow29 = new ModelRenderer(this, 0, 0);
    this.spikerow29.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.spikerow29.setRotationPoint(-1.5F, -13.0F, 2.5F);
    this.spikerow29.setTextureSize(128, 64);
    this.spikerow29.mirror = true;
    setRotation(this.spikerow29, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow29);
    this.spikerow210 = new ModelRenderer(this, 0, 0);
    this.spikerow210.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.spikerow210.setRotationPoint(-3.5F, -13.0F, 2.5F);
    this.spikerow210.setTextureSize(128, 64);
    this.spikerow210.mirror = true;
    setRotation(this.spikerow210, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow210);
    this.spikerow211 = new ModelRenderer(this, 0, 0);
    this.spikerow211.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.spikerow211.setRotationPoint(-3.5F, -13.0F, 0.5F);
    this.spikerow211.setTextureSize(128, 64);
    this.spikerow211.mirror = true;
    setRotation(this.spikerow211, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow211);
    this.spikerow212 = new ModelRenderer(this, 0, 0);
    this.spikerow212.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.spikerow212.setRotationPoint(-3.5F, -13.0F, -1.5F);
    this.spikerow212.setTextureSize(128, 64);
    this.spikerow212.mirror = true;
    setRotation(this.spikerow212, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow212);
    this.spikerow31 = new ModelRenderer(this, 0, 0);
    this.spikerow31.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1);
    this.spikerow31.setRotationPoint(-2.5F, -14.0F, -2.5F);
    this.spikerow31.setTextureSize(128, 64);
    this.spikerow31.mirror = true;
    setRotation(this.spikerow31, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow31);
    this.spikerow32 = new ModelRenderer(this, 0, 0);
    this.spikerow32.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1);
    this.spikerow32.setRotationPoint(-0.5F, -14.0F, -2.5F);
    this.spikerow32.setTextureSize(128, 64);
    this.spikerow32.mirror = true;
    setRotation(this.spikerow32, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow32);
    this.spikerow33 = new ModelRenderer(this, 0, 0);
    this.spikerow33.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1);
    this.spikerow33.setRotationPoint(1.5F, -14.0F, -2.5F);
    this.spikerow33.setTextureSize(128, 64);
    this.spikerow33.mirror = true;
    setRotation(this.spikerow33, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow33);
    this.spikerow34 = new ModelRenderer(this, 0, 0);
    this.spikerow34.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1);
    this.spikerow34.setRotationPoint(1.5F, -14.0F, -0.5F);
    this.spikerow34.setTextureSize(128, 64);
    this.spikerow34.mirror = true;
    setRotation(this.spikerow34, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow34);
    this.spikerow35 = new ModelRenderer(this, 0, 0);
    this.spikerow35.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1);
    this.spikerow35.setRotationPoint(1.5F, -14.0F, 1.5F);
    this.spikerow35.setTextureSize(128, 64);
    this.spikerow35.mirror = true;
    setRotation(this.spikerow35, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow35);
    this.spikerow36 = new ModelRenderer(this, 0, 0);
    this.spikerow36.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1);
    this.spikerow36.setRotationPoint(-0.5F, -14.0F, 1.5F);
    this.spikerow36.setTextureSize(128, 64);
    this.spikerow36.mirror = true;
    setRotation(this.spikerow36, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow36);
    this.spikerow37 = new ModelRenderer(this, 0, 0);
    this.spikerow37.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1);
    this.spikerow37.setRotationPoint(-2.5F, -14.0F, 1.5F);
    this.spikerow37.setTextureSize(128, 64);
    this.spikerow37.mirror = true;
    setRotation(this.spikerow37, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow37);
    this.spikerow38 = new ModelRenderer(this, 0, 0);
    this.spikerow38.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1);
    this.spikerow38.setRotationPoint(-2.5F, -14.0F, -0.5F);
    this.spikerow38.setTextureSize(128, 64);
    this.spikerow38.mirror = true;
    setRotation(this.spikerow38, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow38);
    this.spikerow41 = new ModelRenderer(this, 0, 0);
    this.spikerow41.addBox(0.0F, 0.0F, 0.0F, 1, 5, 1);
    this.spikerow41.setRotationPoint(-1.5F, -15.0F, -1.5F);
    this.spikerow41.setTextureSize(128, 64);
    this.spikerow41.mirror = true;
    setRotation(this.spikerow41, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow41);
    this.spikerow42 = new ModelRenderer(this, 0, 0);
    this.spikerow42.addBox(0.0F, 0.0F, 0.0F, 1, 5, 1);
    this.spikerow42.setRotationPoint(0.5F, -15.0F, -1.5F);
    this.spikerow42.setTextureSize(128, 64);
    this.spikerow42.mirror = true;
    setRotation(this.spikerow42, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow42);
    this.spikerow43 = new ModelRenderer(this, 0, 0);
    this.spikerow43.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1);
    this.spikerow43.setRotationPoint(-1.5F, -15.0F, 0.5F);
    this.spikerow43.setTextureSize(128, 64);
    this.spikerow43.mirror = true;
    setRotation(this.spikerow43, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow43);
    this.spikerow44 = new ModelRenderer(this, 0, 0);
    this.spikerow44.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1);
    this.spikerow44.setRotationPoint(0.5F, -15.0F, 0.5F);
    this.spikerow44.setTextureSize(128, 64);
    this.spikerow44.mirror = true;
    setRotation(this.spikerow44, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.spikerow44);
    this.tbase1 = new ModelRenderer(this, 30, 0);
    this.tbase1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.tbase1.setRotationPoint(-4.5F, -1.0F, -5.5F);
    this.tbase1.setTextureSize(128, 64);
    this.tbase1.mirror = true;
    setRotation(this.tbase1, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.tbase1);
    this.tbase2 = new ModelRenderer(this, 30, 0);
    this.tbase2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.tbase2.setRotationPoint(-2.5F, -1.0F, -5.5F);
    this.tbase2.setTextureSize(128, 64);
    this.tbase2.mirror = true;
    setRotation(this.tbase2, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.tbase2);
    this.tbase3 = new ModelRenderer(this, 30, 0);
    this.tbase3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.tbase3.setRotationPoint(-0.5F, -1.0F, -5.5F);
    this.tbase3.setTextureSize(128, 64);
    this.tbase3.mirror = true;
    setRotation(this.tbase3, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.tbase3);
    this.tbase4 = new ModelRenderer(this, 30, 0);
    this.tbase4.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.tbase4.setRotationPoint(1.5F, -1.0F, -5.5F);
    this.tbase4.setTextureSize(128, 64);
    this.tbase4.mirror = true;
    setRotation(this.tbase4, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.tbase4);
    this.tbase5 = new ModelRenderer(this, 30, 0);
    this.tbase5.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.tbase5.setRotationPoint(3.5F, -1.0F, -5.5F);
    this.tbase5.setTextureSize(128, 64);
    this.tbase5.mirror = true;
    setRotation(this.tbase5, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.tbase5);
    this.tentacle1 = new ModelRenderer(this, 30, 0);
    this.tentacle1.addBox(0.0F, 0.0F, 0.0F, 1, 5, 1);
    this.tentacle1.setRotationPoint(0.0F, 0.0F, -1.0F);
    this.tentacle1.setTextureSize(128, 64);
    this.tentacle1.mirror = true;
    setRotation(this.tentacle1, 0.0F, 0.0F, 0.0F);
    this.tbase1.addChild(this.tentacle1);
    this.tentacle2 = new ModelRenderer(this, 30, 0);
    this.tentacle2.addBox(0.0F, 0.0F, 0.0F, 1, 5, 1);
    this.tentacle2.setRotationPoint(0.0F, 0.0F, -1.0F);
    this.tentacle2.setTextureSize(128, 64);
    this.tentacle2.mirror = true;
    setRotation(this.tentacle2, 0.0F, 0.0F, 0.0F);
    this.tbase2.addChild(this.tentacle2);
    this.tentacle3 = new ModelRenderer(this, 30, 0);
    this.tentacle3.addBox(0.0F, 0.0F, 0.0F, 1, 5, 1);
    this.tentacle3.setRotationPoint(0.0F, 0.0F, -1.0F);
    this.tentacle3.setTextureSize(128, 64);
    this.tentacle3.mirror = true;
    setRotation(this.tentacle3, 0.0F, 0.0F, 0.0F);
    this.tbase3.addChild(this.tentacle3);
    this.tentacle4 = new ModelRenderer(this, 30, 0);
    this.tentacle4.addBox(0.0F, 0.0F, 0.0F, 1, 5, 1);
    this.tentacle4.setRotationPoint(0.0F, 0.0F, -1.0F);
    this.tentacle4.setTextureSize(128, 64);
    this.tentacle4.mirror = true;
    setRotation(this.tentacle4, 0.0F, 0.0F, 0.0F);
    this.tbase4.addChild(this.tentacle4);
    this.tentacle5 = new ModelRenderer(this, 30, 0);
    this.tentacle5.addBox(0.0F, 0.0F, 0.0F, 1, 5, 1);
    this.tentacle5.setRotationPoint(0.0F, 0.0F, -1.0F);
    this.tentacle5.setTextureSize(128, 64);
    this.tentacle5.mirror = true;
    setRotation(this.tentacle5, 0.0F, 0.0F, 0.0F);
    this.tbase5.addChild(this.tentacle5);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.head.render(f5);
    this.leftshoulder.render(f5);
    this.leftarm1.render(f5);
    this.rightshoulder.render(f5);
    this.rightarm1.render(f5);
    this.body.render(f5);
    this.core.render(f5);
    this.leftleg.render(f5);
    this.rightleg.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z) {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity par7Entity) {
    EntityTameBase entity = (EntityTameBase)par7Entity;
    this.head.rotateAngleY = f3 / 57.295776F;
    this.head.rotateAngleX = f4 / 57.295776F;
    this.tentacle1.offsetX = this.tentacle1.offsetY = this.tentacle1.offsetZ = 0.0F;
    float f6 = 0.01F * (entity.getEntityId() % 10);
    this.tentacle1.rotateAngleX = MathHelper.sin(entity.ticksExisted * f6) * 4.5F * 3.1415927F / 180.0F;
    this.tentacle1.rotateAngleY = 0.0F;
    this.tentacle1.rotateAngleZ = MathHelper.cos(entity.ticksExisted * f6) * 2.5F * 3.1415927F / 180.0F;
    float f7 = 0.02F * (entity.getEntityId() % 10);
    this.tentacle2.offsetX = this.tentacle2.offsetY = this.tentacle2.offsetZ = 0.0F;
    this.tentacle2.rotateAngleX = MathHelper.sin(entity.ticksExisted * f7) * 4.5F * 3.1415927F / 180.0F;
    this.tentacle2.rotateAngleY = 0.0F;
    this.tentacle2.rotateAngleZ = MathHelper.cos(entity.ticksExisted * f7) * 2.5F * 3.1415927F / 180.0F;
    float f8 = 0.03F * (entity.getEntityId() % 10);
    this.tentacle3.offsetX = this.tentacle3.offsetY = this.tentacle3.offsetZ = 0.0F;
    this.tentacle3.rotateAngleX = MathHelper.sin(entity.ticksExisted * f8) * 4.5F * 3.1415927F / 180.0F;
    this.tentacle3.rotateAngleY = 0.0F;
    this.tentacle3.rotateAngleZ = MathHelper.cos(entity.ticksExisted * f8) * 2.5F * 3.1415927F / 180.0F;
    float f9 = 0.04F * (entity.getEntityId() % 10);
    this.tentacle4.offsetX = this.tentacle4.offsetY = this.tentacle4.offsetZ = 0.0F;
    this.tentacle4.rotateAngleX = MathHelper.sin(entity.ticksExisted * f9) * 4.5F * 3.1415927F / 180.0F;
    this.tentacle4.rotateAngleY = 0.0F;
    this.tentacle4.rotateAngleZ = MathHelper.cos(entity.ticksExisted * f9) * 2.5F * 3.1415927F / 180.0F;
    float f10 = 0.04F * (entity.getEntityId() % 10);
    this.tentacle5.offsetX = this.tentacle5.offsetY = this.tentacle5.offsetZ = 0.0F;
    this.tentacle5.rotateAngleX = MathHelper.sin(entity.ticksExisted * f10) * 4.5F * 3.1415927F / 180.0F;
    this.tentacle5.rotateAngleY = 0.0F;
    this.tentacle5.rotateAngleZ = MathHelper.cos(entity.ticksExisted * f10) * 2.5F * 3.1415927F / 180.0F;
    this.rightarm1.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.leftarm1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
    this.rightarm1.rotateAngleZ = 0.0F;
    this.leftarm1.rotateAngleZ = 0.0F;
    this.rightleg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
    this.rightleg.rotateAngleY = 0.0F;
    this.leftleg.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1;
    this.leftleg.rotateAngleY = 0.0F;
    if (this.isRiding) {
      this.rightarm1.rotateAngleX += -0.62831855F;
      this.leftarm1.rotateAngleX += -0.62831855F;
      this.leftleg.rotateAngleX = -1.2566371F;
      this.rightleg.rotateAngleX = -1.2566371F;
      this.leftleg.rotateAngleY = 0.31415927F;
      this.rightleg.rotateAngleY = -0.31415927F;
    } 
    if (!entity.getCurrentBook().isEmpty()) {
      this.rightarm1.rotateAngleY = (-entity.bookSpread + 1.125F) * 0.5F;
      this.leftarm1.rotateAngleY = (entity.bookSpread - 1.125F) * 0.5F;
      this.rightarm1.rotateAngleZ = 0.0F;
      this.leftarm1.rotateAngleZ = 0.0F;
      this.rightarm1.rotateAngleX = -1.0F + 0.1F + MathHelper.sin(entity.ticksExisted * 0.1F) * 0.01F;
      this.leftarm1.rotateAngleX = -1.0F + 0.1F + MathHelper.sin(entity.ticksExisted * 0.1F) * 0.01F;
    } 
  }
}
