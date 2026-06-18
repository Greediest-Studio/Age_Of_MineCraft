package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntitySheep;
import net.minecraft.AgeOfMinecraft.models.ModelSheep1;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerSheepWool implements LayerRenderer<EntitySheep> {
  private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
  
  private static final ResourceLocation antiTEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/anti/sheep_fur.png");
  
  private final RenderSheep sheepRenderer;
  
  private final ModelSheep1 sheepModel = new ModelSheep1();
  
  public LayerSheepWool(RenderSheep sheepRendererIn) {
    this.sheepRenderer = sheepRendererIn;
  }
  
  public void doRenderLayer(EntitySheep entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    if (!entitylivingbaseIn.getSheared() && !entitylivingbaseIn.func_82150_aj()) {
      this.sheepRenderer.func_110776_a(entitylivingbaseIn.isAntiMob() ? antiTEXTURE : TEXTURE);
      if (entitylivingbaseIn.func_145818_k_() && "jeb_".equals(entitylivingbaseIn.func_95999_t())) {
        int i = entitylivingbaseIn.field_70173_aa / 5 + entitylivingbaseIn.func_145782_y();
        int j = (EnumDyeColor.values()).length;
        int k = i % j;
        int l = (i + 1) % j;
        float f = ((entitylivingbaseIn.field_70173_aa % 5) + partialTicks) / 5.0F;
        float[] afloat1 = EntitySheep.getDyeRgb(EnumDyeColor.func_176764_b(k));
        float[] afloat2 = EntitySheep.getDyeRgb(EnumDyeColor.func_176764_b(l));
        GlStateManager.func_179124_c(afloat1[0] * (1.0F - f) + afloat2[0] * f, afloat1[1] * (1.0F - f) + afloat2[1] * f, afloat1[2] * (1.0F - f) + afloat2[2] * f);
      } else {
        float[] afloat = EntitySheep.getDyeRgb(entitylivingbaseIn.getFleeceColor());
        GlStateManager.func_179124_c(afloat[0], afloat[1], afloat[2]);
      } 
      this.sheepModel.func_178686_a(this.sheepRenderer.func_177087_b());
      this.sheepModel.func_78086_a((EntityLivingBase)entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
      this.sheepModel.func_78088_a((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    } 
  }
  
  public boolean func_177142_b() {
    return true;
  }
}
