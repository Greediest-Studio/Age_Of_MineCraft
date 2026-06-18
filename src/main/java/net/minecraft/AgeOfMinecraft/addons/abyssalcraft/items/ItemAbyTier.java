package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.items.ItemTierItem;
import net.minecraft.AgeOfMinecraft.registry.ETab;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemAbyTier extends ItemTierItem {
  public ItemAbyTier(int tier, int fusingTime, int stacking, String name) {
    super(tier, fusingTime, stacking, ETab.abyssal, name);
  }
  
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    super.addInformation(stack, worldIn, tooltip, flagIn);
    tooltip.add(TextFormatting.DARK_AQUA + "(Abyssalcraft)" + TextFormatting.WHITE);
  }
}
