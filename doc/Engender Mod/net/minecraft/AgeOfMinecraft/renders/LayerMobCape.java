package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.models.ICappedModel;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerMobCape implements LayerRenderer<EntityTameBase> {
  private static final String[] NAMES = new String[] { "Umbrella_Ghast", "JadeRabbitTsuki", "Mrbt0907", "Entanos", "GB_Doge_9000", "BeckBroJack", "Milo1133", "4ChanMeta", "TheMCOverlordYT" };
  
  private static final String[] CAPES = new String[] { "textures/cape_ug.png", "textures/cape_jd.png", "textures/cape_bt.png", "textures/cape_en.png", "textures/cape_sh.png", "textures/cape_bbj.png", "textures/cape_m.png", "textures/cape_4c.png", "textures/cape_mc.png" };
  
  private final RenderLivingBase<?> playerRenderer;
  
  public LayerMobCape(RenderLivingBase<?> playerRendererIn) {
    this.playerRenderer = playerRendererIn;
  }
  
  public void doRenderLayer(EntityTameBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    if (!entitylivingbaseIn.func_82150_aj() && !entitylivingbaseIn.func_70631_g_() && this.playerRenderer.func_177087_b() instanceof ICappedModel && !entitylivingbaseIn.isWild() && ((AbstractClientPlayer)entitylivingbaseIn.getOwner()).func_152122_n() && ((AbstractClientPlayer)entitylivingbaseIn.getOwner()).func_175148_a(EnumPlayerModelParts.CAPE) && ((AbstractClientPlayer)entitylivingbaseIn.getOwner()).func_110303_q() != null) {
      ItemStack itemstack = entitylivingbaseIn.func_184582_a(EntityEquipmentSlot.CHEST);
      String cape = null;
      for (int i = 0; i <= NAMES.length; i++) {
        if (entitylivingbaseIn.getOwner().func_70005_c_() == NAMES[i])
          cape = CAPES[i]; 
      } 
      if (cape != null && itemstack.func_77973_b() != Items.field_185160_cR) {
        GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
        this.playerRenderer.func_110776_a(new ResourceLocation("ageofminecraft", cape));
        GlStateManager.func_179094_E();
        GlStateManager.func_179109_b(0.0F, 0.0F, 0.125F);
        double d0 = entitylivingbaseIn.prevChasingPosX + (entitylivingbaseIn.chasingPosX - entitylivingbaseIn.prevChasingPosX) * partialTicks - entitylivingbaseIn.field_70169_q + (entitylivingbaseIn.field_70165_t - entitylivingbaseIn.field_70169_q) * partialTicks;
        double d1 = entitylivingbaseIn.prevChasingPosY + (entitylivingbaseIn.chasingPosY - entitylivingbaseIn.prevChasingPosY) * partialTicks - entitylivingbaseIn.field_70167_r + (entitylivingbaseIn.field_70163_u - entitylivingbaseIn.field_70167_r) * partialTicks;
        double d2 = entitylivingbaseIn.prevChasingPosZ + (entitylivingbaseIn.chasingPosZ - entitylivingbaseIn.prevChasingPosZ) * partialTicks - entitylivingbaseIn.field_70166_s + (entitylivingbaseIn.field_70161_v - entitylivingbaseIn.field_70166_s) * partialTicks;
        float f = entitylivingbaseIn.field_70760_ar + (entitylivingbaseIn.field_70761_aq - entitylivingbaseIn.field_70760_ar) * partialTicks;
        double d3 = MathHelper.func_76126_a(f * 0.017453292F);
        double d4 = -MathHelper.func_76134_b(f * 0.017453292F);
        float f1 = (float)d1 * 10.0F;
        f1 = MathHelper.func_76131_a(f1, -6.0F, 32.0F);
        float f2 = (float)(d0 * d3 + d2 * d4) * 100.0F;
        float f3 = (float)(d0 * d4 - d2 * d3) * 100.0F;
        if (f2 < 0.0F)
          f2 = 0.0F; 
        float f4 = entitylivingbaseIn.field_70126_B + (entitylivingbaseIn.field_70177_z - entitylivingbaseIn.field_70177_z) * partialTicks;
        f1 += MathHelper.func_76126_a((entitylivingbaseIn.field_70141_P + (entitylivingbaseIn.field_70140_Q - entitylivingbaseIn.field_70141_P) * partialTicks) * 6.0F) * 32.0F * f4;
        if (entitylivingbaseIn.func_70093_af())
          f1 += 25.0F; 
        if (entitylivingbaseIn.field_70160_al)
          f1 -= MathHelper.func_76131_a((float)(entitylivingbaseIn.field_70181_x * Math.PI * 45.0D), -75.0F, 3.0F); 
        if (entitylivingbaseIn.func_82150_aj())
          f1 = 0.0F; 
        ((ICappedModel)this.playerRenderer.func_177087_b()).renderCape(0.0625F, f1, f2, f3);
        GlStateManager.func_179121_F();
      } 
    } 
  }
  
  public boolean func_177142_b() {
    return false;
  }
}
