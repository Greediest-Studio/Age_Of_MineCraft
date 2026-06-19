package net.minecraft.AgeOfMinecraft.items;

import net.minecraft.AgeOfMinecraft.registry.ETab;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemGlowing extends Item {
  public ItemGlowing(String name) {
    ItemCompat.setup(this, name, ETab.engender);
  }

  public boolean hasEffect(ItemStack stack) {
    return true;
  }
}
