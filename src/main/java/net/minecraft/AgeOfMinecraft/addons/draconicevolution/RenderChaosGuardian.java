package net.minecraft.AgeOfMinecraft.addons.draconicevolution;

import net.minecraft.AgeOfMinecraft.renders.RenderLayerCompat;

import com.brandon3055.draconicevolution.client.model.ModelGuardianCrystal;
import java.util.Random;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
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
public class RenderChaosGuardian extends RenderLiving<EntityChaosGuardian> {
  private static final ResourceLocation ENDER_CRYSTAL_TEXTURES = new ResourceLocation("draconicevolution", "textures/entity/guardian_crystal.png");
  
  private static final ResourceLocation ENDERCRYSTAL_BEAM_TEXTURES = new ResourceLocation("textures/entity/endercrystal/endercrystal_beam.png");
  
  private static final ResourceLocation DRAGON_EXPLODING_TEXTURES = new ResourceLocation("textures/entity/enderdragon/dragon_exploding.png");
  
  private static final ResourceLocation DRAGON_TEXTURES = new ResourceLocation("draconicevolution", "textures/entity/chaos_guardian.png");
  
  protected ModelChaosGuardian modelDragon;
  
  private final ModelGuardianCrystal modelEnderCrystalNoBase = new ModelGuardianCrystal(false);
  
  public RenderChaosGuardian(RenderManager manager) {
    super(manager, new ModelChaosGuardian(0.0F), 8.0F);
    this.modelDragon = (ModelChaosGuardian)RenderLayerCompat.getMainModel(this);
    RenderLayerCompat.addLayer(this, new LayerChaosGuardianEyes(this));
    RenderLayerCompat.addLayer(this, new LayerChaosGuardianDeath());
    
  }
  
  protected void applyRotations(EntityChaosGuardian entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
    float f3 = (float)entityLiving.getMovementOffsets(7, partialTicks)[0];
    float f4 = (float)(entityLiving.getMovementOffsets(5, partialTicks)[1] - entityLiving.getMovementOffsets(10, partialTicks)[1]);
    GL11.glRotatef(-f3, 0.0F, 1.0F, 0.0F);
    GL11.glRotatef(f4 * 10.0F, 1.0F, 0.0F, 0.0F);
    GL11.glTranslatef(0.0F, 0.0F, 1.0F);
    if (entityLiving.deathTime > 0) {
      float f5 = (entityLiving.deathTime + partialTicks - 1.0F) / 20.0F * 1.6F;
      f5 = MathHelper.sqrt(f5);
      if (f5 > 1.0F)
        f5 = 1.0F; 
      GL11.glRotatef(f5 * getDeathMaxRotation(entityLiving), 0.0F, 0.0F, 1.0F);
    } 
  }
  
