package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonMinion;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.model.ModelDragonMinion;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.layer.LayerSpectralDragonEyes;
import net.minecraft.AgeOfMinecraft.renders.LayerLearningBook;
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
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
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
    super(manager, (ModelBase)new ModelDragonMinion(0.0F), 0.0F);
    this.modelDragon = (ModelDragonMinion)this.field_77045_g;
    func_177094_a((LayerRenderer)new LayerSpectralDragonEyes(this));
    func_177094_a((LayerRenderer)new LayerLearningBook(this));
  }
  
  protected void preRenderScale(EntityDragonMinion entitylivingbaseIn, float partialTickTime) {
    GlStateManager.func_179152_a(this.scale, this.scale, this.scale);
    float fit = entitylivingbaseIn.getFittness();
    GlStateManager.func_179152_a(fit, fit, fit);
    if (entitylivingbaseIn.isHero())
      GlStateManager.func_179152_a(1.05F, 1.05F, 1.05F); 
    if (entitylivingbaseIn.func_70631_g_())
      GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F); 
    if (entitylivingbaseIn.field_70173_aa <= 21 && entitylivingbaseIn.field_70173_aa > 0) {
      float f5 = (entitylivingbaseIn.field_70173_aa + partialTickTime - 1.0F) / 20.0F * 1.6F;
      f5 = MathHelper.func_76129_c(f5);
      if (f5 > 1.0F)
        f5 = 1.0F; 
      GlStateManager.func_179152_a(f5, f5, f5);
      GlStateManager.func_179114_b(f5 * 90.0F - 90.0F, f5, f5, f5);
    } 
  }
  
  protected void applyRotations(EntityDragonMinion par1EntityDragonMinion, float par2, float par3, float par4) {
    float f3 = (float)par1EntityDragonMinion.getMovementOffsets(7, par4)[0];
    float f4 = (float)(par1EntityDragonMinion.getMovementOffsets(5, par4)[1] - par1EntityDragonMinion.getMovementOffsets(10, par4)[1]);
    GlStateManager.func_179114_b(-f3, 0.0F, 1.0F, 0.0F);
    GlStateManager.func_179114_b(f4 * 10.0F, 1.0F, 0.0F, 0.0F);
    GlStateManager.func_179109_b(0.0F, 0.0F, 1.0F);
    if (par1EntityDragonMinion.field_70725_aQ > 0) {
      float f5 = (par1EntityDragonMinion.field_70725_aQ + par4 - 1.0F) / 20.0F * 1.6F;
      f5 = MathHelper.func_76129_c(f5);
      if (f5 > 1.0F)
        f5 = 1.0F; 
      GlStateManager.func_179114_b(f5 * func_77037_a((EntityLivingBase)par1EntityDragonMinion), 0.0F, 0.0F, 1.0F);
    } 
  }
  
  public void doRender(EntityDragonMinion dragon, double par2, double par4, double par6, float par8, float par9) {
    super.func_76986_a((EntityLiving)dragon, par2, par4, par6, par8, par9);
    if (dragon.healingcircle != null) {
      float f = 0.0F + par9;
      float f1 = MathHelper.func_76126_a(f * 0.2F) / 2.0F + 0.5F;
      f1 = (f1 * f1 + f1) * 0.2F;
      float f2 = (float)(dragon.healingcircle.field_70165_t - dragon.field_70165_t - (dragon.field_70169_q - dragon.field_70165_t) * (1.0F - par9));
      float f3 = (float)(f1 + dragon.healingcircle.field_70163_u + 1.0D - dragon.field_70163_u - (dragon.field_70167_r - dragon.field_70163_u - 1.0D) * (1.0F - par9));
      float f4 = (float)(dragon.healingcircle.field_70161_v - dragon.field_70161_v - (dragon.field_70166_s - dragon.field_70161_v) * (1.0F - par9));
      float f5 = MathHelper.func_76129_c(f2 * f2 + f4 * f4);
      float f6 = MathHelper.func_76129_c(f2 * f2 + f3 * f3 + f4 * f4);
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b((float)par2, (float)par4 + 1.0F, (float)par6);
      GlStateManager.func_179114_b((float)-Math.atan2(f4, f2) * 180.0F / 3.1415927F - 90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b((float)-Math.atan2(f5, f3) * 180.0F / 3.1415927F - 90.0F, 1.0F, 0.0F, 0.0F);
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder worldrenderer = tessellator.func_178180_c();
      RenderHelper.func_74518_a();
      GlStateManager.func_179129_p();
      func_110776_a(DRAGON_EXPLODING_TEXTURES);
      GlStateManager.func_179103_j(7425);
      float f7 = 0.0F + (dragon.field_70173_aa + par9) * 0.1F;
      float f8 = MathHelper.func_76129_c(f2 * f2 + f3 * f3 + f4 * f4) / 32.0F - (dragon.field_70173_aa + par9) * 0.01F;
      worldrenderer.func_181668_a(5, DefaultVertexFormats.field_181709_i);
      for (int j = 0; j <= 4; j++) {
        float f9 = MathHelper.func_76126_a((j % 8) * 3.1415927F * 2.0F / 8.0F) * 0.75F;
        float f10 = MathHelper.func_76134_b((j % 8) * 3.1415927F * 2.0F / 8.0F) * 0.75F;
        float f11 = (j % 8) * 1.0F / 8.0F;
        worldrenderer.func_181662_b((f9 * 0.2F), (f10 * 0.2F), 0.0D).func_187315_a(f11, f8).func_181669_b(0, 0, 0, 255).func_181675_d();
        worldrenderer.func_181662_b(f9, f10, f6).func_187315_a(f11, f7).func_181669_b(255, 255, 255, 255).func_181675_d();
      } 
      tessellator.func_78381_a();
      GlStateManager.func_179089_o();
      GlStateManager.func_179103_j(7424);
      RenderHelper.func_74519_b();
      GlStateManager.func_179121_F();
    } 
  }
  
  protected void renderModel(EntityDragonMinion par1EntityDragonMinion, float par2, float par3, float par4, float par5, float par6, float par7) {
    func_180548_c((Entity)par1EntityDragonMinion);
    GlStateManager.func_179147_l();
    GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 0.5F);
    this.field_77045_g.func_78088_a((Entity)par1EntityDragonMinion, par2, par3, par4, par5, par6, par7);
    GlStateManager.func_179084_k();
    if (par1EntityDragonMinion.field_70737_aN > 0) {
      GL11.glDepthFunc(514);
      GlStateManager.func_179090_x();
      GlStateManager.func_179147_l();
      GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      GlStateManager.func_179131_c(1.0F, 0.0F, 0.0F, 0.5F);
      this.field_77045_g.func_78088_a((Entity)par1EntityDragonMinion, par2, par3, par4, par5, par6, par7);
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
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
