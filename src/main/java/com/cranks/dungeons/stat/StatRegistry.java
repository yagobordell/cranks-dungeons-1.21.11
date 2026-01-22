package com.cranks.dungeons.stat;

import com.cranks.dungeons.registry.ModAttributes;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.Formatting;

import java.util.*;
import java.util.stream.Collectors;

public class StatRegistry {
    private static final Map<String, CustomStat> STATS = new HashMap<>();

    public static void registerStats() {
        register(new CustomStat(
                "attack_damage",
                "Attack Damage",
                StatCategory.OFFENSIVE,
                EntityAttributes.ATTACK_DAMAGE,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 2.5, 4.5, 7.5, 12.0},
                false,
                Formatting.WHITE
        ));

        register(new CustomStat(
                "attack_speed",
                "Attack Speed",
                StatCategory.OFFENSIVE,
                ModAttributes.ATTACK_SPEED,
                new double[]{0.10, 0.20, 0.30, 0.40, 0.50},
                new double[]{0.18, 0.28, 0.38, 0.48, 0.58},
                true,
                true,
                Formatting.YELLOW
        ));

        register(new CustomStat(
                "crit_chance",
                "Critical Strike Chance",
                StatCategory.OFFENSIVE,
                ModAttributes.CRIT_CHANCE,
                new double[]{0.01, 0.03, 0.06, 0.10, 0.15},
                new double[]{0.02, 0.05, 0.09, 0.14, 0.22},
                true,
                true,
                Formatting.GOLD
        ));

        register(new CustomStat(
                "knockback",
                "Knockback",
                StatCategory.OFFENSIVE,
                ModAttributes.KNOCKBACK,
                new double[]{0.2, 0.4, 0.6, 0.9, 1.2},
                new double[]{0.3, 0.6, 0.9, 1.3, 1.8},
                false,
                Formatting.DARK_GREEN
        ));

