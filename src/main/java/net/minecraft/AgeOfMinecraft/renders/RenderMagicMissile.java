package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMagicMissile<T extends Entity> extends Render<T> {
  public RenderMagicMissile(RenderManager renderManagerIn) {
    super(renderManagerIn);
  }
  
  public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
    GlStateManager.pushMatrix();
    GlStateManager.translate((float)x, (float)y, (float)z);
    GlStateManager.enableRescaleNormal();
    GlStateManager.rotate(-RenderLayerCompat.getRenderManager(this).playerViewY, 0.0F, 1.0F, 0.0F);
    GlStateManager.rotate(((RenderLayerCompat.getRenderManager(this).options.thirdPersonView == 2) ? -1.0F : 1.0F) * RenderLayerCompat.getRenderManager(this).playerViewX, 1.0F, 0.0F, 0.0F);
    GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
    bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
    if (RenderLayerCompat.isRenderOutlines(this)) {
      GlStateManager.enableColorMaterial();
      GlStateManager.enableOutlineMode(getTeamColor(entity));
    } 
    Minecraft.getMinecraft().getRenderItem().renderItem(getStackToRender(entity), ItemCameraTransforms.TransformType.GROUND);
    if (RenderLayerCompat.isRenderOutlines(this)) {
      GlStateManager.disableOutlineMode();
      GlStateManager.disableColorMaterial();
    } 
    GlStateManager.disableRescaleNormal();
    GlStateManager.popMatrix();
    super.doRender(entity, x, y, z, entityYaw, partialTicks);
  }
  
  public ItemStack getStackToRender(T entityIn) {
    return new ItemStack(Items.NETHER_STAR);
  }
  
  protected ResourceLocation getEntityTexture(Entity entity) {
    return TextureMap.LOCATION_BLOCKS_TEXTURE;
  }
}
