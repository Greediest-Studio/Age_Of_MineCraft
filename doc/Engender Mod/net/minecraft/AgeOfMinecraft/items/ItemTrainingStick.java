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
    func_77625_d(1);
  }
  
  public EnumRarity func_77613_e(ItemStack stack) {
    return EnumRarity.UNCOMMON;
  }
  
  public ActionResult<ItemStack> func_77659_a(World worldIn, EntityPlayer player, EnumHand hand) {
    List<EntityTameBase> list = worldIn.func_175647_a(EntityTameBase.class, player.func_174813_aQ().func_186662_g(32.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
    if (!list.isEmpty()) {
      player.func_184609_a(hand);
      for (EntityTameBase mob : list) {
        if (!player.field_70170_p.field_72995_K && mob != null && mob.func_70089_S() && mob.func_184191_r((Entity)player)) {
          if (player.func_70093_af()) {
            mob.setFakeHealth(0.0F);
          } else {
            mob.setFakeHealth(mob.func_110138_aP() * 2.0F);
            mob.func_70624_b(null);
            mob.func_70661_as().func_75499_g();
          } 
          mob.incrementConversion(player);
        } 
      } 
      if (!player.field_70170_p.field_72995_K)
        if (player.func_70093_af()) {
          player.func_145747_a((ITextComponent)new TextComponentTranslation("Your mobs have stopped training.", new Object[0]));
        } else {
          player.func_145747_a((ITextComponent)new TextComponentTranslation("Your mobs are ready to train!", new Object[0]));
        }  
      return new ActionResult(EnumActionResult.SUCCESS, player.func_184586_b(hand));
    } 
    return new ActionResult(EnumActionResult.FAIL, player.func_184586_b(hand));
  }
  
  public boolean func_111207_a(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand) {
    if (target instanceof EntityTameBase) {
      EntityTameBase mob = (EntityTameBase)target;
      player.func_184609_a(hand);
      if (!player.field_70170_p.field_72995_K && mob != null && mob.func_70089_S() && mob.func_184191_r((Entity)player)) {
        mob.setFakeHealth(mob.func_110138_aP() * 2.0F);
        mob.func_70624_b(null);
        mob.func_70661_as().func_75499_g();
        mob.incrementConversion(player);
        if (stack.func_82837_s())
          mob.fakeTeam = func_77653_i(stack); 
      } 
      if (!player.field_70170_p.field_72995_K)
        player.func_145747_a((ITextComponent)new TextComponentTranslation(target.func_70005_c_() + " is ready to train!", new Object[0])); 
      return true;
    } 
    return false;
  }
  
  public boolean func_77644_a(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
    if (attacker instanceof EntityPlayer && target instanceof EntityLivingBase)
      func_111207_a(stack, (EntityPlayer)attacker, target, EnumHand.MAIN_HAND); 
    return true;
  }
  
  public void func_77624_a(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    tooltip.add("Force your engendered mobs to train in combat");
    tooltip.add(TextFormatting.GOLD + "Right click a mob to activate only that mob");
    tooltip.add(TextFormatting.GOLD + "Left click a mob to deactivate only that mob");
    tooltip.add(TextFormatting.GOLD + "Right click anywhere to activate all nearby mobs");
    tooltip.add(TextFormatting.GOLD + "Shift right anywhere click to deactivate all nearby mobs");
  }
}
