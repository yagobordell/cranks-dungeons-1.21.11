package com.cranks.dungeons.loot;

import com.cranks.dungeons.item.ModItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles Runic Tome drops from mobs based on their tier.
 *
 * Drop Rates Philosophy:
 * - Tier 1 Mobs: Primarily drop T1-T2 tomes (Common)
 * - Tier 2 Mobs: Primarily drop T2-T3-T4 tomes (Rare)
 * - Tier 3 Mobs: Primarily drop T3-T4-T5 tomes (Unique)
 */
public class RunicTomeLootHandler {

    // Mob tier classification
    private static final Map<EntityType<?>, Integer> MOB_TIERS = new HashMap<>();

    public static void register() {
        registerMobTiers();

        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            // Only modify entity loot tables
            if (!source.isBuiltin() || key.getValue().getPath().startsWith("blocks/")) {
                return;
            }

            // Get entity type from loot table key
            EntityType<?> entityType = getEntityTypeFromLootTable(key);
            if (entityType == null) return;

            Integer mobTier = MOB_TIERS.get(entityType);
            if (mobTier == null) return;

            // Add appropriate tome drops based on mob tier
            addTomeDrops(tableBuilder, mobTier);
        });
    }

    private static void registerMobTiers() {
        // TIER 1 MOBS (Common enemies, basic threats)
        registerTier1(EntityType.ZOMBIE);
        registerTier1(EntityType.SKELETON);
        registerTier1(EntityType.SPIDER);
        registerTier1(EntityType.CAVE_SPIDER);
        registerTier1(EntityType.SLIME);
        registerTier1(EntityType.MAGMA_CUBE);
        registerTier1(EntityType.SILVERFISH);
        registerTier1(EntityType.ENDERMITE);
        registerTier1(EntityType.HUSK);
        registerTier1(EntityType.STRAY);
        registerTier1(EntityType.DROWNED);
        registerTier1(EntityType.CREEPER); // Added vanilla creeper

        // TIER 2 MOBS (Elite enemies, moderate threats)
        registerTier2(EntityType.PILLAGER);
        registerTier2(EntityType.VINDICATOR);
        registerTier2(EntityType.WITCH);
        registerTier2(EntityType.BLAZE);
        registerTier2(EntityType.GHAST);
        registerTier2(EntityType.ENDERMAN);
        registerTier2(EntityType.BOGGED);
        registerTier2(EntityType.BREEZE);
        registerTier2(EntityType.PIGLIN);
        registerTier2(EntityType.HOGLIN);
        registerTier2(EntityType.GUARDIAN);
        registerTier2(EntityType.PHANTOM);
        registerTier2(EntityType.ZOMBIFIED_PIGLIN);

        // TIER 3 MOBS (Boss/Elite enemies, major threats)
        registerTier3(EntityType.RAVAGER);
        registerTier3(EntityType.EVOKER);
        registerTier3(EntityType.PIGLIN_BRUTE);
        registerTier3(EntityType.WARDEN);
        registerTier3(EntityType.ELDER_GUARDIAN);
        registerTier3(EntityType.WITHER_SKELETON);
        registerTier3(EntityType.SHULKER);
        registerTier3(EntityType.WITHER);
        registerTier3(EntityType.ENDER_DRAGON);
    }

    private static void registerTier1(EntityType<?> entityType) {
        MOB_TIERS.put(entityType, 1);
    }

    private static void registerTier2(EntityType<?> entityType) {
        MOB_TIERS.put(entityType, 2);
    }

    private static void registerTier3(EntityType<?> entityType) {
        MOB_TIERS.put(entityType, 3);
    }

    private static void addTomeDrops(LootTable.Builder tableBuilder, int mobTier) {
        LootPool.Builder poolBuilder = LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1));

        switch (mobTier) {
            case 1 -> addTier1Drops(poolBuilder);
            case 2 -> addTier2Drops(poolBuilder);
            case 3 -> addTier3Drops(poolBuilder);
        }

        tableBuilder.pool(poolBuilder);
    }

    /**
     * TIER 1 MOBS - Focus on T1 and T2 tomes
     * Total: 15% drop chance
     * Distribution (base, no luck):
     * - T1: 60% of drops (9% overall)
     * - T2: 35% of drops (5.25% overall)
     * - T3: 5% of drops (0.75% overall) - Lucky bonus
     *
     * With Luck: Shifts distribution toward higher tiers
     * Each point of luck increases the weight of higher tiers by 10%
     */
    private static void addTier1Drops(LootPool.Builder poolBuilder) {
        poolBuilder
                .with(ItemEntry.builder(ModItems.RUNIC_TOME_T1)
                        .weight(60)
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))))
                .with(ItemEntry.builder(ModItems.RUNIC_TOME_T2)
                        .weight(35)
                        .quality(1)  // LUCK BONUS: +10% weight per luck point
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))))
                .with(ItemEntry.builder(ModItems.RUNIC_TOME_T3)
                        .weight(5)
                        .quality(2)  // LUCK BONUS: +20% weight per luck point (better scaling)
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))))
                .with(ItemEntry.builder(net.minecraft.item.Items.AIR)
                        .weight(485)
                        .quality(-1)); // LUCK PENALTY: Less likely to get nothing with luck
    }

    /**
     * TIER 2 MOBS - Focus on T2, T3, and T4 tomes
     * Total: 25% drop chance
     * Distribution (base, no luck):
     * - T2: 35% of drops (8.75% overall)
     * - T3: 40% of drops (10% overall)
     * - T4: 20% of drops (5% overall)
     * - T5: 5% of drops (1.25% overall) - Lucky bonus
     *
     * With Luck: Higher tiers become more common
     */
    private static void addTier2Drops(LootPool.Builder poolBuilder) {
        poolBuilder
                .with(ItemEntry.builder(ModItems.RUNIC_TOME_T2)
                        .weight(35)
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))))
                .with(ItemEntry.builder(ModItems.RUNIC_TOME_T3)
                        .weight(40)
                        .quality(1)  // LUCK BONUS
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))))
                .with(ItemEntry.builder(ModItems.RUNIC_TOME_T4)
                        .weight(20)
                        .quality(2)  // LUCK BONUS: Better scaling
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))))
                .with(ItemEntry.builder(ModItems.RUNIC_TOME_T5)
                        .weight(5)
                        .quality(3)  // LUCK BONUS: Best scaling for rarest item
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))))
                .with(ItemEntry.builder(net.minecraft.item.Items.AIR)
                        .weight(300)
                        .quality(-1)); // LUCK PENALTY
    }

    /**
     * TIER 3 MOBS - Focus on T3, T4, and T5 tomes
     * Total: 40% drop chance (bosses/elites should be rewarding)
     * Distribution (base, no luck):
     * - T3: 30% of drops (12% overall)
     * - T4: 40% of drops (16% overall)
     * - T5: 30% of drops (12% overall) - Much higher than T2 mobs
     *
     * With Luck: T5 becomes significantly more common
     */
    private static void addTier3Drops(LootPool.Builder poolBuilder) {
        poolBuilder
                .with(ItemEntry.builder(ModItems.RUNIC_TOME_T3)
                        .weight(30)
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))))
                .with(ItemEntry.builder(ModItems.RUNIC_TOME_T4)
                        .weight(40)
                        .quality(1)  // LUCK BONUS
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))))
                .with(ItemEntry.builder(ModItems.RUNIC_TOME_T5)
                        .weight(30)
                        .quality(3)  // LUCK BONUS: Strong scaling for legendary items
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))))
                .with(ItemEntry.builder(net.minecraft.item.Items.AIR)
                        .weight(150)
                        .quality(-2)); // LUCK PENALTY: Stronger than lower tiers
    }

    private static EntityType<?> getEntityTypeFromLootTable(RegistryKey<LootTable> key) {
        String path = key.getValue().getPath();

        // Loot tables are usually "entities/entity_name"
        if (!path.startsWith("entities/")) {
            return null;
        }

        String entityName = path.substring("entities/".length());
        Identifier entityId = Identifier.of("minecraft", entityName);

        // Get EntityType from the registry
        return net.minecraft.registry.Registries.ENTITY_TYPE.get(entityId);
    }

    /**
     * Helper method to add new mob tiers programmatically
     */
    public static void registerCustomMob(EntityType<?> entityType, int tier) {
        if (tier < 1 || tier > 3) {
            throw new IllegalArgumentException("Mob tier must be between 1 and 3");
        }
        MOB_TIERS.put(entityType, tier);
    }
}