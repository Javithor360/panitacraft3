package com.panita.panitacraft3.chat.commands.panitacraft;

import com.panita.panitacraft3.util.commands.dynamic.AdvancedCommand;
import com.panita.panitacraft3.util.commands.identifiers.CommandSpec;
import org.bukkit.command.CommandSender;

@CommandSpec(
        name = "panitacraft",
        description = "Panitacraft main command",
        syntax = "/panitacraft <subcommand>",
        aliases = {"pc"}
)
public class PanitacraftCommand implements AdvancedCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage("🌟 Comando raíz ejecutado correctamente.");
    }
}
