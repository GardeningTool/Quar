package club.quar.config.manager;

import club.quar.config.QuarConfig;
import club.quar.config.annotation.ConfigField;
import club.quar.manager.Manager;
import club.quar.plugin.QuarPlugin;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

@RequiredArgsConstructor
public class QuarConfigManager extends Manager<QuarConfig> {
    /**
     * Lets require the parent plugin. This is only required for saving.
     */
    private final QuarPlugin quarPlugin;

    public void registerConfig(String fileName, QuarConfig quarConfig) {
        registerConfig(new File(quarPlugin.getDataFolder(), fileName), quarConfig);
    }

    public void registerConfig(File filePath, QuarConfig quarConfig) {
        if (!filePath.exists()) {
            quarPlugin.getLogger().info(String.format("Creating configuration folder for %s", filePath.getName()));

            if (!filePath.getParentFile().mkdirs()) {
                quarPlugin.getLogger().severe(String.format("Could not create configuration folder for %s", filePath.getName()));
            }

            try {
                if (!filePath.createNewFile()) {
                    quarPlugin.getLogger().severe(String.format("Could not create configuration file %s", filePath.getName()));
                }
            } catch (IOException e) {
                quarPlugin.getLogger().severe("An exception occurred while trying to create configuration file.");
                e.printStackTrace();
            }
        }

        FileConfiguration configuration = YamlConfiguration.loadConfiguration(filePath);

        Class<?> clazz = quarConfig.getClass();

        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(ConfigField.class))
                .forEach(field -> {
                    if (!field.isAccessible()) {
                        try {
                            field.setAccessible(true);
                        } catch (Exception e) {
                            quarPlugin.getLogger().severe("Failed to change field accessibility.");
                            e.printStackTrace();
                        }
                    }

                    // Get the field and get its path.
                    ConfigField configField = field.getDeclaredAnnotation(ConfigField.class);
                    String path = configField.path();

                    try {
                        // Check if the configuration contains the path.
                        if (!configuration.isSet(path) && field.get(quarConfig) != null) {
                            configuration.set(path, field.get(quarConfig));
                        } else {
                            Class<?> type = field.getType();

                            // Save the fields value on the config file.
                            if (type.equals(int.class)) {
                                field.set(quarConfig, configuration.getInt(path));
                            } else if (type.equals(double.class)) {
                                field.set(quarConfig, configuration.getDouble(path));
                            } else if (type.equals(boolean.class)) {
                                field.set(quarConfig, configuration.getBoolean(path));
                            } else if (type.equals(long.class)) {
                                field.set(quarConfig, configuration.getLong(path));
                            } else if (type.isInstance(Collection.class)) {
                                field.set(quarConfig, configuration.getList(path));
                            } else {
                                field.set(quarConfig, configuration.get(path));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        try {
            configuration.save(filePath);
        } catch (IOException e) {
            quarPlugin.getLogger().severe("Failed to save configuration file.");
        }
    }

    /**
     * Register the configuration file given by the user.
     *
     * @param file The file that should be stored in.
     * @param quarConfig The QuarConfig instance for our config.
     */
//    @Deprecated
//    public void registerConfig(File file, QuarConfig quarConfig) {
//         Our YAML configuration file.
//        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
//
//         Registering our YAML configuration file alongside our Quar Config instance.
//        registerConfig(configuration, quarConfig);
//    }

    /**
     * Register the configuration file given by the user.
     *
     * @param configuration The file configuration that should be stored in.
     * @param quarConfig The QuarConfig instance for our config.
     */
    @Deprecated
    public void registerConfig(FileConfiguration configuration, QuarConfig quarConfig) {
        // Get the class of our QuarConfig class.
        Class<?> clazz = quarConfig.getClass();

        // Loop through all the fields declared.
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

            try {
                // Check if the configuration contains the path.
                if (!configuration.contains(path) && field.get(quarConfig) != null) {
                    configuration.set(path, field.get(quarConfig));

                } else {
                    Class<?> type = field.getType();

                    // Save the fields value on the config file.
                    if (type.equals(int.class)) {
                        field.set(quarConfig, configuration.getInt(path));
                    } else if (type.equals(double.class)) {
                        field.set(quarConfig, configuration.getDouble(path));
                    } else if (type.equals(boolean.class)) {
                        field.set(quarConfig, configuration.getBoolean(path));
                    } else if (type.equals(long.class)) {
                        field.set(quarConfig, configuration.getLong(path));
                    } else if (type.isInstance(Collection.class)) {
                        field.set(quarConfig, configuration.getList(path));
                    } else {
                        field.set(quarConfig, configuration.get(path));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
