package ru.healthanmary.titlemanager.menus.titleConfirmation;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import ru.healthanmary.titlemanager.TitleManager;
import ru.healthanmary.titlemanager.mysql.Storage;
import ru.healthanmary.titlemanager.util.CreationMenuManager;

public class ConfirmationManager implements Listener {
    private final CreationMenuManager creationMenuManager;
    private final Storage storage;

    public ConfirmationManager(CreationMenuManager creationMenuManager, Storage storage) {
        this.creationMenuManager = creationMenuManager;
        this.storage = storage;
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        Inventory inventory = e.getInventory();
        if (inventory == null || !(inventory.getHolder() instanceof TitleConfirmationMenuHolder)) return;

        Player player = (Player) e.getWhoClicked();
        switch (e.getSlot()) {
            // send to db
            case 29: {
                creationMenuManager.removePendingPlayer(player);
                player.closeInventory();
                Bukkit.getScheduler().runTaskAsynchronously(TitleManager.instance, () -> {
                    Integer playerPoints = Integer.valueOf(storage.getPlayerPoints(player.getName()));
                    if (playerPoints <= 0) {
                        Bukkit.getScheduler().runTask(TitleManager.instance, () -> {
                            player.sendMessage(ChatColor.RED + "▶ " + ChatColor.WHITE + "У вас недостаточно жетонов");
                        });
                    } else {
                        TitleConfirmationMenuHolder holder = (TitleConfirmationMenuHolder) inventory.getHolder();
                        storage.takePlayerPoints(player.getName(), 1);
                        storage.sendTitleToReview(player.getName(), holder.getTitle());
                        player.sendMessage(ChatColor.GREEN + "▶ " + ChatColor.WHITE + "Ваш титул был отправлен на рассмотрение");
                    }
                });
                break;
            }
            // reject
            case 33: {
                creationMenuManager.removePendingPlayer(player);
                player.closeInventory();
                player.sendMessage(ChatColor.RED + "▶ " + ChatColor.WHITE + "Вы отменили создание титула");
                break;
            }
            // restart the process
            case 40: {
                creationMenuManager.restartCreating(player);
                break;
            }
        }
    }
}
