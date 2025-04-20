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
 * This class modifies the oxygen bonus of certain mobs based on the difficulty level.
 */
public class OxygenBonusModifier implements MobModifier {
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
        return "OXYGEN_BONUS";
    }

    @Override
    public double getBaseWeight() {
        return 7;
    }

    @Override
    public double getMaxBoost() {
        return 2;
    }

    @Override
    public double getMinDifficulty() {
        return 5;
    }

    @Override
    public boolean canApply(LivingEntity entity) {
        return APPLICABLE_ENTITIES.contains(entity.getType()) &&
                DifficultyCurveUtil.ensureAttributeExists(entity, Attribute.OXYGEN_BONUS, 1);
    }

    @Override
    public double apply(LivingEntity entity, double difficulty, double boostRatio, DebugReport debugReport) {
        AttributeInstance attr = entity.getAttribute(Attribute.OXYGEN_BONUS);
        if (attr == null) return 0;

        double base = DifficultyCurveUtil.ensureValidBaseValue(attr, 1);
        double multiplier = 1.0 + boostRatio;
        double newValue = base * multiplier;

        double weightCost = DifficultyCurveUtil.getSafeWeightCost(base, newValue, getBaseWeight(), boostRatio, getMaxBoost());

        attr.setBaseValue(newValue);
        debugReport.logModifier(this, base, newValue, weightCost);

        return weightCost;
    }
}
