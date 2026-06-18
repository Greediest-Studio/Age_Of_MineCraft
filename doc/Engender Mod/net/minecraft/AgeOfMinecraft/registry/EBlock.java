package net.minecraft.AgeOfMinecraft.registry;

import net.minecraft.AgeOfMinecraft.blocks.BlockBeaconSPC;
import net.minecraft.AgeOfMinecraft.blocks.BlockGuardBlock;
import net.minecraft.AgeOfMinecraft.blocks.BlockMonsterSpawnerSPC;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.GameData;

public class EBlock {
  public static BlockMonsterSpawnerSPC mob_spawner_spc;
  
  public static BlockGuardBlock guard_block;
  
  public static BlockBeaconSPC beacon_spc;
  
  public static void init() {
    Blocks.field_150483_bI.func_149647_a(CreativeTabs.field_78028_d);
    Blocks.field_185777_dd.func_149647_a(CreativeTabs.field_78028_d);
    Blocks.field_185776_dc.func_149647_a(CreativeTabs.field_78028_d);
    Blocks.field_185779_df.func_149647_a(CreativeTabs.field_78028_d);
    Blocks.field_180401_cv.func_149647_a(CreativeTabs.field_78026_f);
    mob_spawner_spc = new BlockMonsterSpawnerSPC();
    guard_block = new BlockGuardBlock();
    register();
  }
  
  public static void register() {
    GameData.register_impl(mob_spawner_spc.setRegistryName("mob_spawner_spc"));
    GameData.register_impl(guard_block.setRegistryName("guard_block"));
    GameData.register_impl((new ItemBlock((Block)mob_spawner_spc)).setRegistryName(mob_spawner_spc.getRegistryName()));
    GameData.register_impl((new ItemBlock((Block)guard_block)).setRegistryName(guard_block.getRegistryName()));
  }
  
  @SideOnly(Side.CLIENT)
  public static void registerRenders() {
    registerRender((Block)mob_spawner_spc);
    registerRender((Block)guard_block);
  }
  
  @SideOnly(Side.CLIENT)
  public static void registerRender(Block block) {
    Item item = Item.func_150898_a(block);
    Minecraft.func_71410_x().func_175599_af().func_175037_a().func_178086_a(item, 0, new ModelResourceLocation("ageofminecraft:" + item.func_77658_a().substring(5), "inventory"));
  }
}
