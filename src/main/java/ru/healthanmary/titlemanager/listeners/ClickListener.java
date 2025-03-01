package ru.healthanmary.titlemanager.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import ru.healthanmary.titlemanager.ui.TitleCreationMenuBuilder;

public class ClickListener implements Listener {
    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        if (TitleCreationMenuBuilder.menu.equals(e.getInventory())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryDragEvent(InventoryDragEvent e) {
        if (TitleCreationMenuBuilder.menu.equals(e.getInventory())) {
            e.setCancelled(true);
        }
    }
}
