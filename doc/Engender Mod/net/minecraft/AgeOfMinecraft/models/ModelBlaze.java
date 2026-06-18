package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelBlaze extends ModelBase implements ICappedModel {
  public ModelRenderer[] blazeSticks = new ModelRenderer[12];
  
  public ModelRenderer blazeHead;
  
  public ModelRenderer bipedCape;
  
  public boolean isSneak;
  
  public ModelBlaze() {
    for (int i = 0; i < this.blazeSticks.length; i++) {
      this.blazeSticks[i] = new ModelRenderer(this, 0, 16);
      this.blazeSticks[i].func_78789_a(0.0F, 0.0F, 0.0F, 2, 8, 2);
    } 
    this.blazeHead = new ModelRenderer(this, 0, 0);
    this.blazeHead.func_78789_a(-4.0F, -8.0F, -4.0F, 8, 8, 8);
    this.bipedCape = new ModelRenderer(this, 0, 0);
    this.bipedCape.func_78787_b(64, 32);
    this.bipedCape.func_78790_a(-5.0F, 0.0F, -1.0F, 10, 16, 1, 0.0F);
  }
  
  public void func_78088_a(Entity entityIn, float p_78088_2_, float limbSwing, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    func_78087_a(p_78088_2_, limbSwing, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
    if (this.field_78091_s) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179152_a(0.75F, 0.75F, 0.75F);
      GlStateManager.func_179109_b(0.0F, 16.0F * scale, 0.0F);
      this.blazeHead.func_78785_a(scale);
      GlStateManager.func_179121_F();
      GlStateManager.func_179094_E();
      GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
      GlStateManager.func_179109_b(0.0F, 24.0F * scale, 0.0F);
      for (int i = 0; i < this.blazeSticks.length; i++)
        this.blazeSticks[i].func_78785_a(scale); 
      GlStateManager.func_179121_F();
    } else {
      this.blazeHead.func_78785_a(scale);
      for (int i = 0; i < this.blazeSticks.length; i++)
        this.blazeSticks[i].func_78785_a(scale); 
    } 
  }
  
  public void renderCape(float scale, float flo1, float flo2, float flo3) {
    if (this.field_78091_s) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
      GlStateManager.func_179109_b(0.0F, 1.5F, -0.125F);
      GlStateManager.func_179114_b(6.0F + flo2 / 2.0F + flo1, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179114_b(flo3 / 2.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.func_179114_b(-flo3 / 2.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
      this.bipedCape.func_78785_a(scale);
      GlStateManager.func_179121_F();
    } else {
      GlStateManager.func_179094_E();
      GlStateManager.func_179114_b(6.0F + flo2 / 2.0F + flo1, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179114_b(flo3 / 2.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.func_179114_b(-flo3 / 2.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
      this.bipedCape.func_78785_a(scale);
      GlStateManager.func_179121_F();
    } 
  }
  
  public void func_78087_a(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    EntityTameBase entity = (EntityTameBase)entityIn;
    if (entity.func_175446_cd())
      ageInTicks = 0.0F; 
    this.blazeHead.field_78795_f = headPitch * 0.017453292F;
    this.blazeHead.field_78796_g = netHeadYaw * 0.017453292F;
    this.field_78091_s = entity.func_70631_g_();
    this.isSneak = entity.func_70093_af();
    float swing = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F);
    float f = ageInTicks * 3.1415927F * ((entity.getJukeboxToDanceTo() != null) ? -0.2F : -0.1F);
    this.blazeHead.field_78800_c = 0.0F;
    if (this.isSneak) {
      f *= 0.5F;
      this.blazeHead.field_78797_d = 8.0F;
    } else {
      this.blazeHead.field_78797_d = 4.0F;
    } 
    for (int i = 0; i < 4; i++) {
      if (this.isSneak) {
        (this.blazeSticks[i]).field_78797_d = 2.0F + MathHelper.func_76134_b(((i * 2) + ageInTicks) * 0.25F);
      } else {
        (this.blazeSticks[i]).field_78797_d = -2.0F + MathHelper.func_76134_b(((i * 2) + ageInTicks) * 0.25F);
      } 
      (this.blazeSticks[i]).field_78800_c = MathHelper.func_76134_b(f - swing) * 9.0F;
      (this.blazeSticks[i]).field_78798_e = MathHelper.func_76126_a(f - swing) * 9.0F;
      f += 1.5F;
    } 
    f = 0.7853982F + ageInTicks * 3.1415927F * ((entity.getJukeboxToDanceTo() != null) ? 0.1F : 0.03F);
    for (int j = 4; j < 8; j++) {
      if (this.isSneak) {
        (this.blazeSticks[j]).field_78797_d = 5.0F + MathHelper.func_76134_b(((j * 2) + ageInTicks) * 0.25F);
      } else {
        (this.blazeSticks[j]).field_78797_d = 2.0F + MathHelper.func_76134_b(((j * 2) + ageInTicks) * 0.25F);
      } 
      (this.blazeSticks[j]).field_78800_c = MathHelper.func_76134_b(f) * 7.0F;
      (this.blazeSticks[j]).field_78798_e = MathHelper.func_76126_a(f) * 7.0F;
      f += 1.5F;
    } 
    f = 0.47123894F + ageInTicks * 3.1415927F * ((entity.getJukeboxToDanceTo() != null) ? -0.15F : -0.05F);
    for (int k = 8; k < 12; k++) {
      (this.blazeSticks[k]).field_78797_d = 11.0F + MathHelper.func_76134_b((k * 1.5F + ageInTicks) * 0.5F);
      (this.blazeSticks[k]).field_78800_c = MathHelper.func_76134_b(f) * 5.0F;
      (this.blazeSticks[k]).field_78798_e = MathHelper.func_76126_a(f) * 5.0F;
      f += 1.5F;
    } 
    this.blazeHead.field_78795_f -= swing;
    if (!entity.func_70089_S() && !entity.field_70122_E) {
      this.blazeHead.field_78795_f -= 0.5F;
      this.blazeHead.field_78796_g += MathHelper.func_76134_b(ageInTicks * 0.6662F) * 0.5F;
    } 
    if (entity.getJukeboxToDanceTo() != null) {
      float f4 = MathHelper.func_76126_a(ageInTicks * 0.125F) * 0.7F;
      this.blazeHead.field_78800_c += MathHelper.func_76134_b(ageInTicks * 0.25F) * 1.0F;
      this.blazeHead.field_78797_d += f4 + MathHelper.func_76134_b(ageInTicks * 0.5F) * 1.0F;
      for (int m = 0; m < 12; m++) {
        f4 = MathHelper.func_76126_a(m * 2.0F + ageInTicks * 0.125F) * 0.7F;
        (this.blazeSticks[m]).field_78797_d += f4 + MathHelper.func_76134_b((m * 1.5F + ageInTicks) * 0.5F);
      } 
    } 
    this.bipedCape.field_78797_d = this.blazeHead.field_78797_d;
  }
}
