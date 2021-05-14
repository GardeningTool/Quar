package dev.gardeningtool.example;

import club.quar.config.QuarConfig;
import club.quar.config.annotation.ConfigField;
import club.quar.plugin.QuarPlugin;
import dev.gardeningtool.example.command.HelpCommand;
import dev.gardeningtool.example.inventory.ExampleInventory;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class QuarExample extends QuarPlugin implements Listener, QuarConfig {

    private final ExampleInventory inventory = new ExampleInventory();

    @ConfigField(path = "messages.joinMessage")
    private String joinMessage;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
        FileConfiguration configuration = getConfig();
        quarConfigManager.registerConfig(configuration, this);

        registerCommand(new HelpCommand());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(String.format(joinMessage, player.getName()));
        player.openInventory(inventory.build(player));
    }

}
