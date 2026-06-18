package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEvoker;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelCMMEvoker extends ModelBase implements ICappedModel {
  public ModelRenderer LLeg;
  
  public ModelRenderer RArm;
  
  public ModelRenderer LArm;
  
  public ModelRenderer Abdoman;
  
  public ModelRenderer RLeg;
  
  public ModelRenderer RArm2;
  
  public ModelRenderer LArm2;
  
  public ModelRenderer Body;
  
  public ModelRenderer Skirt1;
  
  public ModelRenderer Skirt2;
  
  public ModelRenderer Skirt3;
  
  public ModelRenderer Neck;
  
  public ModelRenderer BustFront;
  
  public ModelRenderer Head;
  
  public ModelRenderer Hair;
  
  public ModelRenderer HTop;
  
  public ModelRenderer HBun;
  
  public ModelRenderer BustTop;
  
  public ModelRenderer BustBottom;
  
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
  
  public ModelCMMEvoker() {
    this.bipedCape = new ModelRenderer(this, 0, 0);
    this.bipedCape.setTextureSize(64, 32);
    this.bipedCape.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, 0.0F);
    this.textureWidth = 64;
    this.textureHeight = 64;
    this.Body = new ModelRenderer(this, 0, 47);
    this.Body.setRotationPoint(0.0F, -6.0F, 0.0F);
    this.Body.addBox(-3.5F, -5.0F, -1.5F, 7, 5, 3, 0.0F);
    this.LArm = new ModelRenderer(this, 36, 0);
    this.LArm.mirror = true;
    this.LArm.setRotationPoint(3.5F, 2.0F, 0.0F);
    this.LArm.addBox(0.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
    this.Skirt1 = new ModelRenderer(this, 42, 30);
    this.Skirt1.setRotationPoint(0.0F, -3.0F, 0.0F);
    this.Skirt1.addBox(-3.5F, 0.0F, -2.0F, 7, 4, 4, 0.0F);
    this.Skirt2 = new ModelRenderer(this, 36, 38);
    this.Skirt2.setRotationPoint(0.0F, 4.0F, 0.0F);
    this.Skirt2.addBox(-4.0F, 0.0F, -3.0F, 8, 5, 6, 0.0F);
    this.Skirt3 = new ModelRenderer(this, 30, 49);
    this.Skirt3.setRotationPoint(0.0F, 5.0F, 0.0F);
    this.Skirt3.addBox(-4.5F, 0.0F, -4.0F, 9, 5, 8, 0.0F);
    this.LArm2 = new ModelRenderer(this, 30, 14);
    this.LArm2.mirror = true;
    this.LArm2.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.LArm2.addBox(-0.5F, 0.0F, -1.5F, 3, 10, 3, 0.0F);
    this.RArm2 = new ModelRenderer(this, 30, 14);
    this.RArm2.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.RArm2.addBox(-2.5F, 0.0F, -1.5F, 3, 10, 3, 0.0F);
    this.Head = new ModelRenderer(this, 0, 0);
    this.Head.setRotationPoint(0.0F, -1.0F, 0.0F);
    this.Head.addBox(-3.5F, -7.0F, -3.0F, 7, 7, 6, 0.0F);
    this.Abdoman = new ModelRenderer(this, 0, 55);
    this.Abdoman.setRotationPoint(0.0F, 12.0F, 0.0F);
    this.Abdoman.addBox(-3.0F, -6.0F, -1.5F, 6, 6, 3, 0.0F);
    this.RLeg = new ModelRenderer(this, 52, 0);
    this.RLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
    this.RLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 12, 3, 0.0F);
    this.Hair = new ModelRenderer(this, 0, 13);
    this.Hair.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.Hair.addBox(-4.0F, -7.0F, -3.5F, 8, 7, 7, 0.0F);
    this.RArm = new ModelRenderer(this, 36, 0);
    this.RArm.setRotationPoint(-3.5F, 2.0F, 0.0F);
    this.RArm.addBox(-2.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
    this.Neck = new ModelRenderer(this, 20, 49);
    this.Neck.setRotationPoint(0.0F, -5.0F, 0.0F);
    this.Neck.addBox(-1.5F, -2.0F, -1.5F, 3, 2, 3, 0.0F);
    this.HTop = new ModelRenderer(this, 0, 30);
    this.HTop.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.HTop.addBox(-3.5F, -8.0F, -3.0F, 7, 1, 6, 0.0F);
    this.LLeg = new ModelRenderer(this, 52, 0);
    this.LLeg.mirror = true;
    this.LLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
    this.LLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 12, 3, 0.0F);
    this.HBun = new ModelRenderer(this, 0, 37);
    this.HBun.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.HBun.addBox(-2.0F, -9.0F, 0.5F, 4, 4, 4, 0.0F);
    this.BustFront = new ModelRenderer(this, 20, 41);
    this.BustFront.setRotationPoint(0.0F, -0.8F, -1.0F);
    this.BustFront.addBox(-3.0F, -1.0F, -2.0F, 6, 2, 2, 0.0F);
    this.BustTop = new ModelRenderer(this, 20, 37);
    this.BustTop.setRotationPoint(0.0F, -2.3F, -0.6F);
    this.BustTop.addBox(-3.0F, 0.0F, 0.0F, 6, 2, 2, 0.0F);
    setRotateAngle(this.BustTop, -0.7853982F, 0.0F, 0.0F);
    this.BustBottom = new ModelRenderer(this, 20, 45);
    this.BustBottom.setRotationPoint(0.0F, -0.7F, -1.0F);
    this.BustBottom.addBox(-3.0F, 0.0F, 0.0F, 6, 2, 2, 0.0F);
    setRotateAngle(this.BustBottom, -0.43633232F, 0.0F, 0.0F);
    this.Abdoman.addChild(this.Body);
    this.Body.addChild(this.BustFront);
    this.Abdoman.addChild(this.Skirt1);
    this.Skirt1.addChild(this.Skirt2);
    this.Skirt2.addChild(this.Skirt3);
    this.LArm.addChild(this.LArm2);
    this.RArm.addChild(this.RArm2);
    this.Neck.addChild(this.Head);
    this.Head.addChild(this.Hair);
    this.BustFront.addChild(this.BustBottom);
    this.BustFront.addChild(this.BustTop);
    this.Body.addChild(this.Neck);
    this.Head.addChild(this.HTop);
    this.Head.addChild(this.HBun);
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
    EntityTameBase entity = (EntityTameBase)entityIn;
    if (entity.isAIDisabled())
      ageInTicks = 0.0F; 
    this.Head.rotateAngleY = netHeadYaw * 0.017453292F;
    this.Head.rotateAngleX = headPitch * 0.017453292F;
    this.isChild = entity.isChild();
    this.isSneak = entity.isSneaking();
    if (this.isChild) {
      this.BustFront.isHidden = true;
    } else {
      this.BustFront.isHidden = false;
    } 
    this.BustFront.setRotationPoint(0.0F, -0.75F, -1.0F);
    this.RArm.setRotationPoint(-3.5F, 3.0F, 0.0F);
    this.LArm.setRotationPoint(3.5F, 3.0F, 0.0F);
    this.LLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
    this.RLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
    this.Abdoman.setRotationPoint(0.0F, 12.0F, 0.0F);
    this.Body.rotateAngleX = 0.0F;
    this.Abdoman.rotateAngleX = 0.0F;
    this.Body.rotateAngleZ = 0.0F;
    this.Abdoman.rotateAngleZ = 0.0F;
    this.Neck.rotateAngleZ = 0.0F;
    this.Neck.rotateAngleY = 0.0F;
    this.Neck.rotateAngleX = 0.0F;
    this.Head.rotateAngleZ = 0.0F;
    this.Skirt1.rotateAngleX = 0.0F;
    this.Skirt2.rotateAngleX = 0.0F;
    this.Skirt3.rotateAngleX = 0.0F;
    this.Skirt1.rotateAngleZ = 0.0F;
    this.Skirt2.rotateAngleZ = 0.0F;
    this.Skirt3.rotateAngleZ = 0.0F;
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
    this.Neck.rotateAngleZ += MathHelper.cos(limbSwing * 0.6662F) * 0.3F * limbSwingAmount * 0.5F;
    this.Body.rotateAngleZ -= MathHelper.cos(limbSwing * 0.6662F) * 0.4F * limbSwingAmount * 0.5F;
    this.Abdoman.rotateAngleZ += MathHelper.cos(limbSwing * 0.6662F) * 0.2F * limbSwingAmount * 0.5F;
    this.Abdoman.rotationPointZ = MathHelper.cos(limbSwing * 0.6662F) * 0.5F * limbSwingAmount * 0.5F;
    this.Abdoman.rotationPointY = 12.0F + MathHelper.cos(limbSwing * 0.6662F) * 0.1F * limbSwingAmount * 0.5F;
    this.RArm.rotationPointX += MathHelper.cos(limbSwing * 0.6662F) * 0.5F * limbSwingAmount * 0.5F;
    this.LArm.rotationPointX += MathHelper.cos(limbSwing * 0.6662F) * 0.5F * limbSwingAmount * 0.5F;
    if (!entity.onGround)
      if (!entity.isBeingRidden() && !this.isRiding) {
        this.RArm.rotateAngleZ -= 0.6F;
        this.LArm.rotateAngleZ += 0.6F;
        this.RLeg.rotateAngleZ = 0.5F;
        this.LLeg.rotateAngleZ = -0.5F;
        this.RArm.rotateAngleX -= 2.0F;
        this.LArm.rotateAngleX -= 2.0F;
        this.RLeg.rotateAngleX = (float)(this.RLeg.rotateAngleX + (MathHelper.cos(ageInTicks * 0.5F) * 0.25F) - ((entity.motionY > 2.0D) ? 1.0D : ((entity.motionY < -2.0D) ? -1.0D : (entity.motionY / 2.0D))));
        this.LLeg.rotateAngleX = (float)(this.LLeg.rotateAngleX + (MathHelper.cos(ageInTicks * 0.5F + 3.1415927F) * 0.25F) - ((entity.motionY > 2.0D) ? 1.0D : ((entity.motionY < -2.0D) ? -1.0D : (entity.motionY / 2.0D))));
      }  
    if (this.isSneak && !entity.isSitResting() && entity.onGround && entity.getJukeboxToDanceTo() == null) {
      this.Abdoman.rotateAngleX++;
      this.Skirt1.rotateAngleX -= 0.2F;
      this.Skirt2.rotateAngleX -= 0.4F;
      this.Skirt3.rotateAngleX -= 0.4F;
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
        this.Skirt1.rotateAngleX = -0.70685834F;
        this.Skirt2.rotateAngleX = -0.70685834F;
        this.Skirt3.rotateAngleX = -0.25F;
      }  
    if (entity.isSitResting()) {
      this.RArm.rotateAngleX += -0.15707964F;
      this.LArm.rotateAngleX += -0.15707964F;
      this.RLeg.rotateAngleX = -1.55F;
      this.RLeg.rotateAngleY = -0.01F - MathHelper.cos(ageInTicks * 0.45F) * 0.05F;
      this.RLeg.rotateAngleZ = 0.07853982F;
      this.LLeg.rotateAngleX = -1.55F;
      this.LLeg.rotateAngleY = 0.01F + MathHelper.cos(ageInTicks * 0.45F) * 0.05F;
      this.LLeg.rotateAngleZ = -0.07853982F;
      this.Skirt1.rotateAngleX = -0.70685834F;
      this.Skirt2.rotateAngleX = -0.70685834F;
      this.Skirt3.rotateAngleX = -0.25F;
    } 
    if (((EntityEvoker)entity).isSpellcasting()) {
      float f = MathHelper.sin(this.swingProgress * 3.1415927F);
      float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * 3.1415927F);
      this.RArm.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.25F;
      this.LArm.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.25F;
      this.RArm.rotateAngleX -= f * 2.5F - f1 * 0.5F;
      this.LArm.rotateAngleX -= f * 2.5F - f1 * 0.5F;
      this.RArm.rotateAngleZ = 2.3561945F;
      this.LArm.rotateAngleZ = -2.3561945F;
      this.RArm.rotateAngleY = 0.0F;
      this.LArm.rotateAngleY = 0.0F;
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
    if (entity.getJukeboxToDanceTo() != null) {
      this.Skirt1.rotateAngleZ = -(MathHelper.cos(ageInTicks * 0.5F) * 0.15F);
      this.Skirt2.rotateAngleZ = -(MathHelper.cos(ageInTicks * 0.5F) * 0.125F);
      this.Skirt3.rotateAngleZ = -(MathHelper.cos(ageInTicks * 0.5F) * 0.125F);
      this.Neck.rotateAngleZ = MathHelper.cos(ageInTicks * 0.5F - 3.1415927F) * 0.5F;
      this.Body.rotateAngleZ = MathHelper.cos(ageInTicks * 0.5F - 3.1415927F) * 0.5F;
      this.Abdoman.rotateAngleZ = MathHelper.cos(ageInTicks * 0.5F) * 0.5F;
      this.Abdoman.rotationPointX = MathHelper.cos(ageInTicks * 0.5F - 3.1415927F) * 1.0F;
      this.Abdoman.rotationPointY = 12.0F + MathHelper.cos(ageInTicks * 1.0F) * 1.0F;
      this.RArm.rotationPointX = -3.5F + MathHelper.cos(ageInTicks * 0.5F) * 1.5F;
      this.LArm.rotationPointX = 3.5F + MathHelper.cos(ageInTicks * 0.5F) * 1.5F;
      this.RArm.rotationPointY = 4.0F + MathHelper.cos(ageInTicks * 0.5F) * 0.5F;
      this.LArm.rotationPointY = 4.0F + MathHelper.cos(ageInTicks * 0.5F) * 0.5F;
      this.Abdoman.rotationPointZ = 0.0F + MathHelper.cos(ageInTicks * 0.5F) * 3.0F;
      this.RArm.rotationPointZ = 0.0F + MathHelper.cos(ageInTicks * 0.5F) * 3.0F;
      this.LArm.rotationPointZ = 0.0F + MathHelper.cos(ageInTicks * 0.5F) * 3.0F;
      this.RLeg.rotationPointZ = 0.0F + MathHelper.cos(ageInTicks * 0.5F) * 3.0F;
      this.LLeg.rotationPointZ = 0.0F + MathHelper.cos(ageInTicks * 0.5F) * 3.0F;
      this.RArm.rotationPointY = 3.0F + MathHelper.cos(ageInTicks * 0.5F) * 0.5F;
      this.LArm.rotationPointY = 3.0F + MathHelper.cos(ageInTicks * 0.5F) * 0.5F;
      this.RArm.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.5F;
      this.LArm.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.5F;
      this.RArm.rotateAngleZ = 2.3561945F;
      this.LArm.rotateAngleZ = -2.3561945F;
      this.RArm.rotateAngleY = 0.0F;
      this.LArm.rotateAngleY = 0.0F;
      this.RLeg.rotateAngleX = MathHelper.cos(ageInTicks * 0.5F) * 0.5F - MathHelper.cos(ageInTicks * 0.5F) * 1.0F;
      this.LLeg.rotateAngleX = MathHelper.cos(ageInTicks * 0.5F - 3.1415927F) * 0.5F - MathHelper.cos(ageInTicks * 0.5F - 3.1415927F) * 1.0F;
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
