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

/**
 * Abstract class used for creation of commands by Quar.
 */
public abstract class QCommand implements CommandExecutor {

    // Our thread that is used for async commands.
    protected static final ExecutorService ASYNC_EXECUTOR = Executors.newSingleThreadExecutor();

    // Cached command annotation.
    private final CommandInfo commandInfo;

    /**
     * When the class is constructed cache the annotation as getting
     * annotations require reflection and reflection is slow compared to direct access.
     */
    public QCommand() {
        // Get our class and store it in a variable.
        Class<?> clazz = getClass();

        // Check if the wanted annotation is present on the class as its necessary.
        if (!clazz.isAnnotationPresent(CommandInfo.class)) {
            // If an annotation is not found, throw an exception to tell the user its required.
            throw new RuntimeException("CommandInfo annotation not present!");
        }

        // Cache the annotation on a field.
        this.commandInfo = clazz.getDeclaredAnnotation(CommandInfo.class);
    }

    /**
     * This abstract method gets called whenever the requested command has been called.
     *
     * @param sender The user who sent the command to the server.
     * @param args The arguments given for the command.
     */
    public abstract void onCommand(CommandSender sender, String[] args);

    /**
     * If the user lacks the permissions to run this command send a message which informs the user.
     *
     * @param sender The user who sent the command to the server.
     * @param args The arguments given for the command.
     * @return The message that was set.
     */
    public abstract String permissionMessage(CommandSender sender, String[] args);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Create a runnable with the handing that has to be done.
        Runnable runnable = () -> {
            /*
             * If the user wanted to command to be only ran by a player and the entity
             * who ran it was not a player log that the command was executed by a non player.
             */
            if (commandInfo.requirePlayer() && !(sender instanceof Player)) {
                sender.sendMessage("This command must be executed as a player!");
            }

            // If the user requested a permission for the command check if they have it and or else tell the player they don't have the permissions.
            if (commandInfo.requirePermission() && !sender.hasPermission(commandInfo.requiredPermission())) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', permissionMessage(sender, args)));
                return;
            }

            // Handle the command.
            onCommand(sender, args);
        };

        // If the command was set to be async call the command on a separate thread provided by Quar.
        if (commandInfo.async()) {
            ASYNC_EXECUTOR.execute(runnable);
        } else {
            runnable.run();
        }

        return true;
    }

    /**
     * Send a color formatted messages that is given by a parameter.
     * The character for color codes are '&'.
     *
     * @param sender The entity we want to send the command to.
     * @param messages The messages that should be formatted and sent.
     */
    public void sendMessage(CommandSender sender, String... messages) {
        Stream.of(messages).map(message -> ChatColor.translateAlternateColorCodes('&', message))
                .forEach(sender::sendMessage);
    }
}
