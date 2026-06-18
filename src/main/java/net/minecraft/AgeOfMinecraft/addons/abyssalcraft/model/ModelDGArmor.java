package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelDGArmor extends ModelDG {
  public ModelRenderer chestplate;
  
  public ModelDGArmor() {
    this(0.0F);
  }
  
  public ModelDGArmor(float f) {
    super(f);
    this.chestplate = new ModelRenderer(this, 0, 18);
    this.chestplate.setTextureSize(128, 64);
    this.chestplate.addBox(-6.0F, -7.5F, -3.0F, 12, 15, 6, f);
    this.chestplate.setRotationPoint(0.0F, -3.0F, 3.0F);
  }
  
  public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
    super.render(par1Entity, par2, par3, par4, par5, par6, par7);
    if (this.isChild) {
      float f6 = 2.0F;
      GlStateManager.pushMatrix();
      GlStateManager.scale(1.0F / f6, 1.0F / f6, 1.0F / f6);
      GlStateManager.translate(0.0F, 24.0F * par7, 0.0F);
      this.chestplate.render(par7);
      GlStateManager.popMatrix();
    } else {
      this.chestplate.render(par7);
    } 
  }
  
  public void setInvisible(boolean invisible) {
    super.setInvisible(invisible);
    this.chestplate.showModel = invisible;
  }
}
