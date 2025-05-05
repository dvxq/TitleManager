package ru.healthanmary.titlemanager.menus;

import org.bukkit.inventory.Inventory;

public class MenuManager {
    public static boolean isTitleMenu(Inventory inventory) {
        return inventory.getHolder() instanceof TitleMenuHolder;
    }
}
