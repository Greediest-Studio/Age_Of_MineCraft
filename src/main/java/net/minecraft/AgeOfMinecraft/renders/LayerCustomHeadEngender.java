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
  
  public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    ItemStack itemstack = entitylivingbaseIn.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
    if (!itemstack.isEmpty() && this.modelRenderer != null && this.secondaryModelRenderer != null && !entitylivingbaseIn.isChild()) {
      Item item = itemstack.getItem();
      Minecraft minecraft = Minecraft.getMinecraft();
      GlStateManager.pushMatrix();
      boolean flag = ((entitylivingbaseIn instanceof EntityZombie && ((EntityZombie)entitylivingbaseIn).isVillager()) || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityVillager || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityWitch || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityVindicator || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEvoker);
      if (!EngenderConfig.mobs.useMobTalkerModels && entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGhast) {
        float f = 2.0F;
        GlStateManager.translate(0.0F, 0.5F * scale, 0.0F);
        GlStateManager.scale(f, f, f);
        GlStateManager.translate(0.0F, 4.0F * scale, 0.0F);
      } 
      if (!EngenderConfig.mobs.useMobTalkerModels && entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGuardian) {
        float f = 2.0F;
        GlStateManager.translate(0.0F, 0.5F * scale, 0.0F);
        GlStateManager.scale(f, f, f);
        GlStateManager.translate(0.0F, 4.0F * scale, 0.0F);
      } 
      if (!EngenderConfig.mobs.useMobTalkerModels && entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySlime)
        GlStateManager.translate(0.0F, 1.5F, 0.0F); 
      if ((!EngenderConfig.mobs.useMobTalkerModels && entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityIronGolem) || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityMagmaGolem || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityPrisonGolem)
        GlStateManager.translate(0.0F, -0.25F, -0.125F + entitylivingbaseIn.rotationPitch / 180.0F); 
      if (!EngenderConfig.mobs.useMobTalkerModels && entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySpider)
        GlStateManager.translate(0.0F, 0.25F, -0.25F + entitylivingbaseIn.rotationPitch / 180.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityCow || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntitySheep || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityPig)
        GlStateManager.translate(0.0F, 0.25F, ((entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityPig) ? -0.25F : -0.125F) + entitylivingbaseIn.rotationPitch / 180.0F); 
      if (entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityLlama)
        GlStateManager.translate(0.0F, -0.5F, -0.25F + entitylivingbaseIn.rotationPitch / 180.0F); 
      if ((!EngenderConfig.mobs.useMobTalkerModels && entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntitySilverfish) || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityEndermite || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityWolf || entitylivingbaseIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityPolarBear)
        GlStateManager.translate(0.0F, 0.25F, 0.0F); 
      if (EngenderConfig.mobs.useMobTalkerModels && this.secondaryModelRenderer != this.modelRenderer)
        GlStateManager.translate(0.0F, 0.1F, 0.0F); 
      if (EngenderConfig.mobs.useMobTalkerModels) {
        this.secondaryModelRenderer.postRender(0.0625F);
      } else {
        this.modelRenderer.postRender(0.0625F);
      } 
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      if (item == Items.SKULL) {
        GlStateManager.scale(1.1875F, -1.1875F, -1.1875F);
        if (flag)
          GlStateManager.translate(0.0F, 0.0625F, 0.0F); 
        GameProfile gameprofile = null;
        if (itemstack.hasTagCompound()) {
          NBTTagCompound nbttagcompound = itemstack.getTagCompound();
          if (nbttagcompound.hasKey("SkullOwner", 10)) {
            gameprofile = NBTUtil.readGameProfileFromNBT(nbttagcompound.getCompoundTag("SkullOwner"));
          } else if (nbttagcompound.hasKey("SkullOwner", 8)) {
            String s = nbttagcompound.getString("SkullOwner");
            if (!StringUtils.isBlank(s)) {
              gameprofile = TileEntitySkull.updateGameProfile(new GameProfile((UUID)null, s));
              nbttagcompound.setTag("SkullOwner", (NBTBase)NBTUtil.writeGameProfile(new NBTTagCompound(), gameprofile));
            } 
          } 
        } 
        TileEntitySkullRenderer.instance.renderSkull(-0.5F, 0.0F, -0.5F, EnumFacing.UP, 180.0F, itemstack.getMetadata(), gameprofile, -1, limbSwing);
      } else if (!(item instanceof ItemArmor) || ((ItemArmor)item).getEquipmentSlot() != EntityEquipmentSlot.HEAD) {
        GlStateManager.translate(0.0F, -0.25F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(0.625F, -0.625F, -0.625F);
        if (flag)
          GlStateManager.translate(0.0F, 0.1875F, 0.0F); 
        minecraft.getItemRenderer().renderItem(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.HEAD);
      } 
      GlStateManager.popMatrix();
    } 
  }
  
  public boolean shouldCombineTextures() {
    return false;
  }
}
