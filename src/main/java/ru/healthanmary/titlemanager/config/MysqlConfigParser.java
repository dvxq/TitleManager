package ru.healthanmary.titlemanager.config;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class MysqlConfigParser extends AbstractConfig {

    public MysqlConfigParser(JavaPlugin plugin, String fileName) {
        super(plugin, fileName);
    }

    private String username;
    private String password;
    private String host;
    private String port;
    private String databaseName;

    @Override
    void parse() {
        ConfigurationSection section = getConfig().getConfigurationSection("mysql");
        if (section == null) {
            throw new IllegalStateException("Failed to get mysql section");
        }
        username = section.getString("username");
        password = section.getString("password");
        host = section.getString("host");
        port = section.getString("port");
        databaseName = section.getString("database_name");
    }
}