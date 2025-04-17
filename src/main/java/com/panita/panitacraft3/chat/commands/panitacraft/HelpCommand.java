package com.panita.panitacraft3.chat.commands.panitacraft;

import com.panita.panitacraft3.util.commands.dynamic.AdvancedCommand;
import com.panita.panitacraft3.util.commands.identifiers.SubCommandSpec;
import org.bukkit.command.CommandSender;

@SubCommandSpec(
        parent = "panitacraft",
        name = "help",
        description = "Displays help information for Panitacraft commands.",
        syntax = "/panitacraft help"
)
public class HelpCommand implements AdvancedCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {

    }
}
