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
        ItemStack golden_apple = new ItemStack(Material.GOLDEN_APPLE, 1);
        ItemStack ender_eye = new ItemStack(Material.ENDER_EYE, 1);
        ItemStack writable_book = new ItemStack(Material.WRITABLE_BOOK, 1);

        ItemMeta paneItemMeta = red_pane.getItemMeta();
        paneItemMeta.setDisplayName(" ");
        red_pane.setItemMeta(paneItemMeta);

        ItemMeta goldenAppleItemMeta = golden_apple.getItemMeta();
        goldenAppleItemMeta.setLore(List.of(
                " ",
                ChatColor.GRAY+ "Просто лежит для красоты"
        ));
        golden_apple.setItemMeta(goldenAppleItemMeta);


        ItemMeta enderEyeItemMeta = ender_eye.getItemMeta();
        enderEyeItemMeta.setDisplayName(ChatColor.RED + "Создать кастомный титул " + ChatColor.GRAY + "(ЛКМ)");
        enderEyeItemMeta.setLore(List.of(
                " ",
                ChatColor.WHITE + "Количество доступных марок: " + ChatColor.AQUA + "connect mysql"
        ));
        ender_eye.setItemMeta(enderEyeItemMeta);

        ItemMeta writableBookItemMeta = writable_book.getItemMeta();
        writableBookItemMeta.setDisplayName(ChatColor.GOLD + "Основная информация");
        writableBookItemMeta.setLore(List.of(
                " ",
                ChatColor.GRAY+ "1. " + ChatColor.WHITE+ "Бла бла бла",
                ChatColor.GRAY+ "2. " + ChatColor.WHITE+ "Бла бла бла",
                ChatColor.GRAY+ "3. " + ChatColor.WHITE+ "Бла бла бла"
        ));
        writable_book.setItemMeta(writableBookItemMeta);

        menu.setItem(20, golden_apple);
        menu.setItem(22, ender_eye);
        menu.setItem(24, writable_book);

        int[] redPaneSlots = {1, 2, 3, 4, 5, 6, 7, 9, 18, 27, 36, 37, 38, 39, 40, 41, 42, 4, 0, 8, 43, 44, 17, 26, 35};
        for (int slot : redPaneSlots) {
         menu.setItem(slot, red_pane);
        }
        return menu;
    }
}
