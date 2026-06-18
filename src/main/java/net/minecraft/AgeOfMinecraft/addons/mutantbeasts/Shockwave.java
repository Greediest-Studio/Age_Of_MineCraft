package net.minecraft.AgeOfMinecraft.addons.mutantbeasts;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class Shockwave extends Vec3i {
  private boolean first;
  
  private boolean spawnParticles;
  
  public Shockwave(int x, int y, int z) {
    super(x, y, z);
    this.spawnParticles = true;
  }
  
  public Shockwave(int x, int y, int z, boolean spawnParticles) {
    this(x, y, z);
    this.spawnParticles = spawnParticles;
  }
  
  public boolean isFirst() {
    return this.first;
  }
  
  public static void createWaves(World world, List<Shockwave> list, int x1, int z1, int x2, int z2, int y) {
    int deltaX = x2 - x1;
    int deltaZ = z2 - z1;
    int xStep = (deltaX < 0) ? -1 : 1;
    int zStep = (deltaZ < 0) ? -1 : 1;
    deltaX = Math.abs(deltaX);
    deltaZ = Math.abs(deltaZ);
    int x = x1;
    int z = z1;
    int deltaX2 = deltaX * 2;
    int deltaZ2 = deltaZ * 2;
    Shockwave wave = addWave(world, list, x1, y, z1);
    if (wave != null)
      wave.first = true; 
    if (deltaX2 >= deltaZ2) {
      int error = deltaX;
      for (int i = 0; i < deltaX; i++) {
        x += xStep;
        error += deltaZ2;
        if (error > deltaX2) {
          z += zStep;
          error -= deltaX2;
        } 
        addWave(world, list, x, y, z);
      } 
    } else {
      int error = deltaZ;
      for (int i = 0; i < deltaZ; i++) {
        z += zStep;
        error += deltaX2;
        if (error > deltaZ2) {
          x += xStep;
          error -= deltaZ2;
        } 
        addWave(world, list, x, y, z);
      } 
    } 
  }
  
  public static Shockwave addWave(World world, List<Shockwave> list, int x, int y, int z) {
    y = FriendlyZombieSummon.getSuitableGround(world, x, y, z, 3, false);
    Shockwave wave = null;
    if (y != -1)
      list.add(wave = new Shockwave(x, y, z)); 
    if (world.rand.nextInt(2) == 0)
      list.add(new Shockwave(x, y + 1, z, false)); 
    return wave;
  }
  
  public void affectBlocks(World world, EntityLivingBase entity) {
    boolean isPlayer = entity instanceof EntityPlayer;
    BlockPos pos = new BlockPos(this);
    IBlockState blockState = world.getBlockState(pos);
    Block block = blockState.getBlock();
    BlockPos posUp = new BlockPos(getX(), getY() + 1, getZ());
    IBlockState blockStateUp = world.getBlockState(posUp);
    Block blockUp = blockState.getBlock();
    if ((isPlayer && ((EntityPlayer)entity).isAllowEdit()) || ForgeEventFactory.getMobGriefingEvent(world, (Entity)entity)) {
      if (block instanceof net.minecraft.block.BlockGrass || block instanceof net.minecraft.block.BlockGrassPath || block instanceof net.minecraft.block.BlockFarmland || block instanceof net.minecraft.block.BlockMycelium || block instanceof net.minecraft.block.BlockDirt) {
        EntityFallingBlock fallingblock = new EntityFallingBlock(world, pos.getX(), pos.getY(), pos.getZ(), blockState);
        fallingblock.motionY = 0.6D;
        fallingblock.setHurtEntities(true);
        fallingblock.setPosition(getX() + 0.5D, getY(), getZ() + 0.5D);
        world.spawnEntity((Entity)fallingblock);
      } 
      if (block instanceof net.minecraft.block.BlockBreakable || block instanceof net.minecraft.block.BlockLeaves)
        world.destroyBlock(pos, false); 
      if (blockStateUp.getBlockHardness(world, posUp) <= 1.0F && EntityWither.canDestroyBlock(block))
        world.destroyBlock(posUp, isPlayer); 
      if (blockUp instanceof net.minecraft.block.BlockDoor)
        if (blockStateUp.getMaterial() == Material.WOOD) {
          switch (world.getDifficulty()) {
            case PEACEFUL:
            case EASY:
            case NORMAL:
              world.playEvent(1019, posUp, 0);
              break;
            case HARD:
              world.setBlockToAir(posUp);
              world.playEvent(1021, posUp, 0);
              world.playEvent(2001, posUp, Block.getIdFromBlock(block));
              break;
          } 
        } else if (blockStateUp.getMaterial() == Material.IRON) {
          world.playEvent(1020, posUp, 0);
        }  
      if (block instanceof BlockTNT) {
        ((BlockTNT)block).explode(world, pos, blockState.withProperty((IProperty)BlockTNT.EXPLODE, Boolean.valueOf(true)), entity);
        world.setBlockToAir(pos);
      } 
    } 
    if (block instanceof net.minecraft.block.BlockRedstoneOre)
      block.onEntityWalk(world, pos, (Entity)entity); 
    if (this.spawnParticles)
      world.playEvent(2001, posUp, Block.getStateId(blockState)); 
  }
}
