package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.entity.tame.cameos.EntityBoneAttack;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBoneAttack<T extends Entity> extends Render<T> {
  public RenderBoneAttack(RenderManager renderManagerIn) {
    super(renderManagerIn);
  }
  
  public void func_76986_a(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
    func_180548_c((Entity)entity);
    GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
    GlStateManager.func_179094_E();
    GlStateManager.func_179140_f();
    GlStateManager.func_179109_b((float)x, (float)y, (float)z);
    Tessellator tessellator = Tessellator.func_178181_a();
    BufferBuilder bufferbuilder = tessellator.func_178180_c();
    int i = 0;
    float f = 0.0F;
    float f1 = 0.5F;
    float f2 = 0.0F;
    float f3 = 0.15625F;
    float f4 = 0.0F;
    float f5 = 0.15625F;
    float f6 = 0.15625F;
    float f7 = 0.3125F;
    float f8 = 0.05625F;
    GlStateManager.func_179091_B();
    switch (((EntityBoneAttack)entity).getBoneType()) {
      case 0:
        GlStateManager.func_179114_b(((Entity)entity).field_70126_B + (((Entity)entity).field_70177_z - ((Entity)entity).field_70126_B) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.func_179114_b(((Entity)entity).field_70127_C + (((Entity)entity).field_70125_A - ((Entity)entity).field_70127_C) * partialTicks, 0.0F, 0.0F, 1.0F);
        GlStateManager.func_179152_a(0.125F, 0.125F, 0.125F);
        break;
      default:
        GlStateManager.func_179114_b(90.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.func_179152_a(0.125F, 0.125F, 0.125F);
        GlStateManager.func_179109_b(8.0F, 0.0F, 0.0F);
        break;
    } 
    if (this.field_188301_f) {
      GlStateManager.func_179142_g();
      GlStateManager.func_187431_e(func_188298_c((Entity)entity));
    } 
    double v1 = 0.0D;
    double v2 = 0.15625D;
    if (((EntityBoneAttack)entity).isBlue()) {
      v1 += v2;
      v2 += v2;
    } 
    for (int j = 0; j < 4; j++) {
      GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_187432_a(0.0F, 0.0F, 0.05625F);
      bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
      bufferbuilder.func_181662_b(-8.0D, -2.0D, 0.0D).func_187315_a(0.0D, v1).func_181675_d();
      bufferbuilder.func_181662_b(8.0D, -2.0D, 0.0D).func_187315_a(0.78125D, v1).func_181675_d();
      bufferbuilder.func_181662_b(8.0D, 2.0D, 0.0D).func_187315_a(0.78125D, v2).func_181675_d();
      bufferbuilder.func_181662_b(-8.0D, 2.0D, 0.0D).func_187315_a(0.0D, v2).func_181675_d();
      tessellator.func_78381_a();
    } 
    if (this.field_188301_f) {
      GlStateManager.func_187417_n();
      GlStateManager.func_179119_h();
    } 
    GlStateManager.func_179101_C();
    GlStateManager.func_179145_e();
    GlStateManager.func_179121_F();
    super.func_76986_a((Entity)entity, x, y, z, entityYaw, partialTicks);
  }
  
  public ItemStack getStackToRender(T entityIn) {
    return new ItemStack(Items.field_151156_bN);
  }
  
  protected ResourceLocation func_110775_a(Entity entity) {
    return new ResourceLocation("ageofminecraft", "textures/entities/boneattacks.png");
  }
}
