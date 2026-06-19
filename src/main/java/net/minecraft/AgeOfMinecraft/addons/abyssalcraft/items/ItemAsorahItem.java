package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonBoss;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.asorahphases.PhaseListAsorah;
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

public class ItemAsorahItem extends ItemAbyTier {
  public ItemAsorahItem() {
    super(4, 600, 4, "asorah");
  }
  
  public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    ItemStack stack = playerIn.getHeldItem(hand);
    if (worldIn.isRemote)
      return EnumActionResult.SUCCESS; 
    if (!playerIn.canPlayerEdit(pos.offset(facing), facing, stack))
      return EnumActionResult.FAIL; 
    EntityDragonBoss entityliving = new EntityDragonBoss(worldIn);
    pos = pos.offset(facing);
    entityliving.setLocationAndAngles(pos.getX() + 0.5D, pos.getY() + 60.0D, pos.getZ() + 0.5D, MathHelper.wrapDegrees(worldIn.rand.nextFloat() * 360.0F), 0.0F);
    entityliving.rotationYawHead = entityliving.rotationYaw;
    entityliving.renderYawOffset = entityliving.rotationYaw;
    entityliving.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(entityliving)), null);
    if (!worldIn.isRemote) {
      worldIn.spawnEntity(entityliving);
      int i = 360000;
      while (i > 0) {
        int j = EntityXPOrb.getXPSplit(i);
        i -= j;
        if (!worldIn.getGameRules().getBoolean("disableExpItemDrops"))
          entityliving.world.spawnEntity(new EntityXPOrb(entityliving.world, entityliving.posX, entityliving.posY + entityliving.getEyeHeight(), entityliving.posZ, j));
      } 
    } 
    if (entityliving != null) {
      entityliving.world.playEvent(3000, pos, 0);
      entityliving.setOwnerId(playerIn.getUniqueID());
      entityliving.getPhaseManager().setPhase(PhaseListAsorah.LANDING_APPROACH);
      entityliving.playLivingSound();
      entityliving.playSound(ESound.createMob, 10.0F, 0.75F);
      entityliving.playSound(ESound.createBossMob, 1.0E7F, 1.0F);
      if (!playerIn.capabilities.isCreativeMode)
        stack.shrink(1); 
    } 
    return EnumActionResult.SUCCESS;
  }
}
