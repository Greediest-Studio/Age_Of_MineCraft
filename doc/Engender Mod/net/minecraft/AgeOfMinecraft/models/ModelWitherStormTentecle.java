package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelWitherStormTentecle extends ModelBase {
  public ModelRenderer TentecleBlock;
  
  public ModelRenderer TentecleBase1;
  
  public ModelRenderer TentecleBase2;
  
  public ModelRenderer TentecleBase3;
  
  public ModelRenderer TentecleBase4;
  
  public ModelRenderer TentecleMiddle1;
  
  public ModelRenderer TentecleMiddle2;
  
  public ModelRenderer TentecleMiddle3;
  
  public ModelRenderer TentecleTip1;
  
  public ModelRenderer TentecleTip2;
  
  public ModelRenderer TentecleTip3;
  
  public ModelWitherStormTentecle() {
    this.field_78090_t = 256;
    this.field_78089_u = 256;
    this.TentecleBlock = new ModelRenderer(this, 0, 0);
    this.TentecleBlock.func_78793_a(0.0F, 0.0F, 0.0F);
    this.TentecleBlock.func_78790_a(-32.0F, 0.0F, -32.0F, 64, 64, 64, 0.0F);
    this.TentecleTip1 = new ModelRenderer(this, 0, 0);
    this.TentecleTip1.func_78793_a(0.0F, -148.0F, 0.0F);
    this.TentecleTip1.func_78790_a(-10.0F, -164.0F, -10.0F, 20, 164, 20, 0.0F);
    this.TentecleTip2 = new ModelRenderer(this, 0, 0);
    this.TentecleTip2.func_78793_a(0.0F, -164.0F, 0.0F);
    this.TentecleTip2.func_78790_a(-8.0F, -128.0F, -8.0F, 16, 128, 16, 0.0F);
    this.TentecleMiddle3 = new ModelRenderer(this, 0, 0);
    this.TentecleMiddle3.func_78793_a(0.0F, -128.0F, 0.0F);
    this.TentecleMiddle3.func_78790_a(-14.0F, -148.0F, -14.0F, 28, 148, 28, 0.0F);
    this.TentecleBase3 = new ModelRenderer(this, 0, 0);
    this.TentecleBase3.func_78793_a(0.0F, -64.0F, 0.0F);
    this.TentecleBase3.func_78790_a(-22.0F, -64.0F, -22.0F, 44, 64, 44, 0.0F);
    this.TentecleBase1 = new ModelRenderer(this, 0, 0);
    this.TentecleBase1.func_78793_a(0.0F, 0.0F, 0.0F);
    this.TentecleBase1.func_78790_a(-16.0F, -128.0F, -16.0F, 32, 128, 32, 0.0F);
    this.TentecleBase4 = new ModelRenderer(this, 0, 0);
    this.TentecleBase4.func_78793_a(0.0F, -64.0F, 0.0F);
    this.TentecleBase4.func_78790_a(-20.0F, -96.0F, -20.0F, 40, 96, 40, 0.0F);
    this.TentecleTip3 = new ModelRenderer(this, 0, 0);
    this.TentecleTip3.func_78793_a(0.0F, -128.0F, 0.0F);
    this.TentecleTip3.func_78790_a(-8.0F, -128.0F, -8.0F, 16, 128, 16, 0.0F);
    this.TentecleMiddle1 = new ModelRenderer(this, 0, 0);
    this.TentecleMiddle1.func_78793_a(0.0F, -96.0F, 0.0F);
    this.TentecleMiddle1.func_78790_a(-18.0F, -64.0F, -18.0F, 36, 64, 36, 0.0F);
    this.TentecleMiddle2 = new ModelRenderer(this, 0, 0);
    this.TentecleMiddle2.func_78793_a(0.0F, -64.0F, 0.0F);
    this.TentecleMiddle2.func_78790_a(-16.0F, -128.0F, -16.0F, 32, 128, 32, 0.0F);
    this.TentecleBase2 = new ModelRenderer(this, 0, 0);
    this.TentecleBase2.func_78793_a(0.0F, -128.0F, 0.0F);
    this.TentecleBase2.func_78790_a(-24.0F, -64.0F, -24.0F, 48, 64, 48, 0.0F);
    this.TentecleMiddle3.func_78792_a(this.TentecleTip1);
    this.TentecleTip1.func_78792_a(this.TentecleTip2);
    this.TentecleMiddle2.func_78792_a(this.TentecleMiddle3);
    this.TentecleBase2.func_78792_a(this.TentecleBase3);
    this.TentecleBlock.func_78792_a(this.TentecleBase1);
    this.TentecleBase3.func_78792_a(this.TentecleBase4);
    this.TentecleTip2.func_78792_a(this.TentecleTip3);
    this.TentecleBase4.func_78792_a(this.TentecleMiddle1);
    this.TentecleMiddle1.func_78792_a(this.TentecleMiddle2);
    this.TentecleBase1.func_78792_a(this.TentecleBase2);
  }
  
  public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    this.TentecleBlock.func_78785_a(f5);
  }
  
  public void func_78087_a(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    this.TentecleBlock.field_78795_f = 1.575F;
    this.TentecleBase1.field_78796_g = 0.45F * MathHelper.func_76126_a(ageInTicks * 0.031200068F + 3.9F);
    this.TentecleBase1.field_78808_h = 0.5F * MathHelper.func_76126_a(ageInTicks * 0.04F + 3.9F);
    this.TentecleBase2.field_78808_h = 0.2F * MathHelper.func_76126_a(ageInTicks * 0.04F + 3.6F);
    this.TentecleBase3.field_78808_h = 0.2F * MathHelper.func_76126_a(ageInTicks * 0.04F + 3.3F);
    this.TentecleBase4.field_78808_h = 0.2F * MathHelper.func_76126_a(ageInTicks * 0.04F + 3.0F);
    this.TentecleMiddle1.field_78808_h = 0.15F * MathHelper.func_76126_a(ageInTicks * 0.04F + 2.8F);
    this.TentecleMiddle2.field_78808_h = 0.15F * MathHelper.func_76126_a(ageInTicks * 0.04F + 2.4F);
    this.TentecleMiddle3.field_78808_h = 0.15F * MathHelper.func_76126_a(ageInTicks * 0.04F + 2.0F);
    this.TentecleTip1.field_78808_h = 0.1F * MathHelper.func_76126_a(ageInTicks * 0.04F + 1.8F);
    this.TentecleTip2.field_78808_h = 0.1F * MathHelper.func_76126_a(ageInTicks * 0.04F + 1.4F);
    this.TentecleTip3.field_78808_h = 0.2F * MathHelper.func_76126_a(ageInTicks * 0.04F + 1.0F);
    EntityTameBase entitytentacle = (EntityTameBase)entityIn;
    this.TentecleBase1.field_78795_f = 0.5F * MathHelper.func_76126_a(ageInTicks * 0.075F + 3.9F);
    this.TentecleBase2.field_78795_f = 0.3F * MathHelper.func_76126_a(ageInTicks * 0.075F + 3.6F);
    this.TentecleBase3.field_78795_f = 0.2F * MathHelper.func_76126_a(ageInTicks * 0.075F + 3.3F);
    this.TentecleBase4.field_78795_f = 0.2F * MathHelper.func_76126_a(ageInTicks * 0.075F + 3.0F);
    this.TentecleMiddle1.field_78795_f = 0.2F * MathHelper.func_76126_a(ageInTicks * 0.075F + 2.8F);
    this.TentecleMiddle2.field_78795_f = 0.2F * MathHelper.func_76126_a(ageInTicks * 0.075F + 2.4F);
    this.TentecleMiddle3.field_78795_f = 0.2F * MathHelper.func_76126_a(ageInTicks * 0.075F + 2.0F);
    this.TentecleTip1.field_78795_f = 0.1F * MathHelper.func_76126_a(ageInTicks * 0.075F + 1.8F);
    this.TentecleTip2.field_78795_f = 0.1F * MathHelper.func_76126_a(ageInTicks * 0.075F + 1.4F);
    this.TentecleTip3.field_78795_f = 0.2F * MathHelper.func_76126_a(ageInTicks * 0.075F + 1.0F);
    if (entitytentacle.getJukeboxToDanceTo() != null) {
      this.TentecleBase1.field_78808_h = MathHelper.func_76126_a(ageInTicks * 0.5F) * 0.25F;
      this.TentecleBase1.field_78795_f = 0.45F * MathHelper.func_76126_a(ageInTicks * 0.25F + 3.9F);
      this.TentecleBase2.field_78795_f = 0.3F * MathHelper.func_76126_a(ageInTicks * 0.25F + 3.6F);
      this.TentecleBase3.field_78795_f = 0.2F * MathHelper.func_76126_a(ageInTicks * 0.25F + 3.3F);
      this.TentecleBase4.field_78795_f = 0.2F * MathHelper.func_76126_a(ageInTicks * 0.25F + 3.0F);
      this.TentecleMiddle1.field_78795_f = 0.2F * MathHelper.func_76126_a(ageInTicks * 0.25F + 2.8F);
      this.TentecleMiddle2.field_78795_f = 0.2F * MathHelper.func_76126_a(ageInTicks * 0.25F + 2.4F);
      this.TentecleMiddle3.field_78795_f = 0.2F * MathHelper.func_76126_a(ageInTicks * 0.25F + 2.0F);
      this.TentecleTip1.field_78795_f = 0.1F * MathHelper.func_76126_a(ageInTicks * 0.25F + 1.8F);
      this.TentecleTip2.field_78795_f = 0.1F * MathHelper.func_76126_a(ageInTicks * 0.25F + 1.4F);
      this.TentecleTip3.field_78795_f = 0.2F * MathHelper.func_76126_a(ageInTicks * 0.25F + 1.0F);
    } 
  }
  
  public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
    modelRenderer.field_78795_f = x;
    modelRenderer.field_78796_g = y;
    modelRenderer.field_78808_h = z;
  }
}
