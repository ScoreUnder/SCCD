package me.arskhelios.commandcooldown.util;

import java.io.File;
import me.arskhelios.commandcooldown.CommandCooldown;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;

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

      this.plugin.logging.logException(error, e, false);
      try
      {
        yamlConfig.load(this.plugin.getResource(this.configName));
      }
      catch (Exception e1)
      {
        error = String.format("%s couldn't load the embedded %s.", new Object[] { this.plugin.getDescription().getName(), this.configName });

        this.plugin.logging.logException(error, e1, true);

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

        this.plugin.logging.logException(error, e, false);
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

      this.plugin.logging.logException(error, e, false);
    }
  }

  public long getLong(String path)
  {
    return this.config.getLong(path);
  }

  public boolean contains(String path)
  {
    return this.config.contains(path);
  }
}