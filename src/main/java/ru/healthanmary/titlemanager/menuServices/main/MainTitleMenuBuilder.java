package ru.healthanmary.titlemanager.menuServices.main;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.healthanmary.titlemanager.mysql.Storage;

import java.util.List;

public class MainTitleMenuBuilder {
    private final Storage storage;

    public MainTitleMenuBuilder(Storage storage) {
        this.storage = storage;
    }

    public Inventory getTitleCreationMenu(String playerName) {
        Inventory menu = Bukkit.createInventory(new MainMenuHolder(), 45, "Создание титула");
        ItemStack purplePane = new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE, 1);
        ItemStack pinkPake = new ItemStack(Material.PINK_STAINED_GLASS_PANE, 1);
        ItemStack painting = new ItemStack(Material.PAINTING, 1);
        ItemStack enderEye = new ItemStack(Material.ENDER_EYE, 1);
        ItemStack writableBook = new ItemStack(Material.WRITABLE_BOOK, 1);
        String orangeColor = "#E94F08";
        String yellowColor = "#EDAE01";
        String whiteColor = "#FFE4BD";

        ItemMeta paneItemMeta = pinkPake.getItemMeta();
        paneItemMeta.setDisplayName(" ");
        pinkPake.setItemMeta(paneItemMeta);

        ItemMeta purplePaneMeta = purplePane.getItemMeta();
        purplePaneMeta.setDisplayName(" ");
        purplePane.setItemMeta(purplePaneMeta);

        ItemMeta paintingItemMeta = painting.getItemMeta();
        paintingItemMeta.setDisplayName("§8◀ " + ChatColor.of(whiteColor) +"Меню титулов §8▶");
        painting.setItemMeta(paintingItemMeta);

        ItemMeta enderEyeItemMeta = enderEye.getItemMeta();
        enderEyeItemMeta.setDisplayName("§8              ◀ " + ChatColor.of(whiteColor) + "Создать кастомный титул §8▶");
        enderEyeItemMeta.setLore(List.of(
                " ",
                ChatColor.of(orangeColor) + " Количество доступных жетонов: " +
                ChatColor.of(yellowColor) + storage.getPlayerPoints(playerName),
                " ",
                " §7После нажатия начнется процесс создания титула.",
                " §7Вам нужно будет ввести желаемый титул в чат.",
                " §7Затем отправится заявка на одобрения, после",
                " §7принятия которой вы сможете установить титул."
        ));
        enderEye.setItemMeta(enderEyeItemMeta);

        ItemMeta writableBookItemMeta = writableBook.getItemMeta();
        writableBookItemMeta.setDisplayName("§8               ◀ " + ChatColor.of(whiteColor) + "Основная информация §8▶");
        writableBookItemMeta.setLore(List.of(
                " ",
                ChatColor.of(yellowColor) + " §lНе хватает разнообразия в обычной игре?",
                ChatColor.of(yellowColor) + " Есть решение — " + ChatColor.of(orangeColor) + "создай кастомный титул!",
                ChatColor.of(orangeColor) + " Подумай котелком " + ChatColor.of(yellowColor) + "и сделай что-то неповторимое.",
                ChatColor.of(yellowColor) + " Титул уникален, такой будет " + ChatColor.of(orangeColor) + "только у тебя!",
                " ",
                " §7Один жетон = 1 создание титула"
        ));
        writableBook.setItemMeta(writableBookItemMeta);

        menu.setItem(24, painting);
        menu.setItem(20, enderEye);
        menu.setItem(22, writableBook);

        int[] purplePaneSlots = {1, 2, 3, 4, 5, 6, 7, 36, 37, 38, 39, 40, 41, 42, 4, 0, 8, 43, 44};
        int[] pinkPaneSlots = {9, 18, 27, 17, 26, 35};
        for (int slot : purplePaneSlots) {
            menu.setItem(slot, purplePane);
        }
        for (int slot : pinkPaneSlots) {
            menu.setItem(slot, pinkPake);
        }
        return menu;
    }
}
