package ru.healthanmary.titlemanager;

import org.bukkit.plugin.java.JavaPlugin;
import ru.healthanmary.titlemanager.commands.OpenTitleCreationMenuCommand;
import ru.healthanmary.titlemanager.listeners.ClickListener;
import ru.healthanmary.titlemanager.ui.TitleCreationMenuBuilder;

public final class TitleManager extends JavaPlugin {
    public static TitleManager instance;
    private TitleCreationMenuBuilder titleCreationMenuBuilder;
    @Override
    public void onEnable() {
        instance = this;
        titleCreationMenuBuilder = new TitleCreationMenuBuilder();
        getCommand("customtitle").setExecutor(new OpenTitleCreationMenuCommand());
        getServer().getPluginManager().registerEvents(new ClickListener(), this);
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}
