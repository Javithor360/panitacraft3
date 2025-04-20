package com.panita.panitacraft3.difficulty.modifiers.pool.armor;

import com.panita.panitacraft3.difficulty.modifiers.pool.BaseArmorEquipModifier;
import com.panita.panitacraft3.difficulty.util.armor.ArmorPieceRegistry.ArmorSlot;

/**
 * This class represents a modifier that applies when a helmet is equipped.
 * It extends the BaseArmorEquipModifier class and sets specific values for helmets.
 */
public class HelmetEquipModifier extends BaseArmorEquipModifier {
    public HelmetEquipModifier() {
        super(
                ArmorSlot.HELMET,
                22.0,
                50.0,
                "HELMET_EQUIP"
        );
    }
}