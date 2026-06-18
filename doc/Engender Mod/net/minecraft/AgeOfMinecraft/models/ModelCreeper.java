package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityCreeper;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelCreeper extends ModelBase implements ICappedModel {
  public ModelRenderer head;
  
  public ModelRenderer creeperArmor;
  
  public ModelRenderer body;
  
  public ModelRenderer leg1;
  
  public ModelRenderer leg2;
  
  public ModelRenderer leg3;
  
  public ModelRenderer leg4;
  
  public boolean isSneak;
  
  public ModelRenderer bipedCape;
  
  public ModelCreeper() {
    this(0.0F);
  }
  
  public ModelCreeper(float p_i46366_1_) {
    int i = 6;
    this.head = new ModelRenderer(this, 0, 0);
    this.head.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, p_i46366_1_);
    this.head.func_78793_a(0.0F, i, 0.0F);
    this.creeperArmor = new ModelRenderer(this, 32, 0);
    this.creeperArmor.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, p_i46366_1_ + 0.5F);
    this.creeperArmor.func_78793_a(0.0F, i, 0.0F);
    this.body = new ModelRenderer(this, 16, 16);
    this.body.func_78790_a(-4.0F, 0.0F, -2.0F, 8, 12, 4, p_i46366_1_);
    this.body.func_78793_a(0.0F, i, 0.0F);
    this.leg1 = new ModelRenderer(this, 0, 16);
    this.leg1.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 6, 4, p_i46366_1_);
    this.leg1.func_78793_a(-2.0F, (12 + i), 4.0F);
    this.leg2 = new ModelRenderer(this, 0, 16);
    this.leg2.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 6, 4, p_i46366_1_);
    this.leg2.func_78793_a(2.0F, (12 + i), 4.0F);
    this.leg3 = new ModelRenderer(this, 0, 16);
    this.leg3.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 6, 4, p_i46366_1_);
    this.leg3.func_78793_a(-2.0F, (12 + i), -4.0F);
    this.leg4 = new ModelRenderer(this, 0, 16);
    this.leg4.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 6, 4, p_i46366_1_);
    this.leg4.func_78793_a(2.0F, (12 + i), -4.0F);
    this.bipedCape = new ModelRenderer(this, 0, 0);
    this.bipedCape.func_78787_b(64, 32);
    this.bipedCape.func_78790_a(-5.0F, 0.0F, -1.0F, 10, 16, 1, p_i46366_1_);
  }
  
  public void renderCape(float scale, float flo1, float flo2, float flo3) {
    if (this.field_78091_s) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
      GlStateManager.func_179109_b(0.0F, 1.0F, -0.025F);
      GlStateManager.func_179114_b(6.0F + flo2 / 2.0F + flo1, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179114_b(flo3 / 2.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.func_179114_b(-flo3 / 2.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
      this.bipedCape.func_78785_a(scale);
      GlStateManager.func_179121_F();
    } else {
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b(0.0F, this.isSneak ? 0.425F : 0.375F, this.isSneak ? -0.25F : -0.025F);
      GlStateManager.func_179114_b(6.0F + flo2 / 2.0F + flo1, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179114_b(flo3 / 2.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.func_179114_b(-flo3 / 2.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
      this.bipedCape.func_78785_a(scale);
      GlStateManager.func_179121_F();
    } 
  }
  
  public void func_78088_a(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    func_78087_a(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
    GlStateManager.func_179094_E();
    if (this.field_78091_s) {
      GlStateManager.func_179152_a(1.5F, 1.5F, 1.5F);
      this.head.func_78785_a(scale);
      GlStateManager.func_179121_F();
      GlStateManager.func_179094_E();
      GlStateManager.func_179152_a(1.0F, 1.0F, 1.0F);
      this.body.func_78785_a(scale);
      this.leg1.func_78785_a(scale);
      this.leg2.func_78785_a(scale);
      this.leg3.func_78785_a(scale);
      this.leg4.func_78785_a(scale);
    } else {
      this.head.func_78785_a(scale);
      this.body.func_78785_a(scale);
      this.leg1.func_78785_a(scale);
      this.leg2.func_78785_a(scale);
      this.leg3.func_78785_a(scale);
      this.leg4.func_78785_a(scale);
    } 
    GlStateManager.func_179121_F();
  }
  
  public void func_78087_a(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    float f = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F);
    this.head.field_78796_g = netHeadYaw * 0.017453292F;
    this.head.field_78795_f = headPitch * 0.017453292F + f;
    this.leg1.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    this.leg2.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
    this.leg3.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
    this.leg4.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    this.head.field_78800_c = 0.0F;
    this.head.field_78795_f += MathHelper.func_76134_b(ageInTicks * 0.1F) * 0.0125F * 3.1415927F;
    if (this.isSneak) {
      this.body.field_78795_f = 0.5F;
      this.head.field_78797_d = 8.0F;
      this.body.field_78797_d = 8.0F;
      this.head.field_78798_e = -5.0F;
      this.body.field_78798_e = -5.0F;
    } else {
      this.body.field_78795_f = 0.0F;
      this.head.field_78797_d = 6.0F;
      this.body.field_78797_d = 6.0F;
      this.head.field_78798_e = 0.0F;
      this.body.field_78798_e = 0.0F;
    } 
    EntityCreeper entity = (EntityCreeper)entityIn;
    this.field_78091_s = entity.func_70631_g_();
    this.isSneak = entity.func_70093_af();
    if (entity.func_70027_ad() || (!entity.func_70089_S() && !entity.field_70122_E)) {
      this.head.field_78795_f -= 0.5F;
      this.head.field_78796_g += MathHelper.func_76134_b(ageInTicks * 0.6662F) * 0.5F;
    } 
    if (entity.getJukeboxToDanceTo() != null) {
      float fl = MathHelper.func_76126_a(ageInTicks * 0.5F) * 0.7F;
      this.head.field_78800_c += MathHelper.func_76134_b(ageInTicks * 0.25F) * 1.0F;
      this.head.field_78797_d -= fl;
    } 
  }
}
