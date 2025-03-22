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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class AvailableTitlesMenuBuilder {
    private Storage storage;

    public AvailableTitlesMenuBuilder(Storage storage) {
        this.storage = storage;
    }
    public Inventory getAvailableTitlesMenu(String name) {
        Inventory inventory = Bukkit.createInventory(new AvailableTitlesMenuHolder(null), 54, "Доступные титулы");
        String orange_color = "#E94F08";
        ArrayList<Integer> availableSlots = new ArrayList<>(Arrays.asList(
                10, 11, 12, 13, 14, 15, 16,15, 20, 21, 22, 23, 24, 25,
                28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43
        ));
        ItemStack purple_pane = new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE, 1);
        ItemMeta purplePaneItemMeta = purple_pane.getItemMeta();
        purplePaneItemMeta.setDisplayName(" ");
        purple_pane.setItemMeta(purplePaneItemMeta);

        ItemStack pink_pane = new ItemStack(Material.PINK_STAINED_GLASS_PANE, 1);
        ItemMeta pinkPaneItemMeta = pink_pane.getItemMeta();
        pinkPaneItemMeta.setDisplayName(" ");
        pink_pane.setItemMeta(pinkPaneItemMeta);

        ItemStack barrier = new ItemStack(Material.BARRIER, 1);
        ItemMeta barrierItemMeta = barrier.getItemMeta();
        barrierItemMeta.setDisplayName(ChatColor.WHITE + "Убрать отображение титула " + ChatColor.RED + "(Клик)");
        barrier.setItemMeta(barrierItemMeta);

        ItemStack arrow_next = new ItemStack(Material.ARROW, 1);
        ItemMeta arrowNextItemMeta = arrow_next.getItemMeta();
        arrowNextItemMeta.setDisplayName("Следующая страница " + ChatColor.of(orange_color) + "▶");
        arrow_next.setItemMeta(arrowNextItemMeta);

        ItemStack arrow_previous = new ItemStack(Material.ARROW, 1);
        ItemMeta arrowPreviousItemMeta = arrow_previous.getItemMeta();
        arrowPreviousItemMeta.setDisplayName(ChatColor.of(orange_color) + "◀ " + ChatColor.WHITE + "Предыдущая страница ");
        arrow_previous.setItemMeta(arrowNextItemMeta);

        ItemStack nether_star = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta starItemMeta = nether_star.getItemMeta();
        starItemMeta.setDisplayName(ChatColor.WHITE + "Текущий титул: " + storage.getCurrentTitleByName(name).getTitle_text());
        nether_star.setItemMeta(starItemMeta);

        for (int i : List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 46, 47, 48, 50, 51, 52, 53)) {
            inventory.setItem(i, purple_pane);
        }
        for (int i : List.of(9, 17, 18, 26, 27, 35, 36, 44)) {
            inventory.setItem(i, pink_pane);
        }
        inventory.setItem(49, nether_star);
        inventory.setItem(45, barrier);

        ArrayList<Title> playerTitles = storage.getArrayOfTitles(name);
        LinkedHashMap<Integer, ArrayList<Title>> pageList = new LinkedHashMap<>();
        int pages = playerTitles.size() % 28 == 0 ? playerTitles.size() / 28 : playerTitles.size() / 28 + 1;
        if (pages <= 1) {
            for (Title title : playerTitles) {
//                inventory.setItem(availableSlots.fir);
            }
        } else {
            // show the arrows
            for (int i = 1; i == pages; i++) {
                pageList.put(i, )
            }
        }
        return inventory;
    }
    public ItemStack getTitleNametag(Title title) {
        ItemStack namegtag = new ItemStack(Material.NAME_TAG, 1);
        ItemMeta itemMeta = namegtag.getItemMeta();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyyг.");
        LocalDateTime request_date = title.getRequest_date().toLocalDateTime();
        LocalDateTime accepted_date;

        String orange_color = "#E94F08";
        String purple_color = "#e3b5fa";
        String comment = title.getAdmin_comment().isBlank() ? "Нету" : title.getAdmin_comment();
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
