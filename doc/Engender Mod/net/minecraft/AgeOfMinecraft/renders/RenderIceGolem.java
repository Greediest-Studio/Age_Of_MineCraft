package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityIceGolem;
import net.minecraft.AgeOfMinecraft.models.ModelIceGolem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderIceGolem extends RenderLiving<EntityIceGolem> {
  private static final ResourceLocation iceGolemTextures = new ResourceLocation("ageofminecraft", "textures/entities/ice_golem.png");
  
  private static ModelIceGolem regularmodel = new ModelIceGolem();
  
  public RenderIceGolem(RenderManager p_i46133_1_) {
    super(p_i46133_1_, (ModelBase)regularmodel, 0.75F);
    func_177094_a(new LayerArrowCustomSized((RenderLivingBase<?>)this, 1.0F));
    func_177094_a(new LayerSnowmanHead(regularmodel.ironGolemHead));
    func_177094_a(new LayerLearningBook(this));
    func_177094_a(new LayerMobCape((RenderLivingBase<?>)this));
  }
  
  protected ResourceLocation getEntityTexture(EntityIceGolem entity) {
    return iceGolemTextures;
  }
  
  protected void preRenderCallback(EntityIceGolem entitylivingbaseIn, float partialTickTime) {
    if (entitylivingbaseIn.func_70631_g_())
      GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F); 
    if (entitylivingbaseIn.func_184218_aH())
      GlStateManager.func_179109_b(0.0F, -0.825F, 0.0F); 
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
  
  protected void applyRotations(EntityIceGolem entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
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
    if (entityLiving.field_70721_aZ >= 0.01D) {
      float f3 = 13.0F;
      float f4 = entityLiving.field_184619_aG - entityLiving.field_70721_aZ * (1.0F - partialTicks) + 6.0F;
      float f5 = (Math.abs(f4 % f3 - f3 * 0.5F) - f3 * 0.25F) / f3 * 0.25F;
      GlStateManager.func_179114_b(-7.0F * f5, 0.0F, 0.0F, 1.0F);
    } 
  }
  
  public void doRender(EntityIceGolem entity, double x, double y, double z, float entityYaw, float partialTicks) {
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
  
  protected boolean isVisible(EntityIceGolem entity) {
    return (!entity.func_82150_aj() || this.field_188301_f || entity.getGhostTime() > 0);
  }
  
  @SideOnly(Side.CLIENT)
  public class LayerSnowmanHead implements LayerRenderer<EntityIceGolem> {
    private final ModelRenderer snowManRenderer;
    
    public LayerSnowmanHead(ModelRenderer snowManRendererIn) {
      this.snowManRenderer = snowManRendererIn;
    }
    
    public void doRenderLayer(EntityIceGolem entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      if (!entitylivingbaseIn.func_82150_aj()) {
        GlStateManager.func_179094_E();
        this.snowManRenderer.func_78794_c(0.0625F);
        float f = 0.55F;
        GlStateManager.func_179109_b(0.0F, -0.25F, 0.0F);
        GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.func_179152_a(f, -f, -f);
        Minecraft.func_71410_x().func_175597_ag().func_178099_a((EntityLivingBase)entitylivingbaseIn, new ItemStack(Blocks.field_150423_aK, 1), ItemCameraTransforms.TransformType.HEAD);
        GlStateManager.func_179121_F();
      } 
    }
    
    public boolean func_177142_b() {
      return true;
    }
  }
}
