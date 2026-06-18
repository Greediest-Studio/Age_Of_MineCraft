package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityEnderman;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerHeldBlock implements LayerRenderer<EntityLivingBase> {
  private final RenderEnderman endermanRenderer;
  
  public LayerHeldBlock(RenderEnderman p_i46122_1_) {
    this.endermanRenderer = p_i46122_1_;
  }
  
  public void func_177173_a(EntityEnderman entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    IBlockState iblockstate = entitylivingbaseIn.getHeldBlockState();
    if (iblockstate != null) {
      BlockRendererDispatcher blockrendererdispatcher = Minecraft.func_71410_x().func_175602_ab();
      GlStateManager.func_179091_B();
      GlStateManager.func_179094_E();
      if (EngenderConfig.mobs.useMobTalkerModels) {
        GlStateManager.func_179109_b(0.35F, 0.6F, -0.4F);
        GlStateManager.func_179114_b(45.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.func_179114_b(20.0F, 1.0F, 0.0F, 1.0F);
        if (entitylivingbaseIn.func_70093_af())
          GlStateManager.func_179109_b(0.35F, 0.25F, -0.4F); 
        if (entitylivingbaseIn.func_82150_aj()) {
          GlStateManager.func_179109_b(-0.35F, 0.25F, 0.5F);
          GlStateManager.func_179114_b(40.0F, 1.0F, 0.0F, 1.0F);
        } 
        if (entitylivingbaseIn.func_70631_g_()) {
          GlStateManager.func_179109_b(-0.15F, 0.5F, -0.1F);
          GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
        } 
      } else {
        if (entitylivingbaseIn.func_70631_g_()) {
          GlStateManager.func_179109_b(0.0F, 0.75F, 0.0F);
          GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
        } 
        GlStateManager.func_179109_b(0.0F, 0.6875F, -0.75F);
        GlStateManager.func_179114_b(20.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.func_179114_b(45.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.func_179109_b(0.25F, 0.1875F, 0.25F);
      } 
      float f = 0.5F;
      GlStateManager.func_179152_a(-f, -f, f);
      int i = entitylivingbaseIn.func_70070_b();
      int j = i % 65536;
      int k = i / 65536;
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, j, k);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      this.endermanRenderer.func_110776_a(TextureMap.field_110575_b);
      blockrendererdispatcher.func_175016_a(iblockstate, 1.0F);
      GlStateManager.func_179121_F();
      GlStateManager.func_179101_C();
    } 
  }
  
  public boolean func_177142_b() {
    return false;
  }
  
  public void func_177141_a(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
    func_177173_a((EntityEnderman)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
  }
}
