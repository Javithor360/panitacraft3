package com.panita.panitacraft3.difficulty.util;

import com.panita.panitacraft3.Panitacraft;
import org.bukkit.configuration.file.FileConfiguration;

public class DifficultyConfig {
    private static final FileConfiguration config = Panitacraft.getPlugin(Panitacraft.class).getConfig();

    // Basic values
    public static boolean isEnabled() {
        return config.getBoolean("difficulty.enabled", true);
    }

    public static double getMaxDifficultyScale() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getDouble("difficulty.max_difficulty_scale", 250.0);
    }

    public static double getManualMultiplier() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getDouble("difficulty.multiplier", 1.0);
    }

    public static int getGroupRadius() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getInt("difficulty.group_difficulty_radius", 128);
    }

    // Weights in formula
    public static double getChronologicWeight() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getDouble("difficulty.auto_generated.chronological_weight_in_formula", 0.4);
    }

    public static double getGroupWeight() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getDouble("difficulty.auto_generated.group_weight_in_formula", 0.6);
    }

    // Chronologic difficulty and factors
    public static long getDifficultyCheckDelay() {
        return config.getLong("difficulty.auto_generated.delay_between_checks", 3600000L);
    }

    public static double getChronoWorldDaysWeight() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getDouble("difficulty.auto_generated.chronological.factors.world_days.weight", 0.3);
    }

    public static int getChronoWorldDaysMin() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getInt("difficulty.auto_generated.chronological.factors.world_days.min", 0);
    }

    public static int getChronoWorldDaysMax() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getInt("difficulty.auto_generated.chronological.factors.world_days.max", 5000);
    }

    public static double getChronoOnlinePlayersWeight() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getDouble("difficulty.auto_generated.chronological.factors.online_players.weight", 0.2);
    }

    public static int getChronoOnlinePlayersMin() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getInt("difficulty.auto_generated.chronological.factors.online_players.min", 0);
    }

    public static int getChronoOnlinePlayersMax(int maxPlayers) {
        if (!DifficultyConfig.isEnabled()) return 0;

        // Replacing dynamic %MAX_PLAYERS%
        String raw = config.getString("difficulty.auto_generated.chronological.factors.online_players.max", String.valueOf(maxPlayers));
        return raw.equalsIgnoreCase("%MAX_PLAYERS%") ? maxPlayers : Integer.parseInt(raw);
    }

    public static double getChronoChunksWeight() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getDouble("difficulty.auto_generated.chronological.factors.totalLoadedChunks.weight", 0.3);
    }

    public static int getChronoChunksMin() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getInt("difficulty.auto_generated.chronological.factors.totalLoadedChunks.min", 16);
    }

    public static int getChronoChunksMax() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getInt("difficulty.auto_generated.chronological.factors.totalLoadedChunks.max", 50000);
    }

    public static double getChronoEventsWeight() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getDouble("difficulty.auto_generated.chronological.factors.event_penalty.weight", 0.2);
    }

    // Individual difficulty and factors
    public static double getIndividualTimeSinceDeathWeight() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getDouble("difficulty.auto_generated.individual.factors.time_since_last_death.weight", 0.1);
    }

    public static double getIndividualTimeSinceDeathMin() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getDouble("difficulty.auto_generated.individual.factors.time_since_last_death.min", 0.0);
    }

    public static double getIndividualTimeSinceDeathMax() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getDouble("difficulty.auto_generated.individual.factors.time_since_last_death.max", 48.0);
    }

    public static double getIndividualTimeSinceRestWeight() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getDouble("difficulty.auto_generated.individual.factors.time_since_last_rest.weight", 0.1);
    }

    public static double getIndividualTimeSinceRestMin() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getDouble("difficulty.auto_generated.individual.factors.time_since_last_rest.min", 0.0);
    }

    public static double getIndividualTimeSinceRestMax() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getDouble("difficulty.auto_generated.individual.factors.time_since_last_rest.max", 12.0);
    }

    public static double getIndividualPlaytimeWeight() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getDouble("difficulty.auto_generated.individual.factors.play_time_hours.weight", 0.4);
    }

    public static double getIndividualPlaytimeMin() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getDouble("difficulty.auto_generated.individual.factors.play_time_hours.min", 0.0);
    }

    public static double getIndividualPlaytimeMax() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getDouble("difficulty.auto_generated.individual.factors.play_time_hours.max", 250.0);
    }

    public static double getIndividualMobsKilledWeight() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getDouble("difficulty.auto_generated.individual.factors.mobs_killed.weight", 0.3);
    }

    public static double getIndividualMobsKilledMin() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getDouble("difficulty.auto_generated.individual.factors.mobs_killed.min", 0.0);
    }

    public static double getIndividualMobsKilledMax() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getDouble("difficulty.auto_generated.individual.factors.mobs_killed.max", 5000.0);
    }

    public static double getIndividualEquipmentWeight() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getDouble("difficulty.auto_generated.individual.factors.equipment_score.weight", 0.5);
    }

    public static double getIndividualEquipmentMin() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getDouble("difficulty.auto_generated.individual.factors.equipment_score.min", 0.0);
    }

    public static double getIndividualEquipmentMax() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getDouble("difficulty.auto_generated.individual.factors.equipment_score.max", 500.0);
    }

    public static double getIndividualXpWeight() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getDouble("difficulty.auto_generated.individual.factors.xp_level.weight", 0.2);
    }

    public static double getIndividualXpMin() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getDouble("difficulty.auto_generated.individual.factors.xp_level.min", 0.0);
    }

    public static double getIndividualXpMax() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getDouble("difficulty.auto_generated.individual.factors.xp_level.max", 500.0);
    }

    public static double getIndividualDeathCountWeight() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getDouble("difficulty.auto_generated.individual.factors.death_count.weight", 0.4);
    }

    public static double getIndividualDeathCountMin() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getDouble("difficulty.auto_generated.individual.factors.death_count.min", 0.0);
    }

    public static double getIndividualDeathCountMax() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getDouble("difficulty.auto_generated.individual.factors.death_count.max", 250.0);
    }

    // Dimension multipliers
    public static double getDimensionMultiplierOverworld() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getDouble("difficulty.auto_generated.individual.dimension_multiplier.overworld", 1.0);
    }

    public static double getDimensionMultiplierNether() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getDouble("difficulty.auto_generated.individual.dimension_multiplier.nether", 1.75);
    }

    public static double getDimensionMultiplierEnd() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getDouble("difficulty.auto_generated.individual.dimension_multiplier.end", 1.5);
    }

    public static double getDimensionMultiplierOther() {
        if (!DifficultyConfig.isEnabled()) return 0;
        return config.getDouble("difficulty.auto_generated.individual.dimension_multiplier.other", 1.25);
    }

    // Set the config values
    public static void setDifficultyMultiplier(double multiplier) {
        Panitacraft plugin = Panitacraft.getPlugin(Panitacraft.class);
        plugin.getConfig().set("difficulty.multiplier", multiplier);
        plugin.saveConfig();
    }
}
