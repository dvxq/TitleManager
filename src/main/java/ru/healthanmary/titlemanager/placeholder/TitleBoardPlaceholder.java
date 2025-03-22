package ru.healthanmary.titlemanager.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.healthanmary.titlemanager.cache.PlayerTitleCache;
import ru.healthanmary.titlemanager.util.Title;

public class TitleBoardPlaceholder extends PlaceholderExpansion {
    private PlayerTitleCache playerCache;

    public TitleBoardPlaceholder(PlayerTitleCache playerCache) {
        this.playerCache = playerCache;
    }
    @Override
    public @NotNull String getIdentifier() {
        return "custom-player-title-board";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Author";
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
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) return "";
        Title title = playerCache.getTitle(player.getUniqueId());
        return title == null ? "Нету" : title.getTitle_text();
    }
}
