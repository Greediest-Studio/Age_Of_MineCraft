package net.minecraft.AgeOfMinecraft.entity.tame;

import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.Team;

public interface ITeamedMobs {
  Team getTeam();
  
  UUID getOwnerId();
  
  Entity getOwner();
  
  EnumTier getTier();
}
