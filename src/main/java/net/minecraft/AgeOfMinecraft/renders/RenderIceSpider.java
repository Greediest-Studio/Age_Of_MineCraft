package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySpider;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityIceSpider;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderIceSpider extends RenderSpider<EntityIceSpider> {
  private static final ResourceLocation cmmSpiderTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/ispider.png");
  
  private static final ResourceLocation spiderTextures = new ResourceLocation("ageofminecraft", "textures/entities/ice_spider.png");
  
  public RenderIceSpider(RenderManager renderManagerIn) {
    super(renderManagerIn);
  }
  
  protected ResourceLocation getEntityTexture(EntityIceSpider entity) {
    return EngenderConfig.mobs.useMobTalkerModels ? cmmSpiderTextures : spiderTextures;
  }
}