  protected void renderModel(EntityChaosGuardian chaosGuardian, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
    if (chaosGuardian.deathTicks > 0) {
      float f6 = chaosGuardian.deathTicks / 200.0F;
      GL11.glDepthFunc(515);
      GL11.glEnable(3008);
      GL11.glAlphaFunc(516, f6);
      bindTexture(DRAGON_EXPLODING_TEXTURES);
      RenderLayerCompat.getMainModel(this).render(chaosGuardian, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
      GL11.glAlphaFunc(516, 0.1F);
      GL11.glDepthFunc(514);
    } 
    bindEntityTexture(chaosGuardian);
    RenderLayerCompat.getMainModel(this).render(chaosGuardian, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
    if (chaosGuardian.hurtTime > 0) {
      GL11.glDepthFunc(514);
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glColor4f(1.0F, 0.0F, 0.0F, 0.5F);
      RenderLayerCompat.getMainModel(this).render(chaosGuardian, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDepthFunc(515);
    } 
  }
  
  public void doRender(EntityChaosGuardian chaosGuardian, double x, double y, double z, float entityYaw, float partialTicks) {
    super.doRender(chaosGuardian, x, y, z, entityYaw, partialTicks);
    if (chaosGuardian.isCarryingCrystal()) {
      GlStateManager.pushMatrix();
      float f = chaosGuardian.innerRotation + partialTicks;
      float fl1 = (chaosGuardian.getJukeboxToDanceTo() != null) ? (MathHelper.sin(f * 0.1F) * 10.0F) : ((float)chaosGuardian.motionX * 10.0F);
      float fl2 = (chaosGuardian.getJukeboxToDanceTo() != null) ? (MathHelper.sin(f * 0.3F) * 5.0F) : ((float)chaosGuardian.motionY * 5.0F);
      float fl3 = (chaosGuardian.getJukeboxToDanceTo() != null) ? (MathHelper.cos(f * 0.1F) * 10.0F) : ((float)chaosGuardian.motionZ * 10.0F);
      GlStateManager.translate(((float)x - fl1), (float)y + 10.0D - fl2, ((float)z - fl3));
      bindTexture(ENDER_CRYSTAL_TEXTURES);
      float f1 = MathHelper.sin(f * 0.2F) / 2.0F + 0.5F;
      f1 = f1 * f1 + f1;
      if (RenderLayerCompat.isRenderOutlines(this)) {
        GlStateManager.enableColorMaterial();
        GlStateManager.enableOutlineMode(getTeamColor(chaosGuardian));
      } 
      this.modelEnderCrystalNoBase.base.isHidden = true;
      this.modelEnderCrystalNoBase.render(chaosGuardian, 0.0F, f * 3.0F, f1 * 0.2F, 0.0F, 0.0F, 0.0625F);
      if (RenderLayerCompat.isRenderOutlines(this)) {
        GlStateManager.disableOutlineMode();
        GlStateManager.disableColorMaterial();
      } 
      if (chaosGuardian.getJukeboxToDanceTo() != null) {
        GlStateManager.pushMatrix();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        RenderHelper.disableStandardItemLighting();
        float u = f / 250.0F;
        float u1 = 0.0F;
        if (f > 0.9F)
          u1 = (u - 0.9F) / 0.2F; 
        Random random = new Random(432L);
        GlStateManager.disableTexture2D();
        GlStateManager.shadeModel(7425);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        GlStateManager.disableAlpha();
        GlStateManager.enableCull();
        GlStateManager.depthMask(false);
        GlStateManager.translate(((float)x - fl1), (float)y + 10.0D - fl2 + f1, ((float)z - fl3));
        for (int i = 0; i < (u + u * u) / 2.0F * 30.0F; i++) {
          GlStateManager.rotate(random.nextFloat() * 720.0F, 1.0F, 0.0F, 0.0F);
          GlStateManager.rotate(random.nextFloat() * 720.0F, 0.0F, 1.0F, 0.0F);
          GlStateManager.rotate(random.nextFloat() * 720.0F, 0.0F, 0.0F, 1.0F);
          GlStateManager.rotate(random.nextFloat() * 720.0F, 1.0F, 0.0F, 0.0F);
          GlStateManager.rotate(random.nextFloat() * 720.0F, 0.0F, 1.0F, 0.0F);
          GlStateManager.rotate(random.nextFloat() * 720.0F + u * 180.0F, 0.0F, 0.0F, 1.0F);
          float f5 = random.nextFloat() * 20.0F + 5.0F + u1 * 10.0F;
          float f6 = random.nextFloat() * 2.0F + 1.0F + u1 * 2.0F;
          vertexbuffer.begin(6, DefaultVertexFormats.POSITION_COLOR);
          vertexbuffer.pos(0.0D, 0.0D, 0.0D).color(random.nextInt(255), random.nextInt(255), random.nextInt(255), (int)(255.0F * (1.0F - u1))).endVertex();
          vertexbuffer.pos(-0.866D * f6, f5, (-0.5F * f6)).color(random.nextInt(255), random.nextInt(255), random.nextInt(255), 0).endVertex();
          vertexbuffer.pos(0.866D * f6, f5, (-0.5F * f6)).color(random.nextInt(255), random.nextInt(255), random.nextInt(255), 0).endVertex();
          vertexbuffer.pos(0.0D, f5, (1.0F * f6)).color(random.nextInt(255), random.nextInt(255), random.nextInt(255), 0).endVertex();
          vertexbuffer.pos(-0.866D * f6, f5, (-0.5F * f6)).color(random.nextInt(255), random.nextInt(255), random.nextInt(255), 0).endVertex();
          tessellator.draw();
        } 
        GlStateManager.depthMask(true);
        GlStateManager.disableCull();
        GlStateManager.disableBlend();
        GlStateManager.shadeModel(7424);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableTexture2D();
        GlStateManager.enableAlpha();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
      } 
      GlStateManager.popMatrix();
      bindTexture(ENDERCRYSTAL_BEAM_TEXTURES);
      float f2 = (float)chaosGuardian.posX + fl1;
      float f3 = (float)chaosGuardian.posY - 10.0F + fl2;
      float f4 = (float)chaosGuardian.posZ + fl3;
      double d0 = f2 - chaosGuardian.posX + fl1;
      double d1 = f3 - chaosGuardian.posY - 10.0D + fl2;
      double d2 = f4 - chaosGuardian.posZ + fl3;
      if (chaosGuardian.getHealth() < chaosGuardian.getMaxHealth())
        renderCrystalBeams(x + d0, y - 0.3D + (f1 * 0.4F) + d1, z + d2, partialTicks, f2, f3, f4, chaosGuardian.innerRotation, chaosGuardian.posX, chaosGuardian.posY, chaosGuardian.posZ); 
    } 
  }
  
  public static void renderCrystalBeams(double p_188325_0_, double p_188325_2_, double p_188325_4_, float p_188325_6_, double p_188325_7_, double p_188325_9_, double p_188325_11_, int p_188325_13_, double p_188325_14_, double p_188325_16_, double p_188325_18_) {
    float f = (float)(p_188325_14_ - p_188325_7_);
    float f1 = (float)(p_188325_16_ - 1.0D - p_188325_9_);
    float f2 = (float)(p_188325_18_ - p_188325_11_);
    float f3 = MathHelper.sqrt(f * f + f2 * f2);
    float f4 = MathHelper.sqrt(f * f + f1 * f1 + f2 * f2);
    GlStateManager.pushMatrix();
    GlStateManager.translate((float)p_188325_0_, (float)p_188325_2_ + 2.0F, (float)p_188325_4_);
    GlStateManager.rotate((float)-Math.atan2(f2, f) * 57.295776F - 90.0F, 0.0F, 1.0F, 0.0F);
    GlStateManager.rotate((float)-Math.atan2(f3, f1) * 57.295776F - 90.0F, 1.0F, 0.0F, 0.0F);
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder vertexbuffer = tessellator.getBuffer();
    RenderHelper.disableStandardItemLighting();
    GlStateManager.disableCull();
    GlStateManager.shadeModel(7425);
    float f5 = 0.0F - (p_188325_13_ + p_188325_6_) * 0.01F;
    float f6 = MathHelper.sqrt(f * f + f1 * f1 + f2 * f2) / 32.0F - (p_188325_13_ + p_188325_6_) * 0.01F;
    vertexbuffer.begin(5, DefaultVertexFormats.POSITION_TEX_COLOR);
    for (int j = 0; j <= 8; j++) {
      float f7 = MathHelper.sin((j % 8) * 6.2831855F / 8.0F) * 0.5F;
      float f8 = MathHelper.cos((j % 8) * 6.2831855F / 8.0F) * 0.5F;
      float f9 = (j % 8) / 8.0F;
      vertexbuffer.pos((f7 * 0.25F), (f8 * 0.25F), 0.0D).tex(f9, f5).color(0, 0, 0, 255).endVertex();
      vertexbuffer.pos(f7, f8, f4).tex(f9, f6).color(255, 255, 255, 255).endVertex();
    } 
    tessellator.draw();
    GlStateManager.enableCull();
    GlStateManager.shadeModel(7424);
    RenderHelper.enableStandardItemLighting();
    GlStateManager.popMatrix();
  }
  
  protected ResourceLocation getEntityTexture(EntityChaosGuardian entity) {
    return DRAGON_TEXTURES;
  }
  
  public static class LayerChaosGuardianEyes implements LayerRenderer<EntityChaosGuardian> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/enderdragon/dragon_eyes.png");
    
    private final RenderChaosGuardian dragonRenderer;
    
    public LayerChaosGuardianEyes(RenderChaosGuardian dragonRendererIn) {
      this.dragonRenderer = dragonRendererIn;
    }
    
    public void doRenderLayer(EntityChaosGuardian entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      this.dragonRenderer.bindTexture(TEXTURE);
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
      GlStateManager.disableLighting();
      GlStateManager.depthFunc(514);
      int i = 61680;
      int j = i % 65536;
      int k = i / 65536;
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j, k);
      GlStateManager.enableLighting();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      RenderLayerCompat.getMainModel(this.dragonRenderer).render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      this.dragonRenderer.setLightmap(entitylivingbaseIn);
      GlStateManager.disableBlend();
      GlStateManager.enableAlpha();
      GlStateManager.depthFunc(515);
    }
    
    public boolean shouldCombineTextures() {
      return false;
    }
  }
  
  @SideOnly(Side.CLIENT)
  public class LayerChaosGuardianDeath implements LayerRenderer<EntityChaosGuardian> {
    public void doRenderLayer(EntityChaosGuardian entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      if (entitylivingbaseIn.deathTicks > 0) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        RenderHelper.disableStandardItemLighting();
        float f = (entitylivingbaseIn.deathTicks + partialTicks) / 200.0F;
        float f1 = 0.0F;
        if (f > 0.8F)
          f1 = (f - 0.8F) / 0.2F; 
        Random random = new Random(432L);
        GlStateManager.disableTexture2D();
        GlStateManager.shadeModel(7425);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        GlStateManager.disableAlpha();
        GlStateManager.enableCull();
        GlStateManager.depthMask(false);
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, -1.0F, -2.0F);
        for (int i = 0; i < (f + f * f) / 2.0F * 60.0F; i++) {
          GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
          GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
          GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
          GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
          GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
          GlStateManager.rotate(random.nextFloat() * 360.0F + f * 90.0F, 0.0F, 0.0F, 1.0F);
          float f2 = random.nextFloat() * 20.0F + 5.0F + f1 * 10.0F;
          float f3 = random.nextFloat() * 2.0F + 1.0F + f1 * 2.0F;
          vertexbuffer.begin(6, DefaultVertexFormats.POSITION_COLOR);
          vertexbuffer.pos(0.0D, 0.0D, 0.0D).color(255, 255, 255, (int)(255.0F * (1.0F - f1))).endVertex();
          vertexbuffer.pos(-0.866D * f3, f2, (-0.5F * f3)).color(255, 0, 255, 0).endVertex();
          vertexbuffer.pos(0.866D * f3, f2, (-0.5F * f3)).color(255, 0, 255, 0).endVertex();
          vertexbuffer.pos(0.0D, f2, (1.0F * f3)).color(255, 0, 255, 0).endVertex();
          vertexbuffer.pos(-0.866D * f3, f2, (-0.5F * f3)).color(255, 0, 255, 0).endVertex();
          tessellator.draw();
        } 
        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.disableCull();
        GlStateManager.disableBlend();
        GlStateManager.shadeModel(7424);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableTexture2D();
        GlStateManager.enableAlpha();
        RenderHelper.enableStandardItemLighting();
      } 
    }
    
    public boolean shouldCombineTextures() {
      return false;
    }
  }
}
