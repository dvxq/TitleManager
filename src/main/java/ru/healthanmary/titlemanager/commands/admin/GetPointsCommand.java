package ru.healthanmary.titlemanager.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import ru.healthanmary.titlemanager.TitleManager;
import ru.healthanmary.titlemanager.mysql.Storage;

public class GetPointsCommand implements SubCommand{
    private final Storage storage;

    public GetPointsCommand(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("§c▶ §fУказано некорректное количество аргументов");
            return;
        }

        String targetName = args[1];
        Bukkit.getScheduler().runTaskAsynchronously(TitleManager.instance, () -> {
            String playerPoints = storage.getPlayerPoints(targetName);
            sender.sendMessage("§d▶ §fИгрок §a" + targetName + " §fимеет §e" + playerPoints + " жетонов");
        });
    }
}
