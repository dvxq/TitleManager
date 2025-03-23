package ru.healthanmary.titlemanager.util;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class AvailableTitlesMenuHolder implements InventoryHolder {
    private Inventory inventory;
    @Getter @Setter
    private int currentPage;
    @Getter @Setter
    private int maxPage;
    @Getter @Setter
    private int minPage;

    public AvailableTitlesMenuHolder(Inventory inventory, int currentPage, int maxPage, int mixPage) {
        this.inventory = inventory;
        this.currentPage = currentPage;
        this.maxPage = maxPage;
        this.minPage = mixPage;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
