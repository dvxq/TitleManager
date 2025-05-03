package ru.healthanmary.titlemanager.util;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.healthanmary.titlemanager.TitleManager;
import ru.healthanmary.titlemanager.menuServices.titleConfirmation.TitleConfirmationMenuHolder;
import ru.healthanmary.titlemanager.menuServices.titleConfirmation.TitleConfirmationMenuBuilder;

import java.util.HashMap;
import java.util.Map;

public class CreatingMenuService implements Listener {
    private Map<Player, Integer> pendingPlayers = new HashMap<>();
    private final TitleConfirmationMenuBuilder titleConfirmationMenuBuilder;

    public CreatingMenuService(TitleConfirmationMenuBuilder titleConfirmationMenuBuilder) {
        this.titleConfirmationMenuBuilder = titleConfirmationMenuBuilder;
    }

    public void addPendingPlayer(Player player) {
        if (!pendingPlayers.containsKey(player) || (pendingPlayers.get(player) == null)) {
            int taskId = Bukkit.getScheduler().runTaskTimer(TitleManager.instance, () -> {
                player.sendMessage(" ");
                player.sendMessage(ChatColor.AQUA + "▶ " + ChatColor.WHITE + "Впишите в чат желаемый титул");
                player.sendMessage(" ");
            }, 0L, 20*5L).getTaskId();
            pendingPlayers.put(player, taskId);
        }
    }
    public void restartCreating(Player player) {
        pendingPlayers.remove(player);
        player.closeInventory();
        int taskId = Bukkit.getScheduler().runTaskTimer(TitleManager.instance, () -> {
            player.sendMessage(" ");
            player.sendMessage(ChatColor.AQUA + "▶ " + ChatColor.WHITE + "Впишите в чат желаемый титул");
            player.sendMessage(" ");
        }, 0L, 20*5L).getTaskId();
        pendingPlayers.put(player, taskId);
    }
    public void removePendingPlayer(Player player) {
        Integer taskId = pendingPlayers.get(player);
        if (taskId != null) {
            Bukkit.getScheduler().cancelTask(taskId);
            pendingPlayers.remove(player);
        } else if (pendingPlayers.containsKey(player) && taskId == null) {
            pendingPlayers.remove(player);
        }
    }
    public void stopTimer(Player player) {
        Integer taskId = pendingPlayers.get(player);
        if (taskId != null) {
            Bukkit.getScheduler().cancelTask(taskId);
            pendingPlayers.put(player, null);
        }
    }
    public boolean isPending(Player player) {
        return pendingPlayers.containsKey(player);
    }

    // remove pending
    @EventHandler
    public void onPlayerQuitEvent1(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if (isPending(player)) {
            removePendingPlayer(player);
        }
    }

    // if pending player closes menu
    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        if (isPending(player) && e.getInventory().getHolder() instanceof TitleConfirmationMenuHolder) {
            removePendingPlayer(player);
            player.sendMessage(ChatColor.RED + "▶ " + ChatColor.WHITE + "Вы прекратили создание титула");
        }
    }

    // open review menu after entering the title
    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        if (isPending(player)) {
            e.setCancelled(true);
            Bukkit.getScheduler().runTask(TitleManager.instance, () -> {
                player.openInventory(titleConfirmationMenuBuilder.getConfirmationMenu(player, e.getMessage()));
            });
            stopTimer(player);
        }
    }
}
