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
    this.field_78090_t = 128;
    this.field_78089_u = 64;
    this.frontspike1 = new ModelRenderer(this, 30, 50);
    this.frontspike1.func_78789_a(0.0F, -4.0F, 0.0F, 1, 5, 1);
    this.frontspike1.func_78793_a(-10.0F, 10.0F, -9.0F);
    this.frontspike1.func_78787_b(128, 64);
    this.frontspike1.field_78809_i = true;
    setRotation(this.frontspike1, 0.6320364F, 0.0F, 0.0F);
    this.frontspike2 = new ModelRenderer(this, 30, 50);
    this.frontspike2.func_78789_a(0.0F, -4.0F, 0.0F, 1, 5, 1);
    this.frontspike2.func_78793_a(-4.0F, 10.0F, -9.0F);
    this.frontspike2.func_78787_b(128, 64);
    this.frontspike2.field_78809_i = true;
    setRotation(this.frontspike2, 0.6320364F, 0.0F, 0.0F);
    this.frontspike3 = new ModelRenderer(this, 30, 50);
    this.frontspike3.func_78789_a(0.0F, -4.0F, 0.0F, 1, 5, 1);
    this.frontspike3.func_78793_a(2.0F, 10.0F, -9.0F);
    this.frontspike3.func_78787_b(128, 64);
    this.frontspike3.field_78809_i = true;
    setRotation(this.frontspike3, 0.6320364F, 0.0F, 0.0F);
    this.frontspike4 = new ModelRenderer(this, 30, 50);
    this.frontspike4.func_78789_a(0.0F, -4.0F, 0.0F, 1, 5, 1);
    this.frontspike4.func_78793_a(8.0F, 10.0F, -9.0F);
    this.frontspike4.func_78787_b(128, 64);
    this.frontspike4.field_78809_i = true;
    setRotation(this.frontspike4, 0.6320364F, 0.0F, 0.0F);
    this.frontspike5 = new ModelRenderer(this, 30, 50);
    this.frontspike5.func_78789_a(0.0F, -4.0F, 0.0F, 1, 5, 1);
    this.frontspike5.func_78793_a(14.0F, 10.0F, -9.0F);
    this.frontspike5.func_78787_b(128, 64);
    this.frontspike5.field_78809_i = true;
    setRotation(this.frontspike5, 0.6320364F, 0.0F, 0.0F);
    this.leftneck = new ModelRenderer(this, 34, 0);
    this.leftneck.func_78789_a(-1.0F, -16.0F, -2.0F, 3, 16, 3);
    this.leftneck.func_78793_a(-8.0F, 10.0F, -1.0F);
    this.leftneck.func_78787_b(128, 64);
    this.leftneck.field_78809_i = true;
    setRotation(this.leftneck, 0.4833219F, 0.2230717F, 0.0F);
    this.middleneck = new ModelRenderer(this, 34, 0);
    this.middleneck.func_78789_a(-1.0F, -16.0F, -2.0F, 3, 16, 3);
    this.middleneck.func_78793_a(2.0F, 10.0F, -1.0F);
    this.middleneck.func_78787_b(128, 64);
    this.middleneck.field_78809_i = true;
    setRotation(this.middleneck, 0.4833219F, 0.0F, 0.0F);
    this.rightneck = new ModelRenderer(this, 34, 0);
    this.rightneck.func_78789_a(-1.0F, -16.0F, -2.0F, 3, 16, 3);
    this.rightneck.func_78793_a(12.0F, 10.0F, -1.0F);
    this.rightneck.func_78787_b(128, 64);
    this.rightneck.field_78809_i = true;
    setRotation(this.rightneck, 0.4833219F, -0.2230705F, 0.0F);
    this.body = new ModelRenderer(this, 38, 30);
    this.body.func_78789_a(0.0F, 0.0F, 0.0F, 25, 14, 20);
    this.body.func_78793_a(-10.0F, 10.0F, -9.0F);
    this.body.func_78787_b(128, 64);
    this.body.field_78809_i = true;
    setRotation(this.body, 0.0F, 0.0F, 0.0F);
    this.tentacle2base = new ModelRenderer(this, 0, 0);
    this.tentacle2base.func_78789_a(0.0F, 0.0F, 0.0F, 9, 3, 3);
    this.tentacle2base.func_78793_a(15.0F, 12.0F, -8.0F);
    this.tentacle2base.func_78787_b(128, 64);
    this.tentacle2base.field_78809_i = true;
    setRotation(this.tentacle2base, 0.0F, 0.0F, 0.0F);
    this.tentacle1 = new ModelRenderer(this, 0, 0);
    this.tentacle1.func_78789_a(-1.5F, -10.0F, -1.5F, 3, 10, 3);
    this.tentacle1.func_78793_a(-2.0F, 10.0F, 9.0F);
    this.tentacle1.func_78787_b(128, 64);
    this.tentacle1.field_78809_i = true;
    setRotation(this.tentacle1, 0.0F, 0.0F, 0.0F);
    this.tentacle2 = new ModelRenderer(this, 0, 0);
    this.tentacle2.func_78789_a(-1.5F, -9.0F, -1.5F, 3, 9, 3);
    this.tentacle2.func_78793_a(-2.0F, 1.0F, 9.0F);
    this.tentacle2.func_78787_b(128, 64);
    this.tentacle2.field_78809_i = true;
    setRotation(this.tentacle2, 0.4833219F, 0.0F, 0.0F);
    this.tentacle3 = new ModelRenderer(this, 0, 0);
    this.tentacle3.func_78789_a(-1.5F, -8.0F, -1.5F, 3, 8, 3);
    this.tentacle3.func_78793_a(-2.0F, -6.0F, 5.0F);
    this.tentacle3.func_78787_b(128, 64);
    this.tentacle3.field_78809_i = true;
    setRotation(this.tentacle3, -0.4461433F, 0.0F, 0.0F);
    this.tentacle4 = new ModelRenderer(this, 0, 0);
    this.tentacle4.func_78789_a(0.0F, -1.5F, -1.5F, 9, 3, 3);
    this.tentacle4.func_78793_a(-1.0F, 1.0F, 9.0F);
    this.tentacle4.func_78787_b(128, 64);
    this.tentacle4.field_78809_i = true;
    setRotation(this.tentacle4, 0.0F, 0.0F, -0.5205006F);
    this.tentacle5 = new ModelRenderer(this, 0, 0);
    this.tentacle5.func_78789_a(-1.5F, -1.5F, 0.0F, 3, 3, 10);
    this.tentacle5.func_78793_a(-2.0F, -12.0F, 7.0F);
    this.tentacle5.func_78787_b(128, 64);
    this.tentacle5.field_78809_i = true;
    setRotation(this.tentacle5, 0.0F, 0.0F, 0.0F);
    this.tentacle6 = new ModelRenderer(this, 0, 0);
    this.tentacle6.func_78789_a(-1.5F, -8.5F, -1.5F, 3, 9, 3);
    this.tentacle6.func_78793_a(-17.5F, 16.0F, -4.0F);
    this.tentacle6.func_78787_b(128, 64);
    this.tentacle6.field_78809_i = true;
    setRotation(this.tentacle6, 0.0F, 0.0F, -0.3346075F);
    this.tentacle7 = new ModelRenderer(this, 0, 0);
    this.tentacle7.func_78789_a(0.0F, -1.5F, -1.5F, 10, 3, 3);
    this.tentacle7.func_78793_a(27.0F, 5.0F, 3.0F);
    this.tentacle7.func_78787_b(128, 64);
    this.tentacle7.field_78809_i = true;
    setRotation(this.tentacle7, 0.0F, -0.5576792F, 0.0F);
    this.tentacle8 = new ModelRenderer(this, 0, 0);
    this.tentacle8.func_78789_a(0.0F, -2.5F, -2.5F, 13, 5, 5);
    this.tentacle8.func_78793_a(17.0F, 6.0F, 4.0F);
    this.tentacle8.func_78787_b(128, 64);
    this.tentacle8.field_78809_i = true;
    setRotation(this.tentacle8, 0.0F, 0.2602503F, -0.1487144F);
    this.tentacle9 = new ModelRenderer(this, 0, 0);
    this.tentacle9.func_78789_a(0.0F, 0.0F, 0.0F, 3, 12, 3);
    this.tentacle9.func_78793_a(8.0F, 23.0F, -4.0F);
    this.tentacle9.func_78787_b(128, 64);
    this.tentacle9.field_78809_i = true;
    setRotation(this.tentacle9, -0.4833219F, 0.0F, -0.5948578F);
    this.tentacle10 = new ModelRenderer(this, 0, 0);
    this.tentacle10.func_78789_a(-9.0F, -1.5F, -1.5F, 9, 3, 3);
    this.tentacle10.func_78793_a(-10.0F, 17.0F, -4.0F);
    this.tentacle10.func_78787_b(128, 64);
    this.tentacle10.field_78809_i = true;
    setRotation(this.tentacle10, 0.0F, 0.0F, 0.0F);
    this.tentacle11 = new ModelRenderer(this, 0, 0);
    this.tentacle11.func_78789_a(-1.5F, -1.5F, -11.0F, 3, 3, 11);
    this.tentacle11.func_78793_a(-6.0F, 19.0F, -7.0F);
    this.tentacle11.func_78787_b(128, 64);
    this.tentacle11.field_78809_i = true;
    setRotation(this.tentacle11, 0.1487144F, -0.4089647F, 0.0F);
    this.tentacle12 = new ModelRenderer(this, 0, 0);
    this.tentacle12.func_78789_a(-2.5F, -10.0F, -2.5F, 5, 10, 5);
    this.tentacle12.func_78793_a(16.0F, 8.0F, 4.0F);
    this.tentacle12.func_78787_b(128, 64);
    this.tentacle12.field_78809_i = true;
    setRotation(this.tentacle12, 0.0F, 0.0F, 0.0F);
    this.tentacle13 = new ModelRenderer(this, 0, 0);
    this.tentacle13.func_78789_a(-2.5F, -13.0F, -2.5F, 5, 8, 5);
    this.tentacle13.func_78793_a(10.0F, 18.0F, 5.0F);
    this.tentacle13.func_78787_b(128, 64);
    this.tentacle13.field_78809_i = true;
    setRotation(this.tentacle13, 0.0371786F, 0.0F, 0.5205006F);
    this.tentacle14 = new ModelRenderer(this, 0, 0);
    this.tentacle14.func_78789_a(-1.5F, -11.0F, -1.5F, 3, 11, 3);
    this.tentacle14.func_78793_a(17.0F, 0.0F, 5.0F);
    this.tentacle14.func_78787_b(128, 64);
    this.tentacle14.field_78809_i = true;
    setRotation(this.tentacle14, -0.2602503F, 0.0F, 1.07818F);
    this.tentacle15 = new ModelRenderer(this, 0, 0);
    this.tentacle15.func_78789_a(-1.5F, -1.5F, -8.0F, 3, 3, 8);
    this.tentacle15.func_78793_a(25.0F, -5.0F, 7.0F);
    this.tentacle15.func_78787_b(128, 64);
    this.tentacle15.field_78809_i = true;
    setRotation(this.tentacle15, 0.0F, -0.2974289F, 0.0F);
    this.tentacle16 = new ModelRenderer(this, 0, 0);
    this.tentacle16.func_78789_a(0.0F, 0.0F, 0.0F, 3, 10, 3);
    this.tentacle16.func_78793_a(0.0F, 22.0F, -6.0F);
    this.tentacle16.func_78787_b(128, 64);
    this.tentacle16.field_78809_i = true;
    setRotation(this.tentacle16, -0.1115358F, 0.0F, 0.5205006F);
    this.tentacle17 = new ModelRenderer(this, 0, 0);
    this.tentacle17.func_78789_a(0.0F, 0.0F, 0.0F, 3, 10, 3);
    this.tentacle17.func_78793_a(5.0F, 24.0F, 4.0F);
    this.tentacle17.func_78787_b(128, 64);
    this.tentacle17.field_78809_i = true;
    setRotation(this.tentacle17, 0.4461433F, 0.0F, 0.0F);
    this.tentacle18 = new ModelRenderer(this, 0, 0);
    this.tentacle18.func_78789_a(0.0F, 0.0F, 0.0F, 5, 15, 5);
    this.tentacle18.func_78793_a(-8.0F, 21.0F, 1.0F);
    this.tentacle18.func_78787_b(128, 64);
    this.tentacle18.field_78809_i = true;
    setRotation(this.tentacle18, -0.37179F, 0.0F, 0.37179F);
    this.tentacle19 = new ModelRenderer(this, 0, 0);
    this.tentacle19.func_78789_a(-12.0F, -1.5F, -1.5F, 12, 3, 3);
    this.tentacle19.func_78793_a(-20.0F, 17.0F, 3.0F);
    this.tentacle19.func_78787_b(128, 64);
    this.tentacle19.field_78809_i = true;
    setRotation(this.tentacle19, 0.0F, 0.8179294F, 0.3717861F);
    this.tentacle20 = new ModelRenderer(this, 0, 0);
    this.tentacle20.func_78789_a(-8.0F, 0.0F, 0.0F, 13, 5, 5);
    this.tentacle20.func_78793_a(-15.0F, 15.0F, 0.0F);
    this.tentacle20.func_78787_b(128, 64);
    this.tentacle20.field_78809_i = true;
    setRotation(this.tentacle20, 0.0F, 0.0F, 0.0F);
    this.tlib12 = new ModelRenderer(this, 0, 0);
    this.tlib12.func_78789_a(-1.0F, -1.0F, -8.0F, 3, 3, 8);
    this.tlib12.func_78793_a(22.0F, 13.0F, -8.0F);
    this.tlib12.func_78787_b(128, 64);
    this.tlib12.field_78809_i = true;
    setRotation(this.tlib12, 0.0F, 0.0F, 0.0F);
    this.tlib22 = new ModelRenderer(this, 30, 20);
    this.tlib22.func_78789_a(-1.0F, -5.0F, -1.0F, 3, 5, 3);
    this.tlib22.func_78793_a(22.0F, 14.0F, -15.5F);
    this.tlib22.func_78787_b(128, 64);
    this.tlib22.field_78809_i = true;
    setRotation(this.tlib22, 0.8922867F, 0.0F, 0.0F);
    this.tspike12 = new ModelRenderer(this, 25, 25);
    this.tspike12.func_78789_a(0.0F, -2.0F, 0.0F, 1, 2, 1);
    this.tspike12.func_78793_a(23.0F, 10.0F, -19.0F);
    this.tspike12.func_78787_b(128, 64);
    this.tspike12.field_78809_i = true;
    setRotation(this.tspike12, 0.0371786F, 0.0F, 0.5948578F);
    this.tspike22 = new ModelRenderer(this, 25, 25);
    this.tspike22.func_78789_a(-1.0F, -2.0F, 0.0F, 1, 2, 1);
    this.tspike22.func_78793_a(22.0F, 10.0F, -19.0F);
    this.tspike22.func_78787_b(128, 64);
    this.tspike22.field_78809_i = true;
    setRotation(this.tspike22, 0.0371786F, 0.0F, -0.5948606F);
    this.tspike32 = new ModelRenderer(this, 25, 25);
    this.tspike32.func_78789_a(0.0F, -2.0F, 0.0F, 1, 2, 1);
    this.tspike32.func_78793_a(23.0F, 11.5F, -19.0F);
    this.tspike32.func_78787_b(128, 64);
    this.tspike32.field_78809_i = true;
    setRotation(this.tspike32, 1.115355F, 0.0F, 0.5948578F);
    this.tspike42 = new ModelRenderer(this, 25, 25);
    this.tspike42.func_78789_a(-1.0F, -2.0F, 0.0F, 1, 2, 1);
    this.tspike42.func_78793_a(22.0F, 11.5F, -19.0F);
    this.tspike42.func_78787_b(128, 64);
    this.tspike42.field_78809_i = true;
    setRotation(this.tspike42, 1.115353F, 0.0F, -0.5948606F);
    this.tlib1 = new ModelRenderer(this, 0, 0);
    this.tlib1.func_78789_a(-1.0F, -1.0F, -8.0F, 3, 3, 8);
    this.tlib1.func_78793_a(-2.0F, 20.0F, -16.0F);
    this.tlib1.func_78787_b(128, 64);
    this.tlib1.field_78809_i = true;
    setRotation(this.tlib1, 0.0F, 0.0F, 0.0F);
    this.tlib2 = new ModelRenderer(this, 30, 20);
    this.tlib2.func_78789_a(-1.0F, -5.0F, -1.0F, 3, 5, 3);
    this.tlib2.func_78793_a(-2.0F, 21.0F, -23.5F);
    this.tlib2.func_78787_b(128, 64);
    this.tlib2.field_78809_i = true;
    setRotation(this.tlib2, 0.8922867F, 0.0F, 0.0F);
    this.tspike1 = new ModelRenderer(this, 25, 25);
    this.tspike1.func_78789_a(0.0F, -2.0F, 0.0F, 1, 2, 1);
    this.tspike1.func_78793_a(-1.0F, 17.0F, -27.0F);
    this.tspike1.func_78787_b(128, 64);
    this.tspike1.field_78809_i = true;
    setRotation(this.tspike1, 0.0371786F, 0.0F, 0.5948578F);
    this.tspike2 = new ModelRenderer(this, 25, 25);
    this.tspike2.func_78789_a(-1.0F, -2.0F, 0.0F, 1, 2, 1);
    this.tspike2.func_78793_a(-2.0F, 17.0F, -27.0F);
    this.tspike2.func_78787_b(128, 64);
    this.tspike2.field_78809_i = true;
    setRotation(this.tspike2, 0.0371786F, 0.0F, -0.5948606F);
    this.tspike3 = new ModelRenderer(this, 25, 25);
    this.tspike3.func_78789_a(0.0F, -2.0F, 0.0F, 1, 2, 1);
    this.tspike3.func_78793_a(-1.0F, 18.5F, -27.0F);
    this.tspike3.func_78787_b(128, 64);
    this.tspike3.field_78809_i = true;
    setRotation(this.tspike3, 1.115355F, 0.0F, 0.5948578F);
    this.tspike4 = new ModelRenderer(this, 25, 25);
    this.tspike4.func_78789_a(-1.0F, -2.0F, 0.0F, 1, 2, 1);
    this.tspike4.func_78793_a(-2.0F, 18.5F, -27.5F);
    this.tspike4.func_78787_b(128, 64);
    this.tspike4.field_78809_i = true;
    setRotation(this.tspike4, 1.115353F, 0.0F, -0.5948606F);
    this.lefthead = new ModelRenderer(this, 0, 30);
    this.lefthead.func_78789_a(-4.5F, -9.0F, -4.5F, 9, 9, 8);
    this.lefthead.func_78793_a(-9.0F, -3.0F, -7.0F);
    this.lefthead.func_78787_b(128, 64);
    this.lefthead.field_78809_i = true;
    setRotation(this.lefthead, 0.0F, 0.0F, 0.0F);
    this.leftjaw = new ModelRenderer(this, 0, 50);
    this.leftjaw.func_78789_a(-4.5F, 1.0F, -6.0F, 9, 1, 9);
    this.leftjaw.func_78793_a(0.0F, 0.0F, 0.0F);
    this.leftjaw.func_78787_b(128, 64);
    this.leftjaw.field_78809_i = true;
    setRotation(this.leftjaw, 0.3346075F, 0.0F, 0.0F);
    this.lefthead.func_78792_a(this.leftjaw);
    this.leftupperjaw = new ModelRenderer(this, 0, 28);
    this.leftupperjaw.func_78789_a(0.0F, 0.0F, 0.0F, 9, 1, 1);
    this.leftupperjaw.func_78793_a(-4.5F, -1.0F, -5.5F);
    this.leftupperjaw.func_78787_b(128, 64);
    this.leftupperjaw.field_78809_i = true;
    setRotation(this.leftupperjaw, 0.0F, 0.0F, 0.0F);
    this.lefthead.func_78792_a(this.leftupperjaw);
    this.lstooth1 = new ModelRenderer(this, 0, 50);
    this.lstooth1.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.lstooth1.func_78793_a(-3.5F, 0.0F, -6.0F);
    this.lstooth1.func_78787_b(128, 64);
    this.lstooth1.field_78809_i = true;
    setRotation(this.lstooth1, 0.0F, 0.0F, 0.0F);
    this.leftjaw.func_78792_a(this.lstooth1);
    this.lstooth2 = new ModelRenderer(this, 0, 50);
    this.lstooth2.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.lstooth2.func_78793_a(-1.5F, 0.0F, -6.0F);
    this.lstooth2.func_78787_b(128, 64);
    this.lstooth2.field_78809_i = true;
    setRotation(this.lstooth2, 0.0F, 0.0F, 0.0F);
    this.leftjaw.func_78792_a(this.lstooth2);
    this.lstooth3 = new ModelRenderer(this, 0, 50);
    this.lstooth3.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.lstooth3.func_78793_a(0.5F, 0.0F, -6.0F);
    this.lstooth3.func_78787_b(128, 64);
    this.lstooth3.field_78809_i = true;
    setRotation(this.lstooth3, 0.0F, 0.0F, 0.0F);
    this.leftjaw.func_78792_a(this.lstooth3);
    this.lstooth4 = new ModelRenderer(this, 0, 50);
    this.lstooth4.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.lstooth4.func_78793_a(2.5F, 0.0F, -6.0F);
    this.lstooth4.func_78787_b(128, 64);
    this.lstooth4.field_78809_i = true;
    setRotation(this.lstooth4, 0.0F, 0.0F, 0.0F);
    this.leftjaw.func_78792_a(this.lstooth4);
    this.lbtooth1 = new ModelRenderer(this, 0, 50);
    this.lbtooth1.func_78789_a(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.lbtooth1.func_78793_a(0.0F, 1.0F, 0.0F);
    this.lbtooth1.func_78787_b(128, 64);
    this.lbtooth1.field_78809_i = true;
    setRotation(this.lbtooth1, 0.0F, 0.0F, 0.0F);
    this.leftupperjaw.func_78792_a(this.lbtooth1);
    this.lbtooth2 = new ModelRenderer(this, 0, 50);
    this.lbtooth2.func_78789_a(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.lbtooth2.func_78793_a(2.0F, 1.0F, 0.0F);
    this.lbtooth2.func_78787_b(128, 64);
    this.lbtooth2.field_78809_i = true;
    setRotation(this.lbtooth2, 0.0F, 0.0F, 0.0F);
    this.leftupperjaw.func_78792_a(this.lbtooth2);
    this.lbtooth3 = new ModelRenderer(this, 0, 50);
    this.lbtooth3.func_78789_a(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.lbtooth3.func_78793_a(4.0F, 1.0F, 0.0F);
    this.lbtooth3.func_78787_b(128, 64);
    this.lbtooth3.field_78809_i = true;
    setRotation(this.lbtooth3, 0.0F, 0.0F, 0.0F);
    this.leftupperjaw.func_78792_a(this.lbtooth3);
    this.lbtooth4 = new ModelRenderer(this, 0, 50);
    this.lbtooth4.func_78789_a(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.lbtooth4.func_78793_a(6.0F, 1.0F, 0.0F);
    this.lbtooth4.func_78787_b(128, 64);
    this.lbtooth4.field_78809_i = true;
    setRotation(this.lbtooth4, 0.0F, 0.0F, 0.0F);
    this.leftupperjaw.func_78792_a(this.lbtooth4);
    this.lbtooth5 = new ModelRenderer(this, 0, 50);
    this.lbtooth5.func_78789_a(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.lbtooth5.func_78793_a(8.0F, 1.0F, 0.0F);
    this.lbtooth5.func_78787_b(128, 64);
    this.lbtooth5.field_78809_i = true;
    setRotation(this.lbtooth5, 0.0F, 0.0F, 0.0F);
    this.leftupperjaw.func_78792_a(this.lbtooth5);
    this.lbackspike1 = new ModelRenderer(this, 10, 24);
    this.lbackspike1.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 3);
    this.lbackspike1.func_78793_a(-0.5F, -2.0F, 3.5F);
    this.lbackspike1.func_78787_b(128, 64);
    this.lbackspike1.field_78809_i = true;
    setRotation(this.lbackspike1, 0.0F, 0.0F, 0.0F);
    this.lefthead.func_78792_a(this.lbackspike1);
    this.lbackspike2 = new ModelRenderer(this, 10, 24);
    this.lbackspike2.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 3);
    this.lbackspike2.func_78793_a(-0.5F, -4.0F, 3.5F);
    this.lbackspike2.func_78787_b(128, 64);
    this.lbackspike2.field_78809_i = true;
    setRotation(this.lbackspike2, 0.0F, 0.0F, 0.0F);
    this.lefthead.func_78792_a(this.lbackspike2);
    this.lbackspike3 = new ModelRenderer(this, 10, 24);
    this.lbackspike3.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 3);
    this.lbackspike3.func_78793_a(-0.5F, -6.0F, 3.5F);
    this.lbackspike3.func_78787_b(128, 64);
    this.lbackspike3.field_78809_i = true;
    setRotation(this.lbackspike3, 0.0F, 0.0F, 0.0F);
    this.lefthead.func_78792_a(this.lbackspike3);
    this.lackspike4 = new ModelRenderer(this, 10, 24);
    this.lackspike4.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 3);
    this.lackspike4.func_78793_a(-0.5F, -8.0F, 3.5F);
    this.lackspike4.func_78787_b(128, 64);
    this.lackspike4.field_78809_i = true;
    setRotation(this.lackspike4, 0.0F, 0.0F, 0.0F);
    this.lefthead.func_78792_a(this.lackspike4);
    this.lefteye1 = new ModelRenderer(this, 0, 24);
    this.lefteye1.func_78789_a(0.0F, 0.0F, 0.0F, 3, 3, 1);
    this.lefteye1.func_78793_a(0.5F, -6.0F, -5.5F);
    this.lefteye1.func_78787_b(128, 64);
    this.lefteye1.field_78809_i = true;
    setRotation(this.lefteye1, 0.0F, 0.0F, 0.0F);
    this.lefthead.func_78792_a(this.lefteye1);
    this.lefteye2 = new ModelRenderer(this, 0, 24);
    this.lefteye2.func_78789_a(0.0F, 0.0F, 0.0F, 3, 3, 1);
    this.lefteye2.func_78793_a(-3.5F, -6.0F, -5.5F);
    this.lefteye2.func_78787_b(128, 64);
    this.lefteye2.field_78809_i = true;
    setRotation(this.lefteye2, 0.0F, 0.0F, 0.0F);
    this.lefthead.func_78792_a(this.lefteye2);
    this.middlehead = new ModelRenderer(this, 0, 30);
    this.middlehead.func_78789_a(-4.5F, -9.0F, -4.5F, 9, 9, 8);
    this.middlehead.func_78793_a(3.0F, -3.0F, -7.0F);
    this.middlehead.func_78787_b(128, 64);
    this.middlehead.field_78809_i = true;
    setRotation(this.middlehead, 0.0F, 0.0F, 0.0F);
    this.middlejaw = new ModelRenderer(this, 0, 50);
    this.middlejaw.func_78789_a(-4.5F, 1.0F, -6.0F, 9, 1, 9);
    this.middlejaw.func_78793_a(0.0F, 0.0F, 0.0F);
    this.middlejaw.func_78787_b(128, 64);
    this.middlejaw.field_78809_i = true;
    setRotation(this.middlejaw, 0.3346075F, 0.0F, 0.0F);
    this.middlehead.func_78792_a(this.middlejaw);
    this.middleupperjaw = new ModelRenderer(this, 0, 28);
    this.middleupperjaw.func_78789_a(0.0F, 0.0F, 0.0F, 9, 1, 1);
    this.middleupperjaw.func_78793_a(-4.5F, -1.0F, -5.5F);
    this.middleupperjaw.func_78787_b(128, 64);
    this.middleupperjaw.field_78809_i = true;
    setRotation(this.middleupperjaw, 0.0F, 0.0F, 0.0F);
    this.middlehead.func_78792_a(this.middleupperjaw);
    this.mstooth1 = new ModelRenderer(this, 0, 50);
    this.mstooth1.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.mstooth1.func_78793_a(-3.5F, 0.0F, -6.0F);
    this.mstooth1.func_78787_b(128, 64);
    this.mstooth1.field_78809_i = true;
    setRotation(this.mstooth1, 0.0F, 0.0F, 0.0F);
    this.middlejaw.func_78792_a(this.mstooth1);
    this.mstooth2 = new ModelRenderer(this, 0, 50);
    this.mstooth2.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.mstooth2.func_78793_a(-1.5F, 0.0F, -6.0F);
    this.mstooth2.func_78787_b(128, 64);
    this.mstooth2.field_78809_i = true;
    setRotation(this.mstooth2, 0.0F, 0.0F, 0.0F);
    this.middlejaw.func_78792_a(this.mstooth2);
    this.mstooth3 = new ModelRenderer(this, 0, 50);
    this.mstooth3.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.mstooth3.func_78793_a(0.5F, 0.0F, -6.0F);
    this.mstooth3.func_78787_b(128, 64);
    this.mstooth3.field_78809_i = true;
    setRotation(this.mstooth3, 0.0F, 0.0F, 0.0F);
    this.middlejaw.func_78792_a(this.mstooth3);
    this.mstooth4 = new ModelRenderer(this, 0, 50);
    this.mstooth4.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.mstooth4.func_78793_a(2.5F, 0.0F, -6.0F);
    this.mstooth4.func_78787_b(128, 64);
    this.mstooth4.field_78809_i = true;
    setRotation(this.mstooth4, 0.0F, 0.0F, 0.0F);
    this.middlejaw.func_78792_a(this.mstooth4);
    this.mbtooth1 = new ModelRenderer(this, 0, 50);
    this.mbtooth1.func_78789_a(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.mbtooth1.func_78793_a(0.0F, 1.0F, 0.0F);
    this.mbtooth1.func_78787_b(128, 64);
    this.mbtooth1.field_78809_i = true;
    setRotation(this.mbtooth1, 0.0F, 0.0F, 0.0F);
    this.middleupperjaw.func_78792_a(this.mbtooth1);
    this.mbtooth2 = new ModelRenderer(this, 0, 50);
    this.mbtooth2.func_78789_a(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.mbtooth2.func_78793_a(2.0F, 1.0F, 0.0F);
    this.mbtooth2.func_78787_b(128, 64);
    this.mbtooth2.field_78809_i = true;
    setRotation(this.mbtooth2, 0.0F, 0.0F, 0.0F);
    this.middleupperjaw.func_78792_a(this.mbtooth2);
    this.mbtooth3 = new ModelRenderer(this, 0, 50);
    this.mbtooth3.func_78789_a(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.mbtooth3.func_78793_a(4.0F, 1.0F, 0.0F);
    this.mbtooth3.func_78787_b(128, 64);
    this.mbtooth3.field_78809_i = true;
    setRotation(this.mbtooth3, 0.0F, 0.0F, 0.0F);
    this.middleupperjaw.func_78792_a(this.mbtooth3);
    this.mbtooth4 = new ModelRenderer(this, 0, 50);
    this.mbtooth4.func_78789_a(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.mbtooth4.func_78793_a(6.0F, 1.0F, 0.0F);
    this.mbtooth4.func_78787_b(128, 64);
    this.mbtooth4.field_78809_i = true;
    setRotation(this.mbtooth4, 0.0F, 0.0F, 0.0F);
    this.middleupperjaw.func_78792_a(this.mbtooth4);
    this.mbtooth5 = new ModelRenderer(this, 0, 50);
    this.mbtooth5.func_78789_a(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.mbtooth5.func_78793_a(8.0F, 1.0F, 0.0F);
    this.mbtooth5.func_78787_b(128, 64);
    this.mbtooth5.field_78809_i = true;
    setRotation(this.mbtooth5, 0.0F, 0.0F, 0.0F);
    this.middleupperjaw.func_78792_a(this.mbtooth5);
    this.mbackspike1 = new ModelRenderer(this, 10, 24);
    this.mbackspike1.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 3);
    this.mbackspike1.func_78793_a(-0.5F, -2.0F, 3.5F);
    this.mbackspike1.func_78787_b(128, 64);
    this.mbackspike1.field_78809_i = true;
    setRotation(this.mbackspike1, 0.0F, 0.0F, 0.0F);
    this.middlehead.func_78792_a(this.mbackspike1);
    this.mbackspike2 = new ModelRenderer(this, 10, 24);
    this.mbackspike2.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 3);
    this.mbackspike2.func_78793_a(-0.5F, -4.0F, 3.5F);
    this.mbackspike2.func_78787_b(128, 64);
    this.mbackspike2.field_78809_i = true;
    setRotation(this.mbackspike2, 0.0F, 0.0F, 0.0F);
    this.middlehead.func_78792_a(this.mbackspike2);
    this.mbackspike3 = new ModelRenderer(this, 10, 24);
    this.mbackspike3.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 3);
    this.mbackspike3.func_78793_a(-0.5F, -6.0F, 3.5F);
    this.mbackspike3.func_78787_b(128, 64);
    this.mbackspike3.field_78809_i = true;
    setRotation(this.mbackspike3, 0.0F, 0.0F, 0.0F);
    this.middlehead.func_78792_a(this.mbackspike3);
    this.mbackspike4 = new ModelRenderer(this, 10, 24);
    this.mbackspike4.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 3);
    this.mbackspike4.func_78793_a(-0.5F, -8.0F, 3.5F);
    this.mbackspike4.func_78787_b(128, 64);
    this.mbackspike4.field_78809_i = true;
    setRotation(this.mbackspike4, 0.0F, 0.0F, 0.0F);
    this.middlehead.func_78792_a(this.mbackspike4);
    this.middleeye1 = new ModelRenderer(this, 0, 24);
    this.middleeye1.func_78789_a(0.0F, 0.0F, 0.0F, 3, 3, 1);
    this.middleeye1.func_78793_a(0.5F, -6.0F, -5.5F);
    this.middleeye1.func_78787_b(128, 64);
    this.middleeye1.field_78809_i = true;
    setRotation(this.middleeye1, 0.0F, 0.0F, 0.0F);
    this.middlehead.func_78792_a(this.middleeye1);
    this.middleeye2 = new ModelRenderer(this, 0, 24);
    this.middleeye2.func_78789_a(0.0F, 0.0F, 0.0F, 3, 3, 1);
    this.middleeye2.func_78793_a(-3.5F, -6.0F, -5.5F);
    this.middleeye2.func_78787_b(128, 64);
    this.middleeye2.field_78809_i = true;
    setRotation(this.middleeye2, 0.0F, 0.0F, 0.0F);
    this.middlehead.func_78792_a(this.middleeye2);
    this.righthead = new ModelRenderer(this, 0, 30);
    this.righthead.func_78789_a(-4.5F, -9.0F, -4.5F, 9, 9, 8);
    this.righthead.func_78793_a(15.0F, -3.0F, -7.0F);
    this.righthead.func_78787_b(128, 64);
    this.righthead.field_78809_i = true;
    setRotation(this.righthead, 0.0F, 0.0F, 0.0F);
    this.rightjaw = new ModelRenderer(this, 0, 50);
    this.rightjaw.func_78789_a(-4.5F, 1.0F, -6.0F, 9, 1, 9);
    this.rightjaw.func_78793_a(0.0F, 0.0F, 0.0F);
    this.rightjaw.func_78787_b(128, 64);
    this.rightjaw.field_78809_i = true;
    setRotation(this.rightjaw, 0.3346075F, 0.0F, 0.0F);
    this.righthead.func_78792_a(this.rightjaw);
    this.rightupperjaw = new ModelRenderer(this, 0, 28);
    this.rightupperjaw.func_78789_a(0.0F, 0.0F, 0.0F, 9, 1, 1);
    this.rightupperjaw.func_78793_a(-4.5F, -1.0F, -5.5F);
    this.rightupperjaw.func_78787_b(128, 64);
    this.rightupperjaw.field_78809_i = true;
    setRotation(this.rightupperjaw, 0.0F, 0.0F, 0.0F);
    this.righthead.func_78792_a(this.rightupperjaw);
    this.rstooth1 = new ModelRenderer(this, 0, 50);
    this.rstooth1.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.rstooth1.func_78793_a(-3.5F, 0.0F, -6.0F);
    this.rstooth1.func_78787_b(128, 64);
    this.rstooth1.field_78809_i = true;
    setRotation(this.rstooth1, 0.0F, 0.0F, 0.0F);
    this.rightjaw.func_78792_a(this.rstooth1);
    this.rstooth2 = new ModelRenderer(this, 0, 50);
    this.rstooth2.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.rstooth2.func_78793_a(-1.5F, 0.0F, -6.0F);
    this.rstooth2.func_78787_b(128, 64);
    this.rstooth2.field_78809_i = true;
    setRotation(this.rstooth2, 0.0F, 0.0F, 0.0F);
    this.rightjaw.func_78792_a(this.rstooth2);
    this.rstooth3 = new ModelRenderer(this, 0, 50);
    this.rstooth3.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.rstooth3.func_78793_a(0.5F, 0.0F, -6.0F);
    this.rstooth3.func_78787_b(128, 64);
    this.rstooth3.field_78809_i = true;
    setRotation(this.rstooth3, 0.0F, 0.0F, 0.0F);
    this.rightjaw.func_78792_a(this.rstooth3);
    this.rstooth4 = new ModelRenderer(this, 0, 50);
    this.rstooth4.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.rstooth4.func_78793_a(2.5F, 0.0F, -6.0F);
    this.rstooth4.func_78787_b(128, 64);
    this.rstooth4.field_78809_i = true;
    setRotation(this.rstooth4, 0.0F, 0.0F, 0.0F);
    this.rightjaw.func_78792_a(this.rstooth4);
    this.rbtooth1 = new ModelRenderer(this, 0, 50);
    this.rbtooth1.func_78789_a(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.rbtooth1.func_78793_a(0.0F, 1.0F, 0.0F);
    this.rbtooth1.func_78787_b(128, 64);
    this.rbtooth1.field_78809_i = true;
    setRotation(this.rbtooth1, 0.0F, 0.0F, 0.0F);
    this.rightupperjaw.func_78792_a(this.rbtooth1);
    this.rbtooth2 = new ModelRenderer(this, 0, 50);
    this.rbtooth2.func_78789_a(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.rbtooth2.func_78793_a(2.0F, 1.0F, 0.0F);
    this.rbtooth2.func_78787_b(128, 64);
    this.rbtooth2.field_78809_i = true;
    setRotation(this.rbtooth2, 0.0F, 0.0F, 0.0F);
    this.rightupperjaw.func_78792_a(this.rbtooth2);
    this.rbtooth3 = new ModelRenderer(this, 0, 50);
    this.rbtooth3.func_78789_a(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.rbtooth3.func_78793_a(4.0F, 1.0F, 0.0F);
    this.rbtooth3.func_78787_b(128, 64);
    this.rbtooth3.field_78809_i = true;
    setRotation(this.rbtooth3, 0.0F, 0.0F, 0.0F);
    this.rightupperjaw.func_78792_a(this.rbtooth3);
    this.rbtooth4 = new ModelRenderer(this, 0, 50);
    this.rbtooth4.func_78789_a(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.rbtooth4.func_78793_a(6.0F, 1.0F, 0.0F);
    this.rbtooth4.func_78787_b(128, 64);
    this.rbtooth4.field_78809_i = true;
    setRotation(this.rbtooth4, 0.0F, 0.0F, 0.0F);
    this.rightupperjaw.func_78792_a(this.rbtooth4);
    this.rbtooth5 = new ModelRenderer(this, 0, 50);
    this.rbtooth5.func_78789_a(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.rbtooth5.func_78793_a(8.0F, 1.0F, 0.0F);
    this.rbtooth5.func_78787_b(128, 64);
    this.rbtooth5.field_78809_i = true;
    setRotation(this.rbtooth5, 0.0F, 0.0F, 0.0F);
    this.rightupperjaw.func_78792_a(this.rbtooth5);
    this.rbackspike1 = new ModelRenderer(this, 10, 24);
    this.rbackspike1.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 3);
    this.rbackspike1.func_78793_a(-0.5F, -2.0F, 3.5F);
    this.rbackspike1.func_78787_b(128, 64);
    this.rbackspike1.field_78809_i = true;
    setRotation(this.rbackspike1, 0.0F, 0.0F, 0.0F);
    this.righthead.func_78792_a(this.rbackspike1);
    this.rbackspike2 = new ModelRenderer(this, 10, 24);
    this.rbackspike2.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 3);
    this.rbackspike2.func_78793_a(-0.5F, -4.0F, 3.5F);
    this.rbackspike2.func_78787_b(128, 64);
    this.rbackspike2.field_78809_i = true;
    setRotation(this.rbackspike2, 0.0F, 0.0F, 0.0F);
    this.righthead.func_78792_a(this.rbackspike2);
    this.rbackspike3 = new ModelRenderer(this, 10, 24);
    this.rbackspike3.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 3);
    this.rbackspike3.func_78793_a(-0.5F, -6.0F, 3.5F);
    this.rbackspike3.func_78787_b(128, 64);
    this.rbackspike3.field_78809_i = true;
    setRotation(this.rbackspike3, 0.0F, 0.0F, 0.0F);
    this.righthead.func_78792_a(this.rbackspike3);
    this.rbackspike4 = new ModelRenderer(this, 10, 24);
    this.rbackspike4.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 3);
    this.rbackspike4.func_78793_a(-0.5F, -8.0F, 3.5F);
    this.rbackspike4.func_78787_b(128, 64);
    this.rbackspike4.field_78809_i = true;
    setRotation(this.rbackspike4, 0.0F, 0.0F, 0.0F);
    this.righthead.func_78792_a(this.rbackspike4);
    this.righteye1 = new ModelRenderer(this, 0, 24);
    this.righteye1.func_78789_a(0.0F, 0.0F, 0.0F, 3, 3, 1);
    this.righteye1.func_78793_a(0.5F, -6.0F, -5.5F);
    this.righteye1.func_78787_b(128, 64);
    this.righteye1.field_78809_i = true;
    setRotation(this.righteye1, 0.0F, 0.0F, 0.0F);
    this.righthead.func_78792_a(this.righteye1);
    this.righteye2 = new ModelRenderer(this, 0, 24);
    this.righteye2.func_78789_a(0.0F, 0.0F, 0.0F, 3, 3, 1);
    this.righteye2.func_78793_a(-3.5F, -6.0F, -5.5F);
    this.righteye2.func_78787_b(128, 64);
    this.righteye2.field_78809_i = true;
    setRotation(this.righteye2, 0.0F, 0.0F, 0.0F);
    this.righthead.func_78792_a(this.righteye2);
    this.middlewall = new ModelRenderer(this, 46, 0);
    this.middlewall.func_78789_a(0.0F, 0.0F, 0.0F, 40, 40, 1);
    this.middlewall.func_78793_a(-18.0F, -15.0F, 17.0F);
    this.middlewall.func_78787_b(128, 64);
    this.middlewall.field_78809_i = true;
    setRotation(this.middlewall, 0.0F, 0.0F, 0.0F);
    this.rightwall = new ModelRenderer(this, 46, 0);
    this.rightwall.func_78789_a(0.0F, 0.0F, 0.0F, 40, 40, 1);
    this.rightwall.func_78793_a(22.0F, -15.0F, 17.0F);
    this.rightwall.func_78787_b(128, 64);
    this.rightwall.field_78809_i = true;
    setRotation(this.rightwall, 0.0F, 0.669215F, 0.0F);
    this.leftwall = new ModelRenderer(this, 46, 0);
    this.leftwall.func_78789_a(-40.0F, 0.0F, 0.0F, 40, 40, 1);
    this.leftwall.func_78793_a(-18.0F, -15.0F, 17.0F);
    this.leftwall.func_78787_b(128, 64);
    this.leftwall.field_78809_i = true;
    setRotation(this.leftwall, 0.0F, -0.6692116F, 0.0F);
    this.walltentacle1 = new ModelRenderer(this, 0, 0);
    this.walltentacle1.func_78789_a(-1.5F, -1.5F, -12.0F, 3, 3, 12);
    this.walltentacle1.func_78793_a(-30.0F, -3.0F, 9.0F);
    this.walltentacle1.func_78787_b(128, 64);
    this.walltentacle1.field_78809_i = true;
    setRotation(this.walltentacle1, 0.0F, 0.0F, 0.0F);
    this.walltentacle2 = new ModelRenderer(this, 0, 0);
    this.walltentacle2.func_78789_a(-1.5F, -1.5F, -12.0F, 3, 3, 12);
    this.walltentacle2.func_78793_a(-33.0F, 9.0F, 7.0F);
    this.walltentacle2.func_78787_b(128, 64);
    this.walltentacle2.field_78809_i = true;
    setRotation(this.walltentacle2, 0.0F, 0.0F, 0.0F);
    this.walltentacle3 = new ModelRenderer(this, 0, 0);
    this.walltentacle3.func_78789_a(-1.5F, -1.5F, -12.0F, 3, 3, 12);
    this.walltentacle3.func_78793_a(-42.0F, 0.0F, 0.0F);
    this.walltentacle3.func_78787_b(128, 64);
    this.walltentacle3.field_78809_i = true;
    setRotation(this.walltentacle3, 0.0F, 0.0F, 0.0F);
    this.walltentacle4 = new ModelRenderer(this, 0, 0);
    this.walltentacle4.func_78789_a(-1.5F, -1.5F, -12.0F, 3, 3, 12);
    this.walltentacle4.func_78793_a(-23.0F, 6.0F, 15.0F);
    this.walltentacle4.func_78787_b(128, 64);
    this.walltentacle4.field_78809_i = true;
    setRotation(this.walltentacle4, 0.0F, 0.0F, 0.0F);
    this.walltentacle5 = new ModelRenderer(this, 0, 0);
    this.walltentacle5.func_78789_a(-1.5F, -1.5F, -12.0F, 3, 3, 12);
    this.walltentacle5.func_78793_a(42.0F, 0.0F, 3.0F);
    this.walltentacle5.func_78787_b(128, 64);
    this.walltentacle5.field_78809_i = true;
    setRotation(this.walltentacle5, 0.0F, 0.0F, 0.0F);
    this.walltentacle6 = new ModelRenderer(this, 0, 0);
    this.walltentacle6.func_78789_a(-1.5F, -1.5F, -12.0F, 3, 3, 12);
    this.walltentacle6.func_78793_a(33.0F, -6.0F, 9.0F);
    this.walltentacle6.func_78787_b(128, 64);
    this.walltentacle6.field_78809_i = true;
    setRotation(this.walltentacle6, 0.0F, 0.0F, 0.0F);
    this.walltentacle7 = new ModelRenderer(this, 0, 0);
    this.walltentacle7.func_78789_a(-1.5F, -1.5F, -12.0F, 3, 3, 12);
    this.walltentacle7.func_78793_a(36.0F, 11.0F, 7.0F);
    this.walltentacle7.func_78787_b(128, 64);
    this.walltentacle7.field_78809_i = true;
    setRotation(this.walltentacle7, 0.0F, 0.0F, 0.0F);
    this.walltentacle8 = new ModelRenderer(this, 0, 0);
    this.walltentacle8.func_78789_a(-1.5F, -1.5F, -12.0F, 3, 3, 12);
    this.walltentacle8.func_78793_a(46.13334F, 17.0F, 0.0F);
    this.walltentacle8.func_78787_b(128, 64);
    this.walltentacle8.field_78809_i = true;
    setRotation(this.walltentacle8, 0.0F, 0.0F, 0.0F);
  }
  
  public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    GlStateManager.func_179094_E();
    super.func_78088_a(entity, f, f1, f2, f3, f4, f5);
    GlStateManager.func_179152_a(2.0F, 2.0F, 2.0F);
    GlStateManager.func_179109_b(-0.175F, -0.75F, 0.5F);
    func_78087_a(f, f1, f2, f3, f4, f5, entity);
    this.frontspike1.func_78785_a(f5);
    this.frontspike2.func_78785_a(f5);
    this.frontspike3.func_78785_a(f5);
    this.frontspike4.func_78785_a(f5);
    this.frontspike5.func_78785_a(f5);
    this.leftneck.func_78785_a(f5);
    this.middleneck.func_78785_a(f5);
    this.rightneck.func_78785_a(f5);
    this.body.func_78785_a(f5);
    this.tentacle2base.func_78785_a(f5);
    this.tentacle1.func_78785_a(f5);
    this.tentacle2.func_78785_a(f5);
    this.tentacle3.func_78785_a(f5);
    this.tentacle4.func_78785_a(f5);
    this.tentacle5.func_78785_a(f5);
    this.tentacle6.func_78785_a(f5);
    this.tentacle7.func_78785_a(f5);
    this.tentacle8.func_78785_a(f5);
    this.tentacle9.func_78785_a(f5);
    this.tentacle10.func_78785_a(f5);
    this.tentacle11.func_78785_a(f5);
    this.tentacle12.func_78785_a(f5);
    this.tentacle13.func_78785_a(f5);
    this.tentacle14.func_78785_a(f5);
    this.tentacle15.func_78785_a(f5);
    this.tentacle16.func_78785_a(f5);
    this.tentacle17.func_78785_a(f5);
    this.tentacle18.func_78785_a(f5);
    this.tentacle19.func_78785_a(f5);
    this.tentacle20.func_78785_a(f5);
    this.tlib12.func_78785_a(f5);
    this.tlib22.func_78785_a(f5);
    this.tspike12.func_78785_a(f5);
    this.tspike22.func_78785_a(f5);
    this.tspike32.func_78785_a(f5);
    this.tspike42.func_78785_a(f5);
    this.tlib1.func_78785_a(f5);
    this.tlib2.func_78785_a(f5);
    this.tspike1.func_78785_a(f5);
    this.tspike2.func_78785_a(f5);
    this.tspike3.func_78785_a(f5);
    this.tspike4.func_78785_a(f5);
    this.lefthead.func_78785_a(f5);
    this.middlehead.func_78785_a(f5);
    this.righthead.func_78785_a(f5);
    this.middlewall.func_78785_a(f5);
    this.rightwall.func_78785_a(f5);
    this.leftwall.func_78785_a(f5);
    this.walltentacle1.func_78785_a(f5);
    this.walltentacle2.func_78785_a(f5);
    this.walltentacle3.func_78785_a(f5);
    this.walltentacle4.func_78785_a(f5);
    this.walltentacle5.func_78785_a(f5);
    this.walltentacle6.func_78785_a(f5);
    this.walltentacle7.func_78785_a(f5);
    this.walltentacle8.func_78785_a(f5);
    GlStateManager.func_179121_F();
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z) {
    model.field_78795_f = x;
    model.field_78796_g = y;
    model.field_78808_h = z;
  }
  
  public void func_78086_a(EntityLivingBase p_78086_1_, float p_78086_2_, float p_78086_3_, float p_78086_4_) {
    EntityChagaroth entitywither = (EntityChagaroth)p_78086_1_;
    this.lefthead.field_78796_g = (entitywither.getHeadYRotation(0) - p_78086_1_.field_70761_aq) * 0.017453292F + (!entitywither.getCurrentBook().func_190926_b() ? -(MathHelper.func_76126_a(entitywither.field_70173_aa * 0.1F) * 0.5F + 0.5F) : 0.0F);
    this.lefthead.field_78795_f = entitywither.getHeadXRotation(0) * 0.017453292F;
    this.righthead.field_78796_g = (entitywither.getHeadYRotation(1) - p_78086_1_.field_70761_aq) * 0.017453292F + (!entitywither.getCurrentBook().func_190926_b() ? -(MathHelper.func_76126_a(entitywither.field_70173_aa * 0.1F) * 0.5F - 0.5F) : 0.0F);
    this.righthead.field_78795_f = entitywither.getHeadXRotation(1) * 0.017453292F;
  }
  
  public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
    this.middlehead.field_78796_g = f3 / 57.295776F;
    this.middlehead.field_78795_f = f4 / 57.295776F;
    this.walltentacle1.field_78795_f = MathHelper.func_76134_b(f4 * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.walltentacle2.field_78795_f = MathHelper.func_76134_b(f4 * 0.6662F) * 2.0F * f1 * 0.5F;
    this.walltentacle3.field_78795_f = MathHelper.func_76134_b(f4 * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.walltentacle4.field_78795_f = MathHelper.func_76134_b(f4 * 0.6662F) * 2.0F * f1 * 0.5F;
    this.walltentacle5.field_78795_f = MathHelper.func_76134_b(f4 * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.walltentacle6.field_78795_f = MathHelper.func_76134_b(f4 * 0.6662F) * 2.0F * f1 * 0.5F;
    this.walltentacle7.field_78795_f = MathHelper.func_76134_b(f4 * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.walltentacle8.field_78795_f = MathHelper.func_76134_b(f4 * 0.6662F) * 2.0F * f1 * 0.5F;
    this.walltentacle1.field_78796_g = MathHelper.func_76134_b(f3 * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.walltentacle2.field_78796_g = MathHelper.func_76134_b(f3 * 0.6662F) * 2.0F * f1 * 0.5F;
    this.walltentacle3.field_78796_g = MathHelper.func_76134_b(f3 * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.walltentacle4.field_78796_g = MathHelper.func_76134_b(f3 * 0.6662F) * 2.0F * f1 * 0.5F;
    this.walltentacle5.field_78796_g = MathHelper.func_76134_b(f3 * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.walltentacle6.field_78796_g = MathHelper.func_76134_b(f3 * 0.6662F) * 2.0F * f1 * 0.5F;
    this.walltentacle7.field_78796_g = MathHelper.func_76134_b(f3 * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.walltentacle8.field_78796_g = MathHelper.func_76134_b(f3 * 0.6662F) * 2.0F * f1 * 0.5F;
    this.walltentacle1.field_78808_h = MathHelper.func_76134_b(f5 * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.walltentacle2.field_78808_h = MathHelper.func_76134_b(f5 * 0.6662F) * 2.0F * f1 * 0.5F;
    this.walltentacle3.field_78808_h = MathHelper.func_76134_b(f5 * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.walltentacle4.field_78808_h = MathHelper.func_76134_b(f5 * 0.6662F) * 2.0F * f1 * 0.5F;
    this.walltentacle5.field_78808_h = MathHelper.func_76134_b(f5 * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.walltentacle6.field_78808_h = MathHelper.func_76134_b(f5 * 0.6662F) * 2.0F * f1 * 0.5F;
    this.walltentacle7.field_78808_h = MathHelper.func_76134_b(f5 * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.walltentacle8.field_78808_h = MathHelper.func_76134_b(f5 * 0.6662F) * 2.0F * f1 * 0.5F;
    if (((EntityChagaroth)entity).deathTicks <= 200 && ((EntityChagaroth)entity).deathTicks > 0) {
      this.rot++;
      this.lefthead.field_78796_g = (180.0F - this.rot) / 3.1415927F;
      this.lefthead.field_78795_f = (180.0F + this.rot) / 3.1415927F;
      this.lefthead.field_78808_h = (180.0F + this.rot) / 3.1415927F;
      this.middlehead.field_78796_g = (180.0F + this.rot) / 3.1415927F;
      this.middlehead.field_78795_f = (180.0F - this.rot) / 3.1415927F;
      this.middlehead.field_78808_h = (180.0F - this.rot) / 3.1415927F;
      this.righthead.field_78796_g = (180.0F + this.rot) / 3.1415927F;
      this.righthead.field_78795_f = (180.0F + this.rot) / 3.1415927F;
      this.righthead.field_78808_h = (180.0F + this.rot) / 3.1415927F;
      this.walltentacle1.field_78807_k = true;
      this.walltentacle2.field_78807_k = true;
      this.walltentacle3.field_78807_k = true;
      this.walltentacle4.field_78807_k = true;
      this.walltentacle5.field_78807_k = true;
      this.walltentacle6.field_78807_k = true;
      this.walltentacle7.field_78807_k = true;
      this.walltentacle8.field_78807_k = true;
      this.leftwall.field_78807_k = true;
      this.rightwall.field_78807_k = true;
    } 
    if (((EntityChagaroth)entity).deathTicks == 0) {
      this.lefthead.field_78808_h = 0.0F;
      this.middlehead.field_78796_g = f3 / 57.295776F;
      this.middlehead.field_78795_f = f4 / 57.295776F;
      this.middlehead.field_78808_h = 0.0F;
      this.middlejaw.field_78795_f = 0.3346075F;
      this.righthead.field_78808_h = 0.0F;
      this.walltentacle1.field_78807_k = false;
      this.walltentacle2.field_78807_k = false;
      this.walltentacle3.field_78807_k = false;
      this.walltentacle4.field_78807_k = false;
      this.walltentacle5.field_78807_k = false;
      this.walltentacle6.field_78807_k = false;
      this.walltentacle7.field_78807_k = false;
      this.walltentacle8.field_78807_k = false;
      this.leftwall.field_78807_k = false;
      this.rightwall.field_78807_k = false;
      if (((EntityChagaroth)entity).hasOpenMouth()) {
        this.middlehead.field_78795_f -= 0.75F + MathHelper.func_76134_b(f2 * 2.0F) * 0.125F;
        this.middlejaw.field_78795_f += 0.75F;
      } 
    } 
  }
}
