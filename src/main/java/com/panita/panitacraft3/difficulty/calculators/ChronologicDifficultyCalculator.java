package com.panita.panitacraft3.difficulty.calculators;

import com.panita.panitacraft3.util.Global;
import org.bukkit.Bukkit;

import static com.panita.panitacraft3.difficulty.DifficultyService.FIXED_MAX_DIFFICULTY;

/**
 * ChronologicDifficultyCalculator is a utility class that calculates the global difficulty of the server
 * based on various parameters such as world days, online players, and global events.
 */
public class ChronologicDifficultyCalculator {
    /**
     * Calculates the global difficulty of the server based on various parameters.
     *
     * @return A number between 0 and the max fixed difficulty set representing the global difficulty.
     */
    public static double calculate() {
        double worldDaysNorm = Global.normalize(getWorldDays(), 0, 1000); // Normalize up to 1000 in-game days
        double onlinePlayersNorm = Global.normalize(Bukkit.getOnlinePlayers().size(), 0, Bukkit.getMaxPlayers()); // Normalize online players to max players
        double globalEventPenalty = getGlobalEventPenalty(); //

        // Applies the following formula:
        // [(worldDays · 0.4) + (onlinePlayers · 0.25) + (biomeDanger · 0.1) - (eventPenalty · 0.15)] * maxDifficulty
        double globalDifficulty =
                ((worldDaysNorm * 0.7) +
                        (onlinePlayersNorm * 0.2) +
                        (globalEventPenalty * 0.1)
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
