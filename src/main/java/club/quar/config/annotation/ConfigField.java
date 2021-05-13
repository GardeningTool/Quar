package club.quar.config.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigField {

    /**
     * The path that the given field will be stored on the configuration file.
     */
    String path();

}
