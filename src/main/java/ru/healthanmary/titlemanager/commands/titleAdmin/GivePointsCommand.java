package ru.healthanmary.titlemanager.commands.titleAdmin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import ru.healthanmary.titlemanager.TitleManager;
import ru.healthanmary.titlemanager.mysql.Storage;

public class GivePointsCommand implements SubCommand{
    private final Storage storage;

    public GivePointsCommand(Storage storage) {
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
                storage.givePlayerPoints(targetName, points);
                sender.sendMessage("§d▶ §fИгроку §a" + targetName + " §fбыло добавлено §e" + points + " жетонов.");
            } catch (NumberFormatException e) {
                sender.sendMessage("§d▶ §fНекорректно указано количество");
            }
        });
    }
}
