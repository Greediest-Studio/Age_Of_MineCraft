package net.minecraft.AgeOfMinecraft.entity.tame.ai;

import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIDoorInteract;
import net.minecraft.util.EnumHand;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.event.ForgeEventFactory;

public class EntityAIVindicatorBreakDoor extends EntityAIDoorInteract {
  private int breakingTime;
  
  private int previousBreakProgress = -1;
  
  public EntityAIVindicatorBreakDoor(EntityLiving entityIn) {
    super(entityIn);
  }
  
  public boolean shouldExecute() {
    if (!super.shouldExecute())
      return false; 
    if (!this.entity.world.getBlockState(this.doorPosition).getBlock().canEntityDestroy(this.entity.world.getBlockState(this.doorPosition), this.entity.world, this.doorPosition, this.entity) || !ForgeEventFactory.onEntityDestroyBlock(this.entity, this.doorPosition, this.entity.world.getBlockState(this.doorPosition)))
      return false; 
    BlockDoor blockdoor = this.doorBlock;
    return !BlockDoor.isOpen(this.entity.world, this.doorPosition);
  }
  
  public void startExecuting() {
    super.startExecuting();
    this.breakingTime = 0;
  }
  
  public boolean shouldContinueExecuting() {
    double d0 = this.entity.getDistanceSq(this.doorPosition);
    if (this.breakingTime <= 8) {
      BlockDoor blockdoor = this.doorBlock;
      if (!BlockDoor.isOpen(this.entity.world, this.doorPosition) && d0 < 4.0D) {
        boolean bool = true;
        return bool;
      } 
    } 
    boolean flag = false;
    return flag;
  }
  
  public void resetTask() {
    super.resetTask();
    this.entity.world.sendBlockBreakProgress(this.entity.getEntityId(), this.doorPosition, -1);
  }
  
  public void updateTask() {
    super.updateTask();
    if (this.entity.ticksExisted % 10 == 0) {
      this.breakingTime++;
      this.entity.swingArm(EnumHand.MAIN_HAND);
      this.entity.world.playEvent(1019, this.doorPosition, 0);
    } 
    int i = (int)(this.breakingTime / 8.0F * 10.0F);
    if (i != this.previousBreakProgress) {
      this.entity.world.sendBlockBreakProgress(this.entity.getEntityId(), this.doorPosition, i);
      this.previousBreakProgress = i;
    } 
    if (this.breakingTime == 8) {
      if (this.breakingTime > 8)
        this.breakingTime = 8; 
      this.entity.world.setBlockToAir(this.doorPosition);
      this.entity.world.setBlockToAir(this.doorPosition.down());
      this.entity.playSound(ESound.heresJohnny, 2.0F, 1.0F);
      this.entity.world.playEvent(1021, this.doorPosition, 0);
      this.entity.world.playEvent(2001, this.doorPosition, Block.getIdFromBlock(this.doorBlock));
    } 
  }
}
