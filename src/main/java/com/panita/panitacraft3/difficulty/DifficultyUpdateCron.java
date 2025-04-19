package com.panita.panitacraft3.difficulty;

import com.panita.panitacraft3.difficulty.util.DifficultyConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * DifficultyUpdateCron is a utility class that handles the periodic update of player difficulties.
 * It runs a task every specified interval to ensure that players' difficulties are updated
 * based on their current conditions and the overall game state.
 */
public class DifficultyUpdateCron {
    /**
     * Starts the periodic update task for player difficulties.
     * This method should be called during the plugin's startup process.
     *
     * @param plugin The instance of the JavaPlugin that is running this task.
     */
    public static void start(JavaPlugin plugin) {
        new BukkitRunnable() { // Runnable task to be executed periodically
            @Override
            public void run() {
                if (!DifficultyConfig.isEnabled()) return; // Check if difficulty is enabled

                // Update the difficulty for each online player
                for (Player player : Bukkit.getOnlinePlayers()) {
                    DifficultyService.getIndividualDifficulty(player, true); // force update
                }

                // Update the chronologic difficulty
                DifficultyService.getChronologicDifficulty(true);
            }
        }.runTaskTimerAsynchronously(plugin, 0L, DifficultyConfig.getDifficultyCheckDelay());
    }
}
