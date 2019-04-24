package ml.bmlzootown;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ml.bmlzootown.Commands.ReloadCommand;
import ml.bmlzootown.Commands.SimpleBroadcastCommand;


public class SimpleBroadcast extends JavaPlugin {
	private static int currentLine = 0;
	private static int lastLine;
	private static String prefix;
	private static boolean makeNoise;
	private static String sound;
	private static int pitch;
	private static List<String> lines;

	private static int version = 5;

	public SimpleBroadcast() {}

	public void onEnable() {
		if (!Config.DefaultCf.exists()) {
			saveResource("config.yml", true);
		}

		//Load Config
		getLogger().info("Loading Configuration ...");
		getConfig().options().copyDefaults(true);
		prefix = getConfig().getString("Prefix");
		makeNoise = getConfig().getBoolean("Settings.Sound-on-broadcast.Enabled");
		sound = getConfig().getString("Settings.Sound-on-broadcast.Sound");
		pitch = getConfig().getInt("Settings.Sound-on-broadcast.Pitch");
		getLogger().info("The configuration has loaded!!");

		//Load Commands
		getLogger().info("Loading Commands...");
		getCommand("broadcast-reload").setExecutor(new ReloadCommand(this));
		getCommand("simplebroadcast").setExecutor(new SimpleBroadcastCommand(this));
		getLogger().info("Commands loaded!");

		//Load Messages
		File dfile = new File(getDataFolder(), "messages.txt");
		if (dfile.exists()) {
			getLogger().info("Loading Messages!");
			//Loading from file...
			try {
				lines = loadMessages(dfile);
			} catch (FileNotFoundException err) {
				err.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//Get number of lines
			lastLine = lines.size();

			//Start scheduler and broadcast messages at set interval
			Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
				public void run() {
					broadcastMsg(lines);
				}
			}, 0L, getConfig().getInt("Settings.Delay-Time"));
			getLogger().info("AutoMessages loaded!");
		} else {
			try {
				dfile.createNewFile();
				FileWriter fw = new FileWriter(dfile.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write("Example announcement!");
				bw.close();
			} catch (IOException err) {
				err.printStackTrace();
			}
		}

		if (getConfig().getInt("Version") != version) {
			getLogger().info("Your config version is " + getConfig().getInt("Version") + "! Please update the configuration file!");
		}

		getLogger().info("This plugin has enabled!");
	}

	public void onDisable() {
		getLogger().info("Saving Configuration...");
		getLogger().info("Configuration saved");
		getLogger().info(" This plugin has disabled!");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("broadcast")) {
			if (args.length >= 1) {
				String msg = "";
				for (int i = 0; i < args.length; i++) {
					msg = String.valueOf(msg) + args[i] + " ";
				}
				String serverport = Integer.toString(Bukkit.getServer().getPort());
				String online = Integer.toString(Bukkit.getOnlinePlayers().size());
				String monsterlimit = Integer.toString(Bukkit.getMonsterSpawnLimit());

				getServer().broadcastMessage(String.valueOf(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Prefix"))) + " " + ChatColor.translateAlternateColorCodes('&', msg).replace("%server-name%", Bukkit.getServer().getName()).replace("%server-motd%", Bukkit.getServer().getMotd()).replace("%version%", Bukkit.getServer().getBukkitVersion()).replace("%server-port%", serverport).replace("%monster-spawn-limit%", monsterlimit).replace("%online%", online));
				soundonbroadcast();
			}
			else {
				sender.sendMessage(ChatColor.RED + "Wrong Syntax: A message must contain at least 1 word!");
			}
			return true;
		}
		return false;
	}

	public static void broadcastMsg(List<String> list) {
		String msg = list.get(currentLine);

		String message = String.valueOf(ChatColor.translateAlternateColorCodes('&', prefix)) + " " + ChatColor.translateAlternateColorCodes('&', msg)
				.replace("%server-name%", Bukkit.getServer().getName())
				.replace("%server-motd%", Bukkit.getServer().getMotd())
				.replace("%version%", Bukkit.getServer().getBukkitVersion())
				.replace("%server-port%", Integer.toString(Bukkit.getServer().getPort()))
				.replace("%monster-spawn-limit%", Integer.toString(Bukkit.getMonsterSpawnLimit()))
				.replace("%online%", Integer.toString(Bukkit.getOnlinePlayers().size()));

		if (currentLine + 1 == lastLine) {
			currentLine = 0;
		} else {
			currentLine += 1;
		}

		Bukkit.getServer().broadcastMessage(message);
		soundonbroadcast();
	}

	public static List<String> loadMessages(File filename) throws IOException {
		List<String> lines = new ArrayList<>();
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String str;

		while((str = in.readLine()) != null){
			lines.add(str);
		}

		return lines;
	}

	public static void soundonbroadcast() {
		if (makeNoise) {
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				p.playSound(p.getLocation(), Sound.valueOf(sound),
						10.0F, pitch);
			}
		}
	}
}
