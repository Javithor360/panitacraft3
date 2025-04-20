package com.panita.panitacraft3.difficulty.modifiers.pool;

import com.panita.panitacraft3.difficulty.debug.DebugReport;
import com.panita.panitacraft3.difficulty.modifiers.MobModifier;
import com.panita.panitacraft3.difficulty.util.DifficultyConfig;
import com.panita.panitacraft3.difficulty.util.DifficultyCurveUtil;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.Set;

/**
 * This class modifies the maximum health of certain mobs based on the difficulty level.
 */
public class ScaleModifier implements MobModifier {
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
        return "SCALE";
    }

    @Override
    public double getBaseWeight() {
        return 21;
    }

    @Override
    public double getMaxBoost() {
        return 0.75;
    }

    @Override
    public double getMinDifficulty() {
        return 100;
    }

    @Override
    public boolean canApply(LivingEntity entity) {
        return DifficultyCurveUtil.isApplicableEntity(entity, APPLICABLE_ENTITIES, Attribute.SCALE);
    }

    @Override
    public double apply(LivingEntity entity, double difficulty, double boostRatio, DebugReport debugReport) {
        AttributeInstance attr = entity.getAttribute(Attribute.SCALE);
        if (attr == null) return 0;

        double base = DifficultyCurveUtil.ensureValidBaseValue(attr, 1.0);
        double normDiff = Math.min(difficulty / DifficultyConfig.getMaxDifficultyScale(), 1.0);
        double minScale = normDiff > 0.75 ? 0.5 : 0.8;
        double maxScale = normDiff > 0.75 ? 1.75 : normDiff > 0.5 ? 1.5 : 1.25;
        double newScale = DifficultyCurveUtil.getNumberFromRange(difficulty, minScale, maxScale);

        double relativeBoost = Math.abs(newScale - 1.0);
        double weightCost = DifficultyCurveUtil.getSafeWeightCost(base, newScale, getBaseWeight(), relativeBoost, getMaxBoost());

        attr.setBaseValue(newScale);
        debugReport.logModifier(this, base, newScale, weightCost);

        return weightCost;
    }
}
