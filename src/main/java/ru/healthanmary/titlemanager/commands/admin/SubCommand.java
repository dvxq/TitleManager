package ru.healthanmary.titlemanager.commands.admin;

import org.bukkit.command.CommandSender;

public interface SubCommand {
    void execute(CommandSender sender, String[] args);
}
