package ru.healthanmary.titlemanager.cache;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ru.healthanmary.titlemanager.TitleManager;
import ru.healthanmary.titlemanager.mysql.Storage;
import ru.healthanmary.titlemanager.util.Title;

import java.util.HashMap;

public class RejectCacheManager implements Listener {
    private final HashMap<Player, Title> pending = new HashMap<>();
    private final Storage storage;

    public RejectCacheManager(Storage storage) {
        this.storage = storage;
    }

    public void add(Player player, Title title) {
        pending.put(player, title);
    }
    public void remove(Player player) {
        pending.remove(player);
    }
    public boolean isPending(Player player) {
        return pending.containsKey(player);
    }
    @EventHandler
    public void onAsyncChatEvent(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        if (!isPending(player)) return;
        e.setCancelled(true);

        Title title = pending.get(player);
        if (title == null) return;

        // already async
        int id = title.getId();
        if (storage.getTitleState(id) != Title.State.UNDER_REVIEW) return;
        String comment = e.getMessage().equals("-") ? "Нету" : e.getMessage();
        storage.reviewTitle(id, player.getName(), comment, false);
        player.sendMessage("§d▶ §fВы успешно §cотклонили §fтитул " + title.getTitleText() + ". §bID: " + id);
        remove(player);
        Bukkit.getScheduler().runTask(TitleManager.instance, () -> {
            player.performCommand("ta review");
        });
    }
}
