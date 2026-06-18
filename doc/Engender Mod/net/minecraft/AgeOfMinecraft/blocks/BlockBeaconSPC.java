package net.minecraft.AgeOfMinecraft.blocks;

import net.minecraft.AgeOfMinecraft.registry.ETab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBeaconSPC extends BlockContainer {
  public BlockBeaconSPC() {
    super(Material.field_151592_s, MapColor.field_151648_G);
    func_149663_c("beacon_spc");
    func_149711_c(3.0F);
    func_149752_b(6000000.0F);
    func_149647_a(ETab.engender);
  }
  
  public TileEntity func_149915_a(World worldIn, int meta) {
    return (TileEntity)new TileEntityBeaconSPC();
  }
  
  public boolean func_180639_a(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    if (worldIn.field_72995_K)
      return true; 
    TileEntity tileentity = worldIn.func_175625_s(pos);
    if (tileentity instanceof TileEntityBeaconSPC) {
      playerIn.func_71007_a((IInventory)tileentity);
      playerIn.func_71029_a(StatList.field_188082_P);
    } 
    return true;
  }
  
  public boolean func_149662_c(IBlockState state) {
    return false;
  }
  
  public boolean func_149686_d(IBlockState state) {
    return false;
  }
  
  public EnumBlockRenderType func_149645_b(IBlockState state) {
    return EnumBlockRenderType.MODEL;
  }
  
  public void func_180633_a(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    super.func_180633_a(worldIn, pos, state, placer, stack);
    if (stack.func_82837_s()) {
      TileEntity tileentity = worldIn.func_175625_s(pos);
      if (tileentity instanceof TileEntityBeaconSPC)
        ((TileEntityBeaconSPC)tileentity).setName(stack.func_82833_r()); 
    } 
  }
  
  public void func_189540_a(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    TileEntity tileentity = worldIn.func_175625_s(pos);
    if (tileentity instanceof TileEntityBeaconSPC) {
      ((TileEntityBeaconSPC)tileentity).updateBeacon();
      worldIn.func_175641_c(pos, (Block)this, 1, 0);
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public BlockRenderLayer func_180664_k() {
    return BlockRenderLayer.CUTOUT;
  }
  
  public static void updateColorAsync(final World worldIn, final BlockPos glassPos) {
    HttpUtil.field_180193_a.submit(new Runnable() {
          public void run() {
            Chunk chunk = worldIn.func_175726_f(glassPos);
            for (int i = glassPos.func_177956_o() - 1; i >= 0; i--) {
              final BlockPos blockpos = new BlockPos(glassPos.func_177958_n(), i, glassPos.func_177952_p());
              if (!chunk.func_177444_d(blockpos))
                break; 
              IBlockState iblockstate = worldIn.func_180495_p(blockpos);
              if (iblockstate.func_177230_c() == Blocks.field_150461_bJ)
                ((WorldServer)worldIn).func_152344_a(new Runnable() {
                      public void run() {
                        TileEntity tileentity = worldIn.func_175625_s(blockpos);
                        if (tileentity instanceof TileEntityBeaconSPC) {
                          ((TileEntityBeaconSPC)tileentity).updateBeacon();
                          worldIn.func_175641_c(blockpos, (Block)Blocks.field_150461_bJ, 1, 0);
                        } 
                      }
                    }); 
            } 
          }
        });
  }
}
