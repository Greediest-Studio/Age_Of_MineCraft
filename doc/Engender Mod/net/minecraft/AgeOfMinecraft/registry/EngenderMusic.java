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
    this.field_147659_g = true;
    this.field_147665_h = 0;
    this.field_147662_b = (type == EngenderMusicEvent.MusicTypeExtra.MEGALOVANIA) ? 1.0F : 0.01F;
    this.field_147660_d = (float)player.field_70165_t;
    this.field_147661_e = (float)player.field_70163_u;
    this.field_147658_f = (float)player.field_70161_v;
  }
  
  public void func_73660_a() {
    if (this.musicID == this.newMusicID && this.field_147662_b < 1.0F) {
      this.field_147662_b += 0.01F;
    } else if ((this.musicID != this.newMusicID && this.field_147662_b > 0.0F) || this.player.field_70128_L) {
      this.field_147662_b -= 0.01F;
    } else if ((this.musicID != this.newMusicID && this.field_147662_b <= 0.0F) || this.player.field_70128_L) {
      this.field_147668_j = true;
    } 
  }
  
  public void onMusicUpdate(int ID) {
    if (this.newMusicID != ID)
      this.newMusicID = ID; 
    this.field_147660_d = (float)this.player.field_70165_t;
    this.field_147661_e = (float)this.player.field_70163_u;
    this.field_147658_f = (float)this.player.field_70161_v;
  }
}
