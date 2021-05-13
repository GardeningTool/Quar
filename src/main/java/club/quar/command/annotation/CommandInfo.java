package club.quar.command.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInfo {

    String name();

    String requiredPermission();

    boolean requirePermission();

    boolean async();

    boolean requirePlayer();

}
