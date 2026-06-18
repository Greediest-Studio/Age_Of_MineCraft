package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityZombie;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelZombieVillager extends ModelBiped implements ICappedModel {
  public ModelRenderer bipedCape;
  
  public ModelZombieVillager() {
    this(0.0F, 0.0F, false);
  }
  
  public ModelZombieVillager(float p_i1165_1_, float p_i1165_2_, boolean p_i1165_3_) {
    super(p_i1165_1_, 0.0F, 64, p_i1165_3_ ? 32 : 64);
    if (p_i1165_3_) {
      this.field_78116_c = new ModelRenderer((ModelBase)this, 0, 0);
      this.field_78116_c.func_78790_a(-4.0F, -10.0F, -4.0F, 8, 8, 8, p_i1165_1_);
      this.field_78116_c.func_78793_a(0.0F, 0.0F + p_i1165_2_, 0.0F);
      this.field_78115_e = new ModelRenderer((ModelBase)this, 16, 16);
      this.field_78115_e.func_78793_a(0.0F, 0.0F + p_i1165_2_, 0.0F);
      this.field_78115_e.func_78790_a(-4.0F, 0.0F, -2.0F, 8, 12, 4, p_i1165_1_ + 0.1F);
      this.field_178721_j = new ModelRenderer((ModelBase)this, 0, 16);
      this.field_178721_j.func_78793_a(-2.0F, 12.0F + p_i1165_2_, 0.0F);
      this.field_178721_j.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i1165_1_ + 0.1F);
      this.field_178722_k = new ModelRenderer((ModelBase)this, 0, 16);
      this.field_178722_k.field_78809_i = true;
      this.field_178722_k.func_78793_a(2.0F, 12.0F + p_i1165_2_, 0.0F);
      this.field_178722_k.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i1165_1_ + 0.1F);
    } else {
      this.field_78116_c = new ModelRenderer((ModelBase)this, 0, 0);
      this.field_78116_c.func_78793_a(0.0F, p_i1165_2_, 0.0F);
      this.field_78116_c.func_78784_a(0, 0).func_78790_a(-4.0F, -10.0F, -4.0F, 8, 10, 8, p_i1165_1_);
      this.field_78116_c.func_78784_a(24, 0).func_78790_a(-1.0F, -3.0F, -6.0F, 2, 4, 2, p_i1165_1_);
      this.field_78115_e = new ModelRenderer((ModelBase)this, 16, 20);
      this.field_78115_e.func_78793_a(0.0F, 0.0F + p_i1165_2_, 0.0F);
      this.field_78115_e.func_78790_a(-4.0F, 0.0F, -3.0F, 8, 12, 6, p_i1165_1_);
      this.field_78115_e.func_78784_a(0, 38).func_78790_a(-4.0F, 0.0F, -3.0F, 8, 18, 6, p_i1165_1_ + 0.05F);
      this.field_178723_h = new ModelRenderer((ModelBase)this, 44, 38);
      this.field_178723_h.func_78790_a(-3.0F, -2.0F, -2.0F, 4, 12, 4, p_i1165_1_);
      this.field_178723_h.func_78793_a(-5.0F, 2.0F + p_i1165_2_, 0.0F);
      this.field_178724_i = new ModelRenderer((ModelBase)this, 44, 38);
      this.field_178724_i.field_78809_i = true;
      this.field_178724_i.func_78790_a(-1.0F, -2.0F, -2.0F, 4, 12, 4, p_i1165_1_);
      this.field_178724_i.func_78793_a(5.0F, 2.0F + p_i1165_2_, 0.0F);
      this.field_178721_j = new ModelRenderer((ModelBase)this, 0, 22);
      this.field_178721_j.func_78793_a(-2.0F, 12.0F + p_i1165_2_, 0.0F);
      this.field_178721_j.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i1165_1_);
      this.field_178722_k = new ModelRenderer((ModelBase)this, 0, 22);
      this.field_178722_k.field_78809_i = true;
      this.field_178722_k.func_78793_a(2.0F, 12.0F + p_i1165_2_, 0.0F);
      this.field_178722_k.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i1165_1_);
    } 
    this.bipedCape = new ModelRenderer((ModelBase)this, 0, 0);
    this.bipedCape.func_78787_b(64, 32);
    this.bipedCape.func_78790_a(-5.0F, 0.0F, -1.0F, 10, 16, 1, p_i1165_1_);
  }
  
  public void func_78088_a(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    func_78087_a(limbSwing, limbSwingAmount, ((EntityTameBase)entityIn).func_175446_cd() ? 0.0F : ageInTicks, netHeadYaw, headPitch, scale, entityIn);
    GlStateManager.func_179094_E();
    if (this.field_78091_s) {
      GlStateManager.func_179152_a(0.75F, 0.75F, 0.75F);
      GlStateManager.func_179109_b(0.0F, 16.0F * scale, 0.0F);
      this.field_78116_c.func_78785_a(scale);
      GlStateManager.func_179121_F();
      GlStateManager.func_179094_E();
      GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
      GlStateManager.func_179109_b(0.0F, 24.0F * scale, 0.0F);
      this.field_78115_e.func_78785_a(scale);
      this.field_178723_h.func_78785_a(scale);
      this.field_178724_i.func_78785_a(scale);
      this.field_178721_j.func_78785_a(scale);
      this.field_178722_k.func_78785_a(scale);
      this.field_178720_f.func_78785_a(scale);
    } else {
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
  
  public void renderCape(float scale, float flo1, float flo2, float flo3) {
    if (this.field_78091_s) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
      GlStateManager.func_179109_b(0.0F, 1.5F, -0.125F);
      GlStateManager.func_179114_b(6.0F + flo2 / 2.0F + flo1, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179114_b(flo3 / 2.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.func_179114_b(-flo3 / 2.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
      this.bipedCape.func_78785_a(scale);
      GlStateManager.func_179121_F();
    } else {
      GlStateManager.func_179094_E();
      GlStateManager.func_179114_b(6.0F + flo2 / 2.0F + flo1, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179114_b(flo3 / 2.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.func_179114_b(-flo3 / 2.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
      this.bipedCape.func_78785_a(scale);
      GlStateManager.func_179121_F();
    } 
  }
  
  public void func_78087_a(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    EntityZombie entity = (EntityZombie)entityIn;
    super.func_78087_a(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    float f = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F);
    float f1 = MathHelper.func_76126_a((1.0F - (1.0F - this.field_78095_p) * (1.0F - this.field_78095_p)) * 3.1415927F);
    this.field_178723_h.field_78808_h = 0.0F;
    this.field_178724_i.field_78808_h = 0.0F;
    this.field_178723_h.field_78796_g = -(0.1F - f * 0.6F);
    this.field_178724_i.field_78796_g = 0.1F - f * 0.6F;
    float f2 = entity.isArmsRaised() ? -1.5F : -0.75F;
    this.field_178723_h.field_78795_f = this.field_78116_c.field_78795_f + f2 - 0.5F;
    this.field_178724_i.field_78795_f = this.field_78116_c.field_78795_f + f2 - 0.5F;
    this.field_178723_h.field_78795_f += f * 1.2F - f1 * 0.4F;
    this.field_178724_i.field_78795_f += f * 1.2F - f1 * 0.4F;
    this.field_178723_h.field_78808_h += MathHelper.func_76134_b(ageInTicks * 0.09F) * 0.05F + 0.05F;
    this.field_178724_i.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.09F) * 0.05F + 0.05F;
    this.field_178723_h.field_78795_f += MathHelper.func_76126_a(ageInTicks * 0.067F) * 0.05F;
    this.field_178724_i.field_78795_f -= MathHelper.func_76126_a(ageInTicks * 0.067F) * 0.05F;
    this.field_78091_s = entity.func_70631_g_();
    this.field_78117_n = entity.func_70093_af();
    if (entity.func_70027_ad() || (!entity.func_70089_S() && !entity.field_70122_E)) {
      this.field_78116_c.field_78795_f -= 0.5F;
      this.field_78116_c.field_78796_g += MathHelper.func_76134_b(ageInTicks * 0.6662F) * 0.5F;
      this.field_178723_h.field_78798_e = 0.0F;
      this.field_178723_h.field_78800_c = -5.0F;
      this.field_178724_i.field_78798_e = 0.0F;
      this.field_178724_i.field_78800_c = 5.0F;
      this.field_178723_h.field_78795_f = -MathHelper.func_76134_b(ageInTicks * 0.6662F) * 0.5F;
      this.field_178724_i.field_78795_f = MathHelper.func_76134_b(ageInTicks * 0.6662F) * 0.5F;
      this.field_178723_h.field_78808_h = 2.3561945F;
      this.field_178724_i.field_78808_h = -2.3561945F;
      this.field_178723_h.field_78796_g = 0.0F;
      this.field_178724_i.field_78796_g = 0.0F;
    } 
    this.field_78116_c.field_78795_f += MathHelper.func_76134_b(ageInTicks * 0.1F) * 0.0125F * 3.1415927F;
    if (!entity.getCurrentBook().func_190926_b()) {
      this.field_178723_h.field_78796_g = entity.bookSpread - 1.0F;
      this.field_178724_i.field_78796_g = -entity.bookSpread + 1.0F;
      this.field_178723_h.field_78808_h = 0.0F;
      this.field_178724_i.field_78808_h = 0.0F;
      this.field_178723_h.field_78795_f = -1.5F + 0.1F + MathHelper.func_76126_a(entity.field_70173_aa * 0.1F) * 0.01F;
      this.field_178724_i.field_78795_f = -1.5F + 0.1F + MathHelper.func_76126_a(entity.field_70173_aa * 0.1F) * 0.01F;
    } 
  }
}
