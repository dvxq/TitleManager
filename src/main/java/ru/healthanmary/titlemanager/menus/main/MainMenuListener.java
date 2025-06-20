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
import ru.healthanmary.titlemanager.util.CreationMenuManager;

public class MainMenuListener implements Listener {
    private final Storage storage;
    private final CreationMenuManager creationMenuManager;
    private final TitleManager titleManager;

    public MainMenuListener(Storage storage, CreationMenuManager creationMenuManager, TitleManager titleManager) {
        this.storage = storage;
        this.creationMenuManager = creationMenuManager;
        this.titleManager = titleManager;
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
                Bukkit.getScheduler().runTaskAsynchronously(titleManager, () -> {
                    Integer playerPoints = Integer.valueOf(storage.getPlayerPoints(player.getName()));
                    if (playerPoints > 0) {
                        Bukkit.getScheduler().runTask(titleManager, () -> {
                            player.closeInventory();
                            creationMenuManager.addPendingPlayer(player);
                        });
                    } else {
                        Bukkit.getScheduler().runTask(titleManager, () -> {
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
