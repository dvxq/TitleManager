package ru.healthanmary.titlemanager.cache;

import lombok.Getter;
import org.bukkit.Bukkit;
import ru.healthanmary.titlemanager.TitleManager;
import ru.healthanmary.titlemanager.util.Title;

import java.util.HashMap;
import java.util.UUID;

public class PlayerTitleCache {
    private HashMap<UUID, Title> currentPlayersTitles = new HashMap<>();
    public void switchDebugMode() {
        if (isDebugEnabled) isDebugEnabled = false;
        else isDebugEnabled = true;
    }
    @Getter
    private boolean isDebugEnabled = false;
    public PlayerTitleCache() {
        Bukkit.getScheduler().runTaskTimer(TitleManager.instance, () -> {
            if (isDebugEnabled) System.out.println(currentPlayersTitles.toString());
        }, 0L, 100L);
    }
    public void putTitle(UUID uuid, Title title) {
        currentPlayersTitles.put(uuid, title);
    }
    public void clearValue(UUID uuid) {
        currentPlayersTitles.remove(uuid);
    }
    public Title getTitle(UUID uuid) {
        return currentPlayersTitles.get(uuid);
    }
}
