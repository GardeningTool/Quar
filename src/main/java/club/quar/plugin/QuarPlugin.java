package club.quar.plugin;

import club.quar.config.manager.QuarConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class QuarPlugin extends JavaPlugin {

    public final QuarConfigManager quarConfigManager = new QuarConfigManager();

}
