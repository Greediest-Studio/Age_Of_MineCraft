package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelSnowMan extends ModelBase implements ICappedModel {
  public ModelRenderer body;
  
  public ModelRenderer bottomBody;
  
  public ModelRenderer head;
  
  public ModelRenderer rightHand;
  
  public ModelRenderer leftHand;
  
  public ModelRenderer bipedCape;
  
  public ModelSnowMan() {
    this.head = (new ModelRenderer(this, 0, 0)).func_78787_b(64, 64);
    this.head.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, -0.5F);
    this.head.func_78793_a(0.0F, 4.0F, 0.0F);
    this.rightHand = (new ModelRenderer(this, 32, 0)).func_78787_b(64, 64);
    this.rightHand.func_78790_a(-1.0F, 0.0F, -1.0F, 12, 2, 2, -0.5F);
    this.rightHand.func_78793_a(0.0F, 6.0F, 0.0F);
    this.leftHand = (new ModelRenderer(this, 32, 0)).func_78787_b(64, 64);
    this.leftHand.func_78790_a(-1.0F, 0.0F, -1.0F, 12, 2, 2, -0.5F);
    this.leftHand.func_78793_a(0.0F, 6.0F, 0.0F);
    this.body = (new ModelRenderer(this, 0, 16)).func_78787_b(64, 64);
    this.body.func_78790_a(-5.0F, -10.0F, -5.0F, 10, 10, 10, -0.5F);
    this.body.func_78793_a(0.0F, 13.0F, 0.0F);
    this.bottomBody = (new ModelRenderer(this, 0, 36)).func_78787_b(64, 64);
    this.bottomBody.func_78790_a(-6.0F, -12.0F, -6.0F, 12, 12, 12, -0.5F);
    this.bottomBody.func_78793_a(0.0F, 24.0F, 0.0F);
    this.bipedCape = new ModelRenderer(this, 0, 0);
    this.bipedCape.func_78787_b(64, 32);
    this.bipedCape.func_78790_a(-5.0F, 0.0F, -1.0F, 10, 16, 1, 0.0F);
    this.rightHand.field_78808_h = 1.0F;
    this.leftHand.field_78808_h = -1.0F;
    this.rightHand.field_78796_g = 0.0F + this.body.field_78796_g;
    this.leftHand.field_78796_g = 3.1415927F + this.body.field_78796_g;
  }
  
  public void func_78087_a(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    super.func_78087_a(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    this.head.field_78796_g = netHeadYaw * 0.017453292F;
    this.head.field_78795_f = headPitch * 0.017453292F;
    this.body.field_78796_g = netHeadYaw * 0.017453292F * 0.25F;
    float f = MathHelper.func_76126_a(this.body.field_78796_g);
    float f1 = MathHelper.func_76134_b(this.body.field_78796_g);
    this.rightHand.field_78808_h = 1.0F;
    this.leftHand.field_78808_h = -1.0F;
    this.rightHand.field_78796_g = 0.0F + this.body.field_78796_g;
    this.leftHand.field_78796_g = 3.1415927F + this.body.field_78796_g;
    this.rightHand.field_78800_c = f1 * 5.0F;
    this.rightHand.field_78798_e = -f * 5.0F;
    this.leftHand.field_78800_c = -f1 * 5.0F;
    this.leftHand.field_78798_e = f * 5.0F;
  }
  
  public void renderCape(float scale, float flo1, float flo2, float flo3) {
    GlStateManager.func_179094_E();
    GlStateManager.func_179109_b(0.0F, 0.25F, 0.125F);
    GlStateManager.func_179114_b(6.0F + flo2 / 2.0F + flo1, 1.0F, 0.0F, 0.0F);
    GlStateManager.func_179114_b(flo3 / 2.0F, 0.0F, 0.0F, 1.0F);
    GlStateManager.func_179114_b(-flo3 / 2.0F, 0.0F, 1.0F, 0.0F);
    GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
    this.bipedCape.func_78785_a(scale);
    GlStateManager.func_179121_F();
  }
  
  public void func_78088_a(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    func_78087_a(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
    this.body.func_78785_a(scale);
    this.bottomBody.func_78785_a(scale);
    this.head.func_78785_a(scale);
    this.rightHand.func_78785_a(scale);
    this.leftHand.func_78785_a(scale);
  }
}
