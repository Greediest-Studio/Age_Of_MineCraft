package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityCoraliumChargeOther;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCoraliumCharge extends Render<EntityCoraliumChargeOther> {
  private static final ResourceLocation DRAGON_FIREBALL_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/coralium_fireball.png");
  
  public RenderCoraliumCharge(RenderManager renderManagerIn) {
    super(renderManagerIn);
  }
  
  public void doRender(EntityCoraliumChargeOther entity, double x, double y, double z, float entityYaw, float partialTicks) {
    GlStateManager.func_179094_E();
    func_180548_c((Entity)entity);
    GlStateManager.func_179109_b((float)x, (float)y, (float)z);
    GlStateManager.func_179091_B();
    GlStateManager.func_179152_a(2.0F, 2.0F, 2.0F);
    Tessellator tessellator = Tessellator.func_178181_a();
    BufferBuilder vertexbuffer = tessellator.func_178180_c();
    GlStateManager.func_179114_b(180.0F - this.field_76990_c.field_78735_i, 0.0F, 1.0F, 0.0F);
    GlStateManager.func_179114_b(((this.field_76990_c.field_78733_k.field_74320_O == 2) ? -1 : true) * -this.field_76990_c.field_78732_j, 1.0F, 0.0F, 0.0F);
    if (this.field_188301_f) {
      GlStateManager.func_179142_g();
      GlStateManager.func_187431_e(func_188298_c((Entity)entity));
    } 
    vertexbuffer.func_181668_a(7, DefaultVertexFormats.field_181710_j);
    vertexbuffer.func_181662_b(-0.5D, -0.25D, 0.0D).func_187315_a(0.0D, 1.0D).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
    vertexbuffer.func_181662_b(0.5D, -0.25D, 0.0D).func_187315_a(1.0D, 1.0D).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
    vertexbuffer.func_181662_b(0.5D, 0.75D, 0.0D).func_187315_a(1.0D, 0.0D).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
    vertexbuffer.func_181662_b(-0.5D, 0.75D, 0.0D).func_187315_a(0.0D, 0.0D).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
    tessellator.func_78381_a();
    if (this.field_188301_f) {
      GlStateManager.func_187417_n();
      GlStateManager.func_179119_h();
    } 
    GlStateManager.func_179101_C();
    GlStateManager.func_179121_F();
    super.func_76986_a((Entity)entity, x, y, z, entityYaw, partialTicks);
  }
  
  protected ResourceLocation getEntityTexture(EntityCoraliumChargeOther entity) {
    return DRAGON_FIREBALL_TEXTURE;
  }
}
