package net.minecraft.AgeOfMinecraft.items;

import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityChicken;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityZombie;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemChickenJockeyItem extends ItemVanillaTier {
  public ItemChickenJockeyItem() {
    super(2, 24, 32, "chickenjockey");
  }
  
  public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    ItemStack stack = playerIn.getHeldItem(hand);
    if (net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(worldIn))
      return EnumActionResult.SUCCESS; 
    if (!playerIn.canPlayerEdit(pos.offset(facing), facing, stack))
      return EnumActionResult.FAIL; 
    EntityZombie entityliving = new EntityZombie(worldIn);
    pos = pos.offset(facing);
    entityliving.setLocationAndAngles(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, MathHelper.wrapDegrees(worldIn.rand.nextFloat() * 360.0F), 0.0F);
    net.minecraft.AgeOfMinecraft.util.EntityCompat.copyYawToHeadAndBody(entityliving);
    entityliving.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(entityliving)), null);
    entityliving.setChild(true);
    entityliving.setGrowingAge(-48000);
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(worldIn)) {
      worldIn.spawnEntity(entityliving);
      int i = 60;
      while (i > 0) {
        int j = EntityXPOrb.getXPSplit(i);
        i -= j;
        if (!worldIn.getGameRules().getBoolean("disableExpItemDrops"))
          net.minecraft.AgeOfMinecraft.util.EntityCompat.spawnXpOrbAt(entityliving, entityliving.getEyeHeight(), j);
      } 
    } 
    if (entityliving != null) {
      EntityChicken entitymount = new EntityChicken(worldIn);
      pos = pos.offset(facing);
      entitymount.setLocationAndAngles(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, MathHelper.wrapDegrees(worldIn.rand.nextFloat() * 360.0F), 0.0F);
      entitymount.rotationYawHead = entitymount.rotationYaw;
      entitymount.renderYawOffset = entitymount.rotationYaw;
      entitymount.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(entityliving)), null);
      entitymount.setChickenJockey(true);
      worldIn.spawnEntity(entitymount);
      entityliving.setOwnerId(playerIn.getUniqueID());
      entitymount.setOwnerId(playerIn.getUniqueID());
      entityliving.playLivingSound();
      entityliving.playSound(ESound.createMob, 5.0F, 1.0F);
      entityliving.startRiding(entitymount);
      if (!playerIn.capabilities.isCreativeMode)
        stack.shrink(1); 
    } 
    return EnumActionResult.SUCCESS;
  }
}
