package com.panita.panitacraft3.difficulty.modifiers.pool;

import com.panita.panitacraft3.difficulty.debug.DebugReport;
import com.panita.panitacraft3.difficulty.modifiers.MobModifier;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.Set;

/**
 * This class modifies the maximum health of certain mobs based on the difficulty level.
 */
public class MaxHealthModifier implements MobModifier {
    /**
     * A set of entity types that this modifier can be applied to.
     * These are the entities that will have their max health modified.
     */
    private static final Set<EntityType> APPLICABLE_ENTITIES = Set.of(
            EntityType.ZOMBIE,
            EntityType.HUSK,
            EntityType.ZOMBIE_VILLAGER,
            EntityType.DROWNED,
            EntityType.SKELETON,
            EntityType.STRAY,
            EntityType.CREEPER,
            EntityType.SPIDER,
            EntityType.ENDERMAN,
            EntityType.WITCH,
            EntityType.PHANTOM,
            EntityType.SLIME,
            EntityType.MAGMA_CUBE,
            EntityType.GHAST,
            EntityType.BLAZE,
            EntityType.WITHER_SKELETON,
            EntityType.ILLUSIONER,
            EntityType.PILLAGER,
            EntityType.VINDICATOR,
            EntityType.EVOKER,
            EntityType.VEX,
            EntityType.RAVAGER
    );

    @Override
    public String getName() {
        return "MAX_HEALTH";
    }

    @Override
    public double getBaseWeight() {
        return 8;
    }

    @Override
    public double getMaxBoost() {
        return 2;
    }

    @Override
    public double getMinDifficulty() {
        return 35;
    }

    @Override
    public boolean canApply(LivingEntity entity) {
        return APPLICABLE_ENTITIES.contains(entity.getType()) &&
                entity.getAttribute(Attribute.MAX_HEALTH) != null;
    }

    @Override
    public void apply(LivingEntity entity, double difficulty, double boostRatio, DebugReport debugReport) {
        AttributeInstance attr = entity.getAttribute(Attribute.MAX_HEALTH);
        if (attr == null) return;

        double base = attr.getBaseValue();
        double multiplier = 1.0 + boostRatio;
        double newMaxHealth = base * multiplier;

        double weightCost = getBaseWeight() * (1.0 + (boostRatio / getMaxBoost()));

        attr.setBaseValue(newMaxHealth);
        entity.setHealth(newMaxHealth);

        debugReport.logModifier(this, base, newMaxHealth, weightCost);
    }
}
