package com.panita.panitacraft3.difficulty.calculators;

import com.panita.panitacraft3.util.Global;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;

import static com.panita.panitacraft3.difficulty.DifficultyService.FIXED_MAX_DIFFICULTY;

/**
 * ChronologicDifficultyCalculator is a utility class that calculates the global difficulty of the server
 * based on various parameters such as world days, online players, and global events.
 */
public class ChronologicDifficultyCalculator {
    /**
     * Calculates the global difficulty of the server based on various parameters.
     * Uses the following formula:
     * <br /> <code>[(worldDays * 0.4) + (onlinePlayers * 0.3) + (totalLoadedChunks * 0.04) + (globalEventPenalty * 0.1)] * maxDifficulty</code>
     *
     * @return A number between 0 and the max fixed difficulty set representing the global difficulty.
     */
    public static double calculate() {
        double worldDaysNorm = Global.normalize(getWorldDays(), 0, 5000); // Normalize up to 5000 in-game days
        double onlinePlayersNorm = Global.normalize(Bukkit.getOnlinePlayers().size(), 0, Bukkit.getMaxPlayers()); // Normalize online players to max players
        double totalLoadedChunks = Global.normalize(Bukkit.getWorlds().stream()
                .mapToInt(world -> world.getLoadedChunks().length)
                .sum(), 16, 50000); // Normalize loaded chunks to 16-50000
        double globalEventPenalty = getGlobalEventPenalty(); //

        // Applies the following formula:
        // [(worldDays * 0.4) + (onlinePlayers * 0.3) + (totalLoadedChunks * 0.04) + (globalEventPenalty * 0.1)] * maxDifficulty
        double globalDifficulty =
                ((worldDaysNorm * 0.3) +
                        (onlinePlayersNorm * 0.2) +
                        (totalLoadedChunks * 0.3) +
                        (globalEventPenalty * 0.2)
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
     * Retrieves the global event penalty based on server events.
     *
     * @return The global event penalty.
     */
    public static double getGlobalEventPenalty() {
        return 0; // Placeholder for global event penalty
    }
}
