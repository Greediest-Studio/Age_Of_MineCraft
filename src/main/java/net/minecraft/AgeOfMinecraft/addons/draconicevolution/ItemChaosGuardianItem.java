package net.minecraft.AgeOfMinecraft.addons.draconicevolution;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.items.ItemTierItem;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.AgeOfMinecraft.registry.ETab;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemChaosGuardianItem extends ItemTierItem {
  public ItemChaosGuardianItem() {
    super(5, 3000, 4, ETab.draconic, "chaosguardian");
  }
  
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    super.addInformation(stack, worldIn, tooltip, flagIn);
    tooltip.add(TextFormatting.DARK_RED + "(Draconic Evolution)" + TextFormatting.WHITE);
  }
  
  public EnumRarity getRarity(ItemStack stack) {
    return ESetup.UBEREPIC;
  }
  
  public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    ItemStack stack = playerIn.getHeldItem(hand);
    if (net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(worldIn))
      return EnumActionResult.SUCCESS; 
    if (!playerIn.canPlayerEdit(pos.offset(facing), facing, stack))
      return EnumActionResult.FAIL; 
    EntityChaosGuardian entityliving = new EntityChaosGuardian(worldIn);
    pos = pos.offset(facing);
    entityliving.setLocationAndAngles(pos.getX() + 0.5D, pos.getY() + 60.0D, pos.getZ() + 0.5D, MathHelper.wrapDegrees(worldIn.rand.nextFloat() * 360.0F), 0.0F);
    entityliving.setOwnerId(playerIn.getUniqueID());
    entityliving.homeX = pos.getX();
    entityliving.homeY = pos.getY() + 60;
    entityliving.homeZ = pos.getZ();
    entityliving.targetX = entityliving.homeX;
    entityliving.targetY = entityliving.homeY;
    entityliving.targetZ = entityliving.homeZ;
    entityliving.homeSet = true;
    entityliving.rotationYawHead = entityliving.rotationYaw;
    entityliving.renderYawOffset = entityliving.rotationYaw;
    entityliving.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(entityliving)), null);
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(worldIn)) {
      worldIn.spawnEntity(entityliving);
      int i = 1600000;
      while (i > 0) {
        int j = EntityXPOrb.getXPSplit(i);
        i -= j;
        if (!worldIn.getGameRules().getBoolean("disableExpItemDrops"))
          entityliving.world.spawnEntity(new EntityXPOrb(entityliving.world, entityliving.posX, entityliving.posY + entityliving.getEyeHeight() + 5.0D, entityliving.posZ, j));
      } 
    } 
    if (entityliving != null) {
      entityliving.playLivingSound();
      entityliving.playSound(ESound.createMob, 10.0F, 0.5F);
      entityliving.playSound(ESound.createBossMob, Float.MAX_VALUE, 0.75F);
      if (!playerIn.capabilities.isCreativeMode)
        stack.shrink(1); 
    } 
    return EnumActionResult.SUCCESS;
  }
}
