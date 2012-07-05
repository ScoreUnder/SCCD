package me.arskhelios.commandcooldown;

import me.arskhelios.commandcooldown.util.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommandExecutor
  implements org.bukkit.command.CommandExecutor
{
  private final CommandCooldown plugin;

  public CommandExecutor(CommandCooldown instance)
  {
    this.plugin = instance;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {

       if(args.length == 0) { //If the arguments given are none, so they typed /command, send them this
           sender.sendMessage(ChatColor.GREEN + "SCCommandCooldown Version 1.0.0");
           return true;
       }
      
       //This means they typed /command reload
       if (args[0].equalsIgnoreCase("reload") && (sender.hasPermission("commandcooldown.reload") || sender.isOp() || sender instanceof ConsoleCommandSender)) 
       {
          this.plugin.config.loadConfig();
          sender.sendMessage("The CommandCooldown config has been reloaded.");
          return true;
       }
       else 
       {
           sender.sendMessage(ChatColor.RED + "You don't have permission to do that.");
           return true;
       }
    }
}