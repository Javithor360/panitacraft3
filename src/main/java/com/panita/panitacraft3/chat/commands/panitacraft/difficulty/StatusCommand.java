package com.panita.panitacraft3.chat.commands.panitacraft.difficulty;

import com.panita.panitacraft3.difficulty.DifficultyManager;
import com.panita.panitacraft3.difficulty.DifficultyService;
import com.panita.panitacraft3.util.chat.Messenger;
import com.panita.panitacraft3.util.commands.dynamic.AdvancedCommand;
import com.panita.panitacraft3.util.commands.identifiers.SubCommandSpec;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
        if (!(sender instanceof Player p)) return;

        double autoGeneratedDifficulty = DifficultyService.getAutoGeneratedDifficulty(p.getLocation());
        double multiplier = DifficultyService.getManualDifficultyMultiplier();
        double real = autoGeneratedDifficulty * multiplier;

        Messenger.prefixedSend(sender, String.format(
                "<gray>Dificultad auto-generada base:</gray> <aqua>%.2f</aqua>\n" +
                        "<gray>Multiplicador manual:</gray> <gold>x%.2f</gold>\n" +
                        "<gray>Dificultad efectiva:</gray> <green>%.2f</green>",
                autoGeneratedDifficulty, multiplier, real
        ));
    }
}
