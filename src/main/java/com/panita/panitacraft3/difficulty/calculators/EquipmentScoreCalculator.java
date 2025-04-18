package com.panita.panitacraft3.difficulty.calculators;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public class EquipmentScoreCalculator {

    private static final Map<String, Integer> CUSTOM_ARMOR_TIERS = Map.of(
            "common", 5,
            "uncommon", 10,
            "rare", 15,
            "epic", 25,
            "legendary", 35
    );

    private static final JavaPlugin PLUGIN = JavaPlugin.getProvidingPlugin(EquipmentScoreCalculator.class);

    public static int calculate(Player player) {
        int total = 0;

        // Iterate over armor contents
        for (ItemStack armor : player.getInventory().getArmorContents()) {
            total += evaluateItem(armor);
        }

        // Evaluate main hand and offhand
        total += evaluateItem(player.getInventory().getItemInMainHand());
        total += evaluateOffhand(player.getInventory().getItemInOffHand());

        return Math.min(total, 500);
    }

    private static int evaluateItem(ItemStack item) {
        if (item == null || item.getType().isAir()) return 0;

        int base = isCustomArmor(item)
                ? getCustomArmorScore(item)
                : getVanillaArmorScore(item);

        return base + getEnchantBonus(item);
    }

    private static boolean isCustomArmor(ItemStack item) {
        return item.getItemMeta() != null &&
                item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(PLUGIN, "isArmor"), PersistentDataType.BOOLEAN);
    }

    private static int getCustomArmorScore(ItemStack item) {
        if (item.getItemMeta() == null) return 0;
        var container = item.getItemMeta().getPersistentDataContainer();

        String tier = container.getOrDefault(
                new NamespacedKey(PLUGIN, "armorTier"),
                PersistentDataType.STRING,
                "common"
        ).toLowerCase();

        return CUSTOM_ARMOR_TIERS.getOrDefault(tier, 5);
    }

    private static int getVanillaArmorScore(ItemStack item) {
        Material mat = item.getType();

        if (mat.name().contains("NETHERITE")) return 30;
        if (mat.name().contains("DIAMOND")) return 20;
        if (mat.name().contains("IRON")) return 12;
        if (mat.name().contains("CHAINMAIL")) return 10;
        if (mat.name().contains("GOLDEN")) return 8;
        if (mat.name().contains("LEATHER")) return 5;

        if (isWeaponOrTool(mat)) return 25;

        return 3; // Non-considered armor
    }

    private static int evaluateOffhand(ItemStack item) {
        if (item == null || item.getType().isAir()) return 0;
        return switch (item.getType()) {
            case SHIELD -> 10;
            case TOTEM_OF_UNDYING -> 25;
            default -> isCustomArmor(item) ? getCustomArmorScore(item) : 5;
        };
    }

    private static boolean isWeaponOrTool(Material mat) {
        String name = mat.name();
        return name.endsWith("_SWORD") || name.endsWith("_AXE") ||
                name.endsWith("_PICKAXE") || name.endsWith("_SHOVEL") ||
                name.endsWith("_HOE") || name.contains("TRIDENT") ||
                name.contains("BOW") || name.contains("CROSSBOW") || name.contains("MACE");
    }

    private static int getEnchantBonus(ItemStack item) {
        return item.getEnchantments().values().stream()
                .mapToInt(integer -> integer * 3)
                .sum();
    }
}
