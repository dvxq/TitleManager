package ru.healthanmary.titlemanager.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.healthanmary.titlemanager.mysql.Storage;

public class PlayerJoinListener implements Listener {
    private Storage storage;

    public PlayerJoinListener(Storage storage) {
        this.storage = storage;
    }

    @EventHandler
    public void on(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        storage.getTitleByName(player.getName());
    }
}
