package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityEnderman;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelEnderman extends ModelBiped implements ICappedModel {
  public boolean isCarrying;
  
  public boolean isAttacking;
  
  public ModelRenderer bipedCape;
  
  public ModelEnderman() {
    this(0.0F);
  }
  
  public ModelEnderman(float scale) {
    super(0.0F, -14.0F, 64, 32);
    float f = -14.0F;
    this.field_178720_f = new ModelRenderer((ModelBase)this, 0, 16);
    this.field_178720_f.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, scale - 0.5F);
    this.field_178720_f.func_78793_a(0.0F, 0.0F + f, 0.0F);
    this.field_78115_e = new ModelRenderer((ModelBase)this, 32, 16);
    this.field_78115_e.func_78790_a(-4.0F, 0.0F, -2.0F, 8, 12, 4, scale);
    this.field_78115_e.func_78793_a(0.0F, 0.0F + f, 0.0F);
    this.field_178723_h = new ModelRenderer((ModelBase)this, 56, 0);
    this.field_178723_h.func_78790_a(-1.0F, -2.0F, -1.0F, 2, 30, 2, scale);
    this.field_178723_h.func_78793_a(-3.0F, 2.0F + f, 0.0F);
    this.field_178724_i = new ModelRenderer((ModelBase)this, 56, 0);
    this.field_178724_i.field_78809_i = true;
    this.field_178724_i.func_78790_a(-1.0F, -2.0F, -1.0F, 2, 30, 2, scale);
    this.field_178724_i.func_78793_a(5.0F, 2.0F + f, 0.0F);
    this.field_178721_j = new ModelRenderer((ModelBase)this, 56, 0);
    this.field_178721_j.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 30, 2, scale);
    this.field_178721_j.func_78793_a(-2.0F, 12.0F + f, 0.0F);
    this.field_178722_k = new ModelRenderer((ModelBase)this, 56, 0);
    this.field_178722_k.field_78809_i = true;
    this.field_178722_k.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 30, 2, scale);
    this.field_178722_k.func_78793_a(2.0F, 12.0F + f, 0.0F);
    this.bipedCape = new ModelRenderer((ModelBase)this, 0, 0);
    this.bipedCape.func_78787_b(64, 32);
    this.bipedCape.func_78790_a(-5.0F, 0.0F, -1.0F, 10, 16, 1, scale);
  }
  
  public void renderCape(float scale, float flo1, float flo2, float flo3) {
    if (this.field_78091_s) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b(0.0F, -0.875F, 0.0F);
      GlStateManager.func_179114_b(6.0F + flo2 / 2.0F + flo1, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179114_b(flo3 / 2.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.func_179114_b(-flo3 / 2.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
      this.bipedCape.func_78785_a(scale);
      GlStateManager.func_179121_F();
    } else {
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b(0.0F, -0.875F, 0.0F);
      GlStateManager.func_179114_b(6.0F + flo2 / 2.0F + flo1, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179114_b(flo3 / 2.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.func_179114_b(-flo3 / 2.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
      this.bipedCape.func_78785_a(scale);
      GlStateManager.func_179121_F();
    } 
  }
  
  public void func_78088_a(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    func_78087_a(limbSwing, limbSwingAmount, ((EntityTameBase)entityIn).func_175446_cd() ? 0.0F : ageInTicks, netHeadYaw, headPitch, scale, entityIn);
    GlStateManager.func_179094_E();
    if (this.field_78091_s) {
      GlStateManager.func_179152_a(1.5F, 1.5F, 1.5F);
      GlStateManager.func_179109_b(0.0F, 5.0F * scale, 0.0F);
      this.field_78116_c.func_78785_a(scale);
      this.field_178720_f.func_78785_a(scale);
      GlStateManager.func_179121_F();
      GlStateManager.func_179094_E();
      GlStateManager.func_179152_a(1.0F, 1.0F, 1.0F);
      this.field_78115_e.func_78785_a(scale);
      this.field_178723_h.func_78785_a(scale);
      this.field_178724_i.func_78785_a(scale);
      this.field_178721_j.func_78785_a(scale);
      this.field_178722_k.func_78785_a(scale);
    } else {
      if (entityIn.func_70093_af())
        GlStateManager.func_179109_b(0.0F, 0.2F, 0.0F); 
      this.field_78116_c.func_78785_a(scale);
      this.field_78115_e.func_78785_a(scale);
      this.field_178723_h.func_78785_a(scale);
      this.field_178724_i.func_78785_a(scale);
      this.field_178721_j.func_78785_a(scale);
      this.field_178722_k.func_78785_a(scale);
      this.field_178720_f.func_78785_a(scale);
    } 
    GlStateManager.func_179121_F();
  }
  
  public void func_78087_a(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    super.func_78087_a(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    this.field_78116_c.field_78806_j = true;
    float f = -14.0F;
    this.field_78115_e.field_78795_f = 0.0F;
    this.field_78115_e.field_78797_d = f;
    this.field_78115_e.field_78798_e = -0.0F;
    this.field_178721_j.field_78795_f -= 0.0F;
    this.field_178722_k.field_78795_f -= 0.0F;
    this.field_178723_h.field_78795_f = (float)(this.field_178723_h.field_78795_f * 0.5D);
    this.field_178724_i.field_78795_f = (float)(this.field_178724_i.field_78795_f * 0.5D);
    this.field_178721_j.field_78795_f = (float)(this.field_178721_j.field_78795_f * 0.5D);
    this.field_178722_k.field_78795_f = (float)(this.field_178722_k.field_78795_f * 0.5D);
    float f1 = 0.4F;
    if (this.field_178723_h.field_78795_f > f1)
      this.field_178723_h.field_78795_f = f1; 
    if (this.field_178724_i.field_78795_f > f1)
      this.field_178724_i.field_78795_f = f1; 
    if (this.field_178723_h.field_78795_f < -f1)
      this.field_178723_h.field_78795_f = -f1; 
    if (this.field_178724_i.field_78795_f < -f1)
      this.field_178724_i.field_78795_f = -f1; 
    if (this.field_178721_j.field_78795_f > f1)
      this.field_178721_j.field_78795_f = f1; 
    if (this.field_178722_k.field_78795_f > f1)
      this.field_178722_k.field_78795_f = f1; 
    if (this.field_178721_j.field_78795_f < -f1)
      this.field_178721_j.field_78795_f = -f1; 
    if (this.field_178722_k.field_78795_f < -f1)
      this.field_178722_k.field_78795_f = -f1; 
    if (this.isCarrying) {
      this.field_178723_h.field_78795_f = -0.5F;
      this.field_178724_i.field_78795_f = -0.5F;
      this.field_178723_h.field_78808_h = 0.05F;
      this.field_178724_i.field_78808_h = -0.05F;
    } 
    this.field_178723_h.field_78798_e = 0.0F;
    this.field_178724_i.field_78798_e = 0.0F;
    this.field_178721_j.field_78798_e = 0.0F;
    this.field_178722_k.field_78798_e = 0.0F;
    this.field_178721_j.field_78797_d = 9.0F + f;
    this.field_178722_k.field_78797_d = 9.0F + f;
    this.field_78116_c.field_78798_e = -0.0F;
    this.field_78116_c.field_78797_d = f + 1.0F;
    this.field_178720_f.field_78800_c = this.field_78116_c.field_78800_c;
    this.field_178720_f.field_78797_d = this.field_78116_c.field_78797_d;
    this.field_178720_f.field_78798_e = this.field_78116_c.field_78798_e;
    this.field_178720_f.field_78795_f = this.field_78116_c.field_78795_f;
    this.field_178720_f.field_78796_g = this.field_78116_c.field_78796_g;
    this.field_178720_f.field_78808_h = this.field_78116_c.field_78808_h;
    if (this.field_78117_n) {
      this.field_78115_e.field_78795_f = 0.5F;
      if (this.isCarrying) {
        this.field_178723_h.field_78795_f = -0.85F;
        this.field_178724_i.field_78795_f = -0.85F;
        this.field_178723_h.field_78808_h = 0.05F;
        this.field_178724_i.field_78808_h = -0.05F;
      } 
      this.field_178723_h.field_78798_e = 6.0F;
      this.field_178724_i.field_78798_e = 6.0F;
      this.field_78115_e.field_78798_e = 6.0F;
      this.field_78116_c.field_78798_e = 6.0F;
      this.field_178720_f.field_78798_e = 6.0F;
      this.field_178721_j.field_78798_e = 12.0F;
      this.field_178722_k.field_78798_e = 12.0F;
      this.field_178721_j.field_78797_d = -3.0F;
      this.field_178722_k.field_78797_d = -3.0F;
      this.field_178723_h.field_78797_d = -10.0F;
      this.field_178724_i.field_78797_d = -10.0F;
      this.field_78115_e.field_78797_d = -12.0F;
      this.field_78116_c.field_78797_d = -11.0F;
      this.field_178720_f.field_78797_d = -11.0F;
    } else {
      this.field_78115_e.field_78795_f = 0.0F;
      this.field_178721_j.field_78798_e = 0.0F;
      this.field_178722_k.field_78798_e = 0.0F;
      this.field_178721_j.field_78797_d = -5.0F;
      this.field_178722_k.field_78797_d = -5.0F;
      this.field_178723_h.field_78797_d = -12.0F;
      this.field_178724_i.field_78797_d = -12.0F;
      this.field_78115_e.field_78797_d = -14.0F;
      this.field_78116_c.field_78797_d = -13.0F;
      this.field_178720_f.field_78797_d = -13.0F;
    } 
    EntityEnderman entity = (EntityEnderman)entityIn;
    this.isAttacking = entity.isArmsRaised();
    this.isCarrying = (entity.getHeldBlockState() != null || entity.func_184179_bs() != null);
    this.field_78091_s = entity.func_70631_g_();
    this.field_78117_n = entity.func_70093_af();
    if (this.isAttacking) {
      float f2 = 1.0F;
      this.field_78116_c.field_78797_d -= f2 * 5.0F;
    } 
    if (entity.getJukeboxToDanceTo() != null) {
      float fl = MathHelper.func_76126_a(ageInTicks * 0.5F) * 4.0F;
      this.field_78116_c.func_78793_a(0.0F, -16.0F - fl, 0.0F);
    } 
    if (entity.func_70027_ad() || entity.func_70026_G() || (!entity.func_70089_S() && !entity.field_70122_E)) {
      this.field_78116_c.field_78795_f -= 0.5F;
      this.field_78116_c.field_78796_g += MathHelper.func_76134_b(ageInTicks * 0.6662F) * 0.5F;
      this.field_178720_f.field_78795_f -= 0.5F;
      this.field_178720_f.field_78796_g += MathHelper.func_76134_b(ageInTicks * 0.6662F) * 0.5F;
      this.field_178723_h.field_78798_e = 0.0F;
      this.field_178723_h.field_78800_c = -5.0F;
      this.field_178724_i.field_78798_e = 0.0F;
      this.field_178724_i.field_78800_c = 5.0F;
      this.field_178721_j.field_78795_f = -MathHelper.func_76134_b(ageInTicks * 0.6662F);
      this.field_178722_k.field_78795_f = MathHelper.func_76134_b(ageInTicks * 0.6662F);
      this.field_178723_h.field_78795_f = -MathHelper.func_76134_b(ageInTicks * 0.6662F);
      this.field_178724_i.field_78795_f = MathHelper.func_76134_b(ageInTicks * 0.6662F);
      this.field_178723_h.field_78808_h = 2.3561945F;
      this.field_178724_i.field_78808_h = -2.3561945F;
      this.field_178723_h.field_78796_g = 0.0F;
      this.field_178724_i.field_78796_g = 0.0F;
    } 
    if (!entity.getCurrentBook().func_190926_b()) {
      this.field_178723_h.field_78796_g = (entity.bookSpread - 1.0F) * 0.25F;
      this.field_178724_i.field_78796_g = (-entity.bookSpread + 1.0F) * 0.25F;
      this.field_178723_h.field_78808_h = 0.0F;
      this.field_178724_i.field_78808_h = 0.0F;
      this.field_178723_h.field_78795_f = -1.25F + 0.1F + MathHelper.func_76126_a(entity.field_70173_aa * 0.1F) * 0.01F;
      this.field_178724_i.field_78795_f = -1.25F + 0.1F + MathHelper.func_76126_a(entity.field_70173_aa * 0.1F) * 0.01F;
    } 
  }
}
