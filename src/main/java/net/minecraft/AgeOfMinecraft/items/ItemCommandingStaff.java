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
    setMaxStackSize(1);
    setHasSubtypes(true);
  }
  
  public EnumAction getItemUseAction(ItemStack stack) {
    return EnumAction.BOW;
  }
  
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    tooltip.add("Turn some of your enemy player's engendered mobs into wild engendered mobs");
    tooltip.add(TextFormatting.GREEN + "Tier 5 allows you to convert wild mobs also");
    tooltip.add(TextFormatting.GOLD + "Hold right click for 4 seconds");
  }
  
  public EnumRarity getRarity(ItemStack stack) {
    switch (stack.getMetadata()) {
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
  public boolean hasEffect(ItemStack stack) {
    return (super.hasEffect(stack) || stack.getMetadata() > 3);
  }
  
  public int getMaxItemUseDuration(ItemStack stack) {
    return 10;
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
    playerIn.world.playSound(playerIn, new BlockPos((Entity)playerIn), SoundEvents.ENTITY_WITHER_AMBIENT, SoundCategory.PLAYERS, 100.0F, 0.5F);
    return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
  }
  
  private ItemStack findAmmo(EntityPlayer player) {
    return player.getHeldItem(EnumHand.MAIN_HAND);
  }
  
  public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
    return onItemUseFinish(stack, worldIn, (EntityPlayer)entityLiving);
  }
  
  public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
    List<EntityTameBase> list = playerIn.world.getEntitiesWithinAABB(EntityTameBase.class, playerIn.getEntityBoundingBox().grow(32.0D), Predicates.and(new Predicate[] { EntitySelectors.NOT_SPECTATING }));
    if (!playerIn.world.isRemote && list != null)
      if (!list.isEmpty()) {
        playerIn.world.playSound(null, new BlockPos((Entity)playerIn), ESound.chaos, SoundCategory.PLAYERS, 100.0F, 1.0F);
        if (!playerIn.world.isRemote)
          playerIn.sendMessage((ITextComponent)new TextComponentTranslation("Nearby mobs are deserting their original alliances!", new Object[0])); 
        for (int i1 = 0; i1 < list.size(); i1++) {
          EntityTameBase entity = list.get(i1);
          if (entity != null && entity.getOwnerId() != playerIn.getUniqueID() && entity.affectedByCommandingStaff() && entity.getTier() != EnumTier.TIER6 && entity.isEntityAlive() && (entity.getTier() != EnumTier.TIER5 || stack.getMetadata() > 3) && (entity.getTier() != EnumTier.TIER4 || stack.getMetadata() > 2) && (entity.getTier() != EnumTier.TIER3 || stack.getMetadata() > 1) && (entity.getTier() != EnumTier.TIER2 || stack.getMetadata() > 0) && i1 < stack.getMetadata() * entity.getRNG().nextInt(4 * stack.getMetadata())) {
            if (entity.getOwnerId() == null && stack.getMetadata() > 3) {
              playerIn.getCooldownTracker().setCooldown(this, 20);
              entity.ticksExisted = 0;
              entity.setOwnerId(playerIn.getUniqueID());
              entity.setAttackTarget(null);
              entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 40, 0));
              entity.getOwner().sendMessage((ITextComponent)new TextComponentTranslation("A " + entity.getName() + " has joined your side!", new Object[0]));
            } 
            if (!entity.isWild() && entity.getOwnerId() != playerIn.getUniqueID()) {
              playerIn.getCooldownTracker().setCooldown(this, 10);
              entity.ticksExisted = 0;
              entity.getOwner().sendMessage((ITextComponent)new TextComponentTranslation("Your " + entity.getName() + " has deserted you!", new Object[0]));
              entity.setAttackTarget(null);
              entity.setOwnerId(null);
              entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 40, 0));
            } 
          } 
        } 
      }  
    return stack;
  }
  
  public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
    if (isInCreativeTab(tab))
      for (int i = 0; i <= 4; i++)
        items.add(new ItemStack(this, 1, i));  
  }
}
