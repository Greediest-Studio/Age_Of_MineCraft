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
  
  public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    if (this.modelRenderer1 != null && this.modelRenderer2 != null) {
      boolean flag = (entitylivingbaseIn.getPrimaryHand() == EnumHandSide.RIGHT);
      ItemStack itemstack = flag ? entitylivingbaseIn.getHeldItemOffhand() : entitylivingbaseIn.getHeldItemMainhand();
      ItemStack itemstack1 = flag ? entitylivingbaseIn.getHeldItemMainhand() : entitylivingbaseIn.getHeldItemOffhand();
      if (!itemstack.isEmpty() || !itemstack1.isEmpty()) {
        GlStateManager.pushMatrix();
        if (entitylivingbaseIn.isChild()) {
          GlStateManager.scale(0.5F, 0.5F, 0.5F);
          GlStateManager.translate(0.0F, 1.5F, 0.0F);
        } 
        renderHeldItemMainHand(entitylivingbaseIn, itemstack1, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
        renderHeldItemOffHand(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
        GlStateManager.popMatrix();
      } 
    } 
  }
  
  private void renderHeldItemMainHand(EntityLivingBase p_188358_1_, ItemStack p_188358_2_, ItemCameraTransforms.TransformType p_188358_3_, EnumHandSide handSide) {
    if (!p_188358_2_.isEmpty()) {
      GlStateManager.pushMatrix();
      if (p_188358_1_.isSneaking())
        GlStateManager.translate(0.0F, 0.2F, 0.0F); 
      if (this.modelRenderer1 != null) {
        this.modelRenderer1.postRender(0.0625F);
        GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        boolean flag = (handSide == EnumHandSide.LEFT);
        GlStateManager.translate(0.05F, 0.125F, -0.625F);
        if (p_188358_1_ instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityWitch) {
          GlStateManager.rotate(75.0F, 1.0F, 0.0F, 0.0F);
          GlStateManager.rotate(15.0F, 0.0F, 0.0F, 1.0F);
          GlStateManager.translate(-0.15F, 0.0F, 0.0F);
        } 
        Minecraft.getMinecraft().getItemRenderer().renderItemSide(p_188358_1_, p_188358_2_, p_188358_3_, flag);
      } 
      GlStateManager.popMatrix();
    } 
  }
  
  private void renderHeldItemOffHand(EntityLivingBase p_188358_1_, ItemStack p_188358_2_, ItemCameraTransforms.TransformType p_188358_3_, EnumHandSide handSide) {
    if (!p_188358_2_.isEmpty()) {
      GlStateManager.pushMatrix();
      if (p_188358_1_.isSneaking())
        GlStateManager.translate(0.0F, 0.2F, 0.0F); 
      if (this.modelRenderer2 != null) {
        this.modelRenderer2.postRender(0.0625F);
        GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        boolean flag = (handSide == EnumHandSide.LEFT);
        GlStateManager.translate(-0.05F, 0.125F, -0.625F);
        if (p_188358_1_ instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityWitch) {
          GlStateManager.rotate(75.0F, 1.0F, 0.0F, 0.0F);
          GlStateManager.rotate(-15.0F, 0.0F, 0.0F, 1.0F);
          GlStateManager.translate(0.15F, 0.0F, 0.0F);
        } 
        Minecraft.getMinecraft().getItemRenderer().renderItemSide(p_188358_1_, p_188358_2_, p_188358_3_, flag);
      } 
      GlStateManager.popMatrix();
    } 
  }
  
  public boolean shouldCombineTextures() {
    return false;
  }
}
