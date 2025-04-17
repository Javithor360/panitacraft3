package com.panita.panitacraft3.chat.commands;

import com.panita.panitacraft3.util.commands.dynamic.AdvancedCommand;
import com.panita.panitacraft3.util.commands.identifiers.CommandSpec;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandSpec(
        name = "sharecoordinates",
        description = "Share your coordinates with other players.",
        syntax = "/sharecoordinates",
        aliases = {"c", "coords"},
        playerOnly = true
)
public class ShareCoordinates implements AdvancedCommand {

    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command can only be used by players.");
            return;
        }

        Component msg = miniMessage.deserialize("<green>Coordinates: <yellow>" + player.getLocation().getBlockX() + " " + player.getLocation().getBlockY() + " " + player.getLocation().getBlockZ());
        player.getWorld().getPlayers().forEach(p -> p.sendMessage(msg));
    }
}
