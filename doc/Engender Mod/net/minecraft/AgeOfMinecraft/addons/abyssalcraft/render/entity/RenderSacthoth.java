package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity;

import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACLib;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntitySacthoth;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.model.ModelSacthoth;
import net.minecraft.AgeOfMinecraft.renders.LayerLearningBook;
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
import net.minecraft.entity.Entity;
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
    super(manager, (ModelBase)new ModelSacthoth(), 0.0F);
    func_177094_a(new LayerHeldItemSacthoth((RenderLivingBase<?>)this));
    func_177094_a((LayerRenderer)new LayerLearningBook(this));
    func_177094_a((LayerRenderer)new LayerMobCape((RenderLivingBase)this));
  }
  
  protected void renderModel(EntitySacthoth entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
    if (entitylivingbaseIn.field_71093_bK != ACLib.dark_realm_id)
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, Math.max(entitylivingbaseIn.func_70013_c(), 0.15F)); 
    GlStateManager.func_179147_l();
    GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    super.func_77036_a((EntityLivingBase)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
    GlStateManager.func_179084_k();
  }
  
  protected void preRenderCallback(EntitySacthoth entitylivingbaseIn, float partialTickTime) {
    float fit = entitylivingbaseIn.getFittness();
    GlStateManager.func_179152_a(fit, fit, fit);
    if (entitylivingbaseIn.isHero())
      GlStateManager.func_179152_a(1.05F, 1.05F, 1.05F); 
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
    
    public void func_177141_a(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      ItemStack itemstack = new ItemStack(ACItems.sacthoths_soul_harvesting_blade);
      if (!entitylivingbaseIn.func_82150_aj() && entitylivingbaseIn.func_70089_S()) {
        GlStateManager.func_179094_E();
        renderHeldItem(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
        GlStateManager.func_179121_F();
      } 
    }
    
    private void renderHeldItem(EntityLivingBase p_188358_1_, ItemStack p_188358_2_, ItemCameraTransforms.TransformType p_188358_3_, EnumHandSide handSide) {
      if (!p_188358_2_.func_190926_b()) {
        GlStateManager.func_179094_E();
        ((ModelSacthoth)this.livingEntityRenderer.func_177087_b()).leftarm1.func_78794_c(0.0625F);
        GlStateManager.func_179114_b(-90.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.func_179152_a(1.2F, 1.2F, 1.2F);
        GlStateManager.func_179109_b(-0.125F, 0.325F, 0.825F);
        Minecraft.func_71410_x().func_175597_ag().func_187462_a(p_188358_1_, p_188358_2_, p_188358_3_, false);
        GlStateManager.func_179121_F();
      } 
    }
    
    protected void translateToHand(EnumHandSide p_191361_1_) {
      ((ModelBiped)this.livingEntityRenderer.func_177087_b()).func_187073_a(0.0625F, p_191361_1_);
    }
    
    public boolean func_177142_b() {
      return false;
    }
  }
}
