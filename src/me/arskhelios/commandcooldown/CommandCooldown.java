package me.arskhelios.commandcooldown;

import java.util.HashMap;
import java.util.Map;
import me.arskhelios.commandcooldown.util.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandCooldown extends JavaPlugin
{
  public ConfigManager config;
  public Map<String, Map<String, Long>> cooldowns;

  @Override
  public void onEnable()
  {
    this.config = new ConfigManager(this, "config.yml");
    this.cooldowns = new HashMap<String, Map<String, Long>>();

    getCommand("commandcooldown").setExecutor(new SCCommandExecutor(this));
    getServer().getPluginManager().registerEvents(new EventListener(this), this);
  }
}
