package net.minecraft.AgeOfMinecraft.addons.draconicevolution;

import com.brandon3055.draconicevolution.client.model.ModelGuardianCrystal;
import java.util.Random;
import net.minecraft.AgeOfMinecraft.renders.LayerLearningBook;
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
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
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
    this.modelDragon = (ModelChaosGuardian)this.field_77045_g;
    func_177094_a(new LayerChaosGuardianEyes(this));
    func_177094_a(new LayerChaosGuardianDeath());
    func_177094_a((LayerRenderer)new LayerLearningBook(this));
  }
  
  protected void applyRotations(EntityChaosGuardian entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
    float f3 = (float)entityLiving.getMovementOffsets(7, partialTicks)[0];
    float f4 = (float)(entityLiving.getMovementOffsets(5, partialTicks)[1] - entityLiving.getMovementOffsets(10, partialTicks)[1]);
    GL11.glRotatef(-f3, 0.0F, 1.0F, 0.0F);
    GL11.glRotatef(f4 * 10.0F, 1.0F, 0.0F, 0.0F);
    GL11.glTranslatef(0.0F, 0.0F, 1.0F);
    if (entityLiving.field_70725_aQ > 0) {
      float f5 = (entityLiving.field_70725_aQ + partialTicks - 1.0F) / 20.0F * 1.6F;
      f5 = MathHelper.func_76129_c(f5);
      if (f5 > 1.0F)
        f5 = 1.0F; 
      GL11.glRotatef(f5 * func_77037_a((EntityLivingBase)entityLiving), 0.0F, 0.0F, 1.0F);
    } 
  }
  
  protected void renderModel(EntityChaosGuardian chaosGuardian, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
    if (chaosGuardian.deathTicks > 0) {
      float f6 = chaosGuardian.deathTicks / 200.0F;
      GL11.glDepthFunc(515);
      GL11.glEnable(3008);
      GL11.glAlphaFunc(516, f6);
      func_110776_a(DRAGON_EXPLODING_TEXTURES);
      this.field_77045_g.func_78088_a((Entity)chaosGuardian, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
      GL11.glAlphaFunc(516, 0.1F);
      GL11.glDepthFunc(514);
    } 
    func_180548_c((Entity)chaosGuardian);
    this.field_77045_g.func_78088_a((Entity)chaosGuardian, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
    if (chaosGuardian.field_70737_aN > 0) {
      GL11.glDepthFunc(514);
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glColor4f(1.0F, 0.0F, 0.0F, 0.5F);
      this.field_77045_g.func_78088_a((Entity)chaosGuardian, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDepthFunc(515);
    } 
  }
  
  public void doRender(EntityChaosGuardian chaosGuardian, double x, double y, double z, float entityYaw, float partialTicks) {
    super.func_76986_a((EntityLiving)chaosGuardian, x, y, z, entityYaw, partialTicks);
    if (chaosGuardian.isCarryingCrystal()) {
      GlStateManager.func_179094_E();
      float f = chaosGuardian.innerRotation + partialTicks;
      float fl1 = (chaosGuardian.getJukeboxToDanceTo() != null) ? (MathHelper.func_76126_a(f * 0.1F) * 10.0F) : ((float)chaosGuardian.field_70159_w * 10.0F);
      float fl2 = (chaosGuardian.getJukeboxToDanceTo() != null) ? (MathHelper.func_76126_a(f * 0.3F) * 5.0F) : ((float)chaosGuardian.field_70181_x * 5.0F);
      float fl3 = (chaosGuardian.getJukeboxToDanceTo() != null) ? (MathHelper.func_76134_b(f * 0.1F) * 10.0F) : ((float)chaosGuardian.field_70179_y * 10.0F);
      GlStateManager.func_179137_b(((float)x - fl1), (float)y + 10.0D - fl2, ((float)z - fl3));
      func_110776_a(ENDER_CRYSTAL_TEXTURES);
      float f1 = MathHelper.func_76126_a(f * 0.2F) / 2.0F + 0.5F;
      f1 = f1 * f1 + f1;
      if (this.field_188301_f) {
        GlStateManager.func_179142_g();
        GlStateManager.func_187431_e(func_188298_c((Entity)chaosGuardian));
      } 
      this.modelEnderCrystalNoBase.base.field_78807_k = true;
      this.modelEnderCrystalNoBase.func_78088_a((Entity)chaosGuardian, 0.0F, f * 3.0F, f1 * 0.2F, 0.0F, 0.0F, 0.0625F);
      if (this.field_188301_f) {
        GlStateManager.func_187417_n();
        GlStateManager.func_179119_h();
      } 
      if (chaosGuardian.getJukeboxToDanceTo() != null) {
        GlStateManager.func_179094_E();
        Tessellator tessellator = Tessellator.func_178181_a();
        BufferBuilder vertexbuffer = tessellator.func_178180_c();
        RenderHelper.func_74518_a();
        float u = f / 250.0F;
        float u1 = 0.0F;
        if (f > 0.9F)
          u1 = (u - 0.9F) / 0.2F; 
        Random random = new Random(432L);
        GlStateManager.func_179090_x();
        GlStateManager.func_179103_j(7425);
        GlStateManager.func_179147_l();
        GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        GlStateManager.func_179118_c();
        GlStateManager.func_179089_o();
        GlStateManager.func_179132_a(false);
        GlStateManager.func_179137_b(((float)x - fl1), (float)y + 10.0D - fl2 + f1, ((float)z - fl3));
        for (int i = 0; i < (u + u * u) / 2.0F * 30.0F; i++) {
          GlStateManager.func_179114_b(random.nextFloat() * 720.0F, 1.0F, 0.0F, 0.0F);
          GlStateManager.func_179114_b(random.nextFloat() * 720.0F, 0.0F, 1.0F, 0.0F);
          GlStateManager.func_179114_b(random.nextFloat() * 720.0F, 0.0F, 0.0F, 1.0F);
          GlStateManager.func_179114_b(random.nextFloat() * 720.0F, 1.0F, 0.0F, 0.0F);
          GlStateManager.func_179114_b(random.nextFloat() * 720.0F, 0.0F, 1.0F, 0.0F);
          GlStateManager.func_179114_b(random.nextFloat() * 720.0F + u * 180.0F, 0.0F, 0.0F, 1.0F);
          float f5 = random.nextFloat() * 20.0F + 5.0F + u1 * 10.0F;
          float f6 = random.nextFloat() * 2.0F + 1.0F + u1 * 2.0F;
          vertexbuffer.func_181668_a(6, DefaultVertexFormats.field_181706_f);
          vertexbuffer.func_181662_b(0.0D, 0.0D, 0.0D).func_181669_b(random.nextInt(255), random.nextInt(255), random.nextInt(255), (int)(255.0F * (1.0F - u1))).func_181675_d();
          vertexbuffer.func_181662_b(-0.866D * f6, f5, (-0.5F * f6)).func_181669_b(random.nextInt(255), random.nextInt(255), random.nextInt(255), 0).func_181675_d();
          vertexbuffer.func_181662_b(0.866D * f6, f5, (-0.5F * f6)).func_181669_b(random.nextInt(255), random.nextInt(255), random.nextInt(255), 0).func_181675_d();
          vertexbuffer.func_181662_b(0.0D, f5, (1.0F * f6)).func_181669_b(random.nextInt(255), random.nextInt(255), random.nextInt(255), 0).func_181675_d();
          vertexbuffer.func_181662_b(-0.866D * f6, f5, (-0.5F * f6)).func_181669_b(random.nextInt(255), random.nextInt(255), random.nextInt(255), 0).func_181675_d();
          tessellator.func_78381_a();
        } 
        GlStateManager.func_179132_a(true);
        GlStateManager.func_179129_p();
        GlStateManager.func_179084_k();
        GlStateManager.func_179103_j(7424);
        GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.func_179098_w();
        GlStateManager.func_179141_d();
        RenderHelper.func_74519_b();
        GlStateManager.func_179121_F();
      } 
      GlStateManager.func_179121_F();
      func_110776_a(ENDERCRYSTAL_BEAM_TEXTURES);
      float f2 = (float)chaosGuardian.field_70165_t + fl1;
      float f3 = (float)chaosGuardian.field_70163_u - 10.0F + fl2;
      float f4 = (float)chaosGuardian.field_70161_v + fl3;
      double d0 = f2 - chaosGuardian.field_70165_t + fl1;
      double d1 = f3 - chaosGuardian.field_70163_u - 10.0D + fl2;
      double d2 = f4 - chaosGuardian.field_70161_v + fl3;
      if (chaosGuardian.func_110143_aJ() < chaosGuardian.func_110138_aP())
        func_188325_a(x + d0, y - 0.3D + (f1 * 0.4F) + d1, z + d2, partialTicks, f2, f3, f4, chaosGuardian.innerRotation, chaosGuardian.field_70165_t, chaosGuardian.field_70163_u, chaosGuardian.field_70161_v); 
    } 
  }
  
  public static void func_188325_a(double p_188325_0_, double p_188325_2_, double p_188325_4_, float p_188325_6_, double p_188325_7_, double p_188325_9_, double p_188325_11_, int p_188325_13_, double p_188325_14_, double p_188325_16_, double p_188325_18_) {
    float f = (float)(p_188325_14_ - p_188325_7_);
    float f1 = (float)(p_188325_16_ - 1.0D - p_188325_9_);
    float f2 = (float)(p_188325_18_ - p_188325_11_);
    float f3 = MathHelper.func_76129_c(f * f + f2 * f2);
    float f4 = MathHelper.func_76129_c(f * f + f1 * f1 + f2 * f2);
    GlStateManager.func_179094_E();
    GlStateManager.func_179109_b((float)p_188325_0_, (float)p_188325_2_ + 2.0F, (float)p_188325_4_);
    GlStateManager.func_179114_b((float)-Math.atan2(f2, f) * 57.295776F - 90.0F, 0.0F, 1.0F, 0.0F);
    GlStateManager.func_179114_b((float)-Math.atan2(f3, f1) * 57.295776F - 90.0F, 1.0F, 0.0F, 0.0F);
    Tessellator tessellator = Tessellator.func_178181_a();
    BufferBuilder vertexbuffer = tessellator.func_178180_c();
    RenderHelper.func_74518_a();
    GlStateManager.func_179129_p();
    GlStateManager.func_179103_j(7425);
    float f5 = 0.0F - (p_188325_13_ + p_188325_6_) * 0.01F;
    float f6 = MathHelper.func_76129_c(f * f + f1 * f1 + f2 * f2) / 32.0F - (p_188325_13_ + p_188325_6_) * 0.01F;
    vertexbuffer.func_181668_a(5, DefaultVertexFormats.field_181709_i);
    for (int j = 0; j <= 8; j++) {
      float f7 = MathHelper.func_76126_a((j % 8) * 6.2831855F / 8.0F) * 0.5F;
      float f8 = MathHelper.func_76134_b((j % 8) * 6.2831855F / 8.0F) * 0.5F;
      float f9 = (j % 8) / 8.0F;
      vertexbuffer.func_181662_b((f7 * 0.25F), (f8 * 0.25F), 0.0D).func_187315_a(f9, f5).func_181669_b(0, 0, 0, 255).func_181675_d();
      vertexbuffer.func_181662_b(f7, f8, f4).func_187315_a(f9, f6).func_181669_b(255, 255, 255, 255).func_181675_d();
    } 
    tessellator.func_78381_a();
    GlStateManager.func_179089_o();
    GlStateManager.func_179103_j(7424);
    RenderHelper.func_74519_b();
    GlStateManager.func_179121_F();
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
      this.dragonRenderer.func_110776_a(TEXTURE);
      GlStateManager.func_179147_l();
      GlStateManager.func_179118_c();
      GlStateManager.func_187401_a(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
      GlStateManager.func_179140_f();
      GlStateManager.func_179143_c(514);
      int i = 61680;
      int j = i % 65536;
      int k = i / 65536;
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, j, k);
      GlStateManager.func_179145_e();
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      this.dragonRenderer.func_177087_b().func_78088_a((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      this.dragonRenderer.func_177105_a((EntityLiving)entitylivingbaseIn);
      GlStateManager.func_179084_k();
      GlStateManager.func_179141_d();
      GlStateManager.func_179143_c(515);
    }
    
    public boolean func_177142_b() {
      return false;
    }
  }
  
  @SideOnly(Side.CLIENT)
  public class LayerChaosGuardianDeath implements LayerRenderer<EntityChaosGuardian> {
    public void doRenderLayer(EntityChaosGuardian entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      if (entitylivingbaseIn.deathTicks > 0) {
        Tessellator tessellator = Tessellator.func_178181_a();
        BufferBuilder vertexbuffer = tessellator.func_178180_c();
        RenderHelper.func_74518_a();
        float f = (entitylivingbaseIn.deathTicks + partialTicks) / 200.0F;
        float f1 = 0.0F;
        if (f > 0.8F)
          f1 = (f - 0.8F) / 0.2F; 
        Random random = new Random(432L);
        GlStateManager.func_179090_x();
        GlStateManager.func_179103_j(7425);
        GlStateManager.func_179147_l();
        GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        GlStateManager.func_179118_c();
        GlStateManager.func_179089_o();
        GlStateManager.func_179132_a(false);
        GlStateManager.func_179094_E();
        GlStateManager.func_179109_b(0.0F, -1.0F, -2.0F);
        for (int i = 0; i < (f + f * f) / 2.0F * 60.0F; i++) {
          GlStateManager.func_179114_b(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
          GlStateManager.func_179114_b(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
          GlStateManager.func_179114_b(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
          GlStateManager.func_179114_b(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
          GlStateManager.func_179114_b(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
          GlStateManager.func_179114_b(random.nextFloat() * 360.0F + f * 90.0F, 0.0F, 0.0F, 1.0F);
          float f2 = random.nextFloat() * 20.0F + 5.0F + f1 * 10.0F;
          float f3 = random.nextFloat() * 2.0F + 1.0F + f1 * 2.0F;
          vertexbuffer.func_181668_a(6, DefaultVertexFormats.field_181706_f);
          vertexbuffer.func_181662_b(0.0D, 0.0D, 0.0D).func_181669_b(255, 255, 255, (int)(255.0F * (1.0F - f1))).func_181675_d();
          vertexbuffer.func_181662_b(-0.866D * f3, f2, (-0.5F * f3)).func_181669_b(255, 0, 255, 0).func_181675_d();
          vertexbuffer.func_181662_b(0.866D * f3, f2, (-0.5F * f3)).func_181669_b(255, 0, 255, 0).func_181675_d();
          vertexbuffer.func_181662_b(0.0D, f2, (1.0F * f3)).func_181669_b(255, 0, 255, 0).func_181675_d();
          vertexbuffer.func_181662_b(-0.866D * f3, f2, (-0.5F * f3)).func_181669_b(255, 0, 255, 0).func_181675_d();
          tessellator.func_78381_a();
        } 
        GlStateManager.func_179121_F();
        GlStateManager.func_179132_a(true);
        GlStateManager.func_179129_p();
        GlStateManager.func_179084_k();
        GlStateManager.func_179103_j(7424);
        GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.func_179098_w();
        GlStateManager.func_179141_d();
        RenderHelper.func_74519_b();
      } 
    }
    
    public boolean func_177142_b() {
      return false;
    }
  }
}
