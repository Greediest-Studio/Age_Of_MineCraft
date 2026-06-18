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
        setMaxStackSize(1);
        break;
      case 4:
        setMaxStackSize(8);
        break;
      case 3:
        setMaxStackSize(16);
        break;
      case 2:
        setMaxStackSize(24);
        break;
      case 1:
        setMaxStackSize(32);
        break;
      default:
        setMaxStackSize(64);
        break;
    } 
    setCreativeTab(tab);
  }
  
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
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
