package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityCreeper;
import net.minecraft.AgeOfMinecraft.models.ModelCMMCreeper;
import net.minecraft.AgeOfMinecraft.models.ModelCreeper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerCreeperCharge implements LayerRenderer<EntityCreeper> {
  private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
  
  private static final ResourceLocation Anti_LIGHTNING_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/anti/creeper_armor.png");
  
  private final RenderCreeper creeperRenderer;
  
  private final ModelCreeper creeperModel = new ModelCreeper(2.0F);
  
  private final ModelCMMCreeper cuteCreeperModel = new ModelCMMCreeper(1.0F);
  
  public LayerCreeperCharge(RenderCreeper creeperRendererIn) {
    this.creeperRenderer = creeperRendererIn;
  }
  
  public void doRenderLayer(EntityCreeper entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    if (entitylivingbaseIn.getIllusionFormTime() <= 0 && entitylivingbaseIn.getPowered()) {
      boolean flag = entitylivingbaseIn.func_82150_aj();
      GlStateManager.func_179132_a(!flag);
      this.creeperRenderer.func_110776_a(entitylivingbaseIn.isAntiMob() ? Anti_LIGHTNING_TEXTURE : LIGHTNING_TEXTURE);
      GlStateManager.func_179128_n(5890);
      GlStateManager.func_179096_D();
      float f = entitylivingbaseIn.field_70173_aa + partialTicks;
      if (entitylivingbaseIn.isAntiMob())
        f = -f; 
      GlStateManager.func_179109_b(f * 0.01F, f * 0.01F, 0.0F);
      GlStateManager.func_179128_n(5888);
      GlStateManager.func_179147_l();
      int c0 = entitylivingbaseIn.func_82150_aj() ? 61680 : 15728880;
      int i = c0 % 65536;
      int j = c0 / 65536;
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, i / 1.0F, j / 1.0F);
      GlStateManager.func_179145_e();
      float ran = entitylivingbaseIn.func_70681_au().nextFloat() * 0.25F;
      GlStateManager.func_179131_c(0.75F + ran, 0.75F + ran, 0.75F + ran, 1.0F);
      GlStateManager.func_187401_a(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
      if (EngenderConfig.mobs.useMobTalkerModels) {
        this.cuteCreeperModel.func_178686_a(this.creeperRenderer.func_177087_b());
        this.cuteCreeperModel.func_78088_a((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      } else {
        this.creeperModel.func_178686_a(this.creeperRenderer.func_177087_b());
        this.creeperModel.func_78088_a((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      } 
      GlStateManager.func_179128_n(5890);
      GlStateManager.func_179096_D();
      GlStateManager.func_179128_n(5888);
      GlStateManager.func_179145_e();
      GlStateManager.func_179084_k();
      GlStateManager.func_179132_a(flag);
    } 
  }
  
  public boolean func_177142_b() {
    return true;
  }
}
