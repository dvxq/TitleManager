package ru.healthanmary.titlemanager.commands.menus;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import ru.healthanmary.titlemanager.TitleManager;
import ru.healthanmary.titlemanager.menus.main.MainTitleMenuBuilder;

public class OpenMainTitleMenuCmd implements CommandExecutor {
    private final MainTitleMenuBuilder titleMenuBuilder;
    private final TitleManager titleManager;

    public OpenMainTitleMenuCmd(MainTitleMenuBuilder menuBuilder, TitleManager titleManager) {
        this.titleMenuBuilder = menuBuilder;
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
            Inventory titleCreationMenu = titleMenuBuilder.getTitleCreationMenu(player.getName());
            Bukkit.getScheduler().runTask(titleManager, () -> {
                player.openInventory(titleCreationMenu);
            });
        });
        return true;
    }
}
