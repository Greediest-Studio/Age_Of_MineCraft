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
    if (!entity.isNonBoss())
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
          break;
        case 2:
          music = MusicTypeExtra.WITHER;
          break;
        case 3:
          music = MusicTypeExtra.TSTMPWYFSANS;
          break;
        case 4:
          music = MusicTypeExtra.GHASTHER;
          break;
        case 5:
          music = MusicTypeExtra.GIANT_MAGMA_GOLEM;
          break;
        case 6:
          music = MusicTypeExtra.ENDER_DRAGON;
          break;
        case 7:
          music = MusicTypeExtra.NYEHHEHHEH;
          break;
        case 8:
          music = MusicTypeExtra.JZAHAR;
          break;
        case 9:
          music = MusicTypeExtra.THE_TWINS;
          break;
        case 10:
          music = MusicTypeExtra.CHAOS_GUARDIAN;
          break;
        case 11:
          music = MusicTypeExtra.WITHER_STORM_PRE;
          break;
        case 12:
          music = MusicTypeExtra.WITHER_STORM;
          break;
        case 13:
          music = MusicTypeExtra.WITHER_STORM_DEVOURER;
          break;
        case 14:
          music = MusicTypeExtra.BONETOUSLE;
          break;
        case 15:
          music = MusicTypeExtra.DARKNESS;
          break;
        case 16:
          music = MusicTypeExtra.DARKNESS_MAX;
          break;
        case 17:
          music = MusicTypeExtra.BTERTD;
          break;
        case 18:
          music = MusicTypeExtra.ALDUIN;
          break;
        case 19:
          music = MusicTypeExtra.SOVNGARDE;
          break;
        case 68:
          music = MusicTypeExtra.WITHER_STORM_THUNDERSTORM;
          break;
        case 100:
          music = MusicTypeExtra.MEGALOVANIA;
          break;
        case 101:
          music = MusicTypeExtra.DUNKS_AND_JUDGEMENT;
          break;
      } 
    } 
    if (musicID != 0)
      EngenderMod.console("Music ID " + musicID + " was selected"); 
    return setMusic(music, musicID);
  }
  
  public void update() {
    this.ticks++;
    if (this.mc.player.world.loadedEntityList.size() != this.entities || this.ticks % 160 == 0) {
      for (Entity entity : this.mc.player.world.loadedEntityList) {
        if (!this.entityList.contains(entity) && (!entity.isNonBoss() || (entity instanceof EntityTameBase && ((EntityTameBase)entity).isABoss()) || (entity instanceof EntityBase && ((EntityBase)entity).playMusic() != 0)) && entity.ticksExisted > 10 && entity.isEntityAlive() && (!entity.isOnSameTeam((Entity)this.mc.player) || entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.cameos.EntitySans) && !entity.isSneaking() && !entity.isInvisibleToPlayer((EntityPlayer)this.mc.player)) {
          this.entityList.add(entity);
          continue;
        } 
        if (this.entityList.contains(entity) && (entity.isDead || entity.isSneaking() || entity.isInvisibleToPlayer((EntityPlayer)this.mc.player)))
          this.entityList.remove(entity); 
      } 
      if (this.entityList.isEmpty())
        setNoMusic(); 
      this.entities = this.mc.player.world.loadedEntityList.size();
      this.musicticker$musictype = selectMusicID();
      if (this.musicticker$musictype != null)
        playMusic(this.musicticker$musictype); 
      for (int j = 1; j <= this.entityList.size(); j++) {
        if (!this.mc.player.world.loadedEntityList.contains(this.entityList.get(j - 1))) {
          this.entityList.remove(this.entityList.get(j - 1));
          j--;
        } 
      } 
      this.pastTicks += 20;
    } 
    for (int i = 1; i <= this.musicList.size(); i++) {
      if (this.musicList.get(i - 1) != null) {
        ((EngenderMusic)this.musicList.get(i - 1)).onMusicUpdate(this.newMusicID);
        if (((EngenderMusic)this.musicList.get(i - 1)).isDonePlaying() || this.mc.player.isDead) {
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
        this.currentMusic = new EngenderMusic(this.mc.player, type, this.newMusicID);
        if (this.musicList.size() < 4) {
          this.musicList.add(this.currentMusic);
          EngenderMod.console("Music ID " + this.currentMusic.musicID + " was added to musicList");
        } else {
          EngenderMod.console("Music ID " + ((EngenderMusic)this.musicList.get(0)).musicID + " was removed from musicList");
          this.mc.getSoundHandler().stopSound((ISound)this.musicList.get(0));
          this.musicList.add(this.currentMusic);
          EngenderMod.console("Music ID " + this.currentMusic.musicID + " was added to musicList");
        } 
        this.mc.getSoundHandler().playSound((ISound)this.currentMusic);
      } else {
        EngenderMod.console("Music ID " + this.currentMusic.musicID + " was resumed");
      } 
      this.musicID = this.newMusicID;
    } 
  }
  
  public void stopMusic(EngenderMusic music) {
    if (music != null) {
      EngenderMod.console("Music ID " + music.musicID + " was removed from musicList");
      this.mc.getSoundHandler().stopSound((ISound)music);
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
    ENDER_DRAGON(SoundEvents.MUSIC_DRAGON),
    WITHER(ESound.withertheme),
    GIANT_MAGMA_GOLEM(ESound.giantmagmagolemtheme),
    GHASTHER(ESound.ghasthertheme),
    WITHER_STORM_PRE(ESound.commandBlockWitherTheme),
    WITHER_STORM(ESound.witherStormTheme),
    WITHER_STORM_DEVOURER(ESound.witherStormTheme2),
    WITHER_STORM_THUNDERSTORM(ESound.witherStormTheme3),
    JZAHAR(ESound.jzahartheme),
    CHAOS_GUARDIAN(ESound.chaosguardiantheme),
    SANS(ESound.sans),
    NYEHHEHHEH(ESound.nyehhehheh),
    BONETOUSLE(ESound.bonetrousle),
    TSTMPWYFSANS(ESound.tstmpwyfSans),
    BTERTD(ESound.btertd),
    MEGALOVANIA(ESound.megalovania),
    DUNKS_AND_JUDGEMENT(ESound.dunksandjudgement),
    THE_TWINS(ESound.terrariathetwins),
    SOVNGARDE(ESound.sovngarde),
    ALDUIN(ESound.alduintheme),
    DARKNESS(ESound.darkness),
    DARKNESS_MAX(ESound.darknessMax);
    
    private final SoundEvent musicLocation;
    
    MusicTypeExtra(SoundEvent musicLocationIn) {
      this.musicLocation = musicLocationIn;
    }
    
    public SoundEvent getMusicLocation() {
      return this.musicLocation;
    }
  }
}


