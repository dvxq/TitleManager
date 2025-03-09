package ru.healthanmary.titlemanager.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;
import ru.healthanmary.titlemanager.TitleManager;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        TitleManager instance = TitleManager.instance;
        Bukkit.getScheduler().runTaskLater(instance, () -> {
            PluginManager pluginManager = instance.getServer().getPluginManager();
            pluginManager.disablePlugin(instance);
            pluginManager.enablePlugin(instance);
        }, 1L);
        return true;
    }
}
