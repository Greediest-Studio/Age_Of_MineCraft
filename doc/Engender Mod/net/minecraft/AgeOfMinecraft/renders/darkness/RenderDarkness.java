package net.minecraft.AgeOfMinecraft.renders.darkness;

import net.minecraft.AgeOfMinecraft.entity.untame.tier5.EntityDarkness;
import net.minecraft.AgeOfMinecraft.models.ModelDarkness;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
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
public class RenderDarkness extends RenderLiving<EntityDarkness> {
  private static final ResourceLocation ENDER_CRYSTAL_TEXTURES = new ResourceLocation("textures/entity/endercrystal/endercrystal.png");
  
  public static final ResourceLocation ENDERCRYSTAL_BEAM_TEXTURES = new ResourceLocation("textures/entity/endercrystal/endercrystal_beam.png");
  
  private static final ResourceLocation DRAGON_EXPLODING_TEXTURES = new ResourceLocation("textures/entity/enderdragon/dragon_exploding.png");
  
  private static final ResourceLocation DRAGON_TEXTURES = new ResourceLocation("textures/entity/enderdragon/dragon.png");
  
  private final ModelBase modelEnderCrystalNoBase = (ModelBase)new ModelEnderCrystal(0.0F, false);
  
  protected ModelDarkness modelDragon;
  
  public RenderDarkness(RenderManager renderManagerIn) {
    super(renderManagerIn, (ModelBase)new ModelDarkness(0.0F), 8.0F);
    this.modelDragon = (ModelDarkness)this.field_77045_g;
    func_177094_a(new LayerStaringIntoYou(this));
    func_177094_a(new LayerDarknessExplosion());
  }
  
  protected void applyRotations(EntityDarkness bat, float p_77043_2_, float p_77043_3_, float partialTicks) {
    float f = (float)bat.getMovementOffsets(7, partialTicks)[0];
    float f1 = (float)(bat.getMovementOffsets(5, partialTicks)[1] - bat.getMovementOffsets(10, partialTicks)[1]);
    GlStateManager.func_179114_b(-f, 0.0F, 1.0F, 0.0F);
    GlStateManager.func_179114_b(f1 * 10.0F, 1.0F, 0.0F, 0.0F);
    GlStateManager.func_179109_b(0.0F, 0.0F, 1.0F);
  }
  
  protected void renderModel(EntityDarkness entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
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
  
  protected void preRenderCallback(EntityDarkness entitylivingbaseIn, float partialTickTime) {
    if (entitylivingbaseIn.deathTicks > 0)
      GlStateManager.func_179114_b((entitylivingbaseIn.deathTicks * 10), 0.0F, 1.0F, 0.0F); 
  }
  
  public void doRender(EntityDarkness entity, double x, double y, double z, float entityYaw, float partialTicks) {
    super.func_76986_a((EntityLiving)entity, x, y, z, entityYaw, partialTicks);
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
    int i = 8;
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
  
  protected ResourceLocation getEntityTexture(EntityDarkness entity) {
    return DRAGON_TEXTURES;
  }
}
