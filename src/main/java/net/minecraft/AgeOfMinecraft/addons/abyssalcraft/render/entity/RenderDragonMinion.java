package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity;

import net.minecraft.AgeOfMinecraft.renders.RenderLayerCompat;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonMinion;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.model.ModelDragonMinion;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.layer.LayerSpectralDragonEyes;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderDragonMinion extends RenderLiving<EntityDragonMinion> {
  private float scale = 0.5F;
  
  private static final ResourceLocation DRAGON_EXPLODING_TEXTURES = new ResourceLocation("abyssalcraft:textures/model/boss/dragonboss_exploding.png");
  
  private static final ResourceLocation DRAGON_TEXTURES = new ResourceLocation("abyssalcraft:textures/model/elite/dragonminion.png");
  
  protected ModelDragonMinion modelDragon;
  
  public RenderDragonMinion(RenderManager manager) {
    super(manager, new ModelDragonMinion(0.0F), 0.0F);
    this.modelDragon = (ModelDragonMinion)RenderLayerCompat.getMainModel(this);
    RenderLayerCompat.addLayer(this, (LayerRenderer)new LayerSpectralDragonEyes(this));
    
  }
  
  protected void preRenderScale(EntityDragonMinion entitylivingbaseIn, float partialTickTime) {
    GlStateManager.scale(this.scale, this.scale, this.scale);
    float fit = entitylivingbaseIn.getFittness();
    GlStateManager.scale(fit, fit, fit);
    if (entitylivingbaseIn.isHero())
      GlStateManager.scale(1.05F, 1.05F, 1.05F); 
    if (entitylivingbaseIn.isChild())
      GlStateManager.scale(0.5F, 0.5F, 0.5F); 
    if (entitylivingbaseIn.ticksExisted <= 21 && entitylivingbaseIn.ticksExisted > 0) {
      float f5 = (entitylivingbaseIn.ticksExisted + partialTickTime - 1.0F) / 20.0F * 1.6F;
      f5 = MathHelper.sqrt(f5);
      if (f5 > 1.0F)
        f5 = 1.0F; 
      GlStateManager.scale(f5, f5, f5);
      GlStateManager.rotate(f5 * 90.0F - 90.0F, f5, f5, f5);
    } 
  }
  
  protected void applyRotations(EntityDragonMinion par1EntityDragonMinion, float par2, float par3, float par4) {
    float f3 = (float)par1EntityDragonMinion.getMovementOffsets(7, par4)[0];
    float f4 = (float)(par1EntityDragonMinion.getMovementOffsets(5, par4)[1] - par1EntityDragonMinion.getMovementOffsets(10, par4)[1]);
    GlStateManager.rotate(-f3, 0.0F, 1.0F, 0.0F);
    GlStateManager.rotate(f4 * 10.0F, 1.0F, 0.0F, 0.0F);
    GlStateManager.translate(0.0F, 0.0F, 1.0F);
    if (par1EntityDragonMinion.deathTime > 0) {
      float f5 = (par1EntityDragonMinion.deathTime + par4 - 1.0F) / 20.0F * 1.6F;
      f5 = MathHelper.sqrt(f5);
      if (f5 > 1.0F)
        f5 = 1.0F; 
      GlStateManager.rotate(f5 * getDeathMaxRotation(par1EntityDragonMinion), 0.0F, 0.0F, 1.0F);
    } 
  }
  
  public void doRender(EntityDragonMinion dragon, double par2, double par4, double par6, float par8, float par9) {
    super.doRender(dragon, par2, par4, par6, par8, par9);
    if (dragon.healingcircle != null) {
      float f = 0.0F + par9;
      float f1 = MathHelper.sin(f * 0.2F) / 2.0F + 0.5F;
      f1 = (f1 * f1 + f1) * 0.2F;
      float f2 = (float)(dragon.healingcircle.posX - dragon.posX - (dragon.prevPosX - dragon.posX) * (1.0F - par9));
      float f3 = (float)(f1 + dragon.healingcircle.posY + 1.0D - dragon.posY - (dragon.prevPosY - dragon.posY - 1.0D) * (1.0F - par9));
      float f4 = (float)(dragon.healingcircle.posZ - dragon.posZ - (dragon.prevPosZ - dragon.posZ) * (1.0F - par9));
      float f5 = MathHelper.sqrt(f2 * f2 + f4 * f4);
      float f6 = MathHelper.sqrt(f2 * f2 + f3 * f3 + f4 * f4);
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)par2, (float)par4 + 1.0F, (float)par6);
      GlStateManager.rotate((float)-Math.atan2(f4, f2) * 180.0F / 3.1415927F - 90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate((float)-Math.atan2(f5, f3) * 180.0F / 3.1415927F - 90.0F, 1.0F, 0.0F, 0.0F);
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder worldrenderer = tessellator.getBuffer();
      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableCull();
      bindTexture(DRAGON_EXPLODING_TEXTURES);
      GlStateManager.shadeModel(7425);
      float f7 = 0.0F + (dragon.ticksExisted + par9) * 0.1F;
      float f8 = MathHelper.sqrt(f2 * f2 + f3 * f3 + f4 * f4) / 32.0F - (dragon.ticksExisted + par9) * 0.01F;
      worldrenderer.begin(5, DefaultVertexFormats.POSITION_TEX_COLOR);
      for (int j = 0; j <= 4; j++) {
        float f9 = MathHelper.sin((j % 8) * 3.1415927F * 2.0F / 8.0F) * 0.75F;
        float f10 = MathHelper.cos((j % 8) * 3.1415927F * 2.0F / 8.0F) * 0.75F;
        float f11 = (j % 8) * 1.0F / 8.0F;
        worldrenderer.pos((f9 * 0.2F), (f10 * 0.2F), 0.0D).tex(f11, f8).color(0, 0, 0, 255).endVertex();
        worldrenderer.pos(f9, f10, f6).tex(f11, f7).color(255, 255, 255, 255).endVertex();
      } 
      tessellator.draw();
      GlStateManager.enableCull();
      GlStateManager.shadeModel(7424);
      RenderHelper.enableStandardItemLighting();
      GlStateManager.popMatrix();
    } 
  }
  
  protected void renderModel(EntityDragonMinion par1EntityDragonMinion, float par2, float par3, float par4, float par5, float par6, float par7) {
    bindEntityTexture(par1EntityDragonMinion);
    GlStateManager.enableBlend();
    GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
    RenderLayerCompat.getMainModel(this).render(par1EntityDragonMinion, par2, par3, par4, par5, par6, par7);
    GlStateManager.disableBlend();
    if (par1EntityDragonMinion.hurtTime > 0) {
      GL11.glDepthFunc(514);
      GlStateManager.disableTexture2D();
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      GlStateManager.color(1.0F, 0.0F, 0.0F, 0.5F);
      RenderLayerCompat.getMainModel(this).render(par1EntityDragonMinion, par2, par3, par4, par5, par6, par7);
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
      GL11.glDepthFunc(515);
    } 
  }
  
  protected ResourceLocation func_110841_a(EntityDragonMinion par1EntityDragonMinion) {
    return DRAGON_TEXTURES;
  }
  
  protected void preRenderCallback(EntityDragonMinion par1EntityLivingBase, float par2) {
    preRenderScale(par1EntityLivingBase, par2);
  }
  
  protected ResourceLocation getEntityTexture(EntityDragonMinion par1Entity) {
    return func_110841_a(par1Entity);
  }
}
