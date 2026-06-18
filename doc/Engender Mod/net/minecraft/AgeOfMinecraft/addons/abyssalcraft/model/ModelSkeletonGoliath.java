package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.model;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelSkeletonGoliath extends ModelBase {
  public ModelRenderer base1;
  
  public ModelRenderer base2;
  
  public ModelRenderer base3;
  
  public ModelRenderer base4;
  
  public ModelRenderer spike1;
  
  public ModelRenderer spike2;
  
  public ModelRenderer spike3;
  
  public ModelRenderer spike4;
  
  public ModelRenderer spike5;
  
  public ModelRenderer spike6;
  
  public ModelRenderer spike7;
  
  public ModelRenderer spike8;
  
  public ModelRenderer spike9;
  
  public ModelRenderer spike10;
  
  public ModelRenderer spike11;
  
  public ModelRenderer thing1;
  
  public ModelRenderer thing2;
  
  public ModelRenderer head;
  
  public ModelRenderer leftjaw;
  
  public ModelRenderer rightjaw;
  
  public ModelRenderer jaw1;
  
  public ModelRenderer jaw2;
  
  public ModelRenderer jaw3;
  
  public ModelRenderer tooth1;
  
  public ModelRenderer tooth2;
  
  public ModelRenderer tooth3;
  
  public ModelRenderer tooth4;
  
  public ModelRenderer tooth5;
  
  public ModelRenderer shoulders;
  
  public ModelRenderer spine;
  
  public ModelRenderer leftarm;
  
  public ModelRenderer rightarm;
  
  public ModelRenderer leftrib1;
  
  public ModelRenderer leftrib12;
  
  public ModelRenderer leftrib13;
  
  public ModelRenderer leftrib2;
  
  public ModelRenderer leftrib22;
  
  public ModelRenderer leftrib23;
  
  public ModelRenderer leftrib3;
  
  public ModelRenderer leftrib32;
  
  public ModelRenderer leftrib33;
  
  public ModelRenderer leftrib4;
  
  public ModelRenderer leftrib42;
  
  public ModelRenderer leftrib43;
  
  public ModelRenderer leftrib5;
  
  public ModelRenderer leftrib52;
  
  public ModelRenderer leftrib53;
  
  public ModelRenderer leftrib6;
  
  public ModelRenderer leftrib62;
  
  public ModelRenderer leftrib63;
  
  public ModelRenderer rightrib1;
  
  public ModelRenderer rightrib12;
  
  public ModelRenderer rightrib13;
  
  public ModelRenderer rightrib2;
  
  public ModelRenderer rightrib22;
  
  public ModelRenderer rightrib23;
  
  public ModelRenderer rightrib3;
  
  public ModelRenderer rightrib32;
  
  public ModelRenderer rightrib33;
  
  public ModelRenderer rightrib4;
  
  public ModelRenderer rightrib42;
  
  public ModelRenderer rightrib43;
  
  public ModelRenderer rightrib5;
  
  public ModelRenderer rightrib52;
  
  public ModelRenderer rightrib53;
  
  public ModelRenderer rightrib6;
  
  public ModelRenderer rightrib62;
  
  public ModelRenderer rightrib63;
  
  public ModelRenderer sternum;
  
  public ModelRenderer pelvis;
  
  public ModelRenderer leftleg;
  
  public ModelRenderer rightleg;
  
  public ModelSkeletonGoliath(boolean renderCudgel) {
    this.field_78090_t = 128;
    this.field_78089_u = 64;
    this.base1 = new ModelRenderer(this, 0, 0);
    this.base1.func_78789_a(-1.0F, -1.0F, -7.0F, 2, 2, 10);
    this.base1.func_78793_a(-1.5F, 13.0F, 0.0F);
    this.base1.func_78787_b(128, 64);
    this.base1.field_78809_i = true;
    setRotation(this.base1, 0.0F, 0.0F, 0.0F);
    this.base2 = new ModelRenderer(this, 24, 2);
    this.base2.func_78789_a(0.0F, 0.0F, 0.0F, 3, 3, 3);
    this.base2.func_78793_a(-1.5F, -2.5F, -8.0F);
    this.base2.func_78787_b(128, 64);
    this.base2.field_78809_i = true;
    setRotation(this.base2, -0.4089647F, 0.0F, 0.0F);
    this.base3 = new ModelRenderer(this, 24, 8);
    this.base3.func_78789_a(0.0F, 0.0F, 0.0F, 4, 4, 6);
    this.base3.func_78793_a(-0.5F, -5.0F, -13.0F);
    this.base3.func_78787_b(128, 64);
    this.base3.field_78809_i = true;
    setRotation(this.base3, -0.3717861F, 0.0F, 0.2602503F);
    this.base4 = new ModelRenderer(this, 0, 12);
    this.base4.func_78789_a(0.0F, 0.0F, 0.0F, 5, 5, 7);
    this.base4.func_78793_a(-1.0F, -9.0F, -15.0F);
    this.base4.func_78787_b(128, 64);
    this.base4.field_78809_i = true;
    setRotation(this.base4, -0.8551081F, 0.0743572F, 0.0F);
    this.spike1 = new ModelRenderer(this, 27, 0);
    this.spike1.func_78789_a(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.spike1.func_78793_a(1.0F, -1.0F, -8.0F);
    this.spike1.func_78787_b(128, 64);
    this.spike1.field_78809_i = true;
    setRotation(this.spike1, 0.0F, 0.0F, 0.0F);
    this.spike2 = new ModelRenderer(this, 27, 0);
    this.spike2.func_78789_a(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.spike2.func_78793_a(-4.0F, -1.0F, -8.0F);
    this.spike2.func_78787_b(128, 64);
    this.spike2.field_78809_i = true;
    setRotation(this.spike2, 0.0F, 0.0F, 0.0F);
    this.spike3 = new ModelRenderer(this, 48, 0);
    this.spike3.func_78789_a(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.spike3.func_78793_a(-0.5F, -5.0F, -7.0F);
    this.spike3.func_78787_b(128, 64);
    this.spike3.field_78809_i = true;
    setRotation(this.spike3, -0.3346075F, 0.0F, 0.0F);
    this.spike4 = new ModelRenderer(this, 48, 0);
    this.spike4.func_78789_a(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.spike4.func_78793_a(-0.5F, 0.0F, -9.0F);
    this.spike4.func_78787_b(128, 64);
    this.spike4.field_78809_i = true;
    setRotation(this.spike4, 0.0F, 0.0F, 0.0F);
    this.spike5 = new ModelRenderer(this, 48, 0);
    this.spike5.func_78789_a(0.0F, 0.0F, 0.0F, 1, 4, 1);
    this.spike5.func_78793_a(1.0F, -9.0F, -11.0F);
    this.spike5.func_78787_b(128, 64);
    this.spike5.field_78809_i = true;
    setRotation(this.spike5, -0.8179294F, 0.0F, 0.0F);
    this.spike6 = new ModelRenderer(this, 26, 0);
    this.spike6.func_78789_a(0.0F, 0.0F, 0.0F, 4, 1, 1);
    this.spike6.func_78793_a(-4.0F, -5.0F, -13.0F);
    this.spike6.func_78787_b(128, 64);
    this.spike6.field_78809_i = true;
    setRotation(this.spike6, -0.3717861F, 0.2602503F, 0.0F);
    this.spike7 = new ModelRenderer(this, 26, 0);
    this.spike7.func_78789_a(0.0F, 0.0F, 0.0F, 4, 1, 1);
    this.spike7.func_78793_a(-4.0F, -9.0F, -14.0F);
    this.spike7.func_78787_b(128, 64);
    this.spike7.field_78809_i = true;
    setRotation(this.spike7, -0.4461433F, 0.4089647F, 0.5576792F);
    this.spike8 = new ModelRenderer(this, 26, 0);
    this.spike8.func_78789_a(0.0F, 0.0F, 0.0F, 4, 1, 1);
    this.spike8.func_78793_a(4.0F, -3.0F, -15.0F);
    this.spike8.func_78787_b(128, 64);
    this.spike8.field_78809_i = true;
    setRotation(this.spike8, -0.0371786F, -0.7807508F, -0.4461433F);
    this.spike9 = new ModelRenderer(this, 48, 0);
    this.spike9.func_78789_a(0.0F, 0.0F, 0.0F, 1, 5, 1);
    this.spike9.func_78793_a(0.0F, -4.0F, -15.0F);
    this.spike9.func_78787_b(128, 64);
    this.spike9.field_78809_i = true;
    setRotation(this.spike9, -0.9294653F, -0.6320364F, 0.8551081F);
    this.spike10 = new ModelRenderer(this, 36, 0);
    this.spike10.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 5);
    this.spike10.func_78793_a(3.0F, -9.0F, -20.0F);
    this.spike10.func_78787_b(128, 64);
    this.spike10.field_78809_i = true;
    setRotation(this.spike10, -0.3717861F, 0.0F, 0.7435722F);
    this.spike11 = new ModelRenderer(this, 24, 0);
    this.spike11.func_78789_a(0.0F, 0.0F, 0.0F, 5, 1, 1);
    this.spike11.func_78793_a(3.0F, -4.0F, -16.0F);
    this.spike11.func_78787_b(128, 64);
    this.spike11.field_78809_i = true;
    setRotation(this.spike11, 0.0F, 0.0F, -0.5205006F);
    this.thing1 = new ModelRenderer(this, 24, 18);
    this.thing1.func_78789_a(0.0F, 0.0F, 0.0F, 4, 4, 1);
    this.thing1.func_78793_a(-2.0F, -2.0F, -6.0F);
    this.thing1.func_78787_b(128, 64);
    this.thing1.field_78809_i = true;
    setRotation(this.thing1, 0.0F, 0.0F, 0.0F);
    this.thing2 = new ModelRenderer(this, 34, 18);
    this.thing2.func_78789_a(0.0F, 0.0F, 0.0F, 6, 6, 1);
    this.thing2.func_78793_a(-1.5F, -5.0F, -10.0F);
    this.thing2.func_78787_b(128, 64);
    this.thing2.field_78809_i = true;
    setRotation(this.thing2, -0.2602503F, 0.0F, 0.260246F);
    this.head = new ModelRenderer(this, 53, 0);
    this.head.func_78789_a(-4.0F, -9.0F, -4.0F, 9, 9, 9);
    this.head.func_78793_a(0.0F, -15.0F, 0.0F);
    this.head.func_78787_b(128, 64);
    this.head.field_78809_i = true;
    setRotation(this.head, 0.0F, 0.0F, 0.0F);
    this.leftjaw = new ModelRenderer(this, 49, 16);
    this.leftjaw.func_78789_a(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.leftjaw.func_78793_a(4.0F, 0.0F, 4.0F);
    this.leftjaw.func_78787_b(128, 64);
    this.leftjaw.field_78809_i = true;
    setRotation(this.leftjaw, 0.0F, 0.0F, 0.0F);
    this.rightjaw = new ModelRenderer(this, 49, 16);
    this.rightjaw.func_78789_a(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.rightjaw.func_78793_a(-4.0F, 0.0F, 4.0F);
    this.rightjaw.func_78787_b(128, 64);
    this.rightjaw.field_78809_i = true;
    setRotation(this.rightjaw, 0.0F, 0.0F, 0.0F);
    this.jaw1 = new ModelRenderer(this, 53, 18);
    this.jaw1.func_78789_a(-4.0F, 0.0F, -1.0F, 9, 1, 1);
    this.jaw1.func_78793_a(0.0F, 2.0F, -3.0F);
    this.jaw1.func_78787_b(128, 64);
    this.jaw1.field_78809_i = true;
    setRotation(this.jaw1, 0.0F, 0.0F, 0.0F);
    this.jaw2 = new ModelRenderer(this, 73, 18);
    this.jaw2.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 8);
    this.jaw2.func_78793_a(4.0F, 2.0F, -4.0F);
    this.jaw2.func_78787_b(128, 64);
    this.jaw2.field_78809_i = true;
    setRotation(this.jaw2, 0.0F, 0.0F, 0.0F);
    this.jaw3 = new ModelRenderer(this, 73, 18);
    this.jaw3.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 8);
    this.jaw3.func_78793_a(-4.0F, 2.0F, -4.0F);
    this.jaw3.func_78787_b(128, 64);
    this.jaw3.field_78809_i = true;
    setRotation(this.jaw3, 0.0F, 0.0F, 0.0F);
    this.tooth1 = new ModelRenderer(this, 49, 20);
    this.tooth1.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.tooth1.func_78793_a(4.0F, 1.0F, -4.0F);
    this.tooth1.func_78787_b(128, 64);
    this.tooth1.field_78809_i = true;
    setRotation(this.tooth1, 0.0F, 0.0F, 0.0F);
    this.tooth2 = new ModelRenderer(this, 49, 20);
    this.tooth2.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.tooth2.func_78793_a(2.0F, 1.0F, -4.0F);
    this.tooth2.func_78787_b(128, 64);
    this.tooth2.field_78809_i = true;
    setRotation(this.tooth2, 0.0F, 0.0F, 0.0F);
    this.tooth3 = new ModelRenderer(this, 49, 20);
    this.tooth3.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.tooth3.func_78793_a(0.0F, 1.0F, -4.0F);
    this.tooth3.func_78787_b(128, 64);
    this.tooth3.field_78809_i = true;
    setRotation(this.tooth3, 0.0F, 0.0F, 0.0F);
    this.tooth4 = new ModelRenderer(this, 49, 20);
    this.tooth4.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.tooth4.func_78793_a(-2.0F, 1.0F, -4.0F);
    this.tooth4.func_78787_b(128, 64);
    this.tooth4.field_78809_i = true;
    setRotation(this.tooth4, 0.0F, 0.0F, 0.0F);
    this.tooth5 = new ModelRenderer(this, 49, 20);
    this.tooth5.func_78789_a(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.tooth5.func_78793_a(-4.0F, 1.0F, -4.0F);
    this.tooth5.func_78787_b(128, 64);
    this.tooth5.field_78809_i = true;
    setRotation(this.tooth5, 0.0F, 0.0F, 0.0F);
    this.shoulders = new ModelRenderer(this, 68, 27);
    this.shoulders.func_78789_a(-6.0F, 0.0F, 0.0F, 13, 3, 3);
    this.shoulders.func_78793_a(0.0F, -11.0F, -1.0F);
    this.shoulders.func_78787_b(128, 64);
    this.shoulders.field_78809_i = true;
    setRotation(this.shoulders, 0.0F, 0.0F, 0.0F);
    this.spine = new ModelRenderer(this, 89, 0);
    this.spine.func_78789_a(-2.0F, 0.0F, -2.0F, 3, 20, 3);
    this.spine.func_78793_a(1.0F, -15.0F, 1.0F);
    this.spine.func_78787_b(128, 64);
    this.spine.field_78809_i = true;
    setRotation(this.spine, 0.0F, 0.0F, 0.0F);
    this.leftarm = new ModelRenderer(this, 101, 0);
    this.leftarm.func_78789_a(0.0F, -1.0F, -1.5F, 3, 16, 3);
    this.leftarm.func_78793_a(7.0F, -10.0F, 0.5F);
    this.leftarm.func_78787_b(128, 64);
    this.leftarm.field_78809_i = true;
    setRotation(this.leftarm, 0.0F, 0.0F, 0.0F);
    this.rightarm = new ModelRenderer(this, 101, 0);
    this.rightarm.func_78789_a(-3.0F, -1.0F, -1.5F, 3, 16, 3);
    this.rightarm.func_78793_a(-6.0F, -10.0F, 0.5F);
    this.rightarm.func_78787_b(128, 64);
    this.rightarm.field_78809_i = true;
    setRotation(this.rightarm, 0.0F, 0.0F, 0.0F);
    this.leftrib1 = new ModelRenderer(this, 60, 20);
    this.leftrib1.func_78789_a(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.leftrib1.func_78793_a(2.0F, -7.0F, 0.0F);
    this.leftrib1.func_78787_b(128, 64);
    this.leftrib1.field_78809_i = true;
    setRotation(this.leftrib1, 0.0F, 0.0F, 0.0F);
    this.leftrib12 = new ModelRenderer(this, 60, 20);
    this.leftrib12.func_78789_a(0.0F, 0.0F, 0.0F, 2, 1, 1);
    this.leftrib12.func_78793_a(4.0F, -7.0F, 0.0F);
    this.leftrib12.func_78787_b(128, 64);
    this.leftrib12.field_78809_i = true;
    setRotation(this.leftrib12, 0.0F, 1.570796F, 0.0F);
    this.leftrib13 = new ModelRenderer(this, 60, 20);
    this.leftrib13.func_78789_a(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.leftrib13.func_78793_a(2.0F, -7.0F, -3.0F);
    this.leftrib13.func_78787_b(128, 64);
    this.leftrib13.field_78809_i = true;
    setRotation(this.leftrib13, 0.0F, 0.0F, 0.0F);
    this.leftrib2 = new ModelRenderer(this, 60, 20);
    this.leftrib2.func_78789_a(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.leftrib2.func_78793_a(2.0F, -5.0F, 0.0F);
    this.leftrib2.func_78787_b(128, 64);
    this.leftrib2.field_78809_i = true;
    setRotation(this.leftrib2, 0.0F, 0.0F, 0.0F);
    this.leftrib22 = new ModelRenderer(this, 60, 20);
    this.leftrib22.func_78789_a(0.0F, 0.0F, 0.0F, 2, 1, 1);
    this.leftrib22.func_78793_a(4.0F, -5.0F, 0.0F);
    this.leftrib22.func_78787_b(128, 64);
    this.leftrib22.field_78809_i = true;
    setRotation(this.leftrib22, 0.0F, 1.570796F, 0.0F);
    this.leftrib23 = new ModelRenderer(this, 60, 20);
    this.leftrib23.func_78789_a(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.leftrib23.func_78793_a(2.0F, -5.0F, -3.0F);
    this.leftrib23.func_78787_b(128, 64);
    this.leftrib23.field_78809_i = true;
    setRotation(this.leftrib23, 0.0F, 0.0F, 0.0F);
    this.leftrib3 = new ModelRenderer(this, 60, 20);
    this.leftrib3.func_78789_a(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.leftrib3.func_78793_a(2.0F, -3.0F, 0.0F);
    this.leftrib3.func_78787_b(128, 64);
    this.leftrib3.field_78809_i = true;
    setRotation(this.leftrib3, 0.0F, 0.0F, 0.0F);
    this.leftrib32 = new ModelRenderer(this, 60, 20);
    this.leftrib32.func_78789_a(0.0F, 0.0F, 0.0F, 2, 1, 1);
    this.leftrib32.func_78793_a(4.0F, -3.0F, 0.0F);
    this.leftrib32.func_78787_b(128, 64);
    this.leftrib32.field_78809_i = true;
    setRotation(this.leftrib32, 0.0F, 1.570796F, 0.0F);
    this.leftrib33 = new ModelRenderer(this, 60, 20);
    this.leftrib33.func_78789_a(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.leftrib33.func_78793_a(2.0F, -3.0F, -3.0F);
    this.leftrib33.func_78787_b(128, 64);
    this.leftrib33.field_78809_i = true;
    setRotation(this.leftrib33, 0.0F, 0.0F, 0.0F);
    this.leftrib4 = new ModelRenderer(this, 60, 20);
    this.leftrib4.func_78789_a(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.leftrib4.func_78793_a(2.0F, -1.0F, 0.0F);
    this.leftrib4.func_78787_b(128, 64);
    this.leftrib4.field_78809_i = true;
    setRotation(this.leftrib4, 0.0F, 0.0F, 0.0F);
    this.leftrib42 = new ModelRenderer(this, 60, 20);
    this.leftrib42.func_78789_a(0.0F, 0.0F, 0.0F, 2, 1, 1);
    this.leftrib42.func_78793_a(4.0F, -1.0F, 0.0F);
    this.leftrib42.func_78787_b(128, 64);
    this.leftrib42.field_78809_i = true;
    setRotation(this.leftrib42, 0.0F, 1.570796F, 0.0F);
    this.leftrib43 = new ModelRenderer(this, 60, 20);
    this.leftrib43.func_78789_a(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.leftrib43.func_78793_a(2.0F, -1.0F, -3.0F);
    this.leftrib43.func_78787_b(128, 64);
    this.leftrib43.field_78809_i = true;
    setRotation(this.leftrib43, 0.0F, 0.0F, 0.0F);
    this.leftrib5 = new ModelRenderer(this, 60, 20);
    this.leftrib5.func_78789_a(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.leftrib5.func_78793_a(2.0F, 1.0F, 0.0F);
    this.leftrib5.func_78787_b(128, 64);
    this.leftrib5.field_78809_i = true;
    setRotation(this.leftrib5, 0.0F, 0.0F, 0.0F);
    this.leftrib52 = new ModelRenderer(this, 60, 20);
    this.leftrib52.func_78789_a(0.0F, 0.0F, 0.0F, 2, 1, 1);
    this.leftrib52.func_78793_a(4.0F, 1.0F, 0.0F);
    this.leftrib52.func_78787_b(128, 64);
    this.leftrib52.field_78809_i = true;
    setRotation(this.leftrib52, 0.0F, 1.570796F, 0.0F);
    this.leftrib53 = new ModelRenderer(this, 60, 20);
    this.leftrib53.func_78789_a(0.0F, 0.0F, 0.0F, 2, 1, 1);
    this.leftrib53.func_78793_a(3.0F, 1.0F, -3.0F);
    this.leftrib53.func_78787_b(128, 64);
    this.leftrib53.field_78809_i = true;
    setRotation(this.leftrib53, 0.0F, 0.0F, 0.0F);
    this.leftrib6 = new ModelRenderer(this, 60, 20);
    this.leftrib6.func_78789_a(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.leftrib6.func_78793_a(2.0F, 3.0F, 0.0F);
    this.leftrib6.func_78787_b(128, 64);
    this.leftrib6.field_78809_i = true;
    setRotation(this.leftrib6, 0.0F, 0.0F, 0.0F);
    this.leftrib62 = new ModelRenderer(this, 60, 20);
    this.leftrib62.func_78789_a(0.0F, 0.0F, 0.0F, 2, 1, 1);
    this.leftrib62.func_78793_a(4.0F, 3.0F, 0.0F);
    this.leftrib62.func_78787_b(128, 64);
    this.leftrib62.field_78809_i = true;
    setRotation(this.leftrib62, 0.0F, 1.570796F, 0.0F);
    this.leftrib63 = new ModelRenderer(this, 60, 20);
    this.leftrib63.func_78789_a(0.0F, 0.0F, 0.0F, 2, 1, 1);
    this.leftrib63.func_78793_a(3.0F, 3.0F, -3.0F);
    this.leftrib63.func_78787_b(128, 64);
    this.leftrib63.field_78809_i = true;
    setRotation(this.leftrib63, 0.0F, 0.0F, 0.0F);
    this.rightrib1 = new ModelRenderer(this, 60, 20);
    this.rightrib1.func_78789_a(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.rightrib1.func_78793_a(-4.0F, -7.0F, 0.0F);
    this.rightrib1.func_78787_b(128, 64);
    this.rightrib1.field_78809_i = true;
    setRotation(this.rightrib1, 0.0F, 0.0F, 0.0F);
    this.rightrib12 = new ModelRenderer(this, 60, 20);
    this.rightrib12.func_78789_a(0.0F, 0.0F, 0.0F, 2, 1, 1);
    this.rightrib12.func_78793_a(-4.0F, -7.0F, 0.0F);
    this.rightrib12.func_78787_b(128, 64);
    this.rightrib12.field_78809_i = true;
    setRotation(this.rightrib12, 0.0F, 1.570796F, 0.0F);
    this.rightrib13 = new ModelRenderer(this, 60, 20);
    this.rightrib13.func_78789_a(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.rightrib13.func_78793_a(-4.0F, -7.0F, -3.0F);
    this.rightrib13.func_78787_b(128, 64);
    this.rightrib13.field_78809_i = true;
    setRotation(this.rightrib13, 0.0F, 0.0F, 0.0F);
    this.rightrib2 = new ModelRenderer(this, 60, 20);
    this.rightrib2.func_78789_a(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.rightrib2.func_78793_a(-4.0F, -5.0F, 0.0F);
    this.rightrib2.func_78787_b(128, 64);
    this.rightrib2.field_78809_i = true;
    setRotation(this.rightrib2, 0.0F, 0.0F, 0.0F);
    this.rightrib22 = new ModelRenderer(this, 60, 20);
    this.rightrib22.func_78789_a(0.0F, 0.0F, 0.0F, 2, 1, 1);
    this.rightrib22.func_78793_a(-4.0F, -5.0F, 0.0F);
    this.rightrib22.func_78787_b(128, 64);
    this.rightrib22.field_78809_i = true;
    setRotation(this.rightrib22, 0.0F, 1.570796F, 0.0F);
    this.rightrib23 = new ModelRenderer(this, 60, 20);
    this.rightrib23.func_78789_a(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.rightrib23.func_78793_a(-4.0F, -5.0F, -3.0F);
    this.rightrib23.func_78787_b(128, 64);
    this.rightrib23.field_78809_i = true;
    setRotation(this.rightrib23, 0.0F, 0.0F, 0.0F);
    this.rightrib3 = new ModelRenderer(this, 60, 20);
    this.rightrib3.func_78789_a(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.rightrib3.func_78793_a(-4.0F, -3.0F, 0.0F);
    this.rightrib3.func_78787_b(128, 64);
    this.rightrib3.field_78809_i = true;
    setRotation(this.rightrib3, 0.0F, 0.0F, 0.0F);
    this.rightrib32 = new ModelRenderer(this, 60, 20);
    this.rightrib32.func_78789_a(0.0F, 0.0F, 0.0F, 2, 1, 1);
    this.rightrib32.func_78793_a(-4.0F, -3.0F, 0.0F);
    this.rightrib32.func_78787_b(128, 64);
    this.rightrib32.field_78809_i = true;
    setRotation(this.rightrib32, 0.0F, 1.570796F, 0.0F);
    this.rightrib33 = new ModelRenderer(this, 60, 20);
    this.rightrib33.func_78789_a(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.rightrib33.func_78793_a(-4.0F, -3.0F, -3.0F);
    this.rightrib33.func_78787_b(128, 64);
    this.rightrib33.field_78809_i = true;
    setRotation(this.rightrib33, 0.0F, 0.0F, 0.0F);
    this.rightrib4 = new ModelRenderer(this, 60, 20);
    this.rightrib4.func_78789_a(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.rightrib4.func_78793_a(-4.0F, -1.0F, 0.0F);
    this.rightrib4.func_78787_b(128, 64);
    this.rightrib4.field_78809_i = true;
    setRotation(this.rightrib4, 0.0F, 0.0F, 0.0F);
    this.rightrib42 = new ModelRenderer(this, 60, 20);
    this.rightrib42.func_78789_a(0.0F, 0.0F, 0.0F, 2, 1, 1);
    this.rightrib42.func_78793_a(-4.0F, -1.0F, 0.0F);
    this.rightrib42.func_78787_b(128, 64);
    this.rightrib42.field_78809_i = true;
    setRotation(this.rightrib42, 0.0F, 1.570796F, 0.0F);
    this.rightrib43 = new ModelRenderer(this, 60, 20);
    this.rightrib43.func_78789_a(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.rightrib43.func_78793_a(-4.0F, -1.0F, -3.0F);
    this.rightrib43.func_78787_b(128, 64);
    this.rightrib43.field_78809_i = true;
    setRotation(this.rightrib43, 0.0F, 0.0F, 0.0F);
    this.rightrib5 = new ModelRenderer(this, 60, 20);
    this.rightrib5.func_78789_a(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.rightrib5.func_78793_a(-4.0F, 1.0F, 0.0F);
    this.rightrib5.func_78787_b(128, 64);
    this.rightrib5.field_78809_i = true;
    setRotation(this.rightrib5, 0.0F, 0.0F, 0.0F);
    this.rightrib52 = new ModelRenderer(this, 60, 20);
    this.rightrib52.func_78789_a(0.0F, 0.0F, 0.0F, 2, 1, 1);
    this.rightrib52.func_78793_a(-4.0F, 1.0F, 0.0F);
    this.rightrib52.func_78787_b(128, 64);
    this.rightrib52.field_78809_i = true;
    setRotation(this.rightrib52, 0.0F, 1.570796F, 0.0F);
    this.rightrib53 = new ModelRenderer(this, 60, 20);
    this.rightrib53.func_78789_a(0.0F, 0.0F, 0.0F, 2, 1, 1);
    this.rightrib53.func_78793_a(-4.0F, 1.0F, -3.0F);
    this.rightrib53.func_78787_b(128, 64);
    this.rightrib53.field_78809_i = true;
    setRotation(this.rightrib53, 0.0F, 0.0F, 0.0F);
    this.rightrib6 = new ModelRenderer(this, 60, 20);
    this.rightrib6.func_78789_a(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.rightrib6.func_78793_a(-4.0F, 3.0F, 0.0F);
    this.rightrib6.func_78787_b(128, 64);
    this.rightrib6.field_78809_i = true;
    setRotation(this.rightrib6, 0.0F, 0.0F, 0.0F);
    this.rightrib62 = new ModelRenderer(this, 60, 20);
    this.rightrib62.func_78789_a(0.0F, 0.0F, 0.0F, 2, 1, 1);
    this.rightrib62.func_78793_a(-4.0F, 3.0F, 0.0F);
    this.rightrib62.func_78787_b(128, 64);
    this.rightrib62.field_78809_i = true;
    setRotation(this.rightrib62, 0.0F, 1.570796F, 0.0F);
    this.rightrib63 = new ModelRenderer(this, 60, 20);
    this.rightrib63.func_78789_a(0.0F, 0.0F, 0.0F, 2, 1, 1);
    this.rightrib63.func_78793_a(-4.0F, 3.0F, -3.0F);
    this.rightrib63.func_78787_b(128, 64);
    this.rightrib63.field_78809_i = true;
    setRotation(this.rightrib63, 0.0F, 0.0F, 0.0F);
    this.sternum = new ModelRenderer(this, 113, 11);
    this.sternum.func_78789_a(0.0F, 0.0F, 0.0F, 3, 7, 1);
    this.sternum.func_78793_a(-1.0F, -7.0F, -3.0F);
    this.sternum.func_78787_b(128, 64);
    this.sternum.field_78809_i = true;
    setRotation(this.sternum, 0.0F, 0.0F, 0.0F);
    this.pelvis = new ModelRenderer(this, 99, 21);
    this.pelvis.func_78789_a(0.0F, 0.0F, 0.0F, 9, 3, 3);
    this.pelvis.func_78793_a(-4.0F, 5.0F, -1.0F);
    this.pelvis.func_78787_b(128, 64);
    this.pelvis.field_78809_i = true;
    setRotation(this.pelvis, 0.0F, 0.0F, 0.0F);
    this.leftleg = new ModelRenderer(this, 54, 22);
    this.leftleg.func_78789_a(-1.5F, 0.0F, -1.5F, 3, 16, 3);
    this.leftleg.func_78793_a(3.5F, 8.0F, 0.5F);
    this.leftleg.func_78787_b(128, 64);
    this.leftleg.field_78809_i = true;
    setRotation(this.leftleg, 0.0F, 0.0F, 0.0F);
    this.rightleg = new ModelRenderer(this, 54, 22);
    this.rightleg.func_78789_a(-1.5F, 0.0F, -1.5F, 3, 16, 3);
    this.rightleg.func_78793_a(-2.5F, 8.0F, 0.5F);
    this.rightleg.func_78787_b(128, 64);
    this.rightleg.field_78809_i = true;
    setRotation(this.rightleg, 0.0F, 0.0F, 0.0F);
    this.head.func_78792_a(this.leftjaw);
    this.head.func_78792_a(this.rightjaw);
    this.head.func_78792_a(this.jaw1);
    this.head.func_78792_a(this.jaw1);
    this.head.func_78792_a(this.jaw2);
    this.head.func_78792_a(this.jaw3);
    this.head.func_78792_a(this.tooth1);
    this.head.func_78792_a(this.tooth2);
    this.head.func_78792_a(this.tooth3);
    this.head.func_78792_a(this.tooth4);
    this.head.func_78792_a(this.tooth5);
    if (renderCudgel)
      this.rightarm.func_78792_a(this.base1); 
    this.base1.func_78792_a(this.base2);
    this.base1.func_78792_a(this.base3);
    this.base1.func_78792_a(this.base4);
    this.base1.func_78792_a(this.spike1);
    this.base1.func_78792_a(this.spike2);
    this.base1.func_78792_a(this.spike3);
    this.base1.func_78792_a(this.spike4);
    this.base1.func_78792_a(this.spike5);
    this.base1.func_78792_a(this.spike6);
    this.base1.func_78792_a(this.spike7);
    this.base1.func_78792_a(this.spike8);
    this.base1.func_78792_a(this.spike9);
    this.base1.func_78792_a(this.spike10);
    this.base1.func_78792_a(this.spike11);
    this.base1.func_78792_a(this.thing1);
    this.base1.func_78792_a(this.thing2);
  }
  
  public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    super.func_78088_a(entity, f, f1, f2, f3, f4, f5);
    func_78087_a(f, f1, f2, f3, f4, f5, entity);
    this.head.func_78785_a(f5);
    this.shoulders.func_78785_a(f5);
    this.spine.func_78785_a(f5);
    this.leftarm.func_78785_a(f5);
    this.rightarm.func_78785_a(f5);
    this.leftrib1.func_78785_a(f5);
    this.leftrib12.func_78785_a(f5);
    this.leftrib13.func_78785_a(f5);
    this.leftrib2.func_78785_a(f5);
    this.leftrib22.func_78785_a(f5);
    this.leftrib23.func_78785_a(f5);
    this.leftrib3.func_78785_a(f5);
    this.leftrib32.func_78785_a(f5);
    this.leftrib33.func_78785_a(f5);
    this.leftrib4.func_78785_a(f5);
    this.leftrib42.func_78785_a(f5);
    this.leftrib43.func_78785_a(f5);
    this.leftrib5.func_78785_a(f5);
    this.leftrib52.func_78785_a(f5);
    this.leftrib53.func_78785_a(f5);
    this.leftrib6.func_78785_a(f5);
    this.leftrib62.func_78785_a(f5);
    this.leftrib63.func_78785_a(f5);
    this.rightrib1.func_78785_a(f5);
    this.rightrib12.func_78785_a(f5);
    this.rightrib13.func_78785_a(f5);
    this.rightrib2.func_78785_a(f5);
    this.rightrib22.func_78785_a(f5);
    this.rightrib23.func_78785_a(f5);
    this.rightrib3.func_78785_a(f5);
    this.rightrib32.func_78785_a(f5);
    this.rightrib33.func_78785_a(f5);
    this.rightrib4.func_78785_a(f5);
    this.rightrib42.func_78785_a(f5);
    this.rightrib43.func_78785_a(f5);
    this.rightrib5.func_78785_a(f5);
    this.rightrib52.func_78785_a(f5);
    this.rightrib53.func_78785_a(f5);
    this.rightrib6.func_78785_a(f5);
    this.rightrib62.func_78785_a(f5);
    this.rightrib63.func_78785_a(f5);
    this.sternum.func_78785_a(f5);
    this.pelvis.func_78785_a(f5);
    this.leftleg.func_78785_a(f5);
    this.rightleg.func_78785_a(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z) {
    model.field_78795_f = x;
    model.field_78796_g = y;
    model.field_78808_h = z;
  }
  
  public void func_78087_a(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
    EntityTameBase entity = (EntityTameBase)par7Entity;
    this.head.field_78796_g = par4 / 57.295776F;
    this.head.field_78795_f = par5 / 57.295776F;
    this.rightarm.field_78795_f = MathHelper.func_76134_b(par1 * 0.6662F + 3.1415927F) * 2.0F * par2 * 0.5F;
    this.leftarm.field_78795_f = MathHelper.func_76134_b(par1 * 0.6662F) * 2.0F * par2 * 0.5F;
    this.rightarm.field_78808_h = 0.0F;
    this.leftarm.field_78808_h = 0.0F;
    this.rightleg.field_78795_f = MathHelper.func_76134_b(par1 * 0.6662F) * 1.4F * par2;
    this.rightleg.field_78796_g = 0.0F;
    this.leftleg.field_78795_f = MathHelper.func_76134_b(par1 * 0.6662F + 3.1415927F) * 1.4F * par2;
    this.leftleg.field_78796_g = 0.0F;
    if (this.field_78093_q) {
      this.rightarm.field_78795_f += -0.62831855F;
      this.leftarm.field_78795_f += -0.62831855F;
      this.rightleg.field_78795_f = -1.2566371F;
      this.leftleg.field_78795_f = -1.2566371F;
      this.rightleg.field_78796_g = 0.31415927F;
      this.leftleg.field_78796_g = -0.31415927F;
    } 
    this.rightarm.field_78796_g = 0.0F;
    this.leftarm.field_78796_g = 0.0F;
    if (this.field_78095_p > -9990.0F) {
      float f6 = this.field_78095_p;
      this.shoulders.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.spine.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib1.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib12.field_78795_f = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib13.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib2.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib22.field_78795_f = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib23.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib3.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib32.field_78795_f = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib33.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib4.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib42.field_78795_f = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib43.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib5.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib52.field_78795_f = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib53.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib6.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib62.field_78795_f = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib63.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib1.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib12.field_78795_f = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib13.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib2.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib22.field_78795_f = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib23.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib3.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib32.field_78795_f = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib33.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib4.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib42.field_78795_f = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib43.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib5.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib52.field_78795_f = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib53.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib6.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib62.field_78795_f = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib63.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.sternum.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightarm.field_78796_g += this.spine.field_78796_g;
      this.leftarm.field_78796_g += this.spine.field_78796_g;
      this.leftarm.field_78795_f += this.spine.field_78796_g;
      f6 = 1.0F - this.field_78095_p;
      f6 *= f6;
      f6 *= f6;
      f6 = 1.0F - f6;
      float f7 = MathHelper.func_76126_a(f6 * 3.1415927F);
      float f8 = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F) * -(this.head.field_78795_f - 0.7F) * 0.75F;
      this.rightarm.field_78795_f = (float)(this.rightarm.field_78795_f - f7 * 1.2D + f8);
      this.rightarm.field_78796_g += this.spine.field_78796_g * 2.0F;
      this.rightarm.field_78808_h = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F) * -0.4F;
    } 
    if (entity.func_70027_ad() && !entity.isArmsRaised()) {
      this.head.field_78795_f -= 0.5F;
      this.head.field_78796_g += MathHelper.func_76134_b(par3 * 0.6662F) * 0.5F;
      this.rightarm.field_78798_e = 0.0F;
      this.rightarm.field_78800_c = -5.0F;
      this.leftarm.field_78798_e = 0.0F;
      this.leftarm.field_78800_c = 5.0F;
      this.rightarm.field_78795_f = -MathHelper.func_76134_b(par3 * 0.6662F) * 0.5F;
      this.leftarm.field_78795_f = MathHelper.func_76134_b(par3 * 0.6662F) * 0.5F;
      this.rightarm.field_78808_h = 2.3561945F;
      this.leftarm.field_78808_h = -2.3561945F;
      this.rightarm.field_78796_g = 0.0F;
      this.leftarm.field_78796_g = 0.0F;
    } 
    if (!entity.getCurrentBook().func_190926_b()) {
      this.rightarm.field_78796_g = (entity.bookSpread - 1.0F) * 0.5F;
      this.leftarm.field_78796_g = (-entity.bookSpread + 1.0F) * 0.5F;
      this.rightarm.field_78808_h = 0.0F;
      this.leftarm.field_78808_h = 0.0F;
      this.rightarm.field_78795_f = -1.25F + 0.1F + MathHelper.func_76126_a(entity.field_70173_aa * 0.1F) * 0.01F;
      this.leftarm.field_78795_f = -1.25F + 0.1F + MathHelper.func_76126_a(entity.field_70173_aa * 0.1F) * 0.01F;
    } 
  }
}
