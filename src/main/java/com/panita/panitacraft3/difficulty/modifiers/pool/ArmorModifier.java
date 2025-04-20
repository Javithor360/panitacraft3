package com.panita.panitacraft3.difficulty.modifiers.pool;

import com.panita.panitacraft3.difficulty.debug.DebugReport;
import com.panita.panitacraft3.difficulty.modifiers.MobModifier;
import com.panita.panitacraft3.difficulty.util.DifficultyCurveUtil;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * This modifier class alters the armor value of certain mobs based on the difficulty level.
 * It implements the MobModifier interface and provides methods to apply the modifier.
 */
public class ArmorModifier implements MobModifier {

    // A set of entity types that this modifier can be applied to.
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
        return "ARMOR";
    }

    @Override
    public double getBaseWeight() {
        return 15;
    }

    @Override
    public double getMaxBoost() {
        return 12.45;
    }

    @Override
    public double getMinDifficulty() {
        return 50;
    }

    @Override
    public boolean canApply(LivingEntity entity) {
        return APPLICABLE_ENTITIES.contains(entity.getType()) &&
                DifficultyCurveUtil.ensureAttributeExists(entity, Attribute.ARMOR, 1.0);
    }

    @Override
    public void apply(LivingEntity entity, double difficulty, double boostRatio, DebugReport debugReport) {
        AttributeInstance attr = entity.getAttribute(Attribute.ARMOR);
        if (attr == null) return;

        double base = DifficultyCurveUtil.ensureValidBaseValue(attr, 1.0);
        double multiplier = 1.0 + boostRatio;
        double newValue = base * multiplier;

        double weightCost = DifficultyCurveUtil.getSafeWeightCost(base, newValue, getBaseWeight(), boostRatio, getMaxBoost());

        attr.setBaseValue(newValue);
        debugReport.logModifier(this, base, newValue, weightCost);
    }
}
