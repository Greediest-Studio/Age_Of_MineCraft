package net.minecraft.AgeOfMinecraft.items;

import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.client.util.ITooltipFlag;
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
    setMaxStackSize(1);
  }
  
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    tooltip.add("Raise moral with this horn");
    tooltip.add(TextFormatting.GREEN + "+50% Engendered Mob Moral");
    tooltip.add(TextFormatting.GREEN + "+50% Engendered Mob Attack");
    tooltip.add(TextFormatting.GREEN + "+Speed");
    tooltip.add(TextFormatting.GREEN + "+Strength");
    tooltip.add(TextFormatting.GOLD + "Hold right click to use");
  }
  
  public EnumAction getItemUseAction(ItemStack stack) {
    return EnumAction.BOW;
  }
  
  public EnumRarity getRarity(ItemStack stack) {
    return EnumRarity.RARE;
  }
  
  public int getMaxItemUseDuration(ItemStack stack) {
    return 40;
  }
  
  public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
    ItemStack itemStackIn = playerIn.getHeldItem(hand);
    boolean flag = (findAmmo(playerIn) != null);
    ActionResult<ItemStack> ret = ForgeEventFactory.onArrowNock(itemStackIn, worldIn, playerIn, hand, flag);
    if (ret != null)
      return ret; 
    if (!playerIn.capabilities.isCreativeMode && !flag)
      return !flag ? new ActionResult(EnumActionResult.FAIL, itemStackIn) : new ActionResult(EnumActionResult.PASS, itemStackIn); 
    playerIn.setActiveHand(hand);
    return new ActionResult<>(EnumActionResult.SUCCESS, itemStackIn);
  }
  
  private ItemStack findAmmo(EntityPlayer player) {
    return player.getHeldItem(EnumHand.MAIN_HAND);
  }
  
  public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
    return onItemUseFinish(stack, worldIn, (EntityPlayer)entityLiving);
  }
  
  public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
    playerIn.rotationPitch = -45.0F;
    playerIn.world.playSound(playerIn.posX, playerIn.posY + playerIn.getDefaultEyeHeight(), playerIn.posZ, getHornSound(), SoundCategory.PLAYERS, 1.0E9F, 1.0F, false);
    List<EntityLivingBase> list = playerIn.world.getEntitiesWithinAABB(EntityLivingBase.class, playerIn.getEntityBoundingBox().grow(256.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
    if (list != null && !list.isEmpty())
        for (EntityLivingBase entity : list) {
            if (entity instanceof EntityTameBase && entity.isEntityAlive())
                ((EntityTameBase) entity).moralRaisedTimer = 600;
        }
    playerIn.playSound(ESound.battlecry, 10.0F, 1.0F);
    playerIn.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 600, 0));
    playerIn.addPotionEffect(new PotionEffect(MobEffects.SPEED, 600, 1));
    playerIn.addExhaustion(0.5F);
    return stack;
  }
  
  public SoundEvent getHornSound() {
    return ESound.moralHornBlow;
  }
}
