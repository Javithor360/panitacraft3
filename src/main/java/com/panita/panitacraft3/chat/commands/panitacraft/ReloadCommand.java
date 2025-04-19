package com.panita.panitacraft3.chat.commands.panitacraft;

import com.panita.panitacraft3.Panitacraft;
import com.panita.panitacraft3.difficulty.DifficultyManager;
import com.panita.panitacraft3.difficulty.DifficultyService;
import com.panita.panitacraft3.difficulty.util.DifficultyConfig;
import com.panita.panitacraft3.util.Global;
import com.panita.panitacraft3.util.chat.Messenger;
import com.panita.panitacraft3.util.commands.dynamic.AdvancedCommand;
import com.panita.panitacraft3.util.commands.identifiers.SubCommandSpec;
import org.bukkit.command.CommandSender;


@SubCommandSpec(
        parent = "panitacraft",
        name = "reload",
        description = "Reloads the Panitacraft configuration.",
        syntax = "/panitacraft reload",
        permission = "panitacraft.admin.command.reload"
)
public class ReloadCommand implements AdvancedCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        Panitacraft plugin = Panitacraft.getPlugin(Panitacraft.class);

        plugin.reloadConfig(); // reload config.yml

        // -- Global-related reloads --
        Global.load(plugin); // reload Global variables

        // -- Difficulty-related reloads --
        DifficultyConfig.load(plugin.getConfig()); // reload difficulty config
        DifficultyService.resetCaches(); // reset difficulty caches to ensure clean recalculation

        Messenger.prefixedSend(sender, "<green>Configuración recargada correctamente.</green>");
    }
}
