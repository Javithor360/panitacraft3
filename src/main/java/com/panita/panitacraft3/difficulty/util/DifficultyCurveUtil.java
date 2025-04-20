package com.panita.panitacraft3.difficulty.util;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.Set;

/**
 * Utility class for difficulty curve calculations.
 * This class provides methods to normalize difficulty values,
 * calculate boost ratios, and compute weight costs based on difficulty.
 */
public class DifficultyCurveUtil {
    private static final double SIGNIFICANT_CHANGE_THRESHOLD = 0.01;

    /**
     * Normalizes the difficulty value to a range of 0.0 to 1.0.
     * The formula used is:
     * <pre>
     *   normalizedDifficulty = min(difficulty / 100, 1.0)
     * </pre>
     *
     * @param difficulty The difficulty value to normalize.
     * @return The normalized difficulty value.
     */
    public static double normalize(double difficulty) {
        return Math.min(difficulty / 100, 1.0);
    }

    /**
     * Calculates the boost ratio based on difficulty, maximum boost, and curve factor.
     * The formula used is:
     * <pre>
     *   boostRatio = maxBoost * (1 - exp(curveFactor * (1 - normalizedDifficulty)))
     * </pre>
     *
     * @param difficulty  The difficulty value to normalize.
     * @param maxBoost    The maximum boost value.
     * @param curveFactor The curve factor for the calculation.
     * @return The calculated boost ratio.
     */
    public static double getBoostRatio(double difficulty, double maxBoost, double curveFactor) {
        double norm = normalize(difficulty);
        double exponent = Math.exp(curveFactor * (1.0 - norm));
        double skewed = Math.pow(Math.random(), exponent);
        return skewed * maxBoost;
    }

    /**
     * Calculates the weight cost based on the base weight, boost ratio, and maximum boost.
     * The formula used is:
     * <pre>
     *   cost = baseWeight * (1 + ((boostRatio / maxBoost) ^ 1.5) * 2)
     * </pre>
     *
     * @param baseWeight The base weight of the attribute.
     * @param boostRatio The boost ratio calculated from difficulty.
     * @param maxBoost   The maximum boost value.
     * @return The calculated weight cost.
     */
    public static double calculateWeightCost(double baseWeight, double boostRatio, double maxBoost) {
        if (boostRatio <= 0 || maxBoost <= 0) return 0;

        double ratio = boostRatio / maxBoost;
        double scaledCost = Math.pow(ratio, 1.5);
        return baseWeight * (1.0 + (scaledCost * 2));
    }

    /**
     * Checks if the new value is significantly different from the base.
     */
    public static boolean isChangeSignificant(double base, double newValue) {
        return Math.abs(newValue - base) >= SIGNIFICANT_CHANGE_THRESHOLD;
    }

    /**
     * Ensures the attribute value is valid and returns a safe base value.
     * If the value is NaN or too low, sets it to a minimum.
     */
    public static double ensureValidBaseValue(AttributeInstance attr, double minValue) {
        double base = attr.getBaseValue();
        if (Double.isNaN(base) || base <= 0.0) {
            attr.setBaseValue(minValue);
            return minValue;
        }
        return base;
    }

    /**
     * Calculates weight cost, returning 0 if not significant.
     */
    public static double getSafeWeightCost(double baseValue, double newValue, double baseWeight, double boostRatio, double maxBoost) {
        if (!isChangeSignificant(baseValue, newValue)) {
            return 0.0;
        }
        return calculateWeightCost(baseWeight, boostRatio, maxBoost);
    }

    /**
     * Checks if the entity is applicable for the given attribute.
     * It checks if the entity type is in the valid types and if the attribute exists.
     *
     * @param entity      The entity to check.
     * @param validTypes  The set of valid entity types.
     * @param attribute   The attribute to check.
     * @return True if applicable, false otherwise.
     */
    public static boolean isApplicableEntity(LivingEntity entity, Set<EntityType> validTypes, Attribute attribute) {
        if (!validTypes.contains(entity.getType())) return false;

        AttributeInstance attr = entity.getAttribute(attribute);
        return attr != null;
    }

    /**
     * Ensures the attribute exists for the entity and sets a default value if not.
     * It tries to register the attribute and set its base value.
     *
     * @param entity      The entity to check.
     * @param attribute   The attribute to ensure.
     * @param defaultValue The default value to set if the attribute doesn't exist.
     * @return True if the attribute exists or was successfully created, false otherwise.
     */
    public static boolean ensureAttributeExists(LivingEntity entity, Attribute attribute, double defaultValue) {
        AttributeInstance attr = entity.getAttribute(attribute);

        if (attr == null) {
            try {
                entity.registerAttribute(attribute);
                attr = entity.getAttribute(attribute);
                if (attr != null) {
                    attr.setBaseValue(defaultValue);
                    return true;
                }
            } catch (Exception ignored) {
                return false;
            }
        }
        return true;
    }

    /**
     * Generates a scale value based on the difficulty.
     * The formula used is:
     * <pre>
     *   scaleValue = min + (max - min) * skewed
     * </pre>
     *
     * @param difficulty The difficulty value to normalize.
     * @return The generated scale value.
     */
    public static double getNumberFromRange(double difficulty, double min, double max) {
        double norm = normalize(difficulty);
        double exponent = Math.exp(5.0 * (1.0 - norm));
        double skewed = 1.0 - Math.pow(Math.random(), exponent);
        return min + (max - min) * skewed;
    }
}
