package club.quar.command;

import club.quar.command.annotation.CommandInfo;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public abstract class QCommand implements CommandExecutor {

    protected static final ExecutorService asyncExecutor = Executors.newSingleThreadExecutor();
    private final CommandInfo commandInfo;

    public QCommand() {
        Class<?> clazz = getClass();
        if (!clazz.isAnnotationPresent(CommandInfo.class)) {
            throw new RuntimeException("CommandInfo annotation not present!");
        }
        this.commandInfo = clazz.getDeclaredAnnotation(CommandInfo.class);
    }
    public abstract void onCommand(CommandSender sender, String[] args);

    public abstract String permissionMessage(CommandSender sender, String[] args);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Runnable runnable = () -> {
            if (commandInfo.requirePermission() && !(sender instanceof Player)) {
                System.out.println("This command must be executed as a player!");
                return;
            }
            if (commandInfo.requirePermission() && !sender.hasPermission(commandInfo.requiredPermission())) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', permissionMessage(sender, args)));
                return;
            }
            onCommand(sender, args);
        };
        if (commandInfo.async()) {
            asyncExecutor.execute(runnable);
        } else {
            runnable.run();
        }
        return true;
    }

    public void sendMessage(CommandSender sender, String... messages) {
        Stream.of(messages).map(message -> ChatColor.translateAlternateColorCodes('&', message))
                .forEach(sender::sendMessage);
    }
}
