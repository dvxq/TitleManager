package ru.healthanmary.titlemanager.menus.availableTitles;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.healthanmary.titlemanager.mysql.Storage;
import ru.healthanmary.titlemanager.util.Title;
import ru.healthanmary.titlemanager.util.TitleUtil;

import java.util.*;

public class AvailableTitlesMenuBuilder {
    private final Storage storage;

    public AvailableTitlesMenuBuilder(Storage storage) {
        this.storage = storage;
    }
    public Inventory getAvailableTitlesMenu(String name, int page) {

        HashMap<Integer, List<Title>> splitedMap = TitleUtil.getSplitedMap(storage.getArrayOfTitles(name));
        List<Title> titles = splitedMap.get(page);
        int maxPage = splitedMap.isEmpty() ? 1 : splitedMap.keySet().stream().max(Integer::compareTo).orElse(Integer.MIN_VALUE);
        int minPage = splitedMap.isEmpty() ? 1 : splitedMap.keySet().stream().min(Integer::compareTo).orElse(Integer.MIN_VALUE);

        Inventory inventory = Bukkit.createInventory(new AvailableTitlesMenuHolder(page, maxPage, minPage, titles),
                54, "Доступные титулы (" + page + "/" + maxPage + ")");
        String orangeColor = "#E94F08";
        ItemStack purplePane = new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE, 1);
        ItemMeta purplePaneItemMeta = purplePane.getItemMeta();
        purplePaneItemMeta.setDisplayName(" ");
        purplePane.setItemMeta(purplePaneItemMeta);

        ItemStack pinkPane = new ItemStack(Material.PINK_STAINED_GLASS_PANE, 1);
        ItemMeta pinkPaneItemMeta = pinkPane.getItemMeta();
        pinkPaneItemMeta.setDisplayName(" ");
        pinkPane.setItemMeta(pinkPaneItemMeta);

        ItemStack barrier = new ItemStack(Material.BARRIER, 1);
        ItemMeta barrierItemMeta = barrier.getItemMeta();
        barrierItemMeta.setDisplayName("§c▶ " + ChatColor.WHITE + "Убрать отображение титула");
        barrier.setItemMeta(barrierItemMeta);

        ItemStack arrowNext = new ItemStack(Material.ARROW, 1);
        ItemMeta arrowNextItemMeta = arrowNext.getItemMeta();
        arrowNextItemMeta.setDisplayName(ChatColor.WHITE + "Следующая страница " + ChatColor.of(orangeColor) + "▶");
        arrowNext.setItemMeta(arrowNextItemMeta);

        ItemStack arrowPrevious = new ItemStack(Material.ARROW, 1);
        ItemMeta arrowPreviousItemMeta = arrowPrevious.getItemMeta();
        arrowPreviousItemMeta.setDisplayName(ChatColor.of(orangeColor) + "◀ " + ChatColor.WHITE + "Предыдущая страница ");
        arrowPrevious.setItemMeta(arrowPreviousItemMeta);

        ItemStack netherStar = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta starItemMeta = netherStar.getItemMeta();
        String currentTitle = storage.getCurrentTitleByName(name) == null ||
                storage.getCurrentTitleByName(name).getTitleText().isBlank() ? ChatColor.GRAY
                + "Нету" : storage.getCurrentTitleByName(name).getTitleText();
        starItemMeta.setDisplayName(ChatColor.WHITE + "Текущий титул: " + currentTitle);
        netherStar.setItemMeta(starItemMeta);

        ItemStack spectralArrow = new ItemStack(Material.SPECTRAL_ARROW, 1);
        ItemMeta spectralArrowMeta = spectralArrow.getItemMeta();
        spectralArrowMeta.setDisplayName(ChatColor.of(orangeColor) + "◀ §fВернуться назад");
        spectralArrow.setItemMeta(spectralArrowMeta);

        for (int i : List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 47, 48, 50, 51, 52, 53)) {
            inventory.setItem(i, purplePane);
        }
        for (int i : List.of(9, 17, 18, 26, 27, 35, 36, 44)) {
            inventory.setItem(i, pinkPane);
        }
        inventory.setItem(49, netherStar);
        inventory.setItem(45, barrier);
        inventory.setItem(46, spectralArrow);

        // invalid conditions
        if (page < minPage) {
            inventory.setItem(52, purplePane);
            inventory.setItem(53, arrowNext);
            return inventory;
        }
        if (page > maxPage) {
            inventory.setItem(53, purplePane);
            inventory.setItem(52, arrowPrevious);
            return inventory;
        }

        // valid
        if (minPage == maxPage) {
            inventory.setItem(52, purplePane);
            inventory.setItem(53, purplePane);
        }else if (page == minPage) {
            inventory.setItem(52, purplePane);
            inventory.setItem(53, arrowNext);
        } else if (page == maxPage) {
            inventory.setItem(52, arrowPrevious);
            inventory.setItem(53, purplePane);
        } else {
            inventory.setItem(52, arrowPrevious);
            inventory.setItem(53, arrowNext);
        }

        List<Integer> availableSlots = new ArrayList<>(Arrays.asList(
                10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25,
                28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43
        ));

        if (titles == null || titles.isEmpty()) return inventory;

        for (Title title : titles) {
            int minAvailableSlot = Collections.min(availableSlots);
            inventory.setItem(minAvailableSlot, TitleUtil.getTitleNametag(title, false));
            availableSlots.remove((Integer) minAvailableSlot);
        }

        return inventory;
    }
}
