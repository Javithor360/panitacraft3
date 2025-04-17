package com.panita.panitacraft3.util.commands.dynamic;

import com.panita.panitacraft3.util.commands.identifiers.CommandMeta;

/**
 * Interface for commands that can suggest tab completions.
 */
public interface TabSuggestingCommand {
    void applySuggestions(CommandMeta meta);
}
