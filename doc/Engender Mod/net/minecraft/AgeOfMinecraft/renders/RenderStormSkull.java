package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStormSkull;
import net.minecraft.AgeOfMinecraft.models.ModelStormSkull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderStormSkull extends Render<EntityWitherStormSkull> {
  private static final ResourceLocation witherTextures = new ResourceLocation("ageofminecraft", "textures/entities/wither_storm_skull.png");
  
  private final ModelStormSkull skeletonHeadModel = new ModelStormSkull();
  
  public RenderStormSkull(RenderManager renderManagerIn) {
    super(renderManagerIn);
  }
  
  public void doRender(EntityWitherStormSkull entity, double x, double y, double z, float entityYaw, float partialTicks) {
    GlStateManager.func_179094_E();
    GlStateManager.func_179129_p();
    GlStateManager.func_179109_b((float)x, (float)y, (float)z);
    float f2 = 0.0625F;
    GlStateManager.func_179091_B();
    GlStateManager.func_179141_d();
    GlStateManager.func_179114_b(-entity.field_70177_z, 0.0F, 1.0F, 0.0F);
    GlStateManager.func_179114_b(-entity.field_70125_A, 1.0F, 0.0F, 0.0F);
    GlStateManager.func_179109_b(0.0F, -0.5F, 0.0F);
    func_180548_c((Entity)entity);
    if (this.field_188301_f) {
      GlStateManager.func_179142_g();
      GlStateManager.func_187431_e(func_188298_c((Entity)entity));
    } 
    this.skeletonHeadModel.func_78088_a((Entity)entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, f2);
    if (this.field_188301_f) {
      GlStateManager.func_187417_n();
      GlStateManager.func_179119_h();
    } 
    BlockRendererDispatcher blockrendererdispatcher = Minecraft.func_71410_x().func_175602_ab();
    func_110776_a(TextureMap.field_110575_b);
    GlStateManager.func_179089_o();
    GlStateManager.func_179094_E();
    GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
    GlStateManager.func_179109_b(-0.5F, 0.5F, -0.5F);
    blockrendererdispatcher.func_175016_a(Blocks.field_150480_ab.func_176223_P(), 1.0F);
    GlStateManager.func_179121_F();
    GlStateManager.func_179129_p();
    GlStateManager.func_179121_F();
    super.func_76986_a((Entity)entity, x, y, z, entityYaw, partialTicks);
  }
  
  protected ResourceLocation getEntityTexture(EntityWitherStormSkull entity) {
    return witherTextures;
  }
}
