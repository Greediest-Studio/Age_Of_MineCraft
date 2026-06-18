package net.minecraft.AgeOfMinecraft.registry;

import net.minecraft.AgeOfMinecraft.events.EngenderMusicEvent;
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
    this.xPosF = (float)player.posX;
    this.yPosF = (float)player.posY;
    this.zPosF = (float)player.posZ;
  }
  
  public void update() {
    if (this.musicID == this.newMusicID && this.volume < 1.0F) {
      this.volume += 0.01F;
    } else if ((this.musicID != this.newMusicID && this.volume > 0.0F) || this.player.isDead) {
      this.volume -= 0.01F;
    } else if ((this.musicID != this.newMusicID && this.volume <= 0.0F) || this.player.isDead) {
      this.donePlaying = true;
    } 
  }
  
  public void onMusicUpdate(int ID) {
    if (this.newMusicID != ID)
      this.newMusicID = ID; 
    this.xPosF = (float)this.player.posX;
    this.yPosF = (float)this.player.posY;
    this.zPosF = (float)this.player.posZ;
  }
}
