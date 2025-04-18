package com.panita.panitacraft3.difficulty;

import com.panita.panitacraft3.difficulty.calculators.GlobalDifficultyCalculator;
import com.panita.panitacraft3.difficulty.calculators.GroupDifficultyCalculator;
import com.panita.panitacraft3.difficulty.calculators.IndividualDifficultyCalculator;
import com.panita.panitacraft3.util.Global;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * DifficultyService is a utility class that provides methods to calculate and manage the difficulty of the game.
 * It calculates global, group, and individual difficulties based on various parameters.
 */
public class DifficultyService {
    // Time in milliseconds to cache the global difficulty
    private static final long CACHE_DURATION_MS = 10_000;
    // The maximum difficulty value that can be returned
    public static final double FIXED_MAX_DIFFICULTY = 250.0;
    // The given difficulty multiplier for manual difficulty adjustments
    private static double MANUAL_DIFFICULTY_MULTIPLIER = Global.DIFFICULTY_MULTIPLIER;

    // The cached global difficulty value
    private static double cachedGlobalDifficulty = 0.0;
    // The last time the global difficulty was calculated
    private static long lastGlobalCalculation = 0;

    /**
     * Evaluates the last cached global difficulty and determines if it needs to be recalculated.
     * @return A number between 0 and the max fixed difficulty set representing the global difficulty.
     */
    public static double getGlobalDifficulty() {
        long now = System.currentTimeMillis();

        // Check if the cached global difficulty is older than the cache duration
        if (now - lastGlobalCalculation > CACHE_DURATION_MS) {
            // Recalculate the global difficulty
            cachedGlobalDifficulty = GlobalDifficultyCalculator.calculate();
            lastGlobalCalculation = now;
        }

        return cachedGlobalDifficulty;
    }

    /**
     * Calculates the difficulty of a group of players based on their individual difficulties.
     * @param location The location to calculate the group difficulty for.
     * @return A number between 0 and the max fixed difficulty set representing the group difficulty.
     */
    public static double getGroupDifficulty(Location location) {
        return GroupDifficultyCalculator.calculate(location); // Calculate the group difficulty based on the players in the given location
    }

    /**
     * Calculates the accumulated final difficulty using this formula
     * <br/> <code>[(GlobalDifficulty * 0.4) + (GroupDifficulty * 0.6)] * (1 + OnlinePlayers)</code>
     * @param context The location to calculate the group difficulty for.
     * @return A number that represents the total difficulty.
     */
    public static double getTotalDifficulty(Location context) {
        double dg = getGlobalDifficulty(); // Get the global difficulty
        double dgp = getGroupDifficulty(context); // Get the group difficulty
        int onlinePlayers = Bukkit.getOnlinePlayers().size(); // Get the number of online players

        double normDG = Global.normalize(dg, 0, FIXED_MAX_DIFFICULTY); // Normalize the global difficulty
        double normDGP = Global.normalize(dgp, 0, FIXED_MAX_DIFFICULTY); // Normalize the group difficulty

        // Calculate the auto-generated difficulty using the formula
        double autoGeneratedDifficulty = ((normDG * 0.4) + (normDGP * 0.6)) * (1 + onlinePlayers);

        // Return the final difficulty adjusted by the manual difficulty multiplier
        return autoGeneratedDifficulty * MANUAL_DIFFICULTY_MULTIPLIER; // Final difficulty can exceed the max fixed difficulty
    }

    /**
     * Calculates the difficulty of a player based on their individual difficulty.
     * @param player The player to calculate the difficulty for.
     * @return A number between 0 and the max fixed difficulty set representing the individual difficulty.
     */
    public static double getIndividualDifficulty(Player player) {
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
