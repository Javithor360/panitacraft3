package com.panita.panitacraft3.difficulty.modifiers.pool.armor;

import com.panita.panitacraft3.difficulty.modifiers.pool.BaseArmorEquipModifier;
import com.panita.panitacraft3.difficulty.util.armor.ArmorPieceRegistry.ArmorSlot;

/**
 * This class represents a modifier that applies when leggings are equipped.
 * It extends the BaseArmorEquipModifier class and sets specific values for leggings.
 */
public class LeggingsEquipModifier extends BaseArmorEquipModifier {
    public LeggingsEquipModifier() {
        super(
                ArmorSlot.LEGGINGS,
                24.0,
                50.0,
                "LEGGINGS_EQUIP"
        );
    }
}