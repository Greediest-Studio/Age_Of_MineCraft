package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.model;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonMinion;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelDragonMinion extends ModelBase {
  private ModelRenderer head;
  
  private ModelRenderer neck;
  
  private ModelRenderer jaw;
  
  private ModelRenderer body;
  
  private ModelRenderer rearLeg;
  
  private ModelRenderer frontLeg;
  
  private ModelRenderer rearLegTip;
  
  private ModelRenderer frontLegTip;
  
  private ModelRenderer rearFoot;
  
  private ModelRenderer frontFoot;
  
  private ModelRenderer wing;
  
  private ModelRenderer wingTip;
  
  private float partialTicks;
  
  public ModelDragonMinion(float par1) {
    this.textureWidth = 256;
    this.textureHeight = 256;
    setTextureOffset("body.body", 0, 0);
    setTextureOffset("wing.skin", -56, 88);
    setTextureOffset("wingtip.skin", -56, 144);
    setTextureOffset("rearleg.main", 0, 0);
    setTextureOffset("rearfoot.main", 112, 0);
    setTextureOffset("rearlegtip.main", 196, 0);
    setTextureOffset("head.upperhead", 112, 30);
    setTextureOffset("wing.bone", 112, 88);
    setTextureOffset("head.upperlip", 176, 44);
    setTextureOffset("jaw.jaw", 176, 65);
    setTextureOffset("frontleg.main", 112, 104);
    setTextureOffset("wingtip.bone", 112, 136);
    setTextureOffset("frontfoot.main", 144, 104);
    setTextureOffset("neck.box", 192, 104);
    setTextureOffset("frontlegtip.main", 226, 138);
    setTextureOffset("body.scale", 220, 53);
    setTextureOffset("head.scale", 0, 0);
    setTextureOffset("neck.scale", 48, 0);
    setTextureOffset("head.nostril", 112, 0);
    float f1 = -16.0F;
    this.head = new ModelRenderer(this, "head");
    this.head.addBox("upperlip", -6.0F, -1.0F, -8.0F + f1, 12, 5, 16);
    this.head.addBox("upperhead", -8.0F, -8.0F, 6.0F + f1, 16, 16, 16);
    this.head.mirror = true;
    this.head.addBox("scale", -5.0F, -12.0F, 12.0F + f1, 2, 4, 6);
    this.head.addBox("nostril", -5.0F, -3.0F, -6.0F + f1, 2, 2, 4);
    this.head.mirror = false;
    this.head.addBox("scale", 3.0F, -12.0F, 12.0F + f1, 2, 4, 6);
    this.head.addBox("nostril", 3.0F, -3.0F, -6.0F + f1, 2, 2, 4);
    this.jaw = new ModelRenderer(this, "jaw");
    this.jaw.setRotationPoint(0.0F, 4.0F, 8.0F + f1);
    this.jaw.addBox("jaw", -6.0F, 0.0F, -16.0F, 12, 4, 16);
    this.head.addChild(this.jaw);
    this.neck = new ModelRenderer(this, "neck");
    this.neck.addBox("box", -5.0F, -5.0F, -5.0F, 10, 10, 10);
    this.neck.addBox("scale", -1.0F, -9.0F, -3.0F, 2, 4, 6);
    this.body = new ModelRenderer(this, "body");
    this.body.setRotationPoint(0.0F, 4.0F, 8.0F);
    this.body.addBox("body", -12.0F, 0.0F, -16.0F, 24, 24, 64);
    this.body.addBox("scale", -1.0F, -6.0F, -10.0F, 2, 6, 12);
    this.body.addBox("scale", -1.0F, -6.0F, 10.0F, 2, 6, 12);
    this.body.addBox("scale", -1.0F, -6.0F, 30.0F, 2, 6, 12);
    this.wing = new ModelRenderer(this, "wing");
    this.wing.setRotationPoint(-12.0F, 5.0F, 2.0F);
    this.wing.addBox("bone", -56.0F, -4.0F, -4.0F, 56, 8, 8);
    this.wing.addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
    this.wingTip = new ModelRenderer(this, "wingtip");
    this.wingTip.setRotationPoint(-56.0F, 0.0F, 0.0F);
    this.wingTip.addBox("bone", -56.0F, -2.0F, -2.0F, 56, 4, 4);
    this.wingTip.addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
    this.wing.addChild(this.wingTip);
    this.frontLeg = new ModelRenderer(this, "frontleg");
    this.frontLeg.setRotationPoint(-12.0F, 20.0F, 2.0F);
    this.frontLeg.addBox("main", -4.0F, -4.0F, -4.0F, 8, 24, 8);
    this.frontLegTip = new ModelRenderer(this, "frontlegtip");
    this.frontLegTip.setRotationPoint(0.0F, 20.0F, -1.0F);
    this.frontLegTip.addBox("main", -3.0F, -1.0F, -3.0F, 6, 24, 6);
    this.frontLeg.addChild(this.frontLegTip);
    this.frontFoot = new ModelRenderer(this, "frontfoot");
    this.frontFoot.setRotationPoint(0.0F, 23.0F, 0.0F);
    this.frontFoot.addBox("main", -4.0F, 0.0F, -12.0F, 8, 4, 16);
    this.frontLegTip.addChild(this.frontFoot);
    this.rearLeg = new ModelRenderer(this, "rearleg");
    this.rearLeg.setRotationPoint(-16.0F, 16.0F, 42.0F);
    this.rearLeg.addBox("main", -8.0F, -4.0F, -8.0F, 16, 32, 16);
    this.rearLegTip = new ModelRenderer(this, "rearlegtip");
    this.rearLegTip.setRotationPoint(0.0F, 32.0F, -4.0F);
    this.rearLegTip.addBox("main", -6.0F, -2.0F, 0.0F, 12, 32, 12);
    this.rearLeg.addChild(this.rearLegTip);
    this.rearFoot = new ModelRenderer(this, "rearfoot");
    this.rearFoot.setRotationPoint(0.0F, 31.0F, 4.0F);
    this.rearFoot.addBox("main", -9.0F, 0.0F, -20.0F, 18, 6, 24);
    this.rearLegTip.addChild(this.rearFoot);
  }
  
  public void setLivingAnimations(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4) {
    this.partialTicks = par4;
  }
  
  public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
    GlStateManager.pushMatrix();
    EntityDragonMinion entitydragon = (EntityDragonMinion)par1Entity;
    float f6 = entitydragon.prevAnimTime + (entitydragon.animTime - entitydragon.prevAnimTime) * this.partialTicks;
    this.jaw.rotateAngleX = (float)(Math.sin((f6 * 3.1415927F * 2.0F)) + 1.0D) * 0.2F;
    float f7 = (float)(Math.sin((f6 * 3.1415927F * 2.0F - 1.0F)) + 1.0D);
    f7 = (f7 * f7 * 1.0F + f7 * 2.0F) * 0.05F;
    GlStateManager.translate(0.0F, f7 - 2.0F, -3.0F);
    GlStateManager.rotate(f7 * 2.0F, 1.0F, 0.0F, 0.0F);
    float f8 = -30.0F;
    float f9 = 0.0F;
    float f10 = 1.5F;
    double[] adouble = entitydragon.getMovementOffsets(6, this.partialTicks);
    float f11 = updateRotations(entitydragon.getMovementOffsets(5, this.partialTicks)[0] - entitydragon.getMovementOffsets(10, this.partialTicks)[0]);
    float f12 = updateRotations(entitydragon.getMovementOffsets(5, this.partialTicks)[0] + (f11 / 2.0F));
    f8 += 2.0F;
    float f13 = f6 * 3.1415927F * 2.0F;
    f8 = 20.0F;
    float f14 = -12.0F;
    for (int i = 0; i < 5; i++) {
      double[] adouble1 = entitydragon.getMovementOffsets(5 - i, this.partialTicks);
      float f15 = (float)Math.cos((i * 0.45F + f13)) * 0.15F;
      this.neck.rotateAngleY = updateRotations(adouble1[0] - adouble[0]) * 3.1415927F / 180.0F * f10;
      this.neck.rotateAngleX = f15 + (float)(adouble1[1] - adouble[1]) * 3.1415927F / 180.0F * f10 * 5.0F;
      this.neck.rotateAngleZ = -updateRotations(adouble1[0] - f12) * 3.1415927F / 180.0F * f10;
      this.neck.rotationPointY = f8;
      this.neck.rotationPointZ = f14;
      this.neck.rotationPointX = f9;
      f8 = (float)(f8 + Math.sin(this.neck.rotateAngleX) * 10.0D);
      f14 = (float)(f14 - Math.cos(this.neck.rotateAngleY) * Math.cos(this.neck.rotateAngleX) * 10.0D);
      f9 = (float)(f9 - Math.sin(this.neck.rotateAngleY) * Math.cos(this.neck.rotateAngleX) * 10.0D);
      this.neck.render(par7);
    } 
    this.head.rotationPointY = f8;
    this.head.rotationPointZ = f14;
    this.head.rotationPointX = f9;
    double[] adouble2 = entitydragon.getMovementOffsets(0, this.partialTicks);
    this.head.rotateAngleY = updateRotations(adouble2[0] - adouble[0]) * 3.1415927F / 180.0F * 1.0F;
    this.head.rotateAngleZ = -updateRotations(adouble2[0] - f12) * 3.1415927F / 180.0F * 1.0F;
    this.head.render(par7);
    GlStateManager.pushMatrix();
    GlStateManager.translate(0.0F, 1.0F, 0.0F);
    GlStateManager.rotate(-f11 * f10 * 1.0F, 0.0F, 0.0F, 1.0F);
    GlStateManager.translate(0.0F, -1.0F, 0.0F);
    this.body.rotateAngleZ = 0.0F;
    this.body.render(par7);
    for (int j = 0; j < 2; j++) {
      GlStateManager.enableCull();
      float f15 = f6 * 3.1415927F * 2.0F;
      this.wing.rotateAngleX = 0.125F - (float)Math.cos(f15) * 0.2F;
      this.wing.rotateAngleY = 0.25F;
      this.wing.rotateAngleZ = (float)(Math.sin(f15) + 0.125D) * 0.8F;
      this.wingTip.rotateAngleZ = -((float)(Math.sin((f15 + 2.0F)) + 0.5D)) * 0.75F;
      this.rearLeg.rotateAngleX = 1.0F + f7 * 0.1F;
      this.rearLegTip.rotateAngleX = 0.5F + f7 * 0.1F;
      this.rearFoot.rotateAngleX = 0.75F + f7 * 0.1F;
      this.frontLeg.rotateAngleX = 1.3F + f7 * 0.1F;
      this.frontLegTip.rotateAngleX = -0.5F - f7 * 0.1F;
      this.frontFoot.rotateAngleX = 0.75F + f7 * 0.1F;
      this.wing.render(par7);
      this.frontLeg.render(par7);
      this.rearLeg.render(par7);
      GlStateManager.scale(-1.0F, 1.0F, 1.0F);
      if (j == 0)
        GlStateManager.cullFace(GlStateManager.CullFace.FRONT); 
    } 
    GlStateManager.popMatrix();
    GlStateManager.cullFace(GlStateManager.CullFace.BACK);
    GlStateManager.disableCull();
    float f16 = -((float)Math.sin((f6 * 3.1415927F * 2.0F))) * 0.0F;
    f13 = f6 * 3.1415927F * 2.0F;
    f8 = 10.0F;
    f14 = 60.0F;
    f9 = 0.0F;
    adouble = entitydragon.getMovementOffsets(11, this.partialTicks);
    for (int k = 0; k < 12; k++) {
      adouble2 = entitydragon.getMovementOffsets(12 + k, this.partialTicks);
      f16 = (float)(f16 + Math.sin((k * 0.45F + f13)) * 0.05000000074505806D);
      this.neck.rotateAngleY = (updateRotations(adouble2[0] - adouble[0]) * f10 + 180.0F) * 3.1415927F / 180.0F;
      this.neck.rotateAngleX = f16 + (float)(adouble2[1] - adouble[1]) * 3.1415927F / 180.0F * f10 * 5.0F;
      this.neck.rotateAngleZ = updateRotations(adouble2[0] - f12) * 3.1415927F / 180.0F * f10;
      this.neck.rotationPointY = f8;
      this.neck.rotationPointZ = f14;
      this.neck.rotationPointX = f9;
      f8 = (float)(f8 + Math.sin(this.neck.rotateAngleX) * 10.0D);
      f14 = (float)(f14 - Math.cos(this.neck.rotateAngleY) * Math.cos(this.neck.rotateAngleX) * 10.0D);
      f9 = (float)(f9 - Math.sin(this.neck.rotateAngleY) * Math.cos(this.neck.rotateAngleX) * 10.0D);
      this.neck.render(par7);
    } 
    GlStateManager.popMatrix();
  }
  
  private float updateRotations(double par1) {
    while (par1 >= 180.0D)
      par1 -= 360.0D; 
    while (par1 < -180.0D)
      par1 += 360.0D; 
    return (float)par1;
  }
}
