package com.panita.panitacraft3.chat.commands.panitacraft.difficulty.set;

import com.panita.panitacraft3.difficulty.util.DifficultyConfig;
import com.panita.panitacraft3.util.CommandUtils;
import com.panita.panitacraft3.util.chat.Messenger;
import com.panita.panitacraft3.util.commands.dynamic.AdvancedCommand;
import com.panita.panitacraft3.util.commands.dynamic.TabSuggestingCommand;
import com.panita.panitacraft3.util.commands.identifiers.CommandMeta;
import com.panita.panitacraft3.util.commands.identifiers.SubCommandSpec;
import org.bukkit.command.CommandSender;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@SubCommandSpec(
        parent = "panitacraft difficulty set",
        name = "enabled",
        description = "Enables or disables the dynamic scaling difficulty system.",
        syntax = "/panitacraft difficulty set enabled <value>",
        permission = "panitacraft.admin.command.panitacraft.difficulty.set.enabled"
)
public class EnabledCommand implements AdvancedCommand, TabSuggestingCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!CommandUtils.checkArgsOrUsage(sender, args, 1, this.getClass())) return;

        String inputValue = args[0].toLowerCase();
        if (inputValue.equalsIgnoreCase("true") || inputValue.equalsIgnoreCase("false")) {
            boolean enabled = Boolean.parseBoolean(inputValue);
            DifficultyConfig.setEnabled(enabled);
            Messenger.prefixedSend(sender, "<green>Sistema de dificultad dinámica:</green> " +
                    (enabled ? "<green>Activado ✅</green>" : "<red>Desactivado ❌</red>"));
        } else {
            Messenger.prefixedSend(sender, "<red>Debes ingresar un valor booleano válido: true o false</red>");
        }
    }

    @Override
    public void applySuggestions(CommandMeta meta) {
        meta.setArgumentSuggestion(0, context -> {
            String current = context.getCurrentArg().toLowerCase();
            return Stream.of("true", "false")
                    .filter(s -> s.startsWith(current))
                    .collect(Collectors.toList());
        });
    }
}
