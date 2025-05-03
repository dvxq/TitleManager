package ru.healthanmary.titlemanager.config;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Getter
public abstract class AbstractConfig {
    private final JavaPlugin plugin;
    private final String fileName;

    private File file;
    private FileConfiguration config;

    protected AbstractConfig(JavaPlugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        this.file = new File(plugin.getDataFolder(), fileName);
    }
    abstract void parse();

    public final void loadConfig() {
        if (!file.exists()) {
            reloadConfig();
        }

        config = YamlConfiguration.loadConfiguration(file);
        setDefaults();
        parse();
    }

    protected void setDefaults() {
        if (config == null) {
            throw new IllegalStateException("Failed to set defaults cuz config is null");
        }
        try (InputStream is = plugin.getResource(fileName);
              Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {

            FileConfiguration defaultConfig = YamlConfiguration.loadConfiguration(reader);
            config.setDefaults(defaultConfig);
            config.options().copyDefaults(true);
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    protected void reloadConfig() {
        if (file == null) {
            file = new File(plugin.getDataFolder()+"/"+fileName);
        }
        loadConfig();
    }
}
