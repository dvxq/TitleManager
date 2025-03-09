package ru.healthanmary.titlemanager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.healthanmary.titlemanager.commands.OpenTitleCreationMenuCommand;
import ru.healthanmary.titlemanager.commands.fcmd2;
import ru.healthanmary.titlemanager.commands.fghfcommand;
import ru.healthanmary.titlemanager.config.Config;
import ru.healthanmary.titlemanager.listeners.ClickListener;
import ru.healthanmary.titlemanager.listeners.PlayerJoinListener;
import ru.healthanmary.titlemanager.mysql.MysqlStorage;
import ru.healthanmary.titlemanager.mysql.Storage;
import ru.healthanmary.titlemanager.util.CustomTitleTabPlaceholder;

public final class TitleManager extends JavaPlugin {
    public static TitleManager instance;
    private Storage storage;
    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        Config.loadConfig(getConfig());

        storage = new MysqlStorage();
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new CustomTitleTabPlaceholder().register();
        }
        getCommand("fcmd").setExecutor(new fghfcommand(storage));
        getCommand("fcmd2").setExecutor(new fcmd2(storage));
        getCommand("customtitle").setExecutor(new OpenTitleCreationMenuCommand());
        getServer().getPluginManager().registerEvents(new ClickListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(storage), this);
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}
