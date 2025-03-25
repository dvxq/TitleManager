package ru.healthanmary.titlemanager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.healthanmary.titlemanager.cache.PlayerTitleCache;
import ru.healthanmary.titlemanager.commands.*;
import ru.healthanmary.titlemanager.config.Config;
import ru.healthanmary.titlemanager.listener.Listener;
import ru.healthanmary.titlemanager.mysql.MysqlStorage;
import ru.healthanmary.titlemanager.mysql.Storage;
import ru.healthanmary.titlemanager.placeholder.TitleMainPlaceholder;
import ru.healthanmary.titlemanager.ui.AvailableTitlesMenuBuilder;
import ru.healthanmary.titlemanager.ui.MainTitleMenuBuilder;
import ru.healthanmary.titlemanager.placeholder.TitleBoardPlaceholder;
import ru.healthanmary.titlemanager.util.MenuManager;
import ru.healthanmary.titlemanager.util.Title;

public final class TitleManager extends JavaPlugin {
    public static TitleManager instance;
    private Storage storage;
    private PlayerTitleCache playerCache;
    private MenuManager menuManager;
    private MainTitleMenuBuilder titleCreationMenuBuilder;
    private AvailableTitlesMenuBuilder availableTitlesMenuBuilder;
    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        Config.loadConfig(getConfig());

        storage = new MysqlStorage();
        playerCache = new PlayerTitleCache();
        menuManager = new MenuManager();
        titleCreationMenuBuilder = new MainTitleMenuBuilder(storage);
        availableTitlesMenuBuilder = new AvailableTitlesMenuBuilder(storage);

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new TitleBoardPlaceholder(playerCache).register();
            new TitleMainPlaceholder(playerCache).register();
        }

        getCommand("customtitle").setExecutor(new OpenTitleCreationMenuCmd(titleCreationMenuBuilder));
        getCommand("availabletitles").setExecutor(new OpenAvailableTitlesMenuCmd(availableTitlesMenuBuilder));
        getServer().getPluginManager().registerEvents(new Listener(storage, playerCache, menuManager, availableTitlesMenuBuilder), this);

        for (Player player : Bukkit.getOnlinePlayers()) {
            Title title = storage.getCurrentTitleByName(player.getName());
            playerCache.putTitle(player.getUniqueId(), title);
        }
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}
