package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.model;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelGatekeeperMinion extends ModelBase {
  public ModelRenderer mask1;
  
  public ModelRenderer maskpart1;
  
  public ModelRenderer maskpart2;
  
  public ModelRenderer mask2;
  
  public ModelRenderer maskpart3;
  
  public ModelRenderer maskpart4;
  
  public ModelRenderer head;
  
  public ModelRenderer body;
  
  public ModelRenderer rightshoulder;
  
  public ModelRenderer leftshoulder;
  
  public ModelRenderer rightarm1;
  
  public ModelRenderer rightarm2;
  
  public ModelRenderer leftarm1;
  
  public ModelRenderer leftarm2;
  
  public ModelRenderer tentacle1;
  
  public ModelRenderer tentacle2;
  
  public ModelRenderer tentacle3;
  
  public ModelRenderer tentacle4;
  
  public ModelRenderer rltentacle1;
  
  public ModelRenderer rltentacle2;
  
  public ModelRenderer rltentacle3;
  
  public ModelRenderer rltentacle4;
  
  public ModelRenderer lltentacle1;
  
  public ModelRenderer lltentacle2;
  
  public ModelRenderer lltentacle3;
  
  public ModelRenderer lltentacle4;
  
  public ModelRenderer lowerbody;
  
  public ModelGatekeeperMinion() {
    this.textureWidth = 128;
    this.textureHeight = 64;
    this.mask1 = new ModelRenderer(this, 32, 0);
    this.mask1.addBox(-3.4F, -8.0F, -6.0F, 6, 8, 1);
    this.mask1.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.mask1.setTextureSize(128, 64);
    this.mask1.mirror = true;
    setRotation(this.mask1, 0.0F, -0.5235988F, 0.0F);
    this.maskpart1 = new ModelRenderer(this, 26, 0);
    this.maskpart1.addBox(1.6F, -8.0F, -7.0F, 1, 1, 1);
    this.maskpart1.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.maskpart1.setTextureSize(128, 64);
    this.maskpart1.mirror = true;
    setRotation(this.maskpart1, 0.0F, -0.5235988F, 0.0F);
    this.maskpart2 = new ModelRenderer(this, 26, 0);
    this.maskpart2.addBox(1.6F, -1.0F, -7.0F, 1, 1, 1);
    this.maskpart2.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.maskpart2.setTextureSize(128, 64);
    this.maskpart2.mirror = true;
    setRotation(this.maskpart2, 0.0F, -0.5235988F, 0.0F);
    this.mask2 = new ModelRenderer(this, 32, 0);
    this.mask2.addBox(-2.6F, -8.0F, -6.0F, 6, 8, 1);
    this.mask2.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.mask2.setTextureSize(128, 64);
    this.mask2.mirror = true;
    setRotation(this.mask2, 0.0F, 0.5235988F, 0.0F);
    this.maskpart3 = new ModelRenderer(this, 26, 0);
    this.maskpart3.addBox(-2.6F, -8.0F, -7.0F, 1, 1, 1);
    this.maskpart3.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.maskpart3.setTextureSize(128, 64);
    this.maskpart3.mirror = true;
    setRotation(this.maskpart3, 0.0F, 0.5235988F, 0.0F);
    this.maskpart4 = new ModelRenderer(this, 26, 0);
    this.maskpart4.addBox(-2.6F, -1.0F, -7.0F, 1, 1, 1);
    this.maskpart4.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.maskpart4.setTextureSize(128, 64);
    this.maskpart4.mirror = true;
    setRotation(this.maskpart4, 0.0F, 0.5235988F, 0.0F);
    this.head = new ModelRenderer(this, 0, 0);
    this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8);
    this.head.setRotationPoint(0.0F, -12.0F, 0.0F);
    this.head.setTextureSize(128, 64);
    this.head.mirror = true;
    setRotation(this.head, 0.0F, 0.0F, 0.0F);
    this.head.addChild(this.mask1);
    this.head.addChild(this.mask2);
    this.head.addChild(this.maskpart1);
    this.head.addChild(this.maskpart2);
    this.head.addChild(this.maskpart3);
    this.head.addChild(this.maskpart4);
    this.body = new ModelRenderer(this, 16, 16);
    this.body.addBox(0.0F, 0.0F, 0.0F, 8, 22, 6);
    this.body.setRotationPoint(-4.0F, -12.0F, -3.0F);
    this.body.setTextureSize(128, 64);
    this.body.mirror = true;
    setRotation(this.body, 0.0F, 0.0F, 0.0F);
    this.rightshoulder = new ModelRenderer(this, 44, 9);
    this.rightshoulder.addBox(-7.0F, -2.0F, -2.0F, 7, 4, 6);
    this.rightshoulder.setRotationPoint(0.5F, 2.0F, 2.0F);
    this.rightshoulder.setTextureSize(128, 64);
    this.rightshoulder.mirror = true;
    setRotation(this.rightshoulder, 0.0F, 0.0F, -0.1745329F);
    this.body.addChild(this.rightshoulder);
    this.leftshoulder = new ModelRenderer(this, 44, 9);
    this.leftshoulder.addBox(0.0F, -2.0F, -2.0F, 7, 4, 6);
    this.leftshoulder.setRotationPoint(7.5F, 2.0F, 2.0F);
    this.leftshoulder.setTextureSize(128, 64);
    this.leftshoulder.mirror = true;
    setRotation(this.leftshoulder, 0.0F, 0.0F, 0.1745329F);
    this.body.addChild(this.leftshoulder);
    this.rightarm1 = new ModelRenderer(this, 0, 20);
    this.rightarm1.addBox(-4.0F, -2.0F, -2.0F, 4, 8, 4);
    this.rightarm1.setRotationPoint(-5.0F, -8.0F, 0.0F);
    this.rightarm1.setTextureSize(128, 64);
    this.rightarm1.mirror = true;
    setRotation(this.rightarm1, -0.3839724F, 0.0F, 0.0F);
    this.rightarm2 = new ModelRenderer(this, 0, 26);
    this.rightarm2.addBox(-4.0F, 5.5F, -1.0F, 4, 10, 4);
    this.rightarm2.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.rightarm2.setTextureSize(128, 64);
    this.rightarm2.mirror = true;
    setRotation(this.rightarm2, -0.5585054F - this.rightarm1.rotateAngleX, 0.0F, 0.0F);
    this.rightarm1.addChild(this.rightarm2);
    this.leftarm1 = new ModelRenderer(this, 0, 20);
    this.leftarm1.addBox(0.0F, -2.0F, -2.0F, 4, 8, 4);
    this.leftarm1.setRotationPoint(5.0F, -8.0F, 0.0F);
    this.leftarm1.setTextureSize(128, 64);
    this.leftarm1.mirror = true;
    setRotation(this.leftarm1, -0.3839724F, 0.0F, 0.0F);
    this.leftarm2 = new ModelRenderer(this, 0, 26);
    this.leftarm2.addBox(0.0F, 5.5F, -1.0F, 4, 4, 4);
    this.leftarm2.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.leftarm2.setTextureSize(128, 64);
    this.leftarm2.mirror = true;
    setRotation(this.leftarm2, -0.5585054F - this.leftarm1.rotateAngleX, 0.0F, 0.0F);
    this.leftarm1.addChild(this.leftarm2);
    this.tentacle1 = new ModelRenderer(this, 0, 46);
    this.tentacle1.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2);
    this.tentacle1.setRotationPoint(1.0F, 9.0F, 2.0F);
    this.tentacle1.setTextureSize(128, 64);
    this.tentacle1.mirror = true;
    setRotation(this.tentacle1, 0.0F, 0.0F, 0.0F);
    this.tentacle2 = new ModelRenderer(this, 0, 46);
    this.tentacle2.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2);
    this.tentacle2.setRotationPoint(3.0F, 9.0F, 2.0F);
    this.tentacle2.setTextureSize(128, 64);
    this.tentacle2.mirror = true;
    setRotation(this.tentacle2, 0.0F, 0.0F, 0.0F);
    this.tentacle3 = new ModelRenderer(this, 0, 46);
    this.tentacle3.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2);
    this.tentacle3.setRotationPoint(1.0F, 9.0F, 0.0F);
    this.tentacle3.setTextureSize(128, 64);
    this.tentacle3.mirror = true;
    setRotation(this.tentacle3, 0.0F, 0.0F, 0.0F);
    this.tentacle4 = new ModelRenderer(this, 0, 46);
    this.tentacle4.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2);
    this.tentacle4.setRotationPoint(3.0F, 9.0F, 0.0F);
    this.tentacle4.setTextureSize(128, 64);
    this.tentacle4.mirror = true;
    setRotation(this.tentacle4, 0.0F, 0.0F, 0.0F);
    this.rltentacle1 = new ModelRenderer(this, 0, 46);
    this.rltentacle1.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2);
    this.rltentacle1.setRotationPoint(-3.0F, 18.0F, 1.0F);
    this.rltentacle1.setTextureSize(128, 64);
    this.rltentacle1.mirror = true;
    this.leftarm2.addChild(this.tentacle1);
    this.leftarm2.addChild(this.tentacle2);
    this.leftarm2.addChild(this.tentacle3);
    this.leftarm2.addChild(this.tentacle4);
    setRotation(this.rltentacle1, 0.0F, 0.0F, 0.0F);
    this.rltentacle2 = new ModelRenderer(this, 0, 46);
    this.rltentacle2.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2);
    this.rltentacle2.setRotationPoint(-3.0F, 18.0F, -1.0F);
    this.rltentacle2.setTextureSize(128, 64);
    this.rltentacle2.mirror = true;
    setRotation(this.rltentacle2, 0.0F, 0.0F, 0.0F);
    this.rltentacle3 = new ModelRenderer(this, 0, 46);
    this.rltentacle3.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2);
    this.rltentacle3.setRotationPoint(-1.0F, 18.0F, 1.0F);
    this.rltentacle3.setTextureSize(128, 64);
    this.rltentacle3.mirror = true;
    setRotation(this.rltentacle3, 0.0F, 0.0F, 0.0F);
    this.rltentacle4 = new ModelRenderer(this, 0, 46);
    this.rltentacle4.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2);
    this.rltentacle4.setRotationPoint(-1.0F, 18.0F, -1.0F);
    this.rltentacle4.setTextureSize(128, 64);
    this.rltentacle4.mirror = true;
    setRotation(this.rltentacle4, 0.0F, 0.0F, 0.0F);
    this.lltentacle1 = new ModelRenderer(this, 0, 46);
    this.lltentacle1.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2);
    this.lltentacle1.setRotationPoint(1.0F, 18.0F, 1.0F);
    this.lltentacle1.setTextureSize(128, 64);
    this.lltentacle1.mirror = true;
    setRotation(this.lltentacle1, 0.0F, 0.0F, 0.0F);
    this.lltentacle2 = new ModelRenderer(this, 0, 46);
    this.lltentacle2.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2);
    this.lltentacle2.setRotationPoint(1.0F, 18.0F, -1.0F);
    this.lltentacle2.setTextureSize(128, 64);
    this.lltentacle2.mirror = true;
    setRotation(this.lltentacle2, 0.0F, 0.0F, 0.0F);
    this.lltentacle3 = new ModelRenderer(this, 0, 46);
    this.lltentacle3.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2);
    this.lltentacle3.setRotationPoint(3.0F, 18.0F, 1.0F);
    this.lltentacle3.setTextureSize(128, 64);
    this.lltentacle3.mirror = true;
    setRotation(this.lltentacle3, 0.0F, 0.0F, 0.0F);
    this.lltentacle4 = new ModelRenderer(this, 0, 46);
    this.lltentacle4.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2);
    this.lltentacle4.setRotationPoint(3.0F, 18.0F, -1.0F);
    this.lltentacle4.setTextureSize(128, 64);
    this.lltentacle4.mirror = true;
    setRotation(this.lltentacle4, 0.0F, 0.0F, 0.0F);
    this.lowerbody = new ModelRenderer(this, 8, 44);
    this.lowerbody.addBox(0.0F, 0.0F, 0.0F, 8, 8, 4);
    this.lowerbody.setRotationPoint(0.0F, 22.0F, 1.0F);
    this.lowerbody.setTextureSize(128, 64);
    this.lowerbody.mirror = true;
    setRotation(this.lowerbody, 0.0F, 0.0F, 0.0F);
    this.body.addChild(this.lowerbody);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.head.render(f5);
    this.body.render(f5);
    this.rightarm1.render(f5);
    this.leftarm1.render(f5);
    this.rltentacle1.render(f5);
    this.rltentacle2.render(f5);
    this.rltentacle3.render(f5);
    this.rltentacle4.render(f5);
    this.lltentacle1.render(f5);
    this.lltentacle2.render(f5);
    this.lltentacle3.render(f5);
    this.lltentacle4.render(f5);
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
    this.rightarm1.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
    this.leftarm1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
    this.rightarm1.rotateAngleZ = 0.0F;
    this.leftarm1.rotateAngleZ = 0.0F;
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
    this.rltentacle1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
    this.rltentacle1.rotateAngleZ = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
    this.rltentacle1.rotateAngleY = 0.0F;
    this.rltentacle2.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1;
    this.rltentacle2.rotateAngleZ = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1;
    this.rltentacle2.rotateAngleY = 0.0F;
    this.rltentacle3.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1;
    this.rltentacle3.rotateAngleZ = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1;
    this.rltentacle3.rotateAngleY = 0.0F;
    this.rltentacle4.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
    this.rltentacle4.rotateAngleZ = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
    this.rltentacle4.rotateAngleY = 0.0F;
    this.lltentacle1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
    this.lltentacle1.rotateAngleZ = -MathHelper.cos(f * 0.6662F) * 1.4F * f1;
    this.lltentacle1.rotateAngleY = 0.0F;
    this.lltentacle2.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1;
    this.lltentacle2.rotateAngleZ = -MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1;
    this.lltentacle2.rotateAngleY = 0.0F;
    this.lltentacle3.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1;
    this.lltentacle3.rotateAngleZ = -MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1;
    this.lltentacle3.rotateAngleY = 0.0F;
    this.lltentacle4.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
    this.lltentacle4.rotateAngleZ = -MathHelper.cos(f * 0.6662F) * 1.4F * f1;
    this.lltentacle4.rotateAngleY = 0.0F;
    this.rightarm1.rotateAngleY = 0.0F;
    this.leftarm1.rotateAngleY = 0.0F;
    if (this.swingProgress > -9990.0F) {
      float f6 = this.swingProgress;
      this.body.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f6) * 3.1415927F * 2.0F) * 0.2F;
      this.rightarm1.rotateAngleY += this.body.rotateAngleY;
      this.leftarm1.rotateAngleY += this.body.rotateAngleY;
      f6 = 1.0F - this.swingProgress;
      f6 *= f6;
      f6 *= f6;
      f6 = 1.0F - f6;
      float f7 = MathHelper.sin(f6 * 3.1415927F);
      float f8 = MathHelper.sin(this.swingProgress * 3.1415927F) * -(this.head.rotateAngleX - 0.7F) * 0.75F;
      this.rightarm1.rotateAngleX = (float)(this.rightarm1.rotateAngleX - f7 * 1.2D + f8);
      this.rightarm1.rotateAngleY += this.body.rotateAngleY * 2.0F;
      this.rightarm1.rotateAngleZ = MathHelper.sin(this.swingProgress * 3.1415927F) * -0.4F;
      this.leftarm1.rotateAngleX = (float)(this.leftarm1.rotateAngleX - f7 * 1.2D + f8);
      this.leftarm1.rotateAngleY += this.body.rotateAngleY * -2.0F;
      this.leftarm1.rotateAngleZ = MathHelper.sin(this.swingProgress * 3.1415927F) * 0.4F;
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
      this.rightarm1.rotateAngleY = entity.bookSpread - 1.0F;
      this.leftarm1.rotateAngleY = -entity.bookSpread + 1.0F;
      this.rightarm1.rotateAngleZ = 0.0F;
      this.leftarm1.rotateAngleZ = 0.0F;
      this.rightarm1.rotateAngleX = -1.25F + 0.1F + MathHelper.sin(entity.ticksExisted * 0.1F) * 0.01F;
      this.leftarm1.rotateAngleX = -1.25F + 0.1F + MathHelper.sin(entity.ticksExisted * 0.1F) * 0.01F;
    } 
  }
}
