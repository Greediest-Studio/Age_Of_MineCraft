package net.minecraft.AgeOfMinecraft.renders;

import java.util.Random;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon;
import net.minecraft.AgeOfMinecraft.models.ModelEnderDragon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEnderDragon extends RenderLiving<EntityEnderDragon> {
  private static final ResourceLocation ENDER_CRYSTAL_TEXTURES = new ResourceLocation("textures/entity/endercrystal/endercrystal.png");
  
  public static final ResourceLocation ENDERCRYSTAL_BEAM_TEXTURES = new ResourceLocation("textures/entity/endercrystal/endercrystal_beam.png");
  
  private static final ResourceLocation DRAGON_EXPLODING_TEXTURES = new ResourceLocation("textures/entity/enderdragon/dragon_exploding.png");
  
  private static final ResourceLocation DRAGON_TEXTURES = new ResourceLocation("textures/entity/enderdragon/dragon.png");
  
  private final ModelBase modelEnderCrystalNoBase = (ModelBase)new ModelEnderCrystal(0.0F, false);
  
  protected ModelEnderDragon modelDragon;
  
  public RenderEnderDragon(RenderManager renderManagerIn) {
    super(renderManagerIn, (ModelBase)new ModelEnderDragon(0.0F), 8.0F);
    this.modelDragon = (ModelEnderDragon)this.field_77045_g;
    func_177094_a(new LayerEnderDragonEyes(this));
    func_177094_a(new LayerEnderDragonDeath());
    func_177094_a(new LayerArrowCustomSized((RenderLivingBase<?>)this, 1.0F));
    func_177094_a(new LayerLearningBook(this));
  }
  
  protected void applyRotations(EntityEnderDragon bat, float p_77043_2_, float p_77043_3_, float partialTicks) {
    float f = (float)bat.getMovementOffsets(7, partialTicks)[0];
    float f1 = (float)(bat.getMovementOffsets(5, partialTicks)[1] - bat.getMovementOffsets(10, partialTicks)[1]);
    GlStateManager.func_179114_b(-f, 0.0F, 1.0F, 0.0F);
    GlStateManager.func_179114_b(f1 * 10.0F, 1.0F, 0.0F, 0.0F);
    GlStateManager.func_179109_b(0.0F, 0.0F, 1.0F);
  }
  
  protected void renderModel(EntityEnderDragon entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
    boolean flag = func_193115_c((EntityLivingBase)entitylivingbaseIn);
    boolean flag1 = (!flag && !entitylivingbaseIn.func_98034_c((EntityPlayer)(Minecraft.func_71410_x()).field_71439_g));
    if (flag || flag1) {
      if (flag1)
        GlStateManager.func_187408_a(GlStateManager.Profile.TRANSPARENT_MODEL); 
      if (entitylivingbaseIn.deathTicks > 0) {
        float f = entitylivingbaseIn.deathTicks / 200.0F;
        GlStateManager.func_179143_c(515);
        GlStateManager.func_179141_d();
        GlStateManager.func_179092_a(516, f);
        func_110776_a(DRAGON_EXPLODING_TEXTURES);
        this.field_77045_g.func_78088_a((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        GlStateManager.func_179092_a(516, 0.1F);
        GlStateManager.func_179143_c(514);
      } 
      func_180548_c((Entity)entitylivingbaseIn);
      this.field_77045_g.func_78088_a((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
      if (entitylivingbaseIn.field_70737_aN > 0) {
        GlStateManager.func_179143_c(514);
        GlStateManager.func_179090_x();
        GlStateManager.func_179147_l();
        GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.func_179131_c(1.0F, 0.0F, 0.0F, 0.5F);
        this.field_77045_g.func_78088_a((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179143_c(515);
      } 
      if (flag1)
        GlStateManager.func_187440_b(GlStateManager.Profile.TRANSPARENT_MODEL); 
    } 
  }
  
  protected void preRenderCallback(EntityEnderDragon entitylivingbaseIn, float partialTickTime) {
    float fit = entitylivingbaseIn.getFittness();
    GlStateManager.func_179152_a(fit, fit, fit);
    if (entitylivingbaseIn.isHero())
      GlStateManager.func_179152_a(1.05F, 1.05F, 1.05F); 
    if (entitylivingbaseIn.deathTicks > 0)
      GlStateManager.func_179114_b((entitylivingbaseIn.deathTicks * 10), 0.0F, 1.0F, 0.0F); 
  }
  
  public void doRender(EntityEnderDragon entity, double x, double y, double z, float entityYaw, float partialTicks) {
    super.func_76986_a((EntityLiving)entity, x, y, z, entityYaw, partialTicks);
    if (entity.isCarryingCrystal()) {
      GlStateManager.func_179094_E();
      float f = entity.innerRotation + partialTicks;
      float fl1 = (entity.getJukeboxToDanceTo() != null) ? (MathHelper.func_76126_a(f * 0.1F) * 10.0F) : ((float)entity.field_70159_w * 10.0F);
      float fl2 = (entity.getJukeboxToDanceTo() != null) ? (MathHelper.func_76126_a(f * 0.3F) * 5.0F) : ((float)entity.field_70181_x * 5.0F);
      float fl3 = (entity.getJukeboxToDanceTo() != null) ? (MathHelper.func_76134_b(f * 0.1F) * 10.0F) : ((float)entity.field_70179_y * 10.0F);
      GlStateManager.func_179137_b(((float)x - fl1), (float)y + 10.0D - fl2, ((float)z - fl3));
      func_110776_a(ENDER_CRYSTAL_TEXTURES);
      float f1 = MathHelper.func_76126_a(f * 0.2F) / 2.0F + 0.5F;
      f1 = f1 * f1 + f1;
      if (this.field_188301_f) {
        GlStateManager.func_179142_g();
        GlStateManager.func_187431_e(func_188298_c((Entity)entity));
      } 
      this.modelEnderCrystalNoBase.func_78088_a((Entity)entity, 0.0F, f * 3.0F, f1 * 0.2F, 0.0F, 0.0F, 0.0625F);
      if (this.field_188301_f) {
        GlStateManager.func_187417_n();
        GlStateManager.func_179119_h();
      } 
      if (entity.getJukeboxToDanceTo() != null) {
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
      float f2 = (float)entity.field_70165_t + fl1;
      float f3 = (float)entity.field_70163_u - 10.0F + fl2;
      float f4 = (float)entity.field_70161_v + fl3;
      double d0 = f2 - entity.field_70165_t + fl1;
      double d1 = f3 - entity.field_70163_u - 10.0D + fl2;
      double d2 = f4 - entity.field_70161_v + fl3;
      if (entity.func_110143_aJ() < entity.func_110138_aP())
        func_188325_a(x + d0, y - 0.3D + (f1 * 0.4F) + d1, z + d2, partialTicks, f2, f3, f4, entity.innerRotation, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v); 
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
  
  protected ResourceLocation getEntityTexture(EntityEnderDragon entity) {
    return DRAGON_TEXTURES;
  }
}
