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
      this.silverfishBodyParts[i].func_78789_a(SILVERFISH_BOX_LENGTH[i][0] * -0.5F, 0.0F, SILVERFISH_BOX_LENGTH[i][2] * -0.5F, SILVERFISH_BOX_LENGTH[i][0], SILVERFISH_BOX_LENGTH[i][1], SILVERFISH_BOX_LENGTH[i][2]);
      this.silverfishBodyParts[i].func_78793_a(0.0F, (24 - SILVERFISH_BOX_LENGTH[i][1]), f);
      this.zPlacement[i] = f;
      if (i < this.silverfishBodyParts.length - 1)
        f += (SILVERFISH_BOX_LENGTH[i][2] + SILVERFISH_BOX_LENGTH[i + 1][2]) * 0.5F; 
    } 
    this.silverfishWings = new ModelRenderer[3];
    this.silverfishWings[0] = new ModelRenderer(this, 20, 0);
    this.silverfishWings[0].func_78789_a(-5.0F, 0.0F, SILVERFISH_BOX_LENGTH[2][2] * -0.5F, 10, 8, SILVERFISH_BOX_LENGTH[2][2]);
    this.silverfishWings[0].func_78793_a(0.0F, 16.0F, this.zPlacement[2]);
    this.silverfishWings[1] = new ModelRenderer(this, 20, 11);
    this.silverfishWings[1].func_78789_a(-3.0F, 0.0F, SILVERFISH_BOX_LENGTH[4][2] * -0.5F, 6, 4, SILVERFISH_BOX_LENGTH[4][2]);
    this.silverfishWings[1].func_78793_a(0.0F, 20.0F, this.zPlacement[4]);
    this.silverfishWings[2] = new ModelRenderer(this, 20, 18);
    this.silverfishWings[2].func_78789_a(-3.0F, 0.0F, SILVERFISH_BOX_LENGTH[4][2] * -0.5F, 6, 5, SILVERFISH_BOX_LENGTH[1][2]);
    this.silverfishWings[2].func_78793_a(0.0F, 19.0F, this.zPlacement[1]);
  }
  
  public void func_78088_a(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    func_78087_a(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
    for (ModelRenderer modelrenderer : this.silverfishBodyParts)
      modelrenderer.func_78785_a(scale); 
    for (ModelRenderer modelrenderer1 : this.silverfishWings)
      modelrenderer1.func_78785_a(scale); 
  }
  
  public void func_78087_a(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
    EntitySilverfish silverfish = (EntitySilverfish)entity;
    int i;
    for (i = 0; i < this.silverfishBodyParts.length; i++) {
      (this.silverfishBodyParts[i]).field_78796_g = MathHelper.func_76134_b((silverfish.func_175446_cd() ? 1.0F : ageInTicks) * 0.9F + i * 0.15F * 3.1415927F) * 3.1415927F * (silverfish.func_70093_af() ? 0.0125F : 0.05F) * (1 + Math.abs(i - 2));
      (this.silverfishBodyParts[i]).field_78800_c = MathHelper.func_76126_a((silverfish.func_175446_cd() ? 1.0F : ageInTicks) * 0.9F + i * 0.15F * 3.1415927F) * 3.1415927F * (silverfish.func_70093_af() ? 0.05F : 0.2F) * Math.abs(i - 2);
    } 
    if (silverfish.getJukeboxToDanceTo() != null)
      for (i = 0; i < this.silverfishBodyParts.length; i++) {
        (this.silverfishBodyParts[i]).field_78796_g = MathHelper.func_76134_b((silverfish.func_175446_cd() ? 1.0F : ageInTicks) * 0.45F + i * 0.125F * 3.1415927F) * 3.1415927F * 0.1F * (1 + Math.abs(i - 2));
        (this.silverfishBodyParts[i]).field_78800_c = MathHelper.func_76126_a((silverfish.func_175446_cd() ? 1.0F : ageInTicks) * 0.45F + i * 0.125F * 3.1415927F) * 3.1415927F * 0.2F * Math.abs(i - 2);
      }  
    (this.silverfishBodyParts[0]).field_78796_g += netHeadYaw / 57.295776F;
    (this.silverfishBodyParts[0]).field_78795_f = headPitch / 57.295776F;
    (this.silverfishWings[0]).field_78796_g = (this.silverfishBodyParts[2]).field_78796_g;
    (this.silverfishWings[1]).field_78796_g = (this.silverfishBodyParts[4]).field_78796_g;
    (this.silverfishWings[1]).field_78800_c = (this.silverfishBodyParts[4]).field_78800_c;
    (this.silverfishWings[2]).field_78796_g = (this.silverfishBodyParts[1]).field_78796_g;
    (this.silverfishWings[2]).field_78800_c = (this.silverfishBodyParts[1]).field_78800_c;
  }
}
