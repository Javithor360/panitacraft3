package com.panita.panitacraft3.difficulty.modifiers;

import com.panita.panitacraft3.difficulty.debug.DebugReport;
import com.panita.panitacraft3.difficulty.util.DifficultyCurveUtil;
import org.bukkit.entity.LivingEntity;

/**
 * This interface represents a modifier that can be applied to mobs in the game.
 * It defines methods to get the name, base weight, maximum boost, and minimum difficulty of the modifier,
 * as well as methods to check if it can be applied to a specific entity and to apply the modifier.
 */
public interface MobModifier {
    String getName();
    double getBaseWeight();
    double getMaxBoost();
    double getMinDifficulty();
    boolean canApply(LivingEntity entity);
    double apply(LivingEntity entity, double difficulty, double boostRatio, DebugReport report);

    default double generateBoost(double difficulty) {
        return DifficultyCurveUtil.getBoostRatio(difficulty, getMaxBoost(), 4.5);
    }
}
