package net.minecraft.AgeOfMinecraft.items;

import net.minecraft.AgeOfMinecraft.registry.ETab;
import net.minecraft.item.Item;

public class ItemBEItem extends Item {
  public ItemBEItem(String name) {
    setRegistryName(name);
    func_77655_b(name);
    func_77637_a(ETab.engender);
  }
}
