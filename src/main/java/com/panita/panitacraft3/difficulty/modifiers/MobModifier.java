package com.panita.panitacraft3.difficulty.modifiers;

import com.panita.panitacraft3.difficulty.debug.DebugReport;
import com.panita.panitacraft3.difficulty.util.DifficultyCurveUtil;
import org.bukkit.entity.LivingEntity;

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
