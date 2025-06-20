package ru.healthanmary.titlemanager.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import ru.healthanmary.titlemanager.TitleManager;
import ru.healthanmary.titlemanager.mysql.Storage;
import ru.healthanmary.titlemanager.util.Title;

public class ChangeCommand implements SubCommand{
    private final Storage storage;
    private final TitleManager titleManager;
    public ChangeCommand(Storage storage, TitleManager titleManager) {
        this.storage = storage;
        this.titleManager = titleManager;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage("§c▶ §fУказано некорректное количество аргументов");
            return;
        }

        try {
            Integer titleId = Integer.parseInt(args[1]);
            Title.State state = Title.State.valueOf(args[2]);
            Bukkit.getScheduler().runTaskAsynchronously(titleManager, () -> {
                if (storage.getTitleById(titleId) == null) {
                    sender.sendMessage("§d▶ §fТитул с §bID: " + titleId + " §fне найден");
                    return;
                }

                storage.changeTitleState(state, titleId);
                sender.sendMessage("§d▶ §fТитулу с §bID = " + titleId + " §fбыл сменен статус на §e" + state);
            });
        } catch (NumberFormatException e) {
            sender.sendMessage("§c▶ §fНекорректно указан ID");
        } catch (IllegalArgumentException e) {
            sender.sendMessage("§c▶ §fНекорректно указан статус");
        }
    }
}
