package net.minecraft.AgeOfMinecraft.items;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.registry.ETab;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemMCSMTier extends ItemTierItem {
  public ItemMCSMTier(int tier, int fusingTime, int stacking, String name) {
    super(tier, fusingTime, stacking, ETab.storymode, name);
  }
  
  public void func_77624_a(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    super.func_77624_a(stack, worldIn, tooltip, flagIn);
    tooltip.add(TextFormatting.LIGHT_PURPLE + "(Minecraft Story Mode)" + TextFormatting.WHITE);
  }
}
