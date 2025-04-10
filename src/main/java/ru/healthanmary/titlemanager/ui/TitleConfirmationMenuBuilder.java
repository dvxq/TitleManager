package ru.healthanmary.titlemanager.ui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.healthanmary.titlemanager.invHolders.TitleConfirmationMenuHolder;

import java.util.List;

public class TitleConfirmationMenuBuilder {
    public Inventory getConfirmationMenu() {
        Inventory inventory = Bukkit.createInventory(new TitleConfirmationMenuHolder(), 54, "Подтверждение титула");
        ItemStack pinkPane = new ItemStack(Material.PINK_STAINED_GLASS_PANE, 1);
        ItemStack purplePane = new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE, 1);

        // clear the pane names
        ItemMeta pinkPaneMeta = pinkPane.getItemMeta();
        pinkPaneMeta.setDisplayName(" ");
        pinkPane.setItemMeta(pinkPaneMeta);

        ItemMeta purplePaneMeta = purplePane.getItemMeta();
        purplePaneMeta.setDisplayName(" ");
        purplePane.setItemMeta(pinkPaneMeta);

        // fill the inv with panes
        List<Integer> purpleSlots = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 45, 46, 47, 48, 49, 50, 51, 52, 53);
        List<Integer> pinkSlots = List.of(9, 17, 18, 26, 27, 35, 36, 44);

        purpleSlots.forEach(slot -> inventory.setItem(slot, purplePane));
        pinkSlots.forEach(slot -> inventory.setItem(slot, pinkPane));

        // create click buttons
        ItemStack limeWool = new ItemStack(Material.LIME_WOOL, 1);
        ItemStack redWool = new ItemStack(Material.RED_WOOL, 1);

        // adds a lore (from config)

        return inventory;
    }
}
