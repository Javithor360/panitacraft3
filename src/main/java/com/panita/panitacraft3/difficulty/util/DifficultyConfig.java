package com.panita.panitacraft3.difficulty.util;

import com.panita.panitacraft3.Panitacraft;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class DifficultyConfig {
    private static FileConfiguration config;
    private static Panitacraft plugin;

    // Basic values
    private static boolean enabled;
    private static double maxDifficultyScale;
    private static int groupRadius;
    private static double manualMultiplier;

    // Difficulties weights
    private static double chronoWeight;
    private static double groupWeight;
    private static long delayBetweenChecks;

    // Chronologic factors
    private static double chronoWorldDaysWeight;
    private static int chronoWorldDaysMin;
    private static int chronoWorldDaysMax;
    private static double chronoOnlinePlayersWeight;
    private static int chronoOnlinePlayersMin;
    private static double chronoOnlinePlayersMax; // dynamic: depends on server max
    private static double chronoChunksWeight;
    private static int chronoChunksMin;
    private static int chronoChunksMax;
    private static double chronoEventsWeight;

    // Individual factors
    private static double indivTimeSinceDeathWeight;
    private static double indivTimeSinceDeathMin;
    private static double indivTimeSinceDeathMax;

    private static double indivTimeSinceRestWeight;
    private static double indivTimeSinceRestMin;
    private static double indivTimeSinceRestMax;

    private static double indivPlaytimeWeight;
    private static double indivPlaytimeMin;
    private static double indivPlaytimeMax;

    private static double indivMobsKilledWeight;
    private static int indivMobsKilledMin;
    private static int indivMobsKilledMax;

    private static double indivEquipmentWeight;
    private static int indivEquipmentMin;
    private static int indivEquipmentMax;

    private static double indivXpWeight;
    private static int indivXpMin;
    private static int indivXpMax;

    private static double indivDeathsWeight;
    private static int indivDeathsMin;
    private static int indivDeathsMax;

    // Dimension multipliers
    private static double overworldMultiplier;
    private static double netherMultiplier;
    private static double endMultiplier;
    private static double otherMultiplier;

    public static void load(FileConfiguration loadConfig) {
        plugin = Panitacraft.getPlugin(Panitacraft.class);
        config = loadConfig;

        enabled = config.getBoolean("difficulty.enabled", true);
        maxDifficultyScale = config.getDouble("difficulty.max_difficulty_scale", 250.0);
        groupRadius = config.getInt("difficulty.group_difficulty_radius", 128);
        manualMultiplier = config.getDouble("difficulty.multiplier", 1.0);

        chronoWeight = config.getDouble("difficulty.auto_generated.chronological_weight_in_formula", 0.4);
        groupWeight = config.getDouble("difficulty.auto_generated.group_weight_in_formula", 0.6);
        delayBetweenChecks = config.getLong("difficulty.auto_generated.delay_between_checks", 300000L);

        chronoWorldDaysWeight = config.getDouble("difficulty.auto_generated.chronological.factors.world_days.weight", 0.3);
        chronoWorldDaysMin = config.getInt("difficulty.auto_generated.chronological.factors.world_days.min", 0);
        chronoWorldDaysMax = config.getInt("difficulty.auto_generated.chronological.factors.world_days.max", 5000);

        chronoOnlinePlayersWeight = config.getDouble("difficulty.auto_generated.chronological.factors.online_players.weight", 0.2);
        chronoOnlinePlayersMin = config.getInt("difficulty.auto_generated.chronological.factors.online_players.min", 0);

        String rawOnlineMax = config.getString("difficulty.auto_generated.chronological.factors.online_players.max", "%MAX_PLAYERS%");
        if (rawOnlineMax.equalsIgnoreCase("%MAX_PLAYERS%")) {
            chronoOnlinePlayersMax = Bukkit.getMaxPlayers();
        } else {
            try {
                chronoOnlinePlayersMax = Double.parseDouble(rawOnlineMax);
            } catch (NumberFormatException e) {
                Bukkit.getLogger().warning("[Panitacraft] Invalid value for chronoOnlinePlayersMax. Using default.");
                chronoOnlinePlayersMax = Bukkit.getMaxPlayers();
            }
        }

        chronoChunksWeight = config.getDouble("difficulty.auto_generated.chronological.factors.total_loaded_chunks.weight", 0.3);
        chronoChunksMin = config.getInt("difficulty.auto_generated.chronological.factors.total_loaded_chunks.min", 16);
        chronoChunksMax = config.getInt("difficulty.auto_generated.chronological.factors.total_loaded_chunks.max", 50000);

        chronoEventsWeight = config.getDouble("difficulty.auto_generated.chronological.factors.event_penalty.weight", 0.2);

        indivTimeSinceDeathWeight = config.getDouble("difficulty.auto_generated.individual.factors.time_since_last_death.weight", 0.1);
        indivTimeSinceDeathMin = config.getDouble("difficulty.auto_generated.individual.factors.time_since_last_death.min", 0.0);
        indivTimeSinceDeathMax = config.getDouble("difficulty.auto_generated.individual.factors.time_since_last_death.max", 48.0);

        indivTimeSinceRestWeight = config.getDouble("difficulty.auto_generated.individual.factors.time_since_last_rest.weight", 0.1);
        indivTimeSinceRestMin = config.getDouble("difficulty.auto_generated.individual.factors.time_since_last_rest.min", 0.0);
        indivTimeSinceRestMax = config.getDouble("difficulty.auto_generated.individual.factors.time_since_last_rest.max", 12.0);

        indivPlaytimeWeight = config.getDouble("difficulty.auto_generated.individual.factors.play_time_hours.weight", 0.4);
        indivPlaytimeMin = config.getDouble("difficulty.auto_generated.individual.factors.play_time_hours.min", 0.0);
        indivPlaytimeMax = config.getDouble("difficulty.auto_generated.individual.factors.play_time_hours.max", 250.0);

        indivMobsKilledWeight = config.getDouble("difficulty.auto_generated.individual.factors.mobs_killed.weight", 0.3);
        indivMobsKilledMin = config.getInt("difficulty.auto_generated.individual.factors.mobs_killed.min", 0);
        indivMobsKilledMax = config.getInt("difficulty.auto_generated.individual.factors.mobs_killed.max", 5000);

        indivEquipmentWeight = config.getDouble("difficulty.auto_generated.individual.factors.equipment_score.weight", 0.5);
        indivEquipmentMin = config.getInt("difficulty.auto_generated.individual.factors.equipment_score.min", 0);
        indivEquipmentMax = config.getInt("difficulty.auto_generated.individual.factors.equipment_score.max", 500);

        indivXpWeight = config.getDouble("difficulty.auto_generated.individual.factors.xp_level.weight", 0.2);
        indivXpMin = config.getInt("difficulty.auto_generated.individual.factors.xp_level.min", 0);
        indivXpMax = config.getInt("difficulty.auto_generated.individual.factors.xp_level.max", 500);

        indivDeathsWeight = config.getDouble("difficulty.auto_generated.individual.factors.death_count.weight", 0.4);
        indivDeathsMin = config.getInt("difficulty.auto_generated.individual.factors.death_count.min", 0);
        indivDeathsMax = config.getInt("difficulty.auto_generated.individual.factors.death_count.max", 250);

        overworldMultiplier = config.getDouble("difficulty.auto_generated.individual.dimension_multiplier.overworld", 1.0);
        netherMultiplier = config.getDouble("difficulty.auto_generated.individual.dimension_multiplier.nether", 1.75);
        endMultiplier = config.getDouble("difficulty.auto_generated.individual.dimension_multiplier.end", 1.5);
        otherMultiplier = config.getDouble("difficulty.auto_generated.individual.dimension_multiplier.other", 1.25);
    }

    // Basic Getters
    public static boolean isEnabled() {
        return enabled;
    }

    public static double getMaxDifficultyScale() {
        return enabled ? maxDifficultyScale : 0;
    }

    public static double getManualMultiplier() {
        return enabled ? manualMultiplier : 0;
    }

    public static int getGroupRadius() {
        return enabled ? groupRadius : 0;
    }

    public static double getChronologicWeight() {
        return enabled ? chronoWeight : 0;
    }

    public static double getGroupWeight() {
        return enabled ? groupWeight : 0;
    }

    public static long getDelayBetweenChecks() {
        return delayBetweenChecks;
    }

    // Chronologic difficulty and factors
    public static double getChronoWorldDaysWeight() {
        return enabled ? chronoWorldDaysWeight : 0;
    }

    public static int getChronoWorldDaysMin() {
        return enabled ? chronoWorldDaysMin : 0;

    }
    public static int getChronoWorldDaysMax() {
        return enabled ? chronoWorldDaysMax : 0;
    }

    public static double getChronoOnlinePlayersWeight() {
        return enabled ? chronoOnlinePlayersWeight : 0;
    }

    public static int getChronoOnlinePlayersMin() {
        return enabled ? chronoOnlinePlayersMin : 0;
    }

    public static int getChronoOnlinePlayersMax(int maxPlayers) {
        return enabled ? (int) chronoOnlinePlayersMax : 0;
    }

    public static double getChronoChunksWeight() {
        return enabled ? chronoChunksWeight : 0;
    }

    public static int getChronoChunksMin() {
        return enabled ? chronoChunksMin : 0;
    }

    public static int getChronoChunksMax() {
        return enabled ? chronoChunksMax : 0;
    }

    public static double getChronoEventsWeight() {
        return enabled ? chronoEventsWeight : 0;
    }

    // Individual difficulty and factors
    public static double getIndividualTimeSinceDeathWeight() {
        return enabled ? indivTimeSinceDeathWeight : 0;
    }

    public static double getIndividualTimeSinceDeathMin() {
        return enabled ? indivTimeSinceDeathMin : 0;
    }

    public static double getIndividualTimeSinceDeathMax() {
        return enabled ? indivTimeSinceDeathMax : 0;
    }

    public static double getIndividualTimeSinceRestWeight() {
        return enabled ? indivTimeSinceRestWeight : 0;
    }

    public static double getIndividualTimeSinceRestMin() {
        return enabled ? indivTimeSinceRestMin : 0;
    }

    public static double getIndividualTimeSinceRestMax() {
        return enabled ? indivTimeSinceRestMax : 0;
    }

    public static double getIndividualPlaytimeWeight() {
        return enabled ? indivPlaytimeWeight : 0;
    }

    public static double getIndividualPlaytimeMin() {
        return enabled ? indivPlaytimeMin : 0;
    }

    public static double getIndividualPlaytimeMax() {
        return enabled ? indivPlaytimeMax : 0;
    }

    public static double getIndividualMobsKilledWeight() {
        return enabled ? indivMobsKilledWeight : 0;
    }

    public static double getIndividualMobsKilledMin() {
        return enabled ? indivMobsKilledMin : 0;
    }

    public static double getIndividualMobsKilledMax() {
        return enabled ? indivMobsKilledMax : 0;
    }

    public static double getIndividualEquipmentWeight() {
        return enabled ? indivEquipmentWeight : 0;
    }

    public static int getIndividualEquipmentMin() {
        return enabled ? indivEquipmentMin : 0;
    }

    public static int getIndividualEquipmentMax() {
        return enabled ? indivEquipmentMax : 0;
    }

    public static double getIndividualXpWeight() {
        return enabled ? indivXpWeight : 0;
    }

    public static int getIndividualXpMin() {
        return enabled ? indivXpMin : 0;
    }

    public static int getIndividualXpMax() {
        return enabled ? indivXpMax : 0;
    }

    public static double getIndividualDeathCountWeight() {
        return enabled ? indivDeathsWeight : 0;
    }

    public static int getIndividualDeathCountMin() {
        return enabled ? indivDeathsMin : 0;
    }

    public static int getIndividualDeathCountMax() {
        return enabled ? indivDeathsMax : 0;
    }

    public static double getDimensionMultiplierOverworld() {
        return enabled ? overworldMultiplier : 0;
    }

    public static double getDimensionMultiplierNether() {
        return enabled ? netherMultiplier : 0;
    }

    public static double getDimensionMultiplierEnd() {
        return enabled ? endMultiplier : 0;
    }

    public static double getDimensionMultiplierOther() {
        return enabled ? otherMultiplier : 0;
    }

    // Set the config values
    public static void setEnabled(boolean value) {
        enabled = value;
        config.set("difficulty.enabled", value);
        plugin.saveConfig();
    }

    public static void setDifficultyMultiplier(double multiplier) {
        manualMultiplier = multiplier;
        config.set("difficulty.multiplier", multiplier);
        plugin.saveConfig();
    }

    public static void setMaxDifficultyScale(double scale) {
        maxDifficultyScale = scale;
        config.set("difficulty.max_difficulty_scale", scale);
        plugin.saveConfig();
    }

    public static void setGroupRadius(int radius) {
        groupRadius = radius;
        config.set("difficulty.group_difficulty_radius", radius);
        plugin.saveConfig();
    }
}
