package ru.healthanmary.titlemanager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.healthanmary.titlemanager.ui.AvailableTitlesMenuBuilder;

public class ShowTitlePageCmd implements CommandExecutor {
    private AvailableTitlesMenuBuilder availableTitlesMenuBuilder;

    public ShowTitlePageCmd(AvailableTitlesMenuBuilder availableTitlesMenuBuilder) {
        this.availableTitlesMenuBuilder = availableTitlesMenuBuilder;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        player.openInventory(availableTitlesMenuBuilder.getAvailableTitlesMenu(player.getName(),
                Integer.parseInt((args[0]))));
        return true;
    }
}
