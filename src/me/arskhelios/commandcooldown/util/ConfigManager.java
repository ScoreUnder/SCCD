package me.arskhelios.commandcooldown.util;

import java.io.File;
import java.util.logging.Level;
import me.arskhelios.commandcooldown.CommandCooldown;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager
{
  private final CommandCooldown plugin;
  private final String configName;
  private final File configFile;
  private FileConfiguration config;

  public ConfigManager(CommandCooldown instance, String name)
  {
    this.plugin = instance;
    this.configName = name;
    this.configFile = new File(instance.getDataFolder() + File.separator + name);
    loadConfig();
  }

  public void loadConfig()
  {
    YamlConfiguration yamlConfig = new YamlConfiguration();

    if (!this.configFile.exists())
    {
      this.plugin.getDataFolder().mkdirs();
      this.plugin.saveResource(this.configName, true);
    }

    try
    {
      yamlConfig.load(this.configFile);
    }
    catch (Exception e)
    {
      String error = String.format("%s couldn't load %s from file. Attempting to load the embedded %2$s.", new Object[] { this.plugin.getDescription().getName(), this.configName });

      this.plugin.getLogger().log(Level.SEVERE, error, e);
      try
      {
        yamlConfig.load(this.plugin.getResource(this.configName));
      }
      catch (Exception e1)
      {
        error = String.format("%s couldn't load the embedded %s.", new Object[] { this.plugin.getDescription().getName(), this.configName });

        this.plugin.getLogger().log(Level.SEVERE, error, e1);

        this.config = null;
        return;
      }
    }

    this.config = yamlConfig;
  }

  public void saveConfig()
  {
    if (!this.configFile.exists())
    {
      this.plugin.getDataFolder().mkdirs();
      try
      {
        this.configFile.createNewFile();
      }
      catch (Exception e)
      {
        String error = String.format("%s couldn't create a new %s. %2$s will not be saved.", new Object[] { this.plugin.getDescription().getName(), this.configName });

        this.plugin.getLogger().log(Level.SEVERE, error, e);
        return;
      }
    }

    try
    {
      this.config.save(this.configFile);
    }
    catch (Exception e)
    {
      String error = String.format("%s couldn't save %s to file.", new Object[] { this.plugin.getDescription().getName(), this.configName });

      this.plugin.getLogger().log(Level.SEVERE, error, e);
    }
  }
  
  public long getCommandDelay(String path)
  {
    return (long) (this.config.getDouble(path) * 1000);
  }

  public boolean contains(String path)
  {
    return this.config.contains(path);
  }
}