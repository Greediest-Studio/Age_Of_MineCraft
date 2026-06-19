package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.entity.tame.cameos.EntitySans;
import net.minecraft.AgeOfMinecraft.models.ModelSans;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSans extends RenderLiving<EntitySans> {
  public RenderSans(RenderManager p_i46128_1_) {
    super(p_i46128_1_, (ModelBase)new ModelSans(), 0.5F);
    addLayer(new LayerLearningBook(this));
  }
  
  protected ResourceLocation getEntityTexture(EntitySans entity) {
    return (entity.getEyeType() == 4) ? new ResourceLocation("ageofminecraft", "textures/entities/sans_eyes_closed.png") : ((entity.getEyeType() == 3) ? new ResourceLocation("ageofminecraft", "textures/entities/sans_wink.png") : new ResourceLocation("ageofminecraft", "textures/entities/sans.png"));
  }
  
  protected void applyRotations(EntitySans entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
    GlStateManager.rotate(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
    if (entityLiving.deathTime > 0) {
      float f = (entityLiving.deathTime + partialTicks - 1.0F) / 20.0F * 1.6F;
      f = MathHelper.sqrt(f);
      if (f > 1.0F)
        f = 1.0F; 
      GlStateManager.rotate(f * getDeathMaxRotation(entityLiving), 0.0F, 0.0F, 1.0F);
      GlStateManager.translate(0.0F, -f * 0.25F, 0.0F);
    } else {
      String s = TextFormatting.getTextWithoutFormattingCodes(entityLiving.getName());
      if (s != null && ("Dinnerbone".equals(s) || "Grumm".equals(s))) {
        GlStateManager.translate(0.0F, entityLiving.height + 0.1F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
      } 
    } 
  }
}
