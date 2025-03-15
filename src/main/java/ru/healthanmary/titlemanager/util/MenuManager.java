package ru.healthanmary.titlemanager.util;

import org.bukkit.inventory.Inventory;

public class MenuManager {
    public boolean isTitleMenu(Inventory inventory) {
        return inventory.getHolder() instanceof CustomMenuHolder;
    }
}
