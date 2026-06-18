package net.minecraft.AgeOfMinecraft.items;

import com.shinoow.abyssalcraft.lib.util.SpecialTextUtil;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class ItemHeroMaker extends ItemBEItem {
  public ItemHeroMaker() {
    super("heromaker");
    func_77625_d(1);
  }
  
  public EnumRarity func_77613_e(ItemStack stack) {
    return EnumRarity.UNCOMMON;
  }
  
  public void func_77624_a(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    tooltip.add("Turns an engendered mob into a hero engendered mob");
    tooltip.add(TextFormatting.GREEN + "Boosts all stats and gives special abilities");
    tooltip.add(TextFormatting.GOLD + "Right click on an engendered mob to use");
  }
  
  public boolean func_111207_a(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
    if (target instanceof EntityTameBase) {
      EntityTameBase entity = (EntityTameBase)target;
      if (!entity.field_70170_p.field_72995_K && entity instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityJzahar)
        SpecialTextUtil.JzaharGroup(entity.field_70170_p, new String[] { I18n.func_74838_a("message.jzahar.denyheroism") }); 
      if (EngenderConfig.debugMode && entity.func_184753_b() != playerIn.func_110124_au()) {
        entity.setOwnerId(playerIn.func_110124_au());
        playerIn.func_184609_a(hand);
        return true;
      } 
      if (EngenderConfig.mobs.useHeros && !entity.isHero() && entity.getTier() != EnumTier.TIER6) {
        if (playerIn instanceof EntityPlayerMP)
          CriteriaTriggers.field_193138_y.func_193148_a((EntityPlayerMP)playerIn, stack); 
        entity.becomeAHero();
        playerIn.func_184609_a(hand);
        playerIn.func_70669_a(stack);
        if (!entity.hasLimitedLife())
          stack.func_190918_g(1); 
        return true;
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
