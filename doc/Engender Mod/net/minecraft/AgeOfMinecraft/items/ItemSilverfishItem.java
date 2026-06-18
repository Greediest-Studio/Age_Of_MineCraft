package net.minecraft.AgeOfMinecraft.items;

import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntitySilverfish;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemSilverfishItem extends ItemVanillaTier {
  public ItemSilverfishItem() {
    super(1, 8, 64, "silverfish");
  }
  
  public EnumActionResult func_180614_a(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    ItemStack stack = playerIn.func_184586_b(hand);
    if (worldIn.field_72995_K)
      return EnumActionResult.SUCCESS; 
    if (!playerIn.func_175151_a(pos.func_177972_a(facing), facing, stack))
      return EnumActionResult.FAIL; 
    for (int a = 0; a < 3; a++) {
      EntitySilverfish entityliving = new EntitySilverfish(worldIn);
      pos = pos.func_177972_a(facing);
      entityliving.func_70012_b(pos.func_177958_n() + 0.5D, pos.func_177956_o(), pos.func_177952_p() + 0.5D, MathHelper.func_76142_g(worldIn.field_73012_v.nextFloat() * 360.0F), 0.0F);
      if (!worldIn.field_72995_K) {
        worldIn.func_72838_d((Entity)entityliving);
        int i = 15;
        while (i > 0) {
          int j = EntityXPOrb.func_70527_a(i);
          i -= j;
          if (!worldIn.func_82736_K().func_82766_b("disableExpItemDrops"))
            entityliving.field_70170_p.func_72838_d((Entity)new EntityXPOrb(entityliving.field_70170_p, entityliving.field_70165_t, entityliving.field_70163_u + entityliving.func_70047_e(), entityliving.field_70161_v, j)); 
        } 
      } 
      if (entityliving != null) {
        entityliving.setOwnerId(playerIn.func_110124_au());
        entityliving.func_70642_aH();
      } 
    } 
    worldIn.func_184133_a(null, pos, ESound.createMob, SoundCategory.MASTER, 5.0F, 1.0F);
    if (!playerIn.field_71075_bZ.field_75098_d)
      stack.func_190918_g(1); 
    return EnumActionResult.SUCCESS;
  }
}
