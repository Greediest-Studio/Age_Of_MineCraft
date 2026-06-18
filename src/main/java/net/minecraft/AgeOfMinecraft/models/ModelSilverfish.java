package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntitySilverfish;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelSilverfish extends ModelBase {
  public final ModelRenderer[] silverfishBodyParts = new ModelRenderer[7];
  
  private final ModelRenderer[] silverfishWings;
  
  private final float[] zPlacement = new float[7];
  
  private static final int[][] SILVERFISH_BOX_LENGTH = new int[][] { { 3, 2, 2 }, { 4, 3, 2 }, { 6, 4, 3 }, { 3, 3, 3 }, { 2, 2, 3 }, { 2, 1, 2 }, { 1, 1, 2 } };
  
  private static final int[][] SILVERFISH_TEXTURE_POSITIONS = new int[][] { { 0, 0 }, { 0, 4 }, { 0, 9 }, { 0, 16 }, { 0, 22 }, { 11, 0 }, { 13, 4 } };
  
  public ModelSilverfish() {
    float f = -3.5F;
    for (int i = 0; i < this.silverfishBodyParts.length; i++) {
      this.silverfishBodyParts[i] = new ModelRenderer(this, SILVERFISH_TEXTURE_POSITIONS[i][0], SILVERFISH_TEXTURE_POSITIONS[i][1]);
      this.silverfishBodyParts[i].addBox(SILVERFISH_BOX_LENGTH[i][0] * -0.5F, 0.0F, SILVERFISH_BOX_LENGTH[i][2] * -0.5F, SILVERFISH_BOX_LENGTH[i][0], SILVERFISH_BOX_LENGTH[i][1], SILVERFISH_BOX_LENGTH[i][2]);
      this.silverfishBodyParts[i].setRotationPoint(0.0F, (24 - SILVERFISH_BOX_LENGTH[i][1]), f);
      this.zPlacement[i] = f;
      if (i < this.silverfishBodyParts.length - 1)
        f += (SILVERFISH_BOX_LENGTH[i][2] + SILVERFISH_BOX_LENGTH[i + 1][2]) * 0.5F; 
    } 
    this.silverfishWings = new ModelRenderer[3];
    this.silverfishWings[0] = new ModelRenderer(this, 20, 0);
    this.silverfishWings[0].addBox(-5.0F, 0.0F, SILVERFISH_BOX_LENGTH[2][2] * -0.5F, 10, 8, SILVERFISH_BOX_LENGTH[2][2]);
    this.silverfishWings[0].setRotationPoint(0.0F, 16.0F, this.zPlacement[2]);
    this.silverfishWings[1] = new ModelRenderer(this, 20, 11);
    this.silverfishWings[1].addBox(-3.0F, 0.0F, SILVERFISH_BOX_LENGTH[4][2] * -0.5F, 6, 4, SILVERFISH_BOX_LENGTH[4][2]);
    this.silverfishWings[1].setRotationPoint(0.0F, 20.0F, this.zPlacement[4]);
    this.silverfishWings[2] = new ModelRenderer(this, 20, 18);
    this.silverfishWings[2].addBox(-3.0F, 0.0F, SILVERFISH_BOX_LENGTH[4][2] * -0.5F, 6, 5, SILVERFISH_BOX_LENGTH[1][2]);
    this.silverfishWings[2].setRotationPoint(0.0F, 19.0F, this.zPlacement[1]);
  }
  
  public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
    for (ModelRenderer modelrenderer : this.silverfishBodyParts)
      modelrenderer.render(scale); 
    for (ModelRenderer modelrenderer1 : this.silverfishWings)
      modelrenderer1.render(scale); 
  }
  
  public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
    EntitySilverfish silverfish = (EntitySilverfish)entity;
    int i;
    for (i = 0; i < this.silverfishBodyParts.length; i++) {
      (this.silverfishBodyParts[i]).rotateAngleY = MathHelper.cos((silverfish.isAIDisabled() ? 1.0F : ageInTicks) * 0.9F + i * 0.15F * 3.1415927F) * 3.1415927F * (silverfish.isSneaking() ? 0.0125F : 0.05F) * (1 + Math.abs(i - 2));
      (this.silverfishBodyParts[i]).rotationPointX = MathHelper.sin((silverfish.isAIDisabled() ? 1.0F : ageInTicks) * 0.9F + i * 0.15F * 3.1415927F) * 3.1415927F * (silverfish.isSneaking() ? 0.05F : 0.2F) * Math.abs(i - 2);
    } 
    if (silverfish.getJukeboxToDanceTo() != null)
      for (i = 0; i < this.silverfishBodyParts.length; i++) {
        (this.silverfishBodyParts[i]).rotateAngleY = MathHelper.cos((silverfish.isAIDisabled() ? 1.0F : ageInTicks) * 0.45F + i * 0.125F * 3.1415927F) * 3.1415927F * 0.1F * (1 + Math.abs(i - 2));
        (this.silverfishBodyParts[i]).rotationPointX = MathHelper.sin((silverfish.isAIDisabled() ? 1.0F : ageInTicks) * 0.45F + i * 0.125F * 3.1415927F) * 3.1415927F * 0.2F * Math.abs(i - 2);
      }  
    (this.silverfishBodyParts[0]).rotateAngleY += netHeadYaw / 57.295776F;
    (this.silverfishBodyParts[0]).rotateAngleX = headPitch / 57.295776F;
    (this.silverfishWings[0]).rotateAngleY = (this.silverfishBodyParts[2]).rotateAngleY;
    (this.silverfishWings[1]).rotateAngleY = (this.silverfishBodyParts[4]).rotateAngleY;
    (this.silverfishWings[1]).rotationPointX = (this.silverfishBodyParts[4]).rotationPointX;
    (this.silverfishWings[2]).rotateAngleY = (this.silverfishBodyParts[1]).rotateAngleY;
    (this.silverfishWings[2]).rotationPointX = (this.silverfishBodyParts[1]).rotationPointX;
  }
}
