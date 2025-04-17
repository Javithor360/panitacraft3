package com.panita.panitacraft3.util.commands.dynamic;

import org.bukkit.command.CommandSender;

/**
 * Interface for advanced commands in the Panitacraft plugin.
 */
public interface AdvancedCommand {
    void execute(CommandSender sender, String[] args);
}
