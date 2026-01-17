package com.cranks.dungeons.stat;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class CustomStat {
    private final String id;
    private final String name;
    private final StatCategory category;
    private final RegistryEntry<EntityAttribute> attribute;
    private final double[] tierMinValues;
    private final double[] tierMaxValues;
    private final boolean isPercentage;
    private final Formatting color;

    public CustomStat(String id, String name, StatCategory category,
                      RegistryEntry<EntityAttribute> attribute,
                      double[] tierMinValues, double[] tierMaxValues,
                      boolean isPercentage, Formatting color) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.attribute = attribute;
        this.tierMinValues = tierMinValues;
        this.tierMaxValues = tierMaxValues;
        this.isPercentage = isPercentage;
        this.color = color;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public StatCategory getCategory() { return category; }
    public RegistryEntry<EntityAttribute> getAttribute() { return attribute; }
    public boolean isPercentage() { return isPercentage; }
    public Formatting getColor() { return color; }

    public double getMinValue(int tier) {
        return tierMinValues[Math.max(0, Math.min(tier - 1, 4))];
    }

    public double getMaxValue(int tier) {
        return tierMaxValues[Math.max(0, Math.min(tier - 1, 4))];
    }

    public double rollValue(int tier) {
        double min = getMinValue(tier);
        double max = getMaxValue(tier);
        return min + (Math.random() * (max - min));
    }

    public Text getFormattedValue(double value) {
        String formatted = isPercentage ?
                String.format("%.1f%%", value) :
                String.format("%.1f", value);
        return Text.literal("+" + formatted + " " + name).formatted(color);
    }
}