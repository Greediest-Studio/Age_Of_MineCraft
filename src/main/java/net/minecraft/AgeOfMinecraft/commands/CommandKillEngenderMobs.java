package net.minecraft.AgeOfMinecraft.commands;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandKillEngenderMobs extends CommandBase {
  public String getName() {
    return "destroyteam";
  }
  
  public int getRequiredPermissionLevel() {
    return 0;
  }
  
  public String getUsage(ICommandSender sender) {
    return "commands.destroyteam.usage";
  }
  
  public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
    if (args.length != 0) {
      EntityPlayerMP pkill = getCommandSenderAsPlayer(sender);
      if (pkill != null)
        if (args.length == 1 && args[0].equals("wild")) {
          for (Entity entity : pkill.world.loadedEntityList) {
            if (entity instanceof EntityTameBase && ((EntityTameBase)entity).isWild()) {
              entity.setDead();
              notifyCommandListener(sender, this, "commands.destroyteam.successful", entity.getDisplayName());
            } 
          } 
        } else if (args.length == 1 && args[0].equals("all")) {
          for (Entity entity : pkill.world.loadedEntityList) {
            if (entity instanceof EntityTameBase) {
              entity.setDead();
              notifyCommandListener(sender, this, "commands.destroyteam.successful", entity.getDisplayName());
            } 
          } 
        } else if (args.length == 1 && args[0].equals("not")) {
          for (Entity entity : pkill.world.loadedEntityList) {
            if (entity instanceof EntityTameBase && ((EntityTameBase)entity).getOwnerId() != pkill.getUniqueID()) {
              entity.setDead();
              notifyCommandListener(sender, this, "commands.destroyteam.successful", entity.getDisplayName());
            } 
          } 
        } else if (args.length == 1 && args[0].equals("only")) {
          for (Entity entity : pkill.world.loadedEntityList) {
            if (entity instanceof EntityTameBase && ((EntityTameBase)entity).getOwnerId() == pkill.getUniqueID()) {
              entity.setDead();
              notifyCommandListener(sender, this, "commands.destroyteam.successful", entity.getDisplayName());
            } 
          } 
        }  
    } else {
      throw new WrongUsageException("command.destroyteam.fail");
    } 
  }
  
  public boolean isUsernameIndex(String[] args, int index) {
    return (index == 0);
  }
  
  public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
    if (args.length == 1)
      return getListOfStringsMatchingLastWord(args, "wild", "all", "not", "only");
    return Collections.emptyList();
  }
}
