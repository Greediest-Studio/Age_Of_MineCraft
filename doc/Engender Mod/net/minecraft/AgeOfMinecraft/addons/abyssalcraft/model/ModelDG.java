package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.model;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class ModelDG extends ModelBase {
  public boolean isSneak;
  
  public ModelRenderer Head;
  
  public ModelRenderer jaw;
  
  public ModelRenderer tooth1;
  
  public ModelRenderer tooth2;
  
  public ModelRenderer tooth3;
  
  public ModelRenderer tooth4;
  
  public ModelRenderer tooth5;
  
  public ModelRenderer Spine1;
  
  public ModelRenderer Spine2;
  
  public ModelRenderer lrib1;
  
  public ModelRenderer lrib2;
  
  public ModelRenderer lrib3;
  
  public ModelRenderer rrib1;
  
  public ModelRenderer rrib2;
  
  public ModelRenderer rrib3;
  
  public ModelRenderer pelvis;
  
  public ModelRenderer Spine3;
  
  public ModelRenderer larm1;
  
  public ModelRenderer larm2;
  
  public ModelRenderer clawl1;
  
  public ModelRenderer clawl2;
  
  public ModelRenderer clawl3;
  
  public ModelRenderer clawl4;
  
  public ModelRenderer rarm1;
  
  public ModelRenderer rarm2;
  
  public ModelRenderer clawr1;
  
  public ModelRenderer clawr2;
  
  public ModelRenderer clawr3;
  
  public ModelRenderer clawr4;
  
  public ModelRenderer lleg;
  
  public ModelRenderer rleg;
  
  public ModelRenderer back;
  
  public ModelRenderer lside;
  
  public ModelRenderer rside;
  
  public ModelDG() {
    this(0.0F);
  }
  
  public ModelDG(float f) {
    this.field_78090_t = 128;
    this.field_78089_u = 64;
    this.Head = new ModelRenderer(this, 0, 0);
    this.Head.func_78787_b(128, 64);
    this.Head.func_78790_a(-4.5F, -9.5F, -4.5F, 9, 9, 9, f);
    this.Head.func_78793_a(0.0F, -17.0F, 1.0F);
    this.jaw = new ModelRenderer(this, 36, 0);
    this.jaw.func_78787_b(128, 64);
    this.jaw.func_78790_a(-4.5F, -0.5F, -4.5F, 9, 1, 9, f);
    this.jaw.func_78793_a(0.0F, 1.0F, 0.0F);
    setRotation(this.jaw, 0.2365561F, 0.0F, 0.0F);
    this.Head.func_78792_a(this.jaw);
    this.tooth1 = new ModelRenderer(this, 48, 11);
    this.tooth1.func_78787_b(128, 64);
    this.tooth1.func_78790_a(-0.5F, -1.0F, -0.5F, 1, 2, 1, f);
    this.tooth1.func_78793_a(-4.0F, -1.5F, -4.0F);
    this.jaw.func_78792_a(this.tooth1);
    this.tooth2 = new ModelRenderer(this, 48, 11);
    this.tooth2.func_78787_b(128, 64);
    this.tooth2.func_78790_a(-0.5F, -1.0F, -0.5F, 1, 2, 1, f);
    this.tooth2.func_78793_a(-2.0F, -1.5F, -4.0F);
    this.jaw.func_78792_a(this.tooth2);
    this.tooth3 = new ModelRenderer(this, 48, 11);
    this.tooth3.func_78787_b(128, 64);
    this.tooth3.func_78790_a(-0.5F, -1.0F, -0.5F, 1, 2, 1, f);
    this.tooth3.func_78793_a(0.0F, -1.5F, -4.0F);
    this.jaw.func_78792_a(this.tooth3);
    this.tooth4 = new ModelRenderer(this, 48, 11);
    this.tooth4.func_78787_b(128, 64);
    this.tooth4.func_78790_a(-0.5F, -1.0F, -0.5F, 1, 2, 1, f);
    this.tooth4.func_78793_a(2.0F, -1.5F, -4.0F);
    this.jaw.func_78792_a(this.tooth4);
    this.tooth5 = new ModelRenderer(this, 48, 11);
    this.tooth5.func_78787_b(128, 64);
    this.tooth5.func_78790_a(-0.5F, -1.0F, -0.5F, 1, 2, 1, f);
    this.tooth5.func_78793_a(4.0F, -1.5F, -4.0F);
    this.jaw.func_78792_a(this.tooth5);
    this.Spine1 = new ModelRenderer(this, 0, 42);
    this.Spine1.func_78787_b(128, 64);
    this.Spine1.func_78790_a(-2.5F, -4.0F, -3.0F, 5, 8, 6, f);
    this.Spine1.func_78793_a(0.0F, -14.0F, 2.0F);
    setRotation(this.Spine1, 0.2590069F, 0.0F, 0.0F);
    this.Spine2 = new ModelRenderer(this, 0, 42);
    this.Spine2.func_78787_b(128, 64);
    this.Spine2.func_78790_a(-2.5F, -8.0F, -3.0F, 5, 16, 6, f);
    this.Spine2.func_78793_a(0.0F, -2.854666F, 2.884038F);
    this.lrib1 = new ModelRenderer(this, 101, 47);
    this.lrib1.func_78787_b(128, 64);
    this.lrib1.func_78790_a(-1.5F, -1.0F, -1.0F, 3, 2, 2, f);
    this.lrib1.func_78793_a(4.0F, -6.977365F, 2.8262F);
    setRotation(this.lrib1, -0.07023507F, 0.0F, 0.0F);
    this.lrib2 = new ModelRenderer(this, 101, 47);
    this.lrib2.func_78787_b(128, 64);
    this.lrib2.func_78790_a(-1.5F, -1.0F, -1.0F, 3, 2, 2, f);
    this.lrib2.func_78793_a(4.0F, -3.821312F, 2.627918F);
    setRotation(this.lrib2, -0.07023507F, 0.0F, 0.0F);
    this.lrib3 = new ModelRenderer(this, 101, 47);
    this.lrib3.func_78787_b(128, 64);
    this.lrib3.func_78790_a(-1.5F, -1.0F, -1.0F, 3, 2, 2, f);
    this.lrib3.func_78793_a(4.0F, -0.6646652F, 2.434038F);
    setRotation(this.lrib3, -0.07023507F, 0.0F, 0.0F);
    this.rrib1 = new ModelRenderer(this, 101, 47);
    this.rrib1.func_78787_b(128, 64);
    this.rrib1.func_78790_a(-1.5F, -1.0F, -1.0F, 3, 2, 2, f);
    this.rrib1.func_78793_a(-4.0F, -6.977365F, 2.826201F);
    setRotation(this.rrib1, -0.07023507F, 0.0F, 0.0F);
    this.rrib2 = new ModelRenderer(this, 101, 47);
    this.rrib2.func_78787_b(128, 64);
    this.rrib2.func_78790_a(-1.5F, -1.0F, -1.0F, 3, 2, 2, f);
    this.rrib2.func_78793_a(-4.0F, -3.82131F, 2.627918F);
    setRotation(this.rrib2, -0.07023507F, 0.0F, 0.0F);
    this.rrib3 = new ModelRenderer(this, 101, 47);
    this.rrib3.func_78787_b(128, 64);
    this.rrib3.func_78790_a(-1.5F, -1.0F, -1.0F, 3, 2, 2, f);
    this.rrib3.func_78793_a(-4.0F, -0.6652546F, 2.429635F);
    setRotation(this.rrib3, -0.07023507F, 0.0F, 0.0F);
    this.pelvis = new ModelRenderer(this, 80, 14);
    this.pelvis.func_78787_b(128, 64);
    this.pelvis.func_78790_a(-6.0F, -1.0F, -3.0F, 12, 2, 6, f);
    this.pelvis.func_78793_a(0.0F, 5.390734F, 2.999714F);
    setRotation(this.pelvis, -1.637653E-7F, 0.0F, 0.0F);
    this.Spine3 = new ModelRenderer(this, 76, 28);
    this.Spine3.func_78787_b(128, 64);
    this.Spine3.func_78790_a(-9.0F, -2.0F, -2.0F, 18, 4, 4, f);
    this.Spine3.func_78793_a(0.0F, -11.10007F, 2.768362F);
    setRotation(this.Spine3, 0.1290269F, 0.0F, 0.0F);
    this.larm1 = new ModelRenderer(this, 46, 48);
    this.larm1.func_78787_b(128, 64);
    this.larm1.func_78790_a(0.0F, -2.0F, -2.0F, 4, 12, 4, f);
    this.larm1.func_78793_a(9.0F, -11.0F, 2.0F);
    setRotation(this.larm1, -0.5595525F, 0.0F, 0.0F);
    this.larm2 = new ModelRenderer(this, 64, 52);
    this.larm2.func_78787_b(128, 64);
    this.larm2.func_78790_a(-2.0F, 1.0F, 3.0F, 4, 8, 4, f);
    this.larm2.func_78793_a(2.0F, 4.0F, -2.0F);
    setRotation(this.larm2, -1.44967F - this.larm1.field_78795_f, 0.0F, 0.0F);
    this.clawl1 = new ModelRenderer(this, 110, 57);
    this.clawl1.func_78787_b(128, 64);
    this.clawl1.func_78790_a(-0.5F, -0.5F, -1.0F, 1, 5, 1, f);
    this.clawl1.func_78793_a(-1.0F, 8.0F, 3.0F);
    this.clawl2 = new ModelRenderer(this, 110, 57);
    this.clawl2.func_78787_b(128, 64);
    this.clawl2.func_78790_a(-0.5F, -0.5F, -1.0F, 1, 5, 1, f);
    this.clawl2.func_78793_a(1.0F, 8.0F, 3.0F);
    this.clawl3 = new ModelRenderer(this, 110, 57);
    this.clawl3.func_78787_b(128, 64);
    this.clawl3.func_78790_a(-1.0F, -0.5F, -1.0F, 1, 5, 1, f);
    this.clawl3.func_78793_a(-2.0F, 8.0F, 5.0F);
    this.clawl4 = new ModelRenderer(this, 110, 57);
    this.clawl4.func_78787_b(128, 64);
    this.clawl4.func_78790_a(0.0F, -0.5F, -1.0F, 1, 5, 1, f);
    this.clawl4.func_78793_a(2.0F, 8.0F, 5.0F);
    this.rarm1 = new ModelRenderer(this, 46, 48);
    this.rarm1.func_78787_b(128, 64);
    this.rarm1.func_78790_a(-4.0F, -2.0F, -2.0F, 4, 12, 4, f);
    this.rarm1.func_78793_a(-9.0F, -11.0F, 2.0F);
    setRotation(this.rarm1, -0.559472F, 0.0F, 0.0F);
    this.rarm2 = new ModelRenderer(this, 64, 52);
    this.rarm2.func_78787_b(128, 64);
    this.rarm2.func_78790_a(-2.0F, 1.0F, 3.0F, 4, 8, 4, f);
    this.rarm2.func_78793_a(-2.0F, 4.0F, -2.0F);
    setRotation(this.rarm2, -1.449542F - this.rarm1.field_78795_f, 0.0F, 0.0F);
    this.clawr1 = new ModelRenderer(this, 110, 57);
    this.clawr1.func_78787_b(128, 64);
    this.clawr1.func_78790_a(-0.5F, -0.5F, -1.0F, 1, 5, 1, f);
    this.clawr1.func_78793_a(-1.0F, 8.0F, 3.0F);
    this.clawr2 = new ModelRenderer(this, 110, 57);
    this.clawr2.func_78787_b(128, 64);
    this.clawr2.func_78790_a(-0.5F, -0.5F, -1.0F, 1, 5, 1, f);
    this.clawr2.func_78793_a(1.0F, 8.0F, 3.0F);
    this.clawr3 = new ModelRenderer(this, 110, 57);
    this.clawr3.func_78787_b(128, 64);
    this.clawr3.func_78790_a(0.0F, -0.5F, -1.0F, 1, 5, 1, f);
    this.clawr3.func_78793_a(-3.0F, 8.0F, 5.0F);
    this.clawr4 = new ModelRenderer(this, 110, 57);
    this.clawr4.func_78787_b(128, 64);
    this.clawr4.func_78790_a(-1.0F, -0.5F, -1.0F, 1, 5, 1, f);
    this.clawr4.func_78793_a(3.0F, 8.0F, 5.0F);
    this.lleg = new ModelRenderer(this, 22, 40);
    this.lleg.func_78787_b(128, 64);
    this.lleg.func_78790_a(-3.0F, -1.0F, -3.0F, 6, 18, 6, f);
    this.lleg.func_78793_a(3.0F, 7.0F, 3.0F);
    this.rleg = new ModelRenderer(this, 22, 40);
    this.rleg.func_78787_b(128, 64);
    this.rleg.func_78790_a(-3.0F, -1.0F, -3.0F, 6, 18, 6, f);
    this.rleg.func_78793_a(-3.0F, 7.0F, 3.0F);
    this.back = new ModelRenderer(this, 0, 18);
    this.back.func_78787_b(128, 64);
    this.back.func_78790_a(-6.0F, -7.5F, 0.0F, 12, 15, 0, f);
    this.back.func_78793_a(0.0F, -3.0F, 6.0F);
    this.lside = new ModelRenderer(this, 30, 12);
    this.lside.func_78787_b(128, 64);
    this.lside.func_78790_a(0.0F, -7.5F, -3.0F, 0, 15, 6, f);
    this.lside.func_78793_a(6.0F, -3.0F, 3.0F);
    this.rside = new ModelRenderer(this, 42, 12);
    this.rside.func_78787_b(128, 64);
    this.rside.func_78790_a(0.0F, -7.5F, -3.0F, 0, 15, 6, f);
    this.rside.func_78793_a(-6.0F, -3.0F, 3.0F);
    this.larm1.func_78792_a(this.larm2);
    this.rarm1.func_78792_a(this.rarm2);
    this.larm2.func_78792_a(this.clawl1);
    this.larm2.func_78792_a(this.clawl2);
    this.larm2.func_78792_a(this.clawl3);
    this.larm2.func_78792_a(this.clawl4);
    this.rarm2.func_78792_a(this.clawr1);
    this.rarm2.func_78792_a(this.clawr2);
    this.rarm2.func_78792_a(this.clawr3);
    this.rarm2.func_78792_a(this.clawr4);
  }
  
  public void func_78088_a(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
    super.func_78088_a(par1Entity, par2, par3, par4, par5, par6, par7);
    func_78087_a(par2, par3, par4, par5, par6, par7, par1Entity);
    if (this.field_78091_s) {
      float f6 = 2.0F;
      GlStateManager.func_179094_E();
      GlStateManager.func_179152_a(1.5F / f6, 1.5F / f6, 1.5F / f6);
      GlStateManager.func_179109_b(0.0F, 21.0F * par7, 0.0F);
      this.Head.func_78785_a(par7);
      GlStateManager.func_179121_F();
      GlStateManager.func_179094_E();
      GlStateManager.func_179152_a(1.0F / f6, 1.0F / f6, 1.0F / f6);
      GlStateManager.func_179109_b(0.0F, 24.0F * par7, 0.0F);
      this.Spine1.func_78785_a(par7);
      this.Spine2.func_78785_a(par7);
      this.lrib1.func_78785_a(par7);
      this.lrib2.func_78785_a(par7);
      this.lrib3.func_78785_a(par7);
      this.rrib1.func_78785_a(par7);
      this.rrib2.func_78785_a(par7);
      this.rrib3.func_78785_a(par7);
      this.pelvis.func_78785_a(par7);
      this.Spine3.func_78785_a(par7);
      this.larm1.func_78785_a(par7);
      this.rarm1.func_78785_a(par7);
      this.lleg.func_78785_a(par7);
      this.rleg.func_78785_a(par7);
      this.back.func_78785_a(par7);
      this.lside.func_78785_a(par7);
      this.rside.func_78785_a(par7);
      GlStateManager.func_179121_F();
    } else {
      this.Head.func_78785_a(par7);
      this.Spine1.func_78785_a(par7);
      this.Spine2.func_78785_a(par7);
      this.lrib1.func_78785_a(par7);
      this.lrib2.func_78785_a(par7);
      this.lrib3.func_78785_a(par7);
      this.rrib1.func_78785_a(par7);
      this.rrib2.func_78785_a(par7);
      this.rrib3.func_78785_a(par7);
      this.pelvis.func_78785_a(par7);
      this.Spine3.func_78785_a(par7);
      this.larm1.func_78785_a(par7);
      this.rarm1.func_78785_a(par7);
      this.lleg.func_78785_a(par7);
      this.rleg.func_78785_a(par7);
      this.back.func_78785_a(par7);
      this.lside.func_78785_a(par7);
      this.rside.func_78785_a(par7);
    } 
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z) {
    model.field_78795_f = x;
    model.field_78796_g = y;
    model.field_78808_h = z;
  }
  
  public void func_78087_a(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
    EntityTameBase entity = (EntityTameBase)par7Entity;
    float f2 = entity.isArmsRaised() ? -1.0F : 0.0F;
    this.Head.field_78796_g = par4 / 57.295776F;
    this.Head.field_78795_f = par5 / 57.295776F;
    this.rleg.field_78795_f = MathHelper.func_76134_b(par1 * 0.6662F) * 1.4F * par2;
    this.rleg.field_78796_g = 0.0F;
    this.lleg.field_78795_f = MathHelper.func_76134_b(par1 * 0.6662F + 3.1415927F) * 1.4F * par2;
    this.lleg.field_78796_g = 0.0F;
    if (this.field_78093_q) {
      this.rarm1.field_78795_f += -0.62831855F;
      this.larm1.field_78795_f += -0.62831855F;
      this.rleg.field_78795_f = -1.2566371F;
      this.lleg.field_78795_f = -1.2566371F;
      this.rleg.field_78796_g = 0.31415927F;
      this.lleg.field_78796_g = -0.31415927F;
    } 
    float f6 = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F);
    float f7 = MathHelper.func_76126_a((1.0F - (1.0F - this.field_78095_p) * (1.0F - this.field_78095_p)) * 3.1415927F);
    this.rarm1.field_78808_h = 0.0F;
    this.larm1.field_78808_h = 0.0F;
    this.rarm1.field_78796_g = -(0.1F - f6 * 0.6F);
    this.larm1.field_78796_g = 0.1F - f6 * 0.6F;
    this.rarm1.field_78795_f = -0.84907913F + f2;
    this.larm1.field_78795_f = -0.84907913F + f2;
    this.rarm1.field_78795_f -= f6 * 1.2F - f7 * 0.4F;
    this.larm1.field_78795_f -= f6 * 1.2F - f7 * 0.4F;
    this.rarm1.field_78808_h += MathHelper.func_76134_b(par3 * 0.09F) * 0.05F + 0.05F;
    this.larm1.field_78808_h -= MathHelper.func_76134_b(par3 * 0.09F) * 0.05F + 0.05F;
    this.rarm1.field_78795_f += MathHelper.func_76126_a(par3 * 0.067F) * 0.05F;
    this.larm1.field_78795_f -= MathHelper.func_76126_a(par3 * 0.067F) * 0.05F;
    if (this.isSneak) {
      this.Head.field_78795_f -= 0.5F;
      this.rarm1.field_78795_f -= 0.5F;
      this.larm1.field_78795_f -= 0.5F;
      this.rleg.field_78795_f -= 0.5F;
      this.lleg.field_78795_f -= 0.5F;
    } 
    if (entity.func_70027_ad()) {
      this.Head.field_78795_f -= 0.75F;
      this.Head.field_78796_g += MathHelper.func_76134_b(par3 * 0.6662F) * 0.5F;
      this.rarm1.field_78795_f = -MathHelper.func_76134_b(par3 * 0.6662F) - 2.0F;
      this.larm1.field_78795_f = MathHelper.func_76134_b(par3 * 0.6662F) - 2.0F;
      this.rarm1.field_78796_g = 0.0F;
      this.larm1.field_78796_g = 0.0F;
    } 
    if (!entity.getCurrentBook().func_190926_b()) {
      this.rarm1.field_78796_g = entity.bookSpread - 1.25F;
      this.larm1.field_78796_g = -entity.bookSpread + 1.25F;
      this.rarm1.field_78808_h = entity.bookSpread;
      this.larm1.field_78808_h = -entity.bookSpread;
      this.rarm1.field_78795_f = -1.25F + 0.1F + MathHelper.func_76126_a(entity.field_70173_aa * 0.1F) * 0.01F;
      this.larm1.field_78795_f = -1.25F + 0.1F + MathHelper.func_76126_a(entity.field_70173_aa * 0.1F) * 0.01F;
    } 
  }
  
  public void setInvisible(boolean invisible) {
    this.Head.field_78806_j = invisible;
    this.Spine1.field_78806_j = invisible;
    this.Spine2.field_78806_j = invisible;
    this.lrib1.field_78806_j = invisible;
    this.lrib2.field_78806_j = invisible;
    this.lrib3.field_78806_j = invisible;
    this.rrib1.field_78806_j = invisible;
    this.rrib2.field_78806_j = invisible;
    this.rrib3.field_78806_j = invisible;
    this.pelvis.field_78806_j = invisible;
    this.Spine3.field_78806_j = invisible;
    this.larm1.field_78806_j = invisible;
    this.rarm1.field_78806_j = invisible;
    this.lleg.field_78806_j = invisible;
    this.rleg.field_78806_j = invisible;
    this.back.field_78806_j = invisible;
    this.lside.field_78806_j = invisible;
    this.rside.field_78806_j = invisible;
  }
  
  public void postRenderArm(float scale, EnumHandSide side) {
    getArmForSide(side).func_78794_c(scale);
  }
  
  protected ModelRenderer getArmForSide(EnumHandSide side) {
    return (side == EnumHandSide.LEFT) ? this.larm1 : this.rarm1;
  }
}
