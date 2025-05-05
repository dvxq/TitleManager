package ru.healthanmary.titlemanager.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import ru.healthanmary.titlemanager.TitleManager;
import ru.healthanmary.titlemanager.mysql.Storage;

public class TakePointsCommand implements SubCommand{
    private final Storage storage;

    public TakePointsCommand(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage("§c▶ §fУказано некорректное количество аргументов");
            return;
        }
        String targetName = args[1];
        Bukkit.getScheduler().runTaskAsynchronously(TitleManager.instance, () -> {
            try {
                Integer points = Integer.parseInt(args[2]);
                storage.takePlayerPoints(targetName, points);
                sender.sendMessage("§d▶ §fУ игрока §a" + targetName + " §fбыло снято §e" + points + " жетонов.");
            } catch (NumberFormatException e) {
                sender.sendMessage("§d▶ §fНекорректно указано количество");
            }
        });
    }
}
