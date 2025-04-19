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
        name = "multiplier",
        description = "Sets the difficulty multiplier.",
        syntax = "/panitacraft difficulty set multiplier <value>",
        permission = "panitacraft.admin.command.panitacraft.difficulty.set.multiplier"
)
public class MultiplierCommand implements AdvancedCommand, TabSuggestingCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!CommandUtils.checkArgsOrUsage(sender, args, 1, this.getClass())) return;

        String inputValue = args[0].toLowerCase();
        try {
            double value = Double.parseDouble(inputValue);
            if (value < 0.1 || value > 10.0) {
                Messenger.prefixedSend(sender, "<red>El valor del multiplicador debe estar entre 0.1 y 10.0</red>");
                return;
            }
            DifficultyConfig.setDifficultyMultiplier(value);
            Messenger.prefixedSend(sender, "<green>Multiplicador actualizado a:</green> <gold>x" + value + "</gold>");
        } catch (NumberFormatException e) {
            Messenger.prefixedSend(sender, "<red>Debes ingresar un número válido (ej: 1.0)</red>");
        }
    }

    @Override
    public void applySuggestions(CommandMeta meta) {
        meta.setArgumentSuggestion(0, context -> {
            String current = context.getCurrentArg().toLowerCase();
            return Stream.of("0.5", "1", "1.25", "1.5", "2", "5", "10")
                    .filter(s -> s.startsWith(current))
                    .collect(Collectors.toList());
        });
    }
}
