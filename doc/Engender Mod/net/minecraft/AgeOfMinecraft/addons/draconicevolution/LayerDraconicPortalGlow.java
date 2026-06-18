package net.minecraft.AgeOfMinecraft.addons.draconicevolution;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerDraconicPortalGlow implements LayerRenderer<EntityLivingBase> {
  private static final ResourceLocation PORTAL_OVERLAY = new ResourceLocation("ageofminecraft", "textures/entities/portals/draconic_portal_glow.png");
  
  private static final ResourceLocation PORTAL_OVERLAY2 = new ResourceLocation("ageofminecraft", "textures/entities/portals/draconic_portal_glow2.png");
  
  private static final ResourceLocation PORTAL_OVERLAY3 = new ResourceLocation("ageofminecraft", "textures/entities/portals/draconic_portal_glow3.png");
  
  private static final ResourceLocation PORTAL_OVERLAY4 = new ResourceLocation("ageofminecraft", "textures/entities/portals/draconic_portal_glow4.png");
  
  private static final ResourceLocation PORTAL_OVERLAY5 = new ResourceLocation("ageofminecraft", "textures/entities/portals/draconic_portal_glow5.png");
  
  private final RenderDraconicPortal spiderRenderer;
  
  public LayerDraconicPortalGlow(RenderDraconicPortal p_i46109_1_) {
    this.spiderRenderer = p_i46109_1_;
  }
  
  public void func_177148_a(EntityDraconicPortal p_177148_1_, float p_177148_2_, float p_177148_3_, float p_177148_4_, float p_177148_5_, float p_177148_6_, float p_177148_7_, float p_177148_8_) {
    if (p_177148_1_.getMetaData() > 3) {
      this.spiderRenderer.func_110776_a(PORTAL_OVERLAY5);
    } else if (p_177148_1_.getMetaData() == 3) {
      this.spiderRenderer.func_110776_a(PORTAL_OVERLAY4);
    } else if (p_177148_1_.getMetaData() == 2) {
      this.spiderRenderer.func_110776_a(PORTAL_OVERLAY3);
    } else if (p_177148_1_.getMetaData() == 1) {
      this.spiderRenderer.func_110776_a(PORTAL_OVERLAY2);
    } else {
      this.spiderRenderer.func_110776_a(PORTAL_OVERLAY);
    } 
    GlStateManager.func_179147_l();
    GlStateManager.func_179118_c();
    GlStateManager.func_179112_b(1, 1);
    if (p_177148_1_.func_82150_aj()) {
      GlStateManager.func_179132_a(false);
    } else {
      GlStateManager.func_179132_a(true);
    } 
    int c0 = 15728880;
    int i = c0 % 65536;
    int j = c0 / 65536;
    OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, i / 1.0F, j / 1.0F);
    GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
    this.spiderRenderer.func_177087_b().func_78088_a((Entity)p_177148_1_, p_177148_2_, p_177148_3_, p_177148_5_, p_177148_6_, p_177148_7_, p_177148_8_);
    this.spiderRenderer.func_177105_a((EntityLiving)p_177148_1_);
    GlStateManager.func_179084_k();
    GlStateManager.func_179141_d();
  }
  
  public boolean func_177142_b() {
    return false;
  }
  
  public void func_177141_a(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
    func_177148_a((EntityDraconicPortal)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
  }
}
