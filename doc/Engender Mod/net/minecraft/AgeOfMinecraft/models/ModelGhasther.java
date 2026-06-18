package net.minecraft.AgeOfMinecraft.models;

import java.util.Random;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityGhasther;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelGhasther extends ModelBase {
  public ModelRenderer rightface;
  
  public ModelRenderer leftface;
  
  public ModelRenderer body;
  
  public ModelRenderer target;
  
  public ModelRenderer[] tentacles = new ModelRenderer[9];
  
  public ModelGhasther() {
    this.field_78090_t = 64;
    this.field_78089_u = 32;
    this.body = new ModelRenderer(this, 0, 0);
    this.body.func_78793_a(0.0F, 16.0F, 0.0F);
    this.body.func_78790_a(-8.0F, -8.0F, -8.0F, 16, 16, 16, 0.0F);
    this.body.field_78797_d += 8.0F;
    this.rightface = new ModelRenderer(this, 0, 0);
    this.rightface.func_78793_a(0.0F, 0.0F, 0.0F);
    this.rightface.func_78790_a(-5.0F, -11.0F, -14.0F, 16, 16, 16, -4.0F);
    setRotateAngle(this.rightface, 0.0F, 1.5707964F, 0.0F);
    this.leftface = new ModelRenderer(this, 0, 0);
    this.leftface.func_78793_a(0.0F, 0.0F, 0.0F);
    this.leftface.func_78790_a(-7.0F, -9.0F, -13.0F, 16, 16, 16, -2.0F);
    setRotateAngle(this.leftface, 0.0F, -1.5707964F, 0.0F);
    this.target = new ModelRenderer(this, 42, 0);
    this.target.func_78793_a(0.0F, 0.0F, 0.0F);
    this.target.func_78790_a(-3.0F, -8.1F, -2.0F, 6, 0, 6, 0.0F);
    this.body.func_78792_a(this.target);
    this.body.func_78792_a(this.leftface);
    this.body.func_78792_a(this.rightface);
    Random random = new Random(1660L);
    for (int j = 0; j < this.tentacles.length; j++) {
      this.tentacles[j] = new ModelRenderer(this, 0, 0);
      float f = (((j % 3) - (j / 3 % 2) * 0.5F + 0.25F) / 2.0F * 2.0F - 1.0F) * 5.0F;
      float f1 = ((j / 3) / 2.0F * 2.0F - 1.0F) * 5.0F;
      int k = random.nextInt(7) + 8;
      this.tentacles[j].func_78789_a(-1.0F, 0.0F, -1.0F, 2, k, 2);
      (this.tentacles[j]).field_78800_c = f;
      (this.tentacles[j]).field_78798_e = f1;
      (this.tentacles[j]).field_78797_d = 15.0F;
      this.body.func_78792_a(this.tentacles[j]);
    } 
  }
  
  public void func_78088_a(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    func_78087_a(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
    GlStateManager.func_179094_E();
    GlStateManager.func_179109_b(0.0F, -0.45F, 0.0F);
    this.body.func_78785_a(scale);
    GlStateManager.func_179121_F();
  }
  
  public void func_78087_a(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    this.body.field_78796_g = netHeadYaw / 57.295776F;
    this.body.field_78795_f = headPitch / 57.295776F;
    EntityGhasther entity = (EntityGhasther)entityIn;
    for (int i = 0; i < this.tentacles.length; i++) {
      (this.tentacles[i]).field_78808_h = 0.0F;
      (this.tentacles[i]).field_78797_d = 7.0F;
      (this.tentacles[i]).field_78795_f = 0.2F * MathHelper.func_76126_a((entity.func_175446_cd() ? 1.0F : ageInTicks) * 0.3F + i) + 0.4F;
    } 
    if (entity.getJukeboxToDanceTo() != null) {
      this.body.field_78795_f += MathHelper.func_76126_a(ageInTicks * 0.5F) * 0.25F;
      this.body.field_78796_g += MathHelper.func_76126_a(ageInTicks * 0.25F) * 0.5F;
      for (int k = 0; k < this.tentacles.length; k++)
        (this.tentacles[k]).field_78808_h += MathHelper.func_76126_a(k * 2.0F + (entity.func_175446_cd() ? 1.0F : ageInTicks) * 0.25F) * 0.5F; 
    } 
    if (!entity.getCurrentBook().func_190926_b()) {
      (this.tentacles[0]).field_78796_g = entity.bookSpread - 1.0F;
      (this.tentacles[2]).field_78796_g = -entity.bookSpread + 1.0F;
      (this.tentacles[0]).field_78808_h = 0.0F;
      (this.tentacles[2]).field_78808_h = 0.0F;
      (this.tentacles[0]).field_78795_f = -1.5F + 0.1F + MathHelper.func_76126_a(entity.field_70173_aa * 0.1F) * 0.01F;
      (this.tentacles[2]).field_78795_f = -1.5F + 0.1F + MathHelper.func_76126_a(entity.field_70173_aa * 0.1F) * 0.01F;
    } 
  }
  
  public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
    modelRenderer.field_78795_f = x;
    modelRenderer.field_78796_g = y;
    modelRenderer.field_78808_h = z;
  }
}
