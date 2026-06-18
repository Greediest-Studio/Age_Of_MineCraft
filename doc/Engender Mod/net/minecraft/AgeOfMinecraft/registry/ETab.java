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
  
  public static CreativeTabs twilight;
  
  public static void init() {
    engender = new CreativeTabs("ageofminecraft") {
        public ItemStack func_78016_d() {
          return new ItemStack((Item)EItem.artifact1);
        }
      };
    vanilla = new CreativeTabs("ageofminecraft.vanilla") {
        public ItemStack func_78016_d() {
          return new ItemStack((Item)EItem.enderdragonItem);
        }
      };
    storymode = new CreativeTabs("ageofminecraft.storymode") {
        public ItemStack func_78016_d() {
          return new ItemStack((Item)EItem.witherStormItem);
        }
      };
    dungeons = new CreativeTabs("ageofminecraft.dungeons") {
        public ItemStack func_78016_d() {
          return new ItemStack((Item)EItem.skeletonItem);
        }
      };
    if (Loader.isModLoaded("abyssalcraft"))
      abyssal = new CreativeTabs("ageofabyssalcraft") {
          public ItemStack func_78016_d() {
            return new ItemStack((Item)EItem.jzaharItem);
          }
        }; 
    if (Loader.isModLoaded("draconicevolution"))
      draconic = new CreativeTabs("ageofchaos") {
          public ItemStack func_78016_d() {
            return new ItemStack((Item)EItem.chaosGuardianItem);
          }
        }; 
    if (Loader.isModLoaded("mutantbeasts"))
      mutant = new CreativeTabs("ageofmutants") {
          public ItemStack func_78016_d() {
            return new ItemStack((Item)EItem.mutantZombieItem);
          }
        }; 
  }
}
