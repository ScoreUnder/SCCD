package me.arskhelios.commandcooldown.util;

import java.util.logging.Logger;
import me.arskhelios.commandcooldown.CommandCooldown;

public class LoggingManager
{
  private final CommandCooldown plugin;
  private final Logger logger;

  public LoggingManager(CommandCooldown instance)
  {
    this.plugin = instance;
    this.logger = instance.getLogger();
  }

  public void logInfo(String msg)
  {
    this.logger.info(msg);
  }

  public void logWarning(String msg)
  {
    this.logger.warning(msg);
  }

  public void logSevere(String msg)
  {
    this.logger.severe(msg);
  }

  public void logException(String msg, Exception exc, boolean terminate)
  {
    msg = msg + " Error: " + exc.getLocalizedMessage();
    logSevere(msg);

    if (terminate)
    {
      this.plugin.terminate();
    }
  }
}