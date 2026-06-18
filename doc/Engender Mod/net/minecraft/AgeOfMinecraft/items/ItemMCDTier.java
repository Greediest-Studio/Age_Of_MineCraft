package net.minecraft.AgeOfMinecraft.items;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.registry.ETab;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemMCDTier extends ItemTierItem {
  public ItemMCDTier(int tier, int fusingTime, int stacking, String name) {
    super(tier, fusingTime, stacking, ETab.engender, name);
  }
  
  public void func_77624_a(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    super.func_77624_a(stack, worldIn, tooltip, flagIn);
    tooltip.add(TextFormatting.GOLD + "(Minecraft Dungeons)" + TextFormatting.WHITE);
  }
}
