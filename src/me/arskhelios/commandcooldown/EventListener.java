package me.arskhelios.commandcooldown;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import me.arskhelios.commandcooldown.util.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scheduler.BukkitScheduler;

public class EventListener
  implements Listener
{
  private final CommandCooldown plugin;
  private final BukkitScheduler scheduler;

  public EventListener(CommandCooldown instance)
  {
    this.plugin = instance;
    this.scheduler = instance.getServer().getScheduler();
  }

  @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
  public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
    String command = event.getMessage().replace("/", "").split(" ")[0].toLowerCase();
    long delay = this.plugin.config.getLong(command);
    int delayInSeconds = (int) (delay / 20); 
    Player player = event.getPlayer();
    String name = player.getName();

    if (this.plugin.cooldowns.containsKey(name))
    {
      if (((Set)this.plugin.cooldowns.get(name)).contains(command))
      {
        event.setCancelled(true);
 player.sendMessage(ChatColor.RED + "That command is currently on cooldown for "+delayInSeconds+" seconds");

        return;
      }

    }
    else
    {
      this.plugin.cooldowns.put(name, new HashSet());
    }

    ((Set)this.plugin.cooldowns.get(name)).add(command);
    this.scheduler.scheduleSyncDelayedTask(this.plugin, new CommandDelay(this.plugin, name, command), delay);
  }
}