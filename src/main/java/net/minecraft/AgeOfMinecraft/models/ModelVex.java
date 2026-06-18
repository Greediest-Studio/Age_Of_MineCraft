package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityVex;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelVex extends ModelBiped {
  protected ModelRenderer leftWing;
  
  protected ModelRenderer rightWing;
  
  public ModelVex() {
    this(0.0F);
  }
  
  public ModelVex(float p_i47224_1_) {
    super(p_i47224_1_, 0.0F, 64, 64);
    this.bipedLeftLeg.showModel = false;
    this.bipedHeadwear.showModel = false;
    this.bipedRightLeg = new ModelRenderer((ModelBase)this, 32, 0);
    this.bipedRightLeg.addBox(-1.0F, -1.0F, -2.0F, 6, 10, 4, 0.0F);
    this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
    this.rightWing = new ModelRenderer((ModelBase)this, 0, 32);
    this.rightWing.addBox(-20.0F, 0.0F, 0.0F, 20, 12, 1);
    this.leftWing = new ModelRenderer((ModelBase)this, 0, 32);
    this.leftWing.mirror = true;
    this.leftWing.addBox(0.0F, 0.0F, 0.0F, 20, 12, 1);
  }
  
  public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
    GlStateManager.pushMatrix();
    if (this.isChild) {
      GlStateManager.scale(0.75F, 0.75F, 0.75F);
      GlStateManager.translate(0.0F, 16.0F * scale, 0.0F);
      this.bipedHead.render(scale);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.scale(0.5F, 0.5F, 0.5F);
      GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
      this.bipedBody.render(scale);
      this.bipedRightArm.render(scale);
      this.bipedLeftArm.render(scale);
      this.bipedRightLeg.render(scale);
      this.bipedLeftLeg.render(scale);
      this.bipedHeadwear.render(scale);
      this.rightWing.render(scale);
      this.leftWing.render(scale);
    } else {
      if (entityIn.isSneaking())
        GlStateManager.translate(0.0F, 0.2F, 0.0F); 
      this.bipedHead.render(scale);
      this.bipedBody.render(scale);
      this.bipedRightArm.render(scale);
      this.bipedLeftArm.render(scale);
      this.bipedRightLeg.render(scale);
      this.bipedLeftLeg.render(scale);
      this.bipedHeadwear.render(scale);
      this.rightWing.render(scale);
      this.leftWing.render(scale);
    } 
    GlStateManager.popMatrix();
  }
  
  public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    EntityTameBase entity = (EntityTameBase)entityIn;
    if (entity instanceof EntityVex && (
      (EntityVex)entity).isCharging())
      if (((EntityVex)entity).getPrimaryHand() == EnumHandSide.RIGHT) {
        this.bipedRightArm.rotateAngleX = 3.7699115F;
      } else {
        this.bipedLeftArm.rotateAngleX = 3.7699115F;
      }  
    this.bipedRightLeg.rotateAngleX += 0.62831855F;
    this.rightWing.rotationPointZ = 2.0F;
    this.leftWing.rotationPointZ = 2.0F;
    this.rightWing.rotationPointY = 1.0F;
    this.leftWing.rotationPointY = 1.0F;
    this.rightWing.rotateAngleY = 0.47123894F + MathHelper.cos(ageInTicks * 0.8F) * 3.1415927F * 0.05F;
    this.leftWing.rotateAngleY = -this.rightWing.rotateAngleY;
    this.leftWing.rotateAngleZ = -0.47123894F;
    this.leftWing.rotateAngleX = 0.47123894F;
    this.rightWing.rotateAngleX = 0.47123894F;
    this.rightWing.rotateAngleZ = 0.47123894F;
    if (!entity.getCurrentBook().isEmpty()) {
      this.bipedRightArm.rotateAngleY = entity.bookSpread - 1.0F;
      this.bipedLeftArm.rotateAngleY = -entity.bookSpread + 1.0F;
      this.bipedRightArm.rotateAngleZ = 0.0F;
      this.bipedLeftArm.rotateAngleZ = 0.0F;
      this.bipedRightArm.rotateAngleX = -1.5F + 0.1F + MathHelper.sin(entity.ticksExisted * 0.1F) * 0.01F;
      this.bipedLeftArm.rotateAngleX = -1.5F + 0.1F + MathHelper.sin(entity.ticksExisted * 0.1F) * 0.01F;
    } 
  }
}
