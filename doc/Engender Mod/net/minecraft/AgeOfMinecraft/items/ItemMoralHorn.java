package net.minecraft.AgeOfMinecraft.items;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class ItemMoralHorn extends ItemBEItem {
  public ItemMoralHorn() {
    super("moralhorn");
    func_77625_d(1);
  }
  
  public void func_77624_a(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    tooltip.add("Raise moral with this horn");
    tooltip.add(TextFormatting.GREEN + "+50% Engendered Mob Moral");
    tooltip.add(TextFormatting.GREEN + "+50% Engendered Mob Attack");
    tooltip.add(TextFormatting.GREEN + "+Speed");
    tooltip.add(TextFormatting.GREEN + "+Strength");
    tooltip.add(TextFormatting.GOLD + "Hold right click to use");
  }
  
  public EnumAction func_77661_b(ItemStack stack) {
    return EnumAction.BOW;
  }
  
  public EnumRarity func_77613_e(ItemStack stack) {
    return EnumRarity.RARE;
  }
  
  public int func_77626_a(ItemStack stack) {
    return 40;
  }
  
  public ActionResult<ItemStack> func_77659_a(World worldIn, EntityPlayer playerIn, EnumHand hand) {
    ItemStack itemStackIn = playerIn.func_184586_b(hand);
    boolean flag = (func_185060_a(playerIn) != null);
    ActionResult<ItemStack> ret = ForgeEventFactory.onArrowNock(itemStackIn, worldIn, playerIn, hand, flag);
    if (ret != null)
      return ret; 
    if (!playerIn.field_71075_bZ.field_75098_d && !flag)
      return !flag ? new ActionResult(EnumActionResult.FAIL, itemStackIn) : new ActionResult(EnumActionResult.PASS, itemStackIn); 
    playerIn.func_184598_c(hand);
    return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
  }
  
  private ItemStack func_185060_a(EntityPlayer player) {
    return player.func_184586_b(EnumHand.MAIN_HAND);
  }
  
  public ItemStack func_77654_b(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
    return onItemUseFinish(stack, worldIn, (EntityPlayer)entityLiving);
  }
  
  public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
    playerIn.field_70125_A = -45.0F;
    playerIn.field_70170_p.func_184134_a(playerIn.field_70165_t, playerIn.field_70163_u + playerIn.getDefaultEyeHeight(), playerIn.field_70161_v, getHornSound(), SoundCategory.PLAYERS, 1.0E9F, 1.0F, false);
    List<EntityLivingBase> list = playerIn.field_70170_p.func_175647_a(EntityLivingBase.class, playerIn.func_174813_aQ().func_186662_g(256.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
    if (list != null && !list.isEmpty())
      for (int i1 = 0; i1 < list.size(); i1++) {
        EntityLivingBase entity = list.get(i1);
        if (entity instanceof EntityTameBase && entity.func_70089_S() && ((EntityTameBase)entity).func_184191_r((Entity)playerIn))
          ((EntityTameBase)entity).moralRaisedTimer = 600; 
      }  
    playerIn.func_184185_a(ESound.battlecry, 10.0F, 1.0F);
    playerIn.func_70690_d(new PotionEffect(MobEffects.field_76420_g, 600, 0));
    playerIn.func_70690_d(new PotionEffect(MobEffects.field_76424_c, 600, 1));
    playerIn.func_71020_j(0.5F);
    return stack;
  }
  
  public SoundEvent getHornSound() {
    return ESound.moralHornBlow;
  }
}
