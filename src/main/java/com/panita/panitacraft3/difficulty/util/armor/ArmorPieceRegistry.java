package com.panita.panitacraft3.difficulty.util.armor;

import org.bukkit.Material;

import java.util.*;

/**
 * ArmorPieceRegistry is a utility class that manages the registration and retrieval of armor pieces.
 * It provides methods to get available armor pieces based on the difficulty level and to find armor pieces by their material type.
 */
public class ArmorPieceRegistry {
    /**
     * ArmorPiece represents an individual piece of armor with its material, defense points, and availability based on difficulty.
     */
    public enum ArmorSlot {
        HELMET, CHESTPLATE, LEGGINGS, BOOTS
    }

    // This registry list holds the armor pieces with their respective slots.
    private static final Map<ArmorSlot, List<ArmorPiece>> registry = new EnumMap<>(ArmorSlot.class);

    // Static block to initialize the registry with armor pieces.
    static {
        // Helmet
        register(ArmorSlot.HELMET, List.of(
                new ArmorPiece(Material.LEATHER_HELMET, 3, 0.0),
                new ArmorPiece(Material.GOLDEN_HELMET, 4, 0.0),
                new ArmorPiece(Material.TURTLE_HELMET, 4, 0.0),
                new ArmorPiece(Material.CHAINMAIL_HELMET, 6, 0.0),
                new ArmorPiece(Material.IRON_HELMET, 7, 0.25),
                new ArmorPiece(Material.DIAMOND_HELMET, 10, 0.5),
                new ArmorPiece(Material.NETHERITE_HELMET, 14, 0.7)
        ));

        // Chestplate
        register(ArmorSlot.CHESTPLATE, List.of(
                new ArmorPiece(Material.LEATHER_CHESTPLATE, 3, 0.0),
                new ArmorPiece(Material.GOLDEN_CHESTPLATE, 4, 0.0),
                new ArmorPiece(Material.CHAINMAIL_CHESTPLATE, 6, 0.0),
                new ArmorPiece(Material.IRON_CHESTPLATE, 7, 0.25),
                new ArmorPiece(Material.DIAMOND_CHESTPLATE, 10, 0.5),
                new ArmorPiece(Material.NETHERITE_CHESTPLATE, 14, 0.7)
        ));

        // Leggings
        register(ArmorSlot.LEGGINGS, List.of(
                new ArmorPiece(Material.LEATHER_LEGGINGS, 3, 0.0),
                new ArmorPiece(Material.GOLDEN_LEGGINGS, 4, 0.0),
                new ArmorPiece(Material.CHAINMAIL_LEGGINGS, 6, 0.0),
                new ArmorPiece(Material.IRON_LEGGINGS, 7, 0.25),
                new ArmorPiece(Material.DIAMOND_LEGGINGS, 10, 0.5),
                new ArmorPiece(Material.NETHERITE_LEGGINGS, 14, 0.7)
        ));

        // Boots
        register(ArmorSlot.BOOTS, List.of(
                new ArmorPiece(Material.LEATHER_BOOTS, 3, 0.0),
                new ArmorPiece(Material.GOLDEN_BOOTS, 4, 0.0),
                new ArmorPiece(Material.CHAINMAIL_BOOTS, 6, 0.0),
                new ArmorPiece(Material.IRON_BOOTS, 7, 0.25),
                new ArmorPiece(Material.DIAMOND_BOOTS, 10, 0.5),
                new ArmorPiece(Material.NETHERITE_BOOTS, 14, 0.7)
        ));
    }

    /**
     * Registers a list of armor pieces for a specific armor slot.
     *
     * @param slot    The armor slot to register the pieces for.
     * @param pieces  The list of armor pieces to register.
     */
    private static void register(ArmorSlot slot, List<ArmorPiece> pieces) {
        registry.put(slot, pieces);
    }

    /**
     * Retrieves a list of available armor pieces for a specific slot based on the normalized difficulty.
     *
     * @param slot                The armor slot to retrieve pieces for.
     * @param normalizedDifficulty The normalized difficulty value (0.0 to 1.0).
     * @return A list of available armor pieces for the specified slot.
     */
    public static List<ArmorPiece> getAvailablePieces(ArmorSlot slot, double normalizedDifficulty) {
        return registry.getOrDefault(slot, List.of()).stream()
                .filter(piece -> piece.isAvailable(normalizedDifficulty))
                .toList();
    }

    /**
     * Retrieves an armor piece by its material type.
     *
     * @param material The material type of the armor piece.
     * @return An Optional containing the armor piece if found, otherwise empty.
     */
    public static Optional<ArmorPiece> getByMaterial(Material material) {
        return registry.values().stream()
                .flatMap(List::stream)
                .filter(piece -> piece.getMaterial() == material)
                .findFirst();
    }
}
