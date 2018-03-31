package ml.bmlzootown.Commands;

import ml.bmlzootown.SimpleBroadcast;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class ReloadCommand
  implements CommandExecutor
{
  SimpleBroadcast plugin;
  
  public ReloadCommand(SimpleBroadcast m)
  {
    plugin = m;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!sender.hasPermission("SimpleBroadcast.reload")) {
      sender.sendMessage(ChatColor.RED + "You don't have permission to do that!");
      return true;
    }
    sender.sendMessage("§aPlugin §3reloaded§a!");
    plugin.getServer().getPluginManager().disablePlugin(plugin);
    plugin.getServer().getPluginManager().enablePlugin(plugin);
    return false;
  }
}
