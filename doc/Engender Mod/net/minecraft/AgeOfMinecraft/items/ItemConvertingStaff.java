package net.minecraft.AgeOfMinecraft.items;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemConvertingStaff extends ItemBEItem {
  public ItemConvertingStaff() {
    super("convertingstaff");
    func_77625_d(1);
    func_77627_a(true);
  }
  
  public EnumAction func_77661_b(ItemStack stack) {
    return EnumAction.BOW;
  }
  
  public void func_77624_a(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    tooltip.add("Converts wild Engender mobs to your side");
    tooltip.add(TextFormatting.RED + "Higher tier engendered mobs take longer");
    tooltip.add(TextFormatting.GOLD + "Hold right click to start converting");
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
    return 20;
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
    List<EntityTameBase> list = playerIn.field_70170_p.func_175647_a(EntityTameBase.class, playerIn.func_174813_aQ().func_186662_g(10.0D * (getDamage(stack) + 1)), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
    if (list != null)
      if (!list.isEmpty()) {
        for (int i1 = 0; i1 < list.size(); i1++) {
          EntityTameBase entity = list.get(i1);
          if (entity != null && entity.func_70089_S() && entity.isWild() && entity.func_184753_b() == null && !entity.isABoss() && entity.getTier() != EnumTier.TIER6) {
            if (entity.convertionInt <= 0)
              if (!playerIn.field_70170_p.field_72995_K)
                playerIn.func_145747_a((ITextComponent)new TextComponentTranslation(entity.func_70005_c_() + " is wild. Starting conversion...", new Object[0]));  
            playerIn.func_184811_cZ().func_185145_a(this, 10);
            for (int times = 0; times <= stack.func_77960_j(); times++)
              entity.incrementConversion(playerIn); 
          } 
        } 
      } else if (!playerIn.field_70170_p.field_72995_K) {
        playerIn.func_145747_a((ITextComponent)new TextComponentTranslation("No engender mobs are in your vicinity.", new Object[0]));
      }  
    return stack;
  }
  
  public void func_150895_a(CreativeTabs tab, NonNullList<ItemStack> items) {
    if (func_194125_a(tab))
      for (int i = 0; i <= 4; i++)
        items.add(new ItemStack(this, 1, i));  
  }
}
