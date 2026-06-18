package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.model;

import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntitySpiderPig;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelSpiderPig extends ModelBase {
  private final ModelRenderer snout;
  
  private final JointModelRenderer head;
  
  private final ModelRenderer base;
  
  private final ModelRenderer body1;
  
  private final ModelRenderer body2;
  
  private final ModelRenderer butt;
  
  private final JointModelRenderer frontLeg1;
  
  private final JointModelRenderer frontLegF1;
  
  private final JointModelRenderer frontLeg2;
  
  private final JointModelRenderer frontLegF2;
  
  private final JointModelRenderer middleLeg1;
  
  private final JointModelRenderer middleLegF1;
  
  private final JointModelRenderer middleLeg2;
  
  private final JointModelRenderer middleLegF2;
  
  private final JointModelRenderer backLeg1;
  
  private final JointModelRenderer backLegF1;
  
  private final JointModelRenderer backLeg2;
  
  private final JointModelRenderer backLegF2;
  
  public ModelSpiderPig(float scale) {
    this.field_78090_t = 128;
    this.field_78089_u = 64;
    this.base = new ModelRenderer(this);
    this.base.func_78793_a(0.0F, 14.5F, -2.0F);
    this.body2 = new ModelRenderer(this, 32, 0);
    this.body2.func_78789_a(-3.0F, -3.0F, 0.0F, 6, 6, 10);
    this.body2.func_78784_a(44, 16).func_78790_a(-5.0F, -5.0F, -4.0F, 10, 8, 12, scale);
    this.base.func_78792_a(this.body2);
    this.body1 = new JointModelRenderer(this, 64, 0);
    this.body1.func_78789_a(-3.5F, -3.5F, -9.0F, 7, 7, 9);
    this.body1.func_78793_a(0.0F, -1.0F, 1.5F);
    this.body2.func_78792_a(this.body1);
    this.butt = new ModelRenderer(this, 0, 16);
    this.butt.func_78789_a(-5.0F, -4.5F, 0.0F, 10, 9, 12);
    this.butt.func_78793_a(0.0F, 0.0F, 7.0F);
    this.body2.func_78792_a(this.butt);
    this.head = new JointModelRenderer(this, 0, 0);
    this.head.func_78789_a(-4.0F, -4.0F, -8.0F, 8, 8, 8);
    this.head.func_78793_a(0.0F, 0.0F, -8.0F);
    this.body1.func_78792_a(this.head);
    this.snout = new ModelRenderer(this, 24, 0);
    this.snout.func_78789_a(-2.0F, 0.0F, -9.0F, 4, 3, 1);
    this.head.func_78792_a(this.snout);
    this.frontLeg1 = new JointModelRenderer(this, 0, 37);
    this.frontLeg1.func_78789_a(-1.0F, 0.0F, -1.0F, 2, 12, 2);
    this.frontLeg1.func_78793_a(-3.5F, 0.0F, -5.0F);
    this.body1.func_78792_a(this.frontLeg1);
    this.frontLegF1 = new JointModelRenderer(this, 8, 37);
    this.frontLegF1.func_78789_a(-1.0F, 0.0F, -1.0F, 2, 16, 2);
    this.frontLegF1.func_78793_a(-0.0F, 12.0F, -0.1F);
    this.frontLeg1.func_78792_a(this.frontLegF1);
    this.frontLeg2 = new JointModelRenderer(this, 0, 37);
    this.frontLeg2.field_78809_i = true;
    this.frontLeg2.func_78789_a(-1.0F, 0.0F, -1.0F, 2, 12, 2);
    this.frontLeg2.func_78793_a(3.5F, 0.0F, -5.0F);
    this.body1.func_78792_a(this.frontLeg2);
    this.frontLegF2 = new JointModelRenderer(this, 8, 37);
    this.frontLegF2.field_78809_i = true;
    this.frontLegF2.func_78789_a(-1.0F, 0.0F, -1.0F, 2, 16, 2);
    this.frontLegF2.func_78793_a(0.0F, 12.0F, 0.1F);
    this.frontLeg2.func_78792_a(this.frontLegF2);
    this.middleLeg1 = new JointModelRenderer(this, 0, 37);
    this.middleLeg1.func_78789_a(-1.0F, 0.0F, -1.0F, 2, 12, 2);
    this.middleLeg1.func_78793_a(-3.5F, 0.0F, -3.0F);
    this.body1.func_78792_a(this.middleLeg1);
    this.middleLegF1 = new JointModelRenderer(this, 8, 37);
    this.middleLegF1.func_78789_a(-1.0F, 0.0F, -1.0F, 2, 16, 2);
    this.middleLegF1.func_78793_a(0.0F, 12.0F, -0.1F);
    this.middleLeg1.func_78792_a(this.middleLegF1);
    this.middleLeg2 = new JointModelRenderer(this, 0, 37);
    this.middleLeg2.field_78809_i = true;
    this.middleLeg2.func_78789_a(-1.0F, 0.0F, -1.0F, 2, 12, 2);
    this.middleLeg2.func_78793_a(3.5F, 0.0F, -3.0F);
    this.body1.func_78792_a(this.middleLeg2);
    this.middleLegF2 = new JointModelRenderer(this, 8, 37);
    this.middleLegF2.field_78809_i = true;
    this.middleLegF2.func_78789_a(-1.0F, 0.0F, -1.0F, 2, 16, 2);
    this.middleLegF2.func_78793_a(0.0F, 12.0F, 0.1F);
    this.middleLeg2.func_78792_a(this.middleLegF2);
    this.backLeg1 = new JointModelRenderer(this, 16, 37);
    this.backLeg1.func_78789_a(-2.0F, 0.0F, -2.0F, 4, 4, 4);
    this.backLeg1.func_78793_a(-2.5F, 2.0F, 7.0F);
    this.body2.func_78792_a(this.backLeg1);
    this.backLegF1 = new JointModelRenderer(this, 16, 45);
    this.backLegF1.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 4, 4, 0.2F);
    this.backLegF1.func_78793_a(0.0F, 3.0F, 0.0F);
    this.backLeg1.func_78792_a(this.backLegF1);
    this.backLeg2 = new JointModelRenderer(this, 32, 37);
    this.backLeg2.field_78809_i = true;
    this.backLeg2.func_78789_a(-2.0F, 0.0F, -2.0F, 4, 4, 4);
    this.backLeg2.func_78793_a(2.5F, 2.0F, 7.0F);
    this.body2.func_78792_a(this.backLeg2);
    this.backLegF2 = new JointModelRenderer(this, 16, 45);
    this.backLegF2.field_78809_i = true;
    this.backLegF2.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 4, 4, 0.2F);
    this.backLegF2.func_78793_a(0.0F, 3.0F, 0.0F);
    this.backLeg2.func_78792_a(this.backLegF2);
  }
  
  public void func_78088_a(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    setAngles();
    animate((EntitySpiderPig)entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    this.base.func_78785_a(scale);
  }
  
  public void setAngles() {
    resetAngles(new ModelRenderer[] { this.head, this.head.getModel(), this.body1, this.body2, this.butt });
    resetAngles(new ModelRenderer[] { this.frontLeg1, this.frontLeg1.getModel(), this.frontLegF1, this.frontLegF1.getModel(), this.frontLeg2, this.frontLeg2.getModel(), this.frontLegF2, this.frontLegF2.getModel() });
    resetAngles(new ModelRenderer[] { this.middleLeg1, this.middleLeg1.getModel(), this.middleLegF1, this.middleLegF1.getModel(), this.middleLeg2, this.middleLeg2.getModel(), this.middleLegF2, this.middleLegF2.getModel() });
    resetAngles(new ModelRenderer[] { this.backLeg1, this.backLeg1.getModel(), this.backLegF1, this.backLegF1.getModel(), this.backLeg2, this.backLeg2.getModel(), this.backLegF2, this.backLegF2.getModel() });
    this.body1.field_78795_f += 0.3926991F;
    this.body2.field_78795_f += -0.05235988F;
    this.butt.field_78795_f += 0.5711987F;
    this.head.field_78795_f += -0.3926991F;
    this.frontLeg1.field_78795_f += -(this.body1.field_78795_f + this.body2.field_78795_f);
    this.frontLeg1.field_78796_g += -1.0471976F;
    (this.frontLeg1.getModel()).field_78808_h += 2.0943952F;
    this.frontLegF1.field_78808_h += -1.6534699F;
    this.frontLeg2.field_78795_f += -(this.body1.field_78795_f + this.body2.field_78795_f);
    this.frontLeg2.field_78796_g++;
    (this.frontLeg2.getModel()).field_78808_h += -2.0943952F;
    this.frontLegF2.field_78808_h += 1.6534699F;
    this.middleLeg1.field_78795_f += -(this.body1.field_78795_f + this.body2.field_78795_f);
    this.middleLeg1.field_78796_g += -0.31415927F;
    (this.middleLeg1.getModel()).field_78808_h += 2.0399954F;
    this.middleLegF1.field_78808_h += -1.6534699F;
    this.middleLeg2.field_78795_f += -(this.body1.field_78795_f + this.body2.field_78795_f);
    this.middleLeg2.field_78796_g += 0.31415927F;
    (this.middleLeg2.getModel()).field_78808_h += -2.0399954F;
    this.middleLegF2.field_78808_h += 1.6534699F;
    this.backLeg1.field_78795_f += -0.3926991F;
    (this.backLeg1.getModel()).field_78808_h += 0.3926991F;
    this.backLegF1.field_78808_h += -0.3926991F;
    (this.backLegF1.getModel()).field_78795_f += 0.5711987F;
    this.backLeg2.field_78795_f += -0.3926991F;
    (this.backLeg2.getModel()).field_78808_h += -0.3926991F;
    this.backLegF2.field_78808_h += 0.3926991F;
    (this.backLegF2.getModel()).field_78795_f += 0.5711987F;
  }
  
  public void animate(EntitySpiderPig entity, float f, float f1, float f2, float f3, float f4, float f5) {
    float moveAnim = MathHelper.func_76126_a(f * 0.9F) * f1;
    float moveAnim1 = MathHelper.func_76126_a(f * 0.9F + 0.3F) * f1;
    float moveAnim1d = MathHelper.func_76126_a(f * 0.9F + 0.3F + 0.5F) * f1;
    float moveAnim2 = MathHelper.func_76126_a(f * 0.9F + 0.9F) * f1;
    float moveAnim2d = MathHelper.func_76126_a(f * 0.9F + 0.9F + 0.5F) * f1;
    float moveAnim3 = MathHelper.func_76126_a(f * 0.9F - 0.3F) * f1;
    float moveAnim3d = MathHelper.func_76126_a(f * 0.9F - 0.3F + 0.5F) * f1;
    float moveAnim4 = MathHelper.func_76126_a(f * 0.9F - 0.9F) * f1;
    float moveAnim4d = MathHelper.func_76126_a(f * 0.9F - 0.9F + 0.5F) * f1;
    float breatheAnim = MathHelper.func_76126_a(f2 * 0.2F);
    float faceYaw = f3 * 3.1415927F / 180.0F;
    float facePitch = f4 * 3.1415927F / 180.0F;
    this.head.field_78795_f += breatheAnim * 0.02F;
    this.body1.field_78795_f += breatheAnim * 0.005F;
    this.butt.field_78795_f += -breatheAnim * 0.015F;
    (this.head.getModel()).field_78795_f += facePitch;
    (this.head.getModel()).field_78796_g += faceYaw;
    (this.frontLeg1.getModel()).field_78808_h += -moveAnim1 * 3.1415927F / 6.0F;
    (this.frontLeg1.getModel()).field_78795_f += -0.3926991F * f1;
    this.frontLegF1.field_78808_h += moveAnim1d * 3.1415927F / 6.0F + 0.2617994F * f1;
    (this.frontLeg2.getModel()).field_78808_h += moveAnim2 * 3.1415927F / 6.0F;
    (this.frontLeg2.getModel()).field_78795_f += -0.3926991F * f1;
    this.frontLegF2.field_78808_h += -(moveAnim2d * 3.1415927F / 6.0F + 0.2617994F * f1);
    (this.middleLeg1.getModel()).field_78808_h += -moveAnim3 * 3.1415927F / 6.0F;
    (this.middleLeg1.getModel()).field_78795_f += -0.8975979F * f1;
    this.middleLegF1.field_78808_h += moveAnim3d * 3.1415927F / 6.0F + 0.3926991F * f1;
    (this.middleLeg2.getModel()).field_78808_h += moveAnim4 * 3.1415927F / 6.0F;
    (this.middleLeg2.getModel()).field_78795_f += -0.8975979F * f1;
    this.middleLegF2.field_78808_h += -(moveAnim4d * 3.1415927F / 6.0F + 0.3926991F * f1);
    this.backLeg1.field_78795_f += -moveAnim4 * 3.1415927F / 5.0F + 0.2617994F * f1;
    this.backLeg2.field_78795_f += -moveAnim1 * 3.1415927F / 5.0F + 0.2617994F * f1;
    this.body2.field_78795_f += -moveAnim * 3.1415927F / 20.0F;
    this.head.field_78795_f += moveAnim * 3.1415927F / 20.0F;
  }
  
  public void func_78087_a(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {}
  
  public static void resetAngles(ModelRenderer... boxes) {
    for (ModelRenderer box : boxes) {
      box.field_78795_f = 0.0F;
      box.field_78796_g = 0.0F;
      box.field_78808_h = 0.0F;
    } 
  }
}
