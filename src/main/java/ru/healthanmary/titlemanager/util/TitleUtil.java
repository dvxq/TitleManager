package ru.healthanmary.titlemanager.util;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TitleUtil {
    public static int getTitleIndex(int slot) {
        if ((slot >= 10 && slot <= 16) || (slot >= 19 && slot <= 25) ||
                (slot >= 28 && slot <= 34) || (slot >= 37 && slot <= 43)) {

            return (slot - 10) - ((slot - 10) / 9) * 2;
        }
        return -1;
    }
    public static ItemStack getTitleNametag(Title title, boolean isForAdmin) {
        ItemStack namegtag = new ItemStack(Material.NAME_TAG, 1);
        ItemMeta itemMeta = namegtag.getItemMeta();

        String orange_color = "#E94F08";
        String purple_color = "#e3b5fa";
        String comment = title.getAdminComment() == null || title.getAdminComment().isBlank() ? "Нету" : title.getAdminComment();

        String displayName = ChatColor.of(orange_color) + "▶ "+ ChatColor.WHITE + "Титул: "
                + ChatColor.RESET + title.getTitleText() + (isForAdmin ? " §b(ID: " + title.getId() + ")" : "");
        itemMeta.setDisplayName(displayName);

        List<String> lore = new ArrayList<>();
        lore.add(" ");

        String statusLine;
        String statusColor;

        switch (title.getState()) {
            case ACCEPTED -> statusColor = ChatColor.GREEN + "Принят";
            case REJECTED -> statusColor = ChatColor.RED + "Отклонен";
            case UNDER_REVIEW -> statusColor = ChatColor.GOLD + "На рассмотрении";
            default -> {
                itemMeta.setDisplayName(ChatColor.RED + "Некорректное состояние титула");
                namegtag.setItemMeta(itemMeta);
                return namegtag;
            }
        }

        statusLine = ChatColor.of(orange_color) + "⊢ " + ChatColor.WHITE + "Статус: " + statusColor;
        lore.add(statusLine);

        DateTimeFormatter formatter;
        Timestamp requestDate = title.getRequestDate();
        Timestamp reviewDate = title.getReviewDate();
        formatter = (isForAdmin) ? DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy")
                : DateTimeFormatter.ofPattern("dd MMMM yyyy г.");
        String formattedRequestDate = (requestDate != null)
                ? requestDate.toLocalDateTime().format(formatter)
                : "§7Нету";

        lore.add(ChatColor.of(orange_color) + "⊢ " + ChatColor.WHITE + "Дата заявки: " + ChatColor.of(purple_color) + formattedRequestDate);
        if (title.getState() == Title.State.UNDER_REVIEW) {
            itemMeta.setLore(lore);
            namegtag.setItemMeta(itemMeta);
            return namegtag;
        }

        String formattedReviewDate = (reviewDate != null)
                ? reviewDate.toLocalDateTime().format(formatter)
                : "§7Нету";
        lore.add(ChatColor.of(orange_color) + "⊢ " + ChatColor.WHITE + "Дата рассмотрения: " + ChatColor.of(purple_color) + formattedReviewDate);

        if (isForAdmin) {
            String adminName = (title.getReviewAdmin() != null) ? title.getReviewAdmin() : "§7Нету";
            lore.add(ChatColor.of(orange_color) + "⊢ " + ChatColor.WHITE + "Администратор: " + ChatColor.AQUA + adminName);
        }
        if (title.getState() == Title.State.REJECTED || isForAdmin)
            lore.add(ChatColor.of(orange_color) + "⊢ " + ChatColor.WHITE + "Комментарий: §7" + comment);

        itemMeta.setLore(lore);
        namegtag.setItemMeta(itemMeta);
        return namegtag;
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
}
