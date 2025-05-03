package ru.healthanmary.titlemanager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ru.healthanmary.titlemanager.cache.RejectCacheManager;
import ru.healthanmary.titlemanager.cache.TitleCacheManager;
import ru.healthanmary.titlemanager.commands.menus.OpenAvailableTitlesMenuCmd;
import ru.healthanmary.titlemanager.commands.menus.OpenMainTitleMenuCmd;
import ru.healthanmary.titlemanager.commands.titleAdmin.TitleAdminCommand;
import ru.healthanmary.titlemanager.commands.titleAdmin.TitleAdminTabCompleter;
import ru.healthanmary.titlemanager.config.MainConfigParser;
import ru.healthanmary.titlemanager.config.MysqlConfigParser;
import ru.healthanmary.titlemanager.menuServices.peek.PeekMenuBuilder;
import ru.healthanmary.titlemanager.menuServices.peek.PeekMenuListener;
import ru.healthanmary.titlemanager.menuServices.review.ReviewMenuBuilder;
import ru.healthanmary.titlemanager.menuServices.review.ReviewMenuListener;
import ru.healthanmary.titlemanager.menuServices.titleConfirmation.ConfirmationManager;
import ru.healthanmary.titlemanager.util.MainClickListener;
import ru.healthanmary.titlemanager.menuServices.availableTitles.AvailableTitlesMenuListener;
import ru.healthanmary.titlemanager.menuServices.main.MainMenuListener;
import ru.healthanmary.titlemanager.mysql.MysqlStorage;
import ru.healthanmary.titlemanager.mysql.Storage;
import ru.healthanmary.titlemanager.placeholder.MainPlaceholder;
import ru.healthanmary.titlemanager.menuServices.availableTitles.AvailableTitlesMenuBuilder;
import ru.healthanmary.titlemanager.menuServices.main.MainTitleMenuBuilder;
import ru.healthanmary.titlemanager.menuServices.titleConfirmation.TitleConfirmationMenuBuilder;
import ru.healthanmary.titlemanager.util.CreatingMenuService;
import ru.healthanmary.titlemanager.menuServices.MenuManager;

public final class TitleManager extends JavaPlugin {
    public static TitleManager instance;
    private Storage storage;
    private TitleCacheManager cacheManager;
    private MenuManager menuManager;
    private MainTitleMenuBuilder titleCreationMenuBuilder;
    private AvailableTitlesMenuBuilder availableTitlesMenuBuilder;
    private CreatingMenuService creatingMenuService;
    private TitleConfirmationMenuBuilder titleConfirmationMenuBuilder;
    private MysqlConfigParser mysqlConfigParser;
    private MainConfigParser mainConfigParser;
    private PeekMenuBuilder peekMenuBuilder;
    private ReviewMenuBuilder reviewMenuBuilder;
    private RejectCacheManager rejectCacheManager;
    @Override
    public void onEnable() {
        instance = this;
        ensurePluginsExistence();
        mysqlConfigParser = new MysqlConfigParser(this, "mysql.yml");
        mysqlConfigParser.loadConfig();
        mainConfigParser = new MainConfigParser(this, "config.yml");
        mainConfigParser.loadConfig();

        storage = new MysqlStorage(mysqlConfigParser);
        peekMenuBuilder = new PeekMenuBuilder(storage);
        cacheManager = new TitleCacheManager(storage);
        titleCreationMenuBuilder = new MainTitleMenuBuilder(storage);
        availableTitlesMenuBuilder = new AvailableTitlesMenuBuilder(storage);
        reviewMenuBuilder = new ReviewMenuBuilder(storage);
        rejectCacheManager = new RejectCacheManager(storage);
        titleConfirmationMenuBuilder = new TitleConfirmationMenuBuilder(mainConfigParser);
        creatingMenuService = new CreatingMenuService(titleConfirmationMenuBuilder);
        menuManager = new MenuManager();

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new MainPlaceholder(cacheManager).register();
        }

        // register commands
        getCommand("customtitle").setExecutor(new OpenMainTitleMenuCmd(titleCreationMenuBuilder));
        getCommand("availabletitles").setExecutor(new OpenAvailableTitlesMenuCmd(availableTitlesMenuBuilder));
        getCommand("titleadmin").setExecutor(new TitleAdminCommand(storage, peekMenuBuilder, reviewMenuBuilder));
        getCommand("titleadmin").setTabCompleter(new TitleAdminTabCompleter());

        // register listeners
        getServer().getPluginManager().registerEvents(new MainClickListener(menuManager), this);
        getServer().getPluginManager().registerEvents(new AvailableTitlesMenuListener(availableTitlesMenuBuilder, cacheManager, storage), this);
        getServer().getPluginManager().registerEvents(new MainMenuListener(storage, creatingMenuService), this);
        getServer().getPluginManager().registerEvents(creatingMenuService, this);
        getServer().getPluginManager().registerEvents(new PeekMenuListener(peekMenuBuilder), this);
        getServer().getPluginManager().registerEvents(new ConfirmationManager(creatingMenuService, storage), this);
        getServer().getPluginManager().registerEvents(new ReviewMenuListener(storage, reviewMenuBuilder, rejectCacheManager), this);
        getServer().getPluginManager().registerEvents(rejectCacheManager, this);
        getServer().getPluginManager().registerEvents(cacheManager, this);

        // set the titles
        for (Player player : Bukkit.getOnlinePlayers()) {
            cacheManager.processPlayer(player);
        }
    }
    private void ensurePluginsExistence() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        Plugin phi = pluginManager.getPlugin("PlaceHolderAPI");
        if (phi == null || !phi.isEnabled()) {
            getLogger().warning("One of the necessary plugins hasn't detected, TitleManager disabled");
            pluginManager.disablePlugin(this);
        }
    }
    @Override
    public void onDisable() {
        instance = null;
    }
}
