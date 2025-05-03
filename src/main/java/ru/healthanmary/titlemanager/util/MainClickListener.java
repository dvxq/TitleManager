package ru.healthanmary.titlemanager.util;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import ru.healthanmary.titlemanager.menuServices.MenuManager;

public class MainClickListener implements org.bukkit.event.Listener {
    private final MenuManager menuManager;;

    public MainClickListener(MenuManager menuManager) {

        this.menuManager = menuManager;
    }

    // prohibits all clicks
    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        if (menuManager.isTitleMenu(e.getInventory())) e.setCancelled(true);
    }

    @EventHandler
    public void onInventoryDragEvent(InventoryDragEvent e) {
        if (menuManager.isTitleMenu(e.getInventory())) {
            e.setCancelled(true);
        }
    }
}
