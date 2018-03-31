package ml.bmlzootown;

import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config
{
  public static File DefaultCf = new File("plugins/Simple-Broadcast/config.yml");
  public static YamlConfiguration DefaultC = YamlConfiguration.loadConfiguration(DefaultCf);
  
  public Config() {}
}
