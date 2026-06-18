package net.minecraft.AgeOfMinecraft.renders;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityZombie;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.StringUtils;

@SideOnly(Side.CLIENT)
public class LayerCustomHeadEngender implements LayerRenderer<EntityLivingBase> {
  private final ModelRenderer modelRenderer;
  
  private final ModelRenderer secondaryModelRenderer;
  
  public LayerCustomHeadEngender(ModelRenderer primary, ModelRenderer cmm) {
    this.modelRenderer = primary;
    this.secondaryModelRenderer = cmm;
  }
  
  public void func_177141_a(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    ItemStack itemstack = entitylivingbaseIn.func_184582_a(EntityEquipmentSlot.HEAD);
    if (!itemstack.func_190926_b() && this.modelRenderer != null && this.secondaryModelRenderer != null && !entitylivingbaseIn.func_70631_g_()) {
      Item item = itemstack.func_77973_b();
      Minecraft minecraft = Minecraft.func_71410_x();
      GlStateManager.func_179094_E();
      boolean flag = ((entitylivingbaseIn instanceof EntityZombie && ((EntityZombie)entitylivingbaseIn).isVillager()) || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityVillager || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityWitch || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityVindicator || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEvoker);
      if (!EngenderConfig.mobs.useMobTalkerModels && entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGhast) {
        float f = 2.0F;
        GlStateManager.func_179109_b(0.0F, 0.5F * scale, 0.0F);
        GlStateManager.func_179152_a(f, f, f);
        GlStateManager.func_179109_b(0.0F, 4.0F * scale, 0.0F);
      } 
      if (!EngenderConfig.mobs.useMobTalkerModels && entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGuardian) {
        float f = 2.0F;
        GlStateManager.func_179109_b(0.0F, 0.5F * scale, 0.0F);
        GlStateManager.func_179152_a(f, f, f);
        GlStateManager.func_179109_b(0.0F, 4.0F * scale, 0.0F);
      } 
      if (!EngenderConfig.mobs.useMobTalkerModels && entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySlime)
        GlStateManager.func_179109_b(0.0F, 1.5F, 0.0F); 
      if ((!EngenderConfig.mobs.useMobTalkerModels && entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityIronGolem) || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityMagmaGolem || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityPrisonGolem)
        GlStateManager.func_179109_b(0.0F, -0.25F, -0.125F + entitylivingbaseIn.field_70125_A / 180.0F); 
      if (!EngenderConfig.mobs.useMobTalkerModels && entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySpider)
        GlStateManager.func_179109_b(0.0F, 0.25F, -0.25F + entitylivingbaseIn.field_70125_A / 180.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityCow || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntitySheep || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityPig)
        GlStateManager.func_179109_b(0.0F, 0.25F, ((entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityPig) ? -0.25F : -0.125F) + entitylivingbaseIn.field_70125_A / 180.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityLlama)
        GlStateManager.func_179109_b(0.0F, -0.5F, -0.25F + entitylivingbaseIn.field_70125_A / 180.0F); 
      if ((!EngenderConfig.mobs.useMobTalkerModels && entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntitySilverfish) || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityEndermite || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityWolf || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityPolarBear)
        GlStateManager.func_179109_b(0.0F, 0.25F, 0.0F); 
      if (EngenderConfig.mobs.useMobTalkerModels && this.secondaryModelRenderer != this.modelRenderer)
        GlStateManager.func_179109_b(0.0F, 0.1F, 0.0F); 
      if (EngenderConfig.mobs.useMobTalkerModels) {
        this.secondaryModelRenderer.func_78794_c(0.0625F);
      } else {
        this.modelRenderer.func_78794_c(0.0625F);
      } 
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      if (item == Items.field_151144_bL) {
        GlStateManager.func_179152_a(1.1875F, -1.1875F, -1.1875F);
        if (flag)
          GlStateManager.func_179109_b(0.0F, 0.0625F, 0.0F); 
        GameProfile gameprofile = null;
        if (itemstack.func_77942_o()) {
          NBTTagCompound nbttagcompound = itemstack.func_77978_p();
          if (nbttagcompound.func_150297_b("SkullOwner", 10)) {
            gameprofile = NBTUtil.func_152459_a(nbttagcompound.func_74775_l("SkullOwner"));
          } else if (nbttagcompound.func_150297_b("SkullOwner", 8)) {
            String s = nbttagcompound.func_74779_i("SkullOwner");
            if (!StringUtils.isBlank(s)) {
              gameprofile = TileEntitySkull.func_174884_b(new GameProfile((UUID)null, s));
              nbttagcompound.func_74782_a("SkullOwner", (NBTBase)NBTUtil.func_180708_a(new NBTTagCompound(), gameprofile));
            } 
          } 
        } 
        TileEntitySkullRenderer.field_147536_b.func_188190_a(-0.5F, 0.0F, -0.5F, EnumFacing.UP, 180.0F, itemstack.func_77960_j(), gameprofile, -1, limbSwing);
      } else if (!(item instanceof ItemArmor) || ((ItemArmor)item).func_185083_B_() != EntityEquipmentSlot.HEAD) {
        GlStateManager.func_179109_b(0.0F, -0.25F, 0.0F);
        GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.func_179152_a(0.625F, -0.625F, -0.625F);
        if (flag)
          GlStateManager.func_179109_b(0.0F, 0.1875F, 0.0F); 
        minecraft.func_175597_ag().func_178099_a(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.HEAD);
      } 
      GlStateManager.func_179121_F();
    } 
  }
  
  public boolean func_177142_b() {
    return false;
  }
}
