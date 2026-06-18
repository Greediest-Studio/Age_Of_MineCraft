package net.minecraft.AgeOfMinecraft.items;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCommandingStaff extends ItemBEItem {
  public ItemCommandingStaff() {
    super("commandingstaff");
    func_77625_d(1);
    func_77627_a(true);
  }
  
  public EnumAction func_77661_b(ItemStack stack) {
    return EnumAction.BOW;
  }
  
  public void func_77624_a(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    tooltip.add("Turn some of your enemy player's engendered mobs into wild engendered mobs");
    tooltip.add(TextFormatting.GREEN + "Tier 5 allows you to convert wild mobs also");
    tooltip.add(TextFormatting.GOLD + "Hold right click for 4 seconds");
  }
  
  public EnumRarity func_77613_e(ItemStack stack) {
    switch (stack.func_77960_j()) {
      case 0:
        return EnumRarity.COMMON;
      case 1:
        return EnumRarity.UNCOMMON;
      case 2:
        return EnumRarity.RARE;
      case 3:
        return EnumRarity.EPIC;
      case 4:
        return ESetup.SUPEREPIC;
    } 
    return ESetup.UBEREPIC;
  }
  
  @SideOnly(Side.CLIENT)
  public boolean func_77636_d(ItemStack stack) {
    return (super.func_77636_d(stack) || stack.func_77960_j() > 3);
  }
  
  public int func_77626_a(ItemStack stack) {
    return 10;
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
    playerIn.field_70170_p.func_184133_a(playerIn, new BlockPos((Entity)playerIn), SoundEvents.field_187925_gy, SoundCategory.PLAYERS, 100.0F, 0.5F);
    return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
  }
  
  private ItemStack func_185060_a(EntityPlayer player) {
    return player.func_184586_b(EnumHand.MAIN_HAND);
  }
  
  public ItemStack func_77654_b(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
    return onItemUseFinish(stack, worldIn, (EntityPlayer)entityLiving);
  }
  
  public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
    List<EntityTameBase> list = playerIn.field_70170_p.func_175647_a(EntityTameBase.class, playerIn.func_174813_aQ().func_186662_g(32.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
    if (!playerIn.field_70170_p.field_72995_K && list != null)
      if (!list.isEmpty()) {
        playerIn.field_70170_p.func_184133_a(null, new BlockPos((Entity)playerIn), ESound.chaos, SoundCategory.PLAYERS, 100.0F, 1.0F);
        if (!playerIn.field_70170_p.field_72995_K)
          playerIn.func_145747_a((ITextComponent)new TextComponentTranslation("Nearby mobs are deserting their original alliances!", new Object[0])); 
        for (int i1 = 0; i1 < list.size(); i1++) {
          EntityTameBase entity = list.get(i1);
          if (entity != null && entity.func_184753_b() != playerIn.func_110124_au() && entity.affectedByCommandingStaff() && entity.getTier() != EnumTier.TIER6 && entity.func_70089_S() && (entity.getTier() != EnumTier.TIER5 || stack.func_77960_j() > 3) && (entity.getTier() != EnumTier.TIER4 || stack.func_77960_j() > 2) && (entity.getTier() != EnumTier.TIER3 || stack.func_77960_j() > 1) && (entity.getTier() != EnumTier.TIER2 || stack.func_77960_j() > 0) && i1 < stack.func_77960_j() * entity.func_70681_au().nextInt(4 * stack.func_77960_j())) {
            if (entity.func_184753_b() == null && stack.func_77960_j() > 3) {
              playerIn.func_184811_cZ().func_185145_a(this, 20);
              entity.field_70173_aa = 0;
              entity.setOwnerId(playerIn.func_110124_au());
              entity.func_70624_b(null);
              entity.func_70690_d(new PotionEffect(MobEffects.field_76431_k, 40, 0));
              entity.getOwner().func_145747_a((ITextComponent)new TextComponentTranslation("A " + entity.func_70005_c_() + " has joined your side!", new Object[0]));
            } 
            if (!entity.isWild() && entity.func_184753_b() != playerIn.func_110124_au()) {
              playerIn.func_184811_cZ().func_185145_a(this, 10);
              entity.field_70173_aa = 0;
              entity.getOwner().func_145747_a((ITextComponent)new TextComponentTranslation("Your " + entity.func_70005_c_() + " has deserted you!", new Object[0]));
              entity.func_70624_b(null);
              entity.setOwnerId(null);
              entity.func_70690_d(new PotionEffect(MobEffects.field_76431_k, 40, 0));
            } 
          } 
        } 
      }  
    return stack;
  }
  
  public void func_150895_a(CreativeTabs tab, NonNullList<ItemStack> items) {
    if (func_194125_a(tab))
      for (int i = 0; i <= 4; i++)
        items.add(new ItemStack(this, 1, i));  
  }
}
