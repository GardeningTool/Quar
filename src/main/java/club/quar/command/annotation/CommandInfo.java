package club.quar.command.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * The annotation for Quar commands which is required for commands to be understood by Quar in order to process commands.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInfo {

    /**
     * The name of the command.
     */
    String name();

    /**
     * The permission the command user has to have to run the command.
     * Example: "quar.coolcommand"
     */
    String requiredPermission() default "";

    /**
     * If the command should require a permission at all.
     * If the command does indeed require a permission should be given a permission or it will be ignored.
     */
    boolean requirePermission() default false;

    /**
     * If the command listeners should be called on a separate thread by Quar or not.
     */
    boolean async() default false;

    /**
     * Make the command usable only by a player or players and console.
     */
    boolean requirePlayer();
}
