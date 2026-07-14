package net.minecraft.AgeOfMinecraft.events;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.util.ClientCompat;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EngenderMusicHandler {
  public static final EngenderMusicHandler INSTANCE = new EngenderMusicHandler();

  public static EngenderMusicEvent musicTicker;

  @SubscribeEvent
  public void onClientTick(TickEvent.ClientTickEvent event) {
    Minecraft mc = Minecraft.getMinecraft();
    if (musicTicker != null && ClientCompat.world(mc) != null)
      if (EngenderConfig.general.useMusic) {
        musicTicker.update();
      } else {
        musicTicker.setNoMusic();
      }
  }

  @SubscribeEvent
  public void onMobDeathEvent(LivingDeathEvent event) {
    if (event.getEntity() instanceof EntityPlayer && musicTicker != null) {
      musicTicker.setNoMusic();
      musicTicker = null;
    }
  }

  @SubscribeEvent
  public void onMobSpawnEvent(EntityJoinWorldEvent event) {
    if (event.getEntity() instanceof EntityPlayer)
      musicTicker = new EngenderMusicEvent(Minecraft.getMinecraft());
  }
}