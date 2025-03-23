package ru.healthanmary.titlemanager.util;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;

public class AvailableTitlesMenuHolder implements InventoryHolder {
    private Inventory inventory;
    @Getter @Setter
    private int currentPage;
    @Getter @Setter
    private int maxPage;

    public AvailableTitlesMenuHolder(Inventory inventory, int currentPage) {
        this.inventory = inventory;
        this.currentPage = currentPage;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
