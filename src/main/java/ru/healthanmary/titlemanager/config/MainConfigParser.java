package ru.healthanmary.titlemanager.config;

import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import ru.healthanmary.titlemanager.TitleManager;

@Getter
public class MainConfigParser extends AbstractConfig{
    public MainConfigParser(JavaPlugin plugin, String fileName) {
        super(plugin, fileName);
    }
    private ItemStack exampleItem;
    private Integer exampleItemSlot;

    @Override
    void parse() {
        ConfigurationSection section = getConfig().getConfigurationSection("example-item");
        if (section == null) {
            throw new IllegalStateException("Failed to get mysql section");
        }

        try {
            exampleItem = new ItemStack(Material.NAME_TAG, 1);
            ItemMeta itemMeta = exampleItem.getItemMeta();
            itemMeta.setLore(section.getStringList("lore"));
            itemMeta.setDisplayName(section.getString("display-name"));
            exampleItem.setItemMeta(itemMeta);
        } catch (Exception e) {
            TitleManager.instance.getLogger().warning("You wrote something invalid there: config.yml -> example-item:");
            e.printStackTrace();
        }
        exampleItemSlot = section.getInt("slot");
    }
}
