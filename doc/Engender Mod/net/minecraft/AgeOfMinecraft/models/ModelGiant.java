package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityGiant;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelGiant extends ModelBiped implements ICappedModel {
  public ModelRenderer bipedCape;
  
  public ModelGiant() {
    this(0.0F, false);
  }
  
  public ModelGiant(float modelSize, boolean p_i1168_2_) {
    super(modelSize, 0.0F, 64, p_i1168_2_ ? 32 : 64);
    this.bipedCape = new ModelRenderer((ModelBase)this, 0, 0);
    this.bipedCape.func_78787_b(64, 32);
    this.bipedCape.func_78790_a(-5.0F, 0.0F, -1.0F, 10, 16, 1, modelSize);
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
    super.func_78087_a(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    float f = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F);
    float f1 = MathHelper.func_76126_a((1.0F - (1.0F - this.field_78095_p) * (1.0F - this.field_78095_p)) * 3.1415927F);
    this.field_178723_h.field_78808_h = 0.0F;
    this.field_178724_i.field_78808_h = 0.0F;
    this.field_178723_h.field_78796_g = -(f * 0.6F);
    this.field_178724_i.field_78796_g = f * 0.6F;
    float f2 = -1.5707964F;
    this.field_178723_h.field_78795_f = f2;
    this.field_178724_i.field_78795_f = f2;
    this.field_178723_h.field_78795_f += -f * 1.2F - f1 * 0.4F;
    this.field_178724_i.field_78795_f += -f * 1.2F - f1 * 0.4F;
    this.field_178723_h.field_78808_h += MathHelper.func_76134_b(ageInTicks * 0.09F) * 0.05F + 0.05F;
    this.field_178724_i.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.09F) * 0.05F + 0.05F;
    this.field_178723_h.field_78795_f += MathHelper.func_76126_a(ageInTicks * 0.067F) * 0.05F;
    this.field_178724_i.field_78795_f -= MathHelper.func_76126_a(ageInTicks * 0.067F) * 0.05F;
    EntityGiant entity = (EntityGiant)entityIn;
    if (entity.func_70093_af()) {
      this.field_78117_n = true;
    } else {
      this.field_78117_n = false;
    } 
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
    if (entity.getJukeboxToDanceTo() != null) {
      float fl = MathHelper.func_76126_a(ageInTicks * 0.25F) * 0.7F;
      this.field_178723_h.field_78798_e = 0.0F;
      this.field_178723_h.field_78800_c = -5.0F;
      this.field_178724_i.field_78798_e = 0.0F;
      this.field_178724_i.field_78800_c = 5.0F;
      this.field_178723_h.field_78795_f = -fl;
      this.field_178724_i.field_78795_f = fl;
      this.field_178723_h.field_78808_h = 1.6561944F - MathHelper.func_76134_b(ageInTicks * 0.0625F) * 1.0F;
      this.field_178724_i.field_78808_h = -1.6561944F + MathHelper.func_76134_b(ageInTicks * 0.0625F) * 1.0F;
      this.field_178723_h.field_78796_g = 0.0F;
      this.field_178724_i.field_78796_g = 0.0F;
      this.field_78116_c.func_78793_a(0.0F + MathHelper.func_76134_b(ageInTicks * 0.125F) * 1.0F, 0.5F - fl, 0.0F);
    } 
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
