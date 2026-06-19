package net.minecraft.AgeOfMinecraft.registry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

public class ETab {
  public static CreativeTabs engender;
  
  public static CreativeTabs vanilla;
  
  public static CreativeTabs storymode;
  
  public static CreativeTabs dungeons;
  
  public static CreativeTabs abyssal;
  
  public static CreativeTabs draconic;
  
  public static CreativeTabs mutant;

    public static void init() {
    engender = new CreativeTabs("ageofminecraft") {
        public ItemStack createIcon() {
          return new ItemStack(EItem.artifact1);
        }
      };
    vanilla = new CreativeTabs("ageofminecraft.vanilla") {
        public ItemStack createIcon() {
          return new ItemStack(EItem.enderdragonItem);
        }
      };
    storymode = new CreativeTabs("ageofminecraft.storymode") {
        public ItemStack createIcon() {
          return new ItemStack(EItem.witherStormItem);
        }
      };
    dungeons = new CreativeTabs("ageofminecraft.dungeons") {
        public ItemStack createIcon() {
          return new ItemStack(EItem.skeletonItem);
        }
      };
    if (Loader.isModLoaded("abyssalcraft"))
      abyssal = new CreativeTabs("ageofabyssalcraft") {
          public ItemStack createIcon() {
            return new ItemStack(EItem.jzaharItem);
          }
        }; 
    if (Loader.isModLoaded("draconicevolution"))
      draconic = new CreativeTabs("ageofchaos") {
          public ItemStack createIcon() {
            return new ItemStack(EItem.chaosGuardianItem);
          }
        }; 
    if (Loader.isModLoaded("mutantbeasts"))
      mutant = new CreativeTabs("ageofmutants") {
          public ItemStack createIcon() {
            return new ItemStack(EItem.mutantZombieItem);
          }
        }; 
  }
}
