package com.cranks.dungeons.item;

import net.minecraft.util.Formatting;

public enum CustomRarity {
    COMMON("Common", Formatting.WHITE),
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
}