package net.minecraft.AgeOfMinecraft.renders;

import java.util.Random;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityShulker;
import net.minecraft.AgeOfMinecraft.models.ModelShulker;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderShulker extends RenderLiving<EntityShulker> {
  public static final ResourceLocation[] SHULKER_ENDERGOLEM_TEXTURE = new ResourceLocation[] { 
      new ResourceLocation("textures/entity/shulker/shulker_white.png"), new ResourceLocation("textures/entity/shulker/shulker_orange.png"), new ResourceLocation("textures/entity/shulker/shulker_magenta.png"), new ResourceLocation("textures/entity/shulker/shulker_light_blue.png"), new ResourceLocation("textures/entity/shulker/shulker_yellow.png"), new ResourceLocation("textures/entity/shulker/shulker_lime.png"), new ResourceLocation("textures/entity/shulker/shulker_pink.png"), new ResourceLocation("textures/entity/shulker/shulker_gray.png"), new ResourceLocation("textures/entity/shulker/shulker_silver.png"), new ResourceLocation("textures/entity/shulker/shulker_cyan.png"), 
      new ResourceLocation("textures/entity/shulker/shulker_purple.png"), new ResourceLocation("textures/entity/shulker/shulker_blue.png"), new ResourceLocation("textures/entity/shulker/shulker_brown.png"), new ResourceLocation("textures/entity/shulker/shulker_green.png"), new ResourceLocation("textures/entity/shulker/shulker_red.png"), new ResourceLocation("textures/entity/shulker/shulker_black.png") };
  
  private static final ResourceLocation antiSHULKER_ENDERGOLEM_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/anti/shulker_purple.png");
  
  public RenderShulker(RenderManager p_i46550_1_) {
    super(p_i46550_1_, (ModelBase)new ModelShulker(), 0.75F);
    func_177094_a(new HeadLayer());
    func_177094_a(new LayerArrowCustomSized((RenderLivingBase<?>)this, 1.0F));
    func_177094_a(new LayerCustomHeadEngender(((ModelShulker)this.field_77045_g).head, ((ModelShulker)this.field_77045_g).head));
    func_177094_a(new LayerLearningBook(this));
  }
  
  public void doRender(EntityShulker entity, double x, double y, double z, float entityYaw, float partialTicks) {
    if (entity.field_70725_aQ > 0) {
      double d0 = 0.05D;
      Random rnd = new Random();
      x += rnd.nextGaussian() * d0;
      z += rnd.nextGaussian() * d0;
    } 
    int i = entity.getClientTeleportInterp();
    if (i > 0 && entity.isAttachedToBlock()) {
      BlockPos blockpos = entity.getAttachmentPos();
      BlockPos blockpos1 = entity.getOldAttachPos();
      double d0 = (i - partialTicks) / 6.0D;
      d0 *= d0;
      double d1 = (blockpos.func_177958_n() - blockpos1.func_177958_n()) * d0;
      double d2 = (blockpos.func_177956_o() - blockpos1.func_177956_o()) * d0;
      double d3 = (blockpos.func_177952_p() - blockpos1.func_177952_p()) * d0;
      super.func_76986_a((EntityLiving)entity, x - d1, y - d2, z - d3, entityYaw, partialTicks);
    } else {
      super.func_76986_a((EntityLiving)entity, x, y, z, entityYaw, partialTicks);
    } 
  }
  
  public boolean shouldRender(EntityShulker livingEntity, ICamera camera, double camX, double camY, double camZ) {
    if (super.func_177071_a((EntityLiving)livingEntity, camera, camX, camY, camZ))
      return true; 
    if (livingEntity.getClientTeleportInterp() > 0 && livingEntity.isAttachedToBlock()) {
      BlockPos blockpos = livingEntity.getOldAttachPos();
      BlockPos blockpos1 = livingEntity.getAttachmentPos();
      Vec3d vec3d = new Vec3d(blockpos1.func_177958_n(), blockpos1.func_177956_o(), blockpos1.func_177952_p());
      Vec3d vec3d1 = new Vec3d(blockpos.func_177958_n(), blockpos.func_177956_o(), blockpos.func_177952_p());
      if (camera.func_78546_a(new AxisAlignedBB(vec3d1.field_72450_a, vec3d1.field_72448_b, vec3d1.field_72449_c, vec3d.field_72450_a, vec3d.field_72448_b, vec3d.field_72449_c)))
        return true; 
    } 
    return false;
  }
  
  protected ResourceLocation getEntityTexture(EntityShulker entity) {
    return entity.isAntiMob() ? antiSHULKER_ENDERGOLEM_TEXTURE : SHULKER_ENDERGOLEM_TEXTURE[entity.getColor().func_176765_a()];
  }
  
  protected void applyRotations(EntityShulker entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
    super.func_77043_a((EntityLivingBase)entityLiving, p_77043_2_, p_77043_3_, partialTicks);
    switch (entityLiving.getAttachmentFacing()) {
      default:
        return;
      case EAST:
        GlStateManager.func_179109_b(0.5F, 0.5F, 0.0F);
        GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.func_179114_b(90.0F, 0.0F, 0.0F, 1.0F);
      case WEST:
        GlStateManager.func_179109_b(-0.5F, 0.5F, 0.0F);
        GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.func_179114_b(-90.0F, 0.0F, 0.0F, 1.0F);
      case NORTH:
        GlStateManager.func_179109_b(0.0F, 0.5F, -0.5F);
        GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
      case SOUTH:
        GlStateManager.func_179109_b(0.0F, 0.5F, 0.5F);
        GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
      case UP:
        break;
    } 
    GlStateManager.func_179109_b(0.0F, 1.0F, 0.0F);
    GlStateManager.func_179114_b(180.0F, 1.0F, 0.0F, 0.0F);
  }
  
  protected void preRenderCallback(EntityShulker entitylivingbaseIn, float partialTickTime) {
    if (entitylivingbaseIn.isHero())
      GlStateManager.func_179152_a(1.05F, 1.05F, 1.05F); 
    if (entitylivingbaseIn.func_70631_g_())
      GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F); 
    if (entitylivingbaseIn.func_184218_aH())
      GlStateManager.func_179109_b(0.0F, -0.325F, 0.0F); 
    float fit = entitylivingbaseIn.getFittness();
    GlStateManager.func_179152_a(fit, fit, fit);
    if (entitylivingbaseIn.field_70173_aa <= 21 && entitylivingbaseIn.field_70173_aa > 0) {
      float f5 = (entitylivingbaseIn.field_70173_aa + partialTickTime - 1.0F) / 20.0F;
      f5 = MathHelper.func_76129_c(f5);
      if (f5 > 1.0F)
        f5 = 1.0F; 
      GlStateManager.func_179152_a(1.0F, f5, 1.0F);
    } 
  }
  
  @SideOnly(Side.CLIENT)
  class HeadLayer implements LayerRenderer<EntityShulker> {
    private HeadLayer() {}
    
    public void doRenderLayer(EntityShulker entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      if (!entitylivingbaseIn.func_82150_aj())
        if (entitylivingbaseIn.func_70631_g_()) {
          GlStateManager.func_179094_E();
          ModelRenderer modelrenderer = ((ModelShulker)RenderShulker.this.func_177087_b()).head;
          modelrenderer.field_78796_g = netHeadYaw * 0.017453292F;
          modelrenderer.field_78795_f = headPitch * 0.017453292F;
          RenderShulker.this.func_110776_a(entitylivingbaseIn.isAntiMob() ? RenderShulker.antiSHULKER_ENDERGOLEM_TEXTURE : RenderShulker.SHULKER_ENDERGOLEM_TEXTURE[entitylivingbaseIn.getColor().func_176765_a()]);
          float f6 = 2.0F;
          GlStateManager.func_179152_a(1.5F / f6, 1.5F / f6, 1.5F / f6);
          GlStateManager.func_179109_b(0.0F, 16.0F * scale + 0.3F, 0.0F);
          GlStateManager.func_179121_F();
          GlStateManager.func_179094_E();
          GlStateManager.func_179152_a(1.0F / f6, 1.0F / f6, 1.0F / f6);
          GlStateManager.func_179109_b(0.0F, 24.0F * scale, 0.0F);
          switch (entitylivingbaseIn.getAttachmentFacing()) {
            case EAST:
              GlStateManager.func_179114_b(90.0F, 0.0F, 0.0F, 1.0F);
              GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
              GlStateManager.func_179109_b(1.0F, -1.0F, 0.0F);
              GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
              break;
            case WEST:
              GlStateManager.func_179114_b(-90.0F, 0.0F, 0.0F, 1.0F);
              GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
              GlStateManager.func_179109_b(-1.0F, -1.0F, 0.0F);
              GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
              break;
            case NORTH:
              GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
              GlStateManager.func_179109_b(0.0F, -1.0F, -1.0F);
              break;
            case SOUTH:
              GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
              GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
              GlStateManager.func_179109_b(0.0F, -1.0F, 1.0F);
              break;
            case UP:
              GlStateManager.func_179114_b(180.0F, 1.0F, 0.0F, 0.0F);
              GlStateManager.func_179109_b(0.0F, -2.0F, 0.0F);
              break;
          } 
          modelrenderer.func_78785_a(scale);
          GlStateManager.func_179121_F();
        } else {
          GlStateManager.func_179094_E();
          switch (entitylivingbaseIn.getAttachmentFacing()) {
            case EAST:
              GlStateManager.func_179114_b(90.0F, 0.0F, 0.0F, 1.0F);
              GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
              GlStateManager.func_179109_b(1.0F, -1.0F, 0.0F);
              GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
              break;
            case WEST:
              GlStateManager.func_179114_b(-90.0F, 0.0F, 0.0F, 1.0F);
              GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
              GlStateManager.func_179109_b(-1.0F, -1.0F, 0.0F);
              GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
              break;
            case NORTH:
              GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
              GlStateManager.func_179109_b(0.0F, -1.0F, -1.0F);
              break;
            case SOUTH:
              GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
              GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
              GlStateManager.func_179109_b(0.0F, -1.0F, 1.0F);
              break;
            case UP:
              GlStateManager.func_179114_b(180.0F, 1.0F, 0.0F, 0.0F);
              GlStateManager.func_179109_b(0.0F, -2.0F, 0.0F);
              break;
          } 
          ModelRenderer modelrenderer = ((ModelShulker)RenderShulker.this.func_177087_b()).head;
          modelrenderer.field_78796_g = netHeadYaw * 0.017453292F;
          modelrenderer.field_78795_f = headPitch * 0.017453292F;
          RenderShulker.this.func_110776_a(entitylivingbaseIn.isAntiMob() ? RenderShulker.antiSHULKER_ENDERGOLEM_TEXTURE : RenderShulker.SHULKER_ENDERGOLEM_TEXTURE[entitylivingbaseIn.getColor().func_176765_a()]);
          modelrenderer.func_78785_a(scale);
          GlStateManager.func_179121_F();
        }  
    }
    
    public boolean func_177142_b() {
      return true;
    }
  }
}
