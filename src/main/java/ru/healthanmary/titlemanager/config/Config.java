package ru.healthanmary.titlemanager.config;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import ru.healthanmary.titlemanager.TitleManager;

public class Config {
    public static void loadConfig(FileConfiguration file) {
        ConfigurationSection section = file.getConfigurationSection("mysql");
        if (section == null) {
            // it doesn't work
            section = file.createSection("mysql");
            section.set("mysql.host", "localhost");
            section.set("mysql.port", 3306);
            section.set("mysql.database_name", "titles");
            section.set("mysql.username", "root");
            section.set("mysql.password", "password");
            System.out.println(ChatColor.RED + "Не удалось найти секцию 'mysql' в конфиге, поэтому " +
                    "она была создана плагином со значениями по умолчанию");
        }
        parseSection(section);
    }
    public static void parseSection(ConfigurationSection section) {
        MySqlData.USERNAME = section.getString("username");
        MySqlData.PASSWORD = section.getString("password");
        MySqlData.HOST = section.getString("host");
        MySqlData.PORT = section.getInt("port");
        MySqlData.DB_NAME = section.getString("database_name");
    }
    public static class MySqlData{
        public static String USERNAME;
        public static String PASSWORD;
        public static String HOST;
        public static int PORT;
        public static String DB_NAME;
        private MySqlData() {}
    }
}
