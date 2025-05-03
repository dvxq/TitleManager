package ru.healthanmary.titlemanager.commands.menus;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import ru.healthanmary.titlemanager.TitleManager;
import ru.healthanmary.titlemanager.menuServices.main.MainTitleMenuBuilder;

public class OpenMainTitleMenuCmd implements CommandExecutor {
    private final MainTitleMenuBuilder titleMenuBuilder;

    public OpenMainTitleMenuCmd(MainTitleMenuBuilder menuBuilder) {
        this.titleMenuBuilder = menuBuilder;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Только для игроков!");
            return true;
        }

        Player player = (Player) sender;
        Bukkit.getScheduler().runTaskAsynchronously(TitleManager.instance, () -> {
            Inventory titleCreationMenu = titleMenuBuilder.getTitleCreationMenu(player.getName());
            Bukkit.getScheduler().runTask(TitleManager.instance, () -> {
                player.openInventory(titleCreationMenu);
            });
        });
        return true;
    }
}
