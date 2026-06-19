package net.minecraft.AgeOfMinecraft.registry;

import net.minecraft.AgeOfMinecraft.events.EngenderMusicEvent;
import net.minecraft.AgeOfMinecraft.util.EntityCompat;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.SoundCategory;

public class EngenderMusic extends MovingSound {
  public int musicID;
  
  private int newMusicID;
  
  private EntityPlayerSP player;
  
  public EngenderMusic(EntityPlayerSP player, EngenderMusicEvent.MusicTypeExtra type, int ID) {
    super(type.getMusicLocation(), SoundCategory.MUSIC);
    this.musicID = ID;
    this.newMusicID = ID;
    this.player = player;
    this.repeat = true;
    this.repeatDelay = 0;
    this.volume = (type == EngenderMusicEvent.MusicTypeExtra.MEGALOVANIA) ? 1.0F : 0.01F;
    this.xPosF = (float)EntityCompat.posX(player);
    this.yPosF = (float)EntityCompat.posY(player);
    this.zPosF = (float)EntityCompat.posZ(player);
  }
  
  public void update() {
    if (this.musicID == this.newMusicID && this.volume < 1.0F) {
      this.volume += 0.01F;
    } else if ((this.musicID != this.newMusicID && this.volume > 0.0F) || EntityCompat.isDead(this.player)) {
      this.volume -= 0.01F;
    } else if ((this.musicID != this.newMusicID && this.volume <= 0.0F) || EntityCompat.isDead(this.player)) {
      this.donePlaying = true;
    } 
  }
  
  public void onMusicUpdate(int ID) {
    if (this.newMusicID != ID)
      this.newMusicID = ID; 
    this.xPosF = (float)EntityCompat.posX(this.player);
    this.yPosF = (float)EntityCompat.posY(this.player);
    this.zPosF = (float)EntityCompat.posZ(this.player);
  }
}
