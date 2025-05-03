package ru.healthanmary.titlemanager.commands.titleAdmin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.healthanmary.titlemanager.menuServices.review.ReviewMenuBuilder;

public class ReviewCommand implements SubCommand{
    private final ReviewMenuBuilder reviewMenuBuilder;

    public ReviewCommand(ReviewMenuBuilder reviewMenuBuilder) {
        this.reviewMenuBuilder = reviewMenuBuilder;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c▶ §fКоманда недоступна из консоли");
            return;
        }

        Player player = (Player) sender;
        player.openInventory(reviewMenuBuilder.getReviewMenu(1));
    }
}