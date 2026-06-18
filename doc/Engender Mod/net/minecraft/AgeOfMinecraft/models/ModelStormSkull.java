package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelStormSkull extends ModelBase {
  public ModelRenderer Skull;
  
  public ModelStormSkull() {
    this.field_78090_t = 64;
    this.field_78089_u = 32;
    this.Skull = new ModelRenderer(this, 0, 0);
    this.Skull.func_78793_a(0.0F, 16.0F, 0.0F);
    this.Skull.func_78790_a(-8.0F, -8.0F, -8.0F, 16, 16, 16, 0.0F);
  }
  
  public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    GlStateManager.func_179094_E();
    GlStateManager.func_179152_a(1.0F, -1.0F, 1.0F);
    GlStateManager.func_179109_b(0.0F, -2.0F, 0.0F);
    this.Skull.func_78785_a(f5);
    GlStateManager.func_179121_F();
  }
  
  public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
    modelRenderer.field_78795_f = x;
    modelRenderer.field_78796_g = y;
    modelRenderer.field_78808_h = z;
  }
  
  public void func_78087_a(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    super.func_78087_a(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    this.Skull.field_78796_g = netHeadYaw * 0.017453292F;
    this.Skull.field_78795_f = headPitch * 0.017453292F;
  }
}
