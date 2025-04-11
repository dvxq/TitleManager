package ru.healthanmary.titlemanager.config;

import org.bukkit.configuration.ConfigurationSection;
import ru.healthanmary.titlemanager.TitleManager;

public class MysqlSectionParser implements SectionParser {
    @Override
    public void parse(ConfigurationSection section) {
        if (section != null) {
            Config.MysqlData.USERNAME = section.getString("username");
            Config.MysqlData.PASSWORD = section.getString("password");
            Config.MysqlData.HOST = section.getString("host");
            Config.MysqlData.PORT = section.getInt("port");
            Config.MysqlData.DB_NAME = section.getString("database_name");
        } else {
            TitleManager.instance.getLogger().warning("Mysql configuration section is null so values were set by default");
        }
    }
}
