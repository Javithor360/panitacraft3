package com.panita.panitacraft3.util.commands.identifiers;

import com.panita.panitacraft3.util.commands.dynamic.AdvancedCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * CommandMeta holds the metadata for a command, including its permission, syntax, and description.
 * It also handles permission checks and player-only restrictions.
 */
public record CommandMeta(AdvancedCommand command, String permission, boolean playerOnly, String syntax, String description) {

    /**
     * Checks if the sender has permission to execute the command.
     * Also checks if the command is restricted to players only.
     *
     * @param sender The command sender (e.g., a player).
     * @return true if the sender has permission and meets the conditions; false otherwise.
     */
    public boolean check(CommandSender sender) {
        // Check if the command is restricted to players only
        if (playerOnly && !(sender instanceof Player)) { // Check the instance of the sender
            sender.sendMessage("Este comando solo puede ser ejecutado por jugadores"); // Message for non-players
            return false; // Return false if the sender is not a player
        }

        // Check if the sender has the required permission
        if (!permission.isEmpty() && !sender.hasPermission(permission)) {
            sender.sendMessage("No tienes permiso de ejecutar este comando"); // Message for lack of permission
            return false; // Return false if the sender lacks permission
        }

        return true; // Return true if the sender has permission and is a player (if required)
    }
}