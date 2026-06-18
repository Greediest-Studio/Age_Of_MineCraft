package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityEnderman;
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
public class LayerEndermanEyes implements LayerRenderer<EntityLivingBase> {
  private static final ResourceLocation RES_CMM_ENDERMAN_EYES = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/enderman_eyes.png");
  
  private static final ResourceLocation RES_ENDERMAN_EYES = new ResourceLocation("textures/entity/enderman/enderman_eyes.png");
  
  private static final ResourceLocation antiRES_CMM_ENDERMAN_EYES = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/anti/enderman_eyes.png");
  
  private static final ResourceLocation antiRES_ENDERMAN_EYES = new ResourceLocation("ageofminecraft", "textures/entities/anti/enderman_eyes.png");
  
  private final RenderEnderman endermanRenderer;
  
  public LayerEndermanEyes(RenderEnderman p_i46117_1_) {
    this.endermanRenderer = p_i46117_1_;
  }
  
  public void func_177201_a(EntityEnderman p_177201_1_, float p_177201_2_, float p_177201_3_, float p_177201_4_, float p_177201_5_, float p_177201_6_, float p_177201_7_, float p_177201_8_) {
    if (p_177201_1_.func_70089_S()) {
      this.endermanRenderer.func_110776_a(p_177201_1_.canDodgeAllAttacks() ? new ResourceLocation("textures/entity/enderdragon/dragon_exploding.png") : (EngenderConfig.mobs.useMobTalkerModels ? (p_177201_1_.isAntiMob() ? antiRES_CMM_ENDERMAN_EYES : RES_CMM_ENDERMAN_EYES) : (p_177201_1_.isAntiMob() ? antiRES_ENDERMAN_EYES : RES_ENDERMAN_EYES)));
      GlStateManager.func_179147_l();
      GlStateManager.func_179118_c();
      GlStateManager.func_179112_b(1, 1);
      GlStateManager.func_179140_f();
      if (p_177201_1_.canDodgeAllAttacks()) {
        GlStateManager.func_179128_n(5890);
        GlStateManager.func_179096_D();
        float f7 = p_177201_1_.field_70173_aa + p_177201_4_;
        float f8 = MathHelper.func_76134_b(f7 * 0.2F);
        float f9 = f7 * 0.01F;
        GlStateManager.func_179109_b(f8, f9, 0.0F);
        GlStateManager.func_179128_n(5888);
      } 
      if (p_177201_1_.func_82150_aj()) {
        GlStateManager.func_179132_a(false);
      } else {
        GlStateManager.func_179132_a(true);
      } 
      int c0 = 15728880;
      int i = c0 % 65536;
      int j = c0 / 65536;
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, i / 1.0F, j / 1.0F);
      GlStateManager.func_179145_e();
      if (p_177201_1_.canDodgeAllAttacks()) {
        GlStateManager.func_179131_c(0.5F, 0.5F, 0.5F, 1.0F);
      } else {
        GlStateManager.func_179131_c(1.0F, p_177201_1_.andr ? 0.5F : 1.0F, p_177201_1_.andr ? 0.5F : 1.0F, 1.0F);
      } 
      this.endermanRenderer.func_177087_b().func_78088_a((Entity)p_177201_1_, p_177201_2_, p_177201_3_, p_177201_5_, p_177201_6_, p_177201_7_, p_177201_8_);
      this.endermanRenderer.func_177105_a((EntityLiving)p_177201_1_);
      GlStateManager.func_179084_k();
      GlStateManager.func_179141_d();
      if (p_177201_1_.canDodgeAllAttacks()) {
        GlStateManager.func_179128_n(5890);
        GlStateManager.func_179096_D();
        GlStateManager.func_179128_n(5888);
      } 
    } 
  }
  
  public boolean func_177142_b() {
    return false;
  }
  
  public void func_177141_a(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
    func_177201_a((EntityEnderman)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
  }
}
