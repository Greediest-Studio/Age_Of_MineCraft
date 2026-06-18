package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityRemnant;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.model.ModelRemnant;
import net.minecraft.AgeOfMinecraft.renders.LayerLearningBook;
import net.minecraft.AgeOfMinecraft.renders.LayerMobCape;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderRemnant extends RenderLiving<EntityRemnant> {
  private static ResourceLocation defaultTexture = new ResourceLocation("abyssalcraft:textures/model/remnant/remnant.png");
  
  private static final ResourceLocation remnantTexture = new ResourceLocation("abyssalcraft:textures/model/remnant/remnant.png");
  
  private static final ResourceLocation librarianTexture = new ResourceLocation("abyssalcraft:textures/model/remnant/remnant_librarian.png");
  
  private static final ResourceLocation priestTexture = new ResourceLocation("abyssalcraft:textures/model/remnant/remnant_priest.png");
  
  private static final ResourceLocation blacksmithTexture = new ResourceLocation("abyssalcraft:textures/model/remnant/remnant_blacksmith.png");
  
  private static final ResourceLocation butcherTexture = new ResourceLocation("abyssalcraft:textures/model/remnant/remnant_butcher.png");
  
  private static final ResourceLocation bankerTexture = new ResourceLocation("abyssalcraft:textures/model/remnant/remnant_banker.png");
  
  private static final ResourceLocation masterBlacksmithTexture = new ResourceLocation("abyssalcraft:textures/model/remnant/remnant_master_blacksmith.png");
  
  public RenderRemnant(RenderManager manager) {
    this(manager, new ModelRemnant());
  }
  
  public RenderRemnant(RenderManager manager, ModelRemnant model) {
    super(manager, (ModelBase)model, 0.5F);
    addLayer((LayerRenderer)new LayerCustomHead(model.head));
    addLayer((LayerRenderer)new LayerLearningBook(this));
    addLayer((LayerRenderer)new LayerMobCape((RenderLivingBase)this));
  }
  
  protected ResourceLocation getEntityTexture(EntityRemnant entity) {
    switch (entity.getProfession()) {
      case 0:
        defaultTexture = remnantTexture;
        break;
      case 1:
        defaultTexture = librarianTexture;
        break;
      case 2:
        defaultTexture = priestTexture;
        break;
      case 3:
        defaultTexture = blacksmithTexture;
        break;
      case 4:
        defaultTexture = butcherTexture;
        break;
      case 5:
        defaultTexture = bankerTexture;
        break;
      case 6:
        defaultTexture = masterBlacksmithTexture;
        break;
    } 
    return defaultTexture;
  }
  
  protected void preRenderCallback(EntityRemnant entitylivingbaseIn, float partialTickTime) {
    if (entitylivingbaseIn.isChild())
      GlStateManager.scale(0.5F, 0.5F, 0.5F); 
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
}
