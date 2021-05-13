package club.quar.plugin;

import club.quar.command.QCommand;
import club.quar.command.annotation.CommandInfo;
import club.quar.config.manager.QuarConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.Stream;

public abstract class QuarPlugin extends JavaPlugin {

    protected static QuarPlugin instance;
    protected final QuarConfigManager quarConfigManager = new QuarConfigManager();
    protected final PluginManager pluginManager = Bukkit.getPluginManager();

    public QuarPlugin() {
        instance = this;
    }

    public ClassLoader getSpigotClassLoader() {
        return super.getClassLoader();
    }

    public void registerListener(Listener... listeners) {
        Stream.of(listeners).forEach(listener -> pluginManager.registerEvents(listener, this));
    }

    public void registerCommand(String commandName, CommandExecutor commandExecutor) {
        getCommand(commandName).setExecutor(commandExecutor);
    }

    public void registerCommand(QCommand command) {
        Class<?> clazz = command.getClass();
        /*
        At this point, the QCommand will already have the CommandInfo annotation present,
        since it's checked in the constructor of the QCommand superclass, so there's
        no reason to double check it.
         */
        CommandInfo commandInfo = clazz.getDeclaredAnnotation(CommandInfo.class);
        String name = commandInfo.name();
        registerCommand(name, command);
    }
}
