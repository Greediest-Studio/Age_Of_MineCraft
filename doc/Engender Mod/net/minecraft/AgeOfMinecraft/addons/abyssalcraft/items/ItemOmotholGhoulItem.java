package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityOmotholGhoul;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemOmotholGhoulItem extends ItemAbyTier {
  public ItemOmotholGhoulItem() {
    super(3, 80, 16, "omotholghoul");
  }
  
  public EnumActionResult func_180614_a(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    ItemStack stack = playerIn.func_184586_b(hand);
    if (worldIn.field_72995_K)
      return EnumActionResult.SUCCESS; 
    if (!playerIn.func_175151_a(pos.func_177972_a(facing), facing, stack))
      return EnumActionResult.FAIL; 
    EntityOmotholGhoul entityliving = new EntityOmotholGhoul(worldIn);
    pos = pos.func_177972_a(facing);
    entityliving.func_70012_b(pos.func_177958_n() + 0.5D, pos.func_177956_o(), pos.func_177952_p() + 0.5D, MathHelper.func_76142_g(worldIn.field_73012_v.nextFloat() * 360.0F), 0.0F);
    entityliving.field_70759_as = entityliving.field_70177_z;
    entityliving.field_70761_aq = entityliving.field_70177_z;
    entityliving.func_180482_a(worldIn.func_175649_E(new BlockPos((Entity)entityliving)), null);
    if (!worldIn.field_72995_K) {
      worldIn.func_72838_d((Entity)entityliving);
      int i = 600;
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
      entityliving.func_184185_a(ESound.createMob, 5.0F, 1.0F);
      if (!playerIn.field_71075_bZ.field_75098_d)
        stack.func_190918_g(1); 
    } 
    return EnumActionResult.SUCCESS;
  }
}
