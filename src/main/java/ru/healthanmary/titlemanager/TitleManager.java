package ru.healthanmary.titlemanager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.healthanmary.titlemanager.commands.OpenTitleCreationMenuCommand;
import ru.healthanmary.titlemanager.listeners.ClickListener;
import ru.healthanmary.titlemanager.ui.TitleCreationMenuBuilder;
import ru.healthanmary.titlemanager.util.CustomTitlePlaceholder;

public final class TitleManager extends JavaPlugin {
    public static TitleManager instance;
    private TitleCreationMenuBuilder titleCreationMenuBuilder;
    @Override
    public void onEnable() {
        instance = this;
        titleCreationMenuBuilder = new TitleCreationMenuBuilder();
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"))
            new CustomTitlePlaceholder().register();
        getCommand("customtitle").setExecutor(new OpenTitleCreationMenuCommand());
        getServer().getPluginManager().registerEvents(new ClickListener(), this);
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}
