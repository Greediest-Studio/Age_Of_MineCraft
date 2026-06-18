package net.minecraft.AgeOfMinecraft.entity.tame.tier1;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

public class EntityMooshroom extends EntityCow implements IShearable {
  public EntityMooshroom(World worldIn) {
    super(worldIn);
    this.spawnableBlock = (Block)Blocks.field_150391_bh;
    this.field_70728_aV = 3;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityMooshroom(this.field_70170_p);
  }
  
  public String getDescName() {
    return "cow_shroom";
  }
  
  public int getNextLevelRequirement() {
    return 10;
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    if (hasOwner(player) && !stack.func_190926_b() && stack.func_77973_b() == Items.field_151054_z) {
      player.func_184185_a(SoundEvents.field_187564_an, 1.0F, 1.0F);
      stack.func_190918_g(1);
      if (stack.func_190926_b()) {
        player.func_184611_a(hand, new ItemStack(Items.field_151009_A));
      } else if (!player.field_71071_by.func_70441_a(new ItemStack(Items.field_151009_A))) {
        player.func_71019_a(new ItemStack(Items.field_151009_A), false);
      } 
      return true;
    } 
    if (!stack.func_190926_b() && stack.func_77973_b() == Items.field_151097_aZ) {
      this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_LARGE, this.field_70165_t, this.field_70163_u + this.field_70131_O, this.field_70161_v, 0.0D, 0.0D, 0.0D, new int[0]);
      if (!this.field_70170_p.field_72995_K) {
        for (int i = 0; i < 5; i++)
          this.field_70170_p.func_72838_d((Entity)new EntityItem(this.field_70170_p, this.field_70165_t, this.field_70163_u + this.field_70131_O, this.field_70161_v, new ItemStack((Block)Blocks.field_150337_Q))); 
        stack.func_77972_a(16, (EntityLivingBase)player);
        func_184185_a(SoundEvents.field_187539_bB, 0.5F, 0.5F);
      } 
      return true;
    } 
    return super.interact(player, hand);
  }
  
  public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
    return false;
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_MUSHROOM_COW;
  }
  
  public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
    this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_LARGE, this.field_70165_t, this.field_70163_u + (this.field_70131_O / 2.0F), this.field_70161_v, 0.0D, 0.0D, 0.0D, new int[0]);
    List<ItemStack> ret = new ArrayList<>();
    for (int i = 0; i < 5; i++)
      ret.add(new ItemStack((Block)Blocks.field_150337_Q)); 
    func_184185_a(SoundEvents.field_187539_bB, 1.0F, 0.7F);
    return ret;
  }
  
  protected SoundEvent getRegularHurtSound() {
    return ESound.woodHit;
  }
  
  protected SoundEvent getPierceHurtSound() {
    return ESound.woodHitPierce;
  }
  
  protected SoundEvent getCrushHurtSound() {
    return ESound.woodHitCrush;
  }
}
