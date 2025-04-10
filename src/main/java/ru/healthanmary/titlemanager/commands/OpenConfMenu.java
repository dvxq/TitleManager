package ru.healthanmary.titlemanager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.healthanmary.titlemanager.ui.TitleConfirmationMenuBuilder;

public class OpenConfMenu implements CommandExecutor {
    private final TitleConfirmationMenuBuilder titleConfirmationMenuBuilder;

    public OpenConfMenu(TitleConfirmationMenuBuilder titleConfirmationMenuBuilder) {
        this.titleConfirmationMenuBuilder = titleConfirmationMenuBuilder;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        player.openInventory(titleConfirmationMenuBuilder.getConfirmationMenu());
        return true;
    }
}
