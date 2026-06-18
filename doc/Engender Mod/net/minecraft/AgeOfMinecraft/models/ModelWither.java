package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityWither;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelWither extends ModelBase implements ICappedModel {
  private ModelRenderer[] upperBodyParts;
  
  public ModelRenderer[] heads;
  
  public ModelRenderer bipedCape;
  
  public ModelWither(float p_i46302_1_) {
    this.field_78090_t = 64;
    this.field_78089_u = 64;
    this.upperBodyParts = new ModelRenderer[3];
    this.upperBodyParts[0] = new ModelRenderer(this, 0, 16);
    this.upperBodyParts[0].func_78790_a(-10.0F, 3.9F, -0.5F, 20, 3, 3, p_i46302_1_);
    this.upperBodyParts[1] = (new ModelRenderer(this)).func_78787_b(this.field_78090_t, this.field_78089_u);
    this.upperBodyParts[1].func_78793_a(-2.0F, 6.9F, -0.5F);
    this.upperBodyParts[1].func_78784_a(0, 22).func_78790_a(0.0F, 0.0F, 0.0F, 3, 10, 3, p_i46302_1_);
    this.upperBodyParts[1].func_78784_a(24, 22).func_78790_a(-4.0F, 1.5F, 0.5F, 11, 2, 2, p_i46302_1_);
    this.upperBodyParts[1].func_78784_a(24, 22).func_78790_a(-4.0F, 4.0F, 0.5F, 11, 2, 2, p_i46302_1_);
    this.upperBodyParts[1].func_78784_a(24, 22).func_78790_a(-4.0F, 6.5F, 0.5F, 11, 2, 2, p_i46302_1_);
    this.upperBodyParts[2] = new ModelRenderer(this, 12, 22);
    this.upperBodyParts[2].func_78790_a(0.0F, 0.0F, 0.0F, 3, 6, 3, p_i46302_1_);
    this.heads = new ModelRenderer[3];
    this.heads[0] = new ModelRenderer(this, 0, 0);
    this.heads[0].func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, p_i46302_1_);
    this.heads[1] = new ModelRenderer(this, 32, 0);
    this.heads[1].func_78790_a(-4.0F, -4.0F, -4.0F, 6, 6, 6, p_i46302_1_);
    (this.heads[1]).field_78800_c = -8.0F;
    (this.heads[1]).field_78797_d = 4.0F;
    this.heads[2] = new ModelRenderer(this, 32, 0);
    this.heads[2].func_78790_a(-4.0F, -4.0F, -4.0F, 6, 6, 6, p_i46302_1_);
    (this.heads[2]).field_78800_c = 10.0F;
    (this.heads[2]).field_78797_d = 4.0F;
    this.bipedCape = new ModelRenderer(this, 0, 0);
    this.bipedCape.func_78787_b(64, 32);
    this.bipedCape.func_78790_a(-5.0F, 0.0F, -1.0F, 10, 16, 1, p_i46302_1_);
  }
  
  public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
    func_78087_a(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
    GlStateManager.func_179094_E();
    EntityWither wither = (EntityWither)p_78088_1_;
    if (wither.getRamTime() < 180 && wither.getRamTime() > 100)
      GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F); 
    ModelRenderer[] amodelrenderer = this.heads;
    int i = amodelrenderer.length;
    int j;
    for (j = 0; j < i; j++) {
      ModelRenderer modelrenderer = amodelrenderer[j];
      modelrenderer.func_78785_a(p_78088_7_);
    } 
    amodelrenderer = this.upperBodyParts;
    i = amodelrenderer.length;
    for (j = 0; j < i; j++) {
      ModelRenderer modelrenderer = amodelrenderer[j];
      modelrenderer.func_78785_a(p_78088_7_);
    } 
    GlStateManager.func_179121_F();
  }
  
  public void renderCape(float scale, float flo1, float flo2, float flo3) {
    GlStateManager.func_179094_E();
    GlStateManager.func_179109_b(0.0F, 0.25F, 0.0F);
    GlStateManager.func_179114_b(6.0F + flo2 / 2.0F + flo1 + (this.upperBodyParts[1]).field_78795_f * 50.0F, 1.0F, 0.0F, 0.0F);
    GlStateManager.func_179114_b(flo3 / 2.0F, 0.0F, 0.0F, 1.0F);
    GlStateManager.func_179114_b(-flo3 / 2.0F, 0.0F, 1.0F, 0.0F);
    GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
    this.bipedCape.func_78785_a(scale);
    GlStateManager.func_179121_F();
  }
  
  public void func_78087_a(float limbSwing, float limbSwingAmount, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
    super.func_78087_a(limbSwing, limbSwingAmount, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
    (this.heads[0]).field_78796_g = p_78087_4_ / 57.295776F;
    this.upperBodyParts[2].func_78793_a(-2.0F, 6.9F + MathHelper.func_76134_b((this.upperBodyParts[1]).field_78795_f) * 10.0F, -0.5F + MathHelper.func_76126_a((this.upperBodyParts[1]).field_78795_f) * 10.0F);
    EntityWither wither = (EntityWither)p_78087_7_;
    float f2 = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F);
    float f1 = MathHelper.func_76126_a((1.0F - (1.0F - this.field_78095_p) * (1.0F - this.field_78095_p)) * 3.1415927F);
    (this.heads[0]).field_78795_f = p_78087_5_ / 57.295776F;
    (this.heads[0]).field_78800_c = 0.0F;
    (this.heads[0]).field_78797_d = 4.0F;
    (this.heads[0]).field_78808_h = 0.0F;
    (this.heads[1]).field_78808_h = 0.0F;
    (this.heads[2]).field_78808_h = 0.0F;
    (this.upperBodyParts[1]).field_78795_f = 0.0F;
    (this.upperBodyParts[2]).field_78795_f = 0.0F;
    (this.upperBodyParts[1]).field_78808_h = 0.0F;
    (this.upperBodyParts[2]).field_78808_h = 0.0F;
    if (wither.getJukeboxToDanceTo() != null) {
      float f4 = MathHelper.func_76126_a(p_78087_3_ * 0.125F) * 0.7F;
      (this.heads[0]).field_78800_c += MathHelper.func_76134_b(p_78087_3_ * 0.25F) * 1.0F;
      (this.heads[0]).field_78797_d += f4 + MathHelper.func_76134_b(p_78087_3_ * 0.5F) * 1.0F;
      (this.heads[0]).field_78795_f += MathHelper.func_76134_b(p_78087_3_ * 0.5F) * 0.5F;
      (this.heads[0]).field_78808_h += MathHelper.func_76134_b(p_78087_3_ * 0.25F) * 0.5F;
      (this.heads[1]).field_78808_h += MathHelper.func_76134_b(p_78087_3_ * 0.25F - 2.0F) * 0.5F;
      (this.heads[2]).field_78808_h -= MathHelper.func_76134_b(p_78087_3_ * 0.25F - 2.0F) * 0.5F;
      (this.upperBodyParts[1]).field_78795_f += MathHelper.func_76134_b(p_78087_3_ * 0.25F) * 0.5F;
      (this.upperBodyParts[2]).field_78795_f += MathHelper.func_76134_b(p_78087_3_ * 0.25F - 1.0F) * 0.5F;
    } 
    if (wither.func_70093_af()) {
      float f6 = MathHelper.func_76134_b(p_78087_3_ * 0.25F);
      (this.upperBodyParts[1]).field_78795_f += p_78087_5_ / 57.295776F + (0.35F + 0.025F * f6) * 3.1415927F;
      f6 = MathHelper.func_76134_b(p_78087_3_ * 0.25F - 1.5F);
      (this.upperBodyParts[2]).field_78795_f += p_78087_5_ / 57.295776F + (0.6F + 0.05F * f6) * 3.1415927F;
      (this.heads[0]).field_78795_f -= 0.075F + 0.05F + 0.025F * f6;
      f6 = MathHelper.func_76134_b(p_78087_3_ * 0.275F - 1.0F);
      (this.heads[1]).field_78795_f += (-0.01F + 0.01F * f6) * 3.1415927F;
      (this.heads[2]).field_78795_f += (-0.01F + 0.01F * f6) * 3.1415927F;
      (this.upperBodyParts[1]).field_78795_f -= f2 * 2.0F - f1;
      (this.upperBodyParts[2]).field_78795_f -= f2 * 4.0F - f1;
    } else {
      float f6 = MathHelper.func_76134_b(p_78087_3_ * 0.1F);
      (this.upperBodyParts[1]).field_78795_f += (0.065F + 0.05F * f6) * 3.1415927F;
      f6 = MathHelper.func_76134_b(p_78087_3_ * 0.1F - 1.5F);
      (this.upperBodyParts[2]).field_78795_f += (0.3F + 0.1F * f6) * 3.1415927F;
      (this.heads[0]).field_78795_f -= 0.05F + 0.025F * f6;
      f6 = MathHelper.func_76134_b(p_78087_3_ * 0.125F - 1.0F);
      (this.heads[1]).field_78795_f += (-0.01F + 0.01F * f6) * 3.1415927F;
      (this.heads[2]).field_78795_f += (-0.01F + 0.01F * f6) * 3.1415927F;
      (this.upperBodyParts[1]).field_78795_f -= f2 * 2.0F - f1;
      (this.upperBodyParts[2]).field_78795_f -= f2 * 4.0F - f1;
    } 
  }
  
  public void func_78086_a(EntityLivingBase p_78086_1_, float p_78086_2_, float p_78086_3_, float p_78086_4_) {
    EntityWither entitywither = (EntityWither)p_78086_1_;
    for (int i = 1; i < 3; i++) {
      (this.heads[i]).field_78796_g = (entitywither.getHeadYRotation(i - 1) - p_78086_1_.field_70761_aq) * 0.017453292F + (!entitywither.getCurrentBook().func_190926_b() ? -(MathHelper.func_76126_a(entitywither.field_70173_aa * 0.1F) * 0.5F + ((i == 1) ? 0.5F : -0.5F)) : 0.0F);
      (this.heads[i]).field_78795_f = entitywither.getHeadXRotation(i - 1) * 0.017453292F;
    } 
  }
}
