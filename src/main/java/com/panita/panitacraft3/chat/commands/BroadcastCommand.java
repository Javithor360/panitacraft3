package com.panita.panitacraft3.chat.commands;

import com.panita.panitacraft3.util.chat.Messenger;
import com.panita.panitacraft3.util.commands.dynamic.AdvancedCommand;
import com.panita.panitacraft3.util.commands.dynamic.TabSuggestingCommand;
import com.panita.panitacraft3.util.commands.identifiers.CommandMeta;
import com.panita.panitacraft3.util.commands.identifiers.CommandSpec;
import org.bukkit.command.CommandSender;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@CommandSpec(
        name = "broadcast",
        description = "Broadcast a message to all players",
        syntax = "/broadcast <message>",
        permission = "panitacraft.admin.command.broadcast",
        aliases = {"bc"}
)
public class BroadcastCommand implements AdvancedCommand, TabSuggestingCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            Messenger.prefixedSend(sender, "&cUso: &4/broadcast <mensaje>"); // Corrected spelling
            return;
        }

        boolean usePrefix = true; // Default to prefixed
        String message; // Default message

        // Check if the first argument is "prefixed" or "raw"
        if (args[0].equalsIgnoreCase("prefixed") || args[0].equalsIgnoreCase("raw")) {
            usePrefix = args[0].equalsIgnoreCase("prefixed"); // Determine if we use prefix

            // If "prefixed" or "raw" is used, we need to check if there's a message
            if (args.length < 2) {
                Messenger.prefixedSend(sender, "&cDebes especificar el mensaje que quieres difundir.");
                return;
            }

            message = String.join(" ", Stream.of(args).skip(1).toArray(String[]::new));
        } else {
            // If no prefix or raw is specified, treat the first argument as part of the message
            message = String.join(" ", args);
        }

        // Send the message to all players
        if (usePrefix) {
            Messenger.prefixedBroadcast(message);
        } else {
            // If "raw" is specified, send the message without prefix
            Messenger.broadcast(message);
        }
    }

    @Override
    public void applySuggestions(CommandMeta meta) {
        meta.setArgumentSuggestion(0, context -> {
            String current = context.getCurrentArg().toLowerCase();
            return Stream.of("prefixed", "raw") // Static suggestions
                    .filter(name -> name.toLowerCase().startsWith(current))
                    .collect(Collectors.toList());
        });
    }
}
