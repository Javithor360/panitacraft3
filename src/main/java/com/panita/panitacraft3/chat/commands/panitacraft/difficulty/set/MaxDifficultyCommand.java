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
        name = "max_difficulty",
        description = "Sets the maximum difficulty scale.",
        syntax = "/panitacraft difficulty set max_difficulty <value>",
        permission = "panitacraft.admin.command.panitacraft.difficulty.set.max_difficulty"
)
public class MaxDifficultyCommand implements AdvancedCommand, TabSuggestingCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!CommandUtils.checkArgsOrUsage(sender, args, 1, this.getClass())) return;

        String inputValue = args[0].toLowerCase();
        try {
            double value = Double.parseDouble(inputValue);
            if (value < 1.0 || value > 1000.0) {
                Messenger.prefixedSend(sender, "<red>La dificultad máxima debe estar entre 1.0 y 1000.0</red>");
                return;
            }
            DifficultyConfig.setMaxDifficultyScale(value);
            Messenger.prefixedSend(sender, "<green>Dificultad máxima establecida en:</green> <gold>" + value + "</gold>");
        } catch (NumberFormatException e) {
            Messenger.prefixedSend(sender, "<red>Debes ingresar un número válido (ej: 250.0)</red>");
        }
    }

    @Override
    public void applySuggestions(CommandMeta meta) {
        meta.setArgumentSuggestion(0, context -> {
            String current = context.getCurrentArg().toLowerCase();
            return Stream.of("100", "250", "500", "1000")
                    .filter(s -> s.startsWith(current))
                    .collect(Collectors.toList());
        });
    }
}
