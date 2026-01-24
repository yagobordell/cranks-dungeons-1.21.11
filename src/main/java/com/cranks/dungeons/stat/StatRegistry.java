package com.cranks.dungeons.stat;

import com.cranks.dungeons.equipment.EquipmentType;
import com.cranks.dungeons.registry.ModAttributes;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.Formatting;

import java.util.*;
import java.util.stream.Collectors;

public class StatRegistry {
    private static final Map<String, CustomStat> STATS = new HashMap<>();

    public static void registerStats() {
        // ===== HELMET STATS =====
        register(new CustomStat(
                "helmet_max_health",
                "Max Health",
                EquipmentType.HELMET,
                EntityAttributes.MAX_HEALTH,
                new double[]{1.0, 2.0, 4.5, 5.5, 8.0},
                new double[]{1.5, 3.0, 5.0, 7.5, 12.0},
                false,
                Formatting.DARK_RED
        ));

        register(new CustomStat(
                "helmet_life_regeneration",
                "Life Regeneration",
                EquipmentType.HELMET,
                ModAttributes.LIFE_REGENERATION,
                new double[]{0.1, 0.25, 0.5, 0.8, 1.2},
                new double[]{0.2, 0.45, 0.75, 1.2, 2.0},
                false,
                Formatting.DARK_RED
        ));

        register(new CustomStat(
                "helmet_armor",
                "Armor",
                EquipmentType.HELMET,
                EntityAttributes.ARMOR,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 2.5, 4.5, 7.5, 12.0},
                false,
                Formatting.WHITE
        ));

        register(new CustomStat(
                "helmet_armor_toughness",
                "Armor Toughness",
                EquipmentType.HELMET,
                EntityAttributes.ARMOR_TOUGHNESS,
                new double[]{0.3, 0.8, 1.5, 2.5, 4.0},
                new double[]{0.6, 1.4, 2.4, 4.0, 6.0},
                false,
                Formatting.WHITE
        ));

        register(new CustomStat(
                "helmet_knockback_resistance",
                "Knockback Resistance",
                EquipmentType.HELMET,
                EntityAttributes.KNOCKBACK_RESISTANCE,
                new double[]{0.05, 0.12, 0.20, 0.30, 0.42},
                new double[]{0.10, 0.18, 0.28, 0.40, 0.60},
                true,
                true,
                Formatting.GRAY
        ));

        register(new CustomStat(
                "helmet_fire_resistance",
                "Fire Resistance",
                EquipmentType.HELMET,
                ModAttributes.FIRE_RESISTANCE,
                new double[]{0.18, 0.20, 0.23, 0.26, 0.30},
                new double[]{0.22, 0.25, 0.28, 0.32, 0.38},
                true,
                true,
                Formatting.RED
        ));

        register(new CustomStat(
                "helmet_cold_resistance",
                "Cold Resistance",
                EquipmentType.HELMET,
                ModAttributes.COLD_RESISTANCE,
                new double[]{0.18, 0.20, 0.23, 0.26, 0.30},
                new double[]{0.22, 0.25, 0.28, 0.32, 0.38},
                true,
                true,
                Formatting.AQUA
        ));

        register(new CustomStat(
                "helmet_lightning_resistance",
                "Lightning Resistance",
                EquipmentType.HELMET,
                ModAttributes.LIGHTNING_RESISTANCE,
                new double[]{0.18, 0.20, 0.23, 0.26, 0.30},
                new double[]{0.22, 0.25, 0.28, 0.32, 0.38},
                true,
                true,
                Formatting.DARK_BLUE
        ));

        register(new CustomStat(
                "helmet_void_resistance",
                "Void Resistance",
                EquipmentType.HELMET,
                ModAttributes.VOID_RESISTANCE,
                new double[]{0.18, 0.20, 0.23, 0.26, 0.30},
                new double[]{0.22, 0.25, 0.28, 0.32, 0.38},
                true,
                true,
                Formatting.DARK_PURPLE
        ));

        register(new CustomStat(
                "helmet_experience_bonus",
                "Experience Bonus",
                EquipmentType.HELMET,
                ModAttributes.EXPERIENCE_BONUS,
                new double[]{0.05, 0.12, 0.20, 0.38, 0.43},
                new double[]{0.10, 0.18, 0.28, 0.42, 0.69},
                true,
                true,
                Formatting.DARK_GREEN
        ));

