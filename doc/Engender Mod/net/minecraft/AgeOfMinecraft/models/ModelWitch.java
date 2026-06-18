package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityWitch;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelWitch extends ModelVillager implements ICappedModel {
  public boolean holdingItem;
  
  private final ModelRenderer mole = (new ModelRenderer((ModelBase)this)).func_78787_b(64, 128);
  
  private final ModelRenderer witchHat;
  
  public ModelRenderer bipedCape;
  
  public ModelWitch(float scale) {
    super(scale, 0.0F, 64, 128);
    this.bipedCape = new ModelRenderer((ModelBase)this, 0, 0);
    this.bipedCape.func_78787_b(64, 32);
    this.bipedCape.func_78790_a(-5.0F, 0.0F, -1.0F, 10, 16, 1, scale);
    this.mole.func_78793_a(0.0F, -2.0F, 0.0F);
    this.mole.func_78784_a(0, 0).func_78790_a(0.0F, 3.0F, -6.75F, 1, 1, 1, -0.25F);
    this.field_82898_f.func_78792_a(this.mole);
    this.witchHat = (new ModelRenderer((ModelBase)this)).func_78787_b(64, 128);
    this.witchHat.func_78793_a(-5.0F, -10.03125F, -5.0F);
    this.witchHat.func_78784_a(0, 64).func_78789_a(0.0F, 0.0F, 0.0F, 10, 2, 10);
    this.field_78191_a.func_78792_a(this.witchHat);
    ModelRenderer modelrenderer = (new ModelRenderer((ModelBase)this)).func_78787_b(64, 128);
    modelrenderer.func_78793_a(1.75F, -4.0F, 2.0F);
    modelrenderer.func_78784_a(0, 76).func_78789_a(0.0F, 0.0F, 0.0F, 7, 4, 7);
    modelrenderer.field_78795_f = -0.05235988F;
    modelrenderer.field_78808_h = 0.02617994F;
    this.witchHat.func_78792_a(modelrenderer);
    ModelRenderer modelrenderer1 = (new ModelRenderer((ModelBase)this)).func_78787_b(64, 128);
    modelrenderer1.func_78793_a(1.75F, -4.0F, 2.0F);
    modelrenderer1.func_78784_a(0, 87).func_78789_a(0.0F, 0.0F, 0.0F, 4, 4, 4);
    modelrenderer1.field_78795_f = -0.10471976F;
    modelrenderer1.field_78808_h = 0.05235988F;
    modelrenderer.func_78792_a(modelrenderer1);
    ModelRenderer modelrenderer2 = (new ModelRenderer((ModelBase)this)).func_78787_b(64, 128);
    modelrenderer2.func_78793_a(1.75F, -2.0F, 2.0F);
    modelrenderer2.func_78784_a(0, 95).func_78790_a(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.25F);
    modelrenderer2.field_78795_f = -0.20943952F;
    modelrenderer2.field_78808_h = 0.10471976F;
    modelrenderer1.func_78792_a(modelrenderer2);
  }
  
  public void renderCape(float scale, float flo1, float flo2, float flo3) {
    GlStateManager.func_179094_E();
    GlStateManager.func_179109_b(0.0F, 0.0F, 0.0625F);
    GlStateManager.func_179114_b(6.0F + flo2 / 2.0F + flo1, 1.0F, 0.0F, 0.0F);
    GlStateManager.func_179114_b(flo3 / 2.0F, 0.0F, 0.0F, 1.0F);
    GlStateManager.func_179114_b(-flo3 / 2.0F, 0.0F, 1.0F, 0.0F);
    GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
    this.bipedCape.func_78785_a(scale);
    GlStateManager.func_179121_F();
  }
  
  public void func_78087_a(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    super.func_78087_a(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    this.field_82898_f.field_82906_o = 0.0F;
    this.field_82898_f.field_82908_p = 0.0F;
    this.field_82898_f.field_82907_q = 0.0F;
    float f = 0.01F * (entityIn.func_145782_y() % 10);
    this.field_82898_f.field_78795_f = MathHelper.func_76126_a(entityIn.field_70173_aa * f) * 4.5F * 0.017453292F;
    this.field_82898_f.field_78796_g = 0.0F;
    this.field_82898_f.field_78808_h = MathHelper.func_76134_b(entityIn.field_70173_aa * f) * 2.5F * 0.017453292F;
    if (this.holdingItem) {
      this.field_82898_f.field_78795_f = -0.9F;
      this.field_82898_f.field_82907_q = -0.09375F;
      this.field_82898_f.field_82908_p = 0.1875F;
    } 
    if (((EntityWitch)entityIn).func_70027_ad() || (!((EntityWitch)entityIn).func_70089_S() && !((EntityWitch)entityIn).field_70122_E)) {
      this.field_78191_a.field_78795_f -= 0.5F;
      this.field_78191_a.field_78796_g += MathHelper.func_76134_b(ageInTicks * 0.6662F) * 0.5F;
    } 
  }
}
