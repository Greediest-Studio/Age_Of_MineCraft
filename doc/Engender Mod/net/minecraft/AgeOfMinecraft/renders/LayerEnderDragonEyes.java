package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerEnderDragonEyes implements LayerRenderer<EntityLivingBase> {
  private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/enderdragon/dragon_eyes.png");
  
  private final RenderEnderDragon dragonRenderer;
  
  public LayerEnderDragonEyes(RenderEnderDragon p_i46118_1_) {
    this.dragonRenderer = p_i46118_1_;
  }
  
  public void func_177210_a(EntityEnderDragon p_177210_1_, float p_177210_2_, float p_177210_3_, float p_177210_4_, float p_177210_5_, float p_177210_6_, float p_177210_7_, float p_177210_8_) {
    this.dragonRenderer.func_110776_a(TEXTURE);
    GlStateManager.func_179147_l();
    GlStateManager.func_179118_c();
    GlStateManager.func_179112_b(1, 1);
    GlStateManager.func_179140_f();
    GlStateManager.func_179143_c(514);
    char c0 = '\uF0F0';
    int i = c0 % 65536;
    int j = c0 / 65536;
    OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, i / 1.0F, j / 1.0F);
    GlStateManager.func_179145_e();
    GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
    this.dragonRenderer.func_177087_b().func_78088_a((Entity)p_177210_1_, p_177210_2_, p_177210_3_, p_177210_5_, p_177210_6_, p_177210_7_, p_177210_8_);
    this.dragonRenderer.func_177105_a((EntityLiving)p_177210_1_);
    GlStateManager.func_179084_k();
    GlStateManager.func_179141_d();
    GlStateManager.func_179143_c(515);
  }
  
  public boolean func_177142_b() {
    return false;
  }
  
  public void func_177141_a(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
    func_177210_a((EntityEnderDragon)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
  }
}
