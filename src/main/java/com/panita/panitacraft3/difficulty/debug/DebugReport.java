package com.panita.panitacraft3.difficulty.debug;

import com.panita.panitacraft3.difficulty.modifiers.MobModifier;
import com.panita.panitacraft3.difficulty.util.DifficultyConfig;
import com.panita.panitacraft3.difficulty.util.DifficultyDebugUtil;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class DebugReport {
    private final LivingEntity entity;
    private final double difficulty;
    private final List<AppliedModifierInfo> appliedModifiers = new ArrayList<>();
    private double totalUsedWeight = 0;

    public DebugReport(LivingEntity entity, double difficulty) {
        this.entity = entity;
        this.difficulty = difficulty;
    }

    public void logModifier(MobModifier modifier, double before, double after, double weightCost) {
        appliedModifiers.add(new AppliedModifierInfo(modifier.getName(), before, after, weightCost));
        totalUsedWeight += weightCost;
    }

    public boolean hasData() {
        return !appliedModifiers.isEmpty();
    }

    public void dispatch() {
        if (!hasData()) return;

        double normalized = Math.min(difficulty / DifficultyConfig.getMaxDifficultyScale(), 1.0);

        String header = String.format(
                "<gray>Mob <yellow>%s</yellow> modificado.</gray> <green>Dificultad:</green> <white>%.2f</white> <gray>(Norm: %.2f)</gray>",
                entity.getType().name(), difficulty, normalized
        );
        DifficultyDebugUtil.sendDebugMessage(entity.getLocation(), header);

        for (AppliedModifierInfo info : appliedModifiers) {
            double percent = ((info.after - info.before) / info.before) * 100.0;

            String message = String.format(
                    "<aqua>[%s]</aqua> <white>%.1f</white> → <green>%.1f</green> <gray>(+%.1f%%)</gray> <dark_gray>|</dark_gray> <gray>Coste:</gray> <white>%.2f</white>",
                    info.name, info.before, info.after, percent, info.weightCost
            );

            DifficultyDebugUtil.sendDebugMessage(entity.getLocation(), message);
        }

        DifficultyDebugUtil.sendDebugMessage(entity.getLocation(),
                String.format("<gray>Peso asignado:</gray> <white>%.2f</white> <gray>| Usado:</gray> <green>%.2f</green>",
                        difficulty, totalUsedWeight
                ));
    }

    private static class AppliedModifierInfo {
        String name;
        double before;
        double after;
        double weightCost;

        AppliedModifierInfo(String name, double before, double after, double weightCost) {
            this.name = name;
            this.before = before;
            this.after = after;
            this.weightCost = weightCost;
        }
    }
}
