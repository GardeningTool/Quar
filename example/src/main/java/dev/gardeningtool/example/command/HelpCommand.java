package dev.gardeningtool.example.command;

import club.quar.command.QCommand;
import club.quar.command.annotation.CommandInfo;
import org.bukkit.command.CommandSender;

@CommandInfo(name = "help", requiredPermission = "", requirePermission = false, async = true, requirePlayer = true)
public class HelpCommand extends QCommand {

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        sendMessage(sender, "&fDo /spawn to go to spawn!!!!!!");
    }

    @Override
    public String permissionMessage(CommandSender sender, String[] args) {
        return null; //Will never be called, since requirePermission is false in the CommandInfo
    }
}
