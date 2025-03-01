package ru.healthanmary.titlemanager.util;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CustomTitlePlaceholder extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "custom-player-title";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Null";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) return "";
        // get from db
//        String suffix = suffixCommand.getSuffixMap().get(player);
//        return suffix == null ? "" : " " + suffix;
        return "";
    }
}
