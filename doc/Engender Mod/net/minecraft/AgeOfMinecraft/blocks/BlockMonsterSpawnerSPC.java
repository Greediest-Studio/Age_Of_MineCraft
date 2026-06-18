package net.minecraft.AgeOfMinecraft.blocks;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderMod;
import net.minecraft.AgeOfMinecraft.entity.tame.other.EntityManaOrb;
import net.minecraft.AgeOfMinecraft.registry.EBlock;
import net.minecraft.AgeOfMinecraft.registry.ETab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMonsterSpawnerSPC extends BlockContainer {
  public BlockMonsterSpawnerSPC() {
    super(Material.field_151573_f);
    func_149663_c("mob_spawner_spc");
    func_149711_c(5.0F);
    func_149752_b(6000000.0F);
    setHarvestLevel("pickaxe", 0);
    func_149647_a(ETab.engender);
  }
  
  public void func_190948_a(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    tooltip.add("Fuse all of your fusion parts together with fuel");
  }
  
  public EnumBlockRenderType func_149645_b(IBlockState state) {
    return EnumBlockRenderType.MODEL;
  }
  
  @SideOnly(Side.CLIENT)
  public BlockRenderLayer func_180664_k() {
    return BlockRenderLayer.CUTOUT;
  }
  
  public boolean func_149686_d(IBlockState state) {
    return false;
  }
  
  public TileEntity func_149915_a(World worldIn, int meta) {
    return new TileEntityMonsterSpawnerSPC();
  }
  
  public void func_180633_a(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    if (stack.func_82837_s()) {
      TileEntity tileentity = worldIn.func_175625_s(pos);
      if (tileentity instanceof TileEntityMonsterSpawnerSPC)
        ((TileEntityMonsterSpawnerSPC)tileentity).setCustomInventoryName(stack.func_82833_r()); 
    } 
  }
  
  public boolean func_149662_c(IBlockState state) {
    return false;
  }
  
  public boolean func_180639_a(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    if (worldIn.field_72995_K)
      return true; 
    TileEntity tileentity = worldIn.func_175625_s(pos);
    if (tileentity instanceof TileEntityMonsterSpawnerSPC)
      FMLNetworkHandler.openGui(playerIn, EngenderMod.instance, 101, playerIn.field_70170_p, pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p()); 
    return true;
  }
  
  public void func_180663_b(World worldIn, BlockPos pos, IBlockState state) {
    TileEntity tileentity = worldIn.func_175625_s(pos);
    if (tileentity instanceof TileEntityMonsterSpawnerSPC) {
      InventoryHelper.func_180175_a(worldIn, pos, (TileEntityMonsterSpawnerSPC)tileentity);
      worldIn.func_175666_e(pos, (Block)this);
      if (((TileEntityMonsterSpawnerSPC)tileentity).mana > 0.0D)
        worldIn.func_72838_d((Entity)new EntityManaOrb(worldIn, pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p(), (int)((TileEntityMonsterSpawnerSPC)tileentity).mana, false)); 
      if (((TileEntityMonsterSpawnerSPC)tileentity).entropy > 0.0D)
        worldIn.func_72838_d((Entity)new EntityManaOrb(worldIn, pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p(), (int)((TileEntityMonsterSpawnerSPC)tileentity).entropy, true)); 
    } 
    super.func_180663_b(worldIn, pos, state);
  }
  
  public static void setState(boolean active, World worldIn, BlockPos pos) {
    IBlockState iblockstate = worldIn.func_180495_p(pos);
    TileEntity tileentity = worldIn.func_175625_s(pos);
    if (tileentity != null) {
      tileentity.func_145829_t();
      worldIn.func_175690_a(pos, tileentity);
    } 
  }
  
  public int func_180641_l(IBlockState blockState, World worldIn, BlockPos pos) {
    return Container.func_178144_a(worldIn.func_175625_s(pos));
  }
  
  public ItemStack func_185473_a(World worldIn, BlockPos pos, IBlockState state) {
    return new ItemStack((Block)EBlock.mob_spawner_spc);
  }
  
  public Item func_180660_a(IBlockState state, Random rand, int fortune) {
    return Item.func_150898_a((Block)EBlock.mob_spawner_spc);
  }
}
