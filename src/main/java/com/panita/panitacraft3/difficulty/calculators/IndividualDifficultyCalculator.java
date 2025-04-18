package com.panita.panitacraft3.difficulty.calculators;

import com.panita.panitacraft3.difficulty.DifficultyService;
import com.panita.panitacraft3.difficulty.util.BiomeDanger;
import com.panita.panitacraft3.util.Global;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

/**
 * IndividualDifficultyCalculator is a utility class that calculates the difficulty of an individual player
 * based on their playtime, deaths, experience level, and equipment score.
 */
public class IndividualDifficultyCalculator {
    /**
     * Calculates the difficulty of a player based on their playtime, deaths, experience level, and equipment score.
     *
     * @param player The player whose difficulty is to be calculated.
     * @return A number between 0 and the max fixed difficulty set representing the player's difficulty.
     */
    public static double calculate(Player player) {
        // Extract player statistics
        double timeSinceLastDeath = Global.ticksToHours(player.getStatistic(Statistic.TIME_SINCE_DEATH));
        double timeSinceLastRest = Global.ticksToHours(player.getStatistic(Statistic.TIME_SINCE_REST));
        double playTimeHours = Global.ticksToHours(player.getStatistic(Statistic.PLAY_ONE_MINUTE));
        int mobsKilled = player.getStatistic(Statistic.MOB_KILLS);
        int deaths = player.getStatistic(Statistic.DEATHS);
        int xpLevel = player.getLevel();
        double equipmentScore = EquipmentScoreCalculator.calculate(player); // Apply raw equipment score calculation

        // Environmental factors
        double biomeDanger = BiomeDanger.getDangerLevel(player.getLocation().getBlock().getBiome());
        double dimensionMultiplier = getDimensionMultiplier(player);

        double timeSinceDeathNorm = Global.normalize(timeSinceLastDeath, 0, 48); // Normalize time since rest to 0-48 hours
        double timeSinceRestNorm = Global.normalize(timeSinceLastRest, 0, 12); // Normalize time since rest to 0-12 hours
        double playTimeNorm = Global.normalize(playTimeHours, 0, 250 * 3600); // Normalize playtime to 0-250 played hours
        double mobsKilledNorm = Global.normalize(mobsKilled, 0, 5000); // Normalize up to 5000 mobs killed
        double equipNorm = Global.normalize(equipmentScore, 0, 500); // Normalize equipment score to 0-100
        double levelNorm = Global.normalize(xpLevel, 0, 500); // Normalize level to 0-500
        double deathNorm = Global.normalize(deaths, 0, 250); // Normalize deaths to 0-250

        // Applies the following formula:
        // [(timeSinceLastDeath * 0.1) + (timeSinceLastRest * 0.1) + (playtime * 0.4) + (mobsKilled * 0.3) +
        //  (equipment * 0.3) + (xp level * 0.2) - (deaths * 0.3)] * (biomeDanger * dimensionMultiplier) * maxDifficulty
        double individualDifficulty = (
                (timeSinceDeathNorm * 0.1) +
                (timeSinceRestNorm * 0.1) +
                (playTimeNorm * 0.4) +
                (mobsKilledNorm * 0.3) +
                (equipNorm * 0.5) +
                (levelNorm * 0.2) -
                (deathNorm * 0.4)
        ) * (biomeDanger * dimensionMultiplier) * DifficultyService.FIXED_MAX_DIFFICULTY;

        return Math.min(individualDifficulty, DifficultyService.FIXED_MAX_DIFFICULTY); // Ensure difficulty is not negative
    }

    public static double getDimensionMultiplier(Player player) {
        return switch (player.getWorld().getEnvironment()) {
            case NORMAL -> 1.0; // Overworld
            case NETHER -> 1.5; // Nether
            case THE_END -> 2.0; // End
            default -> 1.25; // Default multiplier for other dimensions
        };
    }
}
