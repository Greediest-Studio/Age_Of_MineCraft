package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.render;

import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityThrowingBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderThrowingBlock extends Render<EntityThrowingBlock> {
  public RenderThrowingBlock(RenderManager renderManager) {
    super(renderManager);
    this.field_76989_e = 0.6F;
  }
  
  public void doRender(EntityThrowingBlock entity, double x, double y, double z, float entityYaw, float partialTicks) {
    GlStateManager.func_179091_B();
    GlStateManager.func_179094_E();
    GlStateManager.func_179109_b((float)x, (float)y + 0.5F, (float)z);
    GlStateManager.func_179114_b(45.0F, 0.0F, 1.0F, 0.0F);
    GlStateManager.func_179114_b((entity.field_70173_aa + partialTicks) * 20.0F, 1.0F, 0.0F, 0.0F);
    GlStateManager.func_179114_b((entity.field_70173_aa + partialTicks) * 12.0F, 0.0F, 0.0F, -1.0F);
    float scale = 0.75F;
    GlStateManager.func_179152_a(-scale, -scale, scale);
    func_180548_c((Entity)entity);
    int var4 = entity.func_70070_b();
    int var5 = var4 % 65536;
    int var6 = var4 / 65536;
    OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, var5, var6);
    GlStateManager.func_179108_z();
    GlStateManager.func_179147_l();
    GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    GlStateManager.func_179109_b(-0.5F, -0.5F, 0.5F);
    Minecraft.func_71410_x().func_175602_ab().func_175016_a(entity.getBlockState(), entity.func_70013_c());
    GlStateManager.func_179084_k();
    GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
    GlStateManager.func_179121_F();
    GlStateManager.func_179101_C();
    super.func_76986_a((Entity)entity, x, y, z, entityYaw, partialTicks);
  }
  
  protected ResourceLocation getEntityTexture(EntityThrowingBlock entity) {
    return TextureMap.field_110575_b;
  }
}
