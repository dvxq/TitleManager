package ru.healthanmary.titlemanager.cache;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.healthanmary.titlemanager.TitleManager;
import ru.healthanmary.titlemanager.mysql.Storage;
import ru.healthanmary.titlemanager.util.Title;

import java.util.HashMap;
import java.util.UUID;

public class TitleCacheManager implements Listener {
    private final Storage storage;
    private final HashMap<UUID, Title> currentPlayersTitles = new HashMap<>();

    public TitleCacheManager(Storage storage) {
        this.storage = storage;
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

    // caches the title
    public void processPlayer(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(TitleManager.instance, () -> {
            UUID uuid = player.getUniqueId();
            Title title = storage.getCurrentTitleByName(player.getName());
            if (title == null) {
                putTitle(uuid, null);
                return;
            }
            if (title.getState() == Title.State.ACCEPTED) {
                putTitle(uuid, title);
            }else {
                storage.setCurrentTitle(player.getName(), null);
                putTitle(uuid, null);
            }
        });
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        processPlayer(e.getPlayer());
    }

    // handles quiting and caches the title
    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        Title title = getTitle(uuid);
        Bukkit.getScheduler().runTaskAsynchronously(TitleManager.instance, () -> {
            if (title != null) {
                if (title.getState() == Title.State.ACCEPTED) {
                    putTitle(player.getUniqueId(), title);
                }else {
                    storage.setCurrentTitle(player.getName(), null);
                    putTitle(player.getUniqueId(), null);
                }
            } else {
                storage.setCurrentTitle(player.getName(), null);
            }
        });
        clearValue(uuid);
    }
}
