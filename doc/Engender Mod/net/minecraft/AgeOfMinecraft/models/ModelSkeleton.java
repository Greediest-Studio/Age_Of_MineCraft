package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelSkeleton extends ModelBiped implements ICappedModel {
  public ModelRenderer bipedCape;
  
  public ModelSkeleton() {
    this(0.0F, false);
  }
  
  public ModelSkeleton(float modelSize, boolean p_i46303_2_) {
    super(modelSize, 0.0F, 64, 32);
    if (!p_i46303_2_) {
      this.field_178723_h = new ModelRenderer((ModelBase)this, 40, 16);
      this.field_178723_h.func_78790_a(-1.0F, -2.0F, -1.0F, 2, 12, 2, modelSize);
      this.field_178723_h.func_78793_a(-5.0F, 2.0F, 0.0F);
      this.field_178724_i = new ModelRenderer((ModelBase)this, 40, 16);
      this.field_178724_i.field_78809_i = true;
      this.field_178724_i.func_78790_a(-1.0F, -2.0F, -1.0F, 2, 12, 2, modelSize);
      this.field_178724_i.func_78793_a(5.0F, 2.0F, 0.0F);
      this.field_178721_j = new ModelRenderer((ModelBase)this, 0, 16);
      this.field_178721_j.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 12, 2, modelSize);
      this.field_178721_j.func_78793_a(-2.0F, 12.0F, 0.0F);
      this.field_178722_k = new ModelRenderer((ModelBase)this, 0, 16);
      this.field_178722_k.field_78809_i = true;
      this.field_178722_k.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 12, 2, modelSize);
      this.field_178722_k.func_78793_a(2.0F, 12.0F, 0.0F);
    } 
    this.bipedCape = new ModelRenderer((ModelBase)this, 0, 0);
    this.bipedCape.func_78787_b(64, 32);
    this.bipedCape.func_78790_a(-5.0F, 0.0F, -1.0F, 10, 16, 1, modelSize);
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
  
  public void func_78086_a(EntityLivingBase entitylivingbaseIn, float p_78086_2_, float p_78086_3_, float partialTickTime) {
    this.field_187076_m = ModelBiped.ArmPose.EMPTY;
    this.field_187075_l = ModelBiped.ArmPose.EMPTY;
    ItemStack itemstack = entitylivingbaseIn.func_184586_b(EnumHand.MAIN_HAND);
    if (itemstack != null && itemstack.func_77973_b() instanceof net.minecraft.item.ItemBow && ((EntityTameBase)entitylivingbaseIn).isArmsRaised())
      if (entitylivingbaseIn.func_184591_cq() == EnumHandSide.RIGHT) {
        this.field_187076_m = ModelBiped.ArmPose.BOW_AND_ARROW;
      } else {
        this.field_187075_l = ModelBiped.ArmPose.BOW_AND_ARROW;
      }  
    super.func_78086_a(entitylivingbaseIn, p_78086_2_, p_78086_3_, partialTickTime);
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
  
  public void func_78087_a(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    super.func_78087_a(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    ItemStack itemstack = ((EntityLivingBase)entityIn).func_184614_ca();
    EntityTameBase entity = (EntityTameBase)entityIn;
    this.field_78091_s = entity.func_70631_g_();
    this.field_78117_n = entity.func_70093_af();
    float f = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F);
    float f1 = MathHelper.func_76126_a((1.0F - (1.0F - this.field_78095_p) * (1.0F - this.field_78095_p)) * 3.1415927F);
    this.field_78116_c.field_78795_f += MathHelper.func_76134_b(ageInTicks * 0.1F) * 0.0125F * 3.1415927F;
    if (entity.isArmsRaised() && (itemstack == null || (itemstack != null && !(itemstack.func_77973_b() instanceof net.minecraft.item.ItemBow)))) {
      this.field_178723_h.field_78808_h = 0.0F;
      this.field_178724_i.field_78808_h = 0.0F;
      this.field_178723_h.field_78796_g = -(0.1F - f * 0.6F);
      this.field_178724_i.field_78796_g = 0.1F - f * 0.6F;
      this.field_178723_h.field_78795_f = -1.5707964F;
      this.field_178724_i.field_78795_f = -1.5707964F;
      this.field_178723_h.field_78795_f -= f * 1.2F - f1 * 0.4F;
      this.field_178724_i.field_78795_f -= f * 1.2F - f1 * 0.4F;
      this.field_178723_h.field_78808_h += MathHelper.func_76134_b(ageInTicks * 0.09F) * 0.05F + 0.05F;
      this.field_178724_i.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.09F) * 0.05F + 0.05F;
      this.field_178723_h.field_78795_f += MathHelper.func_76126_a(ageInTicks * 0.067F) * 0.05F;
      this.field_178724_i.field_78795_f -= MathHelper.func_76126_a(ageInTicks * 0.067F) * 0.05F;
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
    ItemStack itemstack2 = entity.func_184586_b(EnumHand.OFF_HAND);
    if (itemstack2 != null && itemstack2.func_77973_b() == Items.field_185159_cQ && entity.func_184585_cz())
      if (entity.func_184591_cq() == EnumHandSide.RIGHT) {
        this.field_178724_i.field_78795_f += this.field_178724_i.field_78795_f * 0.5F + 0.9424779F;
        this.field_178724_i.field_78796_g += 0.75F;
      } else {
        this.field_178723_h.field_78795_f += this.field_178723_h.field_78795_f * 0.5F + 0.9424779F;
        this.field_178723_h.field_78796_g += -0.75F;
      }  
    if (entity.getJukeboxToDanceTo() != null) {
      float fl = MathHelper.func_76126_a(ageInTicks * 0.5F) * 0.7F;
      this.field_178723_h.field_78798_e = 0.0F;
      this.field_178723_h.field_78800_c = -5.0F;
      this.field_178724_i.field_78798_e = 0.0F;
      this.field_178724_i.field_78800_c = 5.0F;
      this.field_178723_h.field_78795_f = -fl;
      this.field_178724_i.field_78795_f = fl;
      this.field_178723_h.field_78808_h = 1.6561944F - MathHelper.func_76134_b(ageInTicks * 0.25F) * 1.0F;
      this.field_178724_i.field_78808_h = -0.9561945F + MathHelper.func_76134_b(ageInTicks * 0.25F) * 1.0F;
      this.field_178723_h.field_78796_g = 0.0F;
      this.field_178724_i.field_78796_g = 0.0F;
      this.field_78116_c.func_78793_a(0.0F + MathHelper.func_76134_b(ageInTicks * 0.25F) * 1.0F, 0.5F - fl, 0.0F);
    } 
    if (!entity.getCurrentBook().func_190926_b()) {
      this.field_178723_h.field_78796_g = entity.bookSpread - 1.0F;
      this.field_178724_i.field_78796_g = -entity.bookSpread + 1.0F;
      this.field_178723_h.field_78808_h = 0.0F;
      this.field_178724_i.field_78808_h = 0.0F;
      this.field_178723_h.field_78795_f = -1.5F + 0.1F + MathHelper.func_76126_a(entity.field_70173_aa * 0.1F) * 0.01F;
      this.field_178724_i.field_78795_f = -1.5F + 0.1F + MathHelper.func_76126_a(entity.field_70173_aa * 0.1F) * 0.01F;
    } 
  }
  
  public void func_187073_a(float scale, EnumHandSide side) {
    float f = (side == EnumHandSide.RIGHT) ? 1.0F : -1.0F;
    ModelRenderer modelrenderer = func_187074_a(side);
    modelrenderer.field_78800_c += f;
    modelrenderer.func_78794_c(scale);
    modelrenderer.field_78800_c -= f;
  }
}
