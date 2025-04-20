package com.panita.panitacraft3.difficulty.util.armor;

import org.bukkit.Material;

/**
 * Represents an armor piece with its material, extra weight, and minimum difficulty.
 */
public class ArmorPiece {
    private final Material material;
    private final int extraWeight;
    private final double minNormalizedDifficulty;

    /**
     * Constructor for ArmorPiece.
     *
     * @param material              The material of the armor piece.
     * @param extraWeight           The extra weight of the armor piece.
     * @param minNormalizedDifficulty The minimum normalized difficulty for the armor piece to be available.
     */
    public ArmorPiece(Material material, int extraWeight, double minNormalizedDifficulty) {
        this.material = material;
        this.extraWeight = extraWeight;
        this.minNormalizedDifficulty = minNormalizedDifficulty;
    }

    public Material getMaterial() {
        return material;
    }

    public int getExtraWeight() {
        return extraWeight;
    }

    public double getMinDifficulty() {
        return minNormalizedDifficulty;
    }

    /**
     * Checks if the armor piece is available based on the normalized difficulty.
     *
     * @param normalizedDifficulty The normalized difficulty to check against.
     * @return true if the armor piece is available, false otherwise.
     */
    public boolean isAvailable(double normalizedDifficulty) {
        return normalizedDifficulty >= minNormalizedDifficulty;
    }
}
