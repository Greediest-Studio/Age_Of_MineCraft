package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityIllusioner;
import net.minecraft.AgeOfMinecraft.models.ModelIllager;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderIllusioner extends RenderLiving<EntityIllusioner> {
  private static final ResourceLocation ILLUSIONIST = new ResourceLocation("textures/entity/illager/illusionist.png");
  
  private static final ResourceLocation DISGUISE_VILLAGER = new ResourceLocation("textures/entity/villager/farmer.png");
  
  private static final ResourceLocation DISGUISE_VINDICATOR = new ResourceLocation("textures/entity/illager/vindicator.png");
  
  private static final ResourceLocation DISGUISE_EVOKER = new ResourceLocation("textures/entity/illager/evoker.png");
  
  public RenderIllusioner(RenderManager p_i47477_1_) {
    super(p_i47477_1_, (ModelBase)new ModelIllager(0.0F, 0.0F, 64, 64), 0.5F);
    func_177094_a((LayerRenderer)new LayerHeldItem((RenderLivingBase)this) {
          public void func_177141_a(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
            if (((EntityIllusioner)entitylivingbaseIn).isSpellcasting() || ((EntityIllusioner)entitylivingbaseIn).isArmsRaised())
              super.func_177141_a(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale); 
          }
          
          protected void func_191361_a(EnumHandSide p_191361_1_) {
            ((ModelIllager)this.field_177206_a.func_177087_b()).getArm(p_191361_1_).func_78794_c(0.0625F);
          }
        });
    ((ModelIllager)func_177087_b()).hat.field_78806_j = true;
  }
  
  protected ResourceLocation getEntityTexture(EntityIllusioner entity) {
    if (entity.getDisguiseID() > 0 && entity.getDisguiseTime() > 0) {
      switch (entity.getDisguiseID()) {
        case 2:
          return DISGUISE_VINDICATOR;
        case 3:
          return DISGUISE_EVOKER;
      } 
      return DISGUISE_VILLAGER;
    } 
    return ILLUSIONIST;
  }
  
  protected void preRenderCallback(EntityIllusioner entitylivingbaseIn, float partialTickTime) {
    float f = 0.9375F;
    GlStateManager.func_179152_a(f, f, f);
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
  
  public void doRender(EntityIllusioner entity, double x, double y, double z, float entityYaw, float partialTicks) {
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
  
  protected boolean isVisible(EntityIllusioner entity) {
    return (!entity.func_82150_aj() || this.field_188301_f || entity.getGhostTime() > 0);
  }
}
