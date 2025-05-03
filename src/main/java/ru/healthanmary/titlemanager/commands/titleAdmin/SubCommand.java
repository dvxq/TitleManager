package ru.healthanmary.titlemanager.commands.titleAdmin;

import org.bukkit.command.CommandSender;

public interface SubCommand {
    void execute(CommandSender sender, String[] args);
}
