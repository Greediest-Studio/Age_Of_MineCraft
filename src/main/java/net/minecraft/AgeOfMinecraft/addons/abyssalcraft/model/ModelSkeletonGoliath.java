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
    this.textureWidth = 128;
    this.textureHeight = 64;
    this.base1 = new ModelRenderer(this, 0, 0);
    this.base1.addBox(-1.0F, -1.0F, -7.0F, 2, 2, 10);
    this.base1.setRotationPoint(-1.5F, 13.0F, 0.0F);
    this.base1.setTextureSize(128, 64);
    this.base1.mirror = true;
    setRotation(this.base1, 0.0F, 0.0F, 0.0F);
    this.base2 = new ModelRenderer(this, 24, 2);
    this.base2.addBox(0.0F, 0.0F, 0.0F, 3, 3, 3);
    this.base2.setRotationPoint(-1.5F, -2.5F, -8.0F);
    this.base2.setTextureSize(128, 64);
    this.base2.mirror = true;
    setRotation(this.base2, -0.4089647F, 0.0F, 0.0F);
    this.base3 = new ModelRenderer(this, 24, 8);
    this.base3.addBox(0.0F, 0.0F, 0.0F, 4, 4, 6);
    this.base3.setRotationPoint(-0.5F, -5.0F, -13.0F);
    this.base3.setTextureSize(128, 64);
    this.base3.mirror = true;
    setRotation(this.base3, -0.3717861F, 0.0F, 0.2602503F);
    this.base4 = new ModelRenderer(this, 0, 12);
    this.base4.addBox(0.0F, 0.0F, 0.0F, 5, 5, 7);
    this.base4.setRotationPoint(-1.0F, -9.0F, -15.0F);
    this.base4.setTextureSize(128, 64);
    this.base4.mirror = true;
    setRotation(this.base4, -0.8551081F, 0.0743572F, 0.0F);
    this.spike1 = new ModelRenderer(this, 27, 0);
    this.spike1.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.spike1.setRotationPoint(1.0F, -1.0F, -8.0F);
    this.spike1.setTextureSize(128, 64);
    this.spike1.mirror = true;
    setRotation(this.spike1, 0.0F, 0.0F, 0.0F);
    this.spike2 = new ModelRenderer(this, 27, 0);
    this.spike2.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.spike2.setRotationPoint(-4.0F, -1.0F, -8.0F);
    this.spike2.setTextureSize(128, 64);
    this.spike2.mirror = true;
    setRotation(this.spike2, 0.0F, 0.0F, 0.0F);
    this.spike3 = new ModelRenderer(this, 48, 0);
    this.spike3.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.spike3.setRotationPoint(-0.5F, -5.0F, -7.0F);
    this.spike3.setTextureSize(128, 64);
    this.spike3.mirror = true;
    setRotation(this.spike3, -0.3346075F, 0.0F, 0.0F);
    this.spike4 = new ModelRenderer(this, 48, 0);
    this.spike4.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.spike4.setRotationPoint(-0.5F, 0.0F, -9.0F);
    this.spike4.setTextureSize(128, 64);
    this.spike4.mirror = true;
    setRotation(this.spike4, 0.0F, 0.0F, 0.0F);
    this.spike5 = new ModelRenderer(this, 48, 0);
    this.spike5.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1);
    this.spike5.setRotationPoint(1.0F, -9.0F, -11.0F);
    this.spike5.setTextureSize(128, 64);
    this.spike5.mirror = true;
    setRotation(this.spike5, -0.8179294F, 0.0F, 0.0F);
    this.spike6 = new ModelRenderer(this, 26, 0);
    this.spike6.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1);
    this.spike6.setRotationPoint(-4.0F, -5.0F, -13.0F);
    this.spike6.setTextureSize(128, 64);
    this.spike6.mirror = true;
    setRotation(this.spike6, -0.3717861F, 0.2602503F, 0.0F);
    this.spike7 = new ModelRenderer(this, 26, 0);
    this.spike7.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1);
    this.spike7.setRotationPoint(-4.0F, -9.0F, -14.0F);
    this.spike7.setTextureSize(128, 64);
    this.spike7.mirror = true;
    setRotation(this.spike7, -0.4461433F, 0.4089647F, 0.5576792F);
    this.spike8 = new ModelRenderer(this, 26, 0);
    this.spike8.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1);
    this.spike8.setRotationPoint(4.0F, -3.0F, -15.0F);
    this.spike8.setTextureSize(128, 64);
    this.spike8.mirror = true;
    setRotation(this.spike8, -0.0371786F, -0.7807508F, -0.4461433F);
    this.spike9 = new ModelRenderer(this, 48, 0);
    this.spike9.addBox(0.0F, 0.0F, 0.0F, 1, 5, 1);
    this.spike9.setRotationPoint(0.0F, -4.0F, -15.0F);
    this.spike9.setTextureSize(128, 64);
    this.spike9.mirror = true;
    setRotation(this.spike9, -0.9294653F, -0.6320364F, 0.8551081F);
    this.spike10 = new ModelRenderer(this, 36, 0);
    this.spike10.addBox(0.0F, 0.0F, 0.0F, 1, 1, 5);
    this.spike10.setRotationPoint(3.0F, -9.0F, -20.0F);
    this.spike10.setTextureSize(128, 64);
    this.spike10.mirror = true;
    setRotation(this.spike10, -0.3717861F, 0.0F, 0.7435722F);
    this.spike11 = new ModelRenderer(this, 24, 0);
    this.spike11.addBox(0.0F, 0.0F, 0.0F, 5, 1, 1);
    this.spike11.setRotationPoint(3.0F, -4.0F, -16.0F);
    this.spike11.setTextureSize(128, 64);
    this.spike11.mirror = true;
    setRotation(this.spike11, 0.0F, 0.0F, -0.5205006F);
    this.thing1 = new ModelRenderer(this, 24, 18);
    this.thing1.addBox(0.0F, 0.0F, 0.0F, 4, 4, 1);
    this.thing1.setRotationPoint(-2.0F, -2.0F, -6.0F);
    this.thing1.setTextureSize(128, 64);
    this.thing1.mirror = true;
    setRotation(this.thing1, 0.0F, 0.0F, 0.0F);
    this.thing2 = new ModelRenderer(this, 34, 18);
    this.thing2.addBox(0.0F, 0.0F, 0.0F, 6, 6, 1);
    this.thing2.setRotationPoint(-1.5F, -5.0F, -10.0F);
    this.thing2.setTextureSize(128, 64);
    this.thing2.mirror = true;
    setRotation(this.thing2, -0.2602503F, 0.0F, 0.260246F);
    this.head = new ModelRenderer(this, 53, 0);
    this.head.addBox(-4.0F, -9.0F, -4.0F, 9, 9, 9);
    this.head.setRotationPoint(0.0F, -15.0F, 0.0F);
    this.head.setTextureSize(128, 64);
    this.head.mirror = true;
    setRotation(this.head, 0.0F, 0.0F, 0.0F);
    this.leftjaw = new ModelRenderer(this, 49, 16);
    this.leftjaw.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.leftjaw.setRotationPoint(4.0F, 0.0F, 4.0F);
    this.leftjaw.setTextureSize(128, 64);
    this.leftjaw.mirror = true;
    setRotation(this.leftjaw, 0.0F, 0.0F, 0.0F);
    this.rightjaw = new ModelRenderer(this, 49, 16);
    this.rightjaw.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
    this.rightjaw.setRotationPoint(-4.0F, 0.0F, 4.0F);
    this.rightjaw.setTextureSize(128, 64);
    this.rightjaw.mirror = true;
    setRotation(this.rightjaw, 0.0F, 0.0F, 0.0F);
    this.jaw1 = new ModelRenderer(this, 53, 18);
    this.jaw1.addBox(-4.0F, 0.0F, -1.0F, 9, 1, 1);
    this.jaw1.setRotationPoint(0.0F, 2.0F, -3.0F);
    this.jaw1.setTextureSize(128, 64);
    this.jaw1.mirror = true;
    setRotation(this.jaw1, 0.0F, 0.0F, 0.0F);
    this.jaw2 = new ModelRenderer(this, 73, 18);
    this.jaw2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 8);
    this.jaw2.setRotationPoint(4.0F, 2.0F, -4.0F);
    this.jaw2.setTextureSize(128, 64);
    this.jaw2.mirror = true;
    setRotation(this.jaw2, 0.0F, 0.0F, 0.0F);
    this.jaw3 = new ModelRenderer(this, 73, 18);
    this.jaw3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 8);
    this.jaw3.setRotationPoint(-4.0F, 2.0F, -4.0F);
    this.jaw3.setTextureSize(128, 64);
    this.jaw3.mirror = true;
    setRotation(this.jaw3, 0.0F, 0.0F, 0.0F);
    this.tooth1 = new ModelRenderer(this, 49, 20);
    this.tooth1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.tooth1.setRotationPoint(4.0F, 1.0F, -4.0F);
    this.tooth1.setTextureSize(128, 64);
    this.tooth1.mirror = true;
    setRotation(this.tooth1, 0.0F, 0.0F, 0.0F);
    this.tooth2 = new ModelRenderer(this, 49, 20);
    this.tooth2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.tooth2.setRotationPoint(2.0F, 1.0F, -4.0F);
    this.tooth2.setTextureSize(128, 64);
    this.tooth2.mirror = true;
    setRotation(this.tooth2, 0.0F, 0.0F, 0.0F);
    this.tooth3 = new ModelRenderer(this, 49, 20);
    this.tooth3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.tooth3.setRotationPoint(0.0F, 1.0F, -4.0F);
    this.tooth3.setTextureSize(128, 64);
    this.tooth3.mirror = true;
    setRotation(this.tooth3, 0.0F, 0.0F, 0.0F);
    this.tooth4 = new ModelRenderer(this, 49, 20);
    this.tooth4.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.tooth4.setRotationPoint(-2.0F, 1.0F, -4.0F);
    this.tooth4.setTextureSize(128, 64);
    this.tooth4.mirror = true;
    setRotation(this.tooth4, 0.0F, 0.0F, 0.0F);
    this.tooth5 = new ModelRenderer(this, 49, 20);
    this.tooth5.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
    this.tooth5.setRotationPoint(-4.0F, 1.0F, -4.0F);
    this.tooth5.setTextureSize(128, 64);
    this.tooth5.mirror = true;
    setRotation(this.tooth5, 0.0F, 0.0F, 0.0F);
    this.shoulders = new ModelRenderer(this, 68, 27);
    this.shoulders.addBox(-6.0F, 0.0F, 0.0F, 13, 3, 3);
    this.shoulders.setRotationPoint(0.0F, -11.0F, -1.0F);
    this.shoulders.setTextureSize(128, 64);
    this.shoulders.mirror = true;
    setRotation(this.shoulders, 0.0F, 0.0F, 0.0F);
    this.spine = new ModelRenderer(this, 89, 0);
    this.spine.addBox(-2.0F, 0.0F, -2.0F, 3, 20, 3);
    this.spine.setRotationPoint(1.0F, -15.0F, 1.0F);
    this.spine.setTextureSize(128, 64);
    this.spine.mirror = true;
    setRotation(this.spine, 0.0F, 0.0F, 0.0F);
    this.leftarm = new ModelRenderer(this, 101, 0);
    this.leftarm.addBox(0.0F, -1.0F, -1.5F, 3, 16, 3);
    this.leftarm.setRotationPoint(7.0F, -10.0F, 0.5F);
    this.leftarm.setTextureSize(128, 64);
    this.leftarm.mirror = true;
    setRotation(this.leftarm, 0.0F, 0.0F, 0.0F);
    this.rightarm = new ModelRenderer(this, 101, 0);
    this.rightarm.addBox(-3.0F, -1.0F, -1.5F, 3, 16, 3);
    this.rightarm.setRotationPoint(-6.0F, -10.0F, 0.5F);
    this.rightarm.setTextureSize(128, 64);
    this.rightarm.mirror = true;
    setRotation(this.rightarm, 0.0F, 0.0F, 0.0F);
    this.leftrib1 = new ModelRenderer(this, 60, 20);
    this.leftrib1.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.leftrib1.setRotationPoint(2.0F, -7.0F, 0.0F);
    this.leftrib1.setTextureSize(128, 64);
    this.leftrib1.mirror = true;
    setRotation(this.leftrib1, 0.0F, 0.0F, 0.0F);
    this.leftrib12 = new ModelRenderer(this, 60, 20);
    this.leftrib12.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1);
    this.leftrib12.setRotationPoint(4.0F, -7.0F, 0.0F);
    this.leftrib12.setTextureSize(128, 64);
    this.leftrib12.mirror = true;
    setRotation(this.leftrib12, 0.0F, 1.570796F, 0.0F);
    this.leftrib13 = new ModelRenderer(this, 60, 20);
    this.leftrib13.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.leftrib13.setRotationPoint(2.0F, -7.0F, -3.0F);
    this.leftrib13.setTextureSize(128, 64);
    this.leftrib13.mirror = true;
    setRotation(this.leftrib13, 0.0F, 0.0F, 0.0F);
    this.leftrib2 = new ModelRenderer(this, 60, 20);
    this.leftrib2.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.leftrib2.setRotationPoint(2.0F, -5.0F, 0.0F);
    this.leftrib2.setTextureSize(128, 64);
    this.leftrib2.mirror = true;
    setRotation(this.leftrib2, 0.0F, 0.0F, 0.0F);
    this.leftrib22 = new ModelRenderer(this, 60, 20);
    this.leftrib22.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1);
    this.leftrib22.setRotationPoint(4.0F, -5.0F, 0.0F);
    this.leftrib22.setTextureSize(128, 64);
    this.leftrib22.mirror = true;
    setRotation(this.leftrib22, 0.0F, 1.570796F, 0.0F);
    this.leftrib23 = new ModelRenderer(this, 60, 20);
    this.leftrib23.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.leftrib23.setRotationPoint(2.0F, -5.0F, -3.0F);
    this.leftrib23.setTextureSize(128, 64);
    this.leftrib23.mirror = true;
    setRotation(this.leftrib23, 0.0F, 0.0F, 0.0F);
    this.leftrib3 = new ModelRenderer(this, 60, 20);
    this.leftrib3.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.leftrib3.setRotationPoint(2.0F, -3.0F, 0.0F);
    this.leftrib3.setTextureSize(128, 64);
    this.leftrib3.mirror = true;
    setRotation(this.leftrib3, 0.0F, 0.0F, 0.0F);
    this.leftrib32 = new ModelRenderer(this, 60, 20);
    this.leftrib32.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1);
    this.leftrib32.setRotationPoint(4.0F, -3.0F, 0.0F);
    this.leftrib32.setTextureSize(128, 64);
    this.leftrib32.mirror = true;
    setRotation(this.leftrib32, 0.0F, 1.570796F, 0.0F);
    this.leftrib33 = new ModelRenderer(this, 60, 20);
    this.leftrib33.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.leftrib33.setRotationPoint(2.0F, -3.0F, -3.0F);
    this.leftrib33.setTextureSize(128, 64);
    this.leftrib33.mirror = true;
    setRotation(this.leftrib33, 0.0F, 0.0F, 0.0F);
    this.leftrib4 = new ModelRenderer(this, 60, 20);
    this.leftrib4.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.leftrib4.setRotationPoint(2.0F, -1.0F, 0.0F);
    this.leftrib4.setTextureSize(128, 64);
    this.leftrib4.mirror = true;
    setRotation(this.leftrib4, 0.0F, 0.0F, 0.0F);
    this.leftrib42 = new ModelRenderer(this, 60, 20);
    this.leftrib42.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1);
    this.leftrib42.setRotationPoint(4.0F, -1.0F, 0.0F);
    this.leftrib42.setTextureSize(128, 64);
    this.leftrib42.mirror = true;
    setRotation(this.leftrib42, 0.0F, 1.570796F, 0.0F);
    this.leftrib43 = new ModelRenderer(this, 60, 20);
    this.leftrib43.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.leftrib43.setRotationPoint(2.0F, -1.0F, -3.0F);
    this.leftrib43.setTextureSize(128, 64);
    this.leftrib43.mirror = true;
    setRotation(this.leftrib43, 0.0F, 0.0F, 0.0F);
    this.leftrib5 = new ModelRenderer(this, 60, 20);
    this.leftrib5.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.leftrib5.setRotationPoint(2.0F, 1.0F, 0.0F);
    this.leftrib5.setTextureSize(128, 64);
    this.leftrib5.mirror = true;
    setRotation(this.leftrib5, 0.0F, 0.0F, 0.0F);
    this.leftrib52 = new ModelRenderer(this, 60, 20);
    this.leftrib52.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1);
    this.leftrib52.setRotationPoint(4.0F, 1.0F, 0.0F);
    this.leftrib52.setTextureSize(128, 64);
    this.leftrib52.mirror = true;
    setRotation(this.leftrib52, 0.0F, 1.570796F, 0.0F);
    this.leftrib53 = new ModelRenderer(this, 60, 20);
    this.leftrib53.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1);
    this.leftrib53.setRotationPoint(3.0F, 1.0F, -3.0F);
    this.leftrib53.setTextureSize(128, 64);
    this.leftrib53.mirror = true;
    setRotation(this.leftrib53, 0.0F, 0.0F, 0.0F);
    this.leftrib6 = new ModelRenderer(this, 60, 20);
    this.leftrib6.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.leftrib6.setRotationPoint(2.0F, 3.0F, 0.0F);
    this.leftrib6.setTextureSize(128, 64);
    this.leftrib6.mirror = true;
    setRotation(this.leftrib6, 0.0F, 0.0F, 0.0F);
    this.leftrib62 = new ModelRenderer(this, 60, 20);
    this.leftrib62.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1);
    this.leftrib62.setRotationPoint(4.0F, 3.0F, 0.0F);
    this.leftrib62.setTextureSize(128, 64);
    this.leftrib62.mirror = true;
    setRotation(this.leftrib62, 0.0F, 1.570796F, 0.0F);
    this.leftrib63 = new ModelRenderer(this, 60, 20);
    this.leftrib63.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1);
    this.leftrib63.setRotationPoint(3.0F, 3.0F, -3.0F);
    this.leftrib63.setTextureSize(128, 64);
    this.leftrib63.mirror = true;
    setRotation(this.leftrib63, 0.0F, 0.0F, 0.0F);
    this.rightrib1 = new ModelRenderer(this, 60, 20);
    this.rightrib1.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.rightrib1.setRotationPoint(-4.0F, -7.0F, 0.0F);
    this.rightrib1.setTextureSize(128, 64);
    this.rightrib1.mirror = true;
    setRotation(this.rightrib1, 0.0F, 0.0F, 0.0F);
    this.rightrib12 = new ModelRenderer(this, 60, 20);
    this.rightrib12.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1);
    this.rightrib12.setRotationPoint(-4.0F, -7.0F, 0.0F);
    this.rightrib12.setTextureSize(128, 64);
    this.rightrib12.mirror = true;
    setRotation(this.rightrib12, 0.0F, 1.570796F, 0.0F);
    this.rightrib13 = new ModelRenderer(this, 60, 20);
    this.rightrib13.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.rightrib13.setRotationPoint(-4.0F, -7.0F, -3.0F);
    this.rightrib13.setTextureSize(128, 64);
    this.rightrib13.mirror = true;
    setRotation(this.rightrib13, 0.0F, 0.0F, 0.0F);
    this.rightrib2 = new ModelRenderer(this, 60, 20);
    this.rightrib2.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.rightrib2.setRotationPoint(-4.0F, -5.0F, 0.0F);
    this.rightrib2.setTextureSize(128, 64);
    this.rightrib2.mirror = true;
    setRotation(this.rightrib2, 0.0F, 0.0F, 0.0F);
    this.rightrib22 = new ModelRenderer(this, 60, 20);
    this.rightrib22.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1);
    this.rightrib22.setRotationPoint(-4.0F, -5.0F, 0.0F);
    this.rightrib22.setTextureSize(128, 64);
    this.rightrib22.mirror = true;
    setRotation(this.rightrib22, 0.0F, 1.570796F, 0.0F);
    this.rightrib23 = new ModelRenderer(this, 60, 20);
    this.rightrib23.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.rightrib23.setRotationPoint(-4.0F, -5.0F, -3.0F);
    this.rightrib23.setTextureSize(128, 64);
    this.rightrib23.mirror = true;
    setRotation(this.rightrib23, 0.0F, 0.0F, 0.0F);
    this.rightrib3 = new ModelRenderer(this, 60, 20);
    this.rightrib3.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.rightrib3.setRotationPoint(-4.0F, -3.0F, 0.0F);
    this.rightrib3.setTextureSize(128, 64);
    this.rightrib3.mirror = true;
    setRotation(this.rightrib3, 0.0F, 0.0F, 0.0F);
    this.rightrib32 = new ModelRenderer(this, 60, 20);
    this.rightrib32.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1);
    this.rightrib32.setRotationPoint(-4.0F, -3.0F, 0.0F);
    this.rightrib32.setTextureSize(128, 64);
    this.rightrib32.mirror = true;
    setRotation(this.rightrib32, 0.0F, 1.570796F, 0.0F);
    this.rightrib33 = new ModelRenderer(this, 60, 20);
    this.rightrib33.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.rightrib33.setRotationPoint(-4.0F, -3.0F, -3.0F);
    this.rightrib33.setTextureSize(128, 64);
    this.rightrib33.mirror = true;
    setRotation(this.rightrib33, 0.0F, 0.0F, 0.0F);
    this.rightrib4 = new ModelRenderer(this, 60, 20);
    this.rightrib4.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.rightrib4.setRotationPoint(-4.0F, -1.0F, 0.0F);
    this.rightrib4.setTextureSize(128, 64);
    this.rightrib4.mirror = true;
    setRotation(this.rightrib4, 0.0F, 0.0F, 0.0F);
    this.rightrib42 = new ModelRenderer(this, 60, 20);
    this.rightrib42.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1);
    this.rightrib42.setRotationPoint(-4.0F, -1.0F, 0.0F);
    this.rightrib42.setTextureSize(128, 64);
    this.rightrib42.mirror = true;
    setRotation(this.rightrib42, 0.0F, 1.570796F, 0.0F);
    this.rightrib43 = new ModelRenderer(this, 60, 20);
    this.rightrib43.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.rightrib43.setRotationPoint(-4.0F, -1.0F, -3.0F);
    this.rightrib43.setTextureSize(128, 64);
    this.rightrib43.mirror = true;
    setRotation(this.rightrib43, 0.0F, 0.0F, 0.0F);
    this.rightrib5 = new ModelRenderer(this, 60, 20);
    this.rightrib5.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.rightrib5.setRotationPoint(-4.0F, 1.0F, 0.0F);
    this.rightrib5.setTextureSize(128, 64);
    this.rightrib5.mirror = true;
    setRotation(this.rightrib5, 0.0F, 0.0F, 0.0F);
    this.rightrib52 = new ModelRenderer(this, 60, 20);
    this.rightrib52.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1);
    this.rightrib52.setRotationPoint(-4.0F, 1.0F, 0.0F);
    this.rightrib52.setTextureSize(128, 64);
    this.rightrib52.mirror = true;
    setRotation(this.rightrib52, 0.0F, 1.570796F, 0.0F);
    this.rightrib53 = new ModelRenderer(this, 60, 20);
    this.rightrib53.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1);
    this.rightrib53.setRotationPoint(-4.0F, 1.0F, -3.0F);
    this.rightrib53.setTextureSize(128, 64);
    this.rightrib53.mirror = true;
    setRotation(this.rightrib53, 0.0F, 0.0F, 0.0F);
    this.rightrib6 = new ModelRenderer(this, 60, 20);
    this.rightrib6.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1);
    this.rightrib6.setRotationPoint(-4.0F, 3.0F, 0.0F);
    this.rightrib6.setTextureSize(128, 64);
    this.rightrib6.mirror = true;
    setRotation(this.rightrib6, 0.0F, 0.0F, 0.0F);
    this.rightrib62 = new ModelRenderer(this, 60, 20);
    this.rightrib62.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1);
    this.rightrib62.setRotationPoint(-4.0F, 3.0F, 0.0F);
    this.rightrib62.setTextureSize(128, 64);
    this.rightrib62.mirror = true;
    setRotation(this.rightrib62, 0.0F, 1.570796F, 0.0F);
    this.rightrib63 = new ModelRenderer(this, 60, 20);
    this.rightrib63.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1);
    this.rightrib63.setRotationPoint(-4.0F, 3.0F, -3.0F);
    this.rightrib63.setTextureSize(128, 64);
    this.rightrib63.mirror = true;
    setRotation(this.rightrib63, 0.0F, 0.0F, 0.0F);
    this.sternum = new ModelRenderer(this, 113, 11);
    this.sternum.addBox(0.0F, 0.0F, 0.0F, 3, 7, 1);
    this.sternum.setRotationPoint(-1.0F, -7.0F, -3.0F);
    this.sternum.setTextureSize(128, 64);
    this.sternum.mirror = true;
    setRotation(this.sternum, 0.0F, 0.0F, 0.0F);
    this.pelvis = new ModelRenderer(this, 99, 21);
    this.pelvis.addBox(0.0F, 0.0F, 0.0F, 9, 3, 3);
    this.pelvis.setRotationPoint(-4.0F, 5.0F, -1.0F);
    this.pelvis.setTextureSize(128, 64);
    this.pelvis.mirror = true;
    setRotation(this.pelvis, 0.0F, 0.0F, 0.0F);
    this.leftleg = new ModelRenderer(this, 54, 22);
    this.leftleg.addBox(-1.5F, 0.0F, -1.5F, 3, 16, 3);
    this.leftleg.setRotationPoint(3.5F, 8.0F, 0.5F);
    this.leftleg.setTextureSize(128, 64);
    this.leftleg.mirror = true;
    setRotation(this.leftleg, 0.0F, 0.0F, 0.0F);
    this.rightleg = new ModelRenderer(this, 54, 22);
    this.rightleg.addBox(-1.5F, 0.0F, -1.5F, 3, 16, 3);
    this.rightleg.setRotationPoint(-2.5F, 8.0F, 0.5F);
    this.rightleg.setTextureSize(128, 64);
    this.rightleg.mirror = true;
    setRotation(this.rightleg, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.leftjaw);
    this.head.addChild(this.rightjaw);
    this.head.addChild(this.jaw1);
    this.head.addChild(this.jaw1);
    this.head.addChild(this.jaw2);
    this.head.addChild(this.jaw3);
    this.head.addChild(this.tooth1);
    this.head.addChild(this.tooth2);
    this.head.addChild(this.tooth3);
    this.head.addChild(this.tooth4);
    this.head.addChild(this.tooth5);
    if (renderCudgel)
      this.rightarm.addChild(this.base1); 
    this.base1.addChild(this.base2);
    this.base1.addChild(this.base3);
    this.base1.addChild(this.base4);
    this.base1.addChild(this.spike1);
    this.base1.addChild(this.spike2);
    this.base1.addChild(this.spike3);
    this.base1.addChild(this.spike4);
    this.base1.addChild(this.spike5);
    this.base1.addChild(this.spike6);
    this.base1.addChild(this.spike7);
    this.base1.addChild(this.spike8);
    this.base1.addChild(this.spike9);
    this.base1.addChild(this.spike10);
    this.base1.addChild(this.spike11);
    this.base1.addChild(this.thing1);
    this.base1.addChild(this.thing2);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.head.render(f5);
    this.shoulders.render(f5);
    this.spine.render(f5);
    this.leftarm.render(f5);
    this.rightarm.render(f5);
    this.leftrib1.render(f5);
    this.leftrib12.render(f5);
    this.leftrib13.render(f5);
    this.leftrib2.render(f5);
    this.leftrib22.render(f5);
    this.leftrib23.render(f5);
    this.leftrib3.render(f5);
    this.leftrib32.render(f5);
    this.leftrib33.render(f5);
    this.leftrib4.render(f5);
    this.leftrib42.render(f5);
    this.leftrib43.render(f5);
    this.leftrib5.render(f5);
    this.leftrib52.render(f5);
    this.leftrib53.render(f5);
    this.leftrib6.render(f5);
    this.leftrib62.render(f5);
    this.leftrib63.render(f5);
    this.rightrib1.render(f5);
    this.rightrib12.render(f5);
    this.rightrib13.render(f5);
    this.rightrib2.render(f5);
    this.rightrib22.render(f5);
    this.rightrib23.render(f5);
    this.rightrib3.render(f5);
    this.rightrib32.render(f5);
    this.rightrib33.render(f5);
    this.rightrib4.render(f5);
    this.rightrib42.render(f5);
    this.rightrib43.render(f5);
    this.rightrib5.render(f5);
    this.rightrib52.render(f5);
    this.rightrib53.render(f5);
    this.rightrib6.render(f5);
    this.rightrib62.render(f5);
    this.rightrib63.render(f5);
    this.sternum.render(f5);
    this.pelvis.render(f5);
    this.leftleg.render(f5);
    this.rightleg.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z) {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
    EntityTameBase entity = (EntityTameBase)par7Entity;
    this.head.rotateAngleY = par4 / 57.295776F;
    this.head.rotateAngleX = par5 / 57.295776F;
    this.rightarm.rotateAngleX = MathHelper.cos(par1 * 0.6662F + 3.1415927F) * 2.0F * par2 * 0.5F;
    this.leftarm.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 2.0F * par2 * 0.5F;
    this.rightarm.rotateAngleZ = 0.0F;
    this.leftarm.rotateAngleZ = 0.0F;
    this.rightleg.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
    this.rightleg.rotateAngleY = 0.0F;
    this.leftleg.rotateAngleX = MathHelper.cos(par1 * 0.6662F + 3.1415927F) * 1.4F * par2;
    this.leftleg.rotateAngleY = 0.0F;
    if (this.isRiding) {
      this.rightarm.rotateAngleX += -0.62831855F;
      this.leftarm.rotateAngleX += -0.62831855F;
      this.rightleg.rotateAngleX = -1.2566371F;
      this.leftleg.rotateAngleX = -1.2566371F;
      this.rightleg.rotateAngleY = 0.31415927F;
      this.leftleg.rotateAngleY = -0.31415927F;
    } 
    this.rightarm.rotateAngleY = 0.0F;
    this.leftarm.rotateAngleY = 0.0F;
    if (this.swingProgress > -9990.0F) {
      float f6 = this.swingProgress;
      this.shoulders.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.spine.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib1.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib12.rotateAngleX = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib13.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib2.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib22.rotateAngleX = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib23.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib3.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib32.rotateAngleX = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib33.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib4.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib42.rotateAngleX = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib43.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib5.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib52.rotateAngleX = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib53.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib6.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib62.rotateAngleX = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.leftrib63.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib1.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib12.rotateAngleX = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib13.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib2.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib22.rotateAngleX = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib23.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib3.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib32.rotateAngleX = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib33.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib4.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib42.rotateAngleX = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib43.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib5.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib52.rotateAngleX = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib53.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib6.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib62.rotateAngleX = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightrib63.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.sternum.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightarm.rotateAngleY += this.spine.rotateAngleY;
      this.leftarm.rotateAngleY += this.spine.rotateAngleY;
      this.leftarm.rotateAngleX += this.spine.rotateAngleY;
      f6 = 1.0F - this.swingProgress;
      f6 *= f6;
      f6 *= f6;
      f6 = 1.0F - f6;
      float f7 = MathHelper.sin(f6 * 3.1415927F);
      float f8 = MathHelper.sin(this.swingProgress * 3.1415927F) * -(this.head.rotateAngleX - 0.7F) * 0.75F;
      this.rightarm.rotateAngleX = (float)(this.rightarm.rotateAngleX - f7 * 1.2D + f8);
      this.rightarm.rotateAngleY += this.spine.rotateAngleY * 2.0F;
      this.rightarm.rotateAngleZ = MathHelper.sin(this.swingProgress * 3.1415927F) * -0.4F;
    } 
    if (entity.isBurning() && !entity.isArmsRaised()) {
      this.head.rotateAngleX -= 0.5F;
      this.head.rotateAngleY += MathHelper.cos(par3 * 0.6662F) * 0.5F;
      this.rightarm.rotationPointZ = 0.0F;
      this.rightarm.rotationPointX = -5.0F;
      this.leftarm.rotationPointZ = 0.0F;
      this.leftarm.rotationPointX = 5.0F;
      this.rightarm.rotateAngleX = -MathHelper.cos(par3 * 0.6662F) * 0.5F;
      this.leftarm.rotateAngleX = MathHelper.cos(par3 * 0.6662F) * 0.5F;
      this.rightarm.rotateAngleZ = 2.3561945F;
      this.leftarm.rotateAngleZ = -2.3561945F;
      this.rightarm.rotateAngleY = 0.0F;
      this.leftarm.rotateAngleY = 0.0F;
    } 
    if (!entity.getCurrentBook().isEmpty()) {
      this.rightarm.rotateAngleY = (entity.bookSpread - 1.0F) * 0.5F;
      this.leftarm.rotateAngleY = (-entity.bookSpread + 1.0F) * 0.5F;
      this.rightarm.rotateAngleZ = 0.0F;
      this.leftarm.rotateAngleZ = 0.0F;
      this.rightarm.rotateAngleX = -1.25F + 0.1F + MathHelper.sin(entity.ticksExisted * 0.1F) * 0.01F;
      this.leftarm.rotateAngleX = -1.25F + 0.1F + MathHelper.sin(entity.ticksExisted * 0.1F) * 0.01F;
    } 
  }
}
