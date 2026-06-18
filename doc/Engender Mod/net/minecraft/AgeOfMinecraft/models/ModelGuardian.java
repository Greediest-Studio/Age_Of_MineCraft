package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGuardian;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelGuardian extends ModelBase {
  public ModelRenderer guardianBody;
  
  private ModelRenderer guardianEye;
  
  private ModelRenderer[] guardianSpines;
  
  private ModelRenderer[] guardianTail;
  
  public ModelGuardian() {
    this.field_78090_t = 64;
    this.field_78089_u = 64;
    this.guardianSpines = new ModelRenderer[12];
    this.guardianBody = new ModelRenderer(this);
    this.guardianBody.func_78784_a(0, 0).func_78789_a(-6.0F, -6.0F, -8.0F, 12, 12, 16);
    this.guardianBody.func_78784_a(0, 28).func_78789_a(-8.0F, -6.0F, -6.0F, 2, 12, 12);
    this.guardianBody.func_78784_a(0, 28).func_178769_a(6.0F, -6.0F, -6.0F, 2, 12, 12, true);
    this.guardianBody.func_78784_a(16, 40).func_78789_a(-6.0F, -8.0F, -6.0F, 12, 2, 12);
    this.guardianBody.func_78784_a(16, 40).func_78789_a(-6.0F, 6.0F, -6.0F, 12, 2, 12);
    for (int i = 0; i < this.guardianSpines.length; i++) {
      this.guardianSpines[i] = new ModelRenderer(this, 0, 0);
      this.guardianSpines[i].func_78789_a(-1.0F, -8.5F, -1.0F, 2, 9, 2);
      this.guardianBody.func_78792_a(this.guardianSpines[i]);
    } 
    this.guardianEye = new ModelRenderer(this, 8, 0);
    this.guardianEye.func_78789_a(-1.0F, -1.0F, 0.0F, 2, 2, 1);
    this.guardianBody.func_78792_a(this.guardianEye);
    this.guardianTail = new ModelRenderer[3];
    this.guardianTail[0] = new ModelRenderer(this, 40, 0);
    this.guardianTail[0].func_78789_a(-2.0F, -2.0F, 7.0F, 4, 4, 8);
    this.guardianTail[1] = new ModelRenderer(this, 0, 54);
    this.guardianTail[1].func_78789_a(0.0F, -2.0F, 0.0F, 3, 3, 7);
    this.guardianTail[2] = new ModelRenderer(this);
    this.guardianTail[2].func_78784_a(41, 32).func_78789_a(0.0F, -2.0F, 0.0F, 2, 2, 6);
    this.guardianTail[2].func_78784_a(25, 19).func_78789_a(1.0F, -5.5F, 3.0F, 1, 9, 9);
    this.guardianBody.func_78792_a(this.guardianTail[0]);
    this.guardianTail[0].func_78792_a(this.guardianTail[1]);
    this.guardianTail[1].func_78792_a(this.guardianTail[2]);
  }
  
  public int func_178706_a() {
    return 54;
  }
  
  public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
    func_78087_a(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
    this.guardianBody.func_78785_a(p_78088_7_);
  }
  
  public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
    EntityGuardian entityguardian = (EntityGuardian)p_78087_7_;
    float f6 = p_78087_3_ - entityguardian.field_70173_aa;
    this.guardianBody.field_78796_g = p_78087_4_ / 57.295776F;
    this.guardianBody.field_78795_f = !entityguardian.func_70089_S() ? 0.0F : (p_78087_5_ / 57.295776F);
    float[] afloat = { 
        1.75F, 0.25F, 0.0F, 0.0F, 0.5F, 0.5F, 0.5F, 0.5F, 1.25F, 0.75F, 
        0.0F, 0.0F };
    float[] afloat1 = { 
        0.0F, 0.0F, 0.0F, 0.0F, 0.25F, 1.75F, 1.25F, 0.75F, 0.0F, 0.0F, 
        0.0F, 0.0F };
    float[] afloat2 = { 
        0.0F, 0.0F, 0.25F, 1.75F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 
        0.75F, 1.25F };
    float[] afloat3 = { 
        0.0F, 0.0F, 8.0F, -8.0F, -8.0F, 8.0F, 8.0F, -8.0F, 0.0F, 0.0F, 
        8.0F, -8.0F };
    float[] afloat4 = { 
        -8.0F, -8.0F, -8.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 
        8.0F, 8.0F };
    float[] afloat5 = { 
        8.0F, -8.0F, 0.0F, 0.0F, -8.0F, -8.0F, 8.0F, 8.0F, 8.0F, -8.0F, 
        0.0F, 0.0F };
    float f7 = (1.0F - (entityguardian.func_175446_cd() ? 0.5F : entityguardian.func_175469_o(f6))) * 0.55F;
    for (int i = 0; i < 12; i++) {
      (this.guardianSpines[i]).field_78795_f = 3.1415927F * afloat[i];
      (this.guardianSpines[i]).field_78796_g = 3.1415927F * afloat1[i];
      (this.guardianSpines[i]).field_78808_h = 3.1415927F * afloat2[i];
      (this.guardianSpines[i]).field_78800_c = afloat3[i] * (1.0F + MathHelper.func_76134_b((entityguardian.func_175446_cd() ? 0.0F : p_78087_3_) * 1.5F + i) * 0.01F - f7);
      (this.guardianSpines[i]).field_78797_d = afloat4[i] * (1.0F + MathHelper.func_76134_b((entityguardian.func_175446_cd() ? 0.0F : p_78087_3_) * 1.5F + i) * 0.01F - f7);
      (this.guardianSpines[i]).field_78798_e = afloat5[i] * (1.0F + MathHelper.func_76134_b((entityguardian.func_175446_cd() ? 0.0F : p_78087_3_) * 1.5F + i) * 0.01F - f7);
    } 
    this.guardianEye.field_78798_e = -8.25F;
    Object object = Minecraft.func_71410_x().func_175606_aa();
    if (entityguardian.hasTargetedEntity())
      object = entityguardian.getTargetedEntity(); 
    if (object != null) {
      Vec3d vec3 = ((Entity)object).func_174824_e(0.0F);
      Vec3d vec31 = p_78087_7_.func_174824_e(0.0F);
      double d0 = vec3.field_72448_b - vec31.field_72448_b;
      if (d0 <= 0.0D || !entityguardian.getCurrentBook().func_190926_b() || entityguardian.func_175446_cd()) {
        this.guardianEye.field_78797_d = 1.0F;
      } else {
        this.guardianEye.field_78797_d = 0.0F;
      } 
      Vec3d vec32 = p_78087_7_.func_70676_i(0.0F);
      vec32 = new Vec3d(vec32.field_72450_a, 0.0D, vec32.field_72449_c);
      Vec3d vec33 = (new Vec3d(vec31.field_72450_a - vec3.field_72450_a, 0.0D, vec31.field_72449_c - vec3.field_72449_c)).func_72432_b().func_178785_b(1.5707964F);
      double d1 = vec32.func_72430_b(vec33);
      this.guardianEye.field_78800_c = entityguardian.func_175446_cd() ? 0.0F : (!entityguardian.getCurrentBook().func_190926_b() ? (MathHelper.func_76126_a(p_78087_3_ * 0.1F + MathHelper.func_76126_a(p_78087_3_ * 0.05F)) * 1.5F) : (MathHelper.func_76129_c((float)Math.abs(d1)) * 2.0F * (float)Math.signum(d1)));
    } 
    this.guardianEye.field_78806_j = true;
    float f8 = entityguardian.func_175471_a(f6);
    (this.guardianTail[0]).field_78795_f = MathHelper.func_76134_b(p_78087_3_ * 0.05F) * 0.05F;
    (this.guardianTail[0]).field_78796_g = MathHelper.func_76126_a(f8) * 3.1415927F * 0.05F;
    (this.guardianTail[1]).field_78796_g = MathHelper.func_76126_a(f8) * 3.1415927F * 0.1F;
    (this.guardianTail[1]).field_78800_c = -1.5F;
    (this.guardianTail[1]).field_78797_d = 0.5F;
    (this.guardianTail[1]).field_78798_e = 14.0F;
    (this.guardianTail[2]).field_78796_g = MathHelper.func_76126_a(f8) * 3.1415927F * 0.15F;
    (this.guardianTail[2]).field_78800_c = 0.5F;
    (this.guardianTail[2]).field_78797_d = 0.5F;
    (this.guardianTail[2]).field_78798_e = 6.0F;
    if (entityguardian.getJukeboxToDanceTo() != null) {
      this.guardianBody.field_78796_g += MathHelper.func_76126_a(p_78087_3_ * 0.25F) * 0.125F;
      for (int k = 0; k < this.guardianTail.length; k++)
        (this.guardianTail[k]).field_78796_g = MathHelper.func_76126_a((-1 - k) + (entityguardian.func_175446_cd() ? 1.0F : p_78087_3_) * 0.25F) * 0.5F; 
    } 
  }
}
