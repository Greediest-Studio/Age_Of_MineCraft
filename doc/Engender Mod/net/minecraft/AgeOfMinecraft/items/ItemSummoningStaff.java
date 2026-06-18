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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
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

public class ItemSummoningStaff extends ItemBEItem {
  public ItemSummoningStaff() {
    super("summoningstaff");
    func_77625_d(1);
    func_77627_a(true);
  }
  
  public EnumAction func_77661_b(ItemStack stack) {
    return EnumAction.BOW;
  }
  
  public void func_77624_a(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    tooltip.add("Teleport all of your engendered mobs to you");
    tooltip.add(TextFormatting.GREEN + "Tier 5 allows you to convert all wild engendered mobs also");
    tooltip.add(TextFormatting.GOLD + "Hold right click to use");
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
    playerIn.field_70170_p.func_184133_a(playerIn, new BlockPos((Entity)playerIn), SoundEvents.field_187621_J, SoundCategory.PLAYERS, 100.0F, 1.0F);
    return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
  }
  
  private ItemStack func_185060_a(EntityPlayer player) {
    return player.func_184586_b(EnumHand.MAIN_HAND);
  }
  
  public ItemStack func_77654_b(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
    return onItemUseFinish(stack, worldIn, (EntityPlayer)entityLiving);
  }
  
  public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
    List<EntityTameBase> list = worldIn.func_175647_a(EntityTameBase.class, playerIn.func_174813_aQ().func_186662_g(64.0D + stack.func_77960_j() * 32.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
    List<?> everywhere = worldIn.field_72996_f;
    if (list != null)
      if (!list.isEmpty()) {
        if (!playerIn.field_70170_p.field_72995_K)
          playerIn.func_145747_a((ITextComponent)new TextComponentTranslation("You summon your mobs!", new Object[0])); 
        playerIn.field_70170_p.func_184133_a(playerIn, new BlockPos((Entity)playerIn), SoundEvents.field_187812_eh, SoundCategory.PLAYERS, 100.0F, 1.0F);
        for (int i1 = 0; i1 < list.size(); i1++) {
          EntityTameBase entity = list.get(i1);
          if (entity != null && entity.func_70089_S() && (entity.getTier() != EnumTier.TIER6 || stack.func_77960_j() > 2) && (entity.getTier() != EnumTier.TIER5 || stack.func_77960_j() > 0)) {
            playerIn.func_71020_j(0.05F);
            playerIn.func_184811_cZ().func_185145_a(this, 1200 + i1);
            entity.field_70173_aa = 0;
            entity.func_184185_a(SoundEvents.field_187534_aX, 1.0F, 1.0F);
            if ((entity.isWild() && entity.func_184753_b() == null && stack.func_77960_j() > 1) || (!entity.isWild() && entity.func_184753_b() == playerIn.func_110124_au())) {
              entity.setOwnerId(playerIn.func_110124_au());
              entity.func_70012_b(playerIn.field_70169_q, playerIn.field_70167_r, playerIn.field_70166_s, playerIn.field_70759_as, 0.0F);
            } 
          } 
        } 
      }  
    if (everywhere != null && stack.func_77960_j() > 3)
      if (!everywhere.isEmpty()) {
        if (!playerIn.field_70170_p.field_72995_K && worldIn.func_82736_K().func_82766_b("showMobDeathMessages"))
          playerIn.func_145747_a((ITextComponent)new TextComponentTranslation("You summon your mobs!", new Object[0])); 
        playerIn.field_70170_p.func_184133_a(playerIn, new BlockPos((Entity)playerIn), SoundEvents.field_187812_eh, SoundCategory.PLAYERS, 100.0F, 1.0F);
        for (int i1 = 0; i1 < list.size(); i1++) {
          Entity entity = (Entity)list.get(i1);
          if (entity != null && entity.func_70089_S() && entity instanceof EntityTameBase) {
            playerIn.func_71020_j(0.05F);
            playerIn.func_184811_cZ().func_185145_a(this, 1200 + i1);
            entity.field_70173_aa = 0;
            entity.func_184185_a(SoundEvents.field_187534_aX, 1.0F, 1.0F);
            if ((((EntityTameBase)entity).isWild() && ((EntityTameBase)entity).func_184753_b() == null && stack.func_77960_j() > 1) || (!((EntityTameBase)entity).isWild() && ((EntityTameBase)entity).func_184753_b() == playerIn.func_110124_au())) {
              ((EntityTameBase)entity).setOwnerId(playerIn.func_110124_au());
              entity.func_70012_b(playerIn.field_70169_q, playerIn.field_70167_r, playerIn.field_70166_s, playerIn.field_70759_as, 0.0F);
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
