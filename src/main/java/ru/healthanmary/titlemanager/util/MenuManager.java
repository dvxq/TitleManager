package ru.healthanmary.titlemanager.util;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import ru.healthanmary.titlemanager.invHolders.AvailableTitlesMenuHolder;
import ru.healthanmary.titlemanager.invHolders.CustomMenuHolder;
import ru.healthanmary.titlemanager.invHolders.TitleMenuHolder;

public class MenuManager {
    public static boolean isTitleMenu(Inventory inventory) {
        return inventory.getHolder() instanceof TitleMenuHolder;
    }
}
