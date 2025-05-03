package ru.healthanmary.titlemanager.commands.titleAdmin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import ru.healthanmary.titlemanager.TitleManager;
import ru.healthanmary.titlemanager.mysql.Storage;
import ru.healthanmary.titlemanager.util.Title;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class LookupCommand implements SubCommand{
    private final Storage storage;

    public LookupCommand(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("§c▶ §fУказано некорректное количество аргументов");
            return;
        }
        try {
            Integer titleId = Integer.parseInt(args[1]);
            Bukkit.getScheduler().runTaskAsynchronously(TitleManager.instance, () -> {
                Title title = storage.getTitleById(titleId);

                if (title == null) {
                    sender.sendMessage("§d▶ §fТитул с §bID: " + titleId + " §fне найден");
                    return;
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
                Timestamp requestDate = title.getRequestDate();
                Timestamp acceptDate = title.getReviewtDate();
                String acceptedAdmin = (title.getReviewAdmin() != null)
                        ? title.getReviewAdmin() : "§7Нету";

                String comment = (title.getAdminComment() != null)
                        ? title.getAdminComment() : "§7Нету";

                String formattedRequestDate = (requestDate != null)
                        ? requestDate.toLocalDateTime().format(formatter)
                        : "§7Нету";

                String formattedAcceptDate = (acceptDate != null)
                        ? acceptDate.toLocalDateTime().format(formatter)
                        : "§7Нету";

                sender.sendMessage(" ");
                sender.sendMessage("§d▶ §fДанные о титуле §b(ID: " + titleId + ")");
                sender.sendMessage(" ");
                sender.sendMessage("§7- §fВладелец: §a" + title.getPlayerName());
                sender.sendMessage("§7- §fТитул: §r" + title.getTitleText());
                sender.sendMessage("§7- §fДата заявки: §a" + formattedRequestDate);
                sender.sendMessage("§7- §fДата рассмотрения: §a" + formattedAcceptDate);
                sender.sendMessage("§7- §fАдминистратор: §a" + acceptedAdmin);
                sender.sendMessage("§7- §fКомментарий: §7" + comment);
                sender.sendMessage("§7- §fСтатус: §3" + title.getState());
            });
        } catch (NumberFormatException e) {
            sender.sendMessage("§c▶ §fУказан некорректный ID");
        }
    }
}
