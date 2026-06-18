package net.minecraft.AgeOfMinecraft.addons.draconicevolution;

import com.brandon3055.draconicevolution.helpers.ResourceHelperDE;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderGuardianProjectile extends Render<EntityGuardianProjectile> {
  public RenderGuardianProjectile(RenderManager renderManager) {
    super(renderManager);
  }
  
  public void doRender(EntityGuardianProjectile projectile, double x, double y, double z, float entityYaw, float partialTicks) {
    GlStateManager.pushMatrix();
    GlStateManager.translate((float)x, (float)y, (float)z);
    float height = (projectile.ticksExisted % 8) * 1.0F / 8.0F;
    switch (projectile.type) {
      case 1:
      case 3:
        height = 0.0F;
        break;
      case 2:
        height = 0.0F;
        break;
    } 
    bindEntityTexture(projectile);
    GlStateManager.enableRescaleNormal();
    GlStateManager.enableBlend();
    GlStateManager.blendFunc(770, 771);
    GlStateManager.alphaFunc(516, 0.0F);
    GlStateManager.disableLighting();
    float f2 = (projectile.type == 6) ? (projectile.power / 10.0F) : (projectile.power / 5.0F);
    GlStateManager.scale(f2 / 1.0F, f2 / 1.0F, f2 / 1.0F);
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder buffer = tessellator.getBuffer();
    float f3 = 0.0F;
    float f4 = 1.0F;
    float f5 = height;
    float f6 = height + 0.125F;
    float f7 = 1.0F;
    float f8 = 0.5F;
    float f9 = 0.25F;
    GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
    GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
    if (projectile.type == 1 || projectile.type == 3) {
      GlStateManager.translate(0.0D, 0.25D, 0.0D);
      GlStateManager.rotate((projectile.ticksExisted * 40) + partialTicks * 40.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.translate(0.0D, -0.25D, 0.0D);
    } else if (projectile.type == 2) {
      f5 = 0.0F;
      f6 = 1.0F;
    } 
    buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
    buffer.pos((0.0F - f8), (0.0F - f9), 0.0D).tex(f3, f6).endVertex();
    buffer.pos((f7 - f8), (0.0F - f9), 0.0D).tex(f4, f6).endVertex();
    buffer.pos((f7 - f8), (1.0F - f9), 0.0D).tex(f4, f5).endVertex();
    buffer.pos((0.0F - f8), (1.0F - f9), 0.0D).tex(f3, f5).endVertex();
    tessellator.draw();
    GlStateManager.enableLighting();
    GlStateManager.alphaFunc(516, 0.1F);
    GlStateManager.disableBlend();
    GlStateManager.disableRescaleNormal();
    GlStateManager.popMatrix();
  }
  
  protected ResourceLocation getEntityTexture(EntityGuardianProjectile entity) {
    switch (entity.type) {
      case 1:
      case 3:
        return ResourceHelperDE.getResource("textures/entity/projectile_fire.png");
      case 2:
        return ResourceHelperDE.getResourceRAW("textures/items/ender_pearl.png");
      case 4:
        return ResourceHelperDE.getResource("textures/entity/projectile_energy.png");
      case 5:
      case 6:
        return ResourceHelperDE.getResource("textures/entity/projectile_chaos.png");
    } 
    return ResourceHelperDE.getResource("textures/entity/projectile_fire.png");
  }
}
