package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityPrisonSlime;
import net.minecraft.AgeOfMinecraft.models.ModelSlime;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderPrisonSlime extends RenderLiving<EntityPrisonSlime> {
  private static final ResourceLocation slimeTextures = new ResourceLocation("ageofminecraft", "textures/entities/prison_slime.png");
  
  private static ModelSlime regularmodel = new ModelSlime(16);
  
  public RenderPrisonSlime(RenderManager renderManagerIn) {
    super(renderManagerIn, (ModelBase)regularmodel, 0.25F);
    func_177094_a(new LayerSlimeGel(this));
    func_177094_a(new LayerCustomHeadEngender(regularmodel.slimeBodies, regularmodel.slimeBodies));
    func_177094_a(new LayerLearningBook(this));
  }
  
  protected void preRenderCallback(EntityPrisonSlime entitylivingbaseIn, float partialTickTime) {
    this.field_76989_e = 0.25F * entitylivingbaseIn.getSlimeSize();
    float f1 = entitylivingbaseIn.getSlimeSize();
    float f2 = (entitylivingbaseIn.prevSquishFactor + (entitylivingbaseIn.squishFactor - entitylivingbaseIn.prevSquishFactor) * partialTickTime) / (f1 * 0.5F + 1.0F);
    float f3 = 1.0F / (f2 + 1.0F);
    GlStateManager.func_179152_a(f3 * f1, 1.0F / f3 * f1, f3 * f1);
    if (entitylivingbaseIn.func_70093_af())
      GlStateManager.func_179152_a(1.25F, 0.75F, 1.25F); 
    float fit = entitylivingbaseIn.getFittness();
    GlStateManager.func_179152_a(fit, fit, fit);
    if (entitylivingbaseIn.isHero())
      GlStateManager.func_179152_a(1.05F, 1.05F, 1.05F); 
    if (!entitylivingbaseIn.field_70122_E)
      GlStateManager.func_179114_b(entitylivingbaseIn.prevRotationPitchFalling + (entitylivingbaseIn.rotationPitchFalling - entitylivingbaseIn.prevRotationPitchFalling) * 2.0F - 1.0F, 1.0F, 0.0F, 0.0F); 
    if (entitylivingbaseIn.field_70173_aa <= 21 && entitylivingbaseIn.field_70173_aa > 0) {
      float f5 = (entitylivingbaseIn.field_70173_aa + partialTickTime - 1.0F) / 20.0F * 1.6F;
      f5 = MathHelper.func_76129_c(f5);
      if (f5 > 1.0F)
        f5 = 1.0F; 
      GlStateManager.func_179152_a(f5, f5, f5);
      GlStateManager.func_179114_b(f5 * 90.0F - 90.0F, f5, f5, f5);
    } 
  }
  
  protected void applyRotations(EntityPrisonSlime entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
    if (entityLiving.getJukeboxToDanceTo() != null)
      GlStateManager.func_179152_a(1.0F + MathHelper.func_76134_b(p_77043_2_ * 1.0F) * 0.1F, 1.0F - MathHelper.func_76134_b(p_77043_2_ * 1.0F) * 0.05F, 1.0F + MathHelper.func_76134_b(p_77043_2_ * 1.0F) * 0.1F); 
    GlStateManager.func_179114_b(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
    if (entityLiving.field_70725_aQ > 0) {
      float f = (entityLiving.field_70725_aQ + partialTicks - 1.0F) / 20.0F * 1.6F;
      f = MathHelper.func_76129_c(f);
      if (f > 1.0F)
        f = 1.0F; 
      GlStateManager.func_179114_b(f * func_77037_a((EntityLivingBase)entityLiving), 0.0F, 0.0F, 1.0F);
    } else {
      String s = TextFormatting.func_110646_a(entityLiving.func_70005_c_());
      if (s != null && ("Dinnerbone".equals(s) || "Grumm".equals(s))) {
        GlStateManager.func_179109_b(0.0F, entityLiving.field_70131_O + 0.1F, 0.0F);
        GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
      } 
    } 
  }
  
  protected ResourceLocation getEntityTexture(EntityPrisonSlime entity) {
    return slimeTextures;
  }
  
  public void doRender(EntityPrisonSlime entity, double x, double y, double z, float entityYaw, float partialTicks) {
    if (entity.getGhostTime() > 0) {
      Vec3d[] avec3d = entity.getRenderLocations(partialTicks);
      float f = func_77044_a((EntityLivingBase)entity, partialTicks);
      for (int i = 0; i < avec3d.length; i++)
        super.func_76986_a((EntityLiving)entity, x + (avec3d[i]).field_72450_a + MathHelper.func_76134_b(i + f * 0.5F) * 0.025D, y + (avec3d[i]).field_72448_b + MathHelper.func_76134_b(i + f * 0.75F) * 0.0125D, z + (avec3d[i]).field_72449_c + MathHelper.func_76134_b(i + f * 0.7F) * 0.025D, entityYaw, partialTicks); 
      this.field_76987_f = 0.0F;
    } else {
      this.field_76987_f = 1.0F;
      super.func_76986_a((EntityLiving)entity, x, y, z, entityYaw, partialTicks);
    } 
  }
  
  protected boolean isVisible(EntityPrisonSlime entity) {
    return (!entity.func_82150_aj() || this.field_188301_f || entity.getGhostTime() > 0);
  }
  
  @SideOnly(Side.CLIENT)
  public class LayerSlimeGel implements LayerRenderer<EntityLivingBase> {
    private final ResourceLocation slimeTextures = new ResourceLocation("ageofminecraft", "textures/entities/prison_slime.png");
    
    private final RenderPrisonSlime slimeRenderer;
    
    private ModelBase slimeModel = (ModelBase)new ModelSlime(0);
    
    public LayerSlimeGel(RenderPrisonSlime p_i46111_1_) {
      this.slimeRenderer = p_i46111_1_;
    }
    
    public void doRenderLayer(EntityPrisonSlime p_177159_1_, float p_177159_2_, float p_177159_3_, float p_177159_4_, float p_177159_5_, float p_177159_6_, float p_177159_7_, float p_177159_8_) {
      if (!p_177159_1_.func_82150_aj()) {
        GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 0.75F);
        GlStateManager.func_179108_z();
        GlStateManager.func_179147_l();
        GlStateManager.func_179112_b(770, 771);
        this.slimeRenderer.func_110776_a(this.slimeTextures);
        this.slimeModel.func_178686_a(this.slimeRenderer.func_177087_b());
        this.slimeModel.func_78088_a((Entity)p_177159_1_, p_177159_2_, p_177159_3_, p_177159_5_, p_177159_6_, p_177159_7_, p_177159_8_);
        GlStateManager.func_179084_k();
        GlStateManager.func_179133_A();
      } 
    }
    
    public boolean func_177142_b() {
      return true;
    }
    
    public void func_177141_a(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
      doRenderLayer((EntityPrisonSlime)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
    }
  }
}
