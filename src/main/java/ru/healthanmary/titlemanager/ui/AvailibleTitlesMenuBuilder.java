package ru.healthanmary.titlemanager.ui;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.healthanmary.titlemanager.mysql.Storage;
import ru.healthanmary.titlemanager.util.AvailibleTitlesMenuHolder;
import ru.healthanmary.titlemanager.util.Title;

public class AvailibleTitlesMenuBuilder {
    private Storage storage;

    public AvailibleTitlesMenuBuilder(Storage storage) {
        this.storage = storage;
    }
    public Inventory getAvailibleTitlesMenu(String name) {
        Inventory inventory = Bukkit.createInventory(new AvailibleTitlesMenuHolder(null), 54, "Доступные титулы");
        ItemStack purple_pane = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1);
        ItemStack pink_pane = new ItemStack(Material.PINK_STAINED_GLASS_PANE, 1);
        ItemStack red_wool = new ItemStack(Material.RED_WOOL, 1);
        ItemStack yellow_wool = new ItemStack(Material.YELLOW_WOOL, 1);
        ItemStack nametag = new ItemStack(Material.NAME_TAG, 1);
        ItemStack barrier = new ItemStack(Material.BARRIER, 1);
        ItemStack nether_star = new ItemStack(Material.NETHER_STAR, 1);
        ItemStack arrow = new ItemStack(Material.ARROW, 1);


        return inventory;
    }
    private ItemStack renameRedWool(Title title) {
        ItemStack wool = new ItemStack(Material.RED_WOOL, 1);
        ItemMeta itemMeta = wool.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "⇔" + ChatColor.WHITE + " Титул" + ChatColor.RED + ": " +
                title.getTitle_text());

        return wool;
    }
    private String conventToRgb(String code) {
//        StringBuilder result = new StringBuilder();
//        String[] parts = code.split("&x&");
//
//        for (int i = 1; i < parts.length; i++) {
//            String group = parts[i];
//            if (group.length() >= 6) {
//                String hexColor = group.substring(0, 6).replace("&", "");
//                String suffix = group.substring(6).replace("&", "");
//                result.append("&#").append(hexColor).append(suffix);
//            }
//        }
//        return result.toString();
//    }
//    private boolean isHexCode(String code) {
        return "";
    }
}
