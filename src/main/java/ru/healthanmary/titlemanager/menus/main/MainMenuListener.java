package ru.healthanmary.titlemanager.menus.main;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import ru.healthanmary.titlemanager.TitleManager;
import ru.healthanmary.titlemanager.mysql.Storage;
import ru.healthanmary.titlemanager.util.CreatingMenuService;

public class MainMenuListener implements Listener {
    private final Storage storage;
    private final CreatingMenuService creatingMenuService;

    public MainMenuListener(Storage storage, CreatingMenuService creatingMenuService) {
        this.storage = storage;
        this.creatingMenuService = creatingMenuService;
    }

    // main menu functionality
    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        Inventory clickedInventory = e.getClickedInventory();
        if (clickedInventory == null || !(clickedInventory.getHolder() instanceof MainMenuHolder) && clickedInventory.getType() == InventoryType.CHEST) {
            return;
        }
        Player player = (Player) e.getWhoClicked();
        switch (e.getSlot()) {
            case 24: {
                player.performCommand("availabletitles");
                break;
            }
            case 20: {
                Bukkit.getScheduler().runTaskAsynchronously(TitleManager.instance, () -> {
                    Integer playerPoints = Integer.valueOf(storage.getPlayerPoints(player.getName()));
                    if (playerPoints > 0) {
                        Bukkit.getScheduler().runTask(TitleManager.instance, () -> {
                            player.closeInventory();
                            creatingMenuService.addPendingPlayer(player);
                        });
                    } else {
                        Bukkit.getScheduler().runTask(TitleManager.instance, () -> {
                            player.closeInventory();
                            player.sendMessage(ChatColor.RED + "▶ " + ChatColor.WHITE + "У вас недостаточно поинтов");
                        });
                    }
                });
                break;
            }
        }
    }
}
