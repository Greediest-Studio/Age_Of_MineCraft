package net.minecraft.AgeOfMinecraft.util;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RegistryHelper {
  
  public static CreativeTabs createTab(String name, String modid, final Item item) {
    if (modid != null && Loader.isModLoaded(modid)) {
      CreativeTabs tab = new CreativeTabs("ageofminecraft" + ((name != null) ? ("_" + name) : "")) {
          public ItemStack createIcon() {
            return new ItemStack(item);
          }
        };
      return tab;
    } 
    return null;
  }
  
  @SideOnly(Side.CLIENT)
  public static void render(Block block) {
    render(block, 0);
  }
  
  @SideOnly(Side.CLIENT)
  public static void render(Block block, int meta) {
    Item item = Item.getItemFromBlock(block);
    Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation("ageofminecraft:" + item.getTranslationKey().substring(5), "inventory"));
  }
  
  @SideOnly(Side.CLIENT)
  public static void render(Block[] blocks) {
    for (int i = 0; i <= blocks.length - 1; i++) {
      Block block = blocks[i];
      Item item = Item.getItemFromBlock(block);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation("ageofminecraft:" + item.getTranslationKey().substring(5), "inventory"));
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public static void render(Block block, int[] metas) {
    for (int i = 0; i <= metas.length - 1; i++) {
      Item item = Item.getItemFromBlock(block);
      int meta = metas[i];
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation("ageofminecraft:" + item.getTranslationKey().substring(5), "inventory"));
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public static void render(Item item) {
    render(item, 0);
  }
  
  @SideOnly(Side.CLIENT)
  public static void render(Item item, int meta) {
    Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation("ageofminecraft:" + item.getTranslationKey().substring(5), "inventory"));
  }
  
  @SideOnly(Side.CLIENT)
  public static void render(Item[] items) {
    for (int i = 0; i <= items.length - 1; i++) {
      Item item = items[i];
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation("ageofminecraft:" + item.getTranslationKey().substring(5), "inventory"));
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public static void render(Item item, int[] metas) {
    for (int i = 0; i <= metas.length - 1; i++) {
      int meta = metas[i];
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation("ageofminecraft:" + item.getTranslationKey().substring(5), "inventory"));
    } 
  }
}
