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
  
  public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
    bindEntityTexture(entity);
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    GlStateManager.pushMatrix();
    GlStateManager.disableLighting();
    GlStateManager.translate((float)x, (float)y, (float)z);
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder bufferbuilder = tessellator.getBuffer();
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
    GlStateManager.enableRescaleNormal();
    switch (((EntityBoneAttack)entity).getBoneType()) {
      case 0:
        GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - ((Entity)entity).prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - ((Entity)entity).prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
        GlStateManager.scale(0.125F, 0.125F, 0.125F);
        break;
      default:
        GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.scale(0.125F, 0.125F, 0.125F);
        GlStateManager.translate(8.0F, 0.0F, 0.0F);
        break;
    } 
    if (this.renderOutlines) {
      GlStateManager.enableColorMaterial();
      GlStateManager.enableOutlineMode(getTeamColor(entity));
    } 
    double v1 = 0.0D;
    double v2 = 0.15625D;
    if (((EntityBoneAttack)entity).isBlue()) {
      v1 += v2;
      v2 += v2;
    } 
    for (int j = 0; j < 4; j++) {
      GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.glNormal3f(0.0F, 0.0F, 0.05625F);
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(-8.0D, -2.0D, 0.0D).tex(0.0D, v1).endVertex();
      bufferbuilder.pos(8.0D, -2.0D, 0.0D).tex(0.78125D, v1).endVertex();
      bufferbuilder.pos(8.0D, 2.0D, 0.0D).tex(0.78125D, v2).endVertex();
      bufferbuilder.pos(-8.0D, 2.0D, 0.0D).tex(0.0D, v2).endVertex();
      tessellator.draw();
    } 
    if (this.renderOutlines) {
      GlStateManager.disableOutlineMode();
      GlStateManager.disableColorMaterial();
    } 
    GlStateManager.disableRescaleNormal();
    GlStateManager.enableLighting();
    GlStateManager.popMatrix();
    super.doRender(entity, x, y, z, entityYaw, partialTicks);
  }
  
  public ItemStack getStackToRender(T entityIn) {
    return new ItemStack(Items.NETHER_STAR);
  }
  
  protected ResourceLocation getEntityTexture(Entity entity) {
    return new ResourceLocation("ageofminecraft", "textures/entities/boneattacks.png");
  }
}
