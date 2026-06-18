package net.minecraft.AgeOfMinecraft.items;

import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySkeleton;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class ItemSkeletonTrapItem extends ItemVanillaTier {
  public ItemSkeletonTrapItem() {
    super(4, 100, 12, "skeletontrap");
  }
  
  public EnumActionResult func_180614_a(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    ItemStack stack = playerIn.func_184586_b(hand);
    if (worldIn.field_72995_K)
      return EnumActionResult.SUCCESS; 
    if (!playerIn.func_175151_a(pos.func_177972_a(facing), facing, stack))
      return EnumActionResult.FAIL; 
    if (!worldIn.field_72995_K) {
      pos = pos.func_177972_a(facing);
      DifficultyInstance difficultyinstance = worldIn.func_175649_E(pos);
      worldIn.func_72942_c((Entity)new EntityLightningBolt(worldIn, pos.func_177958_n() + 0.5D, pos.func_177956_o(), pos.func_177952_p() + 0.5D, true));
      for (int i = 0; i < 4; i++) {
        EntitySkeletonHorse entityhorse = func_188515_a(difficultyinstance, worldIn, pos.func_177958_n() + 0.5D, pos.func_177956_o(), pos.func_177952_p() + 0.5D);
        EntitySkeleton entityskeleton1 = func_188514_a(difficultyinstance, entityhorse);
        entityskeleton1.setOwnerId(playerIn.func_110124_au());
        entityskeleton1.func_70642_aH();
        int i1 = 3000;
        while (i1 > 0) {
          int j = EntityXPOrb.func_70527_a(i1);
          i1 -= j;
          if (!worldIn.func_82736_K().func_82766_b("disableExpItemDrops"))
            entityskeleton1.field_70170_p.func_72838_d((Entity)new EntityXPOrb(worldIn, pos.func_177958_n() + 0.5D, (pos.func_177956_o() + entityskeleton1.func_70047_e()), pos.func_177952_p() + 0.5D, j)); 
        } 
      } 
      worldIn.func_184133_a(null, pos, ESound.createBossMob, SoundCategory.MASTER, 10.0F, 1.25F);
      if (!playerIn.field_71075_bZ.field_75098_d)
        stack.func_190918_g(1); 
    } 
    return EnumActionResult.SUCCESS;
  }
  
  private EntitySkeletonHorse func_188515_a(DifficultyInstance p_188515_1_, World world, double x, double y, double z) {
    EntitySkeletonHorse entityhorse = new EntitySkeletonHorse(world);
    entityhorse.func_180482_a(p_188515_1_, (IEntityLivingData)null);
    entityhorse.func_70107_b(x, y, z);
    entityhorse.field_70172_ad = 200;
    entityhorse.func_110163_bv();
    entityhorse.func_110234_j(true);
    entityhorse.func_70873_a(0);
    entityhorse.field_70170_p.func_72838_d((Entity)entityhorse);
    entityhorse.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(53.0D);
    entityhorse.func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(12.0D);
    entityhorse.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.3375D);
    entityhorse.func_70024_g(entityhorse.func_70681_au().nextGaussian() * 0.5D, 0.0D, entityhorse.func_70681_au().nextGaussian() * 0.5D);
    return entityhorse;
  }
  
  private EntitySkeleton func_188514_a(DifficultyInstance p_188514_1_, EntitySkeletonHorse p_188514_2_) {
    EntitySkeleton entityskeleton = new EntitySkeleton(p_188514_2_.field_70170_p);
    entityskeleton.func_180482_a(p_188514_1_, (IEntityLivingData)null);
    entityskeleton.func_70107_b(p_188514_2_.field_70165_t, p_188514_2_.field_70163_u, p_188514_2_.field_70161_v);
    entityskeleton.field_70172_ad = 200;
    entityskeleton.func_110163_bv();
    entityskeleton.func_184201_a(EntityEquipmentSlot.HEAD, new ItemStack((Item)Items.field_151028_Y));
    EnchantmentHelper.func_77504_a(entityskeleton.func_70681_au(), entityskeleton.func_184614_ca(), 30, true);
    EnchantmentHelper.func_77504_a(entityskeleton.func_70681_au(), entityskeleton.func_184582_a(EntityEquipmentSlot.HEAD), 30, true);
    entityskeleton.field_70170_p.func_72838_d((Entity)entityskeleton);
    entityskeleton.func_184220_m((Entity)p_188514_2_);
    return entityskeleton;
  }
}
