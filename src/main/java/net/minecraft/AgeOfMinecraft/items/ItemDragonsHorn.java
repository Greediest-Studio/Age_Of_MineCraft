package net.minecraft.AgeOfMinecraft.items;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonBoss;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonMinion;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.asorahphases.PhaseListAsorah;
import net.minecraft.AgeOfMinecraft.addons.draconicevolution.EntityChaosGuardian;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases.PhaseList;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class ItemDragonsHorn extends ItemBEItem {
  public ItemDragonsHorn() {
    super("enderdragonshorn");
    setMaxStackSize(1);
  }
  
  public EnumAction getItemUseAction(ItemStack stack) {
    return EnumAction.BOW;
  }
  
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    tooltip.add("Raise moral with this horn, and call back your dragons");
    tooltip.add(TextFormatting.RED + "All hostile mobs aggro to you");
    tooltip.add(TextFormatting.GREEN + "Damages all nearby mobs");
    tooltip.add(TextFormatting.GREEN + "+50% Engendered Mob Moral");
    tooltip.add(TextFormatting.GREEN + "+50% Engendered Mob Attack");
    tooltip.add(TextFormatting.GREEN + "+Speed");
    tooltip.add(TextFormatting.GREEN + "+Strength");
    tooltip.add(TextFormatting.GOLD + "Hold right click to use");
  }
  
  public int getMaxItemUseDuration(ItemStack stack) {
    return 80;
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
  
  public boolean hasEffect(ItemStack stack) {
    return true;
  }
  
  public EnumRarity getRarity(ItemStack stack) {
    return EnumRarity.EPIC;
  }
  
  public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
    return onItemUseFinish(stack, worldIn, (EntityPlayer)entityLiving);
  }
  
  public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
    playerIn.rotationPitch = -75.0F;
    playerIn.world.playSound(playerIn.posX, playerIn.posY + playerIn.getDefaultEyeHeight(), playerIn.posZ, getHornSound(), SoundCategory.PLAYERS, 1.0E9F, 1.0F, false);
    List<EntityLivingBase> list = playerIn.world.getEntitiesWithinAABB(EntityLivingBase.class, playerIn.getEntityBoundingBox().grow(512.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
    if (list != null && !list.isEmpty())
        for (EntityLivingBase entity : list) {
            if (entity instanceof EntityEnderDragon && entity != null && entity.isEntityAlive() && ((EntityEnderDragon) entity).getOwnerId() == playerIn.getUniqueID()) {
                ((EntityEnderDragon) entity).getPhaseManager().setPhase(PhaseList.LANDING_APPROACH);
                ((EntityEnderDragon) entity).playLivingSound();
            }
            if (entity instanceof EntityDragonBoss && entity != null && entity.isEntityAlive() && ((EntityDragonBoss) entity).getOwnerId() == playerIn.getUniqueID()) {
                ((EntityDragonBoss) entity).getPhaseManager().setPhase(PhaseListAsorah.LANDING_APPROACH);
                ((EntityDragonBoss) entity).playLivingSound();
            }
            if (entity instanceof EntityChaosGuardian && entity != null && entity.isEntityAlive() && ((EntityChaosGuardian) entity).getOwnerId() == playerIn.getUniqueID()) {
                ((EntityChaosGuardian) entity).setToGuard();
                ((EntityChaosGuardian) entity).playLivingSound();
            }
            if (entity instanceof EntityDragonMinion && entity != null && entity.isEntityAlive() && ((EntityDragonMinion) entity).getOwnerId() == playerIn.getUniqueID()) {
                ((EntityDragonMinion) entity).target = (Entity) playerIn;
                ((EntityDragonMinion) entity).playLivingSound();
            }
            if (entity instanceof EntityMob) {
                ((EntityMob) entity).setAttackTarget((EntityLivingBase) playerIn);
                ((EntityMob) entity).setRevengeTarget((EntityLivingBase) playerIn);
                ((EntityMob) entity).getMoveHelper().setMoveTo(playerIn.posX, playerIn.posY, playerIn.posY, 1.2D);
                if (playerIn.getDistanceSq((Entity) entity) <= 64.0D)
                    ((EntityMob) entity).attackEntityFrom(DamageSource.GENERIC, 50.0F);
            }
        }
    List<EntityLivingBase> list1 = playerIn.world.getEntitiesWithinAABB(EntityLivingBase.class, playerIn.getEntityBoundingBox().grow(256.0D, 256.0D, 256.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
    if (list1 != null && !list1.isEmpty())
        for (EntityLivingBase entity : list1) {
            if (entity instanceof EntityTameBase && entity != null && entity.isEntityAlive() && ((EntityTameBase) entity).getOwner() == playerIn)
                ((EntityTameBase) entity).moralRaisedTimer = 600;
        }
    playerIn.playSound(ESound.battlecry, 10.0F, 1.0F);
    playerIn.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 600, 0));
    playerIn.addPotionEffect(new PotionEffect(MobEffects.SPEED, 600, 1));
    playerIn.addExhaustion(2.0F);
    return stack;
  }
  
  public SoundEvent getHornSound() {
    return ESound.dragonHornBlow;
  }
}
