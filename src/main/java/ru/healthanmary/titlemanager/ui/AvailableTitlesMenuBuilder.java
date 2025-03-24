package ru.healthanmary.titlemanager.ui;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.healthanmary.titlemanager.mysql.Storage;
import ru.healthanmary.titlemanager.util.AvailableTitlesMenuHolder;
import ru.healthanmary.titlemanager.util.Title;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AvailableTitlesMenuBuilder {
    private Storage storage;

    public AvailableTitlesMenuBuilder(Storage storage) {
        this.storage = storage;
    }
    public Inventory getAvailableTitlesMenu(String name, int page) {

        HashMap<Integer, List<Title>> splitedMap = getSplitedMap(storage.getArrayOfTitles(name));
        List<Title> titles = splitedMap.get(page);
        int maxPage = splitedMap.isEmpty() ? 1 : splitedMap.keySet().stream().max(Integer::compareTo).orElse(Integer.MIN_VALUE);
        int minPage = splitedMap.isEmpty() ? 1 : splitedMap.keySet().stream().min(Integer::compareTo).orElse(Integer.MIN_VALUE);

        Inventory inventory = Bukkit.createInventory(new AvailableTitlesMenuHolder(null, page, maxPage, minPage, titles),
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
        barrierItemMeta.setDisplayName(ChatColor.WHITE + "Убрать отображение титула " + ChatColor.RED + "(Клик)");
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
                storage.getCurrentTitleByName(name).getTitle_text().isBlank() ? ChatColor.GRAY
                + "Нету" : storage.getCurrentTitleByName(name).getTitle_text();
        starItemMeta.setDisplayName(ChatColor.WHITE + "Текущий титул: " + currentTitle);
        netherStar.setItemMeta(starItemMeta);

        for (int i : List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 46, 47, 48, 50, 51, 52, 53)) {
            inventory.setItem(i, purplePane);
        }
        for (int i : List.of(9, 17, 18, 26, 27, 35, 36, 44)) {
            inventory.setItem(i, pinkPane);
        }
        inventory.setItem(49, netherStar);
        inventory.setItem(45, barrier);

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

        for (Title title : titles) {
            int minAvailableSlot = Collections.min(availableSlots);
            inventory.setItem(minAvailableSlot, getTitleNametag(title));
            availableSlots.remove((Integer) minAvailableSlot);
        }

        return inventory;
    }

    public static HashMap<Integer, List<Title>> getSplitedMap (List<Title> inputArr) {
        HashMap<Integer, List<Title>> map = new HashMap<>();
        int chunkIndex = 1;
        int mappedIndex = 1;
        for (Title title : inputArr) {
            List<Title> mappedTitles = map.computeIfAbsent(mappedIndex, k -> new ArrayList<>());
            if (chunkIndex % 28 == 0) {
                mappedIndex++;
            }
            mappedTitles.add(title);
            chunkIndex++;
        }
        return map;
    }
    public ItemStack getTitleNametag(Title title) {
        ItemStack namegtag = new ItemStack(Material.NAME_TAG, 1);
        ItemMeta itemMeta = namegtag.getItemMeta();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyyг.");
        LocalDateTime request_date = title.getRequest_date().toLocalDateTime();
        LocalDateTime accepted_date;

        String orange_color = "#E94F08";
        String purple_color = "#e3b5fa";
        String comment = title.getAdmin_comment() == null || title.getAdmin_comment().isBlank() ? "Нету" : title.getAdmin_comment();
        String string_request_date = request_date.format(formatter);
        String string_accepted_date = null;

        if (title.getAccept_date() != null) {
            accepted_date = title.getAccept_date().toLocalDateTime();
            string_accepted_date = accepted_date.format(formatter);
        }
        itemMeta.setDisplayName(ChatColor.of(orange_color) + "◀ "+ ChatColor.WHITE + "Титул: " + ChatColor.RESET + title.getTitle_text());

        switch (title.getState()) {
            case ACCEPTED: {
                itemMeta.setLore(List.of(
                        " ",
                        ChatColor.of(orange_color) + "⊢ " + ChatColor.WHITE + "Статус: " + ChatColor.GREEN + "Принят",
                        ChatColor.of(orange_color) + "⊢ " + ChatColor.WHITE + "Дата заявки: " + ChatColor.of(purple_color) + string_request_date,
                        ChatColor.of(orange_color) + "⊢ " + ChatColor.WHITE + "Дата рассмотрения: " + ChatColor.of(purple_color) + string_accepted_date,
                        ChatColor.of(orange_color) + "⊢ " + ChatColor.WHITE + "Комментарий: " + ChatColor.GRAY + comment
                ));
                break;
            }
            case REJECTED: {
                itemMeta.setLore(List.of(
                        " ",
                        ChatColor.of(orange_color) + "⊢ " + ChatColor.WHITE + "Статус: " + ChatColor.RED + "Отклонен",
                        ChatColor.of(orange_color) + "⊢ " + ChatColor.WHITE + "Дата заявки: " + ChatColor.of(purple_color) + string_request_date,
                        ChatColor.of(orange_color) + "⊢ " + ChatColor.WHITE + "Дата рассмотрения: " + ChatColor.of(purple_color) + string_accepted_date,
                        ChatColor.of(orange_color) + "⊢ " + ChatColor.WHITE + "Комментарий: " + ChatColor.GRAY + comment
                ));
                break;
            }
            case UNDER_REVIEW: {
                itemMeta.setLore(List.of(
                        " ",
                        ChatColor.of(orange_color) + "⊢ " + ChatColor.WHITE + "Статус: " + ChatColor.GOLD + "На рассмотрении",
                        ChatColor.of(orange_color) + "⊢ " + ChatColor.WHITE + "Дата заявки: " + ChatColor.of(purple_color) + string_request_date
                ));
                break;
            }
            default: {
                itemMeta.setDisplayName(ChatColor.RED + "Некорректное состояние титула");
                break;
            }
        }
        namegtag.setItemMeta(itemMeta);
        return namegtag;
    }
}
