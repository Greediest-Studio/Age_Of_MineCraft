package net.minecraft.AgeOfMinecraft.events;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.AgeOfMinecraft.EngenderMod;
import net.minecraft.AgeOfMinecraft.entity.EntityBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.AgeOfMinecraft.registry.EngenderMusic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EngenderMusicEvent extends MusicTicker {
  private final Minecraft mc;
  
  private int ticks = 0;
  
  private int pastTicks = 50;
  
  private int entities = 0;
  
  private int musicID = 0;
  
  private int newMusicID = 0;
  
  private boolean shouldPlay = true;
  
  private EngenderMusic currentMusic;
  
  private MusicTypeExtra musicticker$musictype;
  
  private final List<EngenderMusic> musicList = Lists.newArrayList();
  
  private final List<Entity> entityList = Lists.newArrayList();
  
  public EngenderMusicEvent(Minecraft minecraft) {
    super(minecraft);
    this.mc = minecraft;
  }
  
  private int findMob(Entity entity, int musicID) {
    if (entity instanceof EntityBase)
      return (((EntityBase)entity).playMusic() > musicID) ? ((EntityBase)entity).playMusic() : musicID; 
    if (!entity.func_184222_aU())
      return (2 > musicID) ? 2 : musicID; 
    return (0 == musicID) ? 0 : musicID;
  }
  
  private MusicTypeExtra selectMusicID() {
    int musicID = 0;
    MusicTypeExtra music = null;
    for (Entity entity : this.entityList) {
      musicID = findMob(entity, musicID);
      switch (musicID) {
        case 1:
          music = MusicTypeExtra.SANS;
        case 2:
          music = MusicTypeExtra.WITHER;
        case 3:
          music = MusicTypeExtra.TSTMPWYFSANS;
        case 4:
          music = MusicTypeExtra.GHASTHER;
        case 5:
          music = MusicTypeExtra.GIANT_MAGMA_GOLEM;
        case 6:
          music = MusicTypeExtra.ENDER_DRAGON;
        case 7:
          music = MusicTypeExtra.NYEHHEHHEH;
        case 8:
          music = MusicTypeExtra.JZAHAR;
        case 9:
          music = MusicTypeExtra.THE_TWINS;
        case 10:
          music = MusicTypeExtra.CHAOS_GUARDIAN;
        case 11:
          music = MusicTypeExtra.WITHER_STORM_PRE;
        case 12:
          music = MusicTypeExtra.WITHER_STORM;
        case 13:
          music = MusicTypeExtra.WITHER_STORM_DEVOURER;
        case 14:
          music = MusicTypeExtra.BONETOUSLE;
        case 15:
          music = MusicTypeExtra.DARKNESS;
        case 16:
          music = MusicTypeExtra.DARKNESS_MAX;
        case 17:
          music = MusicTypeExtra.BTERTD;
        case 18:
          music = MusicTypeExtra.ALDUIN;
        case 19:
          music = MusicTypeExtra.SOVNGARDE;
        case 68:
          music = MusicTypeExtra.WITHER_STORM_THUNDERSTORM;
        case 100:
          music = MusicTypeExtra.MEGALOVANIA;
        case 101:
          music = MusicTypeExtra.DUNKS_AND_JUDGEMENT;
      } 
    } 
    if (musicID != 0)
      EngenderMod.console("Music ID " + musicID + " was selected"); 
    return setMusic(music, musicID);
  }
  
  public void func_73660_a() {
    this.ticks++;
    if (this.mc.field_71439_g.field_70170_p.field_72996_f.size() != this.entities || this.ticks % 160 == 0) {
      for (Entity entity : this.mc.field_71439_g.field_70170_p.field_72996_f) {
        if (!this.entityList.contains(entity) && (!entity.func_184222_aU() || (entity instanceof EntityTameBase && ((EntityTameBase)entity).isABoss()) || (entity instanceof EntityBase && ((EntityBase)entity).playMusic() != 0)) && entity.field_70173_aa > 10 && entity.func_70089_S() && (!entity.func_184191_r((Entity)this.mc.field_71439_g) || entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.cameos.EntitySans) && !entity.func_70093_af() && !entity.func_98034_c((EntityPlayer)this.mc.field_71439_g)) {
          this.entityList.add(entity);
          continue;
        } 
        if (this.entityList.contains(entity) && (entity.field_70128_L || entity.func_70093_af() || entity.func_98034_c((EntityPlayer)this.mc.field_71439_g)))
          this.entityList.remove(entity); 
      } 
      if (this.entityList.isEmpty())
        setNoMusic(); 
      this.entities = this.mc.field_71439_g.field_70170_p.field_72996_f.size();
      this.musicticker$musictype = selectMusicID();
      if (this.musicticker$musictype != null)
        playMusic(this.musicticker$musictype); 
      for (int j = 1; j <= this.entityList.size(); j++) {
        if (!this.mc.field_71439_g.field_70170_p.field_72996_f.contains(this.entityList.get(j - 1))) {
          this.entityList.remove(this.entityList.get(j - 1));
          j--;
        } 
      } 
      this.pastTicks += 20;
    } 
    for (int i = 1; i <= this.musicList.size(); i++) {
      if (this.musicList.get(i - 1) != null) {
        ((EngenderMusic)this.musicList.get(i - 1)).onMusicUpdate(this.newMusicID);
        if (((EngenderMusic)this.musicList.get(i - 1)).func_147667_k() || this.mc.field_71439_g.field_70128_L) {
          EngenderMod.console("Music ID " + ((EngenderMusic)this.musicList.get(i - 1)).musicID + " was removed from musicList");
          this.musicList.remove(i - 1);
          i--;
        } 
      } 
    } 
  }
  
  private void playMusic(MusicTypeExtra type) {
    if (this.musicID != this.newMusicID) {
      this.shouldPlay = true;
      for (int i = 1; i <= this.musicList.size(); i++) {
        if (((EngenderMusic)this.musicList.get(i - 1)).musicID == this.newMusicID)
          this.shouldPlay = false; 
      } 
      if (this.shouldPlay) {
        this.currentMusic = new EngenderMusic(this.mc.field_71439_g, type, this.newMusicID);
        if (this.musicList.size() < 4) {
          this.musicList.add(this.currentMusic);
          EngenderMod.console("Music ID " + this.currentMusic.musicID + " was added to musicList");
        } else {
          EngenderMod.console("Music ID " + ((EngenderMusic)this.musicList.get(0)).musicID + " was removed from musicList");
          this.mc.func_147118_V().func_147683_b((ISound)this.musicList.get(0));
          this.musicList.add(this.currentMusic);
          EngenderMod.console("Music ID " + this.currentMusic.musicID + " was added to musicList");
        } 
        this.mc.func_147118_V().func_147682_a((ISound)this.currentMusic);
      } else {
        EngenderMod.console("Music ID " + this.currentMusic.musicID + " was resumed");
      } 
      this.musicID = this.newMusicID;
    } 
  }
  
  public void stopMusic(EngenderMusic music) {
    if (music != null) {
      EngenderMod.console("Music ID " + music.musicID + " was removed from musicList");
      this.mc.func_147118_V().func_147683_b((ISound)music);
    } 
  }
  
  public MusicTypeExtra setNoMusic() {
    this.musicID = this.newMusicID;
    this.newMusicID = 0;
    this.musicList.clear();
    this.entityList.clear();
    return null;
  }
  
  public MusicTypeExtra setMusic(MusicTypeExtra music, int ID) {
    this.musicID = this.newMusicID;
    this.newMusicID = ID;
    return music;
  }
  
  @SideOnly(Side.CLIENT)
  public enum MusicTypeExtra {
    ENDER_DRAGON((String)SoundEvents.field_187796_dz),
    WITHER((String)ESound.withertheme),
    GIANT_MAGMA_GOLEM((String)ESound.giantmagmagolemtheme),
    GHASTHER((String)ESound.ghasthertheme),
    WITHER_STORM_PRE((String)ESound.commandBlockWitherTheme),
    WITHER_STORM((String)ESound.witherStormTheme),
    WITHER_STORM_DEVOURER((String)ESound.witherStormTheme2),
    WITHER_STORM_THUNDERSTORM((String)ESound.witherStormTheme3),
    JZAHAR((String)ESound.jzahartheme),
    CHAOS_GUARDIAN((String)ESound.chaosguardiantheme),
    SANS((String)ESound.sans),
    NYEHHEHHEH((String)ESound.nyehhehheh),
    BONETOUSLE((String)ESound.bonetrousle),
    TSTMPWYFSANS((String)ESound.tstmpwyfSans),
    BTERTD((String)ESound.btertd),
    MEGALOVANIA((String)ESound.megalovania),
    DUNKS_AND_JUDGEMENT((String)ESound.dunksandjudgement),
    THE_TWINS((String)ESound.terrariathetwins),
    SOVNGARDE((String)ESound.sovngarde),
    ALDUIN((String)ESound.alduintheme),
    DARKNESS((String)ESound.darkness),
    DARKNESS_MAX((String)ESound.darknessMax);
    
    private final SoundEvent musicLocation;
    
    MusicTypeExtra(SoundEvent musicLocationIn) {
      this.musicLocation = musicLocationIn;
    }
    
    public SoundEvent getMusicLocation() {
      return this.musicLocation;
    }
  }
}
