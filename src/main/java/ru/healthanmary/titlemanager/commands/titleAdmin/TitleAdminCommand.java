package ru.healthanmary.titlemanager.commands.titleAdmin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.healthanmary.titlemanager.menuServices.peek.PeekMenuBuilder;
import ru.healthanmary.titlemanager.menuServices.review.ReviewMenuBuilder;
import ru.healthanmary.titlemanager.mysql.Storage;

import java.util.HashMap;
import java.util.Map;

public class TitleAdminCommand implements CommandExecutor {
    private final Map<String, SubCommand> subCommands = new HashMap<>();
    public TitleAdminCommand(Storage storage, PeekMenuBuilder peekMenuBuilder, ReviewMenuBuilder reviewMenuBuilder) {
        subCommands.put("take", new TakePointsCommand(storage));
        subCommands.put("reset", new ResetPointsCommand(storage));
        subCommands.put("get", new GetPointsCommand(storage));
        subCommands.put("set", new SetPointsCommand(storage));
        subCommands.put("give", new GivePointsCommand(storage));
        subCommands.put("lookup", new LookupCommand(storage));
        subCommands.put("change", new ChangeCommand(storage));
        subCommands.put("review", new ReviewCommand(reviewMenuBuilder));
        subCommands.put("peek", new PeekCommand(peekMenuBuilder));
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0 || !subCommands.containsKey(args[0])) {
            sender.sendMessage("§c▶ §fНавигация по функционалу");
            sender.sendMessage("§d> §eАлиасы: /ta");
            sender.sendMessage("Команды:");
            sender.sendMessage("§d> §e/ta change <ID> <Статус> §f- Сменить статус титула");
            sender.sendMessage("§d> §e/ta get <Ник> §f- Просмотр баланса жетонов игрока");
            sender.sendMessage("§d> §e/ta give <Ник> <Кол-во> §f- Выдать жетоны игроку");
            sender.sendMessage("§d> §e/ta lookup <ID> §f- Получить данные о титуле");
            sender.sendMessage("§d> §e/ta peek <Ник> §f- Просмотр доступных игроку титулов");
            sender.sendMessage("§d> §e/ta reset <Ник> §f- Сброить до 0 жетоны игроку");
            sender.sendMessage("§d> §e/ta review §f- Меню рассмотрения титулов");
            sender.sendMessage("§d> §e/ta set <Ник> <Кол-во> §f- Установить жетоны игроку");
            sender.sendMessage("§d> §e/ta take <Ник> <Кол-во> §f- Забрать жетоны");
            return true;
        }

        subCommands.get(args[0]).execute(sender, args);
        return true;
    }
}
