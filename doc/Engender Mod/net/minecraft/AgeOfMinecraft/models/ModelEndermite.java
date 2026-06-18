package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityEndermite;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelEndermite extends ModelBase {
  private static final int[][] BODY_SIZES = new int[][] { { 4, 3, 2 }, { 6, 4, 5 }, { 3, 3, 1 }, { 1, 2, 1 } };
  
  private static final int[][] BODY_TEXS = new int[][] { { 0, 0 }, { 0, 5 }, { 0, 14 }, { 0, 18 } };
  
  private static final int BODY_COUNT = BODY_SIZES.length;
  
  public final ModelRenderer[] bodyParts = new ModelRenderer[BODY_COUNT];
  
  public ModelEndermite() {
    float f = -3.5F;
    for (int i = 0; i < this.bodyParts.length; i++) {
      this.bodyParts[i] = new ModelRenderer(this, BODY_TEXS[i][0], BODY_TEXS[i][1]);
      this.bodyParts[i].func_78789_a(BODY_SIZES[i][0] * -0.5F, 0.0F, BODY_SIZES[i][2] * -0.5F, BODY_SIZES[i][0], BODY_SIZES[i][1], BODY_SIZES[i][2]);
      this.bodyParts[i].func_78793_a(0.0F, (24 - BODY_SIZES[i][1]), f);
      if (i < this.bodyParts.length - 1)
        f += (BODY_SIZES[i][2] + BODY_SIZES[i + 1][2]) * 0.5F; 
    } 
  }
  
  public void func_78088_a(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    func_78087_a(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
    for (ModelRenderer modelrenderer : this.bodyParts)
      modelrenderer.func_78785_a(scale); 
  }
  
  public void func_78087_a(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    EntityEndermite silverfish = (EntityEndermite)entityIn;
    for (int i = 0; i < this.bodyParts.length; i++) {
      (this.bodyParts[i]).field_78796_g = MathHelper.func_76134_b((silverfish.func_175446_cd() ? 1.0F : ageInTicks) * 0.9F + i * 0.15F * 3.1415927F) * 3.1415927F * 0.01F * (1 + Math.abs(i - 2));
      (this.bodyParts[i]).field_78800_c = MathHelper.func_76126_a((silverfish.func_175446_cd() ? 1.0F : ageInTicks) * 0.9F + i * 0.15F * 3.1415927F) * 3.1415927F * 0.1F * Math.abs(i - 2);
    } 
  }
}
