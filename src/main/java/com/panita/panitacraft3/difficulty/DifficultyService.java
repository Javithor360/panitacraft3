package com.panita.panitacraft3.difficulty;

import com.panita.panitacraft3.difficulty.calculators.ChronologicDifficultyCalculator;
import com.panita.panitacraft3.difficulty.calculators.GroupDifficultyCalculator;
import com.panita.panitacraft3.difficulty.calculators.IndividualDifficultyCalculator;
import com.panita.panitacraft3.difficulty.util.DifficultyConfig;
import com.panita.panitacraft3.util.Global;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * DifficultyService is a utility class that provides methods to calculate and manage the difficulty of the game.
 * It calculates global, group, and individual difficulties based on various parameters.
 */
public class DifficultyService {
    // Time in milliseconds to cache the chronologic difficulty
    private static final long CACHE_DURATION_MS = DifficultyConfig.getChronologicCheckDelay();
    // The maximum difficulty value that can be returned
    public static final double FIXED_MAX_DIFFICULTY = DifficultyConfig.getMaxDifficultyScale();
    // The given difficulty multiplier for manual difficulty adjustments
    private static double MANUAL_DIFFICULTY_MULTIPLIER = DifficultyConfig.getManualMultiplier();

    // The cached chronologic difficulty value
    private static double cachedChronologicDifficulty = 0.0;
    // The last time the chronologic difficulty was calculated
    private static long lastChronologicCalculation = 0;

    /**
     * Evaluates the last cached chronologic difficulty and determines if it needs to be recalculated.
     * @return A number between 0 and the max fixed difficulty set representing the chronologic difficulty.
     */
    public static double getChronologicDifficulty() {
        if (!DifficultyConfig.isEnabled()) return 0;

        long now = System.currentTimeMillis();

        // Check if the cached chronologic difficulty is older than the cache duration
        if (now - lastChronologicCalculation > CACHE_DURATION_MS) {
            // Recalculate the chronologic difficulty
            cachedChronologicDifficulty = ChronologicDifficultyCalculator.calculate();
            lastChronologicCalculation = now;
        }

        return cachedChronologicDifficulty;
    }

    /**
     * Calculates the difficulty of a group of players based on their individual difficulties.
     * @param location The location to calculate the group difficulty for.
     * @return A number between 0 and the max fixed difficulty set representing the group difficulty.
     */
    public static double getGroupDifficulty(Location location) {
        if (!DifficultyConfig.isEnabled()) return 0;
        return GroupDifficultyCalculator.calculate(location); // Calculate the group difficulty based on the players in the given location
    }

    public static double getAutoGeneratedDifficulty(Location context) {
        if (!DifficultyConfig.isEnabled()) return 0;

        double dg = getChronologicDifficulty();
        double dgp = getGroupDifficulty(context);
        int online = Bukkit.getOnlinePlayers().size();

        // Applies the following formula:
        // [(ChronologicDifficulty * 0.4) + (GroupDifficulty * 0.6)] * (1 + OnlinePlayers)
        return ((dg * DifficultyConfig.getChronologicWeight()) + (dgp * DifficultyConfig.getGroupWeight())) * (1 + online);
    }

    /**
     * Calculates the accumulated final difficulty using this formula
     * <br/> <code>[(Chronologic * 0.4) + (GroupDifficulty * 0.6)] * (1 + OnlinePlayers)</code>
     * @param context The location to calculate the group difficulty for.
     * @return A number that represents the total difficulty.
     */
    public static double getLocalDifficulty(Location context) {
        if (!DifficultyConfig.isEnabled()) return 0;

        double autoGeneratedDifficulty = getAutoGeneratedDifficulty(context); // Gets the auto-generated difficulty

        // Applies the following formula:
        // autoGeneratedDifficulty * manualMultiplier
        return autoGeneratedDifficulty * MANUAL_DIFFICULTY_MULTIPLIER; // Final difficulty can exceed the max fixed difficulty
    }

    /**
     * Calculates the difficulty of a player based on their individual difficulty.
     * @param player The player to calculate the difficulty for.
     * @return A number between 0 and the max fixed difficulty set representing the individual difficulty.
     */
    public static double getIndividualDifficulty(Player player) {
        if (!DifficultyConfig.isEnabled()) return 0;
        return IndividualDifficultyCalculator.calculate(player); // Calculate the individual difficulty
    }

    public static double getManualDifficultyMultiplier() {
        return MANUAL_DIFFICULTY_MULTIPLIER;
    }

    public static void setManualDifficultyMultiplier(double multiplier) {
        MANUAL_DIFFICULTY_MULTIPLIER = Math.max(0, multiplier);
        Global.writeDifficultyMultiplier(multiplier);
    }
}
