package net.minecraft.AgeOfMinecraft.blocks;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.registry.ETab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGuardBlock extends Block {
  public BlockGuardBlock() {
    super(Material.field_151573_f);
    func_149675_a(true);
    func_149647_a(ETab.engender);
    func_149663_c("guard_block");
    func_149711_c(5.0F);
    func_149752_b(6000000.0F);
    setHarvestLevel("pickaxe", 0);
    func_149715_a(1.0F);
  }
  
  public void func_190948_a(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    tooltip.add("Sets all of your engendered mobs to guard a 32x32 area");
    tooltip.add(TextFormatting.GOLD + "Right click on the block to activate");
    tooltip.add(TextFormatting.GOLD + "Shift right click on a mob to unbind them");
  }
  
  @SideOnly(Side.CLIENT)
  public void func_180655_c(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
    double d0 = pos.func_177958_n() + 0.5D;
    double d1 = pos.func_177956_o() + 1.0D;
    double d2 = pos.func_177952_p() + 0.5D;
    worldIn.func_175682_a(EnumParticleTypes.END_ROD, true, d0, d1 + 0.1D, d2, 0.0D, 0.0D, 0.0D, new int[0]);
  }
  
  public boolean func_180639_a(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    if (!worldIn.field_72995_K && playerIn != null) {
      List<EntityTameBase> list = worldIn.func_175647_a(EntityTameBase.class, playerIn.func_174813_aQ().func_186662_g(48.0D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
      if (list != null && !list.isEmpty()) {
        worldIn.func_184133_a(null, playerIn.func_180425_c(), SoundEvents.field_187941_ho, SoundCategory.MASTER, 1.0F, 1.0F);
        for (int i1 = 0; i1 < list.size(); i1++) {
          EntityTameBase entity = list.get(i1);
          if (entity != null && entity.func_184191_r((Entity)playerIn) && entity.canUseGuardBlock()) {
            entity.func_70656_aK();
            entity.randPosX = entity.field_70165_t;
            entity.randPosY = entity.field_70163_u;
            entity.randPosZ = entity.field_70161_v;
            entity.setGuardBlock(pos);
          } 
        } 
      } 
      return true;
    } 
    return false;
  }
}
