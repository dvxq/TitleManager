package ru.healthanmary.titlemanager.menuServices.peek;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PeekMenuListener implements Listener {
    private final PeekMenuBuilder peekMenuBuilder;

    public PeekMenuListener(PeekMenuBuilder peekMenuBuilder) {
        this.peekMenuBuilder = peekMenuBuilder;
    }

    @EventHandler
    public void onSwitchPage(InventoryClickEvent e) {
        Inventory inventory = e.getInventory();
        if (inventory == null) return;
        if (inventory.getHolder() instanceof PeekMenuHolder) {
            Player player = (Player) e.getWhoClicked();
            PeekMenuHolder holder = (PeekMenuHolder) inventory.getHolder();
            ItemStack currentItem = e.getCurrentItem();
            if (currentItem == null || currentItem.getType() != Material.ARROW) return;
            int maxPage = holder.getMaxPage();
            int minPage = holder.getMinPage();
            int currentPage = holder.getCurrentPage();

            switch (e.getSlot()) {
                case 52: {
                    if (currentPage > minPage) {
                        player.openInventory(peekMenuBuilder.getPeekMenu(holder.getPlayer(), currentPage-1));
                    }
                    break;
                }
                case 53: {
                    if (currentPage < maxPage) {
                        player.openInventory(peekMenuBuilder.getPeekMenu(player.getName(), currentPage+1));

                    }
                    break;
                }
            }
        }
    }
}
