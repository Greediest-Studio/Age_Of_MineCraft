package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelSlime extends ModelBase {
  public ModelRenderer slimeGel;
  
  public ModelRenderer slimeBodies;
  
  public ModelRenderer slimeRightEye;
  
  public ModelRenderer slimeLeftEye;
  
  public ModelRenderer slimeMouth;
  
  public ModelSlime(int p_i1157_1_) {
    if (p_i1157_1_ > 0) {
      this.slimeBodies = new ModelRenderer(this, 0, p_i1157_1_);
      this.slimeBodies.func_78789_a(-3.0F, 17.0F, -3.0F, 6, 6, 6);
      this.slimeRightEye = new ModelRenderer(this, 32, 0);
      this.slimeRightEye.func_78789_a(-3.25F, 18.0F, -3.5F, 2, 2, 2);
      this.slimeLeftEye = new ModelRenderer(this, 32, 4);
      this.slimeLeftEye.func_78789_a(1.25F, 18.0F, -3.5F, 2, 2, 2);
      this.slimeMouth = new ModelRenderer(this, 32, 8);
      this.slimeMouth.func_78789_a(0.0F, 21.0F, -3.5F, 1, 1, 1);
    } else {
      this.slimeBodies = new ModelRenderer(this, 0, p_i1157_1_);
      this.slimeBodies.func_78789_a(-4.0F, 16.0F, -4.0F, 8, 8, 8);
    } 
  }
  
  public void func_78088_a(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    func_78087_a(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
    this.slimeBodies.func_78785_a(scale);
    if (this.slimeRightEye != null) {
      this.slimeRightEye.func_78785_a(scale);
      this.slimeLeftEye.func_78785_a(scale);
      this.slimeMouth.func_78785_a(scale);
    } 
  }
}
