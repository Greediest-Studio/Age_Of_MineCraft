package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySpider;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityCaveSpider;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCaveSpider extends RenderSpider<EntityCaveSpider> {
  private static final ResourceLocation cmmTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/cspider.png");
  
  private static final ResourceLocation textures = new ResourceLocation("textures/entity/spider/cave_spider.png");
  
  private static final ResourceLocation anticmmTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/anti/cspider.png");
  
  private static final ResourceLocation antiTextures = new ResourceLocation("ageofminecraft", "textures/entities/anti/cave_spider.png");
  
  public RenderCaveSpider(RenderManager renderManagerIn) {
    super(renderManagerIn);
    this.field_76989_e *= 0.7F;
  }
  
  protected void preRenderCallback(EntityCaveSpider entitylivingbaseIn, float partialTickTime) {
    super.func_77041_b((EntityLivingBase)entitylivingbaseIn, partialTickTime);
    this.field_76989_e *= 0.7F;
    if (EngenderConfig.mobs.useMobTalkerModels) {
      if (entitylivingbaseIn.isSitResting())
        GlStateManager.func_179109_b(0.0F, -0.2F, 0.0F); 
      if (entitylivingbaseIn.isSitResting() && entitylivingbaseIn.func_70631_g_())
        GlStateManager.func_179109_b(0.0F, 0.1F, 0.0F); 
      if (entitylivingbaseIn.func_184218_aH() && entitylivingbaseIn.func_70631_g_())
        GlStateManager.func_179109_b(0.0F, -0.15F, 0.0F); 
      GlStateManager.func_179152_a(0.85F, 0.85F, 0.85F);
      if (entitylivingbaseIn.func_184207_aI() && !entitylivingbaseIn.field_70122_E)
        if (!entitylivingbaseIn.func_70617_f_())
          GlStateManager.func_179109_b(0.0F, 0.5F, -0.4F);  
    } else {
      GlStateManager.func_179152_a(0.7F, 0.7F, 0.7F);
      if (entitylivingbaseIn.func_184218_aH())
        GlStateManager.func_179109_b(0.0F, 0.25F, 0.0F); 
    } 
    if (entitylivingbaseIn.field_70173_aa <= 21 && entitylivingbaseIn.field_70173_aa > 0) {
      float f5 = (entitylivingbaseIn.field_70173_aa + partialTickTime - 1.0F) / 20.0F * 1.6F;
      f5 = MathHelper.func_76129_c(f5);
      if (f5 > 1.0F)
        f5 = 1.0F; 
      GlStateManager.func_179152_a(f5, f5, f5);
      GlStateManager.func_179114_b(f5 * 90.0F - 90.0F, f5, f5, f5);
    } 
  }
  
  protected ResourceLocation getEntityTexture(EntityCaveSpider entity) {
    return EngenderConfig.mobs.useMobTalkerModels ? (entity.isAntiMob() ? anticmmTextures : cmmTextures) : (entity.isAntiMob() ? antiTextures : textures);
  }
}
