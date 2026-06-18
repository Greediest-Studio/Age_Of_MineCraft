package net.minecraft.AgeOfMinecraft.models;

import java.util.Random;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelCommandBlockWitherBody extends ModelBase {
  public ModelRenderer Body1;
  
  public ModelRenderer Body2;
  
  public ModelRenderer Body3;
  
  public ModelRenderer Body4;
  
  public ModelRenderer Body5;
  
  public ModelRenderer Body6;
  
  public ModelRenderer Body7;
  
  public ModelRenderer Body8;
  
  public ModelRenderer Body9;
  
  public ModelRenderer Body10;
  
  public ModelCommandBlockWitherBody() {
    Random random = new Random(1660L);
    this.field_78090_t = 64;
    this.field_78089_u = 32;
    this.Body3 = new ModelRenderer(this, random.nextInt(16), random.nextInt(16));
    this.Body3.func_78793_a(0.0F, 16.0F, 0.0F);
    this.Body3.func_78790_a(0.0F, -4.0F, -8.0F, 8, 8, 8, 0.0F);
    this.Body8 = new ModelRenderer(this, random.nextInt(16), random.nextInt(16));
    this.Body8.func_78793_a(0.0F, 16.0F, 0.0F);
    this.Body8.func_78790_a(4.0F, 2.0F, 0.0F, 8, 8, 8, 0.0F);
    this.Body9 = new ModelRenderer(this, random.nextInt(16), random.nextInt(16));
    this.Body9.func_78793_a(0.0F, 16.0F, 0.0F);
    this.Body9.func_78790_a(6.0F, 2.0F, -4.0F, 8, 8, 8, 0.0F);
    this.Body7 = new ModelRenderer(this, random.nextInt(16), random.nextInt(16));
    this.Body7.func_78793_a(0.0F, 16.0F, 0.0F);
    this.Body7.func_78790_a(-4.0F, -7.0F, -2.0F, 8, 8, 8, 0.0F);
    this.Body10 = new ModelRenderer(this, random.nextInt(16), random.nextInt(16));
    this.Body10.func_78793_a(0.0F, 16.0F, 0.0F);
    this.Body10.func_78790_a(-4.0F, 8.0F, -4.0F, 8, 8, 8, 0.0F);
    this.Body6 = new ModelRenderer(this, random.nextInt(16), random.nextInt(16));
    this.Body6.func_78793_a(0.0F, 16.0F, 0.0F);
    this.Body6.func_78790_a(2.0F, 4.0F, -4.0F, 8, 8, 8, 0.0F);
    this.Body5 = new ModelRenderer(this, random.nextInt(16), random.nextInt(16));
    this.Body5.func_78793_a(0.0F, 16.0F, 0.0F);
    this.Body5.func_78790_a(-10.0F, 4.0F, -4.0F, 8, 8, 8, 0.0F);
    this.Body4 = new ModelRenderer(this, random.nextInt(16), random.nextInt(16));
    this.Body4.func_78793_a(0.0F, 16.0F, 0.0F);
    this.Body4.func_78790_a(-8.0F, -2.0F, 0.0F, 8, 8, 8, 0.0F);
    this.Body1 = new ModelRenderer(this, random.nextInt(16), random.nextInt(16));
    this.Body1.func_78793_a(0.0F, 16.0F, 0.0F);
    this.Body1.func_78790_a(-2.0F, 0.0F, -2.0F, 8, 8, 8, 0.0F);
    this.Body2 = new ModelRenderer(this, random.nextInt(16), random.nextInt(16));
    this.Body2.func_78793_a(0.0F, 16.0F, 0.0F);
    this.Body2.func_78790_a(-12.0F, 1.0F, -4.0F, 8, 8, 8, 0.0F);
  }
  
  public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    GlStateManager.func_179094_E();
    GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 0.0F);
    GlStateManager.func_179152_a(1.51F, 1.51F, 1.51F);
    GlStateManager.func_179109_b(0.0F, 1.0F, -0.45F);
    GlStateManager.func_179114_b(180.0F, 1.0F, 0.0F, 0.0F);
    GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
    this.Body3.func_78785_a(f5);
    this.Body8.func_78785_a(f5);
    this.Body9.func_78785_a(f5);
    this.Body7.func_78785_a(f5);
    this.Body10.func_78785_a(f5);
    this.Body6.func_78785_a(f5);
    this.Body5.func_78785_a(f5);
    this.Body4.func_78785_a(f5);
    this.Body1.func_78785_a(f5);
    this.Body2.func_78785_a(f5);
    GlStateManager.func_179121_F();
  }
}
