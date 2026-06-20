package net.minecraft.AgeOfMinecraft.commands;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.util.EntityCompat;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;

public class CommandKillEngenderMobs extends CommandBase {
  public String getName() {
    return "destroyteam";
  }

  public int getRequiredPermissionLevel() {
    return 0;
  }

  public String getUsage(ICommandSender sender) {
    return "/destroyteam";
  }

  public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
    for (Entity entity : EntityCompat.loadedEntityList(EntityCompat.world(getCommandSenderAsPlayer(sender)))) {
      if (entity instanceof EntityTameBase)
        entity.setDead();
    }
  }
}
