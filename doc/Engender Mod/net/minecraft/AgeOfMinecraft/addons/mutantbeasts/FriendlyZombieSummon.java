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
    this.tick = 100 + world.field_73012_v.nextInt(40);
  }
  
  public FriendlyZombieSummon(World world, BlockPos pos, int tick) {
    this.posX = pos.func_177958_n();
    this.posY = pos.func_177956_o();
    this.posZ = pos.func_177952_p();
    this.world = world;
    this.tick = tick;
  }
  
  public int getTick() {
    return this.tick;
  }
  
  public boolean update(EntityMutantZombie mutantZombie) {
    BlockPos pos = getPosition();
    if (this.world.func_175623_d(pos.func_177977_b()) || this.world.func_180495_p(pos).func_185904_a().func_76224_d())
      return false; 
    if (mutantZombie.func_70681_au().nextInt(15) == 0)
      this.world.func_175718_b(2001, pos, Block.func_176210_f(this.world.func_180495_p(new BlockPos(this.posX, this.posY, this.posZ)))); 
    if (!this.world.field_72995_K && --this.tick <= 0) {
      if (Loader.isModLoaded("abyssalcraft") && mutantZombie.getZombieType() == 3) {
        EntityAbyssalZombie zombie = new EntityAbyssalZombie(this.world);
        zombie.func_180482_a(this.world.func_175649_E(pos), null);
        zombie.func_70606_j(zombie.func_110138_aP() * (0.6F + 0.4F * zombie.func_70681_au().nextFloat()));
        zombie.func_70642_aH();
        zombie.setOwnerId(mutantZombie.func_184753_b());
        zombie.func_70107_b(this.posX + 0.5D, this.posY - 1.0D, this.posZ + 0.5D);
        zombie.setLimitedLife(900 + mutantZombie.getLevel() * 40);
        zombie.setIsAntiMob(mutantZombie.isAntiMob());
        zombie.setGrowingAge(mutantZombie.getGrowingAge());
        this.world.func_72838_d((Entity)zombie);
      } else {
        EntityZombie zombie = new EntityZombie(this.world);
        zombie.func_180482_a(this.world.func_175649_E(pos), null);
        zombie.func_70606_j(zombie.func_110138_aP() * (0.6F + 0.4F * zombie.func_70681_au().nextFloat()));
        zombie.func_70642_aH();
        zombie.setOwnerId(mutantZombie.func_184753_b());
        zombie.setZombieType(mutantZombie.getZombieType());
        zombie.setGrowingAge(mutantZombie.getGrowingAge());
        zombie.setIsAntiMob(mutantZombie.isAntiMob());
        zombie.func_70107_b(this.posX + 0.5D, this.posY - 1.0D, this.posZ + 0.5D);
        zombie.setLimitedLife(900 + mutantZombie.getLevel() * 40);
        this.world.func_72838_d((Entity)zombie);
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
      IBlockState blockState = world.func_180495_p(pos);
      Block block = world.func_180495_p(pos).func_177230_c();
      if (block != Blocks.field_150480_ab) {
        if (block != Blocks.field_150353_l) {
          if (world.func_175623_d(pos)) {
            i--;
            continue;
          } 
          if (!world.func_175623_d(pos) && world.func_175623_d(pos.func_177984_a()) && shouldIgnoreBlock(world, blockState, pos)) {
            i--;
          } else if (!world.func_175623_d(pos) && !world.func_175623_d(pos.func_177984_a()) && !shouldIgnoreBlock(world, world.func_180495_p(pos.func_177984_a()), pos.func_177984_a())) {
            i++;
            continue;
          } 
        } 
        if (checkDay && world.func_72935_r()) {
          BlockPos pos1 = new BlockPos(x, y + 1, z);
          float f = world.func_175724_o(pos1);
          if (f > 0.5F && world.func_175710_j(pos1) && world.field_73012_v.nextInt(3) != 0)
            return -1; 
        } 
        return i;
      } 
      return -1;
    } 
    return -1;
  }
  
  private static boolean shouldIgnoreBlock(World world, IBlockState blockState, BlockPos blockPos) {
    if (blockState.func_177230_c() instanceof BlockTrapDoor && blockState.func_177229_b((IProperty)BlockTrapDoor.field_176285_M) == BlockTrapDoor.DoorHalf.TOP)
      return false; 
    if (blockState.func_177230_c() instanceof net.minecraft.block.BlockDoor)
      return true; 
    return blockState.func_177230_c().func_176205_b((IBlockAccess)world, blockPos);
  }
}
