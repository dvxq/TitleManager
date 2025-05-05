package ru.healthanmary.titlemanager.menus.main;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import ru.healthanmary.titlemanager.menus.TitleMenuHolder;

public class MainMenuHolder implements InventoryHolder, TitleMenuHolder {

    @Override
    public @NotNull Inventory getInventory() {
        return null;
    }
}
