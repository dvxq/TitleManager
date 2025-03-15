package ru.healthanmary.titlemanager.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.healthanmary.titlemanager.mysql.Storage;
import ru.healthanmary.titlemanager.util.Title;

public class t1cmd implements CommandExecutor {
    private Storage storage;

    public t1cmd(Storage storage) {
        this.storage = storage;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        int id = Integer.parseInt(args[0]);
        Title title = storage.getTitleById(id);
        if (title == null) {
            sender.sendMessage(ChatColor.RED + "Титула с таким ID не сущетсвует");
            return true;
        }
        sender.sendMessage("Информация по титулу: " + ChatColor.GOLD + "№" + id);
        sender.sendMessage("Титул: " + title.getTitle_text());
        sender.sendMessage("Ник владельца: " + title.getPlayer_name());
        sender.sendMessage("Дата запроса: " + title.getRequest_date());
        sender.sendMessage("Дата рассмотрения: " + title.getAccept_date());
        sender.sendMessage("Рассмотрен админом: " + title.getAccepted_admin());
        sender.sendMessage("Комментарий админа: " + title.getAdmin_comment());
        sender.sendMessage("Рассмотрен ли: " + title.is_accepted());
        return true;
    }
}
