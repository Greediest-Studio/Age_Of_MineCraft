package net.minecraft.AgeOfMinecraft.items;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.registry.ETab;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemFusionVanilla extends ItemFusion {
  public ItemFusionVanilla(ItemTierItem fuse, String name, int mana, int entropy) {
    super(fuse, name, mana, entropy, ETab.vanilla);
  }
  
  public void func_77624_a(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    super.func_77624_a(stack, worldIn, tooltip, flagIn);
    tooltip.add(TextFormatting.WHITE + "(Vanilla)" + TextFormatting.WHITE);
  }
}
