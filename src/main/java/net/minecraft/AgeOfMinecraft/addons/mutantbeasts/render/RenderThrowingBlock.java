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
    this.shadowSize = 0.6F;
  }
  
  public void doRender(EntityThrowingBlock entity, double x, double y, double z, float entityYaw, float partialTicks) {
    GlStateManager.enableRescaleNormal();
    GlStateManager.pushMatrix();
    GlStateManager.translate((float)x, (float)y + 0.5F, (float)z);
    GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
    GlStateManager.rotate((entity.ticksExisted + partialTicks) * 20.0F, 1.0F, 0.0F, 0.0F);
    GlStateManager.rotate((entity.ticksExisted + partialTicks) * 12.0F, 0.0F, 0.0F, -1.0F);
    float scale = 0.75F;
    GlStateManager.scale(-scale, -scale, scale);
    bindEntityTexture(entity);
    int var4 = entity.getBrightnessForRender();
    int var5 = var4 % 65536;
    int var6 = var4 / 65536;
    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var5, var6);
    GlStateManager.enableNormalize();
    GlStateManager.enableBlend();
    GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    GlStateManager.translate(-0.5F, -0.5F, 0.5F);
    Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlockBrightness(entity.getBlockState(), entity.getBrightness());
    GlStateManager.disableBlend();
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    GlStateManager.popMatrix();
    GlStateManager.disableRescaleNormal();
    super.doRender(entity, x, y, z, entityYaw, partialTicks);
  }
  
  protected ResourceLocation getEntityTexture(EntityThrowingBlock entity) {
    return TextureMap.LOCATION_BLOCKS_TEXTURE;
  }
}
