package ru.healthanmary.titlemanager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.healthanmary.titlemanager.cache.PlayerTitleCache;
import ru.healthanmary.titlemanager.commands.OpenTitleCreationMenuCommand;
import ru.healthanmary.titlemanager.commands.t1cmd;
import ru.healthanmary.titlemanager.commands.t2cmd;
import ru.healthanmary.titlemanager.config.Config;
import ru.healthanmary.titlemanager.listeners.Listener;
import ru.healthanmary.titlemanager.mysql.MysqlStorage;
import ru.healthanmary.titlemanager.mysql.Storage;
import ru.healthanmary.titlemanager.ui.TitleCreationMenuBuilder;
import ru.healthanmary.titlemanager.placeholder.TitleBoardPlaceholder;
import ru.healthanmary.titlemanager.util.MenuManager;
import ru.healthanmary.titlemanager.util.Title;

public final class TitleManager extends JavaPlugin {
    public static TitleManager instance;
    private Storage storage;
    private PlayerTitleCache playerCache;
    private MenuManager menuManager;
    private TitleCreationMenuBuilder titleCreationMenuBuilder;
    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        Config.loadConfig(getConfig());

        storage = new MysqlStorage();
        playerCache = new PlayerTitleCache();
        menuManager = new MenuManager();
        titleCreationMenuBuilder = new TitleCreationMenuBuilder(storage);

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new TitleBoardPlaceholder(playerCache).register();
        }
        getCommand("t1cmd").setExecutor(new t1cmd(storage));
        getCommand("t2cmd").setExecutor(new t2cmd(playerCache));
        getCommand("customtitle").setExecutor(new OpenTitleCreationMenuCommand(titleCreationMenuBuilder));
        getServer().getPluginManager().registerEvents(new Listener(storage, playerCache, menuManager), this);

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
