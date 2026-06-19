package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity;

import net.minecraft.AgeOfMinecraft.renders.RenderLayerCompat;

import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACLib;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntitySacthoth;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.model.ModelSacthoth;
import net.minecraft.AgeOfMinecraft.renders.LayerMobCape;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSacthoth extends RenderLiving<EntitySacthoth> {
  private static final ResourceLocation mobTexture = new ResourceLocation("abyssalcraft:textures/model/boss/sacthoth.png");
  
  public RenderSacthoth(RenderManager manager) {
    super(manager, new ModelSacthoth(), 0.0F);
    RenderLayerCompat.addLayer(this, new LayerHeldItemSacthoth(this));
    
    RenderLayerCompat.addLayer(this, (LayerRenderer)new LayerMobCape(this));
  }
  
  protected void renderModel(EntitySacthoth entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
    if (entitylivingbaseIn.dimension != ACLib.dark_realm_id)
      GlStateManager.color(1.0F, 1.0F, 1.0F, Math.max(entitylivingbaseIn.getBrightness(), 0.15F)); 
    GlStateManager.enableBlend();
    GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    super.renderModel(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
    GlStateManager.disableBlend();
  }
  
  protected void preRenderCallback(EntitySacthoth entitylivingbaseIn, float partialTickTime) {
    float fit = entitylivingbaseIn.getFittness();
    GlStateManager.scale(fit, fit, fit);
    if (entitylivingbaseIn.isHero())
      GlStateManager.scale(1.05F, 1.05F, 1.05F); 
  }
  
  protected ResourceLocation getEntityTexture(EntitySacthoth entity) {
    return mobTexture;
  }
  
  @SideOnly(Side.CLIENT)
  public class LayerHeldItemSacthoth implements LayerRenderer<EntityLivingBase> {
    protected final RenderLivingBase<?> livingEntityRenderer;
    
    public LayerHeldItemSacthoth(RenderLivingBase<?> livingEntityRendererIn) {
      this.livingEntityRenderer = livingEntityRendererIn;
    }
    
    public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      ItemStack itemstack = new ItemStack(ACItems.sacthoths_soul_harvesting_blade);
      if (!entitylivingbaseIn.isInvisible() && entitylivingbaseIn.isEntityAlive()) {
        GlStateManager.pushMatrix();
        renderHeldItem(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
        GlStateManager.popMatrix();
      } 
    }
    
    private void renderHeldItem(EntityLivingBase p_188358_1_, ItemStack p_188358_2_, ItemCameraTransforms.TransformType p_188358_3_, EnumHandSide handSide) {
      if (!p_188358_2_.isEmpty()) {
        GlStateManager.pushMatrix();
        ((ModelSacthoth)RenderLayerCompat.getMainModel(this.livingEntityRenderer)).leftarm1.postRender(0.0625F);
        GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(1.2F, 1.2F, 1.2F);
        GlStateManager.translate(-0.125F, 0.325F, 0.825F);
        Minecraft.getMinecraft().getItemRenderer().renderItemSide(p_188358_1_, p_188358_2_, p_188358_3_, false);
        GlStateManager.popMatrix();
      } 
    }
    
    protected void translateToHand(EnumHandSide p_191361_1_) {
      ((ModelBiped)RenderLayerCompat.getMainModel(this.livingEntityRenderer)).postRenderArm(0.0625F, p_191361_1_);
    }
    
    public boolean shouldCombineTextures() {
      return false;
    }
  }
}
