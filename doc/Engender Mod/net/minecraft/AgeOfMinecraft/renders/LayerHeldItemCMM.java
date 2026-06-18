package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerHeldItemCMM implements LayerRenderer<EntityLivingBase> {
  public ModelRenderer modelRenderer1;
  
  public ModelRenderer modelRenderer2;
  
  public LayerHeldItemCMM(ModelRenderer hand1, ModelRenderer hand2) {
    this.modelRenderer1 = hand1;
    this.modelRenderer2 = hand2;
  }
  
  public void func_177141_a(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    if (this.modelRenderer1 != null && this.modelRenderer2 != null) {
      boolean flag = (entitylivingbaseIn.func_184591_cq() == EnumHandSide.RIGHT);
      ItemStack itemstack = flag ? entitylivingbaseIn.func_184592_cb() : entitylivingbaseIn.func_184614_ca();
      ItemStack itemstack1 = flag ? entitylivingbaseIn.func_184614_ca() : entitylivingbaseIn.func_184592_cb();
      if (!itemstack.func_190926_b() || !itemstack1.func_190926_b()) {
        GlStateManager.func_179094_E();
        if (entitylivingbaseIn.func_70631_g_()) {
          GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
          GlStateManager.func_179109_b(0.0F, 1.5F, 0.0F);
        } 
        renderHeldItemMainHand(entitylivingbaseIn, itemstack1, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
        renderHeldItemOffHand(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
        GlStateManager.func_179121_F();
      } 
    } 
  }
  
  private void renderHeldItemMainHand(EntityLivingBase p_188358_1_, ItemStack p_188358_2_, ItemCameraTransforms.TransformType p_188358_3_, EnumHandSide handSide) {
    if (!p_188358_2_.func_190926_b()) {
      GlStateManager.func_179094_E();
      if (p_188358_1_.func_70093_af())
        GlStateManager.func_179109_b(0.0F, 0.2F, 0.0F); 
      if (this.modelRenderer1 != null) {
        this.modelRenderer1.func_78794_c(0.0625F);
        GlStateManager.func_179114_b(-90.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
        boolean flag = (handSide == EnumHandSide.LEFT);
        GlStateManager.func_179109_b(0.05F, 0.125F, -0.625F);
        if (p_188358_1_ instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityWitch) {
          GlStateManager.func_179114_b(75.0F, 1.0F, 0.0F, 0.0F);
          GlStateManager.func_179114_b(15.0F, 0.0F, 0.0F, 1.0F);
          GlStateManager.func_179109_b(-0.15F, 0.0F, 0.0F);
        } 
        Minecraft.func_71410_x().func_175597_ag().func_187462_a(p_188358_1_, p_188358_2_, p_188358_3_, flag);
      } 
      GlStateManager.func_179121_F();
    } 
  }
  
  private void renderHeldItemOffHand(EntityLivingBase p_188358_1_, ItemStack p_188358_2_, ItemCameraTransforms.TransformType p_188358_3_, EnumHandSide handSide) {
    if (!p_188358_2_.func_190926_b()) {
      GlStateManager.func_179094_E();
      if (p_188358_1_.func_70093_af())
        GlStateManager.func_179109_b(0.0F, 0.2F, 0.0F); 
      if (this.modelRenderer2 != null) {
        this.modelRenderer2.func_78794_c(0.0625F);
        GlStateManager.func_179114_b(-90.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
        boolean flag = (handSide == EnumHandSide.LEFT);
        GlStateManager.func_179109_b(-0.05F, 0.125F, -0.625F);
        if (p_188358_1_ instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityWitch) {
          GlStateManager.func_179114_b(75.0F, 1.0F, 0.0F, 0.0F);
          GlStateManager.func_179114_b(-15.0F, 0.0F, 0.0F, 1.0F);
          GlStateManager.func_179109_b(0.15F, 0.0F, 0.0F);
        } 
        Minecraft.func_71410_x().func_175597_ag().func_187462_a(p_188358_1_, p_188358_2_, p_188358_3_, flag);
      } 
      GlStateManager.func_179121_F();
    } 
  }
  
  public boolean func_177142_b() {
    return false;
  }
}
