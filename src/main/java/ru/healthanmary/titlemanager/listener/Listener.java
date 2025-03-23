package ru.healthanmary.titlemanager.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.InventoryHolder;
import ru.healthanmary.titlemanager.cache.PlayerTitleCache;
import ru.healthanmary.titlemanager.mysql.Storage;
import ru.healthanmary.titlemanager.util.AvailableTitlesMenuHolder;
import ru.healthanmary.titlemanager.util.MenuManager;
import ru.healthanmary.titlemanager.util.Title;

import java.util.UUID;

public class Listener implements org.bukkit.event.Listener {
    private Storage storage;
    private PlayerTitleCache playerCache;
    private MenuManager menuManager;

    public Listener(Storage storage, PlayerTitleCache playerCache, MenuManager menuManager) {
        this.storage = storage;
        this.playerCache = playerCache;
        this.menuManager = menuManager;
    }
    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        if (menuManager.isTitleMenu(e.getInventory())) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
            switch (e.getSlot()) {
                case 20: {

                }
            }
        }
    }
    @EventHandler
    public void onInventoryClickEvent2(InventoryClickEvent e) {
        if (e.getInventory().getHolder() instanceof AvailableTitlesMenuHolder) {
            AvailableTitlesMenuHolder holder = (AvailableTitlesMenuHolder) e.getInventory();

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
}
