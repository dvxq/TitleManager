package ru.healthanmary.titlemanager.commands.menus;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import ru.healthanmary.titlemanager.TitleManager;
import ru.healthanmary.titlemanager.menus.availableTitles.AvailableTitlesMenuBuilder;

public class OpenAvailableTitlesMenuCmd implements CommandExecutor {
    private final AvailableTitlesMenuBuilder menuBuilder;
    private final TitleManager titleManager;
    public OpenAvailableTitlesMenuCmd(AvailableTitlesMenuBuilder menuBuilder, TitleManager titleManager) {
        this.menuBuilder = menuBuilder;
        this.titleManager = titleManager;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Только для игроков!");
            return true;
        }

        Player player = (Player) sender;

        Bukkit.getScheduler().runTaskAsynchronously(titleManager, () -> {
            Inventory availableTitlesMenu = menuBuilder.getAvailableTitlesMenu(player.getName(), 1);

            Bukkit.getScheduler().runTask(titleManager, () -> {
                player.openInventory(availableTitlesMenu);
            });
        });
        return true;
    }
}
