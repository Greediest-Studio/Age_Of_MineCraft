package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.render;

import chumbanotz.mutantbeasts.MutantBeasts;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityEndersoulFragment;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.model.ModelEndersoulFragment;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEndersoulFragment extends Render<EntityEndersoulFragment> {
  private static final ResourceLocation TEXTURE = MutantBeasts.getEntityTexture("endersoul_fragment");
  
  private final ModelEndersoulFragment modelRod = new ModelEndersoulFragment();
  
  public RenderEndersoulFragment(RenderManager renderManager) {
    super(renderManager);
    this.shadowSize = 0.3F;
    this.shadowOpaque = 0.5F;
  }
  
  public void doRender(EntityEndersoulFragment entity, double x, double y, double z, float entityYaw, float partialTicks) {
    super.doRender(entity, x, y, z, entityYaw, partialTicks);
    GlStateManager.pushMatrix();
    GlStateManager.translate((float)x, (float)y - 1.9F, (float)z);
    GlStateManager.scale(1.6F, 1.6F, 1.6F);
    bindEntityTexture(entity);
    if (this.renderOutlines) {
      GlStateManager.enableColorMaterial();
      GlStateManager.enableOutlineMode(getTeamColor(entity));
    } 
    GlStateManager.disableLighting();
    GlStateManager.matrixMode(5890);
    GlStateManager.loadIdentity();
    float add = (entity.ticksExisted + partialTicks) * 0.008F;
    GlStateManager.translate(add, add, 0.0F);
    GlStateManager.matrixMode(5888);
    GlStateManager.enableNormalize();
    GlStateManager.enableBlend();
    GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    int var5 = 61680;
    int var6 = var5 % 65536;
    int var7 = var5 / 65536;
    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var6, var7);
    GlStateManager.color(0.9F, 0.3F, 1.0F, 1.0F);
    GlStateManager.enableLighting();
    this.modelRod.render(entity);
    if (this.renderOutlines) {
      GlStateManager.disableOutlineMode();
      GlStateManager.disableColorMaterial();
    } 
    GlStateManager.matrixMode(5890);
    GlStateManager.loadIdentity();
    GlStateManager.matrixMode(5888);
    GlStateManager.disableBlend();
    GlStateManager.popMatrix();
  }
  
  protected ResourceLocation getEntityTexture(EntityEndersoulFragment entity) {
    return TEXTURE;
  }
}
