package ru.healthanmary.titlemanager.invHolders;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class CustomMenuHolder implements InventoryHolder, TitleMenuHolder {
    private Inventory inventory;

    public CustomMenuHolder(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
