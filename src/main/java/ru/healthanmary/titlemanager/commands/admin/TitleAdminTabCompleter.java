package ru.healthanmary.titlemanager.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TitleAdminTabCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return Arrays.asList("give", "get", "set", "reset", "take", "peek", "review", "change", "lookup");
        } else if (args.length == 2) { // Ensure args[1] exists
            if (Arrays.asList("give", "get", "set", "reset", "take", "peek").contains(args[0])) {
                List<String> onlinePlayers = Bukkit.getOnlinePlayers().stream()
                        .map(Player::getName)
                        .collect(Collectors.toList());
                return onlinePlayers;
            } else if (Arrays.asList("change", "lookup").contains(args[0])) {
                return Collections.singletonList("TITLE_ID");
            }
        } else if (args.length == 3 && args[0].equalsIgnoreCase("change")) {
            return Arrays.asList("ACCEPTED", "UNDER_REVIEW", "REJECTED");
        }
        return Collections.emptyList();
    }
}
