package net.minecraft.AgeOfMinecraft.commands;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandKillEngenderMobs extends CommandBase {
  public String func_71517_b() {
    return "destroyteam";
  }
  
  public int func_82362_a() {
    return 0;
  }
  
  public String func_71518_a(ICommandSender sender) {
    return "commands.destroyteam.usage";
  }
  
  public void func_184881_a(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
    if (args.length != 0) {
      EntityPlayerMP pkill = func_71521_c(sender);
      if (pkill != null)
        if (args.length == 1 && args[0].equals("wild")) {
          for (Entity entity : pkill.field_70170_p.field_72996_f) {
            if (entity instanceof EntityTameBase && ((EntityTameBase)entity).isWild()) {
              entity.func_70106_y();
              func_152373_a(sender, (ICommand)this, "commands.destroyteam.successful", new Object[] { entity.func_145748_c_() });
            } 
          } 
        } else if (args.length == 1 && args[0].equals("all")) {
          for (Entity entity : pkill.field_70170_p.field_72996_f) {
            if (entity instanceof EntityTameBase) {
              entity.func_70106_y();
              func_152373_a(sender, (ICommand)this, "commands.destroyteam.successful", new Object[] { entity.func_145748_c_() });
            } 
          } 
        } else if (args.length == 1 && args[0].equals("not")) {
          for (Entity entity : pkill.field_70170_p.field_72996_f) {
            if (entity instanceof EntityTameBase && ((EntityTameBase)entity).func_184753_b() != pkill.func_110124_au()) {
              entity.func_70106_y();
              func_152373_a(sender, (ICommand)this, "commands.destroyteam.successful", new Object[] { entity.func_145748_c_() });
            } 
          } 
        } else if (args.length == 1 && args[0].equals("only")) {
          for (Entity entity : pkill.field_70170_p.field_72996_f) {
            if (entity instanceof EntityTameBase && ((EntityTameBase)entity).func_184753_b() == pkill.func_110124_au()) {
              entity.func_70106_y();
              func_152373_a(sender, (ICommand)this, "commands.destroyteam.successful", new Object[] { entity.func_145748_c_() });
            } 
          } 
        }  
    } else {
      throw new WrongUsageException("command.destroyteam.fail", new Object[0]);
    } 
  }
  
  public boolean func_82358_a(String[] args, int index) {
    return (index == 0);
  }
  
  public List<String> func_184883_a(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
    if (args.length > 0 && args.length == 1)
      return func_71530_a(args, new String[] { "wild", "all", "not", "only" }); 
    return Collections.emptyList();
  }
}
