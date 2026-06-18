package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityAbyssalPortal;
import net.minecraft.AgeOfMinecraft.models.ModelPortal;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerAbyssalPortalOverlay implements LayerRenderer<EntityLivingBase> {
  private static final ResourceLocation PORTAL_OVERLAY = new ResourceLocation("ageofminecraft", "textures/entities/portals/abyssal_portal_portal.png");
  
  private final RenderAbyssalPortal renderer;
  
  private ModelPortal layerModel;
  
  public LayerAbyssalPortalOverlay(RenderAbyssalPortal p_i46109_1_) {
    this.renderer = p_i46109_1_;
    this.layerModel = new ModelPortal(true);
  }
  
  public void func_177148_a(EntityAbyssalPortal entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    float f = entitylivingbaseIn.field_70173_aa + partialTicks;
    int c0 = 15728880;
    int i = c0 % 65536;
    int j = c0 / 65536;
    GlStateManager.func_179108_z();
    GlStateManager.func_179147_l();
    GlStateManager.func_179112_b(770, 771);
    GlStateManager.func_179132_a(!entitylivingbaseIn.func_82150_aj());
    this.renderer.func_110776_a(new ResourceLocation("textures/entity/end_portal.png"));
    GlStateManager.func_179128_n(5890);
    GlStateManager.func_179096_D();
    GlStateManager.func_179109_b(f * 0.5F, f * 0.25F, f * -0.1F);
    GlStateManager.func_179114_b(30.0F, 0.0F, 0.0F, 1.0F);
    GlStateManager.func_179128_n(5888);
    OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, i / 1.0F, j / 1.0F);
    GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 0.9F);
    GlStateManager.func_179152_a(0.999F, 0.999F, 0.999F);
    this.layerModel.onlybase = true;
    this.layerModel.func_78088_a((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    this.layerModel.func_78087_a(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, (Entity)entitylivingbaseIn);
    this.renderer.func_177105_a((EntityLiving)entitylivingbaseIn);
    GlStateManager.func_179128_n(5890);
    GlStateManager.func_179096_D();
    GlStateManager.func_179128_n(5888);
    GlStateManager.func_179145_e();
    GlStateManager.func_179084_k();
    GlStateManager.func_179133_A();
    GlStateManager.func_179108_z();
    GlStateManager.func_179147_l();
    GlStateManager.func_179112_b(770, 771);
    GlStateManager.func_179132_a(!entitylivingbaseIn.func_82150_aj());
    this.renderer.func_110776_a(PORTAL_OVERLAY);
    GlStateManager.func_179128_n(5890);
    GlStateManager.func_179096_D();
    GlStateManager.func_179109_b(f * -5.0E-5F, f * 2.5E-4F, f * 1.0E-4F);
    GlStateManager.func_179114_b(-55.0F, 0.0F, 0.0F, 1.0F);
    GlStateManager.func_179128_n(5888);
    OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, i / 1.0F, j / 1.0F);
    GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 0.8F);
    this.layerModel.onlybase = true;
    this.layerModel.func_78088_a((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    this.layerModel.func_78087_a(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, (Entity)entitylivingbaseIn);
    this.renderer.func_177105_a((EntityLiving)entitylivingbaseIn);
    GlStateManager.func_179128_n(5890);
    GlStateManager.func_179096_D();
    GlStateManager.func_179128_n(5888);
    GlStateManager.func_179145_e();
    GlStateManager.func_179084_k();
    GlStateManager.func_179133_A();
    GlStateManager.func_179108_z();
    GlStateManager.func_179147_l();
    GlStateManager.func_179112_b(770, 771);
    GlStateManager.func_179132_a(!entitylivingbaseIn.func_82150_aj());
    this.renderer.func_110776_a(PORTAL_OVERLAY);
    GlStateManager.func_179128_n(5890);
    GlStateManager.func_179096_D();
    GlStateManager.func_179109_b(f * 5.0E-5F, f * 2.5E-4F, f * -1.0E-4F);
    GlStateManager.func_179114_b(f * -0.01F, 0.0F, 0.0F, 1.0F);
    GlStateManager.func_179128_n(5888);
    OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, i / 1.0F, j / 1.0F);
    GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 0.75F);
    this.layerModel.onlybase = true;
    this.layerModel.func_78088_a((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    this.layerModel.func_78087_a(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, (Entity)entitylivingbaseIn);
    this.renderer.func_177105_a((EntityLiving)entitylivingbaseIn);
    GlStateManager.func_179128_n(5890);
    GlStateManager.func_179096_D();
    GlStateManager.func_179128_n(5888);
    GlStateManager.func_179145_e();
    GlStateManager.func_179084_k();
    GlStateManager.func_179133_A();
    GlStateManager.func_179108_z();
    GlStateManager.func_179147_l();
    GlStateManager.func_179112_b(770, 771);
    GlStateManager.func_179132_a(!entitylivingbaseIn.func_82150_aj());
    this.renderer.func_110776_a(new ResourceLocation("textures/entity/end_portal.png"));
    GlStateManager.func_179128_n(5890);
    GlStateManager.func_179096_D();
    GlStateManager.func_179109_b(f * 0.95F, f * 0.925F, f * -0.1F);
    GlStateManager.func_179128_n(5888);
    OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, i / 1.0F, j / 1.0F);
    GlStateManager.func_179131_c(0.4F + MathHelper.func_76134_b(f * 0.1F) * 0.25F, 0.4F + MathHelper.func_76134_b(f * 0.1F) * 0.25F, 0.4F + MathHelper.func_76134_b(f * 0.1F) * 0.25F, 0.4F + MathHelper.func_76134_b(f * 0.1F) * 0.25F);
    this.layerModel.onlybase = true;
    this.layerModel.func_78088_a((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    this.layerModel.func_78087_a(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, (Entity)entitylivingbaseIn);
    this.renderer.func_177105_a((EntityLiving)entitylivingbaseIn);
    GlStateManager.func_179128_n(5890);
    GlStateManager.func_179096_D();
    GlStateManager.func_179128_n(5888);
    GlStateManager.func_179145_e();
    GlStateManager.func_179084_k();
    GlStateManager.func_179133_A();
  }
  
  public boolean func_177142_b() {
    return false;
  }
  
  public void func_177141_a(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
    func_177148_a((EntityAbyssalPortal)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
  }
}
