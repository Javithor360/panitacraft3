package com.panita.panitacraft3.difficulty.modifiers.pool;

import com.panita.panitacraft3.difficulty.debug.DebugReport;
import com.panita.panitacraft3.difficulty.modifiers.MobModifier;
import com.panita.panitacraft3.difficulty.util.DifficultyCurveUtil;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.Set;

/**
 * This class modifies the knockback resistance of certain mobs based on the difficulty level.
 */
public class KnockbackResistanceModifier implements MobModifier {
    // A set of entity types that this modifier can be applied to.
    private static final Set<EntityType> APPLICABLE_ENTITIES = Set.of(
            EntityType.ZOMBIE,
            EntityType.HUSK,
            EntityType.DROWNED,
            EntityType.ZOMBIE_VILLAGER,
            EntityType.ZOMBIFIED_PIGLIN,
            EntityType.PIGLIN,
            EntityType.CAVE_SPIDER,
            EntityType.ENDERMAN,
            EntityType.IRON_GOLEM,
            EntityType.POLAR_BEAR,
            EntityType.SPIDER,
            EntityType.BLAZE,
            EntityType.BOGGED,
            EntityType.BREEZE,
            EntityType.CREAKING,
            EntityType.ELDER_GUARDIAN,
            EntityType.ENDERMITE,
            EntityType.GUARDIAN,
            EntityType.HOGLIN,
            EntityType.SHULKER,
            EntityType.SILVERFISH,
            EntityType.WARDEN,
            EntityType.ZOGLIN,
            EntityType.GIANT,
            EntityType.SKELETON_HORSE,
            EntityType.ZOMBIE_HORSE,
            EntityType.SKELETON,
            EntityType.STRAY,
            EntityType.CREEPER,
            EntityType.WITCH,
            EntityType.PHANTOM,
            EntityType.SLIME,
            EntityType.MAGMA_CUBE,
            EntityType.GHAST,
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
        return "KNOCKBACK_RESISTANCE";
    }

    @Override
    public double getBaseWeight() {
        return 6;
    }

    @Override
    public double getMaxBoost() {
        return 1;
    }

    @Override
    public double getMinDifficulty() {
        return 75;
    }

    @Override
    public boolean canApply(LivingEntity entity) {
        return APPLICABLE_ENTITIES.contains(entity.getType()) &&
                DifficultyCurveUtil.ensureAttributeExists(entity, Attribute.KNOCKBACK_RESISTANCE, 0.1);
    }

    @Override
    public void apply(LivingEntity entity, double difficulty, double boostRatio, DebugReport debugReport) {
        AttributeInstance attr = entity.getAttribute(Attribute.KNOCKBACK_RESISTANCE);
        if (attr == null) return;

        double base = DifficultyCurveUtil.ensureValidBaseValue(attr, 0.1);
        double newValue = DifficultyCurveUtil.getNumberFromRange(difficulty, 0, getMaxBoost());

        double weightCost = DifficultyCurveUtil.getSafeWeightCost(base, newValue, getBaseWeight(), boostRatio, getMaxBoost());

        attr.setBaseValue(newValue);
        debugReport.logModifier(this, base, newValue, weightCost);
    }
}
