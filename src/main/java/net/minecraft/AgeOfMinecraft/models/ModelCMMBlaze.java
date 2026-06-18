package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelCMMBlaze extends ModelBase implements ICappedModel {
  public ModelRenderer[] BlazeRods = new ModelRenderer[12];
  
  public ModelRenderer Chest;
  
  public ModelRenderer Abdoman;
  
  public ModelRenderer Neck;
  
  public ModelRenderer LArm;
  
  public ModelRenderer RArm;
  
  public ModelRenderer BustFront;
  
  public ModelRenderer Waist;
  
  public ModelRenderer RLeg;
  
  public ModelRenderer LLeg;
  
  public ModelRenderer Head;
  
  public ModelRenderer Hair;
  
  public ModelRenderer HTop;
  
  public ModelRenderer BHair1;
  
  public ModelRenderer BHair2;
  
  public ModelRenderer BHair3;
  
  public ModelRenderer BHair4;
  
  public ModelRenderer BustTop;
  
  public ModelRenderer BustBottom;
  
  public boolean isSneak;
  
  public ModelRenderer bipedCape;
  
  public void renderCape(float scale, float flo1, float flo2, float flo3) {
    GlStateManager.pushMatrix();
    GlStateManager.translate(0.0F, this.isSneak ? 0.375F : 0.05F, this.isSneak ? -0.025F : -0.025F);
    GlStateManager.rotate(6.0F + flo2 / 2.0F + flo1, 1.0F, 0.0F, 0.0F);
    GlStateManager.rotate(flo3 / 2.0F, 0.0F, 0.0F, 1.0F);
    GlStateManager.rotate(-flo3 / 2.0F, 0.0F, 1.0F, 0.0F);
    GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
    this.bipedCape.render(scale);
    GlStateManager.popMatrix();
  }
  
  public ModelCMMBlaze() {
    this.bipedCape = new ModelRenderer(this, 0, 0);
    this.bipedCape.setTextureSize(64, 32);
    this.bipedCape.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, 0.0F);
    this.textureWidth = 64;
    this.textureHeight = 64;
    this.RLeg = new ModelRenderer(this, 52, 0);
    this.RLeg.setRotationPoint(-2.0F, 2.0F, 0.0F);
    this.RLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 12, 3, 0.0F);
    this.Head = new ModelRenderer(this, 0, 0);
    this.Head.setRotationPoint(0.0F, -1.0F, 0.0F);
    this.Head.addBox(-3.5F, -7.0F, -3.0F, 7, 7, 6, 0.0F);
    this.HTop = new ModelRenderer(this, 0, 28);
    this.HTop.setRotationPoint(0.0F, -7.0F, 0.0F);
    this.HTop.addBox(-3.5F, -1.0F, -3.0F, 7, 1, 6, 0.0F);
    this.Hair = new ModelRenderer(this, 0, 13);
    this.Hair.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.Hair.addBox(-4.0F, -7.0F, -3.5F, 8, 8, 7, 0.0F);
    this.Waist = new ModelRenderer(this, 0, 35);
    this.Waist.setRotationPoint(0.0F, 4.0F, 0.0F);
    this.Waist.addBox(-3.5F, 0.0F, -1.5F, 7, 3, 3, 0.0F);
    this.Neck = new ModelRenderer(this, 52, 47);
    this.Neck.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.Neck.addBox(-1.5F, -2.0F, -1.5F, 3, 2, 3, 0.0F);
    this.Abdoman = new ModelRenderer(this, 0, 55);
    this.Abdoman.setRotationPoint(0.0F, 5.0F, 0.0F);
    this.Abdoman.addBox(-3.0F, 0.0F, -1.5F, 6, 4, 3, 0.0F);
    this.RArm = new ModelRenderer(this, 36, 0);
    this.RArm.setRotationPoint(-3.5F, 1.0F, 0.0F);
    this.RArm.addBox(-2.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
    for (int i = 0; i < this.BlazeRods.length; i++) {
      this.BlazeRods[i] = new ModelRenderer(this, 26, 0);
      this.BlazeRods[i].setRotationPoint(0.0F, 0.0F, 0.0F);
      this.BlazeRods[i].addBox(-0.5F, -2.0F, -0.5F, 1, 4, 1, 0.0F);
    } 
    this.BHair4 = new ModelRenderer(this, 30, 23);
    this.BHair4.setRotationPoint(0.0F, 3.0F, 0.0F);
    this.BHair4.addBox(-4.0F, 0.0F, 0.0F, 8, 3, 1, 0.0F);
    this.BHair1 = new ModelRenderer(this, 30, 14);
    this.BHair1.setRotationPoint(0.0F, 0.0F, 2.5F);
    this.BHair1.addBox(-4.0F, 1.0F, 0.0F, 8, 3, 1, 0.0F);
    this.BHair2 = new ModelRenderer(this, 30, 17);
    this.BHair2.setRotationPoint(0.0F, 4.0F, 0.0F);
    this.BHair2.addBox(-4.0F, 0.0F, 0.0F, 8, 3, 1, 0.0F);
    this.BHair3 = new ModelRenderer(this, 30, 20);
    this.BHair3.setRotationPoint(0.0F, 3.0F, 0.0F);
    this.BHair3.addBox(-4.0F, 0.0F, 0.0F, 8, 3, 1, 0.0F);
    this.Chest = new ModelRenderer(this, 0, 47);
    this.Chest.setRotationPoint(0.0F, 1.0F, 0.0F);
    this.Chest.addBox(-3.5F, 0.0F, -1.5F, 7, 5, 3, 0.0F);
    this.LArm = new ModelRenderer(this, 36, 0);
    this.LArm.mirror = true;
    this.LArm.setRotationPoint(3.5F, 1.0F, 0.0F);
    this.LArm.addBox(0.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
    this.LLeg = new ModelRenderer(this, 52, 0);
    this.LLeg.mirror = true;
    this.LLeg.setRotationPoint(2.0F, 2.0F, 0.0F);
    this.LLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 12, 3, 0.0F);
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
    this.Waist.addChild(this.RLeg);
    this.Neck.addChild(this.Head);
    this.BustFront.addChild(this.BustBottom);
    this.Head.addChild(this.HTop);
    this.Head.addChild(this.Hair);
    this.Abdoman.addChild(this.Waist);
    this.Chest.addChild(this.Neck);
    this.BustFront.addChild(this.BustTop);
    this.Chest.addChild(this.Abdoman);
    this.Chest.addChild(this.BustFront);
    this.BHair3.addChild(this.BHair4);
    this.Head.addChild(this.BHair1);
    this.BHair1.addChild(this.BHair2);
    this.BHair2.addChild(this.BHair3);
    this.Waist.addChild(this.LLeg);
  }
  
  public void render(Entity entityIn, float p_78088_2_, float limbSwing, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    setRotationAngles(p_78088_2_, limbSwing, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
    GlStateManager.pushMatrix();
    if (this.isChild) {
      GlStateManager.scale(0.5F, 0.5F, 0.5F);
      GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
      for (int i = 0; i < this.BlazeRods.length; i++)
        this.BlazeRods[i].render(scale); 
      this.Chest.render(scale);
      this.LArm.render(scale);
      this.RArm.render(scale);
    } else {
      for (int i = 0; i < this.BlazeRods.length; i++)
        this.BlazeRods[i].render(scale); 
      this.Chest.render(scale);
      this.LArm.render(scale);
      this.RArm.render(scale);
    } 
    GlStateManager.popMatrix();
  }
  
  public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
    modelRenderer.rotateAngleX = x;
    modelRenderer.rotateAngleY = y;
    modelRenderer.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    EntityTameBase entity = (EntityTameBase)entityIn;
    if (entity.isAIDisabled())
      ageInTicks = 0.0F; 
    if (this.isChild) {
      this.BustFront.isHidden = true;
      this.BHair1.isHidden = true;
    } else {
      this.BustFront.isHidden = false;
      this.BHair1.isHidden = false;
    } 
    for (int i = 0; i < this.BlazeRods.length; i++) {
      setRotateAngle(this.BlazeRods[i], 0.0F, 0.0F, 0.0F);
      this.BlazeRods[i].setRotationPoint(0.0F, 0.0F, 0.0F);
    } 
    float swing = MathHelper.sin(this.swingProgress * 3.1415927F);
    float f = ageInTicks * 3.1415927F * -0.1F;
    for (int m = 0; m < 4; m++) {
      if (this.isSneak) {
        (this.BlazeRods[m]).rotationPointY = 5.0F + MathHelper.cos(((m * 2) + ageInTicks) * 0.25F);
      } else {
        (this.BlazeRods[m]).rotationPointY = 2.0F + MathHelper.cos(((m * 2) + ageInTicks) * 0.25F);
      } 
      (this.BlazeRods[m]).rotationPointX = MathHelper.cos(f - swing) * 11.0F;
      (this.BlazeRods[m]).rotationPointZ = MathHelper.sin(f - swing) * 11.0F;
      f += 1.5F;
    } 
    f = 0.7853982F + ageInTicks * 3.1415927F * 0.03F;
    for (int j = 4; j < 8; j++) {
      if (this.isSneak) {
        (this.BlazeRods[j]).rotationPointY = 8.0F + MathHelper.cos(((j * 2) + ageInTicks) * 0.25F);
      } else {
        (this.BlazeRods[j]).rotationPointY = 5.0F + MathHelper.cos(((j * 2) + ageInTicks) * 0.25F);
      } 
      (this.BlazeRods[j]).rotationPointX = MathHelper.cos(f) * 9.0F;
      (this.BlazeRods[j]).rotationPointZ = MathHelper.sin(f) * 9.0F;
      f += 1.5F;
    } 
    f = 0.47123894F + ageInTicks * 3.1415927F * -0.05F;
    for (int k = 8; k < 12; k++) {
      (this.BlazeRods[k]).rotationPointY = 14.0F + MathHelper.cos((k * 1.5F + ageInTicks) * 0.5F);
      (this.BlazeRods[k]).rotationPointX = MathHelper.cos(f) * 6.0F;
      (this.BlazeRods[k]).rotationPointZ = MathHelper.sin(f) * 6.0F;
      f += 1.5F;
    } 
    this.Head.rotateAngleY = netHeadYaw * 0.017453292F;
    this.Head.rotateAngleX = headPitch * 0.017453292F;
    this.isChild = entity.isChild();
    this.isSneak = (entity.isSneaking() || !entity.onGround);
    this.BustFront.setRotationPoint(0.0F, 3.75F, -1.0F);
    this.RArm.setRotationPoint(-3.5F, 3.0F, 0.0F);
    this.LArm.setRotationPoint(3.5F, 3.0F, 0.0F);
    this.Chest.setRotationPoint(0.0F, 1.0F, 0.0F);
    this.BHair1.rotateAngleX = entity.limbSwingAmount * 0.25F - headPitch * 0.0045F;
    this.BHair2.rotateAngleX = entity.limbSwingAmount * 0.25F - headPitch * 0.0045F;
    this.BHair3.rotateAngleX = entity.limbSwingAmount * 0.25F - headPitch * 0.0045F;
    this.BHair4.rotateAngleX = entity.limbSwingAmount * 0.25F - headPitch * 0.0045F;
    this.BHair1.rotateAngleZ = 0.0F;
    this.BHair2.rotateAngleZ = 0.0F;
    this.BHair3.rotateAngleZ = 0.0F;
    this.BHair4.rotateAngleZ = 0.0F;
    this.Chest.rotateAngleX = 0.0F;
    this.Abdoman.rotateAngleX = 0.0F;
    this.Waist.rotateAngleX = 0.0F;
    this.Chest.rotateAngleZ = 0.0F;
    this.Abdoman.rotateAngleZ = 0.0F;
    this.Waist.rotateAngleZ = 0.0F;
    this.BustFront.rotateAngleX = 0.0F;
    this.Neck.rotateAngleZ = 0.0F;
    this.Neck.rotateAngleY = 0.0F;
    this.Neck.rotateAngleX = 0.0F;
    this.RArm.rotateAngleX = 0.0F;
    this.LArm.rotateAngleX = 0.0F;
    this.RArm.rotateAngleZ = 0.0F;
    this.LArm.rotateAngleZ = 0.0F;
    this.RArm.rotateAngleY = 0.0F;
    this.LArm.rotateAngleY = 0.0F;
    if (entity.onGround && !entity.isBeingRidden()) {
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
      this.Neck.rotateAngleZ -= MathHelper.cos(limbSwing * 0.6662F) * 0.375F * limbSwingAmount * 0.5F;
      this.Chest.rotateAngleZ += MathHelper.cos(limbSwing * 0.6662F) * 0.25F * limbSwingAmount * 0.5F;
      this.BustFront.rotateAngleX += MathHelper.cos(limbSwing * 0.6662F - 1.5707964F) * 0.5F * limbSwingAmount * 0.5F;
      this.Abdoman.rotateAngleZ += MathHelper.cos(limbSwing * 0.6662F - 0.5F) * 0.75F * limbSwingAmount * 0.5F;
      this.RLeg.rotateAngleZ -= MathHelper.cos(limbSwing * 0.6662F - 0.5F) * 0.75F * limbSwingAmount * 0.5F;
      this.LLeg.rotateAngleZ -= MathHelper.cos(limbSwing * 0.6662F - 0.5F) * 0.75F * limbSwingAmount * 0.5F;
      this.BHair1.rotateAngleZ += MathHelper.cos(limbSwing * 0.6662F - 1.0F) * 0.75F * limbSwingAmount * 0.5F;
      this.BHair2.rotateAngleZ += MathHelper.cos(limbSwing * 0.6662F - 2.0F) * 0.75F * limbSwingAmount * 0.5F;
      this.BHair3.rotateAngleZ += MathHelper.cos(limbSwing * 0.6662F - 3.0F) * 0.75F * limbSwingAmount * 0.5F;
      this.BHair4.rotateAngleZ += MathHelper.cos(limbSwing * 0.6662F - 4.0F) * 0.75F * limbSwingAmount * 0.5F;
      if (entity.isSitResting()) {
        this.RArm.rotateAngleX += -0.31415927F;
        this.LArm.rotateAngleX += -0.31415927F;
        this.RLeg.rotateAngleX = -1.2F + MathHelper.cos(ageInTicks * 0.2F + 1.0F) * 0.1F;
        this.RLeg.rotateAngleY = -0.3F;
        this.RLeg.rotateAngleZ = 0.025F;
        this.LLeg.rotateAngleX = -1.6F + MathHelper.cos(ageInTicks * 0.2F + 1.0F) * 0.1F;
        this.LLeg.rotateAngleY = 0.25F;
        this.LLeg.rotateAngleZ = -0.025F;
      } 
    } else {
      float f6 = MathHelper.cos(ageInTicks * 0.1F);
      if (!this.isRiding) {
        this.Neck.rotateAngleX -= (0.065F + 0.02F * f6) * 3.1415927F;
        this.Chest.rotateAngleX = (0.065F + 0.02F * f6) * 3.1415927F;
        this.Abdoman.rotateAngleX = (0.065F + 0.02F * f6) * 3.1415927F;
        this.Waist.rotateAngleX = (0.065F + 0.02F * f6) * 3.1415927F;
        this.LLeg.rotateAngleX = (0.15F + 0.05F * f6) * 3.1415927F;
        this.RLeg.rotateAngleX = (0.15F + 0.05F * f6) * 3.1415927F;
        this.LLeg.rotateAngleZ = (-0.05F + 0.05F * f6) * 3.1415927F;
        this.RLeg.rotateAngleZ = (0.05F + -0.05F * f6) * 3.1415927F;
        this.LLeg.rotateAngleY = (-0.05F + 0.05F * f6) * 3.1415927F;
        this.RLeg.rotateAngleY = (0.05F + -0.05F * f6) * 3.1415927F;
        if (entity.isBeingRidden()) {
          this.RArm.rotateAngleX = -2.25F;
          this.LArm.rotateAngleX = -2.25F;
          this.RArm.rotateAngleZ -= 0.3F;
          this.LArm.rotateAngleZ += 0.3F;
        } else {
          this.RArm.rotateAngleX = (0.1F + 0.2F * f6) * 3.1415927F;
          this.LArm.rotateAngleX = (0.1F + 0.2F * f6) * 3.1415927F;
          this.RArm.rotateAngleY = (0.1F + -0.15F * f6) * 3.1415927F;
          this.LArm.rotateAngleY = (-0.1F + 0.15F * f6) * 3.1415927F;
          this.LArm.rotateAngleZ = (-0.075F + 0.05F * f6) * 3.1415927F;
          this.RArm.rotateAngleZ = (0.075F + -0.05F * f6) * 3.1415927F;
        } 
      } 
      this.BHair1.rotateAngleX += 0.25F + (0.075F + 0.05F * MathHelper.cos(ageInTicks * 0.1F - 1.0F)) * 3.1415927F;
      this.BHair2.rotateAngleX += (0.075F + 0.05F * MathHelper.cos(ageInTicks * 0.1F - 2.0F)) * 3.1415927F;
      this.BHair3.rotateAngleX += (0.075F + 0.05F * MathHelper.cos(ageInTicks * 0.1F - 3.0F)) * 3.1415927F;
      this.BHair4.rotateAngleX += (0.075F + 0.05F * MathHelper.cos(ageInTicks * 0.1F - 4.0F)) * 3.1415927F;
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
    if (this.isSneak && !entity.isSitResting() && entity.onGround && entity.getJukeboxToDanceTo() == null) {
      this.Chest.rotationPointY += 5.0F;
      this.RArm.rotationPointY += 5.0F;
      this.LArm.rotationPointY += 5.0F;
      this.BHair1.rotateAngleX += 0.5F + (0.075F + 0.05F * MathHelper.cos(ageInTicks * 0.1F - 1.0F)) * 3.1415927F;
      this.BHair2.rotateAngleX += (0.075F + 0.05F * MathHelper.cos(ageInTicks * 0.1F - 2.0F)) * 3.1415927F;
      this.BHair3.rotateAngleX += (0.075F + 0.05F * MathHelper.cos(ageInTicks * 0.1F - 3.0F)) * 3.1415927F;
      this.BHair4.rotateAngleX += (0.075F + 0.05F * MathHelper.cos(ageInTicks * 0.1F - 4.0F)) * 3.1415927F;
      float f6 = MathHelper.cos(ageInTicks * 0.1F);
      this.Neck.rotateAngleX -= (0.065F + 0.02F * f6) * 3.1415927F;
      this.Chest.rotateAngleX = (0.065F + 0.02F * f6) * 3.1415927F;
      this.Abdoman.rotateAngleX = 0.25F + (0.065F + 0.02F * f6) * 3.1415927F;
      this.Waist.rotateAngleX = (0.065F + 0.02F * f6) * 3.1415927F;
      this.LLeg.rotateAngleX = (0.15F + 0.05F * f6) * 3.1415927F;
      this.RLeg.rotateAngleX = (0.15F + 0.05F * f6) * 3.1415927F;
      this.LLeg.rotateAngleZ = (-0.05F + 0.05F * f6) * 3.1415927F;
      this.RLeg.rotateAngleZ = (0.05F + -0.05F * f6) * 3.1415927F;
      this.LLeg.rotateAngleY = (-0.05F + 0.05F * f6) * 3.1415927F;
      this.RLeg.rotateAngleY = (0.05F + -0.05F * f6) * 3.1415927F;
      this.RArm.rotateAngleX = (0.1F + 0.2F * f6) * 3.1415927F;
      this.LArm.rotateAngleX = (0.1F + 0.2F * f6) * 3.1415927F;
      this.RArm.rotateAngleY = (0.1F + -0.15F * f6) * 3.1415927F;
      this.LArm.rotateAngleY = (-0.1F + 0.15F * f6) * 3.1415927F;
      this.LArm.rotateAngleZ = (-0.075F + 0.05F * f6) * 3.1415927F;
      this.RArm.rotateAngleZ = (0.075F + -0.05F * f6) * 3.1415927F;
    } 
    if (!entity.isEntityAlive() && !entity.onGround) {
      this.Head.rotateAngleX -= 0.5F;
      this.Head.rotateAngleY += MathHelper.cos(ageInTicks * 0.6662F) * 0.5F;
      this.RArm.rotateAngleX = -MathHelper.cos(ageInTicks * 0.6662F) * 0.5F;
      this.LArm.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.5F;
      this.RArm.rotateAngleZ = 2.3561945F;
      this.LArm.rotateAngleZ = -2.3561945F;
      this.RArm.rotateAngleY = 0.0F;
      this.LArm.rotateAngleY = 0.0F;
    } 
    this.RArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
    this.LArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
    this.RArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
    this.LArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
    if (entity.isBurning()) {
      this.RArm.rotateAngleX = -1.6F;
      this.LArm.rotateAngleX = -1.6F;
      this.RArm.rotateAngleZ = 0.0F;
      this.LArm.rotateAngleZ = 0.0F;
    } 
    if (this.swingProgress > 0.0F) {
      float f1 = this.swingProgress;
      this.Abdoman.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * 6.2831855F) * 0.2F;
      this.RArm.rotateAngleY += this.Abdoman.rotateAngleY;
      this.LArm.rotateAngleY += this.Abdoman.rotateAngleY;
      this.LArm.rotateAngleX += this.Abdoman.rotateAngleY;
      this.BustFront.rotateAngleX += this.Abdoman.rotateAngleY;
      f1 = 1.0F - this.swingProgress;
      f1 *= f1;
      f1 *= f1;
      f1 = 1.0F - f1;
      float f2 = MathHelper.sin(f1 * 3.1415927F);
      float f3 = MathHelper.sin(this.swingProgress * 3.1415927F) * -(this.Neck.rotateAngleX - 0.7F) * 0.75F;
      if (entity.isLeftHanded()) {
        this.Abdoman.rotateAngleY *= -1.0F;
        this.LArm.rotateAngleX = (float)(this.LArm.rotateAngleX - f2 * 1.2D + f3);
        this.LArm.rotateAngleY += this.Abdoman.rotateAngleY * 2.0F;
        this.LArm.rotateAngleZ += MathHelper.sin(this.swingProgress * 3.1415927F) * -0.4F;
      } else {
        this.RArm.rotateAngleX = (float)(this.RArm.rotateAngleX - f2 * 1.2D + f3);
        this.RArm.rotateAngleY += this.Abdoman.rotateAngleY * 2.0F;
        this.RArm.rotateAngleZ += MathHelper.sin(this.swingProgress * 3.1415927F) * -0.4F;
      } 
    } 
    if (entity.getJukeboxToDanceTo() != null) {
      for (int n = 0; n < this.BlazeRods.length; n++)
        setRotateAngle(this.BlazeRods[n], MathHelper.sin(ageInTicks * 0.125F) * 6.2831855F, 0.0F, 0.0F); 
      this.RArm.setRotationPoint(-4.0F + MathHelper.sin(ageInTicks * 0.125F) * 1.0F, 3.0F - MathHelper.cos(ageInTicks * 0.125F) * 1.0F, 0.0F + MathHelper.cos(ageInTicks * 0.125F) * 1.0F);
      this.LArm.setRotationPoint(4.0F + MathHelper.sin(ageInTicks * 0.125F) * 1.0F, 3.0F - MathHelper.cos(ageInTicks * 0.125F) * 1.0F, 0.0F + MathHelper.cos(ageInTicks * 0.125F) * 1.0F);
      this.Chest.setRotationPoint(0.0F + MathHelper.sin(ageInTicks * 0.125F) * 1.0F, 0.0F - MathHelper.cos(ageInTicks * 0.125F) * 1.0F, 0.0F + MathHelper.cos(ageInTicks * 0.125F) * 1.0F);
      this.BHair1.rotateAngleZ += MathHelper.cos(ageInTicks * 0.5F - 3.5F) * 0.5F;
      this.BHair2.rotateAngleZ += MathHelper.cos(ageInTicks * 0.5F - 4.0F) * 0.25F;
      this.BHair3.rotateAngleZ += MathHelper.cos(ageInTicks * 0.5F - 4.5F) * 0.25F;
      this.BHair4.rotateAngleZ += MathHelper.cos(ageInTicks * 0.5F - 5.0F) * 0.25F;
      this.Neck.rotateAngleZ = MathHelper.cos(ageInTicks * 0.5F - 3.1415927F) * 0.5F;
      this.Head.rotateAngleX += MathHelper.cos(ageInTicks * 1.0F - 3.0F) * 0.5F;
      this.BHair1.rotateAngleX += 1.0F + MathHelper.cos(ageInTicks * 1.0F - 2.0F) * 0.25F;
      this.BHair2.rotateAngleX += MathHelper.cos(ageInTicks * 1.0F - 2.5F) * 0.25F;
      this.BHair3.rotateAngleX += MathHelper.cos(ageInTicks * 1.0F - 3.0F) * 0.25F;
      this.BHair4.rotateAngleX += MathHelper.cos(ageInTicks * 1.0F - 2.5F) * 0.25F;
      this.Chest.rotateAngleZ = MathHelper.cos(ageInTicks * 0.25F) * 0.25F;
      this.Abdoman.rotateAngleZ = MathHelper.cos(ageInTicks * 0.25F - 1.0F) * 0.5F;
      this.RArm.rotateAngleX = -0.5F + MathHelper.cos(ageInTicks * 0.5F - 2.0F) * 1.0F;
      this.LArm.rotateAngleX = -1.5F + MathHelper.cos(ageInTicks * 0.5F - 2.0F) * 1.0F;
      this.RArm.rotateAngleZ = 2.0F;
      this.LArm.rotateAngleZ = -2.0F;
      this.RArm.rotateAngleY = 0.0F;
      this.LArm.rotateAngleY = 0.0F;
      this.RLeg.rotateAngleZ = MathHelper.cos(ageInTicks * 0.25F - 2.0F) * 0.5F;
      this.LLeg.rotateAngleZ = MathHelper.cos(ageInTicks * 0.25F - 2.0F) * 0.5F;
      this.LLeg.rotateAngleX = 0.0F;
      this.RLeg.rotateAngleX = -(MathHelper.cos(ageInTicks * 0.5F) * 0.5F);
    } 
    if (!entity.getCurrentBook().isEmpty()) {
      this.RArm.rotateAngleY = entity.bookSpread - 0.825F;
      this.LArm.rotateAngleY = -entity.bookSpread + 0.825F;
      this.RArm.rotateAngleZ = 0.0F;
      this.LArm.rotateAngleZ = 0.0F;
      this.RArm.rotateAngleX = -1.5F + 0.1F + MathHelper.sin(entity.ticksExisted * 0.1F) * 0.01F;
      this.LArm.rotateAngleX = -1.5F + 0.1F + MathHelper.sin(entity.ticksExisted * 0.1F) * 0.01F;
    } 
    this.Head.rotateAngleZ = 0.0F;
    if (entity.deathTime > 0) {
      float f1 = (entity.deathTime - 1.0F) / 20.0F;
      f1 = MathHelper.sqrt(f1);
      if (f1 > 1.0F)
        f1 = 1.0F; 
      this.RArm.rotateAngleZ = f1 * 1.25F;
      this.LArm.rotateAngleZ = -f1 * 1.25F;
      this.RLeg.rotateAngleZ = f1 * 0.75F;
      this.LLeg.rotateAngleZ = -f1 * 0.75F;
      this.Neck.rotateAngleY = -f1 * 0.5F;
      this.Head.rotateAngleY = -f1 * 0.5F;
    } 
  }
}
