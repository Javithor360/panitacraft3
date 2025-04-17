package com.panita.panitacraft3.chat.commands.panitacraft;

import com.panita.panitacraft3.util.Global;
import com.panita.panitacraft3.util.chat.Messenger;
import com.panita.panitacraft3.util.commands.dynamic.AdvancedCommand;
import com.panita.panitacraft3.util.commands.dynamic.TabSuggestingCommand;
import com.panita.panitacraft3.util.commands.identifiers.CommandMeta;
import com.panita.panitacraft3.util.commands.identifiers.SubCommandSpec;
import org.bukkit.command.CommandSender;

import java.util.Map;
import java.util.stream.Collectors;

@SubCommandSpec(
        parent = "panitacraft",
        name = "help",
        description = "Displays help information for Panitacraft commands.",
        syntax = "/panitacraft help [command]"
)
public class HelpCommand implements AdvancedCommand, TabSuggestingCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            // If no command name is provided, show help for all commands
            Messenger.prefixedSend(sender, "<gold>Comandos disponibles:</gold>");
            for (Map.Entry<String, CommandMeta> entry : Global.ROOT_COMMANDS.entrySet()) {
                String rootName = entry.getKey();
                CommandMeta meta = entry.getValue();
                showCommandHelp(sender, "/" + rootName, meta, "  ");
            }
        } else {
            // If a command name is provided, show help for that specific command
            String rootName = args[0].toLowerCase();
            CommandMeta meta = Global.ROOT_COMMANDS.get(rootName);

            if (meta == null) {
                Messenger.prefixedSend(sender, "<red>Comando no encontrado:</red> <gray>" + args[0] + "</gray>");
                return;
            }

            Messenger.prefixedSend(sender, "<gold>Información sobre el comando /" + rootName + ":</gold>");
            showCommandHelp(sender, "/" + rootName, meta, "  ");
        }
    }

    @Override
    public void applySuggestions(CommandMeta meta) {
        meta.setArgumentSuggestion(0, context -> {
            String current = context.getCurrentArg().toLowerCase();

            return Global.ROOT_COMMANDS.keySet().stream()
                    .filter(name -> name.startsWith(current))
                    .collect(Collectors.toList());
        });
    }

        private void showCommandHelp(CommandSender sender, String path, CommandMeta meta, String indent) {
        if (meta.getCommand() != null) {
            String syntax = meta.getSyntax() != null && !meta.getSyntax().isEmpty() ? meta.getSyntax() : path;
            String desc = meta.getDescription() != null ? meta.getDescription() : "(sin descripción)";
            Messenger.send(sender, indent + "<aqua>" + syntax + "</aqua> <gray>-</gray> <white>" + desc + "</white>");
        }

        for (Map.Entry<String, CommandMeta> sub : meta.getSubCommands().entrySet()) {
            String subPath = path + " " + sub.getKey();
            showCommandHelp(sender, subPath, sub.getValue(), indent + "  ");
        }
    }
}
