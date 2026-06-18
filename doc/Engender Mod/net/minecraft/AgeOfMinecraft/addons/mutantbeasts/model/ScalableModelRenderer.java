package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ScalableModelRenderer extends ModelRenderer {
  private float scale = 1.0F;
  
  public ScalableModelRenderer(ModelBase model, int texOffX, int texOffY) {
    super(model, texOffX, texOffY);
  }
  
  public void setScale(float scale) {
    this.scale = scale;
  }
  
  public void func_78785_a(float scale) {
    if (!this.field_78807_k && this.field_78806_j) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179152_a(this.scale, this.scale, this.scale);
      super.func_78785_a(scale);
      GlStateManager.func_179121_F();
    } 
  }
}
