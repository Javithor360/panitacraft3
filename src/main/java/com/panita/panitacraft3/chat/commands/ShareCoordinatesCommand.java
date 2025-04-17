package com.panita.panitacraft3.chat.commands;

import com.panita.panitacraft3.util.chat.Messenger;
import com.panita.panitacraft3.util.commands.dynamic.AdvancedCommand;
import com.panita.panitacraft3.util.commands.dynamic.TabSuggestingCommand;
import com.panita.panitacraft3.util.commands.identifiers.CommandMeta;
import com.panita.panitacraft3.util.commands.identifiers.CommandSpec;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.stream.Collectors;

@CommandSpec(
        name = "sharecoordinates",
        description = "Share your coordinates with other players.",
        syntax = "/sharecoordinates [player]",
        aliases = {"c", "coords"},
        playerOnly = true
)
public class ShareCoordinatesCommand implements AdvancedCommand, TabSuggestingCommand {

    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            Messenger.consoleSend(sender, "This command can only be executed by a player.");
            return;
        }

        int x = player.getLocation().getBlockX();
        int y = player.getLocation().getBlockY();
        int z = player.getLocation().getBlockZ();
        World.Environment env = player.getWorld().getEnvironment();

        String dim = switch (env) {
            case NORMAL -> "<green>(Overworld)</green>";
            case NETHER -> "<red>(Nether)</red>";
            case THE_END -> "<light_purple>(End)</light_purple>";
            default -> "<gray>Desconocida</gray>";
        };

        String message = "<aqua>" + player.getName() +
                "</aqua> <gray>ha compartido sus coordenadas" +
                (args.length > 0 ? " contigo" : " con todos") +
                ":</gray> " + "<aqua>X: " + x + " Y: " + y + " Z: " + z +
                "</aqua> " + dim;

        if (args.length > 0) {
            Player target = Bukkit.getPlayer(args[0]);

            if (target == null || !target.isOnline()) {
                Messenger.prefixedSend(sender, "<red>El jugador <gray>" + args[0] + "</gray> no está conectado.</red>");
                return;
            } else if (target == player) {
                Messenger.prefixedSend(sender, "<gray>Tus coordenadas son:</gray> <aqua>X: " + x + " Y: " + y + " Z: " + z + "</aqua> " + dim);
                return;
            }

            Messenger.prefixedSend(target, message); // Send it to target
            Messenger.prefixedSend(sender, "<gray>Has compartido tus coordenadas con <aqua>" + target.getName() + "</aqua>.</gray>"); // Send to sender
            return;
        }

        Messenger.prefixedBroadcast(message);
    }

    @Override
    public void applySuggestions(CommandMeta meta) {
        meta.setArgumentSuggestion(0, context -> {
            String current = context.getCurrentArg().toLowerCase();
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> name.toLowerCase().startsWith(current))
                    .collect(Collectors.toList());
        });
    }
}
