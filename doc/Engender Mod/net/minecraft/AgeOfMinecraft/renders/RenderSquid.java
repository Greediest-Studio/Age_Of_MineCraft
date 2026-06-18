package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntitySquid;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSquid;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
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
public class RenderSquid extends RenderLiving<EntitySquid> {
  private static final ResourceLocation squidTextures = new ResourceLocation("textures/entity/squid.png");
  
  private static final ResourceLocation antisquidTextures = new ResourceLocation("textures/entity/squid.png");
  
  public RenderSquid(RenderManager p_i46138_1_) {
    super(p_i46138_1_, (ModelBase)new ModelSquid(), 0.7F);
    func_177094_a(new LayerArrowCustomSized((RenderLivingBase<?>)this, 1.0F));
    func_177094_a(new LayerLearningBook(this));
  }
  
  protected ResourceLocation getEntityTexture(EntitySquid entity) {
    return entity.isAntiMob() ? antisquidTextures : squidTextures;
  }
  
  protected void applyRotations(EntitySquid entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
    float f = entityLiving.prevSquidPitch + (entityLiving.squidPitch - entityLiving.prevSquidPitch) * partialTicks;
    float f1 = entityLiving.prevSquidYaw + (entityLiving.squidYaw - entityLiving.prevSquidYaw) * partialTicks;
    GlStateManager.func_179109_b(0.0F, 0.5F, 0.0F);
    GlStateManager.func_179114_b(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
    GlStateManager.func_179114_b(f, 1.0F, 0.0F, 0.0F);
    GlStateManager.func_179114_b(f1, 0.0F, 1.0F, 0.0F);
    GlStateManager.func_179109_b(0.0F, -1.2F, 0.0F);
    if (entityLiving.func_70631_g_())
      GlStateManager.func_179109_b(0.0F, 0.6F, 0.0F); 
    GlStateManager.func_179114_b(90.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
    if (entityLiving.field_70725_aQ > 0) {
      float f11 = (entityLiving.field_70725_aQ + partialTicks - 1.0F) / 20.0F * 1.6F;
      f11 = MathHelper.func_76129_c(f11);
      if (f11 > 1.0F)
        f11 = 1.0F; 
      GlStateManager.func_179114_b(f11 * 90.0F, 0.0F, 1.0F, 0.0F);
    } else {
      String s = TextFormatting.func_110646_a(entityLiving.func_70005_c_());
      if (s != null && (s.equals("Dinnerbone") || s.equals("Grumm"))) {
        GlStateManager.func_179109_b(0.0F, entityLiving.field_70131_O + 0.1F, 0.0F);
        GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
      } 
    } 
  }
  
  protected float handleRotationFloat(EntitySquid livingBase, float partialTicks) {
    return livingBase.lastTentacleAngle + (livingBase.tentacleAngle - livingBase.lastTentacleAngle) * partialTicks;
  }
  
  protected void preRenderCallback(EntitySquid entitylivingbaseIn, float partialTickTime) {
    if (entitylivingbaseIn.func_70631_g_())
      GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F); 
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
  
  public void doRender(EntitySquid entity, double x, double y, double z, float entityYaw, float partialTicks) {
    if (entity.getGhostTime() > 0) {
      Vec3d[] avec3d = entity.getRenderLocations(partialTicks);
      float f = handleRotationFloat(entity, partialTicks);
      for (int i = 0; i < avec3d.length; i++)
        super.func_76986_a((EntityLiving)entity, x + (avec3d[i]).field_72450_a + MathHelper.func_76134_b(i + f * 0.5F) * 0.025D, y + (avec3d[i]).field_72448_b + MathHelper.func_76134_b(i + f * 0.75F) * 0.0125D, z + (avec3d[i]).field_72449_c + MathHelper.func_76134_b(i + f * 0.7F) * 0.025D, entityYaw, partialTicks); 
      this.field_76987_f = 0.0F;
    } else if (!entity.func_82150_aj()) {
      this.field_76987_f = 1.0F;
      super.func_76986_a((EntityLiving)entity, x, y, z, entityYaw, partialTicks);
    } 
  }
  
  protected boolean isVisible(EntitySquid entity) {
    return (!entity.func_82150_aj() || this.field_188301_f || entity.getGhostTime() > 0);
  }
}
