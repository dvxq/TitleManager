package ru.healthanmary.titlemanager.menuServices.availableTitles;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.healthanmary.titlemanager.TitleManager;
import ru.healthanmary.titlemanager.cache.TitleCacheManager;
import ru.healthanmary.titlemanager.mysql.Storage;
import ru.healthanmary.titlemanager.util.Title;
import ru.healthanmary.titlemanager.util.TitleUtil;

import java.util.List;

public class AvailableTitlesMenuListener implements Listener {
    private final AvailableTitlesMenuBuilder availableTitlesMenuBuilder;
    private final TitleCacheManager titleCacheManager;
    private final Storage storage;

    public AvailableTitlesMenuListener(AvailableTitlesMenuBuilder availableTitlesMenuBuilder, TitleCacheManager titleCacheManager, Storage storage) {
        this.availableTitlesMenuBuilder = availableTitlesMenuBuilder;
        this.titleCacheManager = titleCacheManager;
        this.storage = storage;
    }


    // switch page functionality
    @EventHandler
    public void onSwitchPage(InventoryClickEvent e) {
        Inventory inventory = e.getInventory();
        if (inventory == null) return;
        if (inventory.getHolder() instanceof AvailableTitlesMenuHolder) {
            Player player = (Player) e.getWhoClicked();
            AvailableTitlesMenuHolder holder = (AvailableTitlesMenuHolder) inventory.getHolder();
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

    // title reset functionality
    @EventHandler
    public void onButtonClick(InventoryClickEvent e) {
        Inventory inventory = e.getInventory();
        Inventory clickedInventory = e.getClickedInventory();
        if (inventory == null || clickedInventory == null) return;
        if (inventory.getHolder() instanceof AvailableTitlesMenuHolder && clickedInventory.getType() == InventoryType.CHEST) {
            Player player = (Player) e.getWhoClicked();
            switch (e.getSlot()) {
                case 45: {
                    player.closeInventory();
                    titleCacheManager.clearValue(player.getUniqueId());
                    Bukkit.getScheduler().runTaskAsynchronously(TitleManager.instance, () -> {
                        storage.setCurrentTitle(player.getName(), null);
                    });
                    player.sendMessage(ChatColor.of("#E94F08") + "▶ " + ChatColor.WHITE + "Вы успешно убрали отображение титула");
                    break;
                } case 46: {
                    player.performCommand("customtitle");
                    break;
                }
            }
        }
    }

    // title choosing functionality
    @EventHandler
    public void onChooseTitle(InventoryClickEvent e) {
        Inventory clickedInventory = e.getClickedInventory();
        if (clickedInventory == null) return;
        if (clickedInventory.getHolder() instanceof AvailableTitlesMenuHolder) {
            Player player = (Player) e.getWhoClicked();
            String playerName = player.getName();
            AvailableTitlesMenuHolder holder = (AvailableTitlesMenuHolder) e.getInventory().getHolder();
            List<Title> titles = holder.getTitles();
            int clickedSlot = e.getSlot();
            int titleIndex = TitleUtil.getTitleIndex(clickedSlot);

            if (titles == null || titleIndex == -1 || titleIndex >= titles.size()) {
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
                    titleCacheManager.putTitle(player.getUniqueId(), title);
                    player.sendMessage(ChatColor.of("#E94F08") + "▶ " + ChatColor.WHITE + "Вы успешно сменили титул на" +
                            ChatColor.AQUA + ": " + ChatColor.RESET + title.getTitleText());
                } else {
                    Bukkit.getScheduler().runTask(TitleManager.instance, () -> {
                        player.openInventory(availableTitlesMenuBuilder.getAvailableTitlesMenu(playerName, holder.getCurrentPage()));
                    });
                }
            });
        }
    }
}
