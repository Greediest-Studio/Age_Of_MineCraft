package net.minecraft.AgeOfMinecraft.items;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.registry.ETab;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemFusionMCD extends ItemFusion {
  public ItemFusionMCD(ItemTierItem fuse, String name, int mana, int entropy) {
    super(fuse, name, mana, entropy, ETab.dungeons);
  }
  
  public void func_77624_a(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    super.func_77624_a(stack, worldIn, tooltip, flagIn);
    tooltip.add(TextFormatting.GOLD + "(Minecraft Dungeons)" + TextFormatting.WHITE);
  }
}
