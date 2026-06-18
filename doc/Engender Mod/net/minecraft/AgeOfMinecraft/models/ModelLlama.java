package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelLlama extends ModelQuadruped {
  public ModelLlama(float p_i47226_1_) {
    super(15, p_i47226_1_);
    this.field_78090_t = 128;
    this.field_78089_u = 64;
    this.field_78150_a = new ModelRenderer((ModelBase)this, 0, 0);
    this.field_78150_a.func_78790_a(-2.0F, -14.0F, -10.0F, 4, 4, 9, p_i47226_1_);
    this.field_78150_a.func_78793_a(0.0F, 7.0F, -6.0F);
    this.field_78150_a.func_78784_a(0, 14).func_78790_a(-4.0F, -16.0F, -6.0F, 8, 18, 6, p_i47226_1_);
    this.field_78150_a.func_78784_a(17, 0).func_78790_a(-4.0F, -19.0F, -4.0F, 3, 3, 2, p_i47226_1_);
    this.field_78150_a.func_78784_a(17, 0).func_78790_a(1.0F, -19.0F, -4.0F, 3, 3, 2, p_i47226_1_);
    this.field_78148_b = new ModelRenderer((ModelBase)this, 29, 0);
    this.field_78148_b.func_78790_a(-6.0F, -10.0F, -7.0F, 12, 18, 10, p_i47226_1_);
    this.field_78148_b.func_78793_a(0.0F, 5.0F, 2.0F);
    this.field_78149_c = new ModelRenderer((ModelBase)this, 29, 29);
    this.field_78149_c.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 14, 4, p_i47226_1_);
    this.field_78149_c.func_78793_a(-2.5F, 10.0F, 6.0F);
    this.field_78146_d = new ModelRenderer((ModelBase)this, 29, 29);
    this.field_78146_d.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 14, 4, p_i47226_1_);
    this.field_78146_d.func_78793_a(2.5F, 10.0F, 6.0F);
    this.field_78147_e = new ModelRenderer((ModelBase)this, 29, 29);
    this.field_78147_e.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 14, 4, p_i47226_1_);
    this.field_78147_e.func_78793_a(-2.5F, 10.0F, -4.0F);
    this.field_78144_f = new ModelRenderer((ModelBase)this, 29, 29);
    this.field_78144_f.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 14, 4, p_i47226_1_);
    this.field_78144_f.func_78793_a(2.5F, 10.0F, -4.0F);
    this.field_78149_c.field_78800_c--;
    this.field_78146_d.field_78800_c++;
    this.field_78149_c.field_78798_e += 0.0F;
    this.field_78146_d.field_78798_e += 0.0F;
    this.field_78147_e.field_78800_c--;
    this.field_78144_f.field_78800_c++;
    this.field_78147_e.field_78798_e--;
    this.field_78144_f.field_78798_e--;
    this.field_78151_h += 2.0F;
  }
  
  public void func_78088_a(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    func_78087_a(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
    if (this.field_78091_s) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b(0.0F, this.field_78145_g * scale, this.field_78151_h * scale);
      GlStateManager.func_179121_F();
      GlStateManager.func_179094_E();
      GlStateManager.func_179152_a(0.71428573F, 0.64935064F, 0.7936508F);
      GlStateManager.func_179109_b(0.0F, 21.0F * scale, 0.22F);
      this.field_78150_a.func_78785_a(scale);
      GlStateManager.func_179121_F();
      GlStateManager.func_179094_E();
      GlStateManager.func_179152_a(0.625F, 0.45454544F, 0.45454544F);
      GlStateManager.func_179109_b(0.0F, 33.0F * scale, 0.0F);
      this.field_78148_b.func_78785_a(scale);
      GlStateManager.func_179121_F();
      GlStateManager.func_179094_E();
      GlStateManager.func_179152_a(0.45454544F, 0.41322312F, 0.45454544F);
      GlStateManager.func_179109_b(0.0F, 33.0F * scale, 0.0F);
      this.field_78149_c.func_78785_a(scale);
      this.field_78146_d.func_78785_a(scale);
      this.field_78147_e.func_78785_a(scale);
      this.field_78144_f.func_78785_a(scale);
      GlStateManager.func_179121_F();
    } else {
      this.field_78150_a.func_78785_a(scale);
      this.field_78148_b.func_78785_a(scale);
      this.field_78149_c.func_78785_a(scale);
      this.field_78146_d.func_78785_a(scale);
      this.field_78147_e.func_78785_a(scale);
      this.field_78144_f.func_78785_a(scale);
    } 
  }
}
