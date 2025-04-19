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
        name = "group_radius",
        description = "Sets the radius for group difficulty scanning.",
        syntax = "/panitacraft difficulty set group_radius <value>",
        permission = "panitacraft.admin.command.panitacraft.difficulty.set.group_radius"
)
public class GroupRadiusCommand implements AdvancedCommand, TabSuggestingCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!CommandUtils.checkArgsOrUsage(sender, args, 1, this.getClass())) return;

        String inputValue = args[0].toLowerCase();
        try {
            int radius = Integer.parseInt(inputValue);
            if (radius < 0 || radius > 512) {
                Messenger.prefixedSend(sender, "<red>El radio debe estar entre 0 y 512 bloques</red>");
                return;
            }
            DifficultyConfig.setGroupRadius(radius);
            Messenger.prefixedSend(sender, "<green>Radio de dificultad grupal actualizado a:</green> <gold>" + radius + " bloques</gold>");
        } catch (NumberFormatException e) {
            Messenger.prefixedSend(sender, "<red>Debes ingresar un número entero válido (ej: 128)</red>");
        }
    }

    @Override
    public void applySuggestions(CommandMeta meta) {
        meta.setArgumentSuggestion(0, context -> {
            String current = context.getCurrentArg().toLowerCase();
            return Stream.of("32", "64", "128", "256", "512")
                    .filter(s -> s.startsWith(current))
                    .collect(Collectors.toList());
        });
    }
}
