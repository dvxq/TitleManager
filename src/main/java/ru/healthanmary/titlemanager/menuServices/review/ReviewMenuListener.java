package ru.healthanmary.titlemanager.menuServices.review;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import ru.healthanmary.titlemanager.TitleManager;
import ru.healthanmary.titlemanager.mysql.Storage;
import ru.healthanmary.titlemanager.util.Title;
import ru.healthanmary.titlemanager.util.TitleUtil;

import java.util.List;

public class ReviewMenuListener implements Listener {
    private final Storage storage;
    private final ReviewMenuBuilder reviewMenuBuilder;

    public ReviewMenuListener(Storage storage, ReviewMenuBuilder reviewMenuBuilder) {
        this.storage = storage;
        this.reviewMenuBuilder = reviewMenuBuilder;
    }

    @EventHandler
    public void onSwitchPage(InventoryClickEvent e) {
        Inventory inventory = e.getInventory();
        if (inventory == null) return;
        if (inventory.getHolder() instanceof ReviewMenuHolder) {
            Player player = (Player) e.getWhoClicked();
            ReviewMenuHolder holder = (ReviewMenuHolder) inventory.getHolder();
            ItemStack currentItem = e.getCurrentItem();
            int currentPage = holder.getCurrentPage();
            if (e.getSlot() == 49) player.openInventory(reviewMenuBuilder.getReviewMenu(currentPage));
            if (currentItem == null || currentItem.getType() != Material.ARROW) return;
            int maxPage = holder.getMaxPage();
            int minPage = holder.getMinPage();

            switch (e.getSlot()) {
                case 52: {
                    if (currentPage > minPage) {
                        player.openInventory(reviewMenuBuilder.getReviewMenu(currentPage-1));
                    }
                    break;
                }
                case 53: {
                    if (currentPage < maxPage) {
                        player.openInventory(reviewMenuBuilder.getReviewMenu(currentPage+1));

                    }
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onTitleReview(InventoryClickEvent e) {
        Inventory inventory = e.getInventory();
        if (inventory == null) return;
        if (inventory.getHolder() instanceof ReviewMenuHolder) {
            Player player = (Player) e.getWhoClicked();
            ClickType click = e.getClick();
            if (!click.isShiftClick() && !(click == ClickType.CONTROL_DROP)) return;

            int slot = e.getSlot();
            int titleIndex = TitleUtil.getTitleIndex(slot);
            ReviewMenuHolder holder = (ReviewMenuHolder) inventory.getHolder();
            List<Title> titles = holder.getTitles();

            if (titles == null || titleIndex == -1 || titleIndex >= titles.size()) {
                return;
            }
            Title title = titles.get(titleIndex);
            int titleId = title.getId();
            switch (click) {
                case DROP: {
                    // display information
                    player.sendMessage(title.getTitleText());
                    break;
                }
                case SHIFT_LEFT: {
                    Bukkit.getScheduler().runTaskAsynchronously(TitleManager.instance, () -> {
                        if (storage.getTitleState(titleId) == Title.State.UNDER_REVIEW) {
                            storage.reviewTitle(titleId, player.getName(), null, true);
                            player.sendMessage("§d▶ §fВы успешно приняли титул " + title.getTitleText() + ". §bID: " + title.getId());
                            reloadPage((ReviewMenuHolder) reviewMenuBuilder.getReviewMenu(1).getHolder(), player, holder.getCurrentPage());
                        }
                        else {
                            reloadPage((ReviewMenuHolder) reviewMenuBuilder.getReviewMenu(1).getHolder(), player, holder.getCurrentPage());
                        }
                    });
                    break;
                }
                case SHIFT_RIGHT: {

                    break;
                }
            }
        }
    }
    private void reloadPage(ReviewMenuHolder holder, Player player, int currentPage) {
        Bukkit.getScheduler().runTask(TitleManager.instance, () -> {
            if (currentPage <= holder.getMaxPage()) {
                player.openInventory(reviewMenuBuilder.getReviewMenu(currentPage));
            } else {
                player.openInventory(reviewMenuBuilder.getReviewMenu(holder.getMaxPage()));
            }
        });
    }
}
