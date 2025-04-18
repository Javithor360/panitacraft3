package com.panita.panitacraft3.difficulty.calculators;

import com.panita.panitacraft3.difficulty.DifficultyService;
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
        long playTimeTicks = player.getStatistic(Statistic.PLAY_ONE_MINUTE);
        int deaths = player.getStatistic(Statistic.DEATHS);
        int xpLevel = player.getLevel();
        double equipmentScore = EquipmentScoreCalculator.calculate(player); // Apply raw equipment score calculation

        // playTimeNorm formula: playTimeTicks / 20.0 to convert ticks to seconds
        double playTimeNorm = Global.normalize(playTimeTicks / 20.0, 0, 250 * 3600); // Normalize playtime to 0-250 played hours
        double equipNorm = Global.normalize(equipmentScore, 0, 500); // Normalize equipment score to 0-100
        double levelNorm = Global.normalize(xpLevel, 0, 500); // Normalize level to 0-500
        double deathNorm = Global.normalize(deaths, 0, 250); // Normalize deaths to 0-250

        // Applies the following formula:
        // [(playtime · 0.4) + (equipment · 0.3) + (xp level · 0.2) - (deaths · 0.3)] × maxDifficulty
        double difficulty = (
                (playTimeNorm * 0.4) +
                (equipNorm * 0.3) +
                (levelNorm * 0.2) -
                (deathNorm * 0.3)
        ) * DifficultyService.FIXED_MAX_DIFFICULTY;

        return Math.max(0, difficulty); // Ensure difficulty is not negative
    }
}
