package com.cranks.dungeons.item;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Formatting;

public enum CustomRarity {
    COMMON("Common", Formatting.GRAY),
    RARE("Rare", Formatting.GREEN),
    UNIQUE("Unique", Formatting.GOLD);

    private final String name;
    private final Formatting color;

    CustomRarity(String name, Formatting color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Formatting getColor() {
        return color;
    }

    public static CustomRarity getRarity(ItemStack stack) {
        if (stack.isEmpty()) return COMMON;

        // 1. Check for Uniques FIRST
        if (stack.isOf(Items.ELYTRA) || stack.isOf(Items.TRIDENT) || stack.isOf(Items.MACE)) {
            return UNIQUE;
        }

        // 2. Check for Diamond/Netherite Equipment
        String itemName = stack.getItem().toString().toLowerCase();
        if (itemName.contains("diamond") || itemName.contains("netherite")) {
            if (isEquipment(itemName)) {
                return RARE;
            }
        }

        return COMMON;
    }

    private static boolean isEquipment(String name) {
        return name.endsWith("sword") || name.endsWith("axe") || name.endsWith("pickaxe") ||
                name.endsWith("shovel") || name.endsWith("hoe") || name.endsWith("helmet") ||
                name.endsWith("chestplate") || name.endsWith("leggings") || name.endsWith("boots") ||
                name.endsWith("spear") || name.endsWith("elytra") || name.endsWith("trident") ||
                name.endsWith("mace");
    }
}