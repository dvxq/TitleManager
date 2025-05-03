package ru.healthanmary.titlemanager.menuServices;

import org.bukkit.inventory.Inventory;

public class MenuManager {
    public static boolean isTitleMenu(Inventory inventory) {
        return inventory.getHolder() instanceof TitleMenuHolder;
    }
}
