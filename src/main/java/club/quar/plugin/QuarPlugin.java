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

    /**
     * This can be useful for libraries such as ClassIndex, where indexing subclasses
     * requires a ClassLoader as a parameter
     * @return the ClassLoader
     */
    public ClassLoader getSpigotClassLoader() {
        return super.getClassLoader();
    }

    /**
     * Register Bukkit event listeners
     * @param listeners An array of Listeners
     */
    public void registerListener(Listener... listeners) {
        Stream.of(listeners).forEach(listener -> pluginManager.registerEvents(listener, this));
    }

    /**
     * Register a command to Bukkit's command system
     * @param commandName The name of the command, as defined in the plugin.yml
     * @param commandExecutor An instance of the class that will be handling the command
     */
    public void registerCommand(String commandName, CommandExecutor commandExecutor) {
        getCommand(commandName).setExecutor(commandExecutor);
    }

    /**
     * Register a QCommand
     * @param command The command to be registered
     */
    public void registerCommand(QCommand command) {
        Class<?> clazz = command.getClass();
        /*
        At this point, the QCommand will already have the CommandInfo annotation present,
        since it's checked in the constructor of the QCommand superclass, so there's
        no reason to double check it.
         */
        CommandInfo commandInfo = clazz.getDeclaredAnnotation(CommandInfo.class);
        /*
        Command name and data is all processed by annotations
         */
        String name = commandInfo.name();
        /*
        Register the command to Bukkit's command system, since QCommand imlpements Commadndxecutor
         */
        registerCommand(name, command);
    }
}
