package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityWitch;
import net.minecraft.AgeOfMinecraft.models.ModelWitch;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerHeldItemWitch implements LayerRenderer<EntityLivingBase> {
  private final RenderWitch witchRenderer;
  
  public LayerHeldItemWitch(RenderWitch p_i46106_1_) {
    this.witchRenderer = p_i46106_1_;
  }
  
  public void func_177143_a(EntityWitch entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    ItemStack itemstack = entitylivingbaseIn.func_184614_ca();
    if (!itemstack.func_190926_b() && !EngenderConfig.mobs.useMobTalkerModels) {
      GlStateManager.func_179124_c(1.0F, 1.0F, 1.0F);
      GlStateManager.func_179094_E();
      ((ModelWitch)this.witchRenderer.func_177087_b()).field_78190_c.func_78794_c(0.0625F);
      GlStateManager.func_179109_b(-0.25F, 0.25F, -0.125F);
      GlStateManager.func_179114_b(-150.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179114_b(-15.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(-37.5F, 0.0F, 0.0F, 1.0F);
      Item item = itemstack.func_77973_b();
      Minecraft minecraft = Minecraft.func_71410_x();
      if (Block.func_149634_a(item).func_176223_P().func_185901_i() == EnumBlockRenderType.ENTITYBLOCK_ANIMATED) {
        GlStateManager.func_179109_b(0.0F, 0.0625F, -0.25F);
        GlStateManager.func_179114_b(30.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.func_179114_b(-5.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.func_179152_a(0.375F, -0.375F, 0.375F);
      } else if (item == Items.field_151031_f) {
        GlStateManager.func_179109_b(0.0F, 0.125F, -0.125F);
        GlStateManager.func_179114_b(-45.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.func_179152_a(0.625F, -0.625F, 0.625F);
        GlStateManager.func_179114_b(-100.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.func_179114_b(-20.0F, 0.0F, 1.0F, 0.0F);
      } else if (item.func_77662_d()) {
        if (item.func_77629_n_()) {
          GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
          GlStateManager.func_179109_b(0.0F, -0.0625F, 0.0F);
        } 
        this.witchRenderer.func_82422_c();
        GlStateManager.func_179109_b(0.0625F, -0.125F, 0.0F);
        GlStateManager.func_179152_a(0.625F, -0.625F, 0.625F);
        GlStateManager.func_179114_b(0.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.func_179114_b(0.0F, 0.0F, 1.0F, 0.0F);
      } else {
        GlStateManager.func_179109_b(0.1875F, 0.1875F, 0.0F);
        GlStateManager.func_179152_a(0.875F, 0.875F, 0.875F);
      } 
      GlStateManager.func_179114_b(-15.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179114_b(40.0F, 0.0F, 0.0F, 1.0F);
      minecraft.func_175597_ag().func_178099_a((EntityLivingBase)entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND);
      GlStateManager.func_179121_F();
    } 
  }
  
  public boolean func_177142_b() {
    return false;
  }
  
  public void func_177141_a(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
    func_177143_a((EntityWitch)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
  }
}
