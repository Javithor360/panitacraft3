package com.panita.panitacraft3.difficulty.modifiers;

import com.panita.panitacraft3.difficulty.debug.DebugReport;
import com.panita.panitacraft3.difficulty.modifiers.pool.*;
import com.panita.panitacraft3.difficulty.modifiers.pool.armor.BootsEquipModifier;
import com.panita.panitacraft3.difficulty.modifiers.pool.armor.ChestplateEquipModifier;
import com.panita.panitacraft3.difficulty.modifiers.pool.armor.HelmetEquipModifier;
import com.panita.panitacraft3.difficulty.modifiers.pool.armor.LeggingsEquipModifier;
import com.panita.panitacraft3.difficulty.util.DifficultyConfig;
import org.bukkit.entity.LivingEntity;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MobModifierPool {
    private static final List<MobModifier> MODIFIERS = List.of(
            // Vanilla attribute modifiers
            new ArmorModifier(),
            new ArmorToughnessModifier(),
            new AttackDamageModifier(),
            new AttackKnockbackModifier(),
            new FollowRangeModifier(),
            new KnockbackResistanceModifier(),
            new MovementSpeedModifier(),
            new MaxHealthModifier(),
            new OxygenBonusModifier(),
            new SafeFallDistanceModifier(),
            new StepHeightModifier(),
            new ScaleModifier(),
            new WaterMovementEfficiency(),
            // Equipment modifiers
            new HelmetEquipModifier(),
            new ChestplateEquipModifier(),
            new LeggingsEquipModifier(),
            new BootsEquipModifier()
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
            if (weightBudget < mod.getBaseWeight()) continue;

            if (mod.getMaxBoost() > 0) {
                double boost = mod.generateBoost(difficulty);
                if (boost <= 0.0) continue;

                double estimatedCost = mod.getBaseWeight() * (1.0 + (boost / mod.getMaxBoost()));
                if (estimatedCost <= 0 || weightBudget < estimatedCost) continue;

                double finalCost = mod.apply(entity, difficulty, boost, debugReport);
                weightBudget -= finalCost;
            } else {
                double finalCost = mod.apply(entity, difficulty, 0.0, debugReport);
                weightBudget -= finalCost;
            }
        }

        debugReport.dispatch();
    }
}
