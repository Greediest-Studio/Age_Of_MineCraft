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
  
  public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
    GlStateManager.pushMatrix();
    GlStateManager.translate((float)x, (float)y, (float)z);
    GlStateManager.enableRescaleNormal();
    GlStateManager.scale(2.0F, 2.0F, 2.0F);
    GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
    GlStateManager.rotate(((this.renderManager.options.thirdPersonView == 2) ? -1.0F : 1.0F) * this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
    GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
    bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
    if (this.renderOutlines) {
      GlStateManager.enableColorMaterial();
      GlStateManager.enableOutlineMode(getTeamColor(entity));
    } 
    Minecraft.getMinecraft().getRenderItem().renderItem(getStackToRender(entity), ItemCameraTransforms.TransformType.GROUND);
    if (this.renderOutlines) {
      GlStateManager.disableOutlineMode();
      GlStateManager.disableColorMaterial();
    } 
    GlStateManager.disableRescaleNormal();
    GlStateManager.popMatrix();
    super.doRender(entity, x, y, z, entityYaw, partialTicks);
  }
  
  public ItemStack getStackToRender(T entityIn) {
    return new ItemStack(ACItems.essence, 1, 2);
  }
  
  protected ResourceLocation getEntityTexture(Entity entity) {
    return TextureMap.LOCATION_BLOCKS_TEXTURE;
  }
}
