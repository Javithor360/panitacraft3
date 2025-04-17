package com.panita.panitacraft3.chat.commands.panitacraft;

import com.panita.panitacraft3.util.commands.dynamic.AdvancedCommand;
import com.panita.panitacraft3.util.commands.identifiers.CommandSpec;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

@CommandSpec(
        name = "panitacraft",
        description = "PanitaCraft's main command",
        syntax = "/panitacraft <subcommand>",
        aliases = {"pc", "panita"}
)
public class PanitacraftCommand implements AdvancedCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        Bukkit.dispatchCommand(sender, "pc help");
    }
}
