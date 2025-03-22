package ru.healthanmary.titlemanager.util;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class MenuManager {
    public boolean isTitleMenu(Inventory inventory) {
        InventoryHolder holder = inventory.getHolder();
        return holder instanceof CustomMenuHolder
                || holder instanceof AvailableTitlesMenuHolder;
    }
}
