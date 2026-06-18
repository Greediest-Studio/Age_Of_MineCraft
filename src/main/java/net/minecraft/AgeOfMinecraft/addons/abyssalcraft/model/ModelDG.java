package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.model;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class ModelDG extends ModelBase {
  public boolean isSneak;
  
  public ModelRenderer Head;
  
  public ModelRenderer jaw;
  
  public ModelRenderer tooth1;
  
  public ModelRenderer tooth2;
  
  public ModelRenderer tooth3;
  
  public ModelRenderer tooth4;
  
  public ModelRenderer tooth5;
  
  public ModelRenderer Spine1;
  
  public ModelRenderer Spine2;
  
  public ModelRenderer lrib1;
  
  public ModelRenderer lrib2;
  
  public ModelRenderer lrib3;
  
  public ModelRenderer rrib1;
  
  public ModelRenderer rrib2;
  
  public ModelRenderer rrib3;
  
  public ModelRenderer pelvis;
  
  public ModelRenderer Spine3;
  
  public ModelRenderer larm1;
  
  public ModelRenderer larm2;
  
  public ModelRenderer clawl1;
  
  public ModelRenderer clawl2;
  
  public ModelRenderer clawl3;
  
  public ModelRenderer clawl4;
  
  public ModelRenderer rarm1;
  
  public ModelRenderer rarm2;
  
  public ModelRenderer clawr1;
  
  public ModelRenderer clawr2;
  
  public ModelRenderer clawr3;
  
  public ModelRenderer clawr4;
  
  public ModelRenderer lleg;
  
  public ModelRenderer rleg;
  
  public ModelRenderer back;
  
  public ModelRenderer lside;
  
  public ModelRenderer rside;
  
  public ModelDG() {
    this(0.0F);
  }
  
  public ModelDG(float f) {
    this.textureWidth = 128;
    this.textureHeight = 64;
    this.Head = new ModelRenderer(this, 0, 0);
    this.Head.setTextureSize(128, 64);
    this.Head.addBox(-4.5F, -9.5F, -4.5F, 9, 9, 9, f);
    this.Head.setRotationPoint(0.0F, -17.0F, 1.0F);
    this.jaw = new ModelRenderer(this, 36, 0);
    this.jaw.setTextureSize(128, 64);
    this.jaw.addBox(-4.5F, -0.5F, -4.5F, 9, 1, 9, f);
    this.jaw.setRotationPoint(0.0F, 1.0F, 0.0F);
    setRotation(this.jaw, 0.2365561F, 0.0F, 0.0F);
    this.Head.addChild(this.jaw);
    this.tooth1 = new ModelRenderer(this, 48, 11);
    this.tooth1.setTextureSize(128, 64);
    this.tooth1.addBox(-0.5F, -1.0F, -0.5F, 1, 2, 1, f);
    this.tooth1.setRotationPoint(-4.0F, -1.5F, -4.0F);
    this.jaw.addChild(this.tooth1);
    this.tooth2 = new ModelRenderer(this, 48, 11);
    this.tooth2.setTextureSize(128, 64);
    this.tooth2.addBox(-0.5F, -1.0F, -0.5F, 1, 2, 1, f);
    this.tooth2.setRotationPoint(-2.0F, -1.5F, -4.0F);
    this.jaw.addChild(this.tooth2);
    this.tooth3 = new ModelRenderer(this, 48, 11);
    this.tooth3.setTextureSize(128, 64);
    this.tooth3.addBox(-0.5F, -1.0F, -0.5F, 1, 2, 1, f);
    this.tooth3.setRotationPoint(0.0F, -1.5F, -4.0F);
    this.jaw.addChild(this.tooth3);
    this.tooth4 = new ModelRenderer(this, 48, 11);
    this.tooth4.setTextureSize(128, 64);
    this.tooth4.addBox(-0.5F, -1.0F, -0.5F, 1, 2, 1, f);
    this.tooth4.setRotationPoint(2.0F, -1.5F, -4.0F);
    this.jaw.addChild(this.tooth4);
    this.tooth5 = new ModelRenderer(this, 48, 11);
    this.tooth5.setTextureSize(128, 64);
    this.tooth5.addBox(-0.5F, -1.0F, -0.5F, 1, 2, 1, f);
    this.tooth5.setRotationPoint(4.0F, -1.5F, -4.0F);
    this.jaw.addChild(this.tooth5);
    this.Spine1 = new ModelRenderer(this, 0, 42);
    this.Spine1.setTextureSize(128, 64);
    this.Spine1.addBox(-2.5F, -4.0F, -3.0F, 5, 8, 6, f);
    this.Spine1.setRotationPoint(0.0F, -14.0F, 2.0F);
    setRotation(this.Spine1, 0.2590069F, 0.0F, 0.0F);
    this.Spine2 = new ModelRenderer(this, 0, 42);
    this.Spine2.setTextureSize(128, 64);
    this.Spine2.addBox(-2.5F, -8.0F, -3.0F, 5, 16, 6, f);
    this.Spine2.setRotationPoint(0.0F, -2.854666F, 2.884038F);
    this.lrib1 = new ModelRenderer(this, 101, 47);
    this.lrib1.setTextureSize(128, 64);
    this.lrib1.addBox(-1.5F, -1.0F, -1.0F, 3, 2, 2, f);
    this.lrib1.setRotationPoint(4.0F, -6.977365F, 2.8262F);
    setRotation(this.lrib1, -0.07023507F, 0.0F, 0.0F);
    this.lrib2 = new ModelRenderer(this, 101, 47);
    this.lrib2.setTextureSize(128, 64);
    this.lrib2.addBox(-1.5F, -1.0F, -1.0F, 3, 2, 2, f);
    this.lrib2.setRotationPoint(4.0F, -3.821312F, 2.627918F);
    setRotation(this.lrib2, -0.07023507F, 0.0F, 0.0F);
    this.lrib3 = new ModelRenderer(this, 101, 47);
    this.lrib3.setTextureSize(128, 64);
    this.lrib3.addBox(-1.5F, -1.0F, -1.0F, 3, 2, 2, f);
    this.lrib3.setRotationPoint(4.0F, -0.6646652F, 2.434038F);
    setRotation(this.lrib3, -0.07023507F, 0.0F, 0.0F);
    this.rrib1 = new ModelRenderer(this, 101, 47);
    this.rrib1.setTextureSize(128, 64);
    this.rrib1.addBox(-1.5F, -1.0F, -1.0F, 3, 2, 2, f);
    this.rrib1.setRotationPoint(-4.0F, -6.977365F, 2.826201F);
    setRotation(this.rrib1, -0.07023507F, 0.0F, 0.0F);
    this.rrib2 = new ModelRenderer(this, 101, 47);
    this.rrib2.setTextureSize(128, 64);
    this.rrib2.addBox(-1.5F, -1.0F, -1.0F, 3, 2, 2, f);
    this.rrib2.setRotationPoint(-4.0F, -3.82131F, 2.627918F);
    setRotation(this.rrib2, -0.07023507F, 0.0F, 0.0F);
    this.rrib3 = new ModelRenderer(this, 101, 47);
    this.rrib3.setTextureSize(128, 64);
    this.rrib3.addBox(-1.5F, -1.0F, -1.0F, 3, 2, 2, f);
    this.rrib3.setRotationPoint(-4.0F, -0.6652546F, 2.429635F);
    setRotation(this.rrib3, -0.07023507F, 0.0F, 0.0F);
    this.pelvis = new ModelRenderer(this, 80, 14);
    this.pelvis.setTextureSize(128, 64);
    this.pelvis.addBox(-6.0F, -1.0F, -3.0F, 12, 2, 6, f);
    this.pelvis.setRotationPoint(0.0F, 5.390734F, 2.999714F);
    setRotation(this.pelvis, -1.637653E-7F, 0.0F, 0.0F);
    this.Spine3 = new ModelRenderer(this, 76, 28);
    this.Spine3.setTextureSize(128, 64);
    this.Spine3.addBox(-9.0F, -2.0F, -2.0F, 18, 4, 4, f);
    this.Spine3.setRotationPoint(0.0F, -11.10007F, 2.768362F);
    setRotation(this.Spine3, 0.1290269F, 0.0F, 0.0F);
    this.larm1 = new ModelRenderer(this, 46, 48);
    this.larm1.setTextureSize(128, 64);
    this.larm1.addBox(0.0F, -2.0F, -2.0F, 4, 12, 4, f);
    this.larm1.setRotationPoint(9.0F, -11.0F, 2.0F);
    setRotation(this.larm1, -0.5595525F, 0.0F, 0.0F);
    this.larm2 = new ModelRenderer(this, 64, 52);
    this.larm2.setTextureSize(128, 64);
    this.larm2.addBox(-2.0F, 1.0F, 3.0F, 4, 8, 4, f);
    this.larm2.setRotationPoint(2.0F, 4.0F, -2.0F);
    setRotation(this.larm2, -1.44967F - this.larm1.rotateAngleX, 0.0F, 0.0F);
    this.clawl1 = new ModelRenderer(this, 110, 57);
    this.clawl1.setTextureSize(128, 64);
    this.clawl1.addBox(-0.5F, -0.5F, -1.0F, 1, 5, 1, f);
    this.clawl1.setRotationPoint(-1.0F, 8.0F, 3.0F);
    this.clawl2 = new ModelRenderer(this, 110, 57);
    this.clawl2.setTextureSize(128, 64);
    this.clawl2.addBox(-0.5F, -0.5F, -1.0F, 1, 5, 1, f);
    this.clawl2.setRotationPoint(1.0F, 8.0F, 3.0F);
    this.clawl3 = new ModelRenderer(this, 110, 57);
    this.clawl3.setTextureSize(128, 64);
    this.clawl3.addBox(-1.0F, -0.5F, -1.0F, 1, 5, 1, f);
    this.clawl3.setRotationPoint(-2.0F, 8.0F, 5.0F);
    this.clawl4 = new ModelRenderer(this, 110, 57);
    this.clawl4.setTextureSize(128, 64);
    this.clawl4.addBox(0.0F, -0.5F, -1.0F, 1, 5, 1, f);
    this.clawl4.setRotationPoint(2.0F, 8.0F, 5.0F);
    this.rarm1 = new ModelRenderer(this, 46, 48);
    this.rarm1.setTextureSize(128, 64);
    this.rarm1.addBox(-4.0F, -2.0F, -2.0F, 4, 12, 4, f);
    this.rarm1.setRotationPoint(-9.0F, -11.0F, 2.0F);
    setRotation(this.rarm1, -0.559472F, 0.0F, 0.0F);
    this.rarm2 = new ModelRenderer(this, 64, 52);
    this.rarm2.setTextureSize(128, 64);
    this.rarm2.addBox(-2.0F, 1.0F, 3.0F, 4, 8, 4, f);
    this.rarm2.setRotationPoint(-2.0F, 4.0F, -2.0F);
    setRotation(this.rarm2, -1.449542F - this.rarm1.rotateAngleX, 0.0F, 0.0F);
    this.clawr1 = new ModelRenderer(this, 110, 57);
    this.clawr1.setTextureSize(128, 64);
    this.clawr1.addBox(-0.5F, -0.5F, -1.0F, 1, 5, 1, f);
    this.clawr1.setRotationPoint(-1.0F, 8.0F, 3.0F);
    this.clawr2 = new ModelRenderer(this, 110, 57);
    this.clawr2.setTextureSize(128, 64);
    this.clawr2.addBox(-0.5F, -0.5F, -1.0F, 1, 5, 1, f);
    this.clawr2.setRotationPoint(1.0F, 8.0F, 3.0F);
    this.clawr3 = new ModelRenderer(this, 110, 57);
    this.clawr3.setTextureSize(128, 64);
    this.clawr3.addBox(0.0F, -0.5F, -1.0F, 1, 5, 1, f);
    this.clawr3.setRotationPoint(-3.0F, 8.0F, 5.0F);
    this.clawr4 = new ModelRenderer(this, 110, 57);
    this.clawr4.setTextureSize(128, 64);
    this.clawr4.addBox(-1.0F, -0.5F, -1.0F, 1, 5, 1, f);
    this.clawr4.setRotationPoint(3.0F, 8.0F, 5.0F);
    this.lleg = new ModelRenderer(this, 22, 40);
    this.lleg.setTextureSize(128, 64);
    this.lleg.addBox(-3.0F, -1.0F, -3.0F, 6, 18, 6, f);
    this.lleg.setRotationPoint(3.0F, 7.0F, 3.0F);
    this.rleg = new ModelRenderer(this, 22, 40);
    this.rleg.setTextureSize(128, 64);
    this.rleg.addBox(-3.0F, -1.0F, -3.0F, 6, 18, 6, f);
    this.rleg.setRotationPoint(-3.0F, 7.0F, 3.0F);
    this.back = new ModelRenderer(this, 0, 18);
    this.back.setTextureSize(128, 64);
    this.back.addBox(-6.0F, -7.5F, 0.0F, 12, 15, 0, f);
    this.back.setRotationPoint(0.0F, -3.0F, 6.0F);
    this.lside = new ModelRenderer(this, 30, 12);
    this.lside.setTextureSize(128, 64);
    this.lside.addBox(0.0F, -7.5F, -3.0F, 0, 15, 6, f);
    this.lside.setRotationPoint(6.0F, -3.0F, 3.0F);
    this.rside = new ModelRenderer(this, 42, 12);
    this.rside.setTextureSize(128, 64);
    this.rside.addBox(0.0F, -7.5F, -3.0F, 0, 15, 6, f);
    this.rside.setRotationPoint(-6.0F, -3.0F, 3.0F);
    this.larm1.addChild(this.larm2);
    this.rarm1.addChild(this.rarm2);
    this.larm2.addChild(this.clawl1);
    this.larm2.addChild(this.clawl2);
    this.larm2.addChild(this.clawl3);
    this.larm2.addChild(this.clawl4);
    this.rarm2.addChild(this.clawr1);
    this.rarm2.addChild(this.clawr2);
    this.rarm2.addChild(this.clawr3);
    this.rarm2.addChild(this.clawr4);
  }
  
  public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
    super.render(par1Entity, par2, par3, par4, par5, par6, par7);
    setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
    if (this.isChild) {
      float f6 = 2.0F;
      GlStateManager.pushMatrix();
      GlStateManager.scale(1.5F / f6, 1.5F / f6, 1.5F / f6);
      GlStateManager.translate(0.0F, 21.0F * par7, 0.0F);
      this.Head.render(par7);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.scale(1.0F / f6, 1.0F / f6, 1.0F / f6);
      GlStateManager.translate(0.0F, 24.0F * par7, 0.0F);
      this.Spine1.render(par7);
      this.Spine2.render(par7);
      this.lrib1.render(par7);
      this.lrib2.render(par7);
      this.lrib3.render(par7);
      this.rrib1.render(par7);
      this.rrib2.render(par7);
      this.rrib3.render(par7);
      this.pelvis.render(par7);
      this.Spine3.render(par7);
      this.larm1.render(par7);
      this.rarm1.render(par7);
      this.lleg.render(par7);
      this.rleg.render(par7);
      this.back.render(par7);
      this.lside.render(par7);
      this.rside.render(par7);
      GlStateManager.popMatrix();
    } else {
      this.Head.render(par7);
      this.Spine1.render(par7);
      this.Spine2.render(par7);
      this.lrib1.render(par7);
      this.lrib2.render(par7);
      this.lrib3.render(par7);
      this.rrib1.render(par7);
      this.rrib2.render(par7);
      this.rrib3.render(par7);
      this.pelvis.render(par7);
      this.Spine3.render(par7);
      this.larm1.render(par7);
      this.rarm1.render(par7);
      this.lleg.render(par7);
      this.rleg.render(par7);
      this.back.render(par7);
      this.lside.render(par7);
      this.rside.render(par7);
    } 
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z) {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
    EntityTameBase entity = (EntityTameBase)par7Entity;
    float f2 = entity.isArmsRaised() ? -1.0F : 0.0F;
    this.Head.rotateAngleY = par4 / 57.295776F;
    this.Head.rotateAngleX = par5 / 57.295776F;
    this.rleg.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
    this.rleg.rotateAngleY = 0.0F;
    this.lleg.rotateAngleX = MathHelper.cos(par1 * 0.6662F + 3.1415927F) * 1.4F * par2;
    this.lleg.rotateAngleY = 0.0F;
    if (this.isRiding) {
      this.rarm1.rotateAngleX += -0.62831855F;
      this.larm1.rotateAngleX += -0.62831855F;
      this.rleg.rotateAngleX = -1.2566371F;
      this.lleg.rotateAngleX = -1.2566371F;
      this.rleg.rotateAngleY = 0.31415927F;
      this.lleg.rotateAngleY = -0.31415927F;
    } 
    float f6 = MathHelper.sin(this.swingProgress * 3.1415927F);
    float f7 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * 3.1415927F);
    this.rarm1.rotateAngleZ = 0.0F;
    this.larm1.rotateAngleZ = 0.0F;
    this.rarm1.rotateAngleY = -(0.1F - f6 * 0.6F);
    this.larm1.rotateAngleY = 0.1F - f6 * 0.6F;
    this.rarm1.rotateAngleX = -0.84907913F + f2;
    this.larm1.rotateAngleX = -0.84907913F + f2;
    this.rarm1.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
    this.larm1.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
    this.rarm1.rotateAngleZ += MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
    this.larm1.rotateAngleZ -= MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
    this.rarm1.rotateAngleX += MathHelper.sin(par3 * 0.067F) * 0.05F;
    this.larm1.rotateAngleX -= MathHelper.sin(par3 * 0.067F) * 0.05F;
    if (this.isSneak) {
      this.Head.rotateAngleX -= 0.5F;
      this.rarm1.rotateAngleX -= 0.5F;
      this.larm1.rotateAngleX -= 0.5F;
      this.rleg.rotateAngleX -= 0.5F;
      this.lleg.rotateAngleX -= 0.5F;
    } 
    if (entity.isBurning()) {
      this.Head.rotateAngleX -= 0.75F;
      this.Head.rotateAngleY += MathHelper.cos(par3 * 0.6662F) * 0.5F;
      this.rarm1.rotateAngleX = -MathHelper.cos(par3 * 0.6662F) - 2.0F;
      this.larm1.rotateAngleX = MathHelper.cos(par3 * 0.6662F) - 2.0F;
      this.rarm1.rotateAngleY = 0.0F;
      this.larm1.rotateAngleY = 0.0F;
    } 
    if (!entity.getCurrentBook().isEmpty()) {
      this.rarm1.rotateAngleY = entity.bookSpread - 1.25F;
      this.larm1.rotateAngleY = -entity.bookSpread + 1.25F;
      this.rarm1.rotateAngleZ = entity.bookSpread;
      this.larm1.rotateAngleZ = -entity.bookSpread;
      this.rarm1.rotateAngleX = -1.25F + 0.1F + MathHelper.sin(entity.ticksExisted * 0.1F) * 0.01F;
      this.larm1.rotateAngleX = -1.25F + 0.1F + MathHelper.sin(entity.ticksExisted * 0.1F) * 0.01F;
    } 
  }
  
  public void setInvisible(boolean invisible) {
    this.Head.showModel = invisible;
    this.Spine1.showModel = invisible;
    this.Spine2.showModel = invisible;
    this.lrib1.showModel = invisible;
    this.lrib2.showModel = invisible;
    this.lrib3.showModel = invisible;
    this.rrib1.showModel = invisible;
    this.rrib2.showModel = invisible;
    this.rrib3.showModel = invisible;
    this.pelvis.showModel = invisible;
    this.Spine3.showModel = invisible;
    this.larm1.showModel = invisible;
    this.rarm1.showModel = invisible;
    this.lleg.showModel = invisible;
    this.rleg.showModel = invisible;
    this.back.showModel = invisible;
    this.lside.showModel = invisible;
    this.rside.showModel = invisible;
  }
  
  public void postRenderArm(float scale, EnumHandSide side) {
    getArmForSide(side).postRender(scale);
  }
  
  protected ModelRenderer getArmForSide(EnumHandSide side) {
    return (side == EnumHandSide.LEFT) ? this.larm1 : this.rarm1;
  }
}
