package net.minecraft.AgeOfMinecraft.items;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderMod;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

public class ItemEngenderStatChecker extends ItemBEItem {
  public static EntityTameBase viewedEntity;
  
  public ItemEngenderStatChecker() {
    super("statchecker");
    func_77625_d(1);
  }
  
  public EnumRarity func_77613_e(ItemStack stack) {
    return EnumRarity.UNCOMMON;
  }
  
  public void func_77624_a(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    tooltip.add("View the stats of any engendered mob");
    tooltip.add(TextFormatting.GOLD + "Right click on an engendered mob to view stats");
  }
  
  public boolean func_111207_a(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
    if (target instanceof EntityTameBase) {
      EntityTameBase entitypig = (EntityTameBase)target;
      if (entitypig.func_70089_S()) {
        playerIn.func_184609_a(hand);
        viewedEntity = entitypig;
        if (playerIn.field_70170_p.field_72995_K)
          FMLNetworkHandler.openGui(playerIn, EngenderMod.instance, 100, playerIn.field_70170_p, (int)target.field_70165_t, (int)target.field_70163_u, (int)target.field_70161_v); 
      } 
      return true;
    } 
    return false;
  }
  
  public boolean func_77644_a(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
    if (attacker instanceof EntityPlayer && target instanceof EntityLivingBase)
      func_111207_a(stack, (EntityPlayer)attacker, target, EnumHand.MAIN_HAND); 
    return true;
  }
}
