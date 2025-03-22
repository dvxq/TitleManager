package ru.healthanmary.titlemanager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.healthanmary.titlemanager.cache.PlayerTitleCache;

public class SwitchDebugCmd implements CommandExecutor {
    private PlayerTitleCache playerTitleCache;

    public SwitchDebugCmd(PlayerTitleCache playerTitleCache) {
        this.playerTitleCache = playerTitleCache;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        playerTitleCache.switchDebugMode();
        sender.sendMessage("Debug mode was switched on to " + playerTitleCache.isDebugEnabled());
        return true;
    }
}
