package ml.bmlzootown.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class SimpleBroadcastCommand implements org.bukkit.command.CommandExecutor
{
  Plugin p;
  
  public SimpleBroadcastCommand(Plugin p)
  {
    this.p = p;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (args.length == 0) {
      sender.sendMessage("§a=== §f| §3<§4++ §6Simple Broadcast §4++§3> §f| §a===");
      sender.sendMessage("§aType | /sb help |  for more details");
      sender.sendMessage("§aAutoBroadcast plugin! ");
      sender.sendMessage("§a=== §f| §3<§4+++ §6By DarkBug198, bmlzootown §4+++§3> §f| §a===");
    } else if ((args.length == 1) && (args[0].equalsIgnoreCase("help"))) {
      sender.sendMessage("§a=== §f| §3<§4++ §6Simple Broadcast §4++§3> §f| §a===");
      sender.sendMessage("§9/bc : Broadcast on chat / Usage: /bc <message> ");
      sender.sendMessage("§9/bc-r : Reload the plugin / Usage: /bc-r ");
      sender.sendMessage("§a=== §f| §3<§4+++ §6By DarkBug198, bmlzootown §4+++§3> §f| §a===");
    } else if ((args.length >= 2) || (!args[0].equalsIgnoreCase("help"))) {
      sender.sendMessage("§4Wrong Arguments!");
    }
    return false;
  }
}
