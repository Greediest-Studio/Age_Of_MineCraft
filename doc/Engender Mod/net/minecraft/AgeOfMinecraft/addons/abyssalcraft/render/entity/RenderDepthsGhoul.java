package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDepthsGhoul;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.model.ModelDG;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.model.ModelDGArmor;
import net.minecraft.AgeOfMinecraft.renders.LayerArrowCustomSized;
import net.minecraft.AgeOfMinecraft.renders.LayerLearningBook;
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
    super(manager, (ModelBase)model, 0.8F);
    func_177094_a(new LayerGhoulHeldItem((RenderLivingBase<?>)this));
    func_177094_a((LayerRenderer)new LayerGhoulArmor((RenderLivingBase<?>)this));
    func_177094_a((LayerRenderer)new LayerCustomHead(model.Head));
    func_177094_a((LayerRenderer)new LayerArrowCustomSized((RenderLivingBase)this, 1.0F));
    func_177094_a((LayerRenderer)new LayerLearningBook(this));
    func_177094_a((LayerRenderer)new LayerMobCape((RenderLivingBase)this));
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
    ((ModelDG)func_177087_b()).isSneak = entitylivingbaseIn.func_70093_af();
    if (entitylivingbaseIn.func_70093_af()) {
      GlStateManager.func_179114_b(30.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179109_b(0.0F, 0.25F, 0.5F);
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
  
  @SideOnly(Side.CLIENT)
  public class LayerGhoulHeldItem implements LayerRenderer<EntityLivingBase> {
    private final RenderLivingBase<?> livingEntityRenderer;
    
    public LayerGhoulHeldItem(RenderLivingBase<?> livingEntityRendererIn) {
      this.livingEntityRenderer = livingEntityRendererIn;
    }
    
    public void func_177141_a(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      boolean flag = (entitylivingbaseIn.func_184591_cq() == EnumHandSide.RIGHT);
      ItemStack itemstack = flag ? entitylivingbaseIn.func_184592_cb() : entitylivingbaseIn.func_184614_ca();
      ItemStack itemstack1 = flag ? entitylivingbaseIn.func_184614_ca() : entitylivingbaseIn.func_184592_cb();
      if (itemstack != null || itemstack1 != null) {
        GlStateManager.func_179094_E();
        if ((this.livingEntityRenderer.func_177087_b()).field_78091_s) {
          float f = 0.5F;
          GlStateManager.func_179109_b(0.0F, 0.625F, 0.0F);
          GlStateManager.func_179114_b(-20.0F, -1.0F, 0.0F, 0.0F);
          GlStateManager.func_179152_a(f, f, f);
        } 
        renderHeldItem(entitylivingbaseIn, itemstack1, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
        renderHeldItem(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
        GlStateManager.func_179121_F();
      } 
    }
    
    private void renderHeldItem(EntityLivingBase p_188358_1_, ItemStack p_188358_2_, ItemCameraTransforms.TransformType p_188358_3_, EnumHandSide handSide) {
      if (p_188358_2_ != null) {
        GlStateManager.func_179094_E();
        ((ModelDG)this.livingEntityRenderer.func_177087_b()).postRenderArm(0.0625F, handSide);
        if (!(this.livingEntityRenderer.func_177087_b()).field_78091_s) {
          GlStateManager.func_179109_b(-0.08F, 0.55F, -0.16F);
          GlStateManager.func_179114_b(-45.0F, 1.0F, 0.0F, 0.0F);
        } else {
          GlStateManager.func_179109_b(-0.05F, 0.65F, 0.0F);
          GlStateManager.func_179114_b(-70.0F, 1.0F, 0.0F, 0.0F);
        } 
        GlStateManager.func_179114_b(-90.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
        boolean flag = (handSide == EnumHandSide.LEFT);
        GlStateManager.func_179109_b(flag ? -0.0625F : 0.0625F, 0.125F, -0.625F);
        Minecraft.func_71410_x().func_175597_ag().func_187462_a(p_188358_1_, p_188358_2_, p_188358_3_, flag);
        GlStateManager.func_179121_F();
      } 
    }
    
    public boolean func_177142_b() {
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
    
    protected void func_177177_a() {
      this.field_177189_c = (ModelBase)new ModelDGArmor(0.5F);
      this.field_177186_d = (ModelBase)new ModelDGArmor(1.0F);
    }
    
    protected void setModelSlotVisible(ModelDGArmor model, EntityEquipmentSlot slot) {
      setModelVisible(model);
      switch (slot) {
        case FEET:
          model.rleg.field_78806_j = true;
          model.lleg.field_78806_j = true;
          break;
        case LEGS:
          model.chestplate.field_78806_j = true;
          model.pelvis.field_78806_j = true;
          model.rleg.field_78806_j = true;
          model.lleg.field_78806_j = true;
          break;
        case CHEST:
          model.chestplate.field_78806_j = true;
          model.Spine3.field_78806_j = true;
          model.rarm1.field_78806_j = true;
          model.larm1.field_78806_j = true;
          break;
        case HEAD:
          model.Head.field_78806_j = true;
          model.jaw.field_78806_j = true;
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
          res = AbyssalCraftAPI.getGhoulHelmetTexture(stack.func_77973_b());
          if (type != null && type.equals("overlay") && res != null) {
            String domain = res.func_110624_b();
            String path = res.func_110623_a();
            res = new ResourceLocation(domain, path.substring(0, path.length() - 4).concat("_overlay.png"));
          } 
          if (res == null)
            res = this.MISSING_ARMOR; 
          return res;
        case CHEST:
          res = AbyssalCraftAPI.getGhoulChestplateTexture(stack.func_77973_b());
          if (type != null && type.equals("overlay") && res != null) {
            String domain = res.func_110624_b();
            String path = res.func_110623_a();
            res = new ResourceLocation(domain, path.substring(0, path.length() - 4).concat("_overlay.png"));
          } 
          if (res == null)
            res = this.MISSING_ARMOR; 
          return res;
        case LEGS:
          res = AbyssalCraftAPI.getGhoulLeggingsTexture(stack.func_77973_b());
          if (type != null && type.equals("overlay") && res != null) {
            String domain = res.func_110624_b();
            String path = res.func_110623_a();
            res = new ResourceLocation(domain, path.substring(0, path.length() - 4).concat("_overlay.png"));
          } 
          if (res == null)
            res = this.MISSING_LEGGINGS; 
          return res;
        case FEET:
          res = AbyssalCraftAPI.getGhoulBootsTexture(stack.func_77973_b());
          if (type != null && type.equals("overlay") && res != null) {
            String domain = res.func_110624_b();
            String path = res.func_110623_a();
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
