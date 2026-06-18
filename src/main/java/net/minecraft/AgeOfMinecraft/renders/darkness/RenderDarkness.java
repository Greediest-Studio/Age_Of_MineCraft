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
    this.modelDragon = (ModelDarkness)this.mainModel;
    addLayer(new LayerStaringIntoYou(this));
    addLayer(new LayerDarknessExplosion());
  }
  
  protected void applyRotations(EntityDarkness bat, float p_77043_2_, float p_77043_3_, float partialTicks) {
    float f = (float)bat.getMovementOffsets(7, partialTicks)[0];
    float f1 = (float)(bat.getMovementOffsets(5, partialTicks)[1] - bat.getMovementOffsets(10, partialTicks)[1]);
    GlStateManager.rotate(-f, 0.0F, 1.0F, 0.0F);
    GlStateManager.rotate(f1 * 10.0F, 1.0F, 0.0F, 0.0F);
    GlStateManager.translate(0.0F, 0.0F, 1.0F);
  }
  
  protected void renderModel(EntityDarkness entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
    boolean flag = isVisible(entitylivingbaseIn);
    boolean flag1 = (!flag && !entitylivingbaseIn.isInvisibleToPlayer((EntityPlayer)(Minecraft.getMinecraft()).player));
    if (flag || flag1) {
      if (flag1)
        GlStateManager.enableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL); 
      if (entitylivingbaseIn.deathTicks > 0) {
        float f = entitylivingbaseIn.deathTicks / 200.0F;
        GlStateManager.depthFunc(515);
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, f);
        bindTexture(DRAGON_EXPLODING_TEXTURES);
        this.mainModel.render((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.depthFunc(514);
      } 
      bindEntityTexture(entitylivingbaseIn);
      this.mainModel.render((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
      if (entitylivingbaseIn.hurtTime > 0) {
        GlStateManager.depthFunc(514);
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1.0F, 0.0F, 0.0F, 0.5F);
        this.mainModel.render((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.depthFunc(515);
      } 
      if (flag1)
        GlStateManager.disableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL); 
    } 
  }
  
  protected void preRenderCallback(EntityDarkness entitylivingbaseIn, float partialTickTime) {
    if (entitylivingbaseIn.deathTicks > 0)
      GlStateManager.rotate((entitylivingbaseIn.deathTicks * 10), 0.0F, 1.0F, 0.0F); 
  }
  
  public void doRender(EntityDarkness entity, double x, double y, double z, float entityYaw, float partialTicks) {
    super.doRender(entity, x, y, z, entityYaw, partialTicks);
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
    int i = 8;
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
  
  protected ResourceLocation getEntityTexture(EntityDarkness entity) {
    return DRAGON_TEXTURES;
  }
}
