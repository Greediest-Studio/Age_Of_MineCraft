package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity;

import com.shinoow.abyssalcraft.api.item.ACItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderOmotholCharge<T extends Entity> extends Render<T> {
  public RenderOmotholCharge(RenderManager renderManagerIn) {
    super(renderManagerIn);
  }
  
  public void func_76986_a(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
    GlStateManager.func_179094_E();
    GlStateManager.func_179109_b((float)x, (float)y, (float)z);
    GlStateManager.func_179091_B();
    GlStateManager.func_179152_a(2.0F, 2.0F, 2.0F);
    GlStateManager.func_179114_b(-this.field_76990_c.field_78735_i, 0.0F, 1.0F, 0.0F);
    GlStateManager.func_179114_b(((this.field_76990_c.field_78733_k.field_74320_O == 2) ? -1 : true) * this.field_76990_c.field_78732_j, 1.0F, 0.0F, 0.0F);
    GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
    func_110776_a(TextureMap.field_110575_b);
    if (this.field_188301_f) {
      GlStateManager.func_179142_g();
      GlStateManager.func_187431_e(func_188298_c((Entity)entity));
    } 
    Minecraft.func_71410_x().func_175599_af().func_181564_a(getStackToRender(entity), ItemCameraTransforms.TransformType.GROUND);
    if (this.field_188301_f) {
      GlStateManager.func_187417_n();
      GlStateManager.func_179119_h();
    } 
    GlStateManager.func_179101_C();
    GlStateManager.func_179121_F();
    super.func_76986_a((Entity)entity, x, y, z, entityYaw, partialTicks);
  }
  
  public ItemStack getStackToRender(T entityIn) {
    return new ItemStack(ACItems.essence, 1, 2);
  }
  
  protected ResourceLocation func_110775_a(Entity entity) {
    return TextureMap.field_110575_b;
  }
}
