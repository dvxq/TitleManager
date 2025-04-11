package ru.healthanmary.titlemanager.config;

import org.bukkit.configuration.ConfigurationSection;
import ru.healthanmary.titlemanager.TitleManager;

public class ItemsSectionParser implements SectionParser {
    @Override
    public void parse(ConfigurationSection section) {
        if (section != null) {
            Config.ItemsData.limeWoolLore = section.getStringList("lime-wool-lore");
            Config.ItemsData.redWoolLore = section.getStringList("red-wool-lore");
        } else {
            TitleManager.instance.getLogger().warning("Items configuration section is null so values were set by default");
        }
    }
}
