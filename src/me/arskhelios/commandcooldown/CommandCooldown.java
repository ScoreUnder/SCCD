package me.arskhelios.commandcooldown;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import me.arskhelios.commandcooldown.util.ConfigManager;
import me.arskhelios.commandcooldown.util.LoggingManager;
import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class CommandCooldown extends JavaPlugin
{
  public ConfigManager config;
  public LoggingManager logging;
  public Map<String, Set<String>> cooldowns;

  public void onEnable()
  {
    this.config = new ConfigManager(this, "config.yml");
    this.logging = new LoggingManager(this);
    this.cooldowns = new HashMap();

    getCommand("commandcooldown").setExecutor(new CommandExecutor(this));
    getServer().getPluginManager().registerEvents(new EventListener(this), this);

    this.logging.logInfo("CommandCooldown is now enabled!");
  }

  public void onDisable()
  {
    this.config.saveConfig();
    getServer().getScheduler().cancelTasks(this);

    this.logging.logInfo("CommandCooldown is now disabled!");
  }

  public void terminate()
  {
    String alert = String.format("%s has encountered a fatal exception and will now be disabled.", new Object[] { getDescription().getName() });

    this.logging.logSevere(alert);
    getServer().getPluginManager().disablePlugin(this);
  }
}