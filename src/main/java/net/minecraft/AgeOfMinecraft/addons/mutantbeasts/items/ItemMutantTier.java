package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.items;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.items.ItemTierItem;
import net.minecraft.AgeOfMinecraft.registry.ETab;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemMutantTier extends ItemTierItem {
  public ItemMutantTier(int tier, int fusingTime, int stacking, String name) {
    super(tier, fusingTime, stacking, ETab.mutant, name);
  }
  
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    super.addInformation(stack, worldIn, tooltip, flagIn);
    tooltip.add(TextFormatting.DARK_GREEN + "(Mutant Beasts)" + TextFormatting.WHITE);
  }
}
