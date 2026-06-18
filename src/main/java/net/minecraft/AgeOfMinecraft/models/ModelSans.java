package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.cameos.EntitySans;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;

public class ModelSans extends ModelBase {
  public ModelRenderer Trunks;
  
  public ModelRenderer RLeg;
  
  public ModelRenderer LLeg;
  
  public ModelRenderer Body;
  
  public ModelRenderer LShoulder;
  
  public ModelRenderer RShoulder;
  
  public ModelRenderer RPocket;
  
  public ModelRenderer LPocket;
  
  public ModelRenderer Neck;
  
  public ModelRenderer LArm;
  
  public ModelRenderer LHand;
  
  public ModelRenderer RArm;
  
  public ModelRenderer RHand;
  
  public ModelRenderer Head;
  
  public ModelRenderer REye;
  
  public ModelRenderer LEye;
  
  public ModelRenderer JudgmentEyeYellow;
  
  public ModelRenderer JudgmentEyeBlue;
  
  public ModelRenderer RTrunk;
  
  public ModelRenderer RSlipper;
  
  public ModelRenderer LTrunk;
  
  public ModelRenderer LSlipper;
  
  public ModelSans() {
    this.textureWidth = 64;
    this.textureHeight = 64;
    this.Neck = new ModelRenderer(this, 24, 0);
    this.Neck.setRotationPoint(0.0F, -4.3F, 0.0F);
    this.Neck.addBox(-1.5F, -2.0F, -1.5F, 3, 2, 3, 0.0F);
    this.LPocket = new ModelRenderer(this, 48, 12);
    this.LPocket.mirror = true;
    this.LPocket.setRotationPoint(0.0F, -1.0F, -0.5F);
    this.LPocket.addBox(0.0F, 0.0F, -3.5F, 4, 4, 4, 0.0F);
    this.RPocket = new ModelRenderer(this, 48, 12);
    this.RPocket.setRotationPoint(0.0F, -1.0F, -0.5F);
    this.RPocket.addBox(-4.0F, 0.0F, -3.5F, 4, 4, 4, 0.0F);
    this.LLeg = new ModelRenderer(this, 52, 0);
    this.LLeg.mirror = true;
    this.LLeg.setRotationPoint(2.8F, 16.0F, 0.0F);
    this.LLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 8, 3, 0.0F);
    this.Body = new ModelRenderer(this, 0, 16);
    this.Body.setRotationPoint(0.0F, -4.0F, 0.0F);
    this.Body.addBox(-4.0F, -6.0F, -3.0F, 8, 7, 6, 0.0F);
    this.LSlipper = new ModelRenderer(this, 28, 28);
    this.LSlipper.mirror = true;
    this.LSlipper.setRotationPoint(0.0F, 6.0F, 0.0F);
    this.LSlipper.addBox(-2.0F, 0.0F, -3.0F, 4, 2, 5, 0.0F);
    this.Head = new ModelRenderer(this, 0, 0);
    this.Head.setRotationPoint(0.0F, -1.7F, 0.0F);
    this.Head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
    this.REye = new ModelRenderer(this, 0, 0);
    this.REye.setRotationPoint(-1.8F, -4.3F, -3.55F);
    this.REye.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
    this.LArm = new ModelRenderer(this, 36, 9);
    this.LArm.mirror = true;
    this.LArm.setRotationPoint(1.5F, 5.0F, 0.0F);
    this.LArm.addBox(-1.5F, 0.0F, -1.5F, 3, 6, 3, 0.0F);
    this.LHand = new ModelRenderer(this, 22, 18);
    this.LHand.setRotationPoint(0.0F, 5.0F, 0.0F);
    this.LHand.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
    this.LEye = new ModelRenderer(this, 0, 0);
    this.LEye.mirror = true;
    this.LEye.setRotationPoint(1.8F, -4.3F, -3.55F);
    this.LEye.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
    this.RShoulder = new ModelRenderer(this, 36, 0);
    this.RShoulder.setRotationPoint(-3.5F, -4.0F, 0.0F);
    this.RShoulder.addBox(-3.0F, -1.0F, -1.5F, 3, 6, 3, 0.0F);
    this.JudgmentEyeBlue = new ModelRenderer(this, 0, 2);
    this.JudgmentEyeBlue.mirror = true;
    this.JudgmentEyeBlue.setRotationPoint(2.0F, -4.3F, -3.48F);
    this.JudgmentEyeBlue.addBox(-1.2F, -1.2F, -0.5F, 2, 2, 1, 0.0F);
    this.LShoulder = new ModelRenderer(this, 36, 0);
    this.LShoulder.mirror = true;
    this.LShoulder.setRotationPoint(3.5F, -4.0F, 0.0F);
    this.LShoulder.addBox(0.0F, -1.0F, -1.5F, 3, 6, 3, 0.0F);
    this.LTrunk = new ModelRenderer(this, 28, 18);
    this.LTrunk.mirror = true;
    this.LTrunk.setRotationPoint(-0.5F, 0.0F, 0.0F);
    this.LTrunk.addBox(-2.0F, 0.0F, -2.0F, 4, 5, 4, 0.0F);
    this.RLeg = new ModelRenderer(this, 52, 0);
    this.RLeg.setRotationPoint(-2.8F, 16.0F, 0.0F);
    this.RLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 8, 3, 0.0F);
    this.RTrunk = new ModelRenderer(this, 28, 18);
    this.RTrunk.setRotationPoint(0.5F, 0.0F, 0.0F);
    this.RTrunk.addBox(-2.0F, 0.0F, -2.0F, 4, 5, 4, 0.0F);
    this.Trunks = new ModelRenderer(this, 0, 29);
    this.Trunks.mirror = true;
    this.Trunks.setRotationPoint(0.0F, 15.5F, 0.0F);
    this.Trunks.addBox(-4.5F, -3.0F, -3.0F, 9, 4, 6, 0.0F);
    this.RHand = new ModelRenderer(this, 22, 18);
    this.RHand.mirror = true;
    this.RHand.setRotationPoint(0.0F, 5.0F, 0.0F);
    this.RHand.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
    this.RSlipper = new ModelRenderer(this, 28, 28);
    this.RSlipper.setRotationPoint(0.0F, 6.0F, 0.0F);
    this.RSlipper.addBox(-2.0F, 0.0F, -3.0F, 4, 2, 5, 0.0F);
    this.RArm = new ModelRenderer(this, 36, 9);
    this.RArm.setRotationPoint(-1.5F, 5.0F, 0.0F);
    this.RArm.addBox(-1.5F, 0.0F, -1.5F, 3, 6, 3, 0.0F);
    this.JudgmentEyeYellow = new ModelRenderer(this, 0, 5);
    this.JudgmentEyeYellow.mirror = true;
    this.JudgmentEyeYellow.setRotationPoint(2.0F, -4.3F, -3.47F);
    this.JudgmentEyeYellow.addBox(-1.2F, -1.2F, -0.5F, 2, 2, 1, 0.0F);
    this.Body.addChild(this.Neck);
    this.Body.addChild(this.LPocket);
    this.Body.addChild(this.RPocket);
    this.Trunks.addChild(this.Body);
    this.LLeg.addChild(this.LSlipper);
    this.Neck.addChild(this.Head);
    this.Head.addChild(this.REye);
    this.LShoulder.addChild(this.LArm);
    this.LArm.addChild(this.LHand);
    this.Head.addChild(this.LEye);
    this.Body.addChild(this.RShoulder);
    this.Head.addChild(this.JudgmentEyeBlue);
    this.Body.addChild(this.LShoulder);
    this.LLeg.addChild(this.LTrunk);
    this.RLeg.addChild(this.RTrunk);
    this.RArm.addChild(this.RHand);
    this.RLeg.addChild(this.RSlipper);
    this.RShoulder.addChild(this.RArm);
    this.Head.addChild(this.JudgmentEyeYellow);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    this.LLeg.render(f5);
    this.RLeg.render(f5);
    this.LLeg.render(f5);
    this.Trunks.render(f5);
  }
  
  public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
    modelRenderer.rotateAngleX = x;
    modelRenderer.rotateAngleY = y;
    modelRenderer.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    this.Head.rotateAngleY = netHeadYaw * 0.017453292F;
    this.Head.rotateAngleX = headPitch * 0.017453292F;
    setRotateAngle(this.LPocket, 0.0F, 0.0F, -0.22759093F);
    setRotateAngle(this.RPocket, 0.0F, 0.0F, 0.22759093F);
    setRotateAngle(this.LTrunk, -0.0F, 0.0F, -0.13665928F);
    setRotateAngle(this.RTrunk, 0.0F, 0.0F, 0.091106184F);
    this.Neck.setRotationPoint(-0.05F + MathHelper.sin(ageInTicks * 0.6F - 1.8F) * 0.1F, -4.25F + MathHelper.sin(ageInTicks * 0.6F) * 0.05F, 0.0F);
    this.Trunks.setRotationPoint(-0.1F + MathHelper.sin(ageInTicks * 0.3F) * 0.2F, 15.5F + MathHelper.sin(ageInTicks * 0.6F) * 0.05F, 0.0F);
    this.RLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount;
    this.LLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.0F * limbSwingAmount;
    EntitySans sans = (EntitySans)entityIn;
    switch (sans.getEyeType()) {
      case 1:
        this.JudgmentEyeBlue.setRotationPoint(2.0F, -4.3F, -3.48F);
        this.JudgmentEyeYellow.setRotationPoint(2.0F, -4.3F, -3.47F);
        this.REye.setRotationPoint(-1.8F, -4.3F, -3.47F);
        this.LEye.setRotationPoint(1.8F, -4.3F, -3.47F);
        break;
      case 2:
        this.JudgmentEyeBlue.setRotationPoint(2.0F, -4.3F, -3.55F - MathHelper.sin(ageInTicks * 1.5F) * 0.01F);
        this.JudgmentEyeYellow.setRotationPoint(2.0F, -4.3F, -3.54F + MathHelper.sin(ageInTicks * 1.5F) * 0.01F);
        this.REye.setRotationPoint(-1.8F, -4.3F, -3.47F);
        this.LEye.setRotationPoint(1.8F, -4.3F, -3.47F);
        break;
      default:
        this.JudgmentEyeBlue.setRotationPoint(2.0F, -4.3F, -3.48F);
        this.JudgmentEyeYellow.setRotationPoint(2.0F, -4.3F, -3.47F);
        this.REye.setRotationPoint(-1.8F, -4.3F, -3.55F);
        this.LEye.setRotationPoint(1.8F, -4.3F, -3.55F);
        break;
    } 
    setRotateAngle(this.LShoulder, -0.3642502F, 0.0F, -0.5462881F);
    setRotateAngle(this.LArm, -0.18203785F, -0.31869712F, 1.8668041F);
    setRotateAngle(this.LHand, 0.0F, 0.0F, 0.0F);
    setRotateAngle(this.RShoulder, -0.3642502F, 0.0F, 0.5462881F);
    setRotateAngle(this.RArm, -0.18203785F, 0.31869712F, -1.8668041F);
    setRotateAngle(this.RHand, 0.0F, 0.0F, 0.0F);
    if (sans.getAttackType() == 6) {
      setRotateAngle(this.LArm, 0.0F, 0.0F, -1.4F);
      setRotateAngle(this.LShoulder, 0.0F, 0.0F, -0.8F);
      setRotateAngle(this.LHand, 0.0F, 0.0F, 0.6F);
      setRotateAngle(this.RArm, 0.0F, 0.0F, 1.4F);
      setRotateAngle(this.RShoulder, 0.0F, 0.0F, 0.8F);
      setRotateAngle(this.RHand, 0.0F, 0.0F, -0.6F);
    } 
    if (sans.getAttackType() == 5) {
      switch (sans.getHandDirection()) {
        case UP:
          setRotateAngle(this.LArm, -0.18203785F, -0.31869712F, -1.4F);
          setRotateAngle(this.LShoulder, -0.3642502F, 0.0F, -1.6F);
          return;
        case DOWN:
          setRotateAngle(this.LArm, -0.18203785F, -0.31869712F, 0.0F);
          setRotateAngle(this.LShoulder, -0.3642502F, 0.0F, 0.0F);
          return;
        case NORTH:
          setRotateAngle(this.LArm, 0.0F, 0.0F, 0.0F);
          setRotateAngle(this.LShoulder, -1.6F, 0.0F, 0.0F);
          return;
        case EAST:
          setRotateAngle(this.LArm, 0.0F, 0.0F, 0.0F);
          setRotateAngle(this.LShoulder, -1.6F, -1.6F, 0.0F);
          return;
        case WEST:
          setRotateAngle(this.LArm, 0.0F, 0.0F, 0.8F);
          setRotateAngle(this.LShoulder, -1.6F, 0.6F, 0.0F);
          return;
      } 
      setRotateAngle(this.LArm, -0.18203785F, -0.31869712F, 1.8668041F);
      setRotateAngle(this.LShoulder, -0.3642502F, 0.0F, -0.5462881F);
    } 
  }
  
  public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
    super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
  }
}
