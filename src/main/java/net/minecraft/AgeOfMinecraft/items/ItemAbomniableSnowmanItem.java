package net.minecraft.AgeOfMinecraft.items;

import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityAbomniableSnowman;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemAbomniableSnowmanItem extends ItemMCSMTier {
  public ItemAbomniableSnowmanItem() {
    super(4, 300, 4, "abomniablesnowman");
  }
  
  public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    ItemStack stack = playerIn.getHeldItem(hand);
    if (net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(worldIn))
      return EnumActionResult.SUCCESS; 
    if (!playerIn.canPlayerEdit(pos.offset(facing), facing, stack))
      return EnumActionResult.FAIL; 
    EntityAbomniableSnowman entityliving = new EntityAbomniableSnowman(worldIn);
    pos = pos.offset(facing);
    entityliving.setLocationAndAngles(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, MathHelper.wrapDegrees(worldIn.rand.nextFloat() * 360.0F), 0.0F);
    net.minecraft.AgeOfMinecraft.util.EntityCompat.copyYawToHeadAndBody(entityliving);
    entityliving.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(entityliving)), null);
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(worldIn)) {
      worldIn.spawnEntity(entityliving);
      int i = 12000;
      while (i > 0) {
        int j = EntityXPOrb.getXPSplit(i);
        i -= j;
        if (!worldIn.getGameRules().getBoolean("disableExpItemDrops"))
          net.minecraft.AgeOfMinecraft.util.EntityCompat.spawnXpOrbAt(entityliving, entityliving.getEyeHeight(), j);
      } 
    } 
    if (entityliving != null) {
      entityliving.setOwnerId(playerIn.getUniqueID());
      if (playerIn instanceof EntityPlayerMP)
        CriteriaTriggers.SUMMONED_ENTITY.trigger((EntityPlayerMP)playerIn, entityliving);
      entityliving.playLivingSound();
      entityliving.playSound(ESound.createMob, 10.0F, 0.75F);
      entityliving.playSound(ESound.createBossMob, 1.0E7F, 1.0F);
      if (!playerIn.capabilities.isCreativeMode)
        stack.shrink(1); 
    } 
    return EnumActionResult.SUCCESS;
  }
}
