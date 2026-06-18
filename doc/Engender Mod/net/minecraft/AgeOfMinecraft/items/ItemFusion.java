package net.minecraft.AgeOfMinecraft.items;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemFusion extends ItemBEItem {
  private ItemTierItem itemToFuse;
  
  private int manaCost;
  
  private int entropyCost;
  
  public ItemFusion(ItemTierItem fuse, String name, int mana, int entropy, CreativeTabs tab) {
    super(name);
    this.itemToFuse = fuse;
    this.manaCost = mana;
    this.entropyCost = entropy;
    switch (this.itemToFuse.itemTier) {
      case 5:
        func_77625_d(1);
        break;
      case 4:
        func_77625_d(8);
        break;
      case 3:
        func_77625_d(16);
        break;
      case 2:
        func_77625_d(24);
        break;
      case 1:
        func_77625_d(32);
        break;
      default:
        func_77625_d(64);
        break;
    } 
    func_77637_a(tab);
  }
  
  public void func_77624_a(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    tooltip.add("Place in the Fusion Crafter to fuse");
    tooltip.add(TextFormatting.AQUA + "Mana Cost: " + getManaCost());
    if (getEntropyCost() > 0)
      tooltip.add(TextFormatting.RED + "Entropy Cost: " + getEntropyCost()); 
    tooltip.add(TextFormatting.GOLD + "Fuse time: " + getItemToFuse().getTimeToSpawnMob() + " seconds");
    tooltip.add(TextFormatting.GOLD + "Tier " + ((getItemToFuse()).itemTier + 1));
  }
  
  public ItemTierItem getItemToFuse() {
    return this.itemToFuse;
  }
  
  public int getManaCost() {
    return this.manaCost;
  }
  
  public int getEntropyCost() {
    return this.entropyCost;
  }
}
