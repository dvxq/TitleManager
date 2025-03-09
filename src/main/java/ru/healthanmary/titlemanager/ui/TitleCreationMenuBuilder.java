package ru.healthanmary.titlemanager.ui;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class TitleCreationMenuBuilder {
    public static Inventory menu;
    public TitleCreationMenuBuilder() {
        this.menu = getTitleCreationMenu();
    }
    private Inventory getTitleCreationMenu() {
        Inventory menu = Bukkit.createInventory(null, 45, "Создание титула");
        ItemStack red_pane = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        ItemStack painting = new ItemStack(Material.PAINTING, 1);
        ItemStack ender_eye = new ItemStack(Material.ENDER_EYE, 1);
        ItemStack writable_book = new ItemStack(Material.WRITABLE_BOOK, 1);
        String orange_color = "#E94F08";
        String yellow_color = "#EDAE01";
        String white_color = "#FFE4BD";

        ItemMeta paneItemMeta = red_pane.getItemMeta();
        paneItemMeta.setDisplayName(" ");
        red_pane.setItemMeta(paneItemMeta);

        ItemMeta paintingItemMeta = painting.getItemMeta();
        paintingItemMeta.setDisplayName(ChatColor.of(white_color) +"Доступные титулы");
        paintingItemMeta.setLore(List.of(
                ""
        ));
        painting.setItemMeta(paintingItemMeta);


        ItemMeta enderEyeItemMeta = ender_eye.getItemMeta();
        enderEyeItemMeta.setDisplayName(ChatColor.of(white_color) + "Создать кастомный титул");
        enderEyeItemMeta.setLore(List.of(
                " ",
                ChatColor.of(orange_color) + "Количество доступных поинтов: " + ChatColor.of(yellow_color) + "Неизвестно"
        ));
        ender_eye.setItemMeta(enderEyeItemMeta);

        ItemMeta writableBookItemMeta = writable_book.getItemMeta();
        writableBookItemMeta.setDisplayName(ChatColor.of(white_color) + "Основная информация");
        writableBookItemMeta.setLore(List.of(
                " ",
                ChatColor.of(orange_color) + "1. " + ChatColor.of(yellow_color) + "Бла бла бла",
                ChatColor.of(orange_color) + "2. " + ChatColor.of(yellow_color) + "Бла бла бла",
                ChatColor.of(orange_color) + "3. " + ChatColor.of(yellow_color) + "Бла бла бла"
        ));
        writable_book.setItemMeta(writableBookItemMeta);

        menu.setItem(20, painting);
        menu.setItem(22, ender_eye);
        menu.setItem(24, writable_book);

        int[] redPaneSlots = {1, 2, 3, 4, 5, 6, 7, 9, 18, 27, 36, 37, 38, 39, 40, 41, 42, 4, 0, 8, 43, 44, 17, 26, 35};
        for (int slot : redPaneSlots) {
         menu.setItem(slot, red_pane);
        }
        return menu;
    }
}
