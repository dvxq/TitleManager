package ru.healthanmary.titlemanager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.healthanmary.titlemanager.mysql.Storage;

public class fcmd2 implements CommandExecutor {
    private Storage storage;

    public fcmd2(Storage storage) {
        this.storage = storage;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage(String.valueOf(storage.getTitleByName(args[0]).is_accepted()));
        return true;
    }
}
