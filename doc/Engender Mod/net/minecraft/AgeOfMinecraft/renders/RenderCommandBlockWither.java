package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityCommandBlockWither;
import net.minecraft.AgeOfMinecraft.models.ModelCommandBlockWither;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCommandBlockWither extends RenderLiving<EntityCommandBlockWither> {
  public static final ResourceLocation enderDragonCrystalBeamTextures = new ResourceLocation("textures/entity/endercrystal/endercrystal_beam.png");
  
  private static final ResourceLocation witherTextures = new ResourceLocation("ageofminecraft", "textures/entities/command_block_wither.png");
  
  private static final ResourceLocation witherStormTextures = new ResourceLocation("ageofminecraft", "textures/entities/command_block_wither_cycloptic.png");
  
  public RenderCommandBlockWither(RenderManager renderManagerIn) {
    super(renderManagerIn, (ModelBase)new ModelCommandBlockWither(), 1.0F);
    func_177094_a(new LayerArrowCustomSized((RenderLivingBase<?>)this, 0.5F));
    func_177094_a(new LayerWitherBody(this));
    func_177094_a(new LayerWitherTractorBeam(this));
    func_177094_a(new LayerMobCape((RenderLivingBase<?>)this));
    func_177094_a(new LayerCommandBlock((RenderLivingBase<?>)this));
  }
  
  protected ResourceLocation getEntityTexture(EntityCommandBlockWither entity) {
    return (entity.getSize() >= 5000) ? witherStormTextures : witherTextures;
  }
  
  protected void applyRotations(EntityCommandBlockWither entitylivingbaseIn, float p_77043_2_, float p_77043_3_, float partialTicks) {
    if (!entitylivingbaseIn.field_70122_E && !entitylivingbaseIn.func_184207_aI() && !entitylivingbaseIn.func_82150_aj())
      GlStateManager.func_179109_b(0.0F, MathHelper.func_76134_b(p_77043_2_ * 0.2F) * 0.2F, 0.0F); 
    super.func_77043_a((EntityLivingBase)entitylivingbaseIn, p_77043_2_, p_77043_3_, partialTicks);
  }
  
  protected void preRenderCallback(EntityCommandBlockWither entitylivingbaseIn, float partialTickTime) {
    float f = 2.0F;
    GlStateManager.func_179152_a(f, f, f);
    if (entitylivingbaseIn.getSize() >= 1000)
      GlStateManager.func_179109_b(0.0F, 0.1F, -0.3F); 
    if (entitylivingbaseIn.func_70093_af())
      GlStateManager.func_179109_b(0.0F, 0.3F, 0.0F); 
  }
  
  public void doRender(EntityCommandBlockWither entity, double x, double y, double z, float entityYaw, float partialTicks) {
    super.func_76986_a((EntityLiving)entity, x, y, z, entityYaw, partialTicks);
  }
  
  public static void func_188325_a(double p_188325_0_, double p_188325_2_, double p_188325_4_, float p_188325_6_, double p_188325_7_, double p_188325_9_, double p_188325_11_, int p_188325_13_, double p_188325_14_, double p_188325_16_, double p_188325_18_) {
    float f = (float)(p_188325_14_ - p_188325_7_);
    float f1 = (float)(p_188325_16_ - 1.0D - p_188325_9_);
    float f2 = (float)(p_188325_18_ - p_188325_11_);
    float f3 = MathHelper.func_76129_c(f * f + f2 * f2);
    float f4 = MathHelper.func_76129_c(f * f + f1 * f1 + f2 * f2);
    GlStateManager.func_179094_E();
    GlStateManager.func_179109_b((float)p_188325_0_, (float)p_188325_2_ + 2.0F, (float)p_188325_4_);
    GlStateManager.func_179114_b((float)-Math.atan2(f2, f) * 57.295776F - 90.0F, 0.0F, 1.0F, 0.0F);
    GlStateManager.func_179114_b((float)-Math.atan2(f3, f1) * 57.295776F - 90.0F, 1.0F, 0.0F, 0.0F);
    Tessellator tessellator = Tessellator.func_178181_a();
    BufferBuilder vertexbuffer = tessellator.func_178180_c();
    RenderHelper.func_74518_a();
    GlStateManager.func_179129_p();
    GlStateManager.func_179103_j(7425);
    float f5 = 0.0F - (p_188325_13_ + p_188325_6_) * 0.01F;
    float f6 = MathHelper.func_76129_c(f * f + f1 * f1 + f2 * f2) / 32.0F - (p_188325_13_ + p_188325_6_) * 0.01F;
    vertexbuffer.func_181668_a(5, DefaultVertexFormats.field_181709_i);
    for (int j = 0; j <= 8; j++) {
      float f7 = MathHelper.func_76126_a((j % 8) * 6.2831855F / 8.0F) * 0.75F;
      float f8 = MathHelper.func_76134_b((j % 8) * 6.2831855F / 8.0F) * 0.75F;
      float f9 = (j % 8) / 8.0F;
      vertexbuffer.func_181662_b((f7 * 0.2F), (f8 * 0.2F), 0.0D).func_187315_a(f9, f5).func_181669_b(0, 0, 0, 255).func_181675_d();
      vertexbuffer.func_181662_b(f7, f8, f4).func_187315_a(f9, f6).func_181669_b(255, 255, 255, 255).func_181675_d();
    } 
    tessellator.func_78381_a();
    GlStateManager.func_179089_o();
    GlStateManager.func_179103_j(7424);
    RenderHelper.func_74519_b();
    GlStateManager.func_179121_F();
  }
  
  @SideOnly(Side.CLIENT)
  public class LayerCommandBlock implements LayerRenderer<EntityCommandBlockWither> {
    protected final RenderLivingBase<?> livingEntityRenderer;
    
    public LayerCommandBlock(RenderLivingBase<?> livingEntityRendererIn) {
      this.livingEntityRenderer = livingEntityRendererIn;
    }
    
    public void doRenderLayer(EntityCommandBlockWither entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      ItemStack itemstack = new ItemStack(Blocks.field_150483_bI);
      if (!entitylivingbaseIn.func_82150_aj() && entitylivingbaseIn.func_70089_S()) {
        GlStateManager.func_179094_E();
        renderHeldItem(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
        GlStateManager.func_179121_F();
      } 
    }
    
    private void renderHeldItem(EntityCommandBlockWither p_188358_1_, ItemStack p_188358_2_, ItemCameraTransforms.TransformType p_188358_3_, EnumHandSide handSide) {
      if (p_188358_1_.getSize() < 6000) {
        GlStateManager.func_179094_E();
        ((ModelCommandBlockWither)this.livingEntityRenderer.func_177087_b()).spine.func_78794_c(0.0625F);
        GlStateManager.func_179114_b(100.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.func_179114_b(-11.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.func_179114_b(-43.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.func_179152_a(1.4F, 1.4F, 1.4F);
        GlStateManager.func_179109_b(0.09F, -0.32F, -0.23F);
        Minecraft.func_71410_x().func_175597_ag().func_187462_a((EntityLivingBase)p_188358_1_, p_188358_2_, p_188358_3_, false);
        GlStateManager.func_179121_F();
      } 
    }
    
    protected void translateToHand(EnumHandSide p_191361_1_) {
      ((ModelBiped)this.livingEntityRenderer.func_177087_b()).func_187073_a(0.0625F, p_191361_1_);
    }
    
    public boolean func_177142_b() {
      return true;
    }
  }
}
