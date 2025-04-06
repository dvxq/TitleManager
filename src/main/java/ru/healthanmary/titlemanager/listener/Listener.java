package ru.healthanmary.titlemanager.listener;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.healthanmary.titlemanager.TitleManager;
import ru.healthanmary.titlemanager.cache.PlayerTitleCache;
import ru.healthanmary.titlemanager.mysql.Storage;
import ru.healthanmary.titlemanager.ui.AvailableTitlesMenuBuilder;
import ru.healthanmary.titlemanager.invHolders.AvailableTitlesMenuHolder;
import ru.healthanmary.titlemanager.invHolders.CustomMenuHolder;
import ru.healthanmary.titlemanager.util.CreatingMenuManager;
import ru.healthanmary.titlemanager.util.MenuManager;
import ru.healthanmary.titlemanager.util.Title;

import java.util.List;
import java.util.UUID;

public class Listener implements org.bukkit.event.Listener {
    private final Storage storage;
    private final PlayerTitleCache playerCache;
    private final MenuManager menuManager;
    private final AvailableTitlesMenuBuilder availableTitlesMenuBuilder;
    private final CreatingMenuManager creatingMenuManager;
    public Listener(Storage storage, PlayerTitleCache playerCache, MenuManager menuManager, AvailableTitlesMenuBuilder availableTitlesMenuBuilder, CreatingMenuManager creatingMenuManager) {
        this.storage = storage;
        this.playerCache = playerCache;
        this.menuManager = menuManager;
        this.availableTitlesMenuBuilder = availableTitlesMenuBuilder;
        this.creatingMenuManager = creatingMenuManager;
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
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        if (creatingMenuManager.containsPlayer(player)) {
            // open menu for review the title
        }
    }
    @EventHandler
    public void onChooseTitle(InventoryClickEvent e) {
        if (e.getClickedInventory().getHolder() instanceof AvailableTitlesMenuHolder) {
            Player player = (Player) e.getWhoClicked();
            String playerName = player.getName();
            AvailableTitlesMenuHolder holder = (AvailableTitlesMenuHolder) e.getInventory().getHolder();
            List<Title> titles = holder.getTitles();
            int clickedSlot = e.getSlot();
            int titleIndex = getTitleIndex(clickedSlot);

            if (titleIndex == -1 || titleIndex >= titles.size()) {
                return;
            }

            Title title = titles.get(titleIndex);
            int id = title.getId();
            Bukkit.getScheduler().runTaskAsynchronously(TitleManager.instance, () -> {
                if (storage.hasTitle(playerName, id)) {
                    Bukkit.getScheduler().runTask(TitleManager.instance, () -> {
                        player.closeInventory();
                    });
                    storage.setCurrentTitle(playerName, id);
                    playerCache.putTitle(player.getUniqueId(), title);
                    player.sendMessage(ChatColor.of("#E94F08") + "▶ " + ChatColor.WHITE + "Вы успешно сменили титул на" +
                            ChatColor.AQUA + ": " + ChatColor.RESET + title.getTitle_text());
                } else {
                    Bukkit.getScheduler().runTask(TitleManager.instance, () -> {
                        player.openInventory(availableTitlesMenuBuilder.getAvailableTitlesMenu(playerName, holder.getCurrentPage()));
                    });
                }
            });

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
    public void onButtonClick(InventoryClickEvent e) {
        if (e.getInventory().getHolder() instanceof AvailableTitlesMenuHolder && e.getClickedInventory().getType() == InventoryType.CHEST
                && e.getSlot() == 45) {
            Player player = (Player) e.getWhoClicked();
            player.closeInventory();
            playerCache.clearValue(player.getUniqueId());
            Bukkit.getScheduler().runTaskAsynchronously(TitleManager.instance, () -> {
                storage.setCurrentTitle(player.getName(), null);
            });
            player.sendMessage(ChatColor.of("#E94F08") + "▶ " + ChatColor.WHITE + "Вы успешно убрали отображение титула");
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
    public void onInventoryClickEvent2(InventoryClickEvent e) {
        if (e.getInventory().getHolder() instanceof CustomMenuHolder && e.getClickedInventory().getType() == InventoryType.CHEST) {
            Player player = (Player) e.getWhoClicked();
            switch (e.getSlot()) {
                case 20: {
                    Bukkit.getScheduler().runTaskAsynchronously(TitleManager.instance, () -> {
                        Inventory availableTitlesMenu = availableTitlesMenuBuilder.getAvailableTitlesMenu(player.getName(), 1);
                        Bukkit.getScheduler().runTask(TitleManager.instance, () -> {
                            player.openInventory(availableTitlesMenu);
                        });
                    });
                    break;
                }
                case 24: {

                }
            }
        }
    }
    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        Title title = playerCache.getTitle(uuid);
        Bukkit.getScheduler().runTaskAsynchronously(TitleManager.instance, () -> {
            if (title != null) {
                Integer id = title.getId();
                storage.setCurrentTitle(player.getName(), id);
            } else {
                storage.setCurrentTitle(player.getName(), null);
            }
        });
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
