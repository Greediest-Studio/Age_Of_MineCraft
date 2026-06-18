package net.minecraft.AgeOfMinecraft.models;

import java.util.Random;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGhast;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelGhast extends ModelBase {
  public ModelRenderer body;
  
  public ModelRenderer[] tentacles = new ModelRenderer[9];
  
  public ModelGhast() {
    this.body = new ModelRenderer(this, 0, 0);
    this.body.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16);
    this.body.rotationPointY += 8.0F;
    Random random = new Random(1660L);
    for (int j = 0; j < this.tentacles.length; j++) {
      this.tentacles[j] = new ModelRenderer(this, 0, 0);
      float f = (((j % 3) - (j / 3 % 2) * 0.5F + 0.25F) / 2.0F * 2.0F - 1.0F) * 5.0F;
      float f1 = ((j / 3) / 2.0F * 2.0F - 1.0F) * 5.0F;
      int k = random.nextInt(7) + 8;
      this.tentacles[j].addBox(-1.0F, 0.0F, -1.0F, 2, k, 2);
      (this.tentacles[j]).rotationPointX = f;
      (this.tentacles[j]).rotationPointZ = f1;
      (this.tentacles[j]).rotationPointY = 15.0F;
      this.body.addChild(this.tentacles[j]);
    } 
  }
  
  public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    for (int i = 0; i < this.tentacles.length; i++) {
      (this.tentacles[i]).rotateAngleZ = 0.0F;
      (this.tentacles[i]).rotationPointY = 7.0F;
      (this.tentacles[i]).rotateAngleX = 0.2F * MathHelper.sin((((EntityGhast)entityIn).isAIDisabled() ? 1.0F : ageInTicks) * 0.3F + i) + 0.4F;
    } 
    EntityGhast entity = (EntityGhast)entityIn;
    if (entity.getCurrentBook().isEmpty())
      this.body.rotateAngleY = netHeadYaw / 57.295776F; 
    this.body.rotateAngleX = !entity.isEntityAlive() ? 0.0F : (headPitch / 57.295776F);
    if (entity.getJukeboxToDanceTo() != null) {
      this.body.rotateAngleX += MathHelper.sin(ageInTicks * 0.5F) * 0.25F;
      this.body.rotateAngleY += MathHelper.sin(ageInTicks * 0.25F) * 0.5F;
      for (int k = 0; k < this.tentacles.length; k++)
        (this.tentacles[k]).rotateAngleZ += MathHelper.sin(k * 2.0F + (entity.isAIDisabled() ? 1.0F : ageInTicks) * 0.25F) * 0.5F; 
    } 
    if (!entity.getCurrentBook().isEmpty()) {
      (this.tentacles[0]).rotateAngleY = entity.bookSpread - 1.0F;
      (this.tentacles[2]).rotateAngleY = -entity.bookSpread + 1.0F;
      (this.tentacles[0]).rotateAngleZ = 0.0F;
      (this.tentacles[2]).rotateAngleZ = 0.0F;
      (this.tentacles[0]).rotateAngleX = -1.5F + 0.1F + MathHelper.sin(entity.ticksExisted * 0.1F) * 0.01F;
      (this.tentacles[2]).rotateAngleX = -1.5F + 0.1F + MathHelper.sin(entity.ticksExisted * 0.1F) * 0.01F;
    } 
  }
  
  public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
    GlStateManager.pushMatrix();
    GlStateManager.translate(0.0F, 0.55F, 0.0F);
    this.body.render(scale);
    GlStateManager.popMatrix();
  }
}
