package net.minecraft.AgeOfMinecraft.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.AgeOfMinecraft.EngenderMod;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;

public class ChunkLoadingEvent implements ForgeChunkManager.LoadingCallback {
  public static ChunkLoadingEvent instance;
  
  public static Map<Entity, ForgeChunkManager.Ticket> ticketList = new HashMap<>();
  
  public static Map<Entity, ArrayList<ChunkPos>> chunkList = new HashMap<>();
  
  public static boolean hasReportedIssue = false;
  
  public static void init() {
    instance = new ChunkLoadingEvent();
    MinecraftForge.EVENT_BUS.register(instance);
    ForgeChunkManager.setForcedChunkLoadingCallback(EngenderMod.instance, instance);
  }
  
  public static void updateLoaded(Entity mob) {
    ArrayList<ChunkPos> dragonChunks = new ArrayList<>();
    for (int xx = (int)mob.posX / 16 - 2; xx <= (int)mob.posX / 16 + 2; xx++) {
      for (int zz = (int)mob.posZ / 16 - 2; zz <= (int)mob.posZ / 16 + 2; zz++)
        dragonChunks.add(new ChunkPos(xx, zz)); 
    } 
    if (chunkList.containsKey(mob) && dragonChunks.hashCode() == ((ArrayList)chunkList.get(mob)).hashCode())
      return; 
    if (ticketList.containsKey(mob)) {
      ForgeChunkManager.Ticket ticket1 = ticketList.get(mob);
      ForgeChunkManager.releaseTicket(ticket1);
    } 
    ForgeChunkManager.Ticket ticket = ForgeChunkManager.requestTicket(EngenderMod.instance, mob.world, ForgeChunkManager.Type.ENTITY);
    if (ticket != null) {
      ticket.bindEntity(mob);
      ticket.setChunkListDepth(25);
      ticketList.put(mob, ticket);
    } 
    for (ChunkPos pos : dragonChunks)
      ForgeChunkManager.forceChunk(ticket, pos); 
    chunkList.put(mob, dragonChunks);
  }
  
  public static void stopLoading(Entity guardian) {
    if (!ticketList.containsKey(guardian))
      return; 
    ForgeChunkManager.Ticket ticket = ticketList.get(guardian);
    ForgeChunkManager.releaseTicket(ticket);
    ticketList.remove(guardian);
  }
  
  public void ticketsLoaded(List<ForgeChunkManager.Ticket> tickets, World world) {
    if (!tickets.isEmpty())
      for (ForgeChunkManager.Ticket ticket : tickets)
        ForgeChunkManager.releaseTicket(ticket);  
  }
}
