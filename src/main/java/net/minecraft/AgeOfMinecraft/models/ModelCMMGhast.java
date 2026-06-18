package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGhast;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelCMMGhast extends ModelBase implements ICappedModel {
  public ModelRenderer Chest;
  
  public ModelRenderer Abdoman;
  
  public ModelRenderer Neck;
  
  public ModelRenderer BustFront;
  
  public ModelRenderer LArm;
  
  public ModelRenderer RArm;
  
  public ModelRenderer LLeg;
  
  public ModelRenderer RLeg;
  
  public ModelRenderer Skirt1;
  
  public ModelRenderer Skirt2;
  
  public ModelRenderer Skirt3;
  
  public ModelRenderer Skirt4;
  
  public ModelRenderer Head;
  
  public ModelRenderer Hat;
  
  public ModelRenderer HTop;
  
  public ModelRenderer BHair1;
  
  public ModelRenderer Hair;
  
  public ModelRenderer RFHair;
  
  public ModelRenderer LFHair;
  
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
    GlStateManager.rotate(6.0F + flo2 / 2.0F + flo1 + (this.isSneak ? 20.0F : 1.0F), 1.0F, 0.0F, 0.0F);
    GlStateManager.rotate(flo3 / 2.0F, 0.0F, 0.0F, 1.0F);
    GlStateManager.rotate(-flo3 / 2.0F, 0.0F, 1.0F, 0.0F);
    GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
    this.bipedCape.render(scale);
    GlStateManager.popMatrix();
  }
  
  public ModelCMMGhast() {
    this.bipedCape = new ModelRenderer(this, 0, 0);
    this.bipedCape.setTextureSize(64, 32);
    this.bipedCape.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, 0.0F);
    this.textureWidth = 64;
    this.textureHeight = 64;
    this.Hat = new ModelRenderer(this, 0, 34);
    this.Hat.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.Hat.addBox(-4.5F, -9.0F, -4.0F, 9, 4, 8, 0.0F);
    this.Abdoman = new ModelRenderer(this, 0, 54);
    this.Abdoman.setRotationPoint(0.0F, 5.0F, 0.0F);
    this.Abdoman.addBox(-3.0F, 0.0F, -1.5F, 6, 4, 3, 0.0F);
    this.Skirt4 = new ModelRenderer(this, 30, 26);
    this.Skirt4.setRotationPoint(0.0F, 3.0F, 0.0F);
    this.Skirt4.addBox(-5.0F, 0.0F, -3.5F, 10, 4, 7, 0.0F);
    this.Hair = new ModelRenderer(this, 0, 11);
    this.Hair.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.Hair.addBox(-4.0F, -5.0F, -3.5F, 8, 6, 7, 0.0F);
    this.RFHair = new ModelRenderer(this, 6, 23);
    this.RFHair.setRotationPoint(-3.5F, 1.0F, -3.0F);
    this.RFHair.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
    this.RLeg = new ModelRenderer(this, 52, 0);
    this.RLeg.setRotationPoint(-1.9F, 4.0F, 0.0F);
    this.RLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 14, 3, 0.0F);
    this.BHair2 = new ModelRenderer(this, 20, 49);
    this.BHair2.setRotationPoint(0.0F, 3.0F, 0.0F);
    this.BHair2.addBox(-4.0F, 0.0F, -0.5F, 8, 3, 1, 0.0F);
    this.Neck = new ModelRenderer(this, 18, 59);
    this.Neck.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.Neck.addBox(-1.5F, -2.0F, -1.5F, 3, 2, 3, 0.0F);
    this.Skirt2 = new ModelRenderer(this, 38, 45);
    this.Skirt2.setRotationPoint(0.0F, 3.0F, 0.0F);
    this.Skirt2.addBox(-4.0F, 0.0F, -2.5F, 8, 4, 5, 0.0F);
    this.RArm = new ModelRenderer(this, 36, 0);
    this.RArm.setRotationPoint(-3.5F, 1.0F, 0.0F);
    this.RArm.addBox(-2.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
    this.BustFront = new ModelRenderer(this, 36, 18);
    this.BustFront.setRotationPoint(0.0F, 3.2F, -1.0F);
    this.BustFront.addBox(-3.0F, -1.0F, -2.5F, 6, 2, 2, 0.0F);
    this.BHair4 = new ModelRenderer(this, 20, 55);
    this.BHair4.setRotationPoint(0.0F, 3.0F, 0.0F);
    this.BHair4.addBox(-4.0F, 0.0F, -0.5F, 8, 3, 1, 0.0F);
    this.BHair1 = new ModelRenderer(this, 20, 46);
    this.BHair1.setRotationPoint(0.0F, 0.0F, 3.0F);
    this.BHair1.addBox(-4.0F, 0.0F, -0.5F, 8, 3, 1, 0.0F);
    this.HTop = new ModelRenderer(this, 0, 27);
    this.HTop.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.HTop.addBox(-3.5F, -10.0F, -3.0F, 7, 1, 6, 0.0F);
    this.Skirt1 = new ModelRenderer(this, 42, 37);
    this.Skirt1.setRotationPoint(0.0F, 3.0F, 0.0F);
    this.Skirt1.addBox(-3.5F, 0.0F, -2.0F, 7, 4, 4, 0.0F);
    this.BHair3 = new ModelRenderer(this, 20, 52);
    this.BHair3.setRotationPoint(0.0F, 3.0F, 0.0F);
    this.BHair3.addBox(-4.0F, 0.0F, -0.5F, 8, 3, 1, 0.0F);
    this.Skirt3 = new ModelRenderer(this, 34, 54);
    this.Skirt3.setRotationPoint(0.0F, 3.0F, 0.0F);
    this.Skirt3.addBox(-4.5F, 0.0F, -3.0F, 9, 4, 6, 0.0F);
    this.BustTop = new ModelRenderer(this, 36, 14);
    this.BustTop.setRotationPoint(0.0F, -2.0F, -0.65F);
    this.BustTop.addBox(-3.0F, 0.0F, 0.0F, 6, 2, 2, 0.0F);
    setRotateAngle(this.BustTop, -1.0471976F, 0.0F, 0.0F);
    this.BustBottom = new ModelRenderer(this, 36, 22);
    this.BustBottom.setRotationPoint(0.0F, -0.8F, -1.7F);
    this.BustBottom.addBox(-3.0F, 0.0F, 0.0F, 6, 2, 2, 0.0F);
    setRotateAngle(this.BustBottom, -0.4098033F, 0.0F, 0.0F);
    this.Chest = new ModelRenderer(this, 0, 46);
    this.Chest.setRotationPoint(0.0F, 1.0F, 0.0F);
    this.Chest.addBox(-3.5F, 0.0F, -1.5F, 7, 5, 3, 0.0F);
    this.LLeg = new ModelRenderer(this, 52, 0);
    this.LLeg.mirror = true;
    this.LLeg.setRotationPoint(1.9F, 4.0F, 0.0F);
    this.LLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 14, 3, 0.0F);
    this.Head = new ModelRenderer(this, 0, 0);
    this.Head.setRotationPoint(0.0F, -1.0F, 0.0F);
    this.Head.addBox(-3.5F, -5.0F, -3.0F, 7, 5, 6, 0.0F);
    this.LArm = new ModelRenderer(this, 36, 0);
    this.LArm.mirror = true;
    this.LArm.setRotationPoint(3.5F, 1.0F, 0.0F);
    this.LArm.addBox(0.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
    this.LFHair = new ModelRenderer(this, 13, 23);
    this.LFHair.setRotationPoint(3.5F, 1.0F, -3.0F);
    this.LFHair.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
    this.Head.addChild(this.Hat);
    this.Chest.addChild(this.Abdoman);
    this.Skirt3.addChild(this.Skirt4);
    this.Head.addChild(this.Hair);
    this.Head.addChild(this.RFHair);
    this.Abdoman.addChild(this.RLeg);
    this.BHair1.addChild(this.BHair2);
    this.Chest.addChild(this.Neck);
    this.Skirt1.addChild(this.Skirt2);
    this.Chest.addChild(this.RArm);
    this.Chest.addChild(this.BustFront);
    this.BHair3.addChild(this.BHair4);
    this.Head.addChild(this.BHair1);
    this.Head.addChild(this.HTop);
    this.Abdoman.addChild(this.Skirt1);
    this.BHair2.addChild(this.BHair3);
    this.Skirt2.addChild(this.Skirt3);
    this.BustFront.addChild(this.BustTop);
    this.BustFront.addChild(this.BustBottom);
    this.Abdoman.addChild(this.LLeg);
    this.Neck.addChild(this.Head);
    this.Chest.addChild(this.LArm);
    this.Head.addChild(this.LFHair);
  }
  
  public void render(Entity entityIn, float p_78088_2_, float limbSwing, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    setRotationAngles(p_78088_2_, limbSwing, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
    GlStateManager.pushMatrix();
    if (this.isChild) {
      GlStateManager.scale(0.5F, 0.5F, 0.5F);
      GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
      this.Chest.render(scale);
    } else {
      this.Chest.render(scale);
    } 
    GlStateManager.popMatrix();
  }
  
  public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
    modelRenderer.rotateAngleX = x;
    modelRenderer.rotateAngleY = y;
    modelRenderer.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    if (this.isChild) {
      this.BustFront.isHidden = true;
      this.BHair1.isHidden = true;
      this.LFHair.isHidden = true;
      this.RFHair.isHidden = true;
    } else {
      this.BustFront.isHidden = false;
      this.BHair1.isHidden = false;
      this.LFHair.isHidden = false;
      this.RFHair.isHidden = false;
    } 
    this.Head.rotateAngleY = netHeadYaw * 0.017453292F;
    this.Head.rotateAngleX = headPitch * 0.017453292F;
    EntityGhast entity = (EntityGhast)entityIn;
    this.isChild = entity.isChild();
    this.isSneak = (entity.isSneaking() || entity.getJukeboxToDanceTo() != null || !entity.onGround);
    this.BustFront.setRotationPoint(0.0F, 3.0F, -1.0F);
    this.RArm.setRotationPoint(-3.5F, 2.0F, 0.0F);
    this.LArm.setRotationPoint(3.5F, 2.0F, 0.0F);
    this.Chest.setRotationPoint(0.0F, 1.0F, 0.0F);
    this.LFHair.rotateAngleX = entity.limbSwingAmount * 0.75F - headPitch * 0.0045F;
    this.RFHair.rotateAngleX = entity.limbSwingAmount * 0.75F - headPitch * 0.0045F;
    this.LFHair.rotateAngleZ = -(entity.limbSwingAmount * 0.25F - headPitch * 0.0045F);
    this.RFHair.rotateAngleZ = entity.limbSwingAmount * 0.25F - headPitch * 0.0045F;
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
    this.Skirt1.rotateAngleZ = 0.0F;
    this.Skirt1.rotateAngleY = 0.0F;
    this.Skirt1.rotateAngleX = 0.0F;
    this.Skirt2.rotateAngleX = 0.0F;
    this.Skirt3.rotateAngleX = 0.0F;
    this.Skirt4.rotateAngleX = 0.0F;
    this.Skirt1.rotateAngleZ = 0.0F;
    this.Skirt2.rotateAngleZ = 0.0F;
    this.Skirt3.rotateAngleZ = 0.0F;
    this.Skirt4.rotateAngleZ = 0.0F;
    this.Chest.rotateAngleZ = 0.0F;
    this.Abdoman.rotateAngleZ = 0.0F;
    this.BustFront.rotateAngleX = 0.0F;
    this.Neck.rotateAngleZ = 0.0F;
    this.Neck.rotateAngleY = 0.0F;
    this.Neck.rotateAngleX = 0.0F;
    this.Head.rotateAngleZ = 0.0F;
    this.Skirt1.rotateAngleZ = 0.0F;
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
      this.Neck.rotateAngleZ -= MathHelper.cos(limbSwing * 0.6662F) * 0.5F * limbSwingAmount * 0.5F;
      this.Chest.rotateAngleZ += MathHelper.cos(limbSwing * 0.6662F) * 0.5F * limbSwingAmount * 0.5F;
      this.BustFront.rotateAngleX += MathHelper.cos(limbSwing * 0.6662F - 1.5707964F) * 0.75F * limbSwingAmount * 0.5F;
      this.Abdoman.rotateAngleZ += MathHelper.cos(limbSwing * 0.6662F - 0.5F) * 0.5F * limbSwingAmount * 0.5F;
      this.Skirt1.rotateAngleZ -= MathHelper.cos(limbSwing * 0.6662F - 0.5F) * 0.5F * limbSwingAmount * 0.5F;
      this.Skirt1.rotateAngleY += MathHelper.cos(limbSwing * 0.6662F - 0.5F) * 1.0F * limbSwingAmount * 0.5F;
      this.RLeg.rotateAngleZ -= MathHelper.cos(limbSwing * 0.6662F - 0.5F) * 0.75F * limbSwingAmount * 0.5F;
      this.LLeg.rotateAngleZ -= MathHelper.cos(limbSwing * 0.6662F - 0.5F) * 0.75F * limbSwingAmount * 0.5F;
      this.BHair1.rotateAngleZ += MathHelper.cos(limbSwing * 0.6662F - 1.0F) * 0.75F * limbSwingAmount * 0.5F;
      this.BHair2.rotateAngleZ += MathHelper.cos(limbSwing * 0.6662F - 2.0F) * 0.75F * limbSwingAmount * 0.5F;
      this.BHair3.rotateAngleZ += MathHelper.cos(limbSwing * 0.6662F - 3.0F) * 0.75F * limbSwingAmount * 0.5F;
      this.BHair4.rotateAngleZ += MathHelper.cos(limbSwing * 0.6662F - 4.0F) * 0.75F * limbSwingAmount * 0.5F;
      if (entity.isSitResting()) {
        this.Head.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.2F) * 0.05F;
        this.Neck.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.2F) * 0.05F;
        this.LFHair.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.2F - 1.0F) * 0.1F;
        this.RFHair.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.2F - 1.0F) * 0.1F;
        this.BHair1.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.2F - 0.5F) * 0.1F;
        this.BHair2.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.2F - 1.0F) * 0.1F;
        this.BHair3.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.2F - 1.5F) * 0.1F;
        this.BHair4.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.2F - 2.0F) * 0.1F;
        this.RArm.rotateAngleX = -0.8975979F;
        this.LArm.rotateAngleX = -0.8975979F;
        this.RArm.rotateAngleY = -0.5F;
        this.LArm.rotateAngleY = 0.5F;
        this.RLeg.rotateAngleX = -1.25F;
        this.RLeg.rotateAngleY = -0.3F;
        this.RLeg.rotateAngleZ = 0.025F;
        this.LLeg.rotateAngleX = -1.5F + MathHelper.cos(ageInTicks * 0.5F) * 0.05F;
        this.LLeg.rotateAngleY = 0.25F;
        this.LLeg.rotateAngleZ = -0.025F;
        this.Skirt1.rotateAngleX = -0.75F;
        this.Skirt2.rotateAngleX = -0.75F;
      } 
    } else {
      float f6 = MathHelper.cos(ageInTicks * 0.1F);
      this.BHair1.rotateAngleX += 0.5F + (0.075F + 0.05F * MathHelper.cos(ageInTicks * 0.1F - 1.0F)) * 3.1415927F;
      this.BHair2.rotateAngleX += (0.075F + 0.05F * MathHelper.cos(ageInTicks * 0.1F - 2.0F)) * 3.1415927F;
      this.BHair3.rotateAngleX += (0.075F + 0.05F * MathHelper.cos(ageInTicks * 0.1F - 3.0F)) * 3.1415927F;
      this.BHair4.rotateAngleX += (0.075F + 0.05F * MathHelper.cos(ageInTicks * 0.1F - 4.0F)) * 3.1415927F;
      if (!this.isRiding) {
        this.Neck.rotateAngleX -= (0.065F + 0.02F * f6) * 3.1415927F;
        this.Chest.rotateAngleX = (0.065F + 0.02F * f6) * 3.1415927F;
        this.Abdoman.rotateAngleX = (0.065F + 0.02F * f6) * 3.1415927F;
        this.Skirt1.rotateAngleX = (0.0375F + 0.0125F * f6) * 3.1415927F;
        this.Skirt2.rotateAngleX = (0.0375F + 0.0125F * f6) * 3.1415927F;
        this.Skirt3.rotateAngleX = (0.0375F + 0.0125F * f6) * 3.1415927F;
        this.Skirt4.rotateAngleX = (0.0375F + 0.0125F * f6) * 3.1415927F;
        this.LLeg.rotateAngleX = (0.1F + 0.05F * f6) * 3.1415927F;
        this.RLeg.rotateAngleX = (0.1F + 0.05F * f6) * 3.1415927F;
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
    } 
    if (this.isRiding)
      if (this.isChild) {
        this.Head.rotateAngleX = -0.5F;
        this.Neck.rotateAngleX = -0.5F;
        this.RArm.rotateAngleX = -1.75F;
        this.LArm.rotateAngleX = -1.75F;
        this.RArm.rotateAngleY = 0.5F;
        this.LArm.rotateAngleY = -0.5F;
        this.RLeg.rotateAngleX = 0.1F + MathHelper.cos(ageInTicks * 0.3F + 3.1415927F) * 0.1F + MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 0.25F * limbSwingAmount * 0.5F;
        this.LLeg.rotateAngleX = 0.1F + MathHelper.cos(ageInTicks * 0.3F) * 0.1F + MathHelper.cos(limbSwing * 0.6662F) * 0.25F * limbSwingAmount * 0.5F;
        this.RLeg.rotateAngleZ = 0.1F + MathHelper.cos(ageInTicks * 0.3F - 2.0F) * 0.1F;
        this.LLeg.rotateAngleZ = -0.1F + MathHelper.cos(ageInTicks * 0.3F - 2.0F) * 0.1F;
        this.Skirt1.rotateAngleZ = MathHelper.cos(ageInTicks * 0.3F - 2.0F) * 0.05F;
        this.Skirt2.rotateAngleZ = MathHelper.cos(ageInTicks * 0.3F - 2.0F) * 0.05F;
        this.Skirt3.rotateAngleZ = MathHelper.cos(ageInTicks * 0.3F - 2.0F) * 0.05F;
        this.Skirt4.rotateAngleZ = MathHelper.cos(ageInTicks * 0.3F - 2.0F) * 0.05F;
      } else {
        this.RArm.rotateAngleX += -0.62831855F;
        this.LArm.rotateAngleX += -0.62831855F;
        this.RLeg.rotateAngleX = -1.4137167F;
        this.RLeg.rotateAngleY = 0.31415927F;
        this.RLeg.rotateAngleZ = 0.07853982F;
        this.LLeg.rotateAngleX = -1.4137167F;
        this.LLeg.rotateAngleY = -0.31415927F;
        this.LLeg.rotateAngleZ = -0.07853982F;
        this.Skirt1.rotateAngleX = -0.75F;
        this.Skirt2.rotateAngleX = -0.75F;
      }  
    if (this.isSneak && !entity.isSitResting() && entity.onGround) {
      this.Chest.rotationPointY += 5.0F;
      this.BHair1.rotateAngleX += 0.5F + (0.075F + 0.05F * MathHelper.cos(ageInTicks * 0.1F - 1.0F)) * 3.1415927F;
      this.BHair2.rotateAngleX += (0.075F + 0.05F * MathHelper.cos(ageInTicks * 0.1F - 2.0F)) * 3.1415927F;
      this.BHair3.rotateAngleX += (0.075F + 0.05F * MathHelper.cos(ageInTicks * 0.1F - 3.0F)) * 3.1415927F;
      this.BHair4.rotateAngleX += (0.075F + 0.05F * MathHelper.cos(ageInTicks * 0.1F - 4.0F)) * 3.1415927F;
      float f6 = MathHelper.cos(ageInTicks * 0.1F);
      this.Neck.rotateAngleX -= (0.065F + 0.02F * f6) * 3.1415927F;
      this.Chest.rotateAngleX = (0.065F + 0.02F * f6) * 3.1415927F;
      this.Abdoman.rotateAngleX = 0.25F + (0.065F + 0.02F * f6) * 3.1415927F;
      this.Skirt1.rotateAngleX = (0.0375F + 0.0125F * f6) * 3.1415927F;
      this.Skirt2.rotateAngleX = (0.0375F + 0.0125F * f6) * 3.1415927F;
      this.Skirt3.rotateAngleX = (0.0375F + 0.0125F * f6) * 3.1415927F;
      this.Skirt4.rotateAngleX = (0.0375F + 0.0125F * f6) * 3.1415927F;
      this.LLeg.rotateAngleX = (0.1F + 0.05F * f6) * 3.1415927F;
      this.RLeg.rotateAngleX = (0.1F + 0.05F * f6) * 3.1415927F;
      this.RArm.rotateAngleX = (0.1F + 0.2F * f6) * 3.1415927F;
      this.LArm.rotateAngleX = (0.1F + 0.2F * f6) * 3.1415927F;
      this.RArm.rotateAngleY = (0.1F + -0.15F * f6) * 3.1415927F;
      this.LArm.rotateAngleY = (-0.1F + 0.15F * f6) * 3.1415927F;
      this.LArm.rotateAngleZ = (-0.075F + 0.05F * f6) * 3.1415927F;
      this.RArm.rotateAngleZ = (0.075F + -0.05F * f6) * 3.1415927F;
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
    this.RArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
    this.LArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
    this.RArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
    this.LArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
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
      this.Chest.setRotationPoint(0.0F + MathHelper.sin(ageInTicks * 0.125F) * 1.0F, 0.0F - MathHelper.cos(ageInTicks * 0.125F) * 1.0F, 0.0F + MathHelper.cos(ageInTicks * 0.125F) * 1.0F);
      this.BHair1.rotateAngleZ += MathHelper.cos(ageInTicks * 0.25F - 2.6415927F) * 0.2F;
      this.BHair2.rotateAngleZ += MathHelper.cos(ageInTicks * 0.25F - 2.1415927F) * 0.2F;
      this.BHair3.rotateAngleZ += MathHelper.cos(ageInTicks * 0.25F - 3.1415927F) * 0.2F;
      this.BHair4.rotateAngleZ += MathHelper.cos(ageInTicks * 0.25F - 3.6415927F) * 0.2F;
      this.Head.rotateAngleZ = MathHelper.cos(ageInTicks * 0.25F - 2.1415927F) * 0.25F;
      this.Neck.rotateAngleZ = MathHelper.cos(ageInTicks * 0.25F - 3.1415927F) * 0.25F;
      this.Chest.rotateAngleZ = MathHelper.cos(ageInTicks * 0.25F) * 0.5F;
      this.Abdoman.rotateAngleZ = MathHelper.cos(ageInTicks * 0.25F - 1.0F) * 0.5F;
      this.RArm.rotateAngleX = MathHelper.cos(ageInTicks * 0.25F - 1.0F) * 0.5F;
      this.LArm.rotateAngleX = MathHelper.cos(ageInTicks * 0.25F - 1.0F) * 0.5F;
      this.RArm.rotateAngleZ = 1.0F + MathHelper.sin(ageInTicks * 0.125F) * 0.5F;
      this.LArm.rotateAngleZ = -1.0F + MathHelper.sin(ageInTicks * 0.125F) * 0.5F;
      this.RArm.rotateAngleY = 0.0F;
      this.LArm.rotateAngleY = 0.0F;
      this.Skirt1.rotateAngleZ = MathHelper.cos(ageInTicks * 0.25F - 2.0F) * 0.15F;
      this.Skirt2.rotateAngleZ = MathHelper.cos(ageInTicks * 0.25F - 2.0F) * 0.125F;
      this.Skirt3.rotateAngleZ = MathHelper.cos(ageInTicks * 0.25F - 2.0F) * 0.125F;
      this.Skirt4.rotateAngleZ = MathHelper.cos(ageInTicks * 0.25F - 2.0F) * 0.125F;
      this.RLeg.rotateAngleZ = MathHelper.cos(ageInTicks * 0.25F - 2.0F) * 0.5F;
      this.LLeg.rotateAngleZ = MathHelper.cos(ageInTicks * 0.25F - 2.0F) * 0.5F;
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
}
