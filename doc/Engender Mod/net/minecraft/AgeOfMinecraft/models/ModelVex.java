package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityVex;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelVex extends ModelBiped {
  protected ModelRenderer leftWing;
  
  protected ModelRenderer rightWing;
  
  public ModelVex() {
    this(0.0F);
  }
  
  public ModelVex(float p_i47224_1_) {
    super(p_i47224_1_, 0.0F, 64, 64);
    this.field_178722_k.field_78806_j = false;
    this.field_178720_f.field_78806_j = false;
    this.field_178721_j = new ModelRenderer((ModelBase)this, 32, 0);
    this.field_178721_j.func_78790_a(-1.0F, -1.0F, -2.0F, 6, 10, 4, 0.0F);
    this.field_178721_j.func_78793_a(-1.9F, 12.0F, 0.0F);
    this.rightWing = new ModelRenderer((ModelBase)this, 0, 32);
    this.rightWing.func_78789_a(-20.0F, 0.0F, 0.0F, 20, 12, 1);
    this.leftWing = new ModelRenderer((ModelBase)this, 0, 32);
    this.leftWing.field_78809_i = true;
    this.leftWing.func_78789_a(0.0F, 0.0F, 0.0F, 20, 12, 1);
  }
  
  public void func_78088_a(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    func_78087_a(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
    GlStateManager.func_179094_E();
    if (this.field_78091_s) {
      GlStateManager.func_179152_a(0.75F, 0.75F, 0.75F);
      GlStateManager.func_179109_b(0.0F, 16.0F * scale, 0.0F);
      this.field_78116_c.func_78785_a(scale);
      GlStateManager.func_179121_F();
      GlStateManager.func_179094_E();
      GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
      GlStateManager.func_179109_b(0.0F, 24.0F * scale, 0.0F);
      this.field_78115_e.func_78785_a(scale);
      this.field_178723_h.func_78785_a(scale);
      this.field_178724_i.func_78785_a(scale);
      this.field_178721_j.func_78785_a(scale);
      this.field_178722_k.func_78785_a(scale);
      this.field_178720_f.func_78785_a(scale);
      this.rightWing.func_78785_a(scale);
      this.leftWing.func_78785_a(scale);
    } else {
      if (entityIn.func_70093_af())
        GlStateManager.func_179109_b(0.0F, 0.2F, 0.0F); 
      this.field_78116_c.func_78785_a(scale);
      this.field_78115_e.func_78785_a(scale);
      this.field_178723_h.func_78785_a(scale);
      this.field_178724_i.func_78785_a(scale);
      this.field_178721_j.func_78785_a(scale);
      this.field_178722_k.func_78785_a(scale);
      this.field_178720_f.func_78785_a(scale);
      this.rightWing.func_78785_a(scale);
      this.leftWing.func_78785_a(scale);
    } 
    GlStateManager.func_179121_F();
  }
  
  public void func_78087_a(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    super.func_78087_a(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    EntityTameBase entity = (EntityTameBase)entityIn;
    if (entity instanceof EntityVex && (
      (EntityVex)entity).isCharging())
      if (((EntityVex)entity).func_184591_cq() == EnumHandSide.RIGHT) {
        this.field_178723_h.field_78795_f = 3.7699115F;
      } else {
        this.field_178724_i.field_78795_f = 3.7699115F;
      }  
    this.field_178721_j.field_78795_f += 0.62831855F;
    this.rightWing.field_78798_e = 2.0F;
    this.leftWing.field_78798_e = 2.0F;
    this.rightWing.field_78797_d = 1.0F;
    this.leftWing.field_78797_d = 1.0F;
    this.rightWing.field_78796_g = 0.47123894F + MathHelper.func_76134_b(ageInTicks * 0.8F) * 3.1415927F * 0.05F;
    this.leftWing.field_78796_g = -this.rightWing.field_78796_g;
    this.leftWing.field_78808_h = -0.47123894F;
    this.leftWing.field_78795_f = 0.47123894F;
    this.rightWing.field_78795_f = 0.47123894F;
    this.rightWing.field_78808_h = 0.47123894F;
    if (!entity.getCurrentBook().func_190926_b()) {
      this.field_178723_h.field_78796_g = entity.bookSpread - 1.0F;
      this.field_178724_i.field_78796_g = -entity.bookSpread + 1.0F;
      this.field_178723_h.field_78808_h = 0.0F;
      this.field_178724_i.field_78808_h = 0.0F;
      this.field_178723_h.field_78795_f = -1.5F + 0.1F + MathHelper.func_76126_a(entity.field_70173_aa * 0.1F) * 0.01F;
      this.field_178724_i.field_78795_f = -1.5F + 0.1F + MathHelper.func_76126_a(entity.field_70173_aa * 0.1F) * 0.01F;
    } 
  }
}
