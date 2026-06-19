package net.minecraft.AgeOfMinecraft.items;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemTrainingStick extends ItemBEItem {
  public ItemTrainingStick() {
    super("trainingstick");
    setMaxStackSize(1);
  }
  
  public EnumRarity getRarity(ItemStack stack) {
    return EnumRarity.UNCOMMON;
  }
  
  public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand) {
    return new ActionResult(EnumActionResult.PASS, player.getHeldItem(hand));
  }
  
  public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand) {
    return false;
  }
  
  public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
    if (attacker instanceof EntityPlayer && target instanceof EntityLivingBase)
      itemInteractionForEntity(stack, (EntityPlayer)attacker, target, EnumHand.MAIN_HAND); 
    return true;
  }
  
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    tooltip.add("Force your engendered mobs to train in combat");
    tooltip.add(TextFormatting.GOLD + "Right click a mob to activate only that mob");
    tooltip.add(TextFormatting.GOLD + "Left click a mob to deactivate only that mob");
    tooltip.add(TextFormatting.GOLD + "Right click anywhere to activate all nearby mobs");
    tooltip.add(TextFormatting.GOLD + "Shift right anywhere click to deactivate all nearby mobs");
  }
}
