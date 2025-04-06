package ru.healthanmary.titlemanager.util;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CreatingMenuManager {
    private List<Player> pendingPlayers = new ArrayList<>();
    public void addPendingPlayer(Player player) {
        pendingPlayers.add(player);
    }
    public void removePendingPlayer(Player player) {
        pendingPlayers.remove(player);
    }
    public boolean containsPlayer(Player player) {
        return pendingPlayers.contains(player);
    }
}
