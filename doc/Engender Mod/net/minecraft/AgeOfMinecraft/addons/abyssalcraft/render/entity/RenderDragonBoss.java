package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonBoss;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.model.ModelDragonBoss;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.layer.LayerAsorahDeath;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.layer.LayerAsorahEyes;
import net.minecraft.AgeOfMinecraft.renders.LayerLearningBook;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderDragonBoss extends RenderLiving<EntityDragonBoss> {
  private float scale = 1.5F;
  
  private static final ResourceLocation DRAGON_EXPLODING_TEXTURES = new ResourceLocation("abyssalcraft:textures/model/boss/dragonboss_exploding.png");
  
  private static final ResourceLocation ENDERCRYSTAL_BEAM_TEXTURES = new ResourceLocation("textures/entity/endercrystal/endercrystal_beam.png");
  
  private static final ResourceLocation DRAGON_TEXTURES = new ResourceLocation("abyssalcraft:textures/model/boss/dragonboss.png");
  
  protected ModelDragonBoss modelDragon;
  
  public RenderDragonBoss(RenderManager manager) {
    super(manager, (ModelBase)new ModelDragonBoss(0.0F), 0.9F);
    this.modelDragon = (ModelDragonBoss)this.field_77045_g;
    func_177094_a((LayerRenderer)new LayerAsorahEyes(this));
    func_177094_a((LayerRenderer)new LayerAsorahDeath());
    func_177094_a((LayerRenderer)new LayerLearningBook(this));
  }
  
  protected void applyRotations(EntityDragonBoss par1entitydragonboss, float par2, float par3, float par4) {
    float f3 = (float)par1entitydragonboss.getMovementOffsets(7, par4)[0];
    float f4 = (float)(par1entitydragonboss.getMovementOffsets(5, par4)[1] - par1entitydragonboss.getMovementOffsets(10, par4)[1]);
    GlStateManager.func_179114_b(-f3, 0.0F, 1.0F, 0.0F);
    GlStateManager.func_179114_b(f4 * 10.0F, 1.0F, 0.0F, 0.0F);
    GlStateManager.func_179109_b(0.0F, 0.0F, 1.0F);
    if (par1entitydragonboss.field_70725_aQ > 0) {
      float f5 = (par1entitydragonboss.field_70725_aQ + par4 - 1.0F) / 20.0F * 1.6F;
      f5 = MathHelper.func_76129_c(f5);
      if (f5 > 1.0F)
        f5 = 1.0F; 
      GlStateManager.func_179114_b(f5 * func_77037_a((EntityLivingBase)par1entitydragonboss), 0.0F, 0.0F, 1.0F);
    } 
  }
  
  protected void renderModel(EntityDragonBoss par1EntityDragonBoss, float par2, float par3, float par4, float par5, float par6, float par7) {
    if (par1EntityDragonBoss.deathTicks > 0) {
      float f6 = par1EntityDragonBoss.deathTicks / 200.0F;
      GL11.glDepthFunc(515);
      GlStateManager.func_179141_d();
      GL11.glAlphaFunc(516, f6);
      func_110776_a(DRAGON_EXPLODING_TEXTURES);
      this.field_77045_g.func_78088_a((Entity)par1EntityDragonBoss, par2, par3, par4, par5, par6, par7);
      GL11.glAlphaFunc(516, 0.1F);
      GL11.glDepthFunc(514);
    } 
    func_180548_c((Entity)par1EntityDragonBoss);
    this.field_77045_g.func_78088_a((Entity)par1EntityDragonBoss, par2, par3, par4, par5, par6, par7);
    if (par1EntityDragonBoss.field_70737_aN > 0) {
      GL11.glDepthFunc(514);
      GlStateManager.func_179090_x();
      GlStateManager.func_179147_l();
      GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      GlStateManager.func_179131_c(1.0F, 0.0F, 0.0F, 0.5F);
      this.field_77045_g.func_78088_a((Entity)par1EntityDragonBoss, par2, par3, par4, par5, par6, par7);
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
      GL11.glDepthFunc(515);
    } 
  }
  
  public void doRender(EntityDragonBoss dragon, double par2, double par4, double par6, float par8, float par9) {
    super.func_76986_a((EntityLiving)dragon, par2, par4, par6, par8, par9);
  }
  
  protected ResourceLocation func_110841_a(EntityDragonBoss par1EntityDragonBoss) {
    return DRAGON_TEXTURES;
  }
  
  protected void preRenderCallback(EntityDragonBoss entitylivingbaseIn, float partialTickTime) {
    GlStateManager.func_179152_a(this.scale, this.scale, this.scale);
    if (entitylivingbaseIn.deathTicks > 0)
      GlStateManager.func_179114_b((entitylivingbaseIn.deathTicks * 10), 0.0F, 1.0F, 0.0F); 
    float fit = entitylivingbaseIn.getFittness();
    GlStateManager.func_179152_a(fit, fit, fit);
    if (entitylivingbaseIn.isHero())
      GlStateManager.func_179152_a(1.05F, 1.05F, 1.05F); 
  }
  
  protected ResourceLocation getEntityTexture(EntityDragonBoss par1Entity) {
    return func_110841_a(par1Entity);
  }
}
