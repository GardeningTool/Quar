package club.quar.config.manager;

import club.quar.config.QuarConfig;
import club.quar.config.annotation.ConfigField;
import club.quar.manager.Manager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.lang.reflect.Field;

public class QuarConfigManager extends Manager<QuarConfig> {

    /**
     * Register the configuration file given by the user.
     *
     * @param file The file that should be stored in.
     * @param quarConfig The QuarConfig instance for our config.
     */
    public void registerConfig(File file, QuarConfig quarConfig) {
        // Our YAML configuration file.
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        // Registering our YAML configuration file alongside our Quar Config instance.
        registerConfig(configuration, quarConfig);
    }

    /**
     * Register the configuration file given by the user.
     *
     * @param configuration The file configuration that should be stored in.
     * @param quarConfig The QuarConfig instance for our config.
     */
    public void registerConfig(FileConfiguration configuration, QuarConfig quarConfig) {
        // Get the class of our QuarConfig class.
        Class<?> clazz = quarConfig.getClass();

        // Loop thru all of the fields declared.
        for (Field field : clazz.getDeclaredFields()) {
            // If the field doesn't have our annotation skip it
            if (!field.isAnnotationPresent(ConfigField.class)) {
                continue;
            }

            // Make the field accessible.
            if (!field.isAccessible()) {
                try {
                    field.setAccessible(true);
                } catch (Exception exc) {
                    exc.printStackTrace();
                    continue;
                }
            }

            // Get the field and get its path.
            ConfigField configField = field.getDeclaredAnnotation(ConfigField.class);
            String path = configField.path();

            // Save the fields value on the config file.
            try {
                field.set(quarConfig, configuration.get(path));
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }
}
