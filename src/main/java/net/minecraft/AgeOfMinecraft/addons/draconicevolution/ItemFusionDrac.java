package net.minecraft.AgeOfMinecraft.addons.draconicevolution;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.items.ItemFusion;
import net.minecraft.AgeOfMinecraft.items.ItemTierItem;
import net.minecraft.AgeOfMinecraft.registry.ETab;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemFusionDrac extends ItemFusion {
  public ItemFusionDrac(ItemTierItem fuse, String name, int mana, int entropy) {
    super(fuse, name, mana, entropy, ETab.draconic);
  }
  
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    super.addInformation(stack, worldIn, tooltip, flagIn);
    tooltip.add(TextFormatting.DARK_RED + "(Draconic Evolution)" + TextFormatting.WHITE);
  }
}
