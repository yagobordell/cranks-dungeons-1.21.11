package com.cranks.dungeons.stat;

import com.cranks.dungeons.equipment.EquipmentType;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Formatting;

import java.text.DecimalFormat;
import java.util.Random;

public class CustomStat {
    private final String id;
    private final String displayName;
    private final EquipmentType equipmentType;
    private final RegistryEntry<EntityAttribute> attribute;
    private final double[] normalRanges;
    private final double[] uncommonRanges;
    private final boolean isPercentage;
    private final boolean displayAsPercentage;
    private final Formatting color;

    // Constructor with all parameters
    public CustomStat(String id, String displayName, EquipmentType equipmentType,
                      RegistryEntry<EntityAttribute> attribute,
                      double[] normalRanges, double[] uncommonRanges,
                      boolean isPercentage, boolean displayAsPercentage,
                      Formatting color) {
        this.id = id;
        this.displayName = displayName;
        this.equipmentType = equipmentType;
        this.attribute = attribute;
        this.normalRanges = normalRanges;
        this.uncommonRanges = uncommonRanges;
        this.isPercentage = isPercentage;
        this.displayAsPercentage = displayAsPercentage;
        this.color = color;
    }

    // Constructor for non-percentage stats
    public CustomStat(String id, String displayName, EquipmentType equipmentType,
                      RegistryEntry<EntityAttribute> attribute,
                      double[] normalRanges, double[] uncommonRanges,
                      boolean isPercentage, Formatting color) {
        this(id, displayName, equipmentType, attribute, normalRanges, uncommonRanges,
                isPercentage, false, color);
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public RegistryEntry<EntityAttribute> getAttribute() {
        return attribute;
    }

    public double rollValue(int tier) {
        if (tier < 1 || tier > 5) {
            throw new IllegalArgumentException("Tier must be between 1 and 5");
        }

        Random random = new Random();
        double min, max;

        if (tier <= 2) {
            min = normalRanges[tier - 1];
            max = uncommonRanges[tier - 1];
        } else {
            min = normalRanges[tier - 1];
            max = uncommonRanges[tier - 1];
        }

        double value = min + (max - min) * random.nextDouble();

        return Math.round(value * 1000.0) / 1000.0;
    }

    public String formatValue(double value) {
        DecimalFormat df;

        if (displayAsPercentage) {
            df = new DecimalFormat("#.#");
            return "+" + df.format(value * 100) + "% " + displayName;
        } else if (isPercentage) {
            df = new DecimalFormat("#.##");
            return "+" + df.format(value) + " " + displayName;
        } else {
            df = new DecimalFormat("#.#");
            return "+" + df.format(value) + " " + displayName;
        }
    }

    public Formatting getColor() {
        return color;
    }
}