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
    GlStateManager.func_179094_E();
    GlStateManager.func_179109_b((float)x, (float)y, (float)z);
    float height = (projectile.field_70173_aa % 8) * 1.0F / 8.0F;
    switch (projectile.type) {
      case 1:
      case 3:
        height = 0.0F;
        break;
      case 2:
        height = 0.0F;
        break;
    } 
    func_180548_c(projectile);
    GlStateManager.func_179091_B();
    GlStateManager.func_179147_l();
    GlStateManager.func_179112_b(770, 771);
    GlStateManager.func_179092_a(516, 0.0F);
    GlStateManager.func_179140_f();
    float f2 = (projectile.type == 6) ? (projectile.power / 10.0F) : (projectile.power / 5.0F);
    GlStateManager.func_179152_a(f2 / 1.0F, f2 / 1.0F, f2 / 1.0F);
    Tessellator tessellator = Tessellator.func_178181_a();
    BufferBuilder buffer = tessellator.func_178180_c();
    float f3 = 0.0F;
    float f4 = 1.0F;
    float f5 = height;
    float f6 = height + 0.125F;
    float f7 = 1.0F;
    float f8 = 0.5F;
    float f9 = 0.25F;
    GlStateManager.func_179114_b(180.0F - this.field_76990_c.field_78735_i, 0.0F, 1.0F, 0.0F);
    GlStateManager.func_179114_b(-this.field_76990_c.field_78732_j, 1.0F, 0.0F, 0.0F);
    if (projectile.type == 1 || projectile.type == 3) {
      GlStateManager.func_179137_b(0.0D, 0.25D, 0.0D);
      GlStateManager.func_179114_b((projectile.field_70173_aa * 40) + partialTicks * 40.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.func_179137_b(0.0D, -0.25D, 0.0D);
    } else if (projectile.type == 2) {
      f5 = 0.0F;
      f6 = 1.0F;
    } 
    buffer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
    buffer.func_181662_b((0.0F - f8), (0.0F - f9), 0.0D).func_187315_a(f3, f6).func_181675_d();
    buffer.func_181662_b((f7 - f8), (0.0F - f9), 0.0D).func_187315_a(f4, f6).func_181675_d();
    buffer.func_181662_b((f7 - f8), (1.0F - f9), 0.0D).func_187315_a(f4, f5).func_181675_d();
    buffer.func_181662_b((0.0F - f8), (1.0F - f9), 0.0D).func_187315_a(f3, f5).func_181675_d();
    tessellator.func_78381_a();
    GlStateManager.func_179145_e();
    GlStateManager.func_179092_a(516, 0.1F);
    GlStateManager.func_179084_k();
    GlStateManager.func_179101_C();
    GlStateManager.func_179121_F();
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
