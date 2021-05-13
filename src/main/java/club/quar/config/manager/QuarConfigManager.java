package club.quar.config.manager;

import club.quar.config.QuarConfig;
import club.quar.config.annotation.ConfigField;
import club.quar.manager.Manager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.lang.reflect.Field;

public class QuarConfigManager extends Manager<QuarConfig> {

    public void registerConfig(File file, QuarConfig quarConfig) {
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        registerConfig(configuration, quarConfig);
    }

    public void registerConfig(FileConfiguration configuration, QuarConfig quarConfig) {
        Class<?> clazz = quarConfig.getClass();
        for(Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(ConfigField.class)) {
                continue;
            }

            if (!field.isAccessible()) {
                try {
                    field.setAccessible(true);
                } catch (Exception exc) {
                    exc.printStackTrace();
                    continue;
                }
            }
            ConfigField configField = field.getDeclaredAnnotation(ConfigField.class);
            String path = configField.path();
            try {
                field.set(quarConfig, configuration.get(path));
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }
}
