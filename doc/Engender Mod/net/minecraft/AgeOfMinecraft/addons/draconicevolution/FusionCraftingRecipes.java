package net.minecraft.AgeOfMinecraft.addons.draconicevolution;

import com.brandon3055.draconicevolution.DEFeatures;
import com.brandon3055.draconicevolution.lib.RecipeManager;
import net.minecraft.AgeOfMinecraft.registry.EItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FusionCraftingRecipes {
  public static void Recipie_List() {
    RecipeManager.addFusion(RecipeManager.RecipeDifficulty.NORMAL, new ItemStack((Item)EItem.fusionItemChaosGuardian), new ItemStack((Item)EItem.fusionItemEnderDragon), 1000000000, 3, new Object[] { 
          Blocks.field_150380_bt, Blocks.field_150380_bt, DEFeatures.chaoticCore, DEFeatures.chaoticCore, DEFeatures.chaosShard, DEFeatures.chaosShard, DEFeatures.draconicBlock, DEFeatures.draconicBlock, DEFeatures.chaosShard, DEFeatures.chaosShard, 
          DEFeatures.dragonHeart, DEFeatures.dragonHeart, DEFeatures.draconicBlock, DEFeatures.draconicBlock, DEFeatures.dragonHeart, DEFeatures.dragonHeart });
    RecipeManager.addFusion(RecipeManager.RecipeDifficulty.HARD, new ItemStack((Item)EItem.fusionItemChaosGuardian), new ItemStack((Item)EItem.fusionItemEnderDragon), 2000000000, 3, new Object[] { 
          EItem.fusionItemEnderDragon, EItem.fusionItemEnderDragon, DEFeatures.chaoticCore, DEFeatures.chaoticCore, DEFeatures.chaoticCore, DEFeatures.chaoticCore, DEFeatures.chaoticCore, DEFeatures.chaoticCore, DEFeatures.draconicBlock, DEFeatures.draconicBlock, 
          DEFeatures.draconicBlock, DEFeatures.draconicBlock, DEFeatures.draconicBlock, DEFeatures.draconicBlock, DEFeatures.draconicBlock, DEFeatures.draconicBlock });
    RecipeManager.addFusion(RecipeManager.RecipeDifficulty.ALL, new ItemStack(EItem.draconicPortalStaff), new ItemStack(EItem.portalStaff, 1, 4), 25600000, 2, new Object[] { Blocks.field_150380_bt, Blocks.field_150380_bt, DEFeatures.chaosShard, DEFeatures.chaosShard, DEFeatures.chaosShard, DEFeatures.chaosShard, DEFeatures.dragonHeart, DEFeatures.dragonHeart, DEFeatures.draconicIngot, DEFeatures.draconicIngot });
    RecipeManager.addFusion(RecipeManager.RecipeDifficulty.ALL, new ItemStack(EItem.draconicPortalStaff, 1, 1), new ItemStack(EItem.draconicPortalStaff), 51200000, 2, new Object[] { 
          Blocks.field_150380_bt, Blocks.field_150380_bt, DEFeatures.draconicCore, DEFeatures.draconicCore, DEFeatures.chaosShard, DEFeatures.chaosShard, DEFeatures.chaosShard, DEFeatures.chaosShard, DEFeatures.draconicBlock, DEFeatures.draconicBlock, 
          Blocks.field_150461_bJ, Blocks.field_150461_bJ });
    RecipeManager.addFusion(RecipeManager.RecipeDifficulty.ALL, new ItemStack(EItem.draconicPortalStaff, 1, 2), new ItemStack(EItem.draconicPortalStaff, 1, 1), 102400000, 2, new Object[] { 
          Blocks.field_150380_bt, Blocks.field_150380_bt, DEFeatures.wyvernCore, DEFeatures.wyvernCore, DEFeatures.chaosShard, DEFeatures.chaosShard, DEFeatures.chaosShard, DEFeatures.chaosShard, DEFeatures.draconicBlock, DEFeatures.draconicBlock, 
          Blocks.field_150461_bJ, Blocks.field_150461_bJ });
    RecipeManager.addFusion(RecipeManager.RecipeDifficulty.ALL, new ItemStack(EItem.draconicPortalStaff, 1, 3), new ItemStack(EItem.draconicPortalStaff, 1, 2), 204800000, 3, new Object[] { 
          Blocks.field_150380_bt, Blocks.field_150380_bt, DEFeatures.awakenedCore, DEFeatures.awakenedCore, DEFeatures.chaosShard, DEFeatures.chaosShard, DEFeatures.chaosShard, DEFeatures.chaosShard, DEFeatures.draconicBlock, DEFeatures.draconicBlock, 
          Blocks.field_150461_bJ, Blocks.field_150461_bJ });
    RecipeManager.addFusion(RecipeManager.RecipeDifficulty.ALL, new ItemStack(EItem.draconicPortalStaff, 1, 4), new ItemStack(EItem.draconicPortalStaff, 1, 3), 409600000, 3, new Object[] { 
          Blocks.field_150380_bt, Blocks.field_150380_bt, DEFeatures.chaoticCore, DEFeatures.chaoticCore, DEFeatures.chaosShard, DEFeatures.chaosShard, DEFeatures.chaosShard, DEFeatures.chaosShard, DEFeatures.draconicBlock, DEFeatures.draconicBlock, 
          Blocks.field_150461_bJ, Blocks.field_150461_bJ });
  }
}
