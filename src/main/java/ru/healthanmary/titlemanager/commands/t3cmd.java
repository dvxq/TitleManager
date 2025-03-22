package ru.healthanmary.titlemanager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.healthanmary.titlemanager.mysql.MysqlStorage;
import ru.healthanmary.titlemanager.mysql.Storage;
import ru.healthanmary.titlemanager.ui.AvailableTitlesMenuBuilder;

public class t3cmd implements CommandExecutor {
    private AvailableTitlesMenuBuilder availableTitlesMenuBuilder;
    private Storage storage;

    public t3cmd(AvailableTitlesMenuBuilder availableTitlesMenuBuilder, Storage storage) {
        this.availableTitlesMenuBuilder = availableTitlesMenuBuilder;
        this.storage = storage;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        player.getInventory().addItem(availableTitlesMenuBuilder.getTitleNametag(storage.getCurrentTitleByName(player.getName())));
        return true;
    }
}
