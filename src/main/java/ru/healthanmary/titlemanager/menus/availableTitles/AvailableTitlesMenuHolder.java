package ru.healthanmary.titlemanager.menus.availableTitles;

import lombok.Getter;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import ru.healthanmary.titlemanager.menus.TitleMenuHolder;
import ru.healthanmary.titlemanager.util.Title;

import java.util.List;

@Getter
public class AvailableTitlesMenuHolder implements InventoryHolder, TitleMenuHolder {
    private int currentPage;
    private int maxPage;
    private int minPage;
    private List<Title> titles;

    public AvailableTitlesMenuHolder(int currentPage, int maxPage, int mixPage, List<Title> titles) {
        this.currentPage = currentPage;
        this.maxPage = maxPage;
        this.minPage = mixPage;
        this.titles = titles;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return null;
    }
}
