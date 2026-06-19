package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDepthsGhoul;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.model.ModelDG;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.model.ModelDGArmor;
import net.minecraft.AgeOfMinecraft.renders.LayerArrowCustomSized;
import net.minecraft.AgeOfMinecraft.renders.LayerMobCape;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderDepthsGhoul extends RenderLiving<EntityDepthsGhoul> {
  private static final ResourceLocation peteResource = new ResourceLocation("abyssalcraft:textures/model/depths_ghoul_pete.png");
  
  private static final ResourceLocation wilsonResource = new ResourceLocation("abyssalcraft:textures/model/depths_ghoul_wilson.png");
  
  private static final ResourceLocation orangeResource = new ResourceLocation("abyssalcraft:textures/model/depths_ghoul_orange.png");
  
  private static final ResourceLocation ghoulResource = new ResourceLocation("abyssalcraft:textures/model/depths_ghoul.png");
  
  private static final ResourceLocation antighoulResource = new ResourceLocation("abyssalcraft:textures/model/anti/depths_ghoul.png");
  
  public RenderDepthsGhoul(RenderManager manager) {
    this(manager, new ModelDG());
  }
  
  public RenderDepthsGhoul(RenderManager manager, ModelDG model) {
    super(manager, model, 0.8F);
    addLayer(new LayerGhoulHeldItem(this));
    addLayer((LayerRenderer)new LayerGhoulArmor(this));
    addLayer((LayerRenderer)new LayerCustomHead(model.Head));
    addLayer((LayerRenderer)new LayerArrowCustomSized(this, 1.0F));
    
    addLayer((LayerRenderer)new LayerMobCape(this));
  }
  
  protected ResourceLocation getEntityTexture(EntityDepthsGhoul par1EntityLiving) {
    if (par1EntityLiving.isAntiMob())
      return antighoulResource; 
    switch (par1EntityLiving.getGhoulType()) {
      case 0:
        return ghoulResource;
      case 1:
        return peteResource;
      case 2:
        return wilsonResource;
      case 3:
        return orangeResource;
    } 
    return ghoulResource;
  }
  
  protected void preRenderCallback(EntityDepthsGhoul entitylivingbaseIn, float partialTickTime) {
    ((ModelDG)getMainModel()).isSneak = entitylivingbaseIn.isSneaking();
    if (entitylivingbaseIn.isSneaking()) {
      GlStateManager.rotate(30.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.translate(0.0F, 0.25F, 0.5F);
    } 
    float fit = entitylivingbaseIn.getFittness();
    GlStateManager.scale(fit, fit, fit);
    if (entitylivingbaseIn.isHero())
      GlStateManager.scale(1.05F, 1.05F, 1.05F); 
    if (!entitylivingbaseIn.onGround)
      GlStateManager.rotate(entitylivingbaseIn.prevRotationPitchFalling + (entitylivingbaseIn.rotationPitchFalling - entitylivingbaseIn.prevRotationPitchFalling) * 2.0F - 1.0F, 1.0F, 0.0F, 0.0F); 
    if (entitylivingbaseIn.ticksExisted <= 21 && entitylivingbaseIn.ticksExisted > 0) {
      float f5 = (entitylivingbaseIn.ticksExisted + partialTickTime - 1.0F) / 20.0F * 1.6F;
      f5 = MathHelper.sqrt(f5);
      if (f5 > 1.0F)
        f5 = 1.0F; 
      GlStateManager.scale(f5, f5, f5);
      GlStateManager.rotate(f5 * 90.0F - 90.0F, f5, f5, f5);
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public class LayerGhoulHeldItem implements LayerRenderer<EntityLivingBase> {
    private final RenderLivingBase<?> livingEntityRenderer;
    
    public LayerGhoulHeldItem(RenderLivingBase<?> livingEntityRendererIn) {
      this.livingEntityRenderer = livingEntityRendererIn;
    }
    
    public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      boolean flag = (entitylivingbaseIn.getPrimaryHand() == EnumHandSide.RIGHT);
      ItemStack itemstack = flag ? entitylivingbaseIn.getHeldItemOffhand() : entitylivingbaseIn.getHeldItemMainhand();
      ItemStack itemstack1 = flag ? entitylivingbaseIn.getHeldItemMainhand() : entitylivingbaseIn.getHeldItemOffhand();
      if (itemstack != null || itemstack1 != null) {
        GlStateManager.pushMatrix();
        if ((this.livingEntityRenderer.getMainModel()).isChild) {
          float f = 0.5F;
          GlStateManager.translate(0.0F, 0.625F, 0.0F);
          GlStateManager.rotate(-20.0F, -1.0F, 0.0F, 0.0F);
          GlStateManager.scale(f, f, f);
        } 
        renderHeldItem(entitylivingbaseIn, itemstack1, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
        renderHeldItem(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
        GlStateManager.popMatrix();
      } 
    }
    
    private void renderHeldItem(EntityLivingBase p_188358_1_, ItemStack p_188358_2_, ItemCameraTransforms.TransformType p_188358_3_, EnumHandSide handSide) {
      if (p_188358_2_ != null) {
        GlStateManager.pushMatrix();
        ((ModelDG)this.livingEntityRenderer.getMainModel()).postRenderArm(0.0625F, handSide);
        if (!(this.livingEntityRenderer.getMainModel()).isChild) {
          GlStateManager.translate(-0.08F, 0.55F, -0.16F);
          GlStateManager.rotate(-45.0F, 1.0F, 0.0F, 0.0F);
        } else {
          GlStateManager.translate(-0.05F, 0.65F, 0.0F);
          GlStateManager.rotate(-70.0F, 1.0F, 0.0F, 0.0F);
        } 
        GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        boolean flag = (handSide == EnumHandSide.LEFT);
        GlStateManager.translate(flag ? -0.0625F : 0.0625F, 0.125F, -0.625F);
        Minecraft.getMinecraft().getItemRenderer().renderItemSide(p_188358_1_, p_188358_2_, p_188358_3_, flag);
        GlStateManager.popMatrix();
      } 
    }
    
    public boolean shouldCombineTextures() {
      return false;
    }
  }
  
  @SideOnly(Side.CLIENT)
  public class LayerGhoulArmor extends LayerArmorBase<ModelDGArmor> {
    private final ResourceLocation MISSING_ARMOR = new ResourceLocation("abyssalcraft:textures/armor/ghoul/missing_1.png");
    
    private final ResourceLocation MISSING_LEGGINGS = new ResourceLocation("abyssalcraft:textures/armor/ghoul/missing_2.png");
    
    public LayerGhoulArmor(RenderLivingBase<?> rendererIn) {
      super(rendererIn);
    }
    
    protected void initArmor() {
      this.modelLeggings = new ModelDGArmor(0.5F);
      this.modelArmor = new ModelDGArmor(1.0F);
    }
    
    protected void setModelSlotVisible(ModelDGArmor model, EntityEquipmentSlot slot) {
      setModelVisible(model);
      switch (slot) {
        case FEET:
          model.rleg.showModel = true;
          model.lleg.showModel = true;
          break;
        case LEGS:
          model.chestplate.showModel = true;
          model.pelvis.showModel = true;
          model.rleg.showModel = true;
          model.lleg.showModel = true;
          break;
        case CHEST:
          model.chestplate.showModel = true;
          model.Spine3.showModel = true;
          model.rarm1.showModel = true;
          model.larm1.showModel = true;
          break;
        case HEAD:
          model.Head.showModel = true;
          model.jaw.showModel = true;
          break;
      } 
    }
    
    protected void setModelVisible(ModelDGArmor model) {
      model.setInvisible(false);
    }
    
    protected ModelDGArmor getArmorModelHook(EntityLivingBase entity, ItemStack itemStack, EntityEquipmentSlot slot, ModelDGArmor model) {
      return model;
    }
    
    public ResourceLocation getArmorResource(Entity entity, ItemStack stack, EntityEquipmentSlot slot, String type) {
      ResourceLocation res = null;
      switch (slot) {
        case HEAD:
          res = AbyssalCraftAPI.getGhoulHelmetTexture(stack.getItem());
          if (type != null && type.equals("overlay") && res != null) {
            String domain = res.getNamespace();
            String path = res.getPath();
            res = new ResourceLocation(domain, path.substring(0, path.length() - 4).concat("_overlay.png"));
          } 
          if (res == null)
            res = this.MISSING_ARMOR; 
          return res;
        case CHEST:
          res = AbyssalCraftAPI.getGhoulChestplateTexture(stack.getItem());
          if (type != null && type.equals("overlay") && res != null) {
            String domain = res.getNamespace();
            String path = res.getPath();
            res = new ResourceLocation(domain, path.substring(0, path.length() - 4).concat("_overlay.png"));
          } 
          if (res == null)
            res = this.MISSING_ARMOR; 
          return res;
        case LEGS:
          res = AbyssalCraftAPI.getGhoulLeggingsTexture(stack.getItem());
          if (type != null && type.equals("overlay") && res != null) {
            String domain = res.getNamespace();
            String path = res.getPath();
            res = new ResourceLocation(domain, path.substring(0, path.length() - 4).concat("_overlay.png"));
          } 
          if (res == null)
            res = this.MISSING_LEGGINGS; 
          return res;
        case FEET:
          res = AbyssalCraftAPI.getGhoulBootsTexture(stack.getItem());
          if (type != null && type.equals("overlay") && res != null) {
            String domain = res.getNamespace();
            String path = res.getPath();
            res = new ResourceLocation(domain, path.substring(0, path.length() - 4).concat("_overlay.png"));
          } 
          if (res == null)
            res = this.MISSING_ARMOR; 
          return res;
      } 
      res = this.MISSING_ARMOR;
      return res;
    }
  }
}
