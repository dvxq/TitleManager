package ru.healthanmary.titlemanager.util;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class AvailibleTitlesMenuHolder implements InventoryHolder {
    private Inventory inventory;

    public AvailibleTitlesMenuHolder(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
