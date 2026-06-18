package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityGiant;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class ModelCMMGiant extends ModelGiant implements ICappedModel {
  public ModelRenderer Abdoman;
  
  public ModelRenderer RArm;
  
  public ModelRenderer LArm;
  
  public ModelRenderer RLeg;
  
  public ModelRenderer LLeg;
  
  public ModelRenderer Body;
  
  public ModelRenderer Body2Z;
  
  public ModelRenderer Neck;
  
  public ModelRenderer BustFront;
  
  public ModelRenderer Head;
  
  public ModelRenderer HSide;
  
  public ModelRenderer Hair;
  
  public ModelRenderer HTop;
  
  public ModelRenderer RArm2;
  
  public ModelRenderer LArm2;
  
  public ModelRenderer bipedCape;
  
  public void renderCape(float scale, float flo1, float flo2, float flo3) {
    GlStateManager.pushMatrix();
    GlStateManager.translate(0.0F, this.isSneak ? 0.25F : 0.075F, this.isSneak ? -0.625F : -0.0325F);
    GlStateManager.rotate(6.0F + flo2 / 2.0F + flo1 + (this.isSneak ? 20.0F : 1.0F), 1.0F, 0.0F, 0.0F);
    GlStateManager.rotate(flo3 / 2.0F, 0.0F, 0.0F, 1.0F);
    GlStateManager.rotate(-flo3 / 2.0F, 0.0F, 1.0F, 0.0F);
    GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
    this.bipedCape.render(scale);
    GlStateManager.popMatrix();
  }
  
  public ModelCMMGiant() {
    this.bipedCape = new ModelRenderer((ModelBase)this, 0, 0);
    this.bipedCape.setTextureSize(64, 32);
    this.bipedCape.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, 0.0F);
    this.textureWidth = 64;
    this.textureHeight = 128;
    this.HSide = new ModelRenderer((ModelBase)this, 0, 71);
    this.HSide.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.HSide.addBox(-5.0F, -6.0F, -3.5F, 10, 7, 6, 0.0F);
    this.RArm2 = new ModelRenderer((ModelBase)this, 30, 14);
    this.RArm2.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.RArm2.addBox(-2.5F, 4.0F, -1.5F, 3, 6, 3, 0.0F);
    this.Head = new ModelRenderer((ModelBase)this, 0, 0);
    this.Head.setRotationPoint(0.0F, -1.0F, 0.0F);
    this.Head.addBox(-3.5F, -7.0F, -3.0F, 7, 7, 6, 0.0F);
    this.HTop = new ModelRenderer((ModelBase)this, 0, 30);
    this.HTop.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.HTop.addBox(-3.0F, -8.0F, -2.5F, 6, 1, 5, 0.0F);
    this.LArm = new ModelRenderer((ModelBase)this, 36, 0);
    this.LArm.mirror = true;
    this.LArm.setRotationPoint(3.5F, 2.0F, 0.0F);
    this.LArm.addBox(0.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
    this.RLeg = new ModelRenderer((ModelBase)this, 52, 0);
    this.RLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
    this.RLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 12, 3, 0.0F);
    this.RArm = new ModelRenderer((ModelBase)this, 36, 0);
    this.RArm.setRotationPoint(-3.5F, 2.0F, 0.0F);
    this.RArm.addBox(-2.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
    this.Body = new ModelRenderer((ModelBase)this, 0, 47);
    this.Body.setRotationPoint(0.0F, -6.0F, 0.0F);
    this.Body.addBox(-3.5F, -5.0F, -1.5F, 7, 5, 3, 0.0F);
    this.Body2Z = new ModelRenderer((ModelBase)this, 0, 64);
    this.Body2Z.setRotationPoint(0.0F, -3.0F, 0.0F);
    this.Body2Z.addBox(-3.5F, 0.0F, -1.5F, 7, 4, 3, 0.0F);
    this.LLeg = new ModelRenderer((ModelBase)this, 52, 0);
    this.LLeg.mirror = true;
    this.LLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
    this.LLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 12, 3, 0.0F);
    this.Hair = new ModelRenderer((ModelBase)this, 0, 13);
    this.Hair.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.Hair.addBox(-4.0F, -7.0F, -3.5F, 8, 10, 7, 0.0F);
    this.Neck = new ModelRenderer((ModelBase)this, 0, 42);
    this.Neck.setRotationPoint(0.0F, -5.0F, 0.0F);
    this.Neck.addBox(-1.5F, -2.0F, -1.5F, 3, 2, 3, 0.0F);
    this.LArm2 = new ModelRenderer((ModelBase)this, 30, 15);
    this.LArm2.mirror = true;
    this.LArm2.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.LArm2.addBox(-0.5F, 4.0F, -1.5F, 3, 6, 3, 0.0F);
    this.Abdoman = new ModelRenderer((ModelBase)this, 0, 55);
    this.Abdoman.setRotationPoint(0.0F, 12.0F, 0.0F);
    this.Abdoman.addBox(-3.0F, -6.0F, -1.5F, 6, 6, 3, 0.0F);
    this.BustFront = new ModelRenderer((ModelBase)this, 20, 49);
    this.BustFront.setRotationPoint(0.0F, -1.7F, -1.0F);
    this.BustFront.addBox(-3.0F, -1.0F, -2.0F, 6, 2, 2, 0.0F);
    this.Head.addChild(this.HSide);
    this.RArm.addChild(this.RArm2);
    this.Neck.addChild(this.Head);
    this.Head.addChild(this.HTop);
    this.Abdoman.addChild(this.Body);
    this.Abdoman.addChild(this.Body2Z);
    this.Head.addChild(this.Hair);
    this.Body.addChild(this.Neck);
    this.LArm.addChild(this.LArm2);
    this.Body.addChild(this.BustFront);
    this.bipedHead.isHidden = true;
    this.bipedBody.isHidden = true;
    this.bipedRightArm.isHidden = true;
    this.bipedLeftArm.isHidden = true;
    this.bipedRightLeg.isHidden = true;
    this.bipedLeftLeg.isHidden = true;
    this.bipedHeadwear.isHidden = true;
  }
  
  public ModelCMMGiant(float modelSize, boolean p_i46303_2_) {
    super(modelSize, p_i46303_2_);
    this.textureWidth = 64;
    this.textureHeight = 32;
    this.HSide = new ModelRenderer((ModelBase)this, 0, 71);
    this.HSide.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.HSide.addBox(-5.0F, -6.0F, -3.5F, 10, 7, 6, 0.0F);
    this.RArm2 = new ModelRenderer((ModelBase)this, 30, 14);
    this.RArm2.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.RArm2.addBox(-2.5F, 4.0F, -1.5F, 3, 6, 3, 0.0F);
    this.Head = new ModelRenderer((ModelBase)this, 0, 0);
    this.Head.setRotationPoint(0.0F, -1.0F, 0.0F);
    this.Head.addBox(-3.5F, -7.0F, -3.0F, 7, 7, 6, 0.0F);
    this.HTop = new ModelRenderer((ModelBase)this, 0, 30);
    this.HTop.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.HTop.addBox(-3.0F, -8.0F, -2.5F, 6, 1, 5, 0.0F);
    this.LArm = new ModelRenderer((ModelBase)this, 36, 0);
    this.LArm.mirror = true;
    this.LArm.setRotationPoint(3.5F, 2.0F, 0.0F);
    this.LArm.addBox(0.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
    this.RLeg = new ModelRenderer((ModelBase)this, 52, 0);
    this.RLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
    this.RLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 12, 3, 0.0F);
    this.RArm = new ModelRenderer((ModelBase)this, 36, 0);
    this.RArm.setRotationPoint(-3.5F, 2.0F, 0.0F);
    this.RArm.addBox(-2.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
    this.Body = new ModelRenderer((ModelBase)this, 0, 47);
    this.Body.setRotationPoint(0.0F, -6.0F, 0.0F);
    this.Body.addBox(-3.5F, -5.0F, -1.5F, 7, 5, 3, 0.0F);
    this.Body2Z = new ModelRenderer((ModelBase)this, 0, 64);
    this.Body2Z.setRotationPoint(0.0F, -3.0F, 0.0F);
    this.Body2Z.addBox(-3.5F, 0.0F, -1.5F, 7, 4, 3, 0.0F);
    this.LLeg = new ModelRenderer((ModelBase)this, 52, 0);
    this.LLeg.mirror = true;
    this.LLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
    this.LLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 12, 3, 0.0F);
    this.Hair = new ModelRenderer((ModelBase)this, 0, 13);
    this.Hair.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.Hair.addBox(-4.0F, -7.0F, -3.5F, 8, 10, 7, 0.0F);
    this.Neck = new ModelRenderer((ModelBase)this, 0, 42);
    this.Neck.setRotationPoint(0.0F, -5.0F, 0.0F);
    this.Neck.addBox(-1.5F, -2.0F, -1.5F, 3, 2, 3, 0.0F);
    this.LArm2 = new ModelRenderer((ModelBase)this, 30, 15);
    this.LArm2.mirror = true;
    this.LArm2.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.LArm2.addBox(-0.5F, 4.0F, -1.5F, 3, 6, 3, 0.0F);
    this.Abdoman = new ModelRenderer((ModelBase)this, 0, 55);
    this.Abdoman.setRotationPoint(0.0F, 12.0F, 0.0F);
    this.Abdoman.addBox(-3.0F, -6.0F, -1.5F, 6, 6, 3, 0.0F);
    this.BustFront = new ModelRenderer((ModelBase)this, 20, 49);
    this.BustFront.setRotationPoint(0.0F, -1.7F, -1.0F);
    this.BustFront.addBox(-3.0F, -1.0F, -2.0F, 6, 2, 2, 0.0F);
    this.Head.addChild(this.HSide);
    this.RArm.addChild(this.RArm2);
    this.Neck.addChild(this.Head);
    this.Head.addChild(this.HTop);
    this.Abdoman.addChild(this.Body);
    this.Abdoman.addChild(this.Body2Z);
    this.Head.addChild(this.Hair);
    this.Body.addChild(this.Neck);
    this.LArm.addChild(this.LArm2);
    this.Body.addChild(this.BustFront);
    this.RArm.isHidden = true;
    this.LArm.isHidden = true;
    this.RLeg.isHidden = true;
    this.LLeg.isHidden = true;
    this.Abdoman.isHidden = true;
  }
  
  public void render(Entity entityIn, float p_78088_2_, float limbSwing, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    setRotationAngles(p_78088_2_, limbSwing, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
    GlStateManager.pushMatrix();
    if (this.isChild) {
      GlStateManager.scale(0.5F, 0.5F, 0.5F);
      GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
      this.bipedHead.render(scale);
      this.bipedBody.render(scale);
      this.bipedRightArm.render(scale);
      this.bipedLeftArm.render(scale);
      this.bipedRightLeg.render(scale);
      this.bipedLeftLeg.render(scale);
      this.bipedHeadwear.render(scale);
      this.RArm.render(scale);
      this.LArm.render(scale);
      this.RLeg.render(scale);
      this.LLeg.render(scale);
      this.Abdoman.render(scale);
    } else {
      this.bipedHead.render(scale);
      this.bipedBody.render(scale);
      this.bipedRightArm.render(scale);
      this.bipedLeftArm.render(scale);
      this.bipedRightLeg.render(scale);
      this.bipedLeftLeg.render(scale);
      this.bipedHeadwear.render(scale);
      this.RArm.render(scale);
      this.LArm.render(scale);
      this.RLeg.render(scale);
      this.LLeg.render(scale);
      this.Abdoman.render(scale);
    } 
    GlStateManager.popMatrix();
  }
  
  public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
    modelRenderer.rotateAngleX = x;
    modelRenderer.rotateAngleY = y;
    modelRenderer.rotateAngleZ = z;
  }
  
  protected ModelRenderer getArmForSide(EnumHandSide side) {
    return (side == EnumHandSide.LEFT) ? this.LArm : this.RArm;
  }
  
  public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    if (this.isChild) {
      this.BustFront.isHidden = true;
    } else {
      this.BustFront.isHidden = false;
    } 
    this.Head.rotateAngleY = netHeadYaw * 0.017453292F;
    this.Head.rotateAngleX = headPitch * 0.017453292F;
    EntityGiant entity = (EntityGiant)entityIn;
    if (entity.isSneaking()) {
      this.isSneak = true;
    } else {
      this.isSneak = false;
    } 
    this.RArm.setRotationPoint(-3.5F, 3.0F, 0.0F);
    this.LArm.setRotationPoint(3.5F, 3.0F, 0.0F);
    this.Body.rotateAngleX = 0.0F;
    this.Abdoman.rotateAngleX = 0.0F;
    this.Body.rotateAngleZ = 0.0F;
    this.Abdoman.rotateAngleZ = 0.0F;
    this.Neck.rotateAngleZ = 0.0F;
    this.Neck.rotateAngleY = 0.0F;
    this.Neck.rotateAngleX = 0.0F;
    this.Abdoman.rotationPointZ = 0.0F;
    this.Abdoman.rotationPointY = 12.0F;
    this.RArm.rotateAngleZ = 0.0F;
    this.LArm.rotateAngleZ = 0.0F;
    this.RArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 2.0F * limbSwingAmount * 0.5F;
    this.LArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
    this.RArm.rotateAngleZ = 0.0F;
    this.LArm.rotateAngleZ = 0.0F;
    this.RArm.rotateAngleY = 0.0F;
    this.LArm.rotateAngleY = 0.0F;
    this.RLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
    this.LLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount * 0.5F;
    this.RLeg.rotateAngleY = 0.0F;
    this.LLeg.rotateAngleY = 0.0F;
    this.RLeg.rotateAngleZ = 0.0F;
    this.LLeg.rotateAngleZ = 0.0F;
    if (!entity.onGround && !entity.isBeingRidden() && !this.isRiding) {
      this.RArm.rotateAngleZ -= 0.6F;
      this.LArm.rotateAngleZ += 0.6F;
      this.RLeg.rotateAngleZ = 0.5F;
      this.LLeg.rotateAngleZ = -0.5F;
      this.RArm.rotateAngleX--;
      this.LArm.rotateAngleX--;
      this.RLeg.rotateAngleX += MathHelper.cos(ageInTicks * 0.1F) * 0.25F;
      this.LLeg.rotateAngleX += MathHelper.cos(ageInTicks * 0.1F + 3.1415927F) * 0.25F;
    } 
    this.bipedBody.rotationPointX = 0.0F;
    this.bipedBody.rotationPointY = 0.0F;
    this.bipedBody.rotationPointZ = 0.0F;
    this.bipedHead.rotationPointX = 0.0F;
    this.bipedHead.rotationPointY = 0.0F;
    this.bipedHead.rotationPointZ = 0.0F;
    if (this.isSneak && !entity.isSitResting() && entity.onGround && entity.getJukeboxToDanceTo() == null) {
      this.Abdoman.rotateAngleX++;
      this.Body.rotateAngleX -= 0.5F;
      this.Head.rotateAngleX -= 0.5F;
      this.RArm.rotateAngleX += 0.75F;
      this.LArm.rotateAngleX += 0.75F;
      this.Abdoman.rotationPointZ--;
      this.bipedBody.rotationPointZ -= 8.0F;
      this.bipedBody.rotationPointY += 6.0F;
      this.bipedHead.rotationPointZ -= 8.0F;
      this.bipedHead.rotationPointY += 3.0F;
      this.bipedBody.rotateAngleX += 0.5F;
      this.RArm.rotationPointY += 4.0F;
      this.LArm.rotationPointY += 4.0F;
      this.RArm.rotationPointZ -= 8.0F;
      this.LArm.rotationPointZ -= 8.0F;
    } 
    if (entity.isSitResting()) {
      this.RArm.rotateAngleX += -0.62831855F;
      this.LArm.rotateAngleX += -0.62831855F;
      this.RLeg.rotateAngleX = -1.5F;
      this.RLeg.rotateAngleY = 0.15707964F;
      this.RLeg.rotateAngleZ = 0.08F;
      this.LLeg.rotateAngleX = -1.5F;
      this.LLeg.rotateAngleY = -0.15707964F;
      this.LLeg.rotateAngleZ = -0.08F;
      if (entity.isSitResting() && entity.getCurrentBook().isEmpty()) {
        this.RArm.rotateAngleX = 2.0F - MathHelper.cos(ageInTicks * 0.2F) * 0.5F;
        this.LArm.rotateAngleX = 2.0F - MathHelper.cos(ageInTicks * 0.2F) * 0.5F;
        this.Abdoman.rotateAngleX = -1.0F - MathHelper.cos(ageInTicks * 0.2F) * 0.5F;
        this.RArm.rotationPointZ = 7.0F + MathHelper.cos(ageInTicks * 0.2F) * 3.0F;
        this.LArm.rotationPointZ = 7.0F + MathHelper.cos(ageInTicks * 0.2F) * 3.0F;
        this.RArm.rotationPointY = 7.0F + MathHelper.cos(ageInTicks * 0.2F) * 3.0F;
        this.LArm.rotationPointY = 7.0F + MathHelper.cos(ageInTicks * 0.2F) * 3.0F;
      } 
    } 
    if (entity.isBurning() || (!entity.isEntityAlive() && !entity.onGround)) {
      this.Head.rotateAngleX -= 0.5F;
      this.Head.rotateAngleY += MathHelper.cos(ageInTicks * 0.6662F) * 0.5F;
      this.RArm.rotateAngleX = -MathHelper.cos(ageInTicks * 0.6662F) * 0.5F;
      this.LArm.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.5F;
      this.RArm.rotateAngleZ = 2.3561945F;
      this.LArm.rotateAngleZ = -2.3561945F;
      this.RArm.rotateAngleY = 0.0F;
      this.LArm.rotateAngleY = 0.0F;
    } 
    if (this.swingProgress > 0.0F) {
      float f4 = this.swingProgress;
      this.Abdoman.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f4) * 6.2831855F) * 0.2F;
      this.RArm.rotationPointZ = MathHelper.sin(this.Abdoman.rotateAngleY) * 5.0F;
      this.RArm.rotationPointX = -MathHelper.cos(this.Abdoman.rotateAngleY) * 3.0F;
      this.LArm.rotationPointZ = -MathHelper.sin(this.Abdoman.rotateAngleY) * 5.0F;
      this.LArm.rotationPointX = MathHelper.cos(this.Abdoman.rotateAngleY) * 3.0F;
      this.RArm.rotateAngleY += this.Abdoman.rotateAngleY;
      this.LArm.rotateAngleY += this.Abdoman.rotateAngleY;
      this.LArm.rotateAngleX += this.Abdoman.rotateAngleY;
      f4 = 1.0F - this.swingProgress;
      f4 *= f4;
      f4 *= f4;
      f4 = 1.0F - f4;
      float f5 = MathHelper.sin(f4 * 3.1415927F);
      float f3 = MathHelper.sin(this.swingProgress * 3.1415927F) * -(this.Neck.rotateAngleX - 0.7F) * 0.75F;
      if (entity.isLeftHanded()) {
        this.Abdoman.rotateAngleY *= -1.0F;
        this.LArm.rotateAngleX = (float)(this.LArm.rotateAngleX - f5 * 1.2D + f3);
        this.LArm.rotateAngleY += this.Abdoman.rotateAngleY * 2.0F;
        this.LArm.rotateAngleZ += MathHelper.sin(this.swingProgress * 3.1415927F) * -0.4F;
      } else {
        this.RArm.rotateAngleX = (float)(this.RArm.rotateAngleX - f5 * 1.2D + f3);
        this.RArm.rotateAngleY += this.Abdoman.rotateAngleY * 2.0F;
        this.RArm.rotateAngleZ += MathHelper.sin(this.swingProgress * 3.1415927F) * -0.4F;
      } 
    } 
    switch (this.leftArmPose) {
      default:
        this.LArm.rotateAngleY = 0.0F;
        break;
      case BLOCK:
        this.LArm.rotateAngleX = this.LArm.rotateAngleX * 0.5F - 1.0F;
        this.LArm.rotateAngleY = 0.75F;
        break;
      case ITEM:
        this.LArm.rotateAngleX = this.LArm.rotateAngleX * 0.5F - 0.31415927F;
        this.LArm.rotateAngleY = 0.0F;
        break;
      case BOW_AND_ARROW:
        this.RArm.rotateAngleY = -0.1F + this.Neck.rotateAngleY + this.Head.rotateAngleY - 0.4F;
        this.LArm.rotateAngleY = 0.1F + this.Neck.rotateAngleY + this.Head.rotateAngleY;
        this.RArm.rotateAngleX = -1.5707964F + this.Neck.rotateAngleX + this.Head.rotateAngleX;
        this.LArm.rotateAngleX = -1.5707964F + this.Neck.rotateAngleX + this.Head.rotateAngleX;
        break;
    } 
    switch (this.rightArmPose) {
      default:
        this.RArm.rotateAngleY = 0.0F;
        break;
      case BLOCK:
        this.RArm.rotateAngleX = this.RArm.rotateAngleX * 0.5F - 1.0F;
        this.RArm.rotateAngleY = -0.75F;
        break;
      case ITEM:
        this.RArm.rotateAngleX = this.RArm.rotateAngleX * 0.5F - 0.31415927F;
        this.RArm.rotateAngleY = 0.0F;
        break;
      case BOW_AND_ARROW:
        this.RArm.rotateAngleY = -0.1F + this.Neck.rotateAngleY + this.Head.rotateAngleY;
        this.LArm.rotateAngleY = 0.1F + this.Neck.rotateAngleY + this.Head.rotateAngleY + 0.4F;
        this.RArm.rotateAngleX = -1.5707964F + this.Neck.rotateAngleX + this.Head.rotateAngleX;
        this.LArm.rotateAngleX = -1.5707964F + this.Neck.rotateAngleX + this.Head.rotateAngleX;
        break;
    } 
    float f = MathHelper.sin(this.swingProgress * 3.1415927F);
    float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * 3.1415927F);
    this.RArm.rotateAngleZ = 0.0F;
    this.LArm.rotateAngleZ = 0.0F;
    this.RArm.rotateAngleY = -(f * 0.6F);
    this.LArm.rotateAngleY = f * 0.6F;
    float f2 = -1.0471976F;
    this.RArm.rotateAngleX = this.bipedHead.rotateAngleX + f2 - 0.5F;
    this.LArm.rotateAngleX = this.bipedHead.rotateAngleX + f2 - 0.5F;
    this.RArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
    this.LArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
    if (this.isRiding)
      if (this.isChild) {
        this.Head.rotateAngleX = 0.1F;
        this.Neck.rotateAngleX = 0.1F;
        this.RArm.rotateAngleX = -1.0F;
        this.LArm.rotateAngleX = -1.0F;
        this.RArm.rotateAngleY = -0.5F;
        this.LArm.rotateAngleY = 0.5F;
        this.RLeg.rotateAngleX = 1.5F;
        this.LLeg.rotateAngleX = 1.5F;
        this.RLeg.rotateAngleY = -0.25F;
        this.LLeg.rotateAngleY = 0.25F;
      } else {
        this.RArm.rotateAngleX += -0.62831855F;
        this.LArm.rotateAngleX += -0.62831855F;
        this.RLeg.rotateAngleX = -1.4137167F;
        this.RLeg.rotateAngleY = 0.31415927F;
        this.RLeg.rotateAngleZ = 0.07853982F;
        this.LLeg.rotateAngleX = -1.4137167F;
        this.LLeg.rotateAngleY = -0.31415927F;
        this.LLeg.rotateAngleZ = -0.07853982F;
      }  
    this.RArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.1F;
    this.LArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.1F;
    this.RArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
    this.LArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
    if (entity.getJukeboxToDanceTo() != null) {
      this.Neck.rotateAngleZ = MathHelper.cos(ageInTicks * 0.125F - 3.1415927F) * 0.125F;
      this.Body.rotateAngleZ = MathHelper.cos(ageInTicks * 0.125F - 3.1415927F) * 0.25F;
      this.Abdoman.rotateAngleZ = MathHelper.cos(ageInTicks * 0.125F) * 0.25F;
      this.Abdoman.rotationPointX = MathHelper.cos(ageInTicks * 0.125F - 3.1415927F) * 0.25F;
      this.Abdoman.rotationPointY = 12.0F + MathHelper.cos(ageInTicks * 0.25F) * 1.0F;
      this.RArm.rotationPointX = -3.5F + MathHelper.cos(ageInTicks * 0.125F) * 1.5F;
      this.LArm.rotationPointX = 3.5F + MathHelper.cos(ageInTicks * 0.125F) * 1.5F;
      this.Abdoman.rotationPointZ = 0.0F + MathHelper.cos(ageInTicks * 0.125F) * 6.0F;
      this.RArm.rotationPointZ = 0.0F + MathHelper.cos(ageInTicks * 0.125F) * 6.0F;
      this.LArm.rotationPointZ = 0.0F + MathHelper.cos(ageInTicks * 0.125F) * 6.0F;
      this.RLeg.rotationPointZ = 0.0F + MathHelper.cos(ageInTicks * 0.125F) * 6.0F;
      this.LLeg.rotationPointZ = 0.0F + MathHelper.cos(ageInTicks * 0.125F) * 6.0F;
      this.RArm.rotationPointY = 3.0F + MathHelper.cos(ageInTicks * 0.25F) * 1.0F;
      this.LArm.rotationPointY = 3.0F + MathHelper.cos(ageInTicks * 0.25F) * 1.0F;
      this.RArm.rotateAngleX = -0.5F;
      this.LArm.rotateAngleX = -0.5F;
      this.RArm.rotateAngleZ = 2.0F + MathHelper.cos(ageInTicks * 0.25F - 3.1415927F) * 0.25F - MathHelper.cos(ageInTicks * 0.125F) * 1.0F;
      this.LArm.rotateAngleZ = -2.0F - MathHelper.cos(ageInTicks * 0.25F - 3.1415927F) * 0.25F + MathHelper.cos(ageInTicks * 0.125F) * 1.0F;
      this.RArm.rotateAngleY = 0.0F;
      this.LArm.rotateAngleY = 0.0F;
      this.RLeg.rotateAngleX = MathHelper.cos(ageInTicks * 0.125F) * 0.5F - MathHelper.cos(ageInTicks * 0.125F) * 1.0F;
      this.LLeg.rotateAngleX = MathHelper.cos(ageInTicks * 0.125F - 3.1415927F) * 0.5F - MathHelper.cos(ageInTicks * 0.125F - 3.1415927F) * 1.0F;
    } 
    this.Neck.rotateAngleX += this.Head.rotateAngleX;
    this.Neck.rotateAngleY += this.Head.rotateAngleY;
    this.Neck.rotateAngleZ += this.Head.rotateAngleZ;
    copyModelAngles(this.RLeg, this.bipedRightLeg);
    copyModelAngles(this.LLeg, this.bipedLeftLeg);
    copyModelAngles(this.RArm, this.bipedRightArm);
    copyModelAngles(this.LArm, this.bipedLeftArm);
    this.bipedRightLeg.rotationPointY -= 1.5F;
    this.bipedLeftLeg.rotationPointY -= 1.5F;
    this.bipedRightArm.rotationPointX--;
    this.bipedLeftArm.rotationPointX++;
    this.bipedHead.rotationPointY -= 1.1F;
    if (!entity.getCurrentBook().isEmpty()) {
      this.RArm.rotateAngleY = entity.bookSpread - 0.825F;
      this.LArm.rotateAngleY = -entity.bookSpread + 0.825F;
      this.RArm.rotateAngleZ = 0.0F;
      this.LArm.rotateAngleZ = 0.0F;
      this.RArm.rotateAngleX = -1.5F + 0.1F + MathHelper.sin(entity.ticksExisted * 0.1F) * 0.01F;
      this.LArm.rotateAngleX = -1.5F + 0.1F + MathHelper.sin(entity.ticksExisted * 0.1F) * 0.01F;
    } 
  }
}
