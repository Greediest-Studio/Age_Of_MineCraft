package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityIronGolem;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelCMMIronGolem extends ModelBase implements ICappedModel {
  public ModelRenderer RArm;
  
  public ModelRenderer LArm;
  
  public ModelRenderer LLeg;
  
  public ModelRenderer RLeg;
  
  public ModelRenderer Abdoman;
  
  public ModelRenderer Body;
  
  public ModelRenderer Skirt1;
  
  public ModelRenderer Neck;
  
  public ModelRenderer BustFront;
  
  public ModelRenderer Head;
  
  public ModelRenderer Hair;
  
  public ModelRenderer HTop;
  
  public ModelRenderer HLeft;
  
  public ModelRenderer BustTop;
  
  public ModelRenderer BustBottom;
  
  public ModelRenderer Skirt;
  
  public boolean isSneak;
  
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
  
  public ModelCMMIronGolem() {
    this.bipedCape = new ModelRenderer(this, 0, 0);
    this.bipedCape.setTextureSize(64, 32);
    this.bipedCape.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, 0.0F);
    this.textureWidth = 64;
    this.textureHeight = 64;
    this.RLeg = new ModelRenderer(this, 52, 0);
    this.RLeg.setRotationPoint(-2.0F, 11.0F, 0.0F);
    this.RLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 13, 3, 0.0F);
    this.LLeg = new ModelRenderer(this, 52, 0);
    this.LLeg.mirror = true;
    this.LLeg.setRotationPoint(2.0F, 11.0F, 0.0F);
    this.LLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 13, 3, 0.0F);
    this.LArm = new ModelRenderer(this, 36, 0);
    this.LArm.mirror = true;
    this.LArm.setRotationPoint(3.5F, 1.0F, 0.0F);
    this.LArm.addBox(0.0F, -1.0F, -1.0F, 2, 13, 2, 0.0F);
    this.Body = new ModelRenderer(this, 0, 47);
    this.Body.setRotationPoint(0.0F, -6.0F, 0.0F);
    this.Body.addBox(-3.5F, -5.0F, -1.5F, 7, 5, 3, 0.0F);
    this.Hair = new ModelRenderer(this, 0, 12);
    this.Hair.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.Hair.addBox(-4.0F, -6.0F, -3.5F, 8, 7, 7, 0.0F);
    this.RArm = new ModelRenderer(this, 36, 0);
    this.RArm.setRotationPoint(-3.5F, 1.0F, 0.0F);
    this.RArm.addBox(-2.0F, -1.0F, -1.0F, 2, 13, 2, 0.0F);
    this.Neck = new ModelRenderer(this, 0, 42);
    this.Neck.setRotationPoint(0.0F, -5.0F, 0.0F);
    this.Neck.addBox(-1.5F, -2.0F, -1.5F, 3, 2, 3, 0.0F);
    this.HLeft = new ModelRenderer(this, 32, 0);
    this.HLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.HLeft.addBox(3.5F, -8.0F, -0.5F, 1, 4, 1, 0.0F);
    this.HTop = new ModelRenderer(this, 0, 26);
    this.HTop.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.HTop.addBox(-4.0F, -9.0F, -3.5F, 8, 3, 7, 0.0F);
    this.Abdoman = new ModelRenderer(this, 0, 55);
    this.Abdoman.setRotationPoint(0.0F, 11.0F, 0.0F);
    this.Abdoman.addBox(-3.0F, -6.0F, -1.5F, 6, 6, 3, 0.0F);
    this.Head = new ModelRenderer(this, 0, 0);
    this.Head.setRotationPoint(0.0F, -1.0F, 0.0F);
    this.Head.addBox(-3.5F, -6.0F, -3.0F, 7, 6, 6, 0.0F);
    this.Skirt = new ModelRenderer(this, 38, 39);
    this.Skirt.setRotationPoint(0.0F, 3.0F, 0.0F);
    this.Skirt.addBox(-4.0F, 0.0F, -2.5F, 8, 3, 5, 0.0F);
    this.Skirt1 = new ModelRenderer(this, 42, 32);
    this.Skirt1.setRotationPoint(0.0F, -2.0F, 0.0F);
    this.Skirt1.addBox(-3.5F, 0.0F, -2.0F, 7, 3, 4, 0.0F);
    this.BustFront = new ModelRenderer(this, 48, 56);
    this.BustFront.setRotationPoint(0.0F, -0.8F, -1.0F);
    this.BustFront.addBox(-3.0F, -1.0F, -2.0F, 6, 2, 2, 0.0F);
    this.BustTop = new ModelRenderer(this, 48, 52);
    this.BustTop.setRotationPoint(0.0F, -2.3F, -0.6F);
    this.BustTop.addBox(-3.0F, 0.0F, 0.0F, 6, 2, 2, 0.0F);
    setRotateAngle(this.BustTop, -0.7853982F, 0.0F, 0.0F);
    this.BustBottom = new ModelRenderer(this, 48, 60);
    this.BustBottom.setRotationPoint(0.0F, -0.7F, -1.0F);
    this.BustBottom.addBox(-3.0F, 0.0F, 0.0F, 6, 2, 2, 0.0F);
    setRotateAngle(this.BustBottom, -0.43633232F, 0.0F, 0.0F);
    this.Abdoman.addChild(this.Body);
    this.Body.addChild(this.BustFront);
    this.Head.addChild(this.Hair);
    this.BustFront.addChild(this.BustTop);
    this.Body.addChild(this.Neck);
    this.Head.addChild(this.HLeft);
    this.Head.addChild(this.HTop);
    this.Neck.addChild(this.Head);
    this.Skirt1.addChild(this.Skirt);
    this.Abdoman.addChild(this.Skirt1);
    this.BustFront.addChild(this.BustBottom);
  }
  
  public void render(Entity entityIn, float p_78088_2_, float limbSwing, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    setRotationAngles(p_78088_2_, limbSwing, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
    GlStateManager.pushMatrix();
    if (this.isChild) {
      GlStateManager.scale(0.5F, 0.5F, 0.5F);
      GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
      this.RArm.render(scale);
      this.LArm.render(scale);
      this.RLeg.render(scale);
      this.LLeg.render(scale);
      this.Abdoman.render(scale);
    } else {
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
  
  public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    EntityIronGolem entity = (EntityIronGolem)entityIn;
    this.LLeg.rotateAngleX = -1.5F * triangleWave(limbSwing, 13.0F) * limbSwingAmount;
    this.RLeg.rotateAngleX = 1.5F * triangleWave(limbSwing, 13.0F) * limbSwingAmount;
    float swing = MathHelper.sin(this.swingProgress * 3.1415927F);
    int i = entity.getAttackTimer();
    if (i > 0) {
      this.RArm.rotateAngleX = -2.0F + 1.5F * triangleWave(swing * 5.0F, 10.0F);
      this.LArm.rotateAngleX = -2.0F + 1.5F * triangleWave(swing * 5.0F, 10.0F);
    } else {
      int j = entity.getHoldRoseTick();
      if (j > 0) {
        this.RArm.rotateAngleX = -0.8F + 0.025F * triangleWave(j, 70.0F);
        this.LArm.rotateAngleX = (-0.2F - 1.5F * triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
      } else {
        this.RArm.rotateAngleX = (-0.2F + 1.5F * triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
        this.LArm.rotateAngleX = (-0.2F - 1.5F * triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
      } 
    } 
    if (this.isChild) {
      this.BustFront.isHidden = true;
    } else {
      this.BustFront.isHidden = false;
    } 
    this.Head.rotateAngleY = netHeadYaw * 0.017453292F;
    this.Head.rotateAngleX = headPitch * 0.017453292F;
    if (entity.isSneaking()) {
      this.isSneak = true;
    } else {
      this.isSneak = false;
    } 
    this.BustFront.setRotationPoint(0.0F, -0.75F, -1.0F);
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
    this.RArm.rotateAngleZ = 0.0F;
    this.LArm.rotateAngleZ = 0.0F;
    this.RArm.rotateAngleY = 0.0F;
    this.LArm.rotateAngleY = 0.0F;
    this.RLeg.rotateAngleY = 0.0F;
    this.LLeg.rotateAngleY = 0.0F;
    this.RLeg.rotateAngleZ = 0.0F;
    this.LLeg.rotateAngleZ = 0.0F;
    this.BustFront.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 2.0F) * 0.5F * limbSwingAmount * 0.5F;
    this.Abdoman.rotationPointZ = MathHelper.cos(limbSwing * 0.6662F) * 0.5F * limbSwingAmount * 0.5F;
    this.Abdoman.rotationPointY = 12.0F + MathHelper.cos(limbSwing * 0.6662F) * 0.1F * limbSwingAmount * 0.5F;
    if (this.isSneak && !entity.isSitResting() && entity.onGround && entity.getJukeboxToDanceTo() == null) {
      this.Abdoman.rotateAngleX++;
      this.Body.rotateAngleX -= 0.5F;
      this.Head.rotateAngleX -= 0.5F;
      this.RArm.rotateAngleX += 0.75F;
      this.LArm.rotateAngleX += 0.75F;
      this.Abdoman.rotationPointZ--;
      this.RArm.rotationPointY += 4.0F;
      this.LArm.rotationPointY += 4.0F;
      this.RArm.rotationPointZ -= 8.0F;
      this.LArm.rotationPointZ -= 8.0F;
    } 
    if (this.isRiding)
      if (this.isChild) {
        this.Head.rotateAngleX = -0.5F;
        this.Neck.rotateAngleX = -0.5F;
        this.RArm.rotateAngleX = -1.75F;
        this.LArm.rotateAngleX = -1.75F;
        this.RArm.rotateAngleY = 0.5F;
        this.LArm.rotateAngleY = -0.5F;
        this.RLeg.rotateAngleX = 0.25F + MathHelper.cos(ageInTicks * 0.3F + 3.1415927F) * 0.1F + MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 0.25F * limbSwingAmount * 0.5F;
        this.LLeg.rotateAngleX = 0.25F + MathHelper.cos(ageInTicks * 0.3F) * 0.1F + MathHelper.cos(limbSwing * 0.6662F) * 0.25F * limbSwingAmount * 0.5F;
        this.RLeg.rotateAngleZ = 0.25F + MathHelper.cos(ageInTicks * 0.3F - 2.0F) * 0.1F;
        this.LLeg.rotateAngleZ = -0.25F + MathHelper.cos(ageInTicks * 0.3F - 2.0F) * 0.1F;
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
    if (entity.isSitResting()) {
      this.RArm.rotateAngleX += -0.62831855F;
      this.LArm.rotateAngleX += -0.62831855F;
      this.RLeg.rotateAngleX = -1.4137167F;
      this.RLeg.rotateAngleY = 0.31415927F;
      this.RLeg.rotateAngleZ = 0.07853982F;
      this.LLeg.rotateAngleX = -1.4137167F;
      this.LLeg.rotateAngleY = -0.31415927F;
      this.LLeg.rotateAngleZ = -0.07853982F;
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
    if (entity.getJukeboxToDanceTo() != null) {
      this.Neck.rotateAngleZ = MathHelper.cos(ageInTicks * 0.5F - 3.1415927F) * 0.25F - MathHelper.cos(ageInTicks * 0.25F) * 0.25F;
      this.RArm.rotateAngleZ = 1.0F - MathHelper.cos(ageInTicks * 0.5F - 3.1415927F) * 0.25F - MathHelper.cos(ageInTicks * 0.25F) * 1.0F;
      this.LArm.rotateAngleZ = -1.0F - MathHelper.cos(ageInTicks * 0.5F - 3.1415927F) * 0.25F - MathHelper.cos(ageInTicks * 0.25F) * 1.0F;
      this.RArm.rotateAngleY = 0.0F;
      this.LArm.rotateAngleY = 0.0F;
      this.RLeg.rotateAngleX = 0.0F;
      this.LLeg.rotateAngleX = 0.0F;
    } 
    if (!entity.getCurrentBook().isEmpty()) {
      this.RArm.rotateAngleY = entity.bookSpread - 0.825F;
      this.LArm.rotateAngleY = -entity.bookSpread + 0.825F;
      this.RArm.rotateAngleZ = 0.0F;
      this.LArm.rotateAngleZ = 0.0F;
      this.RArm.rotateAngleX = -1.5F + 0.1F + MathHelper.sin(entity.ticksExisted * 0.1F) * 0.01F;
      this.LArm.rotateAngleX = -1.5F + 0.1F + MathHelper.sin(entity.ticksExisted * 0.1F) * 0.01F;
    } 
  }
  
  private float triangleWave(float p_78172_1_, float p_78172_2_) {
    return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5F) - p_78172_2_ * 0.25F) / p_78172_2_ * 0.25F;
  }
}
