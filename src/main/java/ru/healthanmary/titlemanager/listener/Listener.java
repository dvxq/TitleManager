package ru.healthanmary.titlemanager.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import ru.healthanmary.titlemanager.cache.PlayerTitleCache;
import ru.healthanmary.titlemanager.mysql.Storage;
import ru.healthanmary.titlemanager.ui.AvailableTitlesMenuBuilder;
import ru.healthanmary.titlemanager.util.AvailableTitlesMenuHolder;
import ru.healthanmary.titlemanager.util.MenuManager;
import ru.healthanmary.titlemanager.util.Title;

import java.util.List;
import java.util.UUID;

public class Listener implements org.bukkit.event.Listener {
    private Storage storage;
    private PlayerTitleCache playerCache;
    private MenuManager menuManager;
    private AvailableTitlesMenuBuilder availableTitlesMenuBuilder;
    public Listener(Storage storage, PlayerTitleCache playerCache, MenuManager menuManager, AvailableTitlesMenuBuilder availableTitlesMenuBuilder) {
        this.storage = storage;
        this.playerCache = playerCache;
        this.menuManager = menuManager;
        this.availableTitlesMenuBuilder = availableTitlesMenuBuilder;
    }
    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        if (menuManager.isTitleMenu(e.getInventory())) {
            e.setCancelled(true);
//            Player player = (Player) e.getWhoClicked();
//            switch (e.getSlot()) {
//                case 20: {
//
//                }
//            }
        }
    }
    @EventHandler
    public void onChooseTitle(InventoryClickEvent e) {
        if (e.getClickedInventory().getHolder() instanceof AvailableTitlesMenuHolder) {
            Player player = (Player) e.getWhoClicked();
            int clickedSlot = e.getSlot();
            AvailableTitlesMenuHolder holder = (AvailableTitlesMenuHolder) e.getInventory().getHolder();
            List<Title> titles = holder.getTitles();
            int titleIndex = getTitleIndex(clickedSlot);
            if (titleIndex == -1 || titleIndex >= titles.size()) return;
            player.sendMessage(String.valueOf(titles.get(titleIndex).getId()));
        }
    }
    @EventHandler
    public void onSwitchPage(InventoryClickEvent e) {
        if (e.getInventory().getHolder() instanceof AvailableTitlesMenuHolder) {
            Player player = (Player) e.getWhoClicked();
            AvailableTitlesMenuHolder holder = (AvailableTitlesMenuHolder) e.getInventory().getHolder();
            ItemStack currentItem = e.getCurrentItem();
            if (currentItem == null || currentItem.getType() != Material.ARROW) return;
            int maxPage = holder.getMaxPage();
            int minPage = holder.getMinPage();
            int currentPage = holder.getCurrentPage();

            switch (e.getSlot()) {
                case 52: {
                    if (currentPage > minPage) {
                        player.openInventory(availableTitlesMenuBuilder.getAvailableTitlesMenu(player.getName(), currentPage-1));
                    }
                    break;
                }
                case 53: {
                    if (currentPage < maxPage) {
                        player.openInventory(availableTitlesMenuBuilder.getAvailableTitlesMenu(player.getName(), currentPage+1));

                    }
                    break;
                }
            }
        }
    }
    @EventHandler
    public void onInventoryDragEvent(InventoryDragEvent e) {
        if (menuManager.isTitleMenu(e.getInventory())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void on(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        Title title = storage.getCurrentTitleByName(player.getName());
        playerCache.putTitle(player.getUniqueId(), title);
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        Title title = playerCache.getTitle(uuid);
        if (title != null) {
            Integer id = title.getId();
            storage.setCurrentTitle(player.getName(), id);
        } else {
            storage.setCurrentTitle(player.getName(), null);
        }
        playerCache.clearValue(uuid);
    }
    public static int getTitleIndex(int slot) {
        if ((slot >= 10 && slot <= 16) || (slot >= 19 && slot <= 25) ||
                (slot >= 28 && slot <= 34) || (slot >= 37 && slot <= 43)) {

            return (slot - 10) - ((slot - 10) / 9) * 2;
        }
        return -1;
    }
}
