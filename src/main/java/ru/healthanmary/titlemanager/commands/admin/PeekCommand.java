package ru.healthanmary.titlemanager.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.healthanmary.titlemanager.menus.peek.PeekMenuBuilder;

public class PeekCommand implements SubCommand{
    private final PeekMenuBuilder peekMenuBuilder;

    public PeekCommand(PeekMenuBuilder peekMenuBuilder) {
        this.peekMenuBuilder = peekMenuBuilder;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c▶ §fКоманда недоступна из консоли");
            return;
        }
        if (args.length < 2) {
            sender.sendMessage("§c▶ §fУказано некорректное количество аргументов");
            return;
        }

        Player player = (Player) sender;
        player.openInventory(peekMenuBuilder.getPeekMenu(args[1], 1));
    }
}
