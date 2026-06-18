package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTractorBeam extends ModelBase {
  public ModelRenderer shape1;
  
  public ModelTractorBeam() {
    this.field_78090_t = 32;
    this.field_78089_u = 256;
    this.shape1 = new ModelRenderer(this, 0, -8);
    this.shape1.func_78793_a(0.0F, 0.0F, 0.0F);
    this.shape1.func_78790_a(-4.0F, -256.0F, -4.0F, 8, 256, 8, 0.0F);
  }
  
  public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    this.shape1.func_78785_a(f5);
  }
  
  public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
    modelRenderer.field_78795_f = x;
    modelRenderer.field_78796_g = y;
    modelRenderer.field_78808_h = z;
  }
}
