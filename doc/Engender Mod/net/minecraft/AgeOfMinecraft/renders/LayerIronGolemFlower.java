package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityIronGolem;
import net.minecraft.AgeOfMinecraft.models.ModelCMMIronGolem;
import net.minecraft.AgeOfMinecraft.models.ModelIronGolem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerIronGolemFlower implements LayerRenderer<EntityLivingBase> {
  private final RenderIronGolem ironGolemRenderer;
  
  public LayerIronGolemFlower(RenderIronGolem p_i46107_1_) {
    this.ironGolemRenderer = p_i46107_1_;
  }
  
  public void func_177153_a(EntityIronGolem p_177153_1_, float p_177153_2_, float p_177153_3_, float p_177153_4_, float p_177153_5_, float p_177153_6_, float p_177153_7_, float p_177153_8_) {
    if (p_177153_1_.getIllusionFormTime() <= 0 && p_177153_1_.getHoldRoseTick() != 0) {
      BlockRendererDispatcher blockrendererdispatcher = Minecraft.func_71410_x().func_175602_ab();
      GlStateManager.func_179091_B();
      GlStateManager.func_179094_E();
      GlStateManager.func_179114_b(5.0F + 180.0F * (EngenderConfig.mobs.useMobTalkerModels ? ((ModelCMMIronGolem)this.ironGolemRenderer.func_177087_b()).RArm.field_78795_f : ((ModelIronGolem)this.ironGolemRenderer.func_177087_b()).ironGolemRightArm.field_78795_f) / 3.1415927F, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
      if (p_177153_1_.func_70631_g_())
        if (EngenderConfig.mobs.useMobTalkerModels) {
          GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
          GlStateManager.func_179109_b(0.0F, 1.0F, -1.1F);
        }  
      if (EngenderConfig.mobs.useMobTalkerModels) {
        GlStateManager.func_179109_b(-0.525F, 0.0F, -0.55F);
      } else {
        GlStateManager.func_179109_b(-0.9375F, -0.625F, -0.9375F);
      } 
      float f7 = 0.5F;
      GlStateManager.func_179152_a(f7, -f7, f7);
      int i = p_177153_1_.func_70070_b();
      int j = i % 65536;
      int k = i / 65536;
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, j / 1.0F, k / 1.0F);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      this.ironGolemRenderer.func_110776_a(TextureMap.field_110575_b);
      blockrendererdispatcher.func_175016_a(Blocks.field_150328_O.func_176223_P(), 1.0F);
      GlStateManager.func_179121_F();
      GlStateManager.func_179101_C();
    } 
  }
  
  public boolean func_177142_b() {
    return false;
  }
  
  public void func_177141_a(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
    func_177153_a((EntityIronGolem)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
  }
}
