package com.panita.panitacraft3.chat.commands.panitacraft.difficulty;

import com.panita.panitacraft3.difficulty.DifficultyManager;
import com.panita.panitacraft3.difficulty.DifficultyService;
import com.panita.panitacraft3.util.CommandUtils;
import com.panita.panitacraft3.util.chat.Messenger;
import com.panita.panitacraft3.util.commands.dynamic.AdvancedCommand;
import com.panita.panitacraft3.util.commands.identifiers.SubCommandSpec;
import org.bukkit.command.CommandSender;

@SubCommandSpec(
        parent = "panitacraft difficulty",
        name = "set",
        description = "Manual alters the difficulty multiplier.",
        syntax = "/panitacraft difficulty set <multiplier>",
        permission = "panitacraft.admin.command.panitacraft.difficulty.set"
)
public class SetCommand implements AdvancedCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!CommandUtils.checkArgsOrUsage(sender, args, 1, this.getClass())) return;

        try {
            double value = Double.parseDouble(args[0]);
            if (value < 0.0 || value > 10.0) {
                Messenger.prefixedSend(sender, "<red>El valor debe estar entre 0.0 y 10.0</red>");
                return;
            }

            DifficultyService.setManualDifficultyMultiplier(value);
            Messenger.prefixedSend(sender, "<green>Multiplicador actualizado a:</green> <gold>x" + value + "</gold>");
        } catch (NumberFormatException e) {
            Messenger.prefixedSend(sender, "<red>Debes ingresar un número válido (ej: 1.0)</red>");
        }
    }
}
