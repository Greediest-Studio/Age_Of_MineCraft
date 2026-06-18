package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.render;

import chumbanotz.mutantbeasts.MutantBeasts;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityEndersoulFragment;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.model.ModelEndersoulFragment;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEndersoulFragment extends Render<EntityEndersoulFragment> {
  private static final ResourceLocation TEXTURE = MutantBeasts.getEntityTexture("endersoul_fragment");
  
  private final ModelEndersoulFragment modelRod = new ModelEndersoulFragment();
  
  public RenderEndersoulFragment(RenderManager renderManager) {
    super(renderManager);
    this.field_76989_e = 0.3F;
    this.field_76987_f = 0.5F;
  }
  
  public void doRender(EntityEndersoulFragment entity, double x, double y, double z, float entityYaw, float partialTicks) {
    super.func_76986_a((Entity)entity, x, y, z, entityYaw, partialTicks);
    GlStateManager.func_179094_E();
    GlStateManager.func_179109_b((float)x, (float)y - 1.9F, (float)z);
    GlStateManager.func_179152_a(1.6F, 1.6F, 1.6F);
    func_180548_c((Entity)entity);
    if (this.field_188301_f) {
      GlStateManager.func_179142_g();
      GlStateManager.func_187431_e(func_188298_c((Entity)entity));
    } 
    GlStateManager.func_179140_f();
    GlStateManager.func_179128_n(5890);
    GlStateManager.func_179096_D();
    float add = (entity.field_70173_aa + partialTicks) * 0.008F;
    GlStateManager.func_179109_b(add, add, 0.0F);
    GlStateManager.func_179128_n(5888);
    GlStateManager.func_179108_z();
    GlStateManager.func_179147_l();
    GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    int var5 = 61680;
    int var6 = var5 % 65536;
    int var7 = var5 / 65536;
    OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, var6, var7);
    GlStateManager.func_179131_c(0.9F, 0.3F, 1.0F, 1.0F);
    GlStateManager.func_179145_e();
    this.modelRod.render(entity);
    if (this.field_188301_f) {
      GlStateManager.func_187417_n();
      GlStateManager.func_179119_h();
    } 
    GlStateManager.func_179128_n(5890);
    GlStateManager.func_179096_D();
    GlStateManager.func_179128_n(5888);
    GlStateManager.func_179084_k();
    GlStateManager.func_179121_F();
  }
  
  protected ResourceLocation getEntityTexture(EntityEndersoulFragment entity) {
    return TEXTURE;
  }
}
