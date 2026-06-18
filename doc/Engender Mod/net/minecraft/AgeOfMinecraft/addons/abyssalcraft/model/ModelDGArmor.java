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
    this.chestplate.func_78787_b(128, 64);
    this.chestplate.func_78790_a(-6.0F, -7.5F, -3.0F, 12, 15, 6, f);
    this.chestplate.func_78793_a(0.0F, -3.0F, 3.0F);
  }
  
  public void func_78088_a(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
    super.func_78088_a(par1Entity, par2, par3, par4, par5, par6, par7);
    if (this.field_78091_s) {
      float f6 = 2.0F;
      GlStateManager.func_179094_E();
      GlStateManager.func_179152_a(1.0F / f6, 1.0F / f6, 1.0F / f6);
      GlStateManager.func_179109_b(0.0F, 24.0F * par7, 0.0F);
      this.chestplate.func_78785_a(par7);
      GlStateManager.func_179121_F();
    } else {
      this.chestplate.func_78785_a(par7);
    } 
  }
  
  public void setInvisible(boolean invisible) {
    super.setInvisible(invisible);
    this.chestplate.field_78806_j = invisible;
  }
}
