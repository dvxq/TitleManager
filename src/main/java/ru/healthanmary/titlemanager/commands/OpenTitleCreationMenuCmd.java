package ru.healthanmary.titlemanager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.healthanmary.titlemanager.ui.MainTitleMenuBuilder;

public class OpenTitleCreationMenuCmd implements CommandExecutor {
    private MainTitleMenuBuilder menuBuilder;

    public OpenTitleCreationMenuCmd(MainTitleMenuBuilder menuBuilder) {
        this.menuBuilder = menuBuilder;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Только для игроков!");
            return true;
        }

        Player player = (Player) sender;
        player.openInventory(menuBuilder.getTitleCreationMenu(player.getName()));
        return true;
    }
}
