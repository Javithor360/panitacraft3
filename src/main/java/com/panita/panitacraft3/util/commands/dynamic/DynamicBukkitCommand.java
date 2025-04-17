package com.panita.panitacraft3.util.commands.dynamic;

import com.panita.panitacraft3.util.commands.identifiers.CommandMeta;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Map;

/**
 * Represents a dynamic command in Bukkit, which can have both a main command and subcommands.
 */
public class DynamicBukkitCommand extends Command {
    private final CommandMeta rootMeta; // The main command metadata

    /**
     * Constructor for DynamicBukkitCommand.
     *
     * @param name The name of the command.
     * @param rootMeta The metadata for the main command.
     */
    public DynamicBukkitCommand(String name, CommandMeta rootMeta) {
        super(name);
        this.rootMeta = rootMeta;
    }

    /**
     * Executes the command with the given sender and arguments.
     *
     * @param sender The command sender (e.g., a player).
     * @param label The label of the command.
     * @param args The arguments passed to the command.
     * @return true if the command was executed successfully; false otherwise.
     */
    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        return execute(sender, rootMeta, args);
    }

    /**
     * Recursive method to look for subcommands and execute the appropriate command.
     *
     * @param sender The command sender (e.g., a player).
     * @param currentMeta The current command metadata.
     * @param args The arguments passed to the command.
     * @return true if the command was executed successfully; false otherwise.
     */
    private boolean execute(CommandSender sender, CommandMeta currentMeta, String[] args) {
        // If there are no arguments, execute the main command
        if (args.length == 0) {
            return executeMetaCommand(sender, currentMeta, new String[0]);
        }

        // If there are arguments, check if they match any subcommands
        String nextArg = args[0].toLowerCase();
        Map<String, CommandMeta> subCommands = currentMeta.getSubCommands(); // Get the subcommands of the current meta

        if (subCommands.containsKey(nextArg)) {
            // Recursive call with the next subcommand and remaining arguments
            return execute(sender, subCommands.get(nextArg), Arrays.copyOfRange(args, 1, args.length));
        }

        // If the argument does not match any subcommands, check if the current command can be executed
        if (!subCommands.isEmpty()) {
            return sendErrorMessage(sender, "invalid_arguments");
        }

        // If the argument does not match any subcommands, execute the main command
        return executeMetaCommand(sender, currentMeta, args);
    }

    private boolean executeMetaCommand(CommandSender sender, CommandMeta currentMeta, String[] args) {
        String errorCode = currentMeta.check(sender);

        // Verify if there is an error code
        if (!errorCode.isEmpty()) {
            return sendErrorMessage(sender, errorCode); // Handle the error
        }

        if (currentMeta.getCommand() != null) {
            currentMeta.getCommand().execute(sender, args);
        } else {
            sendErrorMessage(sender, "invalid_command");
        }
        return true;
    }

    private boolean sendErrorMessage(CommandSender sender, String type) {
        switch (type) {
            case "invalid_command":
                sender.sendMessage("Comando inválido");
                break;
            case "invalid_arguments":
                sender.sendMessage("Argumentos inválidos");
                break;
            case "no_permission":
                sender.sendMessage("No tienes permiso de ejecutar este comando");
                break;
            case "player_only":
                sender.sendMessage("Este comando solo puede ser ejecutado por un jugador");
                break;
            default:
                sender.sendMessage("Error desconocido");
        }
        return true;
    }
}
