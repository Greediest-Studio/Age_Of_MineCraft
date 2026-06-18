package net.minecraft.AgeOfMinecraft.addons.abyssalcraft;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconInfusionRitual;
import com.shinoow.abyssalcraft.lib.ACLib;
import net.minecraft.AgeOfMinecraft.registry.EItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class NecronomiconStaffUpgradeRitual extends NecronomiconInfusionRitual {
  public NecronomiconStaffUpgradeRitual(String unlocalizedName, int upgradelevel, ItemStack item, Object sacrifice) {
    super(unlocalizedName, 4, ACLib.omothol_id, 100000.0F, item, sacrifice, new Object[] { (upgradelevel == 3) ? EItem.heromaker : ((upgradelevel == 2) ? Blocks.field_150461_bJ : ((upgradelevel == 1) ? Blocks.field_150380_bt : Items.field_185158_cP)), new ItemStack(ACBlocks.ingot_block, 1, upgradelevel), ACItems.essence_of_the_gatekeeper, new ItemStack(ACBlocks.ingot_block, 1, upgradelevel), (upgradelevel == 3) ? EItem.heromaker : ((upgradelevel == 2) ? Blocks.field_150461_bJ : ((upgradelevel == 1) ? Blocks.field_150380_bt : Items.field_185158_cP)), new ItemStack(ACBlocks.ingot_block, 1, upgradelevel), ACItems.essence_of_the_gatekeeper, new ItemStack(ACBlocks.ingot_block, 1, upgradelevel) });
  }
}
