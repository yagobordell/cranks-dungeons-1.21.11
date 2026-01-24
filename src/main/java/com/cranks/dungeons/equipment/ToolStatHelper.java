package com.cranks.dungeons.equipment;

import net.minecraft.item.ItemStack;

/**
 * Helper utility for getting equipment-specific stats from items.
 * Reduces code duplication in mixins by centralizing the stat lookup logic.
 */
public class ToolStatHelper {

    /**
     * Gets a stat value from a tool by checking all possible equipment-type variants.
     * For example, getToolStat(stack, "mining_efficiency") checks:
     * - pickaxe_mining_efficiency
     * - axe_mining_efficiency
     * - shovel_mining_efficiency
     * - hoe_mining_efficiency
     *
     * @param stack The item stack to check
     * @param baseStat The base stat name (without equipment prefix) - e.g., "mining_efficiency"
     * @return The stat value, or 0 if not found
     */
    public static double getToolStat(ItemStack stack, String baseStat) {
        if (stack.isEmpty()) {
            return 0;
        }

        // Just use getItemStatValue - it already handles the equipment prefix matching
        return EquipmentStatApplier.getItemStatValue(stack, baseStat);
    }

    /**
     * Gets a specific equipment-type stat value.
     * More explicit version for cases where you know the equipment type.
     */
    public static double getEquipmentStat(ItemStack stack, EquipmentType equipmentType, String baseStat) {
        if (stack.isEmpty()) {
            return 0;
        }

        String statId = equipmentType.name().toLowerCase() + "_" + baseStat;
        return EquipmentStatApplier.getItemStatValue(stack, statId);
    }

    /**
     * Convenience method for mining efficiency.
     */
    public static double getMiningEfficiency(ItemStack stack) {
        return getToolStat(stack, "mining_efficiency");
    }

    /**
     * Convenience method for fortune.
     */
    public static double getFortune(ItemStack stack) {
        return getToolStat(stack, "fortune");
    }

    /**
     * Convenience method for durability bonus.
     */
    public static double getDurabilityBonus(ItemStack stack) {
        return getToolStat(stack, "durability_bonus");
    }

    /**
     * Convenience method for precision mining.
     */
    public static double getPrecisionMining(ItemStack stack) {
        return getToolStat(stack, "repair_chance");
    }

    /**
     * Convenience method for breaking range.
     */
    public static double getBreakingRange(ItemStack stack) {
        return getToolStat(stack, "breaking_range");
    }
}