package ru.healthanmary.titlemanager.commands.titleAdmin;

import org.bukkit.Bukkit;
import org.bukkit.command.BufferedCommandSender;
import org.bukkit.command.CommandSender;
import ru.healthanmary.titlemanager.TitleManager;
import ru.healthanmary.titlemanager.mysql.Storage;

public class ResetPointsCommand implements SubCommand{
    private final Storage storage;

    public ResetPointsCommand(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("§c▶ §fУказано некорректное количество аргументов");
            return;
        }
        String targetName = args[1];
        Bukkit.getScheduler().runTaskAsynchronously(TitleManager.instance, () -> {
            storage.resetPlayerPoints(targetName);
            sender.sendMessage("§d▶ §fУ игрока §a" + targetName + " §fбыли обнулены жетоны");
        });
    }
}
