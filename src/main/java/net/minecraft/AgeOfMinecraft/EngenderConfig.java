package net.minecraft.AgeOfMinecraft;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeDouble;

@Config(modid = "ageofminecraft")
public class EngenderConfig {
  @Name("Enable Debug Mode")
  @Comment({"Enable debug mode", "Warning: Will break balance!"})
  public static boolean debugMode = false;
  
  @Name("Enable Lore Friendly Mode")
  @Comment({"If enabled, engendered mobs will be balanced based on the lore of Engender (Umbrella_Ghast's Balance)", "If disabled, engender mobs will be balanced for a fair experience (Mrbt0907's Balance)", "Warning: Will change the game dramatically!"})
  public static boolean loreFriendlyMode = true;
  
  @Name("Enable Nightmare Mode")
  @Comment({"If enabled, the Engender Mod will become much harder to complete, and the mobs will be extremely difficult to combat"})
  public static boolean nightmareMode = false;
  
  @Name("General Settings")
  @Comment({"Settings that deal with general Engender functionality"})
  public static General general = new General();
  
  @Name("Mob Settings")
  @Comment({"Settings that deal with Engender Mobs"})
  public static Mobs mobs = new Mobs();
  
  public static class General {
    @Name("Enable Engender Death Messages")
    @Comment({"If enabled, engendered mob death messages will display in chat."})
    public boolean useMessage = true;
    
    @Name("Enable Music")
    @Comment({"If enabled, boss themes and general music from Engender can play ingame"})
    public boolean useMusic = true;
    
    @Name("Enable Miniboss Music")
    @Comment({"If enabled, minibosses can play music themes"})
    public boolean useMiniMusic = false;
    
    @Name("Enable Renewable Dragon Eggs")
    @Comment({"If enabled, killing an Ender Dragon will always spawn a Dragon Egg."})
    public boolean dragonEgg = true;
    
    @Name("Enable Mana Orbs")
    @Comment({"If enabled, Mana Orbs can spawn."})
    public boolean mana = true;
    
    @Name("Mana Spawn Distance")
    @RangeDouble(min = 8.0D)
    @Comment({"If \"Enable Mana Orbs\" is enabled, This will determine how far Mana Orbs can be from a player to spawn on mob deaths."})
    public double manaDistance = 16.0D;
  }
  
  public static class Mobs {
    @Name("Enable Natural Engender Mob Spawns")
    @Comment({"If enabled, some mobs will be converted into wild engendered mobs."})
    public boolean naturalSpawns = false;
    
    @Name("Enable Mob Griefing")
    @Comment({"If enabled, engendered mobs can grief the world"})
    public boolean grief = true;
    
    @Name("Enable Regeneration")
    @Comment({"If enabled, engendered mobs will have engender regeneration along side natural regeneration"})
    public boolean regeneration = true;
    
    @Name("Enable Hunger")
    @Comment({"If enabled, most ally engendered mobs must eat to survive.", "If disabled, all ally mobs can eat to regain health"})
    public boolean hunger = false;
    
    @Name("Enable Heros")
    @Comment({"If enabled, all engendered mobs can turn into heros"})
    public boolean useHeros = true;
    
    @Name("Enable Mob Girls")
    @Comment({"If enabled, converts most mobs into mob girls."})
    public boolean useMobTalkerModels = false;
    
    @Name("Enable Mob Girl Breeding")
    @Comment({"If enabled, players can breed with mob girls."})
    public boolean breeding = false;
  }
}
