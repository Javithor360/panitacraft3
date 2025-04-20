package com.panita.panitacraft3.difficulty.modifiers.pool.armor;

import com.panita.panitacraft3.difficulty.modifiers.pool.BaseArmorEquipModifier;
import com.panita.panitacraft3.difficulty.util.armor.ArmorPieceRegistry.ArmorSlot;

/**
 * This class represents a modifier that applies when boots are equipped.
 * It extends the BaseArmorEquipModifier class and sets specific values for boots.
 */
public class BootsEquipModifier extends BaseArmorEquipModifier {
    public BootsEquipModifier() {
        super(
                ArmorSlot.BOOTS,
                23.0,
                50.0,
                "BOOTS_EQUIP"
        );
    }
}