package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityPigZombie;
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
public class ModelZombie extends ModelBiped implements ICappedModel {
  public ModelRenderer bipedCape;
  
  public ModelZombie() {
    this(0.0F, false);
  }
  
  public ModelZombie(float modelSize, boolean p_i1168_2_) {
    super(modelSize, 0.0F, 64, p_i1168_2_ ? 32 : 64);
    this.bipedCape = new ModelRenderer((ModelBase)this, 0, 0);
    this.bipedCape.func_78787_b(64, 32);
    this.bipedCape.func_78790_a(-5.0F, 0.0F, -1.0F, 10, 16, 1, modelSize);
  }
  
  public void func_78088_a(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    func_78087_a(limbSwing, limbSwingAmount, ((EntityTameBase)entityIn).func_175446_cd() ? 0.0F : ageInTicks, netHeadYaw, headPitch, scale, entityIn);
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
    } else {
      this.field_78116_c.func_78785_a(scale);
      this.field_78115_e.func_78785_a(scale);
      this.field_178723_h.func_78785_a(scale);
      this.field_178724_i.func_78785_a(scale);
      this.field_178721_j.func_78785_a(scale);
      this.field_178722_k.func_78785_a(scale);
      this.field_178720_f.func_78785_a(scale);
    } 
    GlStateManager.func_179121_F();
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
      ageInTicks = 1.0F; 
    super.func_78087_a(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    this.field_78091_s = entity.func_70631_g_();
    this.field_78117_n = entity.func_70093_af();
    if (this.field_78117_n) {
      this.bipedCape.field_78797_d = 2.0F;
    } else {
      this.bipedCape.field_78797_d = 0.0F;
    } 
    this.field_178720_f.field_78807_k = (!EngenderConfig.mobs.useMobTalkerModels && entity instanceof EntityPigZombie && ((EntityPigZombie)entity).isOldPEPigman());
    if (!(entity instanceof EntityPigZombie) || (entity instanceof EntityPigZombie && !((EntityPigZombie)entity).isOldPEPigman())) {
      float f = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F);
      float f1 = MathHelper.func_76126_a((1.0F - (1.0F - this.field_78095_p) * (1.0F - this.field_78095_p)) * 3.1415927F);
      this.field_178723_h.field_78808_h = 0.0F;
      this.field_178724_i.field_78808_h = 0.0F;
      this.field_178723_h.field_78796_g = -(f * 0.6F);
      this.field_178724_i.field_78796_g = f * 0.6F;
      float f2 = entity.isArmsRaised() ? -1.5F : -0.75F;
      this.field_178723_h.field_78795_f = this.field_78116_c.field_78795_f + f2 - 0.5F;
      this.field_178724_i.field_78795_f = this.field_78116_c.field_78795_f + f2 - 0.5F;
      this.field_178723_h.field_78795_f += f * 1.2F - f1 * 0.4F;
      this.field_178724_i.field_78795_f += f * 1.2F - f1 * 0.4F;
      this.field_178723_h.field_78808_h += MathHelper.func_76134_b(ageInTicks * 0.09F) * 0.05F + 0.05F;
      this.field_178724_i.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.09F) * 0.05F + 0.05F;
      this.field_178723_h.field_78795_f += MathHelper.func_76126_a(ageInTicks * 0.067F) * 0.1F;
      this.field_178724_i.field_78795_f -= MathHelper.func_76126_a(ageInTicks * 0.067F) * 0.1F;
    } 
    if (entity instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityAbygolem || entity instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDreadgolem)
      super.func_78087_a(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn); 
    this.field_78116_c.field_78795_f += MathHelper.func_76134_b(ageInTicks * 0.1F) * 0.0125F * 3.1415927F;
    if (entity.func_184207_aI() && entity instanceof EntityPigZombie)
      if (entity.func_184591_cq() == EnumHandSide.RIGHT) {
        this.field_178723_h.field_78795_f -= 0.325F;
        this.field_178724_i.field_78795_f = -1.875F;
      } else {
        this.field_178723_h.field_78795_f = -1.875F;
        this.field_178724_i.field_78795_f -= 0.325F;
      }  
    if (entity.func_70027_ad() || (!entity.func_70089_S() && !entity.field_70122_E)) {
      this.field_78116_c.field_78795_f -= 0.5F;
      this.field_78116_c.field_78796_g += MathHelper.func_76134_b(ageInTicks * 0.6662F) * 0.5F;
      this.field_178723_h.field_78798_e = 0.0F;
      this.field_178723_h.field_78800_c = -5.0F;
      this.field_178724_i.field_78798_e = 0.0F;
      this.field_178724_i.field_78800_c = 5.0F;
      this.field_178723_h.field_78795_f = -MathHelper.func_76134_b(ageInTicks * 0.6662F) * 0.5F;
      this.field_178724_i.field_78795_f = MathHelper.func_76134_b(ageInTicks * 0.6662F) * 0.5F;
      this.field_178723_h.field_78808_h = 2.3561945F;
      this.field_178724_i.field_78808_h = -2.3561945F;
      this.field_178723_h.field_78796_g = 0.0F;
      this.field_178724_i.field_78796_g = 0.0F;
    } 
    if (entity.getJukeboxToDanceTo() != null) {
      float fl = MathHelper.func_76126_a(ageInTicks * 0.5F) * 0.7F;
      this.field_178723_h.field_78798_e = 0.0F;
      this.field_178723_h.field_78800_c = -5.0F;
      this.field_178724_i.field_78798_e = 0.0F;
      this.field_178724_i.field_78800_c = 5.0F;
      this.field_178723_h.field_78795_f = -fl;
      this.field_178724_i.field_78795_f = fl;
      this.field_178723_h.field_78808_h = 1.6561944F - MathHelper.func_76134_b(ageInTicks * 0.125F) * 1.0F;
      this.field_178724_i.field_78808_h = -1.6561944F + MathHelper.func_76134_b(ageInTicks * 0.125F) * 1.0F;
      this.field_178723_h.field_78796_g = 0.0F;
      this.field_178724_i.field_78796_g = 0.0F;
      this.field_78116_c.func_78793_a(0.0F + MathHelper.func_76134_b(ageInTicks * 0.25F) * 1.0F, 0.5F - fl, 0.0F);
    } else {
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
    } 
    func_178685_a(this.field_78116_c, this.field_178720_f);
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
