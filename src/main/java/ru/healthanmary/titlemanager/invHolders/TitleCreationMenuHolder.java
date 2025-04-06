package ru.healthanmary.titlemanager.invHolders;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class TitleCreationMenuHolder implements InventoryHolder, TitleMenuHolder {
    @Override
    public @NotNull Inventory getInventory() {
        return null;
    }
}
