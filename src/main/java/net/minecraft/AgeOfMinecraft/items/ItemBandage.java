package net.minecraft.AgeOfMinecraft.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import java.util.List;
import net.minecraft.AgeOfMinecraft.registry.EEffect;
import net.minecraft.AgeOfMinecraft.registry.EItem;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Interface(iface = "baubles.api.IBauble", modid = "baubles")
public class ItemBandage extends ItemBEItem implements IBauble {
  public ItemBandage(String name, int durability) {
    super("bandage_" + name);
    setMaxDamage(durability);
    setMaxStackSize(1);
  }
  
  public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
    ItemStack stack = playerIn.getHeldItem(handIn);
    if (playerIn.isPotionActive(EEffect.BLEEDING) && (playerIn.getActivePotionEffect(EEffect.BLEEDING).getAmplifier() <= 1 || this != EItem.bandage)) {
      playerIn.playSound(SoundEvents.ENTITY_LEASHKNOT_PLACE, 1.0F, 1.5F);
      playerIn.removeActivePotionEffect(EEffect.BLEEDING);
      if (!playerIn.world.isRemote) {
        playerIn.heal(2.0F);
        if (this == EItem.bandage || this == EItem.bandageMedical)
          stack.damageItem(1, (EntityLivingBase)playerIn); 
      } 
      return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    } 
    if (this == EItem.artifact2) {
      for (PotionEffect effect : playerIn.getActivePotionEffects()) {
        if (effect.getPotion().isBadEffect() || effect.getPotion() == MobEffects.GLOWING || effect.getPotion() == EEffect.BLEEDING)
          playerIn.removeActivePotionEffect(effect.getPotion()); 
      } 
      return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    } 
    return super.onItemRightClick(worldIn, playerIn, handIn);
  }
  
  public EnumRarity getRarity(ItemStack stack) {
    switch (getTranslationKey().substring(5)) {
      case "bandage_artifact":
        return ESetup.UBEREPIC;
      case "bandage_box":
        return EnumRarity.RARE;
    } 
    return super.getRarity(stack);
  }
  
  @SideOnly(Side.CLIENT)
  public boolean hasEffect(ItemStack stack) {
    return (this == EItem.artifact2 || super.hasEffect(stack));
  }
  
  public void addInformation(ItemStack stack, World player, List<String> l, ITooltipFlag B) {
    if (!stack.hasTagCompound())
      stack.setTagCompound(new NBTTagCompound()); 
    switch (getTranslationKey().substring(5)) {
      case "bandage_artifact":
        l.add("(" + TextFormatting.GOLD + "ARTIFACT" + TextFormatting.GRAY + ")");
        l.add("A box enchanted to heal not only injuries, but even negative potion effects.");
        l.add(TextFormatting.GOLD + "Right click or equip in Bauble slot to use.");
        break;
      case "bandage_box":
        l.add("A first-aid kit that can cure bleeding an infinite amount of times.");
        l.add(TextFormatting.GOLD + "Right click or equip in Bauble slot to use.");
        break;
      case "bandage_good":
        l.add("A medical bandage that heals any bleeding.");
        l.add(TextFormatting.GOLD + "Right click or equip in Bauble slot to use.");
        break;
      case "bandage_poor":
        l.add("A simple bandage that's only good at healing hurt knees.");
        l.add(TextFormatting.GOLD + "Right click or equip in Bauble slot to use.");
        break;
    } 
  }
  
  public BaubleType getBaubleType(ItemStack itemstack) {
    return BaubleType.TRINKET;
  }
  
  public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
    player.playSound(SoundEvents.ENTITY_LEASHKNOT_PLACE, 0.75F, 1.9F);
  }
  
  public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
    player.playSound(SoundEvents.ENTITY_LEASHKNOT_PLACE, 0.75F, 2.0F);
  }
  
  public void onWornTick(ItemStack stack, EntityLivingBase entityIn) {
    if (entityIn.isPotionActive(EEffect.BLEEDING) && (entityIn.getActivePotionEffect(EEffect.BLEEDING).getAmplifier() <= 1 || this != EItem.bandage)) {
      entityIn.playSound(SoundEvents.ENTITY_LEASHKNOT_PLACE, 1.0F, 1.5F);
      entityIn.removeActivePotionEffect(EEffect.BLEEDING);
      if (!entityIn.world.isRemote) {
        entityIn.heal(2.0F);
        if (this == EItem.bandage || this == EItem.bandageMedical)
          stack.damageItem(1, entityIn); 
      } 
    } 
    if (this == EItem.artifact2 && entityIn.getActivePotionEffects() != null && !entityIn.getActivePotionEffects().isEmpty()) {
      for (PotionEffect effect : entityIn.getActivePotionEffects()) {
        if (effect.getPotion().isBadEffect() || effect.getPotion() == MobEffects.GLOWING || effect.getPotion() == EEffect.BLEEDING)
          entityIn.removeActivePotionEffect(effect.getPotion()); 
      } 
      if (!entityIn.world.isRemote && entityIn.ticksExisted % 20 == 0)
        entityIn.heal(2.0F); 
    } 
  }
}
