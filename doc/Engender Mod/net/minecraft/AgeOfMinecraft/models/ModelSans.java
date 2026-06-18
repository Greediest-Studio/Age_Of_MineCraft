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
    this.field_78090_t = 64;
    this.field_78089_u = 64;
    this.Neck = new ModelRenderer(this, 24, 0);
    this.Neck.func_78793_a(0.0F, -4.3F, 0.0F);
    this.Neck.func_78790_a(-1.5F, -2.0F, -1.5F, 3, 2, 3, 0.0F);
    this.LPocket = new ModelRenderer(this, 48, 12);
    this.LPocket.field_78809_i = true;
    this.LPocket.func_78793_a(0.0F, -1.0F, -0.5F);
    this.LPocket.func_78790_a(0.0F, 0.0F, -3.5F, 4, 4, 4, 0.0F);
    this.RPocket = new ModelRenderer(this, 48, 12);
    this.RPocket.func_78793_a(0.0F, -1.0F, -0.5F);
    this.RPocket.func_78790_a(-4.0F, 0.0F, -3.5F, 4, 4, 4, 0.0F);
    this.LLeg = new ModelRenderer(this, 52, 0);
    this.LLeg.field_78809_i = true;
    this.LLeg.func_78793_a(2.8F, 16.0F, 0.0F);
    this.LLeg.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 8, 3, 0.0F);
    this.Body = new ModelRenderer(this, 0, 16);
    this.Body.func_78793_a(0.0F, -4.0F, 0.0F);
    this.Body.func_78790_a(-4.0F, -6.0F, -3.0F, 8, 7, 6, 0.0F);
    this.LSlipper = new ModelRenderer(this, 28, 28);
    this.LSlipper.field_78809_i = true;
    this.LSlipper.func_78793_a(0.0F, 6.0F, 0.0F);
    this.LSlipper.func_78790_a(-2.0F, 0.0F, -3.0F, 4, 2, 5, 0.0F);
    this.Head = new ModelRenderer(this, 0, 0);
    this.Head.func_78793_a(0.0F, -1.7F, 0.0F);
    this.Head.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
    this.REye = new ModelRenderer(this, 0, 0);
    this.REye.func_78793_a(-1.8F, -4.3F, -3.55F);
    this.REye.func_78790_a(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
    this.LArm = new ModelRenderer(this, 36, 9);
    this.LArm.field_78809_i = true;
    this.LArm.func_78793_a(1.5F, 5.0F, 0.0F);
    this.LArm.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 6, 3, 0.0F);
    this.LHand = new ModelRenderer(this, 22, 18);
    this.LHand.func_78793_a(0.0F, 5.0F, 0.0F);
    this.LHand.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
    this.LEye = new ModelRenderer(this, 0, 0);
    this.LEye.field_78809_i = true;
    this.LEye.func_78793_a(1.8F, -4.3F, -3.55F);
    this.LEye.func_78790_a(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
    this.RShoulder = new ModelRenderer(this, 36, 0);
    this.RShoulder.func_78793_a(-3.5F, -4.0F, 0.0F);
    this.RShoulder.func_78790_a(-3.0F, -1.0F, -1.5F, 3, 6, 3, 0.0F);
    this.JudgmentEyeBlue = new ModelRenderer(this, 0, 2);
    this.JudgmentEyeBlue.field_78809_i = true;
    this.JudgmentEyeBlue.func_78793_a(2.0F, -4.3F, -3.48F);
    this.JudgmentEyeBlue.func_78790_a(-1.2F, -1.2F, -0.5F, 2, 2, 1, 0.0F);
    this.LShoulder = new ModelRenderer(this, 36, 0);
    this.LShoulder.field_78809_i = true;
    this.LShoulder.func_78793_a(3.5F, -4.0F, 0.0F);
    this.LShoulder.func_78790_a(0.0F, -1.0F, -1.5F, 3, 6, 3, 0.0F);
    this.LTrunk = new ModelRenderer(this, 28, 18);
    this.LTrunk.field_78809_i = true;
    this.LTrunk.func_78793_a(-0.5F, 0.0F, 0.0F);
    this.LTrunk.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 5, 4, 0.0F);
    this.RLeg = new ModelRenderer(this, 52, 0);
    this.RLeg.func_78793_a(-2.8F, 16.0F, 0.0F);
    this.RLeg.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 8, 3, 0.0F);
    this.RTrunk = new ModelRenderer(this, 28, 18);
    this.RTrunk.func_78793_a(0.5F, 0.0F, 0.0F);
    this.RTrunk.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 5, 4, 0.0F);
    this.Trunks = new ModelRenderer(this, 0, 29);
    this.Trunks.field_78809_i = true;
    this.Trunks.func_78793_a(0.0F, 15.5F, 0.0F);
    this.Trunks.func_78790_a(-4.5F, -3.0F, -3.0F, 9, 4, 6, 0.0F);
    this.RHand = new ModelRenderer(this, 22, 18);
    this.RHand.field_78809_i = true;
    this.RHand.func_78793_a(0.0F, 5.0F, 0.0F);
    this.RHand.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
    this.RSlipper = new ModelRenderer(this, 28, 28);
    this.RSlipper.func_78793_a(0.0F, 6.0F, 0.0F);
    this.RSlipper.func_78790_a(-2.0F, 0.0F, -3.0F, 4, 2, 5, 0.0F);
    this.RArm = new ModelRenderer(this, 36, 9);
    this.RArm.func_78793_a(-1.5F, 5.0F, 0.0F);
    this.RArm.func_78790_a(-1.5F, 0.0F, -1.5F, 3, 6, 3, 0.0F);
    this.JudgmentEyeYellow = new ModelRenderer(this, 0, 5);
    this.JudgmentEyeYellow.field_78809_i = true;
    this.JudgmentEyeYellow.func_78793_a(2.0F, -4.3F, -3.47F);
    this.JudgmentEyeYellow.func_78790_a(-1.2F, -1.2F, -0.5F, 2, 2, 1, 0.0F);
    this.Body.func_78792_a(this.Neck);
    this.Body.func_78792_a(this.LPocket);
    this.Body.func_78792_a(this.RPocket);
    this.Trunks.func_78792_a(this.Body);
    this.LLeg.func_78792_a(this.LSlipper);
    this.Neck.func_78792_a(this.Head);
    this.Head.func_78792_a(this.REye);
    this.LShoulder.func_78792_a(this.LArm);
    this.LArm.func_78792_a(this.LHand);
    this.Head.func_78792_a(this.LEye);
    this.Body.func_78792_a(this.RShoulder);
    this.Head.func_78792_a(this.JudgmentEyeBlue);
    this.Body.func_78792_a(this.LShoulder);
    this.LLeg.func_78792_a(this.LTrunk);
    this.RLeg.func_78792_a(this.RTrunk);
    this.RArm.func_78792_a(this.RHand);
    this.RLeg.func_78792_a(this.RSlipper);
    this.RShoulder.func_78792_a(this.RArm);
    this.Head.func_78792_a(this.JudgmentEyeYellow);
  }
  
  public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    this.LLeg.func_78785_a(f5);
    this.RLeg.func_78785_a(f5);
    this.LLeg.func_78785_a(f5);
    this.Trunks.func_78785_a(f5);
  }
  
  public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
    modelRenderer.field_78795_f = x;
    modelRenderer.field_78796_g = y;
    modelRenderer.field_78808_h = z;
  }
  
  public void func_78087_a(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    this.Head.field_78796_g = netHeadYaw * 0.017453292F;
    this.Head.field_78795_f = headPitch * 0.017453292F;
    setRotateAngle(this.LPocket, 0.0F, 0.0F, -0.22759093F);
    setRotateAngle(this.RPocket, 0.0F, 0.0F, 0.22759093F);
    setRotateAngle(this.LTrunk, -0.0F, 0.0F, -0.13665928F);
    setRotateAngle(this.RTrunk, 0.0F, 0.0F, 0.091106184F);
    this.Neck.func_78793_a(-0.05F + MathHelper.func_76126_a(ageInTicks * 0.6F - 1.8F) * 0.1F, -4.25F + MathHelper.func_76126_a(ageInTicks * 0.6F) * 0.05F, 0.0F);
    this.Trunks.func_78793_a(-0.1F + MathHelper.func_76126_a(ageInTicks * 0.3F) * 0.2F, 15.5F + MathHelper.func_76126_a(ageInTicks * 0.6F) * 0.05F, 0.0F);
    this.RLeg.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F) * 1.0F * limbSwingAmount;
    this.LLeg.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F + 3.1415927F) * 1.0F * limbSwingAmount;
    EntitySans sans = (EntitySans)entityIn;
    switch (sans.getEyeType()) {
      case 1:
        this.JudgmentEyeBlue.func_78793_a(2.0F, -4.3F, -3.48F);
        this.JudgmentEyeYellow.func_78793_a(2.0F, -4.3F, -3.47F);
        this.REye.func_78793_a(-1.8F, -4.3F, -3.47F);
        this.LEye.func_78793_a(1.8F, -4.3F, -3.47F);
        break;
      case 2:
        this.JudgmentEyeBlue.func_78793_a(2.0F, -4.3F, -3.55F - MathHelper.func_76126_a(ageInTicks * 1.5F) * 0.01F);
        this.JudgmentEyeYellow.func_78793_a(2.0F, -4.3F, -3.54F + MathHelper.func_76126_a(ageInTicks * 1.5F) * 0.01F);
        this.REye.func_78793_a(-1.8F, -4.3F, -3.47F);
        this.LEye.func_78793_a(1.8F, -4.3F, -3.47F);
        break;
      default:
        this.JudgmentEyeBlue.func_78793_a(2.0F, -4.3F, -3.48F);
        this.JudgmentEyeYellow.func_78793_a(2.0F, -4.3F, -3.47F);
        this.REye.func_78793_a(-1.8F, -4.3F, -3.55F);
        this.LEye.func_78793_a(1.8F, -4.3F, -3.55F);
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
  
  public void func_78086_a(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
    super.func_78086_a(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
  }
}
