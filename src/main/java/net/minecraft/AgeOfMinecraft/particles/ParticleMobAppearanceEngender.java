package net.minecraft.AgeOfMinecraft.particles;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGuardian;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleMobAppearanceEngender extends Particle {
  public EntityGuardian entity;
  
  public EntityGuardian source;
  
  public ParticleMobAppearanceEngender(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn) {
    super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
    this.particleRed = 1.0F;
    this.particleGreen = 1.0F;
    this.particleBlue = 1.0F;
    this.motionX = 0.0D;
    this.motionY = 0.0D;
    this.motionZ = 0.0D;
    this.particleGravity = 0.0F;
    this.particleMaxAge = 30;
  }
  
  public int getFXLayer() {
    return 3;
  }
  
  public void onUpdate() {
    super.onUpdate();
    if (this.entity == null) {
      EntityGuardian entityelderguardian = new EntityGuardian(this.world);
      entityelderguardian.setElder();
      entityelderguardian.ticksExisted = 21;
      this.entity = entityelderguardian;
      if (this.source != null) {
        this.entity.setChild(this.source.isChild());
        this.entity.setFittness(this.source.getFittness());
      } 
    } 
  }
  
  public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
    if (this.entity != null) {
      this.entity.renderYawOffset = this.entity.rotationYaw = this.entity.rotationYawHead = this.entity.rotationPitch = 0.0F;
      RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
      rendermanager.setRenderPosition(Particle.interpPosX, Particle.interpPosY, Particle.interpPosZ);
      float f = EngenderConfig.mobs.useMobTalkerModels ? 0.825F : 0.425F;
      float f1 = (this.particleAge + partialTicks) / this.particleMaxAge;
      GlStateManager.depthMask(true);
      GlStateManager.enableBlend();
      GlStateManager.enableDepth();
      GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
      GlStateManager.pushMatrix();
      float f3 = 0.05F + 0.5F * MathHelper.sin(f1 * 3.1415927F);
      GlStateManager.color(1.0F, 1.0F, 1.0F, f3);
      GlStateManager.translate(0.0F, 1.5F, 0.0F);
      GlStateManager.rotate(180.0F - entityIn.rotationYaw, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(60.0F - 150.0F * f1 - entityIn.rotationPitch, 1.0F, 0.0F, 0.0F);
      GlStateManager.translate(0.0F, -0.4F, -1.5F);
      GlStateManager.scale(f, f, f);
      this.entity.rotationYaw = 0.0F;
      this.entity.rotationYawHead = 0.0F;
      this.entity.prevRotationYaw = 0.0F;
      this.entity.prevRotationYawHead = 0.0F;
      rendermanager.renderEntity(this.entity, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks, false);
      GlStateManager.popMatrix();
      GlStateManager.enableDepth();
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public static class Factory implements IParticleFactory {
    public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
      return new ParticleMobAppearanceEngender(worldIn, xCoordIn, yCoordIn, zCoordIn);
    }
  }
}
