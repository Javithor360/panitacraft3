package com.panita.panitacraft3.difficulty.modifiers.pool;

import com.panita.panitacraft3.difficulty.debug.DebugReport;
import com.panita.panitacraft3.difficulty.modifiers.MobModifier;
import com.panita.panitacraft3.difficulty.util.armor.EquipmentGenerationUtil;
import com.panita.panitacraft3.difficulty.util.armor.ArmorPiece;
import com.panita.panitacraft3.difficulty.util.armor.ArmorPieceRegistry.*;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

/**
 * This class modifies the equipped armor of certain mobs based on the difficulty level.
 */
public abstract class BaseArmorEquipModifier implements MobModifier {
    protected final ArmorSlot slot;
    protected final double baseWeight;
    protected final double minDifficulty;
    protected final String name;

    protected final Set<EntityType> applicableTypes = Set.of(
            EntityType.ZOMBIE,
            EntityType.HUSK,
            EntityType.DROWNED,
            EntityType.ZOMBIE_VILLAGER,
            EntityType.ZOMBIFIED_PIGLIN,
            EntityType.PIGLIN,
            EntityType.BOGGED,
            EntityType.GIANT,
            EntityType.SKELETON,
            EntityType.STRAY,
            EntityType.WITHER_SKELETON,
            EntityType.ILLUSIONER,
            EntityType.PILLAGER,
            EntityType.VINDICATOR,
            EntityType.EVOKER,
            EntityType.VEX
    );

    protected BaseArmorEquipModifier(ArmorSlot slot, double baseWeight, double minDifficulty, String name) {
        this.slot = slot;
        this.baseWeight = baseWeight;
        this.minDifficulty = minDifficulty;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getBaseWeight() {
        return baseWeight;
    }

    @Override
    public double getMaxBoost() {
        return 0;
    }

    @Override
    public double getMinDifficulty() {
        return minDifficulty;
    }

    @Override
    public boolean canApply(LivingEntity entity) {
        return applicableTypes.contains(entity.getType()); // Check if the entity type is in the applicable types
    }

    @Override
    public double apply(LivingEntity entity, double difficulty, double ignoredBoost, DebugReport report) {
        ArmorPiece piece = EquipmentGenerationUtil.generateRandomArmorPiece(slot, difficulty);
        if (piece == null) return 0;

        Material mat = piece.getMaterial(); // Get the material of the armor piece
        int extra = piece.getExtraWeight();
        double cost = baseWeight + extra;

        EntityEquipment eq = entity.getEquipment();
        if (eq == null) return 0;

        ItemStack item = new ItemStack(mat);
        switch (slot) { // Assign the item to the appropriate armor slot
            case HELMET -> {
                eq.setHelmet(item);
                eq.setHelmetDropChance(0.0f); // Set drop chance to 0 to prevent item drops
            }
            case CHESTPLATE -> {
                eq.setChestplate(item);
                eq.setChestplateDropChance(0.0f);
            }
            case LEGGINGS -> {
                eq.setLeggings(item);
                eq.setLeggingsDropChance(0.0f);
            }
            case BOOTS -> {
                eq.setBoots(item);
                eq.setBootsDropChance(0.0f);
            }
        }

        report.logModifier(this, 0, 0, cost);
        return cost;

    }
}
