package me.arskhelios.commandcooldown;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class EventListener implements Listener
{
  private final CommandCooldown plugin;

  public EventListener(CommandCooldown instance)
  {
    this.plugin = instance;
  }

  @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
  public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event)
  {
    String command = event.getMessage().substring(1).trim().toLowerCase();
    command = getLongestMatchingCommand(command);

    long delay = this.plugin.config.getCommandDelay(command);

    if (delay == 0)
    {
      return; // No delay, let's not go through the rest of it too.
    }

    long currTime = System.currentTimeMillis();
    Player player = event.getPlayer();
    String name = player.getName();

    Map<String, Long> playerCooldowns = this.plugin.cooldowns.get(name);

    if (playerCooldowns == null)
    {
      playerCooldowns = new HashMap<String, Long>();
      this.plugin.cooldowns.put(name, playerCooldowns);
    }
    else if (playerCooldowns.containsKey(command))
    {
      long timeLeft = playerCooldowns.get(command) - currTime;
      if (timeLeft > 0)
      {
        event.setCancelled(true);
        player.sendMessage(ChatColor.RED + "Please wait " + numSeconds(ceilDiv(timeLeft, 1000)) + " before using that command again.");
        return;
      }
    }

    playerCooldowns.put(command, currTime + delay);
  }

  public static String numSeconds(long num)
  {
    if (num == 1)
    {
      return "1 second";
    }
    return num + " seconds";
  }

  private String getLongestMatchingCommand(String command)
  {
    while (!this.plugin.config.contains(command))
    {
      int pos = command.lastIndexOf(' ');
      if (pos == -1)
      {
        break;
      }
      command = command.substring(0, pos);
    }
    return command;
  }

  /**
   * Divides and rounds up.
   * Equivalent of (int)Math.ceil(num / (double) div).
   * @param num Number to divide
   * @param div Divisor
   * @return the result of the division rounded up
   */
  public static long ceilDiv(long num, long div)
  {
    return (num + div - 1) / div;
  }
}