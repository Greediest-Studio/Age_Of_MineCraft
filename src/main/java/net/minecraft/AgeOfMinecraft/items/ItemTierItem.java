package net.minecraft.AgeOfMinecraft.items;

import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemTierItem extends ItemBEItem {
  public int itemTier;
  
  public Item item;
  
  public int fuseTime;
  
  public ItemTierItem(int tier, int fusingTime, int stacking, CreativeTabs tab, String name) {
    super(name);
    ItemCompat.setCreativeTab(this, tab);
    this.item = this;
    this.itemTier = tier;
    this.fuseTime = fusingTime;
    ItemCompat.setMaxStackSize(this, stacking);
  }
  
  public int getTimeToSpawnMob() {
    return this.fuseTime;
  }
  
  public EnumRarity getRarity(ItemStack stack) {
    switch (this.itemTier) {
      case 5:
        return ESetup.UBEREPIC;
      case 4:
        return ESetup.SUPEREPIC;
      case 3:
        return EnumRarity.EPIC;
      case 2:
        return EnumRarity.RARE;
      case 1:
        return EnumRarity.UNCOMMON;
    } 
    return EnumRarity.COMMON;
  }
  
  public boolean hasEffect(ItemStack stack) {
    return (this.itemTier >= 4) ? true : super.hasEffect(stack);
  }
  
  public void triggerAction(EntityPlayer playerIn, ItemStack stack) {
    if (playerIn instanceof EntityPlayerMP)
      CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP)playerIn, stack); 
  }
}
