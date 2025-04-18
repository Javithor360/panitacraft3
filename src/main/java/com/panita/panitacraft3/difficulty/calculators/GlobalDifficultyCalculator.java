package com.panita.panitacraft3.difficulty.calculators;

import com.panita.panitacraft3.difficulty.util.BiomeDanger;
import com.panita.panitacraft3.util.Global;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;

import static com.panita.panitacraft3.difficulty.DifficultyService.FIXED_MAX_DIFFICULTY;

/**
 * GlobalDifficultyCalculator is a utility class that calculates the global difficulty of the server
 * based on various factors such as world days, online players, average biome danger, and global events.
 */
public class GlobalDifficultyCalculator {
    /**
     * Calculates the global difficulty of the server based on various parameters.
     *
     * @return A number between 0 and the max fixed difficulty set representing the global difficulty.
     */
    public static double calculate() {
        double worldDaysNorm = Global.normalize(getWorldDays(), 0, 1000); // Normalize up to 1000 in-game days
        double onlinePlayersNorm = Global.normalize(Bukkit.getOnlinePlayers().size(), 0, Bukkit.getMaxPlayers()); // Normalize online players to max players
        double averageBiomeDanger = getAverageBiomeDanger(); // Get the average biome danger level
        double globalEventPenalty = getGlobalEventPenalty(); //

        // Applies the following formula:
        // [(worldDays · 0.4) + (onlinePlayers · 0.25) + (biomeDanger · 0.1) - (eventPenalty · 0.15)] * maxDifficulty
        double globalDifficulty =
                ((worldDaysNorm * 0.5) +
                        (onlinePlayersNorm * 0.25) +
                        (averageBiomeDanger * 0.1) +
                        (globalEventPenalty * 0.15)
                ) * FIXED_MAX_DIFFICULTY;

        return Math.min(globalDifficulty, FIXED_MAX_DIFFICULTY); // Ensure difficulty does not exceed max difficulty
    }

    /**
     * Retrieves the number of world days based on the server's world time.
     *
     * @return The number of world days.
     */
    private static int getWorldDays() {
        long time = Bukkit.getWorlds().getFirst().getFullTime();
        return (int) (time / 24000);
    }

    /**
     * Retrieves the average biome danger level based on the players' locations.
     *
     * @return The average biome danger level.
     */
    public static double getAverageBiomeDanger() {
        if (Bukkit.getOnlinePlayers().isEmpty()) return 0;

        return Bukkit.getOnlinePlayers().stream()
                .mapToDouble(p -> BiomeDanger.getDangerLevel(p.getLocation().getBlock().getBiome()))
                .average().orElse(0);
    }

    /**
     * Retrieves the global event penalty based on server events.
     *
     * @return The global event penalty.
     */
    public static double getGlobalEventPenalty() {
        return 0; // Placeholder for global event penalty
    }
}
