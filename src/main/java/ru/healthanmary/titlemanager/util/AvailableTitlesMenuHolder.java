package ru.healthanmary.titlemanager.util;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AvailableTitlesMenuHolder implements InventoryHolder {
    private Inventory inventory;
    @Getter
    private int currentPage;
    @Getter
    private int maxPage;
    @Getter
    private int minPage;
    @Getter
    private List<Title> titles;

    public AvailableTitlesMenuHolder(Inventory inventory, int currentPage, int maxPage, int mixPage, List<Title> titles) {
        this.inventory = inventory;
        this.currentPage = currentPage;
        this.maxPage = maxPage;
        this.minPage = mixPage;
        this.titles = titles;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
