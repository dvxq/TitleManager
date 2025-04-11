package ru.healthanmary.titlemanager.config;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.List;

public class Config {
    private static ItemsSectionParser itemsSectionParser = new ItemsSectionParser();
    private static MysqlSectionParser mysqlSectionParser = new MysqlSectionParser();

    public static void loadConfig(FileConfiguration file) {
        List<String> sectionNames = Arrays.asList("mysql", "items");
        sectionNames.forEach(sectionName -> );
    }
    public static class ItemsData {
        public static List<String> limeWoolLore;
        public static List<String> redWoolLore;
        private ItemsData() {}
    }
    public static class MysqlData {
        public static String USERNAME;
        public static String PASSWORD;
        public static String HOST;
        public static int PORT;
        public static String DB_NAME;
        private MysqlData() {}
    }
}