        register(new CustomStat(
                "helmet_luck",
                "Luck",
                EquipmentType.HELMET,
                ModAttributes.LUCK,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 2.5, 4.5, 7.5, 12.0},
                true,
                true,
                Formatting.DARK_GREEN
        ));

        // ===== CHESTPLATE STATS =====
        register(new CustomStat(
                "chestplate_max_health",
                "Max Health",
                EquipmentType.CHESTPLATE,
                EntityAttributes.MAX_HEALTH,
                new double[]{1.0, 2.0, 4.5, 5.5, 8.0},
                new double[]{1.5, 3.0, 5.0, 7.5, 12.0},
                false,
                Formatting.DARK_RED
        ));

        register(new CustomStat(
                "chestplate_life_regeneration",
                "Life Regeneration",
                EquipmentType.CHESTPLATE,
                ModAttributes.LIFE_REGENERATION,
                new double[]{0.1, 0.25, 0.5, 0.8, 1.2},
                new double[]{0.2, 0.45, 0.75, 1.2, 2.0},
                false,
                Formatting.DARK_RED
        ));

        register(new CustomStat(
                "chestplate_armor",
                "Armor",
                EquipmentType.CHESTPLATE,
                EntityAttributes.ARMOR,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 2.5, 4.5, 7.5, 12.0},
                false,
                Formatting.WHITE
        ));

        register(new CustomStat(
                "chestplate_armor_toughness",
                "Armor Toughness",
                EquipmentType.CHESTPLATE,
                EntityAttributes.ARMOR_TOUGHNESS,
                new double[]{0.3, 0.8, 1.5, 2.5, 4.0},
                new double[]{0.6, 1.4, 2.4, 4.0, 6.0},
                false,
                Formatting.WHITE
        ));

        register(new CustomStat(
                "chestplate_knockback_resistance",
                "Knockback Resistance",
                EquipmentType.CHESTPLATE,
                EntityAttributes.KNOCKBACK_RESISTANCE,
                new double[]{0.05, 0.12, 0.20, 0.30, 0.42},
                new double[]{0.10, 0.18, 0.28, 0.40, 0.60},
                true,
                true,
                Formatting.GRAY
        ));

        register(new CustomStat(
                "chestplate_fire_resistance",
                "Fire Resistance",
                EquipmentType.CHESTPLATE,
                ModAttributes.FIRE_RESISTANCE,
                new double[]{0.18, 0.20, 0.23, 0.26, 0.30},
                new double[]{0.22, 0.25, 0.28, 0.32, 0.38},
                true,
                true,
                Formatting.RED
        ));

        register(new CustomStat(
                "chestplate_cold_resistance",
                "Cold Resistance",
                EquipmentType.CHESTPLATE,
                ModAttributes.COLD_RESISTANCE,
                new double[]{0.18, 0.20, 0.23, 0.26, 0.30},
                new double[]{0.22, 0.25, 0.28, 0.32, 0.38},
                true,
                true,
                Formatting.AQUA
        ));

        register(new CustomStat(
                "chestplate_lightning_resistance",
                "Lightning Resistance",
                EquipmentType.CHESTPLATE,
                ModAttributes.LIGHTNING_RESISTANCE,
                new double[]{0.18, 0.20, 0.23, 0.26, 0.30},
                new double[]{0.22, 0.25, 0.28, 0.32, 0.38},
                true,
                true,
                Formatting.DARK_BLUE
        ));

        register(new CustomStat(
                "chestplate_void_resistance",
                "Void Resistance",
                EquipmentType.CHESTPLATE,
                ModAttributes.VOID_RESISTANCE,
                new double[]{0.18, 0.20, 0.23, 0.26, 0.30},
                new double[]{0.22, 0.25, 0.28, 0.32, 0.38},
                true,
                true,
                Formatting.DARK_PURPLE
        ));

        register(new CustomStat(
                "chestplate_attack_damage",
                "Attack Damage",
                EquipmentType.CHESTPLATE,
                EntityAttributes.ATTACK_DAMAGE,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 2.5, 4.5, 7.5, 12.0},
                false,
                Formatting.WHITE
        ));

        register(new CustomStat(
                "chestplate_attack_speed",
                "Attack Speed",
                EquipmentType.CHESTPLATE,
                EntityAttributes.ATTACK_SPEED,
                new double[]{0.10, 0.20, 0.30, 0.40, 0.50},
                new double[]{0.18, 0.28, 0.38, 0.48, 0.58},
                true,
                true,
                Formatting.YELLOW
        ));

        register(new CustomStat(
                "chestplate_crit_chance",
                "Critical Strike Chance",
                EquipmentType.CHESTPLATE,
                ModAttributes.CRIT_CHANCE,
                new double[]{0.01, 0.03, 0.06, 0.10, 0.15},
                new double[]{0.02, 0.05, 0.09, 0.14, 0.22},
                true,
                true,
                Formatting.GOLD
        ));

        register(new CustomStat(
                "chestplate_chance_to_burn",
                "Chance to burn",
                EquipmentType.CHESTPLATE,
                ModAttributes.CHANCE_TO_BURN,
                new double[]{0.05, 0.12, 0.20, 0.30, 0.42},
                new double[]{0.10, 0.20, 0.30, 0.45, 0.65},
                true,
                true,
                Formatting.RED
        ));

        register(new CustomStat(
                "chestplate_fire_damage",
                "Fire Damage",
                EquipmentType.CHESTPLATE,
                ModAttributes.FIRE_DAMAGE,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 3.0, 5.5, 9.0, 14.0},
                false,
                Formatting.RED
        ));

        register(new CustomStat(
                "chestplate_cold_damage",
                "Cold Damage",
                EquipmentType.CHESTPLATE,
                ModAttributes.COLD_DAMAGE,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 3.0, 5.5, 9.0, 14.0},
                false,
                Formatting.AQUA
        ));

        register(new CustomStat(
                "chestplate_lightning_damage",
                "Lightning Damage",
                EquipmentType.CHESTPLATE,
                ModAttributes.LIGHTNING_DAMAGE,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 3.0, 5.5, 9.0, 14.0},
                false,
                Formatting.DARK_BLUE
        ));

        register(new CustomStat(
                "chestplate_void_damage",
                "Void Damage",
                EquipmentType.CHESTPLATE,
                ModAttributes.VOID_DAMAGE,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 3.0, 5.5, 9.0, 14.0},
                false,
                Formatting.DARK_PURPLE
        ));

        register(new CustomStat(
                "chestplate_life_steal",
                "Life Steal",
                EquipmentType.CHESTPLATE,
                ModAttributes.LIFE_STEAL,
                new double[]{0.01, 0.03, 0.05, 0.08, 0.11},
                new double[]{0.02, 0.05, 0.07, 0.10, 0.13},
                true,
                true,
                Formatting.DARK_RED
        ));

        register(new CustomStat(
                "chestplate_knockback",
                "Knockback",
                EquipmentType.CHESTPLATE,
                ModAttributes.KNOCKBACK,
                new double[]{0.1, 0.3, 0.6, 0.9, 1.2},
                new double[]{0.2, 0.5, 0.8, 1.1, 1.4},
                false,
                Formatting.GRAY
        ));

        register(new CustomStat(
                "chestplate_experience_bonus",
                "Experience Bonus",
                EquipmentType.CHESTPLATE,
                ModAttributes.EXPERIENCE_BONUS,
                new double[]{0.05, 0.12, 0.20, 0.38, 0.43},
                new double[]{0.10, 0.18, 0.28, 0.42, 0.69},
                true,
                true,
                Formatting.DARK_GREEN
        ));

        register(new CustomStat(
                "chestplate_luck",
                "Luck",
                EquipmentType.CHESTPLATE,
                ModAttributes.LUCK,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 2.5, 4.5, 7.5, 12.0},
                true,
                true,
                Formatting.DARK_GREEN
        ));

        // ===== LEGGINGS STATS =====
        register(new CustomStat(
                "leggings_max_health",
                "Max Health",
                EquipmentType.LEGGINGS,
                EntityAttributes.MAX_HEALTH,
                new double[]{1.0, 2.0, 4.5, 5.5, 8.0},
                new double[]{1.5, 3.0, 5.0, 7.5, 12.0},
                false,
                Formatting.DARK_RED
        ));

        register(new CustomStat(
                "leggings_life_regeneration",
                "Life Regeneration",
                EquipmentType.LEGGINGS,
                ModAttributes.LIFE_REGENERATION,
                new double[]{0.1, 0.25, 0.5, 0.8, 1.2},
                new double[]{0.2, 0.45, 0.75, 1.2, 2.0},
                false,
                Formatting.DARK_RED
        ));

        register(new CustomStat(
                "leggings_armor",
                "Armor",
                EquipmentType.LEGGINGS,
                EntityAttributes.ARMOR,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 2.5, 4.5, 7.5, 12.0},
                false,
                Formatting.WHITE
        ));

        register(new CustomStat(
                "leggings_armor_toughness",
                "Armor Toughness",
                EquipmentType.LEGGINGS,
                EntityAttributes.ARMOR_TOUGHNESS,
                new double[]{0.3, 0.8, 1.5, 2.5, 4.0},
                new double[]{0.6, 1.4, 2.4, 4.0, 6.0},
                false,
                Formatting.WHITE
        ));

        register(new CustomStat(
                "leggings_knockback_resistance",
                "Knockback Resistance",
                EquipmentType.LEGGINGS,
                EntityAttributes.KNOCKBACK_RESISTANCE,
                new double[]{0.05, 0.12, 0.20, 0.30, 0.42},
                new double[]{0.10, 0.18, 0.28, 0.40, 0.60},
                true,
                true,
                Formatting.GRAY
        ));

        register(new CustomStat(
                "leggigns_fire_resistance",
                "Fire Resistance",
                EquipmentType.LEGGINGS,
                ModAttributes.FIRE_RESISTANCE,
                new double[]{0.18, 0.20, 0.23, 0.26, 0.30},
                new double[]{0.22, 0.25, 0.28, 0.32, 0.38},
                true,
                true,
                Formatting.RED
        ));

        register(new CustomStat(
                "leggings_cold_resistance",
                "Cold Resistance",
                EquipmentType.LEGGINGS,
                ModAttributes.COLD_RESISTANCE,
                new double[]{0.18, 0.20, 0.23, 0.26, 0.30},
                new double[]{0.22, 0.25, 0.28, 0.32, 0.38},
                true,
                true,
                Formatting.AQUA
        ));

        register(new CustomStat(
                "leggings_lightning_resistance",
                "Lightning Resistance",
                EquipmentType.LEGGINGS,
                ModAttributes.LIGHTNING_RESISTANCE,
                new double[]{0.18, 0.20, 0.23, 0.26, 0.30},
                new double[]{0.22, 0.25, 0.28, 0.32, 0.38},
                true,
                true,
                Formatting.DARK_BLUE
        ));

        register(new CustomStat(
                "leggings_void_resistance",
                "Void Resistance",
                EquipmentType.LEGGINGS,
                ModAttributes.VOID_RESISTANCE,
                new double[]{0.18, 0.20, 0.23, 0.26, 0.30},
                new double[]{0.22, 0.25, 0.28, 0.32, 0.38},
                true,
                true,
                Formatting.DARK_PURPLE
        ));

        register(new CustomStat(
                "leggings_experience_bonus",
                "Experience Bonus",
                EquipmentType.LEGGINGS,
                ModAttributes.EXPERIENCE_BONUS,
                new double[]{0.05, 0.12, 0.20, 0.38, 0.43},
                new double[]{0.10, 0.18, 0.28, 0.42, 0.69},
                true,
                true,
                Formatting.DARK_GREEN
        ));

        register(new CustomStat(
                "leggings_luck",
                "Luck",
                EquipmentType.LEGGINGS,
                ModAttributes.LUCK,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 2.5, 4.5, 7.5, 12.0},
                true,
                true,
                Formatting.DARK_GREEN
        ));

        // ===== BOOTS STATS =====
        register(new CustomStat(
                "boots_max_health",
                "Max Health",
                EquipmentType.BOOTS,
                EntityAttributes.MAX_HEALTH,
                new double[]{1.0, 2.0, 4.5, 5.5, 8.0},
                new double[]{1.5, 3.0, 5.0, 7.5, 12.0},
                false,
                Formatting.DARK_RED
        ));

        register(new CustomStat(
                "boots_life_regeneration",
                "Life Regeneration",
                EquipmentType.BOOTS,
                ModAttributes.LIFE_REGENERATION,
                new double[]{0.1, 0.25, 0.5, 0.8, 1.2},
                new double[]{0.2, 0.45, 0.75, 1.2, 2.0},
                false,
                Formatting.DARK_RED
        ));

        register(new CustomStat(
                "boots_armor",
                "Armor",
                EquipmentType.BOOTS,
                EntityAttributes.ARMOR,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 2.5, 4.5, 7.5, 12.0},
                false,
                Formatting.WHITE
        ));

        register(new CustomStat(
                "boots_armor_toughness",
                "Armor Toughness",
                EquipmentType.BOOTS,
                EntityAttributes.ARMOR_TOUGHNESS,
                new double[]{0.3, 0.8, 1.5, 2.5, 4.0},
                new double[]{0.6, 1.4, 2.4, 4.0, 6.0},
                false,
                Formatting.WHITE
        ));

        register(new CustomStat(
                "boots_knockback_resistance",
                "Knockback Resistance",
                EquipmentType.BOOTS,
                EntityAttributes.KNOCKBACK_RESISTANCE,
                new double[]{0.05, 0.12, 0.20, 0.30, 0.42},
                new double[]{0.10, 0.18, 0.28, 0.40, 0.60},
                true,
                true,
                Formatting.GRAY
        ));

        register(new CustomStat(
                "boots_fire_resistance",
                "Fire Resistance",
                EquipmentType.BOOTS,
                ModAttributes.FIRE_RESISTANCE,
                new double[]{0.18, 0.20, 0.23, 0.26, 0.30},
                new double[]{0.22, 0.25, 0.28, 0.32, 0.38},
                true,
                true,
                Formatting.RED
        ));

        register(new CustomStat(
                "boots_cold_resistance",
                "Cold Resistance",
                EquipmentType.BOOTS,
                ModAttributes.COLD_RESISTANCE,
                new double[]{0.18, 0.20, 0.23, 0.26, 0.30},
                new double[]{0.22, 0.25, 0.28, 0.32, 0.38},
                true,
                true,
                Formatting.AQUA
        ));

        register(new CustomStat(
                "boots_lightning_resistance",
                "Lightning Resistance",
                EquipmentType.BOOTS,
                ModAttributes.LIGHTNING_RESISTANCE,
                new double[]{0.18, 0.20, 0.23, 0.26, 0.30},
                new double[]{0.22, 0.25, 0.28, 0.32, 0.38},
                true,
                true,
                Formatting.DARK_BLUE
        ));

        register(new CustomStat(
                "boots_void_resistance",
                "Void Resistance",
                EquipmentType.BOOTS,
                ModAttributes.VOID_RESISTANCE,
                new double[]{0.18, 0.20, 0.23, 0.26, 0.30},
                new double[]{0.22, 0.25, 0.28, 0.32, 0.38},
                true,
                true,
                Formatting.DARK_PURPLE
        ));

        register(new CustomStat(
                "boots_movement_speed",
                "Movement Speed",
                EquipmentType.BOOTS,
                EntityAttributes.MOVEMENT_SPEED,
                new double[]{0.01, 0.025, 0.045, 0.07, 0.10},
                new double[]{0.02, 0.04, 0.065, 0.095, 0.125},
                true,
                true,
                Formatting.GREEN
        ));

        register(new CustomStat(
                "boots_feather_falling",
                "Feather Falling",
                EquipmentType.BOOTS,
                ModAttributes.FEATHER_FALLING,
                new double[]{0.10, 0.18, 0.28, 0.40, 0.55},
                new double[]{0.15, 0.25, 0.38, 0.55, 0.75},
                true,
                true,
                Formatting.WHITE
        ));

        register(new CustomStat(
                "boots_experience_bonus",
                "Experience Bonus",
                EquipmentType.BOOTS,
                ModAttributes.EXPERIENCE_BONUS,
                new double[]{0.05, 0.12, 0.20, 0.38, 0.43},
                new double[]{0.10, 0.18, 0.28, 0.42, 0.69},
                true,
                true,
                Formatting.DARK_GREEN
        ));

        register(new CustomStat(
                "boots_luck",
                "Luck",
                EquipmentType.BOOTS,
                ModAttributes.LUCK,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 2.5, 4.5, 7.5, 12.0},
                true,
                true,
                Formatting.DARK_GREEN
        ));

        // ===== SWORD STATS =====
        register(new CustomStat(
                "sword_attack_damage",
                "Attack Damage",
                EquipmentType.SWORD,
                EntityAttributes.ATTACK_DAMAGE,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 2.5, 4.5, 7.5, 12.0},
                false,
                Formatting.WHITE
        ));

        register(new CustomStat(
                "sword_attack_speed",
                "Attack Speed",
                EquipmentType.SWORD,
                EntityAttributes.ATTACK_SPEED,
                new double[]{0.10, 0.20, 0.30, 0.40, 0.50},
                new double[]{0.18, 0.28, 0.38, 0.48, 0.58},
                true,
                true,
                Formatting.YELLOW
        ));

        register(new CustomStat(
                "sword_crit_chance",
                "Critical Strike Chance",
                EquipmentType.SWORD,
                ModAttributes.CRIT_CHANCE,
                new double[]{0.01, 0.03, 0.06, 0.10, 0.15},
                new double[]{0.02, 0.05, 0.09, 0.14, 0.22},
                true,
                true,
                Formatting.GOLD
        ));

        register(new CustomStat(
                "sword_chance_to_burn",
                "Chance to burn",
                EquipmentType.SWORD,
                ModAttributes.CHANCE_TO_BURN,
                new double[]{0.05, 0.12, 0.20, 0.30, 0.42},
                new double[]{0.10, 0.20, 0.30, 0.45, 0.65},
                true,
                true,
                Formatting.RED
        ));

        register(new CustomStat(
                "sword_fire_damage",
                "Fire Damage",
                EquipmentType.SWORD,
                ModAttributes.FIRE_DAMAGE,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 3.0, 5.5, 9.0, 14.0},
                false,
                Formatting.RED
        ));

        register(new CustomStat(
                "sword_cold_damage",
                "Cold Damage",
                EquipmentType.SWORD,
                ModAttributes.COLD_DAMAGE,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 3.0, 5.5, 9.0, 14.0},
                false,
                Formatting.AQUA
        ));

        register(new CustomStat(
                "sword_lightning_damage",
                "Lightning Damage",
                EquipmentType.SWORD,
                ModAttributes.LIGHTNING_DAMAGE,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 3.0, 5.5, 9.0, 14.0},
                false,
                Formatting.DARK_BLUE
        ));

        register(new CustomStat(
                "sword_void_damage",
                "Void Damage",
                EquipmentType.SWORD,
                ModAttributes.VOID_DAMAGE,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 3.0, 5.5, 9.0, 14.0},
                false,
                Formatting.DARK_PURPLE
        ));

        register(new CustomStat(
                "sword_life_steal",
                "Life Steal",
                EquipmentType.SWORD,
                ModAttributes.LIFE_STEAL,
                new double[]{0.01, 0.03, 0.05, 0.08, 0.11},
                new double[]{0.02, 0.05, 0.07, 0.10, 0.13},
                true,
                true,
                Formatting.DARK_RED
        ));

        register(new CustomStat(
                "sword_knockback",
                "Knockback",
                EquipmentType.SWORD,
                ModAttributes.KNOCKBACK,
                new double[]{0.1, 0.3, 0.6, 0.9, 1.2},
                new double[]{0.2, 0.5, 0.8, 1.1, 1.4},
                false,
                Formatting.GRAY
        ));

        register(new CustomStat(
                "sword_attack_range",
                "Attack Range",
                EquipmentType.SWORD,
                ModAttributes.ATTACK_RANGE,
                new double[]{0.1, 0.3, 0.5, 0.7, 0.9},
                new double[]{0.2, 0.4, 0.6, 0.8, 1.0},
                false,
                Formatting.GREEN
        ));

        // ===== SPEAR STATS =====
        register(new CustomStat(
                "spear_attack_range",
                "Attack Range",
                EquipmentType.SPEAR,
                ModAttributes.ATTACK_RANGE,
                new double[]{0.3, 0.5, 0.7, 0.9, 1.1},
                new double[]{0.4, 0.6, 0.8, 1.0, 1.2},
                false,
                Formatting.GREEN
        ));

        register(new CustomStat(
                "spear_attack_damage",
                "Attack Damage",
                EquipmentType.SPEAR,
                EntityAttributes.ATTACK_DAMAGE,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 2.5, 4.5, 7.5, 12.0},
                false,
                Formatting.WHITE
        ));

        register(new CustomStat(
                "spear_attack_speed",
                "Attack Speed",
                EquipmentType.SPEAR,
                EntityAttributes.ATTACK_SPEED,
                new double[]{0.10, 0.20, 0.30, 0.40, 0.50},
                new double[]{0.18, 0.28, 0.38, 0.48, 0.58},
                true,
                true,
                Formatting.YELLOW
        ));

        register(new CustomStat(
                "spear_crit_chance",
                "Critical Strike Chance",
                EquipmentType.SPEAR,
                ModAttributes.CRIT_CHANCE,
                new double[]{0.01, 0.03, 0.06, 0.10, 0.15},
                new double[]{0.02, 0.05, 0.09, 0.14, 0.22},
                true,
                true,
                Formatting.GOLD
        ));

        register(new CustomStat(
                "spear_chance_to_burn",
                "Chance to burn",
                EquipmentType.SPEAR,
                ModAttributes.CHANCE_TO_BURN,
                new double[]{0.05, 0.12, 0.20, 0.30, 0.42},
                new double[]{0.10, 0.20, 0.30, 0.45, 0.65},
                true,
                true,
                Formatting.RED
        ));

        register(new CustomStat(
                "spear_fire_damage",
                "Fire Damage",
                EquipmentType.SPEAR,
                ModAttributes.FIRE_DAMAGE,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 3.0, 5.5, 9.0, 14.0},
                false,
                Formatting.RED
        ));

        register(new CustomStat(
                "spear_cold_damage",
                "Cold Damage",
                EquipmentType.SPEAR,
                ModAttributes.COLD_DAMAGE,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 3.0, 5.5, 9.0, 14.0},
                false,
                Formatting.AQUA
        ));

        register(new CustomStat(
                "spear_lightning_damage",
                "Lightning Damage",
                EquipmentType.SPEAR,
                ModAttributes.LIGHTNING_DAMAGE,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 3.0, 5.5, 9.0, 14.0},
                false,
                Formatting.DARK_BLUE
        ));

        register(new CustomStat(
                "spear_void_damage",
                "Void Damage",
                EquipmentType.SPEAR,
                ModAttributes.VOID_DAMAGE,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 3.0, 5.5, 9.0, 14.0},
                false,
                Formatting.DARK_PURPLE
        ));

        register(new CustomStat(
                "spear_life_steal",
                "Life Steal",
                EquipmentType.SPEAR,
                ModAttributes.LIFE_STEAL,
                new double[]{0.01, 0.03, 0.05, 0.08, 0.11},
                new double[]{0.02, 0.05, 0.07, 0.10, 0.13},
                true,
                true,
                Formatting.DARK_RED
        ));

        register(new CustomStat(
                "spear_knockback",
                "Knockback",
                EquipmentType.SPEAR,
                ModAttributes.KNOCKBACK,
                new double[]{0.1, 0.3, 0.6, 0.9, 1.2},
                new double[]{0.2, 0.5, 0.8, 1.1, 1.4},
                false,
                Formatting.GRAY
        ));

        // ===== MACE STATS =====
        register(new CustomStat(
                "mace_attack_damage",
                "Attack Damage",
                EquipmentType.MACE,
                EntityAttributes.ATTACK_DAMAGE,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 2.5, 4.5, 7.5, 12.0},
                false,
                Formatting.WHITE
        ));

        register(new CustomStat(
                "mace_attack_speed",
                "Attack Speed",
                EquipmentType.MACE,
                EntityAttributes.ATTACK_SPEED,
                new double[]{0.10, 0.20, 0.30, 0.40, 0.50},
                new double[]{0.18, 0.28, 0.38, 0.48, 0.58},
                true,
                true,
                Formatting.YELLOW
        ));

        register(new CustomStat(
                "mace_crit_chance",
                "Critical Strike Chance",
                EquipmentType.MACE,
                ModAttributes.CRIT_CHANCE,
                new double[]{0.01, 0.03, 0.06, 0.10, 0.15},
                new double[]{0.02, 0.05, 0.09, 0.14, 0.22},
                true,
                true,
                Formatting.GOLD
        ));

        register(new CustomStat(
                "mace_chance_to_burn",
                "Chance to burn",
                EquipmentType.MACE,
                ModAttributes.CHANCE_TO_BURN,
                new double[]{0.05, 0.12, 0.20, 0.30, 0.42},
                new double[]{0.10, 0.20, 0.30, 0.45, 0.65},
                true,
                true,
                Formatting.RED
        ));

        register(new CustomStat(
                "mace_fire_damage",
                "Fire Damage",
                EquipmentType.MACE,
                ModAttributes.FIRE_DAMAGE,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 3.0, 5.5, 9.0, 14.0},
                false,
                Formatting.RED
        ));

        register(new CustomStat(
                "mace_cold_damage",
                "Cold Damage",
                EquipmentType.MACE,
                ModAttributes.COLD_DAMAGE,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 3.0, 5.5, 9.0, 14.0},
                false,
                Formatting.AQUA
        ));

        register(new CustomStat(
                "mace_lightning_damage",
                "Lightning Damage",
                EquipmentType.MACE,
                ModAttributes.LIGHTNING_DAMAGE,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 3.0, 5.5, 9.0, 14.0},
                false,
                Formatting.DARK_BLUE
        ));

        register(new CustomStat(
                "mace_void_damage",
                "Void Damage",
                EquipmentType.MACE,
                ModAttributes.VOID_DAMAGE,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 3.0, 5.5, 9.0, 14.0},
                false,
                Formatting.DARK_PURPLE
        ));

        register(new CustomStat(
                "mace_life_steal",
                "Life Steal",
                EquipmentType.MACE,
                ModAttributes.LIFE_STEAL,
                new double[]{0.01, 0.03, 0.05, 0.08, 0.11},
                new double[]{0.02, 0.05, 0.07, 0.10, 0.13},
                true,
                true,
                Formatting.DARK_RED
        ));

        register(new CustomStat(
                "mace_knockback",
                "Knockback",
                EquipmentType.MACE,
                ModAttributes.KNOCKBACK,
                new double[]{0.1, 0.3, 0.6, 0.9, 1.2},
                new double[]{0.2, 0.5, 0.8, 1.1, 1.4},
                false,
                Formatting.GRAY
        ));

        register(new CustomStat(
                "mace_attack_range",
                "Attack Range",
                EquipmentType.MACE,
                ModAttributes.ATTACK_RANGE,
                new double[]{0.1, 0.3, 0.5, 0.7, 0.9},
                new double[]{0.2, 0.4, 0.6, 0.8, 1.0},
                false,
                Formatting.GREEN
        ));

        // ===== BOW STATS =====
        register(new CustomStat(
                "bow_attack_damage",
                "Attack Damage",
                EquipmentType.BOW,
                EntityAttributes.ATTACK_DAMAGE,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 2.5, 4.5, 7.5, 12.0},
                false,
                Formatting.WHITE
        ));

        register(new CustomStat(
                "bow_crit_chance",
                "Critical Strike Chance",
                EquipmentType.BOW,
                ModAttributes.CRIT_CHANCE,
                new double[]{0.01, 0.03, 0.06, 0.10, 0.15},
                new double[]{0.02, 0.05, 0.09, 0.14, 0.22},
                true,
                true,
                Formatting.GOLD
        ));

        register(new CustomStat(
                "bow_chance_to_burn",
                "Chance to burn",
                EquipmentType.BOW,
                ModAttributes.CHANCE_TO_BURN,
                new double[]{0.05, 0.12, 0.20, 0.30, 0.42},
                new double[]{0.10, 0.20, 0.30, 0.45, 0.65},
                true,
                true,
                Formatting.RED
        ));

        register(new CustomStat(
                "bow_fire_damage",
                "Fire Damage",
                EquipmentType.BOW,
                ModAttributes.FIRE_DAMAGE,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 3.0, 5.5, 9.0, 14.0},
                false,
                Formatting.RED
        ));

        register(new CustomStat(
                "bow_cold_damage",
                "Cold Damage",
                EquipmentType.BOW,
                ModAttributes.COLD_DAMAGE,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 3.0, 5.5, 9.0, 14.0},
                false,
                Formatting.AQUA
        ));

        register(new CustomStat(
                "bow_lightning_damage",
                "Lightning Damage",
                EquipmentType.BOW,
                ModAttributes.LIGHTNING_DAMAGE,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 3.0, 5.5, 9.0, 14.0},
                false,
                Formatting.DARK_BLUE
        ));

        register(new CustomStat(
                "bow_void_damage",
                "Void Damage",
                EquipmentType.BOW,
                ModAttributes.VOID_DAMAGE,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 3.0, 5.5, 9.0, 14.0},
                false,
                Formatting.DARK_PURPLE
        ));

        register(new CustomStat(
                "bow_life_steal",
                "Life Steal",
                EquipmentType.BOW,
                ModAttributes.LIFE_STEAL,
                new double[]{0.01, 0.03, 0.05, 0.07, 0.09},
                new double[]{0.02, 0.04, 0.06, 0.08, 0.10},
                true,
                true,
                Formatting.DARK_RED
        ));

        register(new CustomStat(
                "bow_knockback",
                "Knockback",
                EquipmentType.BOW,
                ModAttributes.KNOCKBACK,
                new double[]{0.1, 0.3, 0.5, 0.7, 0.9},
                new double[]{0.2, 0.4, 0.6, 0.8, 1.0},
                false,
                Formatting.GRAY
        ));

        // ===== SHIELD STATS =====
        register(new CustomStat(
                "shield_max_health",
                "Max Health",
                EquipmentType.SHIELD,
                EntityAttributes.MAX_HEALTH,
                new double[]{1.0, 2.0, 4.5, 5.5, 8.0},
                new double[]{1.5, 3.0, 5.0, 7.5, 12.0},
                false,
                Formatting.DARK_RED
        ));

        register(new CustomStat(
                "shield_life_regeneration",
                "Life Regeneration",
                EquipmentType.SHIELD,
                ModAttributes.LIFE_REGENERATION,
                new double[]{0.1, 0.25, 0.5, 0.8, 1.2},
                new double[]{0.2, 0.45, 0.75, 1.2, 2.0},
                false,
                Formatting.DARK_RED
        ));

        register(new CustomStat(
                "shield_armor",
                "Armor",
                EquipmentType.SHIELD,
                EntityAttributes.ARMOR,
                new double[]{0.5, 1.5, 3.0, 5.0, 8.0},
                new double[]{1.0, 2.5, 4.5, 7.5, 12.0},
                false,
                Formatting.WHITE
        ));

        register(new CustomStat(
                "shield_armor_toughness",
                "Armor Toughness",
                EquipmentType.SHIELD,
                EntityAttributes.ARMOR_TOUGHNESS,
                new double[]{0.3, 0.8, 1.5, 2.5, 4.0},
                new double[]{0.6, 1.4, 2.4, 4.0, 6.0},
                false,
                Formatting.WHITE
        ));

        register(new CustomStat(
                "shield_knockback_resistance",
                "Knockback Resistance",
                EquipmentType.SHIELD,
                EntityAttributes.KNOCKBACK_RESISTANCE,
                new double[]{0.05, 0.12, 0.20, 0.30, 0.42},
                new double[]{0.10, 0.18, 0.28, 0.40, 0.60},
                true,
                true,
                Formatting.GRAY
        ));

        register(new CustomStat(
                "shield_fire_resistance",
                "Fire Resistance",
                EquipmentType.SHIELD,
                ModAttributes.FIRE_RESISTANCE,
                new double[]{0.18, 0.20, 0.23, 0.26, 0.30},
                new double[]{0.22, 0.25, 0.28, 0.32, 0.38},
                true,
                true,
                Formatting.RED
        ));

        register(new CustomStat(
                "shield_cold_resistance",
                "Cold Resistance",
                EquipmentType.SHIELD,
                ModAttributes.COLD_RESISTANCE,
                new double[]{0.18, 0.20, 0.23, 0.26, 0.30},
                new double[]{0.22, 0.25, 0.28, 0.32, 0.38},
                true,
                true,
                Formatting.AQUA
        ));

        register(new CustomStat(
                "shield_lightning_resistance",
                "Lightning Resistance",
                EquipmentType.SHIELD,
                ModAttributes.LIGHTNING_RESISTANCE,
                new double[]{0.18, 0.20, 0.23, 0.26, 0.30},
                new double[]{0.22, 0.25, 0.28, 0.32, 0.38},
                true,
                true,
                Formatting.DARK_BLUE
        ));

        register(new CustomStat(
                "shield_void_resistance",
                "Void Resistance",
                EquipmentType.SHIELD,
                ModAttributes.VOID_RESISTANCE,
                new double[]{0.18, 0.20, 0.23, 0.26, 0.30},
                new double[]{0.22, 0.25, 0.28, 0.32, 0.38},
                true,
                true,
                Formatting.DARK_PURPLE
        ));

        // ===== PICKAXE STATS =====
        register(new CustomStat(
                "pickaxe_mining_efficiency",
                "Mining Speed",
                EquipmentType.PICKAXE,
                ModAttributes.MINING_EFFICIENCY,
                new double[]{0.08, 0.15, 0.23, 0.32, 0.42},
                new double[]{0.12, 0.22, 0.32, 0.45, 0.60},
                true,
                true,
                Formatting.WHITE
        ));

        register(new CustomStat(
                "pickaxe_fortune",
                "Ore Fortune",
                EquipmentType.PICKAXE,
                ModAttributes.FORTUNE,
                new double[]{0.10, 0.18, 0.28, 0.40, 0.55},
                new double[]{0.15, 0.28, 0.40, 0.55, 0.75},
                true,
                true,
                Formatting.GOLD
        ));

        register(new CustomStat(
                "pickaxe_durability_bonus",
                "Durability Bonus",
                EquipmentType.PICKAXE,
                ModAttributes.DURABILITY_BONUS,
                new double[]{0.10, 0.18, 0.28, 0.40, 0.55},
                new double[]{0.15, 0.25, 0.38, 0.55, 0.75},
                true,
                true,
                Formatting.WHITE
        ));

        register(new CustomStat(
                "pickaxe_repair_chance",
                "Repair Chance",
                EquipmentType.PICKAXE,
                ModAttributes.PRECISION_MINING,
                new double[]{0.03, 0.08, 0.13, 0.18, 0.25},
                new double[]{0.05, 0.12, 0.18, 0.25, 0.35},
                true,
                true,
                Formatting.AQUA
        ));

        register(new CustomStat(
                "pickaxe_breaking_range",
                "Breaking Range",
                EquipmentType.PICKAXE,
                ModAttributes.BREAKING_RANGE,
                new double[]{0.3, 0.7, 1.1, 1.5, 1.8},
                new double[]{0.5, 0.9, 1.3, 1.7, 2.0},
                false,
                Formatting.GREEN
        ));

        // ===== AXE STATS =====
        register(new CustomStat(
                "axe_mining_efficiency",
                "Chopping Speed",
                EquipmentType.AXE,
                ModAttributes.MINING_EFFICIENCY,
                new double[]{0.08, 0.15, 0.23, 0.32, 0.42},
                new double[]{0.12, 0.22, 0.32, 0.45, 0.60},
                true,
                true,
                Formatting.GRAY
        ));

        register(new CustomStat(
                "axe_durability_bonus",
                "Durability Bonus",
                EquipmentType.AXE,
                ModAttributes.DURABILITY_BONUS,
                new double[]{0.10, 0.18, 0.28, 0.40, 0.55},
                new double[]{0.15, 0.25, 0.38, 0.55, 0.75},
                true,
                true,
                Formatting.BLUE
        ));

        register(new CustomStat(
                "axe_repair_chance",
                "Repair Chance",
                EquipmentType.AXE,
                ModAttributes.PRECISION_MINING,
                new double[]{0.03, 0.08, 0.13, 0.18, 0.25},
                new double[]{0.05, 0.12, 0.18, 0.25, 0.35},
                true,
                true,
                Formatting.AQUA
        ));

        register(new CustomStat(
                "axe_breaking_range",
                "Breaking Range",
                EquipmentType.AXE,
                ModAttributes.BREAKING_RANGE,
                new double[]{0.3, 0.7, 1.1, 1.5, 1.8},
                new double[]{0.5, 0.9, 1.3, 1.7, 2.0},
                false,
                Formatting.GREEN
        ));

        // ===== SHOVEL STATS =====
        register(new CustomStat(
                "shovel_mining_efficiency",
                "Digging Speed",
                EquipmentType.SHOVEL,
                ModAttributes.MINING_EFFICIENCY,
                new double[]{0.08, 0.15, 0.23, 0.32, 0.42},
                new double[]{0.12, 0.22, 0.32, 0.45, 0.60},
                true,
                true,
                Formatting.GRAY
        ));

        register(new CustomStat(
                "shovel_durability_bonus",
                "Durability Bonus",
                EquipmentType.SHOVEL,
                ModAttributes.DURABILITY_BONUS,
                new double[]{0.10, 0.18, 0.28, 0.40, 0.55},
                new double[]{0.15, 0.25, 0.38, 0.55, 0.75},
                true,
                true,
                Formatting.BLUE
        ));

        register(new CustomStat(
                "shovel_repair_chance",
                "Repair Chance",
                EquipmentType.SHOVEL,
                ModAttributes.PRECISION_MINING,
                new double[]{0.03, 0.08, 0.13, 0.18, 0.25},
                new double[]{0.05, 0.12, 0.18, 0.25, 0.35},
                true,
                true,
                Formatting.AQUA
        ));

        register(new CustomStat(
                "shovel_breaking_range",
                "Breaking Range",
                EquipmentType.SHOVEL,
                ModAttributes.BREAKING_RANGE,
                new double[]{0.3, 0.7, 1.1, 1.5, 1.8},
                new double[]{0.5, 0.9, 1.3, 1.7, 2.0},
                false,
                Formatting.GREEN
        ));

        // ===== HOE STATS =====
        register(new CustomStat(
                "hoe_durability_bonus",
                "Durability Bonus",
                EquipmentType.HOE,
                ModAttributes.DURABILITY_BONUS,
                new double[]{0.10, 0.18, 0.28, 0.40, 0.55},
                new double[]{0.15, 0.25, 0.38, 0.55, 0.75},
                true,
                true,
                Formatting.BLUE
        ));

        register(new CustomStat(
                "hoe_breaking_range",
                "Breaking Range",
                EquipmentType.HOE,
                ModAttributes.BREAKING_RANGE,
                new double[]{0.3, 0.7, 1.1, 1.5, 1.8},
                new double[]{0.5, 0.9, 1.3, 1.7, 2.0},
                false,
                Formatting.GREEN
        ));

        register(new CustomStat(
                "hoe_crop_fortune",
                "Crop Fortune",
                EquipmentType.HOE,
                ModAttributes.CROP_FORTUNE,
                new double[]{0.10, 0.18, 0.28, 0.40, 0.55},
                new double[]{0.15, 0.28, 0.40, 0.55, 0.75},
                true,
                true,
                Formatting.GOLD
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

    public static List<CustomStat> getStatsByEquipmentType(EquipmentType equipmentType) {
        return STATS.values().stream()
                .filter(stat -> stat.getEquipmentType() == equipmentType)
                .collect(Collectors.toList());
    }

    public static CustomStat getRandomStatForEquipmentType(EquipmentType equipmentType) {
        List<CustomStat> stats = getStatsByEquipmentType(equipmentType);
        return stats.isEmpty() ? null : stats.get(new Random().nextInt(stats.size()));
    }
}