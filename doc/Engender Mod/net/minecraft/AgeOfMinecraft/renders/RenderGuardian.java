package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGuardian;
import net.minecraft.AgeOfMinecraft.models.ModelCMMGuardian;
import net.minecraft.AgeOfMinecraft.models.ModelGuardian;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderGuardian extends RenderLiving<EntityGuardian> {
  private static final ResourceLocation CMM_GUARDIAN_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/guardian.png");
  
  private static final ResourceLocation CMM_GUARDIAN_ELDER_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/guardiane.png");
  
  private static final ResourceLocation GUARDIAN_TEXTURE = new ResourceLocation("textures/entity/guardian.png");
  
  private static final ResourceLocation GUARDIAN_ELDER_TEXTURE = new ResourceLocation("textures/entity/guardian_elder.png");
  
  private static final ResourceLocation antiCMM_GUARDIAN_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/anti/guardian.png");
  
  private static final ResourceLocation antiCMM_GUARDIAN_ELDER_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/anti/guardiane.png");
  
  private static final ResourceLocation antiGUARDIAN_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/anti/guardian.png");
  
  private static final ResourceLocation antiGUARDIAN_ELDER_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/anti/guardian_elder.png");
  
  private static final ResourceLocation GUARDIAN_BEAM_TEXTURE = new ResourceLocation("textures/entity/guardian_beam.png");
  
  private float scaledown = 1.0F;
  
  private static ModelCMMGuardian cmmmodel = new ModelCMMGuardian();
  
  private static ModelGuardian regularmodel = new ModelGuardian();
  
  public RenderGuardian(RenderManager renderManagerIn) {
    super(renderManagerIn, EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)cmmmodel : (ModelBase)regularmodel, 0.5F);
    func_177094_a(new LayerArrowCustomSized((RenderLivingBase<?>)this, 0.5F));
    func_177094_a(new LayerCustomHeadEngender(regularmodel.guardianBody, cmmmodel.Head));
    func_177094_a(new LayerLearningBook(this));
    func_177094_a(new LayerMobCape((RenderLivingBase<?>)this));
  }
  
  public boolean shouldRender(EntityGuardian livingEntity, ICamera camera, double camX, double camY, double camZ) {
    if (super.func_177071_a((EntityLiving)livingEntity, camera, camX, camY, camZ))
      return true; 
    if (livingEntity.hasTargetedEntity()) {
      EntityLivingBase entitylivingbase = livingEntity.getTargetedEntity();
      if (entitylivingbase != null) {
        Vec3d vec3d = getPosition(entitylivingbase, entitylivingbase.field_70131_O * 0.5D, 1.0F);
        Vec3d vec3d1 = getPosition((EntityLivingBase)livingEntity, livingEntity.func_70047_e(), 1.0F);
        if (camera.func_78546_a(new AxisAlignedBB(vec3d1.field_72450_a, vec3d1.field_72448_b, vec3d1.field_72449_c, vec3d.field_72450_a, vec3d.field_72448_b, vec3d.field_72449_c)))
          return true; 
      } 
    } 
    return false;
  }
  
  private Vec3d getPosition(EntityLivingBase entityLivingBaseIn, double p_177110_2_, float p_177110_4_) {
    double d0 = entityLivingBaseIn.field_70142_S + (entityLivingBaseIn.field_70165_t - entityLivingBaseIn.field_70142_S) * p_177110_4_;
    double d1 = p_177110_2_ + entityLivingBaseIn.field_70137_T + (entityLivingBaseIn.field_70163_u - entityLivingBaseIn.field_70137_T) * p_177110_4_;
    double d2 = entityLivingBaseIn.field_70136_U + (entityLivingBaseIn.field_70161_v - entityLivingBaseIn.field_70136_U) * p_177110_4_;
    return new Vec3d(d0, d1, d2);
  }
  
  public void doRenderGuardian(EntityGuardian entity, double x, double y, double z, float entityYaw, float partialTicks) {
    EntityLivingBase entitylivingbase = entity.getTargetedEntity();
    if (entitylivingbase != null) {
      float f = entity.getAttackAnimationScale(partialTicks);
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder vertexbuffer = tessellator.func_178180_c();
      func_110776_a(GUARDIAN_BEAM_TEXTURE);
      GlStateManager.func_187421_b(3553, 10242, 10497);
      GlStateManager.func_187421_b(3553, 10243, 10497);
      GlStateManager.func_179140_f();
      GlStateManager.func_179129_p();
      GlStateManager.func_179084_k();
      GlStateManager.func_179132_a(true);
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
      GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
      float f2 = (float)entity.field_70170_p.func_82737_E() + partialTicks;
      float f3 = f2 * 0.5F % 1.0F;
      float f4 = entity.func_70047_e();
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b((float)x, (float)y + f4, (float)z);
      Vec3d vec3d = getPosition(entitylivingbase, entitylivingbase.func_70047_e(), partialTicks);
      Vec3d vec3d1 = getPosition((EntityLivingBase)entity, f4, partialTicks);
      Vec3d vec3d2 = vec3d.func_178788_d(vec3d1);
      double d0 = vec3d2.func_72433_c() + 1.0D;
      vec3d2 = vec3d2.func_72432_b();
      float f5 = (float)Math.acos(vec3d2.field_72448_b);
      float f6 = (float)Math.atan2(vec3d2.field_72449_c, vec3d2.field_72450_a);
      GlStateManager.func_179114_b((1.5707964F + -f6) * 57.295776F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(f5 * 57.295776F, 1.0F, 0.0F, 0.0F);
      double d1 = f2 * 0.05D * -1.5D;
      vertexbuffer.func_181668_a(7, DefaultVertexFormats.field_181709_i);
      float f7 = f * f;
      int j = 64 + (int)(f7 * 191.0F);
      int k = 32 + (int)(f7 * 191.0F);
      int l = 128 - (int)(f7 * 64.0F);
      double d4 = 0.0D + Math.cos(d1 + 2.356194490192345D) * 0.282D;
      double d5 = 0.0D + Math.sin(d1 + 2.356194490192345D) * 0.282D;
      double d6 = 0.0D + Math.cos(d1 + 0.7853981633974483D) * 0.282D;
      double d7 = 0.0D + Math.sin(d1 + 0.7853981633974483D) * 0.282D;
      double d8 = 0.0D + Math.cos(d1 + 3.9269908169872414D) * 0.282D;
      double d9 = 0.0D + Math.sin(d1 + 3.9269908169872414D) * 0.282D;
      double d10 = 0.0D + Math.cos(d1 + 5.497787143782138D) * 0.282D;
      double d11 = 0.0D + Math.sin(d1 + 5.497787143782138D) * 0.282D;
      double d12 = 0.0D + Math.cos(d1 + Math.PI) * 0.2D;
      double d13 = 0.0D + Math.sin(d1 + Math.PI) * 0.2D;
      double d14 = 0.0D + Math.cos(d1 + 0.0D) * 0.2D;
      double d15 = 0.0D + Math.sin(d1 + 0.0D) * 0.2D;
      double d16 = 0.0D + Math.cos(d1 + 1.5707963267948966D) * 0.2D;
      double d17 = 0.0D + Math.sin(d1 + 1.5707963267948966D) * 0.2D;
      double d18 = 0.0D + Math.cos(d1 + 4.71238898038469D) * 0.2D;
      double d19 = 0.0D + Math.sin(d1 + 4.71238898038469D) * 0.2D;
      double d22 = (-1.0F + f3);
      double d23 = d0 * 2.5D + d22;
      vertexbuffer.func_181662_b(d12, d0, d13).func_187315_a(0.4999D, d23).func_181669_b(j, k, l, 255).func_181675_d();
      vertexbuffer.func_181662_b(d12, 0.0D, d13).func_187315_a(0.4999D, d22).func_181669_b(j, k, l, 255).func_181675_d();
      vertexbuffer.func_181662_b(d14, 0.0D, d15).func_187315_a(0.0D, d22).func_181669_b(j, k, l, 255).func_181675_d();
      vertexbuffer.func_181662_b(d14, d0, d15).func_187315_a(0.0D, d23).func_181669_b(j, k, l, 255).func_181675_d();
      vertexbuffer.func_181662_b(d16, d0, d17).func_187315_a(0.4999D, d23).func_181669_b(j, k, l, 255).func_181675_d();
      vertexbuffer.func_181662_b(d16, 0.0D, d17).func_187315_a(0.4999D, d22).func_181669_b(j, k, l, 255).func_181675_d();
      vertexbuffer.func_181662_b(d18, 0.0D, d19).func_187315_a(0.0D, d22).func_181669_b(j, k, l, 255).func_181675_d();
      vertexbuffer.func_181662_b(d18, d0, d19).func_187315_a(0.0D, d23).func_181669_b(j, k, l, 255).func_181675_d();
      double d24 = 0.0D;
      if (entity.field_70173_aa % 2 == 0)
        d24 = 0.5D; 
      GlStateManager.func_179152_a(entity.getFittness(), 1.0F, entity.getFittness());
      if (entity.isHero() && entity.getSpecialAttackTimer() <= 0)
        GlStateManager.func_179152_a(4.0F, 1.0F, 4.0F); 
      vertexbuffer.func_181662_b(d4, d0, d5).func_187315_a(0.5D, d24 + 0.5D).func_181669_b(j, k, l, 255).func_181675_d();
      vertexbuffer.func_181662_b(d6, d0, d7).func_187315_a(1.0D, d24 + 0.5D).func_181669_b(j, k, l, 255).func_181675_d();
      vertexbuffer.func_181662_b(d10, d0, d11).func_187315_a(1.0D, d24).func_181669_b(j, k, l, 255).func_181675_d();
      vertexbuffer.func_181662_b(d8, d0, d9).func_187315_a(0.5D, d24).func_181669_b(j, k, l, 255).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179121_F();
    } 
  }
  
  protected void applyRotations(EntityGuardian entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
    GlStateManager.func_179114_b(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
    if (EngenderConfig.mobs.useMobTalkerModels && entityLiving.func_70089_S())
      if (entityLiving.func_70090_H() || entityLiving.func_180799_ab() || entityLiving.isSitResting() || (entityLiving.isElder() && !entityLiving.func_70631_g_()))
        GlStateManager.func_179109_b(0.0F, MathHelper.func_76134_b(p_77043_2_ * 0.2F) * 0.1F, 0.0F);  
    if (entityLiving.field_70725_aQ > 0) {
      float f = (entityLiving.field_70725_aQ + partialTicks - 1.0F) / 20.0F * 1.6F;
      f = MathHelper.func_76129_c(f);
      if (f > 1.0F)
        f = 1.0F; 
      if (EngenderConfig.mobs.useMobTalkerModels) {
        GlStateManager.func_179114_b(f * func_77037_a((EntityLivingBase)entityLiving), -1.0F, 0.0F, 0.0F);
        GlStateManager.func_179109_b(0.0F, 0.0F, f * 0.1F);
      } else {
        GlStateManager.func_179114_b(f * func_77037_a((EntityLivingBase)entityLiving), 0.0F, 0.0F, 1.0F);
        GlStateManager.func_179109_b(f * 0.5F, -f * 0.5F, 0.0F);
      } 
    } else {
      String s = TextFormatting.func_110646_a(entityLiving.func_70005_c_());
      if (s != null && ("Dinnerbone".equals(s) || "Grumm".equals(s))) {
        GlStateManager.func_179109_b(0.0F, entityLiving.field_70131_O + 0.1F, 0.0F);
        GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
      } 
    } 
  }
  
  protected void preRenderCallback(EntityGuardian entitylivingbaseIn, float partialTickTime) {
    this.field_77045_g = EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)cmmmodel : (ModelBase)regularmodel;
    if (EngenderConfig.mobs.useMobTalkerModels) {
      GlStateManager.func_179152_a(0.85F, 0.85F, 0.85F);
      if (entitylivingbaseIn.isElder()) {
        GlStateManager.func_179152_a(1.4F, 1.4F, 1.4F);
        if (entitylivingbaseIn.func_184218_aH() && entitylivingbaseIn.func_70631_g_())
          GlStateManager.func_179109_b(0.0F, 0.05F, -0.05F); 
      } 
      if (entitylivingbaseIn.func_184218_aH() && entitylivingbaseIn.func_70631_g_())
        GlStateManager.func_179109_b(0.0F, 0.6F, 0.25F); 
    } else {
      if (entitylivingbaseIn.func_184218_aH())
        GlStateManager.func_179109_b(0.0F, -0.5F, 0.0F); 
      if (entitylivingbaseIn.func_70631_g_())
        GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F); 
      GlStateManager.func_179109_b(0.0F, 1.0F, 0.0F);
      if (entitylivingbaseIn.isElder()) {
        GlStateManager.func_179152_a(2.35F, 2.35F, 2.35F);
        this.scaledown = 0.425F;
        if (entitylivingbaseIn.func_70089_S() && !entitylivingbaseIn.field_70160_al && entitylivingbaseIn.field_70122_E && !entitylivingbaseIn.func_70093_af() && !entitylivingbaseIn.func_70090_H() && !entitylivingbaseIn.func_180799_ab()) {
          GlStateManager.func_179109_b(0.0F, 0.3F, 0.0F);
        } else {
          GlStateManager.func_179109_b(0.0F, 0.6F, 0.0F);
        } 
      } 
    } 
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
  
  protected ResourceLocation getEntityTexture(EntityGuardian entity) {
    return EngenderConfig.mobs.useMobTalkerModels ? (entity.isElder() ? (entity.isAntiMob() ? antiCMM_GUARDIAN_ELDER_TEXTURE : CMM_GUARDIAN_ELDER_TEXTURE) : (entity.isAntiMob() ? antiCMM_GUARDIAN_TEXTURE : CMM_GUARDIAN_TEXTURE)) : (entity.isElder() ? (entity.isAntiMob() ? antiGUARDIAN_ELDER_TEXTURE : GUARDIAN_ELDER_TEXTURE) : (entity.isAntiMob() ? antiGUARDIAN_TEXTURE : GUARDIAN_TEXTURE));
  }
  
  public void doRender(EntityGuardian entity, double x, double y, double z, float entityYaw, float partialTicks) {
    if (entity.getGhostTime() > 0) {
      Vec3d[] avec3d = entity.getRenderLocations(partialTicks);
      float f = func_77044_a((EntityLivingBase)entity, partialTicks);
      for (int i = 0; i < avec3d.length; i++) {
        doRenderGuardian(entity, x + (avec3d[i]).field_72450_a + MathHelper.func_76134_b(i + f * 0.5F) * 0.025D, y + (avec3d[i]).field_72448_b + MathHelper.func_76134_b(i + f * 0.75F) * 0.0125D, z + (avec3d[i]).field_72449_c + MathHelper.func_76134_b(i + f * 0.7F) * 0.025D, entityYaw, partialTicks);
        super.func_76986_a((EntityLiving)entity, x + (avec3d[i]).field_72450_a + MathHelper.func_76134_b(i + f * 0.5F) * 0.025D, y + (avec3d[i]).field_72448_b + MathHelper.func_76134_b(i + f * 0.75F) * 0.0125D, z + (avec3d[i]).field_72449_c + MathHelper.func_76134_b(i + f * 0.7F) * 0.025D, entityYaw, partialTicks);
      } 
      this.field_76987_f = 0.0F;
    } else {
      this.field_76987_f = 1.0F;
      doRenderGuardian(entity, x, y, z, entityYaw, partialTicks);
      super.func_76986_a((EntityLiving)entity, x, y, z, entityYaw, partialTicks);
    } 
  }
  
  protected boolean isVisible(EntityGuardian entity) {
    return (!entity.func_82150_aj() || this.field_188301_f || entity.getGhostTime() > 0);
  }
}
