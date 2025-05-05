package ru.healthanmary.titlemanager.menus.peek;

import lombok.Getter;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import ru.healthanmary.titlemanager.menus.TitleMenuHolder;
import ru.healthanmary.titlemanager.util.Title;

import java.util.List;

@Getter
public class PeekMenuHolder implements InventoryHolder, TitleMenuHolder {
    private String player;
    private int currentPage;
    private int maxPage;
    private int minPage;
    private List<Title> titles;
    public PeekMenuHolder(int currentPage, int maxPage, int mixPage, List<Title> titles, String player) {
        this.currentPage = currentPage;
        this.maxPage = maxPage;
        this.minPage = mixPage;
        this.titles = titles;
        this.player = player;
    }
    @Override
    public @NotNull Inventory getInventory() {
        return null;
    }
}
