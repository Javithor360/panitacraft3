package com.panita.panitacraft3.chat.commands.panitacraft.difficulty;

import com.panita.panitacraft3.difficulty.DifficultyManager;
import com.panita.panitacraft3.util.chat.Messenger;
import com.panita.panitacraft3.util.commands.dynamic.AdvancedCommand;
import com.panita.panitacraft3.util.commands.identifiers.SubCommandSpec;
import org.bukkit.command.CommandSender;

@SubCommandSpec(
        parent = "panitacraft difficulty",
        name = "status",
        description = "Check the status of the difficulty system",
        syntax = "/panitacraft difficulty status",
        permission = "panitacraft.admin.command.panitacraft.difficulty.status"
)
public class StatusCommand implements AdvancedCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        double global = DifficultyManager.get().getGlobalDifficulty();
        double multiplier = DifficultyManager.get().getDifficultyMultiplier();
        double real = global * multiplier;

        Messenger.prefixedSend(sender, String.format(
                "<gray>Dificultad global base:</gray> <aqua>%.2f</aqua>\n" +
                        "<gray>Multiplicador manual:</gray> <gold>x%.2f</gold>\n" +
                        "<gray>Dificultad efectiva:</gray> <green>%.2f</green>",
                global, multiplier, real
        ));
    }
}
