package dev.gardeningtool.example;

import club.quar.config.QuarConfig;
import club.quar.config.annotation.ConfigField;
import club.quar.plugin.QuarPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class QuarExample extends QuarPlugin implements Listener, QuarConfig {

    @ConfigField(path = "messages.joinMessage")
    private String joinMessage;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
        FileConfiguration configuration = getConfig();
        quarConfigManager.registerConfig(configuration, this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(String.format(joinMessage, event.getPlayer().getName()));
    }

}
