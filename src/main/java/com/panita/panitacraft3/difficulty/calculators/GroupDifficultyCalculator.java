package com.panita.panitacraft3.difficulty.calculators;

import com.panita.panitacraft3.difficulty.DifficultyService;
import com.panita.panitacraft3.difficulty.util.DifficultyConfig;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * GroupDifficultyCalculator is a utility class that calculates the difficulty of a group of players
 * based on the average difficulty of individual players within a specified radius.
 */
public class GroupDifficultyCalculator {
    /**
     * Calculates the difficulty of a group of players based on their average individual difficulties.
     * Uses the following formula:
     * <br /> <code>(Σ eachPlayerDI) / playersInRadius</code>
     *
     * @param center The location to calculate the group difficulty for.
     * @return A number between 0 and the max fixed difficulty set representing the group difficulty.
     */
    public static double calculate(Location center) {
        // The radius distance to consider players for group difficulty calculation
        double radius = DifficultyConfig.getGroupRadius();

        // Get all players within the specified radius of the center location
        Collection<Player> players = center.getWorld().getNearbyPlayers(center, radius);

        // If no players are found, return 0 difficulty
        if (players.isEmpty()) return 0;

        // The total represents the sum of individual difficulties
        double total = 0;
        for (Player p : players) {
            // Calculate individual difficulty for each player and sum it up
            total += DifficultyService.getIndividualDifficulty(p);
        }

        // Applies the following formula:
        // (Σ eachPlayerDI) / playersInRadius
        return Math.min(total / players.size(), DifficultyService.FIXED_MAX_DIFFICULTY);
    }
}
