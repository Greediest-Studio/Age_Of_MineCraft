package net.minecraft.AgeOfMinecraft.addons.mutantbeasts;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityAbyssalZombie;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantZombie;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityZombie;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;

public class FriendlyZombieSummon {
  private final int posX;
  
  private final int posY;
  
  private final int posZ;
  
  private int tick;
  
  private final World world;
  
  public FriendlyZombieSummon(World world, int x, int y, int z) {
    this.posX = x;
    this.posY = y;
    this.posZ = z;
    this.world = world;
    this.tick = 100 + world.rand.nextInt(40);
  }
  
  public FriendlyZombieSummon(World world, BlockPos pos, int tick) {
    this.posX = pos.getX();
    this.posY = pos.getY();
    this.posZ = pos.getZ();
    this.world = world;
    this.tick = tick;
  }
  
  public int getTick() {
    return this.tick;
  }
  
  public boolean update(EntityMutantZombie mutantZombie) {
    BlockPos pos = getPosition();
    if (this.world.isAirBlock(pos.down()) || this.world.getBlockState(pos).getMaterial().isLiquid())
      return false; 
    if (mutantZombie.getRNG().nextInt(15) == 0)
      this.world.playEvent(2001, pos, Block.getStateId(this.world.getBlockState(new BlockPos(this.posX, this.posY, this.posZ)))); 
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && --this.tick <= 0) {
      if (Loader.isModLoaded("abyssalcraft") && mutantZombie.getZombieType() == 3) {
        EntityAbyssalZombie zombie = new EntityAbyssalZombie(this.world);
        zombie.onInitialSpawn(this.world.getDifficultyForLocation(pos), null);
        zombie.setHealth(zombie.getMaxHealth() * (0.6F + 0.4F * zombie.getRNG().nextFloat()));
        zombie.playLivingSound();
        zombie.setOwnerId(mutantZombie.getOwnerId());
        zombie.setPosition(this.posX + 0.5D, this.posY - 1.0D, this.posZ + 0.5D);
        zombie.setLimitedLife(900 + mutantZombie.getLevel() * 40);
        zombie.setIsAntiMob(mutantZombie.isAntiMob());
        zombie.setGrowingAge(mutantZombie.getGrowingAge());
        this.world.spawnEntity(zombie);
      } else {
        EntityZombie zombie = new EntityZombie(this.world);
        zombie.onInitialSpawn(this.world.getDifficultyForLocation(pos), null);
        zombie.setHealth(zombie.getMaxHealth() * (0.6F + 0.4F * zombie.getRNG().nextFloat()));
        zombie.playLivingSound();
        zombie.setOwnerId(mutantZombie.getOwnerId());
        zombie.setZombieType(mutantZombie.getZombieType());
        zombie.setGrowingAge(mutantZombie.getGrowingAge());
        zombie.setIsAntiMob(mutantZombie.isAntiMob());
        zombie.setPosition(this.posX + 0.5D, this.posY - 1.0D, this.posZ + 0.5D);
        zombie.setLimitedLife(900 + mutantZombie.getLevel() * 40);
        this.world.spawnEntity(zombie);
      } 
      return false;
    } 
    return true;
  }
  
  public BlockPos getPosition() {
    return new BlockPos(this.posX, this.posY + 1, this.posZ);
  }
  
  public static int getSuitableGround(World world, int x, int y, int z) {
    return getSuitableGround(world, x, y, z, 4, true);
  }
  
  public static int getSuitableGround(World world, int x, int y, int z, int range, boolean checkDay) {
    int i = y;
    while (Math.abs(y - i) <= range) {
      BlockPos pos = new BlockPos(x, i, z);
      IBlockState blockState = world.getBlockState(pos);
      Block block = world.getBlockState(pos).getBlock();
      if (block != Blocks.FIRE) {
        if (block != Blocks.LAVA) {
          if (world.isAirBlock(pos)) {
            i--;
            continue;
          } 
          if (!world.isAirBlock(pos) && world.isAirBlock(pos.up()) && shouldIgnoreBlock(world, blockState, pos)) {
            i--;
          } else if (!world.isAirBlock(pos) && !world.isAirBlock(pos.up()) && !shouldIgnoreBlock(world, world.getBlockState(pos.up()), pos.up())) {
            i++;
            continue;
          } 
        } 
        if (checkDay && world.isDaytime()) {
          BlockPos pos1 = new BlockPos(x, y + 1, z);
          float f = world.getLightBrightness(pos1);
          if (f > 0.5F && world.canBlockSeeSky(pos1) && world.rand.nextInt(3) != 0)
            return -1; 
        } 
        return i;
      } 
      return -1;
    } 
    return -1;
  }
  
  private static boolean shouldIgnoreBlock(World world, IBlockState blockState, BlockPos blockPos) {
    if (blockState.getBlock() instanceof BlockTrapDoor && blockState.getValue((IProperty)BlockTrapDoor.HALF) == BlockTrapDoor.DoorHalf.TOP)
      return false; 
    if (blockState.getBlock() instanceof net.minecraft.block.BlockDoor)
      return true; 
    return blockState.getBlock().isPassable(world, blockPos);
  }
}
