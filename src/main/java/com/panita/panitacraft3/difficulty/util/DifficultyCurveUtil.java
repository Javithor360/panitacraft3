package com.panita.panitacraft3.difficulty.util;

import com.panita.panitacraft3.difficulty.DifficultyService;
import com.panita.panitacraft3.difficulty.util.DifficultyConfig;

public class DifficultyCurveUtil {
    public static double normalize(double difficulty) {
        return Math.min(difficulty / 100, 1.0);
    }

    public static double getBoostRatio(double difficulty, double maxBoost, double curveFactor) {
        double norm = normalize(difficulty);
        double exponent = Math.exp(curveFactor * (1.0 - norm));
        double skewed = Math.pow(Math.random(), exponent);
        return skewed * maxBoost;
    }
}
