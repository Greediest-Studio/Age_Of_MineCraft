package net.minecraft.AgeOfMinecraft.items;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemLastChance extends ItemBEItem {
  public ItemLastChance() {
    super("last_chance");
    func_77625_d(1);
  }
  
  @SideOnly(Side.CLIENT)
  public boolean func_77636_d(ItemStack stack) {
    return true;
  }
  
  public EnumRarity func_77613_e(ItemStack stack) {
    return EnumRarity.EPIC;
  }
  
  public void func_77624_a(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    tooltip.add("Give your mob an extra life upon death");
    tooltip.add(TextFormatting.RED + "Does not work with tier 6 engendered mobs");
  }
  
  public boolean func_111207_a(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
    if (target instanceof EntityTameBase) {
      EntityTameBase entity = (EntityTameBase)target;
      if (!entity.hasLastChance() && entity.getTier() != EnumTier.TIER6) {
        entity.setLastChance(true);
        entity.func_184185_a(SoundEvents.field_187557_bK, 1.0F, 1.0F);
        entity.func_70656_aK();
        playerIn.func_184609_a(hand);
        playerIn.func_70669_a(stack);
        if (!entity.hasLimitedLife())
          stack.func_190918_g(1); 
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
