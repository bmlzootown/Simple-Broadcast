package ro.DarkBug198.SimpleBroadcast;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		getLogger().info("Loading Configuration ...");
		getConfig().addDefault("Prefix", "&3Broadcast &a>&e");
		getConfig().addDefault("Version", "1.0");
		getConfig().options().copyDefaults(true);
		saveConfig();
		getLogger().info("The configuration has loaded!!");
		getLogger().info("This plugin has enabled!");
	}

	@Override
	public void onDisable() {
		getLogger().info("Saving Configuration...");
		getLogger().info("Configuration saved");
		getLogger().info(" This plugin has disabled!");
	}
	
	public boolean onCommand(CommandSender sender , Command cmd , String label , String[] args) {
		if (cmd.getName().equalsIgnoreCase("broadcast")) {
			if (sender.hasPermission("SimpleBroadcast.broadcast")) {
				if (args.length >= 1) {
					
					String msg = "";
					for (int i = 0; i < args.length; i++) {
						msg = msg + args[i] + " ";
					}
							
					getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Prefix")) + " " + ChatColor.translateAlternateColorCodes('&', msg).replace("%server-name%", getServer().getServerName()).replace("%server-motd%", getServer().getMotd()));
			} else {
				sender.sendMessage(ChatColor.RED + "Wrong Syntax: A message must contain at least 1 word!");
			}
			
		return true;
			
			}
		}
		
		return false;
	}
}
