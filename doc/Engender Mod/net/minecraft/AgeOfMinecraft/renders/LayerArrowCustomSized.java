package net.minecraft.AgeOfMinecraft.renders;

import java.util.Random;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerArrowCustomSized implements LayerRenderer<EntityLivingBase> {
  private final RenderLivingBase<?> renderer;
  
  private float scale;
  
  public LayerArrowCustomSized(RenderLivingBase<?> rendererIn, float scale) {
    this.renderer = rendererIn;
    this.scale = scale;
  }
  
  public void func_177141_a(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    int i = entitylivingbaseIn.func_85035_bI();
    if (i > 0) {
      EntityTippedArrow entityTippedArrow = new EntityTippedArrow(entitylivingbaseIn.field_70170_p, entitylivingbaseIn.field_70165_t, entitylivingbaseIn.field_70163_u, entitylivingbaseIn.field_70161_v);
      Random random = new Random(entitylivingbaseIn.func_145782_y());
      RenderHelper.func_74518_a();
      for (int j = 0; j < i; j++) {
        GlStateManager.func_179094_E();
        ModelRenderer modelrenderer = this.renderer.func_177087_b().func_85181_a(random);
        ModelBox modelbox = modelrenderer.field_78804_l.get(random.nextInt(modelrenderer.field_78804_l.size()));
        modelrenderer.func_78794_c(0.0625F);
        float f = random.nextFloat();
        float f1 = random.nextFloat();
        float f2 = random.nextFloat();
        float f3 = (modelbox.field_78252_a + (modelbox.field_78248_d - modelbox.field_78252_a) * f) / 16.0F;
        float f4 = (modelbox.field_78250_b + (modelbox.field_78249_e - modelbox.field_78250_b) * f1) / 16.0F;
        float f5 = (modelbox.field_78251_c + (modelbox.field_78246_f - modelbox.field_78251_c) * f2) / 16.0F;
        GlStateManager.func_179109_b(f3, f4, f5);
        GlStateManager.func_179152_a(this.scale, this.scale, this.scale);
        f = f * 2.0F - 1.0F;
        f1 = f1 * 2.0F - 1.0F;
        f2 = f2 * 2.0F - 1.0F;
        f *= -1.0F;
        f1 *= -1.0F;
        f2 *= -1.0F;
        float f6 = MathHelper.func_76129_c(f * f + f2 * f2);
        ((Entity)entityTippedArrow).field_70126_B = ((Entity)entityTippedArrow).field_70177_z = (float)(Math.atan2(f, f2) * 57.29577951308232D);
        ((Entity)entityTippedArrow).field_70127_C = ((Entity)entityTippedArrow).field_70125_A = (float)(Math.atan2(f1, f6) * 57.29577951308232D);
        double d0 = 0.0D;
        double d1 = 0.0D;
        double d2 = 0.0D;
        this.renderer.func_177068_d().func_188391_a((Entity)entityTippedArrow, d0, d1, d2, 0.0F, partialTicks, false);
        GlStateManager.func_179121_F();
      } 
      RenderHelper.func_74519_b();
    } 
  }
  
  public boolean func_177142_b() {
    return false;
  }
}
