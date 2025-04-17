package com.panita.panitacraft3.util.commands.dynamic;

import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Functional interface for providing tab completion suggestions.
 */
@FunctionalInterface
public interface SuggestionProvider {
    List<String> suggest(TabContext context);
}
