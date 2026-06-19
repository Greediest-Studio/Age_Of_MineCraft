package net.minecraft.AgeOfMinecraft;

import net.minecraft.AgeOfMinecraft.events.ChunkLoadingEvent;
import net.minecraft.AgeOfMinecraft.gui.GuiEngenderMobInventory;
import net.minecraft.AgeOfMinecraft.items.ItemEngenderStatChecker;
import net.minecraft.AgeOfMinecraft.registry.EEntity;
import net.minecraft.AgeOfMinecraft.registry.EItem;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.AgeOfMinecraft.registry.ESpawner;
import net.minecraft.AgeOfMinecraft.registry.ETab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {

  public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    return null;
  }
  
  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
      ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);
      if (ID == 100) {
          if (!stack.isEmpty() && stack.getItem() instanceof ItemEngenderStatChecker)
              return new GuiEngenderMobInventory(player, ItemEngenderStatChecker.viewedEntity);
      }
    return null;
  }
  
  public void preInit(FMLPreInitializationEvent e) {
    new ESetup();
    ETab.init();
    ESpawner.init();
    ChunkLoadingEvent.init();
    EItem.ENMO();
    ELoot.registerAllModdedLootTables();
    ESound.registerSounds();
    if (Loader.isModLoaded("abyssalcraft"))
      EItem.ENMOA(); 
    if (Loader.isModLoaded("draconicevolution"))
      EItem.ENMODE(); 
    if (Loader.isModLoaded("mutantbeasts"))
      EItem.ENMOMB(); 
  }
  
  public void init(FMLInitializationEvent e) {
    EEntity.registerEntity();
  }
  
  public void postInit(FMLPostInitializationEvent e) {}
}
