package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelMutantCreeper extends ModelBase {
  private final ModelRenderer pelvis;
  
  private final ModelRenderer body;
  
  private final ModelRenderer neck;
  
  private final ModelRenderer head;
  
  private final ModelRenderer frleg;
  
  private final ModelRenderer flleg;
  
  private final ModelRenderer frforeleg;
  
  private final ModelRenderer flforeleg;
  
  private final ModelRenderer brleg;
  
  private final ModelRenderer blleg;
  
  private final ModelRenderer brforeleg;
  
  private final ModelRenderer blforeleg;
  
  public ModelMutantCreeper() {
    this(0.0F);
  }
  
  public ModelMutantCreeper(float scale) {
    this.field_78090_t = 128;
    this.field_78089_u = 64;
    this.pelvis = new ModelRenderer(this, 0, 0);
    this.pelvis.func_78790_a(-5.0F, -14.0F, -4.0F, 10, 14, 8, scale);
    this.pelvis.func_78793_a(0.0F, 14.0F, -3.0F);
    this.body = new ModelRenderer(this, 36, 0);
    this.body.func_78790_a(-4.5F, -14.0F, -3.5F, 9, 16, 7, scale);
    this.body.func_78793_a(0.0F, -12.0F, 0.0F);
    this.pelvis.func_78792_a(this.body);
    this.neck = new ModelRenderer(this, 68, 0);
    this.neck.func_78790_a(-4.0F, -14.0F, -3.0F, 8, 14, 6, scale);
    this.neck.func_78793_a(0.0F, -11.0F, 1.0F);
    this.body.func_78792_a(this.neck);
    this.head = new ModelRenderer(this, 0, 22);
    this.head.func_78790_a(-5.0F, -12.0F, -5.0F, 10, 12, 10, scale);
    this.head.func_78793_a(0.0F, -12.0F, 1.0F);
    this.neck.func_78792_a(this.head);
    this.frleg = new ModelRenderer(this, 40, 24);
    this.frleg.func_78790_a(-3.0F, -4.0F, -14.0F, 6, 4, 14, scale);
    this.frleg.func_78793_a(3.0F, 0.0F, 0.0F);
    this.pelvis.func_78792_a(this.frleg);
    this.flleg = new ModelRenderer(this, 40, 24);
    this.flleg.field_78809_i = true;
    this.flleg.func_78790_a(-3.0F, -4.0F, -14.0F, 6, 4, 14, scale);
    this.flleg.func_78793_a(-3.0F, 0.0F, 0.0F);
    this.pelvis.func_78792_a(this.flleg);
    this.frforeleg = new ModelRenderer(this, 96, 0);
    this.frforeleg.func_78790_a(-3.5F, 0.0F, -4.0F, 7, 20, 8, scale);
    this.frforeleg.func_78793_a(0.0F, -4.0F, -14.0F);
    this.frleg.func_78792_a(this.frforeleg);
    this.flforeleg = new ModelRenderer(this, 96, 0);
    this.flforeleg.field_78809_i = true;
    this.flforeleg.func_78790_a(-3.5F, 0.0F, -4.0F, 7, 20, 8, scale);
    this.flforeleg.func_78793_a(0.0F, -4.0F, -14.0F);
    this.flleg.func_78792_a(this.flforeleg);
    this.brleg = new ModelRenderer(this, 0, 44);
    this.brleg.func_78790_a(-2.0F, -4.0F, 0.0F, 4, 4, 14, scale);
    this.brleg.func_78793_a(2.0F, -2.0F, 4.0F);
    this.pelvis.func_78792_a(this.brleg);
    this.blleg = new ModelRenderer(this, 0, 44);
    this.blleg.field_78809_i = true;
    this.blleg.func_78790_a(-2.0F, -4.0F, 0.0F, 4, 4, 14, scale);
    this.blleg.func_78793_a(-2.0F, -2.0F, 4.0F);
    this.pelvis.func_78792_a(this.blleg);
    this.brforeleg = new ModelRenderer(this, 80, 28);
    this.brforeleg.func_78790_a(-3.0F, 0.0F, -3.0F, 6, 18, 6, scale);
    this.brforeleg.func_78793_a(0.0F, -4.0F, 14.0F);
    this.brleg.func_78792_a(this.brforeleg);
    this.blforeleg = new ModelRenderer(this, 80, 28);
    this.blforeleg.field_78809_i = true;
    this.blforeleg.func_78790_a(-3.0F, 0.0F, -3.0F, 6, 18, 6, scale);
    this.blforeleg.func_78793_a(0.0F, -4.0F, 14.0F);
    this.blleg.func_78792_a(this.blforeleg);
  }
  
  public void func_78088_a(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    setAngles();
    setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    this.pelvis.func_78785_a(scale);
  }
  
  private void setAngles() {
    this.pelvis.field_78797_d = 14.0F;
    this.pelvis.field_78795_f = -0.7853982F;
    this.body.field_78795_f = 0.9424778F;
    this.body.field_78796_g = 0.0F;
    this.neck.field_78795_f = 1.0471976F;
    this.head.field_78795_f = 0.5235988F;
    this.frleg.field_78795_f = 0.31415927F;
    this.frleg.field_78796_g = -0.7853982F;
    this.frleg.field_78808_h = 0.0F;
    this.flleg.field_78795_f = 0.31415927F;
    this.flleg.field_78796_g = 0.7853982F;
    this.flleg.field_78808_h = 0.0F;
    this.frforeleg.field_78795_f = -0.20943952F;
    this.frforeleg.field_78796_g = 0.3926991F;
    this.flforeleg.field_78795_f = -0.20943952F;
    this.flforeleg.field_78796_g = -0.3926991F;
    this.brleg.field_78795_f = 0.9F;
    this.brleg.field_78796_g = 0.62831855F;
    this.brleg.field_78808_h = 0.0F;
    this.blleg.field_78795_f = 0.9F;
    this.blleg.field_78796_g = -0.62831855F;
    this.blleg.field_78808_h = 0.0F;
    this.brforeleg.field_78795_f = 0.48332196F;
    this.blforeleg.field_78795_f = 0.48332196F;
  }
  
  private void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
    float breatheAnim = MathHelper.func_76126_a(f2 * 0.1F);
    float walkAnim1 = (MathHelper.func_76126_a(f * 3.1415927F / 4.0F) + 0.4F) * f1;
    float walkAnim2 = (MathHelper.func_76126_a(f * 3.1415927F / 4.0F + 3.1415927F) + 0.4F) * f1;
    if (walkAnim1 < 0.0F)
      walkAnim1 = 0.0F; 
    if (walkAnim2 < 0.0F)
      walkAnim2 = 0.0F; 
    float walkAnim3 = MathHelper.func_76126_a(f * 3.1415927F / 8.0F) * f1;
    float walkAnim4 = (MathHelper.func_76126_a(f * 3.1415927F / 4.0F + 1.5707964F) + 0.4F) * f1;
    float walkAnim5 = (MathHelper.func_76126_a(f * 3.1415927F / 4.0F + 4.712389F) + 0.4F) * f1;
    if (walkAnim4 < 0.0F)
      walkAnim4 = 0.0F; 
    if (walkAnim5 < 0.0F)
      walkAnim5 = 0.0F; 
    float walkAnim6 = MathHelper.func_76126_a(f * 3.1415927F / 8.0F + 1.5707964F) * f1;
    float faceYaw = f3 / 57.295776F;
    float facePitch = f4 / 57.295776F;
    float f6 = faceYaw / 3.0F;
    float f7 = facePitch / 3.0F;
    this.pelvis.field_78797_d += MathHelper.func_76126_a(f * 3.1415927F / 4.0F) * f1 * 0.5F;
    this.body.field_78795_f += breatheAnim * 0.02F;
    this.body.field_78795_f += f7;
    this.body.field_78796_g += f6;
    this.neck.field_78795_f += breatheAnim * 0.02F;
    this.neck.field_78795_f += f7;
    this.neck.field_78796_g = f6;
    this.head.field_78795_f += breatheAnim * 0.02F;
    this.head.field_78795_f += f7;
    this.head.field_78796_g = f6;
    this.frleg.field_78795_f -= walkAnim1 * 0.3F;
    this.frleg.field_78796_g += walkAnim3 * 0.2F;
    this.frleg.field_78808_h += walkAnim3 * 0.2F;
    this.flleg.field_78795_f -= walkAnim2 * 0.3F;
    this.flleg.field_78796_g -= walkAnim3 * 0.2F;
    this.flleg.field_78808_h -= walkAnim3 * 0.2F;
    this.brleg.field_78795_f += walkAnim5 * 0.3F;
    this.brleg.field_78796_g -= walkAnim6 * 0.2F;
    this.brleg.field_78808_h -= walkAnim6 * 0.2F;
    this.blleg.field_78795_f += walkAnim4 * 0.3F;
    this.blleg.field_78796_g += walkAnim6 * 0.2F;
    this.blleg.field_78808_h += walkAnim6 * 0.2F;
    if (this.field_78095_p > -9990.0F) {
      float swingAnim = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F);
      this.body.field_78795_f += swingAnim * 3.1415927F / 3.0F;
      this.neck.field_78795_f -= swingAnim * 3.1415927F / 4.0F;
    } 
  }
}
