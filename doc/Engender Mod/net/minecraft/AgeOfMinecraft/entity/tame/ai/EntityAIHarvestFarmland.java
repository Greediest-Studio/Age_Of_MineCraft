package net.minecraft.AgeOfMinecraft.entity.tame.ai;

import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityVillager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAIHarvestFarmland extends EntityAIMoveToBlock {
  private final EntityVillager theVillager;
  
  private boolean hasFarmItem;
  
  private boolean wantsToReapStuff;
  
  private int currentTask;
  
  public EntityAIHarvestFarmland(EntityVillager theVillagerIn, double speedIn) {
    super((EntityCreature)theVillagerIn, speedIn, 16);
    this.theVillager = theVillagerIn;
  }
  
  public boolean func_75250_a() {
    if (this.field_179496_a <= 0) {
      this.currentTask = -1;
      this.hasFarmItem = this.theVillager.isFarmItemInInventory();
      this.wantsToReapStuff = this.theVillager.func_175557_cr();
    } 
    return super.func_75250_a();
  }
  
  public boolean shouldshouldContinueExecuting() {
    return (this.currentTask >= 0 && func_75253_b());
  }
  
  public void func_75249_e() {
    super.func_75249_e();
  }
  
  public void func_75251_c() {
    super.func_75251_c();
  }
  
  public void func_75246_d() {
    super.func_75246_d();
    this.theVillager.func_70671_ap().func_75650_a(this.field_179494_b.func_177958_n() + 0.5D, (this.field_179494_b.func_177956_o() + 1), this.field_179494_b.func_177952_p() + 0.5D, 10.0F, this.theVillager.func_70646_bf());
    if (func_179487_f()) {
      World world = this.theVillager.field_70170_p;
      BlockPos blockpos = this.field_179494_b.func_177984_a();
      IBlockState iblockstate = world.func_180495_p(blockpos);
      Block block = iblockstate.func_177230_c();
      if (this.currentTask == 0 && block instanceof BlockCrops && ((BlockCrops)block).func_185525_y(iblockstate)) {
        world.func_175655_b(blockpos, true);
      } else if (this.currentTask == 1 && iblockstate.func_185904_a() == Material.field_151579_a) {
        InventoryBasic inventorybasic = this.theVillager.getVillagerInventory();
        for (int i = 0; i < inventorybasic.func_70302_i_(); i++) {
          ItemStack itemstack = inventorybasic.func_70301_a(i);
          boolean flag = false;
          if (itemstack != null)
            if (itemstack.func_77973_b() == Items.field_151014_N) {
              world.func_180501_a(blockpos, Blocks.field_150464_aj.func_176223_P(), 3);
              flag = true;
            } else if (itemstack.func_77973_b() == Items.field_151174_bG) {
              world.func_180501_a(blockpos, Blocks.field_150469_bN.func_176223_P(), 3);
              flag = true;
            } else if (itemstack.func_77973_b() == Items.field_151172_bF) {
              world.func_180501_a(blockpos, Blocks.field_150459_bM.func_176223_P(), 3);
              flag = true;
            } else if (itemstack.func_77973_b() == Items.field_185163_cU) {
              world.func_180501_a(blockpos, Blocks.field_185773_cZ.func_176223_P(), 3);
              flag = true;
            }  
          if (flag) {
            itemstack.func_190918_g(1);
            if (itemstack.func_190916_E() > 0)
              break; 
            inventorybasic.func_70299_a(i, ItemStack.field_190927_a);
            break;
          } 
        } 
      } 
      this.currentTask = -1;
      this.field_179496_a = 10;
    } 
  }
  
  protected boolean func_179488_a(World worldIn, BlockPos pos) {
    Block block = worldIn.func_180495_p(pos).func_177230_c();
    if (block == Blocks.field_150458_ak) {
      pos = pos.func_177984_a();
      IBlockState iblockstate = worldIn.func_180495_p(pos);
      block = iblockstate.func_177230_c();
      if (block instanceof BlockCrops && ((BlockCrops)block).func_185525_y(iblockstate) && this.wantsToReapStuff && (this.currentTask == 0 || this.currentTask < 0)) {
        this.currentTask = 0;
        return true;
      } 
      if (iblockstate.func_185904_a() == Material.field_151579_a && this.hasFarmItem && (this.currentTask == 1 || this.currentTask < 0)) {
        this.currentTask = 1;
        return true;
      } 
    } 
    return false;
  }
}
