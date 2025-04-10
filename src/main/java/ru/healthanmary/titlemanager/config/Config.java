package ru.healthanmary.titlemanager.config;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Config {
    public static void loadConfig(FileConfiguration file) {
        ConfigurationSection section = file.getConfigurationSection("mysql");
        if (section == null) {
            /*
             it doesn't work
             todo: recode and override for all the sections
            */
            section = file.createSection("mysql");
            section.set("mysql.host", "localhost");
            section.set("mysql.port", 3306);
            section.set("mysql.database_name", "titles");
            section.set("mysql.username", "root");
            section.set("mysql.password", "password");
            System.out.println(ChatColor.RED + "Конфиг не целен, поэтому значения были установлены по умолчанию");
        }
        parseMysqlSection(section);
    }
    public static void parseMysqlSection(ConfigurationSection section) {
        switch (section.getName()){
            case "mysql": {
                MysqlData.USERNAME = section.getString("username");
                MysqlData.PASSWORD = section.getString("password");
                MysqlData.HOST = section.getString("host");
                MysqlData.PORT = section.getInt("port");
                MysqlData.DB_NAME = section.getString("database_name");
            }
            case "items": {
                ItemsData.limeWoolLore = section.getStringList("lime-wool-lore");
                ItemsData.redWoolLore = section.getStringList("red-wool-lore");
            }
            default: // write smth
        }
    }

    public static class ItemsData {
        public static List<String> limeWoolLore;
        public static List<String> redWoolLore;
    }
    //
    public static class MysqlData {
        public static String USERNAME;
        public static String PASSWORD;
        public static String HOST;
        public static int PORT;
        public static String DB_NAME;
        private MysqlData() {}
    }
}
