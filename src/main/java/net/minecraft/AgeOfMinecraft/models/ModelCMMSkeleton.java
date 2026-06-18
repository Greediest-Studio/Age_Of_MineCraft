package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class ModelCMMSkeleton extends ModelSkeleton implements ICappedModel {
  public ModelRenderer Abdoman;
  
  public ModelRenderer RArm;
  
  public ModelRenderer LArm;
  
  public ModelRenderer RLeg;
  
  public ModelRenderer LLeg;
  
  public ModelRenderer Body;
  
  public ModelRenderer Skirt;
  
  public ModelRenderer Neck;
  
  public ModelRenderer BustFront;
  
  public ModelRenderer Head;
  
  public ModelRenderer Hair;
  
  public ModelRenderer Hat1;
  
  public ModelRenderer Hat2;
  
  public ModelRenderer BHair1;
  
  public ModelRenderer LPointyTair1;
  
  public ModelRenderer RPointyTair1;
  
  public ModelRenderer BHair2;
  
  public ModelRenderer BHair3;
  
  public ModelRenderer BHair4;
  
  public ModelRenderer LPointyTair2;
  
  public ModelRenderer LPointyTair3;
  
  public ModelRenderer RPointyTair2;
  
  public ModelRenderer RPointyTair3;
  
  public ModelRenderer BustTop;
  
  public ModelRenderer BustBottom;
  
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
  
  public ModelCMMSkeleton() {
    this.bipedCape = new ModelRenderer((ModelBase)this, 0, 0);
    this.bipedCape.setTextureSize(64, 32);
    this.bipedCape.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, 0.0F);
    this.textureWidth = 64;
    this.textureHeight = 64;
    this.RArm = new ModelRenderer((ModelBase)this, 36, 0);
    this.RArm.setRotationPoint(-3.5F, 2.0F, 0.0F);
    this.RArm.addBox(-2.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
    this.LArm = new ModelRenderer((ModelBase)this, 36, 0);
    this.LArm.mirror = true;
    this.LArm.setRotationPoint(3.5F, 2.0F, 0.0F);
    this.LArm.addBox(0.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
    this.Hair = new ModelRenderer((ModelBase)this, 0, 12);
    this.Hair.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.Hair.addBox(-4.0F, -6.0F, -3.5F, 8, 7, 7, 0.0F);
    this.BHair1 = new ModelRenderer((ModelBase)this, 0, 36);
    this.BHair1.setRotationPoint(0.0F, 0.0F, 2.5F);
    this.BHair1.addBox(-4.0F, 0.0F, 0.0F, 8, 3, 1, 0.0F);
    this.Head = new ModelRenderer((ModelBase)this, 0, 0);
    this.Head.setRotationPoint(0.0F, -1.0F, 0.0F);
    this.Head.addBox(-3.5F, -6.0F, -3.0F, 7, 6, 6, 0.0F);
    this.LPointyTair1 = new ModelRenderer((ModelBase)this, 24, 36);
    this.LPointyTair1.mirror = true;
    this.LPointyTair1.setRotationPoint(2.0F, 0.0F, 2.0F);
    this.LPointyTair1.addBox(-1.0F, 0.0F, -0.5F, 2, 3, 1, 0.0F);
    this.RLeg = new ModelRenderer((ModelBase)this, 52, 0);
    this.RLeg.setRotationPoint(-2.0F, 14.0F, 0.0F);
    this.RLeg.addBox(-1.5F, -1.0F, -1.5F, 3, 11, 3, 0.0F);
    this.BHair2 = new ModelRenderer((ModelBase)this, 0, 39);
    this.BHair2.setRotationPoint(0.0F, 3.0F, 0.0F);
    this.BHair2.addBox(-4.0F, 0.0F, 0.0F, 8, 3, 1, 0.0F);
    this.BHair3 = new ModelRenderer((ModelBase)this, 0, 41);
    this.BHair3.setRotationPoint(0.0F, 3.0F, 0.0F);
    this.BHair3.addBox(-4.0F, 0.0F, 0.0F, 8, 3, 1, 0.0F);
    this.LPointyTair3 = new ModelRenderer((ModelBase)this, 24, 42);
    this.LPointyTair3.mirror = true;
    this.LPointyTair3.setRotationPoint(0.0F, 3.0F, 0.0F);
    this.LPointyTair3.addBox(-1.0F, 0.0F, -0.5F, 2, 4, 1, 0.0F);
    this.Hat2 = new ModelRenderer((ModelBase)this, 38, 37);
    this.Hat2.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.Hat2.addBox(-3.5F, -10.0F, -3.0F, 7, 1, 6, 0.0F);
    this.RPointyTair3 = new ModelRenderer((ModelBase)this, 24, 42);
    this.RPointyTair3.setRotationPoint(0.0F, 3.0F, 0.0F);
    this.RPointyTair3.addBox(-1.0F, 0.0F, -0.5F, 2, 4, 1, 0.0F);
    this.LLeg = new ModelRenderer((ModelBase)this, 52, 0);
    this.LLeg.mirror = true;
    this.LLeg.setRotationPoint(2.0F, 14.0F, 0.0F);
    this.LLeg.addBox(-1.5F, -1.0F, -1.5F, 3, 11, 3, 0.0F);
    this.Body = new ModelRenderer((ModelBase)this, 0, 47);
    this.Body.setRotationPoint(0.0F, -6.0F, 0.0F);
    this.Body.addBox(-3.5F, -5.0F, -1.5F, 7, 5, 3, 0.0F);
    this.RPointyTair2 = new ModelRenderer((ModelBase)this, 24, 39);
    this.RPointyTair2.setRotationPoint(0.0F, 3.0F, 0.0F);
    this.RPointyTair2.addBox(-1.0F, 0.0F, -0.5F, 2, 3, 1, 0.0F);
    this.RPointyTair1 = new ModelRenderer((ModelBase)this, 24, 36);
    this.RPointyTair1.setRotationPoint(-2.0F, 0.0F, 2.0F);
    this.RPointyTair1.addBox(-1.0F, 0.0F, -0.5F, 2, 3, 1, 0.0F);
    this.Skirt = new ModelRenderer((ModelBase)this, 42, 30);
    this.Skirt.setRotationPoint(0.0F, -2.0F, 0.0F);
    this.Skirt.addBox(-3.5F, 0.0F, -2.0F, 7, 3, 4, 0.0F);
    this.Hat1 = new ModelRenderer((ModelBase)this, 0, 26);
    this.Hat1.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.Hat1.addBox(-4.0F, -9.0F, -3.5F, 8, 3, 7, 0.0F);
    this.Abdoman = new ModelRenderer((ModelBase)this, 0, 55);
    this.Abdoman.setRotationPoint(0.0F, 12.0F, 0.0F);
    this.Abdoman.addBox(-3.0F, -6.0F, -1.5F, 6, 6, 3, 0.0F);
    this.LPointyTair2 = new ModelRenderer((ModelBase)this, 24, 39);
    this.LPointyTair2.mirror = true;
    this.LPointyTair2.setRotationPoint(0.0F, 3.0F, 0.0F);
    this.LPointyTair2.addBox(-1.0F, 0.0F, -0.5F, 2, 3, 1, 0.0F);
    this.Neck = new ModelRenderer((ModelBase)this, 52, 47);
    this.Neck.setRotationPoint(0.0F, -5.0F, 0.0F);
    this.Neck.addBox(-1.5F, -2.0F, -1.5F, 3, 2, 3, 0.0F);
    this.BHair4 = new ModelRenderer((ModelBase)this, 0, 43);
    this.BHair4.setRotationPoint(0.0F, 3.0F, 0.0F);
    this.BHair4.addBox(-4.0F, 0.0F, 0.0F, 8, 3, 1, 0.0F);
    this.BustFront = new ModelRenderer((ModelBase)this, 48, 56);
    this.BustFront.setRotationPoint(0.0F, -0.8F, -1.0F);
    this.BustFront.addBox(-3.0F, -1.0F, -2.0F, 6, 2, 2, 0.0F);
    this.BustTop = new ModelRenderer((ModelBase)this, 48, 52);
    this.BustTop.setRotationPoint(0.0F, -2.3F, -0.6F);
    this.BustTop.addBox(-3.0F, 0.0F, 0.0F, 6, 2, 2, 0.0F);
    setRotateAngle(this.BustTop, -0.7853982F, 0.0F, 0.0F);
    this.BustBottom = new ModelRenderer((ModelBase)this, 48, 60);
    this.BustBottom.setRotationPoint(0.0F, -0.7F, -1.0F);
    this.BustBottom.addBox(-3.0F, 0.0F, 0.0F, 6, 2, 2, 0.0F);
    setRotateAngle(this.BustBottom, -0.43633232F, 0.0F, 0.0F);
    this.Body.addChild(this.BustFront);
    this.Head.addChild(this.Hair);
    this.BustFront.addChild(this.BustTop);
    this.Head.addChild(this.BHair1);
    this.Neck.addChild(this.Head);
    this.BustFront.addChild(this.BustBottom);
    this.Head.addChild(this.LPointyTair1);
    this.BHair1.addChild(this.BHair2);
    this.BHair2.addChild(this.BHair3);
    this.LPointyTair2.addChild(this.LPointyTair3);
    this.Head.addChild(this.Hat2);
    this.RPointyTair2.addChild(this.RPointyTair3);
    this.Abdoman.addChild(this.Body);
    this.RPointyTair1.addChild(this.RPointyTair2);
    this.Head.addChild(this.RPointyTair1);
    this.Abdoman.addChild(this.Skirt);
    this.Head.addChild(this.Hat1);
    this.LPointyTair1.addChild(this.LPointyTair2);
    this.Body.addChild(this.Neck);
    this.BHair3.addChild(this.BHair4);
    this.bipedHead.isHidden = true;
    this.bipedBody.isHidden = true;
    this.bipedRightArm.isHidden = true;
    this.bipedLeftArm.isHidden = true;
    this.bipedRightLeg.isHidden = true;
    this.bipedLeftLeg.isHidden = true;
    this.bipedHeadwear.isHidden = true;
  }
  
  public ModelCMMSkeleton(float modelSize, boolean p_i46303_2_) {
    super(modelSize, p_i46303_2_);
    this.bipedRightArm = new ModelRenderer((ModelBase)this, 40, 16);
    this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
    this.bipedRightArm.setRotationPoint(-4.0F, 2.0F, 0.0F);
    this.bipedLeftArm = new ModelRenderer((ModelBase)this, 40, 16);
    this.bipedLeftArm.mirror = true;
    this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
    this.bipedLeftArm.setRotationPoint(4.0F, 2.0F, 0.0F);
    this.RArm = new ModelRenderer((ModelBase)this, 36, 0);
    this.RArm.setRotationPoint(-3.5F, 2.0F, 0.0F);
    this.RArm.addBox(-2.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
    this.BustFront = new ModelRenderer((ModelBase)this, 48, 56);
    this.BustFront.setRotationPoint(0.0F, -2.0F, 0.0F);
    this.BustFront.addBox(-3.0F, -1.0F, -3.0F, 6, 2, 2, 0.0F);
    this.LArm = new ModelRenderer((ModelBase)this, 36, 0);
    this.LArm.mirror = true;
    this.LArm.setRotationPoint(3.5F, 2.0F, 0.0F);
    this.LArm.addBox(0.0F, -1.0F, -1.0F, 2, 12, 2, 0.0F);
    this.Hair = new ModelRenderer((ModelBase)this, 0, 12);
    this.Hair.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.Hair.addBox(-4.0F, -6.0F, -3.5F, 8, 7, 7, 0.0F);
    this.BustTop = new ModelRenderer((ModelBase)this, 48, 52);
    this.BustTop.setRotationPoint(0.0F, -2.25F, -1.6F);
    this.BustTop.addBox(-3.0F, 0.0F, 0.0F, 6, 2, 2, 0.0F);
    setRotateAngle(this.BustTop, -0.7853982F, 0.0F, 0.0F);
    this.BHair1 = new ModelRenderer((ModelBase)this, 0, 36);
    this.BHair1.setRotationPoint(0.0F, 0.0F, 2.5F);
    this.BHair1.addBox(-4.0F, 0.0F, 0.0F, 8, 3, 1, 0.0F);
    this.Head = new ModelRenderer((ModelBase)this, 0, 0);
    this.Head.setRotationPoint(0.0F, -1.0F, 0.0F);
    this.Head.addBox(-3.5F, -6.0F, -3.0F, 7, 6, 6, 0.0F);
    this.BustBottom = new ModelRenderer((ModelBase)this, 48, 60);
    this.BustBottom.setRotationPoint(0.0F, -0.75F, -2.0F);
    this.BustBottom.addBox(-3.0F, 0.0F, 0.0F, 6, 2, 2, 0.0F);
    setRotateAngle(this.BustBottom, -0.43633232F, 0.0F, 0.0F);
    this.LPointyTair1 = new ModelRenderer((ModelBase)this, 24, 36);
    this.LPointyTair1.mirror = true;
    this.LPointyTair1.setRotationPoint(2.0F, 0.0F, 2.0F);
    this.LPointyTair1.addBox(-1.0F, 0.0F, -0.5F, 2, 3, 1, 0.0F);
    this.RLeg = new ModelRenderer((ModelBase)this, 52, 0);
    this.RLeg.setRotationPoint(-2.0F, 14.0F, 0.0F);
    this.RLeg.addBox(-1.5F, -1.0F, -1.5F, 3, 11, 3, 0.0F);
    this.BHair2 = new ModelRenderer((ModelBase)this, 0, 39);
    this.BHair2.setRotationPoint(0.0F, 3.0F, 0.0F);
    this.BHair2.addBox(-4.0F, 0.0F, 0.0F, 8, 3, 1, 0.0F);
    this.BHair3 = new ModelRenderer((ModelBase)this, 0, 41);
    this.BHair3.setRotationPoint(0.0F, 3.0F, 0.0F);
    this.BHair3.addBox(-4.0F, 0.0F, 0.0F, 8, 3, 1, 0.0F);
    this.LPointyTair3 = new ModelRenderer((ModelBase)this, 24, 42);
    this.LPointyTair3.mirror = true;
    this.LPointyTair3.setRotationPoint(0.0F, 3.0F, 0.0F);
    this.LPointyTair3.addBox(-1.0F, 0.0F, -0.5F, 2, 4, 1, 0.0F);
    this.Hat2 = new ModelRenderer((ModelBase)this, 38, 37);
    this.Hat2.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.Hat2.addBox(-3.5F, -10.0F, -3.0F, 7, 1, 6, 0.0F);
    this.RPointyTair3 = new ModelRenderer((ModelBase)this, 24, 42);
    this.RPointyTair3.setRotationPoint(0.0F, 3.0F, 0.0F);
    this.RPointyTair3.addBox(-1.0F, 0.0F, -0.5F, 2, 4, 1, 0.0F);
    this.LLeg = new ModelRenderer((ModelBase)this, 52, 0);
    this.LLeg.mirror = true;
    this.LLeg.setRotationPoint(2.0F, 14.0F, 0.0F);
    this.LLeg.addBox(-1.5F, -1.0F, -1.5F, 3, 11, 3, 0.0F);
    this.Body = new ModelRenderer((ModelBase)this, 0, 47);
    this.Body.setRotationPoint(0.0F, -6.0F, 0.0F);
    this.Body.addBox(-3.5F, -5.0F, -1.5F, 7, 5, 3, 0.0F);
    this.RPointyTair2 = new ModelRenderer((ModelBase)this, 24, 39);
    this.RPointyTair2.setRotationPoint(0.0F, 3.0F, 0.0F);
    this.RPointyTair2.addBox(-1.0F, 0.0F, -0.5F, 2, 3, 1, 0.0F);
    this.RPointyTair1 = new ModelRenderer((ModelBase)this, 24, 36);
    this.RPointyTair1.setRotationPoint(-2.0F, 0.0F, 2.0F);
    this.RPointyTair1.addBox(-1.0F, 0.0F, -0.5F, 2, 3, 1, 0.0F);
    this.Skirt = new ModelRenderer((ModelBase)this, 42, 30);
    this.Skirt.setRotationPoint(0.0F, -2.0F, 0.0F);
    this.Skirt.addBox(-3.5F, 0.0F, -2.0F, 7, 3, 4, 0.0F);
    this.Hat1 = new ModelRenderer((ModelBase)this, 0, 26);
    this.Hat1.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.Hat1.addBox(-4.0F, -9.0F, -3.5F, 8, 3, 7, 0.0F);
    this.Abdoman = new ModelRenderer((ModelBase)this, 0, 55);
    this.Abdoman.setRotationPoint(0.0F, 12.0F, 0.0F);
    this.Abdoman.addBox(-3.0F, -6.0F, -1.5F, 6, 6, 3, 0.0F);
    this.LPointyTair2 = new ModelRenderer((ModelBase)this, 24, 39);
    this.LPointyTair2.mirror = true;
    this.LPointyTair2.setRotationPoint(0.0F, 3.0F, 0.0F);
    this.LPointyTair2.addBox(-1.0F, 0.0F, -0.5F, 2, 3, 1, 0.0F);
    this.Neck = new ModelRenderer((ModelBase)this, 52, 47);
    this.Neck.setRotationPoint(0.0F, -5.0F, 0.0F);
    this.Neck.addBox(-1.5F, -2.0F, -1.5F, 3, 2, 3, 0.0F);
    this.BHair4 = new ModelRenderer((ModelBase)this, 0, 43);
    this.BHair4.setRotationPoint(0.0F, 3.0F, 0.0F);
    this.BHair4.addBox(-4.0F, 0.0F, 0.0F, 8, 3, 1, 0.0F);
    this.Body.addChild(this.BustFront);
    this.Head.addChild(this.Hair);
    this.BustFront.addChild(this.BustTop);
    this.Head.addChild(this.BHair1);
    this.Neck.addChild(this.Head);
    this.BustFront.addChild(this.BustBottom);
    this.Head.addChild(this.LPointyTair1);
    this.BHair1.addChild(this.BHair2);
    this.BHair2.addChild(this.BHair3);
    this.LPointyTair2.addChild(this.LPointyTair3);
    this.Head.addChild(this.Hat2);
    this.RPointyTair2.addChild(this.RPointyTair3);
    this.Abdoman.addChild(this.Body);
    this.RPointyTair1.addChild(this.RPointyTair2);
    this.Head.addChild(this.RPointyTair1);
    this.Abdoman.addChild(this.Skirt);
    this.Head.addChild(this.Hat1);
    this.LPointyTair1.addChild(this.LPointyTair2);
    this.Body.addChild(this.Neck);
    this.BHair3.addChild(this.BHair4);
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
      this.bipedHead.render(scale);
      this.bipedBody.render(scale);
      this.bipedRightArm.render(scale);
      this.bipedLeftArm.render(scale);
      this.bipedRightLeg.render(scale);
      this.bipedLeftLeg.render(scale);
      this.bipedHeadwear.render(scale);
    } else {
      this.RArm.render(scale);
      this.LArm.render(scale);
      this.RLeg.render(scale);
      this.LLeg.render(scale);
      this.Abdoman.render(scale);
      this.bipedHead.render(scale);
      this.bipedBody.render(scale);
      this.bipedRightArm.render(scale);
      this.bipedLeftArm.render(scale);
      this.bipedRightLeg.render(scale);
      this.bipedLeftLeg.render(scale);
      this.bipedHeadwear.render(scale);
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
    super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    float swing = MathHelper.sin(this.swingProgress * 3.1415927F);
    if (this.isChild) {
      this.BustFront.isHidden = true;
      this.BHair1.isHidden = true;
      this.LPointyTair1.isHidden = true;
      this.RPointyTair1.isHidden = true;
    } else {
      this.BustFront.isHidden = false;
      this.BHair1.isHidden = false;
      this.LPointyTair1.isHidden = false;
      this.RPointyTair1.isHidden = false;
    } 
    this.Head.rotateAngleY = netHeadYaw * 0.017453292F;
    this.Head.rotateAngleX = headPitch * 0.017453292F;
    if (entity.isSneaking()) {
      this.isSneak = true;
    } else {
      this.isSneak = false;
    } 
    this.BustFront.setRotationPoint(0.0F, -0.75F, -1.0F);
    this.LLeg.setRotationPoint(2.0F, 14.0F, 0.0F);
    this.RLeg.setRotationPoint(-2.0F, 14.0F, 0.0F);
    this.Abdoman.setRotationPoint(0.0F, 12.0F, 0.0F);
    this.RArm.setRotationPoint(-3.5F, 3.0F, 0.0F);
    this.LArm.setRotationPoint(3.5F, 3.0F, 0.0F);
    this.LPointyTair1.rotateAngleZ = -0.125F;
    this.RPointyTair1.rotateAngleZ = 0.125F;
    this.LPointyTair2.rotateAngleZ = 0.0F;
    this.RPointyTair2.rotateAngleZ = 0.0F;
    this.LPointyTair3.rotateAngleZ = 0.0F;
    this.RPointyTair3.rotateAngleZ = 0.0F;
    this.LPointyTair1.rotateAngleX = 0.25F + entity.limbSwingAmount * 0.5F - headPitch * 0.0045F;
    this.RPointyTair1.rotateAngleX = 0.25F + entity.limbSwingAmount * 0.5F - headPitch * 0.0045F;
    this.LPointyTair2.rotateAngleX = entity.limbSwingAmount * 0.5F - headPitch * 0.0045F;
    this.RPointyTair2.rotateAngleX = entity.limbSwingAmount * 0.5F - headPitch * 0.0045F;
    this.LPointyTair3.rotateAngleX = entity.limbSwingAmount * 0.5F - headPitch * 0.0045F;
    this.RPointyTair3.rotateAngleX = entity.limbSwingAmount * 0.5F - headPitch * 0.0045F;
    this.BHair1.rotateAngleX = entity.limbSwingAmount * 0.5F - headPitch * 0.0045F;
    this.BHair2.rotateAngleX = entity.limbSwingAmount * 0.25F - headPitch * 0.0045F;
    this.BHair3.rotateAngleX = entity.limbSwingAmount * 0.25F - headPitch * 0.0045F;
    this.BHair4.rotateAngleX = entity.limbSwingAmount * 0.25F - headPitch * 0.0045F;
    this.BHair1.rotateAngleZ = 0.0F;
    this.BHair2.rotateAngleZ = 0.0F;
    this.BHair3.rotateAngleZ = 0.0F;
    this.BHair4.rotateAngleZ = 0.0F;
    this.Body.rotateAngleX = 0.0F;
    this.Abdoman.rotateAngleX = 0.0F;
    this.Body.rotateAngleZ = 0.0F;
    this.Abdoman.rotateAngleZ = 0.0F;
    this.Neck.rotateAngleZ = 0.0F;
    this.Neck.rotateAngleY = 0.0F;
    this.Neck.rotateAngleX = 0.0F;
    this.Head.rotateAngleZ = 0.0F;
    this.Skirt.rotateAngleX = 0.0F;
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
    this.BustFront.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 2.0F) * 0.5F * limbSwingAmount * 0.5F;
    this.RLeg.rotateAngleY = 0.0F;
    this.LLeg.rotateAngleY = 0.0F;
    this.RLeg.rotateAngleZ = 0.0F;
    this.LLeg.rotateAngleZ = 0.0F;
    this.Neck.rotateAngleZ += MathHelper.cos(limbSwing * 0.6662F) * 0.6F * limbSwingAmount * 0.5F;
    this.Body.rotateAngleZ -= MathHelper.cos(limbSwing * 0.6662F) * 0.8F * limbSwingAmount * 0.5F;
    this.Abdoman.rotateAngleZ += MathHelper.cos(limbSwing * 0.6662F) * 0.4F * limbSwingAmount * 0.5F;
    this.Abdoman.rotationPointX = 0.0F;
    this.Abdoman.rotationPointZ = MathHelper.cos(limbSwing * 0.6662F) * 0.5F * limbSwingAmount * 0.5F;
    this.Abdoman.rotationPointY = 12.0F + MathHelper.cos(limbSwing * 0.6662F) * 0.1F * limbSwingAmount * 0.5F;
    this.RArm.rotationPointX += MathHelper.cos(limbSwing * 0.6662F) * 1.5F * limbSwingAmount * 0.5F;
    this.LArm.rotationPointX += MathHelper.cos(limbSwing * 0.6662F) * 1.5F * limbSwingAmount * 0.5F;
    this.BHair1.rotateAngleX += MathHelper.cos(limbSwing * 0.9993F) * 0.5F * limbSwingAmount * 0.5F;
    this.BHair1.rotateAngleZ += MathHelper.cos(limbSwing * 0.6662F - 1.0F) * 0.5F * limbSwingAmount * 0.5F;
    this.BHair2.rotateAngleZ += MathHelper.cos(limbSwing * 0.6662F - 2.0F) * 0.5F * limbSwingAmount * 0.5F;
    this.BHair3.rotateAngleZ += MathHelper.cos(limbSwing * 0.6662F - 3.0F) * 0.5F * limbSwingAmount * 0.5F;
    this.BHair4.rotateAngleZ += MathHelper.cos(limbSwing * 0.6662F - 4.0F) * 0.5F * limbSwingAmount * 0.5F;
    this.LPointyTair1.rotateAngleX += MathHelper.cos(limbSwing * 0.9993F) * 0.5F * limbSwingAmount * 0.5F;
    this.RPointyTair1.rotateAngleX += MathHelper.cos(limbSwing * 0.9993F) * 0.5F * limbSwingAmount * 0.5F;
    this.LPointyTair1.rotateAngleZ += MathHelper.cos(limbSwing * 0.6662F - 1.0F) * 0.5F * limbSwingAmount * 0.5F;
    this.LPointyTair2.rotateAngleZ += MathHelper.cos(limbSwing * 0.6662F - 2.0F) * 0.5F * limbSwingAmount * 0.5F;
    this.LPointyTair3.rotateAngleZ += MathHelper.cos(limbSwing * 0.6662F - 3.0F) * 0.5F * limbSwingAmount * 0.5F;
    this.RPointyTair1.rotateAngleZ += MathHelper.cos(limbSwing * 0.6662F - 1.0F) * 0.5F * limbSwingAmount * 0.5F;
    this.RPointyTair2.rotateAngleZ += MathHelper.cos(limbSwing * 0.6662F - 2.0F) * 0.5F * limbSwingAmount * 0.5F;
    this.RPointyTair3.rotateAngleZ += MathHelper.cos(limbSwing * 0.6662F - 3.0F) * 0.5F * limbSwingAmount * 0.5F;
    if (!entity.onGround && !entity.isBeingRidden() && !this.isRiding) {
      this.RArm.rotateAngleZ += 0.6F;
      this.LArm.rotateAngleZ -= 0.6F;
      this.RLeg.rotateAngleZ = 0.5F;
      this.LLeg.rotateAngleZ = -0.5F;
      this.RArm.rotateAngleX -= 2.0F;
      this.LArm.rotateAngleX -= 2.0F;
      this.BHair1.rotateAngleX = (float)(this.BHair1.rotateAngleX + (MathHelper.cos(ageInTicks * 0.3F - 0.5F) * 0.1F) - ((entity.motionY > 4.0D) ? 1.0D : ((entity.motionY < -4.0D) ? 0.0D : (entity.motionY / 4.0D))));
      this.BHair2.rotateAngleX = (float)(this.BHair2.rotateAngleX + (MathHelper.cos(ageInTicks * 0.3F - 1.0F) * 0.1F) - ((entity.motionY > 4.0D) ? 1.0D : ((entity.motionY < -4.0D) ? 0.0D : (entity.motionY / 4.0D))));
      this.BHair3.rotateAngleX = (float)(this.BHair3.rotateAngleX + (MathHelper.cos(ageInTicks * 0.3F - 1.5F) * 0.1F) - ((entity.motionY > 4.0D) ? 1.0D : ((entity.motionY < -4.0D) ? 0.0D : (entity.motionY / 4.0D))));
      this.BHair4.rotateAngleX = (float)(this.BHair4.rotateAngleX + (MathHelper.cos(ageInTicks * 0.3F - 2.0F) * 0.1F) - ((entity.motionY > 4.0D) ? 1.0D : ((entity.motionY < -4.0D) ? 0.0D : (entity.motionY / 4.0D))));
      this.RLeg.rotateAngleX = (float)(this.RLeg.rotateAngleX + (MathHelper.cos(ageInTicks * 0.5F) * 0.25F) - ((entity.motionY > 2.0D) ? 1.0D : ((entity.motionY < -2.0D) ? -1.0D : (entity.motionY / 2.0D))));
      this.LLeg.rotateAngleX = (float)(this.LLeg.rotateAngleX + (MathHelper.cos(ageInTicks * 0.5F + 3.1415927F) * 0.25F) - ((entity.motionY > 2.0D) ? 1.0D : ((entity.motionY < -2.0D) ? -1.0D : (entity.motionY / 2.0D))));
    } 
    this.bipedBody.rotationPointX = 0.0F;
    this.bipedBody.rotationPointY = 0.0F;
    this.bipedBody.rotationPointZ = 0.0F;
    this.bipedHead.rotationPointX = 0.0F;
    this.bipedHead.rotationPointY = 0.0F;
    this.bipedHead.rotationPointZ = 0.0F;
    if (this.isSneak && !entity.isSitResting() && entity.onGround && entity.getJukeboxToDanceTo() == null) {
      this.BHair1.rotateAngleX += 0.75F;
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
    EntityTameBase entityskeleton = (EntityTameBase)entityIn;
    ItemStack itemstack1 = entityskeleton.getHeldItem(EnumHand.MAIN_HAND);
    ItemStack itemstack2 = entityskeleton.getHeldItem(EnumHand.OFF_HAND);
    if ((itemstack1.isEmpty() || (!itemstack1.isEmpty() && itemstack1.getItem() != Items.BOW)) && entityskeleton.isArmsRaised())
      if (entityskeleton.getPrimaryHand() == EnumHandSide.RIGHT) {
        this.RArm.rotateAngleX = -1.8849558F + MathHelper.cos(ageInTicks * 0.1F) * 0.15F;
        this.RArm.rotateAngleX += swing * 2.2F - swing * 0.4F;
        if (itemstack2 == null || (itemstack2 != null && itemstack2.getItem() != Items.SHIELD)) {
          this.LArm.rotateAngleX = -0.0F + MathHelper.cos(ageInTicks * 0.5F) * 0.5F;
          this.LArm.rotateAngleZ -= 1.8849558F + MathHelper.cos(ageInTicks * 0.1F) * 0.25F;
          this.LArm.rotateAngleX += swing * 1.2F - swing * 0.4F;
        } 
      } else {
        this.LArm.rotateAngleX = -1.8849558F + MathHelper.cos(ageInTicks * 0.1F) * 0.15F;
        this.LArm.rotateAngleX += swing * 2.2F - swing * 0.4F;
        if (itemstack2 == null || (itemstack2 != null && itemstack2.getItem() != Items.SHIELD)) {
          this.RArm.rotateAngleX = -0.0F + MathHelper.cos(ageInTicks * 0.5F) * 0.5F;
          this.RArm.rotateAngleZ -= -1.8849558F + MathHelper.cos(ageInTicks * 0.1F) * 0.25F;
          this.RArm.rotateAngleX += swing * 1.2F - swing * 0.4F;
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
      this.Head.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.2F) * 0.05F;
      this.Neck.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.2F) * 0.05F;
      this.BHair1.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.2F - 0.5F) * 0.1F;
      this.BHair2.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.2F - 1.0F) * 0.1F;
      this.BHair3.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.2F - 1.5F) * 0.1F;
      this.BHair4.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.2F - 2.0F) * 0.1F;
      this.LPointyTair1.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.2F - 0.5F) * 0.1F;
      this.LPointyTair2.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.2F - 1.0F) * 0.1F;
      this.LPointyTair3.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.2F - 1.5F) * 0.1F;
      this.RPointyTair1.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.2F - 0.5F) * 0.1F;
      this.RPointyTair2.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.2F - 1.0F) * 0.1F;
      this.RPointyTair3.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.2F - 1.5F) * 0.1F;
      this.RArm.rotateAngleX = -0.8975979F;
      this.LArm.rotateAngleX = -0.8975979F;
      this.RArm.rotateAngleY = -0.5F;
      this.LArm.rotateAngleY = 0.5F;
      this.RLeg.rotateAngleX = -1.4F;
      this.RLeg.rotateAngleY = -0.3F;
      this.RLeg.rotateAngleZ = 0.025F;
      this.LLeg.rotateAngleX = -1.6F + MathHelper.cos(ageInTicks * 0.5F) * 0.05F;
      this.LLeg.rotateAngleY = 0.25F;
      this.LLeg.rotateAngleZ = -0.025F;
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
    this.RArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.1F;
    this.LArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.1F;
    this.RArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
    this.LArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
    if (entity.getJukeboxToDanceTo() != null) {
      this.RPointyTair1.rotateAngleZ += MathHelper.cos(ageInTicks * 0.5F - 3.5F) * 0.5F;
      this.RPointyTair2.rotateAngleZ += MathHelper.cos(ageInTicks * 0.5F - 4.0F) * 0.25F;
      this.RPointyTair3.rotateAngleZ += MathHelper.cos(ageInTicks * 0.5F - 4.5F) * 0.25F;
      this.LPointyTair1.rotateAngleZ += MathHelper.cos(ageInTicks * 0.5F - 3.5F) * 0.5F;
      this.LPointyTair2.rotateAngleZ += MathHelper.cos(ageInTicks * 0.5F - 4.0F) * 0.25F;
      this.LPointyTair3.rotateAngleZ += MathHelper.cos(ageInTicks * 0.5F - 4.5F) * 0.25F;
      this.BHair1.rotateAngleZ += MathHelper.cos(ageInTicks * 0.5F - 3.5F) * 0.5F;
      this.BHair2.rotateAngleZ += MathHelper.cos(ageInTicks * 0.5F - 4.0F) * 0.25F;
      this.BHair3.rotateAngleZ += MathHelper.cos(ageInTicks * 0.5F - 4.5F) * 0.25F;
      this.BHair4.rotateAngleZ += MathHelper.cos(ageInTicks * 0.5F - 5.0F) * 0.25F;
      this.Neck.rotateAngleZ = MathHelper.cos(ageInTicks * 0.5F - 3.1415927F) * 0.5F;
      this.Head.rotateAngleX += MathHelper.cos(ageInTicks * 1.0F) * 0.5F;
      this.RPointyTair1.rotateAngleX += 1.0F + MathHelper.cos(ageInTicks * 1.0F - 2.0F) * 0.25F;
      this.RPointyTair2.rotateAngleX += MathHelper.cos(ageInTicks * 1.0F - 2.5F) * 0.25F;
      this.RPointyTair3.rotateAngleX += MathHelper.cos(ageInTicks * 1.0F - 3.0F) * 0.25F;
      this.LPointyTair1.rotateAngleX += 1.0F + MathHelper.cos(ageInTicks * 1.0F - 2.0F) * 0.25F;
      this.LPointyTair2.rotateAngleX += MathHelper.cos(ageInTicks * 1.0F - 2.5F) * 0.25F;
      this.LPointyTair3.rotateAngleX += MathHelper.cos(ageInTicks * 1.0F - 3.0F) * 0.25F;
      this.BHair1.rotateAngleX += 1.0F + MathHelper.cos(ageInTicks * 1.0F - 2.0F) * 0.25F;
      this.BHair2.rotateAngleX += MathHelper.cos(ageInTicks * 1.0F - 2.5F) * 0.25F;
      this.BHair3.rotateAngleX += MathHelper.cos(ageInTicks * 1.0F - 3.0F) * 0.25F;
      this.BHair4.rotateAngleX += MathHelper.cos(ageInTicks * 1.0F - 2.5F) * 0.25F;
      this.Body.rotateAngleZ = MathHelper.cos(ageInTicks * 0.5F - 3.1415927F) * 0.5F;
      this.Abdoman.rotateAngleZ = MathHelper.cos(ageInTicks * 0.5F) * 0.5F;
      this.Abdoman.rotationPointX = MathHelper.cos(ageInTicks * 0.5F - 3.1415927F) * 1.0F;
      this.Abdoman.rotationPointY = 13.0F + MathHelper.cos(ageInTicks * 1.0F) * 1.0F;
      this.RArm.rotationPointX = -3.5F + MathHelper.cos(ageInTicks * 0.5F) * 3.0F;
      this.LArm.rotationPointX = 3.5F + MathHelper.cos(ageInTicks * 0.5F) * 3.0F;
      this.RArm.rotationPointY = 4.0F + MathHelper.cos(ageInTicks * 1.0F) * 1.0F;
      this.LArm.rotationPointY = 4.0F + MathHelper.cos(ageInTicks * 1.0F) * 1.0F;
      this.RArm.rotateAngleX = -MathHelper.cos(ageInTicks * 0.5F - 2.0F) * 0.5F;
      this.LArm.rotateAngleX = MathHelper.cos(ageInTicks * 0.5F - 2.0F) * 0.5F;
      this.RArm.rotateAngleZ = 1.5F + MathHelper.cos(ageInTicks * 0.25F) * 1.0F;
      this.LArm.rotateAngleZ = -1.5F + MathHelper.cos(ageInTicks * 0.25F) * 1.0F;
      this.RArm.rotateAngleY = 0.0F;
      this.LArm.rotateAngleY = 0.0F;
      this.LLeg.rotateAngleZ = -(MathHelper.cos(ageInTicks * 0.5F) * 0.2F);
      this.RLeg.rotateAngleX = 0.0F + MathHelper.cos(ageInTicks * 0.5F) * 0.25F - MathHelper.cos(ageInTicks * 0.25F) * 1.0F;
      this.LLeg.rotateAngleX = 0.0F;
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
      this.RArm.rotateAngleZ = k * 0.25F;
      this.LArm.rotateAngleZ = -k * 2.25F;
      this.RLeg.rotateAngleZ = k * 0.5F;
      this.LLeg.rotateAngleZ = -k * 0.5F;
      this.Neck.rotateAngleY = -k * 0.25F;
      this.Head.rotateAngleY = -k * 0.25F;
    } 
  }
}
