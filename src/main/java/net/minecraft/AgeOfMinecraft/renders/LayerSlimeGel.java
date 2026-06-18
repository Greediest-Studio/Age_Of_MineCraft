package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySlime;
import net.minecraft.AgeOfMinecraft.models.ModelCMMSlime;
import net.minecraft.AgeOfMinecraft.models.ModelSlime;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerSlimeGel implements LayerRenderer<EntityLivingBase> {
  private static final ResourceLocation cmmSlimeLargeTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/slimedress4.png");
  
  private static final ResourceLocation cmmSlimeSmallTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/slimedress2.png");
  
  private static final ResourceLocation cmmSlimeTinyTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/slimedress1.png");
  
  private static final ResourceLocation slimeTextures = new ResourceLocation("textures/entity/slime/slime.png");
  
  private static final ResourceLocation anticmmSlimeLargeTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/anti/slimedress4.png");
  
  private static final ResourceLocation anticmmSlimeSmallTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/anti/slimedress2.png");
  
  private static final ResourceLocation anticmmSlimeTinyTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/anti/slimedress1.png");
  
  private static final ResourceLocation antislimeTextures = new ResourceLocation("ageofminecraft", "textures/entities/anti/slime.png");
  
  private final RenderSlime slimeRenderer;
  
  private ModelBase slimeModel = EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)new ModelCMMSlime(0.25F) : (ModelBase)new ModelSlime(0);
  
  public LayerSlimeGel(RenderSlime p_i46111_1_) {
    this.slimeRenderer = p_i46111_1_;
  }
  
  public void doRenderLayer(EntitySlime p_177159_1_, float p_177159_2_, float p_177159_3_, float p_177159_4_, float p_177159_5_, float p_177159_6_, float p_177159_7_, float p_177159_8_) {
    if (!p_177159_1_.isInvisible()) {
      this.slimeModel = EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)new ModelCMMSlime(0.25F) : (ModelBase)new ModelSlime(0);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 0.75F);
      GlStateManager.enableNormalize();
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(770, 771);
      this.slimeRenderer.bindTexture(EngenderConfig.mobs.useMobTalkerModels ? ((p_177159_1_.getSlimeSize() <= 1) ? (p_177159_1_.isAntiMob() ? anticmmSlimeTinyTextures : cmmSlimeTinyTextures) : ((p_177159_1_.getSlimeSize() == 2 || p_177159_1_.getSlimeSize() == 3) ? (p_177159_1_.isAntiMob() ? anticmmSlimeSmallTextures : cmmSlimeSmallTextures) : (p_177159_1_.isAntiMob() ? anticmmSlimeLargeTextures : cmmSlimeLargeTextures))) : (p_177159_1_.isAntiMob() ? antislimeTextures : slimeTextures));
      this.slimeModel.setModelAttributes(this.slimeRenderer.getMainModel());
      this.slimeModel.render((Entity)p_177159_1_, p_177159_2_, p_177159_3_, p_177159_5_, p_177159_6_, p_177159_7_, p_177159_8_);
      GlStateManager.disableBlend();
      GlStateManager.disableNormalize();
    } 
  }
  
  public boolean shouldCombineTextures() {
    return true;
  }
  
  public void doRenderLayer(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
    doRenderLayer((EntitySlime)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
  }
}
