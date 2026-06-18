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
    if (world.field_73012_v.nextInt(2) == 0)
      list.add(new Shockwave(x, y + 1, z, false)); 
    return wave;
  }
  
  public void affectBlocks(World world, EntityLivingBase entity) {
    boolean isPlayer = entity instanceof EntityPlayer;
    BlockPos pos = new BlockPos(this);
    IBlockState blockState = world.func_180495_p(pos);
    Block block = blockState.func_177230_c();
    BlockPos posUp = new BlockPos(func_177958_n(), func_177956_o() + 1, func_177952_p());
    IBlockState blockStateUp = world.func_180495_p(posUp);
    Block blockUp = blockState.func_177230_c();
    if ((isPlayer && ((EntityPlayer)entity).func_175142_cm()) || ForgeEventFactory.getMobGriefingEvent(world, (Entity)entity)) {
      if (block instanceof net.minecraft.block.BlockGrass || block instanceof net.minecraft.block.BlockGrassPath || block instanceof net.minecraft.block.BlockFarmland || block instanceof net.minecraft.block.BlockMycelium || block instanceof net.minecraft.block.BlockDirt) {
        EntityFallingBlock fallingblock = new EntityFallingBlock(world, pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p(), blockState);
        fallingblock.field_70181_x = 0.6D;
        fallingblock.func_145806_a(true);
        fallingblock.func_70107_b(func_177958_n() + 0.5D, func_177956_o(), func_177952_p() + 0.5D);
        world.func_72838_d((Entity)fallingblock);
      } 
      if (block instanceof net.minecraft.block.BlockBreakable || block instanceof net.minecraft.block.BlockLeaves)
        world.func_175655_b(pos, false); 
      if (blockStateUp.func_185887_b(world, posUp) <= 1.0F && EntityWither.func_181033_a(block))
        world.func_175655_b(posUp, isPlayer); 
      if (blockUp instanceof net.minecraft.block.BlockDoor)
        if (blockStateUp.func_185904_a() == Material.field_151575_d) {
          switch (world.func_175659_aa()) {
            case PEACEFUL:
            case EASY:
            case NORMAL:
              world.func_175718_b(1019, posUp, 0);
              break;
            case HARD:
              world.func_175698_g(posUp);
              world.func_175718_b(1021, posUp, 0);
              world.func_175718_b(2001, posUp, Block.func_149682_b(block));
              break;
          } 
        } else if (blockStateUp.func_185904_a() == Material.field_151573_f) {
          world.func_175718_b(1020, posUp, 0);
        }  
      if (block instanceof BlockTNT) {
        ((BlockTNT)block).func_180692_a(world, pos, blockState.func_177226_a((IProperty)BlockTNT.field_176246_a, Boolean.valueOf(true)), entity);
        world.func_175698_g(pos);
      } 
    } 
    if (block instanceof net.minecraft.block.BlockRedstoneOre)
      block.func_176199_a(world, pos, (Entity)entity); 
    if (this.spawnParticles)
      world.func_175718_b(2001, posUp, Block.func_176210_f(blockState)); 
  }
}