        register(new CustomStat(
                "fire_damage",
                "Fire Damage",
                StatCategory.OFFENSIVE,
                ModAttributes.FIRE_DAMAGE,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 3.0, 5.5, 9.0, 14.0},
                false,
                Formatting.RED
        ));

        register(new CustomStat(
                "cold_damage",
                "Cold Damage",
                StatCategory.OFFENSIVE,
                ModAttributes.COLD_DAMAGE,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 3.0, 5.5, 9.0, 14.0},
                false,
                Formatting.AQUA
        ));

        register(new CustomStat(
                "lightning_damage",
                "Lightning Damage",
                StatCategory.OFFENSIVE,
                ModAttributes.LIGHTNING_DAMAGE,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 3.0, 5.5, 9.0, 14.0},
                false,
                Formatting.DARK_BLUE
        ));

        register(new CustomStat(
                "void_damage",
                "Void Damage",
                StatCategory.OFFENSIVE,
                ModAttributes.VOID_DAMAGE,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 3.0, 5.5, 9.0, 14.0},
                false,
                Formatting.DARK_PURPLE
        ));

        register(new CustomStat(
                "life_steal",
                "Life Steal",
                StatCategory.OFFENSIVE,
                ModAttributes.LIFE_STEAL,
                new double[]{0.01, 0.03, 0.05, 0.08, 0.12},
                new double[]{0.02, 0.05, 0.08, 0.12, 0.18},
                true,
                true,
                Formatting.DARK_RED
        ));

        register(new CustomStat(
                "chance_to_burn",
                "Chance to burn",
                StatCategory.OFFENSIVE,
                ModAttributes.CHANCE_TO_BURN,
                new double[]{0.05, 0.12, 0.20, 0.30, 0.42},
                new double[]{0.10, 0.20, 0.30, 0.45, 0.65},
                true,
                true,
                Formatting.RED
        ));

        register(new CustomStat(
                "attack_range",
                "Attack Range",
                StatCategory.OFFENSIVE,
                ModAttributes.ATTACK_RANGE,
                new double[]{0.2, 0.5, 0.8, 1.2, 1.6},
                new double[]{0.4, 0.8, 1.2, 1.8, 2.5},
                false,
                Formatting.GREEN
        ));

        register(new CustomStat(
                "armor",
                "Armor",
                StatCategory.DEFENSIVE,
                EntityAttributes.ARMOR,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 2.5, 4.5, 7.5, 12.0},
                false,
                Formatting.WHITE
        ));

        register(new CustomStat(
                "max_health",
                "Max Health",
                StatCategory.DEFENSIVE,
                EntityAttributes.MAX_HEALTH,
                new double[]{1.0, 2.0, 4.5, 5.5, 8.0},
                new double[]{1.5, 3.0, 5.0, 7.5, 12.0},
                false,
                Formatting.DARK_RED
        ));

        register(new CustomStat(
                "armor_toughness",
                "Armor Toughness",
                StatCategory.DEFENSIVE,
                EntityAttributes.ARMOR_TOUGHNESS,
                new double[]{0.3, 0.8, 1.5, 2.5, 4.0},
                new double[]{0.6, 1.4, 2.4, 4.0, 6.0},
                false,
                Formatting.GRAY
        ));

        register(new CustomStat(
                "knockback_resistance",
                "Knockback Resistance",
                StatCategory.DEFENSIVE,
                EntityAttributes.KNOCKBACK_RESISTANCE,
                new double[]{0.05, 0.12, 0.20, 0.30, 0.42},
                new double[]{0.10, 0.18, 0.28, 0.40, 0.60},
                true,
                true,
                Formatting.DARK_GRAY
        ));

        register(new CustomStat(
                "fire_resistance",
                "Fire Resistance",
                StatCategory.DEFENSIVE,
                ModAttributes.FIRE_RESISTANCE,
                new double[]{0.18, 0.20, 0.23, 0.26, 0.30},
                new double[]{0.22, 0.25, 0.28, 0.32, 0.38},
                true,
                true,
                Formatting.RED
        ));

        register(new CustomStat(
                "cold_resistance",
                "Cold Resistance",
                StatCategory.DEFENSIVE,
                ModAttributes.COLD_RESISTANCE,
                new double[]{0.18, 0.20, 0.23, 0.26, 0.30},
                new double[]{0.22, 0.25, 0.28, 0.32, 0.38},
                true,
                true,
                Formatting.AQUA
        ));

        register(new CustomStat(
                "lightning_resistance",
                "Lightning Resistance",
                StatCategory.DEFENSIVE,
                ModAttributes.LIGHTNING_RESISTANCE,
                new double[]{0.18, 0.20, 0.23, 0.26, 0.30},
                new double[]{0.22, 0.25, 0.28, 0.32, 0.38},
                true,
                true,
                Formatting.DARK_BLUE
        ));

        register(new CustomStat(
                "void_resistance",
                "Void Resistance",
                StatCategory.DEFENSIVE,
                ModAttributes.VOID_RESISTANCE,
                new double[]{0.18, 0.20, 0.23, 0.26, 0.30},
                new double[]{0.22, 0.25, 0.28, 0.32, 0.38},
                true,
                true,
                Formatting.DARK_PURPLE
        ));

        register(new CustomStat(
                "life_regeneration",
                "Life Regeneration",
                StatCategory.DEFENSIVE,
                ModAttributes.LIFE_REGENERATION,
                new double[]{0.1, 0.25, 0.5, 0.8, 1.2},
                new double[]{0.2, 0.45, 0.75, 1.2, 2.0},
                false,
                Formatting.DARK_RED
        ));

        register(new CustomStat(
                "experience_bonus_defensive",
                "Experience Bonus",
                StatCategory.DEFENSIVE,
                ModAttributes.EXPERIENCE_BONUS,
                new double[]{0.05, 0.12, 0.20, 0.30, 0.42},
                new double[]{0.10, 0.18, 0.28, 0.42, 0.60},
                true,
                true,
                Formatting.GREEN
        ));

        register(new CustomStat(
                "luck_defensive",
                "Luck",
                StatCategory.DEFENSIVE,
                ModAttributes.LUCK,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 2.5, 4.5, 7.5, 12.0},
                false,
                Formatting.GOLD
        ));

        register(new CustomStat(
                "experience_bonus_utility",
                "Experience Bonus",
                StatCategory.UTILITY,
                ModAttributes.EXPERIENCE_BONUS,
                new double[]{0.05, 0.12, 0.20, 0.30, 0.42},
                new double[]{0.10, 0.18, 0.28, 0.42, 0.60},
                true,
                true,
                Formatting.GREEN
        ));

        register(new CustomStat(
                "luck_utility",
                "Luck",
                StatCategory.UTILITY,
                ModAttributes.LUCK,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 2.5, 4.5, 7.5, 12.0},
                false,
                Formatting.GOLD
        ));

        register(new CustomStat(
                "movement_speed",
                "Movement Speed",
                StatCategory.UTILITY,
                EntityAttributes.MOVEMENT_SPEED,
                new double[]{0.01, 0.025, 0.045, 0.07, 0.10},
                new double[]{0.02, 0.04, 0.065, 0.095, 0.125},
                true,
                true,
                Formatting.GREEN
        ));

        register(new CustomStat(
                "feather_falling",
                "Feather Falling",
                StatCategory.UTILITY,
                ModAttributes.FEATHER_FALLING,
                new double[]{0.10, 0.18, 0.28, 0.40, 0.55},
                new double[]{0.15, 0.25, 0.38, 0.55, 0.75},
                true,
                true,
                Formatting.WHITE
        ));

        register(new CustomStat(
                "mining_efficiency",
                "Mining Efficiency",
                StatCategory.TOOL,
                ModAttributes.MINING_EFFICIENCY,
                new double[]{0.08, 0.15, 0.23, 0.32, 0.42},
                new double[]{0.12, 0.22, 0.32, 0.45, 0.60},
                true,
                true,
                Formatting.GRAY
        ));

        register(new CustomStat(
                "fortune",
                "Fortune",
                StatCategory.TOOL,
                ModAttributes.FORTUNE,
                new double[]{0.10, 0.18, 0.28, 0.40, 0.55},
                new double[]{0.15, 0.28, 0.40, 0.55, 0.75},
                true,
                true,
                Formatting.GOLD
        ));

        register(new CustomStat(
                "durability_bonus",
                "Durability Bonus",
                StatCategory.TOOL,
                ModAttributes.DURABILITY_BONUS,
                new double[]{0.10, 0.18, 0.28, 0.40, 0.55},
                new double[]{0.15, 0.25, 0.38, 0.55, 0.75},
                true,
                true,
                Formatting.BLUE
        ));

        register(new CustomStat(
                "precision_mining",
                "Precision Mining",
                StatCategory.TOOL,
                ModAttributes.PRECISION_MINING,
                new double[]{0.03, 0.08, 0.13, 0.18, 0.25},
                new double[]{0.05, 0.12, 0.18, 0.25, 0.35},
                true,
                true,
                Formatting.AQUA
        ));

        register(new CustomStat(
                "breaking_range",
                "Breaking Range",
                StatCategory.TOOL,
                ModAttributes.BREAKING_RANGE,
                new double[]{0.3, 0.6, 1.0, 1.4, 1.8},
                new double[]{0.5, 1.0, 1.5, 2.0, 2.8},
                false,
                Formatting.GREEN
        ));

        System.out.println("Registered " + STATS.size() + " custom stats");
    }

    private static void register(CustomStat stat) {
        STATS.put(stat.getId(), stat);
    }

    public static CustomStat getStat(String id) {
        return STATS.get(id);
    }

    public static Collection<CustomStat> getAllStats() {
        return STATS.values();
    }

    public static List<CustomStat> getStatsByCategory(StatCategory category) {
        return STATS.values().stream()
                .filter(stat -> stat.getCategory() == category)
                .collect(Collectors.toList());
    }

    public static CustomStat getRandomStatForCategory(StatCategory category) {
        List<CustomStat> stats = getStatsByCategory(category);
        return stats.isEmpty() ? null : stats.get(new Random().nextInt(stats.size()));
    }
}