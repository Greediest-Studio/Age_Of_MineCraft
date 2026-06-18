package net.minecraft.AgeOfMinecraft.util;

import net.minecraft.AgeOfMinecraft.EngenderMod;
import net.minecraft.AgeOfMinecraft.registry.ETab;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegistryHelper {
  public static CreativeTabs createTab(String name, Item item) {
    return createTab(name, "minecraft", item);
  }
  
  public static CreativeTabs createTab(String name, String modid, final Item item) {
    if (modid != null && Loader.isModLoaded(modid)) {
      CreativeTabs tab = new CreativeTabs("ageofminecraft" + ((name != null) ? ("_" + name) : "")) {
          public ItemStack func_78016_d() {
            return new ItemStack(item);
          }
        };
      return tab;
    } 
    return null;
  }
  
  public static void impl(Block block, String name) {
    impl(block, name, ETab.engender);
  }
  
  public static void impl(Block block, String name, CreativeTabs tab) {
    GameData.register_impl((IForgeRegistryEntry)((Block)block.setRegistryName(name)).func_149663_c(name).func_149647_a(tab));
    GameData.register_impl((new ItemBlock(block)).setRegistryName(name));
  }
  
  public static void impl(Block[] blocks, String[] names) {
    impl(blocks, names, ETab.engender);
  }
  
  public static void impl(Block[] blocks, String[] names, CreativeTabs tab) {
    if (names.length != blocks.length) {
      EngenderMod.logger.warn("Block array (" + blocks[0].toString() + ") does not have enough names.  Skipping...");
      return;
    } 
    for (int i = 0; i <= blocks.length - 1; i++) {
      Block block = blocks[i];
      String name = names[i];
      GameData.register_impl((IForgeRegistryEntry)((Block)block.setRegistryName(name)).func_149663_c(name).func_149647_a(tab));
      GameData.register_impl((new ItemBlock(block)).setRegistryName(name));
    } 
  }
  
  public static void impl(Block block, String name, String local_name) {
    impl(block, name, local_name, ETab.engender);
  }
  
  public static void impl(Block block, String name, String local_name, CreativeTabs tab) {
    GameData.register_impl((IForgeRegistryEntry)((Block)block.setRegistryName(name)).func_149663_c(local_name).func_149647_a(tab));
    GameData.register_impl((new ItemBlock(block)).setRegistryName(name));
  }
  
  public static void impl(Block[] blocks, String[] names, String[] local_names) {
    impl(blocks, names, local_names, ETab.engender);
  }
  
  public static void impl(Block[] blocks, String[] names, String[] local_names, CreativeTabs tab) {
    if (names.length != blocks.length) {
      EngenderMod.logger.warn("Block array (" + blocks[0].toString() + ") does not have enough names.  Skipping...");
      return;
    } 
    for (int i = 0; i <= blocks.length - 1; i++) {
      Block block = blocks[i];
      String name = names[i], local_name = (i > local_names.length - 1) ? name : local_names[i];
      GameData.register_impl((IForgeRegistryEntry)((Block)block.setRegistryName(name)).func_149663_c(local_name).func_149647_a(tab));
      GameData.register_impl((new ItemBlock(block)).setRegistryName(name));
    } 
  }
  
  public static void impl(Item item, String name) {
    impl(item, name, ETab.engender);
  }
  
  public static void impl(Item item, String name, CreativeTabs tab) {
    GameData.register_impl((IForgeRegistryEntry)((Item)item.setRegistryName(name)).func_77655_b(name).func_77637_a(tab));
  }
  
  public static void impl(Item[] items, String[] names) {
    impl(items, names, ETab.engender);
  }
  
  public static void impl(Item[] items, String[] names, CreativeTabs tab) {
    if (names.length != items.length) {
      EngenderMod.logger.warn("Item array (" + items[0].toString() + ") does not have enough names.  Skipping...");
      return;
    } 
    for (int i = 0; i <= items.length - 1; i++) {
      Item item = items[i];
      String name = names[i];
      GameData.register_impl((IForgeRegistryEntry)((Item)item.setRegistryName(name)).func_77655_b(name).func_77637_a(tab));
    } 
  }
  
  public static void impl(Item item, String name, String local_name) {
    impl(item, name, local_name, ETab.engender);
  }
  
  public static void impl(Item item, String name, String local_name, CreativeTabs tab) {
    GameData.register_impl((IForgeRegistryEntry)((Item)item.setRegistryName(name)).func_77655_b(local_name).func_77637_a(tab));
  }
  
  public static void impl(Item[] items, String[] names, String[] local_names) {
    impl(items, names, local_names, ETab.engender);
  }
  
  public static void impl(Item[] items, String[] names, String[] local_names, CreativeTabs tab) {
    if (names.length != items.length) {
      EngenderMod.logger.warn("Item array (" + items[0].toString() + ") does not have enough names.  Skipping...");
      return;
    } 
    for (int i = 0; i <= items.length - 1; i++) {
      Item item = items[i];
      String name = names[i], local_name = (i > local_names.length - 1) ? name : local_names[i];
      GameData.register_impl((IForgeRegistryEntry)((Item)item.setRegistryName(name)).func_77655_b(local_name).func_77637_a(tab));
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public static void render(Block block) {
    render(block, 0);
  }
  
  @SideOnly(Side.CLIENT)
  public static void render(Block block, int meta) {
    Item item = Item.func_150898_a(block);
    Minecraft.func_71410_x().func_175599_af().func_175037_a().func_178086_a(item, meta, new ModelResourceLocation("ageofminecraft:" + item.func_77658_a().substring(5), "inventory"));
  }
  
  @SideOnly(Side.CLIENT)
  public static void render(Block[] blocks) {
    for (int i = 0; i <= blocks.length - 1; i++) {
      Block block = blocks[i];
      Item item = Item.func_150898_a(block);
      Minecraft.func_71410_x().func_175599_af().func_175037_a().func_178086_a(item, 0, new ModelResourceLocation("ageofminecraft:" + item.func_77658_a().substring(5), "inventory"));
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public static void render(Block block, int[] metas) {
    for (int i = 0; i <= metas.length - 1; i++) {
      Item item = Item.func_150898_a(block);
      int meta = metas[i];
      Minecraft.func_71410_x().func_175599_af().func_175037_a().func_178086_a(item, meta, new ModelResourceLocation("ageofminecraft:" + item.func_77658_a().substring(5), "inventory"));
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public static void render(Item item) {
    render(item, 0);
  }
  
  @SideOnly(Side.CLIENT)
  public static void render(Item item, int meta) {
    Minecraft.func_71410_x().func_175599_af().func_175037_a().func_178086_a(item, meta, new ModelResourceLocation("ageofminecraft:" + item.func_77658_a().substring(5), "inventory"));
  }
  
  @SideOnly(Side.CLIENT)
  public static void render(Item[] items) {
    for (int i = 0; i <= items.length - 1; i++) {
      Item item = items[i];
      Minecraft.func_71410_x().func_175599_af().func_175037_a().func_178086_a(item, 0, new ModelResourceLocation("ageofminecraft:" + item.func_77658_a().substring(5), "inventory"));
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public static void render(Item item, int[] metas) {
    for (int i = 0; i <= metas.length - 1; i++) {
      int meta = metas[i];
      Minecraft.func_71410_x().func_175599_af().func_175037_a().func_178086_a(item, meta, new ModelResourceLocation("ageofminecraft:" + item.func_77658_a().substring(5), "inventory"));
    } 
  }
}
