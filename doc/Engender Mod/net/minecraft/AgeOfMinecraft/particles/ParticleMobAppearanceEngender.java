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
    this.field_70552_h = 1.0F;
    this.field_70553_i = 1.0F;
    this.field_70551_j = 1.0F;
    this.field_187129_i = 0.0D;
    this.field_187130_j = 0.0D;
    this.field_187131_k = 0.0D;
    this.field_70545_g = 0.0F;
    this.field_70547_e = 30;
  }
  
  public int func_70537_b() {
    return 3;
  }
  
  public void func_189213_a() {
    super.func_189213_a();
    if (this.entity == null) {
      EntityGuardian entityelderguardian = new EntityGuardian(this.field_187122_b);
      entityelderguardian.setElder();
      entityelderguardian.field_70173_aa = 21;
      this.entity = entityelderguardian;
      if (this.source != null) {
        this.entity.setChild(this.source.func_70631_g_());
        this.entity.setFittness(this.source.getFittness());
      } 
    } 
  }
  
  public void func_180434_a(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
    if (this.entity != null) {
      this.entity.field_70761_aq = this.entity.field_70177_z = this.entity.field_70759_as = this.entity.field_70125_A = 0.0F;
      RenderManager rendermanager = Minecraft.func_71410_x().func_175598_ae();
      rendermanager.func_178628_a(Particle.field_70556_an, Particle.field_70554_ao, Particle.field_70555_ap);
      float f = EngenderConfig.mobs.useMobTalkerModels ? 0.825F : 0.425F;
      float f1 = (this.field_70546_d + partialTicks) / this.field_70547_e;
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179147_l();
      GlStateManager.func_179126_j();
      GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
      GlStateManager.func_179094_E();
      float f3 = 0.05F + 0.5F * MathHelper.func_76126_a(f1 * 3.1415927F);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, f3);
      GlStateManager.func_179109_b(0.0F, 1.5F, 0.0F);
      GlStateManager.func_179114_b(180.0F - entityIn.field_70177_z, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(60.0F - 150.0F * f1 - entityIn.field_70125_A, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179109_b(0.0F, -0.4F, -1.5F);
      GlStateManager.func_179152_a(f, f, f);
      this.entity.field_70177_z = 0.0F;
      this.entity.field_70759_as = 0.0F;
      this.entity.field_70126_B = 0.0F;
      this.entity.field_70758_at = 0.0F;
      rendermanager.func_188391_a((Entity)this.entity, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks, false);
      GlStateManager.func_179121_F();
      GlStateManager.func_179126_j();
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public static class Factory implements IParticleFactory {
    public Particle func_178902_a(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
      return new ParticleMobAppearanceEngender(worldIn, xCoordIn, yCoordIn, zCoordIn);
    }
  }
}
