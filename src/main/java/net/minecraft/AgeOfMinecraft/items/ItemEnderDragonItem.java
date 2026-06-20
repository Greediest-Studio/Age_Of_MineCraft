package net.minecraft.AgeOfMinecraft.items;

import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases.PhaseList;
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

public class ItemEnderDragonItem extends ItemVanillaTier {
  public ItemEnderDragonItem() {
    super(4, 720, 4, "enderdragon");
  }
  
  public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    ItemStack stack = playerIn.getHeldItem(hand);
    if (net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(worldIn))
      return EnumActionResult.SUCCESS; 
    if (!playerIn.canPlayerEdit(pos.offset(facing), facing, stack))
      return EnumActionResult.FAIL; 
    EntityEnderDragon entityliving = new EntityEnderDragon(worldIn);
    pos = pos.offset(facing);
    entityliving.setLocationAndAngles(pos.getX() + 0.5D, pos.getY() + 60.0D, pos.getZ() + 0.5D, MathHelper.wrapDegrees(worldIn.rand.nextFloat() * 360.0F), 0.0F);
    net.minecraft.AgeOfMinecraft.util.EntityCompat.copyYawToHeadAndBody(entityliving);
    entityliving.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(entityliving)), null);
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(worldIn)) {
      worldIn.spawnEntity(entityliving);
      int i = 360000;
      while (i > 0) {
        int j = EntityXPOrb.getXPSplit(i);
        i -= j;
        if (!worldIn.getGameRules().getBoolean("disableExpItemDrops"))
          net.minecraft.AgeOfMinecraft.util.EntityCompat.spawnXpOrbAt(entityliving, entityliving.getEyeHeight(), j);
      } 
    } 
    if (entityliving != null) {
      net.minecraft.AgeOfMinecraft.util.EntityCompat.world(entityliving).playEvent(3000, pos, 0);
      entityliving.setOwnerId(playerIn.getUniqueID());
      triggerAction(playerIn, stack);
      entityliving.getPhaseManager().setPhase(PhaseList.LANDING_APPROACH);
      entityliving.playLivingSound();
      entityliving.playSound(ESound.createMob, 10.0F, 0.75F);
      entityliving.playSound(ESound.createBossMob, 1.0E7F, 1.0F);
      if (!playerIn.capabilities.isCreativeMode)
        stack.shrink(1); 
    } 
    return EnumActionResult.SUCCESS;
  }
}
