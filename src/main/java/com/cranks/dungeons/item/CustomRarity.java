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

        if (stack.isOf(Items.ELYTRA) || stack.isOf(Items.TRIDENT) || stack.isOf(Items.MACE)) {
            return UNIQUE;
        }

        String itemName = stack.getItem().toString().toLowerCase();

        if (itemName.contains("diamond") || itemName.contains("netherite")) {
            if (isEquipment(itemName)) {
                return RARE;
            }
        }

        return COMMON;
    }

    private static boolean isEquipment(String name) {
        return name.contains("sword") || name.contains("axe") || name.contains("pickaxe") ||
                name.contains("shovel") || name.contains("hoe") || name.contains("helmet") ||
                name.contains("chestplate") || name.contains("leggings") || name.contains("boots") ||
                name.contains("spear");
    }
}