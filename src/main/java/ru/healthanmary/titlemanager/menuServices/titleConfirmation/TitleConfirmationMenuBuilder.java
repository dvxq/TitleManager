package ru.healthanmary.titlemanager.menuServices.titleConfirmation;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.healthanmary.titlemanager.config.MainConfigParser;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TitleConfirmationMenuBuilder {
    private MainConfigParser mainConfigParser;

    public TitleConfirmationMenuBuilder(MainConfigParser mainConfigParser) {
        this.mainConfigParser = mainConfigParser;
    }

    private ItemStack getExampleItem(Player player, String title) {
        ItemStack exampleItem = new ItemStack(mainConfigParser.getExampleItem());
        ItemMeta itemMeta = exampleItem.getItemMeta();

        itemMeta.setDisplayName(PlaceholderAPI.setPlaceholders(player, itemMeta.getDisplayName()).replace("{title}", title)
                .replace("&", "§"));
        List<String> lore = PlaceholderAPI.setPlaceholders(player, itemMeta.getLore()).stream()
                .map(text -> text.replace("{title}", title))
                .map(text -> text.replace('&', '§'))
                .collect(Collectors.toList());
        itemMeta.setLore(lore);
        exampleItem.setItemMeta(itemMeta);
        return exampleItem;
    }
    public Inventory getConfirmationMenu(Player player, String title) {
        Inventory inventory = Bukkit.createInventory(new TitleConfirmationMenuHolder(title), 54, "Подтверждение титула");
        ItemStack pinkPane = new ItemStack(Material.PINK_STAINED_GLASS_PANE, 1);
        ItemStack purplePane = new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE, 1);

        // clear pane names
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
        ItemStack grayDye =  new ItemStack(Material.GRAY_DYE, 1);

        ItemMeta woolMeta = limeWool.getItemMeta();
        woolMeta.setDisplayName(ChatColor.DARK_GRAY + "◀ " + ChatColor.GREEN + "Подтвердить выбор" + ChatColor.DARK_GRAY+  " ▶");
        woolMeta.setLore(Arrays.asList(ChatColor.GRAY + " ",
                ChatColor.GRAY + " После нажатия заявка на",
                ChatColor.GRAY + " создание титула будет отправлена, ",
                ChatColor.GRAY + " а жетон списан"));
        limeWool.setItemMeta(woolMeta);

        ItemMeta redWoolMeta = redWool.getItemMeta();
        redWoolMeta.setDisplayName(ChatColor.DARK_GRAY + "◀ " + ChatColor.RED + "Отменить создание" + ChatColor.DARK_GRAY+  " ▶");
        redWoolMeta.setLore(Arrays.asList(" ",
                ChatColor.GRAY + " После нажатия процесс создания",
                ChatColor.GRAY + " будет отменен, а жетон возвращен"));
        redWool.setItemMeta(redWoolMeta);

        ItemMeta grayDyeMeta = grayDye.getItemMeta();
        grayDyeMeta.setDisplayName(ChatColor.DARK_GRAY + "◀ " + ChatColor.GOLD + "Начать создание заново" + ChatColor.DARK_GRAY+  " ▶");
        grayDyeMeta.setLore(Arrays.asList(" ",
                ChatColor.GRAY + " После нажатия создание начнется",
                ChatColor.GRAY + " сначала. Жетон списан не будет"));
        grayDye.setItemMeta(grayDyeMeta);

        inventory.setItem(29, limeWool);
        inventory.setItem(33, redWool);
        inventory.setItem(40, grayDye);

        // set an example item
        inventory.setItem(mainConfigParser.getExampleItemSlot(), getExampleItem(player, title));
        return inventory;
    }
}
