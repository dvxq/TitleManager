package ru.healthanmary.titlemanager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.healthanmary.titlemanager.ui.AvailableTitlesMenuBuilder;

public class OpenAvailableTitlesMenuCmd implements CommandExecutor {
    private AvailableTitlesMenuBuilder menuBuilder;
    public OpenAvailableTitlesMenuCmd(AvailableTitlesMenuBuilder menuBuilder) {
        this.menuBuilder = menuBuilder;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Только для игроков!");
            return true;
        }

        Player player = (Player) sender;
        player.openInventory(menuBuilder.getAvailableTitlesMenu(player.getName(), 1));
        return false;
    }
}
