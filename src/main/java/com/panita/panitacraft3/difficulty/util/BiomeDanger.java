package com.panita.panitacraft3.difficulty.util;

import org.bukkit.block.Biome;

import java.util.Map;

public class BiomeDanger {
    private static final Map<Biome, Double> DANGER_LEVELS = Map.ofEntries(
            Map.entry(Biome.DEEP_DARK, 1.0),
            Map.entry(Biome.SOUL_SAND_VALLEY, 0.9),
            Map.entry(Biome.BASALT_DELTAS, 0.8),
            Map.entry(Biome.PALE_GARDEN, 0.8),
            Map.entry(Biome.JUNGLE, 0.7),
            Map.entry(Biome.SWAMP, 0.6),
            Map.entry(Biome.BADLANDS, 0.5),
            Map.entry(Biome.DARK_FOREST, 0.5),
            Map.entry(Biome.DRIPSTONE_CAVES, 0.5),
            Map.entry(Biome.DESERT, 0.5),
            Map.entry(Biome.LUSH_CAVES, 0.4),
            Map.entry(Biome.FOREST, 0.3),
            Map.entry(Biome.PLAINS, 0.1)
    );

    public static double getDangerLevel(Biome biome) {
        return DANGER_LEVELS.getOrDefault(biome, 0.2);
    }
}
