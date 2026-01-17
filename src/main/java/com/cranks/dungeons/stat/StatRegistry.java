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
                Formatting.RED
        ));

        register(new CustomStat(
                "attack_speed",
                "Attack Speed",
                StatCategory.OFFENSIVE,
                EntityAttributes.ATTACK_SPEED,
                new double[]{2.0, 5.0, 9.0, 14.0, 20.0},
                new double[]{4.0, 8.0, 13.0, 19.0, 28.0},
                true,
                Formatting.RED
        ));

        register(new CustomStat(
                "crit_chance",
                "Critical Strike Chance",
                StatCategory.OFFENSIVE,
                ModAttributes.CRIT_CHANCE,
                new double[]{1.0, 3.0, 6.0, 10.0, 15.0},
                new double[]{2.0, 5.0, 9.0, 14.0, 22.0},
                true,
                Formatting.GOLD
        ));

        register(new CustomStat(
                "armor",
                "Armor",
                StatCategory.DEFENSIVE,
                EntityAttributes.ARMOR,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 2.5, 4.5, 7.5, 12.0},
                false,
                Formatting.BLUE
        ));

        register(new CustomStat(
                "max_health",
                "Max Health",
                StatCategory.DEFENSIVE,
                EntityAttributes.MAX_HEALTH,
                new double[]{2.0, 4.0, 7.0, 11.0, 16.0},
                new double[]{3.0, 6.0, 10.0, 15.0, 24.0},
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
                Formatting.BLUE
        ));

        register(new CustomStat(
                "knockback_resistance",
                "Knockback Resistance",
                StatCategory.DEFENSIVE,
                EntityAttributes.KNOCKBACK_RESISTANCE,
                new double[]{5.0, 12.0, 20.0, 30.0, 42.0},
                new double[]{10.0, 18.0, 28.0, 40.0, 60.0},
                true,
                Formatting.DARK_BLUE
        ));

        register(new CustomStat(
                "fire_resistance",
                "Fire Resistance",
                StatCategory.DEFENSIVE,
                ModAttributes.FIRE_RESISTANCE,
                new double[]{18.0, 20.0, 23.0, 26.0, 30.0},
                new double[]{22.0, 25.0, 28.0, 32.0, 38.0},
                true,
                Formatting.RED
        ));

        register(new CustomStat(
                "cold_resistance",
                "Cold Resistance",
                StatCategory.DEFENSIVE,
                ModAttributes.COLD_RESISTANCE,
                new double[]{18.0, 20.0, 23.0, 26.0, 30.0},
                new double[]{22.0, 25.0, 28.0, 32.0, 38.0},
                true,
                Formatting.AQUA
        ));

        register(new CustomStat(
                "lightning_resistance",
                "Lightning Resistance",
                StatCategory.DEFENSIVE,
                ModAttributes.LIGHTNING_RESISTANCE,
                new double[]{18.0, 20.0, 23.0, 26.0, 30.0},
                new double[]{22.0, 25.0, 28.0, 32.0, 38.0},
                true,
                Formatting.YELLOW
        ));

        register(new CustomStat(
                "void_resistance",
                "Void Resistance",
                StatCategory.DEFENSIVE,
                ModAttributes.VOID_RESISTANCE,
                new double[]{18.0, 20.0, 23.0, 26.0, 30.0},
                new double[]{22.0, 25.0, 28.0, 32.0, 38.0},
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
                Formatting.LIGHT_PURPLE
        ));

        register(new CustomStat(
                "luck",
                "Luck",
                StatCategory.DEFENSIVE,
                EntityAttributes.LUCK,
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
                new double[]{2.0, 5.0, 9.0, 14.0, 20.0},
                new double[]{4.0, 8.0, 13.0, 19.0, 28.0},
                true,
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