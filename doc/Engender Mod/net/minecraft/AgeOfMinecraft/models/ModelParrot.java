package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityParrot;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelParrot extends ModelBase {
  ModelRenderer body;
  
  ModelRenderer tail;
  
  ModelRenderer wingLeft;
  
  ModelRenderer wingRight;
  
  ModelRenderer head;
  
  ModelRenderer head2;
  
  ModelRenderer beak1;
  
  ModelRenderer beak2;
  
  ModelRenderer feather;
  
  ModelRenderer legLeft;
  
  ModelRenderer legRight;
  
  private State state = State.STANDING;
  
  public ModelParrot() {
    this.field_78090_t = 32;
    this.field_78089_u = 32;
    this.body = new ModelRenderer(this, 2, 8);
    this.body.func_78789_a(-1.5F, 0.0F, -1.5F, 3, 6, 3);
    this.body.func_78793_a(0.0F, 16.5F, -3.0F);
    this.tail = new ModelRenderer(this, 22, 1);
    this.tail.func_78789_a(-1.5F, -1.0F, -1.0F, 3, 4, 1);
    this.tail.func_78793_a(0.0F, 21.07F, 1.16F);
    this.wingLeft = new ModelRenderer(this, 19, 8);
    this.wingLeft.func_78789_a(-0.5F, 0.0F, -1.5F, 1, 5, 3);
    this.wingLeft.func_78793_a(1.5F, 16.94F, -2.76F);
    this.wingRight = new ModelRenderer(this, 19, 8);
    this.wingRight.func_78789_a(-0.5F, 0.0F, -1.5F, 1, 5, 3);
    this.wingRight.func_78793_a(-1.5F, 16.94F, -2.76F);
    this.head = new ModelRenderer(this, 2, 2);
    this.head.func_78789_a(-1.0F, -1.5F, -1.0F, 2, 3, 2);
    this.head.func_78793_a(0.0F, 15.69F, -2.76F);
    this.head2 = new ModelRenderer(this, 10, 0);
    this.head2.func_78789_a(-1.0F, -0.5F, -2.0F, 2, 1, 4);
    this.head2.func_78793_a(0.0F, -2.0F, -1.0F);
    this.head.func_78792_a(this.head2);
    this.beak1 = new ModelRenderer(this, 11, 7);
    this.beak1.func_78789_a(-0.5F, -1.0F, -0.5F, 1, 2, 1);
    this.beak1.func_78793_a(0.0F, -0.5F, -1.5F);
    this.head.func_78792_a(this.beak1);
    this.beak2 = new ModelRenderer(this, 16, 7);
    this.beak2.func_78789_a(-0.5F, 0.0F, -0.5F, 1, 2, 1);
    this.beak2.func_78793_a(0.0F, -1.75F, -2.45F);
    this.head.func_78792_a(this.beak2);
    this.feather = new ModelRenderer(this, 2, 18);
    this.feather.func_78789_a(0.0F, -4.0F, -2.0F, 0, 5, 4);
    this.feather.func_78793_a(0.0F, -2.15F, 0.15F);
    this.head.func_78792_a(this.feather);
    this.legLeft = new ModelRenderer(this, 14, 18);
    this.legLeft.func_78789_a(-0.5F, 0.0F, -0.5F, 1, 2, 1);
    this.legLeft.func_78793_a(1.0F, 22.0F, -1.05F);
    this.legRight = new ModelRenderer(this, 14, 18);
    this.legRight.func_78789_a(-0.5F, 0.0F, -0.5F, 1, 2, 1);
    this.legRight.func_78793_a(-1.0F, 22.0F, -1.05F);
  }
  
  public void func_78088_a(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    this.body.func_78785_a(scale);
    this.wingLeft.func_78785_a(scale);
    this.wingRight.func_78785_a(scale);
    this.tail.func_78785_a(scale);
    this.head.func_78785_a(scale);
    this.legLeft.func_78785_a(scale);
    this.legRight.func_78785_a(scale);
  }
  
  public void func_78087_a(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    float f = ageInTicks * 0.3F;
    this.head.field_78795_f = headPitch * 0.017453292F;
    this.head.field_78796_g = netHeadYaw * 0.017453292F;
    this.head.field_78808_h = 0.0F;
    this.head.field_78800_c = 0.0F;
    this.body.field_78800_c = 0.0F;
    this.tail.field_78800_c = 0.0F;
    this.wingRight.field_78800_c = -1.5F;
    this.wingLeft.field_78800_c = 1.5F;
    if (this.state != State.FLYING) {
      if (this.state == State.SITTING)
        return; 
      if (this.state == State.PARTY) {
        float f1 = MathHelper.func_76134_b(entityIn.field_70173_aa);
        float f2 = MathHelper.func_76126_a(entityIn.field_70173_aa);
        this.head.field_78800_c = f1;
        this.head.field_78797_d = 15.69F + f2;
        this.head.field_78795_f = 0.0F;
        this.head.field_78796_g = 0.0F;
        this.head.field_78808_h = MathHelper.func_76126_a(entityIn.field_70173_aa) * 0.4F;
        this.body.field_78800_c = f1;
        this.body.field_78797_d = 16.5F + f2;
        this.wingLeft.field_78808_h = -0.0873F - ageInTicks;
        this.wingLeft.field_78800_c = 1.5F + f1;
        this.wingLeft.field_78797_d = 16.94F + f2;
        this.wingRight.field_78808_h = 0.0873F + ageInTicks;
        this.wingRight.field_78800_c = -1.5F + f1;
        this.wingRight.field_78797_d = 16.94F + f2;
        this.tail.field_78800_c = f1;
        this.tail.field_78797_d = 21.07F + f2;
        return;
      } 
      this.legLeft.field_78795_f += MathHelper.func_76134_b(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
      this.legRight.field_78795_f += MathHelper.func_76134_b(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
    } 
    this.head.field_78797_d = 15.69F + f;
    this.tail.field_78795_f = 1.015F + MathHelper.func_76134_b(limbSwing * 0.6662F) * 0.3F * limbSwingAmount;
    this.tail.field_78797_d = 21.07F + f;
    this.body.field_78797_d = 16.5F + f;
    this.wingLeft.field_78808_h = -0.0873F - ageInTicks;
    this.wingLeft.field_78797_d = 16.94F + f;
    this.wingRight.field_78808_h = 0.0873F + ageInTicks;
    this.wingRight.field_78797_d = 16.94F + f;
    this.legLeft.field_78797_d = 22.0F + f;
    this.legRight.field_78797_d = 22.0F + f;
  }
  
  public void func_78086_a(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
    this.feather.field_78795_f = -0.2214F;
    this.body.field_78795_f = 0.4937F;
    this.wingLeft.field_78795_f = -0.69813174F;
    this.wingLeft.field_78796_g = -3.1415927F;
    this.wingRight.field_78795_f = -0.69813174F;
    this.wingRight.field_78796_g = -3.1415927F;
    this.legLeft.field_78795_f = -0.0299F;
    this.legRight.field_78795_f = -0.0299F;
    this.legLeft.field_78797_d = 22.0F;
    this.legRight.field_78797_d = 22.0F;
    if (entitylivingbaseIn instanceof EntityParrot) {
      EntityParrot entityparrot = (EntityParrot)entitylivingbaseIn;
      if (entityparrot.jukeBoxToDanceTo != null) {
        this.legLeft.field_78808_h = -0.34906584F;
        this.legRight.field_78808_h = 0.34906584F;
        this.state = State.PARTY;
        return;
      } 
      if (entityparrot.func_70093_af()) {
        this.head.field_78797_d = 17.59F;
        this.tail.field_78795_f = 1.5388988F;
        this.tail.field_78797_d = 22.97F;
        this.body.field_78797_d = 18.4F;
        this.wingLeft.field_78808_h = -0.0873F;
        this.wingLeft.field_78797_d = 18.84F;
        this.wingRight.field_78808_h = 0.0873F;
        this.wingRight.field_78797_d = 18.84F;
        this.legLeft.field_78797_d++;
        this.legRight.field_78797_d++;
        this.legLeft.field_78795_f++;
        this.legRight.field_78795_f++;
        this.state = State.SITTING;
      } else if (entityparrot.isFlying()) {
        this.legLeft.field_78795_f += 0.69813174F;
        this.legRight.field_78795_f += 0.69813174F;
        this.state = State.FLYING;
      } else {
        this.state = State.STANDING;
      } 
      this.legLeft.field_78808_h = 0.0F;
      this.legRight.field_78808_h = 0.0F;
    } 
  }
  
  @SideOnly(Side.CLIENT)
  enum State {
    FLYING, STANDING, SITTING, PARTY;
  }
}
