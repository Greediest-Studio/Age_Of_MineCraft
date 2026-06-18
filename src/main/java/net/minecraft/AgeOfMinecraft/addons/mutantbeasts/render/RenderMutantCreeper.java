package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.render;

import chumbanotz.mutantbeasts.MutantBeasts;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantCreeper;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.model.ModelMutantCreeper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMutantCreeper extends RenderLiving<EntityMutantCreeper> {
  private static final ResourceLocation TEXTURE = MutantBeasts.getEntityTexture("mutant_creeper");
  
  private static final ResourceLocation Anti_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/mutant/anti/anti_mutant_creeper.png");
  
  private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
  
  private static final ResourceLocation Anti_LIGHTNING_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/anti/creeper_armor.png");
  
  public RenderMutantCreeper(RenderManager rendermanagerIn) {
    super(rendermanagerIn, (ModelBase)new ModelMutantCreeper(), 1.5F);
    addLayer(new LayerCreeperCharge(this, (ModelBase)new ModelMutantCreeper(2.0F)));
  }
  
  protected void preRenderCallback(EntityMutantCreeper entitylivingbaseIn, float partialTickTime) {
    float scale = 1.2F;
    if (entitylivingbaseIn.deathTime > 0) {
      float f1 = entitylivingbaseIn.deathTime / 100.0F;
      scale -= f1 * 0.4F;
    } 
    GlStateManager.scale(scale, scale, scale);
    float fit = entitylivingbaseIn.getFittness();
    GlStateManager.scale(fit, fit, fit);
    if (entitylivingbaseIn.isHero())
      GlStateManager.scale(1.05F, 1.05F, 1.05F); 
    if (entitylivingbaseIn.isChild())
      GlStateManager.scale(0.5F, 0.5F, 0.5F); 
    if (!entitylivingbaseIn.onGround)
      GlStateManager.rotate(entitylivingbaseIn.prevRotationPitchFalling + (entitylivingbaseIn.rotationPitchFalling - entitylivingbaseIn.prevRotationPitchFalling) * 2.0F - 1.0F, 1.0F, 0.0F, 0.0F); 
  }
  
  protected int getColorMultiplier(EntityMutantCreeper entitylivingbaseIn, float lightBrightness, float partialTickTime) {
    int a = (int)entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);
    int r = 255;
    int g = 255;
    int b = 255;
    if (entitylivingbaseIn.getPowered()) {
      r = 160;
      g = 180;
    } 
    return a << 24 | r << 16 | g << 8 | b;
  }
  
  protected float getDeathMaxRotation(EntityMutantCreeper entityLivingBaseIn) {
    return 0.0F;
  }
  
  protected ResourceLocation getEntityTexture(EntityMutantCreeper entity) {
    return entity.isAntiMob() ? Anti_TEXTURE : TEXTURE;
  }
  
  @SideOnly(Side.CLIENT)
  public class LayerCreeperCharge implements LayerRenderer<EntityMutantCreeper> {
    private final RenderMutantCreeper renderer;
    
    private final ModelBase model;
    
    public LayerCreeperCharge(RenderMutantCreeper creeperRenderer, ModelBase model) {
      this.renderer = creeperRenderer;
      this.model = model;
    }
    
    public void doRenderLayer(EntityMutantCreeper entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      if (entitylivingbaseIn.getPowered()) {
        boolean flag = entitylivingbaseIn.isInvisible();
        GlStateManager.depthMask(!flag);
        this.renderer.bindTexture(entitylivingbaseIn.isAntiMob() ? RenderMutantCreeper.Anti_LIGHTNING_TEXTURE : RenderMutantCreeper.LIGHTNING_TEXTURE);
        GlStateManager.matrixMode(5890);
        GlStateManager.loadIdentity();
        float f = entitylivingbaseIn.ticksExisted + partialTicks;
        if (entitylivingbaseIn.isAntiMob())
          f = -f; 
        GlStateManager.translate(f * 0.01F, f * 0.01F, 0.0F);
        GlStateManager.matrixMode(5888);
        int c0 = 15728880;
        int i = c0 % 65536;
        int j = c0 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, i / 1.0F, j / 1.0F);
        GlStateManager.enableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableLighting();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
        this.model.setModelAttributes(this.renderer.getMainModel());
        (Minecraft.getMinecraft()).entityRenderer.setupFogColor(true);
        this.model.render((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        (Minecraft.getMinecraft()).entityRenderer.setupFogColor(false);
        GlStateManager.matrixMode(5890);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode(5888);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(flag);
      } 
    }
    
    public boolean shouldCombineTextures() {
      return false;
    }
  }
}
