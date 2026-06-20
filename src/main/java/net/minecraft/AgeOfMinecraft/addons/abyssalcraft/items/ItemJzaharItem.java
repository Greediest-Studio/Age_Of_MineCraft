package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityJzahar;
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

public class ItemJzaharItem extends ItemAbyTier {
  public ItemJzaharItem() {
    super(5, 2500, 4, "jzahar");
  }
  
  public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    ItemStack stack = playerIn.getHeldItem(hand);
    if (net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(worldIn))
      return EnumActionResult.SUCCESS; 
    if (!playerIn.canPlayerEdit(pos.offset(facing), facing, stack))
      return EnumActionResult.FAIL; 
    EntityJzahar entityliving = new EntityJzahar(worldIn);
    pos = pos.offset(facing);
    entityliving.setLocationAndAngles(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, MathHelper.wrapDegrees(worldIn.rand.nextFloat() * 360.0F), 0.0F);
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
      entityliving.setOwnerId(playerIn.getUniqueID());
      entityliving.playLivingSound();
      entityliving.playSound(ESound.createMob, 10.0F, 0.5F);
      entityliving.playSound(ESound.createBossMob, Float.MAX_VALUE, 0.75F);
      if (!playerIn.capabilities.isCreativeMode)
        stack.shrink(1); 
    } 
    return EnumActionResult.SUCCESS;
  }
}
