package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.model;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelRemnant extends ModelBase {
  public ModelRenderer mask1;
  
  public ModelRenderer mask2;
  
  public ModelRenderer head;
  
  public ModelRenderer body;
  
  public ModelRenderer leg1;
  
  public ModelRenderer leg11;
  
  public ModelRenderer leg12;
  
  public ModelRenderer leg13;
  
  public ModelRenderer leg14;
  
  public ModelRenderer leg2;
  
  public ModelRenderer leg21;
  
  public ModelRenderer leg22;
  
  public ModelRenderer leg23;
  
  public ModelRenderer leg24;
  
  public ModelRenderer leg3;
  
  public ModelRenderer leg31;
  
  public ModelRenderer leg32;
  
  public ModelRenderer leg33;
  
  public ModelRenderer leg34;
  
  public ModelRenderer leg4;
  
  public ModelRenderer leg41;
  
  public ModelRenderer leg42;
  
  public ModelRenderer leg43;
  
  public ModelRenderer leg44;
  
  public ModelRenderer leftarm;
  
  public ModelRenderer tentacle1;
  
  public ModelRenderer tentacle2;
  
  public ModelRenderer tentacle3;
  
  public ModelRenderer tentacle4;
  
  public ModelRenderer rightarm;
  
  public ModelRemnant() {
    this.textureWidth = 128;
    this.textureHeight = 64;
    this.mask1 = new ModelRenderer(this, 32, 0);
    this.mask1.addBox(-3.4F, -8.0F, -6.0F, 6, 8, 1);
    this.mask1.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.mask1.setTextureSize(128, 64);
    this.mask1.mirror = true;
    setRotation(this.mask1, 0.0F, -0.5235988F, 0.0F);
    this.mask2 = new ModelRenderer(this, 32, 0);
    this.mask2.addBox(-2.6F, -8.0F, -6.0F, 6, 8, 1);
    this.mask2.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.mask2.setTextureSize(128, 64);
    this.mask2.mirror = true;
    setRotation(this.mask2, 0.0F, 0.5235988F, 0.0F);
    this.head = new ModelRenderer(this, 0, 0);
    this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8);
    this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.head.setTextureSize(128, 64);
    this.head.mirror = true;
    setRotation(this.head, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.mask1);
    this.head.addChild(this.mask2);
    this.body = new ModelRenderer(this, 16, 16);
    this.body.addBox(-4.0F, 0.0F, -2.0F, 8, 22, 4);
    this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.body.setTextureSize(128, 64);
    this.body.mirror = true;
    setRotation(this.body, 0.0F, 0.0F, 0.0F);
    this.leg1 = new ModelRenderer(this, 0, 42);
    this.leg1.addBox(0.0F, -1.0F, -1.0F, 2, 2, 2);
    this.leg1.setRotationPoint(1.0F, 23.0F, 0.0F);
    this.leg1.setTextureSize(128, 64);
    this.leg1.mirror = true;
    setRotation(this.leg1, 0.0F, 0.0F, 0.0F);
    this.leg11 = new ModelRenderer(this, 0, 42);
    this.leg11.addBox(0.0F, -1.0F, -1.0F, 2, 2, 2);
    this.leg11.setRotationPoint(2.0F, 0.0F, 0.0F);
    this.leg11.setTextureSize(128, 64);
    this.leg11.mirror = true;
    setRotation(this.leg11, 0.0F, 0.0F, 0.0F);
    this.leg1.addChild(this.leg11);
    this.leg12 = new ModelRenderer(this, 0, 42);
    this.leg12.addBox(0.0F, -1.0F, -1.0F, 2, 2, 2);
    this.leg12.setRotationPoint(2.0F, 0.0F, 0.0F);
    this.leg12.setTextureSize(128, 64);
    this.leg12.mirror = true;
    setRotation(this.leg12, 0.0F, 0.0F, 0.0F);
    this.leg11.addChild(this.leg12);
    this.leg13 = new ModelRenderer(this, 0, 42);
    this.leg13.addBox(0.0F, -1.0F, -1.0F, 2, 2, 2);
    this.leg13.setRotationPoint(2.0F, 0.0F, 0.0F);
    this.leg13.setTextureSize(128, 64);
    this.leg13.mirror = true;
    setRotation(this.leg13, 0.0F, 0.0F, 0.0F);
    this.leg12.addChild(this.leg13);
    this.leg14 = new ModelRenderer(this, 0, 42);
    this.leg14.addBox(0.0F, -1.0F, -1.0F, 2, 2, 2);
    this.leg14.setRotationPoint(2.0F, 0.0F, 0.0F);
    this.leg14.setTextureSize(128, 64);
    this.leg14.mirror = true;
    setRotation(this.leg14, 0.0F, 0.0F, 0.0F);
    this.leg13.addChild(this.leg14);
    this.leg2 = new ModelRenderer(this, 0, 42);
    this.leg2.addBox(-2.0F, -1.0F, -1.0F, 2, 2, 2);
    this.leg2.setRotationPoint(-1.0F, 23.0F, 0.0F);
    this.leg2.setTextureSize(128, 64);
    this.leg2.mirror = true;
    setRotation(this.leg2, 0.0F, 0.0F, 0.0F);
    this.leg21 = new ModelRenderer(this, 0, 42);
    this.leg21.addBox(-2.0F, -1.0F, -1.0F, 2, 2, 2);
    this.leg21.setRotationPoint(-2.0F, 0.0F, 0.0F);
    this.leg21.setTextureSize(128, 64);
    this.leg21.mirror = true;
    setRotation(this.leg21, 0.0F, 0.0F, 0.0F);
    this.leg2.addChild(this.leg21);
    this.leg22 = new ModelRenderer(this, 0, 42);
    this.leg22.addBox(-2.0F, -1.0F, -1.0F, 2, 2, 2);
    this.leg22.setRotationPoint(-2.0F, 0.0F, 0.0F);
    this.leg22.setTextureSize(128, 64);
    this.leg22.mirror = true;
    setRotation(this.leg22, 0.0F, 0.0F, 0.0F);
    this.leg21.addChild(this.leg22);
    this.leg23 = new ModelRenderer(this, 0, 42);
    this.leg23.addBox(-2.0F, -1.0F, -1.0F, 2, 2, 2);
    this.leg23.setRotationPoint(-2.0F, 0.0F, 0.0F);
    this.leg23.setTextureSize(128, 64);
    this.leg23.mirror = true;
    setRotation(this.leg23, 0.0F, 0.0F, 0.0F);
    this.leg22.addChild(this.leg23);
    this.leg24 = new ModelRenderer(this, 0, 42);
    this.leg24.addBox(-2.0F, -1.0F, -1.0F, 2, 2, 2);
    this.leg24.setRotationPoint(-2.0F, 0.0F, 0.0F);
    this.leg24.setTextureSize(128, 64);
    this.leg24.mirror = true;
    setRotation(this.leg24, 0.0F, 0.0F, 0.0F);
    this.leg23.addChild(this.leg24);
    this.leg3 = new ModelRenderer(this, 2, 42);
    this.leg3.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 2);
    this.leg3.setRotationPoint(0.0F, 23.0F, 1.0F);
    this.leg3.setTextureSize(128, 64);
    this.leg3.mirror = true;
    setRotation(this.leg3, 0.0F, 0.0F, 0.0F);
    this.leg31 = new ModelRenderer(this, 2, 42);
    this.leg31.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 2);
    this.leg31.setRotationPoint(0.0F, 0.0F, 2.0F);
    this.leg31.setTextureSize(128, 64);
    this.leg31.mirror = true;
    setRotation(this.leg31, 0.0F, 0.0F, 0.0F);
    this.leg3.addChild(this.leg31);
    this.leg32 = new ModelRenderer(this, 2, 42);
    this.leg32.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 2);
    this.leg32.setRotationPoint(0.0F, 0.0F, 2.0F);
    this.leg32.setTextureSize(128, 64);
    this.leg32.mirror = true;
    setRotation(this.leg32, 0.0F, 0.0F, 0.0F);
    this.leg31.addChild(this.leg32);
    this.leg33 = new ModelRenderer(this, 2, 42);
    this.leg33.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 2);
    this.leg33.setRotationPoint(0.0F, 0.0F, 2.0F);
    this.leg33.setTextureSize(128, 64);
    this.leg33.mirror = true;
    setRotation(this.leg33, 0.0F, 0.0F, 0.0F);
    this.leg32.addChild(this.leg33);
    this.leg34 = new ModelRenderer(this, 2, 42);
    this.leg34.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 2);
    this.leg34.setRotationPoint(0.0F, 0.0F, 2.0F);
    this.leg34.setTextureSize(128, 64);
    this.leg34.mirror = true;
    setRotation(this.leg34, 0.0F, 0.0F, 0.0F);
    this.leg33.addChild(this.leg34);
    this.leg4 = new ModelRenderer(this, 2, 42);
    this.leg4.addBox(-1.0F, -1.0F, -2.0F, 2, 2, 2);
    this.leg4.setRotationPoint(0.0F, 23.0F, -1.0F);
    this.leg4.setTextureSize(128, 64);
    this.leg4.mirror = true;
    setRotation(this.leg4, 0.0F, 0.0F, 0.0F);
    this.leg41 = new ModelRenderer(this, 2, 42);
    this.leg41.addBox(-1.0F, -1.0F, -2.0F, 2, 2, 2);
    this.leg41.setRotationPoint(0.0F, 0.0F, -2.0F);
    this.leg41.setTextureSize(128, 64);
    this.leg41.mirror = true;
    setRotation(this.leg41, 0.0F, 0.0F, 0.0F);
    this.leg4.addChild(this.leg41);
    this.leg42 = new ModelRenderer(this, 2, 42);
    this.leg42.addBox(-1.0F, -1.0F, -2.0F, 2, 2, 2);
    this.leg42.setRotationPoint(0.0F, 0.0F, -2.0F);
    this.leg42.setTextureSize(128, 64);
    this.leg42.mirror = true;
    setRotation(this.leg42, 0.0F, 0.0F, 0.0F);
    this.leg41.addChild(this.leg42);
    this.leg43 = new ModelRenderer(this, 2, 42);
    this.leg43.addBox(-1.0F, -1.0F, -2.0F, 2, 2, 2);
    this.leg43.setRotationPoint(0.0F, 0.0F, -2.0F);
    this.leg43.setTextureSize(128, 64);
    this.leg43.mirror = true;
    setRotation(this.leg43, 0.0F, 0.0F, 0.0F);
    this.leg42.addChild(this.leg43);
    this.leg44 = new ModelRenderer(this, 2, 42);
    this.leg44.addBox(-1.0F, -1.0F, -2.0F, 2, 2, 2);
    this.leg44.setRotationPoint(0.0F, 0.0F, -2.0F);
    this.leg44.setTextureSize(128, 64);
    this.leg44.mirror = true;
    setRotation(this.leg44, 0.0F, 0.0F, 0.0F);
    this.leg43.addChild(this.leg44);
    this.leftarm = new ModelRenderer(this, 0, 20);
    this.leftarm.addBox(0.0F, -2.0F, -2.0F, 4, 10, 4);
    this.leftarm.setRotationPoint(4.0F, 2.0F, 0.0F);
    this.leftarm.setTextureSize(128, 64);
    this.leftarm.mirror = true;
    setRotation(this.leftarm, 0.0F, 0.0F, 0.0F);
    this.tentacle1 = new ModelRenderer(this, 0, 46);
    this.tentacle1.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2);
    this.tentacle1.setRotationPoint(1.0F, 8.0F, 1.0F);
    this.tentacle1.setTextureSize(128, 64);
    this.tentacle1.mirror = true;
    setRotation(this.tentacle1, 0.0F, 0.0F, 0.0F);
    this.tentacle2 = new ModelRenderer(this, 0, 46);
    this.tentacle2.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2);
    this.tentacle2.setRotationPoint(3.0F, 8.0F, 1.0F);
    this.tentacle2.setTextureSize(128, 64);
    this.tentacle2.mirror = true;
    setRotation(this.tentacle2, 0.0F, 0.0F, 0.0F);
    this.tentacle3 = new ModelRenderer(this, 0, 46);
    this.tentacle3.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2);
    this.tentacle3.setRotationPoint(1.0F, 8.0F, -1.0F);
    this.tentacle3.setTextureSize(128, 64);
    this.tentacle3.mirror = true;
    setRotation(this.tentacle3, 0.0F, 0.0F, 0.0F);
    this.tentacle4 = new ModelRenderer(this, 0, 46);
    this.tentacle4.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2);
    this.tentacle4.setRotationPoint(3.0F, 8.0F, -1.0F);
    this.tentacle4.setTextureSize(128, 64);
    this.tentacle4.mirror = true;
    setRotation(this.tentacle4, 0.0F, 0.0F, 0.0F);
    this.leftarm.addChild(this.tentacle1);
    this.leftarm.addChild(this.tentacle2);
    this.leftarm.addChild(this.tentacle3);
    this.leftarm.addChild(this.tentacle4);
    this.rightarm = new ModelRenderer(this, 0, 20);
    this.rightarm.addBox(-4.0F, -2.0F, -2.0F, 4, 16, 4);
    this.rightarm.setRotationPoint(-4.0F, 2.0F, 0.0F);
    this.rightarm.setTextureSize(128, 64);
    this.rightarm.mirror = true;
    setRotation(this.rightarm, 0.0F, 0.0F, 0.0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.head.render(f5);
    this.body.render(f5);
    this.leg1.render(f5);
    this.leg2.render(f5);
    this.leg3.render(f5);
    this.leg4.render(f5);
    this.leftarm.render(f5);
    this.rightarm.render(f5);
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
    this.rightarm.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.leftarm.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
    this.rightarm.rotateAngleZ = 0.0F;
    this.leftarm.rotateAngleZ = 0.0F;
    this.tentacle1.offsetX = this.tentacle1.offsetY = this.tentacle1.offsetZ = 0.0F;
    float f16 = 0.03F * (entity.getEntityId() % 10);
    this.tentacle1.rotateAngleX = MathHelper.cos(entity.ticksExisted * f16) * 10.5F * 3.1415927F / 180.0F;
    this.tentacle1.rotateAngleY = 0.0F;
    this.tentacle1.rotateAngleZ = MathHelper.sin(entity.ticksExisted * f16) * 6.5F * 3.1415927F / 180.0F;
    float f17 = 0.03F * (entity.getEntityId() % 10);
    this.tentacle2.offsetX = this.tentacle2.offsetY = this.tentacle2.offsetZ = 0.0F;
    this.tentacle2.rotateAngleX = MathHelper.sin(entity.ticksExisted * f17) * 10.5F * 3.1415927F / 180.0F;
    this.tentacle2.rotateAngleY = 0.0F;
    this.tentacle2.rotateAngleZ = MathHelper.cos(entity.ticksExisted * f17) * 6.5F * 3.1415927F / 180.0F;
    float f18 = 0.03F * (entity.getEntityId() % 10);
    this.tentacle3.offsetX = this.tentacle3.offsetY = this.tentacle3.offsetZ = 0.0F;
    this.tentacle3.rotateAngleX = MathHelper.sin(entity.ticksExisted * f18) * 10.5F * 3.1415927F / 180.0F;
    this.tentacle3.rotateAngleY = 0.0F;
    this.tentacle3.rotateAngleZ = MathHelper.cos(entity.ticksExisted * f18) * 6.5F * 3.1415927F / 180.0F;
    float f19 = 0.03F * (entity.getEntityId() % 10);
    this.tentacle4.offsetX = this.tentacle4.offsetY = this.tentacle4.offsetZ = 0.0F;
    this.tentacle4.rotateAngleX = MathHelper.cos(entity.ticksExisted * f19) * 10.5F * 3.1415927F / 180.0F;
    this.tentacle4.rotateAngleY = 0.0F;
    this.tentacle4.rotateAngleZ = MathHelper.sin(entity.ticksExisted * f19) * 6.5F * 3.1415927F / 180.0F;
    this.leg1.rotateAngleY = 0.6662F + MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
    this.leg11.rotateAngleY = MathHelper.cos(f * 0.6662F) * 1.9F * f1 * 0.5F;
    this.leg12.rotateAngleY = MathHelper.cos(f * 0.6662F) * 1.8F * f1 * 0.5F;
    this.leg13.rotateAngleY = MathHelper.cos(f * 0.6662F) * 1.7F * f1 * 0.5F;
    this.leg14.rotateAngleY = MathHelper.cos(f * 0.6662F) * 1.6F * f1 * 0.5F;
    this.leg2.rotateAngleY = 0.6662F + MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
    this.leg21.rotateAngleY = MathHelper.cos(f * 0.6662F) * 1.9F * f1 * 0.5F;
    this.leg22.rotateAngleY = MathHelper.cos(f * 0.6662F) * 1.8F * f1 * 0.5F;
    this.leg23.rotateAngleY = MathHelper.cos(f * 0.6662F) * 1.7F * f1 * 0.5F;
    this.leg24.rotateAngleY = MathHelper.cos(f * 0.6662F) * 1.6F * f1 * 0.5F;
    this.leg3.rotateAngleY = 0.6662F + MathHelper.cos(f * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.leg31.rotateAngleY = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.9F * f1 * 0.5F;
    this.leg32.rotateAngleY = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.8F * f1 * 0.5F;
    this.leg33.rotateAngleY = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.7F * f1 * 0.5F;
    this.leg34.rotateAngleY = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.6F * f1 * 0.5F;
    this.leg4.rotateAngleY = 0.6662F + MathHelper.cos(f * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.leg41.rotateAngleY = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.9F * f1 * 0.5F;
    this.leg42.rotateAngleY = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.8F * f1 * 0.5F;
    this.leg43.rotateAngleY = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.7F * f1 * 0.5F;
    this.leg44.rotateAngleY = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.6F * f1 * 0.5F;
    if (this.isRiding) {
      this.rightarm.rotateAngleX += -0.62831855F;
      this.leftarm.rotateAngleX += -0.62831855F;
      this.leg1.rotateAngleX = -1.2566371F;
      this.leg2.rotateAngleX = -1.2566371F;
      this.leg3.rotateAngleX = -1.2566371F;
      this.leg4.rotateAngleX = -1.2566371F;
      this.leg1.rotateAngleY = 0.31415927F;
      this.leg2.rotateAngleY = -0.31415927F;
      this.leg3.rotateAngleY = 0.31415927F;
      this.leg4.rotateAngleY = -0.31415927F;
    } 
    this.rightarm.rotateAngleY = 0.0F;
    this.leftarm.rotateAngleY = 0.0F;
    if (this.swingProgress > -9990.0F) {
      float f6 = this.swingProgress;
      this.body.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightarm.rotateAngleY += this.body.rotateAngleY;
      this.leftarm.rotateAngleY += this.body.rotateAngleY;
      f6 = 1.0F - this.swingProgress;
      f6 *= f6;
      f6 *= f6;
      f6 = 1.0F - f6;
      float f7 = MathHelper.sin(f6 * 3.1415927F);
      float f8 = MathHelper.sin(this.swingProgress * 3.1415927F) * -(this.head.rotateAngleX - 0.7F) * 0.75F;
      this.rightarm.rotateAngleX = (float)(this.rightarm.rotateAngleX - f7 * 1.2D + f8);
      this.rightarm.rotateAngleY += this.body.rotateAngleY * 2.0F;
      this.rightarm.rotateAngleZ = MathHelper.sin(this.swingProgress * 3.1415927F) * -0.4F;
      this.leftarm.rotateAngleX = (float)(this.leftarm.rotateAngleX - f7 * 1.2D + f8);
      this.leftarm.rotateAngleY += this.body.rotateAngleY * -2.0F;
      this.leftarm.rotateAngleZ = MathHelper.sin(this.swingProgress * 3.1415927F) * 0.4F;
    } 
    if (!entity.getCurrentBook().isEmpty()) {
      this.tentacle1.rotateAngleX = 0.0F;
      this.tentacle1.rotateAngleY = 0.0F;
      this.tentacle1.rotateAngleZ = entity.bookSpread - 0.75F;
      this.tentacle2.rotateAngleX = 0.0F;
      this.tentacle2.rotateAngleY = 0.0F;
      this.tentacle2.rotateAngleZ = entity.bookSpread - 0.75F;
      this.tentacle3.rotateAngleX = 0.0F;
      this.tentacle3.rotateAngleY = 0.0F;
      this.tentacle3.rotateAngleZ = entity.bookSpread - 0.75F;
      this.tentacle4.rotateAngleX = 0.0F;
      this.tentacle4.rotateAngleY = 0.0F;
      this.tentacle4.rotateAngleZ = entity.bookSpread - 0.75F;
      this.rightarm.rotateAngleY = entity.bookSpread - 1.0F;
      this.leftarm.rotateAngleY = -entity.bookSpread + 1.0F;
      this.rightarm.rotateAngleZ = 0.0F;
      this.leftarm.rotateAngleZ = 0.0F;
      this.rightarm.rotateAngleX = -1.5F + 0.1F + MathHelper.sin(entity.ticksExisted * 0.1F) * 0.01F;
      this.leftarm.rotateAngleX = -1.5F + 0.1F + MathHelper.sin(entity.ticksExisted * 0.1F) * 0.01F;
    } 
  }
}
