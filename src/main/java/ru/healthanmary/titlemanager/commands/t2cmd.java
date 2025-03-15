package ru.healthanmary.titlemanager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.healthanmary.titlemanager.cache.PlayerTitleCache;

public class t2cmd implements CommandExecutor {
    private PlayerTitleCache playerTitleCache;

    public t2cmd(PlayerTitleCache playerTitleCache) {
        this.playerTitleCache = playerTitleCache;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        String title = playerTitleCache.getTitle(player.getUniqueId()).getTitle_text();
        if (title == null || title.isBlank()) player.sendMessage("is blank");
        else player.sendMessage("not blank - " + title);
        return true;
    }
}
