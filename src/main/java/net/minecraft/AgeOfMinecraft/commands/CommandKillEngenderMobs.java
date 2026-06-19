package net.minecraft.AgeOfMinecraft.commands;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.util.EntityCompat;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import java.util.Collections;
import java.util.List;

public class CommandKillEngenderMobs extends CommandBase {
  public String getName() {
    return "destroyteam";
  }

  public String func_71517_b() {
    return "destroyteam";
  }

  public int getRequiredPermissionLevel() {
    return 0;
  }

  public String getUsage(ICommandSender sender) {
    return "/destroyteam";
  }

  public String func_71518_a(ICommandSender sender) {
    return "/destroyteam";
  }

  public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
    for (Entity entity : EntityCompat.loadedEntityList(EntityCompat.world(getCommandSenderAsPlayer(sender)))) {
      if (entity instanceof EntityTameBase)
        entity.setDead();
    }
  }

  public void func_184881_a(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
    execute(server, sender, args);
  }

  public boolean func_184882_a(MinecraftServer server, ICommandSender sender) {
    return true;
  }

  public List<String> func_71514_a() {
    return Collections.emptyList();
  }

  public List<String> func_184883_a(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
    return Collections.emptyList();
  }

  public boolean func_82358_a(String[] args, int index) {
    return false;
  }
}
