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
    this.field_78090_t = 128;
    this.field_78089_u = 64;
    this.head = new ModelRenderer(this, 0, 0);
    this.head.func_78789_a(-4.5F, -10.0F, -4.5F, 9, 10, 9);
    this.head.func_78793_a(0.0F, -22.0F, 0.0F);
    this.head.func_78787_b(128, 64);
    this.head.field_78809_i = true;
    setRotation(this.head, 0.0F, 0.0F, 0.0F);
    this.leftshoulder = new ModelRenderer(this, 72, 0);
    this.leftshoulder.func_78789_a(0.0F, 0.0F, 0.0F, 8, 5, 7);
    this.leftshoulder.func_78793_a(5.5F, -22.0F, -3.5F);
    this.leftshoulder.func_78787_b(128, 64);
    this.leftshoulder.field_78809_i = true;
    setRotation(this.leftshoulder, 0.0F, 0.0F, 0.185895F);
    this.leftarm1 = new ModelRenderer(this, 72, 12);
    this.leftarm1.func_78789_a(-5.0F, -1.0F, -2.5F, 5, 12, 5);
    this.leftarm1.func_78793_a(-6.5F, -19.0F, 0.0F);
    this.leftarm1.func_78787_b(128, 64);
    this.leftarm1.field_78809_i = true;
    setRotation(this.leftarm1, 0.0F, 0.0F, 0.0F);
    this.leftarm2 = new ModelRenderer(this, 72, 12);
    this.leftarm2.func_78789_a(-5.0F, 8.0F, 3.0F, 5, 12, 5);
    this.leftarm2.func_78793_a(0.0F, 0.0F, 0.0F);
    this.leftarm2.func_78787_b(128, 64);
    this.leftarm2.field_78809_i = true;
    setRotation(this.leftarm2, -0.5576792F, 0.0F, 0.0F);
    this.leftarm1.func_78792_a(this.leftarm2);
    this.rightshoulder = new ModelRenderer(this, 72, 0);
    this.rightshoulder.func_78789_a(-8.0F, 0.0F, 0.0F, 8, 5, 7);
    this.rightshoulder.func_78793_a(-5.5F, -22.0F, -3.5F);
    this.rightshoulder.func_78787_b(128, 64);
    this.rightshoulder.field_78809_i = true;
    setRotation(this.rightshoulder, 0.0F, 0.0F, -0.1858931F);
    this.rightarm1 = new ModelRenderer(this, 72, 12);
    this.rightarm1.func_78789_a(-0.5F, -1.0F, -2.5F, 5, 12, 5);
    this.rightarm1.func_78793_a(7.0F, -19.0F, 0.0F);
    this.rightarm1.func_78787_b(128, 64);
    this.rightarm1.field_78809_i = true;
    setRotation(this.rightarm1, 0.0F, 0.0F, 0.0F);
    this.rightarm2 = new ModelRenderer(this, 72, 12);
    this.rightarm2.func_78789_a(-0.5F, 8.0F, 3.0F, 5, 12, 5);
    this.rightarm2.func_78793_a(0.0F, 0.0F, 0.0F);
    this.rightarm2.func_78787_b(128, 64);
    this.rightarm2.field_78809_i = true;
    setRotation(this.rightarm2, -0.5576851F, 0.0F, 0.0F);
    this.rightarm1.func_78792_a(this.rightarm2);
    this.body = new ModelRenderer(this, 36, 0);
    this.body.func_78789_a(0.0F, 0.0F, 0.0F, 11, 24, 7);
    this.body.func_78793_a(-5.5F, -22.0F, -3.5F);
    this.body.func_78787_b(128, 64);
    this.body.field_78809_i = true;
    setRotation(this.body, 0.0F, 0.0F, 0.0F);
    this.core = new ModelRenderer(this, 0, 20);
    this.core.func_78789_a(0.0F, 0.0F, 0.0F, 5, 5, 1);
    this.core.func_78793_a(-2.5F, -15.0F, -4.5F);
    this.core.func_78787_b(128, 64);
    this.core.field_78809_i = true;
    setRotation(this.core, 0.0F, 0.0F, 0.0F);
    this.leftleg = new ModelRenderer(this, 102, 0);
    this.leftleg.func_78789_a(-2.5F, 0.0F, -3.5F, 5, 22, 7);
    this.leftleg.func_78793_a(-3.0F, 2.0F, 0.0F);
    this.leftleg.func_78787_b(128, 64);
    this.leftleg.field_78809_i = true;
    setRotation(this.leftleg, 0.0F, 0.0F, 0.0F);
    this.rightleg = new ModelRenderer(this, 102, 0);
    this.rightleg.func_78789_a(-2.5F, 0.0F, -3.5F, 5, 22, 7);
    this.rightleg.func_78793_a(3.0F, 2.0F, 0.0F);
    this.rightleg.func_78787_b(128, 64);
    this.rightleg.field_78809_i = true;
    setRotation(this.rightleg, 0.0F, 0.0F, 0.0F);
    this.spikerow11 = new ModelRenderer(this, 0, 0);
    this.spikerow11.func_78789_a(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.spikerow11.func_78793_a(-4.5F, -12.0F, -4.5F);
    this.spikerow11.func_78787_b(128, 64);
    this.spikerow11.field_78809_i = true;
    setRotation(this.spikerow11, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow11);
    this.spikerow12 = new ModelRenderer(this, 0, 0);
    this.spikerow12.func_78789_a(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.spikerow12.func_78793_a(-2.5F, -12.0F, -4.5F);
    this.spikerow12.func_78787_b(128, 64);
    this.spikerow12.field_78809_i = true;
    setRotation(this.spikerow12, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow12);
    this.spikerow13 = new ModelRenderer(this, 0, 0);
    this.spikerow13.func_78789_a(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.spikerow13.func_78793_a(-0.5F, -12.0F, -4.5F);
    this.spikerow13.func_78787_b(128, 64);
    this.spikerow13.field_78809_i = true;
    setRotation(this.spikerow13, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow13);
    this.spikerow14 = new ModelRenderer(this, 0, 0);
    this.spikerow14.func_78789_a(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.spikerow14.func_78793_a(1.5F, -12.0F, -4.5F);
    this.spikerow14.func_78787_b(128, 64);
    this.spikerow14.field_78809_i = true;
    setRotation(this.spikerow14, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow14);
    this.spikerow15 = new ModelRenderer(this, 0, 0);
    this.spikerow15.func_78789_a(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.spikerow15.func_78793_a(3.5F, -12.0F, -4.5F);
    this.spikerow15.func_78787_b(128, 64);
    this.spikerow15.field_78809_i = true;
    setRotation(this.spikerow15, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow15);
    this.spikerow16 = new ModelRenderer(this, 0, 0);
    this.spikerow16.func_78789_a(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.spikerow16.func_78793_a(3.5F, -12.0F, -2.5F);
    this.spikerow16.func_78787_b(128, 64);
    this.spikerow16.field_78809_i = true;
    setRotation(this.spikerow16, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow16);
    this.spikerow17 = new ModelRenderer(this, 0, 0);
    this.spikerow17.func_78789_a(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.spikerow17.func_78793_a(3.5F, -12.0F, -0.5F);
    this.spikerow17.func_78787_b(128, 64);
    this.spikerow17.field_78809_i = true;
    setRotation(this.spikerow17, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow17);
    this.spikerow18 = new ModelRenderer(this, 0, 0);
    this.spikerow18.func_78789_a(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.spikerow18.func_78793_a(3.5F, -12.0F, 1.5F);
    this.spikerow18.func_78787_b(128, 64);
    this.spikerow18.field_78809_i = true;
    setRotation(this.spikerow18, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow18);
    this.spikerow19 = new ModelRenderer(this, 0, 0);
    this.spikerow19.func_78789_a(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.spikerow19.func_78793_a(3.5F, -12.0F, 3.5F);
    this.spikerow19.func_78787_b(128, 64);
    this.spikerow19.field_78809_i = true;
    setRotation(this.spikerow19, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow19);
    this.spikerow110 = new ModelRenderer(this, 0, 0);
    this.spikerow110.func_78789_a(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.spikerow110.func_78793_a(1.5F, -12.0F, 3.5F);
    this.spikerow110.func_78787_b(128, 64);
    this.spikerow110.field_78809_i = true;
    setRotation(this.spikerow110, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow110);
    this.spikerow111 = new ModelRenderer(this, 0, 0);
    this.spikerow111.func_78789_a(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.spikerow111.func_78793_a(-0.5F, -12.0F, 3.5F);
    this.spikerow111.func_78787_b(128, 64);
    this.spikerow111.field_78809_i = true;
    setRotation(this.spikerow111, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow111);
    this.spikerow112 = new ModelRenderer(this, 0, 0);
    this.spikerow112.func_78789_a(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.spikerow112.func_78793_a(-2.5F, -12.0F, 3.5F);
    this.spikerow112.func_78787_b(128, 64);
    this.spikerow112.field_78809_i = true;
    setRotation(this.spikerow112, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow112);
    this.spikerow113 = new ModelRenderer(this, 0, 0);
    this.spikerow113.func_78789_a(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.spikerow113.func_78793_a(-4.5F, -12.0F, 3.5F);
    this.spikerow113.func_78787_b(128, 64);
    this.spikerow113.field_78809_i = true;
    setRotation(this.spikerow113, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow113);
    this.spikerow114 = new ModelRenderer(this, 0, 0);
    this.spikerow114.func_78789_a(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.spikerow114.func_78793_a(-4.5F, -12.0F, 1.5F);
    this.spikerow114.func_78787_b(128, 64);
    this.spikerow114.field_78809_i = true;
    setRotation(this.spikerow114, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow114);
    this.spikerow115 = new ModelRenderer(this, 0, 0);
    this.spikerow115.func_78789_a(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.spikerow115.func_78793_a(-4.5F, -12.0F, -0.5F);
    this.spikerow115.func_78787_b(128, 64);
    this.spikerow115.field_78809_i = true;
    setRotation(this.spikerow115, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow115);
    this.spikerow116 = new ModelRenderer(this, 0, 0);
    this.spikerow116.func_78789_a(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.spikerow116.func_78793_a(-4.5F, -12.0F, -2.5F);
    this.spikerow116.func_78787_b(128, 64);
    this.spikerow116.field_78809_i = true;
    setRotation(this.spikerow116, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow116);
    this.spikerow21 = new ModelRenderer(this, 0, 0);
    this.spikerow21.func_78789_a(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.spikerow21.func_78793_a(-3.5F, -13.0F, -3.5F);
    this.spikerow21.func_78787_b(128, 64);
    this.spikerow21.field_78809_i = true;
    setRotation(this.spikerow21, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow21);
    this.spikerow22 = new ModelRenderer(this, 0, 0);
    this.spikerow22.func_78789_a(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.spikerow22.func_78793_a(-1.5F, -13.0F, -3.5F);
    this.spikerow22.func_78787_b(128, 64);
    this.spikerow22.field_78809_i = true;
    setRotation(this.spikerow22, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow22);
    this.spikerow23 = new ModelRenderer(this, 0, 0);
    this.spikerow23.func_78789_a(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.spikerow23.func_78793_a(0.5F, -13.0F, -3.5F);
    this.spikerow23.func_78787_b(128, 64);
    this.spikerow23.field_78809_i = true;
    setRotation(this.spikerow23, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow23);
    this.spikerow24 = new ModelRenderer(this, 0, 0);
    this.spikerow24.func_78789_a(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.spikerow24.func_78793_a(2.5F, -13.0F, -3.5F);
    this.spikerow24.func_78787_b(128, 64);
    this.spikerow24.field_78809_i = true;
    setRotation(this.spikerow24, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow24);
    this.spikerow25 = new ModelRenderer(this, 0, 0);
    this.spikerow25.func_78789_a(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.spikerow25.func_78793_a(2.5F, -13.0F, -1.5F);
    this.spikerow25.func_78787_b(128, 64);
    this.spikerow25.field_78809_i = true;
    setRotation(this.spikerow25, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow25);
    this.spikerow26 = new ModelRenderer(this, 0, 0);
    this.spikerow26.func_78789_a(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.spikerow26.func_78793_a(2.5F, -13.0F, 0.5F);
    this.spikerow26.func_78787_b(128, 64);
    this.spikerow26.field_78809_i = true;
    setRotation(this.spikerow26, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow26);
    this.spikerow27 = new ModelRenderer(this, 0, 0);
    this.spikerow27.func_78789_a(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.spikerow27.func_78793_a(2.5F, -13.0F, 2.5F);
    this.spikerow27.func_78787_b(128, 64);
    this.spikerow27.field_78809_i = true;
    setRotation(this.spikerow27, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow27);
    this.spikerow28 = new ModelRenderer(this, 0, 0);
    this.spikerow28.func_78789_a(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.spikerow28.func_78793_a(0.5F, -13.0F, 2.5F);
    this.spikerow28.func_78787_b(128, 64);
    this.spikerow28.field_78809_i = true;
    setRotation(this.spikerow28, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow28);
    this.spikerow29 = new ModelRenderer(this, 0, 0);
    this.spikerow29.func_78789_a(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.spikerow29.func_78793_a(-1.5F, -13.0F, 2.5F);
    this.spikerow29.func_78787_b(128, 64);
    this.spikerow29.field_78809_i = true;
    setRotation(this.spikerow29, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow29);
    this.spikerow210 = new ModelRenderer(this, 0, 0);
    this.spikerow210.func_78789_a(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.spikerow210.func_78793_a(-3.5F, -13.0F, 2.5F);
    this.spikerow210.func_78787_b(128, 64);
    this.spikerow210.field_78809_i = true;
    setRotation(this.spikerow210, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow210);
    this.spikerow211 = new ModelRenderer(this, 0, 0);
    this.spikerow211.func_78789_a(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.spikerow211.func_78793_a(-3.5F, -13.0F, 0.5F);
    this.spikerow211.func_78787_b(128, 64);
    this.spikerow211.field_78809_i = true;
    setRotation(this.spikerow211, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow211);
    this.spikerow212 = new ModelRenderer(this, 0, 0);
    this.spikerow212.func_78789_a(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.spikerow212.func_78793_a(-3.5F, -13.0F, -1.5F);
    this.spikerow212.func_78787_b(128, 64);
    this.spikerow212.field_78809_i = true;
    setRotation(this.spikerow212, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow212);
    this.spikerow31 = new ModelRenderer(this, 0, 0);
    this.spikerow31.func_78789_a(0.0F, 0.0F, 0.0F, 1, 4, 1);
    this.spikerow31.func_78793_a(-2.5F, -14.0F, -2.5F);
    this.spikerow31.func_78787_b(128, 64);
    this.spikerow31.field_78809_i = true;
    setRotation(this.spikerow31, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow31);
    this.spikerow32 = new ModelRenderer(this, 0, 0);
    this.spikerow32.func_78789_a(0.0F, 0.0F, 0.0F, 1, 4, 1);
    this.spikerow32.func_78793_a(-0.5F, -14.0F, -2.5F);
    this.spikerow32.func_78787_b(128, 64);
    this.spikerow32.field_78809_i = true;
    setRotation(this.spikerow32, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow32);
    this.spikerow33 = new ModelRenderer(this, 0, 0);
    this.spikerow33.func_78789_a(0.0F, 0.0F, 0.0F, 1, 4, 1);
    this.spikerow33.func_78793_a(1.5F, -14.0F, -2.5F);
    this.spikerow33.func_78787_b(128, 64);
    this.spikerow33.field_78809_i = true;
    setRotation(this.spikerow33, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow33);
    this.spikerow34 = new ModelRenderer(this, 0, 0);
    this.spikerow34.func_78789_a(0.0F, 0.0F, 0.0F, 1, 4, 1);
    this.spikerow34.func_78793_a(1.5F, -14.0F, -0.5F);
    this.spikerow34.func_78787_b(128, 64);
    this.spikerow34.field_78809_i = true;
    setRotation(this.spikerow34, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow34);
    this.spikerow35 = new ModelRenderer(this, 0, 0);
    this.spikerow35.func_78789_a(0.0F, 0.0F, 0.0F, 1, 4, 1);
    this.spikerow35.func_78793_a(1.5F, -14.0F, 1.5F);
    this.spikerow35.func_78787_b(128, 64);
    this.spikerow35.field_78809_i = true;
    setRotation(this.spikerow35, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow35);
    this.spikerow36 = new ModelRenderer(this, 0, 0);
    this.spikerow36.func_78789_a(0.0F, 0.0F, 0.0F, 1, 4, 1);
    this.spikerow36.func_78793_a(-0.5F, -14.0F, 1.5F);
    this.spikerow36.func_78787_b(128, 64);
    this.spikerow36.field_78809_i = true;
    setRotation(this.spikerow36, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow36);
    this.spikerow37 = new ModelRenderer(this, 0, 0);
    this.spikerow37.func_78789_a(0.0F, 0.0F, 0.0F, 1, 4, 1);
    this.spikerow37.func_78793_a(-2.5F, -14.0F, 1.5F);
    this.spikerow37.func_78787_b(128, 64);
    this.spikerow37.field_78809_i = true;
    setRotation(this.spikerow37, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow37);
    this.spikerow38 = new ModelRenderer(this, 0, 0);
    this.spikerow38.func_78789_a(0.0F, 0.0F, 0.0F, 1, 4, 1);
    this.spikerow38.func_78793_a(-2.5F, -14.0F, -0.5F);
    this.spikerow38.func_78787_b(128, 64);
    this.spikerow38.field_78809_i = true;
    setRotation(this.spikerow38, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow38);
    this.spikerow41 = new ModelRenderer(this, 0, 0);
    this.spikerow41.func_78789_a(0.0F, 0.0F, 0.0F, 1, 5, 1);
    this.spikerow41.func_78793_a(-1.5F, -15.0F, -1.5F);
    this.spikerow41.func_78787_b(128, 64);
    this.spikerow41.field_78809_i = true;
    setRotation(this.spikerow41, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow41);
    this.spikerow42 = new ModelRenderer(this, 0, 0);
    this.spikerow42.func_78789_a(0.0F, 0.0F, 0.0F, 1, 5, 1);
    this.spikerow42.func_78793_a(0.5F, -15.0F, -1.5F);
    this.spikerow42.func_78787_b(128, 64);
    this.spikerow42.field_78809_i = true;
    setRotation(this.spikerow42, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow42);
    this.spikerow43 = new ModelRenderer(this, 0, 0);
    this.spikerow43.func_78789_a(0.0F, 0.0F, 0.0F, 1, 4, 1);
    this.spikerow43.func_78793_a(-1.5F, -15.0F, 0.5F);
    this.spikerow43.func_78787_b(128, 64);
    this.spikerow43.field_78809_i = true;
    setRotation(this.spikerow43, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow43);
    this.spikerow44 = new ModelRenderer(this, 0, 0);
    this.spikerow44.func_78789_a(0.0F, 0.0F, 0.0F, 1, 4, 1);
    this.spikerow44.func_78793_a(0.5F, -15.0F, 0.5F);
    this.spikerow44.func_78787_b(128, 64);
    this.spikerow44.field_78809_i = true;
    setRotation(this.spikerow44, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.spikerow44);
    this.tbase1 = new ModelRenderer(this, 30, 0);
    this.tbase1.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.tbase1.func_78793_a(-4.5F, -1.0F, -5.5F);
    this.tbase1.func_78787_b(128, 64);
    this.tbase1.field_78809_i = true;
    setRotation(this.tbase1, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.tbase1);
    this.tbase2 = new ModelRenderer(this, 30, 0);
    this.tbase2.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.tbase2.func_78793_a(-2.5F, -1.0F, -5.5F);
    this.tbase2.func_78787_b(128, 64);
    this.tbase2.field_78809_i = true;
    setRotation(this.tbase2, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.tbase2);
    this.tbase3 = new ModelRenderer(this, 30, 0);
    this.tbase3.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.tbase3.func_78793_a(-0.5F, -1.0F, -5.5F);
    this.tbase3.func_78787_b(128, 64);
    this.tbase3.field_78809_i = true;
    setRotation(this.tbase3, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.tbase3);
    this.tbase4 = new ModelRenderer(this, 30, 0);
    this.tbase4.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.tbase4.func_78793_a(1.5F, -1.0F, -5.5F);
    this.tbase4.func_78787_b(128, 64);
    this.tbase4.field_78809_i = true;
    setRotation(this.tbase4, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.tbase4);
    this.tbase5 = new ModelRenderer(this, 30, 0);
    this.tbase5.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.tbase5.func_78793_a(3.5F, -1.0F, -5.5F);
    this.tbase5.func_78787_b(128, 64);
    this.tbase5.field_78809_i = true;
    setRotation(this.tbase5, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.tbase5);
    this.tentacle1 = new ModelRenderer(this, 30, 0);
    this.tentacle1.func_78789_a(0.0F, 0.0F, 0.0F, 1, 5, 1);
    this.tentacle1.func_78793_a(0.0F, 0.0F, -1.0F);
    this.tentacle1.func_78787_b(128, 64);
    this.tentacle1.field_78809_i = true;
    setRotation(this.tentacle1, 0.0F, 0.0F, 0.0F);
    this.tbase1.func_78792_a(this.tentacle1);
    this.tentacle2 = new ModelRenderer(this, 30, 0);
    this.tentacle2.func_78789_a(0.0F, 0.0F, 0.0F, 1, 5, 1);
    this.tentacle2.func_78793_a(0.0F, 0.0F, -1.0F);
    this.tentacle2.func_78787_b(128, 64);
    this.tentacle2.field_78809_i = true;
    setRotation(this.tentacle2, 0.0F, 0.0F, 0.0F);
    this.tbase2.func_78792_a(this.tentacle2);
    this.tentacle3 = new ModelRenderer(this, 30, 0);
    this.tentacle3.func_78789_a(0.0F, 0.0F, 0.0F, 1, 5, 1);
    this.tentacle3.func_78793_a(0.0F, 0.0F, -1.0F);
    this.tentacle3.func_78787_b(128, 64);
    this.tentacle3.field_78809_i = true;
    setRotation(this.tentacle3, 0.0F, 0.0F, 0.0F);
    this.tbase3.func_78792_a(this.tentacle3);
    this.tentacle4 = new ModelRenderer(this, 30, 0);
    this.tentacle4.func_78789_a(0.0F, 0.0F, 0.0F, 1, 5, 1);
    this.tentacle4.func_78793_a(0.0F, 0.0F, -1.0F);
    this.tentacle4.func_78787_b(128, 64);
    this.tentacle4.field_78809_i = true;
    setRotation(this.tentacle4, 0.0F, 0.0F, 0.0F);
    this.tbase4.func_78792_a(this.tentacle4);
    this.tentacle5 = new ModelRenderer(this, 30, 0);
    this.tentacle5.func_78789_a(0.0F, 0.0F, 0.0F, 1, 5, 1);
    this.tentacle5.func_78793_a(0.0F, 0.0F, -1.0F);
    this.tentacle5.func_78787_b(128, 64);
    this.tentacle5.field_78809_i = true;
    setRotation(this.tentacle5, 0.0F, 0.0F, 0.0F);
    this.tbase5.func_78792_a(this.tentacle5);
  }
  
  public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    super.func_78088_a(entity, f, f1, f2, f3, f4, f5);
    func_78087_a(f, f1, f2, f3, f4, f5, entity);
    this.head.func_78785_a(f5);
    this.leftshoulder.func_78785_a(f5);
    this.leftarm1.func_78785_a(f5);
    this.rightshoulder.func_78785_a(f5);
    this.rightarm1.func_78785_a(f5);
    this.body.func_78785_a(f5);
    this.core.func_78785_a(f5);
    this.leftleg.func_78785_a(f5);
    this.rightleg.func_78785_a(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z) {
    model.field_78795_f = x;
    model.field_78796_g = y;
    model.field_78808_h = z;
  }
  
  public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity par7Entity) {
    EntityTameBase entity = (EntityTameBase)par7Entity;
    this.head.field_78796_g = f3 / 57.295776F;
    this.head.field_78795_f = f4 / 57.295776F;
    this.tentacle1.field_82906_o = this.tentacle1.field_82908_p = this.tentacle1.field_82907_q = 0.0F;
    float f6 = 0.01F * (entity.func_145782_y() % 10);
    this.tentacle1.field_78795_f = MathHelper.func_76126_a(entity.field_70173_aa * f6) * 4.5F * 3.1415927F / 180.0F;
    this.tentacle1.field_78796_g = 0.0F;
    this.tentacle1.field_78808_h = MathHelper.func_76134_b(entity.field_70173_aa * f6) * 2.5F * 3.1415927F / 180.0F;
    float f7 = 0.02F * (entity.func_145782_y() % 10);
    this.tentacle2.field_82906_o = this.tentacle2.field_82908_p = this.tentacle2.field_82907_q = 0.0F;
    this.tentacle2.field_78795_f = MathHelper.func_76126_a(entity.field_70173_aa * f7) * 4.5F * 3.1415927F / 180.0F;
    this.tentacle2.field_78796_g = 0.0F;
    this.tentacle2.field_78808_h = MathHelper.func_76134_b(entity.field_70173_aa * f7) * 2.5F * 3.1415927F / 180.0F;
    float f8 = 0.03F * (entity.func_145782_y() % 10);
    this.tentacle3.field_82906_o = this.tentacle3.field_82908_p = this.tentacle3.field_82907_q = 0.0F;
    this.tentacle3.field_78795_f = MathHelper.func_76126_a(entity.field_70173_aa * f8) * 4.5F * 3.1415927F / 180.0F;
    this.tentacle3.field_78796_g = 0.0F;
    this.tentacle3.field_78808_h = MathHelper.func_76134_b(entity.field_70173_aa * f8) * 2.5F * 3.1415927F / 180.0F;
    float f9 = 0.04F * (entity.func_145782_y() % 10);
    this.tentacle4.field_82906_o = this.tentacle4.field_82908_p = this.tentacle4.field_82907_q = 0.0F;
    this.tentacle4.field_78795_f = MathHelper.func_76126_a(entity.field_70173_aa * f9) * 4.5F * 3.1415927F / 180.0F;
    this.tentacle4.field_78796_g = 0.0F;
    this.tentacle4.field_78808_h = MathHelper.func_76134_b(entity.field_70173_aa * f9) * 2.5F * 3.1415927F / 180.0F;
    float f10 = 0.04F * (entity.func_145782_y() % 10);
    this.tentacle5.field_82906_o = this.tentacle5.field_82908_p = this.tentacle5.field_82907_q = 0.0F;
    this.tentacle5.field_78795_f = MathHelper.func_76126_a(entity.field_70173_aa * f10) * 4.5F * 3.1415927F / 180.0F;
    this.tentacle5.field_78796_g = 0.0F;
    this.tentacle5.field_78808_h = MathHelper.func_76134_b(entity.field_70173_aa * f10) * 2.5F * 3.1415927F / 180.0F;
    this.rightarm1.field_78795_f = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.leftarm1.field_78795_f = MathHelper.func_76134_b(f * 0.6662F) * 2.0F * f1 * 0.5F;
    this.rightarm1.field_78808_h = 0.0F;
    this.leftarm1.field_78808_h = 0.0F;
    this.rightleg.field_78795_f = MathHelper.func_76134_b(f * 0.6662F) * 1.4F * f1;
    this.rightleg.field_78796_g = 0.0F;
    this.leftleg.field_78795_f = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 1.4F * f1;
    this.leftleg.field_78796_g = 0.0F;
    if (this.field_78093_q) {
      this.rightarm1.field_78795_f += -0.62831855F;
      this.leftarm1.field_78795_f += -0.62831855F;
      this.leftleg.field_78795_f = -1.2566371F;
      this.rightleg.field_78795_f = -1.2566371F;
      this.leftleg.field_78796_g = 0.31415927F;
      this.rightleg.field_78796_g = -0.31415927F;
    } 
    if (!entity.getCurrentBook().func_190926_b()) {
      this.rightarm1.field_78796_g = (-entity.bookSpread + 1.125F) * 0.5F;
      this.leftarm1.field_78796_g = (entity.bookSpread - 1.125F) * 0.5F;
      this.rightarm1.field_78808_h = 0.0F;
      this.leftarm1.field_78808_h = 0.0F;
      this.rightarm1.field_78795_f = -1.0F + 0.1F + MathHelper.func_76126_a(entity.field_70173_aa * 0.1F) * 0.01F;
      this.leftarm1.field_78795_f = -1.0F + 0.1F + MathHelper.func_76126_a(entity.field_70173_aa * 0.1F) * 0.01F;
    } 
  }
}
