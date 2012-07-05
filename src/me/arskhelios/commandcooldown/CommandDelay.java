package me.arskhelios.commandcooldown;

import java.util.Map;
import java.util.Set;

class CommandDelay
  implements Runnable
{
  private final CommandCooldown plugin;
  private final String name;
  private final String command;

  public CommandDelay(CommandCooldown instance, String name, String command)
  {
    this.plugin = instance;
    this.name = name;
    this.command = command;
  }

  public void run()
  {
    if ((this.plugin.cooldowns.containsKey(this.name)) && (((Set)this.plugin.cooldowns.get(this.name)).contains(this.command)))
    {
      ((Set)this.plugin.cooldowns.get(this.name)).remove(this.command);
    }
  }
}