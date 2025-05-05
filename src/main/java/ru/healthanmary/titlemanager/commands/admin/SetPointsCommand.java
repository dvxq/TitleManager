package ru.healthanmary.titlemanager.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import ru.healthanmary.titlemanager.TitleManager;
import ru.healthanmary.titlemanager.mysql.Storage;

public class SetPointsCommand implements SubCommand{
    private final Storage storage;

    public SetPointsCommand(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage("§c▶ §fУказано некорректное количество аргументов");
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(TitleManager.instance, () -> {
            try {
                String targetName = args[1];
                Integer points = Integer.parseInt(args[2]);
                storage.setPlayerPoints(targetName, points);
                sender.sendMessage("§d▶ §fБаланс игроку §a" + targetName + " §fбыл установлен: §e" + points + " жетонов");
            } catch (NumberFormatException e) {
                sender.sendMessage("§d▶ §fНекорректно указано количество");
            }
        });
    }
}
