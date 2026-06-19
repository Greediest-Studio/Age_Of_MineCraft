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
    this.spawnableBlock = (Block)Blocks.MYCELIUM;
    this.experienceValue = 3;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityMooshroom(this.world);
  }
  
  public String getDescName() {
    return "cow_shroom";
  }
  
  public int getNextLevelRequirement() {
    return 10;
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (hasOwner(player) && !stack.isEmpty() && stack.getItem() == Items.BOWL) {
      player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
      stack.shrink(1);
      if (stack.isEmpty()) {
        player.setHeldItem(hand, new ItemStack(Items.MUSHROOM_STEW));
      } else if (!player.inventory.addItemStackToInventory(new ItemStack(Items.MUSHROOM_STEW))) {
        player.dropItem(new ItemStack(Items.MUSHROOM_STEW), false);
      } 
      return true;
    } 
    if (!stack.isEmpty() && stack.getItem() == Items.SHEARS) {
      this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX, this.posY + this.height, this.posZ, 0.0D, 0.0D, 0.0D);
      if (!this.world.isRemote) {
        for (int i = 0; i < 5; i++)
          this.world.spawnEntity((Entity)new EntityItem(this.world, this.posX, this.posY + this.height, this.posZ, new ItemStack((Block)Blocks.RED_MUSHROOM))); 
        stack.damageItem(16, (EntityLivingBase)player);
        playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 0.5F, 0.5F);
      } 
      return true;
    } 
    return super.interact(player, hand);
  }
  
  public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
    return false;
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_MUSHROOM_COW;
  }
  
  public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
    this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX, this.posY + (this.height / 2.0F), this.posZ, 0.0D, 0.0D, 0.0D);
    List<ItemStack> ret = new ArrayList<>();
    for (int i = 0; i < 5; i++)
      ret.add(new ItemStack((Block)Blocks.RED_MUSHROOM)); 
    playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0F, 0.7F);
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
