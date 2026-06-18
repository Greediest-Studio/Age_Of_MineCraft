package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class JointModelRenderer extends ModelRenderer {
  private final ModelRenderer model;
  
  public JointModelRenderer(ModelBase model, int x, int y) {
    super(model);
    this.model = new ModelRenderer(model, x, y);
    super.func_78792_a(this.model);
  }
  
  public ModelRenderer func_78784_a(int x, int y) {
    if (this.model != null)
      this.model.func_78784_a(x, y); 
    return this;
  }
  
  public ModelRenderer func_78787_b(int w, int h) {
    if (this.model != null)
      this.model.func_78787_b(w, h); 
    return this;
  }
  
  public void func_78792_a(ModelRenderer renderer) {
    this.model.func_78792_a(renderer);
  }
  
  public ModelRenderer func_78786_a(String partName, float offX, float offY, float offZ, int width, int height, int depth) {
    return this.model.func_78786_a(partName, offX, offY, offZ, width, height, depth);
  }
  
  public ModelRenderer func_78789_a(float offX, float offY, float offZ, int width, int height, int depth) {
    return this.model.func_78789_a(offX, offY, offZ, width, height, depth);
  }
  
  public ModelRenderer func_178769_a(float offX, float offY, float offZ, int width, int height, int depth, boolean mirrored) {
    return this.model.func_178769_a(offX, offY, offZ, width, height, depth, mirrored);
  }
  
  public void func_78790_a(float offX, float offY, float offZ, int width, int height, int depth, float scaleFactor) {
    this.model.func_78790_a(offX, offY, offZ, width, height, depth, scaleFactor);
  }
  
  public ModelRenderer getModel() {
    return this.model;
  }
}
