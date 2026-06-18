package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGuardian;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class ModelCMMGuardian extends ModelBase implements ICappedModel {
  public ModelRenderer Chest;
  
  public ModelRenderer Abdoman;
  
  public ModelRenderer Neck;
  
  public ModelRenderer LArm;
  
  public ModelRenderer RArm;
  
  public ModelRenderer BustFront;
  
  public ModelRenderer Body2Child;
  
  public ModelRenderer Skirt1;
  
  public ModelRenderer RLeg;
  
  public ModelRenderer LLeg;
  
  public ModelRenderer Body2ChildChild;
  
  public ModelRenderer Body2ChildChildChild0;
  
  public ModelRenderer Body2ChildChildChild1;
  
  public ModelRenderer Skirt2;
  
  public ModelRenderer Head;
  
  public ModelRenderer Hair;
  
  public ModelRenderer HTop;
  
  public ModelRenderer REar;
  
  public ModelRenderer LEar;
  
  public ModelRenderer LHorn;
  
  public ModelRenderer RHorn;
  
  public ModelRenderer Eye;
  
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
  
  public ModelCMMGuardian() {
    this.bipedCape = new ModelRenderer(this, 0, 0);
    this.bipedCape.setTextureSize(64, 32);
    this.bipedCape.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, 0.0F);
    this.textureWidth = 64;
    this.textureHeight = 64;
    this.Body2ChildChild = new ModelRenderer(this, 18, 36);
    this.Body2ChildChild.setRotationPoint(0.0F, 0.0F, 7.0F);
    this.Body2ChildChild.addBox(-1.5F, -1.5F, 0.0F, 3, 3, 7, 0.0F);
    this.Body2ChildChildChild0 = new ModelRenderer(this, 0, 39);
    this.Body2ChildChildChild0.setRotationPoint(0.0F, 0.0F, 6.0F);
    this.Body2ChildChildChild0.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 6, 0.0F);
    this.HTop = new ModelRenderer(this, 0, 28);
    this.HTop.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.HTop.addBox(-3.5F, -8.0F, -3.0F, 7, 1, 6, 0.0F);
    this.Neck = new ModelRenderer(this, 52, 47);
    this.Neck.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.Neck.addBox(-1.5F, -2.0F, -1.5F, 3, 2, 3, 0.0F);
    this.LHorn = new ModelRenderer(this, 0, 35);
    this.LHorn.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.LHorn.addBox(3.0F, -7.5F, -1.0F, 2, 1, 1, 0.0F);
    this.Head = new ModelRenderer(this, 0, 0);
    this.Head.setRotationPoint(0.0F, -1.0F, 0.0F);
    this.Head.addBox(-3.5F, -7.0F, -3.0F, 7, 7, 6, 0.0F);
    this.RLeg = new ModelRenderer(this, 52, 0);
    this.RLeg.setRotationPoint(-2.0F, 6.0F, 0.0F);
    this.RLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 12, 3, 0.0F);
    this.RArm = new ModelRenderer(this, 56, 15);
    this.RArm.setRotationPoint(-3.5F, 1.0F, 0.0F);
    this.RArm.addBox(-2.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
    this.Body2ChildChildChild1 = new ModelRenderer(this, 28, 46);
    this.Body2ChildChildChild1.setRotationPoint(0.0F, 0.0F, 6.0F);
    this.Body2ChildChildChild1.addBox(0.0F, -4.5F, 0.0F, 1, 9, 9, 0.0F);
    this.Skirt2 = new ModelRenderer(this, 38, 37);
    this.Skirt2.setRotationPoint(0.0F, 3.0F, 0.0F);
    this.Skirt2.addBox(-4.0F, 0.0F, -2.5F, 8, 4, 5, 0.0F);
    this.RHorn = new ModelRenderer(this, 0, 37);
    this.RHorn.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.RHorn.addBox(-5.0F, -7.5F, -1.0F, 2, 1, 1, 0.0F);
    this.Body2Child = new ModelRenderer(this, 32, 15);
    this.Body2Child.setRotationPoint(0.0F, 4.0F, 1.0F);
    this.Body2Child.addBox(-2.0F, -2.0F, 0.0F, 4, 4, 8, 0.0F);
    this.LLeg = new ModelRenderer(this, 40, 0);
    this.LLeg.setRotationPoint(2.0F, 6.0F, 0.0F);
    this.LLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 12, 3, 0.0F);
    this.Abdoman = new ModelRenderer(this, 0, 55);
    this.Abdoman.setRotationPoint(0.0F, 5.0F, 0.0F);
    this.Abdoman.addBox(-3.0F, 0.0F, -1.5F, 6, 6, 3, 0.0F);
    this.LArm = new ModelRenderer(this, 32, 0);
    this.LArm.setRotationPoint(3.5F, 1.0F, 0.0F);
    this.LArm.addBox(0.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
    this.LEar = new ModelRenderer(this, 18, 60);
    this.LEar.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.LEar.addBox(3.5F, -4.0F, -1.5F, 2, 3, 1, 0.0F);
    this.REar = new ModelRenderer(this, 18, 56);
    this.REar.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.REar.addBox(-5.5F, -4.0F, -1.5F, 2, 3, 1, 0.0F);
    this.Skirt1 = new ModelRenderer(this, 42, 29);
    this.Skirt1.setRotationPoint(0.0F, 3.0F, 0.0F);
    this.Skirt1.addBox(-3.5F, 0.0F, -2.0F, 7, 4, 4, 0.0F);
    this.Hair = new ModelRenderer(this, 0, 13);
    this.Hair.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.Hair.addBox(-4.0F, -7.0F, -3.5F, 8, 8, 7, 0.0F);
    this.Eye = new ModelRenderer(this, 0, 0);
    this.Eye.setRotationPoint(0.0F, -2.5F, -2.6F);
    this.Eye.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
    this.Chest = new ModelRenderer(this, 0, 47);
    this.Chest.setRotationPoint(0.0F, 1.0F, 0.0F);
    this.Chest.addBox(-3.5F, 0.0F, -1.5F, 7, 5, 3, 0.0F);
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
    this.Body2Child.addChild(this.Body2ChildChild);
    this.Body2ChildChild.addChild(this.Body2ChildChildChild0);
    this.Head.addChild(this.HTop);
    this.Chest.addChild(this.Neck);
    this.Chest.addChild(this.BustFront);
    this.Head.addChild(this.LHorn);
    this.Neck.addChild(this.Head);
    this.Abdoman.addChild(this.RLeg);
    this.Chest.addChild(this.RArm);
    this.Body2ChildChildChild0.addChild(this.Body2ChildChildChild1);
    this.Skirt1.addChild(this.Skirt2);
    this.Head.addChild(this.RHorn);
    this.BustFront.addChild(this.BustTop);
    this.Abdoman.addChild(this.Body2Child);
    this.Abdoman.addChild(this.LLeg);
    this.Chest.addChild(this.Abdoman);
    this.Chest.addChild(this.LArm);
    this.Head.addChild(this.LEar);
    this.Head.addChild(this.REar);
    this.Abdoman.addChild(this.Skirt1);
    this.Head.addChild(this.Hair);
    this.Head.addChild(this.Eye);
    this.BustFront.addChild(this.BustBottom);
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
    EntityGuardian entity = (EntityGuardian)entityIn;
    float f6 = ageInTicks - entity.ticksExisted;
    this.Head.rotateAngleY = netHeadYaw * 0.017453292F;
    this.Head.rotateAngleX = headPitch * 0.017453292F;
    if (!entity.isElder())
      limbSwing *= 2.0F; 
    this.Eye.rotationPointZ = -2.6F;
    Object object = Minecraft.getMinecraft().getRenderViewEntity();
    if (entity.isElder() && !this.isChild) {
      this.BustFront.isHidden = false;
    } else {
      this.BustFront.isHidden = true;
    } 
    if (entity.hasTargetedEntity())
      object = entity.getTargetedEntity(); 
    if (object != null) {
      Vec3d vec3 = ((Entity)object).getPositionEyes(0.0F);
      Vec3d vec31 = entityIn.getPositionEyes(0.0F);
      double d0 = vec3.y - vec31.y;
      if (d0 <= 0.0D || !entity.getCurrentBook().isEmpty()) {
        this.Eye.rotationPointY = -2.5F;
      } else {
        this.Eye.rotationPointY = -3.5F;
      } 
      Vec3d vec32 = entityIn.getLook(0.0F);
      vec32 = new Vec3d(vec32.x, 0.0D, vec32.z);
      Vec3d vec33 = (new Vec3d(vec31.x - vec3.x, 0.0D, vec31.z - vec3.z)).normalize().rotateYaw(1.5707964F);
      double d1 = vec32.dotProduct(vec33);
      this.Eye.rotationPointX = !entity.getCurrentBook().isEmpty() ? (MathHelper.sin(ageInTicks * 0.1F + MathHelper.sin(ageInTicks * 0.05F)) * 0.5F) : (0.05F + MathHelper.sqrt((float)Math.abs(d1)) * 0.5F * (float)Math.signum(d1));
    } 
    this.Eye.showModel = true;
    float f8 = entity.getTailAnimation(f6);
    this.Body2Child.rotateAngleY = MathHelper.sin(f8) * 3.1415927F * 0.05F;
    this.Body2ChildChild.rotateAngleY = MathHelper.sin(f8) * 3.1415927F * 0.1F;
    this.Body2ChildChildChild0.rotateAngleY = MathHelper.sin(f8) * 3.1415927F * 0.15F;
    this.BustFront.setRotationPoint(0.0F, 2.75F, -1.0F);
    this.RArm.setRotationPoint(-3.5F, 2.0F, 0.0F);
    this.LArm.setRotationPoint(3.5F, 2.0F, 0.0F);
    this.Chest.setRotationPoint(0.0F, 1.0F, 0.0F);
    this.Chest.rotateAngleX = 0.0F;
    this.Abdoman.rotateAngleX = 0.0F;
    this.Chest.rotateAngleZ = 0.0F;
    this.Abdoman.rotateAngleZ = 0.0F;
    this.BustFront.rotateAngleX = 0.0F;
    this.Skirt1.rotateAngleX = 0.0F;
    this.Skirt2.rotateAngleX = 0.0F;
    this.Neck.rotateAngleZ = 0.0F;
    this.Neck.rotateAngleY = 0.0F;
    this.Neck.rotateAngleX = 0.0F;
    this.Body2Child.rotateAngleX = 0.0F;
    this.Body2ChildChild.rotateAngleX = 0.0F;
    this.Body2ChildChildChild0.rotateAngleX = 0.0F;
    if (!entity.isInWater()) {
      this.RArm.rotateAngleZ = 0.0F;
      this.LArm.rotateAngleZ = 0.0F;
      this.RArm.rotateAngleZ = 0.0F;
      this.LArm.rotateAngleZ = 0.0F;
      this.RArm.rotateAngleY = 0.0F;
      this.LArm.rotateAngleY = 0.0F;
      this.BustFront.rotateAngleX = (float)(entity.motionY / 2.0D);
      if (entity.isSitResting() || (entity.isElder() && !this.isChild && entity.isEntityAlive())) {
        this.RArm.rotateAngleX += -0.31415927F;
        this.LArm.rotateAngleX += -0.31415927F;
        this.RLeg.rotateAngleX = -1.2F + MathHelper.cos(ageInTicks * 0.2F + 1.0F) * 0.1F;
        this.RLeg.rotateAngleY = -0.3F;
        this.RLeg.rotateAngleZ = 0.025F;
        this.LLeg.rotateAngleX = -1.6F + MathHelper.cos(ageInTicks * 0.2F + 1.0F) * 0.1F;
        this.LLeg.rotateAngleY = 0.25F;
        this.LLeg.rotateAngleZ = -0.025F;
        this.RArm.rotateAngleX = -0.25F;
        this.LArm.rotateAngleX = -0.25F;
        this.Skirt1.rotateAngleX = -0.5F + MathHelper.cos(ageInTicks * 0.2F + 1.0F) * 0.05F;
        this.Skirt2.rotateAngleX = -0.7F + MathHelper.cos(ageInTicks * 0.2F + 1.0F) * 0.05F;
        this.BustFront.rotateAngleX += MathHelper.cos(ageInTicks * 0.2F + 1.0F) * 0.05F;
      } else {
        this.RLeg.rotateAngleY = 0.0F;
        this.LLeg.rotateAngleY = 0.0F;
        this.RLeg.rotateAngleZ = 0.0F;
        this.LLeg.rotateAngleZ = 0.0F;
        this.RArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 2.0F * limbSwingAmount * 0.5F;
        this.LArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
        this.RLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
        this.LLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount * 0.5F;
      } 
    } else {
      float f61 = MathHelper.cos(ageInTicks * 0.1F);
      this.Neck.rotateAngleX -= (0.065F + 0.02F * f61) * 3.1415927F;
      this.Chest.rotateAngleX = (0.065F + 0.02F * f61) * 3.1415927F;
      this.Abdoman.rotateAngleX = (0.065F + 0.01F * f61) * 3.1415927F + limbSwingAmount;
      this.Body2Child.rotateAngleX = -((0.065F + 0.02F * f61) * 3.1415927F) - limbSwingAmount;
      this.LLeg.rotateAngleX = (0.05F + 0.01F * f61) * 3.1415927F;
      this.RLeg.rotateAngleX = (0.05F + 0.01F * f61) * 3.1415927F;
      this.RLeg.rotateAngleY = 0.0F;
      this.LLeg.rotateAngleY = 0.0F;
      this.RLeg.rotateAngleZ = 0.0F;
      this.LLeg.rotateAngleZ = 0.0F;
      this.RArm.rotateAngleX = (0.1F + 0.2F * f61) * 3.1415927F;
      this.LArm.rotateAngleX = (0.1F + 0.2F * f61) * 3.1415927F;
      this.RArm.rotateAngleY = (0.1F + -0.15F * f61) * 3.1415927F;
      this.LArm.rotateAngleY = (-0.1F + 0.15F * f61) * 3.1415927F;
      this.LArm.rotateAngleZ = (-0.075F + 0.05F * f61) * 3.1415927F;
      this.RArm.rotateAngleZ = (0.075F + -0.05F * f61) * 3.1415927F;
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
    if (this.isSneak && !entity.isSitResting() && !entity.isInWater()) {
      this.Chest.rotationPointY += 5.0F;
      this.RArm.rotationPointY += 5.0F;
      this.LArm.rotationPointY += 5.0F;
      float f61 = MathHelper.cos(ageInTicks * 0.1F);
      this.Neck.rotateAngleX -= (0.065F + 0.02F * f61) * 3.1415927F;
      this.Chest.rotateAngleX = (0.065F + 0.02F * f61) * 3.1415927F;
      this.Abdoman.rotateAngleX = 0.25F + (0.065F + 0.02F * f61) * 3.1415927F;
      this.LLeg.rotateAngleX = (0.15F + 0.05F * f61) * 3.1415927F;
      this.RLeg.rotateAngleX = (0.15F + 0.05F * f61) * 3.1415927F;
      this.LLeg.rotateAngleZ = (-0.05F + 0.05F * f61) * 3.1415927F;
      this.RLeg.rotateAngleZ = (0.05F + -0.05F * f61) * 3.1415927F;
      this.LLeg.rotateAngleY = (-0.05F + 0.05F * f61) * 3.1415927F;
      this.RLeg.rotateAngleY = (0.05F + -0.05F * f61) * 3.1415927F;
      this.RArm.rotateAngleX = (0.1F + 0.2F * f61) * 3.1415927F;
      this.LArm.rotateAngleX = (0.1F + 0.2F * f61) * 3.1415927F;
      this.RArm.rotateAngleY = (0.1F + -0.15F * f61) * 3.1415927F;
      this.LArm.rotateAngleY = (-0.1F + 0.15F * f61) * 3.1415927F;
      this.LArm.rotateAngleZ = (-0.075F + 0.05F * f61) * 3.1415927F;
      this.RArm.rotateAngleZ = (0.075F + -0.05F * f61) * 3.1415927F;
    } 
    if (entity.getTargetedEntity() != null)
      if (entity.isLeftHanded()) {
        if (!entity.isElder())
          this.RArm.rotateAngleX = this.Neck.rotateAngleX + this.Head.rotateAngleX - 1.6F; 
        this.LArm.rotateAngleX = this.Neck.rotateAngleX + this.Head.rotateAngleX - 1.6F;
      } else {
        if (!entity.isElder())
          this.LArm.rotateAngleX = this.Neck.rotateAngleX + this.Head.rotateAngleX - 1.6F; 
        this.RArm.rotateAngleX = this.Neck.rotateAngleX + this.Head.rotateAngleX - 1.6F;
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
      this.RArm.rotationPointZ = MathHelper.sin(this.Abdoman.rotateAngleY) * 5.0F;
      this.RArm.rotationPointX = -MathHelper.cos(this.Abdoman.rotateAngleY) * 3.0F;
      this.LArm.rotationPointZ = -MathHelper.sin(this.Abdoman.rotateAngleY) * 5.0F;
      this.LArm.rotationPointX = MathHelper.cos(this.Abdoman.rotateAngleY) * 3.0F;
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
      if (entity.isElder()) {
        this.Chest.setRotationPoint(0.0F + MathHelper.sin(ageInTicks * 0.125F) * 1.0F, 0.0F - MathHelper.cos(ageInTicks * 0.125F) * 1.0F, 0.0F + MathHelper.cos(ageInTicks * 0.125F) * 1.0F);
        this.Neck.rotateAngleZ = MathHelper.cos(ageInTicks * 0.5F - 3.1415927F) * 0.5F;
        this.Head.rotateAngleX += MathHelper.cos(ageInTicks * 1.0F - 3.0F) * 0.5F;
        this.Chest.rotateAngleZ = MathHelper.cos(ageInTicks * 0.25F) * 0.25F;
        this.Abdoman.rotateAngleZ = MathHelper.cos(ageInTicks * 0.25F - 1.0F) * 0.5F;
        this.RLeg.rotateAngleZ = MathHelper.cos(ageInTicks * 0.25F - 2.0F) * 0.5F;
        this.LLeg.rotateAngleZ = MathHelper.cos(ageInTicks * 0.25F - 2.0F) * 0.5F;
      } else {
        this.Neck.rotateAngleZ = MathHelper.cos(ageInTicks * 0.5F - 3.1415927F) * 0.5F;
        this.Head.rotateAngleX += MathHelper.cos(ageInTicks * 1.0F - 3.0F) * 0.5F;
        this.Chest.rotateAngleZ = -(MathHelper.cos(ageInTicks * 0.5F) * 0.5F);
        this.Abdoman.rotateAngleZ = MathHelper.cos(ageInTicks * 0.5F) * 0.5F;
        this.RLeg.rotateAngleZ = MathHelper.cos(ageInTicks * 0.5F) * 0.5F;
        this.LLeg.rotateAngleZ = MathHelper.cos(ageInTicks * 0.5F) * 0.5F;
      } 
      this.RArm.rotateAngleX = -1.0F + MathHelper.cos(ageInTicks * 0.5F - 2.0F) * 1.0F;
      this.LArm.rotateAngleX = -1.0F + MathHelper.cos(ageInTicks * 0.5F - 2.0F) * 1.0F;
      this.RArm.rotateAngleZ = 2.0F;
      this.LArm.rotateAngleZ = -2.0F;
      this.RArm.rotateAngleY = 0.0F;
      this.LArm.rotateAngleY = 0.0F;
      this.Body2Child.rotateAngleY = MathHelper.sin(ageInTicks * 0.5F - 2.0F) * 0.5F;
      this.Body2ChildChild.rotateAngleY = MathHelper.sin(ageInTicks * 0.5F - 2.0F) * 0.5F;
      this.Body2ChildChildChild0.rotateAngleY = MathHelper.sin(ageInTicks * 0.5F - 2.0F) * 0.5F;
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
