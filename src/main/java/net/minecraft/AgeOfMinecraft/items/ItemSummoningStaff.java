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
    setMaxStackSize(1);
    setHasSubtypes(true);
  }
  
  public EnumAction getItemUseAction(ItemStack stack) {
    return EnumAction.BOW;
  }
  
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    tooltip.add("Teleport all of your engendered mobs to you");
    tooltip.add(TextFormatting.GREEN + "Tier 5 allows you to convert all wild engendered mobs also");
    tooltip.add(TextFormatting.GOLD + "Hold right click to use");
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
    playerIn.world.playSound(playerIn, new BlockPos((Entity)playerIn), SoundEvents.BLOCK_BREWING_STAND_BREW, SoundCategory.PLAYERS, 100.0F, 1.0F);
    return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
  }
  
  private ItemStack findAmmo(EntityPlayer player) {
    return player.getHeldItem(EnumHand.MAIN_HAND);
  }
  
  public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
    return onItemUseFinish(stack, worldIn, (EntityPlayer)entityLiving);
  }
  
  public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
    List<EntityTameBase> list = worldIn.getEntitiesWithinAABB(EntityTameBase.class, playerIn.getEntityBoundingBox().grow(64.0D + stack.getMetadata() * 32.0D), Predicates.and(new Predicate[] { EntitySelectors.NOT_SPECTATING }));
    List<?> everywhere = worldIn.loadedEntityList;
    if (list != null)
      if (!list.isEmpty()) {
        if (!playerIn.world.isRemote)
          playerIn.sendMessage((ITextComponent)new TextComponentTranslation("You summon your mobs!", new Object[0])); 
        playerIn.world.playSound(playerIn, new BlockPos((Entity)playerIn), SoundEvents.BLOCK_PORTAL_TRAVEL, SoundCategory.PLAYERS, 100.0F, 1.0F);
        for (int i1 = 0; i1 < list.size(); i1++) {
          EntityTameBase entity = list.get(i1);
          if (entity != null && entity.isEntityAlive() && (entity.getTier() != EnumTier.TIER6 || stack.getMetadata() > 2) && (entity.getTier() != EnumTier.TIER5 || stack.getMetadata() > 0)) {
            playerIn.addExhaustion(0.05F);
            playerIn.getCooldownTracker().setCooldown(this, 1200 + i1);
            entity.ticksExisted = 0;
            entity.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
            if ((entity.isWild() && entity.getOwnerId() == null && stack.getMetadata() > 1) || (!entity.isWild() && entity.getOwnerId() == playerIn.getUniqueID())) {
              entity.setOwnerId(playerIn.getUniqueID());
              entity.setLocationAndAngles(playerIn.prevPosX, playerIn.prevPosY, playerIn.prevPosZ, playerIn.rotationYawHead, 0.0F);
            } 
          } 
        } 
      }  
    if (everywhere != null && stack.getMetadata() > 3)
      if (!everywhere.isEmpty()) {
        if (!playerIn.world.isRemote && worldIn.getGameRules().getBoolean("showMobDeathMessages"))
          playerIn.sendMessage((ITextComponent)new TextComponentTranslation("You summon your mobs!", new Object[0])); 
        playerIn.world.playSound(playerIn, new BlockPos((Entity)playerIn), SoundEvents.BLOCK_PORTAL_TRAVEL, SoundCategory.PLAYERS, 100.0F, 1.0F);
        for (int i1 = 0; i1 < list.size(); i1++) {
          Entity entity = (Entity)list.get(i1);
          if (entity != null && entity.isEntityAlive() && entity instanceof EntityTameBase) {
            playerIn.addExhaustion(0.05F);
            playerIn.getCooldownTracker().setCooldown(this, 1200 + i1);
            entity.ticksExisted = 0;
            entity.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
            if ((((EntityTameBase)entity).isWild() && ((EntityTameBase)entity).getOwnerId() == null && stack.getMetadata() > 1) || (!((EntityTameBase)entity).isWild() && ((EntityTameBase)entity).getOwnerId() == playerIn.getUniqueID())) {
              ((EntityTameBase)entity).setOwnerId(playerIn.getUniqueID());
              entity.setLocationAndAngles(playerIn.prevPosX, playerIn.prevPosY, playerIn.prevPosZ, playerIn.rotationYawHead, 0.0F);
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
