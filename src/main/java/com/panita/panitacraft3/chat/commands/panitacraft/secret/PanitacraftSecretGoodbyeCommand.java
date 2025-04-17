package com.panita.panitacraft3.chat.commands.panitacraft.secret;

import com.panita.panitacraft3.util.commands.dynamic.AdvancedCommand;
import com.panita.panitacraft3.util.commands.identifiers.SubCommandSpec;
import org.bukkit.command.CommandSender;

@SubCommandSpec(
        parent = "panitacraft secret",
        name = "goodbye",
        description = "Shows a hidden goodbye to the player.",
        syntax = "/panitacraft secret goodbye"
)
public class PanitacraftSecretGoodbyeCommand implements AdvancedCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage("Goodbye world!");
    }
}
