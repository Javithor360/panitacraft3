package com.panita.panitacraft3.difficulty.modifiers.pool.armor;

import com.panita.panitacraft3.difficulty.modifiers.pool.BaseArmorEquipModifier;
import com.panita.panitacraft3.difficulty.util.armor.ArmorPieceRegistry.ArmorSlot;

/**
 * This class represents a modifier that applies when a chestplate is equipped.
 * It extends the BaseArmorEquipModifier class and sets specific values for chestplates.
 */
public class ChestplateEquipModifier extends BaseArmorEquipModifier {
    public ChestplateEquipModifier() {
        super(
                ArmorSlot.CHESTPLATE,
                25.0,
                50.0,
                "CHESTPLATE_EQUIP"
        );
    }
}