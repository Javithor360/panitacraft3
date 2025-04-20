package com.panita.panitacraft3.difficulty.util.armor;

import com.panita.panitacraft3.difficulty.util.DifficultyConfig;

import java.util.List;
import java.util.Random;

import static com.panita.panitacraft3.difficulty.util.armor.ArmorPieceRegistry.*;

/**
 * Utility class for generating random armor pieces based on difficulty.
 */
public class EquipmentGenerationUtil {
    private static final Random RANDOM = new Random();

    /**
     * Generates a random armor piece based on the given slot and difficulty.
     *
     * @param slot       The armor slot (HELMET, CHESTPLATE, LEGGINGS, BOOTS).
     * @param difficulty The difficulty level (0.0 to 1.0).
     * @return A random armor piece for the specified slot and difficulty, or null if none are available.
     */
    public static ArmorPiece generateRandomArmorPiece(ArmorSlot slot, double difficulty) {
        double norm = Math.min(difficulty / DifficultyConfig.getMaxDifficultyScale(), 1.0);
        List<ArmorPiece> available = ArmorPieceRegistry.getAvailablePieces(slot, norm);
        if (available.isEmpty()) return null;

        double exponent = 2.5 - (norm * 2.0);
        double roll = Math.pow(RANDOM.nextDouble(), exponent);
        int index = Math.min((int) Math.floor(roll * available.size()), available.size() - 1);

        return available.get(index);
    }
}
