package com.panita.panitacraft3.util.commands.dynamic;

import com.panita.panitacraft3.util.commands.identifiers.CommandMeta;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a dynamic command in Bukkit, which can have both a main command and subcommands.
 */
public class DynamicBukkitCommand extends Command {
    private final CommandMeta mainCommand; // The main command metadata
    private final Map<String, CommandMeta> subs; // A map of subcommands

    /**
     * Constructor for DynamicBukkitCommand.
     *
     * @param name The name of the command.
     * @param mainCommand The main command's metadata.
     * @param subs The subcommands associated with the main command.
     */
    public DynamicBukkitCommand(String name, CommandMeta mainCommand, Map<String, CommandMeta> subs) {
        super(name);
        this.mainCommand = mainCommand;
        this.subs = subs != null ? subs : new HashMap<>();
    }

    /**
     * Executes the command. If there are subcommands, it attempts to execute the appropriate one.
     *
     * @param sender The command sender (e.g., a player).
     * @param label The label used to invoke the command.
     * @param args The arguments passed to the command.
     * @return true if the command was successfully executed; false otherwise.
     */
    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        // Of there are subcommands, try to execute the appropriate one
        if (args.length > 0 && subs.containsKey(args[0].toLowerCase())) { // Check if the first argument is a subcommand
            CommandMeta sub = subs.get(args[0].toLowerCase()); // Get the subcommand metadata
            if (!sub.check(sender)) return true; // Check if the sender is able to execute the command
            sub.command().execute(sender, Arrays.copyOfRange(args, 1, args.length)); // Execute the subcommand
            return true; // Return true to indicate the command was executed
        }

        if (!mainCommand.check(sender)) return true; // Check if the sender is able to execute the main command
        mainCommand.command().execute(sender, args); // Execute the main command
        return true; // Return true to indicate the command was executed
    }
}
