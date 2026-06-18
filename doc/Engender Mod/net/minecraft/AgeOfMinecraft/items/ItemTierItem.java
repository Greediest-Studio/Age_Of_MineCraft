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
    func_77637_a(tab);
    this.item = this;
    this.itemTier = tier;
    this.fuseTime = fusingTime;
    func_77625_d(stacking);
  }
  
  public int getTimeToSpawnMob() {
    return this.fuseTime;
  }
  
  public EnumRarity func_77613_e(ItemStack stack) {
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
  
  public boolean func_77636_d(ItemStack stack) {
    return (this.itemTier >= 4) ? true : super.func_77636_d(stack);
  }
  
  public void triggerAction(EntityPlayer playerIn, ItemStack stack) {
    if (playerIn instanceof EntityPlayerMP)
      CriteriaTriggers.field_193138_y.func_193148_a((EntityPlayerMP)playerIn, stack); 
  }
}
