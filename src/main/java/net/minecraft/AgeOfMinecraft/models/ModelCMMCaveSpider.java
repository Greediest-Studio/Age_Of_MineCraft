package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class ModelCMMCaveSpider extends ModelBiped implements ICappedModel {
  public ModelRenderer LLeg;
  
  public ModelRenderer Abdoman;
  
  public ModelRenderer RLeg;
  
  public ModelRenderer LArm;
  
  public ModelRenderer RArm;
  
  public ModelRenderer Body;
  
  public ModelRenderer Skirt2;
  
  public ModelRenderer Neck;
  
  public ModelRenderer Head;
  
  public ModelRenderer Hair;
  
  public ModelRenderer HTop;
  
  public ModelRenderer HLeft;
  
  public ModelRenderer HLeft3;
  
  public ModelRenderer HRight3;
  
  public ModelRenderer HRight;
  
  public ModelRenderer HLeft2;
  
  public ModelRenderer HRight2;
  
  public ModelRenderer Skirt;
  
  public ModelRenderer bipedCape;
  
  public void renderCape(float scale, float flo1, float flo2, float flo3) {
    GlStateManager.pushMatrix();
    GlStateManager.translate(0.0F, this.isSneak ? 0.25F : 0.125F, this.isSneak ? -0.625F : -0.0325F);
    GlStateManager.rotate(6.0F + flo2 / 2.0F + flo1 + (this.isSneak ? 20.0F : 1.0F), 1.0F, 0.0F, 0.0F);
    GlStateManager.rotate(flo3 / 2.0F, 0.0F, 0.0F, 1.0F);
    GlStateManager.rotate(-flo3 / 2.0F, 0.0F, 1.0F, 0.0F);
    GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
    this.bipedCape.render(scale);
    GlStateManager.popMatrix();
  }
  
  public ModelCMMCaveSpider() {
    this.bipedCape = new ModelRenderer((ModelBase)this, 0, 0);
    this.bipedCape.setTextureSize(64, 32);
    this.bipedCape.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, 0.0F);
    this.textureWidth = 64;
    this.textureHeight = 64;
    this.Head = new ModelRenderer((ModelBase)this, 0, 0);
    this.Head.setRotationPoint(0.0F, -1.0F, 0.0F);
    this.Head.addBox(-3.5F, -7.0F, -3.0F, 7, 7, 6, 0.0F);
    this.HRight2 = new ModelRenderer((ModelBase)this, 30, 40);
    this.HRight2.setRotationPoint(-2.0F, 0.0F, 0.0F);
    this.HRight2.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
    this.HLeft = new ModelRenderer((ModelBase)this, 42, 31);
    this.HLeft.setRotationPoint(3.0F, -3.5F, 0.0F);
    this.HLeft.addBox(-0.1F, -2.5F, -2.0F, 2, 5, 4, 0.0F);
    this.RArm = new ModelRenderer((ModelBase)this, 36, 0);
    this.RArm.setRotationPoint(-3.5F, 3.0F, 0.0F);
    this.RArm.addBox(-2.0F, -1.0F, -1.0F, 2, 10, 2, 0.0F);
    this.LArm = new ModelRenderer((ModelBase)this, 44, 0);
    this.LArm.setRotationPoint(3.5F, 3.0F, 0.0F);
    this.LArm.addBox(0.0F, -1.0F, -1.0F, 2, 10, 2, 0.0F);
    this.Hair = new ModelRenderer((ModelBase)this, 0, 31);
    this.Hair.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.Hair.addBox(-4.0F, -7.1F, -3.5F, 8, 7, 7, 0.0F);
    this.RLeg = new ModelRenderer((ModelBase)this, 52, 0);
    this.RLeg.setRotationPoint(-2.0F, 14.0F, 0.0F);
    this.RLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 10, 3, 0.0F);
    this.Skirt = new ModelRenderer((ModelBase)this, 18, 24);
    this.Skirt.setRotationPoint(0.0F, 1.0F, 0.0F);
    this.Skirt.addBox(-4.0F, 0.0F, -2.5F, 8, 2, 5, 0.0F);
    this.HLeft2 = new ModelRenderer((ModelBase)this, 38, 40);
    this.HLeft2.setRotationPoint(0.0F, -0.5F, 0.0F);
    this.HLeft2.addBox(1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
    this.HRight = new ModelRenderer((ModelBase)this, 30, 31);
    this.HRight.setRotationPoint(-3.0F, -3.5F, 0.0F);
    this.HRight.addBox(-1.9F, -2.5F, -2.0F, 2, 5, 4, 0.0F);
    this.Skirt2 = new ModelRenderer((ModelBase)this, 20, 18);
    this.Skirt2.setRotationPoint(0.0F, -1.0F, 0.0F);
    this.Skirt2.addBox(-3.5F, 0.0F, -2.0F, 7, 2, 4, 0.0F);
    this.LLeg = new ModelRenderer((ModelBase)this, 52, 0);
    this.LLeg.mirror = true;
    this.LLeg.setRotationPoint(2.0F, 14.0F, 0.0F);
    this.LLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 10, 3, 0.0F);
    this.HRight3 = new ModelRenderer((ModelBase)this, 26, 0);
    this.HRight3.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.HRight3.addBox(-5.0F, -6.9F, -2.5F, 1, 2, 1, 0.0F);
    this.HLeft3 = new ModelRenderer((ModelBase)this, 26, 0);
    this.HLeft3.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.HLeft3.addBox(4.0F, -6.9F, -2.5F, 1, 2, 1, 0.0F);
    this.Body = new ModelRenderer((ModelBase)this, 0, 15);
    this.Body.setRotationPoint(0.0F, -5.0F, 0.0F);
    this.Body.addBox(-3.5F, -5.0F, -1.5F, 7, 5, 3, 0.0F);
    this.Abdoman = new ModelRenderer((ModelBase)this, 0, 23);
    this.Abdoman.setRotationPoint(0.0F, 12.0F, 0.0F);
    this.Abdoman.addBox(-3.0F, -5.0F, -1.5F, 6, 5, 3, 0.0F);
    this.Neck = new ModelRenderer((ModelBase)this, 20, 13);
    this.Neck.setRotationPoint(0.0F, -5.0F, 0.0F);
    this.Neck.addBox(-1.5F, -2.0F, -1.5F, 3, 2, 3, 0.0F);
    this.HTop = new ModelRenderer((ModelBase)this, 0, 45);
    this.HTop.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.HTop.addBox(-3.0F, -8.0F, -3.0F, 6, 1, 6, 0.0F);
    this.Neck.addChild(this.Head);
    this.HRight.addChild(this.HRight2);
    this.Head.addChild(this.HLeft);
    this.Head.addChild(this.Hair);
    this.Skirt2.addChild(this.Skirt);
    this.HLeft.addChild(this.HLeft2);
    this.Head.addChild(this.HRight);
    this.Abdoman.addChild(this.Skirt2);
    this.Head.addChild(this.HRight3);
    this.Head.addChild(this.HLeft3);
    this.Abdoman.addChild(this.Body);
    this.Body.addChild(this.Neck);
    this.Head.addChild(this.HTop);
    this.bipedHead.isHidden = true;
    this.bipedBody.isHidden = true;
    this.bipedRightArm.isHidden = true;
    this.bipedLeftArm.isHidden = true;
    this.bipedRightLeg.isHidden = true;
    this.bipedLeftLeg.isHidden = true;
    this.bipedHeadwear.isHidden = true;
  }
  
  public ModelCMMCaveSpider(float modelSize) {
    super(modelSize);
    this.bipedRightArm = new ModelRenderer((ModelBase)this, 40, 16);
    this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
    this.bipedRightArm.setRotationPoint(-4.0F, 2.0F, 0.0F);
    this.bipedLeftArm = new ModelRenderer((ModelBase)this, 40, 16);
    this.bipedLeftArm.mirror = true;
    this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
    this.bipedLeftArm.setRotationPoint(4.0F, 2.0F, 0.0F);
    this.Head = new ModelRenderer((ModelBase)this, 0, 0);
    this.Head.setRotationPoint(0.0F, -1.0F, 0.0F);
    this.Head.addBox(-3.5F, -7.0F, -3.0F, 7, 7, 6, 0.0F);
    this.HRight2 = new ModelRenderer((ModelBase)this, 30, 40);
    this.HRight2.setRotationPoint(-2.0F, 0.0F, 0.0F);
    this.HRight2.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
    this.HLeft = new ModelRenderer((ModelBase)this, 42, 31);
    this.HLeft.setRotationPoint(3.0F, -3.5F, 0.0F);
    this.HLeft.addBox(-0.1F, -2.5F, -2.0F, 2, 5, 4, 0.0F);
    this.RArm = new ModelRenderer((ModelBase)this, 36, 0);
    this.RArm.setRotationPoint(-3.5F, 3.0F, 0.0F);
    this.RArm.addBox(-2.0F, -1.0F, -1.0F, 2, 10, 2, 0.0F);
    this.LArm = new ModelRenderer((ModelBase)this, 44, 0);
    this.LArm.setRotationPoint(3.5F, 3.0F, 0.0F);
    this.LArm.addBox(0.0F, -1.0F, -1.0F, 2, 10, 2, 0.0F);
    this.Hair = new ModelRenderer((ModelBase)this, 0, 31);
    this.Hair.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.Hair.addBox(-4.0F, -7.1F, -3.5F, 8, 7, 7, 0.0F);
    this.RLeg = new ModelRenderer((ModelBase)this, 52, 0);
    this.RLeg.setRotationPoint(-2.0F, 14.0F, 0.0F);
    this.RLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 10, 3, 0.0F);
    this.Skirt = new ModelRenderer((ModelBase)this, 18, 24);
    this.Skirt.setRotationPoint(0.0F, 1.0F, 0.0F);
    this.Skirt.addBox(-4.0F, 0.0F, -2.5F, 8, 2, 5, 0.0F);
    this.HLeft2 = new ModelRenderer((ModelBase)this, 38, 40);
    this.HLeft2.setRotationPoint(0.0F, -0.5F, 0.0F);
    this.HLeft2.addBox(1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
    this.HRight = new ModelRenderer((ModelBase)this, 30, 31);
    this.HRight.setRotationPoint(-3.0F, -3.5F, 0.0F);
    this.HRight.addBox(-1.9F, -2.5F, -2.0F, 2, 5, 4, 0.0F);
    this.Skirt2 = new ModelRenderer((ModelBase)this, 20, 18);
    this.Skirt2.setRotationPoint(0.0F, -1.0F, 0.0F);
    this.Skirt2.addBox(-3.5F, 0.0F, -2.0F, 7, 2, 4, 0.0F);
    this.LLeg = new ModelRenderer((ModelBase)this, 52, 0);
    this.LLeg.mirror = true;
    this.LLeg.setRotationPoint(2.0F, 14.0F, 0.0F);
    this.LLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 10, 3, 0.0F);
    this.HRight3 = new ModelRenderer((ModelBase)this, 26, 0);
    this.HRight3.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.HRight3.addBox(-5.0F, -6.9F, -2.5F, 1, 2, 1, 0.0F);
    this.HLeft3 = new ModelRenderer((ModelBase)this, 26, 0);
    this.HLeft3.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.HLeft3.addBox(4.0F, -6.9F, -2.5F, 1, 2, 1, 0.0F);
    this.Body = new ModelRenderer((ModelBase)this, 0, 15);
    this.Body.setRotationPoint(0.0F, -5.0F, 0.0F);
    this.Body.addBox(-3.5F, -5.0F, -1.5F, 7, 5, 3, 0.0F);
    this.Abdoman = new ModelRenderer((ModelBase)this, 0, 23);
    this.Abdoman.setRotationPoint(0.0F, 12.0F, 0.0F);
    this.Abdoman.addBox(-3.0F, -5.0F, -1.5F, 6, 5, 3, 0.0F);
    this.Neck = new ModelRenderer((ModelBase)this, 20, 13);
    this.Neck.setRotationPoint(0.0F, -5.0F, 0.0F);
    this.Neck.addBox(-1.5F, -2.0F, -1.5F, 3, 2, 3, 0.0F);
    this.HTop = new ModelRenderer((ModelBase)this, 0, 45);
    this.HTop.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.HTop.addBox(-3.0F, -8.0F, -3.0F, 6, 1, 6, 0.0F);
    this.Neck.addChild(this.Head);
    this.HRight.addChild(this.HRight2);
    this.Head.addChild(this.HLeft);
    this.Head.addChild(this.Hair);
    this.Skirt2.addChild(this.Skirt);
    this.HLeft.addChild(this.HLeft2);
    this.Head.addChild(this.HRight);
    this.Abdoman.addChild(this.Skirt2);
    this.Head.addChild(this.HRight3);
    this.Head.addChild(this.HLeft3);
    this.Abdoman.addChild(this.Body);
    this.Body.addChild(this.Neck);
    this.Head.addChild(this.HTop);
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
  
  protected ModelRenderer getArmForSide(EnumHandSide side) {
    return (side == EnumHandSide.LEFT) ? this.LArm : this.RArm;
  }
  
  public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    EntityTameBase entity = (EntityTameBase)entityIn;
    if (entity.isAIDisabled())
      ageInTicks = 0.0F; 
    limbSwing *= 2.0F;
    this.Head.rotateAngleY = netHeadYaw * 0.017453292F;
    this.Head.rotateAngleX = headPitch * 0.017453292F;
    this.isChild = entity.isChild();
    this.isSneak = (entity.isSneaking() && entity.onGround);
    this.LLeg.setRotationPoint(2.0F, 14.0F, 0.0F);
    this.RLeg.setRotationPoint(-2.0F, 14.0F, 0.0F);
    this.Abdoman.setRotationPoint(0.0F, 12.0F, 0.0F);
    this.RArm.setRotationPoint(-3.5F, 4.0F, 0.0F);
    this.LArm.setRotationPoint(3.5F, 4.0F, 0.0F);
    this.Body.rotateAngleX = 0.0F;
    this.Abdoman.rotateAngleX = 0.0F;
    this.Body.rotateAngleY = 0.0F;
    this.Abdoman.rotateAngleY = 0.0F;
    this.Body.rotateAngleZ = 0.0F;
    this.Abdoman.rotateAngleZ = 0.0F;
    this.Neck.rotateAngleZ = 0.0F;
    this.Neck.rotateAngleY = 0.0F;
    this.Neck.rotateAngleX = 0.0F;
    this.Head.rotateAngleZ = 0.0F;
    this.Skirt.rotateAngleX = 0.0F;
    this.Skirt2.rotateAngleX = 0.0F;
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
    if (entity.isOnLadder() && !entity.isBeingRidden()) {
      this.RArm.rotateAngleX -= 2.0F;
      this.LArm.rotateAngleX -= 2.0F;
      this.RArm.rotateAngleZ -= 0.6F;
      this.LArm.rotateAngleZ += 0.6F;
    } 
    if (!entity.onGround) {
      if (entity.isOnLadder()) {
        this.RArm.rotateAngleX -= 2.0F;
        this.LArm.rotateAngleX -= 2.0F;
      } else {
        this.RArm.rotateAngleX = -2.0F;
        this.LArm.rotateAngleX = -2.0F;
      } 
      this.Neck.rotateAngleX--;
      if (!entity.isOnLadder()) {
        this.RLeg.rotateAngleX = 0.5F;
        this.LLeg.rotateAngleX = 0.5F;
      } 
      this.RLeg.rotateAngleZ = 0.5F;
      this.LLeg.rotateAngleZ = -0.5F;
    } 
    if (entity.isBeingRidden()) {
      if (entity.isOnLadder()) {
        this.RArm.rotateAngleX -= 2.0F;
        this.LArm.rotateAngleX -= 2.0F;
      } else {
        this.RArm.rotateAngleX = -2.0F;
        this.LArm.rotateAngleX = -2.0F;
      } 
      this.RArm.rotateAngleZ -= 0.6F;
      this.LArm.rotateAngleZ += 0.6F;
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
        this.Skirt.rotateAngleX = -0.70685834F;
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
    if (entity.isSitResting()) {
      this.RArm.rotateAngleX += -0.15707964F;
      this.LArm.rotateAngleX += -0.15707964F;
      this.RLeg.rotateAngleX = -1.4137167F;
      this.RLeg.rotateAngleY = 0.05F - MathHelper.cos(ageInTicks * 0.45F) * 0.1F;
      this.RLeg.rotateAngleZ = 0.07853982F;
      this.LLeg.rotateAngleX = -1.4137167F;
      this.LLeg.rotateAngleY = -0.05F + MathHelper.cos(ageInTicks * 0.45F) * 0.1F;
      this.LLeg.rotateAngleZ = -0.07853982F;
      this.Skirt2.rotateAngleX = -0.47123888F;
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
    this.RArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.1F;
    this.LArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.1F;
    this.RArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
    this.LArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
    if (entity.isOnLadder()) {
      limbSwing *= 0.25F;
      limbSwingAmount *= 0.25F;
    } 
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
      this.Neck.rotateAngleZ = MathHelper.cos(ageInTicks * 0.5F - 3.1415927F) * 0.25F;
      this.Head.rotateAngleX += MathHelper.cos(ageInTicks * 1.0F) * 0.25F;
      this.Body.rotateAngleZ = MathHelper.cos(ageInTicks * 0.5F - 3.1415927F) * 0.5F;
      this.Abdoman.rotateAngleZ = MathHelper.cos(ageInTicks * 0.5F) * 0.5F;
      this.Abdoman.rotationPointX = MathHelper.cos(ageInTicks * 0.5F - 3.1415927F) * 1.0F;
      this.Abdoman.rotationPointY = 12.0F + MathHelper.cos(ageInTicks * 1.0F) * 1.0F;
      this.RArm.rotationPointX = -3.5F + MathHelper.cos(ageInTicks * 0.5F) * 3.0F;
      this.LArm.rotationPointX = 3.5F + MathHelper.cos(ageInTicks * 0.5F) * 3.0F;
      this.RArm.rotationPointY = 4.0F + MathHelper.cos(ageInTicks * 1.0F) * 1.0F;
      this.LArm.rotationPointY = 4.0F + MathHelper.cos(ageInTicks * 1.0F) * 1.0F;
      this.RArm.rotateAngleX = -0.5F + MathHelper.cos(ageInTicks * 0.5F - 3.0F) * 1.0F;
      this.LArm.rotateAngleX = 0.0F + MathHelper.cos(ageInTicks * 1.0F) * 0.5F;
      this.RArm.rotateAngleZ = 2.0F;
      this.LArm.rotateAngleZ = -2.0F + MathHelper.sin(ageInTicks * 1.0F) * 0.5F;
      this.RArm.rotateAngleY = 0.0F;
      this.LArm.rotateAngleY = 0.0F;
      this.LLeg.rotateAngleZ = -(MathHelper.cos(ageInTicks * 0.5F) * 0.2F);
      this.LLeg.rotateAngleX = 0.0F;
      this.RLeg.rotateAngleX = MathHelper.cos(ageInTicks * 0.5F) * 0.5F;
      this.RLeg.rotationPointX = -2.0F - MathHelper.cos(ageInTicks * 0.5F) * 2.0F;
      this.LLeg.rotationPointX = 2.0F - MathHelper.cos(ageInTicks * 0.5F) * 2.0F;
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
    if (entity.deathTime > 0) {
      float k = (entity.deathTime - 1.0F) / 20.0F;
      k = MathHelper.sqrt(k);
      if (k > 1.0F)
        k = 1.0F; 
      this.RArm.rotateAngleZ = k * 2.25F;
      this.LArm.rotateAngleZ = -k * 2.25F;
      this.RLeg.rotateAngleZ = k * 0.25F;
      this.LLeg.rotateAngleZ = -k * 0.25F;
      this.Neck.rotateAngleY = k * 0.25F;
      this.Head.rotateAngleY = k * 0.75F;
    } 
  }
}
