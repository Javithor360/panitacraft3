package com.panita.panitacraft3.difficulty.modifiers;

import com.panita.panitacraft3.difficulty.debug.DebugReport;
import com.panita.panitacraft3.difficulty.modifiers.pool.MaxHealthModifier;
import com.panita.panitacraft3.difficulty.util.DifficultyConfig;
import org.bukkit.entity.LivingEntity;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MobModifierPool {
    private static final List<MobModifier> MODIFIERS = List.of(
            new MaxHealthModifier()
    );

    public static void applyModifiers(LivingEntity entity, double difficulty) {
        if (!DifficultyConfig.isEnabled()) return;

        List<MobModifier> applicable = MODIFIERS.stream()
                .filter(mod -> mod.getMinDifficulty() <= difficulty && mod.canApply(entity))
                .collect(Collectors.toList());

        double weightBudget = difficulty;
        DebugReport debugReport = new DebugReport(entity, difficulty);

        Collections.shuffle(applicable);

        for (MobModifier mod : applicable) {
            double boost = mod.generateBoost(difficulty);

            double cost = mod.getBaseWeight() * (1.0 + (boost / mod.getMaxBoost()));
            if (weightBudget >= cost) {
                mod.apply(entity, difficulty, boost, debugReport);
                weightBudget -= cost;
            }
        }

        debugReport.dispatch();
    }
}
