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
 * This class modifies the maximum health of certain mobs based on the difficulty level.
 */
public class ScaleModifier implements MobModifier {
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
    public void apply(LivingEntity entity, double difficulty, double boostRatio, DebugReport debugReport) {
        AttributeInstance attr = entity.getAttribute(Attribute.SCALE);
        if (attr == null) return;

        double base = DifficultyCurveUtil.ensureValidBaseValue(attr, 1.0);
        double newScale = DifficultyCurveUtil.getScaleValue(difficulty);

        double relativeBoost = Math.abs(newScale - 1.0);
        double weightCost = DifficultyCurveUtil.getSafeWeightCost(base, newScale, getBaseWeight(), relativeBoost, getMaxBoost());

        attr.setBaseValue(newScale);
        debugReport.logModifier(this, base, newScale, weightCost);
    }
}
