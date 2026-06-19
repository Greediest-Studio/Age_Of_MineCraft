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
    ItemCompat.setMaxStackSize(this, 1);
  }
  
  public EnumRarity getRarity(ItemStack stack) {
    return EnumRarity.UNCOMMON;
  }
  
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    tooltip.add("Turns an engendered mob into a hero engendered mob");
    tooltip.add(TextFormatting.GREEN + "Boosts all stats and gives special abilities");
    tooltip.add(TextFormatting.GOLD + "Right click on an engendered mob to use");
  }
  
  public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
    if (target instanceof EntityTameBase) {
      EntityTameBase entity = (EntityTameBase)target;
      if (!entity.world.isRemote && entity instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityJzahar)
        SpecialTextUtil.JzaharGroup(entity.world, I18n.translateToLocal("message.jzahar.denyheroism"));
      if (EngenderConfig.mobs.useHeros && !entity.isHero() && entity.getTier() != EnumTier.TIER6) {
        if (playerIn instanceof EntityPlayerMP)
          CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP)playerIn, stack); 
        entity.becomeAHero();
        playerIn.swingArm(hand);
        playerIn.renderBrokenItemStack(stack);
        if (!entity.hasLimitedLife())
          stack.shrink(1); 
        return true;
      } 
      return true;
    } 
    return false;
  }
  
  public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
    if (attacker instanceof EntityPlayer && target instanceof EntityLivingBase)
      itemInteractionForEntity(stack, (EntityPlayer)attacker, target, EnumHand.MAIN_HAND); 
    return true;
  }
}
